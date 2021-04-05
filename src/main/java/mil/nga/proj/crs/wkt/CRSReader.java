package mil.nga.proj.crs.wkt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mil.nga.proj.PrimeMeridian;
import mil.nga.proj.ProjectionException;
import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateSystem;
import mil.nga.proj.crs.Ellipsoid;
import mil.nga.proj.crs.GeodeticReferenceFrame;
import mil.nga.proj.crs.Identifier;
import mil.nga.proj.crs.Unit;
import mil.nga.proj.crs.UnitType;

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
		String token = reader.readToken();
		if (token == null || (!token.equals("[") && !token.equals("("))) {
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
		String token = reader.readToken();
		if (token == null || (!token.equals("]") && !token.equals(")"))) {
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
		String token = reader.readToken();
		if (token == null || !token.equals(",")) {
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
		return reader.peekToken().equals(",");
	}

	/**
	 * Read quoted text
	 * 
	 * @return text
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readQuotedText() throws IOException {
		String token = reader.readToken();
		if (token == null
				|| (!token.startsWith("\"") || !token.endsWith("\""))) {
			throw new ProjectionException(
					"Invalid double quoted text token. found: '" + token + "'");
		}
		return token.substring(1, token.length() - 1);
	}

	/**
	 * Read a number
	 * 
	 * @return number
	 * @throws IOException
	 *             upon failure to read
	 */
	public double readNumber() throws IOException {
		String token = reader.readToken();
		double number;
		if (token == null) {
			throw new ProjectionException(
					"Invalid number token. found: '" + token + "'");
		}
		try {
			number = Double.parseDouble(token);
		} catch (NumberFormatException e) {
			throw new ProjectionException(
					"Invalid number token. found: '" + token + "'", e);
		}
		return number;
	}

	/**
	 * Read number or quoted text
	 * 
	 * @return number or text value
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readNumberOrQuotedText() throws IOException {
		String token = reader.readToken();
		if (token == null || token.startsWith("\"")) {
			if (token != null && token.endsWith("\"")) {
				token = token.substring(1, token.length() - 1);
			} else {
				throw new ProjectionException(
						"Invalid number or quoted text token. found: '" + token
								+ "'");
			}
		} else {
			// Verify the token is a number
			Double.parseDouble(token);
		}
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

		String crsName = readQuotedText();

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

		// TODO <scope extent identifier remark>

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

		String name = readQuotedText();

		readSeparator();

		Ellipsoid ellipsoid = readEllipsoid();

		String datumAnchor = null;
		List<Identifier> identifiers = new ArrayList<>();

		while (peekSeparator()) {

			readSeparator();
			CoordinateReferenceSystemKeyword optionalKeyword = peekKeyword();

			switch (optionalKeyword) {
			case ANCHOR:
				if (datumAnchor != null || !identifiers.isEmpty()) {
					throw new ProjectionException(
							"Unexpected geodetic reference frame optional keyword. found: "
									+ optionalKeyword);
				}
				readKeyword();
				readLeftDelimiter();
				datumAnchor = readQuotedText();
				readRightDelimiter();
				break;
			case ID:
				identifiers.add(readIdentifier());
				break;
			default:
				throw new ProjectionException(
						"Unexpected geodetic reference frame optional keyword. found: "
								+ optionalKeyword);
			}

		}

		readRightDelimiter();

		if (peekSeparator()) {

			CoordinateReferenceSystemKeyword primeMeridianKeyword = peekOptionalKeyword(
					2);
			if (primeMeridianKeyword != null
					&& primeMeridianKeyword == CoordinateReferenceSystemKeyword.PRIMEM) {

				readSeparator();

				PrimeMeridian primeMeridian = readPrimeMeridian();

				// TODO

			}

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

		PrimeMeridian primeMeridian = null; // TODO

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.PRIMEM);

		readLeftDelimiter();

		String name = readQuotedText();

		readSeparator();

		double irmLongitude = readNumber();

		Unit angleUnit = null;
		List<Identifier> identifiers = new ArrayList<>();

		while (peekSeparator()) {

			readSeparator();

			CoordinateReferenceSystemKeyword optionalKeyword = null;
			Set<CoordinateReferenceSystemKeyword> optionalKeywords = peekKeywords();
			if (optionalKeywords
					.contains(CoordinateReferenceSystemKeyword.ANGLEUNIT)) {
				optionalKeyword = CoordinateReferenceSystemKeyword.ANGLEUNIT;
			} else {
				optionalKeyword = optionalKeywords.iterator().next();
			}

			switch (optionalKeyword) {
			case ANGLEUNIT:
				if (angleUnit != null || !identifiers.isEmpty()) {
					throw new ProjectionException(
							"Unexpected prime meridian optional keyword. found: "
									+ optionalKeyword);
				}
				angleUnit = readAngleUnit();
				break;
			case ID:
				identifiers.add(readIdentifier());
				break;
			default:
				throw new ProjectionException(
						"Unexpected prime meridian optional keyword. found: "
								+ optionalKeyword);
			}

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

		Ellipsoid ellipsoid = null; // TODO

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.ELLIPSOID);

		readLeftDelimiter();

		String name = readQuotedText();

		readSeparator();

		double semiMajorAxis = readNumber();

		readSeparator();

		double inverseFlattening = readNumber();

		Unit lengthUnit = null;
		List<Identifier> identifiers = new ArrayList<>();

		while (peekSeparator()) {

			readSeparator();

			CoordinateReferenceSystemKeyword optionalKeyword = null;
			Set<CoordinateReferenceSystemKeyword> optionalKeywords = peekKeywords();
			if (optionalKeywords
					.contains(CoordinateReferenceSystemKeyword.LENGTHUNIT)) {
				optionalKeyword = CoordinateReferenceSystemKeyword.LENGTHUNIT;
			} else {
				optionalKeyword = optionalKeywords.iterator().next();
			}

			switch (optionalKeyword) {
			case LENGTHUNIT:
				if (lengthUnit != null || !identifiers.isEmpty()) {
					throw new ProjectionException(
							"Unexpected ellipsoid optional keyword. found: "
									+ optionalKeyword);
				}
				lengthUnit = readLengthUnit();
				break;
			case ID:
				identifiers.add(readIdentifier());
				break;
			default:
				throw new ProjectionException(
						"Unexpected ellipsoid optional keyword. found: "
								+ optionalKeyword);
			}

		}

		readRightDelimiter();

		return ellipsoid;
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
	 * Read a Unit
	 * 
	 * @param type
	 *            expected unit type
	 * @return unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readUnit(UnitType type) throws IOException {

		Unit unit = null; // TODO

		Set<CoordinateReferenceSystemKeyword> keywords = readKeywords();
		CoordinateReferenceSystemKeyword crsType = CoordinateReferenceSystemKeyword
				.getType(type.name());
		validateKeywords(keywords, crsType);

		readLeftDelimiter();

		String name = readQuotedText();

		readSeparator();

		double conversationFactor = readNumber();

		List<Identifier> identifiers = new ArrayList<>();

		while (peekSeparator()) {

			readSeparator();

			identifiers.add(readIdentifier());

		}

		readRightDelimiter();

		return unit;
	}

	/**
	 * Read an Identifier
	 * 
	 * @return identifier
	 * @throws IOException
	 *             upon failure to read
	 */
	public Identifier readIdentifier() throws IOException {

		Identifier identifier = null; // TODO

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		validateKeyword(keyword, CoordinateReferenceSystemKeyword.ID);

		readLeftDelimiter();

		String authorityName = readQuotedText();

		readSeparator();

		String authorityUniqueIdentifier = readNumberOrQuotedText();

		String version = null;
		String authorityCitation = null;
		String idUri = null;

		while (peekSeparator()) {

			CoordinateReferenceSystemKeyword optionalKeyword = peekOptionalKeyword();

			if (optionalKeyword == null) {
				if (version != null || authorityCitation != null
						|| idUri != null) {
					throw new ProjectionException(
							"Unexpected identifier optional value. found: "
									+ reader.peekToken());
				}
				version = readNumberOrQuotedText();
			} else {

				optionalKeyword = readKeyword();
				readLeftDelimiter();
				String quotedText = readQuotedText();

				switch (optionalKeyword) {
				case CITATION:
					if (authorityCitation != null || idUri != null) {
						throw new ProjectionException(
								"Unexpected identifier optional keyword. found: "
										+ optionalKeyword);
					}
					authorityCitation = quotedText;
					break;
				case URI:
					if (idUri != null) {
						throw new ProjectionException(
								"Unexpected identifier optional keyword. found: "
										+ optionalKeyword);
					}
					idUri = quotedText;
					break;
				default:
					throw new ProjectionException(
							"Unexpected ellipsoid optional keyword. found: "
									+ keyword);
				}
				readRightDelimiter();

			}

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

		CoordinateSystem coordinateSystem = null; // TODO

		return coordinateSystem;
	}

}
