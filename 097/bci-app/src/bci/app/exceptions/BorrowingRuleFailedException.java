package bci.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception thrown when a borrowing rule is violated.
 */
public class BorrowingRuleFailedException extends CommandException {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /**
     * Constructs the exception with a detailed message.
     * @param message The detail message.
     */
    public BorrowingRuleFailedException(int idUser, int idWork, int idRule) {
        super(Message.borrowingRuleFailed(idUser, idWork, idRule));
    }
}
