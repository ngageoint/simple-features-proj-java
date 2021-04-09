package mil.nga.proj.crs;

/**
 * Geodetic and Geographic Coordinate Reference System
 * 
 * @author osbornb
 */
public class GeodeticCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

	/**
	 * Geographic flag
	 */
	private boolean geographic = false;

	/**
	 * Geodetic Reference Frame
	 */
	private GeodeticReferenceFrame geodeticReferenceFrame = null;

	/**
	 * Constructor
	 */
	public GeodeticCoordinateReferenceSystem() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public GeodeticCoordinateReferenceSystem(String name,
			CoordinateSystem coordinateSystem) {
		super(name, coordinateSystem);
	}

	/**
	 * Is a Geographic Coordinate Reference System
	 * 
	 * @return true if geographic, false if geodetic
	 */
	public boolean isGeographic() {
		return geographic;
	}

	/**
	 * Set if a Geographic Coordinate Reference System
	 * 
	 * @param geographic
	 *            true if geographic, false if geodetic
	 */
	public void setGeographic(boolean geographic) {
		this.geographic = geographic;
	}

	/**
	 * Get the geodetic reference frame
	 * 
	 * @return geodetic reference frame
	 */
	public GeodeticReferenceFrame getGeodeticReferenceFrame() {
		return geodeticReferenceFrame;
	}

	/**
	 * Determine if has a geodetic reference frame
	 * 
	 * @return true if has geodetic reference frame
	 */
	public boolean hasGeodeticReferenceFrame() {
		return getGeodeticReferenceFrame() != null;
	}

	/**
	 * Set the geodetic reference frame
	 * 
	 * @param geodeticReferenceFrame
	 *            geodetic reference frame
	 */
	public void setGeodeticReferenceFrame(
			GeodeticReferenceFrame geodeticReferenceFrame) {
		this.geodeticReferenceFrame = geodeticReferenceFrame;
	}

}
