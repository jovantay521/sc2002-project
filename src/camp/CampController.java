package camp;

import user.Staff;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
