package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.3.2. Display all works.
 */
class DoDisplayWorks extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoDisplayWorks} command.
     *
     * @param receiver The {@link LibraryManager} that provides work
     * information.
     */
    DoDisplayWorks(LibraryManager receiver) {
        super(Label.SHOW_WORKS, receiver);
    }

    /**
     * Executes the command to display all works.
     *
     * @throws CommandException if no works are registered.
     */
    @Override
    protected final void execute() throws CommandException {
        String message = _receiver.displayWorks();
        _display.popup(message);
    }
}
