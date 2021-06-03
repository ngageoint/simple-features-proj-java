package mil.nga.proj;

import java.io.IOException;
import java.util.List;

import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.datum.Datum;
import org.locationtech.proj4j.datum.Ellipsoid;
import org.locationtech.proj4j.datum.Grid;
import org.locationtech.proj4j.parser.DatumParameters;
import org.locationtech.proj4j.proj.Projection;
import org.locationtech.proj4j.units.Units;
import org.locationtech.proj4j.util.ProjectionMath;

import mil.nga.crs.CRS;
import mil.nga.crs.CRSException;
import mil.nga.crs.common.Unit;
import mil.nga.crs.geo.GeoCoordinateReferenceSystem;
import mil.nga.crs.geo.GeoDatum;
import mil.nga.crs.geo.PrimeMeridian;
import mil.nga.crs.geo.TriaxialEllipsoid;
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
	 * Get the CRS Factory
	 * 
	 * @return crs factory
	 */
	public static CRSFactory getCRSFactory() {
		return crsFactory;
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

		Projection proj = createProjection(datum.getEllipsoid(),
				geo.getCoordinateSystem().getAxisUnit(), geoDatum);

		return new CoordinateReferenceSystem(geo.getName(), null, datum, proj);
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

		GeoCoordinateReferenceSystem base = projected.getBase(); // TODO
		MapProjection mapProjection = projected.getMapProjection(); // TODO

		GeoDatum geoDatum = projected.getGeoDatum();

		Ellipsoid ellipsoid = convert(geoDatum.getEllipsoid());
		DatumParameters datumParameters = new DatumParameters();
		datumParameters.setA(ellipsoid.getA());
		datumParameters.setES(0);

		Datum datum = datumParameters.getDatum();

		Projection proj = createProjection(datum.getEllipsoid(),
				projected.getCoordinateSystem().getAxisUnit(), geoDatum);

		proj.initialize();

		return new CoordinateReferenceSystem(projected.getName(), null, datum,
				proj);
	}

	/**
	 * Create a proj4j projection
	 * 
	 * @param ellipsoid
	 *            ellipsoid
	 * @param unit
	 *            unit
	 * @param geoDatum
	 *            geo datum
	 * @return projection
	 */
	public static Projection createProjection(Ellipsoid ellipsoid, Unit unit,
			GeoDatum geoDatum) {

		Projection proj = createProjection(ellipsoid, unit);

		if (geoDatum.hasPrimeMeridian()) {
			PrimeMeridian primeMeridian = geoDatum.getPrimeMeridian();
			double primeMeridianLongitude = primeMeridian.getLongitude();
			if (primeMeridian.hasLongitudeUnit()
					&& Units.findUnits(primeMeridian.getLongitudeUnit()
							.getName()) == Units.RADIANS) {
				primeMeridianLongitude *= ProjectionMath.RTD;
			}
			proj.setPrimeMeridian(Double.toString(primeMeridianLongitude));
		}

		proj.initialize();

		return proj;
	}

	/**
	 * Create a proj4j projection
	 * 
	 * @param ellipsoid
	 *            ellipsoid
	 * @param unit
	 *            unit
	 * @return projection
	 */
	public static Projection createProjection(Ellipsoid ellipsoid, Unit unit) {

		Projection proj = createProjection(unit);

		proj.setEllipsoid(ellipsoid);

		proj.initialize();

		return proj;
	}

	/**
	 * Create a proj4j projection for the unit
	 * 
	 * @param unit
	 *            unit
	 * @return projection
	 */
	public static Projection createProjection(Unit unit) {

		org.locationtech.proj4j.units.Unit projUnit = Units.METRES;
		if (unit != null) {
			projUnit = Units.findUnits(unit.getName());
		}

		String projectionName = null;
		if (projUnit == Units.DEGREES) {
			projectionName = "longlat";
		} else {
			projectionName = "merc";
		}

		Projection proj = getCRSFactory().getRegistry()
				.getProjection(projectionName);
		proj.setUnits(projUnit);

		if (unit != null) {
			// TODO
			// proj.setFromMetres(unit.getConversionFactor());
		}

		return proj;
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
		double[] transform = null; // TODO?
		List<Grid> grids = null; // TODO?
		Ellipsoid ellipsoid = convert(geoDatum.getEllipsoid());

		String code = name;
		if (geoDatum.hasIdentifiers()) {
			code = geoDatum.getIdentifier(0).getNameAndUniqueIdentifier();
		}

		return new Datum(code, transform, grids, ellipsoid, name);
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
		String shortName = name;
		if (ellipsoid.hasIdentifiers()) {
			shortName = ellipsoid.getIdentifier(0).getNameAndUniqueIdentifier();
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

		return new Ellipsoid(shortName, equatorRadius, poleRadius,
				reciprocalFlattening, name);
	}

}
