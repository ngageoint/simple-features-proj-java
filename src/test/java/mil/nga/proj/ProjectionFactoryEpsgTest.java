package mil.nga.proj;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.ProjCoordinate;
import org.locationtech.proj4j.datum.Datum;
import org.locationtech.proj4j.datum.Ellipsoid;
import org.locationtech.proj4j.units.Units;

import junit.framework.TestCase;
import mil.nga.sf.GeometryEnvelope;

/**
 * Projection Factory EPSG Test
 * 
 * @author osbornb
 */
public class ProjectionFactoryEpsgTest {

	/**
	 * Authority
	 */
	private final String authority = "EPSG";

	/**
	 * Clear the projections before each test
	 */
	@Before
	public void clear() {
		ProjectionFactory.clear();
		ProjectionRetriever.clear();
	}

	/**
	 * Test custom projections
	 */
	@Test
	public void test4326() {

		String definition = "GEOGCRS[\"WGS 84\",ENSEMBLE[\"World Geodetic System 1984 ensemble\","
				+ "MEMBER[\"World Geodetic System 1984 (Transit)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"World Geodetic System 1984 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"World Geodetic System 1984 (G873)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,ID[\"EPSG\",7030]],"
				+ "ENSEMBLEACCURACY[2],ID[\"EPSG\",6326]],"
				+ "CS[ellipsoidal,2,ID[\"EPSG\",6422]],"
				+ "AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],"
				+ "ID[\"EPSG\",4326]]";

		projectionTestDerived(4326, definition);

		definition = "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS 84\",6378137,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4326\"]]";

		projectionTestDerived(4326, definition);

		definition = "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS84\",6378137,298.257223563]],"
				+ "PRIMEM[\"Greenwich\",0],"
				+ "UNIT[\"degree\",0.0174532925199433]]";

		projectionTestSpecified(4326, definition);

	}

	/**
	 * Test projection creation and transformations with derived authority and
	 * epsg
	 * 
	 * @param epsg
	 *            EPSG code
	 * @param definition
	 *            WKT definition
	 */
	private void projectionTestDerived(long epsg, String definition) {

		Projection projection = ProjectionFactory
				.getProjectionByDefinition(definition);
		projectionTest(epsg, definition, projection);

	}

	/**
	 * Test projection creation and transformations with specified authority and
	 * epsg
	 * 
	 * @param epsg
	 *            EPSG code
	 * @param definition
	 *            WKT definition
	 */
	private void projectionTestSpecified(long epsg, String definition) {

		Projection projection = ProjectionFactory
				.getProjectionByDefinition(authority, epsg, definition);
		projectionTest(epsg, definition, projection);

	}

	/**
	 * Test projection creation and transformations
	 * 
	 * @param epsg
	 *            EPSG code
	 * @param definition
	 *            WKT definition
	 * @param projection
	 *            projection
	 */
	private void projectionTest(long epsg, String definition,
			Projection projection) {

		TestCase.assertNotNull(projection);
		TestCase.assertEquals(authority, projection.getAuthority());
		TestCase.assertEquals(Long.toString(epsg), projection.getCode());
		TestCase.assertEquals(definition, projection.getDefinition());

		clear();

		Projection projection2 = ProjectionFactory.getProjection(epsg);

		compare(projection, projection2);

		GeometryEnvelope range = new GeometryEnvelope();

		long transformCode;
		if (projection.isUnit(Units.METRES)) {
			transformCode = 4326;
			range.setMinX(-ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH);
			range.setMinY(ProjectionConstants.WEB_MERCATOR_MIN_LAT_RANGE);
			range.setMaxX(ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH);
			range.setMaxY(ProjectionConstants.WEB_MERCATOR_MAX_LAT_RANGE);
		} else {
			transformCode = 3857;
			range.setMinX(-ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH);
			range.setMinY(ProjectionConstants.WEB_MERCATOR_MIN_LAT_RANGE);
			range.setMaxX(ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH);
			range.setMaxY(ProjectionConstants.WEB_MERCATOR_MAX_LAT_RANGE);
		}

		Projection transformProjection = ProjectionFactory
				.getProjection(transformCode);

		ProjectionTransform transformTo = transformProjection
				.getTransformation(projection);
		ProjectionTransform transformTo2 = transformProjection
				.getTransformation(projection2);

		ProjectionTransform transformFrom = projection
				.getTransformation(transformProjection);
		ProjectionTransform transformFrom2 = projection2
				.getTransformation(transformProjection);

		double xRange = range.getMaxX() - range.getMinX();
		double yRange = range.getMaxY() - range.getMinY();
		double midX = range.getMinX() + (xRange / 2.0);
		double midY = range.getMinY() + (yRange / 2.0);

		coordinateTest(range.getMinX(), range.getMinY(), transformTo,
				transformTo2, transformFrom, transformFrom2);
		coordinateTest(range.getMinX(), range.getMaxY(), transformTo,
				transformTo2, transformFrom, transformFrom2);
		coordinateTest(range.getMaxX(), range.getMinY(), transformTo,
				transformTo2, transformFrom, transformFrom2);
		coordinateTest(range.getMaxX(), range.getMaxY(), transformTo,
				transformTo2, transformFrom, transformFrom2);
		coordinateTest(midX, range.getMinY(), transformTo, transformTo2,
				transformFrom, transformFrom2);
		coordinateTest(midX, range.getMaxY(), transformTo, transformTo2,
				transformFrom, transformFrom2);
		coordinateTest(range.getMinX(), midY, transformTo, transformTo2,
				transformFrom, transformFrom2);
		coordinateTest(range.getMaxX(), midY, transformTo, transformTo2,
				transformFrom, transformFrom2);
		coordinateTest(midX, midY, transformTo, transformTo2, transformFrom,
				transformFrom2);

		for (int i = 0; i < 10; i++) {

			double x = range.getMinX() + (Math.random() * xRange);
			double y = range.getMinY() + (Math.random() * yRange);
			coordinateTest(x, y, transformTo, transformTo2, transformFrom,
					transformFrom2);
		}

	}

