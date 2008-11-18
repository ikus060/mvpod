package net.homeip.entreprisesmd.mvconv.gui;

import net.homeip.entreprisesmd.mvconv.core.video.Video;

/**
 * This class represent a selected video in the video list.
 * 
 * @author patapouf
 * 
 */
public class VideoSelection {

	/**
	 * The selected video.
	 */
	private Video selectedVideo;

	/**
	 * Create a new Video selection.
	 * 
	 * @param video
	 *            the selected video.
	 */
	public VideoSelection(Video video) {
		this.selectedVideo = video;
	}

	/**
	 * Return the selected video.
	 * 
	 * @return the video.
	 */
	public Video getSelectedVideo() {
		return selectedVideo;
	}

}
