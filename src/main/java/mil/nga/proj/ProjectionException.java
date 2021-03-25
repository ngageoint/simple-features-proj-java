package mil.nga.proj;

/**
 * Projection exception
 * 
 * @author osbornb
 */
public class ProjectionException extends RuntimeException {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public ProjectionException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            error message
	 */
	public ProjectionException(String message) {
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
	public ProjectionException(String message, Throwable throwable) {
		super(message, throwable);
	}

	/**
	 * Constructor
	 * 
	 * @param throwable
	 *            throwable
	 */
	public ProjectionException(Throwable throwable) {
		super(throwable);
	}

}