package bci.app.request;

import bci.LibraryManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.request.NeedToPayFinesException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.UserIsActiveException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import bci.user.NoFineToPayException;

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int idUser = Form.requestInteger(bci.app.user.Prompt.userId());
        int idWork = Form.requestInteger(bci.app.work.Prompt.workId());
        try {
            _receiver.returnWork(idWork, idUser);
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("no such work")) {
                throw new NoSuchWorkException(idWork);
            } else if (msg != null && msg.contains("no such user")) {
                throw new NoSuchUserException(idUser);
            } else {
                throw new WorkNotBorrowedByUserException(idWork, idUser);
            }
        } catch (NeedToPayFinesException e) {
            int fine = _receiver.getUserFines(idUser);
            _display.popup(Message.showFine(idUser, fine));
            String res = Form.requestString(Prompt.finePaymentChoice());
            if (res.equals("s")) {
                try {
                    _receiver.payFine(idUser);
                } catch (NoFineToPayException ex) {
                    // This should not happen
                    throw new UserIsActiveException(idUser);
                }
            }
        }
    }

}
