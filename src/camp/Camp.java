package camp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import user.Staff;
import user.Student;
import user.StudentCommittee;
import user.User;
import utils.TimeRegion;

public class Camp
{
    //private String name;
    private Staff staff;
    // Student and StudentCommittee 
    private List<Student> members;
    private CampInformation campInfo;

    // and other things here
    private boolean visible;
    
    public Camp(String campName, TimeRegion region, LocalDate regCloseDate, String userGroup,
            String location, int totalSlots, int campCommitteeSlot, String description, Staff staff, boolean visible)
    {
        campInfo = new CampInformation(campName, region, regCloseDate, userGroup, location, totalSlots, campCommitteeSlot, description, staff);
        this.staff = staff;
        this.visible = visible;

        members = new ArrayList<>();
    }
    
    // Checks if the user is a staff or the camp is set to visible.
    boolean isVisible(User user)
    {
        return user instanceof Staff || visible;
    }
    
    boolean isInCharge(Staff staff) 
    { 
    	return this.staff == staff; 
    }
    
    @Override
    public String toString() 
    {
        return campInfo.getCampName(); // for debugging
        // return "{\nCamp Name:" + campInfo.getCampName() + "\nStart Date: " + campInfo.getStartDate() + "\nEnd Date: " + campInfo.getEndDate() + "\nRegistration Closing Date: " + campInfo.getRegCloseDate() + "\nUser Group: " + campInfo.getUserGroup() + "\nLocation: " + campInfo.getLocation() + "\nTotal Slots: " + campInfo.getTotalSlots() +"\nCamp Commitee Slots: " + campInfo.getCampCommitteeSlots() + "\nCamp Description: " + campInfo.getDescription() + "\nCamp In-Charge: " + campInfo.getInCharge() + "\n}";
    }

    // Checks if this method is access after closing registration date.
    public boolean canJoinNow() {
        return LocalDateTime.now().toLocalDate().isAfter(campInfo.getRegCloseDate());
    }

    public CampInformation campInformation() {
        return campInfo;
    }

    public TimeRegion getRegion() {
        return campInfo.getTimeRegion();
    }
    
    public List<Student> getMembers(User user)
    {
        if (user instanceof StudentCommittee || user instanceof Staff)
        {
            return members;
        }
        return null;
    }
}
