package net.homeip.entreprisesmd.mvconv.core.job;

/**
 * This exception is throw by the JobQueue when remove method is call on a job
 * in progress.
 * 
 * @author patapouf
 * 
 */
public class JobInProgressException extends Exception {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = -992572771252871941L;

}
