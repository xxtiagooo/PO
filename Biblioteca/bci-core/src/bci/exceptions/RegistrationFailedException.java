package bci.exceptions;

/**
 * Exception thrown when a user registration attempt fails.
 */
public class RegistrationFailedException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171005L;

    /**
     * Constructs a new {@code RegistrationFailedException} with the specified detail message.
     *
     * @param info A string describing the reason for the registration failure.
     */
    public RegistrationFailedException(String info) {
        super("Registration failed: " + info);
    }
    
}