	/**
	 * Test transformations of a coordinate
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param transformTo
	 *            transformation to
	 * @param transformTo2
	 *            transformation to 2
	 * @param transformFrom
	 *            transformation from
	 * @param transformFrom2
	 *            transformation from 2
	 */
	private void coordinateTest(double x, double y,
			ProjectionTransform transformTo, ProjectionTransform transformTo2,
			ProjectionTransform transformFrom,
			ProjectionTransform transformFrom2) {
		coordinateTest(new ProjCoordinate(x, y), transformTo, transformTo2,
				transformFrom, transformFrom2);
	}

	/**
	 * Test transformation of a coordinate
	 * 
	 * @param coordinate
	 *            projection coordinate
	 * @param transformTo
	 *            transformation to
	 * @param transformTo2
	 *            transformation to 2
	 * @param transformFrom
	 *            transformation from
	 * @param transformFrom2
	 *            transformation from 2
	 */
	private void coordinateTest(ProjCoordinate coordinate,
			ProjectionTransform transformTo, ProjectionTransform transformTo2,
			ProjectionTransform transformFrom,
			ProjectionTransform transformFrom2) {

		ProjCoordinate coordinateTo = transformTo.transform(coordinate);
		ProjCoordinate coordinateTo2 = transformTo2.transform(coordinate);
		assertEquals(coordinateTo2.x, coordinateTo.x, 0);
		assertEquals(coordinateTo2.y, coordinateTo.y, 0);

		ProjCoordinate coordinateFrom = transformFrom.transform(coordinateTo);
		ProjCoordinate coordinateFrom2 = transformFrom2.transform(coordinateTo);
		assertEquals(coordinateFrom2.x, coordinateFrom.x, 0);
		assertEquals(coordinateFrom2.y, coordinateFrom.y, 0);

	}

