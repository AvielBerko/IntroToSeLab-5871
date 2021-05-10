package scene;

import elements.AmbientLight;
import geometries.Geometries;
import geometries.Geometry;
import primitives.Color;

/**
 * Scene class that contains geometries, background color and lights
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight(Color.BLACK, 1);
    public Geometries geometries;

    /**
     * Constructs a new scene with a name
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    /**
     * Builder pattern to build a new scene
     */
    public static class Builder {
        private final Scene _scene;

        private Builder(String name) {
            _scene = new Scene(name);
        }

        /**
         * Creates a new builder class
         * @param name the name of the created scene
         * @return the created builder
         */
        public static Builder create(String name) {
            return new Builder(name);
        }

        /**
         * Sets the background of the created scene
         * @param background the background to set
         * @return the current builder
         */
        public Builder setBackground(Color background) {
            _scene.background = background;
            return this;
        }

        /**
         * Sets the ambient light of the created scene
         * @param ambientLight the ambient light to set
         * @return the current builder
         */
        public Builder setAmbientLight(AmbientLight ambientLight) {
            _scene.ambientLight = ambientLight;
            return this;
        }

        /**
         * Sets the geometries of the created scene
         * @param geometries the geometries to set
         * @return the current builder
         */
        public Builder setGeometries(Geometries geometries) {
            _scene.geometries = geometries;
            return this;
        }

        /**
         * Adds one geometry to the created scene
         * @param geometry the geometry to add
         * @return the current builder
         */
        public Builder addGeometry(Geometry geometry) {
            _scene.geometries.add(geometry);
            return this;
        }

        /**
         * Builds the scene
         * @return the created scene
         */
        public Scene build() {
            return _scene;
        }
    }
}
