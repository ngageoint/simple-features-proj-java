package mil.nga.proj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.ProjCoordinate;
import org.locationtech.proj4j.datum.Datum;
import org.locationtech.proj4j.datum.Ellipsoid;
import org.locationtech.proj4j.proj.LambertConformalConicProjection;
import org.locationtech.proj4j.units.Units;

import junit.framework.TestCase;
import mil.nga.sf.GeometryEnvelope;

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
	 * Test EPSG 3035
	 */
	@Test
	public void test3035() {

		final long code = 3035;

		String definition = "PROJCRS[\"ETRS89-extended / LAEA Europe\",BASEGEOGCRS[\"ETRS89\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCS[\"ETRS89 / ETRS-LAEA\",GEOGCS[\"ETRS89\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCS[\"ETRS89 / LAEA Europe\",GEOGCRS[\"ETRS89\","
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

		projectionTestDerived(code, definition);

	}

	/**
	 * Test EPSG 3395
	 */
	@Test
	public void test3395() {

		final long code = 3395;

		String definition = "PROJCRS[\"WGS 84 / World Mercator\",BASEGEOGCRS[\"WGS 84\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCS[\"WGS 84 / World Mercator\",GEOGCS[\"WGS 84\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCRS[\"WGS 84 / World Mercator\","
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

		projectionTestDerived(code, definition);

	}

	/**
	 * Test EPSG 3857
	 */
	@Test
	public void test3857() {

		final long code = 3857;

		String definition = "PROJCRS[\"WGS 84 / Pseudo-Mercator\",BASEGEOGCRS[\"WGS 84\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCS[\"WGS 84 / Pseudo-Mercator\",GEOGCS[\"WGS 84\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCS[\"WGS 84 / Pseudo-Mercator\","
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

		projectionTestDerived(code, definition);

	}

	/**
	 * Test EPSG 3978
	 */
	@Test
	public void test3978() {

		final long code = 3978;

		String definition = "PROJCRS[\"NAD83 / Canada Atlas Lambert\",BASEGEOGCRS[\"NAD83\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCS[\"NAD83 / Canada Atlas Lambert\",GEOGCS[\"NAD83\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCS[\"NAD83 / Canada Atlas Lambert\",GEOGCRS[\"NAD83\","
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

		projectionTestDerived(code, definition);

	}

	/**
	 * Test EPSG 4326
	 */
	@Test
	public void test4326() {

		final long code = 4326;

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

		projectionTestDerived(code, definition);

		definition = "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS 84\",6378137,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.01745329251994328,"
				+ "AUTHORITY[\"EPSG\",\"9122\"]],"
				+ "AUTHORITY[\"EPSG\",\"4326\"]]";

		projectionTestDerived(code, definition);

		definition = "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\","
				+ "SPHEROID[\"WGS84\",6378137,298.257223563]],"
				+ "PRIMEM[\"Greenwich\",0],"
				+ "UNIT[\"degree\",0.0174532925199433]]";

		projectionTestSpecified(code, definition);

	}

	/**
	 * Test EPSG 4979
	 */
	@Test
	public void test4979() {

		final long code = 4979;

		String definition = "GEOGCRS[\"WGS 84\",ENSEMBLE[\"World Geodetic System 1984 ensemble\","
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

		projectionTestDerived(code, definition);

		definition = "GEOGCS[\"WGS 84\","
				+ "DATUM[\"World Geodetic System 1984\","
				+ "SPHEROID[\"WGS 84\",6378137.0,298.257223563,"
				+ "AUTHORITY[\"EPSG\",\"7030\"]],"
				+ "AUTHORITY[\"EPSG\",\"6326\"]],"
				+ "PRIMEM[\"Greenwich\",0.0,AUTHORITY[\"EPSG\",\"8901\"]],"
				+ "UNIT[\"degree\",0.017453292519943295],"
				+ "AXIS[\"Geodetic latitude\",NORTH],"
				+ "AXIS[\"Geodetic longitude\",EAST],"
				+ "AXIS[\"Ellipsoidal height\",UP],"
				+ "AUTHORITY[\"EPSG\",\"4979\"]]";

		projectionTestDerived(code, definition);

		definition = "GEODCRS[\"WGS 84\",DATUM[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,"
				+ "LENGTHUNIT[\"metre\",1.0]]],CS[ellipsoidal,3],"
				+ "AXIS[\"Geodetic latitude (Lat)\",north,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"Geodetic longitude (Long)\",east,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"Ellipsoidal height (h)\",up,"
				+ "LENGTHUNIT[\"metre\",1.0]],ID[\"EPSG\",4979]]";

		projectionTestDerived(code, definition);

	}

	/**
	 * Test EPSG 5041
	 */
	@Test
	public void test5041() {

		final long code = 5041;

		String definition = "PROJCRS[\"WGS 84 / UPS North (E,N)\",BASEGEOGCRS[\"WGS 84\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCS[\"WGS 84 / UPS North (E,N)\","
				+ "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\","
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
				+ "PARAMETER[\"false_northing\",2000000],"
				+ "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
				+ "AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH],"
				+ "AUTHORITY[\"EPSG\",\"5041\"]]";

		projectionTestDerived(code, definition);

		definition = "PROJCRS[\"WGS 84 / UPS North (E,N)\","
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

		projectionTestDerived(code, definition);

	}

	/**
	 * Test EPSG 5042
	 */
	@Test
	public void test5042() {

		final long code = 5042;

		String definition = "PROJCRS[\"WGS 84 / UPS South (E,N)\",BASEGEOGCRS[\"WGS 84\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCS[\"WGS 84 / UPS South (E,N)\","
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

		projectionTestDerived(code, definition);

		definition = "PROJCRS[\"WGS 84 / UPS South (E,N)\","
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

		projectionTestDerived(code, definition);

	}

	/**
	 * Test EPSG 7405
	 */
	@Test
	public void test7405() {

		final long code = 7405;

		// TODO

	}

	/**
	 * Test EPSG 9801
	 */
	@Test
	public void test9801() {

		final long code = 9801;

		String definition = "PROJCS[\"Lambert_Conformal_Conic (1SP)\","
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

		Projection projection = ProjectionFactory
				.getProjectionByDefinition(definition);

		TestCase.assertNotNull(projection);
		TestCase.assertEquals(authority, projection.getAuthority());
		TestCase.assertEquals(Long.toString(code), projection.getCode());
		TestCase.assertEquals(definition, projection.getDefinition());
		TestCase.assertTrue(projection.getCrs()
				.getProjection() instanceof LambertConformalConicProjection);
		TestCase.assertEquals(6371000, projection.getCrs().getProjection()
				.getEllipsoid().getEquatorRadius(), 0);

	}

	/**
	 * Test EPSG 9802
	 */
	@Test
	public void test9802() {

		final long code = 9802;

		String definition = "PROJCS[\"Lambert Conic Conformal (2SP)\","
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

		Projection projection = ProjectionFactory
				.getProjectionByDefinition(definition);

		TestCase.assertNotNull(projection);
		TestCase.assertEquals(authority, projection.getAuthority());
		TestCase.assertEquals(Long.toString(code), projection.getCode());
		TestCase.assertEquals(definition, projection.getDefinition());
		TestCase.assertTrue(projection.getCrs()
				.getProjection() instanceof LambertConformalConicProjection);
		TestCase.assertEquals(6378160, projection.getCrs().getProjection()
				.getEllipsoid().getEquatorRadius(), 0);

	}

	/**
	 * Test EPSG 32660
	 */
	@Test
	public void test32660() {

		final long code = 32660;
		double delta = 0.00001;

		String definition = "PROJCRS[\"WGS 84 / UTM zone 60N\",BASEGEOGCRS[\"WGS 84\","
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

		projectionTestDerived(code, definition, delta);

		definition = "PROJCS[\"WGS 84 / UTM zone 60N\",GEOGCS[\"WGS 84\","
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

		projectionTestDerived(code, definition, delta);

		definition = "PROJCS[\"WGS 84 / UTM zone 60N\",GEOGCRS[\"WGS 84\","
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

		projectionTestDerived(code, definition, delta);

	}

	/**
	 * Test projection creation and transformations with derived authority and
	 * epsg
	 * 
	 * @param epsg
	 *            EPSG code
	 * @param definition
	 *            WKT definition
	 */
	private void projectionTestDerived(long epsg, String definition) {
		projectionTestDerived(epsg, definition, 0);
	}

	/**
	 * Test projection creation and transformations with specified authority and
	 * epsg
	 * 
	 * @param epsg
	 *            EPSG code
	 * @param definition
	 *            WKT definition
	 */
	private void projectionTestSpecified(long epsg, String definition) {
		projectionTestSpecified(epsg, definition, 0);
	}

	/**
	 * Test projection creation and transformations with derived authority and
	 * epsg
	 * 
	 * @param epsg
	 *            EPSG code
	 * @param definition
	 *            WKT definition
	 * @param delta
	 *            delta comparison
	 */
	private void projectionTestDerived(long epsg, String definition,
			double delta) {

		Projection projection = ProjectionFactory
				.getProjectionByDefinition(definition);
		projectionTest(epsg, definition, projection, delta);

	}

	/**
	 * Test projection creation and transformations with specified authority and
	 * epsg
	 * 
	 * @param epsg
	 *            EPSG code
	 * @param definition
	 *            WKT definition
	 * @param delta
	 *            delta comparison
	 */
	private void projectionTestSpecified(long epsg, String definition,
			double delta) {

		Projection projection = ProjectionFactory
				.getProjectionByDefinition(authority, epsg, definition);
		projectionTest(epsg, definition, projection, delta);

	}

	/**
	 * Test projection creation and transformations
	 * 
	 * @param epsg
	 *            EPSG code
	 * @param definition
	 *            WKT definition
	 * @param projection
	 *            projection
	 */
	private void projectionTest(long epsg, String definition,
			Projection projection, double delta) {

		TestCase.assertNotNull(projection);
		TestCase.assertEquals(authority, projection.getAuthority());
		TestCase.assertEquals(Long.toString(epsg), projection.getCode());
		TestCase.assertEquals(definition, projection.getDefinition());

		clear();

		Projection projection2 = ProjectionFactory.getProjection(epsg);

		compare(projection, projection2, delta);

		GeometryEnvelope range = new GeometryEnvelope();

		long transformCode;
		if (projection.isUnit(Units.METRES)) {
			transformCode = 4326;
			range.setMinX(-ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH);
			range.setMinY(ProjectionConstants.WEB_MERCATOR_MIN_LAT_RANGE);
			range.setMaxX(ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH);
			range.setMaxY(ProjectionConstants.WEB_MERCATOR_MAX_LAT_RANGE);
		} else {
			transformCode = 3857;
			range.setMinX(-ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH);
			range.setMinY(ProjectionConstants.WEB_MERCATOR_MIN_LAT_RANGE);
			range.setMaxX(ProjectionConstants.WGS84_HALF_WORLD_LON_WIDTH);
			range.setMaxY(ProjectionConstants.WEB_MERCATOR_MAX_LAT_RANGE);
		}

		Projection transformProjection = ProjectionFactory
				.getProjection(transformCode);

		ProjectionTransform transformTo = transformProjection
				.getTransformation(projection);
		ProjectionTransform transformTo2 = transformProjection
				.getTransformation(projection2);

		ProjectionTransform transformFrom = projection
				.getTransformation(transformProjection);
		ProjectionTransform transformFrom2 = projection2
				.getTransformation(transformProjection);

		double xRange = range.getMaxX() - range.getMinX();
		double yRange = range.getMaxY() - range.getMinY();
		double midX = range.getMinX() + (xRange / 2.0);
		double midY = range.getMinY() + (yRange / 2.0);

		coordinateTest(range.getMinX(), range.getMinY(), delta, transformTo,
				transformTo2, transformFrom, transformFrom2);
		coordinateTest(range.getMinX(), range.getMaxY(), delta, transformTo,
				transformTo2, transformFrom, transformFrom2);
		coordinateTest(range.getMaxX(), range.getMinY(), delta, transformTo,
				transformTo2, transformFrom, transformFrom2);
		coordinateTest(range.getMaxX(), range.getMaxY(), delta, transformTo,
				transformTo2, transformFrom, transformFrom2);
		coordinateTest(midX, range.getMinY(), delta, transformTo, transformTo2,
				transformFrom, transformFrom2);
		coordinateTest(midX, range.getMaxY(), delta, transformTo, transformTo2,
				transformFrom, transformFrom2);
		coordinateTest(range.getMinX(), midY, delta, transformTo, transformTo2,
				transformFrom, transformFrom2);
		coordinateTest(range.getMaxX(), midY, delta, transformTo, transformTo2,
				transformFrom, transformFrom2);
		coordinateTest(midX, midY, delta, transformTo, transformTo2,
				transformFrom, transformFrom2);

		for (int i = 0; i < 10; i++) {

			double x = range.getMinX() + (Math.random() * xRange);
			double y = range.getMinY() + (Math.random() * yRange);
			coordinateTest(x, y, delta, transformTo, transformTo2,
					transformFrom, transformFrom2);
		}

	}

	/**
	 * Test transformations of a coordinate
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param delta
	 *            delta comparison
	 * @param transformTo
	 *            transformation to
	 * @param transformTo2
	 *            transformation to 2
	 * @param transformFrom
	 *            transformation from
	 * @param transformFrom2
	 *            transformation from 2
	 */
	private void coordinateTest(double x, double y, double delta,
			ProjectionTransform transformTo, ProjectionTransform transformTo2,
			ProjectionTransform transformFrom,
			ProjectionTransform transformFrom2) {
		coordinateTest(new ProjCoordinate(x, y), delta, transformTo,
				transformTo2, transformFrom, transformFrom2);
	}

	/**
	 * Test transformation of a coordinate
	 * 
	 * @param coordinate
	 *            projection coordinate
	 * @param delta
	 *            delta comparison
	 * @param transformTo
	 *            transformation to
	 * @param transformTo2
	 *            transformation to 2
	 * @param transformFrom
	 *            transformation from
	 * @param transformFrom2
	 *            transformation from 2
	 */
	private void coordinateTest(ProjCoordinate coordinate, double delta,
			ProjectionTransform transformTo, ProjectionTransform transformTo2,
			ProjectionTransform transformFrom,
			ProjectionTransform transformFrom2) {

		ProjCoordinate coordinateTo = transformTo.transform(coordinate);
		ProjCoordinate coordinateTo2 = transformTo2.transform(coordinate);
		assertEquals(coordinateTo2.x, coordinateTo.x, delta);
		assertEquals(coordinateTo2.y, coordinateTo.y, delta);

		ProjCoordinate coordinateFrom = transformFrom.transform(coordinateTo);
		ProjCoordinate coordinateFrom2 = transformFrom2.transform(coordinateTo);
		if (delta > 0.0) {
			double difference = Math.abs(coordinateFrom2.x - coordinateFrom.x);
			assertTrue(difference <= delta
					|| Math.abs(difference - 360.0) <= delta);
		} else {
			assertEquals(coordinateFrom2.x, coordinateFrom.x, delta);
		}
		assertEquals(coordinateFrom2.y, coordinateFrom.y, delta);

	}

	/**
	 * Compare two projections
	 * 
	 * @param projection
	 *            projection
	 * @param projection2
	 *            projection 2
	 * @param delta
	 *            delta comparison
	 */
	private void compare(Projection projection, Projection projection2,
			double delta) {

		assertEquals(projection, projection2);

		CoordinateReferenceSystem crs = projection.getCrs();
		CoordinateReferenceSystem crs2 = projection2.getCrs();
		Datum datum = crs.getDatum();
		Datum datum2 = crs2.getDatum();
		Ellipsoid ellipsoid = datum.getEllipsoid();
		Ellipsoid ellipsoid2 = datum2.getEllipsoid();
		double[] transform = datum.getTransformToWGS84();
		double[] transform2 = datum2.getTransformToWGS84();
		org.locationtech.proj4j.proj.Projection proj = crs.getProjection();
		org.locationtech.proj4j.proj.Projection proj2 = crs2.getProjection();

		assertEquals(ellipsoid.getEccentricitySquared(),
				ellipsoid2.getEccentricitySquared(), 0);
		assertEquals(ellipsoid.getEquatorRadius(),
				ellipsoid2.getEquatorRadius(), 0);
		assertEquals(ellipsoid.getA(), ellipsoid2.getA(), 0);
		assertEquals(ellipsoid.getB(), ellipsoid2.getB(), 0);

		assertEquals(proj.getEllipsoid().getEccentricitySquared(),
				proj2.getEllipsoid().getEccentricitySquared(), 0);
		assertEquals(proj.getEllipsoid().getEquatorRadius(),
				proj2.getEllipsoid().getEquatorRadius(), 0);
		assertEquals(proj.getEllipsoid().getA(), proj2.getEllipsoid().getA(),
				0);
		assertEquals(proj.getEllipsoid().getB(), proj2.getEllipsoid().getB(),
				0);

		if (transform != null || transform2 != null) {
			if (transform != null && transform2 != null) {
				assertEquals(transform.length, transform2.length);
				for (int i = 0; i < transform.length; i++) {
					assertEquals(transform[i], transform2[i], 0);
				}
			} else {
				double[] transformTest = transform != null ? transform
						: transform2;
				for (int i = 0; i < transformTest.length; i++) {
					assertEquals(0, transformTest[i], 0);
				}
			}
		}

		assertEquals(proj.getAlpha(), proj2.getAlpha(), 0);
		assertEquals(proj.getAxisOrder(), proj2.getAxisOrder());
		assertEquals(proj.getEPSGCode(), proj2.getEPSGCode(), 0);
		assertEquals(proj.getEquatorRadius(), proj2.getEquatorRadius(), 0);
		assertEquals(proj.getFalseEasting(), proj2.getFalseEasting(), 0);
		assertEquals(proj.getFalseNorthing(), proj2.getFalseNorthing(), 0);
		assertEquals(proj.getFromMetres(), proj2.getFromMetres(), 0);
		assertEquals(proj.getLonC(), proj2.getLonC(), 0);
		assertEquals(proj.getMaxLatitude(), proj2.getMaxLatitude(), 0);
		assertEquals(proj.getMaxLatitudeDegrees(),
				proj2.getMaxLatitudeDegrees(), 0);
		assertEquals(proj.getMaxLongitude(), proj2.getMaxLongitude(), 0);
		assertEquals(proj.getMaxLongitudeDegrees(),
				proj2.getMaxLongitudeDegrees(), 0);
		assertEquals(proj.getMinLatitude(), proj2.getMinLatitude(), 0);
		assertEquals(proj.getMinLatitudeDegrees(),
				proj2.getMinLatitudeDegrees(), 0);
		assertEquals(proj.getMinLongitude(), proj2.getMinLongitude(), 0);
		assertEquals(proj.getMinLongitudeDegrees(),
				proj2.getMinLongitudeDegrees(), 0);
		assertEquals(proj.getPrimeMeridian(), proj2.getPrimeMeridian());
		if (delta == 0.0) {
			assertEquals(proj.getPROJ4Description().toLowerCase(),
					proj2.getPROJ4Description().toLowerCase());
		}
		assertEquals(proj.getProjectionLatitude(),
				proj2.getProjectionLatitude(), 0);
		assertEquals(proj.getProjectionLatitude1(),
				proj2.getProjectionLatitude1(), 0);
		assertEquals(proj.getProjectionLatitude1Degrees(),
				proj2.getProjectionLatitude1Degrees(), 0);
		assertEquals(proj.getProjectionLatitude2(),
				proj2.getProjectionLatitude2(), 0);
		assertEquals(proj.getProjectionLatitude2Degrees(),
				proj2.getProjectionLatitude2Degrees(), 0);
		assertEquals(proj.getProjectionLatitudeDegrees(),
				proj2.getProjectionLatitudeDegrees(), 0);
		assertEquals(proj.getProjectionLongitude(),
				proj2.getProjectionLongitude(), delta);
		assertEquals(proj.getProjectionLongitudeDegrees(),
				proj2.getProjectionLongitudeDegrees(), delta);
		assertEquals(proj.getScaleFactor(), proj2.getScaleFactor(), 0);
		assertEquals(proj.getTrueScaleLatitude(), proj2.getTrueScaleLatitude(),
				0);
		assertEquals(proj.getTrueScaleLatitudeDegrees(),
				proj2.getTrueScaleLatitudeDegrees(), 0);
		assertEquals(proj.getUnits(), proj2.getUnits());

	}

}
