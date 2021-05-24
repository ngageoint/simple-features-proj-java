package mil.nga.crs.wkt;

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
public enum CRSKeyword {

	/**
	 * Abridged Transformation
	 */
	ABRIDGEDTRANSFORMATION(),

	/**
	 * Datum Anchor
	 */
	ANCHOR(),

	/**
	 * Angle Unit
	 */
	ANGLEUNIT("UNIT"),

	/**
	 * Area Description
	 */
	AREA(),

	/**
	 * Axis
	 */
	AXIS(),

	/**
	 * Base Engineering CRS
	 */
	BASEENGCRS(),

	/**
	 * Base Geodetic CRS
	 */
	BASEGEODCRS(),

	/**
	 * Base Geographic CRS
	 */
	BASEGEOGCRS(),

	/**
	 * Base Parametric CRS
	 */
	BASEPARAMCRS(),

	/**
	 * Base Projected CRS
	 */
	BASEPROJCRS(),

	/**
	 * Base Temporal CRS
	 */
	BASETIMECRS(),

	/**
	 * Base Derived CRS
	 */
	BASEVERTCRS(),

	/**
	 * Geographic Bounding Box
	 */
	BBOX(),

	/**
	 * Bearing
	 */
	BEARING(),

	/**
	 * Bound CRS
	 */
	BOUNDCRS(),

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
	 * Compound CRS
	 */
	COMPOUNDCRS("COMPD_CS"),

	/**
	 * Concatenated Operation
	 */
	CONCATENATEDOPERATION(),

	/**
	 * Coordinate Metadata
	 */
	COORDINATEMETADATA(),

	/**
	 * Coordinate Operation
	 */
	COORDINATEOPERATION(),

	/**
	 * Coordinate System
	 */
	CS(),

	/**
	 * Geodetic Reference Frame
	 */
	DATUM("GEODETICDATUM", "TRF"),

	/**
	 * Derived Projected CRS
	 */
	DERIVEDPROJCRS("DERIVEDPROJECTEDCRS"),

	/**
	 * Deriving Conversion
	 */
	DERIVINGCONVERSION(),

	/**
	 * Dynamic Coordinate Reference Systems
	 */
	DYNAMIC(),

	/**
	 * Engineering Datum
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
	 * Datum Ensemble
	 */
	ENSEMBLE(),

	/**
	 * Datum Ensemble Accuracy
	 */
	ENSEMBLEACCURACY(),

	/**
	 * Coordinate Epoch
	 */
	EPOCH("COORDEPOCH"),

	/**
	 * Backwards Compatibility Extension
	 */
	EXTENSION(),

	/**
	 * Frame Reference Epoch
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
	 * Interpolation CRS
	 */
	INTERPOLATIONCRS(),

	/**
	 * Length Unit
	 */
	LENGTHUNIT("UNIT"),

	/**
	 * Backwards Compatibility Engineering CRS
	 */
	LOCAL_CS(),

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
	 * Coordinate Operation Accuracy
	 */
	OPERATIONACCURACY(),

	/**
	 * Axis Order
	 */
	ORDER(),

	/**
	 * Parameter
	 */
	PARAMETER(),

	/**
	 * Parameter File
	 */
	PARAMETERFILE(),

	/**
	 * Parametric CRS
	 */
	PARAMETRICCRS(),

	/**
	 * Parametric Unit
	 */
	PARAMETRICUNIT(),

	/**
	 * Parametric Datum
	 */
	PDATUM("PARAMETRICDATUM"),

	/**
	 * Point Motion Operation
	 */
	POINTMOTIONOPERATION(),

	/**
	 * Prime Meridian
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
	 * Source CRS
	 */
	SOURCECRS(),

	/**
	 * Concatenated Operation Step
	 */
	STEP(),

	/**
	 * Target CRS
	 */
	TARGETCRS(),

	/**
	 * Temporal Datum
	 */
	TDATUM("TIMEDATUM"),

	/**
	 * Temporal CRS
	 */
	TIMECRS(),

	/**
	 * Temporal Extent
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
	 * Backwards Compatibility To WGS84
	 */
	TOWGS84("ABRIDGEDTRANSFORMATION"),

	/**
	 * Triaxial ellipsoid
	 */
	TRIAXIAL(),

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
	 * Operation Version
	 */
	VERSION(),

	/**
	 * Backwards Compatibility Vertical CRS
	 */
	VERT_CS(),

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
	private static final Map<String, Set<CRSKeyword>> keywordTypes = new HashMap<>();
	static {
		for (CRSKeyword type : values()) {
			for (String keyword : type.keywords) {
				String keywordUpperCase = keyword.toUpperCase();
				Set<CRSKeyword> keywordSet = keywordTypes.get(keywordUpperCase);
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
	private CRSKeyword(String... additionalKeywords) {
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
	public static CRSKeyword getType(String keyword) {
		CRSKeyword type = null;
		Set<CRSKeyword> types = getTypes(keyword);
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
	public static CRSKeyword getRequiredType(String keyword) {
		CRSKeyword type = getType(keyword);
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
	public static Set<CRSKeyword> getTypes(String keyword) {
		Set<CRSKeyword> types = null;
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
	public static Set<CRSKeyword> getRequiredTypes(String keyword) {
		Set<CRSKeyword> types = getTypes(keyword);
		if (types == null || types.isEmpty()) {
			throw new ProjectionException(
					"No Coordinate Reference System Keywords for value: "
							+ keyword);
		}
		return types;
	}

}
