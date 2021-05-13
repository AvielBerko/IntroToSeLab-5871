package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Spotlight at a position.
 */
public class SpotLight extends PointLight {

	private final Vector _direction;

	/**
	 * Constructs a spotlight with intensity, position and direction.
	 * @param intensity the intensity of the created spotlight
	 * @param position the position of the created spotlight
	 * @param direction the direction of the spotlight
	 */
	SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		_direction = direction.normalized();
	}

	@Override
	public Color getIntensity(Point3D p) {
		return super.getIntensity(p).scale(Math.max(0, _direction.dotProduct(getL(p))));
	}

	@Override
	public Vector getL(Point3D p) {
		return super.getL(p);
	}
}
