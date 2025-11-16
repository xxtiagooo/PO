package bci;

import bci.exceptions.RegistrationFailedException;
import bci.exceptions.UnrecognizedEntryException;
import bci.request.NeedToPayFinesException;
import bci.request.NoReferenceBooksRule;
import bci.request.Request;
import bci.request.RequestByPriceRule;
import bci.request.RequestLimitRule;
import bci.request.RequestRegistrationFailedException;
import bci.request.Rule3FailedException;
import bci.request.RuleVerification;
import bci.request.SingleRequestRule;
import bci.request.UserActiveRule;
import bci.request.WorkMustBeAvailableRule;
import bci.user.AvailableNotification;
import bci.user.NoFineToPayException;
import bci.user.Notification;
import bci.user.RequestNotification;
import bci.user.User;
import bci.work.Book;
import bci.work.Creator;
import bci.work.DVD;
import bci.work.Work;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Class that represents the library as a whole.
 */
class Library implements Serializable {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    private int _idWork; // Unique Work ID
    private int _idUser; // Unique User ID
    private int _date; // Current Date
    private int _idRequest; // Unique Request ID
    private String _filename; // associated file for import/export
    private transient boolean _changed; // whether the state has changed since last save

    // collections of works <idWork, Work>
    private Map<Integer, Work> _works = new TreeMap<>();

    // collections of users <idUser, User>
    private Map<Integer, User> _users = new TreeMap<>();

    // collections of requests by user <idRequest, Request>
    private Map<Integer, Request> _requests = new TreeMap<>();

    // collections of creators <idCreator, Creator>
    private Map<String, Creator> _creators = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    // collection of request rules 
    private List<RuleVerification> _rules = new ArrayList<>();

    // collection of notifications <idUser, List<Notification>>
    private Map<Integer, List<Notification>> _notifications = new TreeMap<>();

    // collection of users interested in return notifications <idWork, Set<idUser>>
    private Map<Integer, Set<Integer>> _returnInterests = new TreeMap<>();

    // collection of users interested in availability notifications <idWork, Set<idUser>>
    private Map<Integer, Set<Integer>> _availableInterests = new TreeMap<>();

    /**
     * Constructs a new Library instance.
     *
     * @param date Initial date of the system.
     * @param idWork Initial work ID.
     * @param idUser Initial user ID.
     * @param idRequest Initial request ID.
     */
    Library(int date, int idWork, int idUser, int idRequest) {
        _idWork = idWork;
        _idUser = idUser;
        _date = date;
        _idRequest = idRequest;
        _filename = null;
        _changed = false;

        // Initializes the rules
        _rules.add(new SingleRequestRule());
        _rules.add(new UserActiveRule());
        _rules.add(new WorkMustBeAvailableRule());
        _rules.add(new RequestLimitRule());
        _rules.add(new NoReferenceBooksRule());
        _rules.add(new RequestByPriceRule());
    }

    // getters 
    public String getFilename() {
        return _filename;
    }

    public boolean changed() {
        return _changed;
    }

    public int nextIdWork() {
        return _idWork++;
    }

    public int nextIdUser() {
        return _idUser++;
    }

    public int getDate() {
        return _date;
    }

    public int nextIdRequest() {
        return _idRequest++;
    }

    public User getUser(Integer id) {
        return _users.get(id);
    }

    public Work getWork(Integer id) {
        return _works.get(id);
    }

    public int getCurrentRequestCount(User user) {
        return user.getActiveRequestCount();
    }

    /**
     * Returns the number of active (not returned) requests for the given user
     * id.
     *
     * @param idUser user identifier
     * @return number of active requests
     */
    public int getActiveRequestCountByUserId(int idUser) {
        User user = _users.get(idUser);
        if (user == null) {
            return 0;
        }
        return user.getActiveRequestCount();
    }

