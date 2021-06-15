package mil.nga.proj;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.datum.Datum;
import org.locationtech.proj4j.datum.Ellipsoid;
import org.locationtech.proj4j.parser.DatumParameters;
import org.locationtech.proj4j.proj.Projection;
import org.locationtech.proj4j.units.DegreeUnit;

import mil.nga.crs.CRS;
import mil.nga.crs.CRSException;
import mil.nga.crs.CompoundCoordinateReferenceSystem;
import mil.nga.crs.SimpleCoordinateReferenceSystem;
import mil.nga.crs.common.Axis;
import mil.nga.crs.common.CoordinateSystem;
import mil.nga.crs.common.Unit;
import mil.nga.crs.common.UnitType;
import mil.nga.crs.common.Units;
import mil.nga.crs.geo.GeoCoordinateReferenceSystem;
import mil.nga.crs.geo.GeoDatum;
import mil.nga.crs.geo.PrimeMeridian;
import mil.nga.crs.geo.TriaxialEllipsoid;
import mil.nga.crs.operation.OperationMethod;
import mil.nga.crs.operation.OperationParameter;
import mil.nga.crs.projected.MapProjection;
import mil.nga.crs.projected.ProjectedCoordinateReferenceSystem;
import mil.nga.crs.wkt.CRSReader;

/**
 * Coordinate Reference System Well-known text parser
 * 
 * @author osbornb
 */
public class CRSParser {

	/**
	 * CRS Factory
	 */
	private static final CRSFactory crsFactory = new CRSFactory();

	/**
	 * Name to known ellipsoid mapping
	 */
	private static final Map<String, Ellipsoid> ellipsoids = new HashMap<>();

	static {
		for (Ellipsoid ellipsoid : Ellipsoid.ellipsoids) {
			ellipsoids.put(ellipsoid.getShortName().toLowerCase(), ellipsoid);
			ellipsoids.put(ellipsoid.getName().toLowerCase(), ellipsoid);
		}
	}

	/**
	 * Get the CRS Factory
	 * 
	 * @return crs factory
	 */
	public static CRSFactory getCRSFactory() {
		return crsFactory;
	}

	/**
	 * Get a predefined proj4j ellipsoid by name or short name
	 * 
	 * @param name
	 *            name or short name
	 * @return ellipsoid or null
	 */
	public static Ellipsoid getEllipsoid(String name) {
		return ellipsoids.get(name.toLowerCase());
	}

	/**
	 * Parse crs well-known text into a proj4 coordinate reference system
	 * 
	 * @param wkt
	 *            crs well-known text
	 * @return coordinate reference system
	 */
	public static CoordinateReferenceSystem parse(String wkt) {

		CRS crsObject = null;
		try {
			crsObject = CRSReader.read(wkt);
		} catch (IOException e) {
			throw new ProjectionException("Failed to parse WKT: " + wkt, e);
		}

		CoordinateReferenceSystem crs = null;
		if (crsObject != null) {
			crs = convert(crsObject);
		}

		return crs;
	}

	/**
	 * Convert a CRS object into a proj4 coordinate reference system
	 * 
	 * @param crsObject
	 *            CRS object
	 * @return coordinate reference system
	 */
	public static CoordinateReferenceSystem convert(CRS crsObject) {

		CoordinateReferenceSystem crs = null;

		switch (crsObject.getType()) {

		case GEODETIC:
		case GEOGRAPHIC:
			crs = convert((GeoCoordinateReferenceSystem) crsObject);
			break;

		case PROJECTED:
			crs = convert((ProjectedCoordinateReferenceSystem) crsObject);
			break;

		case COMPOUND:
			crs = convert((CompoundCoordinateReferenceSystem) crsObject);
			break;

		default:

		}

		return crs;
	}

