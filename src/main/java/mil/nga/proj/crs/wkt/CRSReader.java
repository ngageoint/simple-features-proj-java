package mil.nga.proj.crs.wkt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mil.nga.proj.ProjectionException;
import mil.nga.proj.crs.Axis;
import mil.nga.proj.crs.AxisDirectionType;
import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateSystem;
import mil.nga.proj.crs.CoordinateSystemType;
import mil.nga.proj.crs.DatumEnsemble;
import mil.nga.proj.crs.DatumEnsembleMember;
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
 * Well Known Text reader
 * 
 * @author osbornb
 */
public class CRSReader {

	/**
	 * Read a Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static CoordinateReferenceSystem readCRS(String text)
			throws IOException {
		CoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.read();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read a Geodetic or Geographic Coordinate Reference System from the
	 * well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Geodetic or Geographic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static GeodeticCoordinateReferenceSystem readGeodeticOrGeographic(
			String text) throws IOException {
		GeodeticCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readGeodeticOrGeographic();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Text Reader
	 */
	private TextReader reader;

	/**
	 * Constructor
	 * 
	 * @param text
	 *            well-known text
	 */
	public CRSReader(String text) {
		this(new TextReader(text));
	}

	/**
	 * Constructor
	 * 
	 * @param reader
	 *            text reader
	 */
	public CRSReader(TextReader reader) {
		this.reader = reader;
	}

	/**
	 * Get the text reader
	 * 
	 * @return text reader
	 */
	public TextReader getTextReader() {
		return reader;
	}

	/**
	 * Close the text reader
	 */
	public void close() {
		reader.close();
	}

	/**
	 * Read a Coordinate Reference System from the well-known text
	 * 
	 * @return Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem read() throws IOException {

		CoordinateReferenceSystem crs = null;

		CoordinateReferenceSystemKeyword keyword = peekKeyword();
		switch (keyword) {
		case GEODCRS:
		case GEOGCRS:
			crs = readGeodeticOrGeographic();
			break;
		default:
			throw new ProjectionException(
					"Unsupported WKT CRS keyword: " + keyword);
		}

		return crs;
	}

	/**
	 * Read a WKT CRS keyword
	 * 
	 * @return keyword
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystemKeyword readKeyword() throws IOException {
		return CoordinateReferenceSystemKeyword
				.getRequiredType(reader.readToken());
	}

	/**
	 * Read WKT CRS keywords
	 * 
	 * @return keywords
	 * @throws IOException
	 *             upon failure to read
	 */
	public Set<CoordinateReferenceSystemKeyword> readKeywords()
			throws IOException {
		return CoordinateReferenceSystemKeyword
				.getRequiredTypes(reader.readToken());
	}

	/**
	 * Peek a WKT CRS keyword
	 * 
	 * @return keyword
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystemKeyword peekKeyword() throws IOException {
		return CoordinateReferenceSystemKeyword
				.getRequiredType(reader.peekToken());
	}

	/**
	 * Peek WKT CRS keywords
	 * 
	 * @return keywords
	 * @throws IOException
	 *             upon failure to read
	 */
	public Set<CoordinateReferenceSystemKeyword> peekKeywords()
			throws IOException {
		return CoordinateReferenceSystemKeyword
				.getRequiredTypes(reader.peekToken());
	}

	/**
	 * Peek an optional WKT CRS keyword
	 * 
	 * @return keyword
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystemKeyword peekOptionalKeyword()
			throws IOException {
		return CoordinateReferenceSystemKeyword.getType(reader.peekToken());
	}

	/**
	 * Peek an optional WKT CRS keywords
	 * 
	 * @return keywords
	 * @throws IOException
	 *             upon failure to read
	 */
	public Set<CoordinateReferenceSystemKeyword> peekOptionalKeywords()
			throws IOException {
		return CoordinateReferenceSystemKeyword.getTypes(reader.peekToken());
	}

