package bci.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception thrown when a user does not exist.
 */
public class NoSuchUserException extends CommandException {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /**
     * Constructs the exception with the user's ID.
     * @param id The ID of the non-existent user.
     */
    public NoSuchUserException(int id) {
        super(Message.noSuchUser(id));
    }
}
