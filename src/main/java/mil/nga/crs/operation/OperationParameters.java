package mil.nga.crs.operation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import mil.nga.crs.CRSException;
import mil.nga.crs.common.UnitType;

/**
 * Operation Parameters
 * 
 * @author osbornb
 */
public enum OperationParameters {

	/**
	 * angle at the natural origin of an oblique projection through which the
	 * natural coordinate reference system is rotated to make the projection
	 * north axis parallel with true north
	 */
	ANGLE_FROM_RECTIFIED_TO_SKEW_GRID(8814, "angle from rectified to skew grid",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT),

	/**
	 * direction (north zero, east of north being positive) of the great circle
	 * which is the centre line of an oblique projection
	 * 
	 * The azimuth is given at the projection centre.
	 */
	AZIMUTH_OF_INITIAL_LINE(8813, "azimuth of initial line",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT),

	/**
	 * The rotation applied to spherical coordinates for the oblique projection,
	 * measured on the conformal sphere in the plane of the meridian of origin.
	 */
	CO_LATITUDE_OF_CONE_AXIS(1036, "co-latitude of cone axis",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT),

	/**
	 * easting value assigned to the false origin
	 */
	EASTING_AT_FALSE_ORIGIN(8826, "easting at false origin",
			OperationType.MAP_PROJECTION, UnitType.LENGTHUNIT, "false easting"),

	/**
	 * easting value assigned to the projection centre
	 */
	EASTING_AT_PROJECTION_CENTRE(8816, "easting at projection centre",
			OperationType.MAP_PROJECTION, UnitType.LENGTHUNIT, "false easting"),

	/**
	 * The name of the [path and] file containing ellipsoidal height
	 * differences.
	 */
	ELLIPSOIDAL_HEIGHT_DIFFERENCE_FILE(1058,
			"Ellipsoidal height difference file", OperationType.COORDINATE),

	/**
	 * value assigned to the abscissa (east or west) axis of the projection grid
	 * at the natural origin
	 */
	FALSE_EASTING(8806, "false easting", OperationType.MAP_PROJECTION,
			UnitType.LENGTHUNIT),

	/**
	 * value assigned to the ordinate (north or south) axis of the projection
	 * grid at the natural origin
	 */
	FALSE_NORTHING(8807, "false northing", OperationType.MAP_PROJECTION,
			UnitType.LENGTHUNIT),

	/**
	 * name of the [path and] file containing latitude and longitude differences
	 */
	LATITUDE_AND_LONGITUDE_DIFFERENCE_FILE(8656,
			"Latitude and longitude difference file", OperationType.COORDINATE),

	/**
	 * name of the [path and] file containing latitude differences
	 */
	LATITUDE_DIFFERENCE_FILE(8657, "Latitude difference file",
			OperationType.COORDINATE),

	/**
	 * geodetic latitude of one of the parallels of intersection of the cone
	 * with the ellipsoid. It is normally but not necessarily that nearest to
	 * the pole
	 * 
	 * Scale is true along this parallel.
	 */
	LATITUDE_OF_1ST_STANDARD_PARALLEL(8823, "latitude of 1st standard parallel",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT,
			"standard parallel 1"),

	/**
	 * 
	 * geodetic latitude of one of the parallels at which the cone intersects
	 * with the ellipsoid. It is normally but not necessarily that nearest to
	 * the equator
	 * 
	 * Scale is true along this parallel.
	 */
	LATITUDE_OF_2ND_STANDARD_PARALLEL(8824, "latitude of 2nd standard parallel",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT,
			"standard parallel 2"),

	/**
	 * geodetic latitude of the point which is not the natural origin and at
	 * which grid coordinate values false easting and false northing are defined
	 */
	LATITUDE_OF_FALSE_ORIGIN(8821, "latitude of false origin",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT,
			"latitude of origin"),

	/**
	 * geodetic latitude of the point from which the values of both the
	 * geographical coordinates on the ellipsoid and the grid coordinates on the
	 * projection are deemed to increment or decrement for computational
	 * purposes
	 * 
	 * Alternatively: geodetic latitude of the point which in the absence of
	 * application of false coordinates has grid coordinates of (0,0).
	 */
	LATITUDE_OF_NATURAL_ORIGIN(8801, "latitude of natural origin",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT,
			"latitude of origin", "latitude of center"),

	/**
	 * latitude of the point at which the azimuth of the central line for an
	 * oblique projection is defined
	 */
	LATITUDE_OF_PROJECTION_CENTRE(8811, "latitude of projection centre",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT),

	/**
	 * Latitude of the parallel on which the conic or cylindrical projection is
	 * based. This latitude is not geographic, but is defined on the conformal
	 * sphere AFTER its rotation to obtain the oblique aspect of the projection.
	 */
	LATITUDE_OF_PSEUDO_STANDARD_PARALLEL(8818,
			"latitude of pseudo standard parallel",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT),

	/**
	 * name of the [path and] file containing longitude differences
	 */
	LONGITUDE_DIFFERENCE_FILE(8658, "Longitude difference file",
			OperationType.COORDINATE),

