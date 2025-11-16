package bci;

import bci.exceptions.ImportFileException;
import bci.exceptions.MissingFileAssociationException;
import bci.exceptions.RegistrationFailedException;
import bci.exceptions.UnavailableFileException;
import bci.exceptions.UnrecognizedEntryException;
import bci.request.NeedToPayFinesException;
import bci.request.RequestRegistrationFailedException;
import bci.request.Rule3FailedException;
import bci.user.NoFineToPayException;
import bci.user.User;
import bci.work.Creator;
import bci.work.Work;
import java.io.*;

/**
 * The façade class.
 */
public class LibraryManager {

    private static final int DATA_INICIAL = 1;
    private static final int IDWORK_INICIAL = 1;
    private static final int IDUSER_INICIAL = 1;
    private static final int IDREQUEST_INICIAL = 1;

    /**
     * The object doing all the actual work.
     */
    private Library _library = new Library(DATA_INICIAL, IDWORK_INICIAL, IDUSER_INICIAL, IDREQUEST_INICIAL);

    public LibraryManager() {
    }

    public String getFilename() {
        return _library.getFilename();
    }

    public void setFilename(String filename) {
        _library.setFilename(filename);
    }

    public void setChanged(boolean changed) {
        _library.setChanged(changed);
    }

    public boolean changed() {
        return _library.changed();
    }

    public int getDate() {
        return _library.getDate();
    }

    public void advanceDate(int days) {
        _library.advanceDate(days);
    }

    public User getUser(int id) {
        return _library.getUser(id);
    }

    public Work getWork(int id) {
        return _library.getWork(id);
    }

    public int getCurrentRequestCount(User user) {
        return _library.getCurrentRequestCount(user);
    }

    public int getActiveRequestCountByUserId(int idUser) {
        return _library.getActiveRequestCountByUserId(idUser);
    }

    public int getLateRequestCountByUserId(int idUser) {
        return _library.getLateRequestCountByUserId(idUser);
    }

    public int getUserFines(int idUser) {
        return _library.getUserFines(idUser);
    }

    public void payFine(int idUser) throws NoFineToPayException {
        _library.payFine(idUser);
    }

    /**
     * Saves the library state to its associated file.
     *
     * @throws MissingFileAssociationException if no file is associated yet
     * @throws IOException if an I/O error occurs during saving
     */
    public void save() throws MissingFileAssociationException, IOException {
        String filename = _library.getFilename();
        if (filename == null) {
            throw new MissingFileAssociationException();
        }
        try (var oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            oos.writeObject(_library);
            _library.setChanged(false);
        }
    }

    /**
     * Saves the library state to a new file and updates the associated
     * filename.
     *
     * @param filename Name of the file to save to
     * @throws MissingFileAssociationException if filename is null (never occurs
     * here)
     * @throws IOException if an I/O error occurs
     */
    public void saveAs(String filename) throws MissingFileAssociationException, IOException {
        _library.setFilename(filename);
        save();
    }

    /**
     * Loads the library state from a serialized file.
     *
     * @param filename Name of the file to load
     * @throws UnavailableFileException if the file cannot be read or
     * deserialized
     */
    public void load(String filename) throws UnavailableFileException {
        try (var ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            _library = (Library) ois.readObject();
            _library.setFilename(filename);
            _library.setChanged(false);
        } catch (IOException | ClassNotFoundException e) {
            throw new UnavailableFileException(filename);
        }
    }

    /**
     * Imports users and works from a structured text file.
     *
     * @param filename Name of the text input file
     * @throws ImportFileException if any error occurs during import
     */
    public void importFile(String filename) throws ImportFileException {
        try {
            _library.importFile(filename);
        } catch (IOException | UnrecognizedEntryException | RegistrationFailedException e) {
            throw new ImportFileException(filename, e);
        }
    }

    public String displayUser(int idUser) throws IllegalArgumentException {
        User user = getUser(idUser);
        if (user == null) {
            throw new IllegalArgumentException("no such user");
        }
        return _library.displayUser(idUser);
    }

    public String displayUsers() throws IllegalArgumentException {
        return _library.displayUsers();
    }

    public String displayWork(int idWork) throws IllegalArgumentException {
        Work work = getWork(idWork);
        if (work == null) {
            throw new IllegalArgumentException("no such work");
        }
        return _library.displayWork(idWork);
    }

    public String displayWorks() {
        return _library.displayWorks();
    }

    public String displayWorksByCreator(String name) throws IllegalArgumentException {
        Creator creator = _library.getCreator(name);
        if (creator == null) {
            throw new IllegalArgumentException("no such creator");
        }
        return _library.displayWorksByCreator(name);
    }

    public String displayUserNotifications(int idUser) {
        return _library.displayUserNotifications(idUser);
    }

    public void subscribeAvailableNotifications(int idUser, int idWork) {
        _library.subscribeAvailableNotifications(idUser, idWork);
    }

    public void subscribeReturnNotifications(int idUser, int idWork) {
        _library.subscribeReturnNotifications(idUser, idWork);
    }

    public void updateInventory(int idWork, int change) throws IllegalArgumentException {
        _library.updateInventory(idWork, change);
    }

    public int registerUser(String name, String email) throws UnrecognizedEntryException, RegistrationFailedException {
        return _library.registerUser(name, email);
    }

    public int registerBook(int id, String... fields) throws RegistrationFailedException {
        return _library.registerBook(id, fields);
    }

    public int registerDVD(int id, String... fields) throws RegistrationFailedException {
        return _library.registerDVD(id, fields);
    }

    public int registerRequest(int idUser, int idWork) throws Rule3FailedException, RequestRegistrationFailedException {
        return _library.registerRequest(idUser, idWork);
    }

    public void returnWork(int idWork, int idUser) throws NeedToPayFinesException, IllegalArgumentException {
        _library.returnWork(idWork, idUser);
    }

    public String search(String searchTerm) {
        return _library.search(searchTerm);
    }

}
