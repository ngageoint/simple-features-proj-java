package mil.nga.crs;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.common.ScopeExtentIdentifierRemark;
import mil.nga.crs.wkt.CRSWriter;

/**
 * Well-known text representation of coordinate reference systems object
 * 
 * @author osbornb
 */
public abstract class CRS implements ScopeExtentIdentifierRemark {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger(CRS.class.getName());

	/**
	 * Type
	 */
	private CRSType type = null;

	/**
	 * Temporary extras that are not included as part of the CRS definition. Not
	 * included in equality and hashing.
	 */
	private Map<String, Object> extras = null;

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
	 * Get the category type
	 * 
	 * @return category type
	 */
	public CategoryType getCategoryType() {
		CategoryType categoryType = null;
		if (type != null) {
			categoryType = type.getCategoryType();
		}
		return categoryType;
	}

	/**
	 * Get temporary extras that are not part of the CRS definition
	 * 
	 * @return extras
	 */
	public Map<String, Object> getExtras() {
		return extras;
	}

	/**
	 * Initialize the extras map if not already
	 */
	public void initializeExtras() {
		if (extras == null) {
			extras = new LinkedHashMap<>();
		}
	}

	/**
	 * Determine if there are temporary extras that are not part of the CRS
	 * definition
	 * 
	 * @return true if extras
	 */
	public boolean hasExtras() {
		return extras != null && !extras.isEmpty();
	}

	/**
	 * Get the number of temporary extras that are not part of the CRS
	 * definition
	 * 
	 * @return number of extras
	 */
	public int numExtras() {
		return extras != null ? extras.size() : 0;
	}

	/**
	 * Get the temporary extra with the name
	 * 
	 * @param name
	 *            extra name
	 * @return extra value or null
	 */
	public Object getExtra(String name) {
		Object extra = null;
		if (hasExtras()) {
			extra = extras.get(name);
		}
		return extra;
	}

	/**
	 * Set the temporary extras that are not part of the CRS definition
	 * 
	 * @param extras
	 *            extras
	 */
	public void setExtras(Map<String, Object> extras) {
		this.extras = extras;
	}

	/**
	 * Add the temporary extra which is not part of the CRS definition
	 * 
	 * @param name
	 *            extra name
	 * @param extra
	 *            extra value
	 */
	public void addExtra(String name, Object extra) {
		initializeExtras();
		extras.put(name, extra);
	}

	/**
	 * Add the temporary extras which are not part of the CRS definition
	 * 
	 * @param extras
	 *            extra values
	 */
	public void addExtras(Map<String, Object> extras) {
		initializeExtras();
		this.extras.putAll(extras);
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
