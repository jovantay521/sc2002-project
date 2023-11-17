package screen.suggestion;

import camp.Camp;
import camp.CampController;
import screen.Screen;
import screen.ScreenException;
import screen.StaffInChargeScreen;
import screen.StaffScreen;
import user.Staff;
import user.StudentCommittee;
import user.UserController;

public class StaffInChargeSuggestionScreen extends StaffScreen {
    protected Camp camp;
    public StaffInChargeSuggestionScreen(UserController userController, CampController campController, Staff staff, Camp camp) {
        super(userController, campController, staff);
        this.camp = camp;
    }

    @Override
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("Suggestions for Camp <" + camp + ">");

        var suggestions = camp.getAllSuggestions();
        displayContents(suggestions);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: Accept suggestions.");
        System.out.println("1: Reject suggestions.");
        System.out.println("9: Back.");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                try {
                    System.out.println("Choose a suggestion to accept.");
                    var selectedSuggestion = select(suggestions);
                    ((StudentCommittee) userController.getUser(selectedSuggestion.getUserID())).addPoint();
                    camp.deleteSuggestion(selectedSuggestion);
                    System.out.println("Accepted suggestion: " + selectedSuggestion);
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 1 -> {
                try {
                    System.out.println("Choose a suggestion to reject.");
                    var selectedSuggestion = select(suggestions);
                    camp.deleteSuggestion(selectedSuggestion);
                    System.out.println("Suggestion rejected.");
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 9 -> new StaffInChargeScreen(userController, campController, staff);
            default -> this;
        };
    }
}