	/**
	 * Peek an optional WKT CRS keyword
	 * 
	 * @param num
	 *            number of tokens out to peek at
	 * @return keyword
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystemKeyword peekOptionalKeyword(int num)
			throws IOException {
		return CoordinateReferenceSystemKeyword.getType(reader.peekToken(num));
	}

	/**
	 * Peek an optional WKT CRS keywords
	 * 
	 * @param num
	 *            number of tokens out to peek at
	 * @return keywords
	 * @throws IOException
	 *             upon failure to read
	 */
	public Set<CoordinateReferenceSystemKeyword> peekOptionalKeywords(int num)
			throws IOException {
		return CoordinateReferenceSystemKeyword.getTypes(reader.peekToken(num));
	}

	/**
	 * Read a left delimiter
	 * 
	 * @throws IOException
	 *             upon failure to read
	 */
	public void readLeftDelimiter() throws IOException {
		String token = reader.readExpectedToken();
		if (!token.equals("[") && !token.equals("(")) {
			throw new ProjectionException(
					"Invalid left delimiter token, expected '[' or '('. found: '"
							+ token + "'");
		}
	}

	/**
	 * Read a right delimiter
	 * 
	 * @throws IOException
	 *             upon failure to read
	 */
	public void readRightDelimiter() throws IOException {
		String token = reader.readExpectedToken();
		if (!token.equals("]") && !token.equals(")")) {
			throw new ProjectionException(
					"Invalid right delimiter token, expected ']' or ')'. found: '"
							+ token + "'");
		}
	}

	/**
	 * Read a WKT Separator (comma)
	 * 
	 * @throws IOException
	 *             upon failure to read
	 */
	public void readSeparator() throws IOException {
		String token = reader.readExpectedToken();
		if (!token.equals(",")) {
			throw new ProjectionException(
					"Invalid separator token, expected ','. found: '" + token
							+ "'");
		}
	}

	/**
	 * Peek if the next token is a WKT Separator (comma)
	 * 
	 * @return true if next token is a separator
	 * @throws IOException
	 *             upon failure to read
	 */
	public boolean peekSeparator() throws IOException {
		boolean separator = false;
		String token = reader.peekToken();
		if (token != null) {
			separator = token.equals(",");
		}
		return separator;
	}

	/**
	 * Read a keyword delimited token
	 * 
	 * @param keyword
	 *            expected keyword
	 * @return token
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readKeywordDelimitedToken(
			CoordinateReferenceSystemKeyword keyword) throws IOException {

		validateKeyword(readKeyword(), keyword);

		readLeftDelimiter();

		String token = reader.readExpectedToken();

		readRightDelimiter();

		return token;
	}

	/**
	 * Validate the keyword against the expected
	 * 
	 * @param keyword
	 *            keyword
	 * @param expected
	 *            expected keyword
	 */
	private void validateKeyword(CoordinateReferenceSystemKeyword keyword,
			CoordinateReferenceSystemKeyword expected) {
		if (keyword != expected) {
			throw new ProjectionException("Unexpected keyword. found: "
					+ keyword + ", expected: " + expected);
		}
	}

	/**
	 * Validate the keyword against the expected keywords
	 * 
	 * @param keyword
	 *            keyword
	 * @param expected
	 *            expected keywords
	 */
	private void validateKeyword(CoordinateReferenceSystemKeyword keyword,
			CoordinateReferenceSystemKeyword... expected) {
		Set<CoordinateReferenceSystemKeyword> expectedSet = new HashSet<>(
				Arrays.asList(expected));
		if (!expectedSet.contains(keyword)) {
			throw new ProjectionException("Unexpected keyword. found: "
					+ keyword + ", expected: " + expectedSet);
		}
	}

	/**
	 * Validate the keywords against the expected keyword
	 * 
	 * @param keywords
	 *            keywords
	 * @param expected
	 *            expected keyword
	 */
	private void validateKeywords(
			Set<CoordinateReferenceSystemKeyword> keywords,
			CoordinateReferenceSystemKeyword expected) {
		if (!keywords.contains(expected)) {
			throw new ProjectionException("Unexpected keyword. found: "
					+ keywords + ", expected: " + expected);
		}
	}

