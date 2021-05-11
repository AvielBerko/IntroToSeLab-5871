package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
	private final Vector _direction;

	DirectionalLight(Color intensity, Vector direction) {
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
}
