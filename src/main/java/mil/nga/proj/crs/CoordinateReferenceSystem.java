package mil.nga.proj.crs;

/**
 * Coordinate Reference System
 * 
 * @author osbornb
 */
public abstract class CoordinateReferenceSystem extends CommonCRS {

	/**
	 * Constructor
	 */
	public CoordinateReferenceSystem() {

	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            coordinate reference system type
	 */
	public CoordinateReferenceSystem(CRSType type) {
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
	public CoordinateReferenceSystem(String name, CRSType type) {
		super(name, type);
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
