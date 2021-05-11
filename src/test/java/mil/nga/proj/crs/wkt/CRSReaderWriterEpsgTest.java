package mil.nga.proj.crs.wkt;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * CRS Reader and Writer EPSG tests
 * 
 * @author osbornb
 */
public class CRSReaderWriterEpsgTest {

	// TODO temp
	/**
	 * Test EPSG 0000
	 * 
	 * @throws IOException
	 *             upon error
	 */
	// @Test
	// public void test0000() throws IOException {
	//
	// String text = "";
	//
	// CoordinateReferenceSystem crs = CRSReader.read(text, true);
	//
	// String expectedText = text;
	//
	// assertEquals(expectedText, crs.toString());
	// assertEquals(expectedText, CRSWriter.write(crs));
	// assertEquals(WKTUtils.pretty(expectedText),
	// CRSWriter.writePretty(crs));
	//
	// text = "";
	//
	// crs = CRSReader.read(text, true);
	//
	// expectedText = text;
	//
	// assertEquals(expectedText, crs.toString());
	// assertEquals(expectedText, CRSWriter.write(crs));
	// assertEquals(WKTUtils.pretty(expectedText),
	// CRSWriter.writePretty(crs));
	//
	// text = "";
	//
	// crs = CRSReader.read(text, true);
	//
	// expectedText = text;
	//
	// assertEquals(expectedText, crs.toString());
	// assertEquals(expectedText, CRSWriter.write(crs));
	// assertEquals(WKTUtils.pretty(expectedText),
	// CRSWriter.writePretty(crs));
	//
	// }

