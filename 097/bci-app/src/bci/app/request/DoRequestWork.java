package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.BorrowingRuleFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.request.Rule3FailedException;
import bci.request.RequestRegistrationFailedException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int idUser = Form.requestInteger(bci.app.user.Prompt.userId());
        int idWork = Form.requestInteger(bci.app.work.Prompt.workId());
        try {
            int dueDate = _receiver.registerRequest(idUser, idWork);
            _display.popup(Message.workReturnDay(idWork, dueDate));
        } catch (IllegalArgumentException e) { 
            // user or work does not exist
            String msg = e.getMessage();
            if (msg != null) {
                if (msg.contains("no such user")) {
                    throw new NoSuchUserException(idUser);
                } else if (msg.contains("no such work")) {
                    throw new NoSuchWorkException(idWork);
                }
            }
        } catch (Rule3FailedException e) {
            String res = Form.requestString(Prompt.returnNotificationPreference());
            if (res.equals("s")) {
                _receiver.subscribeAvailableNotifications(idUser, idWork);
            }
        } catch (RequestRegistrationFailedException e1) {
            throw new BorrowingRuleFailedException(idUser, idWork, e1.getIdRule());
        }
    }

}
