package bci.app.main;

/** Menu entries for the main menu. */
interface Label {

    /** Title of the main menu. */
    String TITLE = "Menu Principal";


     /** Command to open a file. */
    String OPEN_FILE = "Abrir ficheiro";

    /** Command to save a file. */
    String SAVE_FILE = "Guardar ficheiro";


    /** Command to display the current date. */
    String DISPLAY_DATE = "Ver data";

    /** Command to advance the current date. */
    String ADVANCE_DATE = "Avançar data";


    /** Command to open the users management menu. */
    String OPEN_MENU_USERS = "Menu de gestão de utentes";

    /** Command to open the works management menu. */
    String OPEN_MENU_WORKS = "Menu de gestão de obras";

    /** Command to open the requests management menu. */
    String OPEN_MENU_REQUESTS = "Menu de gestão de requisições";

}
