package mil.nga.proj.crs.wkt;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
	 * Length Unit
	 */
	LENGTHUNIT("UNIT"),

	/**
	 * Identifier
	 */
	ID();

	/**
	 * Keyword to type mapping
	 */
	private static final Map<String, CoordinateReferenceSystemKeyword> keywordTypes = new HashMap<>();
	static {
		for (CoordinateReferenceSystemKeyword type : values()) {
			for (String keyword : type.keywords) {
				keywordTypes.put(keyword.toUpperCase(), type);
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
		return keywordTypes.get(keyword.toUpperCase());
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

}
