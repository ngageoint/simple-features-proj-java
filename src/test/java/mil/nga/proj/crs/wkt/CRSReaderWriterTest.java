package mil.nga.proj.crs.wkt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import mil.nga.proj.crs.CRS;
import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.CompoundCoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.bound.AbridgedCoordinateTransformation;
import mil.nga.proj.crs.bound.BoundCoordinateReferenceSystem;
import mil.nga.proj.crs.common.Axis;
import mil.nga.proj.crs.common.AxisDirectionType;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.common.CoordinateSystemType;
import mil.nga.proj.crs.common.DatumEnsemble;
import mil.nga.proj.crs.common.Dynamic;
import mil.nga.proj.crs.common.Extent;
import mil.nga.proj.crs.common.GeographicBoundingBox;
import mil.nga.proj.crs.common.Identifier;
import mil.nga.proj.crs.common.TemporalExtent;
import mil.nga.proj.crs.common.Unit;
import mil.nga.proj.crs.common.UnitType;
import mil.nga.proj.crs.common.Usage;
import mil.nga.proj.crs.common.VerticalExtent;
import mil.nga.proj.crs.derived.DerivedCoordinateReferenceSystem;
import mil.nga.proj.crs.derived.DerivingConversion;
import mil.nga.proj.crs.engineering.EngineeringCoordinateReferenceSystem;
import mil.nga.proj.crs.geo.Ellipsoid;
import mil.nga.proj.crs.geo.GeoCoordinateReferenceSystem;
import mil.nga.proj.crs.geo.GeoDatumEnsemble;
import mil.nga.proj.crs.geo.GeoReferenceFrame;
import mil.nga.proj.crs.geo.PrimeMeridian;
import mil.nga.proj.crs.geo.TriaxialEllipsoid;
import mil.nga.proj.crs.metadata.CoordinateMetadata;
import mil.nga.proj.crs.operation.ConcatenatedOperation;
import mil.nga.proj.crs.operation.CoordinateOperation;
import mil.nga.proj.crs.operation.OperationParameter;
import mil.nga.proj.crs.operation.OperationParameterFile;
import mil.nga.proj.crs.operation.PointMotionOperation;
import mil.nga.proj.crs.parametric.ParametricCoordinateReferenceSystem;
import mil.nga.proj.crs.projected.MapProjection;
import mil.nga.proj.crs.projected.ProjectedCoordinateReferenceSystem;
import mil.nga.proj.crs.temporal.TemporalCoordinateReferenceSystem;
import mil.nga.proj.crs.temporal.TemporalDatum;
import mil.nga.proj.crs.vertical.VerticalCoordinateReferenceSystem;
import mil.nga.proj.crs.vertical.VerticalReferenceFrame;

/**
 * CRS Reader and Writer tests
 * 
 * @author osbornb
 */
public class CRSReaderWriterTest {

