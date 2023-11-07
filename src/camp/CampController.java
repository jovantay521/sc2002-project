package camp;

import user.Staff;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CampController {
    private final ArrayList<Camp> camps = new ArrayList<>();
    public List<Camp> getVisibleCamps(User user) {
        return camps.stream().filter(camp -> camp.isVisible(user)).collect(Collectors.toList());
    }
    public List<Camp> getInChargeCamps(Staff staff) {
        return camps.stream().filter(camp -> camp.isInCharge(staff)).collect(Collectors.toList());
    }
    public void createCamp(Staff staff, String name) {
        camps.add(new Camp(name, staff));
    }

}
