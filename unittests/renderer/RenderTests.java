package renderer;

import org.junit.jupiter.api.Test;
import elements.*;
import geometries.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import primitives.*;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {
    private final Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setDistance(100) //
            .setViewPlaneSize(500, 500)
            .setNumOfRays(50);

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = Scene.Builder.create("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90))
                .build();

        scene.geometries.add(new Sphere(50, new Point3D(0, 0, -100)),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
                // right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
        // right

        ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    /**
     * Test for XML based scene - for bonus
     */
    @Test
    public void basicRenderXml() {
        Scene scene = null;
        try {
            scene = buildSceneFromXml("XML Test scene", "XmlFiles/basicRenderTestTwoColors.xml");
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    /**
     * Builds a scene from a given xml file
     * @param sceneName the name of the created scene
     * @param filename the path for the xml file
     * @return the created scene
     */
    public Scene buildSceneFromXml(String sceneName, String filename) throws IOException, SAXException, ParserConfigurationException {
        Scene.Builder builder = Scene.Builder.create(sceneName);

        File xmlFile = new File(filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        Element root = doc.getDocumentElement();

        // gets the background of the scene
        String[] backgroundRgb = root.getAttribute("background-color").split(" ");
        if (backgroundRgb.length == 3) {
            builder.setBackground(new Color(
                    Double.parseDouble(backgroundRgb[0]),
                    Double.parseDouble(backgroundRgb[1]),
                    Double.parseDouble(backgroundRgb[2])
            ));
        }

        // gets the ambient light of the scene
        NodeList ambientLightNodes = root.getElementsByTagName("ambient-light");
        if (ambientLightNodes.getLength() > 0) {
            Node ambientLightNode = ambientLightNodes.item(0);
            if (ambientLightNode.getNodeType() == Node.ELEMENT_NODE) {
                Element ambientLightElement = (Element) ambientLightNode;
                String[] ambientLightRgb = ambientLightElement.getAttribute("color").split(" ");
                if (ambientLightRgb.length == 3) {
                    builder.setAmbientLight(new AmbientLight(
                            new Color(
                                    Double.parseDouble(ambientLightRgb[0]),
                                    Double.parseDouble(ambientLightRgb[1]),
                                    Double.parseDouble(ambientLightRgb[2])
                            ),
                            1
                    ));
                }
            }
        }

        // gets the geometries of the scene
        NodeList geometriesNodes = root.getElementsByTagName("geometries");
        if (geometriesNodes.getLength() > 0) {
            Node geometriesNode = geometriesNodes.item(0);
            if (geometriesNode.getNodeType() == Node.ELEMENT_NODE) {
                Element geometriesElement = (Element) geometriesNode;
                NodeList geometriesChildren = geometriesElement.getChildNodes();
                // loops on every geometry
                for (int i = 0; i < geometriesChildren.getLength(); i++) {
                    Node geometryNode = geometriesChildren.item(i);
                    if (geometryNode.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }

                    Element geometryElement = (Element) geometryNode;
                    switch (geometryElement.getNodeName()) {
                        case "sphere": {
                            String[] centerCoordinates = geometryElement.getAttribute("center").split(" ");
                            if (centerCoordinates.length != 3) {
                                continue;
                            }
                            double radius = Double.parseDouble(geometryElement.getAttribute("radius"));
                            builder.addGeometries(new Sphere(
                                    radius,
                                    new Point3D(
                                            Double.parseDouble(centerCoordinates[0]),
                                            Double.parseDouble(centerCoordinates[1]),
                                            Double.parseDouble(centerCoordinates[2])
                                    )
                            ));
                        }
                        break;
                        case "triangle":
                        case "polygon": {
                            Point3D[] points = new Point3D[geometryElement.getAttributes().getLength()];
                            for (int p = 0; p < points.length; p++) {
                                String[] pointCoordinates = geometryElement.getAttribute("p" + p).split(" ");
                                if (pointCoordinates.length != 3) {
                                    continue;
                                }
                                points[p] = new Point3D(
                                        Double.parseDouble(pointCoordinates[0]),
                                        Double.parseDouble(pointCoordinates[1]),
                                        Double.parseDouble(pointCoordinates[2])
                                );
                            }

                            if (geometryElement.getNodeName().equals("triangle")) {
                                builder.addGeometries(new Triangle(points[0], points[1], points[2]));
                            } else {
                                builder.addGeometries(new Polygon(points));
                            }
                        }
                        break;
                        case "plane": {
                            int numOfAttributes = geometryElement.getAttributes().getLength();
                            if (numOfAttributes == 3) {
                                // creates a plane with 3 points
                                Point3D[] points = new Point3D[numOfAttributes];
                                for (int p = 0; p < points.length; p++) {
                                    String[] pointCoordinates = geometryElement.getAttribute("p" + p).split(" ");
                                    if (pointCoordinates.length != 3) {
                                        continue;
                                    }
                                    points[p] = new Point3D(
                                            Double.parseDouble(pointCoordinates[0]),
                                            Double.parseDouble(pointCoordinates[1]),
                                            Double.parseDouble(pointCoordinates[2])
                                    );
                                }

                                builder.addGeometries(new Plane(points[0], points[1], points[2]));
                            } else if (numOfAttributes == 2) {
                                // creates a plane with a point and a normal
                                String[] pointCoordinates = geometryElement.getAttribute("point").split(" ");
                                if (pointCoordinates.length != 3) {
                                    continue;
                                }
                                Point3D point = new Point3D(
                                        Double.parseDouble(pointCoordinates[0]),
                                        Double.parseDouble(pointCoordinates[1]),
                                        Double.parseDouble(pointCoordinates[2])
                                );

                                String[] normalCoordinates = geometryElement.getAttribute("normal").split(" ");
                                if (normalCoordinates.length != 3) {
                                    continue;
                                }
                                Vector normal = new Vector(
                                        Double.parseDouble(normalCoordinates[0]),
                                        Double.parseDouble(normalCoordinates[1]),
                                        Double.parseDouble(normalCoordinates[2])
                                );

                                builder.addGeometries(new Plane(point, normal));
                            }
                        }
                        break;
                        default:
                            break;
                    }
                }
            }
        }

        return builder.build();
    }
    // For stage 6 - please disregard in stage 5
    /**
     * Produce a scene with basic 3D model - including individual lights of the bodies
     * and render it into a png image with a grid
     */
    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = Scene.Builder.create("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2))
                .build(); //

        scene.geometries.add(new Sphere(50, new Point3D(0, 0, -100)) //
                        .setEmission(new Color(java.awt.Color.CYAN)), //
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)) // up left
                        .setEmission(new Color(java.awt.Color.GREEN)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)) // down left
                        .setEmission(new Color(java.awt.Color.RED)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100)) // down right
                        .setEmission(new Color(java.awt.Color.BLUE)));

        ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.WHITE));
        render.writeToImage();
    }


}
