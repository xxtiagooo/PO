package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchCreatorException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


/**
 * 4.3.3. Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoDisplayWorksByCreator} command.
     *
     * @param receiver The {@link LibraryManager} that provides work
     * information.
     */
    DoDisplayWorksByCreator(LibraryManager receiver) {
        super(Label.SHOW_WORKS_BY_CREATOR, receiver);
    }

    /**
     * Executes the command to display all works by a specific creator.
     *
     * @throws CommandException if no works are registered for the given
     * creator.
     */
    @Override
    protected final void execute() throws CommandException {
        String idCreator = Form.requestString(Prompt.creatorId());
        try {
            String message = _receiver.displayWorksByCreator(idCreator);
            _display.popup(message);
        } catch (IllegalArgumentException e) {
            throw new NoSuchCreatorException(idCreator);
        }
    }
}