    /**
     * Returns the number of requests for the given user id that are currently
     * late. A request is considered late if it's not returned and its due date
     * is before the current date.
     *
     * @param idUser user identifier
     * @return number of late requests
     */
    public int getLateRequestCountByUserId(int idUser) {
        int late = 0;
        for (Request request : _requests.values()) {
            if (request.getIdUser() == idUser && !request.isReturned()) {
                if (request.getDueDate() < _date) {
                    late++;
                }
            }
        }
        return late;
    }

    public Creator getCreator(String name) {
        if (name == null) {
            return null;
        }
        return _creators.get(name.trim());
    }

    public Set<Integer> getAvailableInterestedUsers(int idWork) {
        Set<Integer> userIds = _availableInterests.get(idWork);
        if (userIds == null) {
            return new TreeSet<>();
        }
        return userIds;
    }

    public Set<Integer> getReturnInterestedUsers(int idWork) {
        Set<Integer> userIds = _returnInterests.get(idWork);
        if (userIds == null) {
            return new TreeSet<>();
        }
        return userIds;
    }

    public boolean wasUnavailable(int idWork) {
        Work work = _works.get(idWork);
        if (work == null) {
            return false;
        }
        return work.getAvailable() == 0;
    }

    public void updateInventory(int idWork, int change) throws IllegalArgumentException {
        Work work = _works.get(idWork);
        if (work == null) {
            throw new IllegalArgumentException("no such work");
        }
        work.updateInventory(change);
        if (work.getInventory() == 0) {
            _works.remove(idWork);
            for (Creator creator : work.getCreators()) {
                String id = creator.getId();
                Creator existing = _creators.get(id);
                if (existing != null) {
                    existing.removeWork(work);
                    if (existing.getWorks().isEmpty()) {
                        _creators.remove(id);
                    }
                }
            }
        }
    }

    public int getUserFines(int idUser) {
        User user = _users.get(idUser);
        if (user == null) {
            return 0;
        }
        return user.getFines();
    }

    /**
     * Advances the current system date by the specified number of days.
     *
     * @param days Number of days to advance.
     */
    public void advanceDate(int days) {
        if (days > 0) {
            _date += days;
        }
        checkDueDate();
    }

    public void checkDueDate() {
        for (Request request : _requests.values()) {
            if (!request.isReturned()) {
                int previous_daysPastDue = request.getDaysPastDue();
                request.calculateDaysPastDue(_date);
                if (request.getDaysPastDue() > 0 && previous_daysPastDue == 0) {
                    // late delivery - work is now overdue
                    User user = request.getUser();
                    user.penalizeForLateDelivery();  // Suspend the user for overdue work
                    user.incrementActiveLateRequests();
                }
            }
        }
    }

    public void setFilename(String filename) {
        _filename = filename;
        _changed = true;
    }

    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * Adds a work to the library and registers its creators.
     *
     * @param work The work to add.
     */
    public void addWork(Work work) {
        for (Creator creator : work.getCreators()) {
            String id = creator.getId();
            if (id == null || id.isEmpty()) {
                continue;
            }
            Creator existing = _creators.get(id);
            if (existing == null) {
                _creators.put(id, creator);
                existing = creator;
            }
            existing.addWork(work);
        }
    }

    // display methods
    public String displayUser(int idUser) throws IllegalArgumentException {
        User user = getUser(idUser);
        if (user == null) {
            throw new IllegalArgumentException("no such user");
        }
        return user.toString();
    }

    public String displayUsers() {
        return _users.values().stream()
                .sorted(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparingInt(User::getIdUser)) // se tiverem o mesmo nome, ordena por ID
                .map(User::toString)
                .collect(Collectors.joining("\n"));
    }

    public String displayWork(int idWork) throws IllegalArgumentException {
        Work work = _works.get(idWork);
        if (work == null) {
            throw new IllegalArgumentException("no such work");
        }
        return work.toString();
    }

    public String displayWorks() {
        return _works.values().stream()
                .map(Work::toString)
                .collect(Collectors.joining("\n"));
    }

