package bci.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception thrown when a file cannot be opened.
 */
public class FileOpenFailedException extends CommandException {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /**
     * Constructs the exception with the file name.
     * @param filename The name of the file that could not be opened.
     */
    public FileOpenFailedException(Exception e) {
        super(Message.problemOpeningFile(e), e);
    }
}
