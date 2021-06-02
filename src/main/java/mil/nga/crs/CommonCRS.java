package mil.nga.crs;

import java.util.ArrayList;
import java.util.List;

import mil.nga.crs.common.Identifier;
import mil.nga.crs.common.Usage;

/**
 * Common coordinate reference system and coordinate operations object
 * 
 * @author osbornb
 */
public class CommonCRS extends CRS {

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Usages
	 */
	private List<Usage> usages = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Remark
	 */
	private String remark = null;

	/**
	 * Constructor
	 */
	public CommonCRS() {

	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            coordinate reference system type
	 */
	public CommonCRS(CRSType type) {
		super(type);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 */
	public CommonCRS(String name, CRSType type) {
		super(type);
		setName(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Usage> getUsages() {
		return usages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasUsages() {
		return usages != null && !usages.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numUsages() {
		return usages != null ? usages.size() : 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Usage getUsage(int index) {
		return usages.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUsages(List<Usage> usages) {
		this.usages = usages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUsage(Usage usage) {
		if (this.usages == null) {
			this.usages = new ArrayList<>();
		}
		this.usages.add(usage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUsages(List<Usage> usages) {
		if (this.usages == null) {
			this.usages = new ArrayList<>();
		}
		this.usages.addAll(usages);
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
	public String getRemark() {
		return remark;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasRemark() {
		return getRemark() != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((usages == null) ? 0 : usages.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommonCRS other = (CommonCRS) obj;
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
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (usages == null) {
			if (other.usages != null)
				return false;
		} else if (!usages.equals(other.usages))
			return false;
		return true;
	}

}
