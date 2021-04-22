package mil.nga.proj.crs.parametric;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Parametric Coordinate Reference System
 * 
 * @author osbornb
 */
public class ParametricCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ParametricCoordinateReferenceSystem.class.getName());

	/**
	 * Parametric Datum
	 */
	private ParametricDatum parametricDatum = null;

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
	 * @param parametricDatum
	 *            parametric datum
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public ParametricCoordinateReferenceSystem(String name,
			ParametricDatum parametricDatum,
			CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.PARAMETRIC, coordinateSystem);
		setParametricDatum(parametricDatum);
	}

	/**
	 * Get the parametric datum
	 * 
	 * @return parametric datum
	 */
	public ParametricDatum getParametricDatum() {
		return parametricDatum;
	}

	/**
	 * Set the parametric datum
	 * 
	 * @param parametricDatum
	 *            parametric datum
	 */
	public void setParametricDatum(ParametricDatum parametricDatum) {
		this.parametricDatum = parametricDatum;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((parametricDatum == null) ? 0 : parametricDatum.hashCode());
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
		if (parametricDatum == null) {
			if (other.parametricDatum != null)
				return false;
		} else if (!parametricDatum.equals(other.parametricDatum))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String value = null;
		try {
			value = CRSWriter.writeCRS(this);
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"Failed to write parametric coordinate reference system as a string",
					e);
			value = super.toString();
		}
		return value;
	}

}
