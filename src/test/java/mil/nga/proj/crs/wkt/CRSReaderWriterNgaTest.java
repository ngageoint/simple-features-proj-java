package mil.nga.proj.crs.wkt;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * CRS Reader and Writer NGA tests
 * 
 * @author osbornb
 */
public class CRSReaderWriterNgaTest {

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

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("\"8056\"", "8056");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

}
