package user;

import camp.Camp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The main storage class of users in the CMS. Handles user registration, user saving and loading.
 */
public class UserController implements Serializable {
    /**
     * Internal storage of users
     */
    private final ArrayList<User> users = new ArrayList<>();

    /**
     * Serialize Method
     * @param filePath Specify which location to save to
     * @param userController Specify which store to retrieve from
     */
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

    /**
     * Deserialize Method
     * @param filePath Specify which location to load from
     * @return Returns {@code Optional.of(UserController)} if load was successful, otherwise returns {@code Optional.empty()}
     */
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

    /**
     * Loads students from student.csv
     * @param filePath File path of student.csv
     */
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

    /**
     * Loads staffs from staff.csv
     * @param filePath File path of staff.csv
     */
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

    /**
     * Used to verify if credentials is valid
     * @param userId User Identification String
     * @param password Password of User
     * @return User if a user matches the credentials
     * @throws NoSuchElementException if all users does not match the credentials
     */
    public User verifyLogin(String userId, String password) throws NoSuchElementException {
        return users.stream().filter(user -> user.verify(userId, password)).findFirst().orElseThrow();
    }

    /**
     * Access a user instance with its User Identification String.
     * @param userId User Identification String
     * @return User if a user matches the credentials
     * @throws NoSuchElementException if all users does not match the credentials
     */
    public User getUser(String userId) throws NoSuchElementException {
        return users.stream().filter(user -> user.getUserID().equals(userId)).findFirst().orElseThrow();
    }

    /**
     * Access users instances which match the User Identification Strings.
     * @param userIds all User Identification Strings of users to be retrieved.
     * @return All matching users
     * @throws NoSuchElementException if all users does not match all the credentials
     */
    public List<User> getUsers(List<String> userIds) throws NoSuchElementException {
        return userIds.stream().map(this::getUser).toList();
    }

    /**
     * Logic to convert student who is not in a committee to a student which is in a committee.
     * @param committeeStudent To be student committee.
     * @param joinCamp Camp which would be joined.
     * @return The new student committee class.
     */
    public StudentCommittee convertTo(Student committeeStudent, Camp joinCamp) {
        if (!users.removeIf(student -> student == committeeStudent)) {
            throw new RuntimeException("Student is not in the controller. Only use this function if the user is obtained from the controller.");
        }
        var newUser = new StudentCommittee(committeeStudent, joinCamp);
        users.add(newUser);
        return newUser;
    }
}
