package mil.nga.proj.crs.wkt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import org.junit.Test;

import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * Well-Known Text Utility tests
 * 
 * @author osbornb
 */
public class WKTUtilsTest {

	/**
	 * Test pretty WKT
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testPretty() throws IOException {

		String text = "PROJCRS[\"WGS 84 / Pseudo-Mercator\",BASEGEOGCRS[\"WGS 84\","
				+ "ENSEMBLE[\"World Geodetic System 1984 ensemble\","
				+ "MEMBER[\"World Geodetic System 1984 (Transit)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"World Geodetic System 1984 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"World Geodetic System 1984 (G873)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,ID[\"EPSG\",7030]],"
				+ "ENSEMBLEACCURACY[2.0],ID[\"EPSG\",6326]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Popular Visualisation Pseudo-Mercator\","
				+ "METHOD[\"Popular Visualisation Pseudo Mercator\",ID[\"EPSG\",1024]],"
				+ "PARAMETER[\"Latitude of natural origin\",0.0,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Longitude of natural origin\",0.0,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"False easting\",0.0,LENGTHUNIT[\"metre\",1.0,ID[\"EPSG\",9001]]],"
				+ "PARAMETER[\"False northing\",0.0,LENGTHUNIT[\"metre\",1.0,ID[\"EPSG\",9001]]],"
				+ "ID[\"EPSG\",3856]],CS[Cartesian,2,ID[\"EPSG\",4499]],"
				+ "AXIS[\"Easting (X)\",east],AXIS[\"Northing (Y)\",north],"
				+ "LENGTHUNIT[\"metre\",1.0,ID[\"EPSG\",9001]],ID[\"EPSG\",3857]]";

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String wkt = CRSWriter.write(crs);
		String pretty = CRSWriter.writePretty(crs);
		String prettyTab = CRSWriter.writePrettyTabIndent(crs);
		String prettyNo = CRSWriter.writePrettyNoIndent(crs);
		String prettyCustom = CRSWriter.writePretty(crs, "\t  ");
		String prettyNewline = CRSWriter.writePretty(crs, "\n\n", "\t  ");

		assertNotEquals(wkt, pretty);
		assertNotEquals(wkt, prettyTab);
		assertNotEquals(wkt, prettyNo);
		assertNotEquals(wkt, prettyCustom);
		assertNotEquals(wkt, prettyNewline);
		assertNotEquals(pretty, prettyTab);
		assertNotEquals(pretty, prettyNo);
		assertNotEquals(pretty, prettyCustom);
		assertNotEquals(pretty, prettyNewline);
		assertNotEquals(prettyTab, prettyNo);
		assertNotEquals(prettyTab, prettyCustom);
		assertNotEquals(prettyTab, prettyNewline);
		assertNotEquals(prettyNo, prettyCustom);
		assertNotEquals(prettyNo, prettyNewline);
		assertNotEquals(prettyCustom, prettyNewline);

		String pretty2 = CRSWriter.writePretty(text);
		String prettyTab2 = CRSWriter.writePrettyTabIndent(text);
		String prettyNo2 = CRSWriter.writePrettyNoIndent(text);
		String prettyCustom2 = CRSWriter.writePretty(text, "\t  ");
		String prettyNewline2 = CRSWriter.writePretty(text, "\n\n", "\t  ");

		assertEquals(pretty, pretty2);
		assertEquals(prettyTab, prettyTab2);
		assertEquals(prettyNo, prettyNo2);
		assertEquals(prettyCustom, prettyCustom2);
		assertEquals(prettyNewline, prettyNewline2);

		String pretty3 = CRSWriter.writePretty(wkt);
		String prettyTab3 = CRSWriter.writePrettyTabIndent(wkt);
		String prettyNo3 = CRSWriter.writePrettyNoIndent(wkt);
		String prettyCustom3 = CRSWriter.writePretty(wkt, "\t  ");
		String prettyNewline3 = CRSWriter.writePretty(wkt, "\n\n", "\t  ");

		assertEquals(pretty, pretty3);
		assertEquals(prettyTab, prettyTab3);
		assertEquals(prettyNo, prettyNo3);
		assertEquals(prettyCustom, prettyCustom3);
		assertEquals(prettyNewline, prettyNewline3);

		CoordinateReferenceSystem prettyCrs = CRSReader.read(pretty, true);
		CoordinateReferenceSystem prettyTabCrs = CRSReader.read(prettyTab,
				true);
		CoordinateReferenceSystem prettyNoCrs = CRSReader.read(prettyNo, true);
		CoordinateReferenceSystem prettyCustomCrs = CRSReader.read(prettyCustom,
				true);
		CoordinateReferenceSystem prettyNewlineCrs = CRSReader
				.read(prettyNewline, true);

		assertEquals(crs, prettyCrs);
		assertEquals(crs, prettyTabCrs);
		assertEquals(crs, prettyNoCrs);
		assertEquals(crs, prettyCustomCrs);
		assertEquals(crs, prettyNewlineCrs);

		assertEquals(wkt, CRSWriter.write(prettyCrs));
		assertEquals(wkt, CRSWriter.write(prettyTabCrs));
		assertEquals(wkt, CRSWriter.write(prettyNoCrs));
		assertEquals(wkt, CRSWriter.write(prettyCustomCrs));
		assertEquals(wkt, CRSWriter.write(prettyNewlineCrs));

	}

}
