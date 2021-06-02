package mil.nga.proj;

import java.io.IOException;
import java.util.List;

import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.datum.Datum;
import org.locationtech.proj4j.datum.Ellipsoid;
import org.locationtech.proj4j.datum.Grid;
import org.locationtech.proj4j.proj.LongLatProjection;
import org.locationtech.proj4j.units.Units;
import org.locationtech.proj4j.util.ProjectionMath;

import mil.nga.crs.CRS;
import mil.nga.crs.CRSException;
import mil.nga.crs.common.Unit;
import mil.nga.crs.geo.GeoCoordinateReferenceSystem;
import mil.nga.crs.geo.GeoDatum;
import mil.nga.crs.geo.PrimeMeridian;
import mil.nga.crs.geo.TriaxialEllipsoid;
import mil.nga.crs.wkt.CRSReader;

/**
 * Coordinate Reference System Well-known text parser
 * 
 * @author osbornb
 */
public class CRSParser {

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

			GeoCoordinateReferenceSystem geo = (GeoCoordinateReferenceSystem) crsObject;

			String name = geo.getName();

			GeoDatum geoDatum = geo.getGeoDatum();
			Datum datum = convert(geoDatum);

			LongLatProjection proj = new LongLatProjection();
			proj.setEllipsoid(datum.getEllipsoid());
			mil.nga.crs.geo.Ellipsoid ellipsoid = geoDatum.getEllipsoid();
			if (ellipsoid.hasUnit()) {
				Unit unit = ellipsoid.getUnit();
				proj.setUnits(Units.findUnits(unit.getName()));
				proj.setFromMetres(unit.getConversionFactor());
			}
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

			crs = new CoordinateReferenceSystem(name, null, datum, proj);

			break;

		default:

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
