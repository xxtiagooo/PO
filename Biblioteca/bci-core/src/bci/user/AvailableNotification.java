package bci.user;

import bci.work.Work;

public class AvailableNotification extends Notification {

    private String _snapshot;
    
    public AvailableNotification(Work work) {
        super(work);
        // save current state of the work
        _snapshot = work.toString();
    }

    @Override
    public String toString() {
        return "DISPONIBILIDADE: " + _snapshot;
    }
}
