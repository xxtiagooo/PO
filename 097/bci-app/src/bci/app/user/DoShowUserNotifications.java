package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.app.exceptions.NoSuchUserException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

    DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int idUser = Form.requestInteger(Prompt.userId());
        try {
            String message = _receiver.displayUserNotifications(idUser);
            _display.popup(message);
        } catch (IllegalArgumentException e) {
            throw new NoSuchUserException(idUser);
        }
    }

}
