package user;

import java.util.ArrayList;
import java.util.List;

public class CampCommitteeMember {
    private String username;
    private List<Camp> camps;
    private List<Suggestion> suggestions;
    private int points;

    public CampCommitteeMember(String username) {
        this.username = username;
        this.camps = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.points = 0;
    }

    public void viewCampDetails(Camp camp) {
        // getCampInformation
    }
    
    public void submitSuggestion(String suggestionText) {
        suggestions.add(new Suggestion(suggestionText));
    }

    public void viewEnquiries(Camp camp) {
        // TODO: implement enquiries class

    public void replyEnquiries() {
        // TODO: implement enquiries class
    }

    public void viewAndProcessSuggestions() {
        // TODO: implement suggestions class
    }

    public void generateReport(Camp camp, String filter, String format) {
        // Code to generate a report in txt or csv format
    } 
}
