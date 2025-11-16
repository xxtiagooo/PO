package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * 4.2.4. Show all users.
 */
class DoShowUsers extends Command<LibraryManager> {

     /**
     * Constructs a new {@code DoShowUsers} command.
     *
     * @param receiver The {@link LibraryManager} that provides user information.
     */
    DoShowUsers(LibraryManager receiver) {
        super(Label.SHOW_USERS, receiver);
    }

    /**
     * Executes the command to display all users.
     */
    @Override
    protected final void execute() throws CommandException {
        _display.popup(_receiver.displayUsers());
    }
}
