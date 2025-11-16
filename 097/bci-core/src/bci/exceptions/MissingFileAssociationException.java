package bci.exceptions;

/**
 * Exception thrown when an application or object is not associated with a file.
 */
public class MissingFileAssociationException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

     /**
     * Constructs a new {@code MissingFileAssociationException} with no detail message.
     */
    public MissingFileAssociationException() {
        super();
    }

     /**
     * Constructs a new {@code MissingFileAssociationException} with the specified detail message.
     *
     * @param message The detail message.
     */
    public MissingFileAssociationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code MissingFileAssociationException} with the specified
     * detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The underlying cause of the exception.
     */
    public MissingFileAssociationException(String message, Throwable cause) {
        super(message, cause);
    }

}
