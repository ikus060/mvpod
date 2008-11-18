package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class represent a pulup filter that will be apply when encoding video.
 * 
 * @author patapouf
 * 
 */
public class PullupFilter implements VideoFilter {

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#getPriority()
	 */
	public int getPriority() {
		return -1;
	}
	
	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#toCommandList()
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {

		String[] args = new String[2];
		args[0] = "-vf-add"; //$NON-NLS-1$
		args[1] = "pullup,softskip"; //$NON-NLS-1$
		return args;

	}

}