    public String displayWorksByIds(Set<Integer> ids) {
        return ids.stream()
                .map(_works::get)
                .filter(work -> work != null)
                .map(Work::toString)
                .collect(Collectors.joining("\n"));
    }

    public String displayWorksByCreator(String idCreator) throws IllegalArgumentException {
        Creator creator = _creators.get(idCreator);
        if (creator == null) {
            throw new IllegalArgumentException("no such creator");
        }
        List<Work> works = creator.getWorks();
        if (works.isEmpty()) {
            throw new IllegalArgumentException("no such creator");
        }
        // Sort works by title 
        works.sort(Comparator.comparing(Work::getTitle, String.CASE_INSENSITIVE_ORDER));
        return works.stream()
                .map(Work::toString)
                .collect(Collectors.joining("\n"));
    }

    // Returns the notifications of a user
    public String displayUserNotifications(int idUser) throws IllegalArgumentException {
        User user = _users.get(idUser);
        if (user == null) {
            throw new IllegalArgumentException("no such user");
        }
        List<Notification> userNotifications = _notifications.get(idUser);
        if (userNotifications == null || userNotifications.isEmpty()) {
            return "";
        }
        String message = userNotifications.stream()
                .map(Notification::toString)
                .collect(Collectors.joining("\n"));
        userNotifications.clear(); // clear notifications after displaying
        setChanged(true);
        return message;
    }

    /**
     * Registers a generic entry based on the type field.
     *
     * @param fields Fields of the entry (first field is the type).
     * @throws UnrecognizedEntryException If the entry cannot be recognized.
     * @throws RegistrationFailedException If registration fails.
     */
    public void registerEntry(String... fields) throws UnrecognizedEntryException, RegistrationFailedException {
        String type = fields[0];
        switch (type) {
            case "BOOK", "DVD" ->
                registerWork(fields);
            case "USER" -> {
                List<String> fieldList = new ArrayList<>(List.of(fields));
                fieldList.remove(0);
                registerUser(fieldList.toArray(String[]::new));
            }
        }
    }

    public void registerWork(String... fields) throws UnrecognizedEntryException, RegistrationFailedException {
        if (fields.length != 7) {
            throw new UnrecognizedEntryException("tamanho inválido");
        }
        int id = nextIdWork();
        String type = fields[0];
        List<String> fieldList = new ArrayList<>(List.of(fields));
        fieldList.remove(0); // remover o tipo
        switch (type) {
            case "BOOK" ->
                registerBook(id, fieldList.toArray(String[]::new));
            case "DVD" ->
                registerDVD(id, fieldList.toArray(String[]::new));
        }
    }

