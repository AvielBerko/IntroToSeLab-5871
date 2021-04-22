package elements;

import primitives.*;
import static primitives.Util.isZero;

/**
 * Camera object in 3d scene for creating rays through pixels.
 */
public class Camera {
	/**
	 * Camera's location.
	 */
	private final Point3D _p0;
	/**
	 * Camera's upper direction.
	 */
	private final Vector _vUp;
	/**
	 * Camera's forward direction.
	 */
	private final Vector _vTo;
	/**
	 * Camera's right direction
	 */
	private final Vector _vRight;
	/**
	 * View plane's width.
	 */
	private double _width;
	/**
	 * View plane's height.
	 */
	private double _height;
	/**
	 * The distance between the camera and the view plane.
	 */
	private double _distance;

	/**
	 * Constructs a camera with location, to and up vectors.
	 * The right vector is being calculated by the to and up vectors.
	 * @param p0 The camera's location.
	 * @param vTo The direction to where the camera is looking.
	 * @param vUp The direction of the camera's upper direction.
	 * @exception IllegalArgumentException When to and up vectors aren't orthogonal.
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp) {
		if(!isZero(vUp.dotProduct(vTo))){
			throw new IllegalArgumentException("Up vector isn't orthogonal with To vector");
		}

		_p0 = p0;
		_vUp = vUp.normalized();
		_vTo = vTo.normalized();
		_vRight = _vTo.crossProduct(_vUp);
	}

	/**
	 * Returns the camera location.
	 */
	public Point3D getP0() {
		return _p0;
	}

	/**
	 * Returns the camera's upper direction.
	 */
	public Vector getVUp() {
		return _vUp;
	}

	/**
	 * Returns the camera's forward direction.
	 */
	public Vector getVTo() {
		return _vTo;
	}

	/**
	 * Returns the camera's right direction.
	 */
	public Vector getVRight() {
		return _vRight;
	}

	/**
	 * Returns the view plane's width.
	 */
	public double getWidth() {
		return _width;
	}

	/**
	 * Returns the view plane's height.
	 */
	public double getHeight() {
		return _height;
	}

	/**
	 * Returns the distance between the camera and the view plane.
	 */
	public double getDistance() {
		return _distance;
	}

	/**
	 * Chaining method for setting the view plane's size.
	 * @param width The new view plane's width.
	 * @param height The new view plane's height.
	 * @return The camera itself.
	 */
	public Camera setViewPlaneSize(double width, double height) {
		_height = height;
		_width = width;
		return this;
	}

	/**
	 * Chaining method for setting the distance between the camera and the view plane.
	 * @param distance The new distance between the camera and the view plane.
	 * @return The camera itself.
	 */
	public Camera setDistance(double distance) {
		_distance = distance;
		return this;
	}

	/**
	 * Constructs a ray through a given pixel on the view plane.
	 * @param nX Total number of pixels in the x dimension.
	 * @param nY Total number of pixels in the y dimension.
	 * @param j  The index of the pixel on the x dimension.
	 * @param i	 The index of the pixel on the y dimension.
	 * @return A ray going through the given pixel.
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i){

		Point3D pC = _p0.add(_vTo.scale(_distance));

		double rX = _width / nX;
		double rY = _height / nY;

		Point3D pIJ = pC;
		double yI = -rY * (i - (nY - 1) / 2d);
		double xJ = rX * (j - (nX - 1) / 2d);

		// prevents creation of the zero vector.
		if(!isZero(xJ)){
			pIJ = pIJ.add(_vRight.scale(xJ));
		}
		if(!isZero(yI)){
			pIJ = pIJ.add(_vUp.scale(yI));
		}
		Vector vIJ = pIJ.subtract(_p0);

		return new Ray(_p0, vIJ);
	}
}
