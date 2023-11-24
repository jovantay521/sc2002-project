package camp;

public final class Enquiry {
    private String message;
    private String answer;
    private final String userID;
    public Enquiry(String message, String userID) {
        this.message = message;
        this.answer = null;
        this.userID = userID;
    }
    public void edit(String message) {
        this.message = message;
    }
    public void reply(String message) { answer = message; }
    public boolean isAnswered() {
        return answer != null;
    }
    public String getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return message + ", " + (isAnswered() ? answer : "not yet answered.");
    }
}
