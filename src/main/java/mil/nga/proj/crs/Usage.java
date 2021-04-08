package mil.nga.proj.crs;

/**
 * Usage
 * 
 * @author osbornb
 */
public class Usage {

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

}
