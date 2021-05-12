package mil.nga.proj.crs.temporal;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.CoordinateSystem;

/**
 * Temporal Coordinate Reference System
 * 
 * @author osbornb
 */
public class TemporalCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

	/**
	 * Temporal Datum
	 */
	private TemporalDatum datum = null;

	/**
	 * Constructor
	 */
	public TemporalCoordinateReferenceSystem() {
		super(CoordinateReferenceSystemType.TEMPORAL);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param temporalDatum
	 *            temporal datum
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public TemporalCoordinateReferenceSystem(String name,
			TemporalDatum temporalDatum, CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.TEMPORAL, coordinateSystem);
		setTemporalDatum(temporalDatum);
	}

	/**
	 * Get the temporal datum
	 * 
	 * @return temporal datum
	 */
	public TemporalDatum getDatum() {
		return datum;
	}

	/**
	 * Set the temporal datum
	 * 
	 * @param datum
	 *            temporal datum
	 */
	public void setTemporalDatum(TemporalDatum datum) {
		this.datum = datum;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
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
		TemporalCoordinateReferenceSystem other = (TemporalCoordinateReferenceSystem) obj;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		return true;
	}

}
