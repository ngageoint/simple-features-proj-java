package mil.nga.crs.projected;

import mil.nga.crs.operation.OperationMethod;

/**
 * Map Projection Method
 * 
 * @author osbornb
 */
public class MapProjectionMethod extends OperationMethod {

	/**
	 * Commonly encountered map projection methods
	 */
	private MapProjectionMethods method;

	/**
	 * Constructor
	 */
	public MapProjectionMethod() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 */
	public MapProjectionMethod(String name) {
		super(name);
		updateMethod();
	}

	/**
	 * Constructor
	 * 
	 * @param method
	 *            map projection method
	 */
	public MapProjectionMethod(MapProjectionMethods method) {
		super(method.getName());
		setMethod(method);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		super.setName(name);
		updateMethod();
	}

	/**
	 * Get the commonly known method type
	 * 
	 * @return method type or null
	 */
	public MapProjectionMethods getMethod() {
		return method;
	}

	/**
	 * Set the commonly known method type
	 * 
	 * @param method
	 *            method type or null
	 */
	public void setMethod(MapProjectionMethods method) {
		this.method = method;
	}

	/**
	 * Update the commonly known method type using the name
	 */
	public void updateMethod() {
		setMethod(MapProjectionMethods.getMethod(getName()));
	}

}
