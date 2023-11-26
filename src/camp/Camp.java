package camp;

import java.io.FileWriter;
import java.io.IOException;
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

/**
 * Camp class for creating camp.
 */
public class Camp
{
	/**
	 * The default user group for the Camp.
	 */
    private static final String GlobalUserGroup = "NTU";
    
    /**
     * The staff object.
     */
    private final Staff staff;
    
    /**
     * The list IDs of the student who joined the camp as normal attendee.
     */
    private final List<String> attendees;
    
    /**
     * The list IDs of the student who joined the camp as camp committee member.
     */
    private final List<String> committees;
    
    /**
     * The list of suggestions submitted by the camp committee members.
     */
    private final List<Suggestion> suggestions;
    
    /**
     * The list of enquiries submitted by the students.
     */
    private final List<Enquiry> enquiries;
    
    /**
     * The list of students that withdraw from the Camp.
     */
    private final List<String> leftAttendees;
    
    /**
     * The camp information object.
     */
    private final CampInformation campInfo;
    
    /**
     * Visibility of Camp.
     * Decide if the Camp is hidden from the user.
     */
    private boolean visible;
    
    /**
     * Creates a new Camp with the given name, duration, registration deadline, user group,
     * location, total slots, camp committee slot, description, staff in-charge and visibility.
     * @param campName This Camp's name.
     * @param region This Camp's start date and end date.
     * @param regCloseDate This Camp's registration deadline.
     * @param userGroup This Camp's is open to which group(faculty) of students.
     * @param location This Camp's location where it will be held at.
     * @param totalSlots This Camp's number of total slots.
     * @param campCommitteeSlot This Camp's number of committee slots.
     * @param description This Camp's description.
     * @param staff This Camp's description.
     * @param visible This Camp's visibility.
     */
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

    /**
     * It returns a map of all variables names and its values.
     * @return a map of all variables names and its values.
     */
    public Map<String, String> getDetailsAsValue() {
        return campInfo.getPairs();
    }

    /**
     * Check if the camp attendee slots is filled up.
     */
    boolean isFullAttendee() {
        return (attendees.size() + committees.size()) == campInfo.getTotalSlots();
    }
    
    /**
     * Check if the camp committee slots is filled up.
     * @return True if filled up and false if not filled up.
     */
    public boolean isFullCommittee() {
        return committees.size() == campInfo.getCampCommitteeSlots();
    }

    /**
     * Check if the user belong to the user group (NTU/faculty).
     * @param userGroup NTU or own school (SCSE, SBS, NBS, ADM, EEE etc).
     * @return True if this user belong to the user group and false if this user does not
     * belong to the user group.
     */
    public boolean isInUserGroup(String userGroup) {
        return userGroup.equals(campInfo.getUserGroup());
    }
    
    /**
     * Check if the students met the criteria to join the camp.
     * @param student current Student accessing the CAM system.
     * @throws CampControllerException Display error message to let the user know why he/she
     * not able to join the camp.
     */
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
    
    /**
     * Add student to the Camp as a normal camp attendee.
     * @param student current Student accessing the CAM system.
     * @throws CampControllerException If the student user cannot join the camp
     */
    public void addStudent(Student student) throws CampControllerException {
        doStudentChecks(student);
        attendees.add(student.getUserID());
        student.joinCamp(this);
    }

    /**
     * Add student to the Camp as a camp committee member.
     * @param student current Student accessing the CAM system.
     * @throws CampControllerException Display error message to let the user know why he/she
     * not able to join the camp.
     */
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
    
    /**
     * Remove student from the Camp.
     * @param student Current Student accessing the CAM system.
     * @throws CampControllerException Display error message to let the user know why he/she
     * not able to withdraw from the camp.
     */
    public void removeStudent(Student student) throws CampControllerException {
        if (committees.stream().anyMatch(s -> s.equals(student.getUserID()))) {
            throw new CampControllerException("A student committee cannot leave the camp!");
        }
        attendees.removeIf(s -> s.equals(student.getUserID()));
        leftAttendees.add(student.getUserID());
        student.removeCamp(this);
    }
    
    /**
     * Add suggestion submitted by the camp committee member in the suggestion list.
     * @param student Student who has a suggestion to add
     * @param suggestion Suggestion to be added
     */
    public void addSuggestion(Student student, Suggestion suggestion) {
        if (committees.stream().noneMatch(s -> s.equals(student.getUserID()))) {
            throw new RuntimeException("Only committee members can add suggestions");
        }
        suggestions.add(suggestion);
    }
    
