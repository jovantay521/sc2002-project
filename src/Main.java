import camp.CampController;
import screen.Screen;
import screen.UserLoginScreen;
import user.*;
import utils.TimeRegion;

import java.io.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        UserController userController = UserController.loadFrom("data/users.bin").orElseGet(() -> {
            var tempController = new UserController();
            tempController.addStudents("data/student_list.csv");
            tempController.addStaff("data/staff_list.csv");
            return tempController;
        });

        CampController campController = CampController.loadFrom("data/camps.bin").orElseGet(() -> {
            var tempController = new CampController();

            LocalDate sDate = LocalDate.of(2023, 12, 12);
            LocalDate eDate = LocalDate.of(2023, 12, 15);
            LocalDate rcDate = LocalDate.of(2023, 11, 30);

            tempController.createCamp((Staff) userController.verifyLogin("HUKUMAR", "password"), "Picnic Camp", new TimeRegion(sDate, eDate), rcDate, "NTU", "NorthSpine", 30, 10, "Holiday Camp 2023");
            return tempController;
        });

        //userController.addStudents("C:/Users/cherm/OneDrive/Documents/NTU Year 2/SC2002 Object Oriented Design & Programming/Assignment/SC2002-Assignment-Cams/data/student_list.csv/");
        //userController.addStaff("C:/Users/cherm/OneDrive/Documents/NTU Year 2/SC2002 Object Oriented Design & Programming/Assignment/SC2002-Assignment-Cams/data/staff_list.csv/");

        Screen screen = new UserLoginScreen(userController, campController);

        while (screen != null) {
            screen = screen.display();
        }

        System.out.println("Saving...");

        UserController.saveTo("data/users.bin", userController);
        CampController.saveTo("data/camps.bin", campController);

        System.out.println("Exiting...");
    }
}