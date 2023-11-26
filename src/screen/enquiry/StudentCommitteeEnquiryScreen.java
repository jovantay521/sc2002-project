package screen.enquiry;

import camp.Camp;
import camp.CampController;
import screen.Screen;
import screen.ScreenException;
import screen.StudentCommitteeScreen;
import user.StudentCommittee;
import user.UserController;
/**
 * Student Committee Enquiry Screen
 */
public class StudentCommitteeEnquiryScreen extends StudentCommitteeScreen {
    /**
     * Camp
     */
	protected Camp camp;
    /**
     * Constructor for Student Committee Enquiry Screen 
     * @param userController User Controller
     * @param campController Camp Controller
     * @param studentCommittee Student Committee
     * @param camp Camp
     */
	public StudentCommitteeEnquiryScreen(UserController userController, CampController campController, StudentCommittee studentCommittee, Camp camp) {
        super(userController, campController, studentCommittee);
        this.camp = camp;
    }

    @Override
    /**
	 * Display
     */
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("All enquiries for camp <" + camp + ">");

        var enquiries = camp.getAllEnquiries();
        displayContents(enquiries);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: Reply to enquiry.");
        System.out.println("9: Back.");

        int choice = -1;
        try {
            choice = getInt();
        } catch (ScreenException e) {
            System.out.println(e.getMessage());
        }

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
