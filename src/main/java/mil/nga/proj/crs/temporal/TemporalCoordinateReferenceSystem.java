package mil.nga.proj.crs.temporal;

import java.util.logging.Logger;

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
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(TemporalCoordinateReferenceSystem.class.getName());

	/**
	 * Temporal Datum
	 */
	private TemporalDatum temporalDatum = null;

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
	public TemporalDatum getTemporalDatum() {
		return temporalDatum;
	}

	/**
	 * Set the temporal datum
	 * 
	 * @param temporalDatum
	 *            temporal datum
	 */
	public void setTemporalDatum(TemporalDatum temporalDatum) {
		this.temporalDatum = temporalDatum;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((temporalDatum == null) ? 0 : temporalDatum.hashCode());
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
		if (temporalDatum == null) {
			if (other.temporalDatum != null)
				return false;
		} else if (!temporalDatum.equals(other.temporalDatum))
			return false;
		return true;
	}

}
