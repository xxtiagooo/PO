package bci.exceptions;

/**
 * Exception thrown when work registration fails.
 */
public class WorkRegistrationFailedException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171004L;

    /**
     * Constructs a new {@code WorkRegistrationFailedException} with the specified
     * work title and request ID.
     *
     * @param title The title of the work that failed to register.
     * @param id    The ID of the request associated with the registration attempt.
     */
    public WorkRegistrationFailedException(String title, int id) {
        super("Work registration failed: " + title + " (Request ID: " + id + ")");
    }

}
