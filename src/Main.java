import camp.CampController;
import screen.Screen;
import screen.UserLoginScreen;
import user.*;

import java.time.LocalDate;
import java.time.format.*;
import java.util.*; //temp
enum FilterOrder
{
    Alphabetical,
    Date,
    Location
}

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        CampController campController = new CampController();

        userController.addStudents("C:/Users/cherm/OneDrive/Documents/NTU Year 2/SC2002 Object Oriented Design & Programming/Assignment/SC2002-Assignment-Cams/data/student_list.csv/");
        userController.addStaff("C:/Users/cherm/OneDrive/Documents/NTU Year 2/SC2002 Object Oriented Design & Programming/Assignment/SC2002-Assignment-Cams/data/staff_list.csv/");

        // temporary
        String sDate = "2023-12-12";
        LocalDate SelectedSDate = LocalDate.parse(sDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DateTimeFormatter sformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = SelectedSDate.format(sformatter);
        
        String eDate = "2023-12-15";
        LocalDate SelectedEDate = LocalDate.parse(eDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DateTimeFormatter eformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endDate = SelectedEDate.format(eformatter);
        
        String rcDate = "2023-11-10";
        LocalDate SelectedRDate = LocalDate.parse(rcDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DateTimeFormatter rcformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String regCloseDate = SelectedRDate.format(rcformatter);
        
        campController.createCamp((Staff) userController.findUser("HUKUMAR", "password"), "Picnic Camp", startDate, endDate, regCloseDate, "NTU", "NorthSpine", 30, 10, "Holiday Camp 2023");

        Screen screen = new UserLoginScreen(userController, campController);

        while (screen != null) {
            screen = screen.display();
        }

        System.out.println("Exiting.");
    }
}