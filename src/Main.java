import camp.CampController;
import screen.Screen;
import screen.UserLoginScreen;
import user.*;


public class Main {
    public static void main(String[] args) {
        UserController userController = UserController.loadFrom("data/users.csv").orElseGet(() -> {
            var tempController = new UserController();
            tempController.addStudents("data/student_list.csv");
            tempController.addStaff("data/staff_list.csv");
            return tempController;
        });

        CampController campController = new CampController();

        Screen screen = new UserLoginScreen(userController, campController);

        while (screen != null) {
            screen = screen.display();
        }

        System.out.println("Saving...");

        UserController.saveTo("data/users.csv", userController);

        System.out.println("Exiting...");
    }
}