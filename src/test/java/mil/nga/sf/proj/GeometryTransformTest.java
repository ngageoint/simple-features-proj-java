package mil.nga.sf.proj;

import org.junit.Test;

import junit.framework.TestCase;
import mil.nga.proj.Projection;
import mil.nga.proj.ProjectionConstants;
import mil.nga.proj.ProjectionFactory;
import mil.nga.sf.Geometry;
import mil.nga.sf.LineString;
import mil.nga.sf.Point;
import mil.nga.sf.Polygon;

/**
 * Geometry Transform tests
 * 
 * @author osbornb
 */
public class GeometryTransformTest {

	/**
	 * Test transforms
	 */
	@Test
	public void testTransform() {

		Polygon polygon = new Polygon();
		LineString ring = new LineString();
		ring.addPoint(
				new Point(-ProjectionConstants.WEB_MERCATOR_HALF_WORLD_WIDTH,
						-ProjectionConstants.WEB_MERCATOR_HALF_WORLD_WIDTH));
		ring.addPoint(
				new Point(ProjectionConstants.WEB_MERCATOR_HALF_WORLD_WIDTH,
						-ProjectionConstants.WEB_MERCATOR_HALF_WORLD_WIDTH));
		ring.addPoint(
				new Point(ProjectionConstants.WEB_MERCATOR_HALF_WORLD_WIDTH,
						ProjectionConstants.WEB_MERCATOR_HALF_WORLD_WIDTH));
		ring.addPoint(
				new Point(-ProjectionConstants.WEB_MERCATOR_HALF_WORLD_WIDTH,
						ProjectionConstants.WEB_MERCATOR_HALF_WORLD_WIDTH));
		polygon.addRing(ring);

		Polygon wgs84Polygon = new Polygon();
		LineString wgs84Ring = new LineString();
		wgs84Ring.addPoint(
				new Point(-ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH,
						-ProjectionConstants.WEB_MERCATOR_MAX_LAT_RANGE));
		wgs84Ring.addPoint(
				new Point(ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH,
						-ProjectionConstants.WEB_MERCATOR_MAX_LAT_RANGE));
		wgs84Ring.addPoint(
				new Point(ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH,
						ProjectionConstants.WEB_MERCATOR_MAX_LAT_RANGE));
		wgs84Ring.addPoint(
				new Point(-ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH,
						ProjectionConstants.WEB_MERCATOR_MAX_LAT_RANGE));
		wgs84Polygon.addRing(wgs84Ring);

		Projection webMercator = ProjectionFactory.getProjection(
				ProjectionConstants.AUTHORITY_EPSG,
				ProjectionConstants.EPSG_WEB_MERCATOR);
		Projection wgs84 = ProjectionFactory.getProjection(
				ProjectionConstants.AUTHORITY_EPSG,
				ProjectionConstants.EPSG_WORLD_GEODETIC_SYSTEM);

		GeometryTransform transformWebMercatorToWgs84 = GeometryTransform
				.create(webMercator, wgs84);

		Geometry transformedGeometry = transformWebMercatorToWgs84
				.transform(polygon);

		TestCase.assertNotNull(transformedGeometry);
		TestCase.assertTrue(transformedGeometry instanceof Polygon);

		TestCase.assertEquals(wgs84Polygon, transformedGeometry);

		GeometryTransform transformWgs84ToWebMercator = GeometryTransform
				.create(wgs84, webMercator);

		Geometry transformedGeometry2 = transformWgs84ToWebMercator
				.transform(transformedGeometry);

		TestCase.assertNotNull(transformedGeometry2);
		TestCase.assertTrue(transformedGeometry2 instanceof Polygon);

		Polygon transformedPolygon2 = (Polygon) transformedGeometry2;

		TestCase.assertEquals(polygon.numRings(),
				transformedPolygon2.numRings());
		TestCase.assertEquals(polygon.getExteriorRing().numPoints(),
				transformedPolygon2.getExteriorRing().numPoints());

		for (int i = 0; i < polygon.getExteriorRing().numPoints(); i++) {
			TestCase.assertEquals(polygon.getExteriorRing().getPoint(i).getX(),
					transformedPolygon2.getExteriorRing().getPoint(i).getX());
			TestCase.assertEquals(polygon.getExteriorRing().getPoint(i).getY(),
					transformedPolygon2.getExteriorRing().getPoint(i).getY(),
					0.0000001);
		}

	}

}
