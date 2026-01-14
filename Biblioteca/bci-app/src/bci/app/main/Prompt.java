package bci.app.main;

/**
 * Interface that provides prompt messages for user input
 * in the main menu commands.
 */
interface Prompt {

    /**
     * Prompt message asking for the file to open.
     *
     * @return String asking for the file to open.
     */
    static String openFile() {
        return "Ficheiro a abrir: ";
    }

    /**
     * Prompt message asking whether to save before exiting.
     *
     * @return String asking for save confirmation.
     */
    static String saveBeforeExit() {
        return "Guardar antes de fechar? ";
    }

    /**
     * Prompt message asking for the filename when saving.
     *
     * @return String asking for the filename to save as.
     */
    static String saveAs() {
        return "Guardar ficheiro como: ";
    }

    /**
     * Prompt message for a new file that has no name yet.
     *
     * @return String asking for a filename for the new file.
     */

    static String newSaveAs() {
        return "Ficheiro sem nome. " + saveAs();
    }

    /**
     * Prompt message asking for the number of days to advance.
     *
     * @return String asking for the number of days.
     */
    static String daysToAdvance() {
        return "Introduza número de dias a avançar: ";
    }

}
