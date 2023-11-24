package camp;

import user.Staff;
import user.Student;
import user.User;
import user.UserController;
import utils.TimeRegion;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class CampController
{
    private final List<Camp> camps = new ArrayList<>();

    public static Filter DateFilter(TimeRegion timeRegion)
    {

        return (Camp camp) -> timeRegion.fullyCover(camp.getRegion());
    }
    public static Filter LocationFilter(String location)
    {
        return (Camp camp) -> camp.getLocation().equals(location);
    }

    public interface Filter {
        public boolean accept(Camp camp);
    }

    // Returns list of camps that can be viewed by user
    public List<Camp> getVisibleCamps(User user)
    {
        return getVisibleCamps(user, user.getFilters().values().stream().toList());
    }

    // Returns list of camps that can be viewed by user with a filter
    public List<Camp> getVisibleCamps(User user, List<Filter> filters)
    {
        var visibleCamps = camps.stream().filter(camp -> camp.isVisible(user));
        for (Filter filter: filters)
            visibleCamps = visibleCamps.filter(filter::accept);

        visibleCamps = visibleCamps.sorted((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        return visibleCamps.collect(Collectors.toList());
    }
    
    // Returns list of camps that belongs to staff
    public List<Camp> getInChargeCamps(Staff staff)
    {
        return camps.stream().filter(camp -> camp.isInCharge(staff)).collect(Collectors.toList());
    }
    // creates a camp
    public Camp createCamp(Staff staff, String campName, TimeRegion region, LocalDate regCloseDate, String userGroup, String location, int totalSlots, int campCommitteeSlot, String description)
    {
        Camp newCamp = new Camp(campName, region, regCloseDate, userGroup, location, totalSlots, campCommitteeSlot, description, staff, true);
        camps.add(newCamp);
        return newCamp;
    }
    public void deleteCamp(Staff staff, Camp camp, UserController userController) throws CampControllerException
    {
        if (!camp.isOwner(staff)) {
            throw new CampControllerException("Not the owner you cannot remove it.");
        }
        var users = userController.getUsers(camp.getStudentNames());
        for (var user: users) {
            if (user instanceof Student student) {
                student.removeCamp(camp);
            }
        }
        camps.remove(camp);
    }
}
