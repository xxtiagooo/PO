package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * §4.1.4 Management menus.
 */
class DoOpenMenuRequests extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoOpenMenuRequests} command.
     *
     * @param receiver The {@link LibraryManager} that coordinates menu actions.
     */
    DoOpenMenuRequests(LibraryManager receiver) {
        super(Label.OPEN_MENU_REQUESTS, receiver);
    }

    /**
     * Executes the command by opening the requests management menu.
     *
     * @throws CommandException if an error occurs while opening the menu.
     */
    @Override
    protected final void execute() throws CommandException {
        (new bci.app.request.Menu(_receiver)).open();
    }

}
