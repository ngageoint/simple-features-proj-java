package mil.nga.proj.crs;

/**
 * Vertical Extent
 * 
 * @author osbornb
 */
public class VerticalExtent {

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

}
