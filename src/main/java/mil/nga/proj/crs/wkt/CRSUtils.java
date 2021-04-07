package mil.nga.proj.crs.wkt;

import mil.nga.proj.ProjectionException;
import mil.nga.proj.crs.CoordinateSystemType;
import mil.nga.proj.crs.UnitType;

public class CRSUtils {

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

}
