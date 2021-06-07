package mil.nga.crs.operation;

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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((method == null) ? 0 : method.hashCode());
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
		return true;
	}

}
