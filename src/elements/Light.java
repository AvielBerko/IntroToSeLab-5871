package elements;

import primitives.Color;

class Light {
	/**
	 * intensity of light color
	 */
	final protected Color _intensity;

	Light(Color intensity) {
		_intensity = intensity;
	}

	/**
	 * get intensity color
	 * @return intensity
	 */
	public Color getIntensity() {
		return _intensity;
	}

}
