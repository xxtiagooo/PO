package bci.app.main;

import bci.LibraryManager;


/**
 * The main menu.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

    /**
     * Constructs the main menu with all its commands.
     *
     * @param receiver The {@link LibraryManager} that executes the commands.
     */
    public Menu(LibraryManager receiver) {
        super(Label.TITLE, // 4.1
                new DoOpenFile(receiver), //
                new DoSaveFile(receiver), //
                new DoDisplayDate(receiver), //
                new DoAdvanceDate(receiver), //
                new DoOpenMenuUsers(receiver), //
                new DoOpenMenuWorks(receiver), //
                new DoOpenMenuRequests(receiver) //
        );
    }

}
