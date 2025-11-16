package bci.user;

import java.io.Serial;
import java.io.Serializable;

public abstract class UserState implements Serializable {

    @Serial
    private static final long serialVersionUID = 202507171005L;
    protected User _user;

    public UserState(User user) {
        _user = user;
    }

    public abstract boolean isActive();

    public abstract boolean isSuspended();

    public String status() {
        return getClass().getName();
    }

    public abstract void returnWork(boolean onTime);

    public abstract void payFine(int amount);
    
    public abstract void penalizeForLateDelivery();

    @Override
    public String toString() {
        return this.getClass().getSimpleName().toUpperCase();
    }
}
