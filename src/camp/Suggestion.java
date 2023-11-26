package camp;
/**
 * Student Committee to send suggestion
 * Staff to reply to suggestion
 */
public final class Suggestion {
    /**
     * content of suggestion
     */
	private String text;
	/*
	 * userID of user who sent suggestion
	 */
    private final String userID;
    /**
     * Constructor for Suggestion
     * @param text Suggestion content
     * @param userID Suggestion User's ID
     */
    public Suggestion(String text, String userID) {
        this.text = text;
        this.userID = userID;
    }
    /**
     * edit suggestion
     * @param newText new content for suggestion
     */
    public void edit(String newText) {
        text = newText;
    }
    /**
     * return userID
     * @return userID
     */
    public String getUserID() {
        return userID;
    }

    @Override
    /**
     * return content of suggestion
     */
    public String toString() {
        return text;
    }
}
