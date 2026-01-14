package bci.app.work;

import bci.LibraryManager;

/**
 * Works management menu.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

    /**
     * Constructs the works management menu with all its commands.
     *
     * @param receiver The {@link LibraryManager} that executes the commands.
     */
    public Menu(LibraryManager receiver) {
        super(Label.TITLE, //
                new DoDisplayWork(receiver), //
                new DoDisplayWorks(receiver), //
                new DoPerformSearch(receiver), //
                new DoDisplayWorksByCreator(receiver), //
                new DoChangeWorkInventory(receiver) //
        );
    }

}
