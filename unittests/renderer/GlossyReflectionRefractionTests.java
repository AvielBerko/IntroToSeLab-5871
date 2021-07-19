package renderer;

import elements.Camera;
import elements.SpotLight;
import geometries.BVH;
import geometries.Cylinder;
import geometries.Polygon;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

public class GlossyReflectionRefractionTests {

    @Test
    public void reflectionSphereAndCylinder() {
        Camera camera = new Camera(
                new Point3D(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setViewPlaneSize(225, 150)
                .setDistance(800)
                .setNumOfRays(50);

        Scene scene = Scene.Builder.create("Test Scene")
                .setLights(
                        new SpotLight(
                                new Color(500, 500, 500),
                                new Point3D(-50, 100, 100),
                                new Vector(-0.5, -1, -0.5))
                                .setKl(0.004)
                                .setKq(0.000006))
                .addGeometries(new BVH(
                        new Sphere(50, new Point3D(50, 0, 0))
                                .setEmission(new Color(5, 5, 5))
                                .setMaterial(new Material()
                                        .setKr(1.0).setKg(0.8)),
                        new Cylinder(new Ray(
                                new Point3D(-90, -35, 0),
                                new Vector(60, 85, 0)),
                                25, 100)
                                .setEmission(new Color(100, 75, 0))
                                .setMaterial(new Material()
                                        .setKd(0.6).setKs(0.4)
                                        .setShininess(80)),
                        new Polygon(
                                new Point3D(-100, -50, -150),
                                new Point3D(-100, -50, 150),
                                new Point3D(100, -50, 150),
                                new Point3D(100, -50, -150))
                                .setEmission(new Color(40, 40, 40))
                                .setMaterial(new Material()
                                        .setKd(0.6).setKs(0.4)
                                        .setShininess(50)),
                        new Polygon(
                                new Point3D(-100, -50, -150),
                                new Point3D(-100, 75, -150),
                                new Point3D(100, 75, -150),
                                new Point3D(100, -50, -150))
                                .setEmission(new Color(40, 40, 40))
                                .setMaterial(new Material()
                                        .setKd(0.6).setKs(0.4)
                                        .setShininess(50))
                )).build();

        Render render = new Render()
                .setImageWriter(
                        new ImageWriter("reflectionGlossinessSphereAndCylinder", 750, 500))
                .setCamera(camera)
                .setMultithreading(3)
                .setPrintPercent(true)
                .setAntiAliasing(true)
                .setRayTracer(new BasicRayTracer(scene).setGlossinessRays(20));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void reflectionSphereCylinderShowKg() {
        Camera camera = new Camera(
                new Point3D(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setViewPlaneSize(225, 150)
                .setDistance(800)
                .setNumOfRays(20);

        for (int i = 0; i <= 10; i++) {
            double glossiness = i / 10.0;
            Scene scene = Scene.Builder.create("Test Scene")
                    .setLights(
                            new SpotLight(
                                    new Color(500, 500, 500),
                                    new Point3D(-50, 100, 100),
                                    new Vector(-0.5, -1, -0.5))
                                    .setKl(0.004)
                                    .setKq(0.000006))
                    .addGeometries(new BVH(
                            new Sphere(50, new Point3D(50, 0, 0))
                                    .setEmission(new Color(5, 5, 5))
                                    .setMaterial(new Material()
                                            .setKr(1.0).setKg(glossiness)),
                            new Cylinder(new Ray(
                                    new Point3D(-90, -35, 0),
                                    new Vector(60, 85, 0)),
                                    25, 100)
                                    .setEmission(new Color(100, 75, 0))
                                    .setMaterial(new Material()
                                            .setKd(0.6).setKs(0.4)
                                            .setShininess(80)),
                            new Polygon(
                                    new Point3D(-100, -50, -150),
                                    new Point3D(-100, -50, 150),
                                    new Point3D(100, -50, 150),
                                    new Point3D(100, -50, -150))
                                    .setEmission(new Color(40, 40, 40))
                                    .setMaterial(new Material()
                                            .setKd(0.6).setKs(0.4)
                                            .setShininess(50)),
                            new Polygon(
                                    new Point3D(-100, -50, -150),
                                    new Point3D(-100, 75, -150),
                                    new Point3D(100, 75, -150),
                                    new Point3D(100, -50, -150))
                                    .setEmission(new Color(40, 40, 40))
                                    .setMaterial(new Material()
                                            .setKd(0.6).setKs(0.4)
                                            .setShininess(50))
                    )).build();

            Render render = new Render()
                    .setImageWriter(
                            new ImageWriter("reflectionGlossiness/glossiness" + glossiness, 750, 500))
                    .setCamera(camera)
                    .setMultithreading(3)
                    .setPrintPercent(true)
                    .setAntiAliasing(true)
                    .setRayTracer(new BasicRayTracer(scene).setGlossinessRays(20));
            render.renderImage();
            render.writeToImage();
        }
    }

    @Test
    public void refractionSphereAndPane() {
        Camera camera = new Camera(
                new Point3D(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setViewPlaneSize(225, 150)
                .setDistance(800)
                .setNumOfRays(20);

        Scene scene = Scene.Builder.create("Test Scene")
                .setLights(
                        new SpotLight(
                                new Color(500, 500, 500),
                                new Point3D(-100, 100, 100),
                                new Vector(-0.5, -1, -0.5))
                                .setKl(0.004)
                                .setKq(0.000006))
                .addGeometries(new BVH(
                        new Sphere(50, new Point3D(0, 0, 0))
                                .setEmission(new Color(5, 50, 120))
                                .setMaterial(new Material()
                                        .setKd(0.6).setKs(0.4)
                                        .setShininess(100)),
                        new Polygon(
                                new Point3D(0, -50, 75),
                                new Point3D(0, 50, 75),
                                new Point3D(75, 50, 75),
                                new Point3D(75, -50, 75))
                                .setEmission(new Color(40, 40, 40))
                                .setMaterial(new Material()
                                        .setKt(1.0).setKg(0.8)),
                        new Polygon(
                                new Point3D(-75, -50, -75),
                                new Point3D(-75, -50, 75),
                                new Point3D(75, -50, 75),
                                new Point3D(75, -50, -75))
                                .setEmission(new Color(40, 40, 40))
                                .setMaterial(new Material()
                                        .setKd(0.6).setKs(0.4)
                                        .setShininess(50)),
                        new Polygon(
                                new Point3D(-75, -50, -75),
                                new Point3D(-75, 75, -75),
                                new Point3D(75, 75, -75),
                                new Point3D(75, -50, -75))
                                .setEmission(new Color(40, 40, 40))
                                .setMaterial(new Material()
                                        .setKd(0.6).setKs(0.4)
                                        .setShininess(50))
                )).build();

        Render render = new Render()
                .setImageWriter(
                        new ImageWriter("refractionGlossinessSphereAndPane", 750, 500))
                .setCamera(camera)
                .setMultithreading(3)
                .setPrintPercent(true)
                .setAntiAliasing(true)
                .setRayTracer(new BasicRayTracer(scene).setGlossinessRays(20));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void refractionSphereAndPaneShowKg() {
        Camera camera = new Camera(
                new Point3D(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setViewPlaneSize(225, 150)
                .setDistance(800)
                .setNumOfRays(20);

        for (int i = 0; i <= 10; i++) {
            double glossiness = i / 10.0;
            Scene scene = Scene.Builder.create("Test Scene")
                    .setLights(
                            new SpotLight(
                                    new Color(500, 500, 500),
                                    new Point3D(-100, 100, 100),
                                    new Vector(-0.5, -1, -0.5))
                                    .setKl(0.004)
                                    .setKq(0.000006))
                    .addGeometries(new BVH(
                            new Sphere(50, new Point3D(0, 0, 0))
                                    .setEmission(new Color(5, 50, 120))
                                    .setMaterial(new Material()
                                            .setKd(0.6).setKs(0.4)
                                            .setShininess(100)),
                            new Polygon(
                                    new Point3D(0, -50, 75),
                                    new Point3D(0, 50, 75),
                                    new Point3D(75, 50, 75),
                                    new Point3D(75, -50, 75))
                                    .setEmission(new Color(40, 40, 40))
                                    .setMaterial(new Material()
                                            .setKt(1.0).setKg(glossiness)),
                            new Polygon(
                                    new Point3D(-75, -50, -75),
                                    new Point3D(-75, -50, 75),
                                    new Point3D(75, -50, 75),
                                    new Point3D(75, -50, -75))
                                    .setEmission(new Color(40, 40, 40))
                                    .setMaterial(new Material()
                                            .setKd(0.6).setKs(0.4)
                                            .setShininess(50)),
                            new Polygon(
                                    new Point3D(-75, -50, -75),
                                    new Point3D(-75, 75, -75),
                                    new Point3D(75, 75, -75),
                                    new Point3D(75, -50, -75))
                                    .setEmission(new Color(40, 40, 40))
                                    .setMaterial(new Material()
                                            .setKd(0.6).setKs(0.4)
                                            .setShininess(50))
                    ) ).build();

            Render render = new Render()
                    .setImageWriter(
                            new ImageWriter("refractionGlossiness/glossiness" + glossiness, 750, 500))
                    .setCamera(camera)
                    .setMultithreading(3)
                    .setPrintPercent(true)
                    .setAntiAliasing(true)
                    .setRayTracer(new BasicRayTracer(scene).setGlossinessRays(20));
            render.renderImage();
            render.writeToImage();
        }
    }
}
