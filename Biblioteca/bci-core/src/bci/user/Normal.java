package bci.user;

import bci.work.Work;
import java.util.List;

public class Normal extends UserBehaviour {

    private final static int LAST_WORK = 3;
    private final static int FIVE_OR_LESS_WORKS = 8;
    private final static int MORE_THAN_FIVE_WORKS = 15;
    private final static int MAX_CONCURRENT_REQUESTS = 3;
    
    public Normal(User user) {
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
        boolean mostRecent = wasLastReturnedOnTime(deliveryHistory);
        boolean secondMostRecent = wasLastReturnedOnTime(deliveryHistory.subList(0, deliveryHistory.size() - 1));
        boolean thirdMostRecent = wasLastReturnedOnTime(deliveryHistory.subList(0, deliveryHistory.size() - 2));
        if (!mostRecent && !secondMostRecent && !thirdMostRecent) {
            _user.setBehaviour(new Faltoso(_user));
            return;
        }
        if (deliveryHistory.size() < 5) {
            return; // Not enough for Cumpridor
        }
        boolean fourthMostRecent = wasLastReturnedOnTime(deliveryHistory.subList(0, deliveryHistory.size() - 3));
        boolean fifthMostRecent = wasLastReturnedOnTime(deliveryHistory.subList(0, deliveryHistory.size() - 4));

        // If the last five were returned on time, changes to Cumpridor
        if (mostRecent && secondMostRecent && thirdMostRecent && fourthMostRecent && fifthMostRecent) {
            _user.setBehaviour(new Cumpridor(_user));
        }
    }
}
