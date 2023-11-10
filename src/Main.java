import camp.CampController;
import screen.Screen;
import screen.UserLoginScreen;
import user.*;
import utils.TimeRegion;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        CampController campController = new CampController();

        userController.addStudents("data/student_list.csv");
        userController.addStaff("data/staff_list.csv");

        //userController.addStudents("C:/Users/cherm/OneDrive/Documents/NTU Year 2/SC2002 Object Oriented Design & Programming/Assignment/SC2002-Assignment-Cams/data/student_list.csv/");
        //userController.addStaff("C:/Users/cherm/OneDrive/Documents/NTU Year 2/SC2002 Object Oriented Design & Programming/Assignment/SC2002-Assignment-Cams/data/staff_list.csv/");

        // temporary
        LocalDate sDate = LocalDate.of(2023, 12, 12);
        LocalDate eDate = LocalDate.of(2023, 12, 15);
        LocalDate rcDate = LocalDate.of(2023, 11, 9);
        
        campController.createCamp((Staff) userController.findUser("HUKUMAR", "password"), "Picnic Camp", new TimeRegion(sDate, eDate), rcDate, "NTU", "NorthSpine", 30, 10, "Holiday Camp 2023");

        Screen screen = new UserLoginScreen(userController, campController);

        while (screen != null) {
            screen = screen.display();
        }

        System.out.println("Exiting.");
    }
}