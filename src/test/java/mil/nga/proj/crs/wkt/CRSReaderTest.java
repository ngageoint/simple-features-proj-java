package mil.nga.proj.crs.wkt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.Extent;
import mil.nga.proj.crs.GeographicBoundingBox;
import mil.nga.proj.crs.TemporalExtent;
import mil.nga.proj.crs.Unit;
import mil.nga.proj.crs.UnitType;
import mil.nga.proj.crs.Usage;
import mil.nga.proj.crs.VerticalExtent;

/**
 * CRS Reader tests
 * 
 * @author osbornb
 */
public class CRSReaderTest {

	/**
	 * Test EPSG 4979
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testRead4979() throws IOException {

		String text = "GEODCRS[\"WGS 84\",DATUM[\"World Geodetic System 1984\",ELLIPSOID[\"WGS 84\",6378137,298.257223563,LENGTHUNIT[\"metre\",1.0]]],CS[ellipsoidal,3],AXIS[\"Geodetic latitude (Lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433]],AXIS[\"Geodetic longitude (Long)\",east,ANGLEUNIT[\"degree\",0.0174532925199433]],AXIS[\"Ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1.0]],ID[\"EPSG\",4979]]\"";

		CoordinateReferenceSystem crs = testRead(text);
		assertNotNull(crs);
	}

	/**
	 * Test read
	 * 
	 * @param text
	 *            crs wkt
	 * @return coordinate reference system
	 * @throws IOException
	 *             upon error
	 */
	private CoordinateReferenceSystem testRead(String text) throws IOException {

		CoordinateReferenceSystem crs = CRSReader.readCRS(text);

		// TODO

		return crs;
	}

