package screen;

import camp.CampController;
import screen.enquiry.StaffInChargeEnquiryScreen;
import screen.suggestion.StaffInChargeSuggestionScreen;
import user.Staff;
import user.UserController;

public class StaffInChargeScreen extends StaffScreen {
    public StaffInChargeScreen(UserController userController, CampController campController, Staff staff) {
        super(userController, campController, staff);
    }

    @Override
    public Screen display() {
        System.out.println("--------------------------");


        var camps = campController.getInChargeCamps(staff);
        System.out.println("Camps in charged: ");
        displayContents(camps);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: View all attendees and committees: ");
        System.out.println("1: View enquiries: ");
        System.out.println("2: View suggestions: ");
        System.out.println("3: Generate attendance report: ");
        System.out.println("4: Generate performance report: ");
        System.out.println("9: Back.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                try {
                    var selectedCamp = select(camps);
                    displayContents(selectedCamp.getStudentNames());
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 1 -> {
                try {
                    var selectedCamp = select(camps);
                    yield new StaffInChargeEnquiryScreen(userController, campController, staff, selectedCamp);
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 2 -> {
                try {
                    var selectedCamp = select(camps);
                    yield new StaffInChargeSuggestionScreen(userController, campController, staff, selectedCamp);
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 9 -> new StaffScreen(userController, campController, staff);
            default -> this;
        };
    }
}
