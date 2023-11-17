package screen.enquiry;

import camp.Camp;
import camp.CampController;
import screen.Screen;
import screen.ScreenException;
import screen.StudentScreen;
import user.Student;
import user.UserController;

public class StudentEnquiryScreen extends StudentScreen {
    protected Camp camp;
    public StudentEnquiryScreen(UserController userController, CampController campController, Student student, Camp camp) {
        super(userController, campController, student);
        this.camp = camp;
    }

    @Override
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("Personal enquiries for camp <" + camp + ">");

        var enquiries = camp.getSentEnquiries(student);
        displayContents(enquiries);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: Edit enquiry: ");
        System.out.println("1: Delete enquiry: ");
        System.out.println("9: Back.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                try {
                    System.out.println("Select enquiry: ");
                    var enquiry = select(enquiries);
                    System.out.println("Type new message to change.");
                    var message = scanner.nextLine();
                    enquiry.edit(message);
                    System.out.println("Enquiry changed.");
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 1 -> {
                try {
                    System.out.println("Select enquiry: ");
                    var enquiry = select(enquiries);
                    camp.deleteEnquiries(enquiry);
                    System.out.println("Enquiry deleted.");
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 9 -> new StudentScreen(userController, campController, student);
            default -> this;
        };
    }
}
