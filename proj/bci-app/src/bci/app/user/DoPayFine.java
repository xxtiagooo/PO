package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.UserIsActiveException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.user.NoFineToPayException;
import bci.app.exceptions.NoSuchUserException;



/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

    DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int idUser = Form.requestInteger(Prompt.userId());
        try {
            _receiver.payFine(idUser);
        } catch (IllegalArgumentException e) {
            // user does not exist
            throw new NoSuchUserException(idUser);
        } catch (NoFineToPayException e) {
            throw new UserIsActiveException(idUser);
        }
    }

}
