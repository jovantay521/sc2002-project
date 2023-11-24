package camp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import user.UserController;
import user.Staff;
import user.Student;
import user.StudentCommittee;
import user.User;
import utils.TimeRegion;

public class Camp
{
    private static final String GlobalUserGroup = "NTU";
    private final Staff staff;
    // Student and StudentCommittee IDs
    private final List<String> attendees;
    private final List<String> committees;
    private final List<Suggestion> suggestions;
    private final List<Enquiry> enquiries;
    private final List<String> leftAttendees;
    private final CampInformation campInfo;
    private boolean visible;
    
    public Camp(String campName, TimeRegion region, LocalDate regCloseDate, String userGroup,
            String location, int totalSlots, int campCommitteeSlot, String description, Staff staff, boolean visible)
    {
        campInfo = new CampInformation(campName, region, regCloseDate, userGroup, location, totalSlots, campCommitteeSlot, description, staff);
        this.staff = staff;
        this.visible = visible;

        enquiries = new ArrayList<>();
        suggestions = new ArrayList<>();
        attendees = new ArrayList<>();
        committees = new ArrayList<>();
        leftAttendees = new ArrayList<>();
    }

    public Map<String, String> getDetailsAsValue() {
        return campInfo.getPairs();
    }

    boolean isFullAttendee() {
        return attendees.size() == campInfo.getTotalSlots();
    }
    
    public boolean isFullCommittee() {
        return committees.size() == campInfo.getCampCommitteeSlots();
    }

    public boolean isInUserGroup(String userGroup) {
        return userGroup.equals(campInfo.getUserGroup());
    }
    
    protected void doStudentChecks(Student student) throws CampControllerException {
        if (Stream.concat(attendees.stream(), committees.stream()).anyMatch(s -> s.equals(student.getUserID()))) {
            throw new CampControllerException("The student is already in the camp.");
        }
        if (!checkIsAfterCloseDate()) {
            throw new CampControllerException("Registration date for this camp has passed.");
        }
        if (!student.checkTimeConflicts(this)) {
            throw new CampControllerException("Student cannot join this camp due to conflicts in time.");
        }
        if (leftAttendees.stream().anyMatch(s -> s.equals(student.getUserID()))) {
            throw new CampControllerException("You are not allowed to join " + campInfo.getCampName() + " as you have left previously.");
        }
        if (isFullAttendee()) {
            throw new CampControllerException("This camp is full! Please join another camp.");
        }
    }
    
    public void addStudent(Student student) throws CampControllerException {
        doStudentChecks(student);
        attendees.add(student.getUserID());
        student.joinCamp(this);
    }

    public void addStudentCommittee(Student student) throws CampControllerException {
        if (student instanceof StudentCommittee) {
            throw new CampControllerException("Student is already a student committee!");
        }
        if (isFullCommittee()) {
            throw new CampControllerException("Camp committee is full!");
        }
        doStudentChecks(student);
        committees.add(student.getUserID());
        student.joinCamp(this);
    }
    
    public void removeStudent(Student student) throws CampControllerException {
        if (committees.stream().anyMatch(s -> s.equals(student.getUserID()))) {
            throw new CampControllerException("A student committee cannot leave the camp!");
        }
        attendees.removeIf(s -> s.equals(student.getUserID()));
        leftAttendees.add(student.getUserID());
        student.removeCamp(this);
    }
    
    public void addSuggestion(Student student, Suggestion suggestion) {
        if (committees.stream().noneMatch(s -> s.equals(student.getUserID()))) {
            throw new RuntimeException("Only committee members can add suggestions");
        }
        suggestions.add(suggestion);
    }
    
    public List<Suggestion> getSentSuggestions(Student student) {
        return suggestions.stream().filter(suggestion -> suggestion.getUserID().equals(student.getUserID())).toList();
    }
    public List<Suggestion> getAllSuggestions() {
        return suggestions;
    }
    public void deleteSuggestion(Suggestion suggestion) {
        suggestions.removeIf(s -> s == suggestion);
    }
    public void addEnquiries(Student student, Enquiry enquiry) {
        enquiries.add(enquiry);
    }
    public List<Enquiry> getSentEnquiries(Student student) {
        return enquiries.stream().filter(enquiry -> enquiry.getUserID().equals(student.getUserID())).toList();
    }
    public List<Enquiry> getAllEnquiries() {
        return enquiries;
    }
    public void deleteEnquiries(Enquiry enquiry) {
        enquiries.removeIf(e -> e == enquiry);
    }
    public List<String> getStudentNames() {
        return Stream.concat(attendees.stream(), committees.stream()).toList();
    }
    