	/**
	 * Check if the keyword is next following an immediate next separator
	 * 
	 * @param keywords
	 *            keywords
	 * @return true if next
	 * @throws IOException
	 *             upon failure to read
	 */
	private boolean isKeywordNext(CoordinateReferenceSystemKeyword... keywords)
			throws IOException {
		boolean next = false;
		if (peekSeparator()) {
			Set<CoordinateReferenceSystemKeyword> nextKeywords = peekOptionalKeywords(
					2);
			if (nextKeywords != null && !nextKeywords.isEmpty()) {
				for (CoordinateReferenceSystemKeyword keyword : keywords) {
					next = nextKeywords.contains(keyword);
					if (next) {
						break;
					}
				}
			}
		}
		return next;
	}

	/**
	 * Check if the keyword is next following an immediate next separator
	 * 
	 * @param keyword
	 *            keyword
	 * @return true if next
	 * @throws IOException
	 *             upon failure to read
	 */
	private boolean isNonKeywordNext() throws IOException {
		boolean next = false;
		if (peekSeparator()) {
			Set<CoordinateReferenceSystemKeyword> nextKeywords = peekOptionalKeywords(
					2);
			next = nextKeywords == null || nextKeywords.isEmpty();
		}
		return next;
	}

	/**
	 * Is a unit next following an immediate next separator
	 * 
	 * @return true if next
	 * @throws IOException
	 *             upon failure to read
	 */
	private boolean isUnitNext() throws IOException {
		return isKeywordNext(CoordinateReferenceSystemKeyword.ANGLEUNIT,
				CoordinateReferenceSystemKeyword.LENGTHUNIT,
				CoordinateReferenceSystemKeyword.PARAMETRICUNIT,
				CoordinateReferenceSystemKeyword.SCALEUNIT,
				CoordinateReferenceSystemKeyword.TIMEUNIT);
	}

	/**
	 * Is a spatial unit next following an immediate next separator
	 * 
	 * @return true if next
	 * @throws IOException
	 *             upon failure to read
	 */
	private boolean isSpatialUnitNext() throws IOException {
		return isKeywordNext(CoordinateReferenceSystemKeyword.ANGLEUNIT,
				CoordinateReferenceSystemKeyword.LENGTHUNIT,
				CoordinateReferenceSystemKeyword.PARAMETRICUNIT,
				CoordinateReferenceSystemKeyword.SCALEUNIT);
	}

	/**
	 * Is a time unit next following an immediate next separator
	 * 
	 * @return true if next
	 * @throws IOException
	 *             upon failure to read
	 */
	private boolean isTimeUnitNext() throws IOException {
		return isKeywordNext(CoordinateReferenceSystemKeyword.TIMEUNIT);
	}

