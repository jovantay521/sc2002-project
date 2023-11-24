package user;

import camp.Camp;
/**
 * Staff class
 */
public class Staff extends User {
    /**
     * Staff Constructor
     * @param name Staff Name
     * @param userID Staff UID
     * @param password Staff Password
     * @param faculty Staff Faculty
     */
    public Staff(String name, String userID, String password, String faculty) {
        super(name, userID, password, faculty);
    }
    
    //Still working on it
    public void generateNameList(Camp camp)
    {
    	System.out.println("Attendees List:");
    	camp.getAttendees();
    	System.out.println("Committees List:");
    	camp.getCommittees();
    	/*if(camp.isInCharge(staff))
    	{
    		switch(choice)
        	{
        		case 1:
        			camp.getAttendees();
        			break;
        		case 2:
        			camp.getCommittees();
        			break;
        		default:
        			System.out.println("Please choose a valid choice");		
        	}
    	}
    	else
    	{
    		System.out.println("You are not an in-charge of this camp, please choose a camp that is created by you to generate a report");
    	}*/
    }
}
