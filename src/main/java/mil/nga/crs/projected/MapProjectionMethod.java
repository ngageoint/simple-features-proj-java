package mil.nga.crs.projected;

import java.util.List;

import mil.nga.crs.operation.OperationMethod;
import mil.nga.crs.operation.Parameter;
import mil.nga.proj.ProjectionException;

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

	/**
	 * Get the parameters
	 * 
	 * @return parameters
	 */
	public List<MapProjectionParameter> getMapProjectionParameters() {
		@SuppressWarnings("unchecked")
		List<MapProjectionParameter> mapProjectionParameters = (List<MapProjectionParameter>) (List<?>) getParameters();
		return mapProjectionParameters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MapProjectionParameter getParameter(int index) {
		return (MapProjectionParameter) super.getParameter(index);
	}

	/**
	 * Set the parameters
	 * 
	 * @param parameters
	 *            parameters
	 */
	public void setMapProjectionParameters(
			List<MapProjectionParameter> parameters) {
		@SuppressWarnings("unchecked")
		List<Parameter> params = (List<Parameter>) (List<?>) parameters;
		setParameters(params);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addParameter(Parameter parameter) {
		if (!(parameter instanceof MapProjectionParameter)) {
			throw new ProjectionException(
					"Parameter is not a Map Projection Parameter. name: "
							+ parameter.getName() + ", type: "
							+ parameter.getClass().getSimpleName());
		}
		super.addParameter(parameter);
	}

	/**
	 * Add the parameters
	 * 
	 * @param parameters
	 *            parameters
	 */
	public void addMapProjectionParameters(
			List<MapProjectionParameter> parameters) {
		@SuppressWarnings("unchecked")
		List<Parameter> params = (List<Parameter>) (List<?>) parameters;
		addParameters(params);
	}

}
