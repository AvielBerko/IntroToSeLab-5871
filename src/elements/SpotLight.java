package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {

	private final Vector _direction;

	SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		_direction = direction.normalized();
	}

	@Override
	public Color getIntensity(Point3D p) {
		Vector l = p.subtract(_position).normalize();
		return super.getIntensity(p).scale(Math.max(0, _direction.dotProduct(l)));
	}

	@Override
	public Vector getL(Point3D p) {
		return super.getL(p);
	}
}
