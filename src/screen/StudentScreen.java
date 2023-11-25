package screen;

import camp.Camp;
import camp.CampController;
import camp.CampControllerException;
import camp.Enquiry;
import screen.enquiry.StudentEnquiryScreen;
import user.Student;
import user.UserController;

import java.util.InputMismatchException;
import java.util.List;

public class StudentScreen extends Screen {
    protected Student student;
    public StudentScreen(UserController userController, CampController campController, Student student) {
        super(userController, campController);
        this.student = student;
    }
    private void printCamps(List<Camp> camps) {
        System.out.println("Camps: ");
        {
            int count = 0;
            for (var camp: camps) {
                System.out.println(count + ": " + camp + " " + camp.getRemainding() + " total slots left " + camp.getRemaindingCommittee() + " committee slots left.");
                count++;
            }
        }
    }
    @Override
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("Logging in as " + student.getName());

        if(student.toggleFirstLogin()) {
            System.out.println("Please change your password! Thank you.");
        }

        var camps = campController.getVisibleCamps(student);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: View all camps");
        System.out.println("1: Register as StudentAttendee or StudentCommittee.");
        System.out.println("2: Submit enquiries to a camp.");
        System.out.println("3: View sent enquiries.");
        System.out.println("4: Withdraw from camp.");
        System.out.println("5: View registered camps.");
        System.out.println("6: Select filters to camps.");
        System.out.println("7: Logout.");
        System.out.println("8: Change Password.");
        System.out.println("9: Quit.");

        int choice = -1;
        try {
            choice = getInt();
        } catch (ScreenException e) {
            System.out.println(e.getMessage());
        }

        return switch (choice) {
            case 0 -> {
                printCamps(camps);
                yield this;
            }
            case 1 -> {
                printCamps(camps);

                System.out.println("(0) Join as attendee or (1) Join as committee.");
                int whichChoice = 0;
                try {
                    whichChoice = getInt();
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }

                if (whichChoice == 0) {
                    try {
                        System.out.println("Select a camp: ");
                        var selectedCamp = select(camps);
                        selectedCamp.addStudent(student);
                        System.out.println("Registered student to " + selectedCamp + " as attendee.");
                    } catch (ScreenException | CampControllerException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (whichChoice == 1) {
                    try {
                        System.out.println("Select a camp: ");
                        var selectedCamp = select(camps);
                        selectedCamp.addStudentCommittee(student);
                        var committeeMember = userController.convertTo(student, selectedCamp);
                        System.out.println("Registered student to " + selectedCamp + " as camp committee member.");
                        yield new StudentCommitteeScreen(userController, campController, committeeMember);
                    } catch (ScreenException | CampControllerException e) {
                        System.out.println(e.getMessage());
                    }
                }
                yield this;
            }
            case 2 -> {
               try {
                    System.out.println("Select a camp: ");
                    printCamps(camps);
                    var selectedCamp = select(camps);
                    System.out.println("Type out your enquiry!");
                    var enquiry = scanner.nextLine();
                    selectedCamp.addEnquiries(student, new Enquiry(enquiry, student.getUserID()));
                    System.out.println("Enquiry submitted!");
               } catch (ScreenException e) {
                   System.out.println(e.getMessage());
               }
                yield this;
            }
            case 3 -> {
                try {
                    System.out.println("Select a camp: ");
                    printCamps(camps);
                    var selectedCamp = select(camps);
                    yield new StudentEnquiryScreen(userController, campController, student, selectedCamp);
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 4 -> {
                try {
                    var attendingCamps = student.getAttendingCamps();
                	displayContents(attendingCamps);
                    var selectedCamp = select(attendingCamps);
                    System.out.println("Requested withdraw for " + selectedCamp);
                    selectedCamp.removeStudent(student);
                } catch (ScreenException | CampControllerException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 5 -> {
                printCamps(student.getAttendingCamps());
                yield this;
            }
            case 6 -> {
                selectFilter(student);
                yield this;
            }
            case 7 -> new UserLoginScreen(userController, campController);
            case 8 -> {
                changePassword(student);
                yield this;
            }
            case 9 -> null;
            default -> this;
        };
    }
}
