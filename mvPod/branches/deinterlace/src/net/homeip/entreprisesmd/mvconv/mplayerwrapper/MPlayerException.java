package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * Thrown by mplayer class if any operation error occur in process.
 * 
 * @author patapouf
 * 
 */
public class MPlayerException extends Exception {

	/**
	 * A serial version unique identifer.
	 */
	private static final long serialVersionUID = -8109109741084378464L;

	/**
	 * Create a new mplayer exception with the given message.
	 * 
	 * @param message
	 *            the error message.
	 */
	public MPlayerException(String message) {
		super(message);
	}

	/**
	 * Create a new mplayer exception with the given description message and the
	 * cause of this exception.
	 * 
	 * @param message
	 *            the error message.
	 * @param cause
	 *            the exception that cause this error.
	 */
	public MPlayerException(String message, Exception cause) {
		super(message, cause);
	}

}