	/**
	 * Convert a geodetic or geographic crs into a proj4 coordinate reference
	 * system
	 * 
	 * @param geo
	 *            geodetic or geographic crs
	 * @return coordinate reference system
	 */
	public static CoordinateReferenceSystem convert(
			GeoCoordinateReferenceSystem geo) {

		GeoDatum geoDatum = geo.getGeoDatum();
		Datum datum = convert(geoDatum);

		Projection projection = createProjection(geo.getCoordinateSystem());
		updateProjection(projection, datum.getEllipsoid(), geoDatum);
		projection.initialize();

		return new CoordinateReferenceSystem(geo.getName(), null, datum,
				projection);
	}

	/**
	 * Convert a projected crs into a proj4 coordinate reference system
	 * 
	 * @param projected
	 *            projected crs
	 * @return coordinate reference system
	 */
	public static CoordinateReferenceSystem convert(
			ProjectedCoordinateReferenceSystem projected) {

		MapProjection mapProjection = projected.getMapProjection();

		GeoDatum geoDatum = projected.getGeoDatum();

		Ellipsoid ellipsoid = convert(geoDatum.getEllipsoid());
		DatumParameters datumParameters = new DatumParameters();

		OperationMethod method = mapProjection.getMethod();
		if (projected.hasIdentifiers()
				&& projected.getIdentifier(0).getNameAndUniqueIdentifier()
						.equalsIgnoreCase(ProjectionConstants.AUTHORITY_EPSG
								+ ":"
								+ ProjectionConstants.EPSG_WEB_MERCATOR)) {
			datumParameters.setA(ellipsoid.getA());
			datumParameters.setES(0);
		} else {
			datumParameters.setEllipsoid(ellipsoid);
		}

		datumParameters.setDatumTransform(convertDatumTransform(method));

		Datum datum = datumParameters.getDatum();

		Projection projection = createProjection(
				projected.getCoordinateSystem(), mapProjection);
		updateProjection(projection, datum.getEllipsoid(), geoDatum);
		updateProjection(projection, method);
		projection.initialize();

		return new CoordinateReferenceSystem(projected.getName(), null, datum,
				projection);
	}

	/**
	 * Convert a compound crs into a proj4 coordinate reference system
	 * 
	 * @param compound
	 *            compound crs
	 * @return coordinate reference system
	 */
	public static CoordinateReferenceSystem convert(
			CompoundCoordinateReferenceSystem compound) {

		CoordinateReferenceSystem crs = null;

		for (SimpleCoordinateReferenceSystem simpleCrs : compound
				.getCoordinateReferenceSystems()) {

			crs = convert(simpleCrs);

			if (crs != null) {
				break;
			}

		}

		return crs;
	}

	/**
	 * Convert a Datum
	 * 
	 * @param geoDatum
	 *            crs wkt geo datum
	 * @return proj4j datum
	 */
	public static Datum convert(GeoDatum geoDatum) {

		String name = geoDatum.getName();
		Ellipsoid ellipsoid = convert(geoDatum.getEllipsoid());

		String code = name;
		if (geoDatum.hasIdentifiers()) {
			code = geoDatum.getIdentifier(0).getNameAndUniqueIdentifier();
		}

		return new Datum(code, null, null, ellipsoid, name);
	}

	/**
	 * Convert an Ellipsoid
	 * 
	 * @param ellipsoid
	 *            crs wkt ellipsoid
	 * @return proj4j ellipsoid
	 */
	public static Ellipsoid convert(mil.nga.crs.geo.Ellipsoid ellipsoid) {

		String name = ellipsoid.getName();

		Ellipsoid converted = getEllipsoid(name);

		if (converted == null) {

			String shortName = name;
			if (ellipsoid.hasIdentifiers()) {
				shortName = ellipsoid.getIdentifier(0)
						.getNameAndUniqueIdentifier();
			}
			double equatorRadius = ellipsoid.getSemiMajorAxis();
			double poleRadius = 0;
			double reciprocalFlattening = 0;

			switch (ellipsoid.getType()) {
			case OBLATE:
				reciprocalFlattening = ellipsoid.getInverseFlattening();
				if (reciprocalFlattening == 0) {
					reciprocalFlattening = Double.POSITIVE_INFINITY;
				}
				break;
			case TRIAXIAL:
				TriaxialEllipsoid triaxial = (TriaxialEllipsoid) ellipsoid;
				poleRadius = triaxial.getSemiMinorAxis();
				break;
			default:
				throw new CRSException(
						"Unsupported Ellipsoid Type: " + ellipsoid.getType());
			}

			converted = new Ellipsoid(shortName, equatorRadius, poleRadius,
					reciprocalFlattening, name);
		}

		return converted;
	}

