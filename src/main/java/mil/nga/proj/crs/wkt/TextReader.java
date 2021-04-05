package mil.nga.proj.crs.wkt;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Read through text string
 * 
 * @author osbornb
 */
public class TextReader {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(TextReader.class.getName());

	/**
	 * Reader
	 */
	private final Reader reader;

	/**
	 * Next tokens cache for peeks
	 */
	private final List<String> nextTokens = new ArrayList<>();

	/**
	 * Next character number cache for between token caching
	 */
	private Integer nextCharacterNum;

	/**
	 * Constructor
	 * 
	 * @param text
	 *            text
	 */
	public TextReader(String text) {
		this(new StringReader(text));
	}

	/**
	 * Constructor
	 * 
	 * @param reader
	 *            reader
	 */
	public TextReader(Reader reader) {
		this.reader = reader;
	}

	/**
	 * Get the reader
	 * 
	 * @return reader
	 */
	public Reader getReader() {
		return reader;
	}

	/**
	 * Close the text reader
	 */
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Failed to close text reader", e);
		}
	}

	/**
	 * Read the next token. Ignores whitespace until a non whitespace character
	 * is encountered. Returns a contiguous block of token characters ( [a-z] |
	 * [A-Z] | [0-9] | - | . | + ) or a non whitespace single character.
	 * 
	 * @return token
	 * @throws IOException
	 *             upon read error
	 */
	public String readToken() throws IOException {

		String token = null;

		// Get the next token, cached or read
		if (!nextTokens.isEmpty()) {
			token = nextTokens.remove(0);
		} else {

			StringBuilder builder = null;
			boolean isQuote = false;
			boolean previousCharQuote = false;

			// Get the next character, cached or read
			int characterNum;
			if (nextCharacterNum != null) {
				characterNum = nextCharacterNum;
				nextCharacterNum = null;
			} else {
				characterNum = reader.read();
			}

			// Continue while characters are left
			while (characterNum != -1) {

				char character = (char) characterNum;

				// Check if not the first character in the token
				if (builder != null) {

					// If in a quoted string
					if (isQuote) {
						boolean charQuote = character == '"';
						if (!previousCharQuote || charQuote) {
							builder.append(character);
						}
						if (previousCharQuote) {
							previousCharQuote = false;
							if (!charQuote) {
								// End of quoted string found at previous
								// character
								nextCharacterNum = characterNum;
								break;
							}
						}
						previousCharQuote = charQuote;
					} else if (isTokenCharacter(character)) {
						// Append token characters
						builder.append(character);
					} else {
						// Complete the token before this character and cache
						// the character
						if (!Character.isWhitespace(character)) {
							nextCharacterNum = characterNum;
						}
						break;
					}

				} else if (!Character.isWhitespace(character)) {

					// First non whitespace character in the token
					builder = new StringBuilder();
					builder.append(character);

					// Start of a quoted string
					if (character == '"') {
						isQuote = true;
					} else if (!isTokenCharacter(character)) {
						// Complete token if a single character token
						break;
					}

				}

				// Read the next character
				characterNum = reader.read();
			}

			if (builder != null) {
				token = builder.toString();
			}

		}
		return token;
	}

	/**
	 * Peek at the next token without reading past it
	 * 
	 * @return next token
	 * @throws IOException
	 *             upon read error
	 */
	public String peekToken() throws IOException {
		return peekToken(1);
	}

	/**
	 * Peek at a token without reading past it
	 * 
	 * @param num
	 *            number of tokens out to peek at
	 * @return token
	 * @throws IOException
	 *             upon read error
	 */
	public String peekToken(int num) throws IOException {
		for (int i = 1; i <= num; i++) {
			if (nextTokens.size() < i) {
				String token = readToken();
				if (token != null) {
					nextTokens.add(token);
				} else {
					break;
				}
			}
		}
		String token = null;
		if (num <= nextTokens.size()) {
			token = nextTokens.get(num - 1);
		}
		return token;
	}

	/**
	 * Check if the character is a contiguous block token character: ( [a-z] |
	 * [A-Z] | [0-9] | - | . | + )
	 * 
	 * @param c
	 *            character
	 * @return true if token character
	 */
	private static boolean isTokenCharacter(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
				|| (c >= '0' && c <= '9') || c == '-' || c == '.' || c == '+';
	}

}
