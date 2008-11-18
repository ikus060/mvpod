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
 * This muxer are use to generate mp4 file using MP4Box utility.
 * 
 * @author patapouf
 * 
 */
public class MP4BoxMuxer implements Muxer {

	private static final String OS_NAME = System.getProperty("os.name");
	/**
	 * MP4Box component name.
	 */
	private static final String MP4BOX_COMPONENT_NAME = "MP4Box"; //$NON-NLS-1$

	/**
	 * MP4Box file name for windows.
	 */
	private static final String MP4BOX_BIN_WINDOWS = "MP4Box.exe"; //$NON-NLS-1$
	/**
	 * MP4Box file name for other OS.
	 */
	private static final String MP4BOX_BIN_OTHER = "MP4Box"; //$NON-NLS-1$

	/**
	 * Define the MP$Box application file name.
	 */
	public static final String MP4BOX_BIN = OS_NAME.indexOf("Windows") >= 0 ? MP4BOX_BIN_WINDOWS //$NON-NLS-1$
			: MP4BOX_BIN_OTHER;

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
	public MP4BoxMuxer() {
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
	public MP4BoxMuxer(File[] paths) {
		this.paths = paths;
	}

	/**
	 * Find the MP4Box utility.
	 */
	private String findMP4Box() throws ComponentMissingException {

		File mp4boxFile = ApplicationFinder.getApplicationPath(MP4BOX_BIN,
				this.paths);
		if (mp4boxFile == null) {
			throw new ComponentMissingException(MP4BOX_COMPONENT_NAME);
		}

		try {
			return mp4boxFile.getCanonicalPath() + File.separator + MP4BOX_BIN;
		} catch (IOException e) {
			return mp4boxFile.getAbsolutePath() + File.separator + MP4BOX_BIN;
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
			String mp4box = findMP4Box();

			return new MP4EncodingJob(mp4box, wrapper, encodingJob, outputFile);
		} catch (FileNotFoundException e) {
			throw new ComponentMissingException(MP4BOX_COMPONENT_NAME);
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
