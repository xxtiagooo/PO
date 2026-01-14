package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.NoSuchWorkException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoChangeWorkInventory} command.
     *
     * @param receiver The {@link LibraryManager} that manages the works.
     */
    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
    }

    /**
     * Executes the command to change the inventory of a work.
     *
     * @throws CommandException if the work does not exist or the change value is invalid.
     */
    @Override
    protected final void execute() throws CommandException {
        int idWork = Form.requestInteger(Prompt.workId());
        int change = Form.requestInteger(Prompt.amountToUpdate());

        try {
            _receiver.updateInventory(idWork, change);
        } catch (IllegalArgumentException e) {
            // Check if the problem is a non-existent work
            if (e.getMessage() != null && e.getMessage().contains("no such work")) {
                throw new NoSuchWorkException(idWork);
            } else {
                _display.popup(Message.notEnoughInventory(idWork, change));
                // throw new NotEnoughInventoryException(idWork, change);
            }
        }
    }

}
