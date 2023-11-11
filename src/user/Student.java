package user;

import camp.Camp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    protected List<Camp> attendingCamps; // for error checking of joining camps
    public Student(String name, String userID, String password, String faculty) {
        super(name, userID, password, faculty);
        attendingCamps = new ArrayList<>();
    }
    // check for conflicts of time, camp remaining slots.
    public boolean checkTimeConflicts(Camp joinCamp) {
        return attendingCamps.stream().noneMatch(camp -> camp.getRegion().conflictsWith(joinCamp.getRegion()));
    }
    public void joinCamp(Camp camp) {
        attendingCamps.add(camp);
    }

}
