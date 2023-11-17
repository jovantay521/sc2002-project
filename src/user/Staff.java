package user;

/**
 * Staff class
 */
public class Staff extends User {
    /**
     * Staff Constructor
     * @param name Staff Name
     * @param userID Staff UID
     * @param password Staff Password
     * @param faculty Staff Faculty
     */
    public Staff(String name, String userID, String password, String faculty) {
        super(name, userID, password, faculty);
    }
}
