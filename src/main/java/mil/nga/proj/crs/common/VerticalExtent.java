package mil.nga.proj.crs.common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Vertical Extent
 * 
 * @author osbornb
 */
public class VerticalExtent {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(VerticalExtent.class.getName());

	/**
	 * Minimum Height
	 */
	private double minimumHeight;

	/**
	 * Maximum Height
	 */
	private double maximumHeight;

	/**
	 * Length unit
	 */
	private Unit lengthUnit = null;

	/**
	 * Constructor
	 */
	public VerticalExtent() {

	}

	/**
	 * Constructor
	 * 
	 * @param minimumHeight
	 *            minimum height
	 * @param maximumHeight
	 *            maximum height
	 */
	public VerticalExtent(double minimumHeight, double maximumHeight) {
		this(minimumHeight, maximumHeight, null);
	}

	/**
	 * Constructor
	 * 
	 * @param minimumHeight
	 *            minimum height
	 * @param maximumHeight
	 *            maximum height
	 * @param lengthUnit
	 *            length unit
	 */
	public VerticalExtent(double minimumHeight, double maximumHeight,
			Unit lengthUnit) {
		setMinimumHeight(minimumHeight);
		setMaximumHeight(maximumHeight);
		setLengthUnit(lengthUnit);
	}

	/**
	 * Get the minimum height
	 * 
	 * @return minimum height
	 */
	public double getMinimumHeight() {
		return minimumHeight;
	}

	/**
	 * Set the minimum height
	 * 
	 * @param minimumHeight
	 *            minimum height
	 */
	public void setMinimumHeight(double minimumHeight) {
		this.minimumHeight = minimumHeight;
	}

	/**
	 * Get the maximum height
	 * 
	 * @return maximum height
	 */
	public double getMaximumHeight() {
		return maximumHeight;
	}

	/**
	 * Set the maximum height
	 * 
	 * @param maximumHeight
	 *            maximum height
	 */
	public void setMaximumHeight(double maximumHeight) {
		this.maximumHeight = maximumHeight;
	}

	/**
	 * Get the length unit
	 * 
	 * @return length unit
	 */
	public Unit getLengthUnit() {
		return lengthUnit;
	}

	/**
	 * Has a length unit
	 * 
	 * @return true if has length unit
	 */
	public boolean hasLengthUnit() {
		return getLengthUnit() != null;
	}

	/**
	 * Set the length unit
	 * 
	 * @param lengthUnit
	 *            length unit
	 */
	public void setLengthUnit(Unit lengthUnit) {
		this.lengthUnit = lengthUnit;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lengthUnit == null) ? 0 : lengthUnit.hashCode());
		long temp;
		temp = Double.doubleToLongBits(maximumHeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minimumHeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerticalExtent other = (VerticalExtent) obj;
		if (lengthUnit == null) {
			if (other.lengthUnit != null)
				return false;
		} else if (!lengthUnit.equals(other.lengthUnit))
			return false;
		if (Double.doubleToLongBits(maximumHeight) != Double
				.doubleToLongBits(other.maximumHeight))
			return false;
		if (Double.doubleToLongBits(minimumHeight) != Double
				.doubleToLongBits(other.minimumHeight))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String value = null;
		CRSWriter writer = new CRSWriter();
		try {
			writer.write(this);
			value = writer.toString();
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"Failed to write vertical extent as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
