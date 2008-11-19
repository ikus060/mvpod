package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter;

public abstract class DeinterlaceFilter implements VideoFilter {

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#getPriority()
	 */
	public int getPriority() {
		return -100;
	}

}
