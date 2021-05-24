package mil.nga.crs.engineering;

import mil.nga.crs.CRSType;
import mil.nga.crs.SimpleCoordinateReferenceSystem;
import mil.nga.crs.common.CoordinateSystem;

/**
 * Engineering Coordinate Reference System
 * 
 * @author osbornb
 */
public class EngineeringCoordinateReferenceSystem
		extends SimpleCoordinateReferenceSystem {

	/**
	 * Engineering Datum
	 */
	private EngineeringDatum datum = null;

	/**
	 * Constructor
	 */
	public EngineeringCoordinateReferenceSystem() {
		super(CRSType.ENGINEERING);
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
		super(name, CRSType.ENGINEERING,
				coordinateSystem);
		setDatum(engineeringDatum);
	}

	/**
	 * Get the engineering datum
	 * 
	 * @return engineering datum
	 */
	public EngineeringDatum getDatum() {
		return datum;
	}

	/**
	 * Set the engineering datum
	 * 
	 * @param datum
	 *            engineering datum
	 */
	public void setDatum(EngineeringDatum datum) {
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
		EngineeringCoordinateReferenceSystem other = (EngineeringCoordinateReferenceSystem) obj;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		return true;
	}

}
