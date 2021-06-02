package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Tube} class.
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#Tube(primitives.Ray, double)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Tube(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 2);
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct tube");
        }

        // =============== Boundary Values Tests ==================
        // TC02: Test when the radius is 0.1
        try {
            new Tube(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 0.1);
        } catch (IllegalArgumentException err) {
            fail("Failed construct a correct tube when the radius is 0.1");
        }

        // TC03: Test when the radius is 0
        try {
            new Tube(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), 0);
            fail("Constructed a tube while the radius is 0");
        } catch (IllegalArgumentException ignored) {}

        // TC04: Test when the radius is -0.1
        try {
            new Tube(new Ray(new Point3D(1, -1.5, 0), new Vector(0, 2, 1)), -0.1);
            fail("Constructed a tube while the radius is -0.1");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube tb = new Tube(new Ray(new Point3D(1, 1, 0), new Vector(0, 0, 1)), 1d);

        assertEquals(new Vector(0, -1, 0), tb.getNormal(new Point3D(1, 0, 2)), "Bad normal to tube");
    }

    @Test
    void testFindIntersections() {
        Tube tb = new Tube(new Ray(
                new Point3D(1, 0, 1),
                new Vector(0, 1, 0)
        ), 1);
        List<Point3D> result;
        Ray ray;
        Point3D p1, p2;

        // ============ Equivalence Partitions Tests ==============

        // TC01: The ray doesn't intersect with the tube
        ray = new Ray(
                new Point3D(-1, 0, -1),
                new Vector(-0.5, -0.5, 1)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray doesn't intersect with the tube shouldn't find any intersections");

        // TC02: The ray intersects once with the tube (the ray is inside the tube)
        ray = new Ray(
                new Point3D(0.5, 1, 0.5),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.06833752096446, 0.56833752096446, 1.36332495807108);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray intersects once with the tube, resulted with wrong coordinates");

        // TC03: The ray intersects twice with the tube
        ray = new Ray(
                new Point3D(1, 1.5, -0.5),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.06833752096446, 0.56833752096446, 1.36332495807108);
        p2 = new Point3D(0.7316624790355399, 1.23166247903554, 0.036675041928920016);

        result = tb.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray intersects twice with the tube has wrong coordinates");

        // TC04: The ray starts after the tube
        ray = new Ray(
                new Point3D(0, 1, 2),
                new Vector(-0.5, -0.5, 1)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray starts after the tube shouldn't find any intersections");

        // =============== Boundary Values Tests ==================

        // *** Group: The ray starts on the tube
        // TC05: The ray goes outside (intersects once)
        ray = new Ray(
                new Point3D(0, 1, 1),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0, 1, 1);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray starts on the tube and goes outside, resulted with wrong coordinates");

        // TC06: The ray goes inside (intersects twice)
        ray = new Ray(
                new Point3D(2, 1, 1),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(1.6, 0.6, 1.8);
        p2 = new Point3D(2, 1, 1);

        result = tb.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray starts on the tube and goes inside, resulted with wrong coordinates");

        // *** Group: The ray's line is passing through the tube's ray (not the origin)
        // TC07: The ray starts after the tube (no intersections)
        ray = new Ray(
                new Point3D(0.5, 0.5, 2),
                new Vector(-0.5, -0.5, 1)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray's line is passing through the tube's ray and the ray starts after the tube, shouldn't find any intersection");

        // TC08: The ray starts on the tube and goes outside (intersects once)
        ray = new Ray(
                new Point3D(0.5527864045000421, 0.5527864045000421, 1.8944271909999157),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, 0.5527864045000421, 1.8944271909999157);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's ray and the ray starts on the tube and goes outside, resulted with wrong coordinates");

        // TC09: The ray starts after the tube's ray, inside the tube (intersects once)
        ray = new Ray(
                new Point3D(0.75, 0.75, 1.5),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, 0.5527864045000421, 1.8944271909999157);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's ray and the ray starts after the tube's ray, resulted with wrong coordinates");

        // TC10: The ray starts on the tube's ray (intersects once)
        ray = new Ray(
                new Point3D(1, 1, 1),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.552786404500042, 0.552786404500042, 1.894427190999916);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's ray and the ray starts on the tube's ray, resulted with wrong coordinates");

        // TC11: The ray starts before the tube's ray, inside the tube (intersects once)
        ray = new Ray(
                new Point3D(1.25, 1.25, 0.5),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, 0.5527864045000421, 1.8944271909999157);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's ray and the ray starts before the tube's ray, resulted with wrong coordinates");

        // TC12: The ray starts on the tube and goes inside (intersects twice)
        ray = new Ray(
                new Point3D(1.4472135954999579, 1.4472135954999579, 0.10557280900008412),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, 0.5527864045000421, 1.8944271909999157);
        p2 = new Point3D(1.4472135954999579, 1.4472135954999579, 0.10557280900008412);

        result = tb.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray's line is passing through the tube's ray and the ray starts on the tube and goes inside, resulted with wrong coordinates");

        // TC13: The ray starts before the tube (intersects twice)
        ray = new Ray(
                new Point3D(1.5, 1.5, 0),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, 0.5527864045000421, 1.8944271909999157);
        p2 = new Point3D(1.4472135954999579, 1.4472135954999579, 0.10557280900008412);

        result = tb.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray's line is passing through the tube's ray and the ray starts on the tube and goes inside, resulted with wrong coordinates");

        // *** Group: The ray's line is passing through the tube's origin
        // TC14: The ray starts after the tube (no intersections)
        ray = new Ray(
                new Point3D(0.5, -0.5, 2),
                new Vector(-0.5, -0.5, 1)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray's line is passing through the tube's origin and the ray starts after the tube, shouldn't find any intersection");

        // TC15: The ray starts on the tube and goes outside (intersects once)
        ray = new Ray(
                new Point3D(0.5527864045000421, -0.44721359549995787, 1.8944271909999157),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, -0.44721359549995787, 1.8944271909999157);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's origin and the ray starts on the tube and goes outside, resulted with wrong coordinates");

        // TC16: The ray starts after the tube's origin, inside the tube (intersects once)
        ray = new Ray(
                new Point3D(0.75, -0.25, 1.5),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, -0.44721359549995787, 1.8944271909999157);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's origin and the ray starts after the tube's ray, resulted with wrong coordinates");

        // TC17: The ray starts on the tube's origin (intersects once)
        ray = new Ray(
                new Point3D(1, 0, 1),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, -0.44721359549995787, 1.8944271909999157);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's origin and the ray starts on the tube's ray, resulted with wrong coordinates");

        // TC18: The ray starts before the tube's origin, inside the tube (intersects once)
        ray = new Ray(
                new Point3D(1.25, 0.25, 0.5),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, -0.44721359549995787, 1.8944271909999157);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's ray and the ray starts before the tube's ray, resulted with wrong coordinates");

        // TC19: The ray starts on the tube and goes inside (intersects twice)
        ray = new Ray(
                new Point3D(1.4472135954999579, 0.4472135954999579, 0.10557280900008412),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, -0.44721359549995787, 1.8944271909999157);
        p2 = new Point3D(1.4472135954999579, 0.4472135954999579, 0.10557280900008412);

        result = tb.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray's line is passing through the tube's origin and the ray starts on the tube and goes inside, resulted with wrong coordinates");

        // TC20: The ray starts before the tube (intersects twice)
        ray = new Ray(
                new Point3D(1.5, 0.5, 0),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(0.5527864045000421, -0.44721359549995787, 1.8944271909999157);
        p2 = new Point3D(1.4472135954999579, 0.4472135954999579, 0.10557280900008412);

        result = tb.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray's line is passing through the tube's origin and the ray starts on the tube and goes inside, resulted with wrong coordinates");

        // *** Group: The ray tangents with the tube
        // TC21: The ray starts after the tangent point (no intersections)
        ray = new Ray(
                new Point3D(0.5 + Math.sqrt(0.8), 0.5, 2 + Math.sqrt(0.2)),
                new Vector(-0.5, -0.5, 1)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray starts after the tangent point, shouldn't find any intersections");

        // TC22: The ray starts before the tangent point (intersects once)
        ray = new Ray(
                new Point3D(1.5 + Math.sqrt(0.8), 1.5, Math.sqrt(0.2)),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(1 + Math.sqrt(0.8), 1, 1 + Math.sqrt(0.2));

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray starts before the tangent point intersects, resulted with wrong coordinates");

        // TC23: The ray starts on the tangent point (intersects once)
        ray = new Ray(
                new Point3D(1 + Math.sqrt(0.8), 1, 1 + Math.sqrt(0.2)),
                new Vector(-0.5, -0.5, 1)
        );
        p1 = new Point3D(1 + Math.sqrt(0.8), 1, 1 + Math.sqrt(0.2));

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray starts on the tangent point intersects, resulted with wrong coordinates");

        // TC24: The ray is orthogonal to the vector between the ray's origin and the tube's ray (no intersections)
        ray = new Ray(
                new Point3D(1 + 2 * Math.sqrt(0.8), 1, 1 + 2 * Math.sqrt(0.2)),
                new Vector(-0.5, -0.5, 1)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is orthogonal to the vector between the ray's origin and the tube's ray, shouldn't find any intersections");

        // *** Group: The ray starts on the tube and is orthogonal to the tube's ray
        // TC25: The ray goes outside (intersects once)
        ray = new Ray(
                new Point3D(0, 1, 1),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0, 1, 1);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray starts on the tube and goes outside, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC26: The ray goes inside (intersects twice)
        ray = new Ray(
                new Point3D(2, 1, 1),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(1.6, 1, 1.8);
        p2 = new Point3D(2, 1, 1);

        result = tb.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray starts on the tube and goes inside, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");


        // *** Group: The ray's line is passing through the tube's ray (not the origin) and is orthogonal to the tube's ray
        // TC27: The ray starts after the tube (no intersections)
        ray = new Ray(
                new Point3D(0.5, 0.5, 2),
                new Vector(-0.5, 0, 1)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray's line is passing through the tube's ray and the ray starts after the tube, the ray is orthogonal to the tube's ray, shouldn't find any intersection");

        // TC28: The ray starts on the tube and goes outside (intersects once)
        ray = new Ray(
                new Point3D(0.552786404500042, 1, 1.894427190999916),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.552786404500042, 1, 1.894427190999916);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's ray and the ray starts on the tube and goes outside, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC29: The ray starts before the tube's ray, inside the tube (intersects once)
        ray = new Ray(
                new Point3D(0.75, 1, 1.5),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.552786404500042, 1, 1.894427190999916);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's ray and the ray starts after the tube's ray, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC30: The ray starts on the tube's ray (intersects once)
        ray = new Ray(
                new Point3D(1, 1, 1),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.552786404500042, 1, 1.894427190999916);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's ray and the ray starts on the tube's ray, the ray is orthogonal to the tube's ray resulted with wrong coordinates");

        // TC31: The ray starts after the tube's ray, inside the tube (intersects once)
        ray = new Ray(
                new Point3D(1.25, 1, 0.5),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.552786404500042, 1, 1.894427190999916);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's ray and the ray starts before the tube's ray, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC32: The ray starts on the tube and goes inside (intersects twice)
        ray = new Ray(
                new Point3D(1.4472135954999579, 1.0, 0.10557280900008419),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.5527864045000419, 1.0, 1.8944271909999162);
        p2 = new Point3D(1.4472135954999579, 1.0, 0.10557280900008419);

        result = tb.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray's line is passing through the tube's ray and the ray starts on the tube and goes inside, the ray is orthogonal to the tube's ray resulted with wrong coordinates");

        // TC33: The ray starts before the tube (intersects twice)
        ray = new Ray(
                new Point3D(1.5, 1, 0),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.5527864045000419, 1.0, 1.8944271909999162);
        p2 = new Point3D(1.4472135954999579, 1.0, 0.10557280900008419);

        result = tb.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray's line is passing through the tube's ray and the ray starts on the tube and goes inside, the ray is orthogonal to the tube's ray resulted with wrong coordinates");


        // *** Group: The ray's line is passing through the tube's origin and is orthogonal to the tube's ray
        // TC34: The ray starts after the tube (no intersections)
        ray = new Ray(
                new Point3D(0.5, 0, 2),
                new Vector(-0.5, 0, 1)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray's line is passing through the tube's origin and the ray starts after the tube, the ray is orthogonal to the tube's ray, shouldn't find any intersection");

        // TC35: The ray starts on the tube and goes outside (intersects once)
        ray = new Ray(
                new Point3D(0.552786404500042, 0.0, 1.894427190999916),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.552786404500042, 0.0, 1.894427190999916);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's origin and the ray starts on the tube and goes outside, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC36: The ray starts before the tube's origin, inside the tube (intersects once)
        ray = new Ray(
                new Point3D(0.75, 0, 1.5),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.552786404500042, 0.0, 1.894427190999916);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's origin and the ray starts after the tube's ray, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC37: The ray starts on the tube's origin (intersects once)
        ray = new Ray(
                new Point3D(1, 0, 1),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.552786404500042, 0.0, 1.894427190999916);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's origin and the ray starts on the tube's ray, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC38: The ray starts after the tube's origin, inside the tube (intersects once)
        ray = new Ray(
                new Point3D(1.25, 0, 0.5),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.552786404500042, 0.0, 1.894427190999916);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray's line is passing through the tube's ray and the ray starts before the tube's ray, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC39: The ray starts on the tube and goes inside (intersects twice)
        ray = new Ray(
                new Point3D(1.4472135954999579, 0.0, 0.10557280900008419),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.552786404500042, 0.0, 1.894427190999916);
        p2 = new Point3D(1.4472135954999579, 0.0, 0.10557280900008419);

        result = tb.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray's line is passing through the tube's origin and the ray starts on the tube and goes inside, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC40: The ray starts before the tube (intersects twice)
        ray = new Ray(
                new Point3D(1.5, 0, 0),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(0.552786404500042, 0.0, 1.894427190999916);
        p2 = new Point3D(1.4472135954999579, 0.0, 0.10557280900008419);

        result = tb.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of intersections");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "The ray's line is passing through the tube's origin and the ray starts on the tube and goes inside, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // *** Group: The ray tangents with the tube and is orthogonal to the tube's ray
        // TC41: The ray starts after the tangent point (no intersections)
        ray = new Ray(
                new Point3D(0.5 + Math.sqrt(0.8), 1, 2 + Math.sqrt(0.2)),
                new Vector(-0.5, 0, 1)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray starts after the tangent point, the ray is orthogonal to the tube's ray, shouldn't find any intersections");

        // TC42: The ray starts before the tangent point (intersects once)
        ray = new Ray(
                new Point3D(1.5 + Math.sqrt(0.8), 1, Math.sqrt(0.2)),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(1 + Math.sqrt(0.8), 1, 1 + Math.sqrt(0.2));

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray starts before the tangent point intersects, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC43: The ray starts on the tangent point (intersects once)
        ray = new Ray(
                new Point3D(1 + Math.sqrt(0.8), 1, 1 + Math.sqrt(0.2)),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(1 + Math.sqrt(0.8), 1, 1 + Math.sqrt(0.2));

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray starts on the tangent point intersects, the ray is orthogonal to the tube's ray, resulted with wrong coordinates");

        // *** Group: Special cases
        // TC44: The ray starts inside the tube and is orthogonal to the tube's ray (intersects once)
        ray = new Ray(
                new Point3D(1.25, 1, 1.5),
                new Vector(-0.5, 0, 1)
        );
        p1 = new Point3D(1, 1, 2);

        result = tb.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of intersections");
        assertEquals(p1, result.get(0), "The ray starts inside the tube is orthogonal to the tube's ray, resulted with wrong coordinates");

        // TC45: The ray is orthogonal to the vector between the ray's origin and the tube's ray,
        //       also the first ray is orthogonal to the tube's ray (no intersections)
        ray = new Ray(
                new Point3D(1 + 2 * Math.sqrt(0.8), 1, 1 + 2 * Math.sqrt(0.2)),
                new Vector(-0.5, 0, 1)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is orthogonal to the vector between the ray's origin and the tube's ray, the ray is orthogonal to the tube's ray, shouldn't find any intersections");

        // *** Group: The ray is on the tube's ray and is parallel to the tube's ray  (no intersections)
        // TC46: The ray starts before the tube's origin
        ray = new Ray(
                new Point3D(1, -1, 1),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's ray, before the tube's origin and is parallel to the tube's ray, shouldn't find any intersections");

        // TC47: The ray starts on the tube's origin
        ray = new Ray(
                new Point3D(1, 0, 1),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's origin and is parallel to the tube's ray, shouldn't find any intersections");

        // TC48: The ray starts after the tube's origin
        ray = new Ray(
                new Point3D(1, 1, 1),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's ray, after the tube's origin and is parallel to the tube's ray, shouldn't find any intersections");

        // *** Group: The ray is on the tube's surface and is parallel to the tube's ray (intersects once)
        // TC49: The ray starts before the tube's origin
        ray = new Ray(
                new Point3D(1, -1, 0),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's surface, starts before the origin and is parallel to the tube's ray, shouldn't find any intersections");

        // TC50: The ray starts on the tube's origin
        ray = new Ray(
                new Point3D(1, 0, 0),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's surface, starts at the origin and is parallel to the tube's ray, shouldn't find any intersections");

        // TC51: The ray starts after the tube's origin
        ray = new Ray(
                new Point3D(1, 1, 0),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's surface, starts after the origin and is parallel to the tube's ray, shouldn't find any intersections");

        // *** Group: The ray is inside the tube and is parallel to the tube's ray (no intersections)
        // TC52: The ray starts before the tube's origin
        ray = new Ray(
                new Point3D(0.5, -1, 0.5),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is inside the tube, starts before the tube's origin and is parallel to the tube's ray, shouldn't find any intersections");

        // TC53: The ray starts at the tube's origin
        ray = new Ray(
                new Point3D(0.5, 0, 0.5),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is inside the tube, starts at the tube's origin and is parallel to the tube's ray, shouldn't find any intersections");

        // TC54: The ray starts after the tube's origin
        ray = new Ray(
                new Point3D(0.5, 1, 0.5),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is inside the tube, starts after the tube's origin and is parallel to the tube's ray, shouldn't find any intersections");

        // *** Group: The ray is outside the tube and is parallel to the tube's ray (no intersections)
        // TC55: The ray starts before the tube's origin
        ray = new Ray(
                new Point3D(2, -1, 2),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is outside the tube, starts before the tube's origin and is parallel to the tube's ray, shouldn't find any intersections");

        // TC56: The ray starts at the tube's origin
        ray = new Ray(
                new Point3D(2, 0, 2),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is outside the tube, starts at the tube's origin and is parallel to the tube's ray, shouldn't find any intersections");

        // TC57: The ray starts after the tube's origin
        ray = new Ray(
                new Point3D(2, 1, 2),
                new Vector(0, 1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is outside the tube, starts after the tube's origin and is parallel to the tube's ray, shouldn't find any intersections");

        // *** Group: The ray is on the tube's ray and is parallel to the tube's ray with the opposite direction (no intersections)
        // TC58: The ray starts before the tube's origin
        ray = new Ray(
                new Point3D(1, -1, 1),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's ray, before the tube's origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // TC59: The ray starts on the tube's origin
        ray = new Ray(
                new Point3D(1, 0, 1),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // TC60: The ray starts after the tube's origin
        ray = new Ray(
                new Point3D(1, 1, 1),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's ray, after the tube's origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // *** Group: The ray is on the tube's surface and is parallel to the tube's ray with the opposite direction (intersects once)
        // TC61: The ray starts before the tube's origin
        ray = new Ray(
                new Point3D(1, -1, 0),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's surface, starts before the origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // TC62: The ray starts on the tube's origin
        ray = new Ray(
                new Point3D(1, 0, 0),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's surface, starts at the origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // TC63: The ray starts after the tube's origin
        ray = new Ray(
                new Point3D(1, 1, 0),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is on the tube's surface, starts after the origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // *** Group: The ray is inside the tube and is parallel to the tube's ray with the opposite direction (no intersections)
        // TC64: The ray starts before the tube's origin
        ray = new Ray(
                new Point3D(0.5, -1, 0.5),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is inside the tube, starts before the tube's origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // TC65: The ray starts at the tube's origin
        ray = new Ray(
                new Point3D(0.5, 0, 0.5),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is inside the tube, starts at the tube's origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // TC66: The ray starts after the tube's origin
        ray = new Ray(
                new Point3D(0.5, 1, 0.5),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is inside the tube, starts after the tube's origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // *** Group: The ray is outside the tube and is parallel to the tube's ray with the opposite direction (no intersections)
        // TC67: The ray starts before the tube's origin
        ray = new Ray(
                new Point3D(2, -1, 2),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is outside the tube, starts before the tube's origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // TC68: The ray starts at the tube's origin
        ray = new Ray(
                new Point3D(2, 0, 2),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is outside the tube, starts at the tube's origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

        // TC69: The ray starts after the tube's origin
        ray = new Ray(
                new Point3D(2, 1, 2),
                new Vector(0, -1, 0)
        );

        result = tb.findIntersections(ray);

        assertNull(result, "The ray is outside the tube, starts after the tube's origin and is parallel to the tube's ray with the opposite direction, shouldn't find any intersections");

    }
}