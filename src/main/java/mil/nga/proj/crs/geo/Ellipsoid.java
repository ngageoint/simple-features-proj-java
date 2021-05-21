package mil.nga.proj.crs.geo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.common.Identifiable;
import mil.nga.proj.crs.common.Identifier;
import mil.nga.proj.crs.common.Unit;
import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Ellipsoid
 * 
 * @author osbornb
 */
public class Ellipsoid implements Identifiable {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(Ellipsoid.class.getName());

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
	 * Unit (Length)
	 */
	private Unit unit = null;

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
	 * Get the Ellipsoid Type
	 * 
	 * @return ellipsoid type
	 */
	public EllipsoidType getType() {
		return EllipsoidType.OBLATE;
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
	 * Get the unit (length)
	 * 
	 * @return unit (length)
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Has a unit (length)
	 * 
	 * @return true if has unit (length)
	 */
	public boolean hasUnit() {
		return getUnit() != null;
	}

	/**
	 * Set the unit (length)
	 * 
	 * @param unit
	 *            unit (length)
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasIdentifiers() {
		return identifiers != null && !identifiers.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numIdentifiers() {
		return identifiers != null ? identifiers.size() : 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identifier getIdentifier(int index) {
		return identifiers.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addIdentifier(Identifier identifier) {
		if (this.identifiers == null) {
			this.identifiers = new ArrayList<>();
		}
		this.identifiers.add(identifier);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addIdentifiers(List<Identifier> identifiers) {
		if (this.identifiers == null) {
			this.identifiers = new ArrayList<>();
		}
		this.identifiers.addAll(identifiers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		long temp;
		temp = Double.doubleToLongBits(inverseFlattening);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(semiMajorAxis);
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
		Ellipsoid other = (Ellipsoid) obj;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (Double.doubleToLongBits(inverseFlattening) != Double
				.doubleToLongBits(other.inverseFlattening))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(semiMajorAxis) != Double
				.doubleToLongBits(other.semiMajorAxis))
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
			logger.log(Level.WARNING, "Failed to write ellipsoid as a string",
					e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
