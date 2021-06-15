package mil.nga.crs.common;

import mil.nga.crs.CRSException;

/**
 * Common Units
 * 
 * @author osbornb
 */
public class Units {

	/**
	 * Micrometre unit name
	 */
	public static final String MICROMETRE = "metre";

	/**
	 * Millimetre unit name
	 */
	public static final String MILLIMETRE = "millimetre";

	/**
	 * Metre unit name
	 */
	public static final String METRE = "metre";

	/**
	 * Kilometre unit name
	 */
	public static final String KILOMETRE = "kilometre";

	/**
	 * German legal metre unit name
	 */
	public static final String GERMAN_LEGAL_METRE = "German legal metre";

	/**
	 * US survey foot unit name
	 */
	public static final String US_SURVEY_FOOT = "US survey foot";

	/**
	 * Microradian unit name
	 */
	public static final String MICRORADIAN = "microradian";

	/**
	 * Milliradian unit name
	 */
	public static final String MILLIRADIAN = "milliradian";

	/**
	 * Radian unit name
	 */
	public static final String RADIAN = "radian";

	/**
	 * Arc-second unit name
	 */
	public static final String ARC_SECOND = "arc-second";

	/**
	 * Arc-minute unit name
	 */
	public static final String ARC_MINUTE = "arc-minute";

	/**
	 * Degree unit name
	 */
	public static final String DEGREE = "degree";

	/**
	 * Grad unit name
	 */
	public static final String GRAD = "grad";

	/**
	 * Unity unit name
	 */
	public static final String UNITY = "unity";

	/**
	 * Bin unit name
	 */
	public static final String BIN = "bin";

	/**
	 * Parts per million unit name
	 */
	public static final String PARTS_PER_MILLION = "parts per million";

	/**
	 * Pascal unit name
	 */
	public static final String PASCAL = "pascal";

	/**
	 * Hectopascal unit name
	 */
	public static final String HECTOPASCAL = "hectopascal";

	/**
	 * Microsecond unit name
	 */
	public static final String MICROSECOND = "microsecond";

	/**
	 * Millisecond unit name
	 */
	public static final String MILLISECOND = "millisecond";

	/**
	 * Second unit name
	 */
	public static final String SECOND = "second";

	/**
	 * Minute unit name
	 */
	public static final String MINUTE = "minute";

	/**
	 * Hour unit name
	 */
	public static final String HOUR = "hour";

	/**
	 * Day unit name
	 */
	public static final String DAY = "day";

	/**
	 * Year unit name
	 */
	public static final String YEAR = "year";

	/**
	 * Calendar second unit name
	 */
	public static final String CALENDAR_SECOND = "calendar second";

	/**
	 * Calendar month unit name
	 */
	public static final String CALENDAR_MONTH = "calendar month";

	/**
	 * Get a micrometre unit
	 * 
	 * @return micrometre unit
	 */
	public static Unit getMicrometre() {
		return new Unit(UnitType.LENGTHUNIT, MICROMETRE, 0.000001);
	}

	/**
	 * Get a millimetre unit
	 * 
	 * @return millimetre unit
	 */
	public static Unit getMillimetre() {
		return new Unit(UnitType.LENGTHUNIT, MILLIMETRE, 0.001);
	}

	/**
	 * Get a metre unit
	 * 
	 * @return metre unit
	 */
	public static Unit getMetre() {
		return new Unit(UnitType.LENGTHUNIT, METRE, 1.0);
	}

	/**
	 * Get a kilometre unit
	 * 
	 * @return kilometre unit
	 */
	public static Unit getKilometre() {
		return new Unit(UnitType.LENGTHUNIT, KILOMETRE, 1000.0);
	}

	/**
	 * Get a German legal metre unit
	 * 
	 * @return German legal metre unit
	 */
	public static Unit getGermanLegalMetre() {
		return new Unit(UnitType.LENGTHUNIT, GERMAN_LEGAL_METRE, 1.0000135965);
	}

	/**
	 * Get a US survey foot unit
	 * 
	 * @return US survey foot unit
	 */
	public static Unit getUSSurveyFoot() {
		return new Unit(UnitType.LENGTHUNIT, US_SURVEY_FOOT, 0.304800609601219);
	}

	/**
	 * Get a microradian unit
	 * 
	 * @return microradian unit
	 */
	public static Unit getMicroradian() {
		return new Unit(UnitType.ANGLEUNIT, MICRORADIAN, 0.000001);
	}

	/**
	 * Get a milliradian unit
	 * 
	 * @return milliradian unit
	 */
	public static Unit getMilliradian() {
		return new Unit(UnitType.ANGLEUNIT, MILLIRADIAN, 0.001);
	}

	/**
	 * Get a radian unit
	 * 
	 * @return radian unit
	 */
	public static Unit getRadian() {
		return new Unit(UnitType.ANGLEUNIT, RADIAN, 1.0);
	}

	/**
	 * Get an arc-second unit
	 * 
	 * @return arc-second unit
	 */
	public static Unit getArcSecond() {
		return new Unit(UnitType.ANGLEUNIT, ARC_SECOND,
				0.00000484813681109535993589914102357);
	}

	/**
	 * Get an arc-minute unit
	 * 
	 * @return arc-minute unit
	 */
	public static Unit getArcMinute() {
		return new Unit(UnitType.ANGLEUNIT, ARC_MINUTE, 0.0002908882086657216);
	}

