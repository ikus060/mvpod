package net.homeip.entreprisesmd.mvconv.core.video;

/**
 * Class that implement this interface can be notify when the video as changed.
 * 
 * @author patapouf
 * 
 */
public interface VideoObserver {
	/**
	 * Call when the video as changed.
	 * 
	 * @param video
	 *            the video htat changed.
	 */
	void videoHasChanged(Video video);

}
