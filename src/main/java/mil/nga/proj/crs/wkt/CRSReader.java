package mil.nga.proj.crs.wkt;

import java.io.IOException;

import mil.nga.proj.ProjectionException;
import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.Ellipsoid;
import mil.nga.proj.crs.GeodeticReferenceFrame;

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

		CoordinateReferenceSystemKeyword keyword = readKeyword();
		switch (keyword) {
		case GEODCRS:
		case GEOGCRS:
			crs = readGeodeticOrGeographic(keyword);
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
		String keywordToken = reader.readToken();
		CoordinateReferenceSystemKeyword keyword = CoordinateReferenceSystemKeyword
				.getRequiredType(keywordToken);
		return keyword;
	}

	/**
	 * Read a left delimiter
	 * 
	 * @param reader
	 *            text reader
	 * @throws IOException
	 *             upon failure to read
	 */
	private static void readLeftDelimiter(TextReader reader)
			throws IOException {
		String token = reader.readToken();
		if (!token.equals("[") && !token.equals("(")) {
			throw new ProjectionException(
					"Invalid left delimiter token, expected '[' or '('. found: '"
							+ token + "'");
		}
	}

	/**
	 * Read a right delimiter
	 * 
	 * @param reader
	 *            text reader
	 * @throws IOException
	 *             upon failure to read
	 */
	private static void readRightDelimiter(TextReader reader)
			throws IOException {
		String token = reader.readToken();
		if (!token.equals("]") && !token.equals(")")) {
			throw new ProjectionException(
					"Invalid right delimiter token, expected ']' or ')'. found: '"
							+ token + "'");
		}
	}

	/**
	 * Read a WKT Separator (comma)
	 * 
	 * @param reader
	 *            text reader
	 * @throws IOException
	 *             upon failure to read
	 */
	private static void readSeparator(TextReader reader) throws IOException {
		String token = reader.readToken();
		if (!token.equals(",")) {
			throw new ProjectionException(
					"Invalid separator token, expected ','. found: '" + token
							+ "'");
		}
	}

	/**
	 * Peek if the next token is a WKT Separator (comma)
	 * 
	 * @param reader
	 *            text reader
	 * @return true if next token is a separator
	 * @throws IOException
	 *             upon failure to read
	 */
	private static boolean peekSeparator(TextReader reader) throws IOException {
		return reader.peekToken().equals(",");
	}

	/**
	 * Read quoted text
	 * 
	 * @param reader
	 *            text reader
	 * @return text
	 * @throws IOException
	 *             upon failure to read
	 */
	private static String readQuotedText(TextReader reader) throws IOException {
		String token = reader.readToken();
		if (!token.startsWith("\"") || !token.endsWith("\"")) {
			throw new ProjectionException(
					"Invalid quoted text token, expected double quoted text. found: '"
							+ token + "'");
		}
		return token.substring(1, token.length() - 1);
	}

	/**
	 * Read a Geodetic or Geographic CRS
	 * 
	 * @param type
	 *            WKT CRS type
	 * @return crs
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readGeodeticOrGeographic(
			CoordinateReferenceSystemKeyword type) throws IOException {

		CoordinateReferenceSystem crs = null;

		readLeftDelimiter(reader);

		String crsName = readQuotedText(reader);

		readSeparator(reader);

		CoordinateReferenceSystemKeyword keyword = readKeyword();
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

		readSeparator(reader);

		// TODO <coordinate system>

		// TODO <scope extent identifier remark>

		readRightDelimiter(reader);

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

		readLeftDelimiter(reader);

		String name = readQuotedText(reader);

		readSeparator(reader);

		Ellipsoid ellipsoid = readEllipsoid();

		// TODO

		return geodeticReferenceFrame;
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
		if (keyword != CoordinateReferenceSystemKeyword.ELLIPSOID) {
			throw new ProjectionException(
					"Unexpected ellipsoid keyword. found: " + keyword);
		}

		readLeftDelimiter(reader);

		String name = readQuotedText(reader);

		readSeparator(reader);

		double semiMajorAxis = reader.readDouble();

		readSeparator(reader);

		double inverseFlattening = reader.readDouble();

		boolean firstOptional = true;
		while (peekSeparator(reader)) {

			readSeparator(reader);
			CoordinateReferenceSystemKeyword optionalKeyword = readKeyword();

			switch (optionalKeyword) {
			case LENGTHUNIT:
				if (firstOptional) {
					// TODO
				} else {
					throw new ProjectionException(
							"Unexpected ellipsoid optional keyword. found: "
									+ keyword);
				}
				break;
			case ID:
				// TODO
				break;
			default:
				throw new ProjectionException(
						"Unexpected ellipsoid optional keyword. found: "
								+ keyword);
			}

			firstOptional = false;

		}

		readRightDelimiter(reader);

		return ellipsoid;
	}

}
