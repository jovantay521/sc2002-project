package screen.enquiry;

import camp.Camp;
import camp.CampController;
import screen.Screen;
import screen.ScreenException;
import screen.StudentCommitteeScreen;
import user.StudentCommittee;
import user.UserController;

public class StudentCommitteeEnquiryScreen extends StudentCommitteeScreen {
    protected Camp camp;
    public StudentCommitteeEnquiryScreen(UserController userController, CampController campController, StudentCommittee studentCommittee, Camp camp) {
        super(userController, campController, studentCommittee);
        this.camp = camp;
    }

    @Override
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("All enquiries for camp <" + camp + ">");

        var enquiries = camp.getAllEnquiries();
        displayContents(enquiries);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: Reply to enquiry.");
        System.out.println("9: Back.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                try {
                    System.out.println("Select enquiry.");
                    var enquiry = select(enquiries);
                    System.out.println("Type message to reply.");
                    var reply = scanner.nextLine();
                    enquiry.reply(reply);
                    studentCommittee.addPoint();
                    System.out.println("Sent!");
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 9 -> new StudentCommitteeScreen(userController, campController, studentCommittee);
            default -> this;
        };
    }
}
