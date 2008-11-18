package net.homeip.entreprisesmd.mvconv.core.video;

/**
 * Class that implement this interface can be notify of any event that occur
 * withing an InputVideoList object.
 * 
 * @author patapouf
 * 
 */
public interface VideoListObserver {

	/**
	 * Fired by the InputVideoList object when the list content has changed.
	 * 
	 * @param list
	 *            the list that changed
	 */
	void listHasChanged(VideoList list);

}
