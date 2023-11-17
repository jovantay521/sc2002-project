package screen;

import camp.CampController;
import user.Staff;
import user.UserController;
import utils.TimeRegion;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class StaffScreen extends Screen {
    protected Staff staff;
    public StaffScreen(UserController userController, CampController campController, Staff staff) {
        super(userController, campController);
        this.staff = staff;
    }

    @Override
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("Logging in as " + staff.getName());

        System.out.println("Camps: ");
        var camps = campController.getVisibleCamps(staff);
        displayContents(camps);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: Create camp.");
        System.out.println("1: Edit camp.");
        System.out.println("2: Delete camp.");
        System.out.println("6: Enter camp submenu (To view members, change details etc.).");
        System.out.println("7: Logout.");
        System.out.println("8: Change Password.");
        System.out.println("9: Quit.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                LocalDate startDate = null;
                LocalDate endDate = null;
                LocalDate registrationDeadline = null;

                System.out.println("Choose a camp name: ");
                var name = scanner.nextLine();

                while(startDate == null) {
	                try {
		                System.out.println("Enter the start date of Camp (yyyy-MM-dd): ");
		                String sDate = scanner.nextLine();
		                startDate = LocalDate.parse(sDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	                }
	                catch(DateTimeException e)  {
	                	System.out.println("Error: Invalid date format. Please enter a date in the format yyyy-MM-dd.");	
	                }
                }
                
                while(endDate == null)
                {
                	try {
	                	System.out.println("Enter the end date of Camp (yyyy-MM-dd): ");
	                    String eDate = scanner.nextLine();
	                    endDate = LocalDate.parse(eDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                	}  catch(DateTimeException e)  {
                		System.out.println("Error: Invalid date format. Please enter a date in the format yyyy-MM-dd.");
                	}
                }

                while (registrationDeadline == null) {
                	try {
	                	System.out.println("Enter the registration closing date of the Camp (yyyy-MM-dd): ");
	                    String rcDate = scanner.nextLine();
	                    registrationDeadline = LocalDate.parse(rcDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                	} catch(DateTimeException e)  {
                		System.out.println("Error: Invalid date format. Please enter a date in the format yyyy-MM-dd.");
                	}
                }
                
                System.out.println("Choose which user group this camp is open to: ");
                var userGroup = scanner.nextLine();
                
                System.out.println("Choose the camp location: ");
                var location = scanner.nextLine();
                
                System.out.println("Enter a short description for the camp: ");
                var description = scanner.nextLine();
                
                System.out.println("Choose the total camp slots: ");
                var totalSlots = scanner.nextInt();
                
                System.out.println("Choose the camp committee slots (max 10): ");
                var campCommitteeSlots = scanner.nextInt();
                 
                campController.createCamp(staff, name, new TimeRegion(startDate, endDate), registrationDeadline, userGroup, location, totalSlots, campCommitteeSlots, description);
                yield this;
            }
            case 1 -> {
                try {
                    System.out.println("Select a camp: ");
                    var selectedCamp = select(camps);

                    var details = selectedCamp.getDetailsAsValue();
                    System.out.println();
                    System.out.println("Details of " + selectedCamp + " : ");
                    final AtomicInteger i = new AtomicInteger();
                    details.forEach((k, v) -> System.out.println("(" + i.getAndIncrement() + ") " + k + " : " + v));

                    System.out.println("Which camp details do you want to edit?");
                    var detail = select(details.keySet().stream().toList());
                    // TODO: Select detail and edit them
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }

                yield this;
            }
            case 2 -> {
                try {
                    System.out.println("Select a camp: ");
                    var selectedCamp = select(camps);
                    campController.deleteCamp(selectedCamp, userController);
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 6 -> new StaffInChargeScreen(userController, campController, staff);
            case 7 -> new UserLoginScreen(userController, campController);
            case 8 -> {
                changePassword(staff);
                yield this;
            }
            case 9 -> null;
            default -> this;
        };
    }
}
