package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * This interface offer all functionnality to handle the playing job of a video.
 * 
 * @author patapouf
 * 
 */
public interface PlayingJob {

	/**
	 * Cancel the playing job.
	 */
	void cancel();

	/**
	 * Start the execution of the encoding process. This methode exit when the
	 * encoding process are finish.
	 * 
	 * @throws MPlayerException
	 *             is any error occure
	 */
	void start() throws MPlayerException;

}
