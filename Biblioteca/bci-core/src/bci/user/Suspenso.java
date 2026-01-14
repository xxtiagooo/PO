package bci.user;

public class Suspenso extends UserState {

    public Suspenso(User user) {
        super(user);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isSuspended(){
        return true;
    }

    @Override
    public void payFine(int amount) {
        _user.updateFines(-amount);
        if (_user.getFines() == 0) {
            // if there are no fines, returns to Active
            _user.setState(new Activo(_user));
        }
    }

    @Override
    public void returnWork(boolean onTime) {
        // If returned on time, has no fines and no active late requests, returns to Activo
        // && _user.getActiveLateRequests() == 0
        if (onTime && _user.getFines() == 0) {
            _user.setState(new Activo(_user));
        }
    }
    
    @Override
    public void penalizeForLateDelivery() {
        // Already suspended, stays suspended
    }
}