	/**
	 * geodetic longitude of the point which is not the natural origin and at
	 * which grid coordinate values false easting and false northing are defined
	 */
	LONGITUDE_OF_FALSE_ORIGIN(8822, "longitude of false origin",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT,
			"longitude of origin", "longitude of center", "central meridian"),

	/**
	 * geodetic longitude of the point from which the values of both the
	 * geographical coordinates on the ellipsoid and the grid coordinates on the
	 * projection are deemed to increment or decrement for computational
	 * purposes
	 * 
	 * Alternatively: geodetic longitude of the point which in the absence of
	 * application of false coordinates has grid coordinates of (0,0).
	 */
	LONGITUDE_OF_NATURAL_ORIGIN(8802, "longitude of natural origin",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT,
			"longitude of origin", "longitude of center", "central meridian"),

	/**
	 * For polar aspect azimuthal projections, the meridian along which the
	 * northing axis increments and also across which parallels of latitude
	 * increment towards the north pole.
	 */
	LONGITUDE_OF_ORIGIN(8833, "longitude of origin",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT),

	/**
	 * longitude of the point at which the azimuth of the central line for an
	 * oblique projection is defined
	 */
	LONGITUDE_OF_PROJECTION_CENTRE(8812, "longitude of projection centre",
			OperationType.MAP_PROJECTION, UnitType.ANGLEUNIT),

	/**
	 * northing value assigned to the false origin
	 */
	NORTHING_AT_FALSE_ORIGIN(8827, "northing at false origin",
			OperationType.MAP_PROJECTION, UnitType.LENGTHUNIT,
			"false northing"),

	/**
	 * northing value assigned to the projection centre
	 */
	NORTHING_AT_PROJECTION_CENTRE(8817, "northing at projection centre",
			OperationType.MAP_PROJECTION, UnitType.LENGTHUNIT,
			"false northing"),

	/**
	 * value of the first ordinate of the evaluation point
	 */
	ORDINATE_1_OF_EVALUATION_POINT(8617, "Ordinate 1 of evaluation point",
			OperationType.COORDINATE),

	/**
	 * value of the second ordinate of the evaluation point
	 */
	ORDINATE_2_OF_EVALUATION_POINT(8618, "Ordinate 2 of evaluation point",
			OperationType.COORDINATE),

	/**
	 * value of the third ordinate of the evaluation point
	 */
	ORDINATE_3_OF_EVALUATION_POINT(8667, "Ordinate 3 of evaluation point",
			OperationType.COORDINATE),

	/**
	 * the ratio of a length between two points in target and source coordinate
	 * reference systems.
	 * 
	 * If a distance of 100 km in the source coordinate reference system
	 * translates into a distance of 100.001 km in the target coordinate
	 * reference system, the scale difference is 1 ppm (the ratio being
	 * 1.000001).
	 */
	SCALE_DIFFERENCE(8611, "Scale difference", OperationType.COORDINATE, "dS",
			"ppm"),

	/**
	 * 
	 * factor by which the map grid is reduced or enlarged during the projection
	 * process, defined by its value at the natural origin
	 */
	SCALE_FACTOR_AT_NATURAL_ORIGIN(8805, "scale factor at natural origin",
			OperationType.MAP_PROJECTION, UnitType.SCALEUNIT, "scale factor"),

	/**
	 * factor by which the map grid is reduced or enlarged during the projection
	 * process, defined by its value at the projection centre
	 */
	SCALE_FACTOR_ON_INITIAL_LINE(8815, "scale factor on initial line",
			OperationType.MAP_PROJECTION, UnitType.SCALEUNIT),

	/**
	 * The factor by which the map grid is reduced or enlarged during the
	 * projection process, defined by its value at the pseudo-standard parallel.
	 */
	SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL(8819,
			"scale factor on pseudo standard parallel",
			OperationType.MAP_PROJECTION, UnitType.SCALEUNIT),

	/**
	 * difference between the height or depth values of a point in the target
	 * and source coordinate reference systems
	 */
	VERTICAL_OFFSET(8603, "Vertical Offset", OperationType.COORDINATE, "dH"),

	/**
	 * angular difference between the Y and Z axes directions of target and
	 * source coordinate reference systems
	 * 
	 * This is a rotation about the X axis as viewed from the origin looking
	 * along that axis. The particular method defines which direction is
	 * positive, and what is being rotated (point or axis).
	 */
	X_AXIS_ROTATION(8608, "X-axis rotation", OperationType.COORDINATE, "rX",
			"eX"),

	/**
	 * difference between the X values of a point in the target and source
	 * coordinate reference systems
	 */
	X_AXIS_TRANSLATION(8605, "X-axis translation", OperationType.COORDINATE,
			"dX", "tX"),

	/**
	 * angular difference between the X and Z axes directions of target and
	 * source coordinate reference systems
	 * 
	 * This is a rotation about the Y axis as viewed from the origin looking
	 * along that axis. The particular method defines which direction is
	 * positive, and what is being rotated (point or axis).
	 */
	Y_AXIS_ROTATION(8609, "Y-axis rotation", OperationType.COORDINATE, "rY",
			"eY"),