	/**
	 * Read a Geodetic or Geographic CRS
	 * 
	 * @return crs
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeodeticCoordinateReferenceSystem readGeodeticOrGeographic()
			throws IOException {

		GeodeticCoordinateReferenceSystem crs = new GeodeticCoordinateReferenceSystem();

		CoordinateReferenceSystemKeyword type = readKeyword();
		validateKeyword(type, CoordinateReferenceSystemKeyword.GEODCRS,
				CoordinateReferenceSystemKeyword.GEOGCRS);
		crs.setGeographic(type == CoordinateReferenceSystemKeyword.GEOGCRS);

		readLeftDelimiter();

		crs.setName(reader.readExpectedToken());

		boolean isDynamic = isKeywordNext(
				CoordinateReferenceSystemKeyword.DYNAMIC);
		if (isDynamic) {
			readSeparator();
			crs.setDynamic(readDynamic());
		}

		if (isDynamic
				|| isKeywordNext(CoordinateReferenceSystemKeyword.DATUM)) {
			readSeparator();
			crs.setGeodeticReferenceFrame(readGeodeticReferenceFrame());
		} else if (isKeywordNext(CoordinateReferenceSystemKeyword.ENSEMBLE)) {
			readSeparator();
			crs.setGeodeticDatumEnsemble(readGeodeticDatumEnsemble());
		} else {
			// Validation error
			readSeparator();
			validateKeyword(type, CoordinateReferenceSystemKeyword.DATUM,
					CoordinateReferenceSystemKeyword.ENSEMBLE);
		}

		readSeparator();

		crs.setCoordinateSystem(readCoordinateSystem());

		if (isKeywordNext(CoordinateReferenceSystemKeyword.USAGE)) {
			readSeparator();
			crs.setUsages(readUsages());
		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {
			readSeparator();
			crs.setIdentifiers(readIdentifiers());
		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.REMARK)) {
			readSeparator();
			crs.setRemark(readRemark());
		}

		readRightDelimiter();

		return crs;
	}

	/**
	 * Read a Geodetic reference frame (datum)
	 * 
	 * @return geodetic reference frame
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeodeticReferenceFrame readGeodeticReferenceFrame()
			throws IOException {

		GeodeticReferenceFrame geodeticReferenceFrame = new GeodeticReferenceFrame();

		validateKeyword(readKeyword(), CoordinateReferenceSystemKeyword.DATUM);

		readLeftDelimiter();

		geodeticReferenceFrame.setName(reader.readExpectedToken());

		readSeparator();

		geodeticReferenceFrame.setEllipsoid(readEllipsoid());

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ANCHOR)) {
			readSeparator();
			geodeticReferenceFrame.setAnchor(readKeywordDelimitedToken(
					CoordinateReferenceSystemKeyword.ANCHOR));
		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {
			readSeparator();
			geodeticReferenceFrame.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		if (isKeywordNext(CoordinateReferenceSystemKeyword.PRIMEM)) {
			readSeparator();
			geodeticReferenceFrame.setPrimeMeridian(readPrimeMeridian());
		}

		return geodeticReferenceFrame;
	}

	/**
	 * Read a Geodetic datum ensemble
	 * 
	 * @return geodetic datum ensemble
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeodeticDatumEnsemble readGeodeticDatumEnsemble()
			throws IOException {
		DatumEnsemble datumEnsemble = readDatumEnsemble();
		if (!(datumEnsemble instanceof GeodeticDatumEnsemble)) {
			throw new ProjectionException(
					"Datum ensemble was not an expected Geodetic Datum Ensemble");
		}
		return (GeodeticDatumEnsemble) datumEnsemble;
	}

	/**
	 * Read a Vertical datum ensemble
	 * 
	 * @return vertical datum ensemble
	 * @throws IOException
	 *             upon failure to read
	 */
	public DatumEnsemble readVerticalDatumEnsemble() throws IOException {
		DatumEnsemble datumEnsemble = readDatumEnsemble();
		if (!datumEnsemble.getClass().equals(DatumEnsemble.class)) {
			throw new ProjectionException(
					"Datum ensemble was not an expected Vertical Datum Ensemble");
		}
		return datumEnsemble;
	}