    /**
     * Return the list of suggestion submitted by current Student using the CAM system 
     * which is also a camp committee member.
     * @param student current Student which is also a camp committee member accessing the CAM system.
     * @return Suggestion list submitted by current Student using the CAM system.
     */
    public List<Suggestion> getSentSuggestions(Student student) {
        return suggestions.stream().filter(suggestion -> suggestion.getUserID().equals(student.getUserID())).toList();
    }
    
    /**
     * Return a list of suggestion submitted by all the camp committee members.
     * @return Suggestion list.
     */
    public List<Suggestion> getAllSuggestions() {
        return suggestions;
    }
    
    /**
     * Delete suggestion from the suggestion list.
     * @param suggestion Suggestion submitted by the camp committee members.
     */
    public void deleteSuggestion(Suggestion suggestion) {
        suggestions.removeIf(s -> s == suggestion);
    }
    
    /**
     * Add the enquiry submitted by the Student in the enquiries list.
     * @param student Current Student accessing the CAM system.
     * @param enquiry Enquiry input by the student.
     */
    public void addEnquiries(Student student, Enquiry enquiry) {
        enquiries.add(enquiry);
    }
    
    /**
     * Return the list of enquiries submitted by the current Student accessing the CAM system.
     * @param student Current Student accessing the CAM system.
     * @return List of enquiries submitted by the current Student accessing the CAM system.
     */
    public List<Enquiry> getSentEnquiries(Student student) {
        return enquiries.stream().filter(enquiry -> enquiry.getUserID().equals(student.getUserID())).toList();
    }
    
    /**
     * Return the list of enquiries submitted by all the Student.
     * @return The list of enquiries submitted by all the Student.
     */
    public List<Enquiry> getAllEnquiries() {
        return enquiries;
    }
    
    /**
     * Delete the enquiry from the enquiries list.
     * @param enquiry Enquiry input by the student.
     */
    public void deleteEnquiries(Enquiry enquiry) {
        enquiries.removeIf(e -> e == enquiry);
    }
    
    /**
     * Return a list of all the student's IDs (Both camp attendees and committee members).
     * @return A list of all the student's IDs.
     */
    public List<String> getStudentNames() {
        
        return Stream.concat(attendees.stream(), committees.stream()).toList();
    }
    
    /**
     * Checks if the user is a staff or the camp is set to visible.
     * @param user Current user that is accessing the CAM system.
     * @return True if the user is a staff or the camp is set to visible and false otherwise.
     */
    boolean isVisible(User user) {
        return user instanceof Staff || (visible && (campInfo.getUserGroup().equals("NTU") || isInUserGroup(user.getFaculty())));
    }
    
    /**
     * Check if the current staff accessing the CAM system is in charge of this Camp.
     * @param staff Current staff accessing the CAM system.
     * @return True if the current staff accessing the CAM system is in charge of this Camp
     * and false otherwise.
     */
    boolean isInCharge(Staff staff)
    { 
    	return this.staff == staff; 
    }
    
    /**
     * Return the value of the variable "visible".
     * @return The value of the variable "visible".
     */
    public boolean getVisibility() {
        return visible;
    }
    
    /**
     * Toggle the value of visibility (off to on OR on to off)
     * @return The previous value of the variable "visible".
     */
    public boolean toggleVisibility() {
        boolean ret = visible;
        visible = !visible;
        return ret;
    }
    
    /**
     * Check if the current staff accessing the CAM system is in charge of this Camp.
     * @param staff Current staff accessing the CAM system.
     * @return True if the current staff accessing the CAM system is in charge of this Camp
     * and false otherwise.
     */
    public boolean isOwner(Staff staff) {
        return staff == this.staff;
    }
    
    /**
     * Return the name of the Camp.
     */
    @Override
    public String toString() 
    {
        return campInfo.getCampName();
    }

    /**
     * Checks if registering for the camp is before the closing registration date.
     * @return True if registering for the camp is before the closing registration date
     * and false otherwise.
     */
    public boolean checkIsAfterCloseDate() {
        return LocalDateTime.now().toLocalDate().isBefore(campInfo.getRegCloseDate());
    }

    /**
     * Return the duration (start date and end date) of the Camp.
     * @return The duration (start date and end date) of the Camp.
     */
    public TimeRegion getRegion() {
        return campInfo.getTimeRegion();
    }

    /**
     * Return the list of student's ID who belong to this Camp committee.
     * @return The list of student's ID who belong to this Camp committee.
     */
    public List<String> getCommittees() {
        return committees;
    }
    
