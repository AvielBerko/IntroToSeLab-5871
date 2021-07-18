package elements;

import primitives.Color;

/**
 * Light object with intensity
 */
abstract class Light {
	/**
	 * Intensity of light color
	 */
	final protected Color _intensity;

	/**
	 * Constructor, builds a new light with a given intensity
	 * @param intensity the intensity of the light
	 */
	Light(Color intensity) {
		_intensity = intensity;
	}

	/**
	 * Get intensity color
	 * @return intensity
	 */
	public Color getIntensity() {
		return _intensity;
	}

}
