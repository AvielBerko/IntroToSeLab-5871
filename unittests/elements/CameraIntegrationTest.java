package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for {@link elements.Camera} class.
 */
public class CameraIntegrationTest {
	/**
	 * Integration tests of a camera and a sphere.
	 */
	@Test
	void testSphereAndCamera() {
		Sphere[] spheres = new Sphere[]{
				// TC01: the camera is directed to the sphere, only the ray of the center pixel should intersect (total: 2).
				new Sphere(1, new Point3D(0, 0, -2.5)),
				// TC02: the view plane is inside the sphere, all rays should intersect twice (total: 18).
				new Sphere(2.5, new Point3D(0, 0, -2.5)),
				// TC03: the sphere is far enough to not intersect with the edge-pixels, all rays except the rays that going through the edges should intersect (total: 10).
				new Sphere(2, new Point3D(0, 0, -2)),
				// TC04: the camera is inside the sphere, all rays should intersect only once (total: 9).
				new Sphere(4, new Point3D(0, 0, -1)),
				// TC05: the sphere is behind the camera, no ray should intersect (total: 0).
				new Sphere(0.5, new Point3D(0, 0, 1))
		};
		int[] expectedIntersections = new int[]{2, 18, 10, 9, 0};

		testIntersectableAndCamera(spheres, expectedIntersections);
	}

	/**
	 * Integration tests of a camera and a plane.
	 */
	@Test
	void testPlaneAndCamera() {
		Plane[] planes = new Plane[]{
				// TC01: the plane is parallel with the view plane, all rays should intersect (total: 9).
				new Plane(new Point3D(0,0,-2), new Vector(0,0,1)),
				// TC02: the plane is in front of the view plane, all rays should intersect (total: 9).
				new Plane(new Point3D(0,0,-1.5), new Vector(0,-0.5,1)),
				// TC03: the plane is above the view plane's third row, only the 6 upper rays should intersect (total: 6).
				new Plane(new Point3D(0,0,-3), new Vector(0,-1,1)),
		};
		int[] expectedIntersections = new int[]{9, 9, 6};

		testIntersectableAndCamera(planes, expectedIntersections);
	}

	/**
	 * Integration tests of a camera and a triangle.
	 */
	@Test
	void testTriangleAndCamera() {
		Triangle[] triangles = new Triangle[]{
		        // TC01: the camera is directed to a small triangle, only the center ray should intersect (total: 1).
				new Triangle(new Point3D(0,1,-2), new Point3D(1,-1,-2), new Point3D(-1,-1,-2)),
                // TC02: the camera is directed to a long triangle, only the center ray and the top-middle ray should intersect(total: 2).
				new Triangle(new Point3D(0,20,-2), new Point3D(1,-1,-2), new Point3D(-1,-1,-2))
		};
		int[] expectedIntersections = new int[]{1,2};

		testIntersectableAndCamera(triangles, expectedIntersections);
	}

	/**
	 * Helper method for testing intersectables and a camera.
	 * @param intersectables Intersectables to check the number of intersections for each one.
	 * @param expectedIntersections All expected intersections for the intersectables, should be in the same order of intersectables.
	 */
	void testIntersectableAndCamera(Intersectable[] intersectables, int[] expectedIntersections) {
		int nX = 3, nY = 3;
		Camera cam = new Camera(new Point3D(0,0,0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
				.setDistance(1)
				.setViewPlaneSize(3, 3);

		List<List<Point3D>> intersections = new ArrayList<>(Collections.nCopies(intersectables.length, null));

		// loop on every pixel on the view plane to construct a ray and find the intersections.
		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				Ray ray = cam.constructRayThroughPixel(nX, nY, j, i);

				// loop on every intersectable to find intersections with each one.
				for (int idx = 0; idx < intersectables.length; idx++) {
					List<Point3D> lst = intersectables[idx].findIntersections(ray);
					if (lst == null) {
						continue;
					}
					if (intersections.get(idx) == null) {
						intersections.set(idx, new ArrayList<>());
					}
					intersections.get(idx).addAll(lst);
				}
			}
		}

		// loop on each intersectable to assert the number of intersections.
		for (int idx = 0; idx < intersectables.length; idx++) {
			int numOfIntersections = 0;
			if (intersections.get(idx) != null) {
				numOfIntersections = intersections.get(idx).size();
			}

			assertEquals(expectedIntersections[idx], numOfIntersections, "Wrong number of intersections, " + intersectables[idx]);
		}
	}
}
