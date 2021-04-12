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
	 * Geodetic Datum Ensemble
	 */
	private GeodeticDatumEnsemble geodeticDatumEnsemble = null;

	/**
	 * Dynamic coordinate reference system
	 */
	private Dynamic dynamic = null;

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
	 * @param geodeticReferenceFrame
	 *            geodetic reference frame
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public GeodeticCoordinateReferenceSystem(String name,
			GeodeticReferenceFrame geodeticReferenceFrame,
			CoordinateSystem coordinateSystem) {
		super(name, coordinateSystem);
		setGeodeticReferenceFrame(geodeticReferenceFrame);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param geodeticDatumEnsemble
	 *            geodetic datum ensemble
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public GeodeticCoordinateReferenceSystem(String name,
			GeodeticDatumEnsemble geodeticDatumEnsemble,
			CoordinateSystem coordinateSystem) {
		super(name, coordinateSystem);
		setGeodeticDatumEnsemble(geodeticDatumEnsemble);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param dynamic
	 *            dynamic
	 * @param geodeticReferenceFrame
	 *            geodetic reference frame
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public GeodeticCoordinateReferenceSystem(String name, Dynamic dynamic,
			GeodeticReferenceFrame geodeticReferenceFrame,
			CoordinateSystem coordinateSystem) {
		super(name, coordinateSystem);
		setDynamic(dynamic);
		setGeodeticReferenceFrame(geodeticReferenceFrame);
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
	 * Determine if a geodetic reference frame
	 * 
	 * @return true if a geodetic reference frame
	 */
	public boolean isGeodeticReferenceFrame() {
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

	/**
	 * Get the geodetic datum ensemble
	 * 
	 * @return geodetic datum ensemble
	 */
	public GeodeticDatumEnsemble getGeodeticDatumEnsemble() {
		return geodeticDatumEnsemble;
	}

	/**
	 * Determine if a geodetic datum ensemble
	 * 
	 * @return true if a geodetic datum ensemble
	 */
	public boolean isGeodeticDatumEnsemble() {
		return getGeodeticDatumEnsemble() != null;
	}

	/**
	 * Set the geodetic datum ensemble
	 * 
	 * @param geodeticDatumEnsemble
	 *            geodetic datum ensemble
	 */
	public void setGeodeticDatumEnsemble(
			GeodeticDatumEnsemble geodeticDatumEnsemble) {
		this.geodeticDatumEnsemble = geodeticDatumEnsemble;
	}

	/**
	 * Get the dynamic coordinate reference system
	 * 
	 * @return dynamic coordinate reference system
	 */
	public Dynamic getDynamic() {
		return dynamic;
	}

	/**
	 * Determine if dynamic
	 * 
	 * @return true if dynamic
	 */
	public boolean isDynamic() {
		return getDynamic() != null;
	}

	/**
	 * Set the dynamic coordinate reference system
	 * 
	 * @param dynamic
	 *            dynamic coordinate reference system
	 */
	public void setDynamic(Dynamic dynamic) {
		this.dynamic = dynamic;
	}

}
