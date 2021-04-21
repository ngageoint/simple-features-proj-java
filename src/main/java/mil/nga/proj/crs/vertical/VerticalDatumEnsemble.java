package mil.nga.proj.crs.vertical;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.common.DatumEnsemble;
import mil.nga.proj.crs.common.DatumEnsembleMember;
import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Vertical Datum Ensemble
 * 
 * @author osbornb
 */
public class VerticalDatumEnsemble extends DatumEnsemble {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(VerticalDatumEnsemble.class.getName());

	/**
	 * Constructor
	 */
	public VerticalDatumEnsemble() {

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
	public VerticalDatumEnsemble(String name, DatumEnsembleMember member,
			double accuracy) {
		super(name, member, accuracy);
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
	public VerticalDatumEnsemble(String name, List<DatumEnsembleMember> members,
			double accuracy) {
		super(name, members, accuracy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
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
					"Failed to write vertical datum ensemble as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
