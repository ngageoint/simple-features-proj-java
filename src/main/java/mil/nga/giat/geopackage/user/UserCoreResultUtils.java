package mil.nga.giat.geopackage.user;

import mil.nga.giat.geopackage.GeoPackageException;
import mil.nga.giat.geopackage.db.GeoPackageDataType;

/**
 * User Core Result utilities
 * 
 * @author osbornb
 */
public class UserCoreResultUtils {

	/**
	 * Integer field type
	 */
	private static final int FIELD_TYPE_INTEGER = 1;

	/**
	 * Float field type
	 */
	private static final int FIELD_TYPE_FLOAT = 2;

	/**
	 * String field type
	 */
	private static final int FIELD_TYPE_STRING = 3;

	/**
	 * Blob field type
	 */
	private static final int FIELD_TYPE_BLOB = 4;

	/**
	 * Null field type
	 */
	private static final int FIELD_TYPE_NULL = 0;

	/**
	 * Get the value from the cursor from the provided column
	 *
	 * @param result
	 * @param index
	 * @param dataType
	 * @return
	 */
	public static Object getValue(UserCoreResult<?, ?, ?> result, int index,
			GeoPackageDataType dataType) {

		Object value = null;

		int type = result.getType(index);

		switch (type) {

		case FIELD_TYPE_INTEGER:
			value = getIntegerValue(result, index, dataType);
			break;

		case FIELD_TYPE_FLOAT:
			value = getFloatValue(result, index, dataType);
			break;

		case FIELD_TYPE_STRING:
			value = result.getString(index);
			break;

		case FIELD_TYPE_BLOB:
			value = result.getBlob(index);
			break;

		case FIELD_TYPE_NULL:
			// leave value as null
		}

		return value;
	}

	/**
	 * Get the integer value from the cursor of the column
	 *
	 * @param index
	 * @param dataType
	 * @return
	 */
	public static Object getIntegerValue(UserCoreResult<?, ?, ?> result,
			int index, GeoPackageDataType dataType) {

		Object value = null;

		switch (dataType) {

		case BOOLEAN:
			short booleanValue = result.getShort(index);
			value = booleanValue == 0 ? Boolean.FALSE : Boolean.TRUE;
			break;
		case TINYINT:
			value = (byte) result.getShort(index);
			break;
		case SMALLINT:
			value = result.getShort(index);
			break;
		case MEDIUMINT:
			value = result.getInt(index);
			break;
		case INT:
		case INTEGER:
			value = result.getLong(index);
			break;
		default:
			throw new GeoPackageException("Data Type " + dataType
					+ " is not an integer type");
		}

		return value;
	}

	/**
	 * Get the float value from the cursor of the column
	 *
	 * @param index
	 * @param dataType
	 * @return
	 */
	public static Object getFloatValue(UserCoreResult<?, ?, ?> result,
			int index, GeoPackageDataType dataType) {

		Object value = null;

		switch (dataType) {

		case FLOAT:
			value = result.getFloat(index);
			break;
		case DOUBLE:
		case REAL:
		case INTEGER:
		case INT:
			value = result.getDouble(index);
			break;
		default:
			throw new GeoPackageException("Data Type " + dataType
					+ " is not a float type");
		}

		return value;
	}

}
