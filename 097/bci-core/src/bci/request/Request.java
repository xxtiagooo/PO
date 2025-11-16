package bci.request;

import bci.user.User;
import bci.work.Work;
import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = 202507171005L;

    public static final int FINE_PER_DAY = 5;
    
    private final int _idRequest;
    private final User _user;
    private final Work _work;
    private final int _dueDate;
    private boolean _isReturned;
    private int _daysPastDue;

    public Request(int idRequest, User user, Work work, int dueDate) {
        _idRequest = idRequest;
        _user = user;
        _work = work;
        _dueDate = dueDate;
        _isReturned = false;
        _user.incrementActiveRequests();
        _daysPastDue = 0;
    }

    public int getIdRequest() {
        return _idRequest;
    }

    public int getIdUser() {
        return _user.getIdUser();
    }

    public int getIdWork() {
        return _work.getIdWork();
    }

    public boolean isReturned() {
        return _isReturned;
    }

    public void setReturned(boolean returned) {
        if (returned && !_isReturned) {
            _isReturned = true;
            _user.decrementActiveRequestCount();
        } else {
            _isReturned = returned;
        }
    }

    public User getUser() {
        return _user;
    }

    public Work getWork() {
        return _work;
    }

    public String getTitle() {
        return _work.getTitle();
    }

    public int getDueDate() {
        return _dueDate;
    }

    public int getDaysPastDue() {
        return _daysPastDue;
    }

    public void calculateDaysPastDue(int currentDate) {
        if (currentDate > _dueDate) {
            _daysPastDue = currentDate - _dueDate;
        } else {
            _daysPastDue = 0;
        }
    }

    public boolean isLate() {
        return !_isReturned && _daysPastDue > 0;
    }

    public void calculateFine() {
        int fine = _daysPastDue * FINE_PER_DAY;
        _user.updateFines(fine);
    }

    public void payFine(int amount) {
        _user.updateFines(-amount);
    }

    public void returnWork() {
        boolean onTime = _daysPastDue == 0;
        setReturned(true);
        _work.updateAvailable(1);
        _user.returnWork(onTime);
        calculateFine();
    }
}