	/**
	 * Convert the operation method into datum transform
	 * 
	 * @param method
	 *            operation method
	 * @return transform
	 */
	public static double[] convertDatumTransform(OperationMethod method) {

		double[] transform3 = new double[3];
		double[] transform7 = new double[7];
		boolean param3 = false;
		boolean param7 = false;

		for (OperationParameter parameter : method.getParameters()) {

			if (parameter.hasParameter()) {

				switch (parameter.getParameter()) {

				case X_AXIS_TRANSLATION:
					transform3[0] = getValue(parameter, Units.getMetre());
					param3 = true;
					break;

				case Y_AXIS_TRANSLATION:
					transform3[1] = getValue(parameter, Units.getMetre());
					param3 = true;
					break;

				case Z_AXIS_TRANSLATION:
					transform3[2] = getValue(parameter, Units.getMetre());
					param3 = true;
					break;

				case X_AXIS_ROTATION:
					transform7[3] = getValue(parameter, Units.getArcSecond());
					param7 = true;
					break;

				case Y_AXIS_ROTATION:
					transform7[4] = getValue(parameter, Units.getArcSecond());
					param7 = true;
					break;

				case Z_AXIS_ROTATION:
					transform7[5] = getValue(parameter, Units.getArcSecond());
					param7 = true;
					break;

				case SCALE_DIFFERENCE:
					transform7[6] = getValue(parameter,
							Units.getPartsPerMillion());
					param7 = true;
					break;

				default:
					break;

				}
			}
		}

		double[] transform = null;
		if (param7) {
			transform7[0] = transform3[0];
			transform7[1] = transform3[1];
			transform7[2] = transform3[2];
			transform = transform7;
		} else if (param3) {
			transform = transform3;
		}

		return transform;
	}

	/**
	 * Create a proj4j projection
	 * 
	 * @param projection
	 *            projection
	 * @param ellipsoid
	 *            ellipsoid
	 * @param geoDatum
	 *            geo datum
	 */
	public static void updateProjection(Projection projection,
			Ellipsoid ellipsoid, GeoDatum geoDatum) {

		projection.setEllipsoid(ellipsoid);

		if (geoDatum.hasPrimeMeridian()) {
			PrimeMeridian primeMeridian = geoDatum.getPrimeMeridian();
			double primeMeridianLongitude = convertValue(
					primeMeridian.getLongitude(),
					primeMeridian.getLongitudeUnit(), Units.getDegree());
			projection
					.setPrimeMeridian(Double.toString(primeMeridianLongitude));
		}

	}

	/**
	 * Create a proj4j projection for the unit
	 * 
	 * @param coordinateSystem
	 *            coordinate system
	 * @return projection
	 */
	public static Projection createProjection(
			CoordinateSystem coordinateSystem) {

		Unit unit = coordinateSystem.getAxisUnit();

		String projectionName = null;
		if (unit != null && (unit.getType() == UnitType.ANGLEUNIT
				|| (unit.getType() == UnitType.UNIT
						&& unit.getName().toLowerCase().startsWith("deg")))) {
			projectionName = "longlat";
		} else {
			projectionName = "merc";
		}

		return createProjection(projectionName, coordinateSystem);
	}

