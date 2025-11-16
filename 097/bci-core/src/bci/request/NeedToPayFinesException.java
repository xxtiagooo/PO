package bci.request;

public class NeedToPayFinesException extends Exception {

    public NeedToPayFinesException(int idUser, int fines) {
        super("User " + idUser + " needs to pay " + fines + " before borrowing more works.");
    }
}
