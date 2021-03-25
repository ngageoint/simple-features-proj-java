package mil.nga.sf.proj;

import org.junit.Test;

import junit.framework.TestCase;
import mil.nga.proj.Projection;
import mil.nga.proj.ProjectionConstants;
import mil.nga.proj.ProjectionFactory;
import mil.nga.sf.Geometry;
import mil.nga.sf.GeometryType;
import mil.nga.sf.Point;

/**
 * README example tests
 * 
 * @author osbornb
 */
public class ReadmeTest {

	/**
	 * Test transform
	 */
	@Test
	public void testTransform() {

		Geometry transformed = testTransform(
				new Point(111319.49079327357, 111325.14286638486));

		TestCase.assertEquals(GeometryType.POINT,
				transformed.getGeometryType());
		Point point = (Point) transformed;
		TestCase.assertEquals(1.0, point.getX(), .0000000000001);
		TestCase.assertEquals(1.0, point.getY(), .0000000000001);

	}

	/**
	 * Test transform
	 * 
	 * @param geometry
	 *            geometry
	 * @return geometry
	 */
	private Geometry testTransform(Geometry geometry) {

		// Geometry geometry = ...

		Projection projection1 = ProjectionFactory.getProjection(
				ProjectionConstants.AUTHORITY_EPSG,
				ProjectionConstants.EPSG_WEB_MERCATOR);
		Projection projection2 = ProjectionFactory.getProjection(
				ProjectionConstants.AUTHORITY_EPSG,
				ProjectionConstants.EPSG_WORLD_GEODETIC_SYSTEM);

		GeometryTransform transform = GeometryTransform.create(projection1,
				projection2);

		Geometry transformed = transform.transform(geometry);

		return transformed;
	}

}
