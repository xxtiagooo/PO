package bci.app.work;

/**
 * Interface that provides prompt messages for work-related commands.
 */
public interface Prompt {

    /**
     * Prompt message asking for the work ID.
     *
     * @return String asking the user to enter the work ID.
     */
    static String workId() {
        return "Introduza o número da obra: ";
    }

     /**
     * Prompt message asking for the amount to update in inventory.
     *
     * @return String asking the user to enter the quantity to update.
     */
    static String amountToUpdate() {
        return "Introduza a quantidade a actualizar: ";
    }

    /**
     * Prompt message asking for a search term.
     *
     * @return String asking the user to enter a search term.
     */
    static String searchTerm() {
        return "Introduza o termo de pesquisa: ";
    }

    /**
     * Prompt message asking for the creator ID.
     *
     * @return String asking the user to enter the creator's ID.
     */
    static String creatorId() {
        return "Introduza o identificador do criador: ";
    }
}
