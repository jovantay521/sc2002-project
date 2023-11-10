package camp;

import user.Staff;
import user.User;
import java.util.*;
import java.util.stream.Collectors;


public class CampController
{
    private final List<Camp> camps = new ArrayList<>();
    
    // Returns list of camps that can be viewed by user
    public List<Camp> getVisibleCamps(User user)
    {
        return camps.stream().filter(camp -> camp.isVisible(user)).collect(Collectors.toList());
    }
    
    // Returns list of camps that belongs to staff
    public List<Camp> getInChargeCamps(Staff staff)
    {
        return camps.stream().filter(camp -> camp.isInCharge(staff)).collect(Collectors.toList());
    }
    // creates a camp
    public void createCamp(Staff staff, String campName, String startDate, String endDate, String regCloseDate, String userGroup, String location, int totalSlots, int campCommitteeSlot, String description)
    {
        camps.add(new Camp(campName, startDate, endDate, regCloseDate, userGroup, location, totalSlots, campCommitteeSlot, description, staff, true));
    }

    public static<T> void displayCamps(List<T> camps) {
        if (camps.isEmpty()) {
            System.out.println("None");
        } else {
            for (var it = camps.listIterator(); it.hasNext(); ) {
                T t = it.next();
                System.out.println(it.previousIndex() + ": " + t.toString());
            }
        }
    }

    public static Camp selectCamp(List<Camp> camps) {
        System.out.println("Select a camp: ");
        Scanner scanner = new Scanner(System.in);
        int campChoice = scanner.nextInt();
        scanner.nextLine();
        try {
            return camps.get(campChoice);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not a valid selection.");
        }
        return null;
    }
}