	/**
	 * difference between the Y values of a point in the target and source
	 * coordinate reference systems
	 */
	Y_AXIS_TRANSLATION(8606, "Y-axis translation", OperationType.COORDINATE,
			"dY", "tY"),

	/**
	 * angular difference between the X and Y axes directions of target and
	 * source coordinate reference systems
	 * 
	 * This is a rotation about the Z axis as viewed from the origin looking
	 * along that axis. The particular method defines which direction is
	 * positive, and what is being rotated (point or axis).
	 */
	Z_AXIS_ROTATION(8610, "Z-axis rotation", OperationType.COORDINATE, "rZ",
			"eZ"),

	/**
	 * difference between the Z values of a point in the target and source
	 * coordinate reference systems
	 */
	Z_AXIS_TRANSLATION(8607, "Z-axis translation", OperationType.COORDINATE,
			"dZ", "tZ");

	/**
	 * Alias to parameter mapping
	 */
	private static final Map<String, Set<OperationParameters>> aliasParameters = new HashMap<>();

	/**
	 * Code to parameter mapping
	 */
	private static final Map<Integer, OperationParameters> codeParameters = new HashMap<>();

	static {
		for (OperationParameters parameter : values()) {

			for (String alias : parameter.aliases) {
				String aliasLowerCase = alias.toLowerCase();
				Set<OperationParameters> parameterSet = aliasParameters
						.get(aliasLowerCase);
				if (parameterSet == null) {
					parameterSet = new HashSet<>();
					aliasParameters.put(aliasLowerCase, parameterSet);
				}
				parameterSet.add(parameter);
			}

			int code = parameter.getCode();
			if (codeParameters.containsKey(code)) {
				throw new CRSException(
						"Duplicate configured Operation Parameter code: "
								+ code);
			}
			codeParameters.put(code, parameter);

		}
	}

	/**
	 * Parameter code
	 */
	private final int code;

	/**
	 * Parameter name
	 */
	private final String name;

	/**
	 * Operation type
	 */
	private final OperationType operationType;

	/**
	 * Aliases
	 */
	private final Set<String> aliases = new LinkedHashSet<>();

	/**
	 * Unit type
	 */
	private final UnitType unitType;

	/**
	 * Constructor
	 * 
	 * @param code
	 *            parameter code
	 * @param name
	 *            parameter name
	 * @param operationType
	 *            operation type
	 * @param aliases
	 *            parameter aliases
	 */
	private OperationParameters(int code, String name,
			OperationType operationType, String... aliases) {
		this(code, name, operationType, null, aliases);
	}

	/**
	 * Constructor
	 * 
	 * @param code
	 *            parameter code
	 * @param name
	 *            parameter name
	 * @param operationType
	 *            operation type
	 * @param unitType
	 *            unit type
	 * @param aliases
	 *            parameter aliases
	 */
	private OperationParameters(int code, String name,
			OperationType operationType, UnitType unitType, String... aliases) {
		this.code = code;
		this.name = name;
		this.operationType = operationType;
		this.unitType = unitType;
		addAlias(name);
		for (String alias : aliases) {
			addAlias(alias);
		}
	}

	/**
	 * Add an alias
	 * 
	 * @param alias
	 *            alias name
	 */
	private void addAlias(String alias) {
		aliases.add(alias);
		aliases.add(alias.replaceAll(" ", "_"));
	}

	/**
	 * Get the parameter code
	 * 
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Get the parameter name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the operation type
	 * 
	 * @return operation type
	 */
	public OperationType getOperationType() {
		return operationType;
	}

	/**
	 * Get the aliases
	 * 
	 * @return aliases
	 */
	public Set<String> getAliases() {
		return Collections.unmodifiableSet(aliases);
	}

	/**
	 * Get the unit type
	 * 
	 * @return unit type
	 */
	public UnitType getUnitType() {
		return unitType;
	}

	/**
	 * Get the parameter type from the alias
	 * 
	 * @param name
	 *            name or alias
	 * @return parameter type
	 */
	public static OperationParameters getParameter(String name) {
		OperationParameters parameter = null;
		Set<OperationParameters> parameters = getParameters(name);
		if (parameters != null && !parameters.isEmpty()) {
			parameter = parameters.iterator().next();
		}
		return parameter;
	}

	/**
	 * Get the parameter types from the alias
	 * 
	 * @param name
	 *            name or alias
	 * @return parameter types
	 */
	public static Set<OperationParameters> getParameters(String name) {
		Set<OperationParameters> parameters = null;
		if (name != null) {
			parameters = aliasParameters.get(name.toLowerCase());
		}
		return parameters;
	}

	/**
	 * Get the parameter type from the code
	 * 
	 * @param code
	 *            parameter code
	 * @return parameter type
	 */
	public static OperationParameters getParameter(int code) {
		return codeParameters.get(code);
	}

}
