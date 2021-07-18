package special;

import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class BVHTests {
    @Test
    public void noAccelerations() {
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
                .addGeometries(
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
                ).build();

        Render render = new Render()
                .setImageWriter(
                        new ImageWriter("bvh/noAccelerations", 750, 500))
                .setCamera(camera)
                .setMultithreading(3)
                .setPrintPercent(true)
                .setAntiAliasing(true)
                .setRayTracer(new BasicRayTracer(scene)
                        .setGlossinessRays(20)
                        .useBoundingBoxes(false));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void boundingBoxAcceleration() {
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
                .addGeometries(
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
                ).build();

        Render render = new Render()
                .setImageWriter(
                        new ImageWriter("bvh/boundingBoxes", 750, 500))
                .setCamera(camera)
                .setMultithreading(3)
                .setPrintPercent(true)
                .setAntiAliasing(true)
                .setRayTracer(new BasicRayTracer(scene)
                        .setGlossinessRays(20)
                        .useBoundingBoxes(true));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void manualBVHAcceleration() {
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
                .addGeometries(
                        new Geometries(
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
                                new Point3D(-75, 75, -75),
                                new Point3D(75, 75, -75),
                                new Point3D(75, -50, -75))
                                .setEmission(new Color(40, 40, 40))
                                .setMaterial(new Material()
                                        .setKd(0.6).setKs(0.4)
                                        .setShininess(50))
                        ),
                        new Polygon(
                                new Point3D(-75, -50, -75),
                                new Point3D(-75, -50, 75),
                                new Point3D(75, -50, 75),
                                new Point3D(75, -50, -75))
                                .setEmission(new Color(40, 40, 40))
                                .setMaterial(new Material()
                                        .setKd(0.6).setKs(0.4)
                                        .setShininess(50))
                ).build();

        Render render = new Render()
                .setImageWriter(
                        new ImageWriter("bvh/manualBVH", 750, 500))
                .setCamera(camera)
                .setMultithreading(3)
                .setPrintPercent(true)
                .setAntiAliasing(true)
                .setRayTracer(new BasicRayTracer(scene)
                        .setGlossinessRays(20)
                        .useBoundingBoxes(true));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void teapot() {
        Scene scene = Scene.Builder.create("Test Scene")
                .setLights(
                        new PointLight(
                                new Color(500, 500, 500),
                                new Point3D(100, 0, -100))
                                .setKq(0.000001))
                .setGeometries(
                        getTeapotModel(
                                new Color(5, 5, 5),
                                new Material().setKr(1.0).setKg(0.8)
                        ))
                .addGeometries(
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
                )
                .build();

        Camera camera = new Camera(
                new Point3D(0, 0, -1000),
                new Vector(0, 0, 1),
                new Vector(0, 1, 0)) //
                .setDistance(1000)
                .setViewPlaneSize(200, 200)
                .setNumOfRays(50);
        ImageWriter imageWriter = new ImageWriter("bvh/teapot", 800, 800);
        Render render = new Render() //
                .setCamera(camera) //
                .setImageWriter(imageWriter) //
                .setMultithreading(3)
                .setPrintPercent(true)
                .setRayTracer(new BasicRayTracer(scene).useBoundingBoxes(true));
        render.renderImage();
        render.writeToImage();
    }

    private static Geometries getTeapotModel(Color color, Material mat) {
        return new Geometries( //
                new Triangle(pnts[7], pnts[6], pnts[1]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[1], pnts[2], pnts[7]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[8], pnts[7], pnts[2]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[2], pnts[3], pnts[8]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[9], pnts[8], pnts[3]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[3], pnts[4], pnts[9]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[10], pnts[9], pnts[4]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[4], pnts[5], pnts[10]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[12], pnts[11], pnts[6]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[6], pnts[7], pnts[12]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[13], pnts[12], pnts[7]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[7], pnts[8], pnts[13]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[14], pnts[13], pnts[8]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[8], pnts[9], pnts[14]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[15], pnts[14], pnts[9]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[9], pnts[10], pnts[15]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[17], pnts[16], pnts[11]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[11], pnts[12], pnts[17]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[18], pnts[17], pnts[12]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[12], pnts[13], pnts[18]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[19], pnts[18], pnts[13]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[13], pnts[14], pnts[19]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[20], pnts[19], pnts[14]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[14], pnts[15], pnts[20]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[22], pnts[21], pnts[16]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[16], pnts[17], pnts[22]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[23], pnts[22], pnts[17]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[17], pnts[18], pnts[23]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[24], pnts[23], pnts[18]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[18], pnts[19], pnts[24]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[25], pnts[24], pnts[19]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[19], pnts[20], pnts[25]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[27], pnts[26], pnts[21]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[21], pnts[22], pnts[27]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[28], pnts[27], pnts[22]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[22], pnts[23], pnts[28]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[29], pnts[28], pnts[23]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[23], pnts[24], pnts[29]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[30], pnts[29], pnts[24]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[24], pnts[25], pnts[30]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[32], pnts[31], pnts[26]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[26], pnts[27], pnts[32]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[33], pnts[32], pnts[27]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[27], pnts[28], pnts[33]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[34], pnts[33], pnts[28]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[28], pnts[29], pnts[34]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[35], pnts[34], pnts[29]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[29], pnts[30], pnts[35]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[37], pnts[36], pnts[31]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[31], pnts[32], pnts[37]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[38], pnts[37], pnts[32]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[32], pnts[33], pnts[38]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[39], pnts[38], pnts[33]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[33], pnts[34], pnts[39]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[40], pnts[39], pnts[34]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[34], pnts[35], pnts[40]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[42], pnts[41], pnts[36]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[36], pnts[37], pnts[42]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[43], pnts[42], pnts[37]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[37], pnts[38], pnts[43]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[44], pnts[43], pnts[38]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[38], pnts[39], pnts[44]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[45], pnts[44], pnts[39]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[39], pnts[40], pnts[45]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[47], pnts[46], pnts[41]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[41], pnts[42], pnts[47]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[48], pnts[47], pnts[42]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[42], pnts[43], pnts[48]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[49], pnts[48], pnts[43]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[43], pnts[44], pnts[49]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[50], pnts[49], pnts[44]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[44], pnts[45], pnts[50]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[52], pnts[51], pnts[46]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[46], pnts[47], pnts[52]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[53], pnts[52], pnts[47]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[47], pnts[48], pnts[53]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[54], pnts[53], pnts[48]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[48], pnts[49], pnts[54]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[55], pnts[54], pnts[49]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[49], pnts[50], pnts[55]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[57], pnts[56], pnts[51]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[51], pnts[52], pnts[57]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[58], pnts[57], pnts[52]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[52], pnts[53], pnts[58]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[59], pnts[58], pnts[53]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[53], pnts[54], pnts[59]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[60], pnts[59], pnts[54]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[54], pnts[55], pnts[60]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[62], pnts[61], pnts[56]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[56], pnts[57], pnts[62]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[63], pnts[62], pnts[57]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[57], pnts[58], pnts[63]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[64], pnts[63], pnts[58]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[58], pnts[59], pnts[64]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[65], pnts[64], pnts[59]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[59], pnts[60], pnts[65]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[67], pnts[66], pnts[61]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[61], pnts[62], pnts[67]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[68], pnts[67], pnts[62]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[62], pnts[63], pnts[68]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[69], pnts[68], pnts[63]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[63], pnts[64], pnts[69]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[70], pnts[69], pnts[64]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[64], pnts[65], pnts[70]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[72], pnts[71], pnts[66]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[66], pnts[67], pnts[72]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[73], pnts[72], pnts[67]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[67], pnts[68], pnts[73]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[74], pnts[73], pnts[68]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[68], pnts[69], pnts[74]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[75], pnts[74], pnts[69]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[69], pnts[70], pnts[75]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[77], pnts[76], pnts[71]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[71], pnts[72], pnts[77]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[78], pnts[77], pnts[72]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[72], pnts[73], pnts[78]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[79], pnts[78], pnts[73]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[73], pnts[74], pnts[79]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[80], pnts[79], pnts[74]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[74], pnts[75], pnts[80]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[2], pnts[1], pnts[76]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[76], pnts[77], pnts[2]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[3], pnts[2], pnts[77]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[77], pnts[78], pnts[3]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[4], pnts[3], pnts[78]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[78], pnts[79], pnts[4]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[5], pnts[4], pnts[79]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[79], pnts[80], pnts[5]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[85], pnts[10], pnts[5]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[5], pnts[81], pnts[85]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[86], pnts[85], pnts[81]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[81], pnts[82], pnts[86]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[87], pnts[86], pnts[82]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[82], pnts[83], pnts[87]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[88], pnts[87], pnts[83]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[83], pnts[84], pnts[88]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[89], pnts[15], pnts[10]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[10], pnts[85], pnts[89]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[90], pnts[89], pnts[85]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[85], pnts[86], pnts[90]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[91], pnts[90], pnts[86]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[86], pnts[87], pnts[91]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[92], pnts[91], pnts[87]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[87], pnts[88], pnts[92]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[93], pnts[20], pnts[15]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[15], pnts[89], pnts[93]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[94], pnts[93], pnts[89]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[89], pnts[90], pnts[94]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[95], pnts[94], pnts[90]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[90], pnts[91], pnts[95]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[96], pnts[95], pnts[91]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[91], pnts[92], pnts[96]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[97], pnts[25], pnts[20]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[20], pnts[93], pnts[97]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[98], pnts[97], pnts[93]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[93], pnts[94], pnts[98]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[99], pnts[98], pnts[94]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[94], pnts[95], pnts[99]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[100], pnts[99], pnts[95]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[95], pnts[96], pnts[100]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[101], pnts[30], pnts[25]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[25], pnts[97], pnts[101]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[102], pnts[101], pnts[97]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[97], pnts[98], pnts[102]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[103], pnts[102], pnts[98]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[98], pnts[99], pnts[103]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[104], pnts[103], pnts[99]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[99], pnts[100], pnts[104]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[105], pnts[35], pnts[30]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[30], pnts[101], pnts[105]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[106], pnts[105], pnts[101]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[101], pnts[102], pnts[106]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[107], pnts[106], pnts[102]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[102], pnts[103], pnts[107]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[108], pnts[107], pnts[103]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[103], pnts[104], pnts[108]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[109], pnts[40], pnts[35]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[35], pnts[105], pnts[109]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[110], pnts[109], pnts[105]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[105], pnts[106], pnts[110]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[111], pnts[110], pnts[106]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[106], pnts[107], pnts[111]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[112], pnts[111], pnts[107]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[107], pnts[108], pnts[112]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[113], pnts[45], pnts[40]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[40], pnts[109], pnts[113]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[114], pnts[113], pnts[109]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[109], pnts[110], pnts[114]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[115], pnts[114], pnts[110]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[110], pnts[111], pnts[115]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[116], pnts[115], pnts[111]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[111], pnts[112], pnts[116]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[117], pnts[50], pnts[45]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[45], pnts[113], pnts[117]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[118], pnts[117], pnts[113]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[113], pnts[114], pnts[118]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[119], pnts[118], pnts[114]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[114], pnts[115], pnts[119]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[120], pnts[119], pnts[115]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[115], pnts[116], pnts[120]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[121], pnts[55], pnts[50]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[50], pnts[117], pnts[121]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[122], pnts[121], pnts[117]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[117], pnts[118], pnts[122]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[123], pnts[122], pnts[118]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[118], pnts[119], pnts[123]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[124], pnts[123], pnts[119]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[119], pnts[120], pnts[124]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[125], pnts[60], pnts[55]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[55], pnts[121], pnts[125]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[126], pnts[125], pnts[121]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[121], pnts[122], pnts[126]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[127], pnts[126], pnts[122]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[122], pnts[123], pnts[127]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[128], pnts[127], pnts[123]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[123], pnts[124], pnts[128]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[129], pnts[65], pnts[60]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[60], pnts[125], pnts[129]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[130], pnts[129], pnts[125]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[125], pnts[126], pnts[130]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[131], pnts[130], pnts[126]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[126], pnts[127], pnts[131]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[132], pnts[131], pnts[127]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[127], pnts[128], pnts[132]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[133], pnts[70], pnts[65]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[65], pnts[129], pnts[133]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[134], pnts[133], pnts[129]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[129], pnts[130], pnts[134]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[135], pnts[134], pnts[130]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[130], pnts[131], pnts[135]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[136], pnts[135], pnts[131]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[131], pnts[132], pnts[136]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[137], pnts[75], pnts[70]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[70], pnts[133], pnts[137]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[138], pnts[137], pnts[133]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[133], pnts[134], pnts[138]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[139], pnts[138], pnts[134]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[134], pnts[135], pnts[139]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[140], pnts[139], pnts[135]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[135], pnts[136], pnts[140]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[141], pnts[80], pnts[75]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[75], pnts[137], pnts[141]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[142], pnts[141], pnts[137]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[137], pnts[138], pnts[142]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[143], pnts[142], pnts[138]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[138], pnts[139], pnts[143]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[144], pnts[143], pnts[139]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[139], pnts[140], pnts[144]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[81], pnts[5], pnts[80]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[80], pnts[141], pnts[81]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[82], pnts[81], pnts[141]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[141], pnts[142], pnts[82]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[83], pnts[82], pnts[142]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[142], pnts[143], pnts[83]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[84], pnts[83], pnts[143]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[143], pnts[144], pnts[84]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[149], pnts[88], pnts[84]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[84], pnts[145], pnts[149]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[150], pnts[149], pnts[145]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[145], pnts[146], pnts[150]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[151], pnts[150], pnts[146]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[146], pnts[147], pnts[151]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[152], pnts[151], pnts[147]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[147], pnts[148], pnts[152]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[153], pnts[92], pnts[88]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[88], pnts[149], pnts[153]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[154], pnts[153], pnts[149]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[149], pnts[150], pnts[154]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[155], pnts[154], pnts[150]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[150], pnts[151], pnts[155]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[156], pnts[155], pnts[151]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[151], pnts[152], pnts[156]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[157], pnts[96], pnts[92]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[92], pnts[153], pnts[157]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[158], pnts[157], pnts[153]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[153], pnts[154], pnts[158]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[159], pnts[158], pnts[154]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[154], pnts[155], pnts[159]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[160], pnts[159], pnts[155]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[155], pnts[156], pnts[160]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[161], pnts[100], pnts[96]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[96], pnts[157], pnts[161]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[162], pnts[161], pnts[157]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[157], pnts[158], pnts[162]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[163], pnts[162], pnts[158]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[158], pnts[159], pnts[163]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[164], pnts[163], pnts[159]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[159], pnts[160], pnts[164]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[165], pnts[104], pnts[100]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[100], pnts[161], pnts[165]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[166], pnts[165], pnts[161]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[161], pnts[162], pnts[166]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[167], pnts[166], pnts[162]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[162], pnts[163], pnts[167]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[168], pnts[167], pnts[163]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[163], pnts[164], pnts[168]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[169], pnts[108], pnts[104]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[104], pnts[165], pnts[169]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[170], pnts[169], pnts[165]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[165], pnts[166], pnts[170]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[171], pnts[170], pnts[166]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[166], pnts[167], pnts[171]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[172], pnts[171], pnts[167]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[167], pnts[168], pnts[172]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[173], pnts[112], pnts[108]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[108], pnts[169], pnts[173]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[174], pnts[173], pnts[169]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[169], pnts[170], pnts[174]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[175], pnts[174], pnts[170]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[170], pnts[171], pnts[175]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[176], pnts[175], pnts[171]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[171], pnts[172], pnts[176]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[177], pnts[116], pnts[112]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[112], pnts[173], pnts[177]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[178], pnts[177], pnts[173]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[173], pnts[174], pnts[178]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[179], pnts[178], pnts[174]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[174], pnts[175], pnts[179]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[180], pnts[179], pnts[175]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[175], pnts[176], pnts[180]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[181], pnts[120], pnts[116]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[116], pnts[177], pnts[181]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[182], pnts[181], pnts[177]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[177], pnts[178], pnts[182]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[183], pnts[182], pnts[178]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[178], pnts[179], pnts[183]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[184], pnts[183], pnts[179]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[179], pnts[180], pnts[184]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[185], pnts[124], pnts[120]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[120], pnts[181], pnts[185]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[186], pnts[185], pnts[181]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[181], pnts[182], pnts[186]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[187], pnts[186], pnts[182]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[182], pnts[183], pnts[187]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[188], pnts[187], pnts[183]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[183], pnts[184], pnts[188]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[189], pnts[128], pnts[124]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[124], pnts[185], pnts[189]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[190], pnts[189], pnts[185]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[185], pnts[186], pnts[190]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[191], pnts[190], pnts[186]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[186], pnts[187], pnts[191]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[192], pnts[191], pnts[187]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[187], pnts[188], pnts[192]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[193], pnts[132], pnts[128]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[128], pnts[189], pnts[193]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[194], pnts[193], pnts[189]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[189], pnts[190], pnts[194]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[195], pnts[194], pnts[190]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[190], pnts[191], pnts[195]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[196], pnts[195], pnts[191]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[191], pnts[192], pnts[196]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[197], pnts[136], pnts[132]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[132], pnts[193], pnts[197]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[198], pnts[197], pnts[193]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[193], pnts[194], pnts[198]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[199], pnts[198], pnts[194]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[194], pnts[195], pnts[199]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[200], pnts[199], pnts[195]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[195], pnts[196], pnts[200]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[201], pnts[140], pnts[136]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[136], pnts[197], pnts[201]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[202], pnts[201], pnts[197]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[197], pnts[198], pnts[202]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[203], pnts[202], pnts[198]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[198], pnts[199], pnts[203]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[204], pnts[203], pnts[199]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[199], pnts[200], pnts[204]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[205], pnts[144], pnts[140]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[140], pnts[201], pnts[205]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[206], pnts[205], pnts[201]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[201], pnts[202], pnts[206]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[207], pnts[206], pnts[202]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[202], pnts[203], pnts[207]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[208], pnts[207], pnts[203]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[203], pnts[204], pnts[208]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[145], pnts[84], pnts[144]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[144], pnts[205], pnts[145]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[146], pnts[145], pnts[205]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[205], pnts[206], pnts[146]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[147], pnts[146], pnts[206]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[206], pnts[207], pnts[147]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[148], pnts[147], pnts[207]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[207], pnts[208], pnts[148]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[213], pnts[152], pnts[148]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[148], pnts[209], pnts[213]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[214], pnts[213], pnts[209]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[209], pnts[210], pnts[214]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[215], pnts[214], pnts[210]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[210], pnts[211], pnts[215]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[215], pnts[211]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[216], pnts[156], pnts[152]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[152], pnts[213], pnts[216]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[217], pnts[216], pnts[213]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[213], pnts[214], pnts[217]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[218], pnts[217], pnts[214]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[214], pnts[215], pnts[218]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[218], pnts[215]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[219], pnts[160], pnts[156]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[156], pnts[216], pnts[219]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[220], pnts[219], pnts[216]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[216], pnts[217], pnts[220]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[221], pnts[220], pnts[217]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[217], pnts[218], pnts[221]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[221], pnts[218]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[222], pnts[164], pnts[160]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[160], pnts[219], pnts[222]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[223], pnts[222], pnts[219]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[219], pnts[220], pnts[223]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[224], pnts[223], pnts[220]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[220], pnts[221], pnts[224]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[224], pnts[221]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[225], pnts[168], pnts[164]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[164], pnts[222], pnts[225]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[226], pnts[225], pnts[222]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[222], pnts[223], pnts[226]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[227], pnts[226], pnts[223]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[223], pnts[224], pnts[227]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[227], pnts[224]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[228], pnts[172], pnts[168]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[168], pnts[225], pnts[228]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[229], pnts[228], pnts[225]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[225], pnts[226], pnts[229]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[230], pnts[229], pnts[226]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[226], pnts[227], pnts[230]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[230], pnts[227]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[231], pnts[176], pnts[172]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[172], pnts[228], pnts[231]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[232], pnts[231], pnts[228]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[228], pnts[229], pnts[232]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[233], pnts[232], pnts[229]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[229], pnts[230], pnts[233]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[233], pnts[230]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[234], pnts[180], pnts[176]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[176], pnts[231], pnts[234]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[235], pnts[234], pnts[231]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[231], pnts[232], pnts[235]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[236], pnts[235], pnts[232]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[232], pnts[233], pnts[236]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[236], pnts[233]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[237], pnts[184], pnts[180]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[180], pnts[234], pnts[237]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[238], pnts[237], pnts[234]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[234], pnts[235], pnts[238]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[239], pnts[238], pnts[235]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[235], pnts[236], pnts[239]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[239], pnts[236]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[240], pnts[188], pnts[184]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[184], pnts[237], pnts[240]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[241], pnts[240], pnts[237]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[237], pnts[238], pnts[241]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[242], pnts[241], pnts[238]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[238], pnts[239], pnts[242]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[242], pnts[239]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[243], pnts[192], pnts[188]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[188], pnts[240], pnts[243]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[244], pnts[243], pnts[240]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[240], pnts[241], pnts[244]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[245], pnts[244], pnts[241]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[241], pnts[242], pnts[245]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[245], pnts[242]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[246], pnts[196], pnts[192]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[192], pnts[243], pnts[246]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[247], pnts[246], pnts[243]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[243], pnts[244], pnts[247]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[248], pnts[247], pnts[244]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[244], pnts[245], pnts[248]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[248], pnts[245]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[249], pnts[200], pnts[196]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[196], pnts[246], pnts[249]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[250], pnts[249], pnts[246]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[246], pnts[247], pnts[250]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[251], pnts[250], pnts[247]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[247], pnts[248], pnts[251]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[251], pnts[248]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[252], pnts[204], pnts[200]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[200], pnts[249], pnts[252]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[253], pnts[252], pnts[249]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[249], pnts[250], pnts[253]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[254], pnts[253], pnts[250]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[250], pnts[251], pnts[254]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[254], pnts[251]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[255], pnts[208], pnts[204]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[204], pnts[252], pnts[255]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[256], pnts[255], pnts[252]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[252], pnts[253], pnts[256]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[257], pnts[256], pnts[253]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[253], pnts[254], pnts[257]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[257], pnts[254]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[209], pnts[148], pnts[208]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[208], pnts[255], pnts[209]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[210], pnts[209], pnts[255]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[255], pnts[256], pnts[210]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[211], pnts[210], pnts[256]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[256], pnts[257], pnts[211]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[212], pnts[211], pnts[257]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[264], pnts[263], pnts[258]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[258], pnts[259], pnts[264]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[265], pnts[264], pnts[259]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[259], pnts[260], pnts[265]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[266], pnts[265], pnts[260]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[260], pnts[261], pnts[266]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[267], pnts[266], pnts[261]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[261], pnts[262], pnts[267]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[269], pnts[268], pnts[263]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[263], pnts[264], pnts[269]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[270], pnts[269], pnts[264]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[264], pnts[265], pnts[270]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[271], pnts[270], pnts[265]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[265], pnts[266], pnts[271]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[272], pnts[271], pnts[266]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[266], pnts[267], pnts[272]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[274], pnts[273], pnts[268]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[268], pnts[269], pnts[274]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[275], pnts[274], pnts[269]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[269], pnts[270], pnts[275]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[276], pnts[275], pnts[270]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[270], pnts[271], pnts[276]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[277], pnts[276], pnts[271]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[271], pnts[272], pnts[277]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[279], pnts[278], pnts[273]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[273], pnts[274], pnts[279]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[280], pnts[279], pnts[274]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[274], pnts[275], pnts[280]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[281], pnts[280], pnts[275]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[275], pnts[276], pnts[281]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[282], pnts[281], pnts[276]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[276], pnts[277], pnts[282]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[284], pnts[283], pnts[278]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[278], pnts[279], pnts[284]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[285], pnts[284], pnts[279]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[279], pnts[280], pnts[285]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[286], pnts[285], pnts[280]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[280], pnts[281], pnts[286]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[287], pnts[286], pnts[281]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[281], pnts[282], pnts[287]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[289], pnts[288], pnts[283]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[283], pnts[284], pnts[289]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[290], pnts[289], pnts[284]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[284], pnts[285], pnts[290]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[291], pnts[290], pnts[285]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[285], pnts[286], pnts[291]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[292], pnts[291], pnts[286]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[286], pnts[287], pnts[292]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[294], pnts[293], pnts[288]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[288], pnts[289], pnts[294]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[295], pnts[294], pnts[289]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[289], pnts[290], pnts[295]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[296], pnts[295], pnts[290]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[290], pnts[291], pnts[296]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[297], pnts[296], pnts[291]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[291], pnts[292], pnts[297]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[259], pnts[258], pnts[293]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[293], pnts[294], pnts[259]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[260], pnts[259], pnts[294]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[294], pnts[295], pnts[260]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[261], pnts[260], pnts[295]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[295], pnts[296], pnts[261]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[262], pnts[261], pnts[296]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[296], pnts[297], pnts[262]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[302], pnts[267], pnts[262]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[262], pnts[298], pnts[302]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[303], pnts[302], pnts[298]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[298], pnts[299], pnts[303]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[304], pnts[303], pnts[299]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[299], pnts[300], pnts[304]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[305], pnts[304], pnts[300]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[300], pnts[301], pnts[305]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[306], pnts[272], pnts[267]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[267], pnts[302], pnts[306]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[307], pnts[306], pnts[302]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[302], pnts[303], pnts[307]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[308], pnts[307], pnts[303]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[303], pnts[304], pnts[308]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[309], pnts[308], pnts[304]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[304], pnts[305], pnts[309]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[310], pnts[277], pnts[272]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[272], pnts[306], pnts[310]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[311], pnts[310], pnts[306]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[306], pnts[307], pnts[311]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[312], pnts[311], pnts[307]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[307], pnts[308], pnts[312]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[313], pnts[312], pnts[308]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[308], pnts[309], pnts[313]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[314], pnts[282], pnts[277]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[277], pnts[310], pnts[314]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[315], pnts[314], pnts[310]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[310], pnts[311], pnts[315]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[316], pnts[315], pnts[311]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[311], pnts[312], pnts[316]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[317], pnts[316], pnts[312]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[312], pnts[313], pnts[317]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[318], pnts[287], pnts[282]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[282], pnts[314], pnts[318]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[319], pnts[318], pnts[314]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[314], pnts[315], pnts[319]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[320], pnts[319], pnts[315]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[315], pnts[316], pnts[320]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[321], pnts[320], pnts[316]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[316], pnts[317], pnts[321]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[322], pnts[292], pnts[287]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[287], pnts[318], pnts[322]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[323], pnts[322], pnts[318]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[318], pnts[319], pnts[323]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[324], pnts[323], pnts[319]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[319], pnts[320], pnts[324]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[325], pnts[324], pnts[320]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[320], pnts[321], pnts[325]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[326], pnts[297], pnts[292]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[292], pnts[322], pnts[326]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[327], pnts[326], pnts[322]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[322], pnts[323], pnts[327]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[328], pnts[327], pnts[323]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[323], pnts[324], pnts[328]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[329], pnts[328], pnts[324]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[324], pnts[325], pnts[329]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[298], pnts[262], pnts[297]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[297], pnts[326], pnts[298]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[299], pnts[298], pnts[326]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[326], pnts[327], pnts[299]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[300], pnts[299], pnts[327]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[327], pnts[328], pnts[300]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[301], pnts[300], pnts[328]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[328], pnts[329], pnts[301]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[336], pnts[335], pnts[330]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[330], pnts[331], pnts[336]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[337], pnts[336], pnts[331]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[331], pnts[332], pnts[337]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[338], pnts[337], pnts[332]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[332], pnts[333], pnts[338]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[339], pnts[338], pnts[333]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[333], pnts[334], pnts[339]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[341], pnts[340], pnts[335]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[335], pnts[336], pnts[341]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[342], pnts[341], pnts[336]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[336], pnts[337], pnts[342]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[343], pnts[342], pnts[337]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[337], pnts[338], pnts[343]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[344], pnts[343], pnts[338]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[338], pnts[339], pnts[344]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[346], pnts[345], pnts[340]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[340], pnts[341], pnts[346]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[347], pnts[346], pnts[341]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[341], pnts[342], pnts[347]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[348], pnts[347], pnts[342]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[342], pnts[343], pnts[348]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[349], pnts[348], pnts[343]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[343], pnts[344], pnts[349]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[351], pnts[350], pnts[345]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[345], pnts[346], pnts[351]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[352], pnts[351], pnts[346]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[346], pnts[347], pnts[352]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[353], pnts[352], pnts[347]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[347], pnts[348], pnts[353]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[354], pnts[353], pnts[348]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[348], pnts[349], pnts[354]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[356], pnts[355], pnts[350]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[350], pnts[351], pnts[356]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[357], pnts[356], pnts[351]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[351], pnts[352], pnts[357]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[358], pnts[357], pnts[352]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[352], pnts[353], pnts[358]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[359], pnts[358], pnts[353]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[353], pnts[354], pnts[359]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[361], pnts[360], pnts[355]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[355], pnts[356], pnts[361]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[362], pnts[361], pnts[356]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[356], pnts[357], pnts[362]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[363], pnts[362], pnts[357]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[357], pnts[358], pnts[363]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[364], pnts[363], pnts[358]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[358], pnts[359], pnts[364]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[366], pnts[365], pnts[360]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[360], pnts[361], pnts[366]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[367], pnts[366], pnts[361]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[361], pnts[362], pnts[367]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[368], pnts[367], pnts[362]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[362], pnts[363], pnts[368]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[369], pnts[368], pnts[363]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[363], pnts[364], pnts[369]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[331], pnts[330], pnts[365]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[365], pnts[366], pnts[331]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[332], pnts[331], pnts[366]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[366], pnts[367], pnts[332]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[333], pnts[332], pnts[367]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[367], pnts[368], pnts[333]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[334], pnts[333], pnts[368]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[368], pnts[369], pnts[334]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[374], pnts[339], pnts[334]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[334], pnts[370], pnts[374]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[375], pnts[374], pnts[370]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[370], pnts[371], pnts[375]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[376], pnts[375], pnts[371]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[371], pnts[372], pnts[376]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[377], pnts[376], pnts[372]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[372], pnts[373], pnts[377]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[378], pnts[344], pnts[339]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[339], pnts[374], pnts[378]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[379], pnts[378], pnts[374]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[374], pnts[375], pnts[379]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[380], pnts[379], pnts[375]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[375], pnts[376], pnts[380]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[381], pnts[380], pnts[376]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[376], pnts[377], pnts[381]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[382], pnts[349], pnts[344]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[344], pnts[378], pnts[382]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[383], pnts[382], pnts[378]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[378], pnts[379], pnts[383]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[384], pnts[383], pnts[379]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[379], pnts[380], pnts[384]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[385], pnts[384], pnts[380]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[380], pnts[381], pnts[385]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[386], pnts[354], pnts[349]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[349], pnts[382], pnts[386]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[387], pnts[386], pnts[382]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[382], pnts[383], pnts[387]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[388], pnts[387], pnts[383]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[383], pnts[384], pnts[388]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[389], pnts[388], pnts[384]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[384], pnts[385], pnts[389]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[390], pnts[359], pnts[354]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[354], pnts[386], pnts[390]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[391], pnts[390], pnts[386]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[386], pnts[387], pnts[391]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[392], pnts[391], pnts[387]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[387], pnts[388], pnts[392]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[393], pnts[392], pnts[388]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[388], pnts[389], pnts[393]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[394], pnts[364], pnts[359]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[359], pnts[390], pnts[394]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[395], pnts[394], pnts[390]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[390], pnts[391], pnts[395]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[396], pnts[395], pnts[391]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[391], pnts[392], pnts[396]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[397], pnts[396], pnts[392]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[392], pnts[393], pnts[397]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[398], pnts[369], pnts[364]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[364], pnts[394], pnts[398]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[399], pnts[398], pnts[394]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[394], pnts[395], pnts[399]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[400], pnts[399], pnts[395]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[395], pnts[396], pnts[400]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[401], pnts[400], pnts[396]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[396], pnts[397], pnts[401]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[370], pnts[334], pnts[369]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[369], pnts[398], pnts[370]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[371], pnts[370], pnts[398]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[398], pnts[399], pnts[371]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[372], pnts[371], pnts[399]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[399], pnts[400], pnts[372]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[373], pnts[372], pnts[400]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[400], pnts[401], pnts[373]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[403], pnts[407]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[408], pnts[407], pnts[403]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[403], pnts[404], pnts[408]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[409], pnts[408], pnts[404]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[404], pnts[405], pnts[409]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[410], pnts[409], pnts[405]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[405], pnts[406], pnts[410]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[407], pnts[411]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[412], pnts[411], pnts[407]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[407], pnts[408], pnts[412]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[413], pnts[412], pnts[408]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[408], pnts[409], pnts[413]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[414], pnts[413], pnts[409]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[409], pnts[410], pnts[414]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[411], pnts[415]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[416], pnts[415], pnts[411]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[411], pnts[412], pnts[416]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[417], pnts[416], pnts[412]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[412], pnts[413], pnts[417]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[418], pnts[417], pnts[413]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[413], pnts[414], pnts[418]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[415], pnts[419]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[420], pnts[419], pnts[415]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[415], pnts[416], pnts[420]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[421], pnts[420], pnts[416]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[416], pnts[417], pnts[421]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[422], pnts[421], pnts[417]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[417], pnts[418], pnts[422]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[419], pnts[423]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[424], pnts[423], pnts[419]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[419], pnts[420], pnts[424]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[425], pnts[424], pnts[420]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[420], pnts[421], pnts[425]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[426], pnts[425], pnts[421]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[421], pnts[422], pnts[426]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[423], pnts[427]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[428], pnts[427], pnts[423]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[423], pnts[424], pnts[428]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[429], pnts[428], pnts[424]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[424], pnts[425], pnts[429]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[430], pnts[429], pnts[425]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[425], pnts[426], pnts[430]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[427], pnts[431]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[432], pnts[431], pnts[427]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[427], pnts[428], pnts[432]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[433], pnts[432], pnts[428]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[428], pnts[429], pnts[433]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[434], pnts[433], pnts[429]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[429], pnts[430], pnts[434]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[431], pnts[435]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[436], pnts[435], pnts[431]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[431], pnts[432], pnts[436]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[437], pnts[436], pnts[432]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[432], pnts[433], pnts[437]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[438], pnts[437], pnts[433]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[433], pnts[434], pnts[438]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[435], pnts[439]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[440], pnts[439], pnts[435]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[435], pnts[436], pnts[440]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[441], pnts[440], pnts[436]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[436], pnts[437], pnts[441]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[442], pnts[441], pnts[437]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[437], pnts[438], pnts[442]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[439], pnts[443]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[444], pnts[443], pnts[439]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[439], pnts[440], pnts[444]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[445], pnts[444], pnts[440]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[440], pnts[441], pnts[445]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[446], pnts[445], pnts[441]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[441], pnts[442], pnts[446]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[443], pnts[447]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[448], pnts[447], pnts[443]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[443], pnts[444], pnts[448]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[449], pnts[448], pnts[444]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[444], pnts[445], pnts[449]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[450], pnts[449], pnts[445]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[445], pnts[446], pnts[450]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[447], pnts[451]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[452], pnts[451], pnts[447]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[447], pnts[448], pnts[452]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[453], pnts[452], pnts[448]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[448], pnts[449], pnts[453]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[454], pnts[453], pnts[449]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[449], pnts[450], pnts[454]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[451], pnts[455]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[456], pnts[455], pnts[451]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[451], pnts[452], pnts[456]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[457], pnts[456], pnts[452]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[452], pnts[453], pnts[457]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[458], pnts[457], pnts[453]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[453], pnts[454], pnts[458]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[455], pnts[459]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[460], pnts[459], pnts[455]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[455], pnts[456], pnts[460]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[461], pnts[460], pnts[456]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[456], pnts[457], pnts[461]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[462], pnts[461], pnts[457]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[457], pnts[458], pnts[462]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[459], pnts[463]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[464], pnts[463], pnts[459]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[459], pnts[460], pnts[464]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[465], pnts[464], pnts[460]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[460], pnts[461], pnts[465]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[466], pnts[465], pnts[461]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[461], pnts[462], pnts[466]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[402], pnts[463], pnts[403]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[404], pnts[403], pnts[463]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[463], pnts[464], pnts[404]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[405], pnts[404], pnts[464]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[464], pnts[465], pnts[405]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[406], pnts[405], pnts[465]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[465], pnts[466], pnts[406]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[471], pnts[410], pnts[406]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[406], pnts[467], pnts[471]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[472], pnts[471], pnts[467]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[467], pnts[468], pnts[472]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[473], pnts[472], pnts[468]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[468], pnts[469], pnts[473]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[474], pnts[473], pnts[469]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[469], pnts[470], pnts[474]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[475], pnts[414], pnts[410]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[410], pnts[471], pnts[475]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[476], pnts[475], pnts[471]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[471], pnts[472], pnts[476]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[477], pnts[476], pnts[472]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[472], pnts[473], pnts[477]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[478], pnts[477], pnts[473]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[473], pnts[474], pnts[478]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[479], pnts[418], pnts[414]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[414], pnts[475], pnts[479]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[480], pnts[479], pnts[475]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[475], pnts[476], pnts[480]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[481], pnts[480], pnts[476]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[476], pnts[477], pnts[481]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[482], pnts[481], pnts[477]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[477], pnts[478], pnts[482]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[483], pnts[422], pnts[418]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[418], pnts[479], pnts[483]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[484], pnts[483], pnts[479]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[479], pnts[480], pnts[484]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[485], pnts[484], pnts[480]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[480], pnts[481], pnts[485]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[486], pnts[485], pnts[481]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[481], pnts[482], pnts[486]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[487], pnts[426], pnts[422]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[422], pnts[483], pnts[487]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[488], pnts[487], pnts[483]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[483], pnts[484], pnts[488]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[489], pnts[488], pnts[484]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[484], pnts[485], pnts[489]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[490], pnts[489], pnts[485]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[485], pnts[486], pnts[490]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[491], pnts[430], pnts[426]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[426], pnts[487], pnts[491]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[492], pnts[491], pnts[487]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[487], pnts[488], pnts[492]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[493], pnts[492], pnts[488]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[488], pnts[489], pnts[493]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[494], pnts[493], pnts[489]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[489], pnts[490], pnts[494]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[495], pnts[434], pnts[430]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[430], pnts[491], pnts[495]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[496], pnts[495], pnts[491]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[491], pnts[492], pnts[496]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[497], pnts[496], pnts[492]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[492], pnts[493], pnts[497]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[498], pnts[497], pnts[493]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[493], pnts[494], pnts[498]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[499], pnts[438], pnts[434]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[434], pnts[495], pnts[499]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[500], pnts[499], pnts[495]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[495], pnts[496], pnts[500]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[501], pnts[500], pnts[496]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[496], pnts[497], pnts[501]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[502], pnts[501], pnts[497]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[497], pnts[498], pnts[502]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[503], pnts[442], pnts[438]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[438], pnts[499], pnts[503]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[504], pnts[503], pnts[499]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[499], pnts[500], pnts[504]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[505], pnts[504], pnts[500]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[500], pnts[501], pnts[505]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[506], pnts[505], pnts[501]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[501], pnts[502], pnts[506]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[507], pnts[446], pnts[442]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[442], pnts[503], pnts[507]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[508], pnts[507], pnts[503]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[503], pnts[504], pnts[508]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[509], pnts[508], pnts[504]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[504], pnts[505], pnts[509]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[510], pnts[509], pnts[505]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[505], pnts[506], pnts[510]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[511], pnts[450], pnts[446]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[446], pnts[507], pnts[511]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[512], pnts[511], pnts[507]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[507], pnts[508], pnts[512]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[513], pnts[512], pnts[508]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[508], pnts[509], pnts[513]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[514], pnts[513], pnts[509]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[509], pnts[510], pnts[514]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[515], pnts[454], pnts[450]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[450], pnts[511], pnts[515]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[516], pnts[515], pnts[511]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[511], pnts[512], pnts[516]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[517], pnts[516], pnts[512]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[512], pnts[513], pnts[517]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[518], pnts[517], pnts[513]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[513], pnts[514], pnts[518]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[519], pnts[458], pnts[454]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[454], pnts[515], pnts[519]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[520], pnts[519], pnts[515]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[515], pnts[516], pnts[520]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[521], pnts[520], pnts[516]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[516], pnts[517], pnts[521]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[522], pnts[521], pnts[517]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[517], pnts[518], pnts[522]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[523], pnts[462], pnts[458]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[458], pnts[519], pnts[523]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[524], pnts[523], pnts[519]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[519], pnts[520], pnts[524]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[525], pnts[524], pnts[520]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[520], pnts[521], pnts[525]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[526], pnts[525], pnts[521]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[521], pnts[522], pnts[526]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[527], pnts[466], pnts[462]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[462], pnts[523], pnts[527]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[528], pnts[527], pnts[523]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[523], pnts[524], pnts[528]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[529], pnts[528], pnts[524]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[524], pnts[525], pnts[529]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[530], pnts[529], pnts[525]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[525], pnts[526], pnts[530]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[467], pnts[406], pnts[466]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[466], pnts[527], pnts[467]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[468], pnts[467], pnts[527]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[527], pnts[528], pnts[468]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[469], pnts[468], pnts[528]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[528], pnts[529], pnts[469]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[470], pnts[469], pnts[529]).setEmission(color).setMaterial(mat), //
                new Triangle(pnts[529], pnts[530], pnts[470]).setEmission(color).setMaterial(mat) //
        );
    }


    private static final Point3D[] pnts = new Point3D[]{null, //
            new Point3D(40.6266, 28.3457, -1.10804), //
            new Point3D(40.0714, 30.4443, -1.10804), //
            new Point3D(40.7155, 31.1438, -1.10804), //
            new Point3D(42.0257, 30.4443, -1.10804), //
            new Point3D(43.4692, 28.3457, -1.10804), //
            new Point3D(37.5425, 28.3457, 14.5117), //
            new Point3D(37.0303, 30.4443, 14.2938), //
            new Point3D(37.6244, 31.1438, 14.5466), //
            new Point3D(38.8331, 30.4443, 15.0609), //
            new Point3D(40.1647, 28.3457, 15.6274), //
            new Point3D(29.0859, 28.3457, 27.1468), //
            new Point3D(28.6917, 30.4443, 26.7527), //
            new Point3D(29.149, 31.1438, 27.2099), //
            new Point3D(30.0792, 30.4443, 28.1402), //
            new Point3D(31.1041, 28.3457, 29.165), //
            new Point3D(16.4508, 28.3457, 35.6034), //
            new Point3D(16.2329, 30.4443, 35.0912), //
            new Point3D(16.4857, 31.1438, 35.6853), //
            new Point3D(16.9999, 30.4443, 36.894), //
            new Point3D(17.5665, 28.3457, 38.2256), //
            new Point3D(0.831025, 28.3457, 38.6876), //
            new Point3D(0.831025, 30.4443, 38.1324), //
            new Point3D(0.831025, 31.1438, 38.7764), //
            new Point3D(0.831025, 30.4443, 40.0866), //
            new Point3D(0.831025, 28.3457, 41.5301), //
            new Point3D(-15.868, 28.3457, 35.6034), //
            new Point3D(-15.0262, 30.4443, 35.0912), //
            new Point3D(-14.9585, 31.1438, 35.6853), //
            new Point3D(-15.3547, 30.4443, 36.894), //
            new Point3D(-15.9044, 28.3457, 38.2256), //
            new Point3D(-28.3832, 28.3457, 27.1468), //
            new Point3D(-27.4344, 30.4443, 26.7527), //
            new Point3D(-27.6068, 31.1438, 27.2099), //
            new Point3D(-28.4322, 30.4443, 28.1402), //
            new Point3D(-29.4421, 28.3457, 29.165), //
            new Point3D(-36.2402, 28.3457, 14.5117), //
            new Point3D(-35.52, 30.4443, 14.2938), //
            new Point3D(-36.0073, 31.1438, 14.5466), //
            new Point3D(-37.1767, 30.4443, 15.0609), //
            new Point3D(-38.5027, 28.3457, 15.6274), //
            new Point3D(-38.9646, 28.3457, -1.10804), //
            new Point3D(-38.4094, 30.4443, -1.10804), //
            new Point3D(-39.0534, 31.1438, -1.10804), //
            new Point3D(-40.3636, 30.4443, -1.10804), //
            new Point3D(-41.8071, 28.3457, -1.10804), //
            new Point3D(-35.8804, 28.3457, -16.7278), //
            new Point3D(-35.3683, 30.4443, -16.5099), //
            new Point3D(-35.9624, 31.1438, -16.7627), //
            new Point3D(-37.1711, 30.4443, -17.2769), //
            new Point3D(-38.5027, 28.3457, -17.8435), //
            new Point3D(-27.4238, 28.3457, -29.3629), //
            new Point3D(-27.0297, 30.4443, -28.9687), //
            new Point3D(-27.4869, 31.1438, -29.426), //
            new Point3D(-28.4172, 30.4443, -30.3562), //
            new Point3D(-29.4421, 28.3457, -31.3811), //
            new Point3D(-14.7887, 28.3457, -37.8195), //
            new Point3D(-14.5708, 30.4443, -37.3073), //
            new Point3D(-14.8236, 31.1438, -37.9014), //
            new Point3D(-15.3379, 30.4443, -39.1101), //
            new Point3D(-15.9044, 28.3457, -40.4417), //
            new Point3D(0.831025, 28.3457, -40.9036), //
            new Point3D(0.831025, 30.4443, -40.3484), //
            new Point3D(0.831025, 31.1438, -40.9925), //
            new Point3D(0.831025, 30.4443, -42.3027), //
            new Point3D(0.831025, 28.3457, -43.7462), //
            new Point3D(16.4508, 28.3457, -37.8195), //
            new Point3D(16.2329, 30.4443, -37.3073), //
            new Point3D(16.4857, 31.1438, -37.9014), //
            new Point3D(16.9999, 30.4443, -39.1101), //
            new Point3D(17.5665, 28.3457, -40.4417), //
            new Point3D(29.0859, 28.3457, -29.3629), //
            new Point3D(28.6917, 30.4443, -28.9687), //
            new Point3D(29.149, 31.1438, -29.426), //
            new Point3D(30.0792, 30.4443, -30.3562), //
            new Point3D(31.1041, 28.3457, -31.3811), //
            new Point3D(37.5425, 28.3457, -16.7278), //
            new Point3D(37.0303, 30.4443, -16.5099), //
            new Point3D(37.6244, 31.1438, -16.7627), //
            new Point3D(38.8331, 30.4443, -17.2769), //
            new Point3D(40.1647, 28.3457, -17.8435), //
            new Point3D(48.6879, 17.1865, -1.10804), //
            new Point3D(53.2404, 6.22714, -1.10804), //
            new Point3D(56.4605, -4.33246, -1.10804), //
            new Point3D(57.6819, -14.2925, -1.10804), //
            new Point3D(44.979, 17.1865, 17.6758), //
            new Point3D(49.1787, 6.22714, 19.4626), //
            new Point3D(52.1492, -4.33246, 20.7265), //
            new Point3D(53.2759, -14.2925, 21.2059), //
            new Point3D(34.8094, 17.1865, 32.8703), //
            new Point3D(38.0417, 6.22714, 36.1026), //
            new Point3D(40.3279, -4.33246, 38.3889), //
            new Point3D(41.1951, -14.2925, 39.2561), //
            new Point3D(19.6148, 17.1865, 43.0399), //
            new Point3D(21.4017, 6.22714, 47.2396), //
            new Point3D(22.6656, -4.33246, 50.2101), //
            new Point3D(23.145, -14.2925, 51.3369), //
            new Point3D(0.831025, 17.1865, 46.7488), //
            new Point3D(0.831025, 6.22714, 51.3013), //
            new Point3D(0.831025, -4.33246, 54.5214), //
            new Point3D(0.831025, -14.2925, 55.7428), //
            new Point3D(-17.9528, 17.1865, 43.0399), //
            new Point3D(-19.7397, 6.22714, 47.2396), //
            new Point3D(-21.0035, -4.33246, 50.2101), //
            new Point3D(-21.4829, -14.2925, 51.3369), //
            new Point3D(-33.1474, 17.1865, 32.8703), //
            new Point3D(-36.3796, 6.22714, 36.1026), //
            new Point3D(-38.6659, -4.33246, 38.3889), //
            new Point3D(-39.5331, -14.2925, 39.2561), //
            new Point3D(-43.3169, 17.1865, 17.6758), //
            new Point3D(-47.5166, 6.22714, 19.4626), //
            new Point3D(-50.4871, -4.33246, 20.7265), //
            new Point3D(-51.6139, -14.2925, 21.2059), //
            new Point3D(-47.0258, 17.1865, -1.10804), //
            new Point3D(-51.5784, 6.22714, -1.10804), //
            new Point3D(-54.7984, -4.33246, -1.10804), //
            new Point3D(-56.0198, -14.2925, -1.10804), //
            new Point3D(-43.3169, 17.1865, -19.8919), //
            new Point3D(-47.5166, 6.22714, -21.6787), //
            new Point3D(-50.4871, -4.33246, -22.9426), //
            new Point3D(-51.6139, -14.2925, -23.422), //
            new Point3D(-33.1474, 17.1865, -35.0864), //
            new Point3D(-36.3796, 6.22714, -38.3187), //
            new Point3D(-38.6659, -4.33246, -40.6049), //
            new Point3D(-39.5331, -14.2925, -41.4721), //
            new Point3D(-17.9528, 17.1865, -45.256), //
            new Point3D(-19.7397, 6.22714, -49.4557), //
            new Point3D(-21.0035, -4.33246, -52.4262), //
            new Point3D(-21.4829, -14.2925, -53.5529), //
            new Point3D(0.831025, 17.1865, -48.9649), //
            new Point3D(0.831025, 6.22714, -53.5174), //
            new Point3D(0.831025, -4.33246, -56.7375), //
            new Point3D(0.831025, -14.2925, -57.9589), //
            new Point3D(19.6148, 17.1865, -45.256), //
            new Point3D(21.4017, 6.22714, -49.4557), //
            new Point3D(22.6656, -4.33246, -52.4262), //
            new Point3D(23.145, -14.2925, -53.5529), //
            new Point3D(34.8094, 17.1865, -35.0864), //
            new Point3D(38.0417, 6.22714, -38.3187), //
            new Point3D(40.3279, -4.33246, -40.6049), //
            new Point3D(41.1951, -14.2925, -41.4721), //
            new Point3D(44.979, 17.1865, -19.8919), //
            new Point3D(49.1787, 6.22714, -21.6787), //
            new Point3D(52.1492, -4.33246, -22.9426), //
            new Point3D(53.2759, -14.2925, -23.422), //
            new Point3D(55.4611, -22.7202, -1.10804), //
            new Point3D(50.5755, -28.9493, -1.10804), //
            new Point3D(45.6899, -33.1798, -1.10804), //
            new Point3D(43.4692, -35.6115, -1.10804), //
            new Point3D(51.2273, -22.7202, 20.3343), //
            new Point3D(46.7203, -28.9493, 18.4167), //
            new Point3D(42.2133, -33.1798, 16.4991), //
            new Point3D(40.1647, -35.6115, 15.6274), //
            new Point3D(39.6184, -22.7202, 37.6793), //
            new Point3D(36.1496, -28.9493, 34.2106), //
            new Point3D(32.6808, -33.1798, 30.7418), //
            new Point3D(31.1041, -35.6115, 29.165), //
            new Point3D(22.2733, -22.7202, 49.2882), //
            new Point3D(20.3557, -28.9493, 44.7813), //
            new Point3D(18.4381, -33.1798, 40.2743), //
            new Point3D(17.5665, -35.6115, 38.2256), //
            new Point3D(0.831025, -22.7202, 53.5221), //
            new Point3D(0.831025, -28.9493, 48.6365), //
            new Point3D(0.831025, -33.1798, 43.7508), //
            new Point3D(0.831025, -35.6115, 41.5301), //
            new Point3D(-20.6113, -22.7202, 49.2882), //
            new Point3D(-18.6937, -28.9493, 44.7813), //
            new Point3D(-16.7761, -33.1798, 40.2743), //
            new Point3D(-15.9044, -35.6115, 38.2256), //
            new Point3D(-37.9564, -22.7202, 37.6793), //
            new Point3D(-34.4876, -28.9493, 34.2106), //
            new Point3D(-31.0188, -33.1798, 30.7418), //
            new Point3D(-29.4421, -35.6115, 29.165), //
            new Point3D(-49.5653, -22.7202, 20.3343), //
            new Point3D(-45.0583, -28.9493, 18.4167), //
            new Point3D(-40.5513, -33.1798, 16.4991), //
            new Point3D(-38.5027, -35.6115, 15.6274), //
            new Point3D(-53.7991, -22.7202, -1.10804), //
            new Point3D(-48.9135, -28.9493, -1.10804), //
            new Point3D(-44.0279, -33.1798, -1.10804), //
            new Point3D(-41.8071, -35.6115, -1.10804), //
            new Point3D(-49.5653, -22.7202, -22.5504), //
            new Point3D(-45.0583, -28.9493, -20.6327), //
            new Point3D(-40.5513, -33.1798, -18.7151), //
            new Point3D(-38.5027, -35.6115, -17.8435), //
            new Point3D(-37.9564, -22.7202, -39.8954), //
            new Point3D(-34.4876, -28.9493, -36.4266), //
            new Point3D(-31.0188, -33.1798, -32.9578), //
            new Point3D(-29.4421, -35.6115, -31.3811), //
            new Point3D(-20.6113, -22.7202, -51.5043), //
            new Point3D(-18.6937, -28.9493, -46.9973), //
            new Point3D(-16.7761, -33.1798, -42.4903), //
            new Point3D(-15.9044, -35.6115, -40.4417), //
            new Point3D(0.831025, -22.7202, -55.7382), //
            new Point3D(0.831025, -28.9493, -50.8525), //
            new Point3D(0.831025, -33.1798, -45.9669), //
            new Point3D(0.831025, -35.6115, -43.7462), //
            new Point3D(22.2733, -22.7202, -51.5043), //
            new Point3D(20.3557, -28.9493, -46.9973), //
            new Point3D(18.4381, -33.1798, -42.4903), //
            new Point3D(17.5665, -35.6115, -40.4417), //
            new Point3D(39.6184, -22.7202, -39.8954), //
            new Point3D(36.1496, -28.9493, -36.4266), //
            new Point3D(32.6808, -33.1798, -32.9578), //
            new Point3D(31.1041, -35.6115, -31.3811), //
            new Point3D(51.2273, -22.7202, -22.5504), //
            new Point3D(46.7203, -28.9493, -20.6327), //
            new Point3D(42.2133, -33.1798, -18.7151), //
            new Point3D(40.1647, -35.6115, -17.8435), //
            new Point3D(42.5031, -37.1772, -1.10804), //
            new Point3D(37.3399, -38.5429, -1.10804), //
            new Point3D(24.5818, -39.5089, -1.10804), //
            new Point3D(0.831025, -39.8754, -1.10804), //
            new Point3D(39.2736, -37.1772, 15.2483), //
            new Point3D(34.5105, -38.5429, 13.2217), //
            new Point3D(22.7411, -39.5089, 8.21414), //
            new Point3D(30.4182, -37.1772, 28.4792), //
            new Point3D(26.7523, -38.5429, 24.8133), //
            new Point3D(17.6941, -39.5089, 15.755), //
            new Point3D(17.1873, -37.1772, 37.3345), //
            new Point3D(15.1608, -38.5429, 32.5714), //
            new Point3D(10.1532, -39.5089, 20.8021), //
            new Point3D(0.831025, -37.1772, 40.5641), //
            new Point3D(0.831025, -38.5429, 35.4009), //
            new Point3D(0.831025, -39.5089, 22.6427), //
            new Point3D(-15.5253, -37.1772, 37.3345), //
            new Point3D(-13.4987, -38.5429, 32.5714), //
            new Point3D(-8.49115, -39.5089, 20.8021), //
            new Point3D(-28.7562, -37.1772, 28.4792), //
            new Point3D(-25.0903, -38.5429, 24.8133), //
            new Point3D(-16.032, -39.5089, 15.755), //
            new Point3D(-37.6115, -37.1772, 15.2483), //
            new Point3D(-32.8484, -38.5429, 13.2217), //
            new Point3D(-21.0791, -39.5089, 8.21414), //
            new Point3D(-40.8411, -37.1772, -1.10804), //
            new Point3D(-35.6779, -38.5429, -1.10804), //
            new Point3D(-22.9198, -39.5089, -1.10804), //
            new Point3D(-37.6115, -37.1772, -17.4643), //
            new Point3D(-32.8484, -38.5429, -15.4378), //
            new Point3D(-21.0791, -39.5089, -10.4302), //
            new Point3D(-28.7562, -37.1772, -30.6952), //
            new Point3D(-25.0903, -38.5429, -27.0294), //
            new Point3D(-16.032, -39.5089, -17.9711), //
            new Point3D(-15.5253, -37.1772, -39.5506), //
            new Point3D(-13.4987, -38.5429, -34.7875), //
            new Point3D(-8.49115, -39.5089, -23.0181), //
            new Point3D(0.831025, -37.1772, -42.7802), //
            new Point3D(0.831025, -38.5429, -37.6169), //
            new Point3D(0.831025, -39.5089, -24.8588), //
            new Point3D(17.1873, -37.1772, -39.5506), //
            new Point3D(15.1608, -38.5429, -34.7875), //
            new Point3D(10.1532, -39.5089, -23.0181), //
            new Point3D(30.4182, -37.1772, -30.6952), //
            new Point3D(26.7523, -38.5429, -27.0294), //
            new Point3D(17.6941, -39.5089, -17.9711), //
            new Point3D(39.2736, -37.1772, -17.4643), //
            new Point3D(34.5105, -38.5429, -15.4378), //
            new Point3D(22.7411, -39.5089, -10.4302), //
            new Point3D(-44.6497, 17.6861, -1.10804), //
            new Point3D(-57.9297, 17.5862, -1.10804), //
            new Point3D(-67.7453, 16.8867, -1.10804), //
            new Point3D(-73.8301, 14.9879, -1.10804), //
            new Point3D(-75.9176, 11.2904, -1.10804), //
            new Point3D(-44.2055, 18.6855, 3.68876), //
            new Point3D(-58.3252, 18.5699, 3.68876), //
            new Point3D(-68.6891, 17.7611, 3.68876), //
            new Point3D(-75.0724, 15.5657, 3.68876), //
            new Point3D(-77.2501, 11.2904, 3.68876), //
            new Point3D(-43.2284, 20.884, 5.28769), //
            new Point3D(-59.1955, 20.7341, 5.28769), //
            new Point3D(-70.7655, 19.6848, 5.28769), //
            new Point3D(-77.8053, 16.8367, 5.28769), //
            new Point3D(-80.1814, 11.2904, 5.28769), //
            new Point3D(-42.2513, 23.0825, 3.68876), //
            new Point3D(-60.0657, 22.8983, 3.68876), //
            new Point3D(-72.8419, 21.6085, 3.68876), //
            new Point3D(-80.5381, 18.1077, 3.68876), //
            new Point3D(-83.1128, 11.2904, 3.68876), //
            new Point3D(-41.8071, 24.0819, -1.10804), //
            new Point3D(-60.4613, 23.882, -1.10804), //
            new Point3D(-73.7857, 22.4829, -1.10804), //
            new Point3D(-81.7804, 18.6855, -1.10804), //
            new Point3D(-84.4453, 11.2904, -1.10804), //
            new Point3D(-42.2513, 23.0825, -5.90483), //
            new Point3D(-60.0657, 22.8983, -5.90483), //
            new Point3D(-72.8419, 21.6085, -5.90483), //
            new Point3D(-80.5381, 18.1077, -5.90483), //
            new Point3D(-83.1128, 11.2904, -5.90483), //
            new Point3D(-43.2284, 20.884, -7.50376), //
            new Point3D(-59.1955, 20.7341, -7.50376), //
            new Point3D(-70.7655, 19.6848, -7.50376), //
            new Point3D(-77.8053, 16.8367, -7.50376), //
            new Point3D(-80.1814, 11.2904, -7.50376), //
            new Point3D(-44.2055, 18.6855, -5.90483), //
            new Point3D(-58.3252, 18.5699, -5.90483), //
            new Point3D(-68.6891, 17.7611, -5.90483), //
            new Point3D(-75.0724, 15.5657, -5.90483), //
            new Point3D(-77.2501, 11.2904, -5.90483), //
            new Point3D(-74.8073, 5.4943, -1.10804), //
            new Point3D(-71.2985, -1.50103, -1.10804), //
            new Point3D(-65.1248, -8.49634, -1.10804), //
            new Point3D(-56.0198, -14.2925, -1.10804), //
            new Point3D(-76.0183, 4.93477, 3.68876), //
            new Point3D(-72.159, -2.35462, 3.68876), //
            new Point3D(-65.4267, -9.55033, 3.68876), //
            new Point3D(-55.5757, -15.6249, 3.68876), //
            new Point3D(-78.6824, 3.70383, 5.28769), //
            new Point3D(-74.0522, -4.23253, 5.28769), //
            new Point3D(-66.0909, -11.8691, 5.28769), //
            new Point3D(-54.5986, -18.5563, 5.28769), //
            new Point3D(-81.3466, 2.47288, 3.68876), //
            new Point3D(-75.9454, -6.11044, 3.68876), //
            new Point3D(-66.755, -14.1878, 3.68876), //
            new Point3D(-53.6214, -21.4877, 3.68876), //
            new Point3D(-82.5576, 1.91336, -1.10804), //
            new Point3D(-76.8059, -6.96404, -1.10804), //
            new Point3D(-67.0569, -15.2418, -1.10804), //
            new Point3D(-53.1773, -22.8201, -1.10804), //
            new Point3D(-81.3466, 2.47288, -5.90483), //
            new Point3D(-75.9454, -6.11044, -5.90483), //
            new Point3D(-66.755, -14.1878, -5.90483), //
            new Point3D(-53.6214, -21.4877, -5.90483), //
            new Point3D(-78.6824, 3.70383, -7.50376), //
            new Point3D(-74.0522, -4.23253, -7.50376), //
            new Point3D(-66.0909, -11.8691, -7.50376), //
            new Point3D(-54.5986, -18.5563, -7.50376), //
            new Point3D(-76.0183, 4.93477, -5.90483), //
            new Point3D(-72.159, -2.35462, -5.90483), //
            new Point3D(-65.4267, -9.55033, -5.90483), //
            new Point3D(-55.5757, -15.6249, -5.90483), //
            new Point3D(49.1543, 0.630882, -1.10804), //
            new Point3D(62.7896, 3.76212, -1.10804), //
            new Point3D(68.6967, 11.2904, -1.10804), //
            new Point3D(71.939, 20.4176, -1.10804), //
            new Point3D(77.5797, 28.3457, -1.10804), //
            new Point3D(49.1543, -3.03333, 9.4449), //
            new Point3D(63.8305, 1.04519, 8.42059), //
            new Point3D(70.0292, 9.70814, 6.1671), //
            new Point3D(73.5629, 19.8451, 3.91361), //
            new Point3D(80.2446, 28.3457, 2.88929), //
            new Point3D(49.1543, -11.0946, 12.9626), //
            new Point3D(66.1207, -4.93206, 11.5968), //
            new Point3D(72.9605, 6.22714, 8.59214), //
            new Point3D(77.1355, 18.5855, 5.58749), //
            new Point3D(86.1073, 28.3457, 4.22173), //
            new Point3D(49.1543, -19.1559, 9.4449), //
            new Point3D(68.4108, -10.9093, 8.42059), //
            new Point3D(75.8919, 2.74614, 6.1671), //
            new Point3D(80.7081, 17.326, 3.91361), //
            new Point3D(91.97, 28.3457, 2.88929), //
            new Point3D(49.1543, -22.8201, -1.10804), //
            new Point3D(69.4518, -13.6262, -1.10804), //
            new Point3D(77.2244, 1.16386, -1.10804), //
            new Point3D(82.3321, 16.7534, -1.10804), //
            new Point3D(94.6349, 28.3457, -1.10804), //
            new Point3D(49.1543, -19.1559, -11.661), //
            new Point3D(68.4108, -10.9093, -10.6367), //
            new Point3D(75.8919, 2.74614, -8.38317), //
            new Point3D(80.7081, 17.326, -6.12968), //
            new Point3D(91.97, 28.3457, -5.10536), //
            new Point3D(49.1543, -11.0946, -15.1786), //
            new Point3D(66.1207, -4.93206, -13.8129), //
            new Point3D(72.9605, 6.22714, -10.8082), //
            new Point3D(77.1355, 18.5855, -7.80356), //
            new Point3D(86.1073, 28.3457, -6.4378), //
            new Point3D(49.1543, -3.03333, -11.661), //
            new Point3D(63.8305, 1.04519, -10.6367), //
            new Point3D(70.0292, 9.70814, -8.38317), //
            new Point3D(73.5629, 19.8451, -6.12968), //
            new Point3D(80.2446, 28.3457, -5.10536), //
            new Point3D(79.6227, 29.5449, -1.10804), //
            new Point3D(81.1329, 29.9446, -1.10804), //
            new Point3D(81.577, 29.5449, -1.10804), //
            new Point3D(80.4222, 28.3457, -1.10804), //
            new Point3D(82.4767, 29.6034, 2.63946), //
            new Point3D(83.8116, 30.0383, 2.08983), //
            new Point3D(83.8515, 29.6268, 1.54019), //
            new Point3D(82.1988, 28.3457, 1.29036), //
            new Point3D(88.7555, 29.7322, 3.88862), //
            new Point3D(89.7049, 30.2444, 3.15578), //
            new Point3D(88.8555, 29.8072, 2.42294), //
            new Point3D(86.1073, 28.3457, 2.08983), //
            new Point3D(95.0343, 29.8611, 2.63946), //
            new Point3D(95.5982, 30.4505, 2.08983), //
            new Point3D(93.8594, 29.9875, 1.54019), //
            new Point3D(90.0158, 28.3457, 1.29036), //
            new Point3D(97.8883, 29.9196, -1.10804), //
            new Point3D(98.2769, 30.5442, -1.10804), //
            new Point3D(96.1339, 30.0695, -1.10804), //
            new Point3D(91.7924, 28.3457, -1.10804), //
            new Point3D(95.0343, 29.8611, -4.85553), //
            new Point3D(95.5982, 30.4505, -4.3059), //
            new Point3D(93.8594, 29.9875, -3.75626), //
            new Point3D(90.0158, 28.3457, -3.50643), //
            new Point3D(88.7555, 29.7322, -6.10469), //
            new Point3D(89.7049, 30.2444, -5.37185), //
            new Point3D(88.8555, 29.8072, -4.63901), //
            new Point3D(86.1073, 28.3457, -4.3059), //
            new Point3D(82.4767, 29.6034, -4.85553), //
            new Point3D(83.8116, 30.0383, -4.3059), //
            new Point3D(83.8515, 29.6268, -3.75626), //
            new Point3D(82.1988, 28.3457, -3.50643), //
            new Point3D(0.831025, 49.6647, -1.10804), //
            new Point3D(10.5134, 48.2657, -1.10804), //
            new Point3D(10.0693, 44.868, -1.10804), //
            new Point3D(6.42728, 40.6708, -1.10804), //
            new Point3D(6.51611, 36.8733, -1.10804), //
            new Point3D(9.76642, 48.2657, 2.70243), //
            new Point3D(9.35632, 44.868, 2.52698), //
            new Point3D(5.9947, 40.6708, 1.09187), //
            new Point3D(6.07552, 36.8733, 1.12336), //
            new Point3D(7.71453, 48.2657, 5.77547), //
            new Point3D(7.39819, 44.868, 5.45913), //
            new Point3D(4.80736, 40.6708, 2.8683), //
            new Point3D(4.86744, 36.8733, 2.92838), //
            new Point3D(4.64149, 48.2657, 7.82736), //
            new Point3D(4.46604, 44.868, 7.41726), //
            new Point3D(3.03093, 40.6708, 4.05564), //
            new Point3D(3.06242, 36.8733, 4.13646), //
            new Point3D(0.831025, 48.2657, 8.57438), //
            new Point3D(0.831025, 44.868, 8.13023), //
            new Point3D(0.831025, 40.6708, 4.48822), //
            new Point3D(0.831025, 36.8733, 4.57705), //
            new Point3D(-2.97944, 48.2657, 7.82736), //
            new Point3D(-2.80399, 44.868, 7.41726), //
            new Point3D(-1.36888, 40.6708, 4.05564), //
            new Point3D(-1.40037, 36.8733, 4.13646), //
            new Point3D(-6.05248, 48.2657, 5.77547), //
            new Point3D(-5.73614, 44.868, 5.45913), //
            new Point3D(-3.14531, 40.6708, 2.8683), //
            new Point3D(-3.20539, 36.8733, 2.92838), //
            new Point3D(-8.10437, 48.2657, 2.70243), //
            new Point3D(-7.69427, 44.868, 2.52698), //
            new Point3D(-4.33265, 40.6708, 1.09187), //
            new Point3D(-4.41347, 36.8733, 1.12336), //
            new Point3D(-8.85139, 48.2657, -1.10804), //
            new Point3D(-8.40724, 44.868, -1.10804), //
            new Point3D(-4.76523, 40.6708, -1.10804), //
            new Point3D(-4.85406, 36.8733, -1.10804), //
            new Point3D(-8.10437, 48.2657, -4.9185), //
            new Point3D(-7.69427, 44.868, -4.74305), //
            new Point3D(-4.33265, 40.6708, -3.30794), //
            new Point3D(-4.41347, 36.8733, -3.33943), //
            new Point3D(-6.05248, 48.2657, -7.99154), //
            new Point3D(-5.73614, 44.868, -7.6752), //
            new Point3D(-3.14531, 40.6708, -5.08437), //
            new Point3D(-3.20539, 36.8733, -5.14445), //
            new Point3D(-2.97944, 48.2657, -10.0434), //
            new Point3D(-2.80399, 44.868, -9.63333), //
            new Point3D(-1.36888, 40.6708, -6.27171), //
            new Point3D(-1.40037, 36.8733, -6.35253), //
            new Point3D(0.831025, 48.2657, -10.7904), //
            new Point3D(0.831025, 44.868, -10.3463), //
            new Point3D(0.831025, 40.6708, -6.70429), //
            new Point3D(0.831025, 36.8733, -6.79312), //
            new Point3D(4.64149, 48.2657, -10.0434), //
            new Point3D(4.46604, 44.868, -9.63333), //
            new Point3D(3.03093, 40.6708, -6.27171), //
            new Point3D(3.06242, 36.8733, -6.35253), //
            new Point3D(7.71453, 48.2657, -7.99154), //
            new Point3D(7.39819, 44.868, -7.6752), //
            new Point3D(4.80736, 40.6708, -5.08437), //
            new Point3D(4.86744, 36.8733, -5.14445), //
            new Point3D(9.76642, 48.2657, -4.9185), //
            new Point3D(9.35632, 44.868, -4.74305), //
            new Point3D(5.9947, 40.6708, -3.30794), //
            new Point3D(6.07552, 36.8733, -3.33943), //
            new Point3D(13.8001, 34.3417, -1.10804), //
            new Point3D(24.282, 32.6095, -1.10804), //
            new Point3D(33.6979, 30.8773, -1.10804), //
            new Point3D(37.7841, 28.3457, -1.10804), //
            new Point3D(12.795, 34.3417, 3.98234), //
            new Point3D(22.4646, 32.6095, 8.09647), //
            new Point3D(31.1507, 30.8773, 11.7922), //
            new Point3D(34.9202, 28.3457, 13.396), //
            new Point3D(10.0391, 34.3417, 8.10003), //
            new Point3D(17.4812, 32.6095, 15.5422), //
            new Point3D(24.1665, 30.8773, 22.2275), //
            new Point3D(27.0677, 28.3457, 25.1286), //
            new Point3D(5.9214, 34.3417, 10.856), //
            new Point3D(10.0355, 32.6095, 20.5255), //
            new Point3D(13.7313, 30.8773, 29.2117), //
            new Point3D(15.3351, 28.3457, 32.9812), //
            new Point3D(0.831025, 34.3417, 11.8611), //
            new Point3D(0.831025, 32.6095, 22.3429), //
            new Point3D(0.831025, 30.8773, 31.7589), //
            new Point3D(0.831025, 28.3457, 35.845), //
            new Point3D(-4.25935, 34.3417, 10.856), //
            new Point3D(-8.37348, 32.6095, 20.5255), //
            new Point3D(-12.0692, 30.8773, 29.2117), //
            new Point3D(-13.673, 28.3457, 32.9812), //
            new Point3D(-8.37704, 34.3417, 8.10003), //
            new Point3D(-15.8192, 32.6095, 15.5422), //
            new Point3D(-22.5045, 30.8773, 22.2275), //
            new Point3D(-25.4056, 28.3457, 25.1286), //
            new Point3D(-11.133, 34.3417, 3.98234), //
            new Point3D(-20.8025, 32.6095, 8.09647), //
            new Point3D(-29.4887, 30.8773, 11.7922), //
            new Point3D(-33.2582, 28.3457, 13.396), //
            new Point3D(-12.1381, 34.3417, -1.10804), //
            new Point3D(-22.62, 32.6095, -1.10804), //
            new Point3D(-32.0359, 30.8773, -1.10804), //
            new Point3D(-36.122, 28.3457, -1.10804), //
            new Point3D(-11.133, 34.3417, -6.19841), //
            new Point3D(-20.8025, 32.6095, -10.3125), //
            new Point3D(-29.4887, 30.8773, -14.0083), //
            new Point3D(-33.2582, 28.3457, -15.6121), //
            new Point3D(-8.37704, 34.3417, -10.3161), //
            new Point3D(-15.8192, 32.6095, -17.7582), //
            new Point3D(-22.5045, 30.8773, -24.4435), //
            new Point3D(-25.4056, 28.3457, -27.3447), //
            new Point3D(-4.25935, 34.3417, -13.072), //
            new Point3D(-8.37348, 32.6095, -22.7416), //
            new Point3D(-12.0692, 30.8773, -31.4277), //
            new Point3D(-13.673, 28.3457, -35.1972), //
            new Point3D(0.831025, 34.3417, -14.0771), //
            new Point3D(0.831025, 32.6095, -24.559), //
            new Point3D(0.831025, 30.8773, -33.9749), //
            new Point3D(0.831025, 28.3457, -38.0611), //
            new Point3D(5.9214, 34.3417, -13.072), //
            new Point3D(10.0355, 32.6095, -22.7416), //
            new Point3D(13.7313, 30.8773, -31.4277), //
            new Point3D(15.3351, 28.3457, -35.1972), //
            new Point3D(10.0391, 34.3417, -10.3161), //
            new Point3D(17.4812, 32.6095, -17.7582), //
            new Point3D(24.1665, 30.8773, -24.4435), //
            new Point3D(27.0677, 28.3457, -27.3447), //
            new Point3D(12.795, 34.3417, -6.19841), //
            new Point3D(22.4646, 32.6095, -10.3125), //
            new Point3D(31.1507, 30.8773, -14.0083), //
            new Point3D(34.8094, 17.1865, -35.0864) //
    };
}
