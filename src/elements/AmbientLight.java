package elements;

import primitives.Color;

/**
 * Ambient Light Color
 */
public class AmbientLight {
    /**
     * intensity of ambient light color
     */
    final private Color _intensity;

    /**
     * Constructor
     * @param iA intensity color
     * @param kA constant for intensity
     */
    public AmbientLight(Color iA, double kA) {
        _intensity = iA.scale(kA);
    }

    /**
     * get intensity color
     * @return intensity
     */
    public Color getIntensity() {
        return _intensity;
    }

}