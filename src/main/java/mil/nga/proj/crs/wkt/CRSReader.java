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
import mil.nga.proj.crs.Ellipsoid;
import mil.nga.proj.crs.Extent;
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
		return reader.peekExpectedToken().equals(",");
	}

	/**
	 * Read a keyword for the quoted text within delimiters
	 * 
	 * @param keyword
	 *            expected keyword
	 * @return text
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readKeywordDelimitedQuotedText(
			CoordinateReferenceSystemKeyword keyword) throws IOException {

		validateKeyword(readKeyword(), keyword);

		readLeftDelimiter();

		String text = reader.readQuotedText();

		readRightDelimiter();

		return text;
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
	public CoordinateReferenceSystem readGeodeticOrGeographic()
			throws IOException {

		CoordinateReferenceSystem crs = null;

		CoordinateReferenceSystemKeyword type = readKeyword();
		validateKeyword(type, CoordinateReferenceSystemKeyword.GEODCRS,
				CoordinateReferenceSystemKeyword.GEOGCRS);

		readLeftDelimiter();

		String crsName = reader.readQuotedText();

		readSeparator();

		CoordinateReferenceSystemKeyword keyword = peekKeyword();
		switch (keyword) {
		case DATUM:
			GeodeticReferenceFrame geodeticReferenceFrame = readGeodeticReferenceFrame();
			// TODO
			break;
		case ENSEMBLE:
			// TODO
			break;
		case DYNAMIC:
			// TODO
			break;
		default:
			throw new ProjectionException(
					"Unsupported WKT CRS keyword: " + keyword);
		}

		readSeparator();

		CoordinateSystem coordinateSystem = readCoordinateSystem();

		List<Usage> usages = readUsages();

		List<Identifier> identifiers = readIdentifiers();

		List<String> remarks = readRemarks();

		readRightDelimiter();

		return crs; // TODO
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

		GeodeticReferenceFrame geodeticReferenceFrame = null; // TODO

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.DATUM);

		readLeftDelimiter();

		String name = reader.readQuotedText();

		readSeparator();

		Ellipsoid ellipsoid = readEllipsoid();

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ANCHOR)) {
			readSeparator();
			String datumAnchor = readKeywordDelimitedQuotedText(
					CoordinateReferenceSystemKeyword.ANCHOR);
			// TODO
		}

		List<Identifier> identifiers = readIdentifiers();

		readRightDelimiter();

		if (isKeywordNext(CoordinateReferenceSystemKeyword.PRIMEM)) {
			readSeparator();
			PrimeMeridian primeMeridian = readPrimeMeridian();
			// TODO
		}

		return geodeticReferenceFrame;
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

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.PRIMEM);

		readLeftDelimiter();

		primeMeridian.setName(reader.readQuotedText());

		readSeparator();

		primeMeridian.setIrmLongitude(reader.readNumber());

		if (isKeywordNext(CoordinateReferenceSystemKeyword.ANGLEUNIT)) {
			readSeparator();
			primeMeridian.setIrmLongitudeAngleUnit(readAngleUnit());
		}

		primeMeridian.setIdentifiers(readIdentifiers());

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

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.ELLIPSOID);

		readLeftDelimiter();

		ellipsoid.setName(reader.readQuotedText());

		readSeparator();

		ellipsoid.setSemiMajorAxis(reader.readUnsignedNumber());

		readSeparator();

		ellipsoid.setInverseFlattening(reader.readUnsignedNumber());

		if (isKeywordNext(CoordinateReferenceSystemKeyword.LENGTHUNIT)) {
			readSeparator();
			ellipsoid.setLengthUnit(readLengthUnit());
		}

		ellipsoid.setIdentifiers(readIdentifiers());

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

		unit.setName(reader.readQuotedText());

		readSeparator();

		unit.setConversionFactor(reader.readUnsignedNumber());

		unit.setIdentifiers(readIdentifiers());

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

		while (isKeywordNext(CoordinateReferenceSystemKeyword.ID)) {

			readSeparator();

			identifiers.add(readIdentifier());

		}

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

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.ID);

		readLeftDelimiter();

		identifier.setName(reader.readQuotedText());

		readSeparator();

		identifier.setUniqueIdentifier(reader.readNumberOrQuotedText());

		if (isNonKeywordNext()) {
			readSeparator();
			identifier.setVersion(reader.readNumberOrQuotedText());
		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.CITATION)) {
			readSeparator();
			identifier.setCitation(readKeywordDelimitedQuotedText(
					CoordinateReferenceSystemKeyword.CITATION));
		}

		if (isKeywordNext(CoordinateReferenceSystemKeyword.URI)) {
			readSeparator();
			identifier.setUri(readKeywordDelimitedQuotedText(
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

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.CS);

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

		coordinateSystem.setIdentifiers(readIdentifiers());

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

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.AXIS);

		readLeftDelimiter();

		String nameAbbrev = reader.readQuotedText();
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

			CoordinateReferenceSystemKeyword bearingKeyword = readKeyword();
			validateKeyword(bearingKeyword,
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

		axis.setIdentifiers(readIdentifiers());

		readRightDelimiter();

		return axis;
	}

	/**
	 * Read Remarks
	 * 
	 * @return remarks
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<String> readRemarks() throws IOException {

		List<String> remarks = new ArrayList<>();

		while (isKeywordNext(CoordinateReferenceSystemKeyword.REMARK)) {

			readSeparator();

			remarks.add(readKeywordDelimitedQuotedText(
					CoordinateReferenceSystemKeyword.REMARK));

		}

		return remarks;
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

		while (isKeywordNext(CoordinateReferenceSystemKeyword.USAGE)) {

			readSeparator();

			usages.add(readUsage());

		}

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

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.USAGE);

		readLeftDelimiter();

		usage.setScope(readKeywordDelimitedQuotedText(
				CoordinateReferenceSystemKeyword.SCOPE));

		usage.setExtent(readExtent());

		readRightDelimiter();

		return usage;
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

			extent.setAreaDescription(readKeywordDelimitedQuotedText(
					CoordinateReferenceSystemKeyword.AREA));
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
	 * Read a Geographic bounding box
	 * 
	 * @return geographic bounding box
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeographicBoundingBox readGeographicBoundingBox()
			throws IOException {

		GeographicBoundingBox boundingBox = new GeographicBoundingBox();

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.BBOX);

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

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword,
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

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.TIMEEXTENT);

		readLeftDelimiter();

		temporalExtent.setStart(reader.readTextOrQuotedText());

		readSeparator();

		temporalExtent.setEnd(reader.readTextOrQuotedText());

		readRightDelimiter();

		return temporalExtent;
	}

}
