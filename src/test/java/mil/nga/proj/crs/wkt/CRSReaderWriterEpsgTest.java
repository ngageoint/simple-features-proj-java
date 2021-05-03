package mil.nga.proj.crs.wkt;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * CRS Reader tests
 * 
 * @author osbornb
 */
public class CRSReaderWriterEpsgTest {

	/**
	 * Test EPSG 3857
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test3857() throws IOException {

		String text = "PROJCRS[\"WGS 84 / Pseudo-Mercator\",BASEGEOGCRS[\"WGS 84\","
				+ "ENSEMBLE[\"World Geodetic System 1984 ensemble\","
				+ "MEMBER[\"World Geodetic System 1984 (Transit)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"World Geodetic System 1984 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"World Geodetic System 1984 (G873)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,ID[\"EPSG\",7030]],"
				+ "ENSEMBLEACCURACY[2],ID[\"EPSG\",6326]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Popular Visualisation Pseudo-Mercator\","
				+ "METHOD[\"Popular Visualisation Pseudo Mercator\",ID[\"EPSG\",1024]],"
				+ "PARAMETER[\"Latitude of natural origin\",0,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Longitude of natural origin\",0,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"False easting\",0,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "PARAMETER[\"False northing\",0,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "ID[\"EPSG\",3856]],CS[Cartesian,2,ID[\"EPSG\",4499]],"
				+ "AXIS[\"Easting (X)\",east],AXIS[\"Northing (Y)\",north],"
				+ "LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",3857]]";

		CoordinateReferenceSystem crs = CRSReader.readCRS(text);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace("ENSEMBLEACCURACY[2]", "ENSEMBLEACCURACY[2.0]")
				.replace(",0,", ",0.0,").replace(",1,", ",1.0,");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.writeCRS(crs));

		text = "PROJCS[\"WGS 84 / Pseudo-Mercator\","
				+ "GEOGCRS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS 84\",6378137,298.257223563,"
				+ "ID[\"EPSG\",\"7030\"]],ID[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,ID[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",\"9122\"]],ID[\"EPSG\",\"4326\"]],"
				+ "PROJECTION[\"Mercator_1SP\"],"
				+ "PARAMETER[\"central_meridian\",0],"
				+ "PARAMETER[\"scale_factor\",1],"
				+ "PARAMETER[\"false_easting\",0],"
				+ "PARAMETER[\"false_northing\",0]"
				+ ",UNIT[\"metre\",1,ID[\"EPSG\",\"9001\"]]"
				+ ",AXIS[\"X\",EAST],AXIS[\"Y\",NORTH]"
				+ ",ID[\"EPSG\",\"3857\"]]";

		crs = CRSReader.readCRS(text);

		expectedText = "PROJCRS[\"WGS 84 / Pseudo-Mercator\","
				+ "BASEGEOGCRS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,"
				+ "ID[\"EPSG\",7030]],ID[\"EPSG\",6326]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Mercator_1SP\",METHOD[\"Mercator_1SP\"],"
				+ "PARAMETER[\"central_meridian\",0.0],"
				+ "PARAMETER[\"scale_factor\",1.0],"
				+ "PARAMETER[\"false_easting\",0.0],"
				+ "PARAMETER[\"false_northing\",0.0]],CS[ellipsoidal,2]"
				+ ",AXIS[\"X\",east],AXIS[\"Y\",north]"
				+ ",UNIT[\"metre\",1.0,ID[\"EPSG\",9001]]"
				+ ",ID[\"EPSG\",3857]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.writeCRS(crs));

	}

	/**
	 * Test EPSG 4326
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test4326() throws IOException {

		String text = "GEOGCRS[\"WGS 84\",ENSEMBLE[\"World Geodetic System 1984 ensemble\","
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

		CoordinateReferenceSystem crs = CRSReader.readCRS(text);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace("ENSEMBLEACCURACY[2]", "ENSEMBLEACCURACY[2.0]");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.writeCRS(crs));

		text = "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS 84\",6378137,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4326\"]]";

		crs = CRSReader.readCRS(text);

		expectedText = "GEOGCRS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,"
				+ "ID[\"EPSG\",7030]],ID[\"EPSG\",6326]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "CS[ellipsoidal,2],"
				+ "AXIS[\"Lon\",east],AXIS[\"Lat\",north],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4326]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.writeCRS(crs));

		text = "GEOGCS[\"WGS 84\"," + "DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS84\",6378137,298.257223563]],"
				+ "PRIMEM[\"Greenwich\",0],"
				+ "UNIT[\"degree\",0.0174532925199433]]";

		crs = CRSReader.readCRS(text);

		expectedText = "GEOGCRS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS84\",6378137.0,298.257223563]],"
				+ "PRIMEM[\"Greenwich\",0.0],"
				+ "CS[ellipsoidal,2],AXIS[\"Lon\",east],AXIS[\"Lat\",north],"
				+ "UNIT[\"degree\",0.0174532925199433]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.writeCRS(crs));

	}

	/**
	 * Test EPSG 4979
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test4979() throws IOException {

		String text = "GEOGCRS[\"WGS 84\",ENSEMBLE[\"World Geodetic System 1984 ensemble\","
				+ "MEMBER[\"World Geodetic System 1984 (Transit)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"World Geodetic System 1984 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"World Geodetic System 1984 (G873)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,ID[\"EPSG\",7030]],"
				+ "ENSEMBLEACCURACY[2],ID[\"EPSG\",6326]],"
				+ "CS[ellipsoidal,3,ID[\"EPSG\",6423]],"
				+ "AXIS[\"Geodetic latitude (Lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "AXIS[\"Geodetic longitude (Lon)\",east,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "AXIS[\"Ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],ID[\"EPSG\",4979]]";

		CoordinateReferenceSystem crs = CRSReader.readCRS(text);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace("ENSEMBLEACCURACY[2]", "ENSEMBLEACCURACY[2.0]")
				.replace(",1,", ",1.0,");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.writeCRS(crs));

		text = "GEOGCS[\"WGS 84\"," + "DATUM[\"World Geodetic System 1984\","
				+ "SPHEROID[\"WGS 84\",6378137.0,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0.0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.017453292519943295],"
				+ "AXIS[\"Geodetic latitude\",NORTH],"
				+ "AXIS[\"Geodetic longitude\",EAST],"
				+ "AXIS[\"Ellipsoidal height\",UP],"
				+ "AUTHORITY[\"EPSG\",\"4979\"]]";

		crs = CRSReader.readCRS(text);

		expectedText = "GEOGCRS[\"WGS 84\","
				+ "DATUM[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,"
				+ "ID[\"EPSG\",7030]],ID[\"EPSG\",6326]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "CS[ellipsoidal,3],AXIS[\"Geodetic latitude\",north],"
				+ "AXIS[\"Geodetic longitude\",east],"
				+ "AXIS[\"Ellipsoidal height\",up],"
				+ "UNIT[\"degree\",0.017453292519943295],"
				+ "ID[\"EPSG\",4979]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.writeCRS(crs));

		text = "GEODCRS[\"WGS 84\",DATUM[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,"
				+ "LENGTHUNIT[\"metre\",1.0]]],CS[ellipsoidal,3],"
				+ "AXIS[\"Geodetic latitude (Lat)\",north,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"Geodetic longitude (Long)\",east,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"Ellipsoidal height (h)\",up,"
				+ "LENGTHUNIT[\"metre\",1.0]],ID[\"EPSG\",4979]]";

		crs = CRSReader.readCRS(text);

		expectedText = text.replaceAll("6378137", "6378137.0");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.writeCRS(crs));

	}

}
