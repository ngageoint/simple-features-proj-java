package mil.nga.proj.crs.derived;

import java.util.List;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.common.Identifier;

/**
 * Derived Coordinate Reference System
 * 
 * @author osbornb
 */
public class DerivedCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

	/**
	 * Base
	 */
	private CoordinateReferenceSystem base = null;

	/**
	 * Deriving Conversion
	 */
	private DerivingConversion conversion = null;

	/**
	 * Constructor
	 */
	public DerivedCoordinateReferenceSystem() {
		super(CoordinateReferenceSystemType.DERIVED);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param base
	 *            base CRS
	 * @param conversion
	 *            deriving conversion
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public DerivedCoordinateReferenceSystem(String name,
			CoordinateReferenceSystem base, DerivingConversion conversion,
			CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.DERIVED, coordinateSystem);
		setBase(base);
		setConversion(conversion);
	}

	/**
	 * Get the base coordinate reference system
	 * 
	 * @return base coordinate reference system
	 */
	public CoordinateReferenceSystem getBase() {
		return base;
	}

	/**
	 * Set the base coordinate reference system
	 * 
	 * @param base
	 *            base coordinate reference system
	 */
	public void setBase(CoordinateReferenceSystem base) {
		this.base = base;
	}

	/**
	 * Get the base name
	 * 
	 * @return base name
	 */
	public String getBaseName() {
		return getBase().getName();
	}

	/**
	 * Get the base type
	 * 
	 * @return base type
	 */
	public CoordinateReferenceSystemType getBaseType() {
		return getBase().getType();
	}

	/**
	 * Get the base identifiers
	 * 
	 * @return base identifiers
	 */
	public List<Identifier> getBaseIdentifiers() {
		return getBase().getIdentifiers();
	}

	/**
	 * Has base identifiers
	 * 
	 * @return true if has base identifiers
	 */
	public boolean hasBaseIdentifiers() {
		return getBase().hasIdentifiers();
	}

	/**
	 * Get the deriving conversion
	 * 
	 * @return deriving conversion
	 */
	public DerivingConversion getConversion() {
		return conversion;
	}

	/**
	 * Set the deriving conversion
	 * 
	 * @param conversion
	 *            deriving conversion
	 */
	public void setConversion(DerivingConversion conversion) {
		this.conversion = conversion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		result = prime * result
				+ ((conversion == null) ? 0 : conversion.hashCode());
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
		DerivedCoordinateReferenceSystem other = (DerivedCoordinateReferenceSystem) obj;
		if (base == null) {
			if (other.base != null)
				return false;
		} else if (!base.equals(other.base))
			return false;
		if (conversion == null) {
			if (other.conversion != null)
				return false;
		} else if (!conversion.equals(other.conversion))
			return false;
		return true;
	}

}
