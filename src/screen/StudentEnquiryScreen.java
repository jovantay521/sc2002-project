package screen;

import camp.Camp;
import camp.CampController;
import user.Student;
import user.UserController;

public class StudentEnquiryScreen extends StudentScreen {
    protected Camp camp;
    StudentEnquiryScreen(UserController userController, CampController campController, Student student, Camp camp) {
        super(userController, campController, student);
        this.camp = camp;
    }

    @Override
    public Screen display() {
        System.out.println("Enquiries options for camp " + camp);
        System.out.println("0: Edit enquiry: ");
        System.out.println("1: Delete enquiry: ");
        System.out.println("9: Back.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 9 -> new StudentScreen(userController, campController, student);
            default -> this;
        };
    }
}
