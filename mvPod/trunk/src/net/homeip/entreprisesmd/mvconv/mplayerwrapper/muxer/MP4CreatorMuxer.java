package net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.ApplicationFinder;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.ComponentMissingException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoDemuxer;

/**
 * This muxer are use to generate mp4 file using mp4creator utility.
 * 
 * @author patapouf
 * 
 */
public class MP4CreatorMuxer implements Muxer {

	/**
	 * mp4creator component name.
	 */
	private static final String MP4CREATOR_COMPONENT_NAME = "mp4creator"; //$NON-NLS-1$

	/**
	 * mp4creator component name.
	 */
	private static final String AVI2RAW_COMPONENT_NAME = "avi2raw"; //$NON-NLS-1$
	
	/**
	 * Define the mp4creator application file name.
	 */
	public static final String MP4CREATOR_BIN = "mp4creator"; //$NON-NLS-1$
	
	/**
	 * Define the avi2raw application file name.
	 */	
	public static final String AVI3RAW_BIN = "avi2raw"; //$NON-NLS-1$

	/**
	 * List of path to search MP4Box.
	 */
	private File[] paths;

	/**
	 * Create a new Wrapper class. Search mplayer in default directory.
	 * 
	 * @throws FileNotFoundException
	 *             when MP4Box application are not found in default location.
	 */
	public MP4CreatorMuxer() {
		this(new File[0]);
	}

	/**
	 * Create a new Wrapper class and search for mplayer in specified directory.
	 * 
	 * @param paths
	 *            list of directory to search for mplayer.
	 * @throws FileNotFoundException
	 *             when MP4Box application are not found in the given paths
	 *             list.
	 */
	public MP4CreatorMuxer(File[] paths) {
		this.paths = paths;
	}

	/**
	 * Find the mp4creator utility.
	 */
	private String findMP4Creator() throws ComponentMissingException {

		File mp4creatorFile = ApplicationFinder.getApplicationPath(MP4CREATOR_BIN,
				this.paths);
		if (mp4creatorFile == null) {
			throw new ComponentMissingException(MP4CREATOR_COMPONENT_NAME);
		}

		try {
			return mp4creatorFile.getCanonicalPath() + File.separator + MP4CREATOR_BIN;
		} catch (IOException e) {
			return mp4creatorFile.getAbsolutePath() + File.separator + MP4CREATOR_BIN;
		}
	}
	
	/**
	 * Find the avi2raw utility.
	 */
	private String findAvi3Raw() throws ComponentMissingException {

		File mp4creatorFile = ApplicationFinder.getApplicationPath(AVI3RAW_BIN,
				this.paths);
		if (mp4creatorFile == null) {
			throw new ComponentMissingException(AVI2RAW_COMPONENT_NAME);
		}

		try {
			return mp4creatorFile.getCanonicalPath() + File.separator + AVI3RAW_BIN;
		} catch (IOException e) {
			return mp4creatorFile.getAbsolutePath() + File.separator + AVI3RAW_BIN;
		}
	}

	/**
	 * This implementation return an new encoding job to create MP4 box.
	 * 
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer#getEncodingJob(net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo,
	 *      java.io.File,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions)
	 */
	public EncodingJob getEncodingJob(MPlayerWrapper wrapper,
			InputVideo inputVideo, File outputFile, EncodingOptions options)
			throws MPlayerException {

		Muxer muxer = new DefaultMuxer();
		EncodingJob encodingJob = muxer.getEncodingJob(wrapper, inputVideo,
				outputFile, options);

		try {
			String mp4creator = findMP4Creator();
			String avi2raw = findAvi3Raw();

			return new MP4CreatorEncodingJob(mp4creator, avi2raw, wrapper, encodingJob, outputFile);
		} catch (FileNotFoundException e) {
			throw new ComponentMissingException(MP4CREATOR_COMPONENT_NAME);
		}

	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer#getPlayingJob(net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingOptions)
	 */
	public PlayingJob getPlayingJob(MPlayerWrapper wrapper,
			InputVideo inputVideo, PlayingOptions options)
			throws MPlayerException {
		Muxer muxer = new DefaultMuxer();
		muxer.getPlayingJob(wrapper, inputVideo, options);
		return null;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer#getVideoDemuxer()
	 */
	public VideoDemuxer getVideoDemuxer() {
		return VideoDemuxer.MUXER_MPG4;
	}

}
