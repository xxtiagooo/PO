package bci.app.main;

import pt.tecnico.uilib.forms.Form;

import bci.LibraryManager;
import bci.app.exceptions.FileOpenFailedException;
import bci.exceptions.UnavailableFileException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * §4.1.1 Open and load files.
 */
class DoOpenFile extends Command<LibraryManager> {

    /**
     * Constructs a new {@code DoOpenFile} command.
     *
     * @param receiver The {@link LibraryManager} that handles file loading.
     */
    DoOpenFile(LibraryManager receiver) {
        super(Label.OPEN_FILE, receiver);
    }

    /**
     * Executes the command to open and load a file.
     *
     * @throws CommandException if the file cannot be opened.
     */
    @Override
    protected final void execute() throws CommandException {
        try {
            if (_receiver.changed() && Form.confirm(Prompt.saveBeforeExit())) {
                var cmd = new DoSaveFile(_receiver);
                cmd.execute();
            }
            _receiver.load(Form.requestString(Prompt.openFile()));
        } catch (UnavailableFileException e) {
            throw new FileOpenFailedException(e);
        }
    }

}
