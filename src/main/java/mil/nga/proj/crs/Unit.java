package mil.nga.proj.crs;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit
 * 
 * @author osbornb
 */
public class Unit {

	/**
	 * Unit Type
	 */
	private UnitType type = null;

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Conversion Factor
	 */
	private double conversionFactor;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Constructor
	 */
	public Unit() {

	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            unit type
	 * @param name
	 *            name
	 * @param conversionFactor
	 *            conversion factor
	 */
	public Unit(UnitType type, String name, double conversionFactor) {
		setType(type);
		setName(name);
		setConversionFactor(conversionFactor);
	}

	/**
	 * Get the unit type
	 * 
	 * @return unit type
	 */
	public UnitType getType() {
		return type;
	}

	/**
	 * Set the unit type
	 * 
	 * @param type
	 *            unit type
	 */
	public void setType(UnitType type) {
		this.type = type;
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
	 * Get the conversion factor
	 * 
	 * @return conversion factor
	 */
	public double getConversionFactor() {
		return conversionFactor;
	}

	/**
	 * Set the conversion factor
	 * 
	 * @param conversionFactor
	 *            conversion factor
	 */
	public void setConversionFactor(double conversionFactor) {
		this.conversionFactor = conversionFactor;
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
