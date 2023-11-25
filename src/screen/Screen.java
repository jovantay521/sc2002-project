package screen;

import camp.CampController;
import user.User;
import user.UserController;
import user.Student;
import utils.TimeRegion;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class Screen {
    protected UserController userController;
    protected CampController campController;
    Screen(UserController userController, CampController campController) {
        this.userController = userController;
        this.campController = campController;
    }
    public static Scanner scanner = new Scanner(System.in);
    public abstract Screen display();
    public static<T> T select(List<T> indexableContainable) throws ScreenException {
        try {
            var choice = scanner.nextInt();
            scanner.nextLine();
            return indexableContainable.get(choice);
        } catch (InputMismatchException e) {
            scanner.nextLine();
            throw new ScreenException("Use a number for selection.");
        } catch (IndexOutOfBoundsException e) {
            throw new ScreenException("Not a valid selection.");
        }
    }
    public static<T> void displayContents(List<T> camps) {
        if (camps.isEmpty()) {
            System.out.println("None");
        } else {
            for (var it = camps.listIterator(); it.hasNext(); ) {
                T t = it.next();
                System.out.println(it.previousIndex() + ": " + t.toString());
            }
        }
    }
    public static int getInt() throws ScreenException {
        int ret;
        try {
            ret = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            throw new ScreenException("Use a number for selection.");
        }
        return ret;
    }
    protected static void changePassword(User user) {
        System.out.println("Choose new password: ");
        var password = scanner.nextLine();
        user.changePassword(password);
    }
    protected static void selectFilter(User user) {
        while (true) {
            System.out.println("Currently in use filters.");
            var filters = user.getFilters().keySet().stream().toList();

            displayContents(filters);

            System.out.println();
            System.out.println("Options: ");
            System.out.println("0: Add Date filter");
            System.out.println("1: Add Location filter");
            System.out.println("8: Delete filter");
            System.out.println("9: Return.");

            int filterChoice = scanner.nextInt();
            scanner.nextLine();
            switch (filterChoice) {
                case 0 -> {
                    System.out.println("What is your preferred date (indicate start) (YYYY-MM-DD)?");
                    var start = scanner.nextLine();
                    LocalDate startDate = null;
                    LocalDate endDate = null;

                    while (startDate == null) {
                        try {
                            startDate = LocalDate.parse(start);
                        } catch (DateTimeParseException e) {
                            System.out.println("Please enter a valid date.");
                        }
                    }

                    System.out.println("What is your preferred date (indicate end) (YYYY-MM-DD)?");
                    var end = scanner.nextLine();
                    while (endDate == null) {
                        try {
                            endDate = LocalDate.parse(end);
                        } catch (DateTimeParseException e) {
                            System.out.println("Please enter a valid date.");
                        }
                    }

                    user.addFilter("Date " + start + " " + end , CampController.DateFilter(new TimeRegion(
                           startDate, endDate
                    )));
                }
                case 1 -> {
                    System.out.println("What is your preferred location?");
                    String location = scanner.nextLine();
                    user.addFilter("Location", CampController.LocationFilter(location));
                }
                case 8 -> {
                    try {
                        System.out.println("Choose which filter to delete.");
                        var filter = select(filters);
                        user.deleteFilter(filter);
                    } catch (ScreenException exception) {
                        System.out.println(exception.getMessage());
                    }
                }
                case 9 -> {
                    return;
                }
            }
        }
    }
}