	/**
	 * Test EPSG 3035
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test3035() throws IOException {

		String text = "PROJCRS[\"ETRS89-extended / LAEA Europe\",BASEGEOGCRS[\"ETRS89\","
				+ "ENSEMBLE[\"European Terrestrial Reference System 1989 ensemble\","
				+ "MEMBER[\"European Terrestrial Reference Frame 1989\",ID[\"EPSG\",1178]],"
				+ "MEMBER[\"European Terrestrial Reference Frame 1990\",ID[\"EPSG\",1179]],"
				+ "MEMBER[\"European Terrestrial Reference Frame 1991\",ID[\"EPSG\",1180]],"
				+ "MEMBER[\"European Terrestrial Reference Frame 1992\",ID[\"EPSG\",1181]],"
				+ "MEMBER[\"European Terrestrial Reference Frame 1993\",ID[\"EPSG\",1182]],"
				+ "MEMBER[\"European Terrestrial Reference Frame 1994\",ID[\"EPSG\",1183]],"
				+ "MEMBER[\"European Terrestrial Reference Frame 1996\",ID[\"EPSG\",1184]],"
				+ "MEMBER[\"European Terrestrial Reference Frame 1997\",ID[\"EPSG\",1185]],"
				+ "MEMBER[\"European Terrestrial Reference Frame 2000\",ID[\"EPSG\",1186]],"
				+ "MEMBER[\"European Terrestrial Reference Frame 2005\",ID[\"EPSG\",1204]],"
				+ "MEMBER[\"European Terrestrial Reference Frame 2014\",ID[\"EPSG\",1206]],"
				+ "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,ID[\"EPSG\",7019]],"
				+ "ENSEMBLEACCURACY[0.1],ID[\"EPSG\",6258]],ID[\"EPSG\",4258]],"
				+ "CONVERSION[\"Europe Equal Area 2001\",METHOD[\"Lambert Azimuthal Equal Area\",ID[\"EPSG\",9820]],"
				+ "PARAMETER[\"Latitude of natural origin\",52,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Longitude of natural origin\",10,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"False easting\",4321000,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "PARAMETER[\"False northing\",3210000,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "ID[\"EPSG\",19986]],CS[Cartesian,2,ID[\"EPSG\",4532]],"
				+ "AXIS[\"Northing (Y)\",north],AXIS[\"Easting (X)\",east],"
				+ "LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",3035]]";

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace(",52", ",52.0").replace(",10", ",10.0")
				.replace("4321000", "4321000.0").replace("3210000", "3210000.0")
				.replace(",1,", ",1.0,");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"ETRS89 / ETRS-LAEA\",GEOGCS[\"ETRS89\","
				+ "DATUM[\"European_Terrestrial_Reference_System_1989\","
				+ "SPHEROID[\"GRS 1980\",6378137,298.257222101,"
				+ "AUTHORITY[\"EPSG\",\"7019\"]],"
				+ "AUTHORITY[\"EPSG\",\"6258\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4258\"]],"
				+ "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
				+ "PROJECTION[\"Lambert_Azimuthal_Equal_Area\"],"
				+ "PARAMETER[\"latitude_of_center\",52],"
				+ "PARAMETER[\"longitude_of_center\",10],"
				+ "PARAMETER[\"false_easting\",4321000],"
				+ "PARAMETER[\"false_northing\",3210000],"
				+ "AUTHORITY[\"EPSG\",\"3035\"],"
				+ "AXIS[\"X\",EAST],AXIS[\"Y\",NORTH]]";

		crs = CRSReader.read(text, true);

		expectedText = "PROJCRS[\"ETRS89 / ETRS-LAEA\",BASEGEOGCRS[\"ETRS89\","
				+ "DATUM[\"European_Terrestrial_Reference_System_1989\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,"
				+ "ID[\"EPSG\",7019]],ID[\"EPSG\",6258]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4258]],"
				+ "CONVERSION[\"Lambert_Azimuthal_Equal_Area\",METHOD[\"Lambert_Azimuthal_Equal_Area\"],"
				+ "PARAMETER[\"latitude_of_center\",52.0],"
				+ "PARAMETER[\"longitude_of_center\",10.0],"
				+ "PARAMETER[\"false_easting\",4321000.0],"
				+ "PARAMETER[\"false_northing\",3210000.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"X\",east],AXIS[\"Y\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],ID[\"EPSG\",3035]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"ETRS89 / LAEA Europe\",GEOGCRS[\"ETRS89\","
				+ "DATUM[\"European_Terrestrial_Reference_System_1989\","
				+ "SPHEROID[\"GRS 1980\",6378137,298.257222101,"
				+ "ID[\"EPSG\",\"7019\"]],"
				+ "ABRIDGEDTRANSFORMATION[0,0,0,0,0,0,0],"
				+ "ID[\"EPSG\",\"6258\"]],"
				+ "PRIMEM[\"Greenwich\",0,ID[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",\"9122\"]],ID[\"EPSG\",\"4258\"]],"
				+ "PROJECTION[\"Lambert_Azimuthal_Equal_Area\"],"
				+ "PARAMETER[\"latitude_of_center\",52],"
				+ "PARAMETER[\"longitude_of_center\",10],"
				+ "PARAMETER[\"false_easting\",4321000],"
				+ "PARAMETER[\"false_northing\",3210000],"
				+ "UNIT[\"metre\",1,ID[\"EPSG\",\"9001\"]],"
				+ "ID[\"EPSG\",\"3035\"]]";

		crs = CRSReader.read(text);

		expectedText = "PROJCRS[\"ETRS89 / LAEA Europe\",BASEGEOGCRS[\"ETRS89\","
				+ "DATUM[\"European_Terrestrial_Reference_System_1989\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,"
				+ "ID[\"EPSG\",7019]],ID[\"EPSG\",6258]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4258]],"
				+ "CONVERSION[\"Lambert_Azimuthal_Equal_Area\",METHOD[\"Lambert_Azimuthal_Equal_Area\"],"
				+ "PARAMETER[\"latitude_of_center\",52.0],"
				+ "PARAMETER[\"longitude_of_center\",10.0],"
				+ "PARAMETER[\"false_easting\",4321000.0],"
				+ "PARAMETER[\"false_northing\",3210000.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"X\",east],AXIS[\"Y\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],"
				+ "ID[\"EPSG\",3035]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"ETRS89 / LAEA Europe\",GEOGCRS[\"ETRS89\","
				+ "DATUM[\"European_Terrestrial_Reference_System_1989\","
				+ "SPHEROID[\"GRS 1980\",6378137,298.257222101,"
				+ "ID[\"EPSG\",\"7019\"]],"
				+ "ABRIDGEDTRANSFORMATION[0,0,0,0,0,0,0]],"
				+ "PRIMEM[\"Greenwich\",0,ID[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",\"9122\"]],ID[\"EPSG\",\"4258\"]],"
				+ "PROJECTION[\"Lambert_Azimuthal_Equal_Area\"],"
				+ "PARAMETER[\"latitude_of_center\",52],"
				+ "PARAMETER[\"longitude_of_center\",10],"
				+ "PARAMETER[\"false_easting\",4321000],"
				+ "PARAMETER[\"false_northing\",3210000],"
				+ "UNIT[\"metre\",1,ID[\"EPSG\",\"9001\"]],"
				+ "ID[\"EPSG\",\"3035\"]]";

		crs = CRSReader.read(text);

		expectedText = "PROJCRS[\"ETRS89 / LAEA Europe\",BASEGEOGCRS[\"ETRS89\","
				+ "DATUM[\"European_Terrestrial_Reference_System_1989\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,"
				+ "ID[\"EPSG\",7019]]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4258]],"
				+ "CONVERSION[\"Lambert_Azimuthal_Equal_Area\",METHOD[\"Lambert_Azimuthal_Equal_Area\"],"
				+ "PARAMETER[\"latitude_of_center\",52.0],"
				+ "PARAMETER[\"longitude_of_center\",10.0],"
				+ "PARAMETER[\"false_easting\",4321000.0],"
				+ "PARAMETER[\"false_northing\",3210000.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"X\",east],AXIS[\"Y\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],"
				+ "ID[\"EPSG\",3035]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"ETRS89 / LAEA Europe\",GEOGCRS[\"ETRS89\","
				+ "DATUM[\"European_Terrestrial_Reference_System_1989\","
				+ "SPHEROID[\"GRS 1980\",6378137,298.257222101,"
				+ "ID[\"EPSG\",\"7019\"]],ID[\"EPSG\",\"6258\"],"
				+ "ABRIDGEDTRANSFORMATION[0,0,0,0,0,0,0]],"
				+ "PRIMEM[\"Greenwich\",0,ID[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",\"9122\"]],ID[\"EPSG\",\"4258\"]],"
				+ "PROJECTION[\"Lambert_Azimuthal_Equal_Area\"],"
				+ "PARAMETER[\"latitude_of_center\",52],"
				+ "PARAMETER[\"longitude_of_center\",10],"
				+ "PARAMETER[\"false_easting\",4321000],"
				+ "PARAMETER[\"false_northing\",3210000],"
				+ "UNIT[\"metre\",1,ID[\"EPSG\",\"9001\"]],"
				+ "ID[\"EPSG\",\"3035\"]]";

		crs = CRSReader.read(text);

		expectedText = "PROJCRS[\"ETRS89 / LAEA Europe\",BASEGEOGCRS[\"ETRS89\","
				+ "DATUM[\"European_Terrestrial_Reference_System_1989\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,"
				+ "ID[\"EPSG\",7019]],ID[\"EPSG\",6258]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4258]],"
				+ "CONVERSION[\"Lambert_Azimuthal_Equal_Area\",METHOD[\"Lambert_Azimuthal_Equal_Area\"],"
				+ "PARAMETER[\"latitude_of_center\",52.0],"
				+ "PARAMETER[\"longitude_of_center\",10.0],"
				+ "PARAMETER[\"false_easting\",4321000.0],"
				+ "PARAMETER[\"false_northing\",3210000.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"X\",east],AXIS[\"Y\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],"
				+ "ID[\"EPSG\",3035]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test EPSG 3395
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test3395() throws IOException {

		String text = "PROJCRS[\"WGS 84 / World Mercator\",BASEGEOGCRS[\"WGS 84\","
				+ "ENSEMBLE[\"World Geodetic System 1984 ensemble\","
				+ "MEMBER[\"World Geodetic System 1984 (Transit)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"World Geodetic System 1984 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"World Geodetic System 1984 (G873)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,ID[\"EPSG\",7030]],"
				+ "ENSEMBLEACCURACY[2],ID[\"EPSG\",6326]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"World Mercator\",METHOD[\"Mercator (variant A)\",ID[\"EPSG\",9804]],"
				+ "PARAMETER[\"Latitude of natural origin\",0,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Longitude of natural origin\",0,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Scale factor at natural origin\",1,SCALEUNIT[\"unity\",1,ID[\"EPSG\",9201]]],"
				+ "PARAMETER[\"False easting\",0,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "PARAMETER[\"False northing\",0,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "ID[\"EPSG\",19883]],CS[Cartesian,2,ID[\"EPSG\",4400]],"
				+ "AXIS[\"Easting (E)\",east],AXIS[\"Northing (N)\",north],"
				+ "LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",3395]]";

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace("ENSEMBLEACCURACY[2]", "ENSEMBLEACCURACY[2.0]")
				.replace(",0,", ",0.0,").replace(",1,", ",1.0,");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"WGS 84 / World Mercator\",GEOGCS[\"WGS 84\","
				+ "DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS 84\",6378137,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4326\"]],"
				+ "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
				+ "PROJECTION[\"Mercator_1SP\"],"
				+ "PARAMETER[\"central_meridian\",0],"
				+ "PARAMETER[\"scale_factor\",1],"
				+ "PARAMETER[\"false_easting\",0],"
				+ "PARAMETER[\"false_northing\",0],"
				+ "AUTHORITY[\"EPSG\",\"3395\"],"
				+ "AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH]]";

		crs = CRSReader.read(text, true);

		expectedText = "PROJCRS[\"WGS 84 / World Mercator\",BASEGEOGCRS[\"WGS 84\","
				+ "DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,"
				+ "ID[\"EPSG\",7030]],ID[\"EPSG\",6326]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Mercator_1SP\",METHOD[\"Mercator_1SP\"],"
				+ "PARAMETER[\"central_meridian\",0.0],"
				+ "PARAMETER[\"scale_factor\",1.0],"
				+ "PARAMETER[\"false_easting\",0.0],"
				+ "PARAMETER[\"false_northing\",0.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"Easting\",east],AXIS[\"Northing\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],ID[\"EPSG\",3395]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCRS[\"WGS 84 / World Mercator\","
				+ "BASEGEODCRS[\"WGS 84\","
				+ "DATUM[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563]]],"
				+ "CONVERSION[\"Mercator\","
				+ "METHOD[\"Mercator (variant A)\",ID[\"EPSG\",\"9804\"]],"
				+ "PARAMETER[\"Latitude of natural origin\",0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Longitude of natural origin\",0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Scale factor at natural origin\",1,"
				+ "SCALEUNIT[\"unity\",1.0]],"
				+ "PARAMETER[\"False easting\",0,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"False northing\",0,LENGTHUNIT[\"metre\",1.0]],"
				+ "ID[\"EPSG\",\"19833\"]],CS[Cartesian,2],"
				+ "AXIS[\"Easting (E)\",east,ORDER[1]],"
				+ "AXIS[\"Northing (N)\",north,ORDER[2]],"
				+ "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",\"3395\"]]";

		crs = CRSReader.read(text, true);

		expectedText = text.replace("6378137", "6378137.0")
				.replace(",0,", ",0.0,").replace(",1,", ",1.0,")
				.replace("\"9804\"", "9804").replace("\"19833\"", "19833")
				.replace("\"3395\"", "3395");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test EPSG 3855
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test3855() throws IOException {

		String text = "VERTCRS[\"EGM2008 height\","
				+ "VDATUM[\"EGM2008 geoid\",ID[\"EPSG\",1027]],"
				+ "CS[vertical,1,ID[\"EPSG\",6499]],"
				+ "AXIS[\"Gravity-related height (H)\",up],"
				+ "LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],"
				+ "ID[\"EPSG\",3855]]";

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("\"metre\",1,", "\"metre\",1.0,");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "VERT_CS[\"EGM2008 geoid height\","
				+ "VERT_DATUM[\"EGM2008 geoid\",2005,"
				+ "AUTHORITY[\"EPSG\",\"1027\"],"
				+ "EXTENSION[\"PROJ4_GRIDS\",\"egm08_25.gtx\"]],"
				+ "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
				+ "AXIS[\"Up\",UP],AUTHORITY[\"EPSG\",\"3855\"]]";

		crs = CRSReader.read(text, true);

		expectedText = "VERTCRS[\"EGM2008 geoid height\","
				+ "VDATUM[\"EGM2008 geoid\",ID[\"EPSG\",1027]],"
				+ "CS[vertical,1],AXIS[\"Up\",up],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],"
				+ "ID[\"EPSG\",3855],"
				+ "REMARK[\"[\"\"datumType\"\",\"\"2005.0\"\"],[\"\"PROJ4_GRIDS\"\",\"\"egm08_25.gtx\"\"]\"]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		assertEquals(
				"[\"datumType\",\"2005.0\"],[\"PROJ4_GRIDS\",\"egm08_25.gtx\"]",
				crs.getRemark());
		Map<String, String> extras = CRSReader.readExtras(crs.getRemark());
		assertEquals(2, extras.size());
		assertEquals("2005.0", extras.get(WKTConstants.DATUM_TYPE));
		assertEquals("egm08_25.gtx", extras.get("PROJ4_GRIDS"));
		assertEquals(crs.getRemark(), CRSReader.writeExtras(extras));

		text = "VERTCRS[\"EGM2008 geoid height\","
				+ "VDATUM[\"EGM2008 geoid\",ANCHOR[\"WGS 84 ellipsoid\"]],"
				+ "CS[vertical,1],"
				+ "AXIS[\"Gravity-related height (H)\",up],LENGTHUNIT[\"metre\",1.0],"
				+ "ID[\"EPSG\",\"3855\"]]";

		crs = CRSReader.read(text, true);

		expectedText = text.replace("\"3855\"", "3855");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

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

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace("ENSEMBLEACCURACY[2]", "ENSEMBLEACCURACY[2.0]")
				.replace(",0,", ",0.0,").replace(",1,", ",1.0,");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"WGS 84 / Pseudo-Mercator\",GEOGCS[\"WGS 84\","
				+ "DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS 84\",6378137,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4326\"]],"
				+ "PROJECTION[\"Mercator_1SP\"],"
				+ "PARAMETER[\"central_meridian\",0],"
				+ "PARAMETER[\"scale_factor\",1],"
				+ "PARAMETER[\"false_easting\",0],"
				+ "PARAMETER[\"false_northing\",0],"
				+ "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
				+ "AXIS[\"X\",EAST],AXIS[\"Y\",NORTH],"
				+ "EXTENSION[\"PROJ4\",\"+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext  +no_defs\"],"
				+ "AUTHORITY[\"EPSG\",\"3857\"]]";

		crs = CRSReader.read(text, true);

		expectedText = "PROJCRS[\"WGS 84 / Pseudo-Mercator\",BASEGEOGCRS[\"WGS 84\","
				+ "DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,ID[\"EPSG\",7030]],"
				+ "ID[\"EPSG\",6326]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Mercator_1SP\",METHOD[\"Mercator_1SP\"],"
				+ "PARAMETER[\"central_meridian\",0.0],"
				+ "PARAMETER[\"scale_factor\",1.0],"
				+ "PARAMETER[\"false_easting\",0.0],"
				+ "PARAMETER[\"false_northing\",0.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"X\",east],AXIS[\"Y\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],ID[\"EPSG\",3857],"
				+ "REMARK[\"[\"\"PROJ4\"\",\"\"+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext  +no_defs\"\"]\"]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

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

		crs = CRSReader.read(text, true);

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
				+ "PARAMETER[\"false_northing\",0.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"X\",east],AXIS[\"Y\",north]"
				+ ",UNIT[\"metre\",1.0,ID[\"EPSG\",9001]]"
				+ ",ID[\"EPSG\",3857]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test EPSG 3978
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test3978() throws IOException {

		String text = "PROJCRS[\"NAD83 / Canada Atlas Lambert\",BASEGEOGCRS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,ID[\"EPSG\",7019]],"
				+ "ID[\"EPSG\",6269]],ID[\"EPSG\",4269]],"
				+ "CONVERSION[\"Canada Atlas Lambert\",METHOD[\"Lambert Conic Conformal (2SP)\",ID[\"EPSG\",9802]],"
				+ "PARAMETER[\"Latitude of false origin\",49,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Longitude of false origin\",-95,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Latitude of 1st standard parallel\",49,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Latitude of 2nd standard parallel\",77,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Easting at false origin\",0,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "PARAMETER[\"Northing at false origin\",0,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "ID[\"EPSG\",3977]],CS[Cartesian,2,ID[\"EPSG\",4400]],"
				+ "AXIS[\"Easting (E)\",east],AXIS[\"Northing (N)\",north],"
				+ "LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",3978]]";

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace(",49,", ",49.0,").replace(",-95,", ",-95.0,")
				.replace(",77,", ",77.0,").replace(",0,", ",0.0,")
				.replace(",1,", ",1.0,");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"NAD83 / Canada Atlas Lambert\",GEOGCS[\"NAD83\","
				+ "DATUM[\"North_American_Datum_1983\","
				+ "SPHEROID[\"GRS 1980\",6378137,298.257222101,AUTHORITY[\"EPSG\",\"7019\"]],"
				+ "TOWGS84[0,0,0,0,0,0,0],AUTHORITY[\"EPSG\",\"6269\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4269\"]],"
				+ "PROJECTION[\"Lambert_Conformal_Conic_2SP\"],"
				+ "PARAMETER[\"standard_parallel_1\",49],"
				+ "PARAMETER[\"standard_parallel_2\",77],"
				+ "PARAMETER[\"latitude_of_origin\",49],"
				+ "PARAMETER[\"central_meridian\",-95],"
				+ "PARAMETER[\"false_easting\",0],"
				+ "PARAMETER[\"false_northing\",0],"
				+ "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
				+ "AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH],"
				+ "AUTHORITY[\"EPSG\",\"3978\"]]";

		crs = CRSReader.read(text);

		expectedText = "PROJCRS[\"NAD83 / Canada Atlas Lambert\",BASEGEOGCRS[\"NAD83\","
				+ "DATUM[\"North_American_Datum_1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,ID[\"EPSG\",7019]],"
				+ "ID[\"EPSG\",6269]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4269]],"
				+ "CONVERSION[\"Lambert_Conformal_Conic_2SP\",METHOD[\"Lambert_Conformal_Conic_2SP\"],"
				+ "PARAMETER[\"standard_parallel_1\",49.0],"
				+ "PARAMETER[\"standard_parallel_2\",77.0],"
				+ "PARAMETER[\"latitude_of_origin\",49.0],"
				+ "PARAMETER[\"central_meridian\",-95.0],"
				+ "PARAMETER[\"false_easting\",0.0],"
				+ "PARAMETER[\"false_northing\",0.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"Easting\",east],AXIS[\"Northing\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],"
				+ "ID[\"EPSG\",3978]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"NAD83 / Canada Atlas Lambert\",GEOGCRS[\"NAD83\","
				+ "DATUM[\"North_American_Datum_1983\","
				+ "SPHEROID[\"GRS 1980\",6378137,298.257222101,"
				+ "ID[\"EPSG\",\"7019\"]],"
				+ "ABRIDGEDTRANSFORMATION[0,0,0,0,0,0,0],"
				+ "ID[\"EPSG\",\"6269\"]],"
				+ "PRIMEM[\"Greenwich\",0,ID[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",\"9122\"]],"
				+ "ID[\"EPSG\",\"4269\"]],"
				+ "PROJECTION[\"Lambert_Conformal_Conic_2SP\"],"
				+ "PARAMETER[\"standard_parallel_1\",49],"
				+ "PARAMETER[\"standard_parallel_2\",77],"
				+ "PARAMETER[\"latitude_of_origin\",49],"
				+ "PARAMETER[\"central_meridian\",-95],"
				+ "PARAMETER[\"false_easting\",0],"
				+ "PARAMETER[\"false_northing\",0],"
				+ "UNIT[\"metre\",1,ID[\"EPSG\",\"9001\"]],"
				+ "AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH],"
				+ "ID[\"EPSG\",\"3978\"]]";

		crs = CRSReader.read(text);

		expectedText = "PROJCRS[\"NAD83 / Canada Atlas Lambert\",BASEGEOGCRS[\"NAD83\","
				+ "DATUM[\"North_American_Datum_1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,ID[\"EPSG\",7019]],"
				+ "ID[\"EPSG\",6269]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9122]],"
				+ "ID[\"EPSG\",4269]],"
				+ "CONVERSION[\"Lambert_Conformal_Conic_2SP\",METHOD[\"Lambert_Conformal_Conic_2SP\"],"
				+ "PARAMETER[\"standard_parallel_1\",49.0],"
				+ "PARAMETER[\"standard_parallel_2\",77.0],"
				+ "PARAMETER[\"latitude_of_origin\",49.0],"
				+ "PARAMETER[\"central_meridian\",-95.0],"
				+ "PARAMETER[\"false_easting\",0.0],"
				+ "PARAMETER[\"false_northing\",0.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"Easting\",east],AXIS[\"Northing\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],"
				+ "ID[\"EPSG\",3978]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

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

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace("ENSEMBLEACCURACY[2]", "ENSEMBLEACCURACY[2.0]");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS 84\",6378137,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4326\"]]";

		crs = CRSReader.read(text, true);

		expectedText = "GEOGCRS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,"
				+ "ID[\"EPSG\",7030]],ID[\"EPSG\",6326]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "CS[ellipsoidal,2],"
				+ "AXIS[\"Lon\",east],AXIS[\"Lat\",north],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4326]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "GEOGCS[\"WGS 84\"," + "DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS84\",6378137,298.257223563]],"
				+ "PRIMEM[\"Greenwich\",0],"
				+ "UNIT[\"degree\",0.0174532925199433]]";

		crs = CRSReader.read(text, true);

		expectedText = "GEOGCRS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS84\",6378137.0,298.257223563]],"
				+ "PRIMEM[\"Greenwich\",0.0],"
				+ "CS[ellipsoidal,2],AXIS[\"Lon\",east],AXIS[\"Lat\",north],"
				+ "UNIT[\"degree\",0.0174532925199433]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

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

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace("ENSEMBLEACCURACY[2]", "ENSEMBLEACCURACY[2.0]")
				.replace(",1,", ",1.0,");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

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

		crs = CRSReader.read(text, true);

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
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "GEODCRS[\"WGS 84\",DATUM[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,"
				+ "LENGTHUNIT[\"metre\",1.0]]],CS[ellipsoidal,3],"
				+ "AXIS[\"Geodetic latitude (Lat)\",north,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"Geodetic longitude (Long)\",east,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"Ellipsoidal height (h)\",up,"
				+ "LENGTHUNIT[\"metre\",1.0]],ID[\"EPSG\",4979]]";

		crs = CRSReader.read(text, true);

		expectedText = text.replaceAll("6378137", "6378137.0");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test EPSG 5041
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test5041() throws IOException {

		String text = "PROJCRS[\"WGS 84 / UPS North (E,N)\",BASEGEOGCRS[\"WGS 84\","
				+ "ENSEMBLE[\"World Geodetic System 1984 ensemble\","
				+ "MEMBER[\"World Geodetic System 1984 (Transit)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"World Geodetic System 1984 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"World Geodetic System 1984 (G873)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,ID[\"EPSG\",7030]],"
				+ "ENSEMBLEACCURACY[2],ID[\"EPSG\",6326]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Universal Polar Stereographic North\","
				+ "METHOD[\"Polar Stereographic (variant A)\",ID[\"EPSG\",9810]],"
				+ "PARAMETER[\"Latitude of natural origin\",90,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Longitude of natural origin\",0,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.994,SCALEUNIT[\"unity\",1,ID[\"EPSG\",9201]]],"
				+ "PARAMETER[\"False easting\",2000000,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "PARAMETER[\"False northing\",2000000,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "ID[\"EPSG\",16061]],CS[Cartesian,2,ID[\"EPSG\",1026]],"
				+ "AXIS[\"Easting (E)\",South,MERIDIAN[90.0,ANGLEUNIT[\"degree\",0.0174532925199433]]],"
				+ "AXIS[\"Northing (N)\",South,MERIDIAN[180.0,ANGLEUNIT[\"degree\",0.0174532925199433]]],"
				+ "LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",5041]]";

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace("ENSEMBLEACCURACY[2]", "ENSEMBLEACCURACY[2.0]")
				.replace(",0,", ",0.0,").replace(",1,", ",1.0,")
				.replace(",90,", ",90.0,").replace(",2000000,", ",2000000.0,")
				.replace("South", "south");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"WGS 84 / UPS North (E,N)\"," + "GEOGCS[\"WGS 84\","
				+ "DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS 84\",6378137,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4326\"]],"
				+ "PROJECTION[\"Polar_Stereographic\"],"
				+ "PARAMETER[\"latitude_of_origin\",90],"
				+ "PARAMETER[\"central_meridian\",0],"
				+ "PARAMETER[\"scale_factor\",0.994],"
				+ "PARAMETER[\"false_easting\",2000000],"
				+ "PARAMETER[\"false_northing\",2000000]," + "UNIT[\"metre\",1,"
				+ "AUTHORITY[\"EPSG\",\"9001\"]],"
				+ "AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH],"
				+ "AUTHORITY[\"EPSG\",\"5041\"]]";

		crs = CRSReader.read(text, true);

		expectedText = "PROJCRS[\"WGS 84 / UPS North (E,N)\",BASEGEOGCRS[\"WGS 84\","
				+ "DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,"
				+ "ID[\"EPSG\",7030]],ID[\"EPSG\",6326]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9122]],"
				+ "ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Polar_Stereographic\",METHOD[\"Polar_Stereographic\"],"
				+ "PARAMETER[\"latitude_of_origin\",90.0],PARAMETER[\"central_meridian\",0.0],"
				+ "PARAMETER[\"scale_factor\",0.994],PARAMETER[\"false_easting\",2000000.0],"
				+ "PARAMETER[\"false_northing\",2000000.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"Easting\",east],AXIS[\"Northing\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],"
				+ "ID[\"EPSG\",5041]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCRS[\"WGS 84 / UPS North (E,N)\","
				+ "BASEGEODCRS[\"WGS 84\","
				+ "DATUM[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,"
				+ "LENGTHUNIT[\"metre\",1.0]]]],"
				+ "CONVERSION[\"Universal Polar Stereographic North\","
				+ "METHOD[\"Polar Stereographic (variant A)\",ID[\"EPSG\",\"9810\"]],"
				+ "PARAMETER[\"Latitude of natural origin\",90,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Longitude of natural origin\",0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.994,"
				+ "SCALEUNIT[\"unity\",1.0]],"
				+ "PARAMETER[\"False easting\",2000000,"
				+ "LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"False northing\",2000000,"
				+ "LENGTHUNIT[\"metre\",1.0]],ID[\"EPSG\",\"16061\"]],"
				+ "CS[Cartesian,2],AXIS[\"Easting (E)\",south,"
				+ "MERIDIAN[90,ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "ORDER[1]],AXIS[\"Northing (N)\",south,"
				+ "MERIDIAN[180,ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "ORDER[2]],LENGTHUNIT[\"metre\",1.0],"
				+ "ID[\"EPSG\",\"5041\"]]";

		crs = CRSReader.read(text, true);

		expectedText = text.replace("6378137", "6378137.0")
				.replace("\"9810\"", "9810").replace(",90,", ",90.0,")
				.replace(",0,", ",0.0,").replace(",2000000,", ",2000000.0,")
				.replace("\"16061\"", "16061").replace("[90,", "[90.0,")
				.replace("[180,", "[180.0,").replace("\"5041\"", "5041");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test EPSG 5042
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test5042() throws IOException {

		String text = "PROJCRS[\"WGS 84 / UPS South (E,N)\",BASEGEOGCRS[\"WGS 84\","
				+ "ENSEMBLE[\"World Geodetic System 1984 ensemble\","
				+ "MEMBER[\"World Geodetic System 1984 (Transit)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"World Geodetic System 1984 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"World Geodetic System 1984 (G873)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,ID[\"EPSG\",7030]],"
				+ "ENSEMBLEACCURACY[2],ID[\"EPSG\",6326]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Universal Polar Stereographic South\","
				+ "METHOD[\"Polar Stereographic (variant A)\",ID[\"EPSG\",9810]],"
				+ "PARAMETER[\"Latitude of natural origin\",-90,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Longitude of natural origin\",0,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.994,SCALEUNIT[\"unity\",1,ID[\"EPSG\",9201]]],"
				+ "PARAMETER[\"False easting\",2000000,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "PARAMETER[\"False northing\",2000000,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "ID[\"EPSG\",16161]],CS[Cartesian,2,ID[\"EPSG\",1027]],"
				+ "AXIS[\"Easting (E)\",North,MERIDIAN[90.0,ANGLEUNIT[\"degree\",0.0174532925199433]]],"
				+ "AXIS[\"Northing (N)\",North,MERIDIAN[0.0,ANGLEUNIT[\"degree\",0.0174532925199433]]],"
				+ "LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",5042]]";

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace("ENSEMBLEACCURACY[2]", "ENSEMBLEACCURACY[2.0]")
				.replace(",0,", ",0.0,").replace(",1,", ",1.0,")
				.replace(",-90,", ",-90.0,").replace(",2000000,", ",2000000.0,")
				.replace(",North", ",north");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"WGS 84 / UPS South (E,N)\","
				+ "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS 84\",6378137,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4326\"]],"
				+ "PROJECTION[\"Polar_Stereographic\"],"
				+ "PARAMETER[\"latitude_of_origin\",-90],"
				+ "PARAMETER[\"central_meridian\",0],"
				+ "PARAMETER[\"scale_factor\",0.994],"
				+ "PARAMETER[\"false_easting\",2000000],"
				+ "PARAMETER[\"false_northing\",2000000],"
				+ "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
				+ "AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH],"
				+ "AUTHORITY[\"EPSG\",\"5042\"]]";

		crs = CRSReader.read(text, true);

		expectedText = "PROJCRS[\"WGS 84 / UPS South (E,N)\","
				+ "BASEGEOGCRS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,"
				+ "ID[\"EPSG\",7030]],ID[\"EPSG\",6326]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.0174532925199433,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Polar_Stereographic\",METHOD[\"Polar_Stereographic\"],"
				+ "PARAMETER[\"latitude_of_origin\",-90.0],"
				+ "PARAMETER[\"central_meridian\",0.0],"
				+ "PARAMETER[\"scale_factor\",0.994],"
				+ "PARAMETER[\"false_easting\",2000000.0],"
				+ "PARAMETER[\"false_northing\",2000000.0]],"
				+ "CS[ellipsoidal,2],"
				+ "AXIS[\"Easting\",east],AXIS[\"Northing\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],ID[\"EPSG\",5042]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCRS[\"WGS 84 / UPS South (E,N)\","
				+ "BASEGEODCRS[\"WGS 84\","
				+ "DATUM[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,"
				+ "LENGTHUNIT[\"metre\",1.0]]]],"
				+ "CONVERSION[\"Universal Polar Stereographic North\","
				+ "METHOD[\"Polar Stereographic (variant A)\",ID[\"EPSG\",\"9810\"]],"
				+ "PARAMETER[\"Latitude of natural origin\",-90,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Longitude of natural origin\",0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.994,"
				+ "SCALEUNIT[\"unity\",1.0]],"
				+ "PARAMETER[\"False easting\",2000000,"
				+ "LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"False northing\",2000000,"
				+ "LENGTHUNIT[\"metre\",1.0]],ID[\"EPSG\",\"16161\"]],"
				+ "CS[Cartesian,2],AXIS[\"Easting (E)\",north,"
				+ "MERIDIAN[90,ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "ORDER[1]],AXIS[\"Northing (N)\",north,"
				+ "MERIDIAN[0,ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "ORDER[2]],LENGTHUNIT[\"metre\",1.0],"
				+ "ID[\"EPSG\",\"5042\"]]";

		crs = CRSReader.read(text, true);

		expectedText = text.replace("6378137", "6378137.0")
				.replace("\"9810\"", "9810").replace(",-90,", ",-90.0,")
				.replace(",0,", ",0.0,").replace(",2000000,", ",2000000.0,")
				.replace("\"16161\"", "16161").replace("[90,", "[90.0,")
				.replace("[0,", "[0.0,").replace("\"5042\"", "5042");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test EPSG 9801
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test9801() throws IOException {

		String text = "PROJCS[\"Lambert_Conformal_Conic (1SP)\","
				+ "GEODCRS[\"GCS_North_American_1983\","
				+ "DATUM[\"North_American_Datum_1983\","
				+ "SPHEROID[\"GRS_1980\",6371000,0]],"
				+ "PRIMEM[\"Greenwich\",0],"
				+ "UNIT[\"Degree\",0.017453292519943295]],"
				+ "PROJECTION[\"Lambert_Conformal_Conic_1SP\"],"
				+ "PARAMETER[\"latitude_of_origin\",25],"
				+ "PARAMETER[\"central_meridian\",-95],"
				+ "PARAMETER[\"scale_factor\",1],"
				+ "PARAMETER[\"false_easting\",0],"
				+ "PARAMETER[\"false_northing\",0],"
				+ "PARAMETER[\"standard_parallel_1\",25],"
				+ "UNIT[\"Meter\",1],AUTHORITY[\"EPSG\",\"9801\"]]";

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = "PROJCRS[\"Lambert_Conformal_Conic (1SP)\","
				+ "BASEGEODCRS[\"GCS_North_American_1983\","
				+ "DATUM[\"North_American_Datum_1983\","
				+ "ELLIPSOID[\"GRS_1980\",6371000.0,0.0]],"
				+ "PRIMEM[\"Greenwich\",0.0],"
				+ "UNIT[\"Degree\",0.017453292519943295]],"
				+ "CONVERSION[\"Lambert_Conformal_Conic_1SP\",METHOD[\"Lambert_Conformal_Conic_1SP\"],"
				+ "PARAMETER[\"latitude_of_origin\",25.0],"
				+ "PARAMETER[\"central_meridian\",-95.0],"
				+ "PARAMETER[\"scale_factor\",1.0],"
				+ "PARAMETER[\"false_easting\",0.0],"
				+ "PARAMETER[\"false_northing\",0.0],"
				+ "PARAMETER[\"standard_parallel_1\",25.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"X\",east],AXIS[\"Y\",north],"
				+ "UNIT[\"Meter\",1.0],ID[\"EPSG\",9801]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test EPSG 9802
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test9802() throws IOException {

		String text = "PROJCS[\"Lambert Conic Conformal (2SP)\","
				+ "GEODCRS[\"GCS_North_American_1983\","
				+ "DATUM[\"North_American_Datum_1983\","
				+ "SPHEROID[\"GRS_1980\",6378160,298.2539162964695]],"
				+ "PRIMEM[\"Greenwich\",0],"
				+ "UNIT[\"degree\",0.0174532925199433]],"
				+ "PROJECTION[\"Lambert_Conformal_Conic_2SP\"],"
				+ "PARAMETER[\"standard_parallel_1\",30],"
				+ "PARAMETER[\"standard_parallel_2\",60],"
				+ "PARAMETER[\"latitude_of_origin\",30],"
				+ "PARAMETER[\"central_meridian\",126],"
				+ "PARAMETER[\"false_easting\",0],"
				+ "PARAMETER[\"false_northing\",0],"
				+ "AUTHORITY[\"EPSG\",\"9802\"]]";

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = "PROJCRS[\"Lambert Conic Conformal (2SP)\","
				+ "BASEGEODCRS[\"GCS_North_American_1983\","
				+ "DATUM[\"North_American_Datum_1983\","
				+ "ELLIPSOID[\"GRS_1980\",6378160.0,298.2539162964695]],"
				+ "PRIMEM[\"Greenwich\",0.0],"
				+ "UNIT[\"degree\",0.0174532925199433]],"
				+ "CONVERSION[\"Lambert_Conformal_Conic_2SP\",METHOD[\"Lambert_Conformal_Conic_2SP\"],"
				+ "PARAMETER[\"standard_parallel_1\",30.0],"
				+ "PARAMETER[\"standard_parallel_2\",60.0],"
				+ "PARAMETER[\"latitude_of_origin\",30.0],"
				+ "PARAMETER[\"central_meridian\",126.0],"
				+ "PARAMETER[\"false_easting\",0.0],"
				+ "PARAMETER[\"false_northing\",0.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"X\",east],AXIS[\"Y\",north],"
				+ "ID[\"EPSG\",9802]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test EPSG 32660
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void test32660() throws IOException {

		String text = "PROJCRS[\"WGS 84 / UTM zone 60N\",BASEGEOGCRS[\"WGS 84\","
				+ "ENSEMBLE[\"World Geodetic System 1984 ensemble\","
				+ "MEMBER[\"World Geodetic System 1984 (Transit)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"World Geodetic System 1984 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"World Geodetic System 1984 (G873)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,ID[\"EPSG\",7030]],"
				+ "ENSEMBLEACCURACY[2],ID[\"EPSG\",6326]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"UTM zone 60N\",METHOD[\"Transverse Mercator\",ID[\"EPSG\",9807]],"
				+ "PARAMETER[\"Latitude of natural origin\",0,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Longitude of natural origin\",177,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.9996,SCALEUNIT[\"unity\",1,ID[\"EPSG\",9201]]],"
				+ "PARAMETER[\"False easting\",500000,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],"
				+ "PARAMETER[\"False northing\",0,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],ID[\"EPSG\",16060]],"
				+ "CS[Cartesian,2,ID[\"EPSG\",4400]],AXIS[\"Easting (E)\",east],AXIS[\"Northing (N)\",north],"
				+ "LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",32660]]";

		CoordinateReferenceSystem crs = CRSReader.read(text, true);

		String expectedText = text.replace("6378137", "6378137.0")
				.replace("ENSEMBLEACCURACY[2]", "ENSEMBLEACCURACY[2.0]")
				.replace(",0,", ",0.0,").replace(",1,", ",1.0,")
				.replace(",177,", ",177.0,").replace(",500000,", ",500000.0,");

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"WGS 84 / UTM zone 60N\",GEOGCS[\"WGS 84\","
				+ "DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS 84\",6378137,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4326\"]],"
				+ "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
				+ "PROJECTION[\"Transverse_Mercator\"],"
				+ "PARAMETER[\"latitude_of_origin\",0],"
				+ "PARAMETER[\"central_meridian\",177],"
				+ "PARAMETER[\"scale_factor\",0.9996],"
				+ "PARAMETER[\"false_easting\",500000],"
				+ "PARAMETER[\"false_northing\",0],"
				+ "AUTHORITY[\"EPSG\",\"32660\"],"
				+ "AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH]]";

		crs = CRSReader.read(text, true);

		expectedText = "PROJCRS[\"WGS 84 / UTM zone 60N\",BASEGEOGCRS[\"WGS 84\","
				+ "DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,"
				+ "ID[\"EPSG\",7030]],ID[\"EPSG\",6326]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "ID[\"EPSG\",9122]],ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Transverse_Mercator\",METHOD[\"Transverse_Mercator\"],"
				+ "PARAMETER[\"latitude_of_origin\",0.0],"
				+ "PARAMETER[\"central_meridian\",177.0],"
				+ "PARAMETER[\"scale_factor\",0.9996],"
				+ "PARAMETER[\"false_easting\",500000.0],"
				+ "PARAMETER[\"false_northing\",0.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"Easting\",east],AXIS[\"Northing\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],"
				+ "ID[\"EPSG\",32660]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "PROJCS[\"WGS 84 / UTM zone 60N\",GEOGCRS[\"WGS 84\","
				+ "DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS84\",6378137,298.257223563,"
				+ "ID[\"EPSG\",\"7030\"]],ID[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,ID[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",\"9122\"]],"
				+ "ID[\"EPSG\",\"4326\"]],"
				+ "PROJECTION[\"Transverse_Mercator\"],"
				+ "PARAMETER[\"latitude_of_origin\",0],"
				+ "PARAMETER[\"central_meridian\",177],"
				+ "PARAMETER[\"scale_factor\",0.9996],"
				+ "PARAMETER[\"false_easting\",500000],"
				+ "PARAMETER[\"false_northing\",0],"
				+ "UNIT[\"metre\",1,ID[\"EPSG\",\"9001\"]],"
				+ "AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH],"
				+ "ID[\"EPSG\",\"32660\"]]";

		crs = CRSReader.read(text, true);

		expectedText = "PROJCRS[\"WGS 84 / UTM zone 60N\",BASEGEOGCRS[\"WGS 84\","
				+ "DATUM[\"WGS_1984\","
				+ "ELLIPSOID[\"WGS84\",6378137.0,298.257223563,"
				+ "ID[\"EPSG\",7030]],ID[\"EPSG\",6326]],"
				+ "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",8901]],"
				+ "UNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9122]],"
				+ "ID[\"EPSG\",4326]],"
				+ "CONVERSION[\"Transverse_Mercator\",METHOD[\"Transverse_Mercator\"],"
				+ "PARAMETER[\"latitude_of_origin\",0.0],"
				+ "PARAMETER[\"central_meridian\",177.0],"
				+ "PARAMETER[\"scale_factor\",0.9996],"
				+ "PARAMETER[\"false_easting\",500000.0],"
				+ "PARAMETER[\"false_northing\",0.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"Easting\",east],AXIS[\"Northing\",north],"
				+ "UNIT[\"metre\",1.0,ID[\"EPSG\",9001]],"
				+ "ID[\"EPSG\",32660]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

}
