package screen;

import camp.Camp;
import camp.CampController;
import camp.CampControllerException;
import screen.enquiry.StudentCommitteeEnquiryScreen;
import screen.suggestion.StudentCommitteeSuggestionScreen;
import user.StudentCommittee;
import user.UserController;

import java.util.List;
/**
 * screen for student committee member
 */
public class StudentCommitteeScreen extends Screen {
    /**
     * student committee member in charge
     */
	protected StudentCommittee studentCommittee;
	/**
	 * Constructor
	 * @param userController user controller
	 * @param campController camp controller
	 * @param studentCommittee student committee member
	 */
    public StudentCommitteeScreen(UserController userController, CampController campController, StudentCommittee studentCommittee) {
        super(userController, campController);
        this.studentCommittee = studentCommittee;
    }
    /**
     * print list of camps to screen
     * @param camps list of camps
     */
    private void printCamp(List< Camp > camps) {
        System.out.println("Camps: ");
        {
            int count = 0;
            for (var camp : camps) {
                System.out.println(count + ": " + camp + " " + camp.getRemainding() + " total slots left " + camp.getRemaindingCommittee() + " committee slots left.");
                count++;
            }
        }
    }

    @Override
    /**
     * display screen for student committee member
     */
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("Logging in as " + studentCommittee.getName() + " student committee of " + studentCommittee.getCommitteeCamp().getName());

        if(studentCommittee.toggleFirstLogin()) {
            System.out.println("Please change your password! Thank you.");
        }

        var camps = campController.getVisibleCamps(studentCommittee);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("-1: View registered camps (excluding committee camp).");
        System.out.println("0: View camps");
        System.out.println("1: Register as StudentAttendee.");
        System.out.println("2: View all enquiries.");
        System.out.println("3. View sent suggestions or submit suggestion.");
        System.out.println("4. Leave attendee camps.");
        System.out.println("5: View details of committee camp.");
        System.out.println("6: Generate attendance report.");
        System.out.println("7: Generate enquiry report.");
        System.out.println("8: Select filters to camps.");
        System.out.println("9: Logout.");
        System.out.println("10: Change Password.");
        System.out.println("11: Quit.");


        int choice = -2;
        try {
            choice = getInt();
        } catch (ScreenException e) {
            System.out.println(e.getMessage());
        }

        return switch (choice) {
            case -1 -> {
                printCamp(studentCommittee.getAttendingCamps());
                yield this;
            }
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
            case 2 ->
                    new StudentCommitteeEnquiryScreen(userController, campController, studentCommittee, studentCommittee.getCommitteeCamp());
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
                    var attendeeCamps = camps.stream().filter(c -> c != studentCommittee.getCommitteeCamp()).toList();
                    printCamp(attendeeCamps);
                    var selectedCamp = select(attendeeCamps);
                    System.out.println("Requested withdraw for " + selectedCamp);
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
            case 6 -> {
                boolean isCommMem = false;
                try {
                    int reportChoice, formatChoice;
                    var selectedCamp = studentCommittee.getCommitteeCamp();
                    for (int i = 0; i < selectedCamp.getCommittees().size(); i++) {
                        if (selectedCamp.getCommittees().get(i) == studentCommittee.getUserID()) {
                            isCommMem = true;
                        }
                    }
                    if (isCommMem) {
                        do {
                            System.out.println("Select what you want to include in the report: ");
                            System.out.println("1: Both Camp Attendees and Camp Committees");
                            System.out.println("2: Camp Attendees");
                            System.out.println("3: Camp Committees");
                            reportChoice = getInt();
                        } while (reportChoice < 1 || reportChoice > 3);
                        do {
                            System.out.println("Select report format: ");
                            System.out.println("1: Text File");
                            System.out.println("2: CSV File");
                            formatChoice = getInt();
                        } while (formatChoice < 1 || formatChoice > 2);
                        switch (formatChoice) {
                            case 1:
                                selectedCamp.generateAttendance(userController, "../data/Committee_Report" + selectedCamp.getName() + ".txt", reportChoice);
                                break;
                            case 2:
                                selectedCamp.generateAttendance(userController, "../data/Committee_Report" + selectedCamp.getName() + ".csv", reportChoice);
                                break;
                        }

                        System.out.println("Report saved to directory.");
                    } else {
                        System.out.println("You are not allowed to generate a report");
                    }
                } catch (ScreenException e) {
                    System.out.println("Error Generating Report!");
                }
                yield this;
            }
            case 7 -> {
                try {
                    System.out.println("Select a camp: ");
                    printCamp(camps);
                    var selectedCamp = select(camps);
                    selectedCamp.generateEnquiryReport("../data/Committee_Enquiry_Report" + selectedCamp.getName() + ".csv");
                    System.out.println("Generated enquiry report!");
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 8 -> {
                selectFilter(studentCommittee);
                yield this;
            }
            case 9 -> new UserLoginScreen(userController, campController);
            case 10 -> {
                changePassword(studentCommittee);
                yield this;
            }
            case 11 -> null;
            default -> this;
        };
    }
}
