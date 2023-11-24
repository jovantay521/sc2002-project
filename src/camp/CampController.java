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

    // Returns list of camps that can be viewed by user
    public List<Camp> getVisibleCamps(User user)
    {
        return camps.stream().filter(camp -> camp.isVisible(user)).collect(Collectors.toList());
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
    public void deleteCamp(Camp camp, UserController userController)
    {
        var users = userController.getUsers(camp.getStudentNames());
        for (var user: users) {
            if (user instanceof Student student) {
                student.removeCamp(camp);
            }
        }
        camps.remove(camp);
    }
}
