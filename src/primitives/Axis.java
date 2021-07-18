package primitives;

import java.util.HashMap;
import java.util.Map;

public enum Axis {
    X(0), Y(1), Z(2);

    private final int _index;
    private final static Map<Integer, Axis> _map = new HashMap<>();

    static {
        for (Axis axis : Axis.values()) {
            _map.put(axis._index, axis);
        }
    }

    Axis(int index) {
        _index = index;
    }

    public int getIndex() {
        return _index;
    }

    public static Axis fromIndex(int index) {
        return _map.get(index);
    }

    public Axis getNext() {
        return _map.get((this.ordinal() + 1) % Axis.values().length);
    }
}
