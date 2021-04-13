package mil.nga.proj.crs.wkt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import mil.nga.proj.crs.Axis;
import mil.nga.proj.crs.AxisDirectionType;
import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateSystem;
import mil.nga.proj.crs.CoordinateSystemType;
import mil.nga.proj.crs.DatumEnsemble;
import mil.nga.proj.crs.Dynamic;
import mil.nga.proj.crs.Ellipsoid;
import mil.nga.proj.crs.Extent;
import mil.nga.proj.crs.GeodeticCoordinateReferenceSystem;
import mil.nga.proj.crs.GeodeticDatumEnsemble;
import mil.nga.proj.crs.GeodeticReferenceFrame;
import mil.nga.proj.crs.GeographicBoundingBox;
import mil.nga.proj.crs.Identifier;
import mil.nga.proj.crs.PrimeMeridian;
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

		CoordinateReferenceSystem crs = CRSReader.readCRS(text);
		assertNotNull(crs);
		// TODO

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

	/**
	 * Test identifier
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testIdentifier() throws IOException {

		CRSReader reader = new CRSReader(
				"ID[\"Authority name\",\"Abcd_Ef\",7.1]");
		Identifier identifier = reader.readIdentifier();
		assertNotNull(identifier);
		assertEquals("Authority name", identifier.getName());
		assertEquals("Abcd_Ef", identifier.getUniqueIdentifier());
		assertEquals("7.1", identifier.getVersion());
		reader.close();

		reader = new CRSReader("ID[\"EPSG\",4326]");
		identifier = reader.readIdentifier();
		assertNotNull(identifier);
		assertEquals("EPSG", identifier.getName());
		assertEquals("4326", identifier.getUniqueIdentifier());
		reader.close();

		reader = new CRSReader(
				"ID[\"EPSG\",4326,URI[\"urn:ogc:def:crs:EPSG::4326\"]]");
		identifier = reader.readIdentifier();
		assertNotNull(identifier);
		assertEquals("EPSG", identifier.getName());
		assertEquals("4326", identifier.getUniqueIdentifier());
		assertEquals("urn:ogc:def:crs:EPSG::4326", identifier.getUri());
		reader.close();

		reader = new CRSReader(
				"ID[\"EuroGeographics\",\"ES_ED50 (BAL99) to ETRS89\",\"2001-04-20\"]");
		identifier = reader.readIdentifier();
		assertNotNull(identifier);
		assertEquals("EuroGeographics", identifier.getName());
		assertEquals("ES_ED50 (BAL99) to ETRS89",
				identifier.getUniqueIdentifier());
		assertEquals("2001-04-20", identifier.getVersion());
		reader.close();

	}

	/**
	 * Test remark
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testRemark() throws IOException {

		CRSReader reader = new CRSReader("REMARK[\"A remark in ASCII\"]");
		assertEquals("A remark in ASCII", reader.readRemark());
		reader.close();

		reader = new CRSReader("REMARK[\"Замечание на русском языке\"]");
		assertEquals("Замечание на русском языке", reader.readRemark());
		reader.close();

		CoordinateReferenceSystem crs = CRSReader.readCRS("GEOGCRS[\"S-95\","
				+ "DATUM[\"Pulkovo 1995\","
				+ "ELLIPSOID[\"Krassowsky 1940\",6378245,298.3,"
				+ "LENGTHUNIT[\"metre\",1.0]]],CS[ellipsoidal,2],"
				+ "AXIS[\"latitude\",north,ORDER[1]],"
				+ "AXIS[\"longitude\",east,ORDER[2]],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],"
				+ "REMARK[\"Система Геодеэических Координвт года 1995(СК-95)\"]"
				+ "]");
		assertEquals("Система Геодеэических Координвт года 1995(СК-95)",
				crs.getRemark());

	}

	/**
	 * Test length unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testLengthUnit() throws IOException {

		CRSReader reader = new CRSReader("LENGTHUNIT[\"metre\",1]");
		Unit unit = reader.readLengthUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("LENGTHUNIT[\"metre\",1]");
		unit = reader.readUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader(
				"LENGTHUNIT[\"German legal metre\",1.0000135965]");
		unit = reader.readLengthUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("German legal metre", unit.getName());
		assertEquals(1.0000135965, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader(
				"LENGTHUNIT[\"German legal metre\",1.0000135965]");
		unit = reader.readUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("German legal metre", unit.getName());
		assertEquals(1.0000135965, unit.getConversionFactor(), 0);
		reader.close();

	}

	/**
	 * Test angle unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testAngleUnit() throws IOException {

		CRSReader reader = new CRSReader(
				"ANGLEUNIT[\"degree\",0.0174532925199433]");
		Unit unit = reader.readAngleUnit();
		assertEquals(UnitType.ANGLEUNIT, unit.getType());
		assertEquals("degree", unit.getName());
		assertEquals(0.0174532925199433, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("ANGLEUNIT[\"degree\",0.0174532925199433]");
		unit = reader.readUnit();
		assertEquals(UnitType.ANGLEUNIT, unit.getType());
		assertEquals("degree", unit.getName());
		assertEquals(0.0174532925199433, unit.getConversionFactor(), 0);
		reader.close();

	}

	/**
	 * Test scale unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testScaleUnit() throws IOException {

		CRSReader reader = new CRSReader(
				"SCALEUNIT[\"parts per million\",1E-06]");
		Unit unit = reader.readScaleUnit();
		assertEquals(UnitType.SCALEUNIT, unit.getType());
		assertEquals("parts per million", unit.getName());
		assertEquals(1E-06, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("SCALEUNIT[\"parts per million\",1E-06]");
		unit = reader.readUnit();
		assertEquals(UnitType.SCALEUNIT, unit.getType());
		assertEquals("parts per million", unit.getName());
		assertEquals(1E-06, unit.getConversionFactor(), 0);
		reader.close();

	}

	/**
	 * Test parametric unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testParametricUnit() throws IOException {

		CRSReader reader = new CRSReader("PARAMETRICUNIT[\"hectopascal\",100]");
		Unit unit = reader.readParametricUnit();
		assertEquals(UnitType.PARAMETRICUNIT, unit.getType());
		assertEquals("hectopascal", unit.getName());
		assertEquals(100, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("PARAMETRICUNIT[\"hectopascal\",100]");
		unit = reader.readUnit();
		assertEquals(UnitType.PARAMETRICUNIT, unit.getType());
		assertEquals("hectopascal", unit.getName());
		assertEquals(100, unit.getConversionFactor(), 0);
		reader.close();

	}

	/**
	 * Test time unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testTimeUnit() throws IOException {

		CRSReader reader = new CRSReader("TIMEUNIT[\"millisecond\",0.001]");
		Unit unit = reader.readTimeUnit();
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("millisecond", unit.getName());
		assertEquals(0.001, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("TIMEUNIT[\"millisecond\",0.001]");
		unit = reader.readUnit();
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("millisecond", unit.getName());
		assertEquals(0.001, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("TIMEUNIT[\"calendar month\"]");
		unit = reader.readTimeUnit();
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("calendar month", unit.getName());
		reader.close();

		reader = new CRSReader("TIMEUNIT[\"calendar month\"]");
		unit = reader.readUnit();
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("calendar month", unit.getName());
		reader.close();

		reader = new CRSReader("TIMEUNIT[\"calendar second\"]");
		unit = reader.readTimeUnit();
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("calendar second", unit.getName());
		reader.close();

		reader = new CRSReader("TIMEUNIT[\"calendar second\"]");
		unit = reader.readUnit();
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("calendar second", unit.getName());
		reader.close();

		reader = new CRSReader("TIMEUNIT[\"day\",86400.0]");
		unit = reader.readTimeUnit();
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("day", unit.getName());
		assertEquals(86400.0, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("TIMEUNIT[\"day\",86400.0]");
		unit = reader.readUnit();
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("day", unit.getName());
		assertEquals(86400.0, unit.getConversionFactor(), 0);
		reader.close();

	}

	/**
	 * Test geodetic coordinate system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeodeticCoordinateSystem() throws IOException {

		CRSReader reader = new CRSReader(
				"CS[Cartesian,3],AXIS[\"(X)\",geocentricX],"
						+ "AXIS[\"(Y)\",geocentricY],AXIS[\"(Z)\",geocentricZ],"
						+ "LENGTHUNIT[\"metre\",1.0]");
		CoordinateSystem coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.CARTESIAN,
				coordinateSystem.getType());
		assertEquals(3, coordinateSystem.getDimension());
		List<Axis> axes = coordinateSystem.getAxes();
		assertEquals(3, axes.size());
		assertEquals("X", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.GEOCENTRIC_X,
				axes.get(0).getDirection());
		assertEquals("Y", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.GEOCENTRIC_Y,
				axes.get(1).getDirection());
		assertEquals("Z", axes.get(2).getAbbreviation());
		assertEquals(AxisDirectionType.GEOCENTRIC_Z,
				axes.get(2).getDirection());
		Unit unit = coordinateSystem.getUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1.0, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("CS[Cartesian,3],"
				+ "AXIS[\"(X)\",east],AXIS[\"(Y)\",north],AXIS[\"(Z)\",up],"
				+ "LENGTHUNIT[\"metre\",1.0]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.CARTESIAN,
				coordinateSystem.getType());
		assertEquals(3, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(3, axes.size());
		assertEquals("X", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.EAST, axes.get(0).getDirection());
		assertEquals("Y", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, axes.get(1).getDirection());
		assertEquals("Z", axes.get(2).getAbbreviation());
		assertEquals(AxisDirectionType.UP, axes.get(2).getDirection());
		unit = coordinateSystem.getUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1.0, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("CS[spherical,3],"
				+ "AXIS[\"distance (r)\",awayFrom,ORDER[1],LENGTHUNIT[\"kilometre\",1000]],"
				+ "AXIS[\"longitude (U)\",counterClockwise,BEARING[0],ORDER[2],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"elevation (V)\",up,ORDER[3],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.SPHERICAL,
				coordinateSystem.getType());
		assertEquals(3, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(3, axes.size());
		assertEquals("distance", axes.get(0).getName());
		assertEquals("r", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.AWAY_FROM, axes.get(0).getDirection());
		assertEquals(1, axes.get(0).getOrder().intValue());
		assertEquals(UnitType.LENGTHUNIT, axes.get(0).getUnit().getType());
		assertEquals("kilometre", axes.get(0).getUnit().getName());
		assertEquals(1000, axes.get(0).getUnit().getConversionFactor(), 0);
		assertEquals("longitude", axes.get(1).getName());
		assertEquals("U", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.COUNTER_CLOCKWISE,
				axes.get(1).getDirection());
		assertEquals(2, axes.get(1).getOrder().intValue());
		assertEquals(UnitType.ANGLEUNIT, axes.get(1).getUnit().getType());
		assertEquals("degree", axes.get(1).getUnit().getName());
		assertEquals(0.0174532925199433,
				axes.get(1).getUnit().getConversionFactor(), 0);
		assertEquals("elevation", axes.get(2).getName());
		assertEquals("V", axes.get(2).getAbbreviation());
		assertEquals(AxisDirectionType.UP, axes.get(2).getDirection());
		assertEquals(3, axes.get(2).getOrder().intValue());
		assertEquals(UnitType.ANGLEUNIT, axes.get(2).getUnit().getType());
		assertEquals("degree", axes.get(2).getUnit().getName());
		assertEquals(0.0174532925199433,
				axes.get(2).getUnit().getConversionFactor(), 0);
		reader.close();

	}

	/**
	 * Test geographic coordinate system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeographicCoordinateSystem() throws IOException {

		CRSReader reader = new CRSReader("CS[ellipsoidal,3],"
				+ "AXIS[\"latitude\",north,ORDER[1],ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"longitude\",east,ORDER[2],ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"ellipsoidal height (h)\",up,ORDER[3],LENGTHUNIT[\"metre\",1.0]]");
		CoordinateSystem coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				coordinateSystem.getType());
		assertEquals(3, coordinateSystem.getDimension());
		List<Axis> axes = coordinateSystem.getAxes();
		assertEquals(3, axes.size());
		assertEquals("latitude", axes.get(0).getName());
		assertEquals(AxisDirectionType.NORTH, axes.get(0).getDirection());
		assertEquals(1, axes.get(0).getOrder().intValue());
		assertEquals(UnitType.ANGLEUNIT, axes.get(0).getUnit().getType());
		assertEquals("degree", axes.get(0).getUnit().getName());
		assertEquals(0.0174532925199433,
				axes.get(0).getUnit().getConversionFactor(), 0);
		assertEquals("longitude", axes.get(1).getName());
		assertEquals(AxisDirectionType.EAST, axes.get(1).getDirection());
		assertEquals(2, axes.get(1).getOrder().intValue());
		assertEquals(UnitType.ANGLEUNIT, axes.get(1).getUnit().getType());
		assertEquals("degree", axes.get(1).getUnit().getName());
		assertEquals(0.0174532925199433,
				axes.get(1).getUnit().getConversionFactor(), 0);
		assertEquals("ellipsoidal height", axes.get(2).getName());
		assertEquals("h", axes.get(2).getAbbreviation());
		assertEquals(AxisDirectionType.UP, axes.get(2).getDirection());
		assertEquals(3, axes.get(2).getOrder().intValue());
		assertEquals(UnitType.LENGTHUNIT, axes.get(2).getUnit().getType());
		assertEquals("metre", axes.get(2).getUnit().getName());
		assertEquals(1.0, axes.get(2).getUnit().getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("CS[ellipsoidal,2],AXIS[\"(lat)\",north],"
				+ "AXIS[\"(lon)\",east],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				coordinateSystem.getType());
		assertEquals(2, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(2, axes.size());
		assertEquals("lat", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, axes.get(0).getDirection());
		assertEquals("lon", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.EAST, axes.get(1).getDirection());
		Unit unit = coordinateSystem.getUnit();
		assertEquals(UnitType.ANGLEUNIT, unit.getType());
		assertEquals("degree", unit.getName());
		assertEquals(0.0174532925199433, unit.getConversionFactor(), 0);
		reader.close();

	}

	/**
	 * Test projected coordinate system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testProjectedCoordinateSystem() throws IOException {

		CRSReader reader = new CRSReader("CS[Cartesian,2],"
				+ "AXIS[\"(E)\",east,ORDER[1],LENGTHUNIT[\"metre\",1.0]],"
				+ "AXIS[\"(N)\",north,ORDER[2],LENGTHUNIT[\"metre\",1.0]]");
		CoordinateSystem coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.CARTESIAN,
				coordinateSystem.getType());
		assertEquals(2, coordinateSystem.getDimension());
		List<Axis> axes = coordinateSystem.getAxes();
		assertEquals(2, axes.size());
		assertEquals("E", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.EAST, axes.get(0).getDirection());
		assertEquals(1, axes.get(0).getOrder().intValue());
		assertEquals(UnitType.LENGTHUNIT, axes.get(0).getUnit().getType());
		assertEquals("metre", axes.get(0).getUnit().getName());
		assertEquals(1.0, axes.get(0).getUnit().getConversionFactor(), 0);
		assertEquals("N", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, axes.get(1).getDirection());
		assertEquals(2, axes.get(1).getOrder().intValue());
		assertEquals(UnitType.LENGTHUNIT, axes.get(1).getUnit().getType());
		assertEquals("metre", axes.get(1).getUnit().getName());
		assertEquals(1.0, axes.get(1).getUnit().getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("CS[Cartesian,2],AXIS[\"(E)\",east],"
				+ "AXIS[\"(N)\",north],LENGTHUNIT[\"metre\",1.0]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.CARTESIAN,
				coordinateSystem.getType());
		assertEquals(2, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(2, axes.size());
		assertEquals("E", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.EAST, axes.get(0).getDirection());
		assertEquals("N", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, axes.get(1).getDirection());
		Unit unit = coordinateSystem.getUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1.0, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader(
				"CS[Cartesian,2],AXIS[\"northing (X)\",north,ORDER[1]],"
						+ "AXIS[\"easting (Y)\",east,ORDER[2]],"
						+ "LENGTHUNIT[\"German legal metre\",1.0000135965]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.CARTESIAN,
				coordinateSystem.getType());
		assertEquals(2, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(2, axes.size());
		assertEquals("northing", axes.get(0).getName());
		assertEquals("X", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, axes.get(0).getDirection());
		assertEquals(1, axes.get(0).getOrder().intValue());
		assertEquals("easting", axes.get(1).getName());
		assertEquals("Y", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.EAST, axes.get(1).getDirection());
		assertEquals(2, axes.get(1).getOrder().intValue());
		unit = coordinateSystem.getUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("German legal metre", unit.getName());
		assertEquals(1.0000135965, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("CS[Cartesian,2],"
				+ "AXIS[\"easting (X)\",south,"
				+ "MERIDIAN[90,ANGLEUNIT[\"degree\",0.0174532925199433]],ORDER[1]"
				+ "],AXIS[\"northing (Y)\",south,"
				+ "MERIDIAN[180,ANGLEUNIT[\"degree\",0.0174532925199433]],ORDER[2]"
				+ "],LENGTHUNIT[\"metre\",1.0]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.CARTESIAN,
				coordinateSystem.getType());
		assertEquals(2, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(2, axes.size());
		assertEquals("easting", axes.get(0).getName());
		assertEquals("X", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.SOUTH, axes.get(0).getDirection());
		assertEquals(90, axes.get(0).getMeridian(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				axes.get(0).getMeridianAngleUnit().getType());
		assertEquals("degree", axes.get(0).getMeridianAngleUnit().getName());
		assertEquals(0.0174532925199433,
				axes.get(0).getMeridianAngleUnit().getConversionFactor(), 0);
		assertEquals(1, axes.get(0).getOrder().intValue());
		assertEquals("northing", axes.get(1).getName());
		assertEquals("Y", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.SOUTH, axes.get(1).getDirection());
		assertEquals(180, axes.get(1).getMeridian(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				axes.get(1).getMeridianAngleUnit().getType());
		assertEquals("degree", axes.get(1).getMeridianAngleUnit().getName());
		assertEquals(0.0174532925199433,
				axes.get(1).getMeridianAngleUnit().getConversionFactor(), 0);
		assertEquals(2, axes.get(1).getOrder().intValue());
		unit = coordinateSystem.getUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1.0, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("CS[Cartesian,3],AXIS[\"(E)\",east],"
				+ "AXIS[\"(N)\",north],AXIS[\"ellipsoid height (h)\",up],"
				+ "LENGTHUNIT[\"metre\",1.0]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.CARTESIAN,
				coordinateSystem.getType());
		assertEquals(3, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(3, axes.size());
		assertEquals("E", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.EAST, axes.get(0).getDirection());
		assertEquals("N", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, axes.get(1).getDirection());
		assertEquals("ellipsoid height", axes.get(2).getName());
		assertEquals("h", axes.get(2).getAbbreviation());
		assertEquals(AxisDirectionType.UP, axes.get(2).getDirection());
		unit = coordinateSystem.getUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1.0, unit.getConversionFactor(), 0);
		reader.close();

	}

	/**
	 * Test vertical coordinate system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testVerticalCoordinateSystem() throws IOException {

		CRSReader reader = new CRSReader(
				"CS[vertical,1],AXIS[\"gravity-related height (H)\",up],"
						+ "LENGTHUNIT[\"metre\",1.0]");
		CoordinateSystem coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.VERTICAL, coordinateSystem.getType());
		assertEquals(1, coordinateSystem.getDimension());
		List<Axis> axes = coordinateSystem.getAxes();
		assertEquals(1, axes.size());
		assertEquals("gravity-related height", axes.get(0).getName());
		assertEquals("H", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.UP, axes.get(0).getDirection());
		Unit unit = coordinateSystem.getUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1.0, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("CS[vertical,1],AXIS[\"depth (D)\",down,"
				+ "LENGTHUNIT[\"metre\",1.0]]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.VERTICAL, coordinateSystem.getType());
		assertEquals(1, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(1, axes.size());
		assertEquals("depth", axes.get(0).getName());
		assertEquals("D", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.DOWN, axes.get(0).getDirection());
		assertEquals(UnitType.LENGTHUNIT, axes.get(0).getUnit().getType());
		assertEquals("metre", axes.get(0).getUnit().getName());
		assertEquals(1.0, axes.get(0).getUnit().getConversionFactor(), 0);
		reader.close();

	}

	/**
	 * Test engineering coordinate system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testEngineeringCoordinateSystem() throws IOException {

		CRSReader reader = new CRSReader("CS[Cartesian,2],"
				+ "AXIS[\"site north (x)\",southeast,ORDER[1]],"
				+ "AXIS[\"site east (y)\",southwest,ORDER[2]],"
				+ "LENGTHUNIT[\"metre\",1.0]");
		CoordinateSystem coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.CARTESIAN,
				coordinateSystem.getType());
		assertEquals(2, coordinateSystem.getDimension());
		List<Axis> axes = coordinateSystem.getAxes();
		assertEquals(2, axes.size());
		assertEquals("site north", axes.get(0).getName());
		assertEquals("x", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.SOUTH_EAST, axes.get(0).getDirection());
		assertEquals(1, axes.get(0).getOrder().intValue());
		assertEquals("site east", axes.get(1).getName());
		assertEquals("y", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.SOUTH_WEST, axes.get(1).getDirection());
		assertEquals(2, axes.get(1).getOrder().intValue());
		Unit unit = coordinateSystem.getUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1.0, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("CS[polar,2],"
				+ "AXIS[\"distance (r)\",awayFrom,ORDER[1],LENGTHUNIT[\"metre\",1.0]],"
				+ "AXIS[\"bearing (U)\",clockwise,BEARING[234],ORDER[2],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.POLAR, coordinateSystem.getType());
		assertEquals(2, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(2, axes.size());
		assertEquals("distance", axes.get(0).getName());
		assertEquals("r", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.AWAY_FROM, axes.get(0).getDirection());
		assertEquals(1, axes.get(0).getOrder().intValue());
		assertEquals(UnitType.LENGTHUNIT, axes.get(0).getUnit().getType());
		assertEquals("metre", axes.get(0).getUnit().getName());
		assertEquals(1.0, axes.get(0).getUnit().getConversionFactor(), 0);
		assertEquals("bearing", axes.get(1).getName());
		assertEquals("U", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.CLOCKWISE, axes.get(1).getDirection());
		assertEquals(234, axes.get(1).getBearing(), 0);
		assertEquals(2, axes.get(1).getOrder().intValue());
		assertEquals(UnitType.ANGLEUNIT, axes.get(1).getUnit().getType());
		assertEquals("degree", axes.get(1).getUnit().getName());
		assertEquals(0.0174532925199433,
				axes.get(1).getUnit().getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader(
				"CS[Cartesian,3],AXIS[\"ahead (x)\",forward,ORDER[1]],"
						+ "AXIS[\"right (y)\",starboard,ORDER[2]],"
						+ "AXIS[\"down (z)\",down,ORDER[3]],"
						+ "LENGTHUNIT[\"metre\",1.0]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.CARTESIAN,
				coordinateSystem.getType());
		assertEquals(3, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(3, axes.size());
		assertEquals("ahead", axes.get(0).getName());
		assertEquals("x", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.FORWARD, axes.get(0).getDirection());
		assertEquals(1, axes.get(0).getOrder().intValue());
		assertEquals("right", axes.get(1).getName());
		assertEquals("y", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.STARBOARD, axes.get(1).getDirection());
		assertEquals(2, axes.get(1).getOrder().intValue());
		assertEquals("down", axes.get(2).getName());
		assertEquals("z", axes.get(2).getAbbreviation());
		assertEquals(AxisDirectionType.DOWN, axes.get(2).getDirection());
		assertEquals(3, axes.get(2).getOrder().intValue());
		unit = coordinateSystem.getUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1.0, unit.getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader(
				"CS[ordinal,2],AXIS[\"Inline (I)\",northEast,ORDER[1]],"
						+ "AXIS[\"Crossline (J)\",northwest,ORDER[2]]");
		coordinateSystem = reader.readCoordinateSystem();
		assertEquals(CoordinateSystemType.ORDINAL, coordinateSystem.getType());
		assertEquals(2, coordinateSystem.getDimension());
		axes = coordinateSystem.getAxes();
		assertEquals(2, axes.size());
		assertEquals("Inline", axes.get(0).getName());
		assertEquals("I", axes.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH_EAST, axes.get(0).getDirection());
		assertEquals(1, axes.get(0).getOrder().intValue());
		assertEquals("Crossline", axes.get(1).getName());
		assertEquals("J", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH_WEST, axes.get(1).getDirection());
		assertEquals(2, axes.get(1).getOrder().intValue());
		reader.close();

	}

	/**
	 * Test geodetic datum ensemble
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeodeticDatumEnsemble() throws IOException {

		CRSReader reader = new CRSReader("ENSEMBLE[\"WGS 84 ensemble\","
				+ "MEMBER[\"WGS 84 (TRANSIT)\"],MEMBER[\"WGS 84 (G730)\"],"
				+ "MEMBER[\"WGS 84 (G834)\"],MEMBER[\"WGS 84 (G1150)\"],"
				+ "MEMBER[\"WGS 84 (G1674)\"],MEMBER[\"WGS 84 (G1762)\"],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.2572236,LENGTHUNIT[\"metre\",1.0]],"
				+ "ENSEMBLEACCURACY[2]]");
		GeodeticDatumEnsemble datumEnsemble = reader
				.readGeodeticDatumEnsemble();
		assertNotNull(datumEnsemble);
		assertEquals("WGS 84 ensemble", datumEnsemble.getName());
		assertEquals(6, datumEnsemble.getMembers().size());
		assertEquals("WGS 84 (TRANSIT)",
				datumEnsemble.getMembers().get(0).getName());
		assertEquals("WGS 84 (G730)",
				datumEnsemble.getMembers().get(1).getName());
		assertEquals("WGS 84 (G834)",
				datumEnsemble.getMembers().get(2).getName());
		assertEquals("WGS 84 (G1150)",
				datumEnsemble.getMembers().get(3).getName());
		assertEquals("WGS 84 (G1674)",
				datumEnsemble.getMembers().get(4).getName());
		assertEquals("WGS 84 (G1762)",
				datumEnsemble.getMembers().get(5).getName());
		assertEquals("WGS 84", datumEnsemble.getEllipsoid().getName());
		assertEquals(6378137, datumEnsemble.getEllipsoid().getSemiMajorAxis(),
				0);
		assertEquals(298.2572236,
				datumEnsemble.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				datumEnsemble.getEllipsoid().getLengthUnit().getType());
		assertEquals("metre",
				datumEnsemble.getEllipsoid().getLengthUnit().getName());
		assertEquals(1.0, datumEnsemble.getEllipsoid().getLengthUnit()
				.getConversionFactor(), 0);
		assertEquals(2, datumEnsemble.getAccuracy(), 0);
		reader.close();

		reader = new CRSReader("ENSEMBLE[\"WGS 84 ensemble\","
				+ "MEMBER[\"WGS 84 (TRANSIT)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"WGS 84 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"WGS 84 (G834)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"WGS 84 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"WGS 84 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"WGS 84 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.2572236,LENGTHUNIT[\"metre\",1.0]],"
				+ "ENSEMBLEACCURACY[2]]");
		datumEnsemble = reader.readGeodeticDatumEnsemble();
		assertNotNull(datumEnsemble);
		assertEquals("WGS 84 ensemble", datumEnsemble.getName());
		assertEquals(6, datumEnsemble.getMembers().size());
		assertEquals("WGS 84 (TRANSIT)",
				datumEnsemble.getMembers().get(0).getName());
		assertEquals("EPSG", datumEnsemble.getMembers().get(0).getIdentifiers()
				.get(0).getName());
		assertEquals("1166", datumEnsemble.getMembers().get(0).getIdentifiers()
				.get(0).getUniqueIdentifier());
		assertEquals("WGS 84 (G730)",
				datumEnsemble.getMembers().get(1).getName());
		assertEquals("EPSG", datumEnsemble.getMembers().get(1).getIdentifiers()
				.get(0).getName());
		assertEquals("1152", datumEnsemble.getMembers().get(1).getIdentifiers()
				.get(0).getUniqueIdentifier());
		assertEquals("WGS 84 (G834)",
				datumEnsemble.getMembers().get(2).getName());
		assertEquals("EPSG", datumEnsemble.getMembers().get(2).getIdentifiers()
				.get(0).getName());
		assertEquals("1153", datumEnsemble.getMembers().get(2).getIdentifiers()
				.get(0).getUniqueIdentifier());
		assertEquals("WGS 84 (G1150)",
				datumEnsemble.getMembers().get(3).getName());
		assertEquals("EPSG", datumEnsemble.getMembers().get(3).getIdentifiers()
				.get(0).getName());
		assertEquals("1154", datumEnsemble.getMembers().get(3).getIdentifiers()
				.get(0).getUniqueIdentifier());
		assertEquals("WGS 84 (G1674)",
				datumEnsemble.getMembers().get(4).getName());
		assertEquals("EPSG", datumEnsemble.getMembers().get(4).getIdentifiers()
				.get(0).getName());
		assertEquals("1155", datumEnsemble.getMembers().get(4).getIdentifiers()
				.get(0).getUniqueIdentifier());
		assertEquals("WGS 84 (G1762)",
				datumEnsemble.getMembers().get(5).getName());
		assertEquals("EPSG", datumEnsemble.getMembers().get(5).getIdentifiers()
				.get(0).getName());
		assertEquals("1156", datumEnsemble.getMembers().get(5).getIdentifiers()
				.get(0).getUniqueIdentifier());
		assertEquals("WGS 84", datumEnsemble.getEllipsoid().getName());
		assertEquals(6378137, datumEnsemble.getEllipsoid().getSemiMajorAxis(),
				0);
		assertEquals(298.2572236,
				datumEnsemble.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				datumEnsemble.getEllipsoid().getLengthUnit().getType());
		assertEquals("metre",
				datumEnsemble.getEllipsoid().getLengthUnit().getName());
		assertEquals(1.0, datumEnsemble.getEllipsoid().getLengthUnit()
				.getConversionFactor(), 0);
		assertEquals(2, datumEnsemble.getAccuracy(), 0);
		reader.close();

	}

	/**
	 * Test vertical datum ensemble
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testVerticalDatumEnsemble() throws IOException {

		CRSReader reader = new CRSReader("ENSEMBLE[\"EVRS ensemble\","
				+ "MEMBER[\"EVRF2000\"],MEMBER[\"EVRF2007\"],"
				+ "ENSEMBLEACCURACY[0.01]]");
		DatumEnsemble datumEnsemble = reader.readVerticalDatumEnsemble();
		assertNotNull(datumEnsemble);
		assertEquals("EVRS ensemble", datumEnsemble.getName());
		assertEquals(2, datumEnsemble.getMembers().size());
		assertEquals("EVRF2000", datumEnsemble.getMembers().get(0).getName());
		assertEquals("EVRF2007", datumEnsemble.getMembers().get(1).getName());
		assertEquals(0.01, datumEnsemble.getAccuracy(), 0);
		reader.close();

	}

	/**
	 * Test dynamic
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testDynamic() throws IOException {

		CRSReader reader = new CRSReader("DYNAMIC[FRAMEEPOCH[2010.0]]");
		Dynamic dynamic = reader.readDynamic();
		assertEquals(2010.0, dynamic.getReferenceEpoch(), 0);
		reader.close();

		reader = new CRSReader(
				"DYNAMIC[FRAMEEPOCH[2010.0],MODEL[\"NAD83(CSRS)v6 velocity grid\"]]");
		dynamic = reader.readDynamic();
		assertEquals(2010.0, dynamic.getReferenceEpoch(), 0);
		assertEquals("NAD83(CSRS)v6 velocity grid",
				dynamic.getDeformationModelName());
		reader.close();

	}

	/**
	 * Test ellipsoid
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testEllipsoid() throws IOException {

		CRSReader reader = new CRSReader(
				"ELLIPSOID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]");
		Ellipsoid ellipsoid = reader.readEllipsoid();
		assertEquals("GRS 1980", ellipsoid.getName());
		assertEquals(6378137, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getLengthUnit().getType());
		assertEquals("metre", ellipsoid.getLengthUnit().getName());
		assertEquals(1.0, ellipsoid.getLengthUnit().getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader(
				"SPHEROID[\"GRS 1980\",6378137.0,298.257222101]");
		ellipsoid = reader.readEllipsoid();
		assertEquals("GRS 1980", ellipsoid.getName());
		assertEquals(6378137, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, ellipsoid.getInverseFlattening(), 0);
		reader.close();

		reader = new CRSReader(
				"ELLIPSOID[\"Clark 1866\",20925832.164,294.97869821,"
						+ "LENGTHUNIT[\"US survey foot\",0.304800609601219]]");
		ellipsoid = reader.readEllipsoid();
		assertEquals("Clark 1866", ellipsoid.getName());
		assertEquals(20925832.164, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(294.97869821, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getLengthUnit().getType());
		assertEquals("US survey foot", ellipsoid.getLengthUnit().getName());
		assertEquals(0.304800609601219,
				ellipsoid.getLengthUnit().getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader(
				"ELLIPSOID[\"Sphere\",6371000,0,LENGTHUNIT[\"metre\",1.0]]");
		ellipsoid = reader.readEllipsoid();
		assertEquals("Sphere", ellipsoid.getName());
		assertEquals(6371000, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(0, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getLengthUnit().getType());
		assertEquals("metre", ellipsoid.getLengthUnit().getName());
		assertEquals(1.0, ellipsoid.getLengthUnit().getConversionFactor(), 0);
		reader.close();

	}

	/**
	 * Test prime meridian
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testPrimeMeridian() throws IOException {

		CRSReader reader = new CRSReader(
				"PRIMEM[\"Paris\",2.5969213,ANGLEUNIT[\"grad\",0.015707963267949]]");
		PrimeMeridian primeMeridian = reader.readPrimeMeridian();
		assertEquals("Paris", primeMeridian.getName());
		assertEquals(2.5969213, primeMeridian.getIrmLongitude(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				primeMeridian.getIrmLongitudeAngleUnit().getType());
		assertEquals("grad",
				primeMeridian.getIrmLongitudeAngleUnit().getName());
		assertEquals(0.015707963267949,
				primeMeridian.getIrmLongitudeAngleUnit().getConversionFactor(),
				0);
		reader.close();

		reader = new CRSReader("PRIMEM[\"Ferro\",-17.6666667]");
		primeMeridian = reader.readPrimeMeridian();
		assertEquals("Ferro", primeMeridian.getName());
		assertEquals(-17.6666667, primeMeridian.getIrmLongitude(), 0);
		reader.close();

		reader = new CRSReader(
				"PRIMEM[\"Greenwich\",0.0, ANGLEUNIT[\"degree\",0.0174532925199433]]");
		primeMeridian = reader.readPrimeMeridian();
		assertEquals("Greenwich", primeMeridian.getName());
		assertEquals(0.0, primeMeridian.getIrmLongitude(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				primeMeridian.getIrmLongitudeAngleUnit().getType());
		assertEquals("degree",
				primeMeridian.getIrmLongitudeAngleUnit().getName());
		assertEquals(0.0174532925199433,
				primeMeridian.getIrmLongitudeAngleUnit().getConversionFactor(),
				0);
		reader.close();

	}

	/**
	 * Test geodetic reference frame
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeodeticReferenceFrame() throws IOException {

		CRSReader reader = new CRSReader("DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]]");
		GeodeticReferenceFrame geodeticReferenceFrame = reader
				.readGeodeticReferenceFrame();
		assertEquals("North American Datum 1983",
				geodeticReferenceFrame.getName());
		Ellipsoid ellipsoid = geodeticReferenceFrame.getEllipsoid();
		assertEquals("GRS 1980", ellipsoid.getName());
		assertEquals(6378137, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getLengthUnit().getType());
		assertEquals("metre", ellipsoid.getLengthUnit().getName());
		assertEquals(1.0, ellipsoid.getLengthUnit().getConversionFactor(), 0);
		reader.close();

		reader = new CRSReader("TRF[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378388.0,298.257223563,LENGTHUNIT[\"metre\",1.0]]"
				+ "],PRIMEM[\"Greenwich\",0.0]");
		geodeticReferenceFrame = reader.readGeodeticReferenceFrame();
		assertEquals("World Geodetic System 1984",
				geodeticReferenceFrame.getName());
		ellipsoid = geodeticReferenceFrame.getEllipsoid();
		assertEquals("WGS 84", ellipsoid.getName());
		assertEquals(6378388.0, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(298.257223563, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getLengthUnit().getType());
		assertEquals("metre", ellipsoid.getLengthUnit().getName());
		assertEquals(1.0, ellipsoid.getLengthUnit().getConversionFactor(), 0);
		assertEquals("Greenwich",
				geodeticReferenceFrame.getPrimeMeridian().getName());
		assertEquals(0.0,
				geodeticReferenceFrame.getPrimeMeridian().getIrmLongitude(), 0);
		reader.close();

		reader = new CRSReader("GEODETICDATUM[\"Tananarive 1925\","
				+ "ELLIPSOID[\"International 1924\",6378388.0,297.0,LENGTHUNIT[\"metre\",1.0]],"
				+ "ANCHOR[\"Tananarive observatory:21.0191667gS, 50.23849537gE of Paris\"]],"
				+ "PRIMEM[\"Paris\",2.5969213,ANGLEUNIT[\"grad\",0.015707963267949]]");
		geodeticReferenceFrame = reader.readGeodeticReferenceFrame();
		assertEquals("Tananarive 1925", geodeticReferenceFrame.getName());
		ellipsoid = geodeticReferenceFrame.getEllipsoid();
		assertEquals("International 1924", ellipsoid.getName());
		assertEquals(6378388.0, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(297.0, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getLengthUnit().getType());
		assertEquals("metre", ellipsoid.getLengthUnit().getName());
		assertEquals(1.0, ellipsoid.getLengthUnit().getConversionFactor(), 0);
		assertEquals(
				"Tananarive observatory:21.0191667gS, 50.23849537gE of Paris",
				geodeticReferenceFrame.getAnchor());
		assertEquals("Paris",
				geodeticReferenceFrame.getPrimeMeridian().getName());
		assertEquals(2.5969213,
				geodeticReferenceFrame.getPrimeMeridian().getIrmLongitude(), 0);
		assertEquals(UnitType.ANGLEUNIT, geodeticReferenceFrame
				.getPrimeMeridian().getIrmLongitudeAngleUnit().getType());
		assertEquals("grad", geodeticReferenceFrame.getPrimeMeridian()
				.getIrmLongitudeAngleUnit().getName());
		assertEquals(0.015707963267949,
				geodeticReferenceFrame.getPrimeMeridian()
						.getIrmLongitudeAngleUnit().getConversionFactor(),
				0);
		reader.close();

	}

	/**
	 * Test geodetic coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeodeticCoordinateReferenceSystem() throws IOException {

		String text = "GEODCRS[\"JGD2000\","
				+ "DATUM[\"Japanese Geodetic Datum 2000\","
				+ "ELLIPSOID[\"GRS 1980\",6378137,298.257222101]],"
				+ "CS[Cartesian,3],AXIS[\"(X)\",geocentricX],"
				+ "AXIS[\"(Y)\",geocentricY],AXIS[\"(Z)\",geocentricZ],"
				+ "LENGTHUNIT[\"metre\",1.0],"
				+ "USAGE[SCOPE[\"Geodesy, topographic mapping and cadastre\"],"
				+ "AREA[\"Japan\"],BBOX[17.09,122.38,46.05,157.64],"
				+ "TIMEEXTENT[2002-04-01,2011-10-21]],"
				+ "ID[\"EPSG\",4946,URI[\"urn:ogc:def:crs:EPSG::4946\"]],"
				+ "REMARK[\"注：JGD2000ジオセントリックは現在JGD2011に代わりました。\"]]";

		CoordinateReferenceSystem crs = CRSReader.readCRS(text);
		GeodeticCoordinateReferenceSystem geodeticCrs = CRSReader
				.readGeodeticOrGeographic(text);
		// assertEquals(crs, geodeticCrs); TODO
		assertFalse(geodeticCrs.isGeographic());
		assertEquals("JGD2000", geodeticCrs.getName());
		assertEquals("Japanese Geodetic Datum 2000",
				geodeticCrs.getGeodeticReferenceFrame().getName());
		assertEquals("GRS 1980", geodeticCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getName());
		assertEquals(6378137, geodeticCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(298.257222101, geodeticCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(CoordinateSystemType.CARTESIAN,
				geodeticCrs.getCoordinateSystem().getType());
		assertEquals(3, geodeticCrs.getCoordinateSystem().getDimension());
		assertEquals("X", geodeticCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.GEOCENTRIC_X, geodeticCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals("Y", geodeticCrs.getCoordinateSystem().getAxes().get(1)
				.getAbbreviation());
		assertEquals(AxisDirectionType.GEOCENTRIC_Y, geodeticCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals("Z", geodeticCrs.getCoordinateSystem().getAxes().get(2)
				.getAbbreviation());
		assertEquals(AxisDirectionType.GEOCENTRIC_Z, geodeticCrs
				.getCoordinateSystem().getAxes().get(2).getDirection());
		assertEquals(UnitType.LENGTHUNIT,
				geodeticCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("metre",
				geodeticCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1.0, geodeticCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		assertEquals("Geodesy, topographic mapping and cadastre",
				geodeticCrs.getUsages().get(0).getScope());
		assertEquals("Japan", geodeticCrs.getUsages().get(0).getExtent()
				.getAreaDescription());
		assertEquals(17.09, geodeticCrs.getUsages().get(0).getExtent()
				.getGeographicBoundingBox().getLowerLeftLatitude(), 0);
		assertEquals(122.38, geodeticCrs.getUsages().get(0).getExtent()
				.getGeographicBoundingBox().getLowerLeftLongitude(), 0);
		assertEquals(46.05, geodeticCrs.getUsages().get(0).getExtent()
				.getGeographicBoundingBox().getUpperRightLatitude(), 0);
		assertEquals(157.64,
				geodeticCrs.getUsages().get(0).getExtent()
						.getGeographicBoundingBox().getUpperRightLongitude(),
				0);
		assertEquals("2002-04-01", geodeticCrs.getUsages().get(0).getExtent()
				.getTemporalExtent().getStart());
		assertEquals("2011-10-21", geodeticCrs.getUsages().get(0).getExtent()
				.getTemporalExtent().getEnd());
		assertEquals("EPSG", geodeticCrs.getIdentifiers().get(0).getName());
		assertEquals("4946",
				geodeticCrs.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("urn:ogc:def:crs:EPSG::4946",
				geodeticCrs.getIdentifiers().get(0).getUri());
		assertEquals("注：JGD2000ジオセントリックは現在JGD2011に代わりました。",
				geodeticCrs.getRemark());

	}

	/**
	 * Test geographic coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeographicCoordinateReferenceSystem() throws IOException {

		String text = "GEOGCRS[\"WGS 84 (G1762)\","
				+ "DYNAMIC[FRAMEEPOCH[2005.0]],"
				+ "TRF[\"World Geodetic System 1984 (G1762)\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,LENGTHUNIT[\"metre\",1.0]]],"
				+ "CS[ellipsoidal,3],"
				+ "AXIS[\"(lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"(lon)\",east,ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1.0]]]";

		CoordinateReferenceSystem crs = CRSReader.readCRS(text);
		GeodeticCoordinateReferenceSystem geographicCrs = CRSReader
				.readGeodeticOrGeographic(text);
		// assertEquals(crs, geographicCrs); TODO
		assertTrue(geographicCrs.isGeographic());
		assertEquals("WGS 84 (G1762)", geographicCrs.getName());
		assertEquals(2005.0, geographicCrs.getDynamic().getReferenceEpoch(), 0);
		assertEquals("World Geodetic System 1984 (G1762)",
				geographicCrs.getGeodeticReferenceFrame().getName());
		assertEquals("WGS 84", geographicCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getName());
		assertEquals(6378137, geographicCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(298.257223563, geographicCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				geographicCrs.getGeodeticReferenceFrame().getEllipsoid()
						.getLengthUnit().getType());
		assertEquals("metre", geographicCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getLengthUnit().getName());
		assertEquals(1.0, geographicCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getLengthUnit().getConversionFactor(), 0);
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				geographicCrs.getCoordinateSystem().getType());
		assertEquals(3, geographicCrs.getCoordinateSystem().getDimension());
		assertEquals("lat", geographicCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, geographicCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(UnitType.ANGLEUNIT, geographicCrs.getCoordinateSystem()
				.getAxes().get(0).getUnit().getType());
		assertEquals("degree", geographicCrs.getCoordinateSystem().getAxes()
				.get(0).getUnit().getName());
		assertEquals(0.0174532925199433, geographicCrs.getCoordinateSystem()
				.getAxes().get(0).getUnit().getConversionFactor(), 0);
		assertEquals("lon", geographicCrs.getCoordinateSystem().getAxes().get(1)
				.getAbbreviation());
		assertEquals(AxisDirectionType.EAST, geographicCrs.getCoordinateSystem()
				.getAxes().get(1).getDirection());
		assertEquals(UnitType.ANGLEUNIT, geographicCrs.getCoordinateSystem()
				.getAxes().get(1).getUnit().getType());
		assertEquals("degree", geographicCrs.getCoordinateSystem().getAxes()
				.get(1).getUnit().getName());
		assertEquals(0.0174532925199433, geographicCrs.getCoordinateSystem()
				.getAxes().get(1).getUnit().getConversionFactor(), 0);
		assertEquals("ellipsoidal height",
				geographicCrs.getCoordinateSystem().getAxes().get(2).getName());
		assertEquals("h", geographicCrs.getCoordinateSystem().getAxes().get(2)
				.getAbbreviation());
		assertEquals(AxisDirectionType.UP, geographicCrs.getCoordinateSystem()
				.getAxes().get(2).getDirection());
		assertEquals(UnitType.LENGTHUNIT, geographicCrs.getCoordinateSystem()
				.getAxes().get(2).getUnit().getType());
		assertEquals("metre", geographicCrs.getCoordinateSystem().getAxes()
				.get(2).getUnit().getName());
		assertEquals(1.0, geographicCrs.getCoordinateSystem().getAxes().get(2)
				.getUnit().getConversionFactor(), 0);

		text = "GEOGRAPHICCRS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]],"
				+ "CS[ellipsoidal,2],AXIS[\"latitude\",north],"
				+ "AXIS[\"longitude\",east],"
				+ "ANGLEUNIT[\"degree\",0.017453292519943],"
				+ "ID[\"EPSG\",4269],REMARK[\"1986 realisation\"]]";

		crs = CRSReader.readCRS(text);
		geographicCrs = CRSReader.readGeodeticOrGeographic(text);
		// assertEquals(crs, geographicCrs); TODO
		assertTrue(geographicCrs.isGeographic());
		assertEquals("NAD83", geographicCrs.getName());
		assertEquals("North American Datum 1983",
				geographicCrs.getGeodeticReferenceFrame().getName());
		assertEquals("GRS 1980", geographicCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getName());
		assertEquals(6378137, geographicCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(298.257222101, geographicCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				geographicCrs.getGeodeticReferenceFrame().getEllipsoid()
						.getLengthUnit().getType());
		assertEquals("metre", geographicCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getLengthUnit().getName());
		assertEquals(1.0, geographicCrs.getGeodeticReferenceFrame()
				.getEllipsoid().getLengthUnit().getConversionFactor(), 0);
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				geographicCrs.getCoordinateSystem().getType());
		assertEquals(2, geographicCrs.getCoordinateSystem().getDimension());
		assertEquals("latitude",
				geographicCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals(AxisDirectionType.NORTH, geographicCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals("longitude",
				geographicCrs.getCoordinateSystem().getAxes().get(1).getName());
		assertEquals(AxisDirectionType.EAST, geographicCrs.getCoordinateSystem()
				.getAxes().get(1).getDirection());
		assertEquals(UnitType.ANGLEUNIT,
				geographicCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("degree",
				geographicCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(0.017453292519943, geographicCrs.getCoordinateSystem()
				.getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", geographicCrs.getIdentifiers().get(0).getName());
		assertEquals("4269",
				geographicCrs.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("1986 realisation", geographicCrs.getRemark());

		// TODO

	}

}