    // Checks if the user is a staff or the camp is set to visible.
    boolean isVisible(User user) {
        return user instanceof Staff || (visible && (campInfo.getUserGroup().equals("NTU") || isInUserGroup(user.getFaculty())));
    }
    boolean isInCharge(Staff staff)
    { 
    	return this.staff == staff; 
    }
    public boolean getVisibility() {
        return visible;
    }
    public boolean toggleVisibility() {
        boolean ret = visible;
        visible = !visible;
        return ret;
    }
    public boolean isOwner(Staff staff) {
        return staff == this.staff;
    }
    @Override
    public String toString() 
    {
        return campInfo.getCampName();
    }

    // Checks if this method is access after closing registration date.
    public boolean checkIsAfterCloseDate() {
        return LocalDateTime.now().toLocalDate().isBefore(campInfo.getRegCloseDate());
    }

    public TimeRegion getRegion() {
        return campInfo.getTimeRegion();
    }

    public List<String> getCommittees() {
        return committees;
    }
    
    /*public List<User> getCampAttendees() {
    	try
    	{
    		return userController.getUsers(attendees);
    	}
    	catch(Exception e)
    	{
    		return null;
    	}
    	
    }*/
    
    public void generateAttendance(String filePath, int reportChoice) {
        // TODO
    	 try (FileWriter writer = new FileWriter(filePath)) {
             // Write header
             writer.write("Camp Attendance Report\n");
             writer.write("Date: " + LocalDate.now() + "\n");
             writer.write("Camp Name: " + campInfo.getCampName() + "\n");
             writer.write("Camp Duration: " + campInfo.getTimeRegion() +"\n");
             writer.write("Registration Deadline: " + campInfo.getRegCloseDate() + "\n");
             writer.write("Camp Faculty: " + campInfo.getUserGroup() + "\n");
             writer.write("Location: " + campInfo.getLocation() + "\n");
             writer.write("Camp Attendee Slots: " + campInfo.getTotalSlots() + "\n");
             writer.write("Camp Committee Slots: " + campInfo.getCampCommitteeSlots() + "\n");
             writer.write("Camp Description: " + campInfo.getInCharge() + "\n");
             writer.write("\n");  // Add a blank line

             // Write attendees
             switch (reportChoice)
             {
             	case 1:
             		List<String> campAttendees = getStudentNames();
                    writer.write("Attendees:\n");
                    for (int i = 0; i < campAttendees.size(); i++) {
                    	for(int a = 0; a < attendees.size(); a++)
                   	 	{	
                    		if(campAttendees.get(i) == attendees.get(a))
                   		 	{
                   			 	writer.write((i + 1) + ". " + campAttendees.get(i) + ", Camp Attendee\n");
                   			 	break;
                   		 	} 
                   	 	}
                   	 	for(int b = 0; b < committees.size(); b++)
                   	 	{
                   	 		if(campAttendees.get(i) == committees.get(b))
                   	 		{
                   	 			writer.write((i + 1) + ". " + campAttendees.get(i) + ", Camp Committee\n");
                   	 			break;
                   	 		} 
                   	 	}   
                    }
                    break;
             	case 2:
             		for(int a = 0; a < attendees.size(); a++)
               	 	{
             			writer.write((a + 1) + ". " + attendees.get(a) + ", Camp Attendee\n");
               	 	}
             		break;
             	case 3:
             		for(int b = 0; b < committees.size(); b++)
               	 	{
             			writer.write((b + 1) + ". " + committees.get(b) + ", Camp Committee\n");
               	 	}
             		break;
             }
             
         } catch (IOException e) {
             // Handle IOException, e.g., log the error or print a message
             e.printStackTrace();
         }
    }
    public String getName() {
        return campInfo.getCampName();
    }
    public String getLocation() {
        return campInfo.getLocation();
    }

    public int getRemainding() { return campInfo.getTotalSlots() - attendees.size() - committees.size(); }
}
