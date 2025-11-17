package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.1.3. Advance the current date.
 */
class DoAdvanceDate extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoAdvanceDate} command.
     *
     * @param receiver The {@link LibraryManager} that executes the date change.
     */
    DoAdvanceDate(LibraryManager receiver) {
        super(Label.ADVANCE_DATE, receiver);
    }

    /**
     * Executes the command to advance the system date.
     *
     * @throws CommandException if the number of days is invalid.
     */
    @Override
    protected final void execute() throws CommandException {
            int days = Form.requestInteger(Prompt.daysToAdvance());
            _receiver.advanceDate(days);    
    }

}
