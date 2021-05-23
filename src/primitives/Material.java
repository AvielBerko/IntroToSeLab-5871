package primitives;

/**
 * Material details for geometries.
 */
public class Material {
	public double kD = 0;
	public double kS = 0;
	public int nShininess = 0;

	/**
	 * Chaining method for setting the kD of the material.
	 * @param kD the kD to set
	 * @return the current material
	 */
	public Material setKd(double kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * Chaining method for setting the kS of the material.
	 * @param kS the kS to set
	 * @return the current material
	 */
	public Material setKs(double kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * Chaining method for setting the n shininess of the material.
	 * @param nShininess the n shininess to set
	 * @return the current material
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
}
