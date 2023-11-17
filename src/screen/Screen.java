package screen;

import camp.CampController;
import user.User;
import user.UserController;

import java.util.InputMismatchException;
import java.util.List;
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
    public static<T> T select(List<T> indexableContainable) throws ScreenException {
        try {
            var choice = scanner.nextInt();
            scanner.nextLine();
            return indexableContainable.get(choice);
        } catch (InputMismatchException e) {
            throw new ScreenException("Use a number for selection.");
        } catch (IndexOutOfBoundsException e) {
            throw new ScreenException("Not a valid selection.");
        }
    }
    public static<T> void displayContents(List<T> camps) {
        if (camps.isEmpty()) {
            System.out.println("None");
        } else {
            for (var it = camps.listIterator(); it.hasNext(); ) {
                T t = it.next();
                System.out.println(it.previousIndex() + ": " + t.toString());
            }
        }
    }
    protected static void changePassword(User user) {
        System.out.println("Choose new password: ");
        var password = scanner.nextLine();
        user.changePassword(password);
    }

}
