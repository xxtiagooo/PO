package bci.user;

import bci.work.Work;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
public abstract class UserBehaviour implements Serializable {

    @Serial
    private static final long serialVersionUID = 202507171005L;
    
    protected User _user;

    public UserBehaviour(User user) {
        _user = user;
    }

    public abstract int getRequestTime(Work work);

    public abstract void calculateBehaviour(List<Boolean> deliveryHistory);
    
    public abstract int getMaxConcurrentRequests();

    public boolean wasLastReturnedOnTime(List<Boolean> deliveryHistory) {
        if (deliveryHistory.isEmpty()) {
            return true; // No history, assume on time
        }
        return deliveryHistory.get(deliveryHistory.size() - 1); // Return the last entry
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().toUpperCase();
    }

}
