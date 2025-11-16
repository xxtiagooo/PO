package bci.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception thrown when a user is active.
 */
public class UserIsActiveException extends CommandException {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /**
     * Constructs the exception with the user's ID.
     * @param id The ID of the active user.
     */
    public UserIsActiveException(int id) {
        super(Message.userNotSuspended(id));
    }
}
