package bci.app.exceptions;

/**
 * Interface containing static methods to generate exception messages.
 */
interface Message {
    /**
     * Generates an error message when a file cannot be opened.
     *
     * @param cause Exception that caused the problem.
     * @return A string describing the error.
     */
    static String problemOpeningFile(Exception cause) {
        return "Problema ao abrir ficheiro: " + cause.getMessage();
    }

    /**
     * Generates an error message for a non-existent creator.
     *
     * @param id Identifier of the creator.
     * @return A string indicating that the creator does not exist.
     */
    static String noSuchCreator(String id) {
        return "O criador '" + id + "' não existe.";
    }

    /**
     * Generates an error message for a non-existent work.
     *
     * @param id Identifier of the work.
     * @return A string indicating that the work does not exist.
     */
    static String noSuchWork(int id) {
        return "A obra " + id + " não existe.";
    }

    /**
     * Generates an error message for a non-existent user.
     *
     * @param id Identifier of the user.
     * @return A string indicating that the user does not exist.
     */
    static String noSuchUser(int id) {
        return "O utente " + id + " não existe.";
    }

     /**
     * Generates an error message when a user is not suspended
     * but an operation requires suspension.
     *
     * @param id Identifier of the user.
     * @return A string indicating that the user is not suspended.
     */
    static String userNotSuspended(int id) {
        return "O utente " + id + " não se encontra suspenso.";
    }

     /**
     * Generates an error message when a user cannot borrow a work
     * due to a borrowing rule violation.
     *
     * @param idUser Identifier of the user.
     * @param idWork Identifier of the work.
     * @param idRule Identifier of the violated rule.
     * @return A string describing the borrowing rule violation.
     */
    static String borrowingRuleFailed(int idUser, int idWork, int idRule) {
        return "O utente " + idUser + " não pode requisitar a obra " + idWork + ". Violação da regra " + idRule + ".";
    }

    /**
     * Generates an error message when a work was not borrowed
     * by the specified user.
     *
     * @param idWork Identifier of the work.
     * @param idUser Identifier of the user.
     * @return A string indicating that the work was not borrowed by the user.
     */
    static String workNotBorrowedByUser(int idWork, int idUser) {
        return "A obra " + idWork + " não foi requisitada pelo utente " + idUser + ".";
    }


}
