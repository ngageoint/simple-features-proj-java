package mil.nga.proj.crs.geodetic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.common.Identifier;
import mil.nga.proj.crs.common.Unit;
import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Prime Meridian
 * 
 * @author osbornb
 *
 */
public class PrimeMeridian {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(PrimeMeridian.class.getName());

	/**
	 * Name
	 */
	private String name = null;;

	/**
	 * IRM longitude
	 */
	private double irmLongitude;

	/**
	 * IRM longitude unit (angle)
	 */
	private Unit irmLongitudeUnit;

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
	 * Get the IRM longitude unit (angle)
	 * 
	 * @return IRM longitude unit (angle)
	 */
	public Unit getIrmLongitudeUnit() {
		return irmLongitudeUnit;
	}

	/**
	 * Has an IRM longitude unit (angle)
	 * 
	 * @return true if has IRM longitude unit (angle)
	 */
	public boolean hasIrmLongitudeUnit() {
		return getIrmLongitudeUnit() != null;
	}

	/**
	 * Set the IRM longitude unit (angle)
	 * 
	 * @param irmLongitudeUnit
	 *            IRM longitude unit (angle)
	 */
	public void setIrmLongitudeUnit(Unit irmLongitudeUnit) {
		this.irmLongitudeUnit = irmLongitudeUnit;
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
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		long temp;
		temp = Double.doubleToLongBits(irmLongitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((irmLongitudeUnit == null) ? 0
				: irmLongitudeUnit.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PrimeMeridian other = (PrimeMeridian) obj;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (Double.doubleToLongBits(irmLongitude) != Double
				.doubleToLongBits(other.irmLongitude))
			return false;
		if (irmLongitudeUnit == null) {
			if (other.irmLongitudeUnit != null)
				return false;
		} else if (!irmLongitudeUnit.equals(other.irmLongitudeUnit))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
			logger.log(Level.WARNING,
					"Failed to write prime meridian as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
