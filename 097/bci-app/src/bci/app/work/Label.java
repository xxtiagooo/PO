package bci.app.work;

/**
 * Menu entries for the works management menu.
 */
interface Label {

    /** Title of the works management menu. */
    String TITLE = "Menu de obras";

    /** Command to show a specific work. */
    String SHOW_WORK = "Mostrar obra";

    /** Command to show all works. */
    String SHOW_WORKS = "Mostrar todas as obras";

    /** Command to show all works by a specific creator. */
    String SHOW_WORKS_BY_CREATOR = "Mostrar todas as obras de um criador";

    /** Command to change the inventory of a work. */
    String CHANGE_WORK_INVENTORY = "Alterar inventário de uma obra";

    /** Command to perform a search by terms. */
    String PERFORM_SEARCH = "Efectuar pesquisa de termos";

}
