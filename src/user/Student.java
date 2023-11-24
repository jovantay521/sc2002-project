package user;

import camp.Camp;

import java.util.ArrayList;
import java.util.List;

/**
 * Student class with its functionalities.
 */
public class Student extends User {
    /**
     * All attending camps, used for error checking.
     */
    protected List<Camp> attendingCamps; //Change to public to so I could use it to display the camp attending by the student on the screen

    /**
     * Student Constructor
     * @param name Student Name
     * @param userID Student UID
     * @param password Student Password
     * @param faculty Student Faculty
     */
    public Student(String name, String userID, String password, String faculty) {
        super(name, userID, password, faculty);
        attendingCamps = new ArrayList<>();
    }
    // check for conflicts of time, camp remaining slots.

    /**
     * Check if this student joins the following camp, they will be a conflict in time slots.
     * @param joinCamp Camp to be joined.
     * @return True if there is no conflicts in time, otherwise false.
     */
    public boolean checkTimeConflicts(Camp joinCamp) {
        return attendingCamps.stream().noneMatch(camp -> camp.getRegion().conflictsWith(joinCamp.getRegion()));
    }

    /**
     * Join camp
     * @param camp Camp to be joined
     */
    public void joinCamp(Camp camp) {
    	
    	attendingCamps.add(camp);	
    }

    /**
     * Remove camp
     * @param camp Camp to be removed
     */
    public void removeCamp(Camp camp) { 
    	
    	attendingCamps.remove(camp); 
    }

    public List<Camp> getAttendingCamps() {
        return attendingCamps;
    }
}