    public int registerBook(int idWork, String... fields) throws RegistrationFailedException {
        if (fields.length != 6 || (fields[4].length() != 10 && fields[4].length() != 13)) {
            throw new RegistrationFailedException("BOOK");
        }
        Work work = new Book(idWork, fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
        _works.put(idWork, work);
        addWork(work);
        setChanged(true);
        return idWork;
    }

    public int registerDVD(int idWork, String... fields) throws RegistrationFailedException {
        if (fields.length != 6 || _works.containsKey(idWork)) {
            throw new RegistrationFailedException("DVD");
        }
        Work work = new DVD(idWork, fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
        _works.put(idWork, work);
        addWork(work);
        setChanged(true);
        return idWork;

    }

    public int registerUser(String... fields) throws UnrecognizedEntryException, RegistrationFailedException {
        if (fields.length != 2) {
            throw new UnrecognizedEntryException("invalid size");
        }
        if (fields[0].trim().isEmpty() || fields[1].trim().isEmpty()) {
            throw new RegistrationFailedException("USER");
        }
        int idUser = nextIdUser();
        User user = new User(idUser, fields[0], fields[1]);
        _users.put(idUser, user);
        setChanged(true);
        return idUser;
    }

    public int registerRequest(int idUser, int idWork) throws Rule3FailedException, RequestRegistrationFailedException {
        User user = _users.get(idUser);
        Work work = _works.get(idWork);

        if (user == null) {
            throw new IllegalArgumentException("no such user");
        }
        if (work == null) {
            throw new IllegalArgumentException("no such work");
        }
        VerificationResult result = verify(user, work);

        switch (result._status) {
            case FAILURE_WITH_NOTIFICATION ->
                throw new Rule3FailedException(work.getTitle(), user.getName());
            case FAILURE ->
                throw new RequestRegistrationFailedException(work.getTitle(), user.getName(), result._idRule);
        }

        int id = nextIdRequest();
        int dueDate = getDate() + user.getRequestTime(work); // calcular dia limite
        work.updateAvailable(-1);
        Request request = new Request(id, user, work, dueDate);

        Set<Integer> availableSubscribers = _availableInterests.get(idWork);
        Set<Integer> returnSubscribers = _returnInterests.get(idWork);
        if (returnSubscribers != null && returnSubscribers.contains(idUser)) {
            // unsubscribe user after requesting the work that he is interested in
            returnSubscribers.remove(idUser);
        }
        if (availableSubscribers != null && availableSubscribers.contains(idUser)) {
            // unsubscribe user after requesting the work that he is interested in
            availableSubscribers.remove(idUser);
        }

        if (_requests.containsKey(id)) {
            throw new RequestRegistrationFailedException(_requests.get(id).getTitle(), _requests.get(id).getUser().getName(), 0);
        }
        _requests.put(id, request);
        user.addRequest(request);
        setChanged(true);
        return dueDate;
    }

    public void returnWork(int idWork, int idUser) throws NeedToPayFinesException, IllegalArgumentException {
        // Validate user and work exist first
        User user = _users.get(idUser);
        if (user == null) {
            throw new IllegalArgumentException("no such user");
        }
        Work work = _works.get(idWork);
        if (work == null) {
            throw new IllegalArgumentException("no such work");
        }
        
        // Then look for the request
        Request request = _requests.values().stream()
                .filter(r -> r.getIdWork() == idWork && r.getIdUser() == idUser && !r.isReturned())
                .findFirst()
                .orElse(null);
        if (request == null) {
            throw new IllegalArgumentException("wrong user");
        }
        // register if the work was unavailable before return
        boolean wasUnavailable = wasUnavailable(idWork);
        request.returnWork();
        user.removeRequest(request);

        // if work became available, notify subscribers
        if (wasUnavailable && work.getAvailable() > 0) {
            Set<Integer> subscribers = _availableInterests.get(idWork);
            if (subscribers != null && !subscribers.isEmpty()) {
                for (Integer idSubscriber : subscribers) {
                    User subscriber = _users.get(idSubscriber);
                    if (subscriber != null) {
                        Notification note = new AvailableNotification(work);
                        _notifications.putIfAbsent(idSubscriber, new ArrayList<>());
                        _notifications.get(idSubscriber).add(note);
                    }
                }
                // clear subscriptions after notifying
                _availableInterests.remove(idWork);
            }
        }

        if (request.getUser().getFines() > 0) {
            throw new NeedToPayFinesException(idUser, request.getUser().getFines());
        }
        setChanged(true);
    }

    public void payFine(int idUser) throws NoFineToPayException, IllegalArgumentException {
        User user = _users.get(idUser);
        if (user == null) {
            throw new IllegalArgumentException("no such user");
        }
        if (user.getFines() == 0) {
            throw new NoFineToPayException();
        }
        user.payFine(user.getFines());
        user.ensureCorrectState();
        setChanged(true);
    }

    public void addUserNotification(int idUser, int idWork, String notification) throws IllegalArgumentException {
        User user = _users.get(idUser);
        Work work = _works.get(idWork);
        if (user == null) {
            throw new IllegalArgumentException("no such user");
        }
        if (work == null) {
            throw new IllegalArgumentException("no such work");
        }
        switch (notification) {
            case "REQUEST" -> {
                Notification note = new RequestNotification(work);
                _notifications.putIfAbsent(idUser, new ArrayList<>());
                _notifications.get(idUser).add(note);
            }
            case "AVAILABLE" -> {
                Notification note = new AvailableNotification(work);
                _notifications.putIfAbsent(idUser, new ArrayList<>());
                _notifications.get(idUser).add(note);
            }
        }
        setChanged(true);
    }

    public void subscribeReturnNotifications(int idUser, int idWork) {
        Work work = _works.get(idWork);
        Set<Integer> userIds = _returnInterests.get(idWork);
        if (userIds == null || userIds.isEmpty()) {
            // no interested users
            return;
        }
        for (Integer idSubscriber : userIds) {
            User user = _users.get(idSubscriber);
            if (user != null) {
                Notification note = new AvailableNotification(work);
                _notifications.putIfAbsent(idSubscriber, new ArrayList<>());
                _notifications.get(idSubscriber).add(note);
            }
        }
        _returnInterests.remove(idWork); // already notified all interested users
        setChanged(true);
    }

    public void subscribeAvailableNotifications(int idUser, int idWork) {
        Set<Integer> userIds = _availableInterests.get(idWork);
        if (userIds == null) {
            userIds = new TreeSet<>();
            _availableInterests.put(idWork, userIds);
        }
        userIds.add(idUser);
        /*
        Notification note = new AvailableNotification(_works.get(idWork));
        _notifications.putIfAbsent(idUser, new ArrayList<>());
        _notifications.get(idUser).add(note);
         */
        setChanged(true);
    }

    /**
     * Inner class to hold verification result with status and rule ID.
     */
    private class VerificationResult {

        final RuleVerification.Status _status;
        final int _idRule;

        VerificationResult(RuleVerification.Status status, int idRule) {
            _status = status;
            _idRule = idRule;
        }
    }

    /**
     * Verifies if a user can request a work, applying all the rules.
     *
     * @param user The user making the request
     * @param work The work to be requested
     * @return The verification result with status and ID of the failed rule
     */
    private VerificationResult verify(User user, Work work) {
        int idRule = 0;
        for (RuleVerification rule : _rules) {
            idRule++;
            RuleVerification.Status status = rule.verify(work, user);
            if (status != RuleVerification.Status.SUCCESS) {
                return new VerificationResult(status, idRule);
            }
        }
        return new VerificationResult(RuleVerification.Status.SUCCESS, 0);
    }

    /**
     * Reads a text file and imports users and works into the library.
     *
     * @param filename Name of the input file.
     * @throws UnrecognizedEntryException If any entry cannot be parsed.
     * @throws IOException If the file cannot be read.
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException, RegistrationFailedException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(":");
            registerEntry(fields);
        }
        setChanged(true);
        reader.close();
    }

    public String search(String searchTerm) {
        Set<Integer> works = new TreeSet<>();
        works.addAll(searchByCreator(searchTerm));
        works.addAll(searchByTitle(searchTerm));
        return displayWorksByIds(works);
    }

    private Set<Integer> searchByTitle(String searchTerm) {
        Set<Integer> works = new TreeSet<>();
        // no difference between uppercase and lowercase
        String lowerCaseSearchTerm = searchTerm.toLowerCase();
        for (Work work : _works.values()) {
            if (work.getTitle().toLowerCase().contains(lowerCaseSearchTerm)) {
                works.add(work.getIdWork());
            }
        }
        return works;
    }

    private Set<Integer> searchByCreator(String searchTerm) {
        Set<Integer> works = new TreeSet<>();
        String lowerCaseSearchTerm = searchTerm.toLowerCase();
        for (Creator creator : _creators.values()) {
            if (creator.getId().toLowerCase().contains(lowerCaseSearchTerm)) {
                List<Work> worksByCreator = creator.getWorks();
                for (Work work : worksByCreator) {
                    works.add(work.getIdWork());
                }
            }
        }
        return works;
    }

}
