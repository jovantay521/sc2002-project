package screen;

import camp.Camp;
import camp.CampController;
import user.Staff;
import user.UserController;
import utils.TimeRegion;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
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

        if(staff.toggleFirstLogin()) {
            System.out.println("Please change your password! Thank you.");
        }

        System.out.println("Camps: ");
        var camps = campController.getVisibleCamps(staff);
        displayContents(camps);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: Create camp.");
        System.out.println("1: Edit camp.");
        System.out.println("2: Delete camp.");
        System.out.println("5: Enter camp submenu (To view members, change details etc.).");
        System.out.println("6: Select filters to camps.");
        System.out.println("7: Logout.");
        System.out.println("8: Change Password.");
        System.out.println("9: Quit.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                boolean validSDate = false;
            	boolean validEDate = false;
            	boolean validRCDate = false;

                boolean validTSVal = false;
                boolean validCSVal = false;

                boolean validUG = false;
                boolean validLoca = false;

                int totalSlots = 0;
                int campCommitteeSlots = 0;

                String userGroup = " ";
                String location = " ";
            	
                LocalDate startDate = null;
                LocalDate endDate = null;
                LocalDate registrationDeadline = null;

                System.out.println("Choose a camp name: ");
                var name = scanner.nextLine();

                do
                {
	                try
	                {
		                System.out.println("Enter the start date of Camp (yyyy-MM-dd): ");
		                String sDate = scanner.nextLine();
		                LocalDate SelectedSDate = LocalDate.parse(sDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        if(SelectedSDate.isBefore(LocalDate.now()))
                        {
                            System.out.println("Error: Start date cannot be in the past. Please enter a future date.");
                        }
                        else
                        {
                            DateTimeFormatter sformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		                    startDate = SelectedSDate;
		                    validSDate = true;
                        }
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

                        if(SelectedEDate.isAfter(startDate))
                        {
                            DateTimeFormatter eformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                        endDate = SelectedEDate;
	                        validEDate = true;
                        }
                        else
                        {
                            System.out.println("Error: End date must be after the start date. Please enter a valid end date.");
                        }
	                    
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

                        if(SelectedRDate.isBefore(LocalDate.now()))
                        {
                            System.out.println("Error: Register date cannot be in the past. Please enter a future date.");
                        }
                        else if(SelectedRDate.isAfter(startDate))
                        {
                            System.out.println("Error: Register date cannot be after start date. Please enter a valid register date.");
                        }
                        else
                        {
                            DateTimeFormatter rcformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                        registrationDeadline = SelectedRDate; 
	                        validRCDate = true;
                        }
                	}
                	catch(DateTimeException e)
                	{
                		System.out.println("Error: Invalid date format! Please enter a date in the format yyyy-MM-dd.");
                	}
                }while(!validRCDate);         
                
                do
                {                    
                        System.out.println("Choose which user group this camp is open to: ");
                        userGroup = scanner.nextLine();
                        
                        if(!userGroup.isEmpty())
                        {
                        	 validUG = true;
                        }
                        else
                        {
                        	System.out.println("Error: Please do not leave user group empty.");
                        }
                }while(!validUG);
              
                do
                {
                	System.out.println("Choose the camp location: ");
                    location = scanner.nextLine();
                        
                    if(!location.isEmpty())
                    {
                    	validLoca = true;
                    }
                    else
                    {
                    	System.out.println("Error: Please do not leave location empty.");
                    }  
                    System.out.println(location);
                    System.out.println(validLoca);
                }while(!validLoca);
                
                
                System.out.println("Enter a short description for the camp: ");
                var description = scanner.nextLine();
                
                do
                {
                    try
                    {
                        System.out.println("Choose the total camp slots: ");
                        totalSlots = scanner.nextInt();

                        if(totalSlots > 0)
                        {
                            validTSVal = true;
                        }
                        else
                        {
                            System.out.println("Please enter a positive integer for total camp slots");
                        }
                    }
                    catch(InputMismatchException e)
                    {
                        System.out.println("Error: Invalid total camp slots! Please enter a valid positive integer for total camp slots.");
                        scanner.nextLine();
                    }
                    
                }while(!validTSVal);

                do
                {
                    try
                    {
                        System.out.println("Choose the camp committee slots (max 10): ");
                        campCommitteeSlots = scanner.nextInt();
                        if(campCommitteeSlots > 0 && campCommitteeSlots < 11)
                        {
                            validCSVal = true;
                        }
                        else
                        {
                            System.out.println("Please enter a positive integer for camp committee slots within the range of 1 to 10");
                        }
                    }
                    catch(InputMismatchException e)
                    {
                        System.out.println("Error: Invalid camp committee slots! Please enter a valid positive integer for camp committee slots.");
                        scanner.next();
                    }
                }while(!validCSVal);
                 
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
            case 5 -> new StaffInChargeScreen(userController, campController, staff);
            case 6 -> {
                selectFilter(staff);
                yield this;
            }
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
