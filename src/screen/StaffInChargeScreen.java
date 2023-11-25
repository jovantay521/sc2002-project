package screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; //Test


import camp.Camp;
import camp.CampController;
import screen.enquiry.StaffInChargeEnquiryScreen;
import screen.suggestion.StaffInChargeSuggestionScreen;
import user.Staff;
import user.StudentCommittee;
import user.User;
import user.UserController;

public class StaffInChargeScreen extends StaffScreen {
    public StaffInChargeScreen(UserController userController, CampController campController, Staff staff) {
        super(userController, campController, staff);
    }

    @Override
    public Screen display() {
        System.out.println("--------------------------");


        var camps = campController.getInChargeCamps(staff);
        System.out.println("Camps in charged: ");
        displayContents(camps);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: View all attendees and committees: ");
        System.out.println("1: View enquiries: ");
        System.out.println("2: View suggestions: ");
        System.out.println("3: Generate attendance report: ");
        System.out.println("4: Generate performance report: ");
        //System.out.println("5: Edit camp: ");
        System.out.println("9: Back.");

        int choice = -1;
        try {
            choice = getInt();
        } catch (ScreenException e) {
            System.out.println(e.getMessage());
        }

        return switch (choice) {
            case 0 -> {
                try {
                    System.out.println("Select a camp: ");
                    var selectedCamp = select(camps);
                    System.out.println();
                    displayContents(selectedCamp.getStudentNames());
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 1 -> {
                try {
                    System.out.println("Select a camp: ");
                    var selectedCamp = select(camps);
                    yield new StaffInChargeEnquiryScreen(userController, campController, staff, selectedCamp);
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 2 -> {
                try {
                    System.out.println("Select a camp: ");
                    var selectedCamp = select(camps);
                    yield new StaffInChargeSuggestionScreen(userController, campController, staff, selectedCamp);
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 3 -> {
            	try {
            		Scanner input = new Scanner(System.in);
            		int reportChoice, formatChoice;
            		System.out.println("Select a camp: ");
            		var selectedCamp = select(camps);
            		do
            		{
            			System.out.println("Select what you want to include in the report: ");
            			System.out.println("1: Both Camp Attendees and Camp Committees");
                		System.out.println("2: Camp Attendees");
                		System.out.println("3: Camp Committees");	
                        reportChoice = input.nextInt();
            		}while(reportChoice < 1 || reportChoice > 3);
                    do
                    {
                    	System.out.println("Select report format: ");
                    	System.out.println("1: Text File");
                		System.out.println("2: CSV File");
                		formatChoice = input.nextInt();
                    }while(formatChoice < 1 || formatChoice > 2);
                    switch(formatChoice)
                    {
                    	case 1:
                    		selectedCamp.generateAttendance("C:\\Users\\cherm\\OneDrive\\Documents\\NTU Year 2\\SC2002 Object Oriented Design & Programming\\SC2002-Assignment-Cams-1\\data\\Staff_Report.txt", reportChoice);
                    		break;
                    	case 2:
                    		selectedCamp.generateAttendance("C:\\Users\\cherm\\OneDrive\\Documents\\NTU Year 2\\SC2002 Object Oriented Design & Programming\\SC2002-Assignment-Cams-1\\data\\Report.csv", reportChoice);
                    		break;
                    }
                    
                    System.out.println("Report saved to directory.");
            	} catch (ScreenException e) {
            		System.out.println("Error Generating Report!");
            	}
            	yield this;
            }
            case 4 -> {
                try {
                    System.out.println("Select a camp: ");
                    var selectedCamp = select(camps);
                    var committees = userController.getUsers(selectedCamp.getCommittees()).stream().map(u -> (StudentCommittee) u).toList();
                    System.out.println(committees.size());
                    System.out.println("Where to save it.");
                    var path = scanner.nextLine();
                    userController.generatePerformance(path, committees);
                } catch (ScreenException e) {

                }

                yield this;
            }
//            case 5 -> {
//            	System.out.println("Select a camp: ");
//        		try {
//					var selectedCamp = select(camps);
//				} catch (ScreenException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        		
//        		yield this;
//            }
            case 9 -> new StaffScreen(userController, campController, staff);
            default -> this;
        };
    }
}
