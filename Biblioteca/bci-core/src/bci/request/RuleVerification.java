package bci.request;

import bci.user.User;
import bci.work.Work;
import java.io.Serial;
import java.io.Serializable;

public interface RuleVerification extends Serializable {

    @Serial
    static final long serialVersionUID = 202507171005L;

    public enum Status {
        SUCCESS,
        FAILURE,
        FAILURE_WITH_NOTIFICATION
    }

    public Status verify(Work work, User user);

    public int getIdRule();
}
