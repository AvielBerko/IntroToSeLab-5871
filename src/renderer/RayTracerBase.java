package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract class for ray tracing
 */
public abstract class RayTracerBase {

    protected Scene _scene;

    /**
     * Constructs a ray tracer object with a given scene
     * @param scene the scene for ray tracing
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * Traces a given ray and returns the color of the hit object
     * @param ray the ray to trace
     * @return the color of the hit object
     */
    public abstract Color traceRay(Ray ray);

}
