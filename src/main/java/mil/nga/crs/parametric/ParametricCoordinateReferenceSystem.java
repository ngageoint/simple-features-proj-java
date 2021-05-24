package mil.nga.crs.parametric;

import mil.nga.crs.CRSType;
import mil.nga.crs.SimpleCoordinateReferenceSystem;
import mil.nga.crs.common.CoordinateSystem;

/**
 * Parametric Coordinate Reference System
 * 
 * @author osbornb
 */
public class ParametricCoordinateReferenceSystem
		extends SimpleCoordinateReferenceSystem {

	/**
	 * Parametric Datum
	 */
	private ParametricDatum datum = null;

	/**
	 * Constructor
	 */
	public ParametricCoordinateReferenceSystem() {
		super(CRSType.PARAMETRIC);
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
		super(name, CRSType.PARAMETRIC, coordinateSystem);
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
