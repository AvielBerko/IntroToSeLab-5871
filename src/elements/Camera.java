package elements;

import primitives.*;
import static primitives.Util.isZero;

public class Camera {
	private final Point3D _p0;
	private final Vector _vUp;
	private final Vector _vTo;
	private final Vector _vRight;
	private double _width;
	private double _height;
	private double _distance;

	public Camera(Point3D p0, Vector vTo, Vector vUp) {
		if(!isZero(vUp.dotProduct(vTo))){
			throw new IllegalArgumentException("Up vector isn't orthogonal with To vector");
		}

		_p0 = p0;
		_vUp = vUp.normalized();
		_vTo = vTo.normalized();
		_vRight = _vTo.crossProduct(_vUp);
	}

	public Point3D getP0() {
		return _p0;
	}

	public Vector getVUp() {
		return _vUp;
	}

	public Vector getVTo() {
		return _vTo;
	}

	public Vector getVRight() {
		return _vRight;
	}

	public double getWidth() {
		return _width;
	}

	public double getHeight() {
		return _height;
	}

	public double getDistance() {
		return _distance;
	}


	// Chaining methods
	public Camera setViewPlaneSize(double width, double height) {
		_height = height;
		_width = width;
		return this;
	}

	public Camera setDistance(double distance) {
		_distance = distance;
		return this;
	}

	public Ray constructRayThroughPixel(int nX, int nY, int j, int i){

		Point3D Pc = _p0.add(_vTo.scale(_distance));

		double Rx = _width / nX;
		double Ry = _height / nY;

		Point3D Pij = Pc;
		double Yi = -Ry * (i - (nY - 1) / 2d);
		double Xj = Rx * (j - (nX - 1) / 2d);

		if(!isZero(Xj)){
			Pij = Pij.add(_vRight.scale(Xj));
		}
		if(!isZero(Yi)){
			Pij = Pij.add(_vUp.scale(Yi));
		}
		Vector Vij = Pij.subtract(_p0);

		return  new Ray(_p0, Vij);
	}
}
