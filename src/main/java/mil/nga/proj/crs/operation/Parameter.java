package mil.nga.proj.crs.operation;

import mil.nga.proj.crs.common.Identifiable;

/**
 * Operation Parameter interface
 * 
 * @author osbornb
 */
public interface Parameter extends Identifiable {

	/**
	 * Get the parameter type
	 * 
	 * @return parameter type
	 */
	public ParameterType getParameterType();

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

}
