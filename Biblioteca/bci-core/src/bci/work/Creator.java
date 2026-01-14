package bci.work;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Creator implements Serializable {

    @Serial
    private static final long serialVersionUID = 202507171007L;

    private final String _id;
    private final List<Work> _works = new ArrayList<>();

    public Creator(String name) {
        _id = name.trim();
    }

    public String getId() {
        return _id;
    }

    public void addWork(Work work) {
        if (!_works.contains(work)) {
            _works.add(work);
        }
    }

    public void removeWork(Work work) {
        _works.remove(work);
    }

    public List<Work> getWorks() {
        return new ArrayList<>(_works);
    }

    @Override
    public String toString() {
        return _id;
    }
}
