package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link primitives.Ray} class.
 */
class RayTest {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}
     */
    @Test
    void findClosestPoint() {
        Ray ray = new Ray(new Point3D(1, 1, 1), new Vector(-1, 1, 2));

        // ============ Equivalence Partitions Tests ==============
        List<Point3D> points1 = new ArrayList<>();
        points1.add(new Point3D(0,0,3));
        points1.add(new Point3D(0,0,1));
        points1.add(new Point3D(2,0,-1));

        // TC01: point in the middle of the list is the closest point.
        assertEquals(points1.get(1), ray.findClosestPoint(points1), "point in the middle of the list was not the closest point.");

        // =============== Boundary Values Tests ==================
        // TC02: the list is null
        assertNull(ray.findClosestPoint(null), "didn't return null when the list is null");

        List<Point3D> points2 = new ArrayList<>();
        points2.add(new Point3D(0,0,1));
        points2.add(new Point3D(0,0,3));
        points2.add(new Point3D(2,0,-1));

        // TC03: the closest point is the first point of the list
        assertEquals(points2.get(0), ray.findClosestPoint(points2), "point in the beginning of the list was not the closest point.");

        List<Point3D> points3 = new ArrayList<>();
        points3.add(new Point3D(0,0,3));
        points3.add(new Point3D(2,0,-1));
        points3.add(new Point3D(0,0,1));

        // TC03: the closest point is the last point of the list
        assertEquals(points3.get(2), ray.findClosestPoint(points3), "point in the end of the list was not the closest point.");
    }
}