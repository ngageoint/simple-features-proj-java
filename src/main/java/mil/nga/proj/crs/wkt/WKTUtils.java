package mil.nga.proj.crs.wkt;

import java.io.IOException;

import mil.nga.proj.ProjectionException;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.CoordinateSystemType;
import mil.nga.proj.crs.common.UnitType;

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
		case BASEGEODCRS:
		case GEOCCS:
			crsType = CoordinateReferenceSystemType.GEODETIC;
			break;

		case GEOGCRS:
		case BASEGEOGCRS:
		case GEOGCS:
			crsType = CoordinateReferenceSystemType.GEOGRAPHIC;
			break;

		default:
			throw new ProjectionException(
					"No coordinate reference system type found. keyword: "
							+ keyword);
		}

		return crsType;
	}

	/**
	 * Convert the WKT to a pretty WKT string, 4 space indents
	 * 
	 * @param wkt
	 *            well-known text
	 * @return pretty wkt
	 * @throws IOException
	 *             upon failure to read and create
	 */
	public static String pretty(String wkt) throws IOException {
		return pretty(wkt, "    ");
	}

	/**
	 * Convert the WKT to a pretty WKT string, tab indents
	 * 
	 * @param wkt
	 *            well-known text
	 * @return pretty wkt
	 * @throws IOException
	 *             upon failure to read and create
	 */
	public static String prettyTabIndent(String wkt) throws IOException {
		return pretty(wkt, "\t");
	}

	/**
	 * Convert the WKT to a pretty WKT string, no indents
	 * 
	 * @param wkt
	 *            well-known text
	 * @return pretty wkt
	 * @throws IOException
	 *             upon failure to read and create
	 */
	public static String prettyNoIndent(String wkt) throws IOException {
		return pretty(wkt, "");
	}

	/**
	 * Convert the WKT to a pretty WKT string
	 * 
	 * @param wkt
	 *            well-known text
	 * @param indent
	 *            indent string
	 * @return pretty wkt
	 * @throws IOException
	 *             upon failure to read and create
	 */
	public static String pretty(String wkt, String indent) throws IOException {
		return pretty(wkt, "\n", indent);
	}

	/**
	 * Convert the WKT to a pretty WKT string
	 * 
	 * @param wkt
	 *            well-known text
	 * @param newline
	 *            newline string
	 * @param indent
	 *            indent string
	 * @return pretty wkt
	 * @throws IOException
	 *             upon failure to read and create
	 */
	public static String pretty(String wkt, String newline, String indent)
			throws IOException {

		StringBuilder pretty = new StringBuilder();

		int depth = 0;

		TextReader reader = new TextReader(wkt, true);

		String previousToken = reader.readToken();
		String token = reader.readToken();
		while (previousToken != null) {

			if (token != null && (token.equals("[") || token.equals("("))) {
				depth++;
				if (pretty.length() > 0) {
					pretty.append(newline);
				}
				for (int i = 1; i < depth; i++) {
					pretty.append(indent);
				}
			}

			pretty.append(previousToken);

			if (previousToken.equals("]") || previousToken.equals(")")) {
				depth--;
			}

			previousToken = token;
			token = reader.readToken();
		}

		reader.close();

		return pretty.toString();
	}

}
