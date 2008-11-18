package net.homeip.entreprisesmd.mvconv.core.job;

/**
 * An interface for object that wish to be informed when the jobqueue are
 * modify.
 * 
 * @author patapouf
 * 
 */
public interface JobQueueObserver {

	/**
	 * Fired when the audoClear mode changed.
	 * 
	 * @param queue
	 *            the queue.
	 */
	void autoClearModeHasChanged(JobQueue queue);
	
	/**
	 * Fired when the job queue are changed.
	 * 
	 * @param queue
	 *            the job queue.
	 */
	void jobQueueHasChanged(JobQueue queue);

	/**
	 * Fired when the job queue are changed.
	 * 
	 * @param queue
	 *            the job queue.
	 * @param job
	 *            the job that changed.
	 */
	void jobStatusHasChanged(JobQueue queue, Job job);



}
