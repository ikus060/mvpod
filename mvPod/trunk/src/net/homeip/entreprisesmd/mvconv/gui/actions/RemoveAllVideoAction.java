package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.core.video.VideoListObserver;

import org.eclipse.jface.action.Action;

/**
 * This Action are use to remove all video from the video list.
 * 
 * @author patapouf
 * 
 */
public class RemoveAllVideoAction extends Action {

	/**
	 * The video list.
	 */
	private VideoList videoList;

	/**
	 * Observer on the video list.
	 */
	private VideoListObserver observer = new VideoListObserver() {
		public void listHasChanged(VideoList list) {
			RemoveAllVideoAction.this.listHasChanged();
		}
	};

	/**
	 * Create a new remove all video action.
	 * 
	 * @param list
	 *            the video list.
	 */
	public RemoveAllVideoAction(VideoList list) {
		super(Localization.getString(Localization.ACTION_REMOVEALL));
		this.videoList = list;

		videoList.addInputVideoListObserver(observer);

		listHasChanged();
	}

	/**
	 * Update the status of this action depending if the list are empty.
	 */
	private void listHasChanged() {
		boolean enabled = false;
		if (videoList.getCount() > 0) {
			enabled = true;
		}
		this.setEnabled(enabled);
	}

	/**
	 * This implementation remove all video from the video list.
	 */
	public void run() {
		videoList.removeAllVideo();
	}

}