	/**
	 * Read a Datum ensemble
	 * 
	 * @return datum ensemble
	 * @throws IOException
	 *             upon failure to read
	 */
	public DatumEnsemble readDatumEnsemble() throws IOException {

		validateKeyword(readKeyword(),
				CoordinateReferenceSystemKeyword.ENSEMBLE);

		readLeftDelimiter();

		String name = reader.readExpectedToken();

		List<DatumEnsembleMember> members = new ArrayList<>();
		do {

			readSeparator();

			members.add(readDatumEnsembleMember());

		} while (isKeywordNext(CoordinateReferenceSystemKeyword.MEMBER));

		DatumEnsemble datumEnsemble = null;
		GeodeticDatumEnsemble geodeticDatumEnsemble = null;

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ELLIPSOID)) {
			geodeticDatumEnsemble = new GeodeticDatumEnsemble();
			datumEnsemble = geodeticDatumEnsemble;
		} else {
			datumEnsemble = new DatumEnsemble();
		}

		datumEnsemble.setName(name);
		datumEnsemble.setMembers(members);

		if (geodeticDatumEnsemble != null) {
			readSeparator();
			geodeticDatumEnsemble.setEllipsoid(readEllipsoid());
		}

		readSeparator();
		validateKeyword(readKeyword(),
				CoordinateReferenceSystemKeyword.ENSEMBLEACCURACY);

		readLeftDelimiter();

		datumEnsemble.setAccuracy(reader.readNumber());

		readRightDelimiter();

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {
			readSeparator();
			datumEnsemble.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		if (geodeticDatumEnsemble != null
				&& isKeywordNext(CoordinateReferenceSystemKeyword.PRIMEM)) {
			// TODO http://ogc.standardstracker.org/show_request.cgi?id=672
			readSeparator();
			geodeticDatumEnsemble.setPrimeMeridian(readPrimeMeridian());
		}

		return datumEnsemble;
	}

	/**
	 * Read a Datum ensemble member
	 * 
	 * @return datum ensemble member
	 * @throws IOException
	 *             upon failure to read
	 */
	public DatumEnsembleMember readDatumEnsembleMember() throws IOException {

		DatumEnsembleMember member = new DatumEnsembleMember();

		validateKeyword(readKeyword(), CoordinateReferenceSystemKeyword.MEMBER);

		readLeftDelimiter();

		member.setName(reader.readExpectedToken());

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {
			readSeparator();
			member.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return member;
	}

	/**
	 * Read a Dynamic coordinate reference system
	 * 
	 * @return dynamic
	 * @throws IOException
	 *             upon failure to read
	 */
	public Dynamic readDynamic() throws IOException {

		Dynamic dynamic = new Dynamic();

		validateKeyword(readKeyword(),
				CoordinateReferenceSystemKeyword.DYNAMIC);

		readLeftDelimiter();

		validateKeyword(readKeyword(),
				CoordinateReferenceSystemKeyword.FRAMEEPOCH);

		readLeftDelimiter();

		dynamic.setReferenceEpoch(reader.readUnsignedNumber());

		readRightDelimiter();

		if (isKeywordNext(CoordinateReferenceSystemKeyword.MODEL)) {

			readSeparator();
			readKeyword();

			readLeftDelimiter();

			dynamic.setDeformationModelName(reader.readExpectedToken());

			if (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {
				readSeparator();
				dynamic.setDeformationModelIdentifiers(readIdentifiers());
			}

			readRightDelimiter();
		}

		readRightDelimiter();

		return dynamic;
	}

	/**
	 * Read a Prime meridian
	 * 
	 * @return prime meridian
	 * @throws IOException
	 *             upon failure to read
	 */
	public PrimeMeridian readPrimeMeridian() throws IOException {

		PrimeMeridian primeMeridian = new PrimeMeridian();

		validateKeyword(readKeyword(), CoordinateReferenceSystemKeyword.PRIMEM);

		readLeftDelimiter();

		primeMeridian.setName(reader.readExpectedToken());

		readSeparator();

		primeMeridian.setIrmLongitude(reader.readNumber());

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ANGLEUNIT)) {
			readSeparator();
			primeMeridian.setIrmLongitudeAngleUnit(readAngleUnit());
		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {
			readSeparator();
			primeMeridian.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return primeMeridian;
	}

	/**
	 * Read an Ellipsoid
	 * 
	 * @return ellipsoid
	 * @throws IOException
	 *             upon failure to read
	 */
	public Ellipsoid readEllipsoid() throws IOException {

		Ellipsoid ellipsoid = new Ellipsoid();

		validateKeyword(readKeyword(),
				CoordinateReferenceSystemKeyword.ELLIPSOID);

		readLeftDelimiter();

		ellipsoid.setName(reader.readExpectedToken());

		readSeparator();

		ellipsoid.setSemiMajorAxis(reader.readUnsignedNumber());

		readSeparator();

		ellipsoid.setInverseFlattening(reader.readUnsignedNumber());

		if (isKeywordNext(CoordinateReferenceSystemKeyword.LENGTHUNIT)) {
			readSeparator();
			ellipsoid.setLengthUnit(readLengthUnit());
		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {
			readSeparator();
			ellipsoid.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return ellipsoid;
	}

	/**
	 * Read a Unit
	 * 
	 * @return unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readUnit() throws IOException {
		return readUnit(UnitType.UNIT);
	}

	/**
	 * Read an Angle Unit
	 * 
	 * @return angle unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readAngleUnit() throws IOException {
		return readUnit(UnitType.ANGLEUNIT);
	}

	/**
	 * Read a Length Unit
	 * 
	 * @return length unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readLengthUnit() throws IOException {
		return readUnit(UnitType.LENGTHUNIT);
	}

	/**
	 * Read a Parametric Unit
	 * 
	 * @return parametric unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readParametricUnit() throws IOException {
		return readUnit(UnitType.PARAMETRICUNIT);
	}

	/**
	 * Read a Scale Unit
	 * 
	 * @return scale unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readScaleUnit() throws IOException {
		return readUnit(UnitType.SCALEUNIT);
	}

	/**
	 * Read a Time Unit
	 * 
	 * @return time unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readTimeUnit() throws IOException {
		return readUnit(UnitType.TIMEUNIT);
	}

	/**
	 * Read a Unit
	 * 
	 * @param type
	 *            expected unit type
	 * @return unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readUnit(UnitType type) throws IOException {

		Unit unit = new Unit();

		Set<CoordinateReferenceSystemKeyword> keywords = readKeywords();
		if (type != UnitType.UNIT) {
			CoordinateReferenceSystemKeyword crsType = CoordinateReferenceSystemKeyword
					.getType(type.name());
			validateKeywords(keywords, crsType);
		} else if (keywords.size() == 1) {
			type = CRSUtils.getUnitType(keywords.iterator().next());
		} else if (keywords.isEmpty()) {
			throw new ProjectionException(
					"Unexpected unit keyword. found: " + keywords);
		}
		unit.setType(type);

		readLeftDelimiter();

		unit.setName(reader.readExpectedToken());

		if (type != UnitType.TIMEUNIT
				|| (peekSeparator() && isNonKeywordNext())) {

			readSeparator();

			unit.setConversionFactor(reader.readUnsignedNumber());

		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {
			readSeparator();
			unit.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return unit;
	}

	/**
	 * Read Identifiers
	 * 
	 * @return identifiers
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<Identifier> readIdentifiers() throws IOException {

		List<Identifier> identifiers = new ArrayList<>();

		do {

			if (!identifiers.isEmpty()) {
				readSeparator();
			}

			identifiers.add(readIdentifier());

		} while (isKeywordNext(CoordinateReferenceSystemKeyword.ID));

		return identifiers;
	}

	/**
	 * Read an Identifier
	 * 
	 * @return identifier
	 * @throws IOException
	 *             upon failure to read
	 */
	public Identifier readIdentifier() throws IOException {

		Identifier identifier = new Identifier();

		validateKeyword(readKeyword(), CoordinateReferenceSystemKeyword.ID);

		readLeftDelimiter();

		identifier.setName(reader.readExpectedToken());

		readSeparator();

		identifier.setUniqueIdentifier(reader.readExpectedToken());

		if (isNonKeywordNext()) {
			readSeparator();
			identifier.setVersion(reader.readExpectedToken());
		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.CITATION)) {
			readSeparator();
			identifier.setCitation(readKeywordDelimitedToken(
					CoordinateReferenceSystemKeyword.CITATION));
		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.URI)) {
			readSeparator();
			identifier.setUri(readKeywordDelimitedToken(
					CoordinateReferenceSystemKeyword.URI));
		}

		readRightDelimiter();

		return identifier;
	}

	/**
	 * Read a Coordinate system
	 * 
	 * @return coordinate system
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateSystem readCoordinateSystem() throws IOException {

		CoordinateSystem coordinateSystem = new CoordinateSystem();

		validateKeyword(readKeyword(), CoordinateReferenceSystemKeyword.CS);

		readLeftDelimiter();

		String csTypeName = reader.readToken();
		CoordinateSystemType csType = CoordinateSystemType.getType(csTypeName);
		if (csType == null) {
			throw new ProjectionException(
					"Unexpected coordinate system type. found: " + csTypeName);
		}
		coordinateSystem.setType(csType);

		readSeparator();

		coordinateSystem.setDimension(reader.readUnsignedInteger());

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {
			readSeparator();
			coordinateSystem.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		boolean isTemporalCountMeasure = CRSUtils
				.isTemporalCountMeasure(csType);
		List<Axis> axes = new ArrayList<>();

		do {

			readSeparator();

			axes.add(readAxis(csType));

			if (isTemporalCountMeasure) {
				break;
			}

		} while (isKeywordNext(CoordinateReferenceSystemKeyword.AXIS));

		coordinateSystem.setAxes(axes);

		if (CRSUtils.isSpatial(csType)) {

			if (isUnitNext()) {

				readSeparator();

				coordinateSystem.setUnit(readUnit());

			}

		}

		return coordinateSystem;
	}

	/**
	 * Read an Axis
	 * 
	 * @param type
	 *            coordinate system type
	 * @return axis
	 * @throws IOException
	 *             upon failure to read
	 */
	public Axis readAxis(CoordinateSystemType type) throws IOException {

		Axis axis = new Axis();

		validateKeyword(readKeyword(), CoordinateReferenceSystemKeyword.AXIS);

		readLeftDelimiter();

		String nameAbbrev = reader.readExpectedToken();
		if (nameAbbrev.matches("((.+ )|^)\\([a-zA-Z]+\\)$")) {
			int abbrevIndex = nameAbbrev.lastIndexOf("(");
			axis.setAbbreviation(nameAbbrev.substring(abbrevIndex + 1,
					nameAbbrev.length() - 1));
			if (abbrevIndex > 0) {
				axis.setName(nameAbbrev.substring(0, abbrevIndex - 1));
			}
		} else {
			axis.setName(nameAbbrev);
		}

		readSeparator();

		String axisDirectionTypeName = reader.readToken();
		AxisDirectionType axisDirectionType = AxisDirectionType
				.getType(axisDirectionTypeName);
		if (axisDirectionType == null) {
			throw new ProjectionException(
					"Unexpected axis direction type. found: "
							+ axisDirectionTypeName);
		}
		axis.setDirection(axisDirectionType);

		switch (axisDirectionType) {

		case NORTH:
		case SOUTH:

			if (isKeywordNext(CoordinateReferenceSystemKeyword.MERIDIAN)) {

				readSeparator();
				readKeyword();

				readLeftDelimiter();

				axis.setMeridian(reader.readNumber());

				readSeparator();

				axis.setMeridianAngleUnit(readAngleUnit());

				readRightDelimiter();

			}

			break;

		case CLOCKWISE:
		case COUNTER_CLOCKWISE:

			readSeparator();

			validateKeyword(readKeyword(),
					CoordinateReferenceSystemKeyword.BEARING);

			readLeftDelimiter();

			axis.setBearing(reader.readNumber());

			readRightDelimiter();

			break;

		default:
		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ORDER)) {

			readSeparator();
			readKeyword();

			readLeftDelimiter();

			axis.setOrder(reader.readUnsignedInteger());

			readRightDelimiter();

		}

		if (CRSUtils.isSpatial(type)) {

			if (isSpatialUnitNext()) {

				readSeparator();

				axis.setUnit(readUnit());

			}

		} else if (CRSUtils.isTemporalCountMeasure(type)) {

			if (isTimeUnitNext()) {

				readSeparator();

				axis.setUnit(readTimeUnit());

			}

		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {
			readSeparator();
			axis.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return axis;
	}

	/**
	 * Read Remark
	 * 
	 * @return remark
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readRemark() throws IOException {
		return readKeywordDelimitedToken(
				CoordinateReferenceSystemKeyword.REMARK);
	}

	/**
	 * Read Usages
	 * 
	 * @return usages
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<Usage> readUsages() throws IOException {

		List<Usage> usages = new ArrayList<>();

		do {

			if (!usages.isEmpty()) {
				readSeparator();
			}

			usages.add(readUsage());

		} while (isKeywordNext(CoordinateReferenceSystemKeyword.USAGE));

		return usages;
	}

	/**
	 * Read a Usage
	 * 
	 * @return usage
	 * @throws IOException
	 *             upon failure to read
	 */
	public Usage readUsage() throws IOException {

		Usage usage = new Usage();

		validateKeyword(readKeyword(), CoordinateReferenceSystemKeyword.USAGE);

		readLeftDelimiter();

		usage.setScope(readScope());

		usage.setExtent(readExtent());

		readRightDelimiter();

		return usage;
	}

	/**
	 * Read a Scope
	 * 
	 * @return scope
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readScope() throws IOException {
		return readKeywordDelimitedToken(
				CoordinateReferenceSystemKeyword.SCOPE);
	}

	/**
	 * Read an Extent
	 * 
	 * @return extent
	 * @throws IOException
	 *             upon failure to read
	 */
	public Extent readExtent() throws IOException {

		Extent extent = new Extent();

		boolean valid = false;

		if (isKeywordNext(CoordinateReferenceSystemKeyword.AREA)) {

			readSeparator();

			extent.setAreaDescription(readAreaDescription());
			valid = true;

		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.BBOX)) {

			readSeparator();

			extent.setGeographicBoundingBox(readGeographicBoundingBox());
			valid = true;

		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.VERTICALEXTENT)) {

			readSeparator();

			extent.setVerticalExtent(readVerticalExtent());
			valid = true;

		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.TIMEEXTENT)) {

			readSeparator();

			extent.setTemporalExtent(readTemporalExtent());
			valid = true;

		}

		if (!valid) {
			throw new ProjectionException("Missing extent type of ["
					+ CoordinateReferenceSystemKeyword.AREA + ", "
					+ CoordinateReferenceSystemKeyword.BBOX + ", "
					+ CoordinateReferenceSystemKeyword.VERTICALEXTENT + ", "
					+ CoordinateReferenceSystemKeyword.TIMEEXTENT + "]");
		}

		return extent;
	}

	/**
	 * Read an Area description
	 * 
	 * @return area description
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readAreaDescription() throws IOException {
		return readKeywordDelimitedToken(CoordinateReferenceSystemKeyword.AREA);
	}

	/**
	 * Read a Geographic bounding box
	 * 
	 * @return geographic bounding box
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeographicBoundingBox readGeographicBoundingBox()
			throws IOException {

		GeographicBoundingBox boundingBox = new GeographicBoundingBox();

		validateKeyword(readKeyword(), CoordinateReferenceSystemKeyword.BBOX);

		readLeftDelimiter();

		boundingBox.setLowerLeftLatitude(reader.readNumber());

		readSeparator();

		boundingBox.setLowerLeftLongitude(reader.readNumber());

		readSeparator();

		boundingBox.setUpperRightLatitude(reader.readNumber());

		readSeparator();

		boundingBox.setUpperRightLongitude(reader.readNumber());

		readRightDelimiter();

		return boundingBox;
	}

	/**
	 * Read a Vertical extent
	 * 
	 * @return vertical extent
	 * @throws IOException
	 *             upon failure to read
	 */
	public VerticalExtent readVerticalExtent() throws IOException {

		VerticalExtent verticalExtent = new VerticalExtent();

		validateKeyword(readKeyword(),
				CoordinateReferenceSystemKeyword.VERTICALEXTENT);

		readLeftDelimiter();

		verticalExtent.setMinimumHeight(reader.readNumber());

		readSeparator();

		verticalExtent.setMaximumHeight(reader.readNumber());

		if (isKeywordNext(CoordinateReferenceSystemKeyword.LENGTHUNIT)) {
			readSeparator();
			verticalExtent.setLengthUnit(readLengthUnit());
		}

		readRightDelimiter();

		return verticalExtent;
	}

	/**
	 * Read a Temporal extent
	 * 
	 * @return temporal extent
	 * @throws IOException
	 *             upon failure to read
	 */
	public TemporalExtent readTemporalExtent() throws IOException {

		TemporalExtent temporalExtent = new TemporalExtent();

		validateKeyword(readKeyword(),
				CoordinateReferenceSystemKeyword.TIMEEXTENT);

		readLeftDelimiter();

		temporalExtent.setStart(reader.readExpectedToken());

		readSeparator();

		temporalExtent.setEnd(reader.readExpectedToken());

		readRightDelimiter();

		return temporalExtent;
	}

}
