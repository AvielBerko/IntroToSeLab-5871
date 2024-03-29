package renderer;

import primitives.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.*;

/**
 * Image writer class combines accumulation of pixel color matrix and finally
 * producing a non-optimized jpeg image from this matrix. The class although is
 * responsible of holding image related parameters of View Plane - pixel matrix
 * size and resolution
 * 
 * @author Dan
 */
public class ImageWriter {
	private final int _nX;
	private final int _nY;

	private static final String FOLDER_PATH = System.getProperty("user.dir") + "/images";

	private final BufferedImage _image;
	private final String _imageName;
	
	private final Logger _logger = Logger.getLogger("ImageWriter");

	// ***************** Constructors ********************** //
	/**
	 * Image Writer constructor accepting image name and View Plane parameters,
	 * @param imageName the name of jpeg file
	 * @param nX        amount of pixels by Width
	 * @param nY        amount of pixels by height
	 */
	public ImageWriter(String imageName, int nX, int nY) {
		this._imageName = imageName;
		this._nX = nX;
		this._nY = nY;

		_image = new BufferedImage(nX, nY, BufferedImage.TYPE_INT_RGB);
	}

	// ***************** Getters/Setters ********************** //
	/**
	 * View Plane Y axis resolution
	 * 
	 * @return the amount of vertical pixels
	 */
	public int getNy() {
		return _nY;
	}

	/**
	 * View Plane X axis resolution
	 * 
	 * @return the amount of horizontal pixels
	 */
	public int getNx() {
		return _nX;
	}

	// ***************** Operations ******************** //

	/**
	 * Function writeToImage produces unoptimized png file of the image according to
	 * pixel color matrix in the directory of the project
	 */
	public void writeToImage() {
		try {
			File file = new File(FOLDER_PATH + '/' + _imageName + ".png");
			ImageIO.write(_image, "png", file);
		} catch (IOException e) {
			_logger.log(Level.SEVERE, "I/O error", e);
		}
	}

	/**
	 * The function writePixel writes a color of a specific pixel into pixel color
	 * matrix
	 * 
	 * @param xIndex X axis index of the pixel
	 * @param yIndex Y axis index of the pixel
	 * @param color  final color of the pixel
	 */
	public void writePixel(int xIndex, int yIndex, Color color) {
		_image.setRGB(xIndex, yIndex, color.getColor().getRGB());
	}

}
