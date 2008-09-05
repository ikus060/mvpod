package net.homeip.entreprisesmd.mvconv.core.video;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is intended to maintain a list of "active" input video that will
 * be converted.
 * 
 * @author patapouf
 * 
 */
public class VideoList {
	
	/**
	 * List of video.
	 */
	private List<Video> videos = new LinkedList<Video>();
	/**
	 * List of observer.
	 */
	private List<VideoListObserver> observerList = new LinkedList<VideoListObserver>();

	/**
	 * Add a new InputVideo to the list.
	 * 
	 * @param video
	 *            the video to add.
	 */
	public void addInputVideo(Video video) {
		if (video == null) {
			throw new NullPointerException();
		}

		videos.add(video);
		notifyObservers();
	}

	/**
	 * Add the given observer to the collections of observers that will be
	 * notify when the Job queue change.
	 * 
	 * @param observer
	 *            the observer to add.
	 */
	public void addInputVideoListObserver(VideoListObserver observer) {
		observerList.add(observer);
	}

	/**
	 * Return the number of video in the list.
	 * 
	 * @return the number of video in the list.
	 */
	public int getCount() {
		return videos.size();
	}

	/**
	 * Return the input video at the given index.
	 * 
	 * @param index
	 *            the index of the input video.
	 * @return the input video.
	 */
	public Video getVideo(int index) {
		return videos.get(index);
	}

	/**
	 * Return an array of input video.
	 * 
	 * @return an array of input video.
	 */
	public Video[] getVideos() {
		Video[] inputs = new Video[0];
		return videos.toArray(inputs);
	}

	/**
	 * Use to notify observer that this object has changed.
	 */
	private void notifyObservers() {
		VideoListObserver[] observers = new VideoListObserver[observerList.size()];
		observers = observerList.toArray(observers);
		for(int index=0;index<observers.length;index++) {
			observers[index].listHasChanged(this);
		}
	}

	/**
	 * Remove all video from this list.
	 */
	public void removeAllVideo() {
		videos.clear();
		notifyObservers();
	}

	/**
	 * Remove an InputVideo from the list.
	 * 
	 * @param video
	 *            the video to remove.
	 */
	public void removeVideo(Video video) {
		videos.remove(video);
		notifyObservers();
	}

	/**
	 * Remove the given observer from the collections of observers.
	 * 
	 * @param observer
	 *            the observer to add.
	 */
	public void removeInputVideoListObserver(VideoListObserver observer) {
		observerList.remove(observer);
	}

}
