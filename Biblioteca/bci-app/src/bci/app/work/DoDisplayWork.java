package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.app.exceptions.NoSuchWorkException;

/**
 * 4.3.1. Display work.
 */
class DoDisplayWork extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoDisplayWork} command.
     *
     * @param receiver The {@link LibraryManager} that provides work information.
     */
    DoDisplayWork(LibraryManager receiver) {
        super(Label.SHOW_WORK, receiver);
    }

    /**
     * Executes the command to display a specific work.
     *
     * @throws CommandException if the specified work does not exist.
     */
    @Override
    protected final void execute() throws CommandException {
        int idWork = Form.requestInteger(Prompt.workId());
        try {
            String message = _receiver.displayWork(idWork);
            _display.popup(message);
        } catch (IllegalArgumentException e) {
            throw new NoSuchWorkException(idWork);
        }
    }

}
