package bci.user;

import bci.work.Work;
import java.io.Serial;
import java.io.Serializable;

public class Notification implements Serializable {

    @Serial
    private static final long serialVersionUID = 202507171006L;

    private final Work _work;

    public Notification(Work work) {
        _work = work;
    }

    public Work getWork() {
        return _work;
    }
}
