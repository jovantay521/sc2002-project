package camp;

import java.io.Serializable;

public final class Suggestion implements Serializable {
    private String text;
    private final String userID;
    public Suggestion(String text, String userID) {
        this.text = text;
        this.userID = userID;
    }
    public void edit(String newText) {
        text = newText;
    }
    public String getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return text;
    }
}
