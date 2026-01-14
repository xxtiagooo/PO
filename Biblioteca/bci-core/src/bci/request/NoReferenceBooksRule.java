package bci.request;

import bci.user.User;
import bci.work.Work;

public class NoReferenceBooksRule implements RuleVerification {

    private static final int IDRULE = 5;

    /* Cannot request reference books. */

    @Override
    public Status verify(Work work, User user) {
        return !work.getType().equals(Work.Type.REFERENCE) ? Status.SUCCESS : Status.FAILURE;
    }

    @Override
    public int getIdRule() {
        return IDRULE;
    }
}
