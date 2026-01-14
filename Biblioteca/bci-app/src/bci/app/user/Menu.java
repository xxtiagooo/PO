package bci.app.user;

import bci.LibraryManager;

/**
 * Users management menu.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

    /**
     * Constructs the users management menu with all its commands.
     *
     * @param receiver The {@link LibraryManager} that executes the commands.
     */
    public Menu(LibraryManager receiver) {
        super(Label.TITLE, //
                new DoRegisterUser(receiver), //
                new DoShowUser(receiver), //
                new DoShowUsers(receiver), //
                new DoShowUserNotifications(receiver), //
                new DoPayFine(receiver) //
        );
    }

}
