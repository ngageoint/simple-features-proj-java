package mil.nga.proj;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.proj4j.ProjCoordinate;

import junit.framework.TestCase;

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

		Projection projection = ProjectionFactory
				.getProjectionByDefinition(definition);
		TestCase.assertNotNull(projection);
		TestCase.assertEquals(authority, projection.getAuthority());
		TestCase.assertEquals("4326", projection.getCode());
		TestCase.assertEquals(definition, projection.getDefinition());

		ProjectionFactory.clear();
		ProjectionRetriever.clear();

		Projection projection2 = ProjectionFactory.getProjection(4326);

		Projection epsg3857 = ProjectionFactory.getProjection(3857);

		ProjectionTransform transformTo = epsg3857
				.getTransformation(projection);
		ProjectionTransform transformTo2 = epsg3857
				.getTransformation(projection2);

		ProjectionTransform transformFrom = projection
				.getTransformation(epsg3857);
		ProjectionTransform transformFrom2 = projection2
				.getTransformation(epsg3857);

		ProjCoordinate coordinate = new ProjCoordinate(
				-ProjectionConstants.WEB_MERCATOR_HALF_WORLD_WIDTH,
				ProjectionConstants.WEB_MERCATOR_MIN_LAT_RANGE);
		ProjCoordinate coordinateTo = transformTo.transform(coordinate);
		ProjCoordinate coordinateTo2 = transformTo.transform(coordinate);
		assertEquals(coordinateTo2.x, coordinateTo.x, 0);
		assertEquals(coordinateTo2.y, coordinateTo.y, 0);

		// TODO

	}

}
