package mil.nga.proj.crs.common;

import java.util.List;

/**
 * Interface Scope, Extent, Identifier, and Remark values
 * 
 * @author osbornb
 */
public interface ScopeExtentIdentifierRemark {

	/**
	 * Get the usages
	 * 
	 * @return usages
	 */
	public List<Usage> getUsages();

	/**
	 * Has usages
	 * 
	 * @return true if has usages
	 */
	public boolean hasUsages();

	/**
	 * Set the usages
	 * 
	 * @param usages
	 *            usages
	 */
	public void setUsages(List<Usage> usages);

	/**
	 * Add the usage
	 * 
	 * @param usage
	 *            usage
	 */
	public void addUsage(Usage usage);

	/**
	 * Add the usages
	 * 
	 * @param usages
	 *            usages
	 */
	public void addUsages(List<Usage> usages);

	/**
	 * Get the identifiers
	 * 
	 * @return identifiers
	 */
	public List<Identifier> getIdentifiers();

	/**
	 * Has identifiers
	 * 
	 * @return true if has identifiers
	 */
	public boolean hasIdentifiers();

	/**
	 * Set the identifiers
	 * 
	 * @param identifiers
	 *            identifiers
	 */
	public void setIdentifiers(List<Identifier> identifiers);

	/**
	 * Add the identifier
	 * 
	 * @param identifier
	 *            identifier
	 */
	public void addIdentifier(Identifier identifier);

	/**
	 * Add the identifiers
	 * 
	 * @param identifiers
	 *            identifiers
	 */
	public void addIdentifiers(List<Identifier> identifiers);

	/**
	 * Get the remark
	 * 
	 * @return remark
	 */
	public String getRemark();

	/**
	 * Has remark
	 * 
	 * @return true if has remark
	 */
	public boolean hasRemark();

	/**
	 * Set the remark
	 * 
	 * @param remark
	 *            remark
	 */
	public void setRemark(String remark);

}
