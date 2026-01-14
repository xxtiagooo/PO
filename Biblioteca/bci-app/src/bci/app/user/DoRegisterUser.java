package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.exceptions.RegistrationFailedException;
import bci.app.exceptions.UserRegistrationFailedException;
import bci.exceptions.UnrecognizedEntryException;

/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoRegisterUser} command.
     *
     * @param receiver The {@link LibraryManager} that handles user registration.
     */
    DoRegisterUser(LibraryManager receiver) {
        super(Label.REGISTER_USER, receiver);
    }

    /**
     * Executes the command to register a new user.
     *
     * @throws CommandException if registration fails.
     */
    @Override
    protected final void execute() throws CommandException {
        String name = Form.requestString(Prompt.userName());
        String email = Form.requestString(Prompt.userEMail());

        try {
            int idUser = _receiver.registerUser(name, email);
            _display.popup(Message.registrationSuccessful(idUser));
        } catch (RegistrationFailedException | UnrecognizedEntryException e) {
            throw new UserRegistrationFailedException(name, email);
        }
    }
}