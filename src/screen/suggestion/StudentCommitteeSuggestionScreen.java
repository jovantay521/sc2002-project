package screen.suggestion;

import camp.Camp;
import camp.CampController;
import screen.Screen;
import screen.ScreenException;
import screen.StudentCommitteeScreen;
import user.StudentCommittee;
import user.UserController;

public class StudentCommitteeSuggestionScreen extends StudentCommitteeScreen {
    protected Camp camp;
    public StudentCommitteeSuggestionScreen(UserController userController, CampController campController, StudentCommittee studentCommittee, Camp camp) {
        super(userController, campController, studentCommittee);
        this.camp = camp;
    }

    @Override
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("Sent suggestions for camp <" + camp + ">");

        var suggestions = camp.getSentSuggestions(studentCommittee);
        displayContents(suggestions);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: Edit suggestion");
        System.out.println("1: Delete suggestion");
        System.out.println("9: Back.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                try {
                    System.out.println("Select suggestion: ");
                    var selectedSuggestion = select(suggestions);
                    System.out.println("Type new message to change.");
                    var newSuggestion = scanner.nextLine();
                    selectedSuggestion.edit(newSuggestion);
                    System.out.println("Suggestion changed.");
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 1 -> {
                try {
                    System.out.println("Select suggestion: ");
                    var selectedSuggestion = select(suggestions);
                    camp.deleteSuggestion(selectedSuggestion);
                    System.out.println("Suggestion deleted.");
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 9 -> new StudentCommitteeScreen(userController, campController, studentCommittee);
            default -> this;
        };
    }
}
