import camp.Camp;
import camp.CampController;
import user.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
enum MenuOption
{
    UserLogin,
    StudentMenu,
    StudentEnquirySubMenu,
    StudentCommitteeMenu,
    StudentCommitteeEnquirySubMenu,
    StudentCommitteeSuggestionSubMenu,
    StaffMenu,
    StaffSubMenu,
    StaffEnquirySubMenu,
    StaffSuggestionSubMenu
}

enum FilterOrder
{
    Alphabetical,
    Date,
    Location
}

public class Main {
    public static void main(String[] args) {
        // System.out.println("CWD:" + System.getProperty("user.dir"));
        UserController userController = new UserController();
        CampController campController = new CampController();
        MenuOption option = MenuOption.UserLogin;
        Scanner scanner = new Scanner(System.in);
        Camp selectedCamp = null;
        User user = null;

        userController.addStudents("data/student_list.csv");
        userController.addStaff("data/staff_list.csv");

        // temporary
        campController.createCamp((Staff) userController.findUser("HUKUMAR", "password"), "Picnic Camp");

        boolean quit = false;
        while (!quit) {
            switch (option) {
                case UserLogin -> {
                    System.out.println("Login: ");
                    System.out.println("What's your userID: ");
                    String userId = scanner.nextLine();
                    System.out.println("What's your password: ");
                    String password = scanner.nextLine();

                    try {
                        user = userController.findUser(userId, password);
                        switch (user) {
                            case StudentCommittee studentCommittee -> option = MenuOption.StudentCommitteeMenu;
                            case Student student -> option = MenuOption.StudentMenu;
                            case Staff staff -> option = MenuOption.StaffMenu;
                            case null, default -> {}
                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("Invalid username or password.");
                    }
                }
                case StudentMenu -> {
                    Student student = (Student) user;
                    System.out.println("Camps: ");
                    var camps = campController.getVisibleCamps(student);
                    displayCamps(camps);

                    System.out.println("Options: ");
                    System.out.println("0: Register as StudentAttendee.");
                    System.out.println("1: Register as StudentCommittee.");
                    System.out.println("2: Submit enquiries to a camp.");
                    System.out.println("3: View sent enquiries.");
                    System.out.println("4: Withdraw from camp.");
                    System.out.println("8: Change Password.");
                    System.out.println("9: Quit.");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 3 -> {
                            if ((selectedCamp = selectCamp(camps)) != null) {
                                option = MenuOption.StudentEnquirySubMenu;
                            }
                        }
                        case 8 -> {
                            System.out.println("Choose new password: ");
                            var password = scanner.nextLine();
                            user.changePassword(password);
                            System.out.println("Password Changed.");
                        }
                        case 9 -> quit = true;
                    }
                }
                case StudentEnquirySubMenu -> {
                    System.out.println("Enquiries options for camp " + selectedCamp);
                    System.out.println("0: Edit enquiry: ");
                    System.out.println("1: Delete enquiry: ");
                    System.out.println("9: Back.");

                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 9 -> option = MenuOption.StudentMenu;
                    }
                }
                case StudentCommitteeMenu -> {
                    StudentCommittee studentCommittee = (StudentCommittee) user;
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
                    switch (choice) {
                        case 2 -> {
                            if ((selectedCamp = selectCamp(camps)) != null) {
                                option = MenuOption.StudentCommitteeEnquirySubMenu;
                            }
                        }
                        case 3 -> {
                            if ((selectedCamp = selectCamp(camps)) != null) {
                                option = MenuOption.StudentCommitteeSuggestionSubMenu;
                            }
                        }
                        case 9 -> quit = true;
                    }
                }
                case StudentCommitteeEnquirySubMenu -> {
                    StudentCommittee studentCommittee = (StudentCommittee) user;
                    System.out.println("All enquiries for camp " + selectedCamp);
                    System.out.println("Options: ");
                    System.out.println("0: Reply to enquiry.");
                    System.out.println("9: Back.");

                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 9 -> quit = true;
                    }
                }
                case StudentCommitteeSuggestionSubMenu -> {
                    StudentCommittee studentCommittee = (StudentCommittee) user;
                    System.out.println("Sent suggestions for camp " + selectedCamp);
                    System.out.println("Options: ");
                    System.out.println("0: Edit suggestion");
                    System.out.println("1: Delete suggestion");
                    System.out.println("9: Back.");

                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 9 -> quit = true;
                    }
                }
                case StaffMenu -> {
                    Staff staff = (Staff) user;
                    System.out.println("Camps: ");
                    var camps = campController.getVisibleCamps(staff);
                    displayCamps(camps);

                    System.out.println("Options: ");
                    System.out.println("0: Create camp.");
                    System.out.println("1: Edit camp.");
                    System.out.println("2: Delete camp.");
                    System.out.println("7: Enter submenu.");
                    System.out.println("8: Change Password.");
                    System.out.println("9: Quit.");

                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 0 -> {
                            System.out.println("Choose a camp name: ");
                            var name = scanner.nextLine();
                            campController.createCamp(staff, name);
                        }
                        case 7 -> {
                            option = MenuOption.StaffSubMenu;
                        }
                        case 8 -> {
                            System.out.println("Choose new password: ");
                            var password = scanner.nextLine();
                            user.changePassword(password);
                        }
                        case 9 -> quit = true;
                    }
                }
                case StaffSubMenu -> {
                    Staff staff = (Staff) user;

                    var camps = campController.getInChargeCamps(staff);
                    System.out.println("Camps in charged: ");
                    displayCamps(camps);

                    System.out.println("0: View enquiries: ");
                    System.out.println("1: View suggestions: ");
                    System.out.println("2: Generate attendance report: ");
                    System.out.println("4: Generate performance report: ");
                    System.out.println("9: Back.");

                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 0 -> {
                            if ((selectedCamp = selectCamp(camps)) != null) {
                                option = MenuOption.StaffEnquirySubMenu;
                            }
                        }
                        case 1 -> {
                            if ((selectedCamp = selectCamp(camps)) != null) {
                                option = MenuOption.StaffSuggestionSubMenu;
                            }
                        }
                        case 9 -> option = MenuOption.StaffMenu;
                    }
                }
                case StaffEnquirySubMenu -> {
                    Staff staff = (Staff) user;
                    System.out.println("Enquiries for Camp " + selectedCamp);
                    System.out.println("9: Back.");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 9 -> option = MenuOption.StaffSubMenu;
                    }
                }
                case StaffSuggestionSubMenu -> {
                    Staff staff = (Staff) user;
                    System.out.println("Suggestions for Camp ." + selectedCamp);
                    System.out.println("9: Back.");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 9 -> option = MenuOption.StaffSubMenu;
                    }
                }
                case null, default -> {
                    throw new RuntimeException("Invalid state reached: " + option.name());
                }
            }
        }
        System.out.println("Exiting.");
    }

    private static<T> void displayCamps(List<T> camps) {
        if (camps.isEmpty()) {
            System.out.println("None");
        } else {
            for (var it = camps.listIterator(); it.hasNext(); ) {
                T t = it.next();
                System.out.println(it.previousIndex() + ": " + t.toString());
            }
        }
    }
    private static Camp selectCamp(List<Camp> camps) {
        System.out.println("Select a camp: ");
        Scanner scanner = new Scanner(System.in);
        int campChoice = scanner.nextInt();
        scanner.nextLine();
        try {
            return camps.get(campChoice);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not a valid selection.");
        }
        return null;
    }
}