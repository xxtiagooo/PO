package bci.request;

import bci.user.User;
import bci.work.Work;

public class RequestByPriceRule implements RuleVerification {

    private static final int IDRULE = 6;

    /* Cannot request works beyond user's price limit. */
    @Override
    public Status verify(Work work, User user) {
        return user.canRequestWorkWithPrice(work.getPrice()) ? Status.SUCCESS : Status.FAILURE;
    }

    @Override
    public int getIdRule() {
        return IDRULE;
    }
}
