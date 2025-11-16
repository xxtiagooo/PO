package bci.request;

/**
 * Exception thrown when a request cannot be registered.
 */
public class RequestRegistrationFailedException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private final int _idRule;

    /**
     * Constructs a new {@code RequestRegistrationFailedException} with the specified
     * work title, user name, and rule ID.
     *
     * @param title The title of the work involved in the request.
     * @param name  The name of the user making the request.
     * @param idRule The ID of the rule that failed.
     */
    public RequestRegistrationFailedException(String title, String name, int idRule) {
        super("Request registration failed: " + title + " (User: " + name + ")");
        _idRule = idRule;
    }
    
    /**
     * Gets the ID of the rule that caused the failure.
     *
     * @return The rule ID.
     */
    public int getIdRule() {
        return _idRule;
    }
    
}
