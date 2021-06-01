package mil.nga.crs.geo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.common.Identifiable;
import mil.nga.crs.common.Identifier;
import mil.nga.crs.common.Unit;
import mil.nga.crs.wkt.CRSWriter;

/**
 * Prime Meridian
 * 
 * @author osbornb
 *
 */
public class PrimeMeridian implements Identifiable {

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
	 * International Reference Meridian longitude
	 */
	private double longitude;

	/**
	 * International Reference Meridian longitude unit (angle)
	 */
	private Unit longitudeUnit;

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
	 * @param longitude
	 *            International Reference Meridian longitude
	 */
	public PrimeMeridian(String name, double longitude) {
		setName(name);
		setLongitude(longitude);
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
	 * Get the International Reference Meridian longitude
	 * 
	 * @return International Reference Meridian longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Set the International Reference Meridian longitude
	 * 
	 * @param longitude
	 *            International Reference Meridian longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Get the International Reference Meridian longitude unit (angle)
	 * 
	 * @return International Reference Meridian longitude unit (angle)
	 */
	public Unit getLongitudeUnit() {
		return longitudeUnit;
	}

	/**
	 * Has an International Reference Meridian longitude unit (angle)
	 * 
	 * @return true if has International Reference Meridian longitude unit
	 *         (angle)
	 */
	public boolean hasLongitudeUnit() {
		return getLongitudeUnit() != null;
	}

	/**
	 * Set the International Reference Meridian longitude unit (angle)
	 * 
	 * @param longitudeUnit
	 *            International Reference Meridian longitude unit (angle)
	 */
	public void setLongitudeUnit(Unit longitudeUnit) {
		this.longitudeUnit = longitudeUnit;
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
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((longitudeUnit == null) ? 0 : longitudeUnit.hashCode());
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
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (longitudeUnit == null) {
			if (other.longitudeUnit != null)
				return false;
		} else if (!longitudeUnit.equals(other.longitudeUnit))
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
