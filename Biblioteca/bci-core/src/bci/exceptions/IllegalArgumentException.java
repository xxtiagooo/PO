package bci.exceptions;

/**
 * Exception thrown when an illegal argument is encountered in BCI operations.
 */
public class IllegalArgumentException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

     /**
     * Constructs a new {@code IllegalArgumentException} with the specified detail message.
     *
     * @param message The detail message describing the illegal argument.
     */
    public IllegalArgumentException(String message) {
        super("Argumento ilegal: " + message);
    }
    
}
