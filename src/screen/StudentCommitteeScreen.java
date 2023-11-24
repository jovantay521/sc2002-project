package screen;

import camp.Camp;
import camp.CampController;
import camp.CampControllerException;
import camp.Suggestion;
import screen.enquiry.StudentCommitteeEnquiryScreen;
import screen.suggestion.StudentCommitteeSuggestionScreen;
import user.StudentCommittee;
import user.UserController;

import java.util.List;

public class StudentCommitteeScreen extends Screen {
    protected StudentCommittee studentCommittee;
    public StudentCommitteeScreen(UserController userController, CampController campController, StudentCommittee studentCommittee) {
        super(userController, campController);
        this.studentCommittee = studentCommittee;
    }

    private void printCamp(List< Camp > camps) {
        System.out.println("Camps: ");
        {
            int count = 0;
            for (var camp : camps) {
                System.out.println(count + ": " + camp + " " + camp.getRemainding() + " slots left.");
                count++;
            }
        }
    }

    @Override
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("Logging in as " + studentCommittee.getName() + " student committee of " + studentCommittee.getCommitteeCamp().getName());

        if(studentCommittee.toggleFirstLogin()) {
            System.out.println("Please change your password! Thank you.");
        }

        var camps = campController.getVisibleCamps(studentCommittee);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: View camps");
        System.out.println("1: Register as StudentAttendee.");
        System.out.println("2: View all enquiries.");
        System.out.println("3. View sent suggestions or submit suggestion.");
        System.out.println("4. Leave attendee camps.");
        System.out.println("5: View details of committee camp.");
        System.out.println("6: Generate attendance report.");
        System.out.println("7: Select filters to camps.");
        System.out.println("8: Logout.");
        System.out.println("9: Change Password.");
        System.out.println("10: Quit.");


        int choice = -1;
        try {
            choice = getInt();
        } catch (ScreenException e) {
            System.out.println(e.getMessage());
        }

        return switch (choice) {
            case 0 -> {
                printCamp(camps);
                yield this;
            }
            case 1 -> {
                try {
                    System.out.println("Select a camp: ");
                    printCamp(camps);
                    var selectedCamp = select(camps);
                    selectedCamp.addStudent(studentCommittee);
                    System.out.println("Joined camp! " + selectedCamp);
                } catch (ScreenException | CampControllerException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 2 -> {
                try {
                    System.out.println("Select a camp: ");
                    printCamp(camps);
                    var selectedCamp = select(camps);
                    yield new StudentCommitteeEnquiryScreen(userController, campController, studentCommittee, selectedCamp);
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 3 -> {
                try {
                    System.out.println("Select a camp: ");
                    printCamp(camps);
                    var selectedCamp = select(camps);
                    yield new StudentCommitteeSuggestionScreen(userController, campController, studentCommittee, selectedCamp);
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 4 -> {
                try {
                    System.out.println("Select a camp: ");
                    printCamp(camps);
                    var selectedCamp = select(camps);
                    System.out.println("Requested withdraw for" + selectedCamp);
                    selectedCamp.removeStudent(studentCommittee);
                } catch (ScreenException | CampControllerException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 5 -> {
                var pairs = studentCommittee.getCamp().getDetailsAsValue();
                System.out.println();
                System.out.println("Details: ");
                for (var pair: pairs.entrySet()) {
                    System.out.println(pair.getKey() + ": " + pair.getValue());
                }
                yield this;
            }
            case 7 -> {
                selectFilter(studentCommittee);
                yield this;
            }
            case 8 -> new UserLoginScreen(userController, campController);
            case 9 -> {
                changePassword(studentCommittee);
                yield this;
            }
            case 10 -> null;
            default -> this;
        };
    }
}
