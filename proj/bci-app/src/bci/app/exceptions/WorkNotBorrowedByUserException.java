package bci.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception thrown when a creator does not exist.
 */
public class WorkNotBorrowedByUserException extends CommandException {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /**
     * Constructs the exception with the work's ID and user's ID.
     * @param idWork The ID of the work not borrowed by the user.
     * @param idUser The ID of the user.
     */
    public WorkNotBorrowedByUserException(int idWork, int idUser) {
        super(Message.workNotBorrowedByUser(idWork, idUser));
    }
}
