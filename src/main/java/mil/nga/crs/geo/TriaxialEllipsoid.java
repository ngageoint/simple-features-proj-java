package mil.nga.crs.geo;

/**
 * Triaxial Ellipsoid
 * 
 * @author osbornb
 */
public class TriaxialEllipsoid extends Ellipsoid {

	/**
	 * Semi Median Axis
	 */
	private double semiMedianAxis;

	/**
	 * Semi Minor Axis
	 */
	private double semiMinorAxis;

	/**
	 * Constructor
	 */
	public TriaxialEllipsoid() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param semiMajorAxis
	 *            semi major axis
	 * @param semiMedianAxis
	 *            semi major axis
	 * @param semiMinorAxis
	 *            semi minor axis
	 */
	public TriaxialEllipsoid(String name, double semiMajorAxis,
			double semiMedianAxis, double semiMinorAxis) {
		setName(name);
		setSemiMajorAxis(semiMajorAxis);
		setSemiMedianAxis(semiMedianAxis);
		setSemiMinorAxis(semiMinorAxis);
	}

	/**
	 * Get the Ellipsoid Type
	 * 
	 * @return ellipsoid type
	 */
	public EllipsoidType getType() {
		return EllipsoidType.TRIAXIAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getInverseFlattening() {
		throw new UnsupportedOperationException(
				"Triaxial Ellipsoid does not support inverse flattening");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInverseFlattening(double inverseFlattening) {
		throw new UnsupportedOperationException(
				"Triaxial Ellipsoid does not support inverse flattening");
	}

	/**
	 * Get the semi median axis
	 * 
	 * @return semi median axis
	 */
	public double getSemiMedianAxis() {
		return semiMedianAxis;
	}

	/**
	 * Set the semi median axis
	 * 
	 * @param semiMedianAxis
	 *            semi median axis
	 */
	public void setSemiMedianAxis(double semiMedianAxis) {
		this.semiMedianAxis = semiMedianAxis;
	}

	/**
	 * Get the semi minor axis
	 * 
	 * @return semi minor axis
	 */
	public double getSemiMinorAxis() {
		return semiMinorAxis;
	}

	/**
	 * Set the semi minor axis
	 * 
	 * @param semiMinorAxis
	 *            semi minor axis
	 */
	public void setSemiMinorAxis(double semiMinorAxis) {
		this.semiMinorAxis = semiMinorAxis;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(semiMedianAxis);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(semiMinorAxis);
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TriaxialEllipsoid other = (TriaxialEllipsoid) obj;
		if (Double.doubleToLongBits(semiMedianAxis) != Double
				.doubleToLongBits(other.semiMedianAxis))
			return false;
		if (Double.doubleToLongBits(semiMinorAxis) != Double
				.doubleToLongBits(other.semiMinorAxis))
			return false;
		return true;
	}

}
