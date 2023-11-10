package screen;

import camp.CampController;
import user.Staff;
import user.UserController;

import static camp.CampController.displayCamps;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class StaffScreen extends Screen {
    protected Staff staff;
    StaffScreen(UserController userController, CampController campController, Staff staff) {
        super(userController, campController);
        this.staff = staff;
    }

    @Override
    public Screen display() {
    	
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
        return switch (choice) {
            case 0 -> {
            	boolean validSDate = false;
            	boolean validEDate = false;
            	boolean validRCDate = false;
            	String startDate = " ";
            	String endDate = " ";
            	String regCloseDate = " ";
            	
            	
                System.out.println("Choose a camp name: ");
                var name = scanner.nextLine();
                do
                {
	                try
	                {
		                System.out.println("Enter the start date of Camp (yyyy-MM-dd): ");
		                String sDate = scanner.nextLine();
		                LocalDate SelectedSDate = LocalDate.parse(sDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		                DateTimeFormatter sformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		                startDate = SelectedSDate.format(sformatter);
		                validSDate = true;
	                }
	                catch(DateTimeException e)
	                {
	                	System.out.println("Error: Invalid date format. Please enter a date in the format yyyy-MM-dd.");	
	                }
                }while(!validSDate);
                
                do
                {
                	try
                	{
	                	System.out.println("Enter the end date of Camp (yyyy-MM-dd): ");
	                    String eDate = scanner.nextLine();
	                    LocalDate SelectedEDate = LocalDate.parse(eDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	                    DateTimeFormatter eformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                    endDate = SelectedEDate.format(eformatter);
	                    validEDate = true;
                	}
                	catch(DateTimeException e)
                	{
                		System.out.println("Error: Invalid date format. Please enter a date in the format yyyy-MM-dd.");
                	}
                }while(!validEDate);
                
                
                do
                {
                	try
                	{
	                	System.out.println("Enter the registration closing date of the Camp (yyyy-MM-dd): ");
	                    String rcDate = scanner.nextLine();
	                    LocalDate SelectedRDate = LocalDate.parse(rcDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	                    DateTimeFormatter rcformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                    regCloseDate = SelectedRDate.format(rcformatter); 
	                    validRCDate = true;
                	}
                	catch(DateTimeException e)
                	{
                		System.out.println("Error: Invalid date format. Please enter a date in the format yyyy-MM-dd.");
                	}
                }while(!validRCDate);         
                
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
                 
                campController.createCamp(staff, name, startDate, endDate, regCloseDate, userGroup, location, totalSlots, campCommitteeSlots, description);
                yield this;
            }
            case 7 -> new StaffInChargeScreen(userController, campController, staff);
            case 8 -> {
                System.out.println("Choose new password: ");
                var password = scanner.nextLine();
                staff.changePassword(password);
                yield this;
            }
            case 9 -> null;
            default -> this;
        };
    }
}
