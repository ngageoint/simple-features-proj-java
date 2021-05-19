package mil.nga.proj.crs.operation;

import java.util.ArrayList;
import java.util.List;

import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * Concatenated Operation
 * 
 * @author osbornb
 */
public class ConcatenatedOperation extends Operation {

	/**
	 * Target Coordinate Reference System
	 */
	private CoordinateReferenceSystem target = null;

	/**
	 * Operations, transformations, and conversions
	 */
	private List<ConcatenableOperation> operations = new ArrayList<>();

	/**
	 * Constructor
	 */
	public ConcatenatedOperation() {
		super(CRSType.CONCATENATED_OPERATION);
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
	 */
	public ConcatenatedOperation(String name, CoordinateReferenceSystem source,
			CoordinateReferenceSystem target) {
		super(name, CRSType.CONCATENATED_OPERATION, source);
		setTarget(target);
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
	 * Get the operations
	 * 
	 * @return operations
	 */
	public List<ConcatenableOperation> getOperations() {
		return operations;
	}

	/**
	 * Set the operations
	 * 
	 * @param operations
	 *            operations
	 */
	public void setOperations(List<ConcatenableOperation> operations) {
		this.operations = operations;
	}

	/**
	 * Add the operation
	 * 
	 * @param operation
	 *            operation
	 */
	public void addOperation(ConcatenableOperation operation) {
		this.operations.add(operation);
	}

	/**
	 * Add the operations
	 * 
	 * @param operations
	 *            operations
	 */
	public void addOperations(List<ConcatenableOperation> operations) {
		this.operations.addAll(operations);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((operations == null) ? 0 : operations.hashCode());
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
		ConcatenatedOperation other = (ConcatenatedOperation) obj;
		if (operations == null) {
			if (other.operations != null)
				return false;
		} else if (!operations.equals(other.operations))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

}
