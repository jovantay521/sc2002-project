package camp;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.*;

import user.Staff;
import utils.TimeRegion;

/**
 * Camp information class with camp's attributes
 */
public final class CampInformation
{
	/**
	 * 	The max camp committee slots for camp.
	 */
	private static final int MAX_COMMITTEE_SLOTS = 10;
	
	/**
	 * The name of camp.
	 */
    private String campName;
    
    /**
     * The duration (start and end date) of the camp.
     */
    private TimeRegion region;
    
    /**
     * The registration deadline of the camp.
     */
    private LocalDate regCloseDate;
    
    /**
     * Which group(faculty) of students is this camp open to.
     * NTU or own school (SCSE, SBS, NBS, ADM, EEE etc).
     */
    private String userGroup;
    
    /**
     * Location where the camp will be held at.
     */
    private String location;
    
    /**
     * Number of total slots for the camp (Camp attendee + Camp committee slots).
     */
    private int totalSlots;
    
    /**
     * Number of camp committee slots for the camp.
     */
    private int campCommitteeSlots;
    
    /**
     * Description of the camp.
     */
    private String description;
    
    /**
     * Name of the staff that is in charge of the camp.
     */
    private String inCharge;
    
    /**
     * Creates a new Camp information with the given name, duration, registration deadline, user group,
     * location, total slots, camp committee slot, description and staff in-charge.s
     * @param campName This Camp's name.
     * @param region This Camp's start date & end date. 
     * @param regCloseDate This Camp's registration deadline.
     * @param userGroup This Camp's is open to which group(faculty) of students.
     * @param location This Camp's location where it will be held at.
     * @param totalSlots This Camp's number of total slots.
     * @param campCommitteeSlot This Camp's number of committee slots.
     * @param description This Camp's description.
     * @param inCharge This Camp's staff in-charge's name.
     */
    public CampInformation(String campName, TimeRegion region,LocalDate regCloseDate, String userGroup,
            String location, int totalSlots, int campCommitteeSlot, String description, Staff inCharge)
    {

        this.campName = campName;
        this.region = region;
        this.regCloseDate = regCloseDate;
        this.userGroup = userGroup;
        this.location = location;
        this.totalSlots = totalSlots;
        this.campCommitteeSlots = campCommitteeSlot;
        this.description = description;
        this.inCharge = inCharge.getName();
    }
    
    /**
     * Changes the name of this Camp.
     * @param campName This Camp's new name.
     */
    public void setCampName(String campName)
    {
    	this.campName = campName;
    }
    
    /**
     * Gets the name of this Camp.
     * @return This Camp's name.
     */
    public String getCampName()
    {
    	return campName;
    }

    /**
     * Changes the duration (start date and end date) of this Camp.
     * @param region This Camp's new duration (start date and end date).
     */
    public void setTimeRegion(TimeRegion region)
    {
    	this.region = region;
    }
    
    /**
     * Gets the duration (start date and end date) of this Camp. 
     * @return This Camp's duration (start date and end date)
     */
    public TimeRegion getTimeRegion() {
        return region;
    }
    
    /**
     * Changes the registration deadline of this Camp.
     * @param regCloseDate This Camp's new registration deadline.
     */
    public void setRegCloseDate(LocalDate regCloseDate)
    {
    	this.regCloseDate = regCloseDate;
    }
    
    /**
     * Gets the registration deadline of this Camp.
     * @return This Camp's registration deadline
     */
    public LocalDate getRegCloseDate()
    {
    	return regCloseDate;
    }
    
    /**
     * Changes the user group (faculty) of this Camp.
     * @param userGroup This Camp's new user group (faculty).
     */
    public void setUserGroup(String userGroup)
    {
    	this.userGroup = userGroup;
    }
    
    /**
     * Gets the user group (faculty) of this Camp.
     * @return This Camp's user group (faculty).
     */
    public String getUserGroup()
    {
    	return userGroup;
    }
    
    /**
     * Changes the location of this Camp.
     * @param location This Camp's new location.
     */
    public void setLocation(String location)
    {
    	this.location = location;
    }
    
    /**
     * Gets the location of this Camp.
     * @return This Camp's location.
     */
    public String getLocation()
    {
    	return location;
    }
    
    /**
     * Changes the number of total slots of this Camp.
     * @param totalSlots This Camp's new total slots.
     */
    public void setTotalSlots(int totalSlots)
    {
    	this.totalSlots = totalSlots;
    }
    
    /**
     * Gets the total slots of this Camp.
     * @return This Camp's total slots.
     */
    public int getTotalSlots()
    {
    	return totalSlots;
    }
    
    /**
     * Changes the number of camp committee slots (capped at 10 slots) of this Camp.
     * @param campCommitteeSlots This Camp's new camp committee slots.
     */
    public void setCampCommitteeSlots(int campCommitteeSlots)
    {
    	if(campCommitteeSlots <= MAX_COMMITTEE_SLOTS)
    		this.campCommitteeSlots = campCommitteeSlots;
    	else
    		System.out.println("Camp Committee Member slots is capped at 10");
    }
    
    /**
     * Get the camp committee slots of this Camp.
     * @return This Camp's camp committee slots.
     */
    public int getCampCommitteeSlots()
    {
    	return campCommitteeSlots;
    }
    
    /**
     * Changes the description of this Camp.
     * @param description This Camp's new description.
     */
    public void setDescription(String description)
    {
    	this.description = description;
    }
    
    /**
     * Get the description of this Camp.
     * @return This Camp's description.
     */
    public String getDescription()
    {
    	return description;
    }
    
    /**
     * Changes the name of the staff in-charge of this Camp.
     * @param inCharge This Camp's new name of the staff in-charge.
     */
    public void setInCharge(String inCharge)
    {
    	this.inCharge = inCharge;
    }
    
    /**
     * Get the name of the staff in-charge of this Camp.
     * @return This Camp's name of the staff in-charge.
     */
    public String getInCharge()
    {
    	return inCharge;
    }

    /**
     * It returns a map of all variables names and its values
     * @return .
     */
    public Map<String, String> getPairs() {
        Map<String, String> pairs = new HashMap<>();
        for (var member: this.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(member.getModifiers()))
                continue;
            try {
                pairs.put(member.getName(), member.get(this).toString());
            } catch (IllegalAccessException ignored) {
            }
        }
        return pairs;
    }
}
