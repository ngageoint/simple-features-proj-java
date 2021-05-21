package mil.nga.proj.crs.operation;

import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * Point Motion Operation
 * 
 * @author osbornb
 */
public class PointMotionOperation extends SimpleOperation {

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
		super(name, CRSType.POINT_MOTION_OPERATION, source, method);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationType getOperationType() {
		return OperationType.POINT_MOTION_OPERATION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
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
		return true;
	}

}
