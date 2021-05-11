package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Light with a position.
 */
public class PointLight extends Light implements LightSource {

	protected final Point3D _position;

	private double _kC = 1;
	private double _kL = 0;
	private double _kQ = 0;

	/**
	 * Constructs a point light with an intensity and position
	 * @param intensity the intensity of the created light
	 * @param position the position of the created light
	 */
	PointLight(Color intensity, Point3D position) {
		super(intensity);
		_position = position;
	}

	/**
	 * Chaining method for setting k constant.
	 * @param kC the new kC
	 * @return the current point light
	 */
	public PointLight setKc(double kC) {
		_kC = kC;
		return this;
	}

	/**
	 * Chaining method for setting k linear.
	 * @param kL the new kL
	 * @return the current point light
	 */
	public PointLight setKl(double kL) {
		_kL = kL;
		return this;
	}

	/**
	 * Chaining method for setting k quadratic.
	 * @param kQ the new kQ
	 * @return the current point light
	 */
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
