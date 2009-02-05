package net.homeip.entreprisesmd.mvconv.core.profile;

import java.io.File;

import org.eclipse.jface.preference.IPreferenceStore;

import net.homeip.entreprisesmd.mvconv.gui.Main;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoDemuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.MP4BoxMuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.MP4CreatorMuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer;

public class MP4Muxer implements Muxer {

	Muxer muxer;

	/**
	 * Key value for OS name property.
	 */
	private static final String OS_NAME = System.getProperty("os.name");//$NON-NLS-1$

	/**
	 * Linux OS value.
	 */
	private static final String OS_NAME_LINUX = "Linux"; //$NON-NLS-1$
	/**
	 * Windows OS value.
	 */
	private static final String OS_NAME_WINDOWS = "Windows"; //$NON-NLS-1$

	/**
	 * Constructor of the MP4Muxer. It's creating the right Muxer depending of
	 * the platform and the user preferences.
	 */
	public MP4Muxer() {

		if (OS_NAME.contains(OS_NAME_LINUX)) {
			IPreferenceStore store = Main.instance().getPreferenceStore();
			File path = new File(store
					.getString(Main.PREF_MP4CREATOR_DIRECTORY));
			this.muxer = new MP4CreatorMuxer(new File[] { path });
		} else if (OS_NAME.contains(OS_NAME_WINDOWS)) {
			IPreferenceStore store = Main.instance().getPreferenceStore();
			File path = new File(store.getString(Main.PREF_MP4BOX_DIRECTORY));
			this.muxer = new MP4BoxMuxer(new File[] { path });
		} else {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer#getEncodingJob(net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo,
	 *      java.io.File,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions)
	 */
	public EncodingJob getEncodingJob(MPlayerWrapper wrapper,
			InputVideo inputVideo, File outputFile, EncodingOptions options)
			throws MPlayerException {
		return this.muxer.getEncodingJob(wrapper, inputVideo, outputFile,
				options);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer#getPlayingJob(net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingOptions)
	 */
	public PlayingJob getPlayingJob(MPlayerWrapper wrapper,
			InputVideo inputVideo, PlayingOptions options)
			throws MPlayerException {
		return this.muxer.getPlayingJob(wrapper, inputVideo, options);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer#getVideoDemuxer()
	 */
	public VideoDemuxer getVideoDemuxer() {
		return this.muxer.getVideoDemuxer();
	}

}
