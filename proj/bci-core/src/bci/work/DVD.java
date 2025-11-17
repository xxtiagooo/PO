package bci.work;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class DVD extends Work implements Serializable {

    @Serial
    private static final long serialVersionUID = 202507171006L;

    private final String _IGAC;
    private final Creator _director;

    public DVD(int idWork, String... fields) {
        // fields: [title, director, price, type, IGAC, inventory]
        super(idWork, fields[0], Integer.parseInt(fields[2]), fields[3], Integer.parseInt(fields[5]));
        _IGAC = fields[4];
        String director = fields[1];
        _director = new Creator(director.trim());
    }

    public String getIGAC() {
        return _IGAC;
    }

    @Override
    public List<Creator> getCreators() {
        return List.of(_director);
    }

    @Override
    public String toString() {
        // Format: "<id> - <available> de <inventory> - <Type> - <Title> - <Price> - <Type> - <Director> - <IGAC>"
        return String.format("%s - %s - %s - %d - %s - %s - %s",
                super.toString(),
                "DVD",
                getTitle(),
                getPrice(),
                typeToString(),
                _director.getId(),
                _IGAC);
    }
}
