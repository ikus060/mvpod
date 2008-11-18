package net.homeip.entreprisesmd.mvconv.core.job;

/**
 * This exception is throw by a Job when the execution of run method failed for
 * any reason.
 * 
 * @author patapouf
 * 
 */
public class JobFailureException extends Exception {

	/**
	 * Serialization version ID.
	 */
	private static final long serialVersionUID = 8575863384589445574L;

	/**
	 * Create a new Job Failure exception with the given description message.
	 * 
	 * @param message
	 *            the message.
	 */
	public JobFailureException(String message) {
		super(message);
	}

}
