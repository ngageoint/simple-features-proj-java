package mil.nga.proj.wkt;

import java.io.IOException;
import java.util.Locale;

import mil.nga.sf.util.SFException;
import mil.nga.sf.util.TextReader;

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

		// TODO

		return crs;
	}

	/**
	 * Read a left parenthesis
	 * 
	 * @param reader
	 *            text reader
	 * @throws IOException
	 *             upon failure to read
	 */
	private static void leftParenthesis(TextReader reader) throws IOException {
		String token = reader.readToken();
		if (!token.equals("(")) {
			throw new SFException(
					"Invalid token, expected '('. found: '" + token + "'");
		}
	}

	/**
	 * Read a right parenthesis
	 * 
	 * @param reader
	 *            text reader
	 * @throws IOException
	 *             upon failure to read
	 */
	private static void rightParenthesis(TextReader reader) throws IOException {
		String token = reader.readToken();
		if (!token.equals(")")) {
			throw new SFException(
					"Invalid token, expected ')'. found: '" + token + "'");
		}
	}

	/**
	 * Determine if the next token is either a comma or right parenthesis
	 * 
	 * @param reader
	 *            text reader
	 * @return true if a comma
	 * @throws IOException
	 *             upon failure to read
	 */
	private static boolean isCommaOrRightParenthesis(TextReader reader)
			throws IOException {

		boolean is = false;

		String token = reader.peekToken();

		switch (token) {
		case ",":
		case ")":
			is = true;
			break;
		default:
		}

		return is;
	}

	/**
	 * To upper case helper with null handling for switch statements
	 * 
	 * @param value
	 *            string value
	 * @return upper case value or empty string
	 */
	private static String toUpperCase(String value) {
		return value != null ? value.toUpperCase(Locale.US) : "";
	}

}
