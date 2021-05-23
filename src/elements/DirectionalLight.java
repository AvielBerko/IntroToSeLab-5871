package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Light from a direction.
 */
public class DirectionalLight extends Light implements LightSource {
	private final Vector _direction;

	/**
	 * Constructs a directional light with intensity and direction.
	 * @param intensity the intensity of the created directional light
	 * @param direction the direction of the crated directional light
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		_direction = direction.normalized();
	}

	@Override
	public Color getIntensity(Point3D p) {
		return _intensity;
	}

	@Override
	public Vector getL(Point3D p) {
		return _direction;
	}

	@Override
	public double getDistance(Point3D point) {
		return Double.POSITIVE_INFINITY;
	}
}
