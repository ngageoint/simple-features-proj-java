package mil.nga.crs.wkt;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import mil.nga.crs.CRS;

/**
 * CRS Reader and Writer NGA tests
 * 
 * @author osbornb
 */
public class CRSReaderWriterNgaTest {

	/**
	 * Test NGA 8047
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test8047() throws IOException {

		String text = "VERTCRS[\"EGM96 geoid depth\","
				+ "VDATUM[\"EGM96 geoid\",ANCHOR[\"WGS 84 ellipsoid\"]],"
				+ "CS[vertical,1],"
				+ "AXIS[\"Gravity-related depth (D)\",down],"
				+ "LENGTHUNIT[\"metre\",1.0],ID[\"NSG\",\"8047\"]]";

		CRS crs = CRSReader.read(text, true);

		String expectedText = text.replace("\"8047\"", "8047");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test NGA 8056
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test8056() throws IOException {

		String text = "VERTCRS[\"EGM2008 geoid depth\","
				+ "VDATUM[\"EGM2008 geoid\",ANCHOR[\"WGS 84 ellipsoid\"]],"
				+ "CS[vertical,1],"
				+ "AXIS[\"Gravity-related depth (D)\",down],"
				+ "LENGTHUNIT[\"metre\",1.0],ID[\"NSG\",\"8056\"]]";

		CRS crs = CRSReader.read(text, true);

		String expectedText = text.replace("\"8056\"", "8056");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test NGA 8101
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test8101() throws IOException {

		String text = "COMPOUNDCRS[“WGS84 Height (EGM08)”,"
				+ "GEODCRS[\"WGS 84\","
				+ "DATUM[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,LENGTHUNIT[\"metre\",1.0]]],"
				+ "CS[ellipsoidal,2],"
				+ "AXIS[\"Geodetic latitude (Lat)\",north],"
				+ "AXIS[\"Geodetic longitude (Long)\",east],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",4326]],"
				+ "VERTCRS[\"EGM2008 geoid height\","
				+ "VDATUM[\"EGM2008 geoid\",ANCHOR[\"WGS 84 ellipsoid\"]],"
				+ "CS[vertical,1],AXIS[\"Gravity-related height (H)\",up],"
				+ "LENGTHUNIT[\"metre\",1.0]ID[\"EPSG\",\"3855\"]],"
				+ "ID[“NSG”,”8101”]]";

		CRS crs = CRSReader.read(text, true);

		String expectedText = "COMPOUNDCRS[\"WGS84 Height (EGM08)\","
				+ "GEODCRS[\"WGS 84\","
				+ "DATUM[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,LENGTHUNIT[\"metre\",1.0]]],"
				+ "CS[ellipsoidal,2],"
				+ "AXIS[\"Geodetic latitude (Lat)\",north],"
				+ "AXIS[\"Geodetic longitude (Long)\",east],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",4326]],"
				+ "VERTCRS[\"EGM2008 geoid height\","
				+ "VDATUM[\"EGM2008 geoid\",ANCHOR[\"WGS 84 ellipsoid\"]],"
				+ "CS[vertical,1],AXIS[\"Gravity-related height (H)\",up],"
				+ "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",3855]],"
				+ "ID[\"NSG\",8101]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

}
