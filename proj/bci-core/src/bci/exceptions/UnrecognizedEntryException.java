package bci.exceptions;

/**
 * Exception representing issues with parsing an input file entry.
 */
public class UnrecognizedEntryException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private final String _entrySpecification;

    /**
     * Constructs a new {@code UnrecognizedEntryException} with the specified entry.
     *
     * @param entrySpecification The entry specification that could not be recognized.
     */
    public UnrecognizedEntryException(String entrySpecification) {
        _entrySpecification = entrySpecification;
    }

    /**
     * Constructs a new {@code UnrecognizedEntryException} with the specified entry
     * and underlying cause.
     *
     * @param entrySpecification The entry specification that could not be recognized.
     * @param cause              The underlying exception that caused this exception.
     */
    public UnrecognizedEntryException(String entrySpecification, Exception cause) {
        super(cause);
        _entrySpecification = entrySpecification;
    }

    /**
     * Returns the entry specification that triggered this exception.
     *
     * @return The unrecognized entry specification.
     */
    public String getEntrySpecification() {
        return _entrySpecification;
    }

}
