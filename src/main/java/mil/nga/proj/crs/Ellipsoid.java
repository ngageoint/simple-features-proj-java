package mil.nga.proj.crs;

import java.util.ArrayList;
import java.util.List;

/**
 * Ellipsoid
 * 
 * @author osbornb
 */
public class Ellipsoid {

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Semi Major Axis
	 */
	private double semiMajorAxis;

	/**
	 * Inverse Flattening
	 */
	private double inverseFlattening;

	/**
	 * Length Unit
	 */
	private Unit lengthUnit = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Constructor
	 */
	public Ellipsoid() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param semiMajorAxis
	 *            semi major axis
	 * @param inverseFlattening
	 *            inverse flattening
	 */
	public Ellipsoid(String name, double semiMajorAxis,
			double inverseFlattening) {
		setName(name);
		setSemiMajorAxis(semiMajorAxis);
		setInverseFlattening(inverseFlattening);
	}

	/**
	 * Get the name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name
	 * 
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the semi major axis
	 * 
	 * @return semi major axis
	 */
	public double getSemiMajorAxis() {
		return semiMajorAxis;
	}

	/**
	 * Set the semi major axis
	 * 
	 * @param semiMajorAxis
	 *            semi major axis
	 */
	public void setSemiMajorAxis(double semiMajorAxis) {
		this.semiMajorAxis = semiMajorAxis;
	}

	/**
	 * Get the inverse flattening
	 * 
	 * @return inverse flattening
	 */
	public double getInverseFlattening() {
		return inverseFlattening;
	}

	/**
	 * Set the inverse flattening
	 * 
	 * @param inverseFlattening
	 *            inverse flattening
	 */
	public void setInverseFlattening(double inverseFlattening) {
		this.inverseFlattening = inverseFlattening;
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
	 * Get the identifiers
	 * 
	 * @return identifiers
	 */
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	/**
	 * Has identifiers
	 * 
	 * @return true if has identifiers
	 */
	public boolean hasIdentifiers() {
		return identifiers != null && !identifiers.isEmpty();
	}

	/**
	 * Set the identifiers
	 * 
	 * @param identifiers
	 *            identifiers
	 */
	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}

	/**
	 * Add the identifier
	 * 
	 * @param identifier
	 *            identifier
	 */
	public void addIdentifier(Identifier identifier) {
		if (this.identifiers == null) {
			this.identifiers = new ArrayList<>();
		}
		this.identifiers.add(identifier);
	}

	/**
	 * Add the identifiers
	 * 
	 * @param identifiers
	 *            identifiers
	 */
	public void addIdentifiers(List<Identifier> identifiers) {
		if (this.identifiers == null) {
			this.identifiers = new ArrayList<>();
		}
		this.identifiers.addAll(identifiers);
	}

}
