package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * §4.1.4 Management menus.
 */
class DoOpenMenuWorks extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoOpenMenuWorks} command.
     *
     * @param receiver The {@link LibraryManager} that coordinates menu actions.
     */
    DoOpenMenuWorks(LibraryManager receiver) {
        super(Label.OPEN_MENU_WORKS, receiver);
    }

    /**
     * Executes the command by opening the works management menu.
     *
     * @throws CommandException if an error occurs while opening the menu.
     */
    @Override
    protected final void execute() throws CommandException {
        (new bci.app.work.Menu(_receiver)).open();
    }

}
