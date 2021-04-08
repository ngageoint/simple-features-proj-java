package mil.nga.proj.crs;

/**
 * Geographic Bounding Box
 * 
 * @author osbornb
 */
public class GeographicBoundingBox {

	/**
	 * Lower Left Latitude
	 */
	private double lowerLeftLatitude;

	/**
	 * Lower Left Longitude
	 */
	private double lowerLeftLongitude;

	/**
	 * Upper Right Latitude
	 */
	private double upperRightLatitude;

	/**
	 * Upper Right Longitude
	 */
	private double upperRightLongitude;

	/**
	 * Constructor
	 */
	public GeographicBoundingBox() {

	}

	/**
	 * Constructor
	 * 
	 * @param lowerLeftLatitude
	 *            lower left latitude
	 * @param lowerLeftLongitude
	 *            lower left longitude
	 * @param upperRightLatitude
	 *            upper right latitude
	 * @param upperRightLongitude
	 *            upper right longitude
	 */
	public GeographicBoundingBox(double lowerLeftLatitude,
			double lowerLeftLongitude, double upperRightLatitude,
			double upperRightLongitude) {
		setLowerLeftLatitude(lowerLeftLatitude);
		setLowerLeftLongitude(lowerLeftLongitude);
		setUpperRightLatitude(upperRightLatitude);
		setUpperRightLongitude(upperRightLongitude);
	}

	/**
	 * Get the lower left latitude
	 * 
	 * @return lower left latitude
	 */
	public double getLowerLeftLatitude() {
		return lowerLeftLatitude;
	}

	/**
	 * Set the lower left latitude
	 * 
	 * @param lowerLeftLatitude
	 *            lower left latitude
	 */
	public void setLowerLeftLatitude(double lowerLeftLatitude) {
		this.lowerLeftLatitude = lowerLeftLatitude;
	}

	/**
	 * Get the lower left longitude
	 * 
	 * @return lower left longitude
	 */
	public double getLowerLeftLongitude() {
		return lowerLeftLongitude;
	}

	/**
	 * Set the lower left longitude
	 * 
	 * @param lowerLeftLongitude
	 *            lower left longitude
	 */
	public void setLowerLeftLongitude(double lowerLeftLongitude) {
		this.lowerLeftLongitude = lowerLeftLongitude;
	}

	/**
	 * Set the upper right latitude
	 * 
	 * @return upper right latitude
	 */
	public double getUpperRightLatitude() {
		return upperRightLatitude;
	}

	/**
	 * Set the upper right latitude
	 * 
	 * @param upperRightLatitude
	 *            upper right latitude
	 */
	public void setUpperRightLatitude(double upperRightLatitude) {
		this.upperRightLatitude = upperRightLatitude;
	}

	/**
	 * Set the upper right longitude
	 * 
	 * @return upper right longitude
	 */
	public double getUpperRightLongitude() {
		return upperRightLongitude;
	}

	/**
	 * Set the upper right longitude
	 * 
	 * @param upperRightLongitude
	 *            upper right longitude
	 */
	public void setUpperRightLongitude(double upperRightLongitude) {
		this.upperRightLongitude = upperRightLongitude;
	}

}
