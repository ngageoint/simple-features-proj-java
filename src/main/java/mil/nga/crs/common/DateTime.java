package mil.nga.crs.common;

import mil.nga.proj.ProjectionException;

/**
 * Date and time
 * 
 * @author osbornb
 */
public class DateTime {

	/**
	 * Hyphen
	 */
	public static final String HYPHEN = "-";

	/**
	 * Time Designator
	 */
	public static final String TIME_DESIGNATOR = "T";

	/**
	 * Colon
	 */
	public static final String COLON = ":";

	/**
	 * Period
	 */
	public static final String PERIOD = ".";

	/**
	 * UTC Time Zone Designator
	 */
	public static final String UTC = "Z";

	/**
	 * Plus Sign
	 */
	public static final String PLUS_SIGN = "+";

	/**
	 * Minus Sign
	 */
	public static final String MINUS_SIGN = "-";

	/**
	 * Parse the text into a Date Time
	 * 
	 * @param text
	 *            date time text
	 * @return date time
	 */
	public static DateTime parse(String text) {

		DateTime dateTime = null;

		if (text != null && text.length() >= 4) {

			String[] dateTimeParts = text.split(TIME_DESIGNATOR);

			String date = null;

			int numDateTimeParts = dateTimeParts.length;
			if (numDateTimeParts == 1 || numDateTimeParts == 2) {
				date = dateTimeParts[0];
			}

			if (date != null) {

				String[] dateParts = date.split(HYPHEN);
				int numDateParts = dateParts.length;

				if (numDateParts >= 1 && numDateParts <= 3) {
					dateTime = new DateTime();

					dateTime.setYear(Integer.parseInt(dateParts[0]));

					if (numDateParts > 1) {
						int datePartTwo = Integer.parseInt(dateParts[1]);
						if (numDateParts == 2) {
							if (dateParts[1].length() == 2) {
								dateTime.setMonth(datePartTwo);
							} else {
								dateTime.setDay(datePartTwo);
							}
						} else {
							dateTime.setMonth(datePartTwo);
							dateTime.setDay(Integer.parseInt(dateParts[2]));
						}
					}
				}

			}

			if (dateTime != null && numDateTimeParts == 2) {
				String timeWithZone = dateTimeParts[1];

				int zoneIndex = timeWithZone.indexOf(UTC);
				if (zoneIndex == -1) {
					zoneIndex = timeWithZone.indexOf(PLUS_SIGN);
					if (zoneIndex == -1) {
						zoneIndex = timeWithZone.indexOf(MINUS_SIGN);
					}
				}
				if (zoneIndex != -1) {

					String timeZone = timeWithZone.substring(zoneIndex);
					if (!timeZone.equals(UTC)) {
						String[] timeZoneParts = timeZone.split(COLON);
						dateTime.setTimeZoneHour(
								Integer.parseInt(timeZoneParts[0]));
						if (timeZoneParts.length == 2) {
							dateTime.setTimeZoneMinute(
									Integer.parseInt(timeZoneParts[1]));
						}
					}

					String time = timeWithZone.substring(0, zoneIndex);
					String[] timeParts = time.split(COLON);
					int numTimeParts = timeParts.length;

					if (numTimeParts >= 1 && numTimeParts <= 3) {
						dateTime.setHour(Integer.parseInt(timeParts[0]));
						if (numTimeParts > 1) {
							dateTime.setMinute(Integer.parseInt(timeParts[1]));
							if (numTimeParts > 2) {
								String seconds = timeParts[2];
								int decimalIndex = seconds.indexOf(PERIOD);
								if (decimalIndex > -1) {
									String fraction = "0" + PERIOD + seconds
											.substring(decimalIndex + 1);
									dateTime.setFraction(
											Double.parseDouble(fraction));
									seconds = seconds.substring(0,
											decimalIndex);
								}
								dateTime.setSecond(Integer.parseInt(seconds));
							}
						}
					}
				}

				if (!dateTime.hasHour()) {
					dateTime = null;
				}
			}

		}

		if (dateTime == null) {
			throw new ProjectionException(
					"Invalid Date and Time value: " + text);
		}

		return dateTime;
	}

