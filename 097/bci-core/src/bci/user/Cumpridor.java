package bci.user;

import bci.work.Work;
import java.util.List;

public class Cumpridor extends UserBehaviour {

    private final static int LAST_WORK = 8;
    private final static int FIVE_OR_LESS_WORKS = 15;
    private final static int MORE_THAN_FIVE_WORKS = 30;
    private final static int MAX_CONCURRENT_REQUESTS = 5;

    public Cumpridor(User user) {
        super(user);
    }

    @Override
    public int getRequestTime(Work work) {
        if (work.getAvailable() == 1) {
            return LAST_WORK;
        } else if (work.getAvailable() <= 5) {
            return FIVE_OR_LESS_WORKS;
        } else {
            return MORE_THAN_FIVE_WORKS;
        }
    }

    @Override
    public int getMaxConcurrentRequests() {
        return MAX_CONCURRENT_REQUESTS;
    }
    
    @Override
    public void calculateBehaviour(List<Boolean> deliveryHistory) {
        if (deliveryHistory.size() < 3) {
            return; // Not enough for Faltoso
        }
        boolean mostRecent = super.wasLastReturnedOnTime(deliveryHistory);
        boolean secondMostRecent = super.wasLastReturnedOnTime(deliveryHistory.subList(0, deliveryHistory.size() - 1));
        boolean thirdMostRecent = super.wasLastReturnedOnTime(deliveryHistory.subList(0, deliveryHistory.size() - 2));
        if (!mostRecent && !secondMostRecent && !thirdMostRecent) {
            _user.setBehaviour(new Faltoso(_user));
        }
    }

}
