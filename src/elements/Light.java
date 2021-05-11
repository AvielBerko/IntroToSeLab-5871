package elements;

import primitives.Color;

/**
 * Light object with intensity.
 */
class Light {
	/**
	 * Intensity of light color
	 */
	final protected Color _intensity;

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
