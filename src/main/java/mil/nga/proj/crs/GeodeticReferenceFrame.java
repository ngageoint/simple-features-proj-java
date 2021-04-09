package mil.nga.proj.crs;

import java.util.ArrayList;
import java.util.List;

/**
 * Geodetic Reference Frame (datum)
 * 
 * @author osbornb
 */
public class GeodeticReferenceFrame {

	/**
	 * Datum Name
	 */
	private String name = null;

	/**
	 * Ellipsoid
	 */
	private Ellipsoid ellipsoid = null;

	/**
	 * Datum anchor description
	 */
	private String anchor = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Prime Meridian
	 */
	private PrimeMeridian primeMeridian = null;

	/**
	 * Constructor
	 */
	public GeodeticReferenceFrame() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param ellipsoid
	 *            ellipsoid
	 */
	public GeodeticReferenceFrame(String name, Ellipsoid ellipsoid) {
		setName(name);
		setEllipsoid(ellipsoid);
	}

	/**
	 * Get the datum name
	 * 
	 * @return datum name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the datum name
	 * 
	 * @param name
	 *            datum name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the ellipsoid
	 * 
	 * @return ellipsoid
	 */
	public Ellipsoid getEllipsoid() {
		return ellipsoid;
	}

	/**
	 * Set the ellipsoid
	 * 
	 * @param ellipsoid
	 *            ellipsoid
	 */
	public void setEllipsoid(Ellipsoid ellipsoid) {
		this.ellipsoid = ellipsoid;
	}

	/**
	 * Get the datum anchor description
	 * 
	 * @return datum anchor description
	 */
	public String getAnchor() {
		return anchor;
	}

	/**
	 * Has a datum anchor description
	 * 
	 * @return true if has datum anchor description
	 */
	public boolean hasAnchor() {
		return getAnchor() != null;
	}

	/**
	 * Set the datum anchor description
	 * 
	 * @param anchor
	 *            datum anchor description
	 */
	public void setAnchor(String anchor) {
		this.anchor = anchor;
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
	 * Get the prime meridian
	 * 
	 * @return prime meridian
	 */
	public PrimeMeridian getPrimeMeridian() {
		return primeMeridian;
	}

	/**
	 * Has a prime meridian
	 * 
	 * @return true if has prime meridian
	 */
	public boolean hasPrimeMeridian() {
		return getPrimeMeridian() != null;
	}

	/**
	 * Set the prime meridian
	 * 
	 * @param primeMeridian
	 *            prime meridian
	 */
	public void setPrimeMeridian(PrimeMeridian primeMeridian) {
		this.primeMeridian = primeMeridian;
	}

}
