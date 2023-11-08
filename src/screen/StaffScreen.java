package screen;

import camp.CampController;
import user.Staff;
import user.UserController;

import static camp.CampController.displayCamps;

public class StaffScreen extends Screen {
    protected Staff staff;
    StaffScreen(UserController userController, CampController campController, Staff staff) {
        super(userController, campController);
        this.staff = staff;
    }

    @Override
    public Screen display() {
        System.out.println("Camps: ");
        var camps = campController.getVisibleCamps(staff);
        displayCamps(camps);

        System.out.println("Options: ");
        System.out.println("0: Create camp.");
        System.out.println("1: Edit camp.");
        System.out.println("2: Delete camp.");
        System.out.println("7: Enter submenu.");
        System.out.println("8: Change Password.");
        System.out.println("9: Quit.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                System.out.println("Choose a camp name: ");
                var name = scanner.nextLine();
                campController.createCamp(staff, name);
                yield this;
            }
            case 7 -> new StaffInChargeScreen(userController, campController, staff);
            case 8 -> {
                System.out.println("Choose new password: ");
                var password = scanner.nextLine();
                staff.changePassword(password);
                yield this;
            }
            case 9 -> null;
            default -> this;
        };
    }
}
