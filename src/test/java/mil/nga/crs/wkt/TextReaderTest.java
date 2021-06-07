package mil.nga.crs.wkt;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

/**
 * Text Reader tests
 * 
 * @author osbornb
 */
public class TextReaderTest {

	/**
	 * Test double quote
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testDoubleQuote() throws IOException {

		TextReader textReader = new TextReader(
				"\"Datum origin is 30°25'20\"\"N, 130°25'20\"\"E.\"");
		String text = textReader.readExpectedToken();
		assertEquals("Datum origin is 30°25'20\"N, 130°25'20\"E.", text);

	}

}
