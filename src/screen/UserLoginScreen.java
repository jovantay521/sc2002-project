package screen;

import camp.CampController;
import user.*;

import java.util.NoSuchElementException;
/**
 * initial login screen
 */
public class UserLoginScreen extends Screen {
    /**
     * constructor for user
     * @param userController user controller
     * @param campController camp controller
     */
	public UserLoginScreen(UserController userController, CampController campController) {
        super(userController, campController);
    }
	/**
	 * display screen for user
	 */
    @Override
    public Screen display() {
        System.out.println("Login: ");
        System.out.println("What's your userID: ");
        String userId = scanner.nextLine();
        System.out.println("What's your password: ");
        String password = scanner.nextLine();

        try {
            User user = userController.verifyLogin(userId, password);
            if (user instanceof StudentCommittee studentCommittee) {
                return new StudentCommitteeScreen(userController, campController, studentCommittee);
            } else if (user instanceof Student student) {
                return new StudentScreen(userController, campController, student);
            } else if (user instanceof Staff staff) {
                return new StaffScreen(userController, campController, staff);
            } else {
                return this;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Invalid username or password.");
        }
        return this;
    }
}
