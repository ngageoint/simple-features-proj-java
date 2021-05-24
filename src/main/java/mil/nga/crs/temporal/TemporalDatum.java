package mil.nga.crs.temporal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.common.DateTime;
import mil.nga.crs.common.Identifiable;
import mil.nga.crs.common.Identifier;
import mil.nga.crs.wkt.CRSWriter;

/**
 * Temporal Datum
 * 
 * @author osbornb
 */
public class TemporalDatum implements Identifiable {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(TemporalDatum.class.getName());

	/**
	 * Datum Name
	 */
	private String name = null;

	/**
	 * Calendar Identifier
	 */
	private String calendar = null;

	/**
	 * Origin Description
	 */
	private String origin = null;

	/**
	 * Origin Description date time
	 */
	private DateTime originDateTime = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Constructor
	 */
	public TemporalDatum() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 */
	public TemporalDatum(String name) {
		setName(name);
	}

	/**
	 * Get the datum name
	 * 
	 * @return datum name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the datum name
	 * 
	 * @param name
	 *            datum name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the calendar identifier
	 * 
	 * @return calendar identifier
	 */
	public String getCalendar() {
		return calendar;
	}

	/**
	 * Has a calendar identifier
	 * 
	 * @return true if has calendar identifier
	 */
	public boolean hasCalendar() {
		return getCalendar() != null;
	}

	/**
	 * Set the calendar identifier
	 * 
	 * @param calendar
	 *            calendar identifier
	 */
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

	/**
	 * Get the origin
	 * 
	 * @return origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Has an origin
	 * 
	 * @return true if has origin
	 */
	public boolean hasOrigin() {
		return getOrigin() != null;
	}

	/**
	 * Set the origin
	 * 
	 * @param origin
	 *            origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
		DateTime dateTime = DateTime.tryParse(origin);
		if (dateTime != null) {
			this.originDateTime = dateTime;
		}
	}

	/**
	 * Get the origin date time
	 * 
	 * @return origin date time
	 */
	public DateTime getOriginDateTime() {
		return originDateTime;
	}

	/**
	 * Has an origin date time
	 * 
	 * @return true if has origin date time
	 */
	public boolean hasOriginDateTime() {
		return getOriginDateTime() != null;
	}

	/**
	 * Set the origin date time
	 * 
	 * @param originDateTime
	 *            origin date time
	 */
	public void setStartDateTime(DateTime originDateTime) {
		this.originDateTime = originDateTime;
		this.origin = originDateTime.toString();
	}

	/**
	 * Set the origin date time
	 * 
	 * @param origin
	 *            origin date time
	 */
	public void setOriginDateTime(String origin) {
		this.origin = origin;
		this.originDateTime = DateTime.parse(origin);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasIdentifiers() {
		return identifiers != null && !identifiers.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numIdentifiers() {
		return identifiers != null ? identifiers.size() : 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identifier getIdentifier(int index) {
		return identifiers.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addIdentifier(Identifier identifier) {
		if (this.identifiers == null) {
			this.identifiers = new ArrayList<>();
		}
		this.identifiers.add(identifier);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addIdentifiers(List<Identifier> identifiers) {
		if (this.identifiers == null) {
			this.identifiers = new ArrayList<>();
		}
		this.identifiers.addAll(identifiers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((calendar == null) ? 0 : calendar.hashCode());
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result
				+ ((originDateTime == null) ? 0 : originDateTime.hashCode());
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
		TemporalDatum other = (TemporalDatum) obj;
		if (calendar == null) {
			if (other.calendar != null)
				return false;
		} else if (!calendar.equals(other.calendar))
			return false;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (originDateTime == null) {
			if (other.originDateTime != null)
				return false;
		} else if (!originDateTime.equals(other.originDateTime))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String value = null;
		CRSWriter writer = new CRSWriter();
		try {
			writer.write(this);
			value = writer.toString();
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"Failed to write temporal datum as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
