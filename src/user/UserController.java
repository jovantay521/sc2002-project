package user;

import camp.Camp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserController implements Serializable {
    private final ArrayList<User> users = new ArrayList<>();
    public static void saveTo(String filePath, UserController userController) {
        try (FileOutputStream s = new FileOutputStream(filePath)) {
            ObjectOutputStream o = new ObjectOutputStream(s);
            o.writeObject(userController);
            o.flush();
            o.close();
        } catch (FileNotFoundException e) {
            System.out.println("S not found.");
        } catch (IOException e) {
            System.out.println("IO Error " + e);
        }
    }
    public static Optional<UserController> loadFrom(String filePath) {
        try (var s = new FileInputStream(filePath)) {
            var o = new ObjectInputStream(s);
            return Optional.of((UserController) o.readObject());
        } catch (FileNotFoundException e) {
            System.out.println("S not found.");
        } catch (IOException e) {
            System.out.println("IO Error " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Class Error " + e);
        }
        return Optional.empty();
    }
    public void addStudents(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // First line is header.
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != 3) {
                    throw new RuntimeException("Text malformed, Length: " + values.length);
                }
                users.add(new Student(values[0], values[1].split("@")[0].trim(), "password", values[2].trim()));
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

                users.add(new Staff(values[0], values[1].split("@")[0].trim(),"password", values[2].trim()));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
    
    // Call this to check the user credentials.
    public User verifyLogin(String userId, String password) throws NoSuchElementException {
        return users.stream().filter(user -> user.verify(userId, password)).findFirst().orElseThrow();
    }

    // Calls this AFTER the user logins
    public User getUser(String userId) throws NoSuchElementException {
        return users.stream().filter(user -> user.getUserID().equals(userId)).findFirst().orElseThrow();
    }

    public List<User> getUsers(List<String> userIds) throws NoSuchElementException {
        return userIds.stream().map(this::getUser).toList();
    }

    public StudentCommittee convertTo(Student committeeStudent, Camp joinCamp) {
        if (!users.removeIf(student -> student == committeeStudent)) {
            throw new RuntimeException("Student is not in the controller. Only use this function if the user is obtained from the controller.");
        }
        var newUser = new StudentCommittee(committeeStudent, joinCamp);
        users.add(newUser);
        return newUser;
    }
}
