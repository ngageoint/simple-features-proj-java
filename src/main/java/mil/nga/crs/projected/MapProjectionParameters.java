package mil.nga.crs.projected;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import mil.nga.crs.common.UnitType;

/**
 * Map Projection Parameters
 * 
 * @author osbornb
 */
public enum MapProjectionParameters {

	/**
	 * The rotation applied to spherical coordinates for the oblique projection,
	 * measured on the conformal sphere in the plane of the meridian of origin.
	 */
	CO_LATITUDE_OF_CONE_AXIS(1036, "co-latitude of cone axis",
			UnitType.ANGLEUNIT),

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
			UnitType.ANGLEUNIT, "latitude of origin", "latitude of center"),

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
			UnitType.ANGLEUNIT, "longitude of origin", "longitude of center",
			"central meridian"),

	/**
	 * 
	 * factor by which the map grid is reduced or enlarged during the projection
	 * process, defined by its value at the natural origin
	 */
	SCALE_FACTOR_AT_NATURAL_ORIGIN(8805, "scale factor at natural origin",
			UnitType.SCALEUNIT, "scale factor"),

	/**
	 * value assigned to the abscissa (east or west) axis of the projection grid
	 * at the natural origin
	 */
	FALSE_EASTING(8806, "false easting", UnitType.LENGTHUNIT),

	/**
	 * value assigned to the ordinate (north or south) axis of the projection
	 * grid at the natural origin
	 */
	FALSE_NORTHING(8807, "false northing", UnitType.LENGTHUNIT),

	/**
	 * latitude of the point at which the azimuth of the central line for an
	 * oblique projection is defined
	 */
	LATITUDE_OF_PROJECTION_CENTRE(8811, "latitude of projection centre",
			UnitType.ANGLEUNIT),

	/**
	 * longitude of the point at which the azimuth of the central line for an
	 * oblique projection is defined
	 */
	LONGITUDE_OF_PROJECTION_CENTRE(8812, "longitude of projection centre",
			UnitType.ANGLEUNIT),

	/**
	 * direction (north zero, east of north being positive) of the great circle
	 * which is the centre line of an oblique projection
	 * 
	 * The azimuth is given at the projection centre.
	 */
	AZIMUTH_OF_INITIAL_LINE(8813, "azimuth of initial line",
			UnitType.ANGLEUNIT),

	/**
	 * angle at the natural origin of an oblique projection through which the
	 * natural coordinate reference system is rotated to make the projection
	 * north axis parallel with true north
	 */
	ANGLE_FROM_RECTIFIED_TO_SKEW_GRID(8814, "angle from rectified to skew grid",
			UnitType.ANGLEUNIT),

	/**
	 * factor by which the map grid is reduced or enlarged during the projection
	 * process, defined by its value at the projection centre
	 */
	SCALE_FACTOR_ON_INITIAL_LINE(8815, "scale factor on initial line",
			UnitType.SCALEUNIT),

	/**
	 * easting value assigned to the projection centre
	 */
	EASTING_AT_PROJECTION_CENTRE(8816, "easting at projection centre",
			UnitType.LENGTHUNIT, "false easting"),

	/**
	 * northing value assigned to the projection centre
	 */
	NORTHING_AT_PROJECTION_CENTRE(8817, "northing at projection centre",
			UnitType.LENGTHUNIT, "false northing"),

	/**
	 * Latitude of the parallel on which the conic or cylindrical projection is
	 * based. This latitude is not geographic, but is defined on the conformal
	 * sphere AFTER its rotation to obtain the oblique aspect of the projection.
	 */
	LATITUDE_OF_PSEUDO_STANDARD_PARALLEL(8818,
			"latitude of pseudo standard parallel", UnitType.ANGLEUNIT),

	/**
	 * The factor by which the map grid is reduced or enlarged during the
	 * projection process, defined by its value at the pseudo-standard parallel.
	 */
	SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL(8819,
			"scale factor on pseudo standard parallel", UnitType.SCALEUNIT),

	/**
	 * geodetic latitude of the point which is not the natural origin and at
	 * which grid coordinate values false easting and false northing are defined
	 */
	LATITUDE_OF_FALSE_ORIGIN(8821, "latitude of false origin",
			UnitType.ANGLEUNIT, "latitude of origin"),

	/**
	 * geodetic longitude of the point which is not the natural origin and at
	 * which grid coordinate values false easting and false northing are defined
	 */
	LONGITUDE_OF_FALSE_ORIGIN(8822, "longitude of false origin",
			UnitType.ANGLEUNIT, "longitude of origin", "longitude of center",
			"central meridian"),

	/**
	 * geodetic latitude of one of the parallels of intersection of the cone
	 * with the ellipsoid. It is normally but not necessarily that nearest to
	 * the pole
	 * 
	 * Scale is true along this parallel.
	 */
	LATITUDE_OF_1ST_STANDARD_PARALLEL(8823, "latitude of 1st standard parallel",
			UnitType.ANGLEUNIT, "standard parallel 1"),

	/**
	 * 
	 * geodetic latitude of one of the parallels at which the cone intersects
	 * with the ellipsoid. It is normally but not necessarily that nearest to
	 * the equator
	 * 
	 * Scale is true along this parallel.
	 */
	LATITUDE_OF_2ND_STANDARD_PARALLEL(8824, "latitude of 2nd standard parallel",
			UnitType.ANGLEUNIT, "standard parallel 2"),

	/**
	 * easting value assigned to the false origin
	 */
	EASTING_AT_FALSE_ORIGIN(8826, "easting at false origin",
			UnitType.LENGTHUNIT, "false easting"),

	/**
	 * northing value assigned to the false origin
	 */
	NORTHING_AT_FALSE_ORIGIN(8827, "northing at false origin",
			UnitType.LENGTHUNIT, "false northing"),

	/**
	 * For polar aspect azimuthal projections, the meridian along which the
	 * northing axis increments and also across which parallels of latitude
	 * increment towards the north pole.
	 */
	LONGITUDE_OF_ORIGIN(8833, "longitude of origin", UnitType.ANGLEUNIT);

	/**
	 * Alias to parameter mapping
	 */
	private static final Map<String, Set<MapProjectionParameters>> aliasParameters = new HashMap<>();

	/**
	 * Code to parameter mapping
	 */
	private static final Map<Integer, MapProjectionParameters> codeParameters = new HashMap<>();

	static {
		for (MapProjectionParameters parameter : values()) {

			for (String alias : parameter.aliases) {
				String aliasLowerCase = alias.toLowerCase();
				Set<MapProjectionParameters> parameterSet = aliasParameters
						.get(aliasLowerCase);
				if (parameterSet == null) {
					parameterSet = new HashSet<>();
					aliasParameters.put(aliasLowerCase, parameterSet);
				}
				parameterSet.add(parameter);
			}

			codeParameters.put(parameter.getCode(), parameter);

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
	 * Aliases
	 */
	private final Set<String> aliases = new LinkedHashSet<>();

	/**
	 * Unite type
	 */
	private final UnitType unitType;

	/**
	 * Constructor
	 * 
	 * @param code
	 *            parameter code
	 * @param name
	 *            parameter name
	 * @param unitType
	 *            unit type
	 * @param aliases
	 *            parameter aliases
	 */
	private MapProjectionParameters(int code, String name, UnitType unitType,
			String... aliases) {
		this.code = code;
		this.name = name;
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
	public static MapProjectionParameters getParameter(String name) {
		MapProjectionParameters parameter = null;
		Set<MapProjectionParameters> parameters = getParameters(name);
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
	public static Set<MapProjectionParameters> getParameters(String name) {
		Set<MapProjectionParameters> parameters = null;
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
	public static MapProjectionParameters getParameter(int code) {
		return codeParameters.get(code);
	}

}
