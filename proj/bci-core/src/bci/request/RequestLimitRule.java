package bci.request;

import bci.user.User;
import bci.work.Work;

public class RequestLimitRule implements RuleVerification {

    private static final int IDRULE = 4;

    /* Limit of simultaneous requests per user */
    @Override
    public Status verify(Work work, User user) {
        return user.canMakeNewRequest() ? Status.SUCCESS : Status.FAILURE;
    }

    @Override
    public int getIdRule() {
        return IDRULE;
    }
}
