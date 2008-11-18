package net.homeip.entreprisesmd.mvconv.core.job;

/**
 * Class that implement this interface can be handle by the JobQueue and be
 * executed.
 * 
 * @author patapouf
 * 
 */
public interface Job {

	/**
	 * Add the given observer to the notification list.
	 * 
	 * @param observer
	 *            the observer to add.
	 */
	void addJobObserver(JobObserver observer);

	/**
	 * Cancel the execution of the running process. This methode are only
	 * effective when the job status are IN_PROGRESS.
	 */
	void cancel();

	/**
	 * Return the description of the Job.
	 * 
	 * @return the description of the Job.
	 */
	String getDescription();

	/**
	 * Return a description of the failure when the process failed to execute.
	 * 
	 * @return a description of failure.
	 */
	String getFailedDescription();

	/**
	 * Return the percent completed.
	 * 
	 * @return the percent completed.
	 */
	int getPercentCompleted();

	/**
	 * Return a string that describe the progress of the execution.
	 * 
	 * @return a progress description
	 */
	String getProgressDescription();

	/**
	 * Return True if this job has been canceled by the user.
	 * @return True if this job has been canceled by the user.
	 */
	boolean isCanceled();
	
	/**
	 * This methode must show to the user the result of the execution. i.e.: by
	 * opening the resulted file.
	 */
	void open();

	/**
	 * Remove the given observer from the notification list.
	 * 
	 * @param observer
	 *            the observer.
	 */
	void removeJobObserver(JobObserver observer);

	/**
	 * Execute the process (might be call only by the JobQueue).
	 * 
	 * @throws JobFailureException
	 *             if any error occur during the process
	 */
	void run() throws JobFailureException;

}
