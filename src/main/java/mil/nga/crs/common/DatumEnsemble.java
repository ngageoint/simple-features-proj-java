package mil.nga.crs.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.wkt.CRSWriter;

/**
 * Datum Ensemble
 * 
 * @author osbornb
 */
public abstract class DatumEnsemble implements Identifiable {

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
	private List<DatumEnsembleMember> members = new ArrayList<>();

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
	 * Number of members
	 * 
	 * @return members count
	 */
	public int numMembers() {
		return members.size();
	}

	/**
	 * Get the member at the index
	 * 
	 * @param index
	 *            member index
	 * @return member
	 */
	public DatumEnsembleMember getMember(int index) {
		return members.get(index);
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
		this.members.add(member);
	}

	/**
	 * Add the members
	 * 
	 * @param members
	 *            members
	 */
	public void addMembers(List<DatumEnsembleMember> members) {
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
