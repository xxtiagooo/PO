package bci.request;

import bci.user.User;
import bci.work.Work;

public class WorkMustBeAvailableRule implements RuleVerification {

    private static final int IDRULE = 3;

    /*Work must have available copies to be requested. */
    @Override
    public Status verify(Work work, User user) {
        return work.getAvailable() > 0 ? Status.SUCCESS : Status.FAILURE_WITH_NOTIFICATION;
    }

    @Override
    public int getIdRule() {
        return IDRULE;
    }
}
