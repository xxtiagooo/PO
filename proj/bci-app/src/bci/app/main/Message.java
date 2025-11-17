package bci.app.main;

/**
 * Interface that provides messages for the main menu commands.
 */
public interface Message {
     /**
     * Returns a message showing the current system date.
     *
     * @param date The current date.
     * @return A string displaying the current date.
     */
    static String currentDate(int date) {
        return "Data actual: " + date;
    }
}
