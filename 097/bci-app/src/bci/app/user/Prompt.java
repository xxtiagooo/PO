package bci.app.user;

/**
 * Interface that defines prompts for user input.
 */
public interface Prompt {

    /**
     * Prompt message asking for the user ID.
     *
     * @return String asking the user to enter the ID.
     */

    static String userId() {
        return "Introduza o número de utente: ";
    }

    /**
     * Prompt message asking for the user name.
     *
     * @return String asking the user to enter the name.
     */
    static String userName() {
        return "Introduza o nome do utente: ";
    }

    /**
     * Prompt message asking for the user email.
     *
     * @return String asking the user to enter the email address.
     */
    static String userEMail() {
        return "Introduza o endereço de correio do utente: ";
    }

}
