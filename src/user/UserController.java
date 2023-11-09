package user;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class UserController {
    private final ArrayList<User> users = new ArrayList<>();
    public void addStudents(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // First line is header.
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != 3) {
                    throw new RuntimeException("Text malformed, Length: " + values.length);
                }
                users.add(new Student(values[0], values[1].split("@")[0].trim(), values[1].trim(), "password", values[2].trim()));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void addStaff(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // First line is header.
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != 3) {
                    throw new RuntimeException("Text malformed, Length: " + values.length);
                }

                users.add(new Staff(values[0], values[1].split("@")[0].trim(), values[1].trim(), "password", values[2].trim()));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
    
    //
    public User findUser(String userId, String password) throws NoSuchElementException {
        return users.stream().filter(user -> user.verify(userId, password)).findFirst().orElseThrow();
    }
}
