package bci.app.work;

/**
 * Interface that provides messages for work-related commands.
 */
interface Message {

    /**
     * Returns a message indicating that the specified work does not exist.
     *
     * @param idWork The ID of the work.
     * @return A string stating the work does not exist.
     */
    static String noSuchWork(int idWork) {
        return "A obra " + idWork + " não existe.";
    }

    /**
     * Returns a message indicating that the specified work ID is invalid.
     *
     * @param idWork The invalid work ID.
     * @return A string stating the work ID is invalid.
     */
    static String invalidWorkId(int idWork) {
        return "Número de obra inválido: " + idWork + ".";
    }

    /**
     * Returns a message indicating that the work was requested for a certain number of days.
     *
     * @param idWork The work ID.
     * @param days   The number of days it was requested for.
     * @return A string stating the work was requested for the given days.
     */
    static String workRequestedForDays(int idWork, int days) {
        return "A obra " + idWork + " foi requisitada por " + days + " dias.";
    }

    /**
     * Returns a message indicating that there is not enough inventory to decrement.
     *
     * @param idWork The work ID.
     * @param amount The requested decrement amount.
     * @return A string stating the decrement exceeds the inventory.
     */
    static String notEnoughInventory(int idWork, int amount) {
        return "A quantidade de exemplares da obra " + idWork + " não pode ser decrementada em " + amount + " (excederia o inventário).";
    }

    /**
     * Returns a message indicating that the work cannot be requested because no copies are available.
     *
     * @param idWork The work ID.
     * @return A string stating there are no copies available for the work.
     */
    static String semExemplares(int idWork) {
        return "A obra " + idWork + " não pode ser requisitada: não há exemplares disponíveis.";
    }

}
