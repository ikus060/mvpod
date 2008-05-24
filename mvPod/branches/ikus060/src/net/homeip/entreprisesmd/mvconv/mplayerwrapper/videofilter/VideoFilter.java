package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class represent a video filter that will be apply when encoding.
 * 
 * @author patapouf
 * 
 */
public interface VideoFilter {

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

	/**
	 * Return the priority value of this filter. If the filter need to be
	 * executed before every other, the value return by this method must be
	 * under 0. Ifthe filter need to be executed at the very end, the value
	 * return by this function must be larger than 0.
	 * 
	 * <p>
	 * User filter must return 0.
	 * </p>
	 * 
	 * @return the priority value.
	 */
	int getPriority();
}
