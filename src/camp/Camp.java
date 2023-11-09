package camp;

import java.util.ArrayList;
import java.util.List;

import user.Staff;
import user.StudentCommittee;
import user.User;

public class Camp {
    private String name;
    private Staff staff;
    // Student and StudentCommittee 
    private List<Student> members;

    // and other things here
    private boolean visible;
    public Camp(String name, Staff staff, boolean visible) {
        this.name = name;
        this.staff = staff;
        this.visible = visible;

        members = new ArrayList<>();
    }
    // Checks if the user is a staff or the camp is set to visible.
    boolean isVisible(User user) {
        return user instanceof Staff || visible;
    }
    boolean isInCharge(Staff staff) { return this.staff == staff; }
    @Override
    public String toString() {
        return "{ Camp: " + name + " }";
    }
    public List<Student> getMembers(User user) {
        if (user instanceof StudentCommittee || user instanceof Staff) {
            return members;
        }
        return null;
    }
}
