package net.homeip.entreprisesmd.mvconv.core.job;

/**
 * An interface for object that wish to be informed when the job are modify. 
 * @author patapouf
 *
 */
public interface JobObserver {

	/**
	 * Fired when the progress of the job changed.
	 * @param job the job
	 */
	void jobProgressHasChanged(Job job);
	
}
