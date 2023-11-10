package user;

import camp.Camp;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    protected List<Camp> attendingCamps; // for error checking of joining camps
    public Student(String name, String userID, String email, String password, String faculty) {
        super(name, userID, email, password, faculty);
        attendingCamps = new ArrayList<>();
    }
    // check for conflicts of time, camp remaining slots.
    public boolean canJoinCamp(Camp joinCamp) {
        if(joinCamp.canJoinNow() && !attendingCamps.stream().anyMatch(camp -> camp.getRegion().conflictsWith(joinCamp.getRegion()))) {
            return true;
        }
        return false;
    }

}
