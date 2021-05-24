package mil.nga.crs.operation;

import java.util.ArrayList;
import java.util.List;

import mil.nga.crs.CRSType;
import mil.nga.crs.CoordinateReferenceSystem;

/**
 * Simple Operation
 * 
 * @author osbornb
 */
public abstract class SimpleOperation extends Operation
		implements CommonOperation {

	/**
	 * Operation Method
	 */
	private OperationMethod method = null;

	/**
	 * Coordinate operation parameters and parameter files
	 */
	private List<Parameter> parameters = null;

	/**
	 * Constructor
	 * 
	 * @param type
	 *            crs operation type
	 */
	public SimpleOperation(CRSType type) {
		super(type);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            crs operation type
	 * @param source
	 *            source crs
	 * @param method
	 *            operation method
	 */
	public SimpleOperation(String name, CRSType type,
			CoordinateReferenceSystem source, OperationMethod method) {
		super(name, type, source);
		setMethod(method);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationMethod getMethod() {
		return method;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMethod(OperationMethod method) {
		this.method = method;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasParameters() {
		return parameters != null && !parameters.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numParameters() {
		return parameters != null ? parameters.size() : 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Parameter getParameter(int index) {
		return parameters.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addParameter(Parameter parameter) {
		if (this.parameters == null) {
			this.parameters = new ArrayList<>();
		}
		this.parameters.add(parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addParameters(List<Parameter> parameters) {
		if (this.parameters == null) {
			this.parameters = new ArrayList<>();
		}
		this.parameters.addAll(parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleOperation other = (SimpleOperation) obj;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}

}
