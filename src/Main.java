import geometries.Sphere;
import primitives.*;
import static java.lang.System.out;
import static primitives.Util.*;

// Aviel Berkowitz 211981105
// Meir  Klemfner  211954185

/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein
 */
public final class Main {

    /**
     * Main program to tests initial functionality of the 1st stage
     * 
     * @param args irrelevant here
     */
    public static void main(String[] args) {

        Sphere sphere = new Sphere(50, new Point3D(0,0,-50));
        Ray ray = new Ray(
                new Point3D(-18.77, 30.98, -15.39),
                new Vector(-0.29, -0.74, 0.6)
        );

        System.out.println(sphere.findIntersections(ray));
    }
}
