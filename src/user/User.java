package user;

import java.util.Objects;

public class User {
    private String name;
    private String userID;
    private String email;
    private String password;
    private String faculty;
    public User(String name, String userID, String email, String password, String faculty) {
        this.name = name;
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.faculty = faculty;
    }

    public String getUserID() {
        return userID;
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
