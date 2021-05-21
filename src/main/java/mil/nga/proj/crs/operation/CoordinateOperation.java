package mil.nga.proj.crs.operation;

import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * Coordinate Operation
 * 
 * @author osbornb
 */
public class CoordinateOperation extends SimpleOperation {

	/**
	 * Target Coordinate Reference System
	 */
	private CoordinateReferenceSystem target = null;

	/**
	 * Interpolation Coordinate Reference System
	 */
	private CoordinateReferenceSystem interpolation = null;

	/**
	 * Constructor
	 */
	public CoordinateOperation() {
		super(CRSType.COORDINATE_OPERATION);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param source
	 *            source crs
	 * @param target
	 *            target crs
	 * @param method
	 *            operation method
	 */
	public CoordinateOperation(String name, CoordinateReferenceSystem source,
			CoordinateReferenceSystem target, OperationMethod method) {
		super(name, CRSType.COORDINATE_OPERATION, source, method);
		setTarget(target);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationType getOperationType() {
		return OperationType.COORDINATE_OPERATION;
	}

	/**
	 * Get the target coordinate reference system
	 * 
	 * @return target crs
	 */
	public CoordinateReferenceSystem getTarget() {
		return target;
	}

	/**
	 * Set the target coordinate reference system
	 * 
	 * @param target
	 *            target crs
	 */
	public void setTarget(CoordinateReferenceSystem target) {
		this.target = target;
	}

	/**
	 * Get the interpolation coordinate reference system
	 * 
	 * @return interpolation crs
	 */
	public CoordinateReferenceSystem getInterpolation() {
		return interpolation;
	}

	/**
	 * Has an interpolation coordinate reference system
	 * 
	 * @return true if has interpolation crs
	 */
	public boolean hasInterpolation() {
		return getInterpolation() != null;
	}

	/**
	 * Set the interpolation coordinate reference system
	 * 
	 * @param interpolation
	 *            interpolation crs
	 */
	public void setInterpolation(CoordinateReferenceSystem interpolation) {
		this.interpolation = interpolation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((interpolation == null) ? 0 : interpolation.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
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
		CoordinateOperation other = (CoordinateOperation) obj;
		if (interpolation == null) {
			if (other.interpolation != null)
				return false;
		} else if (!interpolation.equals(other.interpolation))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

}
