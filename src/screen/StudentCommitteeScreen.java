package screen;

import camp.Camp;
import camp.CampController;
import user.StudentCommittee;
import user.UserController;

import static camp.CampController.displayCamps;
import static camp.CampController.selectCamp;

public class StudentCommitteeScreen extends Screen {
    protected StudentCommittee studentCommittee;
    StudentCommitteeScreen(UserController userController, CampController campController, StudentCommittee studentCommittee) {
        super(userController, campController);
        this.studentCommittee = studentCommittee;
    }

    @Override
    public Screen display() {
        Camp selectedCamp;
        System.out.println("Camps: ");
        var camps = campController.getVisibleCamps(studentCommittee);
        displayCamps(camps);

        System.out.println("Options: ");
        System.out.println("0: Register as StudentAttendee.");
        System.out.println("1: Submit suggestion.");
        System.out.println("2: View all enquiries.");
        System.out.println("3. View sent suggestions.");
        System.out.println("7: Generate attendance report.");
        System.out.println("8: Change Password.");
        System.out.println("9: Quit.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 2 -> {
                if ((selectedCamp = selectCamp(camps)) != null) {
                    yield new StudentCommitteeEnquiryScreen(userController, campController, studentCommittee, selectedCamp);
                }
                yield this;
            }
            case 3 -> {
                if ((selectedCamp = selectCamp(camps)) != null) {
                   yield new StudentCommitteeSuggestionScreen(userController, campController, studentCommittee, selectedCamp);
                }
                yield this;
            }
            case 9 -> null;
            default -> this;
        };
    }
}
