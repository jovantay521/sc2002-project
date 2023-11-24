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

//    public static void ksaveTo(String filePath, CampController campController) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            campController.camps.forEach(camp -> {
//                try {
//                    writer.write(camp.getRepresentation());
//                    writer.newLine();
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//            });
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//    public static Optional<CampController> kloadfrom(String filePath, UserController userController) {
//        var campController = new CampController();
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            var lines = reader.lines();
//            lines.forEach(line -> {
//                var values = line.split(",(?![^()]*\\))"); System.out.println(values[1]);
//                if (values.length < 9)
//                    throw new RuntimeException("Malformed data");
//                var timeSplit = values[2].split("\\s+");
//
//                if (timeSplit.length != 2)
//                    throw new RuntimeException("Malformed data");
//                var newCamp = campController.createCamp(
//                        (Staff) userController.getUser(values[0]),
//                        values[1],
//                        new TimeRegion(LocalDate.parse(timeSplit[0]), LocalDate.parse(timeSplit[1])),
//                        LocalDate.parse(values[3]),
//                        values[4],
//                        values[5],
//                        Integer.parseInt(values[6]),
//                        Integer.parseInt(values[7]),
//                        values[8]
//                );
//                // TODO: Make the load from also load the enquiries or suggestions
////                newCamp.addStudent();
////                newCamp.addStudentCommittee();
////                newCamp.addSuggestion();
////                newCamp.addEnquiries();
//
//            });
//            return Optional.of(campController);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return Optional.empty();
//    }

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
