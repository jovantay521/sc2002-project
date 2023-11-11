package screen;

import camp.Camp;
import camp.CampController;
import user.Student;
import user.StudentCommittee;
import user.UserController;

import static camp.CampController.displayCamps;
import static camp.CampController.selectCamp;

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
        Camp selectedCamp;

        System.out.println("Camps: ");
        var camps = campController.getVisibleCamps(student);
        displayCamps(camps);

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
                if ((selectedCamp = selectCamp(camps)) != null && student.checkTimeConflicts(selectedCamp)) {
                    System.out.println("Registered student to " + selectedCamp + " as attendee.");
                    try {
                        selectedCamp.addStudent(student);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e);
                    }
                }
                yield this;
            }
            case 1 -> {
                if ((selectedCamp = selectCamp(camps)) != null && student.checkTimeConflicts(selectedCamp)) {
                    System.out.println("Registered student to " + selectedCamp + " as committee.");
                    try {
                        selectedCamp.addStudentCommittee(student);
                        var committeeMember = userController.convertTo(student, selectedCamp);
                        yield new StudentCommitteeScreen(userController, campController, committeeMember);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e);
                    }
                }
                yield this;
            }
            case 3 -> {
                if ((selectedCamp = selectCamp(camps)) != null) {
                    yield new StudentEnquiryScreen(userController, campController, student, selectedCamp);
                }
                yield this;
            }
            case 7 -> new UserLoginScreen(userController, campController);
            case 8 -> {
                System.out.println("Choose new password: ");
                var password = scanner.nextLine();
                student.changePassword(password);
                System.out.println("Password Changed.");
                yield this;
            }
            case 9 -> null;
            default -> this;
        };
    }
}
