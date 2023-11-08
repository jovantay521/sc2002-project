package screen;

import camp.Camp;
import camp.CampController;
import user.Staff;
import user.UserController;

public class StaffInChargeEnquiryScreen extends StaffScreen {
    protected Camp camp;
    StaffInChargeEnquiryScreen(UserController userController, CampController campController, Staff staff, Camp camp) {
        super(userController, campController, staff);
        this.camp = camp;
    }

    @Override
    public Screen display() {
        System.out.println("Enquiries for Camp " + camp);
        System.out.println("9: Back.");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 9 -> new StaffInChargeScreen(userController, campController, staff);
            default -> this;
        };
    }
}
