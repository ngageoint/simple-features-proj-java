package mil.nga.proj.crs.geodetic;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.common.Dynamic;

/**
 * Geodetic and Geographic Coordinate Reference System
 * 
 * @author osbornb
 */
public class GeodeticCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

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
	 * @param type
	 *            coordinate reference system type
	 */
	public GeodeticCoordinateReferenceSystem(
			CoordinateReferenceSystemType type) {
		super(type);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 * @param geodeticReferenceFrame
	 *            geodetic reference frame
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public GeodeticCoordinateReferenceSystem(String name,
			CoordinateReferenceSystemType type,
			GeodeticReferenceFrame geodeticReferenceFrame,
			CoordinateSystem coordinateSystem) {
		super(name, type, coordinateSystem);
		setGeodeticReferenceFrame(geodeticReferenceFrame);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 * @param geodeticDatumEnsemble
	 *            geodetic datum ensemble
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public GeodeticCoordinateReferenceSystem(String name,
			CoordinateReferenceSystemType type,
			GeodeticDatumEnsemble geodeticDatumEnsemble,
			CoordinateSystem coordinateSystem) {
		super(name, type, coordinateSystem);
		setGeodeticDatumEnsemble(geodeticDatumEnsemble);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 * @param dynamic
	 *            dynamic
	 * @param geodeticReferenceFrame
	 *            geodetic reference frame
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public GeodeticCoordinateReferenceSystem(String name,
			CoordinateReferenceSystemType type, Dynamic dynamic,
			GeodeticReferenceFrame geodeticReferenceFrame,
			CoordinateSystem coordinateSystem) {
		super(name, type, coordinateSystem);
		setDynamic(dynamic);
		setGeodeticReferenceFrame(geodeticReferenceFrame);
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

	/**
	 * Get the geodetic datum ensemble
	 * 
	 * @return geodetic datum ensemble
	 */
	public GeodeticDatumEnsemble getGeodeticDatumEnsemble() {
		return geodeticDatumEnsemble;
	}

	/**
	 * Determine if has a geodetic datum ensemble
	 * 
	 * @return true if has geodetic datum ensemble
	 */
	public boolean hasGeodeticDatumEnsemble() {
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
	 * Determine if has a dynamic
	 * 
	 * @return true if has dynamic
	 */
	public boolean hasDynamic() {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dynamic == null) ? 0 : dynamic.hashCode());
		result = prime * result + ((geodeticDatumEnsemble == null) ? 0
				: geodeticDatumEnsemble.hashCode());
		result = prime * result + ((geodeticReferenceFrame == null) ? 0
				: geodeticReferenceFrame.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeodeticCoordinateReferenceSystem other = (GeodeticCoordinateReferenceSystem) obj;
		if (dynamic == null) {
			if (other.dynamic != null)
				return false;
		} else if (!dynamic.equals(other.dynamic))
			return false;
		if (geodeticDatumEnsemble == null) {
			if (other.geodeticDatumEnsemble != null)
				return false;
		} else if (!geodeticDatumEnsemble.equals(other.geodeticDatumEnsemble))
			return false;
		if (geodeticReferenceFrame == null) {
			if (other.geodeticReferenceFrame != null)
				return false;
		} else if (!geodeticReferenceFrame.equals(other.geodeticReferenceFrame))
			return false;
		return true;
	}

}
