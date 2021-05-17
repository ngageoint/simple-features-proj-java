package mil.nga.proj.crs;

import mil.nga.proj.crs.common.CoordinateSystem;

/**
 * Simple Coordinate Reference System with Coordinate System
 * 
 * @author osbornb
 */
public abstract class SimpleCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

	/**
	 * Coordinate System
	 */
	private CoordinateSystem coordinateSystem = null;

	/**
	 * Constructor
	 */
	public SimpleCoordinateReferenceSystem() {

	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            coordinate reference system type
	 */
	public SimpleCoordinateReferenceSystem(CRSType type) {
		super(type);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 */
	public SimpleCoordinateReferenceSystem(String name, CRSType type) {
		super(name, type);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public SimpleCoordinateReferenceSystem(String name, CRSType type,
			CoordinateSystem coordinateSystem) {
		super(name, type);
		setCoordinateSystem(coordinateSystem);
	}

	/**
	 * Get the coordinate system
	 * 
	 * @return coordinate system
	 */
	public CoordinateSystem getCoordinateSystem() {
		return coordinateSystem;
	}

	/**
	 * Set the coordinate system
	 * 
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public void setCoordinateSystem(CoordinateSystem coordinateSystem) {
		this.coordinateSystem = coordinateSystem;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((coordinateSystem == null) ? 0
				: coordinateSystem.hashCode());
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
		SimpleCoordinateReferenceSystem other = (SimpleCoordinateReferenceSystem) obj;
		if (coordinateSystem == null) {
			if (other.coordinateSystem != null)
				return false;
		} else if (!coordinateSystem.equals(other.coordinateSystem))
			return false;
		return true;
	}

}
