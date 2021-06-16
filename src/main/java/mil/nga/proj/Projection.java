package mil.nga.proj;

import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.units.Unit;
import org.locationtech.proj4j.units.Units;

import mil.nga.crs.CRS;

/**
 * Single Projection for an authority and code
 * 
 * @author osbornb
 */
public class Projection {

	/**
	 * Projection authority
	 */
	private final String authority;

	/**
	 * Coordinate code
	 */
	private final String code;

	/**
	 * Projection Coordinate Reference System
	 */
	private final CoordinateReferenceSystem crs;

	/**
	 * Well-Known Text Coordinate Definition
	 */
	private final String definition;

	/**
	 * Definition parsed Coordinate Reference System
	 */
	private final CRS definitionCRS;

	/**
	 * Constructor
	 *
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @param crs
	 *            projection coordinate reference system
	 */
	public Projection(String authority, long code,
			CoordinateReferenceSystem crs) {
		this(authority, code, crs, null);
	}

	/**
	 * Constructor
	 *
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @param crs
	 *            projection coordinate reference system
	 */
	public Projection(String authority, String code,
			CoordinateReferenceSystem crs) {
		this(authority, code, crs, null);
	}

	/**
	 * Constructor
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @param crs
	 *            projection coordinate reference system
	 * @param definition
	 *            well-known text coordinate definition
	 */
	public Projection(String authority, long code,
			CoordinateReferenceSystem crs, String definition) {
		this(authority, code, crs, definition, null);
	}

	/**
	 * Constructor
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @param crs
	 *            projection coordinate reference system
	 * @param definition
	 *            well-known text coordinate definition
	 */
	public Projection(String authority, String code,
			CoordinateReferenceSystem crs, String definition) {
		this(authority, code, crs, definition, null);
	}

	/**
	 * Constructor
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @param crs
	 *            projection coordinate reference system
	 * @param definition
	 *            well-known text coordinate definition
	 * @param definitionCRS
	 *            definition parsed coordinate reference system
	 */
	public Projection(String authority, long code,
			CoordinateReferenceSystem crs, String definition,
			CRS definitionCRS) {
		this(authority, String.valueOf(code), crs, definition, definitionCRS);
	}

	/**
	 * Constructor
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @param crs
	 *            projection coordinate reference system
	 * @param definition
	 *            well-known text coordinate definition
	 * @param definitionCRS
	 *            definition parsed coordinate reference system
	 */
	public Projection(String authority, String code,
			CoordinateReferenceSystem crs, String definition,
			CRS definitionCRS) {
		if (authority == null || code == null || crs == null) {
			throw new IllegalArgumentException(
					"All projection arguments are required. authority: "
							+ authority + ", code: " + code + ", crs: " + crs);
		}
		this.authority = authority;
		this.code = code;
		this.crs = crs;
		this.definition = definition;
		this.definitionCRS = definitionCRS;
	}

	/**
	 * Get the coordinate authority
	 * 
	 * @return authority
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * Get the coordinate code
	 * 
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Get the Coordinate Reference System
	 * 
	 * @return coordinate reference system
	 */
	public CoordinateReferenceSystem getCrs() {
		return crs;
	}

	/**
	 * Get the well-known text coordinate definition
	 * 
	 * @return definition
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * Get the definition parsed Coordinate Reference System
	 * 
	 * @return coordinate reference system
	 */
	public CRS getDefinitionCRS() {
		return definitionCRS;
	}

	/**
	 * Get the transformation from this Projection to the EPSG code. Each thread
	 * of execution should have it's own transformation.
	 * 
	 * @param epsg
	 *            epsg
	 * @return transform
	 */
	public ProjectionTransform getTransformation(long epsg) {
		return getTransformation(ProjectionConstants.AUTHORITY_EPSG, epsg);
	}

	/**
	 * Get the transformation from this Projection to the authority and
	 * coordinate code. Each thread of execution should have it's own
	 * transformation.
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @return transform
	 */
	public ProjectionTransform getTransformation(String authority, long code) {
		Projection projectionTo = ProjectionFactory.getProjection(authority,
				code);
		return getTransformation(projectionTo);
	}

	/**
	 * Get the transformation from this Projection to the authority and
	 * coordinate code. Each thread of execution should have it's own
	 * transformation.
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @return transform
	 * @since 4.0.0
	 */
	public ProjectionTransform getTransformation(String authority,
			String code) {
		Projection projectionTo = ProjectionFactory.getProjection(authority,
				code);
		return getTransformation(projectionTo);
	}

	/**
	 * Get the transformation from this Projection to the provided projection.
	 * Each thread of execution should have it's own transformation.
	 * 
	 * @param projection
	 *            projection
	 * @return transform
	 */
	public ProjectionTransform getTransformation(Projection projection) {
		return new ProjectionTransform(this, projection);
	}

	/**
	 * Convert the value to meters
	 * 
	 * @param value
	 *            value
	 * @return meters
	 */
	public double toMeters(double value) {
		return value / crs.getProjection().getFromMetres();
	}

	/**
	 * Get the units of this projection
	 * 
	 * @return the projection unit
	 */
	public Unit getUnit() {
		Unit unit = crs.getProjection().getUnits();
		if (unit == null) {
			unit = Units.METRES;
		}
		return unit;
	}

	/**
	 * Determine if the projection is in the provided unit
	 * 
	 * @param unit
	 *            unit
	 * @return true if in the provided unit
	 */
	public boolean isUnit(Unit unit) {
		return unit != null && getUnit().equals(unit);
	}

	/**
	 * Check if this projection is equal to the authority and code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @return true if equal
	 */
	public boolean equals(String authority, long code) {
		return equals(authority, String.valueOf(code));
	}

	/**
	 * Check if this projection is equal to the authority and code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @return true if equal
	 */
	public boolean equals(String authority, String code) {
		return this.authority.equals(authority) && this.code.equals(code);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authority.hashCode();
		result = prime * result + code.hashCode();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Based upon {@link #getAuthority()} and {@link #getCode()}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projection other = (Projection) obj;
		return equals(other.authority, other.code);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return authority + ":" + code;
	}

}
