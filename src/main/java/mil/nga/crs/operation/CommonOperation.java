package mil.nga.crs.operation;

import mil.nga.crs.common.Identifiable;

/**
 * Common Operation interface
 * 
 * @author osbornb
 */
public interface CommonOperation extends Identifiable {

	/**
	 * Get the operation type
	 * 
	 * @return operation type
	 */
	public OperationType getOperationType();

	/**
	 * Get the name
	 * 
	 * @return name
	 */
	public String getName();

	/**
	 * Set the name
	 * 
	 * @param name
	 *            name
	 */
	public void setName(String name);

	/**
	 * Get the operation version
	 * 
	 * @return operation version
	 */
	public String getVersion();

	/**
	 * Has an operation version
	 * 
	 * @return true if has operation version
	 */
	public boolean hasVersion();

	/**
	 * Set the operation version
	 * 
	 * @param version
	 *            operation version
	 */
	public void setVersion(String version);

	/**
	 * Get the method
	 * 
	 * @return method
	 */
	public OperationMethod getMethod();

	/**
	 * Set the method
	 * 
	 * @param method
	 *            method
	 */
	public void setMethod(OperationMethod method);

}
