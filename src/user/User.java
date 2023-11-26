package user;

import camp.CampController;

import java.util.HashMap;
import java.util.Map;


/**
 * User is the base-class for all users in the CMS.
 */
public class User {
    /**
     * Name of user, indicated in CSV file.
     */
    protected final String name;
    /**
     * Unique Identification String, indicated in CSV file.
     */
    protected final String userID;
    /**
     * Password for verification, defaulted to "password".
     */
    protected String password;
    /**
     * Faculty of user, used for filtering options.
     */
    protected final String faculty;

    /**
     * Filters for each individual.
     */
    protected Map<String, CampController.Filter> filters;

    /**
     * First login flag.
     */
    protected boolean firstLogin;

    /**
     * Constructor
     * @param name Name
     * @param userID User ID
     * @param password Password
     * @param faculty Faculty
     * @param firstLogin User's first login
     */
    public User(String name, String userID, String password, String faculty, boolean firstLogin) {
        this(name, userID, password, faculty);
        this.firstLogin = firstLogin;
    }
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
        this.filters = new HashMap<>();
        this.firstLogin = false;
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
     * Setting first login to true
     */
    public void enableFirstLogin() {
        this.firstLogin = true;
    }

    /**
     * Change user first login to false
     * @return previous firstLogin value
     */
    public boolean toggleFirstLogin() {
        boolean ret = firstLogin;
        firstLogin = false;
        return ret;
    }

    /**
     * Getter
     * @return Filters that are enabled.
     */
    public Map<String, CampController.Filter> getFilters() {
        return filters;
    }

    /**
     * Add filter
     * @param filterName Filter name
     * @param filter The filter
     */
    public void addFilter(String filterName, CampController.Filter filter) {
        filters.put(filterName, filter);
    }

    /**
     * Delete filter
     * @param filterName Name of filter to be deleted
     */
    public void deleteFilter(String filterName) {
        filters.remove(filterName);
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

    /**
     * Representation in csv file
     * @return String that represents the user in a csv.
     */
    public String representation() {
        return String.join(",", getClass().getTypeName(), name, userID, password, faculty, String.valueOf(firstLogin));
    }
}
