package mil.nga.proj.crs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Axis
 * 
 * @author osbornb
 */
public class Axis {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger(Axis.class.getName());

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Abbreviation
	 */
	private String abbreviation = null;

	/**
	 * Axis Direction
	 */
	private AxisDirectionType direction = null;

	/**
	 * Meridian
	 */
	private Double meridian = null;

	/**
	 * Meridian Angle Unit
	 */
	private Unit meridianAngleUnit = null;

	/**
	 * Bearing
	 */
	private Double bearing = null;

	/**
	 * 
	 */
	private Integer order = null;

	/**
	 * Unit
	 */
	private Unit unit = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Axis
	 */
	public Axis() {

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
	 * Has a name
	 * 
	 * @return true if has name
	 */
	public boolean hasName() {
		return getName() != null;
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
	 * Get the abbreviation
	 * 
	 * @return abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * Has an abbreviation
	 * 
	 * @return true if has abbreviation
	 */
	public boolean hasAbbreviation() {
		return getAbbreviation() != null;
	}

	/**
	 * Set the abbreviation
	 * 
	 * @param abbreviation
	 *            abbreviation
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	/**
	 * Get the direction
	 * 
	 * @return direction type
	 */
	public AxisDirectionType getDirection() {
		return direction;
	}

	/**
	 * Set the direction
	 * 
	 * @param direction
	 *            direction type
	 */
	public void setDirection(AxisDirectionType direction) {
		this.direction = direction;
	}

	/**
	 * Get the meridian
	 * 
	 * @return meridian
	 */
	public Double getMeridian() {
		return meridian;
	}

	/**
	 * Has a meridian
	 * 
	 * @return true if has meridian
	 */
	public boolean hasMeridian() {
		return getMeridian() != null;
	}

	/**
	 * Set the meridian
	 * 
	 * @param meridian
	 *            meridian
	 */
	public void setMeridian(Double meridian) {
		this.meridian = meridian;
	}

	/**
	 * Get the meridian angle unit
	 * 
	 * @return meridian angle unit
	 */
	public Unit getMeridianAngleUnit() {
		return meridianAngleUnit;
	}

	/**
	 * Set the meridian angle unit
	 * 
	 * @param meridianAngleUnit
	 *            meridian angle unit
	 */
	public void setMeridianAngleUnit(Unit meridianAngleUnit) {
		this.meridianAngleUnit = meridianAngleUnit;
	}

	/**
	 * Get the bearing
	 * 
	 * @return bearing
	 */
	public Double getBearing() {
		return bearing;
	}

	/**
	 * Has a bearing
	 * 
	 * @return true if has bearing
	 */
	public boolean hasBearing() {
		return getBearing() != null;
	}

	/**
	 * Set the bearing
	 * 
	 * @param bearing
	 *            bearing
	 */
	public void setBearing(Double bearing) {
		this.bearing = bearing;
	}

	/**
	 * Get the order
	 * 
	 * @return order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * Has an order
	 * 
	 * @return true if has order
	 */
	public boolean hasOrder() {
		return getOrder() != null;
	}

	/**
	 * Set the order
	 * 
	 * @param order
	 *            order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * Get the unit
	 * 
	 * @return unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Has a unit
	 * 
	 * @return true if has unit
	 */
	public boolean hasUnit() {
		return getUnit() != null;
	}

	/**
	 * Set the unit
	 * 
	 * @param unit
	 *            unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result + ((bearing == null) ? 0 : bearing.hashCode());
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result
				+ ((meridian == null) ? 0 : meridian.hashCode());
		result = prime * result + ((meridianAngleUnit == null) ? 0
				: meridianAngleUnit.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
		Axis other = (Axis) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null)
				return false;
		} else if (!abbreviation.equals(other.abbreviation))
			return false;
		if (bearing == null) {
			if (other.bearing != null)
				return false;
		} else if (!bearing.equals(other.bearing))
			return false;
		if (direction != other.direction)
			return false;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (meridian == null) {
			if (other.meridian != null)
				return false;
		} else if (!meridian.equals(other.meridian))
			return false;
		if (meridianAngleUnit == null) {
			if (other.meridianAngleUnit != null)
				return false;
		} else if (!meridianAngleUnit.equals(other.meridianAngleUnit))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
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
			logger.log(Level.WARNING, "Failed to write axis as a string", e);
			value = super.toString();
		}
		writer.close();
		return value;
	}

}
