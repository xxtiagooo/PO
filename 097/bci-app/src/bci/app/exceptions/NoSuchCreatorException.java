package bci.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception thrown when a creator does not exist.
 */
public class NoSuchCreatorException extends CommandException {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /**
     * Constructs the exception with the creator's ID.
     * @param id The ID of the non-existent creator.
     */
    public NoSuchCreatorException(String id) {
        super(Message.noSuchCreator(id));
    }
}
