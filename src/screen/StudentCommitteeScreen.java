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
        System.out.println("--------------------------");
        System.out.println("Logging in as " + studentCommittee.getName());

        Camp selectedCamp;
        System.out.println("Camps: ");
        var camps = campController.getVisibleCamps(studentCommittee);
        displayCamps(camps);

        System.out.println("Options: ");
        System.out.println("0: Register as StudentAttendee.");
        System.out.println("1: Submit suggestion.");
        System.out.println("2: View all enquiries.");
        System.out.println("3. View sent suggestions.");
        System.out.println("4. Leave attendee camps.");
        System.out.println("6: Generate attendance report.");
        System.out.println("7: Logout.");
        System.out.println("8: Change Password.");
        System.out.println("9: Quit.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                if ((selectedCamp = selectCamp(camps)) != null) {
                    try {
                        selectedCamp.addStudent(studentCommittee);
                        System.out.println("Joined camp! " + selectedCamp);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e);
                    }
                }
                yield this;
            }
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
            case 7 -> new UserLoginScreen(userController, campController);
            case 9 -> null;
            default -> this;
        };
    }
}
