package camp;
/**
 * Attendees to submit enquiry
 * Student Committee Members and Staff to reply to enquiries
 */
public final class Enquiry {
	/*
	 * Enquiry message
	 */
    private String message;
    /**
     * Enquiry reply
     */
    private String answer;
    /**
     * Enquiry User's ID
     */
    private final String userID;
    /**
     * Constructor for Enquiry
     * @param message message in for enquiry
     * @param userID userID
     */
    public Enquiry(String message, String userID) {
        this.message = message;
        this.answer = null;
        this.userID = userID;
    }
    /**
     * edit message
     * @param message message to edit
     */
    public void edit(String message) {
        this.message = message;
    }
    /**
     * reply to message
     * @param message message to reply with
     */
    public void reply(String message) { answer = message; }
    /**
     * check if enquiry has been answered
     * @return 1 or 0
     */
    public boolean isAnswered() {
        return answer != null;
    }
    /**
     * get User ID of user that submitted enquiry
     * @return userID
     */
    public String getUserID() {
        return userID;
    }

    @Override
    /**
     * toString method
     */
    public String toString() {
        return message + ", " + (isAnswered() ? answer : "not yet answered.");
    }
}
