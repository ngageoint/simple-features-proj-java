package mil.nga.proj.crs.metadata;

import mil.nga.proj.crs.CRS;
import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * Coordinate metadata
 * 
 * @author osbornb
 */
public class CoordinateMetadata extends CRS {

	/**
	 * Coordinate Reference System
	 */
	private CoordinateReferenceSystem coordinateReferenceSystem = null;

	/**
	 * Coordinate Epoch
	 */
	private Double epoch;

	/**
	 * Constructor
	 */
	public CoordinateMetadata() {
		super(CRSType.COORDINATE_METADATA);
	}

	/**
	 * Constructor
	 * 
	 * @param crs
	 *            coordinate reference system
	 */
	public CoordinateMetadata(CoordinateReferenceSystem crs) {
		this();
		setCoordinateReferenceSystem(crs);
	}

	/**
	 * Constructor
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @param epoch
	 *            coordinate epoch
	 */
	public CoordinateMetadata(CoordinateReferenceSystem crs, Double epoch) {
		this(crs);
		setEpoch(epoch);
	}

	/**
	 * Get the coordinate reference system
	 * 
	 * @return coordinate reference system
	 */
	public CoordinateReferenceSystem getCoordinateReferenceSystem() {
		return coordinateReferenceSystem;
	}

	/**
	 * Set the coordinate reference system
	 * 
	 * @param crs
	 *            coordinate reference system
	 */
	public void setCoordinateReferenceSystem(CoordinateReferenceSystem crs) {
		this.coordinateReferenceSystem = crs;
	}

	/**
	 * Get the coordinate epoch
	 * 
	 * @return coordinate epoch
	 */
	public Double getEpoch() {
		return epoch;
	}

	/**
	 * Has a coordinate epoch
	 * 
	 * @return true if has coordinate epoch
	 */
	public boolean hasEpoch() {
		return getEpoch() != null;
	}

	/**
	 * Set the coordinate epoch
	 * 
	 * @param epoch
	 *            coordinate epoch
	 */
	public void setEpoch(Double epoch) {
		this.epoch = epoch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((coordinateReferenceSystem == null) ? 0
				: coordinateReferenceSystem.hashCode());
		result = prime * result + ((epoch == null) ? 0 : epoch.hashCode());
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
		CoordinateMetadata other = (CoordinateMetadata) obj;
		if (coordinateReferenceSystem == null) {
			if (other.coordinateReferenceSystem != null)
				return false;
		} else if (!coordinateReferenceSystem
				.equals(other.coordinateReferenceSystem))
			return false;
		if (epoch == null) {
			if (other.epoch != null)
				return false;
		} else if (!epoch.equals(other.epoch))
			return false;
		return true;
	}

}
