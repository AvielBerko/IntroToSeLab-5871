package elements;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.random;

/**
 * Camera object in 3d scene for creating rays through pixels.
 */
public class Camera {
    /**
     * Camera's location.
     */
    private Point3D _p0;
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
    private Vector _vRight;
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
     * The number of rays sent by the camera.
     */
    private int _numOfRays = 10;

    /**
     * Constructs a camera with location, to and up vectors.
     * The right vector is being calculated by the to and up vectors.
     *
     * @param p0  The camera's location.
     * @param vTo The direction to where the camera is looking.
     * @param vUp The direction of the camera's upper direction.
     * @throws IllegalArgumentException When to and up vectors aren't orthogonal.
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
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
     *
     * @param width  The new view plane's width.
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
     *
     * @param distance The new distance between the camera and the view plane.
     * @return The camera itself.
     */
    public Camera setDistance(double distance) {
        if (isZero(distance)) {
            throw new IllegalArgumentException("distance can't be 0");
        }
        _distance = distance;
        return this;
    }

    /**
     * Chaining method for setting the starting point of the camera.
     *
     * @param x The x coordinate of the point.
     * @param y The x coordinate of the point.
     * @param z The x coordinate of the point.
     * @return The camera itself.
     */
    public Camera setP0(double x, double y, double z) {
        _p0 = new Point3D(x, y, z);
        return this;
    }

    /**
     * Chaining method for setting the  number of rays constructed by the camera.
     *
     * @param numOfRays The number of rays constructed.
     * @return The camera itself.
     */
    public Camera setNumOfRays(int numOfRays) {
        if (numOfRays <= 0) {
            throw new IllegalArgumentException("number of rays should be greater than 0");
        }

        _numOfRays = numOfRays;
        return this;
    }

    /**
     * Adds the given amount to the camera's position.
     *
     * @return the current camera.
     */
    public Camera move(Vector amount) {
        _p0 = _p0.add(amount);
        return this;
    }

    /**
     * Adds x, y, z to the camera's position.
     *
     * @return the current camera.
     */
    public Camera move(double x, double y, double z) {
        return move(new Vector(x, y, z));
    }

    /**
     * Rotates the camera around the axes with the given angles.
     *
     * @param amount vector of angles.
     * @return the current camera.
     */
    public Camera rotate(Vector amount) {
        return rotate(amount.getX(), amount.getY(), amount.getZ());
    }


    /**
     * Rotates the camera around the axes with the given angles.
     *
     * @param x angles to rotate around the x axis.
     * @param y angles to rotate around the y axis.
     * @param z angles to rotate around the z axis.
     * @return the current camera.
     */
    public Camera rotate(double x, double y, double z) {
        _vTo.rotateX(x).rotateY(y).rotateZ(z);
        _vUp.rotateX(x).rotateY(y).rotateZ(z);
        _vRight = _vTo.crossProduct(_vUp);

        return this;
    }

    /**
     * Constructs a ray through a given pixel on the view plane.
     *
     * @param nX Total number of pixels in the x dimension.
     * @param nY Total number of pixels in the y dimension.
     * @param j  The index of the pixel on the x dimension.
     * @param i  The index of the pixel on the y dimension.
     * @return A ray going through the given pixel.
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D pIJ = calculateCenterOfPixel(nX, nY, j, i);
        Vector vIJ = pIJ.subtract(_p0);
        return new Ray(_p0, vIJ);
    }

    /**
     * Calculates the center of a given pixel.
     * @param nX Total number of pixels in the x dimension.
     * @param nY Total number of pixels in the y dimension.
     * @param j  The index of the pixel on the x dimension.
     * @param i  The index of the pixel on the y dimension.
     * @return A center point of the pixel.
     */
    private Point3D calculateCenterOfPixel(int nX, int nY, int j, int i) {
        Point3D pC = _p0.add(_vTo.scale(_distance));

        double rX = _width / nX;
        double rY = _height / nY;

        Point3D pIJ = pC;
        double yI = -rY * (i - (nY - 1) / 2d);
        double xJ = rX * (j - (nX - 1) / 2d);

        // prevents creation of the zero vector.
        if (!isZero(xJ)) {
            pIJ = pIJ.add(_vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(_vUp.scale(yI));
        }
        return pIJ;
    }

    /**
     * Constructs a list of random rays to go through a given pixel.
     * @param nX Total number of pixels in the x dimension.
     * @param nY Total number of pixels in the y dimension.
     * @param j  The index of the pixel on the x dimension.
     * @param i  The index of the pixel on the y dimension.
     * @return A list of rays going through the pixel.
     */
    public List<Ray> constructRayPixelWithAA(int nX, int nY, int j, int i) {
        List<Ray> rays = new LinkedList<>();

        double rX = _width / nX;
        double rY = _height / nY;

        double randX, randY;

        Point3D pCenterPixel = calculateCenterOfPixel(nX, nY, j, i);
        rays.add(new Ray(_p0, pCenterPixel.subtract(_p0)));

        Point3D pInPixel;
        for (int k = 0; k < _numOfRays; k++) {
            randX = random(-rX / 2, rX / 2);
            randY = random(-rY / 2, rY / 2);
            pInPixel = new Point3D(pCenterPixel.getX() + randX, pCenterPixel.getY() + randY, pCenterPixel.getZ());
            rays.add(new Ray(_p0, pInPixel.subtract(_p0)));
        }
        return rays;
    }
}
