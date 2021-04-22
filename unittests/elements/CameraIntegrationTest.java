package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationTest {
	@Test
	void testSphereAndCamera() {
		Sphere[] spheres = new Sphere[]{
				new Sphere(1, new Point3D(0, 0, -2.5)),
				new Sphere(2.5, new Point3D(0, 0, -2.5)),
				new Sphere(2, new Point3D(0, 0, -2)),
				new Sphere(4, new Point3D(0, 0, -1)),
				new Sphere(0.5, new Point3D(0, 0, 1))
		};
		int[] expectedIntersections = new int[]{2, 18, 10, 9, 0};

		testIntersectableAndCamera(spheres, expectedIntersections);
	}

	@Test
	void testPlaneAndCamera() {
		Plane[] planes = new Plane[]{
				new Plane(new Point3D(0,0,-2), new Vector(0,0,1)),
				new Plane(new Point3D(0,0,-1.5), new Vector(0,-0.5,1)),
				new Plane(new Point3D(0,0,-3), new Vector(0,-1,1)),

		};
		int[] expectedIntersections = new int[]{9, 9, 6};

		testIntersectableAndCamera(planes, expectedIntersections);
	}

	@Test
	void testTriangleAndCamera() {
		Triangle[] triangles = new Triangle[]{
				new Triangle(new Point3D(0,1,-2), new Point3D(1,-1,-2), new Point3D(-1,-1,-2)),
				new Triangle(new Point3D(0,20,-2), new Point3D(1,-1,-2), new Point3D(-1,-1,-2))
		};
		int[] expectedIntersections = new int[]{1,2};

		testIntersectableAndCamera(triangles, expectedIntersections);
	}

	void testIntersectableAndCamera(Intersectable[] intersectables, int[] expectedIntersections) {
		int nX = 3, nY = 3;
		Camera cam = new Camera(new Point3D(0,0,0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
				.setDistance(1)
				.setViewPlaneSize(3, 3);

		List<List<Point3D>> intersections = new ArrayList<>(Collections.nCopies(intersectables.length, null));

		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				Ray ray = cam.constructRayThroughPixel(nX, nY, j, i);
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

		for (int idx = 0; idx < intersectables.length; idx++) {
			int numOfIntersections = 0;
			if (intersections.get(idx) != null) {
				numOfIntersections = intersections.get(idx).size();
			}

			assertEquals(expectedIntersections[idx], numOfIntersections, "Wrong number of intersections, " + intersectables[idx]);
		}
	}
}
