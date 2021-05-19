package mil.nga.proj.crs.operation;

import java.util.List;

import mil.nga.proj.crs.common.Identifier;

/**
 * Concatenable Operation interface
 * 
 * @author osbornb
 */
public interface ConcatenableOperation {

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

	/**
	 * Get the parameters
	 * 
	 * @return parameters
	 */
	public List<Parameter> getParameters();

	/**
	 * Has parameters
	 * 
	 * @return true if has parameters
	 */
	public boolean hasParameters();

	/**
	 * Set the parameters
	 * 
	 * @param parameters
	 *            parameters
	 */
	public void setParameters(List<Parameter> parameters);

	/**
	 * Add the parameter
	 * 
	 * @param parameter
	 *            parameter
	 */
	public void addParameter(Parameter parameter);

	/**
	 * Add the parameters
	 * 
	 * @param parameters
	 *            parameters
	 */
	public void addParameters(List<Parameter> parameters);

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

}