    /**
     * Generate attendance report for this Camp that consist of all the information of this Camp
     * and the attendee of this Camp and the camp committee member of this Camp.
     * Staff or camp committee members can choose if he/she want the list of attendees only
     * or the list of camp committee members only or both attendees and camp committee members.
     * Staff or camp committee members can choose if he/she want to generate the report
     * in csv or txt format. 
     * @param userController Controller for User.
     * @param filePath The path where the report want to be generated at.
     * @param reportChoice Generate in csv or txt format.
     */
    public void generateAttendance(UserController userController, String filePath, int reportChoice) {
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
             List<User> campAttendees = userController.getUsers(attendees);
             List<User> campCommittees = userController.getUsers(committees);
             int i = 0;
             switch (reportChoice)
             {
             	case 1:
                    writer.write("Attendees:\n");
                    i = 0;
                    for(var attendee: campAttendees) {
                        writer.write((i + 1) + ". " + attendee.getUserID() + ", " + attendee.getName() + ", Camp Attendee\n");
                        i++;
                    }
                    writer.write("Committees:\n");
                    i = 0;
                    for (var committee: campCommittees) {
                        writer.write((i + 1) + ". " + committee.getUserID() + ", " + committee.getName() + ", Camp Committee\n");
                        i++;
                    }
                    break;
             	case 2:
                    i = 0;
                    for(var attendee: campAttendees) {
                        writer.write((i + 1) + ". " + attendee.getUserID() + ", " + attendee.getName() + ", Camp Attendee\n");
                        i++;
                    }
             		break;
             	case 3:
                     i = 0;
                     for (var committee: campCommittees) {
                         writer.write((i + 1) + ". " + committee.getUserID() + ", " + committee.getName() + ", Camp Committee\n");
                         i++;
                     }
             		break;
             }
             
         } catch (IOException e) {
             // Handle IOException, e.g., log the error or print a message
             e.printStackTrace();
         }
    }

    /**
     * Generate the report of enquiry for this Camp.
     * @param filePath The path where the report want to be generated at.
     */
    public void generateEnquiryReport(String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            for (var enquiry: enquiries) {
                fileWriter.write( enquiry.getUserID() + ", " + enquiry.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the name of this Camp.
     * @return This Camp's name.
     */
    public String getName() {
        return campInfo.getCampName();
    }
    
    /**
     * Gets the location of this Camp.
     * @return This Camp's location.
     */
    public String getLocation() {
        return campInfo.getLocation();
    }
    
    /**
     * Get the name of the staff in-charge of this Camp.
     * @return This Camp's name of the staff in-charge.
     */
    public String getInCharge()
    {
    	return campInfo.getInCharge();
    }
    
    /**
     * Changes the number of camp committee slots (capped at 10 slots) of this Camp.
     * @param slots This Camp's new camp committee slots.
     */
    public void setCampCommSlots(int slots)
    {
    	campInfo.setCampCommitteeSlots(slots);
    }
    
    /**
     * Changes the number of total slots of this Camp.
     * @param slots This Camp's new total slots.
     */
    public void setTotalSlots(int slots)
    {
    	campInfo.setTotalSlots(slots);
    }
    
    /**
     * Changes the registration deadline of this Camp.
     * @param regCloseDate This Camp's new registration deadline.
     */
    public void setRegCloseDate(LocalDate regCloseDate)
    {
    	campInfo.setRegCloseDate(regCloseDate);
    }
    
    /**
     * Changes the description of this Camp.
     * @param description This Camp's new description.
     */
    public void setDescription(String description)
    {
    	campInfo.setDescription(description);
    }

    /**
     * Changes the location of this Camp.
     * @param location Camp's new location.
     */
    public void setLocation(String location)
    {
    	campInfo.setLocation(location);
    }
    
    /**
     * Changes the name of this Camp.
     * @param campName This Camp's new name.
     */
    public void setCampName(String campName)
    {
    	campInfo.setCampName(campName);
    }
    
    /**
     * Changes the duration (start date and end date) of this Camp.
     * @param region This Camp's new duration (start date and end date).
     */
    public void setRegion(TimeRegion region)
    {
    	campInfo.setTimeRegion(region);
    }
    
    /**
     * Changes the user group (faculty) of this Camp.
     * @param userGrp This Camp's new user group (faculty).
     */
    public void setUserGroup(String userGrp)
    {
    	campInfo.setUserGroup(userGrp);
    }
    
    /**
     * Return the remaining available slots of the camp committee.
     * @return The remaining available slots of the camp committee.
     */
    public int getRemaindingCommittee()
    { 
    	return Math.min(campInfo.getTotalSlots() - committees.size(), getRemainding());
    }
    
    /**
     * Return the remaining available slots of the total slots.
     * Total Slots - Number of Camp Attendee - Number of Camp Committee Member.
     * @return The remaining available slots of the total slots.
     */
    public int getRemainding()
    {
    	return campInfo.getTotalSlots() - attendees.size() - committees.size(); 
    }  // attendees should include committee members already
}
