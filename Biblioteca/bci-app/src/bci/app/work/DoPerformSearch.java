package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.3.5. Perform search according to miscellaneous criteria.
 */
class DoPerformSearch extends Command<LibraryManager> {

    DoPerformSearch(LibraryManager receiver) {
        super(Label.PERFORM_SEARCH, receiver);
    }

    @Override
    protected final void execute() {
        String searchTerm = Form.requestString(Prompt.searchTerm());
        String results = _receiver.search(searchTerm);
        _display.popup(results);
    }

}
