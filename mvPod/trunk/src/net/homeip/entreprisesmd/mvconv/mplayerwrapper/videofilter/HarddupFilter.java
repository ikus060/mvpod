package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class represent a harddup filter that will be apply when encoding the
 * video. This filter will force duplicate frames to be encoded in the output
 * video. This uses slightly more space, but is necessary for output to MPEG
 * files or if you plan to demux and remux the video stream after encoding.
 * Should be placed at or near the end of the filter chain unless you have a
 * good reason to do otherwise.
 * 
 * @author patapouf
 * 
 */
public class HarddupFilter implements VideoFilter {

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#getPriority()
	 */
	public int getPriority() {
		return 100;
	}
	
	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#toCommandList()
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {

		String[] args = new String[2];
		args[0] = "-vf-add"; //$NON-NLS-1$
		args[1] = "harddup"; //$NON-NLS-1$
		return args;

	}

}