	/**
	 * Get a degree unit
	 * 
	 * @return degree unit
	 */
	public static Unit getDegree() {
		return new Unit(UnitType.ANGLEUNIT, DEGREE, 0.017453292519943295);
	}

	/**
	 * Get a grad unit
	 * 
	 * @return grad unit
	 */
	public static Unit getGrad() {
		return new Unit(UnitType.ANGLEUNIT, GRAD, 0.015707963267949);
	}

	/**
	 * Get a unity unit
	 * 
	 * @return unity unit
	 */
	public static Unit getUnity() {
		return new Unit(UnitType.SCALEUNIT, UNITY, 1.0);
	}

	/**
	 * Get a bin unit
	 * 
	 * @return bin unit
	 */
	public static Unit getBin() {
		return new Unit(UnitType.SCALEUNIT, BIN, 1.0);
	}

	/**
	 * Get a parts per million unit
	 * 
	 * @return parts per million unit
	 */
	public static Unit getPartsPerMillion() {
		return new Unit(UnitType.SCALEUNIT, PARTS_PER_MILLION, 0.000001);
	}

	/**
	 * Get a pascal unit
	 * 
	 * @return pascal unit
	 */
	public static Unit getPascal() {
		return new Unit(UnitType.PARAMETRICUNIT, PASCAL, 1.0);
	}

	/**
	 * Get a hectopascal unit
	 * 
	 * @return hectopascal unit
	 */
	public static Unit getHectopascal() {
		return new Unit(UnitType.PARAMETRICUNIT, HECTOPASCAL, 100.0);
	}

	/**
	 * Get a microsecond unit
	 * 
	 * @return microsecond unit
	 */
	public static Unit getMicrosecond() {
		return new Unit(UnitType.TIMEUNIT, MICROSECOND, 0.000001);
	}

	/**
	 * Get a millisecond unit
	 * 
	 * @return millisecond unit
	 */
	public static Unit getMillisecond() {
		return new Unit(UnitType.TIMEUNIT, MILLISECOND, 0.001);
	}

	/**
	 * Get a second unit
	 * 
	 * @return second unit
	 */
	public static Unit getSecond() {
		return new Unit(UnitType.TIMEUNIT, SECOND, 1.0);
	}

	/**
	 * Get a minute unit
	 * 
	 * @return minute unit
	 */
	public static Unit getMinute() {
		return new Unit(UnitType.TIMEUNIT, MINUTE, 60.0);
	}

	/**
	 * Get a hour unit
	 * 
	 * @return hour unit
	 */
	public static Unit getHour() {
		return new Unit(UnitType.TIMEUNIT, HOUR, 3600.0);
	}

	/**
	 * Get a day unit
	 * 
	 * @return day unit
	 */
	public static Unit getDay() {
		return new Unit(UnitType.TIMEUNIT, DAY, 86400.0);
	}

	/**
	 * Get a year unit
	 * 
	 * @return year unit
	 */
	public static Unit getYear() {
		return new Unit(UnitType.TIMEUNIT, YEAR, 31557600.0);
	}

	/**
	 * Get a calendar second unit
	 * 
	 * @return calendar second unit
	 */
	public static Unit getCalendarSecond() {
		return new Unit(UnitType.TIMEUNIT, CALENDAR_SECOND);
	}

	/**
	 * Get a calendar month unit
	 * 
	 * @return calendar month unit
	 */
	public static Unit getCalendarMonth() {
		return new Unit(UnitType.TIMEUNIT, CALENDAR_MONTH);
	}

	/**
	 * Get the default unit for the unit type
	 * 
	 * @param type
	 *            unit type
	 * @return default unit or null if no default
	 */
	public static Unit getDefaultUnit(UnitType type) {

		Unit defaultUnit = null;

		switch (type) {
		case LENGTHUNIT:
			defaultUnit = getMetre();
			break;
		case ANGLEUNIT:
			defaultUnit = getDegree();
			break;
		case SCALEUNIT:
			defaultUnit = getUnity();
			break;
		default:
		}

		return defaultUnit;
	}

	/**
	 * Determine if values can be converted between the two units
	 * 
	 * @param unit1
	 *            first unit
	 * @param unit2
	 *            second unit
	 * @return true if can convert
	 */
	public static boolean canConvert(Unit unit1, Unit unit2) {
		return unit1 != null && unit2 != null
				&& unit1.getType() == unit2.getType()
				&& unit1.hasConversionFactor() && unit2.hasConversionFactor();
	}

	/**
	 * Convert the value from a unit to a same typed unit, both with conversion
	 * factors
	 * 
	 * @param value
	 *            value to convert
	 * @param from
	 *            unit to convert from
	 * @param to
	 *            unit to convert to
	 * @return converted value
	 */
	public static double convert(double value, Unit from, Unit to) {

		if (from.getType() != to.getType()) {
			throw new CRSException("Can't convert value '" + value
					+ "' from unit type " + from.getType().name()
					+ " to unit type " + to.getType().name());
		}

		if (!from.hasConversionFactor()) {
			throw new CRSException("Can't convert value '" + value
					+ "' from unit type " + from.getType().name()
					+ " without a conversion factor.");
		}

		if (!to.hasConversionFactor()) {
			throw new CRSException("Can't convert value '" + value
					+ "' to unit type " + to.getType().name()
					+ " without a conversion factor.");
		}

		return value * (from.getConversionFactor() / to.getConversionFactor());
	}

}
