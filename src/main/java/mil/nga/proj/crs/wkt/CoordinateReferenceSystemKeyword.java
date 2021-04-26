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
	 * Datum anchor
	 */
	ANCHOR(),

	/**
	 * Angle Unit
	 */
	ANGLEUNIT("UNIT"),

	/**
	 * Area description
	 */
	AREA(),

	/**
	 * Axis
	 */
	AXIS(),

	/**
	 * Base Geodetic CRS
	 */
	BASEGEODCRS(),

	/**
	 * Base Geographic CRS
	 */
	BASEGEOGCRS(),

	/**
	 * Geographic bounding box
	 */
	BBOX(),

	/**
	 * Bearing
	 */
	BEARING(),

	/**
	 * Calendar
	 */
	CALENDAR(),

	/**
	 * Citation
	 */
	CITATION(),

	/**
	 * Map Projection Conversion
	 */
	CONVERSION(),

	/**
	 * Coordinate system
	 */
	CS(),

	/**
	 * Geodetic reference frame
	 */
	DATUM("GEODETICDATUM", "TRF"),

	/**
	 * Dynamic coordinate reference systems
	 */
	DYNAMIC(),

	/**
	 * Engineering datum
	 */
	EDATUM("ENGINEERINGDATUM", "LOCAL_DATUM"),

	/**
	 * Ellipsoid
	 */
	ELLIPSOID("SPHEROID"),

	/**
	 * Engineering CRS
	 */
	ENGCRS("ENGINEERINGCRS"),

	/**
	 * Datum ensemble
	 */
	ENSEMBLE(),

	/**
	 * Datum ensemble accuracy
	 */
	ENSEMBLEACCURACY(),

	/**
	 * Frame reference epoch
	 */
	FRAMEEPOCH(),

	/**
	 * Backwards Compatibility Geodetic CRS
	 */
	GEOCCS,

	/**
	 * Geodetic CRS
	 */
	GEODCRS("GEODETICCRS"),

	/**
	 * Backwards Compatibility Geographic CRS
	 */
	GEOGCS(),

	/**
	 * Geographic CRS
	 */
	GEOGCRS("GEOGRAPHICCRS"),

	/**
	 * Geoid Model ID
	 */
	GEOIDMODEL,

	/**
	 * Identifier
	 */
	ID("AUTHORITY"),

	/**
	 * Length Unit
	 */
	LENGTHUNIT("UNIT"),

	/**
	 * Member
	 */
	MEMBER(),

	/**
	 * Meridian
	 */
	MERIDIAN(),

	/**
	 * Map Projection Method
	 */
	METHOD("PROJECTION"),

	/**
	 * Model
	 */
	MODEL("VELOCITYGRID"),

	/**
	 * Axis order
	 */
	ORDER(),

	/**
	 * Parameter
	 */
	PARAMETER(),

	/**
	 * Parametric CRS
	 */
	PARAMETRICCRS(),

	/**
	 * Parametric Unit
	 */
	PARAMETRICUNIT(),

	/**
	 * Parametric datum
	 */
	PDATUM("PARAMETRICDATUM"),

	/**
	 * Prime meridian
	 */
	PRIMEM("PRIMEMERIDIAN"),

	/**
	 * Projected CRS
	 */
	PROJCRS("PROJECTEDCRS"),

	/**
	 * Backwards Compatibility Projected CRS
	 */
	PROJCS(),

	/**
	 * Remark
	 */
	REMARK(),

	/**
	 * Scale Unit
	 */
	SCALEUNIT("UNIT"),

	/**
	 * Scope
	 */
	SCOPE(),

	/**
	 * Temporal Datum
	 */
	TDATUM("TIMEDATUM"),

	/**
	 * Temporal CRS
	 */
	TIMECRS(),

	/**
	 * Temporal extent
	 */
	TIMEEXTENT(),

	/**
	 * Temporal Origin
	 */
	TIMEORIGIN(),

	/**
	 * Time Unit
	 */
	TIMEUNIT("TEMPORALQUANTITY"),

	/**
	 * Usage
	 */
	USAGE(),

	/**
	 * URI
	 */
	URI(),

	/**
	 * Vertical Reference Frame
	 */
	VDATUM("VRF", "VERTICALDATUM", "VERT_DATUM"),

	/**
	 * Vertical CRS
	 */
	VERTCRS("VERTICALCRS"),

	/**
	 * Vertical extent
	 */
	VERTICALEXTENT();

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
					"No Coordinate Reference System Keyword for value: "
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
					"No Coordinate Reference System Keywords for value: "
							+ keyword);
		}
		return types;
	}

}
