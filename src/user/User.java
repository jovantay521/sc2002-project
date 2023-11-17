package user;

import java.io.Serializable;

/**
 * User is the base-class for all users in the CMS.
 */
public class User implements Serializable {
    /**
     * Name of user, indicated in CSV file.
     */
    private final String name;
    /**
     * Unique Identification String, indicated in CSV file.
     */
    private final String userID;
    /**
     * Password for verification, defaulted to "password".
     */
    private String password;
    /**
     * Faculty of user, used for filtering options.
     */
    private final String faculty;

    /**
     * Constructor of user.
     * @param name Name of user.
     * @param userID Identification of user.
     * @param password Password of user.
     * @param faculty Faculty of user.
     */
    public User(String name, String userID, String password, String faculty) {
        this.name = name;
        this.userID = userID;
        this.password = password;
        this.faculty = faculty;
    }

    /**
     * Getter for User Identification.
     * @return userID field.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Getter for Name.
     * @return name field.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for Password.
     * @return password field.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for Faculty
     * @return faculty field.
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * Verify if given credentials are valid. Used for login functionality.
     * @param userId User Identification String
     * @param password Password of User
     * @return True if credentials are valid, false otherwise.
     */
    public boolean verify(String userId, String password) {
        return this.userID.equals(userId) && this.password.equals(password);
    }

    /**
     * Modify password of user
     * @param newPassword New password of user
     */
    public void changePassword(String newPassword) {
        password = newPassword;
    }
}
