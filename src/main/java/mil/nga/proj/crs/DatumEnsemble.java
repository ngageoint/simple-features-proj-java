package mil.nga.proj.crs;

import java.util.ArrayList;
import java.util.List;

/**
 * Datum Ensemble
 * 
 * @author osbornb
 */
public class DatumEnsemble {

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

}
