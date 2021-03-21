package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Triangle} class.
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#Triangle(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Triangle(
                    new Point3D(0, 0, 1),
                    new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0)
            );
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct triangle");
        }

        // =============== Boundary Values Tests ==================
        // TC02: Test when point a is equals to point b.
        try {
            new Triangle(
                    new Point3D(1, 0, 0),
                    new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0)
            );
            fail("Constructed a triangle while point a and b are equals");
        } catch (IllegalArgumentException ignored) {}

        // TC03: Test when point a is equals to point c.
        try {
            new Triangle(
                    new Point3D(0, 0, 1),
                    new Point3D(1, 0, 0),
                    new Point3D(0, 0, 1)
            );
            fail("Constructed a triangle while point a and c are equals");
        } catch (IllegalArgumentException ignored) {}

        // TC04: Test when point b is equals to point c.
        try {
            new Triangle(
                    new Point3D(0, 0, 1),
                    new Point3D(0, 1, 0),
                    new Point3D(0, 1, 0)
            );
            fail("Constructed a triangle while point b and c are equals");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(
                new Point3D(0, 0, 1),
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0)
        );

        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), tr.getNormal(new Point3D(0, 0, 1)), "Bad normal to triangle");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(0, 0, 1)
        );

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects inside the triangle (1 points)
        List<Point3D> result = triangle.findIntersections(new Ray(new Point3D(-1, -1, -2), new Vector(1, 1, 2)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(0.25, 0.25, 0.5), result.get(0), "Ray intersects inside the triangle");

        // TC02: Ray intersects outside the triangle against an edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point3D(-1, -2, -2), new Vector(1, 1, 2))), "Ray intersects outside the triangle against an edge");
        // TC03: Ray intersects outside the triangle against a vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point3D(-2, -2, -2), new Vector(1, 1, 2))), "Ray intersects outside the triangle against a vertex");
    }
}