package mil.nga.proj.crs.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Datum Ensemble
 * 
 * @author osbornb
 */
public abstract class DatumEnsemble {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(DatumEnsemble.class.getName());

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Members
	 */
	private List<DatumEnsembleMember> members = null;

	/**
	 * Accuracy (in meters)
	 */
	private double accuracy;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Constructor
	 */
	public DatumEnsemble() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param member
	 *            member
	 * @param accuracy
	 *            accuracy
	 */
	public DatumEnsemble(String name, DatumEnsembleMember member,
			double accuracy) {
		setName(name);
		addMember(member);
		setAccuracy(accuracy);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param members
	 *            members
	 * @param accuracy
	 *            accuracy
	 */
	public DatumEnsemble(String name, List<DatumEnsembleMember> members,
			double accuracy) {
		setName(name);
		addMembers(members);
		setAccuracy(accuracy);
	}

	/**
	 * Get the name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name
	 * 
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the members
	 * 
	 * @return members
	 */
	public List<DatumEnsembleMember> getMembers() {
		return members;
	}

	/**
	 * Set the members
	 * 
	 * @param members
	 *            members
	 */
	public void setMembers(List<DatumEnsembleMember> members) {
		this.members = members;
	}

	/**
	 * Add the member
	 * 
	 * @param member
	 *            member
	 */
	public void addMember(DatumEnsembleMember member) {
		if (this.members == null) {
			this.members = new ArrayList<>();
		}
		this.members.add(member);
	}

	/**
	 * Add the members
	 * 
	 * @param members
	 *            members
	 */
	public void addMembers(List<DatumEnsembleMember> members) {
		if (this.members == null) {
			this.members = new ArrayList<>();
		}
		this.members.addAll(members);
	}

	/**
	 * Get the accuracy
	 * 
	 * @return accuracy
	 */
	public double getAccuracy() {
		return accuracy;
	}

	/**
	 * Set the accuracy
	 * 
	 * @param accuracy
	 *            accuracy
	 */
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * Get the identifiers
	 * 
	 * @return identifiers
	 */
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	/**
	 * Has identifiers
	 * 
	 * @return true if has identifiers
	 */
	public boolean hasIdentifiers() {
		return identifiers != null && !identifiers.isEmpty();
	}

	/**
	 * Set the identifiers
	 * 
	 * @param identifiers
	 *            identifiers
	 */
	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}

	/**
	 * Add the identifier
	 * 
	 * @param identifier
	 *            identifier
	 */
	public void addIdentifier(Identifier identifier) {
		if (this.identifiers == null) {
			this.identifiers = new ArrayList<>();
		}
		this.identifiers.add(identifier);
	}

	/**
	 * Add the identifiers
	 * 
	 * @param identifiers
	 *            identifiers
	 */
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
		long temp;
		temp = Double.doubleToLongBits(accuracy);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DatumEnsemble other = (DatumEnsemble) obj;
		if (Double.doubleToLongBits(accuracy) != Double
				.doubleToLongBits(other.accuracy))
			return false;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
					"Failed to write datum ensemble as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
