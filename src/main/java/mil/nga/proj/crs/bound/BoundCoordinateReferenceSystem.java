package mil.nga.proj.crs.bound;

import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * Bound Coordinate Reference System
 * 
 * @author osbornb
 */
public class BoundCoordinateReferenceSystem extends CoordinateReferenceSystem {

	/**
	 * Source Coordinate Reference System
	 */
	private CoordinateReferenceSystem source = null;

	/**
	 * Target Coordinate Reference System
	 */
	private CoordinateReferenceSystem target = null;

	/**
	 * Abridged Coordinate Transformation
	 */
	private AbridgedCoordinateTransformation transformation = null;

	/**
	 * Constructor
	 */
	public BoundCoordinateReferenceSystem() {
		super(CRSType.BOUND);
	}

	/**
	 * Constructor
	 * 
	 * @param source
	 *            source coordinate reference system
	 * @param target
	 *            target coordinate reference system
	 * @param transformation
	 *            abridged coordinate transformation
	 */
	public BoundCoordinateReferenceSystem(CoordinateReferenceSystem source,
			CoordinateReferenceSystem target,
			AbridgedCoordinateTransformation transformation) {
		super(CRSType.BOUND);
		setSource(source);
		setTarget(target);
		setTransformation(transformation);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		throw new UnsupportedOperationException(
				"Bound CRS does not support name");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		throw new UnsupportedOperationException(
				"Bound CRS does not support name");
	}

	/**
	 * Get the source coordinate reference system
	 * 
	 * @return source coordinate reference system
	 */
	public CoordinateReferenceSystem getSource() {
		return source;
	}

	/**
	 * Set the source coordinate reference system
	 * 
	 * @param source
	 *            source coordinate reference system
	 */
	public void setSource(CoordinateReferenceSystem source) {
		this.source = source;
	}

	/**
	 * Get the target coordinate reference system
	 * 
	 * @return target coordinate reference system
	 */
	public CoordinateReferenceSystem getTarget() {
		return target;
	}

	/**
	 * Set the target coordinate reference system
	 * 
	 * @param target
	 *            target coordinate reference system
	 */
	public void setTarget(CoordinateReferenceSystem target) {
		this.target = target;
	}

	/**
	 * Get the abridged coordinate transformation
	 * 
	 * @return abridged coordinate transformation
	 */
	public AbridgedCoordinateTransformation getTransformation() {
		return transformation;
	}

	/**
	 * Set the abridged coordinate transformation
	 * 
	 * @param transformation
	 *            abridged coordinate transformation
	 */
	public void setTransformation(
			AbridgedCoordinateTransformation transformation) {
		this.transformation = transformation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result
				+ ((transformation == null) ? 0 : transformation.hashCode());
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
		BoundCoordinateReferenceSystem other = (BoundCoordinateReferenceSystem) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (transformation == null) {
			if (other.transformation != null)
				return false;
		} else if (!transformation.equals(other.transformation))
			return false;
		return true;
	}

}
