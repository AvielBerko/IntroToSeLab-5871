package elements;

import primitives.Color;

/**
 * Ambient Light Color
 */
public class AmbientLight extends Light {

    /**
     * Constructs an ambient light with its intensity and its constant for intensity.
     * @param iA intensity color
     * @param kA constant for intensity
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }

    /**
     * Constructs a black ambient light.
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}