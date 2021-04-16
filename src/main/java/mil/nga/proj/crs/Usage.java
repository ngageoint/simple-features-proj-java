package mil.nga.proj.crs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Usage
 * 
 * @author osbornb
 */
public class Usage {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(Usage.class.getName());

	/**
	 * Scope
	 */
	private String scope = null;

	/**
	 * Extent
	 */
	private Extent extent = null;

	/**
	 * Constructor
	 */
	public Usage() {

	}

	/**
	 * Constructor
	 * 
	 * @param scope
	 *            scope
	 * @param extent
	 *            extent
	 */
	public Usage(String scope, Extent extent) {
		setScope(scope);
		setExtent(extent);
	}

	/**
	 * Get the scope
	 * 
	 * @return scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Set the scope
	 * 
	 * @param scope
	 *            scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * Get the extent
	 * 
	 * @return extent
	 */
	public Extent getExtent() {
		return extent;
	}

	/**
	 * Set the extent
	 * 
	 * @param extent
	 *            extent
	 */
	public void setExtent(Extent extent) {
		this.extent = extent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extent == null) ? 0 : extent.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
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
		Usage other = (Usage) obj;
		if (extent == null) {
			if (other.extent != null)
				return false;
		} else if (!extent.equals(other.extent))
			return false;
		if (scope == null) {
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
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
			logger.log(Level.WARNING, "Failed to write usage as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
