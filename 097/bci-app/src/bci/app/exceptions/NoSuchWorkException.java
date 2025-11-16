package bci.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception thrown when a work does not exist.
 */
public class NoSuchWorkException extends CommandException {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /**
     * Constructs the exception with the work's ID.
     * @param id The ID of the non-existent work.
     */
    public NoSuchWorkException(int id) {
        super(Message.noSuchWork(id));
    }
}