	/**
	 * Test scope
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testScope() throws IOException {

		String text = "SCOPE[\"Large scale topographic mapping and cadastre.\"]";
		CRSReader reader = new CRSReader(text);
		String scope = reader.readScope();
		assertNotNull(scope);
		assertEquals("Large scale topographic mapping and cadastre.", scope);
		reader.close();
		CRSWriter writer = new CRSWriter();
		writer.writeScope(scope);
		assertEquals(text, writer.toString());
		writer.close();

	}

	/**
	 * Test area description
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testAreaDescription() throws IOException {

		String text = "AREA[\"Netherlands offshore.\"]";
		CRSReader reader = new CRSReader(text);
		String areaDescription = reader.readAreaDescription();
		assertNotNull(areaDescription);
		assertEquals("Netherlands offshore.", areaDescription);
		reader.close();
		CRSWriter writer = new CRSWriter();
		writer.writeAreaDescription(areaDescription);
		assertEquals(text, writer.toString());
		writer.close();

	}

	/**
	 * Test geographic bounding box
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeographicBoundingBox() throws IOException {

		String text = "BBOX[51.43,2.54,55.77,6.40]";
		CRSReader reader = new CRSReader(text);
		GeographicBoundingBox boundingBox = reader.readGeographicBoundingBox();
		assertNotNull(boundingBox);
		assertEquals(51.43, boundingBox.getLowerLeftLatitude(), 0);
		assertEquals(2.54, boundingBox.getLowerLeftLongitude(), 0);
		assertEquals(55.77, boundingBox.getUpperRightLatitude(), 0);
		assertEquals(6.40, boundingBox.getUpperRightLongitude(), 0);
		reader.close();
		assertEquals(text.replaceAll("\\.40", ".4"), boundingBox.toString());

		text = "BBOX[-55.95,160.60,-25.88,-171.20]";
		reader = new CRSReader(text);
		boundingBox = reader.readGeographicBoundingBox();
		assertNotNull(boundingBox);
		assertEquals(-55.95, boundingBox.getLowerLeftLatitude(), 0);
		assertEquals(160.60, boundingBox.getLowerLeftLongitude(), 0);
		assertEquals(-25.88, boundingBox.getUpperRightLatitude(), 0);
		assertEquals(-171.20, boundingBox.getUpperRightLongitude(), 0);
		reader.close();
		assertEquals(text.replaceAll("\\.60", ".6").replaceAll("\\.20", ".2"),
				boundingBox.toString());

	}

	/**
	 * Test vertical extent
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testVerticalExtent() throws IOException {

		String text = "VERTICALEXTENT[-1000,0,LENGTHUNIT[\"metre\",1.0]]";
		CRSReader reader = new CRSReader(text);
		VerticalExtent verticalExtent = reader.readVerticalExtent();
		assertNotNull(verticalExtent);
		assertEquals(-1000, verticalExtent.getMinimumHeight(), 0);
		assertEquals(0, verticalExtent.getMaximumHeight(), 0);
		Unit lengthUnit = verticalExtent.getUnit();
		assertNotNull(lengthUnit);
		assertEquals(UnitType.LENGTHUNIT, lengthUnit.getType());
		assertEquals("metre", lengthUnit.getName());
		assertEquals(1.0, lengthUnit.getConversionFactor(), 0);
		reader.close();
		text = text.replaceAll("-1000,0", "-1000.0,0.0");
		assertEquals(text, verticalExtent.toString());

		text = "VERTICALEXTENT[-1000,0]";
		reader = new CRSReader(text);
		verticalExtent = reader.readVerticalExtent();
		assertNotNull(verticalExtent);
		assertEquals(-1000, verticalExtent.getMinimumHeight(), 0);
		assertEquals(0, verticalExtent.getMaximumHeight(), 0);
		lengthUnit = verticalExtent.getUnit();
		assertNull(lengthUnit);
		reader.close();
		text = text.replaceAll("-1000,0", "-1000.0,0.0");
		assertEquals(text, verticalExtent.toString());

	}

	/**
	 * Test temporal extent
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testTemporalExtent() throws IOException {

		String text = "TIMEEXTENT[2013-01-01,2013-12-31]";
		CRSReader reader = new CRSReader(text);
		TemporalExtent temporalExtent = reader.readTemporalExtent();
		assertNotNull(temporalExtent);
		assertEquals("2013-01-01", temporalExtent.getStart());
		assertTrue(temporalExtent.hasStartDateTime());
		assertEquals("2013-01-01",
				temporalExtent.getStartDateTime().toString());
		assertEquals("2013-12-31", temporalExtent.getEnd());
		assertTrue(temporalExtent.hasEndDateTime());
		assertEquals("2013-12-31", temporalExtent.getEndDateTime().toString());
		reader.close();
		assertEquals(text, temporalExtent.toString());

		text = "TIMEEXTENT[\"Jurassic\",\"Quaternary\"]";
		reader = new CRSReader(text);
		temporalExtent = reader.readTemporalExtent();
		assertNotNull(temporalExtent);
		assertEquals("Jurassic", temporalExtent.getStart());
		assertFalse(temporalExtent.hasStartDateTime());
		assertEquals("Quaternary", temporalExtent.getEnd());
		assertFalse(temporalExtent.hasEndDateTime());
		reader.close();
		assertEquals(text, temporalExtent.toString());

	}

	/**
	 * Test usage
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testUsage() throws IOException {

		String text = "USAGE[SCOPE[\"Spatial referencing.\"],"
				+ "AREA[\"Netherlands offshore.\"],TIMEEXTENT[1976-01,2001-04]]";
		CRSReader reader = new CRSReader(text);
		Usage usage = reader.readUsage();
		assertNotNull(usage);
		assertEquals("Spatial referencing.", usage.getScope());
		Extent extent = usage.getExtent();
		assertNotNull(extent);
		assertEquals("Netherlands offshore.", extent.getAreaDescription());
		TemporalExtent temporalExtent = extent.getTemporalExtent();
		assertNotNull(temporalExtent);
		assertEquals("1976-01", temporalExtent.getStart());
		assertTrue(temporalExtent.hasStartDateTime());
		assertEquals("1976-01", temporalExtent.getStartDateTime().toString());
		assertEquals("2001-04", temporalExtent.getEnd());
		assertTrue(temporalExtent.hasEndDateTime());
		assertEquals("2001-04", temporalExtent.getEndDateTime().toString());
		reader.close();
		assertEquals(text, usage.toString());

	}

	/**
	 * Test usages
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testUsages() throws IOException {

		String text = "USAGE[SCOPE[\"Small scale topographic mapping.\"],"
				+ "AREA[\"Finland - onshore and offshore.\"]],"
				+ "USAGE[SCOPE[\"Cadastre.\"],"
				+ "AREA[\"Finland - onshore between 26°30'E and 27°30'E.\"],"
				+ "BBOX[60.36,26.5,70.05,27.5]]";
		CRSReader reader = new CRSReader(text);
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
		CRSWriter writer = new CRSWriter();
		writer.writeUsages(usages);
		assertEquals(text, writer.toString());
		writer.close();

	}

	/**
	 * Test identifier
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testIdentifier() throws IOException {

		String text = "ID[\"Authority name\",\"Abcd_Ef\",7.1]";
		CRSReader reader = new CRSReader(text);
		Identifier identifier = reader.readIdentifier();
		assertNotNull(identifier);
		assertEquals("Authority name", identifier.getName());
		assertEquals("Abcd_Ef", identifier.getUniqueIdentifier());
		assertEquals("7.1", identifier.getVersion());
		reader.close();
		assertEquals(text, identifier.toString());

		text = "ID[\"EPSG\",4326]";
		reader = new CRSReader(text);
		identifier = reader.readIdentifier();
		assertNotNull(identifier);
		assertEquals("EPSG", identifier.getName());
		assertEquals("4326", identifier.getUniqueIdentifier());
		reader.close();
		assertEquals(text, identifier.toString());

		text = "ID[\"EPSG\",4326,URI[\"urn:ogc:def:crs:EPSG::4326\"]]";
		reader = new CRSReader(text);
		identifier = reader.readIdentifier();
		assertNotNull(identifier);
		assertEquals("EPSG", identifier.getName());
		assertEquals("4326", identifier.getUniqueIdentifier());
		assertEquals("urn:ogc:def:crs:EPSG::4326", identifier.getUri());
		reader.close();
		assertEquals(text, identifier.toString());

		text = "ID[\"EuroGeographics\",\"ES_ED50 (BAL99) to ETRS89\",\"2001-04-20\"]";
		reader = new CRSReader(text);
		identifier = reader.readIdentifier();
		assertNotNull(identifier);
		assertEquals("EuroGeographics", identifier.getName());
		assertEquals("ES_ED50 (BAL99) to ETRS89",
				identifier.getUniqueIdentifier());
		assertEquals("2001-04-20", identifier.getVersion());
		reader.close();
		assertEquals(text, identifier.toString());

	}

	/**
	 * Test remark
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testRemark() throws IOException {

		String text = "REMARK[\"A remark in ASCII\"]";
		String remark = "A remark in ASCII";
		CRSReader reader = new CRSReader(text);
		assertEquals(remark, reader.readRemark());
		reader.close();
		CRSWriter writer = new CRSWriter();
		writer.writeRemark(remark);
		assertEquals(text, writer.toString());
		writer.close();

		text = "REMARK[\"Замечание на русском языке\"]";
		remark = "Замечание на русском языке";
		reader = new CRSReader(text);
		assertEquals(remark, reader.readRemark());
		reader.close();
		writer = new CRSWriter();
		writer.writeRemark(remark);
		assertEquals(text, writer.toString());
		writer.close();

		text = "GEOGCRS[\"S-95\"," + "DATUM[\"Pulkovo 1995\","
				+ "ELLIPSOID[\"Krassowsky 1940\",6378245,298.3,"
				+ "LENGTHUNIT[\"metre\",1.0]]],CS[ellipsoidal,2],"
				+ "AXIS[\"latitude\",north,ORDER[1]],"
				+ "AXIS[\"longitude\",east,ORDER[2]],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],"
				+ "REMARK[\"Система Геодеэических Координвт года 1995(СК-95)\"]"
				+ "]";
		String remarkText = "REMARK[\"Система Геодеэических Координвт года 1995(СК-95)\"]";
		remark = "Система Геодеэических Координвт года 1995(СК-95)";
		CoordinateReferenceSystem crs = CRSReader
				.readCoordinateReferenceSystem(text, true);
		assertEquals(remark, crs.getRemark());
		writer = new CRSWriter();
		writer.writeRemark(crs.getRemark());
		assertEquals(remarkText, writer.toString());
		writer.close();

	}

	/**
	 * Test length unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testLengthUnit() throws IOException {

		String text = "LENGTHUNIT[\"metre\",1]";
		CRSReader reader = new CRSReader(text);
		Unit unit = reader.readLengthUnit();
		reader.reset();
		assertEquals(unit, reader.readUnit());
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1, unit.getConversionFactor(), 0);
		reader.close();
		text = text.replaceAll("1", "1.0");
		assertEquals(text, unit.toString());
		unit.setType(UnitType.UNIT);
		assertEquals(text.replaceAll("LENGTHUNIT", "UNIT"), unit.toString());

		text = "LENGTHUNIT[\"German legal metre\",1.0000135965]";
		reader = new CRSReader(text);
		unit = reader.readLengthUnit();
		reader.reset();
		assertEquals(unit, reader.readUnit());
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("German legal metre", unit.getName());
		assertEquals(1.0000135965, unit.getConversionFactor(), 0);
		reader.close();
		assertEquals(text, unit.toString());
		unit.setType(UnitType.UNIT);
		assertEquals(text.replaceAll("LENGTHUNIT", "UNIT"), unit.toString());

	}

	/**
	 * Test angle unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testAngleUnit() throws IOException {

		String text = "ANGLEUNIT[\"degree\",0.0174532925199433]";
		CRSReader reader = new CRSReader(text);
		Unit unit = reader.readAngleUnit();
		reader.reset();
		assertEquals(unit, reader.readUnit());
		assertEquals(UnitType.ANGLEUNIT, unit.getType());
		assertEquals("degree", unit.getName());
		assertEquals(0.0174532925199433, unit.getConversionFactor(), 0);
		reader.close();
		assertEquals(text, unit.toString());
		unit.setType(UnitType.UNIT);
		assertEquals(text.replaceAll("ANGLEUNIT", "UNIT"), unit.toString());

	}

	/**
	 * Test scale unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testScaleUnit() throws IOException {

		String text = "SCALEUNIT[\"parts per million\",1E-06]";
		CRSReader reader = new CRSReader(text);
		Unit unit = reader.readScaleUnit();
		reader.reset();
		assertEquals(unit, reader.readUnit());
		assertEquals(UnitType.SCALEUNIT, unit.getType());
		assertEquals("parts per million", unit.getName());
		assertEquals(1E-06, unit.getConversionFactor(), 0);
		reader.close();
		text = text.replaceAll("1E-06", "1.0E-6");
		assertEquals(text, unit.toString());
		unit.setType(UnitType.UNIT);
		assertEquals(text.replaceAll("SCALEUNIT", "UNIT"), unit.toString());

	}

	/**
	 * Test parametric unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testParametricUnit() throws IOException {

		String text = "PARAMETRICUNIT[\"hectopascal\",100]";
		CRSReader reader = new CRSReader(text);
		Unit unit = reader.readParametricUnit();
		reader.reset();
		assertEquals(unit, reader.readUnit());
		assertEquals(UnitType.PARAMETRICUNIT, unit.getType());
		assertEquals("hectopascal", unit.getName());
		assertEquals(100, unit.getConversionFactor(), 0);
		reader.close();
		assertEquals(text.replaceAll("100", "100.0"), unit.toString());

	}

	/**
	 * Test time unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testTimeUnit() throws IOException {

		String text = "TIMEUNIT[\"millisecond\",0.001]";
		CRSReader reader = new CRSReader(text);
		Unit unit = reader.readTimeUnit();
		reader.reset();
		assertEquals(unit, reader.readUnit());
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("millisecond", unit.getName());
		assertEquals(0.001, unit.getConversionFactor(), 0);
		reader.close();
		assertEquals(text, unit.toString());

		text = "TIMEUNIT[\"calendar month\"]";
		reader = new CRSReader(text);
		unit = reader.readTimeUnit();
		reader.reset();
		assertEquals(unit, reader.readUnit());
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("calendar month", unit.getName());
		reader.close();
		assertEquals(text, unit.toString());

		text = "TIMEUNIT[\"calendar second\"]";
		reader = new CRSReader(text);
		unit = reader.readTimeUnit();
		reader.reset();
		assertEquals(unit, reader.readUnit());
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("calendar second", unit.getName());
		reader.close();
		assertEquals(text, unit.toString());

		text = "TIMEUNIT[\"day\",86400.0]";
		reader = new CRSReader(text);
		unit = reader.readTimeUnit();
		reader.reset();
		assertEquals(unit, reader.readUnit());
		assertEquals(UnitType.TIMEUNIT, unit.getType());
		assertEquals("day", unit.getName());
		assertEquals(86400.0, unit.getConversionFactor(), 0);
		reader.close();
		assertEquals(text, unit.toString());

	}

	/**
	 * Test geodetic coordinate system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeodeticCoordinateSystem() throws IOException {

		String text = "CS[Cartesian,3],AXIS[\"(X)\",geocentricX],"
				+ "AXIS[\"(Y)\",geocentricY],AXIS[\"(Z)\",geocentricZ],"
				+ "LENGTHUNIT[\"metre\",1.0]";
		CRSReader reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

		text = "CS[Cartesian,3],"
				+ "AXIS[\"(X)\",east],AXIS[\"(Y)\",north],AXIS[\"(Z)\",up],"
				+ "LENGTHUNIT[\"metre\",1.0]";
		reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

		text = "CS[spherical,3],"
				+ "AXIS[\"distance (r)\",awayFrom,ORDER[1],LENGTHUNIT[\"kilometre\",1000]],"
				+ "AXIS[\"longitude (U)\",counterClockwise,BEARING[0],ORDER[2],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"elevation (V)\",up,ORDER[3],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]]";
		reader = new CRSReader(text);
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
		assertEquals(text.replaceAll("0]", "0.0]"),
				coordinateSystem.toString());

	}

	/**
	 * Test geographic coordinate system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeographicCoordinateSystem() throws IOException {

		String text = "CS[ellipsoidal,3],"
				+ "AXIS[\"latitude\",north,ORDER[1],ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"longitude\",east,ORDER[2],ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"ellipsoidal height (h)\",up,ORDER[3],LENGTHUNIT[\"metre\",1.0]]";
		CRSReader reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

		text = "CS[ellipsoidal,2],AXIS[\"(lat)\",north],"
				+ "AXIS[\"(lon)\",east],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]";
		reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

	}

	/**
	 * Test projected coordinate system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testProjectedCoordinateSystem() throws IOException {

		String text = "CS[Cartesian,2],"
				+ "AXIS[\"(E)\",east,ORDER[1],LENGTHUNIT[\"metre\",1.0]],"
				+ "AXIS[\"(N)\",north,ORDER[2],LENGTHUNIT[\"metre\",1.0]]";
		CRSReader reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

		text = "CS[Cartesian,2],AXIS[\"(E)\",east],"
				+ "AXIS[\"(N)\",north],LENGTHUNIT[\"metre\",1.0]";
		reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

		text = "CS[Cartesian,2],AXIS[\"northing (X)\",north,ORDER[1]],"
				+ "AXIS[\"easting (Y)\",east,ORDER[2]],"
				+ "LENGTHUNIT[\"German legal metre\",1.0000135965]";
		reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

		text = "CS[Cartesian,2]," + "AXIS[\"easting (X)\",south,"
				+ "MERIDIAN[90,ANGLEUNIT[\"degree\",0.0174532925199433]],ORDER[1]"
				+ "],AXIS[\"northing (Y)\",south,"
				+ "MERIDIAN[180,ANGLEUNIT[\"degree\",0.0174532925199433]],ORDER[2]"
				+ "],LENGTHUNIT[\"metre\",1.0]";
		reader = new CRSReader(text);
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
				axes.get(0).getMeridianUnit().getType());
		assertEquals("degree", axes.get(0).getMeridianUnit().getName());
		assertEquals(0.0174532925199433,
				axes.get(0).getMeridianUnit().getConversionFactor(), 0);
		assertEquals(1, axes.get(0).getOrder().intValue());
		assertEquals("northing", axes.get(1).getName());
		assertEquals("Y", axes.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.SOUTH, axes.get(1).getDirection());
		assertEquals(180, axes.get(1).getMeridian(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				axes.get(1).getMeridianUnit().getType());
		assertEquals("degree", axes.get(1).getMeridianUnit().getName());
		assertEquals(0.0174532925199433,
				axes.get(1).getMeridianUnit().getConversionFactor(), 0);
		assertEquals(2, axes.get(1).getOrder().intValue());
		unit = coordinateSystem.getUnit();
		assertEquals(UnitType.LENGTHUNIT, unit.getType());
		assertEquals("metre", unit.getName());
		assertEquals(1.0, unit.getConversionFactor(), 0);
		reader.close();
		assertEquals(text.replaceAll(",ANGLEUNIT", ".0,ANGLEUNIT"),
				coordinateSystem.toString());

		text = "CS[Cartesian,3],AXIS[\"(E)\",east],"
				+ "AXIS[\"(N)\",north],AXIS[\"ellipsoid height (h)\",up],"
				+ "LENGTHUNIT[\"metre\",1.0]";
		reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

	}

	/**
	 * Test vertical coordinate system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testVerticalCoordinateSystem() throws IOException {

		String text = "CS[vertical,1],AXIS[\"gravity-related height (H)\",up],"
				+ "LENGTHUNIT[\"metre\",1.0]";
		CRSReader reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

		text = "CS[vertical,1],AXIS[\"depth (D)\",down,"
				+ "LENGTHUNIT[\"metre\",1.0]]";
		reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

	}

	/**
	 * Test engineering coordinate system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testEngineeringCoordinateSystem() throws IOException {

		String text = "CS[Cartesian,2],"
				+ "AXIS[\"site north (x)\",southeast,ORDER[1]],"
				+ "AXIS[\"site east (y)\",southwest,ORDER[2]],"
				+ "LENGTHUNIT[\"metre\",1.0]";
		CRSReader reader = new CRSReader(text);
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
		assertEquals(text.replaceAll("southeast", "southEast").replaceAll(
				"southwest", "southWest"), coordinateSystem.toString());

		text = "CS[polar,2],"
				+ "AXIS[\"distance (r)\",awayFrom,ORDER[1],LENGTHUNIT[\"metre\",1.0]],"
				+ "AXIS[\"bearing (U)\",clockwise,BEARING[234],ORDER[2],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]]";
		reader = new CRSReader(text);
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
		assertEquals(text.replaceAll("234]", "234.0]"),
				coordinateSystem.toString());

		text = "CS[Cartesian,3],AXIS[\"ahead (x)\",forward,ORDER[1]],"
				+ "AXIS[\"right (y)\",starboard,ORDER[2]],"
				+ "AXIS[\"down (z)\",down,ORDER[3]],"
				+ "LENGTHUNIT[\"metre\",1.0]";
		reader = new CRSReader(text);
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
		assertEquals(text, coordinateSystem.toString());

		text = "CS[ordinal,2],AXIS[\"Inline (I)\",northEast,ORDER[1]],"
				+ "AXIS[\"Crossline (J)\",northwest,ORDER[2]]";
		reader = new CRSReader(text);
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
		assertEquals(text.replaceAll("northwest", "northWest"),
				coordinateSystem.toString());

	}

	/**
	 * Test geodetic datum ensemble
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeodeticDatumEnsemble() throws IOException {

		String text = "ENSEMBLE[\"WGS 84 ensemble\","
				+ "MEMBER[\"WGS 84 (TRANSIT)\"],MEMBER[\"WGS 84 (G730)\"],"
				+ "MEMBER[\"WGS 84 (G834)\"],MEMBER[\"WGS 84 (G1150)\"],"
				+ "MEMBER[\"WGS 84 (G1674)\"],MEMBER[\"WGS 84 (G1762)\"],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.2572236,LENGTHUNIT[\"metre\",1.0]],"
				+ "ENSEMBLEACCURACY[2]]";
		CRSReader reader = new CRSReader(text);
		GeoDatumEnsemble datumEnsemble = reader.readGeoDatumEnsemble();
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
				datumEnsemble.getEllipsoid().getUnit().getType());
		assertEquals("metre", datumEnsemble.getEllipsoid().getUnit().getName());
		assertEquals(1.0,
				datumEnsemble.getEllipsoid().getUnit().getConversionFactor(),
				0);
		assertEquals(2, datumEnsemble.getAccuracy(), 0);
		reader.close();
		assertEquals(
				text.replaceAll("6378137", "6378137.0").replace("[2]", "[2.0]"),
				datumEnsemble.toString());

		text = "ENSEMBLE[\"WGS 84 ensemble\","
				+ "MEMBER[\"WGS 84 (TRANSIT)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"WGS 84 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"WGS 84 (G834)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"WGS 84 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"WGS 84 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"WGS 84 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137,298.2572236,LENGTHUNIT[\"metre\",1.0]],"
				+ "ENSEMBLEACCURACY[2]]";
		reader = new CRSReader(text);
		datumEnsemble = reader.readGeoDatumEnsemble();
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
				datumEnsemble.getEllipsoid().getUnit().getType());
		assertEquals("metre", datumEnsemble.getEllipsoid().getUnit().getName());
		assertEquals(1.0,
				datumEnsemble.getEllipsoid().getUnit().getConversionFactor(),
				0);
		assertEquals(2, datumEnsemble.getAccuracy(), 0);
		reader.close();
		assertEquals(
				text.replaceAll("6378137", "6378137.0").replace("[2]", "[2.0]"),
				datumEnsemble.toString());

	}

	/**
	 * Test vertical datum ensemble
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testVerticalDatumEnsemble() throws IOException {

		String text = "ENSEMBLE[\"EVRS ensemble\","
				+ "MEMBER[\"EVRF2000\"],MEMBER[\"EVRF2007\"],"
				+ "ENSEMBLEACCURACY[0.01]]";
		CRSReader reader = new CRSReader(text);
		DatumEnsemble datumEnsemble = reader.readVerticalDatumEnsemble();
		assertNotNull(datumEnsemble);
		assertEquals("EVRS ensemble", datumEnsemble.getName());
		assertEquals(2, datumEnsemble.getMembers().size());
		assertEquals("EVRF2000", datumEnsemble.getMembers().get(0).getName());
		assertEquals("EVRF2007", datumEnsemble.getMembers().get(1).getName());
		assertEquals(0.01, datumEnsemble.getAccuracy(), 0);
		reader.close();
		assertEquals(text, datumEnsemble.toString());

	}

	/**
	 * Test dynamic
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testDynamic() throws IOException {

		String text = "DYNAMIC[FRAMEEPOCH[2010.0]]";
		CRSReader reader = new CRSReader(text);
		Dynamic dynamic = reader.readDynamic();
		assertEquals(2010.0, dynamic.getReferenceEpoch(), 0);
		reader.close();
		assertEquals(text, dynamic.toString());

		text = "DYNAMIC[FRAMEEPOCH[2010.0],MODEL[\"NAD83(CSRS)v6 velocity grid\"]]";
		reader = new CRSReader(text);
		dynamic = reader.readDynamic();
		assertEquals(2010.0, dynamic.getReferenceEpoch(), 0);
		assertEquals("NAD83(CSRS)v6 velocity grid",
				dynamic.getDeformationModelName());
		reader.close();
		assertEquals(text, dynamic.toString());

	}

	/**
	 * Test ellipsoid
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testEllipsoid() throws IOException {

		String text = "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]";
		CRSReader reader = new CRSReader(text);
		Ellipsoid ellipsoid = reader.readEllipsoid();
		assertEquals("GRS 1980", ellipsoid.getName());
		assertEquals(6378137, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getUnit().getType());
		assertEquals("metre", ellipsoid.getUnit().getName());
		assertEquals(1.0, ellipsoid.getUnit().getConversionFactor(), 0);
		reader.close();
		assertEquals(text.replaceAll("6378137", "6378137.0"),
				ellipsoid.toString());

		text = "SPHEROID[\"GRS 1980\",6378137.0,298.257222101]";
		reader = new CRSReader(text);
		ellipsoid = reader.readEllipsoid();
		assertEquals("GRS 1980", ellipsoid.getName());
		assertEquals(6378137, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, ellipsoid.getInverseFlattening(), 0);
		reader.close();
		assertEquals(text.replaceAll("SPHEROID", "ELLIPSOID"),
				ellipsoid.toString());

		text = "ELLIPSOID[\"Clark 1866\",20925832.164,294.97869821,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219]]";
		reader = new CRSReader(text);
		ellipsoid = reader.readEllipsoid();
		assertEquals("Clark 1866", ellipsoid.getName());
		assertEquals(20925832.164, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(294.97869821, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getUnit().getType());
		assertEquals("US survey foot", ellipsoid.getUnit().getName());
		assertEquals(0.304800609601219,
				ellipsoid.getUnit().getConversionFactor(), 0);
		reader.close();
		assertEquals(text.replaceAll("20925832.164", "2.0925832164E7"),
				ellipsoid.toString());

		text = "ELLIPSOID[\"Sphere\",6371000,0,LENGTHUNIT[\"metre\",1.0]]";
		reader = new CRSReader(text);
		ellipsoid = reader.readEllipsoid();
		assertEquals("Sphere", ellipsoid.getName());
		assertEquals(6371000, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(0, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getUnit().getType());
		assertEquals("metre", ellipsoid.getUnit().getName());
		assertEquals(1.0, ellipsoid.getUnit().getConversionFactor(), 0);
		reader.close();
		assertEquals(text.replaceAll("6371000,0", "6371000.0,0.0"),
				ellipsoid.toString());

	}

	/**
	 * Test prime meridian
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testPrimeMeridian() throws IOException {

		String text = "PRIMEM[\"Paris\",2.5969213,ANGLEUNIT[\"grad\",0.015707963267949]]";
		CRSReader reader = new CRSReader(text);
		PrimeMeridian primeMeridian = reader.readPrimeMeridian();
		assertEquals("Paris", primeMeridian.getName());
		assertEquals(2.5969213, primeMeridian.getIrmLongitude(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				primeMeridian.getIrmLongitudeUnit().getType());
		assertEquals("grad", primeMeridian.getIrmLongitudeUnit().getName());
		assertEquals(0.015707963267949,
				primeMeridian.getIrmLongitudeUnit().getConversionFactor(), 0);
		reader.close();
		assertEquals(text, primeMeridian.toString());

		text = "PRIMEM[\"Ferro\",-17.6666667]";
		reader = new CRSReader(text);
		primeMeridian = reader.readPrimeMeridian();
		assertEquals("Ferro", primeMeridian.getName());
		assertEquals(-17.6666667, primeMeridian.getIrmLongitude(), 0);
		reader.close();
		assertEquals(text, primeMeridian.toString());

		text = "PRIMEM[\"Greenwich\",0.0,ANGLEUNIT[\"degree\",0.0174532925199433]]";
		reader = new CRSReader(text);
		primeMeridian = reader.readPrimeMeridian();
		assertEquals("Greenwich", primeMeridian.getName());
		assertEquals(0.0, primeMeridian.getIrmLongitude(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				primeMeridian.getIrmLongitudeUnit().getType());
		assertEquals("degree", primeMeridian.getIrmLongitudeUnit().getName());
		assertEquals(0.0174532925199433,
				primeMeridian.getIrmLongitudeUnit().getConversionFactor(), 0);
		reader.close();
		assertEquals(text, primeMeridian.toString());

	}

	/**
	 * Test geodetic reference frame
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeodeticReferenceFrame() throws IOException {

		String text = "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]]";
		CRSReader reader = new CRSReader(text);
		GeoReferenceFrame geodeticReferenceFrame = reader
				.readGeoReferenceFrame();
		assertEquals("North American Datum 1983",
				geodeticReferenceFrame.getName());
		Ellipsoid ellipsoid = geodeticReferenceFrame.getEllipsoid();
		assertEquals("GRS 1980", ellipsoid.getName());
		assertEquals(6378137, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getUnit().getType());
		assertEquals("metre", ellipsoid.getUnit().getName());
		assertEquals(1.0, ellipsoid.getUnit().getConversionFactor(), 0);
		reader.close();
		assertEquals(text.replaceAll("6378137", "6378137.0"),
				geodeticReferenceFrame.toString());

		text = "TRF[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378388.0,298.257223563,LENGTHUNIT[\"metre\",1.0]]"
				+ "],PRIMEM[\"Greenwich\",0.0]";
		reader = new CRSReader(text);
		geodeticReferenceFrame = reader.readGeoReferenceFrame();
		assertEquals("World Geodetic System 1984",
				geodeticReferenceFrame.getName());
		ellipsoid = geodeticReferenceFrame.getEllipsoid();
		assertEquals("WGS 84", ellipsoid.getName());
		assertEquals(6378388.0, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(298.257223563, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getUnit().getType());
		assertEquals("metre", ellipsoid.getUnit().getName());
		assertEquals(1.0, ellipsoid.getUnit().getConversionFactor(), 0);
		assertEquals("Greenwich",
				geodeticReferenceFrame.getPrimeMeridian().getName());
		assertEquals(0.0,
				geodeticReferenceFrame.getPrimeMeridian().getIrmLongitude(), 0);
		reader.close();
		assertEquals(text.replaceAll("TRF", "DATUM"),
				geodeticReferenceFrame.toString());

		text = "GEODETICDATUM[\"Tananarive 1925\","
				+ "ELLIPSOID[\"International 1924\",6378388.0,297.0,LENGTHUNIT[\"metre\",1.0]],"
				+ "ANCHOR[\"Tananarive observatory:21.0191667gS, 50.23849537gE of Paris\"]],"
				+ "PRIMEM[\"Paris\",2.5969213,ANGLEUNIT[\"grad\",0.015707963267949]]";
		reader = new CRSReader(text);
		geodeticReferenceFrame = reader.readGeoReferenceFrame();
		assertEquals("Tananarive 1925", geodeticReferenceFrame.getName());
		ellipsoid = geodeticReferenceFrame.getEllipsoid();
		assertEquals("International 1924", ellipsoid.getName());
		assertEquals(6378388.0, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(297.0, ellipsoid.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getUnit().getType());
		assertEquals("metre", ellipsoid.getUnit().getName());
		assertEquals(1.0, ellipsoid.getUnit().getConversionFactor(), 0);
		assertEquals(
				"Tananarive observatory:21.0191667gS, 50.23849537gE of Paris",
				geodeticReferenceFrame.getAnchor());
		assertEquals("Paris",
				geodeticReferenceFrame.getPrimeMeridian().getName());
		assertEquals(2.5969213,
				geodeticReferenceFrame.getPrimeMeridian().getIrmLongitude(), 0);
		assertEquals(UnitType.ANGLEUNIT, geodeticReferenceFrame
				.getPrimeMeridian().getIrmLongitudeUnit().getType());
		assertEquals("grad", geodeticReferenceFrame.getPrimeMeridian()
				.getIrmLongitudeUnit().getName());
		assertEquals(0.015707963267949, geodeticReferenceFrame
				.getPrimeMeridian().getIrmLongitudeUnit().getConversionFactor(),
				0);
		reader.close();
		assertEquals(text.replaceAll("GEODETICDATUM", "DATUM"),
				geodeticReferenceFrame.toString());

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

		CRS crs = CRSReader.read(text, true);
		GeoCoordinateReferenceSystem geodeticOrGeographicCrs = CRSReader
				.readGeo(text);
		assertEquals(crs, geodeticOrGeographicCrs);
		GeoCoordinateReferenceSystem geodeticCrs = CRSReader.readGeodetic(text);
		assertEquals(crs, geodeticCrs);
		assertEquals(CRSType.GEODETIC, geodeticCrs.getType());
		assertEquals("JGD2000", geodeticCrs.getName());
		assertEquals("Japanese Geodetic Datum 2000",
				geodeticCrs.getReferenceFrame().getName());
		assertEquals("GRS 1980",
				geodeticCrs.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137, geodeticCrs.getReferenceFrame().getEllipsoid()
				.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, geodeticCrs.getReferenceFrame()
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
		assertTrue(geodeticCrs.getUsages().get(0).getExtent()
				.getTemporalExtent().hasStartDateTime());
		assertEquals("2002-04-01", geodeticCrs.getUsages().get(0).getExtent()
				.getTemporalExtent().getStartDateTime().toString());
		assertEquals("2011-10-21", geodeticCrs.getUsages().get(0).getExtent()
				.getTemporalExtent().getEnd());
		assertTrue(geodeticCrs.getUsages().get(0).getExtent()
				.getTemporalExtent().hasEndDateTime());
		assertEquals("2011-10-21", geodeticCrs.getUsages().get(0).getExtent()
				.getTemporalExtent().getEndDateTime().toString());
		assertEquals("EPSG", geodeticCrs.getIdentifiers().get(0).getName());
		assertEquals("4946",
				geodeticCrs.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("urn:ogc:def:crs:EPSG::4946",
				geodeticCrs.getIdentifiers().get(0).getUri());
		assertEquals("注：JGD2000ジオセントリックは現在JGD2011に代わりました。",
				geodeticCrs.getRemark());
		text = text.replaceAll("6378137", "6378137.0");
		assertEquals(text, geodeticCrs.toString());
		assertEquals(text, CRSWriter.write(geodeticCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(geodeticCrs));

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

		CRS crs = CRSReader.read(text, true);
		GeoCoordinateReferenceSystem geodeticOrGeographicCrs = CRSReader
				.readGeo(text);
		assertEquals(crs, geodeticOrGeographicCrs);
		GeoCoordinateReferenceSystem geographicCrs = CRSReader
				.readGeographic(text);
		assertEquals(crs, geographicCrs);
		assertEquals(CRSType.GEOGRAPHIC, geographicCrs.getType());
		assertEquals("WGS 84 (G1762)", geographicCrs.getName());
		assertEquals(2005.0, geographicCrs.getDynamic().getReferenceEpoch(), 0);
		assertEquals("World Geodetic System 1984 (G1762)",
				geographicCrs.getReferenceFrame().getName());
		assertEquals("WGS 84",
				geographicCrs.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137, geographicCrs.getReferenceFrame().getEllipsoid()
				.getSemiMajorAxis(), 0);
		assertEquals(298.257223563, geographicCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, geographicCrs.getReferenceFrame()
				.getEllipsoid().getUnit().getType());
		assertEquals("metre", geographicCrs.getReferenceFrame().getEllipsoid()
				.getUnit().getName());
		assertEquals(1.0, geographicCrs.getReferenceFrame().getEllipsoid()
				.getUnit().getConversionFactor(), 0);
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
		text = text.replaceAll("TRF", "DATUM").replaceAll("6378137",
				"6378137.0");
		assertEquals(text, geographicCrs.toString());
		assertEquals(text, CRSWriter.write(geographicCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(geographicCrs));

		text = "GEOGRAPHICCRS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]],"
				+ "CS[ellipsoidal,2],AXIS[\"latitude\",north],"
				+ "AXIS[\"longitude\",east],"
				+ "ANGLEUNIT[\"degree\",0.017453292519943],"
				+ "ID[\"EPSG\",4269],REMARK[\"1986 realisation\"]]";

		crs = CRSReader.read(text, true);
		geodeticOrGeographicCrs = CRSReader.readGeo(text);
		assertEquals(crs, geodeticOrGeographicCrs);
		geographicCrs = CRSReader.readGeographic(text);
		assertEquals(crs, geographicCrs);
		assertEquals(CRSType.GEOGRAPHIC, geographicCrs.getType());
		assertEquals("NAD83", geographicCrs.getName());
		assertEquals("North American Datum 1983",
				geographicCrs.getReferenceFrame().getName());
		assertEquals("GRS 1980",
				geographicCrs.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137, geographicCrs.getReferenceFrame().getEllipsoid()
				.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, geographicCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, geographicCrs.getReferenceFrame()
				.getEllipsoid().getUnit().getType());
		assertEquals("metre", geographicCrs.getReferenceFrame().getEllipsoid()
				.getUnit().getName());
		assertEquals(1.0, geographicCrs.getReferenceFrame().getEllipsoid()
				.getUnit().getConversionFactor(), 0);
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
		text = text.replaceAll("GEOGRAPHICCRS", "GEOGCRS").replaceAll("6378137",
				"6378137.0");
		assertEquals(text, geographicCrs.toString());
		assertEquals(text, CRSWriter.write(geographicCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(geographicCrs));

		text = "GEOGCRS[\"NTF (Paris)\","
				+ "DATUM[\"Nouvelle Triangulation Francaise\","
				+ "ELLIPSOID[\"Clarke 1880 (IGN)\",6378249.2,293.4660213]],"
				+ "PRIMEM[\"Paris\",2.5969213],CS[ellipsoidal,2],"
				+ "AXIS[\"latitude\",north,ORDER[1]],"
				+ "AXIS[\"longitude\",east,ORDER[2]],"
				+ "ANGLEUNIT[\"grad\",0.015707963267949],"
				+ "REMARK[\"Nouvelle Triangulation Française\"]]";

		crs = CRSReader.read(text, true);
		geodeticOrGeographicCrs = CRSReader.readGeo(text);
		assertEquals(crs, geodeticOrGeographicCrs);
		geographicCrs = CRSReader.readGeographic(text);
		assertEquals(crs, geographicCrs);
		assertEquals(CRSType.GEOGRAPHIC, geographicCrs.getType());
		assertEquals("NTF (Paris)", geographicCrs.getName());
		assertEquals("Nouvelle Triangulation Francaise",
				geographicCrs.getReferenceFrame().getName());
		assertEquals("Clarke 1880 (IGN)",
				geographicCrs.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378249.2, geographicCrs.getReferenceFrame().getEllipsoid()
				.getSemiMajorAxis(), 0);
		assertEquals(293.4660213, geographicCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals("Paris",
				geographicCrs.getReferenceFrame().getPrimeMeridian().getName());
		assertEquals(2.5969213, geographicCrs.getReferenceFrame()
				.getPrimeMeridian().getIrmLongitude(), 0);
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				geographicCrs.getCoordinateSystem().getType());
		assertEquals(2, geographicCrs.getCoordinateSystem().getDimension());
		assertEquals("latitude",
				geographicCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals(AxisDirectionType.NORTH, geographicCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, geographicCrs.getCoordinateSystem().getAxes().get(0)
				.getOrder().intValue());
		assertEquals("longitude",
				geographicCrs.getCoordinateSystem().getAxes().get(1).getName());
		assertEquals(AxisDirectionType.EAST, geographicCrs.getCoordinateSystem()
				.getAxes().get(1).getDirection());
		assertEquals(2, geographicCrs.getCoordinateSystem().getAxes().get(1)
				.getOrder().intValue());
		assertEquals(UnitType.ANGLEUNIT,
				geographicCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("grad",
				geographicCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(0.015707963267949, geographicCrs.getCoordinateSystem()
				.getUnit().getConversionFactor(), 0);
		assertEquals("Nouvelle Triangulation Française",
				geographicCrs.getRemark());
		assertEquals(text, geographicCrs.toString());
		assertEquals(text, CRSWriter.write(geographicCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(geographicCrs));

	}

	/**
	 * Test map projection
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testMapProjection() throws IOException {

		String text = "CONVERSION[\"UTM zone 10N\","
				+ "METHOD[\"Transverse Mercator\",ID[\"EPSG\",9807]],"
				+ "PARAMETER[\"Latitude of natural origin\",0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],"
				+ "ID[\"EPSG\",8801]],"
				+ "PARAMETER[\"Longitude of natural origin\",-123,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8802]],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.9996,"
				+ "SCALEUNIT[\"unity\",1.0],ID[\"EPSG\",8805]],"
				+ "PARAMETER[\"False easting\",500000,"
				+ "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",8806]],"
				+ "PARAMETER[\"False northing\",0,LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",8807]]]";
		CRSReader reader = new CRSReader(text);
		MapProjection mapProjection = reader.readMapProjection();
		assertEquals("UTM zone 10N", mapProjection.getName());
		assertEquals("Transverse Mercator",
				mapProjection.getMethod().getName());
		assertEquals("EPSG",
				mapProjection.getMethod().getIdentifiers().get(0).getName());
		assertEquals("9807", mapProjection.getMethod().getIdentifiers().get(0)
				.getUniqueIdentifier());
		assertEquals("Latitude of natural origin",
				mapProjection.getParameters().get(0).getName());
		assertEquals(0,
				mapProjection.getOperationParameters().get(0).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, mapProjection.getOperationParameters()
				.get(0).getUnit().getType());
		assertEquals("degree", mapProjection.getOperationParameters().get(0)
				.getUnit().getName());
		assertEquals(0.0174532925199433, mapProjection.getOperationParameters()
				.get(0).getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", mapProjection.getParameters().get(0)
				.getIdentifiers().get(0).getName());
		assertEquals("8801", mapProjection.getParameters().get(0)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Longitude of natural origin",
				mapProjection.getParameters().get(1).getName());
		assertEquals(-123,
				mapProjection.getOperationParameters().get(1).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, mapProjection.getOperationParameters()
				.get(1).getUnit().getType());
		assertEquals("degree", mapProjection.getOperationParameters().get(1)
				.getUnit().getName());
		assertEquals(0.0174532925199433, mapProjection.getOperationParameters()
				.get(1).getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", mapProjection.getParameters().get(1)
				.getIdentifiers().get(0).getName());
		assertEquals("8802", mapProjection.getParameters().get(1)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Scale factor at natural origin",
				mapProjection.getParameters().get(2).getName());
		assertEquals(0.9996,
				mapProjection.getOperationParameters().get(2).getValue(), 0);
		assertEquals(UnitType.SCALEUNIT, mapProjection.getOperationParameters()
				.get(2).getUnit().getType());
		assertEquals("unity", mapProjection.getOperationParameters().get(2)
				.getUnit().getName());
		assertEquals(1.0, mapProjection.getOperationParameters().get(2)
				.getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", mapProjection.getParameters().get(2)
				.getIdentifiers().get(0).getName());
		assertEquals("8805", mapProjection.getParameters().get(2)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("False easting",
				mapProjection.getParameters().get(3).getName());
		assertEquals(500000,
				mapProjection.getOperationParameters().get(3).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT, mapProjection.getOperationParameters()
				.get(3).getUnit().getType());
		assertEquals("metre", mapProjection.getOperationParameters().get(3)
				.getUnit().getName());
		assertEquals(1.0, mapProjection.getOperationParameters().get(3)
				.getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", mapProjection.getParameters().get(3)
				.getIdentifiers().get(0).getName());
		assertEquals("8806", mapProjection.getParameters().get(3)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("False northing",
				mapProjection.getParameters().get(4).getName());
		assertEquals(0,
				mapProjection.getOperationParameters().get(4).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT, mapProjection.getOperationParameters()
				.get(4).getUnit().getType());
		assertEquals("metre", mapProjection.getOperationParameters().get(4)
				.getUnit().getName());
		assertEquals(1.0, mapProjection.getOperationParameters().get(4)
				.getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", mapProjection.getParameters().get(4)
				.getIdentifiers().get(0).getName());
		assertEquals("8807", mapProjection.getParameters().get(4)
				.getIdentifiers().get(0).getUniqueIdentifier());
		reader.close();
		assertEquals(
				text.replaceAll(",0,", ",0.0,").replaceAll("-123", "-123.0")
						.replaceAll("500000", "500000.0"),
				mapProjection.toString());

		text = "CONVERSION[\"UTM zone 10N\","
				+ "METHOD[\"Transverse Mercator\"],"
				+ "PARAMETER[\"Latitude of natural origin\",0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Longitude of natural origin\",-123,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.9996,"
				+ "SCALEUNIT[\"unity\",1.0]],"
				+ "PARAMETER[\"False easting\",500000,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"False northing\",0,LENGTHUNIT[\"metre\",1.0]],"
				+ "ID[\"EPSG\",16010]]";
		reader = new CRSReader(text);
		mapProjection = reader.readMapProjection();
		assertEquals("UTM zone 10N", mapProjection.getName());
		assertEquals("Transverse Mercator",
				mapProjection.getMethod().getName());
		assertEquals("Latitude of natural origin",
				mapProjection.getParameters().get(0).getName());
		assertEquals(0,
				mapProjection.getOperationParameters().get(0).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, mapProjection.getOperationParameters()
				.get(0).getUnit().getType());
		assertEquals("degree", mapProjection.getOperationParameters().get(0)
				.getUnit().getName());
		assertEquals(0.0174532925199433, mapProjection.getOperationParameters()
				.get(0).getUnit().getConversionFactor(), 0);
		assertEquals("Longitude of natural origin",
				mapProjection.getParameters().get(1).getName());
		assertEquals(-123,
				mapProjection.getOperationParameters().get(1).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, mapProjection.getOperationParameters()
				.get(1).getUnit().getType());
		assertEquals("degree", mapProjection.getOperationParameters().get(1)
				.getUnit().getName());
		assertEquals(0.0174532925199433, mapProjection.getOperationParameters()
				.get(1).getUnit().getConversionFactor(), 0);
		assertEquals("Scale factor at natural origin",
				mapProjection.getParameters().get(2).getName());
		assertEquals(0.9996,
				mapProjection.getOperationParameters().get(2).getValue(), 0);
		assertEquals(UnitType.SCALEUNIT, mapProjection.getOperationParameters()
				.get(2).getUnit().getType());
		assertEquals("unity", mapProjection.getOperationParameters().get(2)
				.getUnit().getName());
		assertEquals(1.0, mapProjection.getOperationParameters().get(2)
				.getUnit().getConversionFactor(), 0);
		assertEquals("False easting",
				mapProjection.getParameters().get(3).getName());
		assertEquals(500000,
				mapProjection.getOperationParameters().get(3).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT, mapProjection.getOperationParameters()
				.get(3).getUnit().getType());
		assertEquals("metre", mapProjection.getOperationParameters().get(3)
				.getUnit().getName());
		assertEquals(1.0, mapProjection.getOperationParameters().get(3)
				.getUnit().getConversionFactor(), 0);
		assertEquals("False northing",
				mapProjection.getParameters().get(4).getName());
		assertEquals(0,
				mapProjection.getOperationParameters().get(4).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT, mapProjection.getOperationParameters()
				.get(4).getUnit().getType());
		assertEquals("metre", mapProjection.getOperationParameters().get(4)
				.getUnit().getName());
		assertEquals(1.0, mapProjection.getOperationParameters().get(4)
				.getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", mapProjection.getIdentifiers().get(0).getName());
		assertEquals("16010",
				mapProjection.getIdentifiers().get(0).getUniqueIdentifier());
		reader.close();
		assertEquals(
				text.replaceAll(",0,", ",0.0,").replaceAll("-123", "-123.0")
						.replaceAll("500000", "500000.0"),
				mapProjection.toString());

	}

	/**
	 * Test projected geographic coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testProjectedGeographicCoordinateReferenceSystem()
			throws IOException {

		String text = "PROJCRS[\"ETRS89 Lambert Azimuthal Equal Area CRS\",BASEGEOGCRS[\"ETRS89\","
				+ "DATUM[\"ETRS89\","
				+ "ELLIPSOID[\"GRS 80\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]"
				+ "],ID[\"EuroGeographics\",\"ETRS89-LatLon\"]],"
				+ "CONVERSION[\"LAEA\","
				+ "METHOD[\"Lambert Azimuthal Equal Area\",ID[\"EPSG\",9820]],"
				+ "PARAMETER[\"Latitude of origin\",52.0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Longitude of origin\",10.0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"False easting\",4321000.0,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"False northing\",3210000.0,LENGTHUNIT[\"metre\",1.0]]"
				+ "],CS[Cartesian,2],AXIS[\"(Y)\",north,ORDER[1]],"
				+ "AXIS[\"(X)\",east,ORDER[2]],LENGTHUNIT[\"metre\",1.0],"
				+ "USAGE[SCOPE[\"Description of a purpose\"],AREA[\"An area description\"]],"
				+ "ID[\"EuroGeographics\",\"ETRS-LAEA\"]]";

		CRS crs = CRSReader.read(text, true);
		ProjectedCoordinateReferenceSystem projectedCrs = CRSReader
				.readProjected(text);
		assertEquals(crs, projectedCrs);
		ProjectedCoordinateReferenceSystem projectedGeographicCrs = CRSReader
				.readProjectedGeographic(text);
		assertEquals(crs, projectedGeographicCrs);
		assertEquals(CRSType.PROJECTED, projectedGeographicCrs.getType());
		assertEquals("ETRS89 Lambert Azimuthal Equal Area CRS",
				projectedGeographicCrs.getName());
		assertEquals(CRSType.GEOGRAPHIC, projectedGeographicCrs.getBaseType());
		assertEquals("ETRS89", projectedGeographicCrs.getBaseName());
		assertEquals("ETRS89",
				projectedGeographicCrs.getReferenceFrame().getName());
		assertEquals("GRS 80", projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getName());
		assertEquals(6378137, projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(298.257222101, projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, projectedGeographicCrs
				.getReferenceFrame().getEllipsoid().getUnit().getType());
		assertEquals("metre", projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getUnit().getName());
		assertEquals(1.0, projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getUnit().getConversionFactor(), 0);
		assertEquals("EuroGeographics",
				projectedGeographicCrs.getBaseIdentifiers().get(0).getName());
		assertEquals("ETRS89-LatLon", projectedGeographicCrs
				.getBaseIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("LAEA",
				projectedGeographicCrs.getMapProjection().getName());
		assertEquals("Lambert Azimuthal Equal Area", projectedGeographicCrs
				.getMapProjection().getMethod().getName());
		assertEquals("EPSG", projectedGeographicCrs.getMapProjection()
				.getMethod().getIdentifiers().get(0).getName());
		assertEquals("9820", projectedGeographicCrs.getMapProjection()
				.getMethod().getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Latitude of origin", projectedGeographicCrs
				.getMapProjection().getParameters().get(0).getName());
		assertEquals(52.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(0).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(0).getUnit().getType());
		assertEquals("degree", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(0).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(0).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("Longitude of origin", projectedGeographicCrs
				.getMapProjection().getParameters().get(1).getName());
		assertEquals(10.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(1).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(1).getUnit().getType());
		assertEquals("degree", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(1).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(1).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("False easting", projectedGeographicCrs.getMapProjection()
				.getParameters().get(2).getName());
		assertEquals(4321000.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(2).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(2).getUnit().getType());
		assertEquals("metre", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(2).getUnit().getName());
		assertEquals(1.0,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(2).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("False northing", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(3).getName());
		assertEquals(3210000.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(3).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(3).getUnit().getType());
		assertEquals("metre", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(3).getUnit().getName());
		assertEquals(1.0,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(3).getUnit()
						.getConversionFactor(),
				0);
		assertEquals(CoordinateSystemType.CARTESIAN,
				projectedGeographicCrs.getCoordinateSystem().getType());
		assertEquals(2,
				projectedGeographicCrs.getCoordinateSystem().getDimension());
		assertEquals("Y", projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, projectedGeographicCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(0).getOrder().intValue());
		assertEquals("X", projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.EAST, projectedGeographicCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(2, projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(1).getOrder().intValue());
		assertEquals(UnitType.LENGTHUNIT, projectedGeographicCrs
				.getCoordinateSystem().getUnit().getType());
		assertEquals("metre", projectedGeographicCrs.getCoordinateSystem()
				.getUnit().getName());
		assertEquals(1.0, projectedGeographicCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		assertEquals("Description of a purpose",
				projectedGeographicCrs.getUsages().get(0).getScope());
		assertEquals("An area description", projectedGeographicCrs.getUsages()
				.get(0).getExtent().getAreaDescription());
		assertEquals("EuroGeographics",
				projectedGeographicCrs.getIdentifiers().get(0).getName());
		assertEquals("ETRS-LAEA", projectedGeographicCrs.getIdentifiers().get(0)
				.getUniqueIdentifier());
		text = text.replaceAll("6378137", "6378137.0");
		assertEquals(text, projectedGeographicCrs.toString());
		assertEquals(text, CRSWriter.write(projectedGeographicCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(projectedGeographicCrs));

		text = "PROJCRS[\"NAD27 / Texas South Central\","
				+ "BASEGEOGCRS[\"NAD27\","
				+ "DATUM[\"North American Datum 1927\","
				+ "ELLIPSOID[\"Clarke 1866\",20925832.164,294.97869821,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219]]]],"
				+ "CONVERSION[\"Texas South Central SPCS27\","
				+ "METHOD[\"Lambert Conic Conformal (2SP)\",ID[\"EPSG\",9802]],"
				+ "PARAMETER[\"Latitude of false origin\",27.83333333333333,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8821]],"
				+ "PARAMETER[\"Longitude of false origin\",-99.0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8822]],"
				+ "PARAMETER[\"Latitude of 1st standard parallel\",28.383333333333,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8823]],"
				+ "PARAMETER[\"Latitude of 2nd standard parallel\",30.283333333333,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8824]],"
				+ "PARAMETER[\"Easting at false origin\",2000000.0,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8826]],"
				+ "PARAMETER[\"Northing at false origin\",0.0,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8827]]],"
				+ "CS[Cartesian,2],AXIS[\"(X)\",east]," + "AXIS[\"(Y)\",north],"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219],"
				+ "REMARK[\"Fundamental point: Meade's Ranch KS, latitude 39°13'26.686\"\"N,"
				+ "longitude 98°32'30.506\"\"W.\"]]";

		crs = CRSReader.read(text, true);
		projectedCrs = CRSReader.readProjected(text);
		assertEquals(crs, projectedCrs);
		projectedGeographicCrs = CRSReader.readProjectedGeographic(text);
		assertEquals(crs, projectedGeographicCrs);
		assertEquals(CRSType.PROJECTED, projectedGeographicCrs.getType());
		assertEquals("NAD27 / Texas South Central",
				projectedGeographicCrs.getName());
		assertEquals(CRSType.GEOGRAPHIC, projectedGeographicCrs.getBaseType());
		assertEquals("NAD27", projectedGeographicCrs.getBaseName());
		assertEquals("North American Datum 1927",
				projectedGeographicCrs.getReferenceFrame().getName());
		assertEquals("Clarke 1866", projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getName());
		assertEquals(20925832.164, projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(294.97869821, projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, projectedGeographicCrs
				.getReferenceFrame().getEllipsoid().getUnit().getType());
		assertEquals("US survey foot", projectedGeographicCrs
				.getReferenceFrame().getEllipsoid().getUnit().getName());
		assertEquals(0.304800609601219,
				projectedGeographicCrs.getReferenceFrame().getEllipsoid()
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Texas South Central SPCS27",
				projectedGeographicCrs.getMapProjection().getName());
		assertEquals("Lambert Conic Conformal (2SP)", projectedGeographicCrs
				.getMapProjection().getMethod().getName());
		assertEquals("EPSG", projectedGeographicCrs.getMapProjection()
				.getMethod().getIdentifiers().get(0).getName());
		assertEquals("9802", projectedGeographicCrs.getMapProjection()
				.getMethod().getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Latitude of false origin", projectedGeographicCrs
				.getMapProjection().getParameters().get(0).getName());
		assertEquals(27.83333333333333, projectedGeographicCrs
				.getMapProjection().getOperationParameters().get(0).getValue(),
				0);
		assertEquals(UnitType.ANGLEUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(0).getUnit().getType());
		assertEquals("degree", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(0).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(0).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("EPSG", projectedGeographicCrs.getMapProjection()
				.getParameters().get(0).getIdentifiers().get(0).getName());
		assertEquals("8821",
				projectedGeographicCrs.getMapProjection().getParameters().get(0)
						.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Longitude of false origin", projectedGeographicCrs
				.getMapProjection().getParameters().get(1).getName());
		assertEquals(-99.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(1).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(1).getUnit().getType());
		assertEquals("degree", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(1).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(1).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("EPSG", projectedGeographicCrs.getMapProjection()
				.getParameters().get(1).getIdentifiers().get(0).getName());
		assertEquals("8822",
				projectedGeographicCrs.getMapProjection().getParameters().get(1)
						.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Latitude of 1st standard parallel", projectedGeographicCrs
				.getMapProjection().getParameters().get(2).getName());
		assertEquals(28.383333333333, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(2).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(2).getUnit().getType());
		assertEquals("degree", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(2).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(2).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("EPSG", projectedGeographicCrs.getMapProjection()
				.getParameters().get(2).getIdentifiers().get(0).getName());
		assertEquals("8823",
				projectedGeographicCrs.getMapProjection().getParameters().get(2)
						.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Latitude of 2nd standard parallel", projectedGeographicCrs
				.getMapProjection().getParameters().get(3).getName());
		assertEquals(30.283333333333, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(3).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(3).getUnit().getType());
		assertEquals("degree", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(3).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(3).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("EPSG", projectedGeographicCrs.getMapProjection()
				.getParameters().get(3).getIdentifiers().get(0).getName());
		assertEquals("8824",
				projectedGeographicCrs.getMapProjection().getParameters().get(3)
						.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Easting at false origin", projectedGeographicCrs
				.getMapProjection().getParameters().get(4).getName());
		assertEquals(2000000.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(4).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(4).getUnit().getType());
		assertEquals("US survey foot", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(4).getUnit().getName());
		assertEquals(0.304800609601219,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(4).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("EPSG", projectedGeographicCrs.getMapProjection()
				.getParameters().get(4).getIdentifiers().get(0).getName());
		assertEquals("8826",
				projectedGeographicCrs.getMapProjection().getParameters().get(4)
						.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Northing at false origin", projectedGeographicCrs
				.getMapProjection().getParameters().get(5).getName());
		assertEquals(0.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(5).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(5).getUnit().getType());
		assertEquals("US survey foot", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(5).getUnit().getName());
		assertEquals(0.304800609601219,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(5).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("EPSG", projectedGeographicCrs.getMapProjection()
				.getParameters().get(5).getIdentifiers().get(0).getName());
		assertEquals("8827",
				projectedGeographicCrs.getMapProjection().getParameters().get(5)
						.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals(CoordinateSystemType.CARTESIAN,
				projectedGeographicCrs.getCoordinateSystem().getType());
		assertEquals(2,
				projectedGeographicCrs.getCoordinateSystem().getDimension());
		assertEquals("X", projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.EAST, projectedGeographicCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals("Y", projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, projectedGeographicCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(UnitType.LENGTHUNIT, projectedGeographicCrs
				.getCoordinateSystem().getUnit().getType());
		assertEquals("US survey foot", projectedGeographicCrs
				.getCoordinateSystem().getUnit().getName());
		assertEquals(0.304800609601219, projectedGeographicCrs
				.getCoordinateSystem().getUnit().getConversionFactor(), 0);
		assertEquals(
				"Fundamental point: Meade's Ranch KS, latitude 39°13'26.686\"N,longitude 98°32'30.506\"W.",
				projectedGeographicCrs.getRemark());
		text = text.replaceAll("20925832.164", "2.0925832164E7");
		assertEquals(text, projectedGeographicCrs.toString());
		assertEquals(text, CRSWriter.write(projectedGeographicCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(projectedGeographicCrs));

		text = "PROJCRS[\"NAD83 UTM 10\",BASEGEOGCRS[\"NAD83(86)\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137,298.257222101]],"
				+ "PRIMEM[\"Greenwich\",0]],CONVERSION[\"UTM zone 10N\","
				+ "METHOD[\"Transverse Mercator\"],"
				+ "PARAMETER[\"Latitude of natural origin\",0.0],"
				+ "PARAMETER[\"Longitude of natural origin\",-123.0],"
				+ "PARAMETER[\"Scale factor\",0.9996],"
				+ "PARAMETER[\"False easting\",500000.0],"
				+ "PARAMETER[\"False northing\",0.0],ID[\"EPSG\",16010]],"
				+ "CS[Cartesian,2]," + "AXIS[\"(E)\",east,ORDER[1]],"
				+ "AXIS[\"(N)\",north,ORDER[2]],LENGTHUNIT[\"metre\",1.0],"
				+ "REMARK[\"In this example parameter value units are not given. This is allowed for backward compatibility. However it is strongly recommended that units are explicitly given in the string, as in the previous two examples.\"]]";

		crs = CRSReader.read(text, true);
		projectedCrs = CRSReader.readProjected(text);
		assertEquals(crs, projectedCrs);
		projectedGeographicCrs = CRSReader.readProjectedGeographic(text);
		assertEquals(crs, projectedGeographicCrs);
		assertEquals(CRSType.PROJECTED, projectedGeographicCrs.getType());
		assertEquals("NAD83 UTM 10", projectedGeographicCrs.getName());
		assertEquals(CRSType.GEOGRAPHIC, projectedGeographicCrs.getBaseType());
		assertEquals("NAD83(86)", projectedGeographicCrs.getBaseName());
		assertEquals("North American Datum 1983",
				projectedGeographicCrs.getReferenceFrame().getName());
		assertEquals("GRS 1980", projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getName());
		assertEquals(6378137, projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(298.257222101, projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals("Greenwich", projectedGeographicCrs.getReferenceFrame()
				.getPrimeMeridian().getName());
		assertEquals(0, projectedGeographicCrs.getReferenceFrame()
				.getPrimeMeridian().getIrmLongitude(), 0);
		assertEquals("UTM zone 10N",
				projectedGeographicCrs.getMapProjection().getName());
		assertEquals("Transverse Mercator", projectedGeographicCrs
				.getMapProjection().getMethod().getName());
		assertEquals("Latitude of natural origin", projectedGeographicCrs
				.getMapProjection().getParameters().get(0).getName());
		assertEquals(0.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(0).getValue(), 0);
		assertEquals("Longitude of natural origin", projectedGeographicCrs
				.getMapProjection().getParameters().get(1).getName());
		assertEquals(-123.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(1).getValue(), 0);
		assertEquals("Scale factor", projectedGeographicCrs.getMapProjection()
				.getParameters().get(2).getName());
		assertEquals(0.9996, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(2).getValue(), 0);
		assertEquals("False easting", projectedGeographicCrs.getMapProjection()
				.getParameters().get(3).getName());
		assertEquals(500000.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(3).getValue(), 0);
		assertEquals("False northing", projectedGeographicCrs.getMapProjection()
				.getParameters().get(4).getName());
		assertEquals(0.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(4).getValue(), 0);
		assertEquals("EPSG", projectedGeographicCrs.getMapProjection()
				.getIdentifiers().get(0).getName());
		assertEquals("16010", projectedGeographicCrs.getMapProjection()
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals(CoordinateSystemType.CARTESIAN,
				projectedGeographicCrs.getCoordinateSystem().getType());
		assertEquals(2,
				projectedGeographicCrs.getCoordinateSystem().getDimension());
		assertEquals("E", projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.EAST, projectedGeographicCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(0).getOrder().intValue());
		assertEquals("N", projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, projectedGeographicCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(2, projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(1).getOrder().intValue());
		assertEquals(UnitType.LENGTHUNIT, projectedGeographicCrs
				.getCoordinateSystem().getUnit().getType());
		assertEquals("metre", projectedGeographicCrs.getCoordinateSystem()
				.getUnit().getName());
		assertEquals(1.0, projectedGeographicCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		assertEquals(
				"In this example parameter value units are not given. This is allowed for backward compatibility. However it is strongly recommended that units are explicitly given in the string, as in the previous two examples.",
				projectedGeographicCrs.getRemark());
		text = text.replaceAll("6378137", "6378137.0").replace(",0]", ",0.0]");
		assertEquals(text, projectedGeographicCrs.toString());
		assertEquals(text, CRSWriter.write(projectedGeographicCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(projectedGeographicCrs));

		text = "PROJCRS[\"WGS 84 (G1762) / UTM zone 31N 3D\",BASEGEOGCRS[\"WGS 84\","
				+ "DATUM[\"World Geodetic System of 1984 (G1762)\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,LENGTHUNIT[\"metre\",1.0]]]],"
				+ "CONVERSION[\"UTM zone 31N 3D\","
				+ "METHOD[\"Transverse Mercator (3D)\"],"
				+ "PARAMETER[\"Latitude of origin\",0.0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Longitude of origin\",3.0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Scale factor\",0.9996,SCALEUNIT[\"unity\",1.0]],"
				+ "PARAMETER[\"False easting\",500000.0,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"False northing\",0.0,LENGTHUNIT[\"metre\",1.0]]],"
				+ "CS[Cartesian,3],AXIS[\"(E)\",east,ORDER[1]],"
				+ "AXIS[\"(N)\",north,ORDER[2]],"
				+ "AXIS[\"ellipsoidal height (h)\",up,ORDER[3]],"
				+ "LENGTHUNIT[\"metre\",1.0]]";

		crs = CRSReader.read(text, true);
		projectedCrs = CRSReader.readProjected(text);
		assertEquals(crs, projectedCrs);
		projectedGeographicCrs = CRSReader.readProjectedGeographic(text);
		assertEquals(crs, projectedGeographicCrs);
		assertEquals(CRSType.PROJECTED, projectedGeographicCrs.getType());
		assertEquals("WGS 84 (G1762) / UTM zone 31N 3D",
				projectedGeographicCrs.getName());
		assertEquals(CRSType.GEOGRAPHIC, projectedGeographicCrs.getBaseType());
		assertEquals("WGS 84", projectedGeographicCrs.getBaseName());
		assertEquals("World Geodetic System of 1984 (G1762)",
				projectedGeographicCrs.getReferenceFrame().getName());
		assertEquals("WGS 84", projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getName());
		assertEquals(6378137, projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(298.257223563, projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, projectedGeographicCrs
				.getReferenceFrame().getEllipsoid().getUnit().getType());
		assertEquals("metre", projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getUnit().getName());
		assertEquals(1.0, projectedGeographicCrs.getReferenceFrame()
				.getEllipsoid().getUnit().getConversionFactor(), 0);
		assertEquals("UTM zone 31N 3D",
				projectedGeographicCrs.getMapProjection().getName());
		assertEquals("Transverse Mercator (3D)", projectedGeographicCrs
				.getMapProjection().getMethod().getName());
		assertEquals("Latitude of origin", projectedGeographicCrs
				.getMapProjection().getParameters().get(0).getName());
		assertEquals(0.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(0).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(0).getUnit().getType());
		assertEquals("degree", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(0).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(0).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("Longitude of origin", projectedGeographicCrs
				.getMapProjection().getParameters().get(1).getName());
		assertEquals(3.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(1).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(1).getUnit().getType());
		assertEquals("degree", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(1).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(1).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("Scale factor", projectedGeographicCrs.getMapProjection()
				.getParameters().get(2).getName());
		assertEquals(0.9996, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(2).getValue(), 0);
		assertEquals(UnitType.SCALEUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(2).getUnit().getType());
		assertEquals("unity", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(2).getUnit().getName());
		assertEquals(1.0,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(2).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("False easting", projectedGeographicCrs.getMapProjection()
				.getParameters().get(3).getName());
		assertEquals(500000.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(3).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(3).getUnit().getType());
		assertEquals("metre", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(3).getUnit().getName());
		assertEquals(1.0,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(3).getUnit()
						.getConversionFactor(),
				0);
		assertEquals("False northing", projectedGeographicCrs.getMapProjection()
				.getParameters().get(4).getName());
		assertEquals(0.0, projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(4).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(4).getUnit().getType());
		assertEquals("metre", projectedGeographicCrs.getMapProjection()
				.getOperationParameters().get(4).getUnit().getName());
		assertEquals(1.0,
				projectedGeographicCrs.getMapProjection()
						.getOperationParameters().get(4).getUnit()
						.getConversionFactor(),
				0);
		assertEquals(CoordinateSystemType.CARTESIAN,
				projectedGeographicCrs.getCoordinateSystem().getType());
		assertEquals(3,
				projectedGeographicCrs.getCoordinateSystem().getDimension());
		assertEquals("E", projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(0).getAbbreviation());
		assertEquals(AxisDirectionType.EAST, projectedGeographicCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(0).getOrder().intValue());
		assertEquals("N", projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(1).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, projectedGeographicCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(2, projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(1).getOrder().intValue());
		assertEquals("ellipsoidal height", projectedGeographicCrs
				.getCoordinateSystem().getAxes().get(2).getName());
		assertEquals("h", projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(2).getAbbreviation());
		assertEquals(AxisDirectionType.UP, projectedGeographicCrs
				.getCoordinateSystem().getAxes().get(2).getDirection());
		assertEquals(3, projectedGeographicCrs.getCoordinateSystem().getAxes()
				.get(2).getOrder().intValue());
		assertEquals(UnitType.LENGTHUNIT, projectedGeographicCrs
				.getCoordinateSystem().getUnit().getType());
		assertEquals("metre", projectedGeographicCrs.getCoordinateSystem()
				.getUnit().getName());
		assertEquals(1.0, projectedGeographicCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		text = text.replaceAll("6378137", "6378137.0");
		assertEquals(text, projectedGeographicCrs.toString());
		assertEquals(text, CRSWriter.write(projectedGeographicCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(projectedGeographicCrs));

	}

	/**
	 * Test vertical reference frame
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testVerticalReferenceFrame() throws IOException {

		String text = "VDATUM[\"Newlyn\"]";
		CRSReader reader = new CRSReader(text);
		VerticalReferenceFrame verticalReferenceFrame = reader
				.readVerticalReferenceFrame();
		assertEquals("Newlyn", verticalReferenceFrame.getName());
		reader.close();
		assertEquals(text, verticalReferenceFrame.toString());

		text = "VERTICALDATUM[\"Newlyn\",ANCHOR[\"Mean Sea Level 1915 to 1921.\"]]";
		reader = new CRSReader(text);
		verticalReferenceFrame = reader.readVerticalReferenceFrame();
		assertEquals("Newlyn", verticalReferenceFrame.getName());
		assertEquals("Mean Sea Level 1915 to 1921.",
				verticalReferenceFrame.getAnchor());
		reader.close();
		assertEquals(text.replaceAll("VERTICALDATUM", "VDATUM"),
				verticalReferenceFrame.toString());

	}

	/**
	 * Test vertical coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testVerticalCoordinateReferenceSystem() throws IOException {

		String text = "VERTCRS[\"NAVD88\","
				+ "VDATUM[\"North American Vertical Datum 1988\"],"
				+ "CS[vertical,1],AXIS[\"gravity-related height (H)\",up],"
				+ "LENGTHUNIT[\"metre\",1.0]]";

		CRS crs = CRSReader.read(text, true);
		VerticalCoordinateReferenceSystem verticalCrs = CRSReader
				.readVertical(text);
		assertEquals(crs, verticalCrs);
		assertEquals(CRSType.VERTICAL, verticalCrs.getType());
		assertEquals("NAVD88", verticalCrs.getName());
		assertEquals("North American Vertical Datum 1988",
				verticalCrs.getReferenceFrame().getName());
		assertEquals(CoordinateSystemType.VERTICAL,
				verticalCrs.getCoordinateSystem().getType());
		assertEquals(1, verticalCrs.getCoordinateSystem().getDimension());
		assertEquals("gravity-related height",
				verticalCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("H", verticalCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.UP, verticalCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.LENGTHUNIT,
				verticalCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("metre",
				verticalCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1.0, verticalCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		assertEquals(text, verticalCrs.toString());
		assertEquals(text, CRSWriter.write(verticalCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(verticalCrs));

		text = "VERTCRS[\"CGVD2013\","
				+ "VRF[\"Canadian Geodetic Vertical Datum of 2013\"],"
				+ "CS[vertical,1],AXIS[\"gravity-related height (H)\",up],"
				+ "LENGTHUNIT[\"metre\",1.0],"
				+ "GEOIDMODEL[\"CGG2013\",ID[\"EPSG\",6648]]]";

		crs = CRSReader.read(text, true);
		verticalCrs = CRSReader.readVertical(text);
		assertEquals(crs, verticalCrs);
		assertEquals(CRSType.VERTICAL, verticalCrs.getType());
		assertEquals("CGVD2013", verticalCrs.getName());
		assertEquals("Canadian Geodetic Vertical Datum of 2013",
				verticalCrs.getReferenceFrame().getName());
		assertEquals(CoordinateSystemType.VERTICAL,
				verticalCrs.getCoordinateSystem().getType());
		assertEquals(1, verticalCrs.getCoordinateSystem().getDimension());
		assertEquals("gravity-related height",
				verticalCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("H", verticalCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.UP, verticalCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.LENGTHUNIT,
				verticalCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("metre",
				verticalCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1.0, verticalCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		assertEquals("CGG2013", verticalCrs.getGeoidModelName());
		assertEquals("EPSG", verticalCrs.getGeoidModelIdentifier().getName());
		assertEquals("6648",
				verticalCrs.getGeoidModelIdentifier().getUniqueIdentifier());
		text = text.replaceAll("VRF", "VDATUM");
		assertEquals(text, verticalCrs.toString());
		assertEquals(text, CRSWriter.write(verticalCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(verticalCrs));

		text = "VERTCRS[\"RH2000\","
				+ "DYNAMIC[FRAMEEPOCH[2000.0],MODEL[\"NKG2016LU\"]],"
				+ "VDATUM[\"Rikets Hojdsystem 2000\"],CS[vertical,1],"
				+ "AXIS[\"gravity-related height (H)\",up],"
				+ "LENGTHUNIT[\"metre\",1.0]]";

		crs = CRSReader.read(text, true);
		verticalCrs = CRSReader.readVertical(text);
		assertEquals(crs, verticalCrs);
		assertEquals(CRSType.VERTICAL, verticalCrs.getType());
		assertEquals("RH2000", verticalCrs.getName());
		assertEquals(2000.0, verticalCrs.getDynamic().getReferenceEpoch(), 0);
		assertEquals("NKG2016LU",
				verticalCrs.getDynamic().getDeformationModelName());
		assertEquals("Rikets Hojdsystem 2000",
				verticalCrs.getReferenceFrame().getName());
		assertEquals(CoordinateSystemType.VERTICAL,
				verticalCrs.getCoordinateSystem().getType());
		assertEquals(1, verticalCrs.getCoordinateSystem().getDimension());
		assertEquals("gravity-related height",
				verticalCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("H", verticalCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.UP, verticalCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.LENGTHUNIT,
				verticalCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("metre",
				verticalCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1.0, verticalCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		assertEquals(text, verticalCrs.toString());
		assertEquals(text, CRSWriter.write(verticalCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(verticalCrs));

	}

	/**
	 * Test engineering coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testEngineeringCoordinateReferenceSystem() throws IOException {

		String text = "ENGCRS[\"A construction site CRS\","
				+ "EDATUM[\"P1\",ANCHOR[\"Peg in south corner\"]],"
				+ "CS[Cartesian,2],AXIS[\"site east\",southWest,ORDER[1]],"
				+ "AXIS[\"site north\",southEast,ORDER[2]],"
				+ "LENGTHUNIT[\"metre\",1.0],"
				+ "USAGE[SCOPE[\"Construction\"],TIMEEXTENT[\"date/time t1\",\"date/time t2\"]]]";

		CRS crs = CRSReader.read(text, true);
		EngineeringCoordinateReferenceSystem engineeringCrs = CRSReader
				.readEngineering(text);
		assertEquals(crs, engineeringCrs);
		assertEquals(CRSType.ENGINEERING, engineeringCrs.getType());
		assertEquals("A construction site CRS", engineeringCrs.getName());
		assertEquals("P1", engineeringCrs.getDatum().getName());
		assertEquals("Peg in south corner",
				engineeringCrs.getDatum().getAnchor());
		assertEquals(CoordinateSystemType.CARTESIAN,
				engineeringCrs.getCoordinateSystem().getType());
		assertEquals(2, engineeringCrs.getCoordinateSystem().getDimension());
		assertEquals("site east", engineeringCrs.getCoordinateSystem().getAxes()
				.get(0).getName());
		assertEquals(AxisDirectionType.SOUTH_WEST, engineeringCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, engineeringCrs.getCoordinateSystem().getAxes().get(0)
				.getOrder().intValue());
		assertEquals("site north", engineeringCrs.getCoordinateSystem()
				.getAxes().get(1).getName());
		assertEquals(AxisDirectionType.SOUTH_EAST, engineeringCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(2, engineeringCrs.getCoordinateSystem().getAxes().get(1)
				.getOrder().intValue());
		assertEquals(UnitType.LENGTHUNIT,
				engineeringCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("metre",
				engineeringCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1.0, engineeringCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		assertEquals(text, engineeringCrs.toString());
		assertEquals(text, CRSWriter.write(engineeringCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(engineeringCrs));

		text = "ENGINEERINGCRS[\"Astra Minas Grid\","
				+ "ENGINEERINGDATUM[\"Astra Minas\"],CS[Cartesian,2],"
				+ "AXIS[\"northing (X)\",north,ORDER[1]],"
				+ "AXIS[\"westing (Y)\",west,ORDER[2]],"
				+ "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",5800]]";

		crs = CRSReader.read(text, true);
		engineeringCrs = CRSReader.readEngineering(text);
		assertEquals(crs, engineeringCrs);
		assertEquals(CRSType.ENGINEERING, engineeringCrs.getType());
		assertEquals("Astra Minas Grid", engineeringCrs.getName());
		assertEquals("Astra Minas", engineeringCrs.getDatum().getName());
		assertEquals(CoordinateSystemType.CARTESIAN,
				engineeringCrs.getCoordinateSystem().getType());
		assertEquals(2, engineeringCrs.getCoordinateSystem().getDimension());
		assertEquals("northing", engineeringCrs.getCoordinateSystem().getAxes()
				.get(0).getName());
		assertEquals("X", engineeringCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, engineeringCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, engineeringCrs.getCoordinateSystem().getAxes().get(0)
				.getOrder().intValue());
		assertEquals("westing", engineeringCrs.getCoordinateSystem().getAxes()
				.get(1).getName());
		assertEquals("Y", engineeringCrs.getCoordinateSystem().getAxes().get(1)
				.getAbbreviation());
		assertEquals(AxisDirectionType.WEST, engineeringCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(2, engineeringCrs.getCoordinateSystem().getAxes().get(1)
				.getOrder().intValue());
		assertEquals(UnitType.LENGTHUNIT,
				engineeringCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("metre",
				engineeringCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1.0, engineeringCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		assertEquals("EPSG", engineeringCrs.getIdentifiers().get(0).getName());
		assertEquals("5800",
				engineeringCrs.getIdentifiers().get(0).getUniqueIdentifier());
		text = text.replaceAll("ENGINEERINGCRS", "ENGCRS")
				.replaceAll("ENGINEERINGDATUM", "EDATUM");
		assertEquals(text, engineeringCrs.toString());
		assertEquals(text, CRSWriter.write(engineeringCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(engineeringCrs));

		text = "ENGCRS[\"A ship-centred CRS\","
				+ "EDATUM[\"Ship reference point\",ANCHOR[\"Centre of buoyancy\"]],"
				+ "CS[Cartesian,3],AXIS[\"(x)\",forward],"
				+ "AXIS[\"(y)\",starboard],AXIS[\"(z)\",down],"
				+ "LENGTHUNIT[\"metre\",1.0]]";

		crs = CRSReader.read(text, true);
		engineeringCrs = CRSReader.readEngineering(text);
		assertEquals(crs, engineeringCrs);
		assertEquals(CRSType.ENGINEERING, engineeringCrs.getType());
		assertEquals("A ship-centred CRS", engineeringCrs.getName());
		assertEquals("Ship reference point",
				engineeringCrs.getDatum().getName());
		assertEquals("Centre of buoyancy",
				engineeringCrs.getDatum().getAnchor());
		assertEquals(CoordinateSystemType.CARTESIAN,
				engineeringCrs.getCoordinateSystem().getType());
		assertEquals(3, engineeringCrs.getCoordinateSystem().getDimension());
		assertEquals("x", engineeringCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.FORWARD, engineeringCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals("y", engineeringCrs.getCoordinateSystem().getAxes().get(1)
				.getAbbreviation());
		assertEquals(AxisDirectionType.STARBOARD, engineeringCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals("z", engineeringCrs.getCoordinateSystem().getAxes().get(2)
				.getAbbreviation());
		assertEquals(AxisDirectionType.DOWN, engineeringCrs
				.getCoordinateSystem().getAxes().get(2).getDirection());
		assertEquals(UnitType.LENGTHUNIT,
				engineeringCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("metre",
				engineeringCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1.0, engineeringCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		assertEquals(text, engineeringCrs.toString());
		assertEquals(text, CRSWriter.write(engineeringCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(engineeringCrs));

		text = "ENGCRS[\"An analogue image CRS\","
				+ "EDATUM[\"Image reference point\","
				+ "ANCHOR[\"Top left corner of image = 0,0\"]],"
				+ "CS[Cartesian,2],AXIS[\"Column (x)\",columnPositive],"
				+ "AXIS[\"Row (y)\",rowPositive],"
				+ "LENGTHUNIT[\"micrometre\",1E-6]]";

		crs = CRSReader.read(text, true);
		engineeringCrs = CRSReader.readEngineering(text);
		assertEquals(crs, engineeringCrs);
		assertEquals(CRSType.ENGINEERING, engineeringCrs.getType());
		assertEquals("An analogue image CRS", engineeringCrs.getName());
		assertEquals("Image reference point",
				engineeringCrs.getDatum().getName());
		assertEquals("Top left corner of image = 0,0",
				engineeringCrs.getDatum().getAnchor());
		assertEquals(CoordinateSystemType.CARTESIAN,
				engineeringCrs.getCoordinateSystem().getType());
		assertEquals(2, engineeringCrs.getCoordinateSystem().getDimension());
		assertEquals("Column", engineeringCrs.getCoordinateSystem().getAxes()
				.get(0).getName());
		assertEquals("x", engineeringCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.COLUMN_POSITIVE, engineeringCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals("Row", engineeringCrs.getCoordinateSystem().getAxes()
				.get(1).getName());
		assertEquals("y", engineeringCrs.getCoordinateSystem().getAxes().get(1)
				.getAbbreviation());
		assertEquals(AxisDirectionType.ROW_POSITIVE, engineeringCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(UnitType.LENGTHUNIT,
				engineeringCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("micrometre",
				engineeringCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1E-6, engineeringCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		text = text.replaceAll("1E-6", "1.0E-6");
		assertEquals(text, engineeringCrs.toString());
		assertEquals(text, CRSWriter.write(engineeringCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(engineeringCrs));

		text = "ENGCRS[\"A digital image CRS\","
				+ "EDATUM[\"Image reference point\","
				+ "ANCHOR[\"Top left corner of image = 0,0\"]],"
				+ "CS[ordinal,2],"
				+ "AXIS[\"Column pixel (x)\",columnPositive,ORDER[1]],"
				+ "AXIS[\"Row pixel (y)\",rowPositive,ORDER[2]]]";

		crs = CRSReader.read(text, true);
		engineeringCrs = CRSReader.readEngineering(text);
		assertEquals(crs, engineeringCrs);
		assertEquals(CRSType.ENGINEERING, engineeringCrs.getType());
		assertEquals("A digital image CRS", engineeringCrs.getName());
		assertEquals("Image reference point",
				engineeringCrs.getDatum().getName());
		assertEquals("Top left corner of image = 0,0",
				engineeringCrs.getDatum().getAnchor());
		assertEquals(CoordinateSystemType.ORDINAL,
				engineeringCrs.getCoordinateSystem().getType());
		assertEquals(2, engineeringCrs.getCoordinateSystem().getDimension());
		assertEquals("Column pixel", engineeringCrs.getCoordinateSystem()
				.getAxes().get(0).getName());
		assertEquals("x", engineeringCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.COLUMN_POSITIVE, engineeringCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, engineeringCrs.getCoordinateSystem().getAxes().get(0)
				.getOrder().intValue());
		assertEquals("Row pixel", engineeringCrs.getCoordinateSystem().getAxes()
				.get(1).getName());
		assertEquals("y", engineeringCrs.getCoordinateSystem().getAxes().get(1)
				.getAbbreviation());
		assertEquals(AxisDirectionType.ROW_POSITIVE, engineeringCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(2, engineeringCrs.getCoordinateSystem().getAxes().get(1)
				.getOrder().intValue());
		assertEquals(text, engineeringCrs.toString());
		assertEquals(text, CRSWriter.write(engineeringCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(engineeringCrs));

	}

	/**
	 * Test parametric coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testParametricCoordinateReferenceSystem() throws IOException {

		String text = "PARAMETRICCRS[\"WMO standard atmosphere layer 0\","
				+ "PDATUM[\"Mean Sea Level\",ANCHOR[\"1013.25 hPa at 15°C\"]],"
				+ "CS[parametric,1],"
				+ "AXIS[\"pressure (hPa)\",up],PARAMETRICUNIT[\"HectoPascal\",100.0]]";

		CRS crs = CRSReader.read(text, true);
		ParametricCoordinateReferenceSystem parametricCrs = CRSReader
				.readParametric(text);
		assertEquals(crs, parametricCrs);
		assertEquals(CRSType.PARAMETRIC, parametricCrs.getType());
		assertEquals("WMO standard atmosphere layer 0",
				parametricCrs.getName());
		assertEquals("Mean Sea Level", parametricCrs.getDatum().getName());
		assertEquals("1013.25 hPa at 15°C",
				parametricCrs.getDatum().getAnchor());
		assertEquals(CoordinateSystemType.PARAMETRIC,
				parametricCrs.getCoordinateSystem().getType());
		assertEquals(1, parametricCrs.getCoordinateSystem().getDimension());
		assertEquals("pressure",
				parametricCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("hPa", parametricCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.UP, parametricCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.PARAMETRICUNIT,
				parametricCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("HectoPascal",
				parametricCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(100.0, parametricCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);
		assertEquals(text, parametricCrs.toString());
		assertEquals(text, CRSWriter.write(parametricCrs));
		assertEquals(WKTUtils.pretty(text),
				CRSWriter.writePretty(parametricCrs));

	}

	/**
	 * Test temporal datum
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testTemporalDatum() throws IOException {

		String text = "TIMEDATUM[\"Gregorian calendar\",CALENDAR[\"proleptic Gregorian\"],"
				+ "TIMEORIGIN[0000-01-01]]";
		CRSReader reader = new CRSReader(text);
		TemporalDatum temporalDatum = reader.readTemporalDatum();
		assertEquals("Gregorian calendar", temporalDatum.getName());
		assertEquals("proleptic Gregorian", temporalDatum.getCalendar());
		assertEquals("0000-01-01", temporalDatum.getOrigin());
		assertTrue(temporalDatum.hasOriginDateTime());
		assertEquals("0000-01-01",
				temporalDatum.getOriginDateTime().toString());
		reader.close();
		text = text.replaceAll("TIMEDATUM", "TDATUM");
		assertEquals(text, temporalDatum.toString());

		text = "TDATUM[\"Gregorian calendar\",TIMEORIGIN[\"0001 January 1st\"]]";
		reader = new CRSReader(text);
		temporalDatum = reader.readTemporalDatum();
		assertEquals("Gregorian calendar", temporalDatum.getName());
		assertFalse(temporalDatum.hasCalendar());
		assertEquals("0001 January 1st", temporalDatum.getOrigin());
		assertFalse(temporalDatum.hasOriginDateTime());
		reader.close();
		assertEquals(text, temporalDatum.toString());

		text = "TDATUM[\"Gregorian calendar\"]";
		reader = new CRSReader(text);
		temporalDatum = reader.readTemporalDatum();
		assertFalse(temporalDatum.hasCalendar());
		assertFalse(temporalDatum.hasOrigin());
		assertFalse(temporalDatum.hasOriginDateTime());
		reader.close();
		assertEquals(text, temporalDatum.toString());

	}

	/**
	 * Test temporal coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testTemporalCoordinateReferenceSystem() throws IOException {

		String text = "TIMECRS[\"DateTime\","
				+ "TDATUM[\"Gregorian Calendar\"],"
				+ "CS[TemporalDateTime,1],AXIS[\"Time (T)\",future]]";

		CRS crs = CRSReader.read(text, true);
		TemporalCoordinateReferenceSystem temporalCrs = CRSReader
				.readTemporal(text);
		assertEquals(crs, temporalCrs);
		assertEquals(CRSType.TEMPORAL, temporalCrs.getType());
		assertEquals("DateTime", temporalCrs.getName());
		assertEquals("Gregorian Calendar", temporalCrs.getDatum().getName());
		assertEquals(CoordinateSystemType.TEMPORAL_DATE_TIME,
				temporalCrs.getCoordinateSystem().getType());
		assertEquals(1, temporalCrs.getCoordinateSystem().getDimension());
		assertEquals("Time",
				temporalCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("T", temporalCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.FUTURE, temporalCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		text = text.replaceAll("TemporalDateTime", "temporalDateTime");
		assertEquals(text, temporalCrs.toString());
		assertEquals(text, CRSWriter.write(temporalCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(temporalCrs));

		text = "TIMECRS[\"GPS milliseconds\","
				+ "TDATUM[\"GPS time origin\",TIMEORIGIN[1980-01-01T00:00:00.0Z]],"
				+ "CS[TemporalCount,1],AXIS[\"(T)\",future,TIMEUNIT[\"millisecond (ms)\",0.001]]]";

		crs = CRSReader.read(text, true);
		temporalCrs = CRSReader.readTemporal(text);
		assertEquals(crs, temporalCrs);
		assertEquals(CRSType.TEMPORAL, temporalCrs.getType());
		assertEquals("GPS milliseconds", temporalCrs.getName());
		assertEquals("GPS time origin", temporalCrs.getDatum().getName());
		assertEquals("1980-01-01T00:00:00.0Z",
				temporalCrs.getDatum().getOrigin());
		assertTrue(temporalCrs.getDatum().hasOriginDateTime());
		assertEquals("1980-01-01T00:00:00.0Z",
				temporalCrs.getDatum().getOriginDateTime().toString());
		assertEquals(CoordinateSystemType.TEMPORAL_COUNT,
				temporalCrs.getCoordinateSystem().getType());
		assertEquals(1, temporalCrs.getCoordinateSystem().getDimension());
		assertEquals("T", temporalCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.FUTURE, temporalCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.TIMEUNIT, temporalCrs.getCoordinateSystem()
				.getAxes().get(0).getUnit().getType());
		assertEquals("millisecond (ms)", temporalCrs.getCoordinateSystem()
				.getAxes().get(0).getUnit().getName());
		assertEquals(0.001, temporalCrs.getCoordinateSystem().getAxes().get(0)
				.getUnit().getConversionFactor(), 0);
		text = text.replaceAll("TemporalCount", "temporalCount");
		assertEquals(text, temporalCrs.toString());
		assertEquals(text, CRSWriter.write(temporalCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(temporalCrs));

		text = "TIMECRS[\"Calendar hours from 1979-12-29\","
				+ "TDATUM[\"29 December 1979\",TIMEORIGIN[1979-12-29T00Z]],"
				+ "CS[TemporalCount,1],AXIS[\"Time\",future,TIMEUNIT[\"hour\"]]]";

		crs = CRSReader.read(text, true);
		temporalCrs = CRSReader.readTemporal(text);
		assertEquals(crs, temporalCrs);
		assertEquals(CRSType.TEMPORAL, temporalCrs.getType());
		assertEquals("Calendar hours from 1979-12-29", temporalCrs.getName());
		assertEquals("29 December 1979", temporalCrs.getDatum().getName());
		assertEquals("1979-12-29T00Z", temporalCrs.getDatum().getOrigin());
		assertTrue(temporalCrs.getDatum().hasOriginDateTime());
		assertEquals("1979-12-29T00Z",
				temporalCrs.getDatum().getOriginDateTime().toString());
		assertEquals(CoordinateSystemType.TEMPORAL_COUNT,
				temporalCrs.getCoordinateSystem().getType());
		assertEquals(1, temporalCrs.getCoordinateSystem().getDimension());
		assertEquals("Time",
				temporalCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals(AxisDirectionType.FUTURE, temporalCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.TIMEUNIT, temporalCrs.getCoordinateSystem()
				.getAxes().get(0).getUnit().getType());
		assertEquals("hour", temporalCrs.getCoordinateSystem().getAxes().get(0)
				.getUnit().getName());
		text = text.replaceAll("TemporalCount", "temporalCount");
		assertEquals(text, temporalCrs.toString());
		assertEquals(text, CRSWriter.write(temporalCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(temporalCrs));

		text = "TIMECRS[\"Decimal Years CE\","
				+ "TDATUM[\"Common Era\",TIMEORIGIN[0000]],"
				+ "CS[TemporalMeasure,1],AXIS[\"Decimal years (a)\",future,TIMEUNIT[\"year\"]]]";

		crs = CRSReader.read(text, true);
		temporalCrs = CRSReader.readTemporal(text);
		assertEquals(crs, temporalCrs);
		assertEquals(CRSType.TEMPORAL, temporalCrs.getType());
		assertEquals("Decimal Years CE", temporalCrs.getName());
		assertEquals("Common Era", temporalCrs.getDatum().getName());
		assertEquals("0000", temporalCrs.getDatum().getOrigin());
		assertTrue(temporalCrs.getDatum().hasOriginDateTime());
		assertEquals("0000",
				temporalCrs.getDatum().getOriginDateTime().toString());
		assertEquals(CoordinateSystemType.TEMPORAL_MEASURE,
				temporalCrs.getCoordinateSystem().getType());
		assertEquals(1, temporalCrs.getCoordinateSystem().getDimension());
		assertEquals("Decimal years",
				temporalCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("a", temporalCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.FUTURE, temporalCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.TIMEUNIT, temporalCrs.getCoordinateSystem()
				.getAxes().get(0).getUnit().getType());
		assertEquals("year", temporalCrs.getCoordinateSystem().getAxes().get(0)
				.getUnit().getName());
		text = text.replaceAll("TemporalMeasure", "temporalMeasure");
		assertEquals(text, temporalCrs.toString());
		assertEquals(text, CRSWriter.write(temporalCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(temporalCrs));

		text = "TIMECRS[\"Unix time\","
				+ "TDATUM[\"Unix epoch\",TIMEORIGIN[1970-01-01T00:00:00Z]],"
				+ "CS[TemporalCount,1],AXIS[\"Time\",future,TIMEUNIT[\"second\"]]]";

		crs = CRSReader.read(text, true);
		temporalCrs = CRSReader.readTemporal(text);
		assertEquals(crs, temporalCrs);
		assertEquals(CRSType.TEMPORAL, temporalCrs.getType());
		assertEquals("Unix time", temporalCrs.getName());
		assertEquals("Unix epoch", temporalCrs.getDatum().getName());
		assertEquals("1970-01-01T00:00:00Z",
				temporalCrs.getDatum().getOrigin());
		assertTrue(temporalCrs.getDatum().hasOriginDateTime());
		assertEquals("1970-01-01T00:00:00Z",
				temporalCrs.getDatum().getOriginDateTime().toString());
		assertEquals(CoordinateSystemType.TEMPORAL_COUNT,
				temporalCrs.getCoordinateSystem().getType());
		assertEquals(1, temporalCrs.getCoordinateSystem().getDimension());
		assertEquals("Time",
				temporalCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals(AxisDirectionType.FUTURE, temporalCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.TIMEUNIT, temporalCrs.getCoordinateSystem()
				.getAxes().get(0).getUnit().getType());
		assertEquals("second", temporalCrs.getCoordinateSystem().getAxes()
				.get(0).getUnit().getName());
		text = text.replaceAll("TemporalCount", "temporalCount");
		assertEquals(text, temporalCrs.toString());
		assertEquals(text, CRSWriter.write(temporalCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(temporalCrs));

	}

	/**
	 * Test deriving conversion
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testDerivingConversion() throws IOException {

		String text = "DERIVINGCONVERSION[\"conversion name\","
				+ "METHOD[\"method name\",ID[\"authority\",123]],"
				+ "PARAMETER[\"parameter 1 name\",0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],"
				+ "ID[\"authority\",456]],"
				+ "PARAMETER[\"parameter 2 name\",-123,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],"
				+ "ID[\"authority\",789]]]";
		CRSReader reader = new CRSReader(text);
		DerivingConversion derivingConversion = reader.readDerivingConversion();
		assertEquals("conversion name", derivingConversion.getName());
		assertEquals("method name", derivingConversion.getMethod().getName());
		assertEquals("authority", derivingConversion.getMethod()
				.getIdentifiers().get(0).getName());
		assertEquals("123", derivingConversion.getMethod().getIdentifiers()
				.get(0).getUniqueIdentifier());
		assertEquals("parameter 1 name",
				derivingConversion.getParameters().get(0).getName());
		assertEquals(0,
				((OperationParameter) derivingConversion.getParameters().get(0))
						.getValue(),
				0);
		assertEquals(UnitType.ANGLEUNIT,
				((OperationParameter) derivingConversion.getParameters().get(0))
						.getUnit().getType());
		assertEquals("degree",
				((OperationParameter) derivingConversion.getParameters().get(0))
						.getUnit().getName());
		assertEquals(0.0174532925199433,
				((OperationParameter) derivingConversion.getParameters().get(0))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("authority", derivingConversion.getParameters().get(0)
				.getIdentifiers().get(0).getName());
		assertEquals("456", derivingConversion.getParameters().get(0)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("parameter 2 name",
				derivingConversion.getParameters().get(1).getName());
		assertEquals(-123,
				((OperationParameter) derivingConversion.getParameters().get(1))
						.getValue(),
				0);
		assertEquals(UnitType.ANGLEUNIT,
				((OperationParameter) derivingConversion.getParameters().get(1))
						.getUnit().getType());
		assertEquals("degree",
				((OperationParameter) derivingConversion.getParameters().get(1))
						.getUnit().getName());
		assertEquals(0.0174532925199433,
				((OperationParameter) derivingConversion.getParameters().get(1))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("authority", derivingConversion.getParameters().get(1)
				.getIdentifiers().get(0).getName());
		assertEquals("789", derivingConversion.getParameters().get(1)
				.getIdentifiers().get(0).getUniqueIdentifier());
		reader.close();
		text = text.replace(",0,", ",0.0,").replace(",-123,", ",-123.0,");
		assertEquals(text, derivingConversion.toString());

	}

	/**
	 * Test derived geographic coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testDerivedGeographicCoordinateReferenceSystem()
			throws IOException {

		String text = "GEOGCRS[\"WMO Atlantic Pole\","
				+ "BASEGEOGCRS[\"WGS 84 (G1762)\","
				+ "DYNAMIC[FRAMEEPOCH[2005.0]],"
				+ "TRF[\"World Geodetic System 1984 (G1762)\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,LENGTHUNIT[\"metre\",1.0]]]],"
				+ "DERIVINGCONVERSION[\"Atlantic pole\","
				+ "METHOD[\"Pole rotation\",ID[\"Authority\",1234]],"
				+ "PARAMETER[\"Latitude of rotated pole\",52.0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Longitude of rotated pole\",-30.0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Axis rotation\",-25.0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]]],"
				+ "CS[ellipsoidal,2],"
				+ "AXIS[\"latitude\",north,ORDER[1]],AXIS[\"longitude\",east,ORDER[2]],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]]";

		CRS crs = CRSReader.read(text, true);
		DerivedCoordinateReferenceSystem derivedCrs = CRSReader
				.readDerived(text);
		assertEquals(crs, derivedCrs);
		assertEquals(CRSType.DERIVED, derivedCrs.getType());
		assertEquals("WMO Atlantic Pole", derivedCrs.getName());
		assertEquals(CRSType.GEOGRAPHIC, derivedCrs.getBaseType());
		assertEquals("WGS 84 (G1762)", derivedCrs.getBaseName());
		GeoCoordinateReferenceSystem baseCrs = (GeoCoordinateReferenceSystem) derivedCrs
				.getBase();
		assertEquals(2005.0, baseCrs.getDynamic().getReferenceEpoch(), 0);
		assertEquals("World Geodetic System 1984 (G1762)",
				baseCrs.getReferenceFrame().getName());
		assertEquals("WGS 84",
				baseCrs.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137,
				baseCrs.getReferenceFrame().getEllipsoid().getSemiMajorAxis(),
				0);
		assertEquals(298.257223563, baseCrs.getReferenceFrame().getEllipsoid()
				.getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT,
				baseCrs.getReferenceFrame().getEllipsoid().getUnit().getType());
		assertEquals("metre",
				baseCrs.getReferenceFrame().getEllipsoid().getUnit().getName());
		assertEquals(1.0, baseCrs.getReferenceFrame().getEllipsoid().getUnit()
				.getConversionFactor(), 0);
		assertEquals("Atlantic pole", derivedCrs.getConversion().getName());
		assertEquals("Pole rotation",
				derivedCrs.getConversion().getMethod().getName());
		assertEquals("Authority", derivedCrs.getConversion().getMethod()
				.getIdentifiers().get(0).getName());
		assertEquals("1234", derivedCrs.getConversion().getMethod()
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Latitude of rotated pole",
				derivedCrs.getConversion().getParameters().get(0).getName());
		assertEquals(52.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(0)).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(0)).getUnit().getType());
		assertEquals("degree", ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(0)).getUnit().getName());
		assertEquals(0.0174532925199433,
				((OperationParameter) derivedCrs.getConversion().getParameters()
						.get(0)).getUnit().getConversionFactor(),
				0);
		assertEquals("Longitude of rotated pole",
				derivedCrs.getConversion().getParameters().get(1).getName());
		assertEquals(-30.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(1)).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(1)).getUnit().getType());
		assertEquals("degree", ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(1)).getUnit().getName());
		assertEquals(0.0174532925199433,
				((OperationParameter) derivedCrs.getConversion().getParameters()
						.get(1)).getUnit().getConversionFactor(),
				0);
		assertEquals("Axis rotation",
				derivedCrs.getConversion().getParameters().get(2).getName());
		assertEquals(-25.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(2)).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(2)).getUnit().getType());
		assertEquals("degree", ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(2)).getUnit().getName());
		assertEquals(0.0174532925199433,
				((OperationParameter) derivedCrs.getConversion().getParameters()
						.get(2)).getUnit().getConversionFactor(),
				0);
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				derivedCrs.getCoordinateSystem().getType());
		assertEquals(2, derivedCrs.getCoordinateSystem().getDimension());
		assertEquals("latitude",
				derivedCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals(AxisDirectionType.NORTH, derivedCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(1, derivedCrs.getCoordinateSystem().getAxes().get(0)
				.getOrder().intValue());
		assertEquals("longitude",
				derivedCrs.getCoordinateSystem().getAxes().get(1).getName());
		assertEquals(AxisDirectionType.EAST, derivedCrs.getCoordinateSystem()
				.getAxes().get(1).getDirection());
		assertEquals(2, derivedCrs.getCoordinateSystem().getAxes().get(1)
				.getOrder().intValue());
		assertEquals(UnitType.ANGLEUNIT,
				derivedCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("degree",
				derivedCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(0.0174532925199433, derivedCrs.getCoordinateSystem()
				.getUnit().getConversionFactor(), 0);
		text = text.replaceAll("TRF", "DATUM").replaceAll("6378137",
				"6378137.0");
		assertEquals(text, derivedCrs.toString());
		assertEquals(text, CRSWriter.write(derivedCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(derivedCrs));

	}

	/**
	 * Test derived projected coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testDerivedProjectedCoordinateReferenceSystem()
			throws IOException {

		String text = "DERIVEDPROJCRS[\"Gulf of Mexico speculative seismic survey bin grid\","
				+ "BASEPROJCRS[\"NAD27 / Texas South Central\","
				+ "BASEGEOGCRS[\"NAD27\","
				+ "DATUM[\"North American Datum 1927\","
				+ "ELLIPSOID[\"Clarke 1866\",20925832.164,294.97869821,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219]]]],"
				+ "CONVERSION[\"Texas South CentralSPCS27\","
				+ "METHOD[\"Lambert Conic Conformal (2SP)\",ID[\"EPSG\",9802]],"
				+ "PARAMETER[\"Latitude of false origin\",27.83333333333333,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8821]],"
				+ "PARAMETER[\"Longitude of false origin\",-99.0,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8822]],"
				+ "PARAMETER[\"Latitude of 1st standard parallel\",28.383333333333,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8823]],"
				+ "PARAMETER[\"Latitude of 2nd standard parallel\",30.283333333333,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8824]],"
				+ "PARAMETER[\"Easting at false origin\",2000000.0,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8826]],"
				+ "PARAMETER[\"Northing at false origin\",0.0,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8827]]]],"
				+ "DERIVINGCONVERSION[\"Gulf of Mexico speculative survey bin grid\","
				+ "METHOD[\"P6 (I = J-90°) seismic bin grid transformation\",ID[\"EPSG\",1049]],"
				+ "PARAMETER[\"Bin grid origin I\",5000,SCALEUNIT[\"Bin\",1.0],ID[\"EPSG\",8733]],"
				+ "PARAMETER[\"Bin grid origin J\",0,SCALEUNIT[\"Bin\",1.0],ID[\"EPSG\",8734]],"
				+ "PARAMETER[\"Bin grid origin Easting\",871200,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8735]],"
				+ "PARAMETER[\"Bin grid origin Northing\",10280160,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8736]],"
				+ "PARAMETER[\"Scale factor of bin grid\",1.0,"
				+ "SCALEUNIT[\"Unity\",1.0],ID[\"EPSG\",8737]],"
				+ "PARAMETER[\"Bin width on I-axis\",82.5,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8738]],"
				+ "PARAMETER[\"Bin width on J-axis\",41.25,"
				+ "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8739]],"
				+ "PARAMETER[\"Map grid bearing of bin grid J-axis\",340,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8740]],"
				+ "PARAMETER[\"Bin node increment on I-axis\",1.0,"
				+ "SCALEUNIT[\"Bin\",1.0],ID[\"EPSG\",8741]],"
				+ "PARAMETER[\"Bin node increment on J-axis\",1.0,"
				+ "SCALEUNIT[\"Bin\",1.0],ID[\"EPSG\",8742]]],"
				+ "CS[ordinal,2],"
				+ "AXIS[\"Inline (I)\",northNorthWest],AXIS[\"Crossline (J)\",westSouthWest]]";

		CRS crs = CRSReader.read(text, true);
		DerivedCoordinateReferenceSystem derivedCrs = CRSReader
				.readDerived(text);
		assertEquals(crs, derivedCrs);
		assertEquals(CRSType.DERIVED, derivedCrs.getType());
		assertEquals("Gulf of Mexico speculative seismic survey bin grid",
				derivedCrs.getName());
		assertEquals(CRSType.PROJECTED, derivedCrs.getBaseType());
		assertEquals("NAD27 / Texas South Central", derivedCrs.getBaseName());
		ProjectedCoordinateReferenceSystem projectedCrs = (ProjectedCoordinateReferenceSystem) derivedCrs
				.getBase();
		assertEquals(CRSType.GEOGRAPHIC, projectedCrs.getBaseType());
		assertEquals("NAD27", projectedCrs.getBaseName());
		assertEquals("North American Datum 1927",
				projectedCrs.getReferenceFrame().getName());
		assertEquals("Clarke 1866",
				projectedCrs.getReferenceFrame().getEllipsoid().getName());
		assertEquals(20925832.164, projectedCrs.getReferenceFrame()
				.getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(294.97869821, projectedCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, projectedCrs.getReferenceFrame()
				.getEllipsoid().getUnit().getType());
		assertEquals("US survey foot", projectedCrs.getReferenceFrame()
				.getEllipsoid().getUnit().getName());
		assertEquals(0.304800609601219, projectedCrs.getReferenceFrame()
				.getEllipsoid().getUnit().getConversionFactor(), 0);
		assertEquals("Texas South CentralSPCS27",
				projectedCrs.getMapProjection().getName());
		assertEquals("Lambert Conic Conformal (2SP)",
				projectedCrs.getMapProjection().getMethod().getName());
		assertEquals("EPSG", projectedCrs.getMapProjection().getMethod()
				.getIdentifiers().get(0).getName());
		assertEquals("9802", projectedCrs.getMapProjection().getMethod()
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Latitude of false origin", projectedCrs.getMapProjection()
				.getParameters().get(0).getName());
		assertEquals(27.83333333333333, projectedCrs.getMapProjection()
				.getOperationParameters().get(0).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, projectedCrs.getMapProjection()
				.getOperationParameters().get(0).getUnit().getType());
		assertEquals("degree", projectedCrs.getMapProjection()
				.getOperationParameters().get(0).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedCrs.getMapProjection().getOperationParameters().get(0)
						.getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", projectedCrs.getMapProjection().getParameters()
				.get(0).getIdentifiers().get(0).getName());
		assertEquals("8821", projectedCrs.getMapProjection().getParameters()
				.get(0).getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Longitude of false origin", projectedCrs
				.getMapProjection().getParameters().get(1).getName());
		assertEquals(-99.0, projectedCrs.getMapProjection()
				.getOperationParameters().get(1).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, projectedCrs.getMapProjection()
				.getOperationParameters().get(1).getUnit().getType());
		assertEquals("degree", projectedCrs.getMapProjection()
				.getOperationParameters().get(1).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedCrs.getMapProjection().getOperationParameters().get(1)
						.getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", projectedCrs.getMapProjection().getParameters()
				.get(1).getIdentifiers().get(0).getName());
		assertEquals("8822", projectedCrs.getMapProjection().getParameters()
				.get(1).getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Latitude of 1st standard parallel", projectedCrs
				.getMapProjection().getParameters().get(2).getName());
		assertEquals(28.383333333333, projectedCrs.getMapProjection()
				.getOperationParameters().get(2).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, projectedCrs.getMapProjection()
				.getOperationParameters().get(2).getUnit().getType());
		assertEquals("degree", projectedCrs.getMapProjection()
				.getOperationParameters().get(2).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedCrs.getMapProjection().getOperationParameters().get(2)
						.getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", projectedCrs.getMapProjection().getParameters()
				.get(2).getIdentifiers().get(0).getName());
		assertEquals("8823", projectedCrs.getMapProjection().getParameters()
				.get(2).getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Latitude of 2nd standard parallel", projectedCrs
				.getMapProjection().getParameters().get(3).getName());
		assertEquals(30.283333333333, projectedCrs.getMapProjection()
				.getOperationParameters().get(3).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, projectedCrs.getMapProjection()
				.getOperationParameters().get(3).getUnit().getType());
		assertEquals("degree", projectedCrs.getMapProjection()
				.getOperationParameters().get(3).getUnit().getName());
		assertEquals(0.0174532925199433,
				projectedCrs.getMapProjection().getOperationParameters().get(3)
						.getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", projectedCrs.getMapProjection().getParameters()
				.get(3).getIdentifiers().get(0).getName());
		assertEquals("8824", projectedCrs.getMapProjection().getParameters()
				.get(3).getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Easting at false origin", projectedCrs.getMapProjection()
				.getParameters().get(4).getName());
		assertEquals(2000000.0, projectedCrs.getMapProjection()
				.getOperationParameters().get(4).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT, projectedCrs.getMapProjection()
				.getOperationParameters().get(4).getUnit().getType());
		assertEquals("US survey foot", projectedCrs.getMapProjection()
				.getOperationParameters().get(4).getUnit().getName());
		assertEquals(0.304800609601219,
				projectedCrs.getMapProjection().getOperationParameters().get(4)
						.getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", projectedCrs.getMapProjection().getParameters()
				.get(4).getIdentifiers().get(0).getName());
		assertEquals("8826", projectedCrs.getMapProjection().getParameters()
				.get(4).getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Northing at false origin", projectedCrs.getMapProjection()
				.getParameters().get(5).getName());
		assertEquals(0.0, projectedCrs.getMapProjection()
				.getOperationParameters().get(5).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT, projectedCrs.getMapProjection()
				.getOperationParameters().get(5).getUnit().getType());
		assertEquals("US survey foot", projectedCrs.getMapProjection()
				.getOperationParameters().get(5).getUnit().getName());
		assertEquals(0.304800609601219,
				projectedCrs.getMapProjection().getOperationParameters().get(5)
						.getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", projectedCrs.getMapProjection().getParameters()
				.get(5).getIdentifiers().get(0).getName());
		assertEquals("8827", projectedCrs.getMapProjection().getParameters()
				.get(5).getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Gulf of Mexico speculative survey bin grid",
				derivedCrs.getConversion().getName());
		assertEquals("P6 (I = J-90°) seismic bin grid transformation",
				derivedCrs.getConversion().getMethod().getName());
		assertEquals("EPSG", derivedCrs.getConversion().getMethod()
				.getIdentifiers().get(0).getName());
		assertEquals("1049", derivedCrs.getConversion().getMethod()
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Bin grid origin I",
				derivedCrs.getConversion().getParameters().get(0).getName());
		assertEquals(5000, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(0)).getValue(), 0);
		assertEquals(UnitType.SCALEUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(0)).getUnit().getType());
		assertEquals("Bin", ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(0)).getUnit().getName());
		assertEquals(1.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(0)).getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", derivedCrs.getConversion().getParameters().get(0)
				.getIdentifiers().get(0).getName());
		assertEquals("8733", derivedCrs.getConversion().getParameters().get(0)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Bin grid origin J",
				derivedCrs.getConversion().getParameters().get(1).getName());
		assertEquals(0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(1)).getValue(), 0);
		assertEquals(UnitType.SCALEUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(1)).getUnit().getType());
		assertEquals("Bin", ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(1)).getUnit().getName());
		assertEquals(1.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(1)).getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", derivedCrs.getConversion().getParameters().get(1)
				.getIdentifiers().get(0).getName());
		assertEquals("8734", derivedCrs.getConversion().getParameters().get(1)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Bin grid origin Easting",
				derivedCrs.getConversion().getParameters().get(2).getName());
		assertEquals(871200, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(2)).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(2)).getUnit().getType());
		assertEquals("US survey foot", ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(2)).getUnit().getName());
		assertEquals(0.304800609601219,
				((OperationParameter) derivedCrs.getConversion().getParameters()
						.get(2)).getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", derivedCrs.getConversion().getParameters().get(2)
				.getIdentifiers().get(0).getName());
		assertEquals("8735", derivedCrs.getConversion().getParameters().get(2)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Bin grid origin Northing",
				derivedCrs.getConversion().getParameters().get(3).getName());
		assertEquals(10280160, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(3)).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(3)).getUnit().getType());
		assertEquals("US survey foot", ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(3)).getUnit().getName());
		assertEquals(0.304800609601219,
				((OperationParameter) derivedCrs.getConversion().getParameters()
						.get(3)).getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", derivedCrs.getConversion().getParameters().get(3)
				.getIdentifiers().get(0).getName());
		assertEquals("8736", derivedCrs.getConversion().getParameters().get(3)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Scale factor of bin grid",
				derivedCrs.getConversion().getParameters().get(4).getName());
		assertEquals(1.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(4)).getValue(), 0);
		assertEquals(UnitType.SCALEUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(4)).getUnit().getType());
		assertEquals("Unity", ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(4)).getUnit().getName());
		assertEquals(1.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(4)).getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", derivedCrs.getConversion().getParameters().get(4)
				.getIdentifiers().get(0).getName());
		assertEquals("8737", derivedCrs.getConversion().getParameters().get(4)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Bin width on I-axis",
				derivedCrs.getConversion().getParameters().get(5).getName());
		assertEquals(82.5, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(5)).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(5)).getUnit().getType());
		assertEquals("US survey foot", ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(5)).getUnit().getName());
		assertEquals(0.304800609601219,
				((OperationParameter) derivedCrs.getConversion().getParameters()
						.get(5)).getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", derivedCrs.getConversion().getParameters().get(5)
				.getIdentifiers().get(0).getName());
		assertEquals("8738", derivedCrs.getConversion().getParameters().get(5)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Bin width on J-axis",
				derivedCrs.getConversion().getParameters().get(6).getName());
		assertEquals(41.25, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(6)).getValue(), 0);
		assertEquals(UnitType.LENGTHUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(6)).getUnit().getType());
		assertEquals("US survey foot", ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(6)).getUnit().getName());
		assertEquals(0.304800609601219,
				((OperationParameter) derivedCrs.getConversion().getParameters()
						.get(6)).getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", derivedCrs.getConversion().getParameters().get(6)
				.getIdentifiers().get(0).getName());
		assertEquals("8739", derivedCrs.getConversion().getParameters().get(6)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Map grid bearing of bin grid J-axis",
				derivedCrs.getConversion().getParameters().get(7).getName());
		assertEquals(340, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(7)).getValue(), 0);
		assertEquals(UnitType.ANGLEUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(7)).getUnit().getType());
		assertEquals("degree", ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(7)).getUnit().getName());
		assertEquals(0.0174532925199433,
				((OperationParameter) derivedCrs.getConversion().getParameters()
						.get(7)).getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", derivedCrs.getConversion().getParameters().get(7)
				.getIdentifiers().get(0).getName());
		assertEquals("8740", derivedCrs.getConversion().getParameters().get(7)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Bin node increment on I-axis",
				derivedCrs.getConversion().getParameters().get(8).getName());
		assertEquals(1.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(8)).getValue(), 0);
		assertEquals(UnitType.SCALEUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(8)).getUnit().getType());
		assertEquals("Bin", ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(8)).getUnit().getName());
		assertEquals(1.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(8)).getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", derivedCrs.getConversion().getParameters().get(8)
				.getIdentifiers().get(0).getName());
		assertEquals("8741", derivedCrs.getConversion().getParameters().get(8)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Bin node increment on J-axis",
				derivedCrs.getConversion().getParameters().get(9).getName());
		assertEquals(1.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(9)).getValue(), 0);
		assertEquals(UnitType.SCALEUNIT, ((OperationParameter) derivedCrs
				.getConversion().getParameters().get(9)).getUnit().getType());
		assertEquals("Bin", ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(9)).getUnit().getName());
		assertEquals(1.0, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(9)).getUnit().getConversionFactor(), 0);
		assertEquals("EPSG", derivedCrs.getConversion().getParameters().get(9)
				.getIdentifiers().get(0).getName());
		assertEquals("8742", derivedCrs.getConversion().getParameters().get(9)
				.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals(CoordinateSystemType.ORDINAL,
				derivedCrs.getCoordinateSystem().getType());
		assertEquals(2, derivedCrs.getCoordinateSystem().getDimension());
		assertEquals("Inline",
				derivedCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("I", derivedCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.NORTH_NORTH_WEST, derivedCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals("Crossline",
				derivedCrs.getCoordinateSystem().getAxes().get(1).getName());
		assertEquals("J", derivedCrs.getCoordinateSystem().getAxes().get(1)
				.getAbbreviation());
		assertEquals(AxisDirectionType.WEST_SOUTH_WEST, derivedCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());

		text = text.replace("20925832.164", "2.0925832164E7")
				.replace(",5000,", ",5000.0,").replace(",0,", ",0.0,")
				.replace("871200", "871200.0").replace("10280160", "1.028016E7")
				.replace(",340,", ",340.0,");
		assertEquals(text, derivedCrs.toString());
		assertEquals(text, CRSWriter.write(derivedCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(derivedCrs));

	}

	/**
	 * Test derived vertical coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testDerivedVerticalCoordinateReferenceSystem()
			throws IOException {

		String text = "VERTCRS[\"Pseudo-pression en kPa\","
				+ "BASEVERTCRS[\"Profondeur sous la surface de la mer\","
				+ "VDATUM[\"Niveau moyen de la mer\"]],"
				+ "DERIVINGCONVERSION[“De la profondeur vers PP”,"
				+ "METHOD[\"Affine\"],"
				+ "PARAMETER[\"num_row\",2],PARAMETER[\"num_col\",2],"
				+ "PARAMETER[\"elt_0_0\",10],PARAMETER[\"elt_0_1”,100]],"
				+ "CS[vertical,1],"
				+ "AXIS[“Pseudo-pression (H)”,down],UNIT[“Unity”,1]]";

		CRS crs = CRSReader.read(text, true);
		DerivedCoordinateReferenceSystem derivedCrs = CRSReader
				.readDerived(text);
		assertEquals(crs, derivedCrs);
		assertEquals(CRSType.DERIVED, derivedCrs.getType());
		assertEquals("Pseudo-pression en kPa", derivedCrs.getName());
		assertEquals(CRSType.VERTICAL, derivedCrs.getBaseType());
		assertEquals("Profondeur sous la surface de la mer",
				derivedCrs.getBaseName());
		VerticalCoordinateReferenceSystem verticalCrs = (VerticalCoordinateReferenceSystem) derivedCrs
				.getBase();
		assertEquals("Niveau moyen de la mer",
				verticalCrs.getReferenceFrame().getName());
		assertEquals("De la profondeur vers PP",
				derivedCrs.getConversion().getName());
		assertEquals("Affine",
				derivedCrs.getConversion().getMethod().getName());
		assertEquals("num_row",
				derivedCrs.getConversion().getParameters().get(0).getName());
		assertEquals(2, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(0)).getValue(), 0);
		assertEquals("num_col",
				derivedCrs.getConversion().getParameters().get(1).getName());
		assertEquals(2, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(1)).getValue(), 0);
		assertEquals("elt_0_0",
				derivedCrs.getConversion().getParameters().get(2).getName());
		assertEquals(10, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(2)).getValue(), 0);
		assertEquals("elt_0_1",
				derivedCrs.getConversion().getParameters().get(3).getName());
		assertEquals(100, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(3)).getValue(), 0);
		assertEquals(CoordinateSystemType.VERTICAL,
				derivedCrs.getCoordinateSystem().getType());
		assertEquals(1, derivedCrs.getCoordinateSystem().getDimension());
		assertEquals("Pseudo-pression",
				derivedCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("H", derivedCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.DOWN, derivedCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.UNIT,
				derivedCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("Unity",
				derivedCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1.0, derivedCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);

		text = "VERTCRS[\"Pseudo-pression en kPa\","
				+ "BASEVERTCRS[\"Profondeur sous la surface de la mer\","
				+ "VDATUM[\"Niveau moyen de la mer\"]],"
				+ "DERIVINGCONVERSION[\"De la profondeur vers PP\","
				+ "METHOD[\"Affine\"],"
				+ "PARAMETER[\"num_row\",2.0],PARAMETER[\"num_col\",2.0],"
				+ "PARAMETER[\"elt_0_0\",10.0],PARAMETER[\"elt_0_1\",100.0]],"
				+ "CS[vertical,1],"
				+ "AXIS[\"Pseudo-pression (H)\",down],UNIT[\"Unity\",1.0]]";
		assertEquals(text, derivedCrs.toString());
		assertEquals(text, CRSWriter.write(derivedCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(derivedCrs));

	}

	/**
	 * Test derived engineering coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testDerivedEngineeringCoordinateReferenceSystem()
			throws IOException {

		String text = "ENGCRS[\"A construction site CRS\","
				+ "BASEENGCRS[\"A construction site Base CRS\","
				+ "EDATUM[\"P1\",ANCHOR[\"Peg in south corner\"]]],"
				+ "DERIVINGCONVERSION[\"Deriving Conversion\","
				+ "METHOD[\"Method\"],"
				+ "PARAMETER[\"param1\",1.0],PARAMETER[\"param2\",2.0]],"
				+ "CS[Cartesian,2],"
				+ "AXIS[\"site east\",southWest,ORDER[1]],AXIS[\"site north\",southEast,ORDER[2]]]";

		CRS crs = CRSReader.read(text, true);
		DerivedCoordinateReferenceSystem derivedCrs = CRSReader
				.readDerived(text);
		assertEquals(crs, derivedCrs);
		assertEquals(CRSType.DERIVED, derivedCrs.getType());
		assertEquals("A construction site CRS", derivedCrs.getName());
		assertEquals(CRSType.ENGINEERING, derivedCrs.getBaseType());
		assertEquals("A construction site Base CRS", derivedCrs.getBaseName());
		EngineeringCoordinateReferenceSystem engineeringCrs = (EngineeringCoordinateReferenceSystem) derivedCrs
				.getBase();
		assertEquals("P1", engineeringCrs.getDatum().getName());
		assertEquals("Peg in south corner",
				engineeringCrs.getDatum().getAnchor());
		assertEquals("Deriving Conversion",
				derivedCrs.getConversion().getName());
		assertEquals("Method",
				derivedCrs.getConversion().getMethod().getName());
		assertEquals("param1",
				derivedCrs.getConversion().getParameters().get(0).getName());
		assertEquals(1, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(0)).getValue(), 0);
		assertEquals("param2",
				derivedCrs.getConversion().getParameters().get(1).getName());
		assertEquals(2, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(1)).getValue(), 0);
		assertEquals(CoordinateSystemType.CARTESIAN,
				derivedCrs.getCoordinateSystem().getType());
		assertEquals(2, derivedCrs.getCoordinateSystem().getDimension());
		assertEquals("site east",
				derivedCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals(AxisDirectionType.SOUTH_WEST, derivedCrs
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, derivedCrs.getCoordinateSystem().getAxes().get(0)
				.getOrder().intValue());
		assertEquals("site north",
				derivedCrs.getCoordinateSystem().getAxes().get(1).getName());
		assertEquals(AxisDirectionType.SOUTH_EAST, derivedCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(2, derivedCrs.getCoordinateSystem().getAxes().get(1)
				.getOrder().intValue());

		assertEquals(text, derivedCrs.toString());
		assertEquals(text, CRSWriter.write(derivedCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(derivedCrs));

	}

	/**
	 * Test derived parametric coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testDerivedParametricCoordinateReferenceSystem()
			throws IOException {

		String text = "PARAMETRICCRS[\"WMO standard atmosphere layer 0\","
				+ "BASEPARAMCRS[\"WMO standard atmosphere layer 0 Base\","
				+ "PDATUM[\"Mean Sea Level\",ANCHOR[\"1013.25 hPa at 15°C\"]]],"
				+ "DERIVINGCONVERSION[\"Deriving Conversion\","
				+ "METHOD[\"Method\"],"
				+ "PARAMETER[\"param1\",1.0],PARAMETER[\"param2\",2.0]],"
				+ "CS[parametric,1],"
				+ "AXIS[\"pressure (hPa)\",up],PARAMETRICUNIT[\"HectoPascal\",100.0]]";

		CRS crs = CRSReader.read(text, true);
		DerivedCoordinateReferenceSystem derivedCrs = CRSReader
				.readDerived(text);
		assertEquals(crs, derivedCrs);
		assertEquals(CRSType.DERIVED, derivedCrs.getType());
		assertEquals("WMO standard atmosphere layer 0", derivedCrs.getName());
		assertEquals(CRSType.PARAMETRIC, derivedCrs.getBaseType());
		assertEquals("WMO standard atmosphere layer 0 Base",
				derivedCrs.getBaseName());
		ParametricCoordinateReferenceSystem parametricCrs = (ParametricCoordinateReferenceSystem) derivedCrs
				.getBase();
		assertEquals("Mean Sea Level", parametricCrs.getDatum().getName());
		assertEquals("1013.25 hPa at 15°C",
				parametricCrs.getDatum().getAnchor());
		assertEquals("Deriving Conversion",
				derivedCrs.getConversion().getName());
		assertEquals("Method",
				derivedCrs.getConversion().getMethod().getName());
		assertEquals("param1",
				derivedCrs.getConversion().getParameters().get(0).getName());
		assertEquals(1, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(0)).getValue(), 0);
		assertEquals("param2",
				derivedCrs.getConversion().getParameters().get(1).getName());
		assertEquals(2, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(1)).getValue(), 0);
		assertEquals(CoordinateSystemType.PARAMETRIC,
				derivedCrs.getCoordinateSystem().getType());
		assertEquals(1, derivedCrs.getCoordinateSystem().getDimension());
		assertEquals("pressure",
				derivedCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("hPa", derivedCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.UP, derivedCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.PARAMETRICUNIT,
				derivedCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("HectoPascal",
				derivedCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(100.0, derivedCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);

		assertEquals(text, derivedCrs.toString());
		assertEquals(text, CRSWriter.write(derivedCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(derivedCrs));

	}

	/**
	 * Test derived temporal coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testDerivedTemporalCoordinateReferenceSystem()
			throws IOException {

		String text = "TIMECRS[\"DateTime\",BASETIMECRS[\"DateTime Base\","
				+ "TDATUM[\"Gregorian Calendar\"]],"
				+ "DERIVINGCONVERSION[\"Deriving Conversion\","
				+ "METHOD[\"Method\"],"
				+ "PARAMETER[\"param1\",1.0],PARAMETER[\"param2\",2.0]],"
				+ "CS[temporalDateTime,1],AXIS[\"Time (T)\",future]]";

		CRS crs = CRSReader.read(text, true);
		DerivedCoordinateReferenceSystem derivedCrs = CRSReader
				.readDerived(text);
		assertEquals(crs, derivedCrs);
		assertEquals(CRSType.DERIVED, derivedCrs.getType());
		assertEquals("DateTime", derivedCrs.getName());
		assertEquals(CRSType.TEMPORAL, derivedCrs.getBaseType());
		assertEquals("DateTime Base", derivedCrs.getBaseName());
		TemporalCoordinateReferenceSystem temporalCrs = (TemporalCoordinateReferenceSystem) derivedCrs
				.getBase();
		assertEquals("Gregorian Calendar", temporalCrs.getDatum().getName());
		assertEquals("Deriving Conversion",
				derivedCrs.getConversion().getName());
		assertEquals("Method",
				derivedCrs.getConversion().getMethod().getName());
		assertEquals("param1",
				derivedCrs.getConversion().getParameters().get(0).getName());
		assertEquals(1, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(0)).getValue(), 0);
		assertEquals("param2",
				derivedCrs.getConversion().getParameters().get(1).getName());
		assertEquals(2, ((OperationParameter) derivedCrs.getConversion()
				.getParameters().get(1)).getValue(), 0);
		assertEquals(CoordinateSystemType.TEMPORAL_DATE_TIME,
				derivedCrs.getCoordinateSystem().getType());
		assertEquals(1, derivedCrs.getCoordinateSystem().getDimension());
		assertEquals("Time",
				derivedCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("T", derivedCrs.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.FUTURE, derivedCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());

		assertEquals(text, derivedCrs.toString());
		assertEquals(text, CRSWriter.write(derivedCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(derivedCrs));

	}

	/**
	 * Test compound coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testCompoundCoordinateReferenceSystem() throws IOException {

		String text = "COMPOUNDCRS[\"NAD83 + NAVD88\","
				+ "GEOGCRS[\"NAD83\",DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,"
				+ "LENGTHUNIT[\"metre\",1.0]]],"
				+ "PRIMEMERIDIAN[\"Greenwich\",0],CS[ellipsoidal,2],"
				+ "AXIS[\"latitude\",north,ORDER[1]],AXIS[\"longitude\",east,ORDER[2]],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "VERTCRS[\"NAVD88\","
				+ "VDATUM[\"North American Vertical Datum 1983\"],"
				+ "CS[vertical,1],"
				+ "AXIS[\"gravity-related height (H)\",up],LENGTHUNIT[\"metre\",1]]]";

		CRS crs = CRSReader.read(text, true);
		CompoundCoordinateReferenceSystem compoundCrs = CRSReader
				.readCompound(text);
		assertEquals(crs, compoundCrs);
		assertEquals(CRSType.COMPOUND, compoundCrs.getType());
		assertEquals("NAD83 + NAVD88", compoundCrs.getName());
		GeoCoordinateReferenceSystem geo = (GeoCoordinateReferenceSystem) compoundCrs
				.getCoordinateReferenceSystems().get(0);
		assertEquals("NAD83", geo.getName());
		assertEquals("North American Datum 1983",
				geo.getReferenceFrame().getName());
		assertEquals("GRS 1980",
				geo.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137,
				geo.getReferenceFrame().getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(298.257222101,
				geo.getReferenceFrame().getEllipsoid().getInverseFlattening(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				geo.getReferenceFrame().getEllipsoid().getUnit().getType());
		assertEquals("metre",
				geo.getReferenceFrame().getEllipsoid().getUnit().getName());
		assertEquals(1.0, geo.getReferenceFrame().getEllipsoid().getUnit()
				.getConversionFactor(), 0);
		assertEquals("Greenwich",
				geo.getReferenceFrame().getPrimeMeridian().getName());
		assertEquals(0,
				geo.getReferenceFrame().getPrimeMeridian().getIrmLongitude(),
				0);
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				geo.getCoordinateSystem().getType());
		assertEquals(2, geo.getCoordinateSystem().getDimension());
		assertEquals("latitude",
				geo.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals(AxisDirectionType.NORTH,
				geo.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, geo.getCoordinateSystem().getAxes().get(0).getOrder()
				.intValue());
		assertEquals("longitude",
				geo.getCoordinateSystem().getAxes().get(1).getName());
		assertEquals(AxisDirectionType.EAST,
				geo.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(2, geo.getCoordinateSystem().getAxes().get(1).getOrder()
				.intValue());
		assertEquals(UnitType.ANGLEUNIT,
				geo.getCoordinateSystem().getUnit().getType());
		assertEquals("degree", geo.getCoordinateSystem().getUnit().getName());
		assertEquals(0.0174532925199433,
				geo.getCoordinateSystem().getUnit().getConversionFactor(), 0);
		VerticalCoordinateReferenceSystem vertical = (VerticalCoordinateReferenceSystem) compoundCrs
				.getCoordinateReferenceSystems().get(1);
		assertEquals("NAVD88", vertical.getName());
		assertEquals("North American Vertical Datum 1983",
				vertical.getReferenceFrame().getName());
		assertEquals(CoordinateSystemType.VERTICAL,
				vertical.getCoordinateSystem().getType());
		assertEquals(1, vertical.getCoordinateSystem().getDimension());
		assertEquals("gravity-related height",
				vertical.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("H", vertical.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.UP,
				vertical.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(UnitType.LENGTHUNIT,
				vertical.getCoordinateSystem().getUnit().getType());
		assertEquals("metre",
				vertical.getCoordinateSystem().getUnit().getName());
		assertEquals(1,
				vertical.getCoordinateSystem().getUnit().getConversionFactor(),
				0);

		text = text.replace("6378137", "6378137.0")
				.replace("PRIMEMERIDIAN", "PRIMEM").replace(",0]", ",0.0]")
				.replace("\",1]", "\",1.0]");
		assertEquals(text, compoundCrs.toString());
		assertEquals(text, CRSWriter.write(compoundCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(compoundCrs));

		text = "COMPOUNDCRS[\"ICAO layer 0\","
				+ "GEOGRAPHICCRS[\"WGS 84\",DYNAMIC[FRAMEEPOCH[2005]],"
				+ "DATUM[\"World Geodetic System 1984\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,"
				+ "LENGTHUNIT[\"metre\",1.0]]],"
				+ "CS[ellipsoidal,2],AXIS[\"latitude\",north,ORDER[1]],AXIS[\"longitude\",east,ORDER[2]],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETRICCRS[\"WMO standard atmosphere\","
				+ "PARAMETRICDATUM[\"Mean Sea Level\","
				+ "ANCHOR[\"Mean Sea Level = 1013.25 hPa\"]],"
				+ "CS[parametric,1],AXIS[\"pressure (P)\",unspecified],"
				+ "PARAMETRICUNIT[\"HectoPascal\",100]]]";

		crs = CRSReader.read(text, true);
		compoundCrs = CRSReader.readCompound(text);
		assertEquals(crs, compoundCrs);
		assertEquals(CRSType.COMPOUND, compoundCrs.getType());
		assertEquals("ICAO layer 0", compoundCrs.getName());
		geo = (GeoCoordinateReferenceSystem) compoundCrs
				.getCoordinateReferenceSystems().get(0);
		assertEquals("WGS 84", geo.getName());
		assertEquals(2005, geo.getDynamic().getReferenceEpoch(), 0);
		assertEquals("World Geodetic System 1984",
				geo.getReferenceFrame().getName());
		assertEquals("WGS 84",
				geo.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137,
				geo.getReferenceFrame().getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(298.257223563,
				geo.getReferenceFrame().getEllipsoid().getInverseFlattening(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				geo.getReferenceFrame().getEllipsoid().getUnit().getType());
		assertEquals("metre",
				geo.getReferenceFrame().getEllipsoid().getUnit().getName());
		assertEquals(1.0, geo.getReferenceFrame().getEllipsoid().getUnit()
				.getConversionFactor(), 0);
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				geo.getCoordinateSystem().getType());
		assertEquals(2, geo.getCoordinateSystem().getDimension());
		assertEquals("latitude",
				geo.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals(AxisDirectionType.NORTH,
				geo.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, geo.getCoordinateSystem().getAxes().get(0).getOrder()
				.intValue());
		assertEquals("longitude",
				geo.getCoordinateSystem().getAxes().get(1).getName());
		assertEquals(AxisDirectionType.EAST,
				geo.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(2, geo.getCoordinateSystem().getAxes().get(1).getOrder()
				.intValue());
		assertEquals(UnitType.ANGLEUNIT,
				geo.getCoordinateSystem().getUnit().getType());
		assertEquals("degree", geo.getCoordinateSystem().getUnit().getName());
		assertEquals(0.0174532925199433,
				geo.getCoordinateSystem().getUnit().getConversionFactor(), 0);
		ParametricCoordinateReferenceSystem parametric = (ParametricCoordinateReferenceSystem) compoundCrs
				.getCoordinateReferenceSystems().get(1);
		assertEquals("WMO standard atmosphere", parametric.getName());
		assertEquals("Mean Sea Level", parametric.getDatum().getName());
		assertEquals("Mean Sea Level = 1013.25 hPa",
				parametric.getDatum().getAnchor());
		assertEquals(CoordinateSystemType.PARAMETRIC,
				parametric.getCoordinateSystem().getType());
		assertEquals(1, parametric.getCoordinateSystem().getDimension());
		assertEquals("pressure",
				parametric.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("P", parametric.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.UNSPECIFIED, parametric
				.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(UnitType.PARAMETRICUNIT,
				parametric.getCoordinateSystem().getUnit().getType());
		assertEquals("HectoPascal",
				parametric.getCoordinateSystem().getUnit().getName());
		assertEquals(100, parametric.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);

		text = text.replace("GEOGRAPHICCRS", "GEOGCRS")
				.replace("2005", "2005.0").replace("6378137", "6378137.0")
				.replace("PARAMETRICDATUM", "PDATUM")
				.replace(",100]", ",100.0]");
		assertEquals(text, compoundCrs.toString());
		assertEquals(text, CRSWriter.write(compoundCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(compoundCrs));

		text = "COMPOUNDCRS[\"2D GPS position with civil time in ISO 8601 format\","
				+ "GEOGCRS[\"WGS 84 (G1762)\"," + "DYNAMIC[FRAMEEPOCH[2005]],"
				+ "DATUM[\"World Geodetic System 1984 (G1762)\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563]],"
				+ "CS[ellipsoidal,2]," + "AXIS[\"(lat)\",north,ORDER[1]],"
				+ "AXIS[\"(lon)\",east,ORDER[2]],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "TIMECRS[\"DateTime\"," + "TDATUM[\"Gregorian Calendar\"],"
				+ "CS[TemporalDateTime,1],AXIS[\"Time (T)\",future]]]";

		crs = CRSReader.read(text, true);
		compoundCrs = CRSReader.readCompound(text);
		assertEquals(crs, compoundCrs);
		assertEquals(CRSType.COMPOUND, compoundCrs.getType());
		assertEquals("2D GPS position with civil time in ISO 8601 format",
				compoundCrs.getName());
		geo = (GeoCoordinateReferenceSystem) compoundCrs
				.getCoordinateReferenceSystems().get(0);
		assertEquals("WGS 84 (G1762)", geo.getName());
		assertEquals(2005, geo.getDynamic().getReferenceEpoch(), 0);
		assertEquals("World Geodetic System 1984 (G1762)",
				geo.getReferenceFrame().getName());
		assertEquals("WGS 84",
				geo.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137,
				geo.getReferenceFrame().getEllipsoid().getSemiMajorAxis(), 0);
		assertEquals(298.257223563,
				geo.getReferenceFrame().getEllipsoid().getInverseFlattening(),
				0);
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				geo.getCoordinateSystem().getType());
		assertEquals(2, geo.getCoordinateSystem().getDimension());
		assertEquals("lat",
				geo.getCoordinateSystem().getAxes().get(0).getAbbreviation());
		assertEquals(AxisDirectionType.NORTH,
				geo.getCoordinateSystem().getAxes().get(0).getDirection());
		assertEquals(1, geo.getCoordinateSystem().getAxes().get(0).getOrder()
				.intValue());
		assertEquals("lon",
				geo.getCoordinateSystem().getAxes().get(1).getAbbreviation());
		assertEquals(AxisDirectionType.EAST,
				geo.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals(2, geo.getCoordinateSystem().getAxes().get(1).getOrder()
				.intValue());
		assertEquals(UnitType.ANGLEUNIT,
				geo.getCoordinateSystem().getUnit().getType());
		assertEquals("degree", geo.getCoordinateSystem().getUnit().getName());
		assertEquals(0.0174532925199433,
				geo.getCoordinateSystem().getUnit().getConversionFactor(), 0);
		TemporalCoordinateReferenceSystem temporal = (TemporalCoordinateReferenceSystem) compoundCrs
				.getCoordinateReferenceSystems().get(1);
		assertEquals("DateTime", temporal.getName());
		assertEquals(CoordinateSystemType.TEMPORAL_DATE_TIME,
				temporal.getCoordinateSystem().getType());
		assertEquals(1, temporal.getCoordinateSystem().getDimension());
		assertEquals("Time",
				temporal.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals("T", temporal.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.FUTURE,
				temporal.getCoordinateSystem().getAxes().get(0).getDirection());

		text = text.replace("2005", "2005.0").replace("6378137", "6378137.0")
				.replace("TemporalDateTime", "temporalDateTime");
		assertEquals(text, compoundCrs.toString());
		assertEquals(text, CRSWriter.write(compoundCrs));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(compoundCrs));

	}

	/**
	 * Test coordinate metadata
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testCoordinateMetadata() throws IOException {

		String text = "COORDINATEMETADATA[GEOGCRS[\"WGS 84 (G1762)\","
				+ "DYNAMIC[FRAMEEPOCH[2005.0]],"
				+ "DATUM[\"World Geodetic System 1984 (G1762)\","
				+ "ELLIPSOID[\"WGS 84\",6378137,298.257223563,LENGTHUNIT[\"metre\",1.0]]],"
				+ "CS[ellipsoidal,3],"
				+ "AXIS[\"(lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"(lon)\",east,ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "AXIS[\"ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1.0]]],"
				+ "EPOCH[2016.47]]";

		CRS crs = CRSReader.read(text, true);
		CoordinateMetadata metadata = CRSReader.readCoordinateMetadata(text);
		assertEquals(crs, metadata);
		assertEquals(CRSType.COORDINATE_METADATA, metadata.getType());
		GeoCoordinateReferenceSystem geographic = (GeoCoordinateReferenceSystem) metadata
				.getCoordinateReferenceSystem();
		assertEquals(CRSType.GEOGRAPHIC, geographic.getType());
		assertEquals("WGS 84 (G1762)", geographic.getName());
		assertEquals(2005.0, geographic.getDynamic().getReferenceEpoch(), 0);
		assertEquals("World Geodetic System 1984 (G1762)",
				geographic.getReferenceFrame().getName());
		assertEquals("WGS 84",
				geographic.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137, geographic.getReferenceFrame().getEllipsoid()
				.getSemiMajorAxis(), 0);
		assertEquals(298.257223563, geographic.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals(UnitType.LENGTHUNIT, geographic.getReferenceFrame()
				.getEllipsoid().getUnit().getType());
		assertEquals("metre", geographic.getReferenceFrame().getEllipsoid()
				.getUnit().getName());
		assertEquals(1.0, geographic.getReferenceFrame().getEllipsoid()
				.getUnit().getConversionFactor(), 0);
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				geographic.getCoordinateSystem().getType());
		assertEquals(3, geographic.getCoordinateSystem().getDimension());
		assertEquals("lat", geographic.getCoordinateSystem().getAxes().get(0)
				.getAbbreviation());
		assertEquals(AxisDirectionType.NORTH, geographic.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals(UnitType.ANGLEUNIT, geographic.getCoordinateSystem()
				.getAxes().get(0).getUnit().getType());
		assertEquals("degree", geographic.getCoordinateSystem().getAxes().get(0)
				.getUnit().getName());
		assertEquals(0.0174532925199433, geographic.getCoordinateSystem()
				.getAxes().get(0).getUnit().getConversionFactor(), 0);
		assertEquals("lon", geographic.getCoordinateSystem().getAxes().get(1)
				.getAbbreviation());
		assertEquals(AxisDirectionType.EAST, geographic.getCoordinateSystem()
				.getAxes().get(1).getDirection());
		assertEquals(UnitType.ANGLEUNIT, geographic.getCoordinateSystem()
				.getAxes().get(1).getUnit().getType());
		assertEquals("degree", geographic.getCoordinateSystem().getAxes().get(1)
				.getUnit().getName());
		assertEquals(0.0174532925199433, geographic.getCoordinateSystem()
				.getAxes().get(1).getUnit().getConversionFactor(), 0);
		assertEquals("ellipsoidal height",
				geographic.getCoordinateSystem().getAxes().get(2).getName());
		assertEquals("h", geographic.getCoordinateSystem().getAxes().get(2)
				.getAbbreviation());
		assertEquals(AxisDirectionType.UP, geographic.getCoordinateSystem()
				.getAxes().get(2).getDirection());
		assertEquals(UnitType.LENGTHUNIT, geographic.getCoordinateSystem()
				.getAxes().get(2).getUnit().getType());
		assertEquals("metre", geographic.getCoordinateSystem().getAxes().get(2)
				.getUnit().getName());
		assertEquals(1.0, geographic.getCoordinateSystem().getAxes().get(2)
				.getUnit().getConversionFactor(), 0);
		assertEquals(2016.47, metadata.getEpoch(), 0);

		text = text.replace("6378137", "6378137.0");
		assertEquals(text, metadata.toString());
		assertEquals(text, CRSWriter.write(metadata));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(metadata));

	}

	/**
	 * Test coordinate operation
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testCoordinateOperation() throws IOException {

		String text = "COORDINATEOPERATION[\"Tokyo to JGD2000\",VERSION[\"GSI\"],"
				+ "SOURCECRS[GEODCRS[\"Tokyo\",DATUM[\"Tokyo 1918\","
				+ "ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1.0]]],"
				+ "CS[Cartesian,3],AXIS[\"(X)\",geocentricX,ORDER[1]],"
				+ "AXIS[\"(Y)\",geocentricY,ORDER[2]],AXIS[\"(Z)\",geocentricZ,ORDER[3]],"
				+ "LENGTHUNIT[\"metre\",1.0]]],"
				+ "TARGETCRS[GEODCRS[\"JGD2000\","
				+ "DATUM[\"Japanese Geodetic Datum 2000\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,LENGTHUNIT[\"metre\",1.0]]],"
				+ "CS[Cartesian,3],AXIS[\"(X)\",geocentricX],"
				+ "AXIS[\"(Y)\",geocentricY],AXIS[\"(Z)\",geocentricZ],"
				+ "LENGTHUNIT[\"metre\",1.0]]],"
				+ "METHOD[\"Geocentric translations\",ID[\"EPSG\",1031]],"
				+ "PARAMETER[\"X-axis translation\",-146.414,"
				+ "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",8605]],"
				+ "PARAMETER[\"Y-axis translation\",507.337,"
				+ "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",8606]],"
				+ "PARAMETER[\"Z-axis translation\",680.507,"
				+ "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",8607]]]";

		CRS crs = CRSReader.read(text, true);
		CoordinateOperation operation = CRSReader.readCoordinateOperation(text);
		assertEquals(crs, operation);
		assertEquals(CRSType.COORDINATE_OPERATION, operation.getType());
		assertEquals("Tokyo to JGD2000", operation.getName());
		assertEquals("GSI", operation.getVersion());
		GeoCoordinateReferenceSystem geodetic1 = (GeoCoordinateReferenceSystem) operation
				.getSource();
		assertEquals(CRSType.GEODETIC, geodetic1.getType());
		assertEquals("Tokyo", geodetic1.getName());
		GeoCoordinateReferenceSystem geodetic2 = (GeoCoordinateReferenceSystem) operation
				.getTarget();
		assertEquals(CRSType.GEODETIC, geodetic2.getType());
		assertEquals("JGD2000", geodetic2.getName());
		assertEquals("Geocentric translations",
				operation.getMethod().getName());
		assertEquals("EPSG",
				operation.getMethod().getIdentifiers().get(0).getName());
		assertEquals("1031", operation.getMethod().getIdentifiers().get(0)
				.getUniqueIdentifier());
		assertEquals("X-axis translation",
				operation.getParameters().get(0).getName());
		assertEquals(-146.414,
				((OperationParameter) operation.getParameters().get(0))
						.getValue(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getType());
		assertEquals("metre",
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getName());
		assertEquals(1.0,
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG",
				((OperationParameter) operation.getParameters().get(0))
						.getIdentifiers().get(0).getName());
		assertEquals("8605",
				((OperationParameter) operation.getParameters().get(0))
						.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Y-axis translation",
				operation.getParameters().get(1).getName());
		assertEquals(507.337,
				((OperationParameter) operation.getParameters().get(1))
						.getValue(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getType());
		assertEquals("metre",
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getName());
		assertEquals(1.0,
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG",
				((OperationParameter) operation.getParameters().get(1))
						.getIdentifiers().get(0).getName());
		assertEquals("8606",
				((OperationParameter) operation.getParameters().get(1))
						.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("Z-axis translation",
				operation.getParameters().get(2).getName());
		assertEquals(680.507,
				((OperationParameter) operation.getParameters().get(2))
						.getValue(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getType());
		assertEquals("metre",
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getName());
		assertEquals(1.0,
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG",
				((OperationParameter) operation.getParameters().get(2))
						.getIdentifiers().get(0).getName());
		assertEquals("8607",
				((OperationParameter) operation.getParameters().get(2))
						.getIdentifiers().get(0).getUniqueIdentifier());

		assertEquals(text, operation.toString());
		assertEquals(text, CRSWriter.write(operation));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(operation));

		text = "COORDINATEOPERATION[\"AGD84 to GDA94\","
				+ "SOURCECRS[GEOGCRS[\"AGD84\",DATUM[\"Australian Geodetic Datum 1984\",ELLIPSOID[\"Australian National Spheroid\",6378160,298.25,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7003]],ID[\"EPSG\",6203]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4203]]],"
				+ "TARGETCRS[GEOGCRS[\"GDA94\",DATUM[\"Geocentric Datum of Australia 1994\",ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ID[\"EPSG\",6283]],CS[ellipsoidal,3,ID[\"EPSG\",6423]],AXIS[\"Geodetic latitude (Lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Geodetic longitude (Lon)\",east,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],ID[\"EPSG\",4939]]],"
				+ "METHOD[\"Geocentric translations\",ID[\"EPSG\",1031]],"
				+ "PARAMETER[\"X-axis translation\",-128.5,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Y-axis translation\",-53.0,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Z-axis translation\",153.4,LENGTHUNIT[\"metre\",1.0]],"
				+ "OPERATIONACCURACY[5],"
				+ "USAGE[SCOPE[\"Low accuracy applications.\"],"
				+ "AREA[\"Australia onshore\"],BBOX[-43.7,112.85,-9.87,153.68]],"
				+ "REMARK[\"Use NTv2 file for better accuracy\"]]";

		crs = CRSReader.read(text, true);
		operation = CRSReader.readCoordinateOperation(text);
		assertEquals(crs, operation);
		assertEquals(CRSType.COORDINATE_OPERATION, operation.getType());
		assertEquals("AGD84 to GDA94", operation.getName());
		GeoCoordinateReferenceSystem geographic1 = (GeoCoordinateReferenceSystem) operation
				.getSource();
		assertEquals(CRSType.GEOGRAPHIC, geographic1.getType());
		assertEquals("AGD84", geographic1.getName());
		GeoCoordinateReferenceSystem geographic2 = (GeoCoordinateReferenceSystem) operation
				.getTarget();
		assertEquals(CRSType.GEOGRAPHIC, geographic2.getType());
		assertEquals("GDA94", geographic2.getName());
		assertEquals("Geocentric translations",
				operation.getMethod().getName());
		assertEquals("EPSG",
				operation.getMethod().getIdentifiers().get(0).getName());
		assertEquals("1031", operation.getMethod().getIdentifiers().get(0)
				.getUniqueIdentifier());
		assertEquals("X-axis translation",
				operation.getParameters().get(0).getName());
		assertEquals(-128.5,
				((OperationParameter) operation.getParameters().get(0))
						.getValue(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getType());
		assertEquals("metre",
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getName());
		assertEquals(1.0,
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Y-axis translation",
				operation.getParameters().get(1).getName());
		assertEquals(-53.0,
				((OperationParameter) operation.getParameters().get(1))
						.getValue(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getType());
		assertEquals("metre",
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getName());
		assertEquals(1.0,
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Z-axis translation",
				operation.getParameters().get(2).getName());
		assertEquals(153.4,
				((OperationParameter) operation.getParameters().get(2))
						.getValue(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getType());
		assertEquals("metre",
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getName());
		assertEquals(1.0,
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getConversionFactor(),
				0);
		assertEquals(5, operation.getAccuracy(), 0);
		assertEquals("Low accuracy applications.",
				operation.getUsages().get(0).getScope());
		assertEquals("Australia onshore",
				operation.getUsages().get(0).getExtent().getAreaDescription());
		assertEquals(-43.7, operation.getUsages().get(0).getExtent()
				.getGeographicBoundingBox().getLowerLeftLatitude(), 0);
		assertEquals(112.85, operation.getUsages().get(0).getExtent()
				.getGeographicBoundingBox().getLowerLeftLongitude(), 0);
		assertEquals(-9.87, operation.getUsages().get(0).getExtent()
				.getGeographicBoundingBox().getUpperRightLatitude(), 0);
		assertEquals(153.68,
				operation.getUsages().get(0).getExtent()
						.getGeographicBoundingBox().getUpperRightLongitude(),
				0);
		assertEquals("Use NTv2 file for better accuracy",
				operation.getRemark());

		text = text.replace("6378160", "6378160.0")
				.replace("6378137", "6378137.0").replace(",1,", ",1.0,")
				.replace("[5]", "[5.0]");
		assertEquals(text, operation.toString());
		assertEquals(text, CRSWriter.write(operation));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(operation));

		text = "COORDINATEOPERATION[\"NZGD49 to NZGD2000\","
				+ "SOURCECRS[PROJCRS[\"NZGD49 / New Zealand Map Grid\",BASEGEOGCRS[\"NZGD49\",DATUM[\"New Zealand Geodetic Datum 1949\",ELLIPSOID[\"International 1924\",6378388,297,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7022]],ID[\"EPSG\",6272]],ID[\"EPSG\",4272]],CONVERSION[\"New Zealand Map Grid\",METHOD[\"New Zealand Map Grid\",ID[\"EPSG\",9811]],PARAMETER[\"Latitude of natural origin\",-41,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],PARAMETER[\"Longitude of natural origin\",173,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],PARAMETER[\"False easting\",2510000,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],PARAMETER[\"False northing\",6023150,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],ID[\"EPSG\",19917]],CS[Cartesian,2,ID[\"EPSG\",4400]],AXIS[\"Easting (E)\",east],AXIS[\"Northing (N)\",north],LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",27200]]],"
				+ "TARGETCRS[GEOGCRS[\"NZGD2000\",DATUM[\"New Zealand Geodetic Datum 2000\",ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ID[\"EPSG\",6167]],CS[ellipsoidal,3,ID[\"EPSG\",6423]],AXIS[\"Geodetic latitude (Lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Geodetic longitude (Lon)\",east,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],ID[\"EPSG\",4959]]],"
				+ "METHOD[\"NTv2\",ID[\"EPSG\",9615]],"
				+ "PARAMETERFILE[\"Latitude and longitude difference file\",\"nzgd2kgrid0005.gsb\"],"
				+ "ID[\"EPSG\",1568,CITATION[\"LINZS25000\"],"
				+ "URI[\"http://www.linz.govt.nz/geodetic/software-downloads/\"]],"
				+ "REMARK[\"Coordinate transformation accuracy 0.1-1.0m\"]]";

		crs = CRSReader.read(text, true);
		operation = CRSReader.readCoordinateOperation(text);
		assertEquals(crs, operation);
		assertEquals(CRSType.COORDINATE_OPERATION, operation.getType());
		assertEquals("NZGD49 to NZGD2000", operation.getName());
		ProjectedCoordinateReferenceSystem projected = (ProjectedCoordinateReferenceSystem) operation
				.getSource();
		assertEquals(CRSType.PROJECTED, projected.getType());
		assertEquals("NZGD49 / New Zealand Map Grid", projected.getName());
		GeoCoordinateReferenceSystem geographic = (GeoCoordinateReferenceSystem) operation
				.getTarget();
		assertEquals(CRSType.GEOGRAPHIC, geographic.getType());
		assertEquals("NZGD2000", geographic.getName());
		assertEquals("NTv2", operation.getMethod().getName());
		assertEquals("EPSG",
				operation.getMethod().getIdentifiers().get(0).getName());
		assertEquals("9615", operation.getMethod().getIdentifiers().get(0)
				.getUniqueIdentifier());
		assertEquals("Latitude and longitude difference file",
				operation.getParameters().get(0).getName());
		assertEquals("nzgd2kgrid0005.gsb",
				((OperationParameterFile) operation.getParameters().get(0))
						.getFileName());
		assertEquals("EPSG", operation.getIdentifiers().get(0).getName());
		assertEquals("1568",
				operation.getIdentifiers().get(0).getUniqueIdentifier());
		assertEquals("LINZS25000",
				operation.getIdentifiers().get(0).getCitation());
		assertEquals("http://www.linz.govt.nz/geodetic/software-downloads/",
				operation.getIdentifiers().get(0).getUri());
		assertEquals("Coordinate transformation accuracy 0.1-1.0m",
				operation.getRemark());

		text = text.replace("6378388", "6378388.0").replace("297", "297.0")
				.replace("6023150", "6023150.0").replace("6378137", "6378137.0")
				.replace(",1,", ",1.0,").replace("-41", "-41.0")
				.replace("173", "173.0").replace("2510000", "2510000.0");
		assertEquals(text, operation.toString());
		assertEquals(text, CRSWriter.write(operation));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(operation));

		text = "COORDINATEOPERATION[\"Amersfoort to ETRS89 (3)\","
				+ "SOURCECRS[GEOGCRS[\"Amersfoort\",DATUM[\"Amersfoort\",ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7004]],ID[\"EPSG\",6289]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4289]]],"
				+ "TARGETCRS[GEOGCRS[\"ETRS89\",ENSEMBLE[\"European Terrestrial Reference System 1989 ensemble\",MEMBER[\"European Terrestrial Reference Frame 1989\",ID[\"EPSG\",1178]],MEMBER[\"European Terrestrial Reference Frame 1990\",ID[\"EPSG\",1179]],MEMBER[\"European Terrestrial Reference Frame 1991\",ID[\"EPSG\",1180]],MEMBER[\"European Terrestrial Reference Frame 1992\",ID[\"EPSG\",1181]],MEMBER[\"European Terrestrial Reference Frame 1993\",ID[\"EPSG\",1182]],MEMBER[\"European Terrestrial Reference Frame 1994\",ID[\"EPSG\",1183]],MEMBER[\"European Terrestrial Reference Frame 1996\",ID[\"EPSG\",1184]],MEMBER[\"European Terrestrial Reference Frame 1997\",ID[\"EPSG\",1185]],MEMBER[\"European Terrestrial Reference Frame 2000\",ID[\"EPSG\",1186]],MEMBER[\"European Terrestrial Reference Frame 2005\",ID[\"EPSG\",1204]],MEMBER[\"European Terrestrial Reference Frame 2014\",ID[\"EPSG\",1206]],ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ENSEMBLEACCURACY[0.1],ID[\"EPSG\",6258]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4258]]],"
				+ "METHOD[\"Coordinate Frame\"],"
				+ "PARAMETER[\"X-axis translation\",565.2369,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Y-axis translation\",50.0087,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Z-axis translation\",465.658,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"X-axis rotation\",1.9725,ANGLEUNIT[\"microradian\",1E-06]],"
				+ "PARAMETER[\"Y-axis rotation\",-1.7004,ANGLEUNIT[\"microradian\",1E-06]],"
				+ "PARAMETER[\"Z-axis rotation\",9.0677,ANGLEUNIT[\"microradian\",1E-06]],"
				+ "PARAMETER[\"Scale difference\",4.0812,SCALEUNIT[\"parts per million\",1E-06]],"
				+ "ID[\"EPSG\",15739]]";

		crs = CRSReader.read(text, true);
		operation = CRSReader.readCoordinateOperation(text);
		assertEquals(crs, operation);
		assertEquals(CRSType.COORDINATE_OPERATION, operation.getType());
		assertEquals("Amersfoort to ETRS89 (3)", operation.getName());
		geographic1 = (GeoCoordinateReferenceSystem) operation.getSource();
		assertEquals(CRSType.GEOGRAPHIC, geographic1.getType());
		assertEquals("Amersfoort", geographic1.getName());
		geographic2 = (GeoCoordinateReferenceSystem) operation.getTarget();
		assertEquals(CRSType.GEOGRAPHIC, geographic2.getType());
		assertEquals("ETRS89", geographic2.getName());
		assertEquals("Coordinate Frame", operation.getMethod().getName());
		assertEquals("X-axis translation",
				operation.getParameters().get(0).getName());
		assertEquals(565.2369,
				((OperationParameter) operation.getParameters().get(0))
						.getValue(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getType());
		assertEquals("metre",
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getName());
		assertEquals(1.0,
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Y-axis translation",
				operation.getParameters().get(1).getName());
		assertEquals(50.0087,
				((OperationParameter) operation.getParameters().get(1))
						.getValue(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getType());
		assertEquals("metre",
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getName());
		assertEquals(1.0,
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Z-axis translation",
				operation.getParameters().get(2).getName());
		assertEquals(465.658,
				((OperationParameter) operation.getParameters().get(2))
						.getValue(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getType());
		assertEquals("metre",
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getName());
		assertEquals(1.0,
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("X-axis rotation",
				operation.getParameters().get(3).getName());
		assertEquals(1.9725,
				((OperationParameter) operation.getParameters().get(3))
						.getValue(),
				0);
		assertEquals(UnitType.ANGLEUNIT,
				((OperationParameter) operation.getParameters().get(3))
						.getUnit().getType());
		assertEquals("microradian",
				((OperationParameter) operation.getParameters().get(3))
						.getUnit().getName());
		assertEquals(1E-06,
				((OperationParameter) operation.getParameters().get(3))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Y-axis rotation",
				operation.getParameters().get(4).getName());
		assertEquals(-1.7004,
				((OperationParameter) operation.getParameters().get(4))
						.getValue(),
				0);
		assertEquals(UnitType.ANGLEUNIT,
				((OperationParameter) operation.getParameters().get(4))
						.getUnit().getType());
		assertEquals("microradian",
				((OperationParameter) operation.getParameters().get(4))
						.getUnit().getName());
		assertEquals(1E-06,
				((OperationParameter) operation.getParameters().get(4))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Z-axis rotation",
				operation.getParameters().get(5).getName());
		assertEquals(9.0677,
				((OperationParameter) operation.getParameters().get(5))
						.getValue(),
				0);
		assertEquals(UnitType.ANGLEUNIT,
				((OperationParameter) operation.getParameters().get(5))
						.getUnit().getType());
		assertEquals("microradian",
				((OperationParameter) operation.getParameters().get(5))
						.getUnit().getName());
		assertEquals(1E-06,
				((OperationParameter) operation.getParameters().get(5))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Scale difference",
				operation.getParameters().get(6).getName());
		assertEquals(4.0812,
				((OperationParameter) operation.getParameters().get(6))
						.getValue(),
				0);
		assertEquals(UnitType.SCALEUNIT,
				((OperationParameter) operation.getParameters().get(6))
						.getUnit().getType());
		assertEquals("parts per million",
				((OperationParameter) operation.getParameters().get(6))
						.getUnit().getName());
		assertEquals(1E-06,
				((OperationParameter) operation.getParameters().get(6))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("EPSG", operation.getIdentifiers().get(0).getName());
		assertEquals("15739",
				operation.getIdentifiers().get(0).getUniqueIdentifier());

		text = text.replace(",1,", ",1.0,").replace("6378137", "6378137.0")
				.replace("1E-06", "1.0E-6");
		assertEquals(text, operation.toString());
		assertEquals(text, CRSWriter.write(operation));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(operation));

		text = "COORDINATEOPERATION[\"DHHN92 height to EVRF2007 height\","
				+ "SOURCECRS[VERTCRS[\"DHHN92 height\",VDATUM[\"Deutsches Haupthoehennetz 1992\",ID[\"EPSG\",5181]],CS[vertical,1,ID[\"EPSG\",6499]],AXIS[\"Gravity-related height (H)\",up],LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",5783]]],"
				+ "TARGETCRS[VERTCRS[\"EVRF2007 height\",VDATUM[\"European Vertical Reference Frame 2007\",ID[\"EPSG\",5215]],CS[vertical,1,ID[\"EPSG\",6499]],AXIS[\"Gravity-related height (H)\",up],LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",5621]]],"
				+ "METHOD[\"Vertical Offset and Slope\",ID[\"EPSG\",1046]],"
				+ "PARAMETER[\"Inclination in latitude\",-0.010,"
				+ "ANGLEUNIT[\"arc-second\",4.84813681109535E-06]],"
				+ "PARAMETER[\"Inclination in longitude\",0.002,"
				+ "ANGLEUNIT[\"arc-second\",4.84813681109535E-06]],"
				+ "PARAMETER[\"Vertical offset\",0.015,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Ordinate 1 of evaluation point\",51.05,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "PARAMETER[\"Ordinate 2 of evaluation point\",10.2166666666667,"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]],"
				+ "INTERPOLATIONCRS[GEOGCRS[\"ETRS89\",ENSEMBLE[\"European Terrestrial Reference System 1989 ensemble\",MEMBER[\"European Terrestrial Reference Frame 1989\",ID[\"EPSG\",1178]],MEMBER[\"European Terrestrial Reference Frame 1990\",ID[\"EPSG\",1179]],MEMBER[\"European Terrestrial Reference Frame 1991\",ID[\"EPSG\",1180]],MEMBER[\"European Terrestrial Reference Frame 1992\",ID[\"EPSG\",1181]],MEMBER[\"European Terrestrial Reference Frame 1993\",ID[\"EPSG\",1182]],MEMBER[\"European Terrestrial Reference Frame 1994\",ID[\"EPSG\",1183]],MEMBER[\"European Terrestrial Reference Frame 1996\",ID[\"EPSG\",1184]],MEMBER[\"European Terrestrial Reference Frame 1997\",ID[\"EPSG\",1185]],MEMBER[\"European Terrestrial Reference Frame 2000\",ID[\"EPSG\",1186]],MEMBER[\"European Terrestrial Reference Frame 2005\",ID[\"EPSG\",1204]],MEMBER[\"European Terrestrial Reference Frame 2014\",ID[\"EPSG\",1206]],ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ENSEMBLEACCURACY[0.1],ID[\"EPSG\",6258]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4258]]],"
				+ "OPERATIONACCURACY[0.1],"
				+ "REMARK[\"Determined at 427 points. RMS residual 0.002m, maximum 0.007m\"]]";

		crs = CRSReader.read(text, true);
		operation = CRSReader.readCoordinateOperation(text);
		assertEquals(crs, operation);
		assertEquals(CRSType.COORDINATE_OPERATION, operation.getType());
		assertEquals("DHHN92 height to EVRF2007 height", operation.getName());
		VerticalCoordinateReferenceSystem vertical1 = (VerticalCoordinateReferenceSystem) operation
				.getSource();
		assertEquals(CRSType.VERTICAL, vertical1.getType());
		assertEquals("DHHN92 height", vertical1.getName());
		VerticalCoordinateReferenceSystem vertical2 = (VerticalCoordinateReferenceSystem) operation
				.getTarget();
		assertEquals(CRSType.VERTICAL, vertical2.getType());
		assertEquals("EVRF2007 height", vertical2.getName());
		assertEquals("Vertical Offset and Slope",
				operation.getMethod().getName());
		assertEquals("EPSG",
				operation.getMethod().getIdentifiers().get(0).getName());
		assertEquals("1046", operation.getMethod().getIdentifiers().get(0)
				.getUniqueIdentifier());
		assertEquals("Inclination in latitude",
				operation.getParameters().get(0).getName());
		assertEquals(-0.010,
				((OperationParameter) operation.getParameters().get(0))
						.getValue(),
				0);
		assertEquals(UnitType.ANGLEUNIT,
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getType());
		assertEquals("arc-second",
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getName());
		assertEquals(4.84813681109535E-06,
				((OperationParameter) operation.getParameters().get(0))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Inclination in longitude",
				operation.getParameters().get(1).getName());
		assertEquals(0.002,
				((OperationParameter) operation.getParameters().get(1))
						.getValue(),
				0);
		assertEquals(UnitType.ANGLEUNIT,
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getType());
		assertEquals("arc-second",
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getName());
		assertEquals(4.84813681109535E-06,
				((OperationParameter) operation.getParameters().get(1))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Vertical offset",
				operation.getParameters().get(2).getName());
		assertEquals(0.015,
				((OperationParameter) operation.getParameters().get(2))
						.getValue(),
				0);
		assertEquals(UnitType.LENGTHUNIT,
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getType());
		assertEquals("metre",
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getName());
		assertEquals(1.0,
				((OperationParameter) operation.getParameters().get(2))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Ordinate 1 of evaluation point",
				operation.getParameters().get(3).getName());
		assertEquals(51.05,
				((OperationParameter) operation.getParameters().get(3))
						.getValue(),
				0);
		assertEquals(UnitType.ANGLEUNIT,
				((OperationParameter) operation.getParameters().get(3))
						.getUnit().getType());
		assertEquals("degree",
				((OperationParameter) operation.getParameters().get(3))
						.getUnit().getName());
		assertEquals(0.0174532925199433,
				((OperationParameter) operation.getParameters().get(3))
						.getUnit().getConversionFactor(),
				0);
		assertEquals("Ordinate 2 of evaluation point",
				operation.getParameters().get(4).getName());
		assertEquals(10.2166666666667,
				((OperationParameter) operation.getParameters().get(4))
						.getValue(),
				0);
		assertEquals(UnitType.ANGLEUNIT,
				((OperationParameter) operation.getParameters().get(4))
						.getUnit().getType());
		assertEquals("degree",
				((OperationParameter) operation.getParameters().get(4))
						.getUnit().getName());
		assertEquals(0.0174532925199433,
				((OperationParameter) operation.getParameters().get(4))
						.getUnit().getConversionFactor(),
				0);
		geographic = (GeoCoordinateReferenceSystem) operation
				.getInterpolation();
		assertEquals(CRSType.GEOGRAPHIC, geographic.getType());
		assertEquals("ETRS89", geographic.getName());
		assertEquals(0.1, operation.getAccuracy(), 0);
		assertEquals(
				"Determined at 427 points. RMS residual 0.002m, maximum 0.007m",
				operation.getRemark());

		text = text.replace("\",1,", "\",1.0,").replace("-0.010", "-0.01")
				.replace("-06", "-6").replace("6378137", "6378137.0");
		assertEquals(text, operation.toString());
		assertEquals(text, CRSWriter.write(operation));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(operation));

	}

	/**
	 * Test point motion operation
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testPointMotionOperation() throws IOException {

		String text = "POINTMOTIONOPERATION[\"Canada velocity grid v6\","
				+ "SOURCECRS[GEOGCRS[\"NAD83(CSRS)v6\",DATUM[\"North American Datum of 1983 (CSRS) version 6\",ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ID[\"EPSG\",1197]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",8252]]],"
				+ "METHOD[\"Point motion by grid (Canada NTv2_Vel)\"],"
				+ "PARAMETERFILE[\"Point motion velocity grid file\",\"cvg60.cvb\"],"
				+ "OPERATIONACCURACY[0.01]]";

		CRS crs = CRSReader.read(text, true);
		PointMotionOperation operation = CRSReader
				.readPointMotionOperation(text);
		assertEquals(crs, operation);
		assertEquals(CRSType.POINT_MOTION_OPERATION, operation.getType());
		assertEquals("Canada velocity grid v6", operation.getName());
		GeoCoordinateReferenceSystem geographic = (GeoCoordinateReferenceSystem) operation
				.getSource();
		assertEquals(CRSType.GEOGRAPHIC, geographic.getType());
		assertEquals("NAD83(CSRS)v6", geographic.getName());
		assertEquals("Point motion by grid (Canada NTv2_Vel)",
				operation.getMethod().getName());
		assertEquals("Point motion velocity grid file",
				operation.getParameters().get(0).getName());
		assertEquals("cvg60.cvb",
				((OperationParameterFile) operation.getParameters().get(0))
						.getFileName());
		assertEquals(0.01, operation.getAccuracy(), 0);

		text = text.replace("\",1,", "\",1.0,").replace("6378137", "6378137.0");
		assertEquals(text, operation.toString());
		assertEquals(text, CRSWriter.write(operation));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(operation));

	}

	/**
	 * Test concatenated operation
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testConcatenatedOperation() throws IOException {

		String text = "CONCATENATEDOPERATION[\"RT90 to KKJ\","
				+ "SOURCECRS[GEOGCRS[\"RT90\",DATUM[\"Rikets koordinatsystem 1990\",ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7004]],ID[\"EPSG\",6124]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4124]]],"
				+ "TARGETCRS[GEOGCRS[\"KKJ\",DATUM[\"Kartastokoordinaattijarjestelma (1966)\",ELLIPSOID[\"International 1924\",6378388,297,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7022]],ID[\"EPSG\",6123]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4123]]],"
				+ "STEP[COORDINATEOPERATION[\"RT90 to ETRS89\","
				+ "SOURCECRS[GEOGCRS[\"RT90\",DATUM[\"Rikets koordinatsystem 1990\",ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7004]],ID[\"EPSG\",6124]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4124]]],"
				+ "TARGETCRS[GEOGCRS[\"ETRS89\",ENSEMBLE[\"European Terrestrial Reference System 1989 ensemble\",MEMBER[\"European Terrestrial Reference Frame 1989\",ID[\"EPSG\",1178]],MEMBER[\"European Terrestrial Reference Frame 1990\",ID[\"EPSG\",1179]],MEMBER[\"European Terrestrial Reference Frame 1991\",ID[\"EPSG\",1180]],MEMBER[\"European Terrestrial Reference Frame 1992\",ID[\"EPSG\",1181]],MEMBER[\"European Terrestrial Reference Frame 1993\",ID[\"EPSG\",1182]],MEMBER[\"European Terrestrial Reference Frame 1994\",ID[\"EPSG\",1183]],MEMBER[\"European Terrestrial Reference Frame 1996\",ID[\"EPSG\",1184]],MEMBER[\"European Terrestrial Reference Frame 1997\",ID[\"EPSG\",1185]],MEMBER[\"European Terrestrial Reference Frame 2000\",ID[\"EPSG\",1186]],MEMBER[\"European Terrestrial Reference Frame 2005\",ID[\"EPSG\",1204]],MEMBER[\"European Terrestrial Reference Frame 2014\",ID[\"EPSG\",1206]],ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ENSEMBLEACCURACY[0.1],ID[\"EPSG\",6258]],CS[ellipsoidal,3,ID[\"EPSG\",6423]],AXIS[\"Geodetic latitude (Lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Geodetic longitude (Lon)\",east,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],ID[\"EPSG\",4937]]],"
				+ "METHOD[\"Coordinate Frame rotation\",ID[\"EPSG\",9607]],"
				+ "PARAMETER[\"X-axis translation\",414.1,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Y-axis translation\",41.3,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Z-axis translation\",603.1,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"X-axis rotation\",0.855,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Y-axis rotation\",-2.141,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Z-axis rotation\",7.023,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Scale difference\",0.0,SCALEUNIT[\"parts per million\",1.0E-6]],"
				+ "ID[\"EPSG\",1437]]],"
				+ "STEP[COORDINATEOPERATION[\"KKJ to ETRS89\","
				+ "SOURCECRS[GEOGCRS[\"KKJ\",DATUM[\"Kartastokoordinaattijarjestelma (1966)\",ELLIPSOID[\"International 1924\",6378388,297,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7022]],ID[\"EPSG\",6123]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4123]]],"
				+ "TARGETCRS[GEOGCRS[\"ETRS89\",ENSEMBLE[\"European Terrestrial Reference System 1989 ensemble\",MEMBER[\"European Terrestrial Reference Frame 1989\",ID[\"EPSG\",1178]],MEMBER[\"European Terrestrial Reference Frame 1990\",ID[\"EPSG\",1179]],MEMBER[\"European Terrestrial Reference Frame 1991\",ID[\"EPSG\",1180]],MEMBER[\"European Terrestrial Reference Frame 1992\",ID[\"EPSG\",1181]],MEMBER[\"European Terrestrial Reference Frame 1993\",ID[\"EPSG\",1182]],MEMBER[\"European Terrestrial Reference Frame 1994\",ID[\"EPSG\",1183]],MEMBER[\"European Terrestrial Reference Frame 1996\",ID[\"EPSG\",1184]],MEMBER[\"European Terrestrial Reference Frame 1997\",ID[\"EPSG\",1185]],MEMBER[\"European Terrestrial Reference Frame 2000\",ID[\"EPSG\",1186]],MEMBER[\"European Terrestrial Reference Frame 2005\",ID[\"EPSG\",1204]],MEMBER[\"European Terrestrial Reference Frame 2014\",ID[\"EPSG\",1206]],ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ENSEMBLEACCURACY[0.1],ID[\"EPSG\",6258]],CS[ellipsoidal,3,ID[\"EPSG\",6423]],AXIS[\"Geodetic latitude (Lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Geodetic longitude (Lon)\",east,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],ID[\"EPSG\",4937]]],"
				+ "METHOD[\"Position Vector transformation\",ID[\"EPSG\",9606]],"
				+ "PARAMETER[\"X-axis translation\",-96.062,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Y-axis translation\",-106.1,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Z-axis translation\",-119.2,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"X-axis rotation\",4.09,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Y-axis rotation\",0.218,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Z-axis rotation\",-1.05,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Scale difference\",1.37,SCALEUNIT[\"parts per million\",1.0E-6]],"
				+ "ID[\"EPSG\",10098]]],"
				+ "USAGE[SCOPE[\"Concatenated operation scope description.\"],"
				+ "AREA[\"Concatenated operation area description.\"]],"
				+ "REMARK[\"Step 2 is applied in reverse direction\"]]";

		CRS crs = CRSReader.read(text, true);
		ConcatenatedOperation operation = CRSReader
				.readConcatenatedOperation(text);
		assertEquals(crs, operation);
		assertEquals(CRSType.CONCATENATED_OPERATION, operation.getType());
		// TODO

		text = text.replace("\",1,", "\",1.0,").replace("6378388", "6378388.0")
				.replace("297", "297.0").replace("6378137", "6378137.0");
		assertEquals(text, operation.toString());
		assertEquals(text, CRSWriter.write(operation));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(operation));

		text = "CONCATENATEDOPERATION[\"RT90 to KKJ\","
				+ "SOURCECRS[GEOGCRS[\"RT90\",DATUM[\"Rikets koordinatsystem 1990\",ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7004]],ID[\"EPSG\",6124]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4124]]],"
				+ "TARGETCRS[GEOGCRS[\"KKJ\",DATUM[\"Kartastokoordinaattijarjestelma (1966)\",ELLIPSOID[\"International 1924\",6378388,297,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7022]],ID[\"EPSG\",6123]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4123]]],"
				+ "STEP[COORDINATEOPERATION[\"RT90 to ETRS89\","
				+ "SOURCECRS[GEOGCRS[\"RT90\",DATUM[\"Rikets koordinatsystem 1990\",ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7004]],ID[\"EPSG\",6124]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4124]]],"
				+ "TARGETCRS[GEOGCRS[\"ETRS89\",ENSEMBLE[\"European Terrestrial Reference System 1989 ensemble\",MEMBER[\"European Terrestrial Reference Frame 1989\",ID[\"EPSG\",1178]],MEMBER[\"European Terrestrial Reference Frame 1990\",ID[\"EPSG\",1179]],MEMBER[\"European Terrestrial Reference Frame 1991\",ID[\"EPSG\",1180]],MEMBER[\"European Terrestrial Reference Frame 1992\",ID[\"EPSG\",1181]],MEMBER[\"European Terrestrial Reference Frame 1993\",ID[\"EPSG\",1182]],MEMBER[\"European Terrestrial Reference Frame 1994\",ID[\"EPSG\",1183]],MEMBER[\"European Terrestrial Reference Frame 1996\",ID[\"EPSG\",1184]],MEMBER[\"European Terrestrial Reference Frame 1997\",ID[\"EPSG\",1185]],MEMBER[\"European Terrestrial Reference Frame 2000\",ID[\"EPSG\",1186]],MEMBER[\"European Terrestrial Reference Frame 2005\",ID[\"EPSG\",1204]],MEMBER[\"European Terrestrial Reference Frame 2014\",ID[\"EPSG\",1206]],ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ENSEMBLEACCURACY[0.1],ID[\"EPSG\",6258]],CS[ellipsoidal,3,ID[\"EPSG\",6423]],AXIS[\"Geodetic latitude (Lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Geodetic longitude (Lon)\",east,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],ID[\"EPSG\",4937]]],"
				+ "METHOD[\"Coordinate Frame rotation\",ID[\"EPSG\",9607]],"
				+ "PARAMETER[\"X-axis translation\",414.1,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Y-axis translation\",41.3,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Z-axis translation\",603.1,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"X-axis rotation\",0.855,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Y-axis rotation\",-2.141,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Z-axis rotation\",7.023,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Scale difference\",0.0,SCALEUNIT[\"parts per million\",1.0E-6]]]],"
				+ "STEP[COORDINATEOPERATION[\"ETRS89 to KKJ\","
				+ "SOURCECRS[GEOGCRS[\"ETRS89\",ENSEMBLE[\"European Terrestrial Reference System 1989 ensemble\",MEMBER[\"European Terrestrial Reference Frame 1989\",ID[\"EPSG\",1178]],MEMBER[\"European Terrestrial Reference Frame 1990\",ID[\"EPSG\",1179]],MEMBER[\"European Terrestrial Reference Frame 1991\",ID[\"EPSG\",1180]],MEMBER[\"European Terrestrial Reference Frame 1992\",ID[\"EPSG\",1181]],MEMBER[\"European Terrestrial Reference Frame 1993\",ID[\"EPSG\",1182]],MEMBER[\"European Terrestrial Reference Frame 1994\",ID[\"EPSG\",1183]],MEMBER[\"European Terrestrial Reference Frame 1996\",ID[\"EPSG\",1184]],MEMBER[\"European Terrestrial Reference Frame 1997\",ID[\"EPSG\",1185]],MEMBER[\"European Terrestrial Reference Frame 2000\",ID[\"EPSG\",1186]],MEMBER[\"European Terrestrial Reference Frame 2005\",ID[\"EPSG\",1204]],MEMBER[\"European Terrestrial Reference Frame 2014\",ID[\"EPSG\",1206]],ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ENSEMBLEACCURACY[0.1],ID[\"EPSG\",6258]],CS[ellipsoidal,3,ID[\"EPSG\",6423]],AXIS[\"Geodetic latitude (Lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Geodetic longitude (Lon)\",east,ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]]],AXIS[\"Ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]]],ID[\"EPSG\",4937]]],"
				+ "TARGETCRS[GEOGCRS[\"KKJ\",DATUM[\"Kartastokoordinaattijarjestelma (1966)\",ELLIPSOID[\"International 1924\",6378388,297,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7022]],ID[\"EPSG\",6123]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4123]]],"
				+ "METHOD[\"Position Vector transformation\"],"
				+ "PARAMETER[\"X-axis translation\",96.062,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Y-axis translation\",106.1,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"Z-axis translation\",119.2,LENGTHUNIT[\"metre\",1.0]],"
				+ "PARAMETER[\"X-axis rotation\",-4.09,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Y-axis rotation\",-0.218,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Z-axis rotation\",1.05,ANGLEUNIT[\"arc-second\",4.84813681109535E-6]],"
				+ "PARAMETER[\"Scale difference\",-1.37,SCALEUNIT[\"parts per million\",1.0E-6]]]]]";

		crs = CRSReader.read(text, true);
		operation = CRSReader.readConcatenatedOperation(text);
		assertEquals(crs, operation);
		assertEquals(CRSType.CONCATENATED_OPERATION, operation.getType());
		// TODO

		text = text.replace("\",1,", "\",1.0,").replace("6378388", "6378388.0")
				.replace("297", "297.0").replace("6378137", "6378137.0");
		assertEquals(text, operation.toString());
		assertEquals(text, CRSWriter.write(operation));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(operation));

	}

	/**
	 * Test abridged coordinate transformation
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testAbridgedCoordinateTransformation() throws IOException {

		String text = "ABRIDGEDTRANSFORMATION[\"Tokyo to JGD2000 (GSI)\","
				+ "METHOD[\"Geocentric translations\",ID[\"EPSG\",1031]],"
				+ "PARAMETER[\"X-axis translation\",-146.414],"
				+ "PARAMETER[\"Y-axis translation\",507.337],"
				+ "PARAMETER[\"Z-axis translation\",680.507]]";
		CRSReader reader = new CRSReader(text);
		AbridgedCoordinateTransformation transformation = reader
				.readAbridgedCoordinateTransformation();
		assertEquals("Tokyo to JGD2000 (GSI)", transformation.getName());
		assertEquals("Geocentric translations",
				transformation.getMethod().getName());
		assertEquals("EPSG",
				transformation.getMethod().getIdentifiers().get(0).getName());
		assertEquals("1031", transformation.getMethod().getIdentifiers().get(0)
				.getUniqueIdentifier());
		assertEquals("X-axis translation",
				transformation.getParameters().get(0).getName());
		assertEquals(-146.414,
				((OperationParameter) transformation.getParameters().get(0))
						.getValue(),
				0);
		assertEquals("Y-axis translation",
				transformation.getParameters().get(1).getName());
		assertEquals(507.337,
				((OperationParameter) transformation.getParameters().get(1))
						.getValue(),
				0);
		assertEquals("Z-axis translation",
				transformation.getParameters().get(2).getName());
		assertEquals(680.507,
				((OperationParameter) transformation.getParameters().get(2))
						.getValue(),
				0);
		reader.close();
		assertEquals(text, transformation.toString());

	}

	/**
	 * Test bound coordinate reference system
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testBoundCoordinateReferenceSystem() throws IOException {

		String text = "BOUNDCRS[SOURCECRS[GEODCRS[\"NAD27\","
				+ "DATUM[\"North American Datum 1927\","
				+ "ELLIPSOID[\"Clarke 1866\",6378206.4,294.978698213]],"
				+ "CS[ellipsoidal,2],"
				+ "AXIS[\"latitude\",north],AXIS[\"longitude\",east],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]]],"
				+ "TARGETCRS[GEODCRS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137,298.2572221]],"
				+ "CS[ellipsoidal,2],"
				+ "AXIS[\"latitude\",north],AXIS[\"longitude\",east],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433]]],"
				+ "ABRIDGEDTRANSFORMATION[\"NAD27 to NAD83 Alaska\","
				+ "METHOD[\"NADCON\",ID[\"EPSG\",9613]],"
				+ "PARAMETERFILE[\"Latitude difference file\",\"alaska.las\"],"
				+ "PARAMETERFILE[\"Longitude difference file\",\"alaska.los\"]]]";

		CRS crs = CRSReader.read(text, true);
		BoundCoordinateReferenceSystem bound = CRSReader.readBound(text);
		assertEquals(crs, bound);
		assertEquals(CRSType.BOUND, bound.getType());
		// TODO

		text = text.replace("6378137", "6378137.0");
		assertEquals(text, bound.toString());
		assertEquals(text, CRSWriter.write(bound));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(bound));

		text = "BOUNDCRS["
				+ "SOURCECRS[GEOGCRS[\"NAD27\",DATUM[\"North American Datum 1927\",ELLIPSOID[\"Clarke 1866\",6378206.4,294.9786982,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7008]],ID[\"EPSG\",6267]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4267]]],"
				+ "TARGETCRS[GEOGCRS[\"NAD83\",DATUM[\"North American Datum 1983\",ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ID[\"EPSG\",6269]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4269]]],"
				+ "ABRIDGEDTRANSFORMATION[\"NAD27 to NAD83(86) National\","
				+ "METHOD[\"NTv2\",ID[\"EPSG\",9615]],"
				+ "PARAMETERFILE[\"Latitude and longitude difference file\",\"NTv2_0.gsb\"]]]";

		crs = CRSReader.read(text, true);
		bound = CRSReader.readBound(text);
		assertEquals(crs, bound);
		assertEquals(CRSType.BOUND, bound.getType());
		// TODO

		text = text.replace("6378137", "6378137.0").replace("\",1,", "\",1.0,");
		assertEquals(text, bound.toString());
		assertEquals(text, CRSWriter.write(bound));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(bound));

		text = "BOUNDCRS["
				+ "SOURCECRS[GEOGCRS[\"Amersfoort\",DATUM[\"Amersfoort\",ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7004]],ID[\"EPSG\",6289]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4289]]],"
				+ "TARGETCRS[GEOGCRS[\"ETRS89\",ENSEMBLE[\"European Terrestrial Reference System 1989 ensemble\",MEMBER[\"European Terrestrial Reference Frame 1989\",ID[\"EPSG\",1178]],MEMBER[\"European Terrestrial Reference Frame 1990\",ID[\"EPSG\",1179]],MEMBER[\"European Terrestrial Reference Frame 1991\",ID[\"EPSG\",1180]],MEMBER[\"European Terrestrial Reference Frame 1992\",ID[\"EPSG\",1181]],MEMBER[\"European Terrestrial Reference Frame 1993\",ID[\"EPSG\",1182]],MEMBER[\"European Terrestrial Reference Frame 1994\",ID[\"EPSG\",1183]],MEMBER[\"European Terrestrial Reference Frame 1996\",ID[\"EPSG\",1184]],MEMBER[\"European Terrestrial Reference Frame 1997\",ID[\"EPSG\",1185]],MEMBER[\"European Terrestrial Reference Frame 2000\",ID[\"EPSG\",1186]],MEMBER[\"European Terrestrial Reference Frame 2005\",ID[\"EPSG\",1204]],MEMBER[\"European Terrestrial Reference Frame 2014\",ID[\"EPSG\",1206]],ELLIPSOID[\"GRS 1980\",6378137,298.2572221,LENGTHUNIT[\"metre\",1,ID[\"EPSG\",9001]],ID[\"EPSG\",7019]],ENSEMBLEACCURACY[0.1],ID[\"EPSG\",6258]],CS[ellipsoidal,2,ID[\"EPSG\",6422]],AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],ID[\"EPSG\",4258]]],"
				+ "ABRIDGEDTRANSFORMATION[\"Amersfoort to ETRS89 (3)\","
				+ "METHOD[\"Coordinate Frame\",ID[\"EPSG\",1032]],"
				+ "PARAMETER[\"X-axis translation\",565.2369,ID[\"EPSG\",8605]],"
				+ "PARAMETER[\"Y-axis translation\",50.0087,ID[\"EPSG\",8606]],"
				+ "PARAMETER[\"Z-axis translation\",465.658,ID[\"EPSG\",8607]],"
				+ "PARAMETER[\"X-axis rotation\",0.407,ID[\"EPSG\",8608]],"
				+ "PARAMETER[\"Y-axis rotation\",-0.351,ID[\"EPSG\",8609]],"
				+ "PARAMETER[\"Z-axis rotation\",1.870,ID[\"EPSG\",8610]],"
				+ "PARAMETER[\"Scale difference\",1.000004812,ID[\"EPSG\",8611]]]]";

		crs = CRSReader.read(text, true);
		bound = CRSReader.readBound(text);
		assertEquals(crs, bound);
		assertEquals(CRSType.BOUND, bound.getType());
		// TODO

		text = text.replace("6378137", "6378137.0")
				.replaceAll("\",1,", "\",1.0,").replace("1.870", "1.87");
		assertEquals(text, bound.toString());
		assertEquals(text, CRSWriter.write(bound));
		assertEquals(WKTUtils.pretty(text), CRSWriter.writePretty(bound));

	}

	/**
	 * Test Backward Compatibility map projection
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testMapProjectionCompat() throws IOException {

		String text = "PROJECTION[\"Transverse Mercator\"],"
				+ "PARAMETER[\"Latitude of origin\",0],"
				+ "PARAMETER[\"Central meridian\",-123],"
				+ "PARAMETER[\"Scale factor\",0.9996],"
				+ "PARAMETER[\"False easting\",500000],"
				+ "PARAMETER[\"False northing\",0]";
		CRSReader reader = new CRSReader(text);
		MapProjection mapProjection = reader.readMapProjectionCompat();
		assertEquals("Transverse Mercator", mapProjection.getName());
		assertEquals("Transverse Mercator",
				mapProjection.getMethod().getName());
		assertEquals("Latitude of origin",
				mapProjection.getParameters().get(0).getName());
		assertEquals(0,
				mapProjection.getOperationParameters().get(0).getValue(), 0);
		assertEquals("Central meridian",
				mapProjection.getParameters().get(1).getName());
		assertEquals(-123,
				mapProjection.getOperationParameters().get(1).getValue(), 0);
		assertEquals("Scale factor",
				mapProjection.getParameters().get(2).getName());
		assertEquals(0.9996,
				mapProjection.getOperationParameters().get(2).getValue(), 0);
		assertEquals("False easting",
				mapProjection.getParameters().get(3).getName());
		assertEquals(500000,
				mapProjection.getOperationParameters().get(3).getValue(), 0);
		assertEquals("False northing",
				mapProjection.getParameters().get(4).getName());
		assertEquals(0,
				mapProjection.getOperationParameters().get(4).getValue(), 0);
		reader.close();

		String expectedText = "CONVERSION[\"Transverse Mercator\",METHOD[\"Transverse Mercator\"],"
				+ "PARAMETER[\"Latitude of origin\",0.0],"
				+ "PARAMETER[\"Central meridian\",-123.0],"
				+ "PARAMETER[\"Scale factor\",0.9996],"
				+ "PARAMETER[\"False easting\",500000.0],"
				+ "PARAMETER[\"False northing\",0.0]]";

		assertEquals(expectedText, mapProjection.toString());

		text = "PROJECTION[\"UTM zone 10N\"],"
				+ "PARAMETER[\"Latitude of natural origin\",0],"
				+ "PARAMETER[\"Longitude of natural origin\",-123],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.9996],"
				+ "PARAMETER[\"FE\",500000],PARAMETER[\"FN\",0]";
		reader = new CRSReader(text);
		mapProjection = reader.readMapProjectionCompat();
		assertEquals("UTM zone 10N", mapProjection.getName());
		assertEquals("UTM zone 10N", mapProjection.getMethod().getName());
		assertEquals("Latitude of natural origin",
				mapProjection.getParameters().get(0).getName());
		assertEquals(0,
				mapProjection.getOperationParameters().get(0).getValue(), 0);
		assertEquals("Longitude of natural origin",
				mapProjection.getParameters().get(1).getName());
		assertEquals(-123,
				mapProjection.getOperationParameters().get(1).getValue(), 0);
		assertEquals("Scale factor at natural origin",
				mapProjection.getParameters().get(2).getName());
		assertEquals(0.9996,
				mapProjection.getOperationParameters().get(2).getValue(), 0);
		assertEquals("FE", mapProjection.getParameters().get(3).getName());
		assertEquals(500000,
				mapProjection.getOperationParameters().get(3).getValue(), 0);
		assertEquals("FN", mapProjection.getParameters().get(4).getName());
		assertEquals(0,
				mapProjection.getOperationParameters().get(4).getValue(), 0);
		reader.close();

		expectedText = "CONVERSION[\"UTM zone 10N\",METHOD[\"UTM zone 10N\"],"
				+ "PARAMETER[\"Latitude of natural origin\",0.0],"
				+ "PARAMETER[\"Longitude of natural origin\",-123.0],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.9996],"
				+ "PARAMETER[\"FE\",500000.0],PARAMETER[\"FN\",0.0]]";

		assertEquals(expectedText, mapProjection.toString());

	}

	/**
	 * Test Backward Compatibility unit
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testUnitCompat() throws IOException {

		String text = "UNIT[\"German legal metre\",1.0000135965]";
		CRSReader reader = new CRSReader(text);
		Unit unit = reader.readUnit();
		assertEquals(UnitType.UNIT, unit.getType());
		assertEquals("German legal metre", unit.getName());
		assertEquals(1.0000135965, unit.getConversionFactor(), 0);
		reader.close();
		assertEquals(text, unit.toString());

	}

	/**
	 * Test Backward Compatibility axis
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testAxisCompat() throws IOException {

		String text = "AXIS[\"northing\",north]";
		CRSReader reader = new CRSReader(text);
		Axis axis = reader.readAxis();
		assertEquals("northing", axis.getName());
		assertEquals(AxisDirectionType.NORTH, axis.getDirection());
		reader.close();
		assertEquals(text, axis.toString());

		text = "AXIS[\"easting\",east]";
		reader = new CRSReader(text);
		axis = reader.readAxis();
		assertEquals("easting", axis.getName());
		assertEquals(AxisDirectionType.EAST, axis.getDirection());
		reader.close();
		assertEquals(text, axis.toString());

	}

	/**
	 * Test Backward Compatibility of Geographic CRS
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testGeographicCompat() throws IOException {

		String text = "GEOGCS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101]],"
				+ "PRIMEM[\"Greenwich\",0],"
				+ "UNIT[\"degree\",0.0174532925199433]]";

		CRS crs = CRSReader.read(text, true);
		GeoCoordinateReferenceSystem geodeticOrGeographicCrs = CRSReader
				.readGeoCompat(text);
		assertEquals(crs, geodeticOrGeographicCrs);
		GeoCoordinateReferenceSystem geographicCrs = CRSReader
				.readGeographicCompat(text);
		assertEquals(crs, geographicCrs);
		assertEquals(CRSType.GEOGRAPHIC, geographicCrs.getType());
		assertEquals("NAD83", geographicCrs.getName());
		assertEquals("North American Datum 1983",
				geographicCrs.getReferenceFrame().getName());
		assertEquals("GRS 1980",
				geographicCrs.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137.0, geographicCrs.getReferenceFrame().getEllipsoid()
				.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, geographicCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals("Greenwich",
				geographicCrs.getReferenceFrame().getPrimeMeridian().getName());
		assertEquals(0.0, geographicCrs.getReferenceFrame().getPrimeMeridian()
				.getIrmLongitude(), 0);
		assertEquals(CoordinateSystemType.ELLIPSOIDAL,
				geographicCrs.getCoordinateSystem().getType());
		assertEquals(2, geographicCrs.getCoordinateSystem().getDimension());
		assertEquals("Lon",
				geographicCrs.getCoordinateSystem().getAxes().get(0).getName());
		assertEquals(AxisDirectionType.EAST, geographicCrs.getCoordinateSystem()
				.getAxes().get(0).getDirection());
		assertEquals("Lat",
				geographicCrs.getCoordinateSystem().getAxes().get(1).getName());
		assertEquals(AxisDirectionType.NORTH, geographicCrs
				.getCoordinateSystem().getAxes().get(1).getDirection());
		assertEquals("degree",
				geographicCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(0.0174532925199433, geographicCrs.getCoordinateSystem()
				.getUnit().getConversionFactor(), 0);

		String expectedText = "GEOGCRS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101]],"
				+ "PRIMEM[\"Greenwich\",0.0],CS[ellipsoidal,2],"
				+ "AXIS[\"Lon\",east],AXIS[\"Lat\",north],UNIT[\"degree\",0.0174532925199433]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

		text = "GEOGCS[\"NAD83\",DATUM[\"North American Datum 1983\","
				+ "SPHEROID[\"GRS 1980\",6378137.0,298.257222101]],"
				+ "PRIMEM[\"Greenwich\",0],"
				+ "AXIS[\"latitude\",NORTH],AXIS[\"longitude\",EAST],"
				+ "UNIT[\"degree\",0.0174532925199433]]";

		crs = CRSReader.read(text, true);
		geodeticOrGeographicCrs = CRSReader.readGeoCompat(text);
		assertEquals(crs, geodeticOrGeographicCrs);
		geographicCrs = CRSReader.readGeographicCompat(text);
		assertEquals(crs, geographicCrs);
		assertEquals(CRSType.GEOGRAPHIC, geographicCrs.getType());
		assertEquals("NAD83", geographicCrs.getName());
		assertEquals("North American Datum 1983",
				geographicCrs.getReferenceFrame().getName());
		assertEquals("GRS 1980",
				geographicCrs.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137.0, geographicCrs.getReferenceFrame().getEllipsoid()
				.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, geographicCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals("Greenwich",
				geographicCrs.getReferenceFrame().getPrimeMeridian().getName());
		assertEquals(0.0, geographicCrs.getReferenceFrame().getPrimeMeridian()
				.getIrmLongitude(), 0);
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
		assertEquals("degree",
				geographicCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(0.0174532925199433, geographicCrs.getCoordinateSystem()
				.getUnit().getConversionFactor(), 0);

		expectedText = "GEOGCRS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101]],"
				+ "PRIMEM[\"Greenwich\",0.0],CS[ellipsoidal,2],"
				+ "AXIS[\"latitude\",north],AXIS[\"longitude\",east],"
				+ "UNIT[\"degree\",0.0174532925199433]]";

		assertEquals(expectedText, crs.toString());
		assertEquals(expectedText, CRSWriter.write(crs));
		assertEquals(WKTUtils.pretty(expectedText), CRSWriter.writePretty(crs));

	}

	/**
	 * Test Backward Compatibility of Projected CRS
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testProjectedCompat() throws IOException {

		String text = "PROJCS[\"NAD83 / UTM zone 10N\",GEOGCS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "SPHEROID[\"GRS 1980\",6378137.0,298.257222101]],"
				+ "PRIMEM[\"Greenwich\",0]],"
				+ "PROJECTION[\"Transverse Mercator\"],"
				+ "PARAMETER[\"Latitude of origin\",0.0],"
				+ "PARAMETER[\"Longitude of origin\",-123],"
				+ "PARAMETER[\"Scale factor\",0.9996],"
				+ "PARAMETER[\"False easting\",500000],"
				+ "PARAMETER[\"False northing\",0],UNIT[\"metre\",1.0]]";

		CRS crs = CRSReader.read(text, true);
		ProjectedCoordinateReferenceSystem projectedCrs = CRSReader
				.readProjectedCompat(text);
		assertEquals(crs, projectedCrs);
		assertEquals(CRSType.PROJECTED, projectedCrs.getType());
		assertEquals("NAD83 / UTM zone 10N", projectedCrs.getName());
		assertEquals(CRSType.GEOGRAPHIC, projectedCrs.getBaseType());
		assertEquals("NAD83", projectedCrs.getBaseName());
		assertEquals("North American Datum 1983",
				projectedCrs.getReferenceFrame().getName());
		assertEquals("GRS 1980",
				projectedCrs.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137.0, projectedCrs.getReferenceFrame().getEllipsoid()
				.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, projectedCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals("Greenwich",
				projectedCrs.getReferenceFrame().getPrimeMeridian().getName());
		assertEquals(0, projectedCrs.getReferenceFrame().getPrimeMeridian()
				.getIrmLongitude(), 0);
		assertEquals("Transverse Mercator",
				projectedCrs.getMapProjection().getName());
		assertEquals("Transverse Mercator",
				projectedCrs.getMapProjection().getMethod().getName());
		assertEquals("Latitude of origin", projectedCrs.getMapProjection()
				.getParameters().get(0).getName());
		assertEquals(0.0, projectedCrs.getMapProjection()
				.getOperationParameters().get(0).getValue(), 0);
		assertEquals("Longitude of origin", projectedCrs.getMapProjection()
				.getParameters().get(1).getName());
		assertEquals(-123, projectedCrs.getMapProjection()
				.getOperationParameters().get(1).getValue(), 0);
		assertEquals("Scale factor", projectedCrs.getMapProjection()
				.getParameters().get(2).getName());
		assertEquals(0.9996, projectedCrs.getMapProjection()
				.getOperationParameters().get(2).getValue(), 0);
		assertEquals("False easting", projectedCrs.getMapProjection()
				.getParameters().get(3).getName());
		assertEquals(500000, projectedCrs.getMapProjection()
				.getOperationParameters().get(3).getValue(), 0);
		assertEquals("False northing", projectedCrs.getMapProjection()
				.getParameters().get(4).getName());
		assertEquals(0, projectedCrs.getMapProjection().getOperationParameters()
				.get(4).getValue(), 0);
		assertEquals(UnitType.UNIT,
				projectedCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("metre",
				projectedCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1.0, projectedCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);

		String expectedText = "PROJCRS[\"NAD83 / UTM zone 10N\",BASEGEOGCRS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101]],"
				+ "PRIMEM[\"Greenwich\",0.0]],"
				+ "CONVERSION[\"Transverse Mercator\",METHOD[\"Transverse Mercator\"],"
				+ "PARAMETER[\"Latitude of origin\",0.0],"
				+ "PARAMETER[\"Longitude of origin\",-123.0],"
				+ "PARAMETER[\"Scale factor\",0.9996],"
				+ "PARAMETER[\"False easting\",500000.0],"
				+ "PARAMETER[\"False northing\",0.0]],"
				+ "CS[ellipsoidal,2],AXIS[\"X\",east],AXIS[\"Y\",north],"
				+ "UNIT[\"metre\",1.0]]";

		assertEquals(expectedText, projectedCrs.toString());
		assertEquals(expectedText, CRSWriter.write(projectedCrs));
		assertEquals(WKTUtils.pretty(expectedText),
				CRSWriter.writePretty(projectedCrs));

		text = "PROJCS[\"NAD83 / UTM zone 10N\",GEOGCS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101]],"
				+ "PRIMEM[\"Greenwich\",0],"
				+ "AXIS[\"latitude\",NORTH],AXIS[\"longitude\",EAST]],"
				+ "PROJECTION[\"UTM zone 10N\"],"
				+ "PARAMETER[\"Latitude of origin\",0.0],"
				+ "PARAMETER[\"Longitude of origin\",-123],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.9996],"
				+ "PARAMETER[\"FE\",500000],PARAMETER[\"FN\",0],"
				+ "AXIS[\"easting\",EAST],AXIS[\"northing\",NORTH],"
				+ "UNIT[\"metre\",1.0]]";

		crs = CRSReader.read(text, true);
		projectedCrs = CRSReader.readProjectedCompat(text);
		assertEquals(crs, projectedCrs);
		assertEquals(CRSType.PROJECTED, projectedCrs.getType());
		assertEquals("NAD83 / UTM zone 10N", projectedCrs.getName());
		assertEquals(CRSType.GEOGRAPHIC, projectedCrs.getBaseType());
		assertEquals("NAD83", projectedCrs.getBaseName());
		assertEquals("North American Datum 1983",
				projectedCrs.getReferenceFrame().getName());
		assertEquals("GRS 1980",
				projectedCrs.getReferenceFrame().getEllipsoid().getName());
		assertEquals(6378137.0, projectedCrs.getReferenceFrame().getEllipsoid()
				.getSemiMajorAxis(), 0);
		assertEquals(298.257222101, projectedCrs.getReferenceFrame()
				.getEllipsoid().getInverseFlattening(), 0);
		assertEquals("Greenwich",
				projectedCrs.getReferenceFrame().getPrimeMeridian().getName());
		assertEquals(0, projectedCrs.getReferenceFrame().getPrimeMeridian()
				.getIrmLongitude(), 0);
		assertEquals("UTM zone 10N", projectedCrs.getMapProjection().getName());
		assertEquals("UTM zone 10N",
				projectedCrs.getMapProjection().getMethod().getName());
		assertEquals("Latitude of origin", projectedCrs.getMapProjection()
				.getParameters().get(0).getName());
		assertEquals(0.0, projectedCrs.getMapProjection()
				.getOperationParameters().get(0).getValue(), 0);
		assertEquals("Longitude of origin", projectedCrs.getMapProjection()
				.getParameters().get(1).getName());
		assertEquals(-123, projectedCrs.getMapProjection()
				.getOperationParameters().get(1).getValue(), 0);
		assertEquals("Scale factor at natural origin", projectedCrs
				.getMapProjection().getParameters().get(2).getName());
		assertEquals(0.9996, projectedCrs.getMapProjection()
				.getOperationParameters().get(2).getValue(), 0);
		assertEquals("FE", projectedCrs.getMapProjection().getParameters()
				.get(3).getName());
		assertEquals(500000, projectedCrs.getMapProjection()
				.getOperationParameters().get(3).getValue(), 0);
		assertEquals("FN", projectedCrs.getMapProjection().getParameters()
				.get(4).getName());
		assertEquals(0, projectedCrs.getMapProjection().getOperationParameters()
				.get(4).getValue(), 0);
		assertEquals(UnitType.UNIT,
				projectedCrs.getCoordinateSystem().getUnit().getType());
		assertEquals("metre",
				projectedCrs.getCoordinateSystem().getUnit().getName());
		assertEquals(1.0, projectedCrs.getCoordinateSystem().getUnit()
				.getConversionFactor(), 0);

		expectedText = "PROJCRS[\"NAD83 / UTM zone 10N\",BASEGEOGCRS[\"NAD83\","
				+ "DATUM[\"North American Datum 1983\","
				+ "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101]],"
				+ "PRIMEM[\"Greenwich\",0.0]],"
				+ "CONVERSION[\"UTM zone 10N\",METHOD[\"UTM zone 10N\"],"
				+ "PARAMETER[\"Latitude of origin\",0.0],"
				+ "PARAMETER[\"Longitude of origin\",-123.0],"
				+ "PARAMETER[\"Scale factor at natural origin\",0.9996],"
				+ "PARAMETER[\"FE\",500000.0],PARAMETER[\"FN\",0.0]],"
				+ "CS[ellipsoidal,2],"
				+ "AXIS[\"easting\",east],AXIS[\"northing\",north],"
				+ "UNIT[\"metre\",1.0]]";

		assertEquals(expectedText, projectedCrs.toString());
		assertEquals(expectedText, CRSWriter.write(projectedCrs));
		assertEquals(WKTUtils.pretty(expectedText),
				CRSWriter.writePretty(projectedCrs));

	}

	/**
	 * Test triaxial ellipsoid
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testTriaxialEllipsoid() throws IOException {

		String text = "TRIAXIAL[\"Io 2009 IAU IAG\",1829400,1819400,1815700,"
				+ "LENGTHUNIT[\"metre\",1]]";
		CRSReader reader = new CRSReader(text);
		TriaxialEllipsoid ellipsoid = (TriaxialEllipsoid) reader
				.readEllipsoid();
		assertEquals("Io 2009 IAU IAG", ellipsoid.getName());
		assertEquals(1829400, ellipsoid.getSemiMajorAxis(), 0);
		assertEquals(1819400, ellipsoid.getSemiMedianAxis(), 0);
		assertEquals(1815700, ellipsoid.getSemiMinorAxis(), 0);
		assertEquals(UnitType.LENGTHUNIT, ellipsoid.getUnit().getType());
		assertEquals("metre", ellipsoid.getUnit().getName());
		assertEquals(1.0, ellipsoid.getUnit().getConversionFactor(), 0);
		reader.close();
		text = text.replaceAll("1829400", "1829400.0")
				.replaceAll("1819400", "1819400.0")
				.replaceAll("1815700", "1815700.0").replace("\",1]", "\",1.0]");
		assertEquals(text, ellipsoid.toString());

	}

}
