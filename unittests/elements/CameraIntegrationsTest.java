package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationsTest {
	@Test
	void testSphereAndCamera() {
		int nX = 3, nY = 3;
		Sphere[] spheres = new Sphere[]{
				new Sphere(1, new Point3D(0, 0, -2.5)),
				new Sphere(2.5, new Point3D(0, 0, -2.5)),
				new Sphere(2, new Point3D(0, 0, -2)),
				new Sphere(4, new Point3D(0, 0, -1)),
				new Sphere(0.5, new Point3D(0, 0, 1))
		};
		int numOfSpheres = spheres.length;

		Camera cam = new Camera(new Point3D(0,0,0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
				.setDistance(1)
				.setViewPlaneSize(3, 3);

		List<List<Point3D>> spheresIntersections = new ArrayList<>(Collections.nCopies(numOfSpheres, null));

		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				Ray ray = cam.constructRayThroughPixel(nX, nY, j, i);
				for (int s = 0; s < numOfSpheres; s++) {
					List<Point3D> lst = spheres[s].findIntersections(ray);
					if (lst == null) {
						continue;
					}
					if (spheresIntersections.get(s) == null) {
						spheresIntersections.set(s, new ArrayList<>());
					}
					spheresIntersections.get(s).addAll(lst);
				}
			}
		}
		int[] expectedIntersections = new int[]{2, 18, 10, 9, 0};
		for (int s = 0; s < numOfSpheres; s++) {
			if (spheresIntersections.get(s) == null) {
				assertEquals(expectedIntersections[s], 0, "Wrong number of intersections, " + spheres[s] );
				continue;
			}
			assertEquals(expectedIntersections[s], spheresIntersections.get(s).size(), "Wrong number of intersections, " + spheres[s]);
		}
	}

	@Test
	void testPlaneAndCamera() {
		int nX = 3, nY = 3;
		Plane[] planes = new Plane[]{
				new Plane(new Point3D(0,0,-2), new Vector(0,0,1)),
				new Plane(new Point3D(0,0,-1.5), new Vector(0,-0.5,1)),
				new Plane(new Point3D(0,0,-3), new Vector(0,-1,1)),

		};

		Camera cam = new Camera(new Point3D(0,0,0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
				.setDistance(1)
				.setViewPlaneSize(3, 3);

		List<List<Point3D>> planesIntersections = new ArrayList<>(Collections.nCopies(planes.length, null));

		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				Ray ray = cam.constructRayThroughPixel(nX, nY, j, i);
				for (int p = 0; p < planes.length; p++) {
					List<Point3D> lst = planes[p].findIntersections(ray);
					if (lst == null) {
						continue;
					}
					if (planesIntersections.get(p) == null) {
						planesIntersections.set(p, new ArrayList<>());
					}
					planesIntersections.get(p).addAll(lst);
				}
			}
		}
		int[] expectedIntersections = new int[]{9, 9, 6};
		for (int p = 0; p < planes.length; p++) {
			if (planesIntersections.get(p) == null) {
				assertEquals(expectedIntersections[p], 0, "Wrong number of intersections, " + planes[p] );
				continue;
			}
			assertEquals(expectedIntersections[p], planesIntersections.get(p).size(), "Wrong number of intersections, " + planes[p]);
		}
	}

	@Test
	void testTriangleAndCamera() {
		int nX = 3, nY = 3;
		Triangle[] triangles = new Triangle[]{
				new Triangle(new Point3D(0,1,-2), new Point3D(1,-1,-2), new Point3D(-1,-1,-2)),
				new Triangle(new Point3D(0,20,-2), new Point3D(1,-1,-2), new Point3D(-1,-1,-2))
		};

		Camera cam = new Camera(new Point3D(0,0,0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
				.setDistance(1)
				.setViewPlaneSize(3, 3);

		List<List<Point3D>> trianglesIntersections = new ArrayList<>(Collections.nCopies(triangles.length, null));

		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				Ray ray = cam.constructRayThroughPixel(nX, nY, j, i);
				for (int t = 0; t < triangles.length; t++) {
					List<Point3D> lst = triangles[t].findIntersections(ray);
					if (lst == null) {
						continue;
					}
					if (trianglesIntersections.get(t) == null) {
						trianglesIntersections.set(t, new ArrayList<>());
					}
					trianglesIntersections.get(t).addAll(lst);
				}
			}
		}
		int[] expectedIntersections = new int[]{1,2};
		for (int t = 0; t < triangles.length; t++) {
			if (trianglesIntersections.get(t) == null) {
				assertEquals(expectedIntersections[t], 0, "Wrong number of intersections, " + triangles[t] );
				continue;
			}
			assertEquals(expectedIntersections[t], trianglesIntersections.get(t).size(), "Wrong number of intersections, " + triangles[t]);
		}
	}
}
