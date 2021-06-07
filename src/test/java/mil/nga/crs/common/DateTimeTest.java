package mil.nga.crs.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Date and Time tests
 * 
 * @author osbornb
 */
public class DateTimeTest {

	/**
	 * Test Year precision
	 */
	@Test
	public void testYear() {

		String text = "2014";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertFalse(dateTime.isOrdinal());
		assertFalse(dateTime.hasTime());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Month precision
	 */
	@Test
	public void testMonth() {

		String text = "2014-01";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(1, dateTime.getMonth().intValue());
		assertFalse(dateTime.isOrdinal());
		assertFalse(dateTime.hasTime());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Day precision
	 */
	@Test
	public void testDay() {

		String text = "2014-03-01";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(3, dateTime.getMonth().intValue());
		assertEquals(1, dateTime.getDay().intValue());
		assertFalse(dateTime.isOrdinal());
		assertFalse(dateTime.hasTime());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Ordinal Day precision
	 */
	@Test
	public void testOrdinalDay() {

		String text = "2014-060";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(60, dateTime.getDay().intValue());
		assertTrue(dateTime.isOrdinal());
		assertFalse(dateTime.hasTime());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Hour precision UTC
	 */
	@Test
	public void testHourUTC() {

		String text = "2014-05-06T23Z";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(5, dateTime.getMonth().intValue());
		assertEquals(6, dateTime.getDay().intValue());
		assertFalse(dateTime.isOrdinal());
		assertTrue(dateTime.hasTime());
		assertEquals(23, dateTime.getHour().intValue());
		assertTrue(dateTime.isTimeZoneUTC());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Ordinal Hour precision UTC
	 */
	@Test
	public void testOrdinalHourUTC() {

		String text = "2014-157T23Z";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(157, dateTime.getDay().intValue());
		assertTrue(dateTime.isOrdinal());
		assertTrue(dateTime.hasTime());
		assertEquals(23, dateTime.getHour().intValue());
		assertTrue(dateTime.isTimeZoneUTC());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Minute precision UTC
	 */
	@Test
	public void testMinuteUTC() {

		String text = "2014-07-12T16:00Z";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(7, dateTime.getMonth().intValue());
		assertEquals(12, dateTime.getDay().intValue());
		assertFalse(dateTime.isOrdinal());
		assertTrue(dateTime.hasTime());
		assertEquals(16, dateTime.getHour().intValue());
		assertEquals(0, dateTime.getMinute().intValue());
		assertTrue(dateTime.isTimeZoneUTC());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Minute precision ahead of UTC
	 */
	@Test
	public void testMinuteAheadUTC() {

		String text = "2014-07-12T17:00+01";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(7, dateTime.getMonth().intValue());
		assertEquals(12, dateTime.getDay().intValue());
		assertFalse(dateTime.isOrdinal());
		assertTrue(dateTime.hasTime());
		assertEquals(17, dateTime.getHour().intValue());
		assertEquals(0, dateTime.getMinute().intValue());
		assertFalse(dateTime.isTimeZoneUTC());
		assertEquals(1, dateTime.getTimeZoneHour().intValue());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Second precision ahead of UTC
	 */
	@Test
	public void testSecondBehindUTC() {

		String text = "2014-09-18T08:17:56-08";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(9, dateTime.getMonth().intValue());
		assertEquals(18, dateTime.getDay().intValue());
		assertFalse(dateTime.isOrdinal());
		assertTrue(dateTime.hasTime());
		assertEquals(8, dateTime.getHour().intValue());
		assertEquals(17, dateTime.getMinute().intValue());
		assertEquals(56, dateTime.getSecond().intValue());
		assertFalse(dateTime.isTimeZoneUTC());
		assertEquals(-8, dateTime.getTimeZoneHour().intValue());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Fraction precision UTC
	 */
	@Test
	public void testFractionUTC() {

		String text = "2014-11-23T00:34:56.789Z";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(11, dateTime.getMonth().intValue());
		assertEquals(23, dateTime.getDay().intValue());
		assertFalse(dateTime.isOrdinal());
		assertTrue(dateTime.hasTime());
		assertEquals(0, dateTime.getHour().intValue());
		assertEquals(34, dateTime.getMinute().intValue());
		assertEquals(56, dateTime.getSecond().intValue());
		assertEquals(0.789, dateTime.getFraction(), 0);
		assertTrue(dateTime.isTimeZoneUTC());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Fraction precision ahead of UTC
	 */
	@Test
	public void testFractionAheadUTC() {

		String text = "2014-11-23T00:34:56.789+06:35";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(11, dateTime.getMonth().intValue());
		assertEquals(23, dateTime.getDay().intValue());
		assertFalse(dateTime.isOrdinal());
		assertTrue(dateTime.hasTime());
		assertEquals(0, dateTime.getHour().intValue());
		assertEquals(34, dateTime.getMinute().intValue());
		assertEquals(56, dateTime.getSecond().intValue());
		assertEquals(0.789, dateTime.getFraction(), 0);
		assertFalse(dateTime.isTimeZoneUTC());
		assertEquals(6, dateTime.getTimeZoneHour().intValue());
		assertEquals(35, dateTime.getTimeZoneMinute().intValue());
		assertEquals(text, dateTime.toString());

	}

	/**
	 * Test Fraction precision behind of UTC
	 */
	@Test
	public void testFractionBehindUTC() {

		String text = "2014-11-23T00:34:56.789-10:07";
		DateTime dateTime = DateTime.parse(text);
		assertNotNull(dateTime);
		assertEquals(2014, dateTime.getYear());
		assertEquals(11, dateTime.getMonth().intValue());
		assertEquals(23, dateTime.getDay().intValue());
		assertFalse(dateTime.isOrdinal());
		assertTrue(dateTime.hasTime());
		assertEquals(0, dateTime.getHour().intValue());
		assertEquals(34, dateTime.getMinute().intValue());
		assertEquals(56, dateTime.getSecond().intValue());
		assertEquals(0.789, dateTime.getFraction(), 0);
		assertFalse(dateTime.isTimeZoneUTC());
		assertEquals(-10, dateTime.getTimeZoneHour().intValue());
		assertEquals(7, dateTime.getTimeZoneMinute().intValue());
		assertEquals(text, dateTime.toString());

	}

}
