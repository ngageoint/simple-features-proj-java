package mil.nga.proj.crs.operation;

import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * Point Motion Operation
 * 
 * @author osbornb
 */
public class PointMotionOperation extends Operation
		implements ConcatenableOperation {

	/**
	 * Operation Method
	 */
	private OperationMethod method = null;

	/**
	 * Constructor
	 */
	public PointMotionOperation() {
		super(CRSType.POINT_MOTION_OPERATION);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param source
	 *            source crs
	 * @param method
	 *            operation method
	 */
	public PointMotionOperation(String name, CoordinateReferenceSystem source,
			OperationMethod method) {
		super(name, CRSType.POINT_MOTION_OPERATION, source);
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
		PointMotionOperation other = (PointMotionOperation) obj;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		return true;
	}

}