	/**
	 * Create a proj4j projection for the method and unit
	 * 
	 * @param coordinateSystem
	 *            coordinate system
	 * @param mapProjection
	 *            map projection
	 * @return projection
	 */
	public static Projection createProjection(CoordinateSystem coordinateSystem,
			MapProjection mapProjection) {

		Projection projection = null;

		OperationMethod method = mapProjection.getMethod();

		if (method.hasMethod()) {

			String projectionName = null;

			switch (method.getMethod()) {

			case ALBERS_EQUAL_AREA:
				projectionName = "aea";
				break;

			case AMERICAN_POLYCONIC:
				projectionName = "poly";
				break;

			case CASSINI_SOLDNER:
				projectionName = "cass";
				break;

			case EQUIDISTANT_CYLINDRICAL:
				projectionName = "eqc";
				break;

			case HOTLINE_OBLIQUE_MERCATOR_A:
			case HOTLINE_OBLIQUE_MERCATOR_B:
				if (mapProjection.getName().toLowerCase()
						.contains("swiss oblique mercator")) {
					projectionName = "somerc";
				} else {
					projectionName = "omerc";
				}
				break;

			case KROVAK:
				projectionName = "krovak";
				break;

			case LAMBERT_AZIMUTHAL_EQUAL_AREA:
				projectionName = "laea";
				break;

			case LAMBERT_CONIC_CONFORMAL_1SP:
			case LAMBERT_CONIC_CONFORMAL_2SP:
				projectionName = "lcc";
				break;

			case LAMBERT_CYLINDRICAL_EQUAL_AREA:
				projectionName = "cea";
				break;

			case MERCATOR_A:
			case MERCATOR_B:
				projectionName = "merc";
				break;

			case NEW_ZEALAND_MAP_GRID:
				projectionName = "nzmg";
				break;

			case OBLIQUE_STEREOGRAPHIC:
				projectionName = "sterea";
				break;

			case POLAR_STEREOGRAPHIC_A:
			case POLAR_STEREOGRAPHIC_B:
			case POLAR_STEREOGRAPHIC_C:
				projectionName = "stere";
				break;

			case POPULAR_VISUALISATION_PSEUDO_MERCATOR:
				projectionName = "merc";
				break;

			case TRANSVERSE_MERCATOR:
			case TRANSVERSE_MERCATOR_SOUTH_ORIENTATED:
				if (mapProjection.getName().toLowerCase()
						.contains("utm zone")) {
					projectionName = "utm";
				} else {
					projectionName = "tmerc";
				}
				break;

			default:

			}

			if (projectionName != null) {
				projection = createProjection(projectionName, coordinateSystem);
			}
		}

		if (projection == null) {
			projection = createProjection(coordinateSystem);
		}

		return projection;
	}

	/**
	 * Create a proj4j projection for the projection name and unit
	 * 
	 * @param projectionName
	 *            projection name
	 * @param coordinateSystem
	 *            coordinate system
	 * @return projection
	 */
	public static Projection createProjection(String projectionName,
			CoordinateSystem coordinateSystem) {

		Projection projection = getCRSFactory().getRegistry()
				.getProjection(projectionName);

		String axisOrder = convert(coordinateSystem.getAxes());
		// Only known proj4 axis specification is wsu
		if (axisOrder.equals("wsu")) {
			projection.setAxisOrder(axisOrder);
		}

		return projection;
	}

	/**
	 * Update the method parameters in the projection
	 * 
	 * @param projection
	 *            proj4j projection
	 * @param method
	 *            operation method
	 */
	public static void updateProjection(Projection projection,
			OperationMethod method) {
		if (method.hasParameters()) {
			for (OperationParameter parameter : method.getParameters()) {
				updateProjection(projection, method, parameter);
			}
		}
	}

