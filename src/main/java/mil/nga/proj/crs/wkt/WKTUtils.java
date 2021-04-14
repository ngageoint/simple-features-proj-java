package mil.nga.proj.crs.wkt;

import mil.nga.proj.ProjectionException;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.CoordinateSystemType;
import mil.nga.proj.crs.UnitType;

/**
 * CRS Well-Known Text Utilities
 * 
 * @author osbornb
 */
public class WKTUtils {

	/**
	 * Is the Coordinate System Type a spatial type
	 * 
	 * @param type
	 *            coordinate system type
	 * @return true if a spatial type
	 */
	public static boolean isSpatial(CoordinateSystemType type) {
		boolean value = false;
		switch (type) {
		case AFFINE:
		case CARTESIAN:
		case CYLINDRICAL:
		case ELLIPSOIDAL:
		case LINEAR:
		case PARAMETRIC:
		case POLAR:
		case SPHERICAL:
		case VERTICAL:
			value = true;
			break;
		default:
		}
		return value;
	}

	/**
	 * Is the Coordinate System Type a temporal count or measure type
	 * 
	 * @param type
	 *            coordinate system type
	 * @return true if a spatial type
	 */
	public static boolean isTemporalCountMeasure(CoordinateSystemType type) {
		boolean value = false;
		switch (type) {
		case TEMPORAL_COUNT:
		case TEMPORAL_MEASURE:
			value = true;
			break;
		default:
		}
		return value;
	}

	/**
	 * Is the Coordinate System Type an ordinal or temporal date time type
	 * 
	 * @param type
	 *            coordinate system type
	 * @return true if a spatial type
	 */
	public static boolean isOrdinalDateTime(CoordinateSystemType type) {
		boolean value = false;
		switch (type) {
		case ORDINAL:
		case TEMPORAL_DATE_TIME:
			value = true;
			break;
		default:
		}
		return value;
	}

	/**
	 * Get the unit type from the keyword
	 * 
	 * @param keyword
	 *            coordinate reference system keyword
	 * @return unit type
	 */
	public static UnitType getUnitType(
			CoordinateReferenceSystemKeyword keyword) {

		UnitType unitType = null;

		try {
			unitType = UnitType.valueOf(keyword.name());
		} catch (Exception e) {
			throw new ProjectionException(
					"No unit type found. keyword: " + keyword, e);
		}

		return unitType;
	}

	/**
	 * Get the Coordinate Reference System Type
	 * 
	 * @param keyword
	 *            coordinate reference system keyword
	 * @return coordinate reference system type
	 */
	public static CoordinateReferenceSystemType getCoordinateReferenceSystemType(
			CoordinateReferenceSystemKeyword keyword) {

		CoordinateReferenceSystemType crsType = null;

		switch (keyword) {

		case GEODCRS:
			crsType = CoordinateReferenceSystemType.GEODETIC;
			break;

		case GEOGCRS:
			crsType = CoordinateReferenceSystemType.GEOGRAPHIC;
			break;

		default:
			throw new ProjectionException(
					"No coordinate reference system type found. keyword: "
							+ keyword);
		}

		return crsType;
	}

}
