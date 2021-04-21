package mil.nga.proj.crs.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Coordinate System Type
 * 
 * @author osbornb
 */
public enum CoordinateSystemType {

	/**
	 * affine
	 */
	AFFINE("affine"),

	/**
	 * Cartesian
	 */
	CARTESIAN("Cartesian"),

	/**
	 * cylindrical
	 */
	CYLINDRICAL("cylindrical"),

	/**
	 * ellipsoidal
	 */
	ELLIPSOIDAL("ellipsoidal"),

	/**
	 * linear
	 */
	LINEAR("linear"),

	/**
	 * ordinal
	 */
	ORDINAL("ordinal"),

	/**
	 * parametric
	 */
	PARAMETRIC("parametric"),

	/**
	 * polar
	 */
	POLAR("polar"),

	/**
	 * spherical
	 */
	SPHERICAL("spherical"),

	/**
	 * temporalCount
	 */
	TEMPORAL_COUNT("temporalCount"),

	/**
	 * temporalDateTime
	 */
	TEMPORAL_DATE_TIME("temporalDateTime"),

	/**
	 * temporalMeasure
	 */
	TEMPORAL_MEASURE("temporalMeasure"),

	/**
	 * vertical
	 */
	VERTICAL("vertical");

	/**
	 * Name to type mapping
	 */
	private static final Map<String, CoordinateSystemType> nameTypes = new HashMap<>();
	static {
		for (CoordinateSystemType type : values()) {
			nameTypes.put(type.getName().toUpperCase(), type);
		}
	}

	/**
	 * Type name
	 */
	private final String name;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            type name
	 */
	private CoordinateSystemType(String name) {
		this.name = name;
	}

	/**
	 * Get the type name
	 * 
	 * @return type name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the type from the name
	 * 
	 * @param name
	 *            type name
	 * @return type
	 */
	public static CoordinateSystemType getType(String name) {
		CoordinateSystemType type = null;
		if (name != null) {
			type = nameTypes.get(name.toUpperCase());
		}
		return type;
	}

}
