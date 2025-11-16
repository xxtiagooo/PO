package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.app.exceptions.NoSuchUserException;

/**
 * 4.2.2. Show specific user.
 */
class DoShowUser extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoShowUser} command.
     *
     * @param receiver The {@link LibraryManager} that provides user information.
     */
    DoShowUser(LibraryManager receiver) {
        super(Label.SHOW_USER, receiver);
    }

     /**
     * Executes the command to show a specific user.
     *
     * @throws CommandException if the specified user does not exist.
     */
    @Override
    protected final void execute() throws CommandException {
        int idUser = Form.requestInteger(Prompt.userId());
        try {
            String message = _receiver.displayUser(idUser);
            _display.popup(message);
        } catch (IllegalArgumentException e) {
            throw new NoSuchUserException(idUser);
        }
    }
} 