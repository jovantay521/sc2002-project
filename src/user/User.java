package user;

import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private final String userID;
    private String password;
    private final String faculty;
    public User(String name, String userID, String password, String faculty) {
        this.name = name;
        this.userID = userID;
        this.password = password;
        this.faculty = faculty;
    }

    public String getUserID() {
        return userID;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getFaculty() {
        return faculty;
    }

    public boolean verify(String userId, String password) {
        return this.userID.equals(userId) && this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        password = newPassword;
    }
}
