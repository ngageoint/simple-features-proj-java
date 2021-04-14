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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anchor == null) ? 0 : anchor.hashCode());
		result = prime * result
				+ ((ellipsoid == null) ? 0 : ellipsoid.hashCode());
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((primeMeridian == null) ? 0 : primeMeridian.hashCode());
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
		GeodeticReferenceFrame other = (GeodeticReferenceFrame) obj;
		if (anchor == null) {
			if (other.anchor != null)
				return false;
		} else if (!anchor.equals(other.anchor))
			return false;
		if (ellipsoid == null) {
			if (other.ellipsoid != null)
				return false;
		} else if (!ellipsoid.equals(other.ellipsoid))
			return false;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (primeMeridian == null) {
			if (other.primeMeridian != null)
				return false;
		} else if (!primeMeridian.equals(other.primeMeridian))
			return false;
		return true;
	}

}
