package mil.nga.proj.crs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Well-known text representation of coordinate reference systems object
 * 
 * @author osbornb
 */
public abstract class CRS {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger(CRS.class.getName());

	/**
	 * Type
	 */
	private CRSType type = null;

	/**
	 * Constructor
	 */
	public CRS() {

	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            coordinate reference system type
	 */
	public CRS(CRSType type) {
		setType(type);
	}

	/**
	 * Get the type
	 * 
	 * @return type
	 */
	public CRSType getType() {
		return type;
	}

	/**
	 * Set the type
	 * 
	 * @param type
	 *            type
	 */
	public void setType(CRSType type) {
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CRS other = (CRS) obj;
		if (type != other.type)
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String value = null;
		try {
			value = CRSWriter.write(this);
		} catch (IOException e) {
			logger.log(Level.WARNING, "Failed to write CRS WKT, type: " + type,
					e);
			value = super.toString();
		}
		return value;
	}

}
