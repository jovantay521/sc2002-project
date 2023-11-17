import camp.CampController;
import camp.CampControllerException;
import user.Staff;
import user.Student;
import user.UserController;
import utils.TimeRegion;

import java.time.LocalDate;
import java.util.Arrays;

interface TestScenario {
    public boolean test();
}



public class TestMain {

    static void TestGroup(String testName, TestScenario... scenarios) {
        System.out.println("Testing " + testName);
        if (!Arrays.stream(scenarios).allMatch(TestScenario::test)) {
            throw new RuntimeException("Test failed on " + testName);
        }
    }

    public static void main(String[] args) {
        UserController userController = new UserController();
        CampController campController = new CampController();

        userController.addStudents("data/student_list.csv");
        userController.addStaff("data/staff_list.csv");

        campController.createCamp(
                (Staff) userController.verifyLogin("HUKUMAR", "password"),
                "Picnic party",
                new TimeRegion(LocalDate.of(2023, 11, 10), LocalDate.of(2023, 12, 10)),
                LocalDate.of(2050, 12, 12),
                "SCSE",
                "NTU",
                10,
                2,
                "Always welcoming."
        );
        campController.createCamp(
                (Staff) userController.verifyLogin("HUKUMAR", "password"),
                "Study group",
                new TimeRegion(LocalDate.of(2023, 12, 5), LocalDate.of(2023, 12, 20)),
                LocalDate.of(2050, 12, 12),
                "SCSE",
                "NTU",
                10,
                2,
                "Study together!"
        );

        Student student = (Student) userController.verifyLogin("YCHERN", "password");
        var camps = campController.getVisibleCamps(student);
        try {
            camps.get(0).addStudent(student);
        } catch (CampControllerException ignored) {
        }


        TestGroup("Conflicts in date should be spotted.", () -> {
            try {
                camps.get(1).addStudent(student);
            } catch (CampControllerException e) {
                return true;
            }
            return false;
        });

        TestGroup("Exceeding capacity should be spotted.", () -> {
            return true;
        });

        TestGroup("Repeated joining should be spotted.", () -> {
            try {
                camps.get(0).addStudent(student);
                return false;
            } catch (CampControllerException e) {
                return true;
            }
        });
    }
}