	/**
	 * Update the method parameter in the projection
	 * 
	 * @param projection
	 *            proj4j projection
	 * @param method
	 *            operation method
	 * @param parameter
	 *            operation parameter
	 */
	public static void updateProjection(Projection projection,
			OperationMethod method, OperationParameter parameter) {

		if (parameter.hasParameter()) {

			switch (parameter.getParameter()) {

			case FALSE_EASTING:
			case EASTING_AT_PROJECTION_CENTRE:
			case EASTING_AT_FALSE_ORIGIN:
				projection.setFalseEasting(
						getValue(parameter, projection.getUnits()));
				break;

			case FALSE_NORTHING:
			case NORTHING_AT_PROJECTION_CENTRE:
			case NORTHING_AT_FALSE_ORIGIN:
				projection.setFalseNorthing(
						getValue(parameter, projection.getUnits()));
				break;

			case SCALE_FACTOR_AT_NATURAL_ORIGIN:
			case SCALE_FACTOR_ON_INITIAL_LINE:
				projection
						.setScaleFactor(getValue(parameter, Units.getUnity()));
				break;

			case LATITUDE_OF_1ST_STANDARD_PARALLEL:
				projection.setProjectionLatitude1(
						getValue(parameter, Units.getRadian()));
				break;

			case LATITUDE_OF_2ND_STANDARD_PARALLEL:
				projection.setProjectionLatitude2(
						getValue(parameter, Units.getRadian()));
				break;

			case LATITUDE_OF_PROJECTION_CENTRE:
			case LATITUDE_OF_NATURAL_ORIGIN:
			case LATITUDE_OF_FALSE_ORIGIN:
				projection.setProjectionLatitude(
						getValue(parameter, Units.getRadian()));
				if (method.hasMethod()) {
					switch (method.getMethod()) {
					case POLAR_STEREOGRAPHIC_A:
					case POLAR_STEREOGRAPHIC_B:
					case POLAR_STEREOGRAPHIC_C:
						projection.setTrueScaleLatitude(
								projection.getProjectionLatitude());
						break;
					default:
					}
				}
				break;

			case LONGITUDE_OF_PROJECTION_CENTRE:
			case LONGITUDE_OF_NATURAL_ORIGIN:
			case LONGITUDE_OF_FALSE_ORIGIN:
			case LONGITUDE_OF_ORIGIN:
				projection.setProjectionLongitude(
						getValue(parameter, Units.getRadian()));
				break;

			default:

			}

		}

	}

	/**
	 * Convert the list of axes to a proj4j axis order
	 * 
	 * @param axes
	 *            list of axes
	 * @return axis order
	 */
	public static String convert(List<Axis> axes) {

		String axisValue = null;

		int axesCount = axes.size();
		if (axesCount == 2 || axesCount == 3) {

			StringBuilder axisString = new StringBuilder();

			for (Axis axis : axes) {

				switch (axis.getDirection()) {

				case EAST:
					axisString.append("e");
					break;

				case WEST:
					axisString.append("w");
					break;

				case NORTH:
					axisString.append("n");
					break;

				case SOUTH:
					axisString.append("s");
					break;

				case UP:
					axisString.append("u");
					break;

				case DOWN:
					axisString.append("d");
					break;

				default:
					axisString = null;

				}

				if (axisString == null) {
					break;
				}
			}

			if (axisString != null) {

				if (axesCount == 2) {
					axisString.append("u");
				}

				axisValue = axisString.toString();
			}

		}

		return axisValue;
	}

	/**
	 * Get the operation parameter value in the specified unit
	 * 
	 * @param parameter
	 *            operation parameter
	 * @param unit
	 *            desired unit
	 * @return value
	 */
	public static double getValue(OperationParameter parameter,
			org.locationtech.proj4j.units.Unit unit) {

		Unit desiredUnit = null;

		if (unit instanceof DegreeUnit) {
			desiredUnit = Units.getDegree();
		} else {
			desiredUnit = Units.getMetre();
		}

		return getValue(parameter, desiredUnit);
	}

	/**
	 * Get the operation parameter value in the specified unit
	 * 
	 * @param parameter
	 *            operation parameter
	 * @param unit
	 *            desired unit
	 * @return value
	 */
	public static double getValue(OperationParameter parameter, Unit unit) {
		return convertValue(parameter.getValue(), parameter.getUnit(), unit);
	}

	/**
	 * Convert the value from a unit to another
	 * 
	 * @param value
	 *            value to convert
	 * @param fromUnit
	 *            from unit
	 * @param toUnit
	 *            to unit
	 * @return value
	 */
	public static double convertValue(double value, Unit fromUnit,
			Unit toUnit) {

		if (fromUnit == null) {
			fromUnit = Units.getDefaultUnit(toUnit.getType());
		}

		if (Units.canConvert(fromUnit, toUnit)) {
			value = Units.convert(value, fromUnit, toUnit);
		}

		return value;
	}

}
