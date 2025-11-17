package bci.user;

import bci.request.Request;
import bci.work.Work;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 202507171005L;

    private static final int MAX_PRICE_FOR_REQUEST = 25;

    private final int _idUser;
    private final String _name;
    private final String _email;
    private UserBehaviour _behaviour;
    private UserState _state;
    private int _activeRequestCount; // Local counter of active requests
    private int _fines;
    private int _activeLateRequests; // Local counter of active late requests
    private final List<Boolean> _deliveryHistory;
    private final List<Request> _activeRequests;

    public User(int idUser, String... fields) {
        _idUser = idUser;
        _name = fields[0].trim();
        _email = fields[1].trim();
        _behaviour = new Normal(this);
        _state = new Activo(this);
        _activeRequestCount = 0;
        _activeLateRequests = 0;
        _fines = 0;
        _deliveryHistory = new ArrayList<>();
        _activeRequests = new ArrayList<>();
    }

    // getters
    public int getIdUser() {
        return _idUser;
    }

    public String getName() {
        return _name;
    }

    public String getEmail() {
        return _email;
    }

    public int getFines() {
        return _fines;
    }

    public int getMaxConcurrentRequests() {
        return _behaviour.getMaxConcurrentRequests();
    }

    public int getRequestTime(Work work) {
        return _behaviour.getRequestTime(work);
    }

    public void incrementActiveRequests() {
        _activeRequestCount++;
    }

    public void incrementActiveLateRequests() {
        _activeLateRequests++;
    }

    public void decrementActiveRequestCount() {
        if (_activeRequestCount > 0) {
            _activeRequestCount--;
        }
    }

    public int getActiveRequestCount() {
        _activeRequestCount = _activeRequests.size();
        return _activeRequestCount;
    }

    public int getActiveLateRequests() {
        _activeLateRequests = (int) _activeRequests.stream().filter(Request::isLate).count();
        return _activeLateRequests;
    }

    public boolean hasActiveRequestForWork(Work work) {
        return _activeRequests.stream().anyMatch(r -> r.getIdWork() == work.getIdWork());
    }

    public boolean canRequestWorkWithPrice(int price) {
        return behaviour().equals("CUMPRIDOR") || price <= MAX_PRICE_FOR_REQUEST;
    }

    public boolean isActive() {
        return _state.isActive();
    }

    public boolean isSuspended() {
        return _state.isSuspended();
    }

    public boolean canMakeNewRequest() {
        return _activeRequestCount < getMaxConcurrentRequests();
    }

    public void addRequest(Request request) {
        _activeRequests.add(request);
    }

    public void removeRequest(Request request) {
        _activeRequests.remove(request);
    }

    public List<Request> getActiveRequests() {
        return new ArrayList<>(_activeRequests);
    }

    protected void setState(UserState state) {
        _state = state;
    }

    protected void setBehaviour(UserBehaviour behaviour) {
        _behaviour = behaviour;
    }

    public void addDeliveryHistory(boolean onTime) {
        _deliveryHistory.add(onTime);
    }

    public void returnWork(boolean onTime) {
        addDeliveryHistory(onTime);
        _state.returnWork(onTime);
        _behaviour.calculateBehaviour(_deliveryHistory);
    }

    public void penalizeForLateDelivery() {
        _state.penalizeForLateDelivery();
    }

    public void payFine(int amount) {
        _state.payFine(amount);
    }

    public void recalculateBehaviour() {
        _behaviour.calculateBehaviour(_deliveryHistory);
    }

    public void ensureCorrectState() {
        // If user has fines, must be suspended
        if (_fines > 0 && isActive()) {
            _state = new Suspenso(this);
        } // If user has no fines and is suspended, can return to active
        else if (_fines == 0 && isSuspended() && _activeLateRequests == 0) {
            _state = new Activo(this);
        }
    }

    protected String state() {
        return _state.toString();
    }

    protected String behaviour() {
        return _behaviour.toString();
    }

    public void updateFines(int change) {
        if (_fines + change >= 0) {
            _fines += change;
        }
    }

    @Override
    public String toString() {
        String fineDisplay = "";
        if (isSuspended()) {
            fineDisplay = " - EUR " + _fines;
        }
        // Format: "<id> - <name> - <email> - <behaviour> - <state> - [EUR: <fines>]"
        return String.format("%d - %s - %s - %s - %s%s", _idUser, _name, _email, behaviour(), state(), fineDisplay);
    }

}
