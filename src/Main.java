import camp.Camp;
import camp.CampController;
import screen.Screen;
import screen.UserLoginScreen;
import user.*;

enum FilterOrder
{
    Alphabetical,
    Date,
    Location
}

public class Main {
    public static void main(String[] args) {
        System.out.println("CWD:" + System.getProperty("user.dir"));
        UserController userController = new UserController();
        CampController campController = new CampController();

        userController.addStudents("data/student_list.csv");
        userController.addStaff("data/staff_list.csv");

        // temporary
        campController.createCamp((Staff) userController.findUser("HUKUMAR", "password"), "Picnic Camp");

        Screen screen = new UserLoginScreen(userController, campController);

        while (screen != null) {
            screen = screen.display();
        }

        System.out.println("Exiting.");
    }
}