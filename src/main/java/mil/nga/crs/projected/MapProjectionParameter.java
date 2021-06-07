package mil.nga.crs.projected;

import mil.nga.crs.operation.OperationParameter;

/**
 * Map Projection Parameter
 * 
 * @author osbornb
 */
public class MapProjectionParameter extends OperationParameter {

	/**
	 * Commonly encountered map projection parameters
	 */
	private MapProjectionParameters parameter;

	/**
	 * Constructor
	 */
	public MapProjectionParameter() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param value
	 *            value
	 */
	public MapProjectionParameter(String name, double value) {
		super(name, value);
		updateParameter();
	}

	/**
	 * Constructor
	 * 
	 * @param parameter
	 *            map projection parameter
	 * @param value
	 *            value
	 */
	public MapProjectionParameter(MapProjectionParameters parameter,
			double value) {
		super(parameter.getName(), value);
		setParameter(parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		super.setName(name);
		updateParameter();
	}

	/**
	 * Get the commonly known parameter type
	 * 
	 * @return parameter type or null
	 */
	public MapProjectionParameters getParameter() {
		return parameter;
	}

	/**
	 * Is a commonly known parameter type
	 * 
	 * @return true if has common parameter type
	 */
	public boolean hasParameter() {
		return getParameter() != null;
	}

	/**
	 * Set the commonly known parameter type
	 * 
	 * @param parameter
	 *            parameter type or null
	 */
	public void setParameter(MapProjectionParameters parameter) {
		this.parameter = parameter;
	}

	/**
	 * Update the commonly known parameter type using the name
	 */
	public void updateParameter() {
		setParameter(MapProjectionParameters.getParameter(getName()));
	}

}
