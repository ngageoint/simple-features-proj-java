package mil.nga.proj.crs.parametric;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.CoordinateSystem;

/**
 * Parametric Coordinate Reference System
 * 
 * @author osbornb
 */
public class ParametricCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

	/**
	 * Parametric Datum
	 */
	private ParametricDatum datum = null;

	/**
	 * Constructor
	 */
	public ParametricCoordinateReferenceSystem() {
		super(CoordinateReferenceSystemType.PARAMETRIC);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param datum
	 *            parametric datum
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public ParametricCoordinateReferenceSystem(String name,
			ParametricDatum datum, CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.PARAMETRIC, coordinateSystem);
		setDatum(datum);
	}

	/**
	 * Get the parametric datum
	 * 
	 * @return parametric datum
	 */
	public ParametricDatum getDatum() {
		return datum;
	}

	/**
	 * Set the parametric datum
	 * 
	 * @param datum
	 *            parametric datum
	 */
	public void setDatum(ParametricDatum datum) {
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
		ParametricCoordinateReferenceSystem other = (ParametricCoordinateReferenceSystem) obj;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		return true;
	}

}
