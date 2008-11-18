package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * The EncodingProgressObserver interface is implemented by objects that monitor
 * the progress of an encoding process; the methods in this interface are
 * invoked by code that performs the activity.
 * 
 * @author patapouf
 * 
 */
public interface EncodingProgressObserver {

	/**
	 * This method is called when progress information has change.
	 * 
	 * @param job
	 *            the encoding job that progress information has changed
	 */
	void progressHasChanged(EncodingJob job);

}
