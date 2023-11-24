import camp.CampController;
import camp.Enquiry;
import screen.Screen;
import screen.UserLoginScreen;
import user.*;
import utils.TimeRegion;

import java.nio.file.Paths;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
        UserController userController = UserController.loadFrom("data/users.csv").orElseGet(() -> {
            var tempController = new UserController();
            tempController.addStudents("data/student_list.csv");
            tempController.addStaff("data/staff_list.csv");
            return tempController;
        });

        CampController campController = new CampController();

        LocalDate sDate = LocalDate.of(2023, 12, 12);
        LocalDate eDate = LocalDate.of(2023, 12, 15);
        LocalDate rcDate = LocalDate.of(2023, 11, 30);

        var camp = campController.createCamp((Staff) userController.verifyLogin("HUKUMAR", "password"), "Picnic Camp", new TimeRegion(sDate, eDate), rcDate, "NTU", "NorthSpine", 3, 2, "Holiday Camp 2023");

        camp.addEnquiries((Student) userController.verifyLogin("DON84", "password"), new Enquiry("J", "DON84"));

        Screen screen = new UserLoginScreen(userController, campController);

        while (screen != null) {
            screen = screen.display();
        }

        System.out.println("Saving...");

        UserController.saveTo("data/users.csv", userController);

        System.out.println("Exiting...");
    }
}