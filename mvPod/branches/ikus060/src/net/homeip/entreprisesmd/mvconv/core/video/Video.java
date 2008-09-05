package net.homeip.entreprisesmd.mvconv.core.video;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;

/**
 * Represent an input video and the output file.
 * 
 * @author patapouf
 * 
 */
public class Video {

	/**
	 * the input video.
	 */
	private InputVideo inputVideo;

	/**
	 * the output video file.
	 */
	private File outputFile;

	/**
	 * Observer list.
	 */
	private List<VideoObserver> observerList = new LinkedList<VideoObserver>();

	/**
	 * Create a new Input/Output object.
	 * 
	 * @param inputVideo
	 *            the input video.
	 * @param outputFile
	 *            the output video file.
	 */
	public Video(InputVideo inputVideo, File outputFile) {
		if (inputVideo == null || outputFile == null) {
			throw new NullPointerException();
		}
		this.inputVideo = inputVideo;
		this.outputFile = outputFile;
	}

	/**
	 * Add the given observer to the collections of observers that will be
	 * notify when the Job queue change.
	 * 
	 * @param observer
	 *            the observer to add
	 */
	public void addVideoObserver(VideoObserver observer) {
		observerList.add(observer);
	}

	/**
	 * Return the input video object.
	 * 
	 * @return the input video object.
	 */
	public InputVideo getInputVideo() {
		return inputVideo;
	}

	/**
	 * Return the output file.
	 * 
	 * @return the output file.
	 */
	public File getOutputFile() {
		return outputFile;
	}

	/**
	 * Use to notify observer that this object has changed.
	 */
	private void notifyObservers() {
		VideoObserver[] observers = new VideoObserver[observerList.size()];
		observers = observerList.toArray(observers);
		for(int index=0;index<observers.length;index++) {
			observers[index].videoHasChanged(this);
		}
	}

	/**
	 * Remove the given observer from the collections of observers.
	 * 
	 * @param observer
	 *            the observer to add.
	 */
	public void removeVideoObserver(VideoObserver observer) {
		observerList.remove(observer);
	}

	/**
	 * Set the input video.
	 * 
	 * @param inputVideo
	 *            the new input video.
	 */
	public void setInputVideo(InputVideo inputVideo) {
		if (inputVideo == null) {
			throw new NullPointerException();
		}
		if (!inputVideo.getClass().equals(inputVideo.getClass())) {
			throw new IllegalArgumentException("InputVideo of difference type"); //$NON-NLS-1$
		}
		this.inputVideo = inputVideo;
		notifyObservers();
	}

	/**
	 * Set the output video file.
	 * 
	 * @param file
	 *            the new output video file.
	 */
	public void setOutputFile(File file) {
		if (file == null) {
			throw new NullPointerException();
		}
		this.outputFile = file;
		notifyObservers();
	}

}
