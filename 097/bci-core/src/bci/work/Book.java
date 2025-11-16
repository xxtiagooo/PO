package bci.work;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class Book extends Work implements Serializable {

    @Serial
    private static final long serialVersionUID = 202507171005L;

    private final String _ISBN;
    private final List<Creator> _authors;

    public Book(int idWork, String... fields) {
        // fields: [title, authors, price, type, isbn, inventory]
        super(idWork, fields[0], Integer.parseInt(fields[2]), fields[3], Integer.parseInt(fields[5]));
        _ISBN = fields[4];
        String authorsField = fields[1];
        _authors = List.of(authorsField.split(","))
                .stream()
                .map(String::trim)
                .map(Creator::new)
                .collect(Collectors.toList());
    }

    public String getISBN() {
        return _ISBN;
    }

    @Override
    public List<Creator> getCreators() {
        return _authors;
    }

    @Override
    public String toString() {
        // Format: "<id> - <available> de <inventory> - <Type> - <Title> - <Price> - <Type> - <Authors> - <ISBN>"
        return String.format("%s - %s - %s - %d - %s - %s - %s",
                super.toString(),
                "Livro",
                getTitle(),
                getPrice(),
                typeToString(),
                _authors.stream().map(Creator::getId).collect(Collectors.joining("; ")),
                _ISBN);
    }
}
