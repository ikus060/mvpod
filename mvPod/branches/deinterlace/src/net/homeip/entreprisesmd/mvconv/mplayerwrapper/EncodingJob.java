package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * A EncodingJob is a specific encoding task that can be execute as part of a
 * thread by calling start() methode. This class intent to seperate every
 * encoding process in a seperate instance of a class.
 * 
 * @author patapouf
 * 
 */
public interface EncodingJob {

	/**
	 * Add an observer to this Job.
	 * 
	 * @param observer
	 *            the observer to add
	 */
	void addProgressObserver(EncodingProgressObserver observer);

	/**
	 * Cancel the job.
	 */
	void cancel();

	/**
	 * Return the average frame rate.
	 * 
	 * @return the frame rate
	 */
	double getFrameRate();

	/**
	 * Return the length.
	 * 
	 * @return the length value
	 */
	int getLength();

	/**
	 * Return the percentage of task that are completed.
	 * 
	 * @return the percent completed
	 */
	int getPercentCompleted();

	/**
	 * Return the average number of second remaining to complete this task.
	 * 
	 * @return the average number of second remaining
	 */
	int getTimeRemaining();

	/**
	 * Start the execution of the encoding process. This methode exit when the
	 * encoding process are finish.
	 * 
	 * @throws MPlayerException
	 *             is any error occure
	 */
	void start() throws MPlayerException;

	/**
	 * Remove the observer from this job.
	 * 
	 * @param observer
	 *            the observer to remove
	 */
	void removeProgressObserver(EncodingProgressObserver observer);

}
