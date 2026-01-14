package bci.user;

import bci.work.Work;

public class RequestNotification extends Notification {

    private String _snapshot;
    public RequestNotification(Work work) {
        super(work);
        // save current state of the work
        _snapshot = work.toString();
    }

    @Override
    public String toString() {
        return "REQUISIÇÃO: " + _snapshot;
    }
}
