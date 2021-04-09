package mil.nga.proj.crs;

import java.util.ArrayList;
import java.util.List;

/**
 * Prime Meridian
 * 
 * @author osbornb
 *
 */
public class PrimeMeridian {

	/**
	 * Name
	 */
	private String name = null;;

	/**
	 * IRM longitude
	 */
	private double irmLongitude;

	/**
	 * IRM longitude angle unit
	 */
	private Unit irmLongitudeAngleUnit;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Constructor
	 */
	public PrimeMeridian() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param irmLongitude
	 *            IRM longitude
	 */
	public PrimeMeridian(String name, double irmLongitude) {
		setName(name);
		setIrmLongitude(irmLongitude);
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
	 * Get the IRM longitude
	 * 
	 * @return IRM longitude
	 */
	public double getIrmLongitude() {
		return irmLongitude;
	}

	/**
	 * Set the IRM longitude
	 * 
	 * @param irmLongitude
	 *            IRM longitude
	 */
	public void setIrmLongitude(double irmLongitude) {
		this.irmLongitude = irmLongitude;
	}

	/**
	 * Get the IRM longitude angle unit
	 * 
	 * @return IRM longitude angle unit
	 */
	public Unit getIrmLongitudeAngleUnit() {
		return irmLongitudeAngleUnit;
	}

	/**
	 * Set the IRM longitude angle unit
	 * 
	 * @param irmLongitudeAngleUnit
	 *            IRM longitude angle unit
	 */
	public void setIrmLongitudeAngleUnit(Unit irmLongitudeAngleUnit) {
		this.irmLongitudeAngleUnit = irmLongitudeAngleUnit;
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
