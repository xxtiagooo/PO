package bci.user;

import bci.work.Work;
import java.util.List;

public class Faltoso extends UserBehaviour {

    private final static int REQUEST_TIME = 2; // always 2 days
    private final static int MAX_CONCURRENT_REQUESTS = 1;
    
    public Faltoso(User user) {
        super(user);
    }

    @Override
    public int getRequestTime(Work work) {
        return REQUEST_TIME; // doent matter the availability
    }
    
    @Override
    public int getMaxConcurrentRequests() {
        return MAX_CONCURRENT_REQUESTS;
    }

    @Override
    public void calculateBehaviour(List<Boolean> deliveryHistory) {
        boolean mostRecent = wasLastReturnedOnTime(deliveryHistory);
        boolean secondMostRecent = wasLastReturnedOnTime(deliveryHistory.subList(0, deliveryHistory.size() - 1));
        boolean thirdMostRecent = wasLastReturnedOnTime(deliveryHistory.subList(0, deliveryHistory.size() - 2));

        // If the last three were returned on time, changes to Normal
        if (mostRecent && secondMostRecent && thirdMostRecent) {
            _user.setBehaviour(new Normal(_user));
        }
    }
}