	/**
	 * Attempt to parse the text into a Date Time
	 * 
	 * @param text
	 *            potential date time text
	 * @return date time or null
	 */
	public static DateTime tryParse(String text) {
		DateTime dateTime = null;
		try {
			dateTime = parse(text);
		} catch (Exception e) {
			// eat
		}
		return dateTime;
	}

	/**
	 * Year
	 */
	private int year;

	/**
	 * Month
	 */
	private Integer month = null;

	/**
	 * Day
	 */
	private Integer day = null;

	/**
	 * Hour
	 */
	private Integer hour = null;

	/**
	 * Minute
	 */
	private Integer minute = null;

	/**
	 * Second
	 */
	private Integer second = null;

	/**
	 * Seconds fraction
	 */
	private Double fraction = null;

	/**
	 * Local time zone hour
	 */
	private Integer timeZoneHour = null;

	/**
	 * Local time zone minute
	 */
	private Integer timeZoneMinute = null;

	/**
	 * Constructor
	 */
	public DateTime() {

	}

	/**
	 * Constructor
	 * 
	 * @param year
	 *            year
	 */
	public DateTime(int year) {
		setYear(year);
	}

	/**
	 * Is an ordinal day
	 * 
	 * @return true if ordinal day
	 */
	public boolean isOrdinal() {
		return day != null && month == null;
	}

	/**
	 * If date has a time precision
	 * 
	 * @return true if has time
	 */
	public boolean hasTime() {
		return hasHour();
	}

	/**
	 * Get the year
	 * 
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Set the year
	 * 
	 * @param year
	 *            year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Get the month
	 * 
	 * @return month
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * Check if has a month
	 * 
	 * @return true if has month
	 */
	public boolean hasMonth() {
		return getMonth() != null;
	}

	/**
	 * Set the month
	 * 
	 * @param month
	 *            month
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * Get the day
	 * 
	 * @return day
	 */
	public Integer getDay() {
		return day;
	}

	/**
	 * Check if has a day
	 * 
	 * @return true if has day
	 */
	public boolean hasDay() {
		return getDay() != null;
	}

	/**
	 * Set the day
	 * 
	 * @param day
	 *            day
	 */
	public void setDay(Integer day) {
		this.day = day;
	}

	/**
	 * Get the hour
	 * 
	 * @return hour
	 */
	public Integer getHour() {
		return hour;
	}

	/**
	 * Check if has a hour
	 * 
	 * @return true if has hour
	 */
	public boolean hasHour() {
		return getHour() != null;
	}

	/**
	 * Set the hour
	 * 
	 * @param hour
	 *            hour
	 */
	public void setHour(Integer hour) {
		this.hour = hour;
	}

	/**
	 * Get the minute
	 * 
	 * @return minute
	 */
	public Integer getMinute() {
		return minute;
	}

	/**
	 * Check if has a minute
	 * 
	 * @return true if has minute
	 */
	public boolean hasMinute() {
		return getMinute() != null;
	}

	/**
	 * Set the minute
	 * 
	 * @param minute
	 *            minute
	 */
	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	/**
	 * Get the second
	 * 
	 * @return second
	 */
	public Integer getSecond() {
		return second;
	}

	/**
	 * Check if has a second
	 * 
	 * @return true if has second
	 */
	public boolean hasSecond() {
		return getSecond() != null;
	}

	/**
	 * Set the second
	 * 
	 * @param second
	 *            second
	 */
	public void setSecond(Integer second) {
		this.second = second;
	}

	/**
	 * Get the fraction
	 * 
	 * @return fraction
	 */
	public Double getFraction() {
		return fraction;
	}

	/**
	 * Check if has a fraction
	 * 
	 * @return true if has fraction
	 */
	public boolean hasFraction() {
		return getFraction() != null;
	}

	/**
	 * Set the fraction
	 * 
	 * @param fraction
	 *            fraction
	 */
	public void setFraction(Double fraction) {
		if (fraction != null && (fraction < 0 || fraction >= 1.0)) {
			throw new ProjectionException(
					"Invalid fraction value: " + fraction);
		}
		this.fraction = fraction;
	}

	/**
	 * Get the signed local time zone hour
	 * 
	 * @return time zone hour (+ or -)
	 */
	public Integer getTimeZoneHour() {
		return timeZoneHour;
	}

