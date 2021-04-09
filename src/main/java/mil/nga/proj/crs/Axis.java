package mil.nga.proj.crs;

import java.util.ArrayList;
import java.util.List;

/**
 * Axis
 * 
 * @author osbornb
 */
public class Axis {

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
