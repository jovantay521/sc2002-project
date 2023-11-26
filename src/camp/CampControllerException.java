package camp;

/**
 * For throwing of exception message for camp controller class
 */
public class CampControllerException extends Exception {
    /**
     * Constructor
     * @param message Exception Message
     */
    public CampControllerException(String message) {
        super(message);
    }
}
