import camp.CampController;
import screen.Screen;
import screen.UserLoginScreen;
import user.*;
import utils.TimeRegion;

import java.io.*;
import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        UserController userController = UserController.loadFrom("data/users.csv").orElseGet(() -> {
            var tempController = new UserController();
            tempController.addStudents("../data/student_list.csv");
            tempController.addStaff("../data/staff_list.csv");
            return tempController;
        });

        CampController campController = new CampController();

        LocalDate sDate = LocalDate.of(2023, 12, 12);
        LocalDate eDate = LocalDate.of(2023, 12, 15);
        LocalDate rcDate = LocalDate.of(2023, 11, 30);

        campController.createCamp((Staff) userController.verifyLogin("HUKUMAR", "password"), "Picnic Camp", new TimeRegion(sDate, eDate), rcDate, "NTU", "NorthSpine", 3, 2, "Holiday Camp 2023");

        //userController.addStudents("C:/Users/cherm/OneDrive/Documents/NTU Year 2/SC2002 Object Oriented Design & Programming/Assignment/SC2002-Assignment-Cams/data/student_list.csv/");
        //userController.addStaff("C:/Users/cherm/OneDrive/Documents/NTU Year 2/SC2002 Object Oriented Design & Programming/Assignment/SC2002-Assignment-Cams/data/staff_list.csv/");

        Screen screen = new UserLoginScreen(userController, campController);

        while (screen != null) {
            screen = screen.display();
        }

        System.out.println("Saving...");

        UserController.saveTo("data/users.csv", userController);

        System.out.println("Exiting...");
    }
}