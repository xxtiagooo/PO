package bci.app.user;

/**
 * Menu entries for the users management menu.
 */
interface Label {

    /** Title of the users management menu. */
    String TITLE = "Menu de Gestão de Utentes";

    /** Command to register a new user. */
    String REGISTER_USER = "Registar utente";

     /** Command to show a specific user. */
    String SHOW_USER = "Mostrar utente";

    /** Command to show a user's notifications. */
    String SHOW_USER_NOTIFICATIONS = "Mostrar notificações de utente";

    /** Command to list all users. */
    String SHOW_USERS = "Listar utentes";

    /** Command to pay a user's fine. */
    String PAY_FINE = "Saldar multa de utente";

}
