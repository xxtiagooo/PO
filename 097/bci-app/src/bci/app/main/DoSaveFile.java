package bci.app.main;

import bci.LibraryManager;
import java.io.IOException;
import pt.tecnico.uilib.forms.Form;
import bci.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * §4.1.1 Open and load files.
 */
class DoSaveFile extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoSaveFile} command.
     *
     * @param receiver The {@link LibraryManager} responsible for saving the state.
     */
    DoSaveFile(LibraryManager receiver) {
        super(Label.SAVE_FILE, receiver);
    }

    /**
     * Saves the current state to the associated file. If no file is associated, requests a file name and saves to
     * that file.
     *
     * @throws CommandException if there is an error while saving.
     */
    @Override
    protected final void execute() {
        try {
            _receiver.save();
        } catch (MissingFileAssociationException e) {
            try {
                _receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
            } catch (MissingFileAssociationException | IOException ex) {
                ex.printStackTrace();   // not tested
            }
        } catch (IOException e) {
            e.printStackTrace();   // not tested
        }
    }

}
