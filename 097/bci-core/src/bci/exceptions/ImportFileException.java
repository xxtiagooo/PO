package bci.exceptions;

/**
 * Exception thrown when there is an error processing an import file.
 */
public class ImportFileException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private static final String ERROR_MESSAGE = "Erro a processar ficheiro de import: ";

     /**
     * Constructs a new {@code ImportFileException} with the specified filename.
     *
     * @param filename The name of the file that caused the exception.
     */
    public ImportFileException(String filename) {
        super(ERROR_MESSAGE + filename);
    }

    /**
     * Constructs a new {@code ImportFileException} with the specified filename
     * and underlying cause.
     *
     * @param filename The name of the file that caused the exception.
     * @param cause    The underlying exception that triggered this exception.
     */
    public ImportFileException(String filename, Exception cause) {
        super(ERROR_MESSAGE + filename, cause);
    }

}
