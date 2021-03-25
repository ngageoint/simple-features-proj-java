package mil.nga.proj;

import org.junit.Test;
import org.locationtech.proj4j.ProjCoordinate;

import junit.framework.TestCase;

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

		ProjCoordinate transformed = testTransform(
				new ProjCoordinate(111319.49079327357, 111325.14286638486));

		TestCase.assertEquals(1.0, transformed.x, .0000000000001);
		TestCase.assertEquals(1.0, transformed.y, .0000000000001);

	}

	/**
	 * Test transform
	 * 
	 * @param coordinate
	 *            projection coordinate
	 * @return projection coordinate
	 */
	private ProjCoordinate testTransform(ProjCoordinate coordinate) {

		// ProjCoordinate coordinate = ...

		Projection projection1 = ProjectionFactory.getProjection(
				ProjectionConstants.AUTHORITY_EPSG,
				ProjectionConstants.EPSG_WEB_MERCATOR);
		Projection projection2 = ProjectionFactory.getProjection(
				ProjectionConstants.AUTHORITY_EPSG,
				ProjectionConstants.EPSG_WORLD_GEODETIC_SYSTEM);

		ProjectionTransform transform = projection1
				.getTransformation(projection2);

		ProjCoordinate transformed = transform.transform(coordinate);

		return transformed;
	}

}
