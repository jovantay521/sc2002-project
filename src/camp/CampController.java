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


public class CampController implements Serializable
{
    private final List<Camp> camps = new ArrayList<>();

    public static Optional<CampController> loadFrom(String filePath) {
        try (var s = new FileInputStream(filePath)) {
            var o = new ObjectInputStream(s);
            return Optional.of((CampController) o.readObject());
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
        } catch (IOException e) {
            System.out.println("IO Error " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Class Error " + e + "\nFix by implementing Serializable.");
        }
        return Optional.empty();
    }

    public static void saveTo(String filePath, CampController campController) {
        try (FileOutputStream s = new FileOutputStream(filePath)) {
            ObjectOutputStream o = new ObjectOutputStream(s);;
            o.writeObject(campController);
            o.flush();
            o.close();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
        } catch (IOException e) {
            System.out.println("IO Error " + e);
        }
    }
    
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
    public void createCamp(Staff staff, String campName, TimeRegion region, LocalDate regCloseDate, String userGroup, String location, int totalSlots, int campCommitteeSlot, String description)
    {
        camps.add(new Camp(campName, region, regCloseDate, userGroup, location, totalSlots, campCommitteeSlot, description, staff, true));
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
