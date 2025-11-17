package bci.request;

public class Rule3FailedException extends Exception {

    private final static String MESSAGE = "Falha na regra 3";
    
    public Rule3FailedException(String title, String name) {
        super(MESSAGE);
    }

}
