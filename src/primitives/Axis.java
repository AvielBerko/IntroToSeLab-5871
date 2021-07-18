package primitives;

import java.util.HashMap;
import java.util.Map;

/**
 * Axis Enum.
 * Has a functionality of getting the next axis
 * Saves a map from int to axis to translate ints to axes
 */
public enum Axis {
    X(0), Y(1), Z(2);
    private final int _index;
    private final static Map<Integer, Axis> _map = new HashMap<>();

     //Static constructor, sets the map with the axes and the fitting int
    static {
        for (Axis axis : Axis.values()) {
            _map.put(axis._index, axis);
        }
    }

    /**
     * Constructor an axis by a given index
     * @param index The index to construct an axis from
     */
    Axis(int index) {
        _index = index;
    }

    /**
     * Index Getter
     * @return The index
     */
    public int getIndex() {
        return _index;
    }

    /**
     * Gets an axis from a given index
     * @param index The index to translate to axis
     * @return The axis fitting with the index
     */
    public static Axis fromIndex(int index) {
        return _map.get(index);
    }

    /**
     * Gets the next axis (X->Y->Z)
     * @return The next axis
     */
    public Axis getNext() {
        return _map.get((this.ordinal() + 1) % Axis.values().length);
    }
}
