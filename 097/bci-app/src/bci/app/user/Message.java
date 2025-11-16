package bci.app.user;

/**
 * Interface that provides messages for user-related commands.
 */
interface Message {

    /**
     * Returns a message indicating that a new user has been successfully registered.
     *
     * @param idUser The ID of the newly created user.
     * @return A string confirming successful registration.
     */
    static String registrationSuccessful(int idUser) {
        return "Novo utente criado com o número " + idUser + ".";
    }

}
