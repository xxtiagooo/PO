package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;

/**
 * 4.1.2. Display the current date.
 */
class DoDisplayDate extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoDisplayDate} command.
     *
     * @param receiver The {@link LibraryManager} that provides the current date.
     */
    DoDisplayDate(LibraryManager receiver) {
        super(Label.DISPLAY_DATE, receiver);
    }

     /**
     * Executes the command to display the current system date.
     */
    @Override
    protected final void execute() {
        int currentDate = _receiver.getDate();
        _display.popup(Message.currentDate(currentDate));
    }

}
