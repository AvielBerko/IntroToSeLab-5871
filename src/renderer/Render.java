package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.MissingResourceException;

/**
 * Class for rendering a scene with ray tracing.
 */
public class Render {
    public ImageWriter imageWriter = null;
    public Camera camera = null;
    public RayTracerBase rayTracer = null;

    /**
     * Chaining method for setting the image writer
     * @param imageWriter the image writer to set
     * @return the current render
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Chaining method for setting the camera
     * @param camera the camera to set
     * @return the current render
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Chaining method for setting the ray tracer
     * @param rayTracer the ray tracer to set
     * @return the current render
     */
    public Render setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Renders the image
     *
     * @exception UnsupportedOperationException when the render didn't receive all the arguments.
     */
    public void renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("Missing resource", ImageWriter.class.getName(), "");
            }
            if (camera == null) {
                throw new MissingResourceException("Missing resource", Camera.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("Missing resource", RayTracerBase.class.getName(), "");
            }

            //rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
                    Color pixelColor = rayTracer.traceRay(ray);
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Render didn't receive " + e.getClassName());
        }
    }

    /**
     * Adds a grid to the image
     * @param interval num of the grid's lines
     * @param color the color of the grid's lines
     */
    public void printGrid(int interval, Color color) {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Saves the image according to image writer.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }
}
