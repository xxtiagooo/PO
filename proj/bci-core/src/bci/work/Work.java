package bci.work;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public abstract class Work implements Serializable {

    private final int _idWork;
    private int _available;
    private int _inventory;
    private int _price;
    private final String _title;
    private final Type _type;

    public enum Type {
        FICTION, REFERENCE, SCITECH
    };

    @Serial
    private static final long serialVersionUID = 202507171004L;

    protected Work(int idWork, String title, int price, String type, int inventory) {
        _idWork = idWork;
        _title = title.trim();
        _price = price;
        _type = stringToType(type);
        _inventory = _available = inventory;
    }

    // getters
    public int getIdWork() {
        return _idWork;
    }

    public int getPrice() {
        return _price;
    }

    public int getAvailable() {
        return _available;
    }

    public int getInventory() {
        return _inventory;
    }

    public abstract List<Creator> getCreators();

    public Type getType() {
        return _type;
    }

    public String getTitle() {
        return _title;
    }

    //setters
    public void setPrice(int price) {
        _price = price;
    }

    public void updateInventory(int change) throws IllegalArgumentException {
        if (_inventory + change < 0 || _available + change < 0) {
            throw new IllegalArgumentException("inventory has to be positive");
        }
        _inventory += change;
        updateAvailable(change);
    }

    public void updateAvailable(int change) throws IllegalArgumentException {
        if (_available + change < 0) {
            throw new IllegalArgumentException("available has to be non-negative");
        }
        _available += change;
    }

    private Type stringToType(String type) {
        return switch (type.toUpperCase().trim()) {
            case "FICTION" ->
                Type.FICTION;
            case "REFERENCE" ->
                Type.REFERENCE;
            case "SCITECH" ->
                Type.SCITECH;
            default ->
                null;
        };
    }

    protected String typeToString() {
        return switch (_type) {
            case FICTION ->
                "Ficção";
            case REFERENCE ->
                "Referência";
            case SCITECH ->
                "Técnica e Científica";
            default ->
                "";
        };
    }

    @Override
    public String toString() {
        // Format: "<id> - <available> de <inventory>"
        return String.format("%d - %d de %d", _idWork, _available, _inventory);
    }

}
