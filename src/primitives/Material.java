package primitives;

/**
 * Material details for geometries.
 */
public class Material {
	/**
	 * kD is the diffuse parameter of the material
	 */
	public double kD = 0;
	/**
	 * kS is the specular parameter of the material
	 */
	public double kS = 0;
	/**
	 * kT is the transparency parameter of the material
	 */
	public double kT = 0;
	/**
	 * kR is the reflection parameter of the material
	 */
	public double kR = 0;
	/**
	 * nShininess is the shininess parameter of the material
	 */
	public int nShininess = 0;


	/**
	 * Chaining method for setting the diffuse of the material.
	 * @param kD the diffuse to set
	 * @return the current material
	 */
	public Material setKd(double kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * Chaining method for setting the specular of the material.
	 * @param kS the specular to set
	 * @return the current material
	 */
	public Material setKs(double kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * Chaining method for setting the transparency of the material.
	 * @param kT the transparency to set
	 * @return the current material
	 */
	public Material setKt(double kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * Chaining method for setting the reflection of the material.
	 * @param kR the reflection to set
	 * @return the current material
	 */
	public Material setKr(double kR) {
		this.kR = kR;
		return this;
	}


	/**
	 * Chaining method for setting the shininess of the material.
	 * @param nShininess the shininess to set
	 * @return the current material
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

}
