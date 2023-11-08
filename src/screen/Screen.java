package screen;

import camp.CampController;
import user.UserController;

import java.util.Scanner;

public abstract class Screen {
    protected UserController userController;
    protected CampController campController;
    Screen(UserController userController, CampController campController) {
        this.userController = userController;
        this.campController = campController;
    }
    public static Scanner scanner = new Scanner(System.in);
    public abstract Screen display();
}