	/**
	 * Compare two projections
	 * 
	 * @param projection
	 *            projection
	 * @param projection2
	 *            projection 2
	 */
	private void compare(Projection projection, Projection projection2) {

		assertEquals(projection, projection2);

		CoordinateReferenceSystem crs = projection.getCrs();
		CoordinateReferenceSystem crs2 = projection2.getCrs();
		Datum datum = crs.getDatum();
		Datum datum2 = crs2.getDatum();
		Ellipsoid ellipsoid = datum.getEllipsoid();
		Ellipsoid ellipsoid2 = datum2.getEllipsoid();
		double[] transform = datum.getTransformToWGS84();
		double[] transform2 = datum2.getTransformToWGS84();
		org.locationtech.proj4j.proj.Projection proj = crs.getProjection();
		org.locationtech.proj4j.proj.Projection proj2 = crs2.getProjection();

		assertEquals(ellipsoid.getEccentricitySquared(),
				ellipsoid2.getEccentricitySquared(), 0);
		assertEquals(ellipsoid.getEquatorRadius(),
				ellipsoid2.getEquatorRadius(), 0);
		assertEquals(ellipsoid.getA(), ellipsoid2.getA(), 0);
		assertEquals(ellipsoid.getB(), ellipsoid2.getB(), 0);

		assertEquals(proj.getEllipsoid().getEccentricitySquared(),
				proj2.getEllipsoid().getEccentricitySquared(), 0);
		assertEquals(proj.getEllipsoid().getEquatorRadius(),
				proj2.getEllipsoid().getEquatorRadius(), 0);
		assertEquals(proj.getEllipsoid().getA(), proj2.getEllipsoid().getA(),
				0);
		assertEquals(proj.getEllipsoid().getB(), proj2.getEllipsoid().getB(),
				0);

		if (transform != null || transform2 != null) {
			if (transform != null && transform2 != null) {
				assertEquals(transform.length, transform2.length);
				for (int i = 0; i < transform.length; i++) {
					assertEquals(transform[i], transform2[i], 0);
				}
			} else {
				double[] transformTest = transform != null ? transform
						: transform2;
				for (int i = 0; i < transformTest.length; i++) {
					assertEquals(0, transformTest[i], 0);
				}
			}
		}

		assertEquals(proj.getAlpha(), proj2.getAlpha(), 0);
		assertEquals(proj.getEPSGCode(), proj2.getEPSGCode(), 0);
		assertEquals(proj.getEquatorRadius(), proj2.getEquatorRadius(), 0);
		assertEquals(proj.getFalseEasting(), proj2.getFalseEasting(), 0);
		assertEquals(proj.getFalseNorthing(), proj2.getFalseNorthing(), 0);
		assertEquals(proj.getFromMetres(), proj2.getFromMetres(), 0);
		assertEquals(proj.getLonC(), proj2.getLonC(), 0);
		assertEquals(proj.getMaxLatitude(), proj2.getMaxLatitude(), 0);
		assertEquals(proj.getMaxLatitudeDegrees(),
				proj2.getMaxLatitudeDegrees(), 0);
		assertEquals(proj.getMaxLongitude(), proj2.getMaxLongitude(), 0);
		assertEquals(proj.getMaxLongitudeDegrees(),
				proj2.getMaxLongitudeDegrees(), 0);
		assertEquals(proj.getMinLatitude(), proj2.getMinLatitude(), 0);
		assertEquals(proj.getMinLatitudeDegrees(),
				proj2.getMinLatitudeDegrees(), 0);
		assertEquals(proj.getMinLongitude(), proj2.getMinLongitude(), 0);
		assertEquals(proj.getMinLongitudeDegrees(),
				proj2.getMinLongitudeDegrees(), 0);
		assertEquals(proj.getPrimeMeridian(), proj2.getPrimeMeridian());
		assertEquals(proj.getPROJ4Description().toLowerCase(),
				proj2.getPROJ4Description().toLowerCase());
		assertEquals(proj.getProjectionLatitude(),
				proj2.getProjectionLatitude(), 0);
		assertEquals(proj.getProjectionLatitude1(),
				proj2.getProjectionLatitude1(), 0);
		assertEquals(proj.getProjectionLatitude1Degrees(),
				proj2.getProjectionLatitude1Degrees(), 0);
		assertEquals(proj.getProjectionLatitude2(),
				proj2.getProjectionLatitude2(), 0);
		assertEquals(proj.getProjectionLatitude2Degrees(),
				proj2.getProjectionLatitude2Degrees(), 0);
		assertEquals(proj.getProjectionLatitudeDegrees(),
				proj2.getProjectionLatitudeDegrees(), 0);
		assertEquals(proj.getProjectionLongitude(),
				proj2.getProjectionLongitude(), 0);
		assertEquals(proj.getProjectionLongitudeDegrees(),
				proj2.getProjectionLongitudeDegrees(), 0);
		assertEquals(proj.getScaleFactor(), proj2.getScaleFactor(), 0);
		assertEquals(proj.getTrueScaleLatitude(), proj2.getTrueScaleLatitude(),
				0);
		assertEquals(proj.getTrueScaleLatitudeDegrees(),
				proj2.getTrueScaleLatitudeDegrees(), 0);
		assertEquals(proj.getUnits(), proj2.getUnits());

	}

}
