package mil.nga.proj.crs.engineering;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.CoordinateSystem;

/**
 * Engineering Coordinate Reference System
 * 
 * @author osbornb
 */
public class EngineeringCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

	/**
	 * Engineering Datum
	 */
	private EngineeringDatum engineeringDatum = null;

	/**
	 * Constructor
	 */
	public EngineeringCoordinateReferenceSystem() {
		super(CoordinateReferenceSystemType.ENGINEERING);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param engineeringDatum
	 *            engineering datum
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public EngineeringCoordinateReferenceSystem(String name,
			EngineeringDatum engineeringDatum,
			CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.ENGINEERING,
				coordinateSystem);
		setEngineeringDatum(engineeringDatum);
	}

	/**
	 * Get the engineering datum
	 * 
	 * @return engineering datum
	 */
	public EngineeringDatum getEngineeringDatum() {
		return engineeringDatum;
	}

	/**
	 * Set the engineering datum
	 * 
	 * @param engineeringDatum
	 *            engineering datum
	 */
	public void setEngineeringDatum(EngineeringDatum engineeringDatum) {
		this.engineeringDatum = engineeringDatum;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((engineeringDatum == null) ? 0
				: engineeringDatum.hashCode());
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
		EngineeringCoordinateReferenceSystem other = (EngineeringCoordinateReferenceSystem) obj;
		if (engineeringDatum == null) {
			if (other.engineeringDatum != null)
				return false;
		} else if (!engineeringDatum.equals(other.engineeringDatum))
			return false;
		return true;
	}

}
