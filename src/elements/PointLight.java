package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

	protected final Point3D _position;

	private double _kC = 1;
	private double _kL = 0;
	private double _kQ = 0;

	PointLight(Color intensity, Point3D position) {
		super(intensity);
		_position = position;
	}

	public PointLight setKc(double kC) {
		_kC = kC;
		return this;
	}

	public PointLight setKl(double kL) {
		_kL = kL;
		return this;
	}

	public PointLight setKq(double kQ) {
		_kQ = kQ;
		return this;
	}

	@Override
	public Color getIntensity(Point3D p) {
		double d = p.distance(_position);
		double attenuator = 1d/(_kC + _kL * d + _kQ * d * d);
		return _intensity.scale(attenuator);
	}

	@Override
	public Vector getL(Point3D p) {
		return p.subtract(_position).normalize();
	}
}