	/**
	 * Test scope
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testScope() throws IOException {

		CRSReader reader = new CRSReader(
				"SCOPE[\"Large scale topographic mapping and cadastre.\"]");
		String scope = reader.readScope();
		assertNotNull(scope);
		assertEquals("Large scale topographic mapping and cadastre.", scope);
		reader.close();

	}

	/**
	 * Test area description
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testAreaDescription() throws IOException {

		CRSReader reader = new CRSReader("AREA[\"Netherlands offshore.\"]");
		String areaDescription = reader.readAreaDescription();
		assertNotNull(areaDescription);
		assertEquals("Netherlands offshore.", areaDescription);
		reader.close();

	}

	/**
	 * Test geographic bounding box
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeographicBoundingBox() throws IOException {

		CRSReader reader = new CRSReader("BBOX[51.43,2.54,55.77,6.40]");
		GeographicBoundingBox boundingBox = reader.readGeographicBoundingBox();
		assertNotNull(boundingBox);
		assertEquals(51.43, boundingBox.getLowerLeftLatitude(), 0);
		assertEquals(2.54, boundingBox.getLowerLeftLongitude(), 0);
		assertEquals(55.77, boundingBox.getUpperRightLatitude(), 0);
		assertEquals(6.40, boundingBox.getUpperRightLongitude(), 0);
		reader.close();

		reader = new CRSReader("BBOX[-55.95,160.60,-25.88,-171.20]");
		boundingBox = reader.readGeographicBoundingBox();
		assertNotNull(boundingBox);
		assertEquals(-55.95, boundingBox.getLowerLeftLatitude(), 0);
		assertEquals(160.60, boundingBox.getLowerLeftLongitude(), 0);
		assertEquals(-25.88, boundingBox.getUpperRightLatitude(), 0);
		assertEquals(-171.20, boundingBox.getUpperRightLongitude(), 0);
		reader.close();

	}

	/**
	 * Test vertical extent
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testVerticalExtent() throws IOException {

		CRSReader reader = new CRSReader(
				"VERTICALEXTENT[-1000,0,LENGTHUNIT[\"metre\",1.0]]");
		VerticalExtent verticalExtent = reader.readVerticalExtent();
		assertNotNull(verticalExtent);
		assertEquals(-1000, verticalExtent.getMinimumHeight(), 0);
		assertEquals(0, verticalExtent.getMaximumHeight(), 0);
		Unit lengthUnit = verticalExtent.getLengthUnit();
		assertNotNull(lengthUnit);
		assertEquals(UnitType.LENGTHUNIT, lengthUnit.getType());
		assertEquals("metre", lengthUnit.getName());
		assertEquals(1.0, lengthUnit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("VERTICALEXTENT[-1000,0]");
		verticalExtent = reader.readVerticalExtent();
		assertNotNull(verticalExtent);
		assertEquals(-1000, verticalExtent.getMinimumHeight(), 0);
		assertEquals(0, verticalExtent.getMaximumHeight(), 0);
		lengthUnit = verticalExtent.getLengthUnit();
		assertNull(lengthUnit);
		reader.close();

	}

	/**
	 * Test temporal extent
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testTemporalExtent() throws IOException {

		CRSReader reader = new CRSReader("TIMEEXTENT[2013-01-01,2013-12-31]");
		TemporalExtent temporalExtent = reader.readTemporalExtent();
		assertNotNull(temporalExtent);
		assertEquals("2013-01-01", temporalExtent.getStart());
		assertEquals("2013-12-31", temporalExtent.getEnd());
		reader.close();

		reader = new CRSReader("TIMEEXTENT[\"Jurassic\",\"Quaternary\"]");
		temporalExtent = reader.readTemporalExtent();
		assertNotNull(temporalExtent);
		assertEquals("Jurassic", temporalExtent.getStart());
		assertEquals("Quaternary", temporalExtent.getEnd());
		reader.close();

	}

	/**
	 * Test usage
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testUsage() throws IOException {

		CRSReader reader = new CRSReader(
				"USAGE[SCOPE[\"Spatial referencing.\"],"
						+ "AREA[\"Netherlands offshore.\"],TIMEEXTENT[1976-01,2001-04]]");
		Usage usage = reader.readUsage();
		assertNotNull(usage);
		assertEquals("Spatial referencing.", usage.getScope());
		Extent extent = usage.getExtent();
		assertNotNull(extent);
		assertEquals("Netherlands offshore.", extent.getAreaDescription());
		TemporalExtent temporalExtent = extent.getTemporalExtent();
		assertNotNull(temporalExtent);
		assertEquals("1976-01", temporalExtent.getStart());
		assertEquals("2001-04", temporalExtent.getEnd());
		reader.close();

	}

	/**
	 * Test usages
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testUsages() throws IOException {

		CRSReader reader = new CRSReader(
				"USAGE[SCOPE[\"Small scale topographic mapping.\"],"
						+ "AREA[\"Finland - onshore and offshore.\"]],"
						+ "USAGE[SCOPE[\"Cadastre.\"],"
						+ "AREA[\"Finland - onshore between 26°30'E and 27°30'E.\"],"
						+ "BBOX[60.36,26.5,70.05,27.5]]");
		List<Usage> usages = reader.readUsages();
		assertNotNull(usages);
		assertEquals(2, usages.size());
		Usage usage = usages.get(0);
		assertEquals("Small scale topographic mapping.", usage.getScope());
		Extent extent = usage.getExtent();
		assertNotNull(extent);
		assertEquals("Finland - onshore and offshore.",
				extent.getAreaDescription());
		usage = usages.get(1);
		assertEquals("Cadastre.", usage.getScope());
		extent = usage.getExtent();
		assertNotNull(extent);
		assertEquals("Finland - onshore between 26°30'E and 27°30'E.",
				extent.getAreaDescription());
		GeographicBoundingBox boundingBox = extent.getGeographicBoundingBox();
		assertNotNull(boundingBox);
		assertEquals(60.36, boundingBox.getLowerLeftLatitude(), 0);
		assertEquals(26.5, boundingBox.getLowerLeftLongitude(), 0);
		assertEquals(70.05, boundingBox.getUpperRightLatitude(), 0);
		assertEquals(27.5, boundingBox.getUpperRightLongitude(), 0);
		reader.close();

	}

}
