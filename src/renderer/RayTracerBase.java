package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * Abstract class for ray tracing.
 */
public abstract class RayTracerBase {

    protected Scene _scene;

    /**
     * Constructs a ray tracer object with a given scene.
     * @param scene the scene for ray tracing.
     */
    public RayTracerBase(Scene scene) {
        if(scene == null) {
            throw new IllegalArgumentException("Scene cannot be null");
        }
        _scene = scene;
    }

    /**
     * Traces a given ray and returns the color of the hit object.
     * @param ray the ray to trace.
     * @return the color of the hit object.
     */
    public abstract Color traceRay(Ray ray);

    /**
     * Calculates the average color of a given list of rays.
     * @param rays The list of rays.
     * @return The average color of all rays.
     */
    public Color averageColor(List<Ray> rays) {
        Color color = Color.BLACK;
        for (Ray ray : rays) {
            color = color.add(traceRay(ray));
        }
        return color.reduce(rays.size());
    }
}
