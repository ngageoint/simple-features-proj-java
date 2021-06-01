package mil.nga.crs.geo;

import mil.nga.crs.CRSType;
import mil.nga.crs.SimpleCoordinateReferenceSystem;
import mil.nga.crs.common.CoordinateSystem;
import mil.nga.crs.common.Dynamic;

/**
 * Geodetic and Geographic Coordinate Reference System
 * 
 * @author osbornb
 */
public class GeoCoordinateReferenceSystem
		extends SimpleCoordinateReferenceSystem {

	/**
	 * Reference Frame
	 */
	private GeoReferenceFrame referenceFrame = null;

	/**
	 * Datum Ensemble
	 */
	private GeoDatumEnsemble datumEnsemble = null;

	/**
	 * Dynamic coordinate reference system
	 */
	private Dynamic dynamic = null;

	/**
	 * Constructor
	 */
	public GeoCoordinateReferenceSystem() {

	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            coordinate reference system type
	 */
	public GeoCoordinateReferenceSystem(CRSType type) {
		super(type);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 * @param referenceFrame
	 *            reference frame
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public GeoCoordinateReferenceSystem(String name, CRSType type,
			GeoReferenceFrame referenceFrame,
			CoordinateSystem coordinateSystem) {
		super(name, type, coordinateSystem);
		setReferenceFrame(referenceFrame);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 * @param datumEnsemble
	 *            datum ensemble
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public GeoCoordinateReferenceSystem(String name, CRSType type,
			GeoDatumEnsemble datumEnsemble, CoordinateSystem coordinateSystem) {
		super(name, type, coordinateSystem);
		setDatumEnsemble(datumEnsemble);
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
	 * @param referenceFrame
	 *            reference frame
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public GeoCoordinateReferenceSystem(String name, CRSType type,
			Dynamic dynamic, GeoReferenceFrame referenceFrame,
			CoordinateSystem coordinateSystem) {
		super(name, type, coordinateSystem);
		setDynamic(dynamic);
		setReferenceFrame(referenceFrame);
	}

	/**
	 * Get the reference frame
	 * 
	 * @return reference frame
	 */
	public GeoReferenceFrame getReferenceFrame() {
		return referenceFrame;
	}

	/**
	 * Determine if has a reference frame
	 * 
	 * @return true if has reference frame
	 */
	public boolean hasReferenceFrame() {
		return getReferenceFrame() != null;
	}

	/**
	 * Set the reference frame
	 * 
	 * @param referenceFrame
	 *            reference frame
	 */
	public void setReferenceFrame(GeoReferenceFrame referenceFrame) {
		this.referenceFrame = referenceFrame;
	}

	/**
	 * Get the datum ensemble
	 * 
	 * @return datum ensemble
	 */
	public GeoDatumEnsemble getDatumEnsemble() {
		return datumEnsemble;
	}

	/**
	 * Determine if has a datum ensemble
	 * 
	 * @return true if has datum ensemble
	 */
	public boolean hasDatumEnsemble() {
		return getDatumEnsemble() != null;
	}

	/**
	 * Set the datum ensemble
	 * 
	 * @param datumEnsemble
	 *            datum ensemble
	 */
	public void setDatumEnsemble(GeoDatumEnsemble datumEnsemble) {
		this.datumEnsemble = datumEnsemble;
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
	 * Get the geodetic or geographic common datum
	 * 
	 * @return geo datum
	 */
	public GeoDatum getGeoDatum() {
		GeoDatum datum = null;
		if (hasReferenceFrame()) {
			datum = getReferenceFrame();
		} else if (hasDatumEnsemble()) {
			datum = getDatumEnsemble();
		}
		return datum;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dynamic == null) ? 0 : dynamic.hashCode());
		result = prime * result
				+ ((datumEnsemble == null) ? 0 : datumEnsemble.hashCode());
		result = prime * result
				+ ((referenceFrame == null) ? 0 : referenceFrame.hashCode());
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
		GeoCoordinateReferenceSystem other = (GeoCoordinateReferenceSystem) obj;
		if (dynamic == null) {
			if (other.dynamic != null)
				return false;
		} else if (!dynamic.equals(other.dynamic))
			return false;
		if (datumEnsemble == null) {
			if (other.datumEnsemble != null)
				return false;
		} else if (!datumEnsemble.equals(other.datumEnsemble))
			return false;
		if (referenceFrame == null) {
			if (other.referenceFrame != null)
				return false;
		} else if (!referenceFrame.equals(other.referenceFrame))
			return false;
		return true;
	}

}
