package mil.nga.crs.common;

import java.util.List;

/**
 * Identifiable object interface
 * 
 * @author osbornb
 */
public interface Identifiable {

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
	 * Number of identifiers
	 * 
	 * @return identifiers count
	 */
	public int numIdentifiers();

	/**
	 * Get the identifier at the index
	 * 
	 * @param index
	 *            identifier index
	 * @return identifier
	 */
	public Identifier getIdentifier(int index);

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

}
