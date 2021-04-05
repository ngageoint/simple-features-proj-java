package mil.nga.proj.crs.wkt;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import mil.nga.proj.ProjectionException;

/**
 * Coordinate Reference System Keyword
 * 
 * @author osbornb
 */
public enum CoordinateReferenceSystemKeyword {

	/**
	 * Geodetic CRS
	 */
	GEODCRS("GEODETICCRS", "GEOCCS"),

	/**
	 * Geographic CRS
	 */
	GEOGCRS("GEOGRAPHICCRS", "GEOGCS"),

	/**
	 * Geodetic reference frame
	 */
	DATUM("GEODETICDATUM", "TRF"),

	/**
	 * Datum ensemble
	 */
	ENSEMBLE(),

	/**
	 * Dynamic coordinate reference systems
	 */
	DYNAMIC(),

	/**
	 * Ellipsoid
	 */
	ELLIPSOID("SPHEROID"),

	/**
	 * Angle Unit
	 */
	ANGLEUNIT("UNIT"),

	/**
	 * Length Unit
	 */
	LENGTHUNIT("UNIT"),

	/**
	 * Parametric Unit
	 */
	PARAMETRICUNIT(),

	/**
	 * Scale Unit
	 */
	SCALEUNIT("UNIT"),

	/**
	 * Time Unit
	 */
	TIMEUNIT("TEMPORALQUANTITY"),

	/**
	 * Identifier
	 */
	ID("AUTHORITY"),

	/**
	 * Citation
	 */
	CITATION(),

	/**
	 * URI
	 */
	URI(),

	/**
	 * 
	 */
	ANCHOR(),

	/**
	 * 
	 */
	PRIMEM("PRIMEMERIDIAN");

	/**
	 * Keyword to type mapping
	 */
	private static final Map<String, Set<CoordinateReferenceSystemKeyword>> keywordTypes = new HashMap<>();
	static {
		for (CoordinateReferenceSystemKeyword type : values()) {
			for (String keyword : type.keywords) {
				String keywordUpperCase = keyword.toUpperCase();
				Set<CoordinateReferenceSystemKeyword> keywordSet = keywordTypes
						.get(keywordUpperCase);
				if (keywordSet == null) {
					keywordSet = new HashSet<>();
					keywordTypes.put(keywordUpperCase, keywordSet);
				}
				keywordSet.add(type);
			}
		}
	}

	/**
	 * Type keywords
	 */
	private final Set<String> keywords = new LinkedHashSet<>();

	/**
	 * Constructor
	 * 
	 * @param additionalKeywords
	 *            additional keywords other than the name
	 */
	private CoordinateReferenceSystemKeyword(String... additionalKeywords) {
		keywords.add(name());
		keywords.addAll(Arrays.asList(additionalKeywords));
	}

	/**
	 * Get the keywords
	 * 
	 * @return keywords
	 */
	public Set<String> getKeywords() {
		return Collections.unmodifiableSet(keywords);
	}

	/**
	 * Get the keyword type from the keyword
	 * 
	 * @param keyword
	 *            CRS keyword
	 * @return type
	 */
	public static CoordinateReferenceSystemKeyword getType(String keyword) {
		CoordinateReferenceSystemKeyword type = null;
		Set<CoordinateReferenceSystemKeyword> types = getTypes(keyword);
		if (types != null && !types.isEmpty()) {
			type = types.iterator().next();
		}
		return type;
	}

	/**
	 * Get the keyword type from the keyword
	 * 
	 * @param keyword
	 *            CRS keyword
	 * @return type
	 */
	public static CoordinateReferenceSystemKeyword getRequiredType(
			String keyword) {
		CoordinateReferenceSystemKeyword type = getType(keyword);
		if (type == null) {
			throw new ProjectionException(
					"No Coordinate Reference System Type for keyword: "
							+ keyword);
		}
		return type;
	}

	/**
	 * Get the keyword types from the keyword
	 * 
	 * @param keyword
	 *            CRS keyword
	 * @return types
	 */
	public static Set<CoordinateReferenceSystemKeyword> getTypes(
			String keyword) {
		Set<CoordinateReferenceSystemKeyword> types = null;
		if (keyword != null) {
			types = keywordTypes.get(keyword.toUpperCase());
		}
		return types;
	}

	/**
	 * Get the keyword types from the keyword
	 * 
	 * @param keyword
	 *            CRS keyword
	 * @return types
	 */
	public static Set<CoordinateReferenceSystemKeyword> getRequiredTypes(
			String keyword) {
		Set<CoordinateReferenceSystemKeyword> types = getTypes(keyword);
		if (types == null || types.isEmpty()) {
			throw new ProjectionException(
					"No Coordinate Reference System Types for keyword: "
							+ keyword);
		}
		return types;
	}

}
