package camp;

import user.Staff;
import user.User;

public class Camp {
    private String name;
    private Staff staff;
    // and other things here
    private boolean visible = true;
    public Camp(String name, Staff staff) {
        this.name = name;
        this.staff = staff;
    }
    boolean isVisible(User user) {
        return user instanceof Staff || visible;
    }
    boolean isInCharge(Staff staff) { return this.staff == staff; }
    @Override
    public String toString() {
        return "{ Camp: " + name + " }";
    }
}
