package screen;

import camp.Camp;
import camp.CampController;
import user.Staff;
import user.UserController;

import static camp.CampController.displayCamps;
import static camp.CampController.selectCamp;

public class StaffInChargeScreen extends StaffScreen {
    StaffInChargeScreen(UserController userController, CampController campController, Staff staff) {
        super(userController, campController, staff);
    }

    @Override
    public Screen display() {
        Camp selectedCamp;

        var camps = campController.getInChargeCamps(staff);
        System.out.println("Camps in charged: ");
        displayCamps(camps);

        System.out.println("Options: ");
        System.out.println("0: View enquiries: ");
        System.out.println("1: View suggestions: ");
        System.out.println("2: Generate attendance report: ");
        System.out.println("4: Generate performance report: ");
        System.out.println("9: Back.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                if ((selectedCamp = selectCamp(camps)) != null) {
                    yield new StaffInChargeEnquiryScreen(userController, campController, staff, selectedCamp);
                }
                yield this;
            }
            case 1 -> {
                if ((selectedCamp = selectCamp(camps)) != null) {
                    yield new StaffInChargeSuggestionScreen(userController, campController, staff, selectedCamp);
                }
                yield this;
            }
            case 9 -> new StaffScreen(userController, campController, staff);
            default -> this;
        };
    }
}
