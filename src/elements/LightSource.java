package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Light from a source with intensity and vector l
 */
public interface LightSource {

	/**
	 * Gets the intensity of the light in a specific position
	 * @param p the position of the light collision
	 * @return the intensity color at the given position
	 */
	Color getIntensity(Point3D p);

	/**
	 * The vector l which is the vector between the source and the position
	 * @param p the position of the light collision
	 * @return the resulted vector
	 */
	Vector getL(Point3D p);

	/**
	 * The distance between the light source and a given point
	 * @param point the point to calculate the distance to
	 * @return the distance between the point and the light source (double)
	 */
	double getDistance(Point3D point);
}
