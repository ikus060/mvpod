package net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer;

import java.io.File;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoDemuxer;

/**
 * This interface are use by the encoding process to muxe correctly video and
 * audio stream.
 * 
 * @author patapouf
 * 
 */
public interface Muxer {

	/**
	 * Return the appropriate encoding job for this muxer.
	 * 
	 * @param wrapper
	 *            the mplayer wrapper to use
	 * @param inputVideo
	 *            the input video
	 * @param outputFile
	 *            the ouput file
	 * @param options
	 *            the encoding options
	 * @return the encoding job
	 * @throws MPlayerException
	 *             if any error occur
	 */
	EncodingJob getEncodingJob(MPlayerWrapper wrapper,
			InputVideo inputVideo, File outputFile, EncodingOptions options)
			throws MPlayerException;

	/**
	 * Return the appropriate playing job for this muxer.
	 * 
	 * @param wrapper
	 *            the mplayer wrapper to use
	 * @param inputVideo
	 *            the input video
	 * @param options
	 *            the playing options
	 * @return the playing job
	 * @throws MPlayerException
	 *             if any error occur
	 */
	PlayingJob getPlayingJob(MPlayerWrapper wrapper,
			InputVideo inputVideo, PlayingOptions options)
			throws MPlayerException;
	
	/**
	 * Return the video demuxer associated to this muxer.
	 * @return the video demuxer.
	 */
	VideoDemuxer getVideoDemuxer();
	

}
