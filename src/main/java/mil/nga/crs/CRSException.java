package mil.nga.crs;

/**
 * Coordinate Reference Systems exception
 * 
 * @author osbornb
 */
public class CRSException extends RuntimeException {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public CRSException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            error message
	 */
	public CRSException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            error message
	 * @param throwable
	 *            throwable
	 */
	public CRSException(String message, Throwable throwable) {
		super(message, throwable);
	}

	/**
	 * Constructor
	 * 
	 * @param throwable
	 *            throwable
	 */
	public CRSException(Throwable throwable) {
		super(throwable);
	}

}