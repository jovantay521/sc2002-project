package screen;

import camp.CampController;
import camp.CampControllerException;
import camp.Enquiry;
import screen.enquiry.StudentEnquiryScreen;
import user.Student;
import user.UserController;

public class StudentScreen extends Screen {
    protected Student student;
    public StudentScreen(UserController userController, CampController campController, Student student) {
        super(userController, campController);
        this.student = student;
    }
    @Override
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("Logging in as " + student.getName());

        System.out.println("Camps: ");
        var camps = campController.getVisibleCamps(student);
        displayContents(camps);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: Register as StudentAttendee.");
        System.out.println("1: Register as StudentCommittee.");
        System.out.println("2: Submit enquiries to a camp.");
        System.out.println("3: View sent enquiries.");
        System.out.println("4: Withdraw from camp.");
        System.out.println("7: Logout.");
        System.out.println("8: Change Password.");
        System.out.println("9: Quit.");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                try {
                    System.out.println("Select a camp: ");
                    var selectedCamp = select(camps);
                    selectedCamp.addStudent(student);
                    System.out.println("Registered student to " + selectedCamp + " as attendee.");
                } catch (ScreenException | CampControllerException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 1 -> {
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
                yield this;
            }
            case 2 -> {
               try {
                    System.out.println("Select a camp: ");
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
                    var selectedCamp = select(camps);
                    yield new StudentEnquiryScreen(userController, campController, student, selectedCamp);
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 4 -> {
                try {
                	displayContents(student.attendingCamps);
                    System.out.println("Select a camp: ");
                    var selectedCamp = select(student.attendingCamps);
                    System.out.println("Requested withdraw for " + selectedCamp);
                    selectedCamp.removeStudent(student);
                } catch (ScreenException | CampControllerException e) {
                    System.out.println(e.getMessage());
                }
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
