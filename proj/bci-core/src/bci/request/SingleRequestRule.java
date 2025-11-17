package bci.request;

import bci.user.User;
import bci.work.Work;

public class SingleRequestRule implements RuleVerification {
    private static final int IDRULE = 1;
    /* Cannot have more than one active request for the same work. */
    @Override
    public Status verify(Work work, User user) {
        boolean hasActiveRequest = user.hasActiveRequestForWork(work);
        if (hasActiveRequest) {
            return Status.FAILURE;
        }
        return Status.SUCCESS;
    }

    @Override
    public int getIdRule() {
        return IDRULE;
    }
}
