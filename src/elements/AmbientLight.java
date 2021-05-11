package elements;

import primitives.Color;

/**
 * Ambient Light Color
 */
public class AmbientLight extends Light {

    /**
     * Constructor
     * @param iA intensity color
     * @param kA constant for intensity
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }

    public AmbientLight() {
        super(Color.BLACK);
    }
}