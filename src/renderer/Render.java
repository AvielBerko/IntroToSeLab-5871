package renderer;

import elements.Camera;
import multithreading.ThreadPool;
import primitives.Color;
import primitives.Ray;

import java.util.List;
import java.util.MissingResourceException;

/**
 * Class for rendering a scene with ray tracing.
 */
public class Render {
    private ImageWriter _imageWriter = null;
    private Camera _camera = null;
    private RayTracerBase _rayTracer = null;
    private ThreadPool<Pixel> _threadPool = null;
    private Pixel _nextPixel = null;
    private boolean _printPercent = false;
    private boolean _antiAliasing = true;

    /**
     * Chaining method for setting the image writer.
     * @param imageWriter the image writer to set.
     * @return the current render.
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this._imageWriter = imageWriter;
        return this;
    }

    /**
     * Chaining method for setting the camera.
     * @param camera the camera to set.
     * @return the current render.
     */
    public Render setCamera(Camera camera) {
        this._camera = camera;
        return this;
    }

    /**
     * Chaining method for setting the ray tracer.
     * @param rayTracer the ray tracer to set.
     * @return the current render.
     */
    public Render setRayTracer(RayTracerBase rayTracer) {
        this._rayTracer = rayTracer;
        return this;
    }

    /**
     * Chaining method for setting number of threads.
     * If set to 1, the render won't use the thread pool.
     * If set to greater than 1, the render will use the thread pool with the given threads.
     * If set to 0, the thread pool will pick the number of threads.
     * @param threads number of threads to use.
     * @exception IllegalArgumentException when threads is less than 0.
     * @return the current render.
     */
    public Render setMultithreading(int threads) {
        if (threads < 0) {
            throw new IllegalArgumentException("threads can be equals or greater to 0");
        }

        // run as single threaded without the thread pool.
        if (threads == 1) {
            _threadPool = null;
            return this;
        }

        _threadPool = new ThreadPool<Pixel>() // the thread pool choose the number of threads (in case threads is 0).
                .setParamGetter(this::getNextPixel)
                .setTarget(this::renderImageMultithreaded);
        if (threads > 0) {
            _threadPool.setNumThreads(threads);
        }

        return this;
    }

    /**
     * Chaining method for making the render to print the progress of the rendering in percents.
     * @param print if true, prints the percents.
     * @return the current render.
     */
    public Render setPrintPercent(boolean print) {
        _printPercent = print;
        return this;
    }

    /**
     * Chaining method for choosing whether to use Anti-Aliasing or not.
     * @param antiAliasing if true, uses Anti-Aliasing for the rendering.
     * @return the current render.
     */
    public Render setAntiAliasing(boolean antiAliasing) {
        _antiAliasing = antiAliasing;
        return this;
    }

    /**
     * Renders the image.
     *
     * @exception UnsupportedOperationException when the render didn't receive all the arguments.
     */
    public void renderImage() {
        try {
            if (_imageWriter == null) {
                throw new MissingResourceException("Missing resource", ImageWriter.class.getName(), "");
            }
            if (_camera == null) {
                throw new MissingResourceException("Missing resource", Camera.class.getName(), "");
            }
            if (_rayTracer == null) {
                throw new MissingResourceException("Missing resource", RayTracerBase.class.getName(), "");
            }

            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();

            //rendering the image when multi-threaded.
            if (_threadPool != null) {
                _nextPixel = new Pixel(0, 0);
                _threadPool.execute();
                if (_printPercent) {
                    printPercentMultithreaded(); // blocks the main thread until finished and prints the progress.
                }
                _threadPool.join();
                return;
            }

            // rendering the image when single-threaded.
            int lastPercent = -1;
            int pixels = nX * nY;
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    if (_printPercent) {
                        int currentPixel = i * nX + j;
                        lastPercent = printPercent(currentPixel, pixels, lastPercent);
                    }
                    castRay(nX, nY, j, i);
                }
            }
            // prints the 100% percent.
            if (_printPercent) {
                printPercent(pixels, pixels, lastPercent);
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Render didn't receive " + e.getClassName());
        }
    }

    /**
     * Casts a ray or multiple rays through a given pixel (depends on Anti-Aliasing).
     * and writes the color to the image.
     * @param nX the number of columns in the picture.
     * @param nY the number of rows in the picture.
     * @param col the column of the current pixel.
     * @param row the row of the current pixel.
     */
    private void castRay(int nX, int nY, int col, int row) {
        Color pixelColor;

        if (_antiAliasing) {
            List<Ray> rays = _camera.constructRayPixelWithAA(nX, nY, col, row);
            pixelColor = _rayTracer.averageColor(rays);
        } else {
            Ray ray = _camera.constructRayThroughPixel(nX, nY, col, row);
            pixelColor = _rayTracer.traceRay(ray);
        }

        _imageWriter.writePixel(col, row, pixelColor);
    }

    /**
     * Prints the progress in percents only if it is greater than the last time printed the progress.
     * @param currentPixel the index of the current pixel.
     * @param pixels the number of pixels in the image.
     * @param lastPercent the percent of the last time printed the progress.
     * @return If printed the new percent, returns the new percent. Else, returns {@code lastPercent}.
     */
    private int printPercent(int currentPixel, int pixels, int lastPercent) {
        int percent = currentPixel * 100 / pixels;
        if (percent > lastPercent) {
            System.out.printf("%02d%%\n", percent);
            System.out.flush();
            return percent;
        }
        return lastPercent;
    }

    /**
     * Adds a grid to the image.
     * @param interval num of the grid's lines.
     * @param color the color of the grid's lines.
     */
    public void printGrid(int interval, Color color) {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Saves the image according to image writer.
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * Returns the next pixel to draw on multithreaded rendering.
     * If finished to draw all pixels, returns {@code null}.
     */
    private synchronized Pixel getNextPixel() {
        if (_printPercent) {
            // notifies the main thread in order to print the percent.
            notifyAll();
        }

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        // updates the row of the next pixel to draw.
        // if got to the end, returns null.
        if (_nextPixel.col >= nX) {
            if (++_nextPixel.row >= nY) {
                return null;
            }
            _nextPixel.col = 0;
        }

        Pixel result = new Pixel();
        result.col = _nextPixel.col++;
        result.row = _nextPixel.row;
        return result;
    }

    /**
     * Renders a given pixel on multithreaded rendering.
     * If the given pixel is null, returns false which means kill the thread.
     * @param p the pixel to render.
     */
    private boolean renderImageMultithreaded(Pixel p) {
        if (p == null) {
            return false; // kill the thread.
        }

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        castRay(nX, nY, p.col, p.row);

        return true; // continue the rendering.
    }

    /**
     * Must run on the main thread.
     * Prints the percent on multithreaded rendering.
     */
    private void printPercentMultithreaded() {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        int pixels = nX * nY;
        int lastPercent = -1;

        while (_nextPixel.row < nY) {
            // waits until got update from the rendering threads.
            synchronized(this) {
                try {
                    wait();
                } catch (InterruptedException e) { }
            }

            int currentPixel = _nextPixel.row * nX + _nextPixel.col;
            lastPercent = printPercent(currentPixel, pixels, lastPercent);
        }
    }

    /**
     * Helper class to represent a pixel to draw in a multithreading rendering.
     */
    private static class Pixel {
        public int col, row;

        public Pixel(int col, int row) {
            this.col = col;
            this.row = row;
        }

        public Pixel() {}
    }
}