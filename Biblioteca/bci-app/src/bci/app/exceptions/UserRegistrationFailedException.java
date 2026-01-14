package bci.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception thrown when user registration fails.
 */
public class UserRegistrationFailedException extends CommandException {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /**
    * Constructs the exception with the user's name and email.
    * @param name The user's name.
    * @param email The user's email.
    */
    public UserRegistrationFailedException(String name, String email) {
        super("Falha ao registar utente '" + name + "' (" + email + ")");
    }

}