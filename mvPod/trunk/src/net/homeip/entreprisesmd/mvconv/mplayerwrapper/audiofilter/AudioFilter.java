package net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class represent an audio filter that will be apply when encoding.
 * 
 * @author patapouf
 * 
 */
public interface AudioFilter {

	/**
	 * Return an argument list that will be add to mencoder arguments when
	 * executing the encoding. Sub-class may overload this function to add it's
	 * own arguments.
	 * 
	 * @param inputVideoInfo
	 *            detail information about input video
	 * @return the argument list
	 */
	String[] toCommandList(VideoInfo inputVideoInfo);

}
