package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * §4.1.4 Management menus.
 */
class DoOpenMenuUsers extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoOpenMenuUsers} command.
     *
     * @param receiver The {@link LibraryManager} that coordinates menu actions.
     */
    DoOpenMenuUsers(LibraryManager receiver) {
        super(Label.OPEN_MENU_USERS, receiver);
    }

     /**
     * Executes the command by opening the users management menu.
     *
     * @throws CommandException if an error occurs while opening the menu.
     */
    @Override
    protected final void execute() throws CommandException {
        (new bci.app.user.Menu(_receiver)).open();
    }

}
