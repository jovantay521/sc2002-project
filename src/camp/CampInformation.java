package camp;

import user.Staff;


public class CampInformation
{
	private static final int MAX_COMMITTEE_SLOTS = 10;
    private String campName;
    private String startDate;
    private String endDate;
    private String regCloseDate;
    private String userGroup;
    private String location;
    private int totalSlots;
    private int campCommitteeSlots;
    private String description;
    private String inCharge;

    public CampInformation(String campName, String startDate, String endDate,String regCloseDate, String userGroup,
            String location, int totalSlots, int campCommitteeSlot, String description, Staff inCharge)
    {

        this.campName = campName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regCloseDate = regCloseDate;
        this.userGroup = userGroup;
        this.location = location;
        this.totalSlots = totalSlots;
        this.campCommitteeSlots = campCommitteeSlot;
        this.description = description;
        this.inCharge = inCharge.getName();
    }
    
    public void setCampName(String campName)
    {
    	this.campName = campName;
    }
    
    public String getCampName()
    {
    	return campName;
    }
    
    public void setStartDate(String startDate)
    {
    	this.startDate = startDate;
    }
    
    public String getStartDate()
    {
    	return startDate;
    }
    
    public void setEndDate(String endDate)
    {
    	this.endDate = endDate;
    }
    
    public String getEndDate()
    {
    	return endDate;
    } 
    
    public void setRegCloseDate(String regCloseDate)
    {
    	this.regCloseDate = regCloseDate;
    }
    
    public String getRegCloseDate()
    {
    	return regCloseDate;
    }
    
    public void setUserGroup(String userGroup)
    {
    	this.userGroup = userGroup;
    }
    
    public String getUserGroup()
    {
    	return userGroup;
    }
    
    public void setLocation(String location)
    {
    	this.location = location;
    }
    
    public String getLocation()
    {
    	return location;
    }
    
    public void setTotalSlots(int totalSlots)
    {
    	this.totalSlots = totalSlots;
    }
    
    public int getTotalSlots()
    {
    	return totalSlots;
    }
    
    public void setCampCommitteeSlots(int campCommitteeSlots)
    {
    	if(campCommitteeSlots <= MAX_COMMITTEE_SLOTS)
    		this.campCommitteeSlots = campCommitteeSlots;
    	else
    		System.out.println("Camp Committee Member slots is capped at 10");
    }
    
    public int getCampCommitteeSlots()
    {
    	return campCommitteeSlots;
    }
    
    public void setDescription(String description)
    {
    	this.description = description;
    }
    
    public String getDescription()
    {
    	return description;
    }
    
    public void setInCharge(String inCharge)
    {
    	this.inCharge = inCharge;
    }
    
    public String getInCharge()
    {
    	return inCharge;
    }
}
