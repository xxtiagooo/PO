package bci.exceptions;

/** Class encoding file access failures. */
public class UnavailableFileException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private final String _filename;

    /**
     * Constructs a new {@code UnavailableFileException} with the specified filename.
     *
     * @param filename The name of the file that could not be accessed or processed.
     */
    public UnavailableFileException(String filename) {
        super("Erro a processar ficheiro " + filename);
        _filename = filename;
    }

    /**
     * Constructs a new {@code UnavailableFileException} with the specified filename
     * and underlying cause.
     *
     * @param filename The name of the file that could not be accessed or processed.
     * @param cause    The underlying exception that caused this exception.
     */
    public UnavailableFileException(String filename, Throwable cause) {
        super("Erro a processar ficheiro " + filename, cause);
        _filename = filename;
    }

    /**
     * Returns the filename that triggered this exception.
     *
     * @return The filename associated with this exception.
     */
    public String getFilename() {
        return _filename;
    }

}