	/**
	 * Has a time zone hour
	 * 
	 * @return true if has time zone hour
	 */
	public boolean hasTimeZoneHour() {
		return getTimeZoneHour() != null;
	}

	/**
	 * Set the signed local time zone hour
	 * 
	 * @param timeZoneHour
	 *            time zone hour (+ or -)
	 */
	public void setTimeZoneHour(Integer timeZoneHour) {
		this.timeZoneHour = timeZoneHour;
	}

	/**
	 * Get the local time zone minute
	 * 
	 * @return time zone minute
	 */
	public Integer getTimeZoneMinute() {
		return timeZoneMinute;
	}

	/**
	 * Has a time zone minute
	 * 
	 * @return true if has time zone minute
	 */
	public boolean hasTimeZoneMinute() {
		return getTimeZoneMinute() != null;
	}

	/**
	 * Set the local time zone minute
	 * 
	 * @param timeZoneMinute
	 *            time zone minute
	 */
	public void setTimeZoneMinute(Integer timeZoneMinute) {
		this.timeZoneMinute = timeZoneMinute;
	}

	/**
	 * Is the time zone UTC
	 * 
	 * @return true if UTC
	 */
	public boolean isTimeZoneUTC() {
		return !hasTimeZoneHour();
	}

	/**
	 * Set the time zone as UTC
	 */
	public void setTimeZoneUTC() {
		setTimeZoneHour(null);
		setTimeZoneMinute(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result
				+ ((fraction == null) ? 0 : fraction.hashCode());
		result = prime * result + ((hour == null) ? 0 : hour.hashCode());
		result = prime * result + ((minute == null) ? 0 : minute.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		result = prime * result
				+ ((timeZoneHour == null) ? 0 : timeZoneHour.hashCode());
		result = prime * result
				+ ((timeZoneMinute == null) ? 0 : timeZoneMinute.hashCode());
		result = prime * result + year;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateTime other = (DateTime) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (fraction == null) {
			if (other.fraction != null)
				return false;
		} else if (!fraction.equals(other.fraction))
			return false;
		if (hour == null) {
			if (other.hour != null)
				return false;
		} else if (!hour.equals(other.hour))
			return false;
		if (minute == null) {
			if (other.minute != null)
				return false;
		} else if (!minute.equals(other.minute))
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		if (timeZoneHour == null) {
			if (other.timeZoneHour != null)
				return false;
		} else if (!timeZoneHour.equals(other.timeZoneHour))
			return false;
		if (timeZoneMinute == null) {
			if (other.timeZoneMinute != null)
				return false;
		} else if (!timeZoneMinute.equals(other.timeZoneMinute))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append(String.format("%04d", getYear()));
		if (hasMonth()) {
			text.append(HYPHEN);
			text.append(String.format("%02d", getMonth()));
		}
		if (hasDay()) {
			text.append(HYPHEN);
			if (isOrdinal()) {
				text.append(String.format("%03d", getDay()));
			} else {
				text.append(String.format("%02d", getDay()));
			}
		}
		if (hasHour()) {
			text.append(TIME_DESIGNATOR);
			text.append(String.format("%02d", getHour()));
			if (hasMinute()) {
				text.append(COLON);
				text.append(String.format("%02d", getMinute()));
				if (hasSecond()) {
					text.append(COLON);
					text.append(String.format("%02d", getSecond()));
					if (hasFraction()) {
						String fraction = getFraction().toString();
						int periodIndex = fraction.indexOf(PERIOD);
						if (periodIndex >= 0
								&& periodIndex + 1 < fraction.length()) {
							text.append(PERIOD);
							text.append(fraction.substring(periodIndex + 1));
						}
					}
				}
			}
			if (isTimeZoneUTC()) {
				text.append(UTC);
			} else {
				int timeZoneHour = getTimeZoneHour();
				if (timeZoneHour >= 0) {
					text.append(PLUS_SIGN);
				} else {
					text.append(MINUS_SIGN);
					timeZoneHour *= -1;
				}
				text.append(String.format("%02d", timeZoneHour));
				if (hasTimeZoneMinute()) {
					text.append(COLON);
					text.append(String.format("%02d", getTimeZoneMinute()));
				}
			}
		}
		return text.toString();
	}

}
