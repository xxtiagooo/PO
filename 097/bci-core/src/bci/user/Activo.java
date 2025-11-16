package bci.user;

public class Activo extends UserState {

    public Activo(User user) {
        super(user);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean isSuspended(){
        return false;
    }
    
    @Override
    public void payFine(int amount) {
        _user.updateFines(-amount);
    }

    @Override
    public void returnWork(boolean onTime) {
        // When user physically returns a work (whether on time or late)
        // Only change state if has fines to pay
        if (!onTime && _user.getFines() > 0) {
            _user.setState(new Suspenso(_user));
        }
    }
    
    public void penalizeForLateDelivery() {
        // When a requisited work passes its due date without being returned
        // User becomes suspended immediately
        _user.setState(new Suspenso(_user));
    }

}
