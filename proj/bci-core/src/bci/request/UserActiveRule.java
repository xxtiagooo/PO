package bci.request;

import bci.user.User;
import bci.work.Work;

public class UserActiveRule implements RuleVerification {
    private static final int IDRULE = 2;
    /* User must be active to make requests. */
    @Override
    public Status verify(Work work, User user) {
        return user.isActive() ? Status.SUCCESS : Status.FAILURE;
    }

    @Override
    public int getIdRule() {
        return IDRULE;
    }
}
