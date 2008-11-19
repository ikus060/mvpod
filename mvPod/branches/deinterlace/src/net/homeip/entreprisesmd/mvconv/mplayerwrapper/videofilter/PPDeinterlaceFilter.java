package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This deinterlace filter use 'pp' video filter to apply a deinterlacing.
 * Deinterlace at original frame rate.
 * 
 * @author patapouf
 * 
 */
public class PPDeinterlaceFilter extends DeinterlaceFilter {

	/**
	 * Filter to use.
	 */
	String filter;

	/**
	 * Cubic interpolating deinterlacing filter deinterlaces the given block by
	 * cubically interpolating every second line.
	 */
	public static final String FILTER_CUBIC_INTERPOLATING_DETINTERLACING = "cubicipoldeint"; //$NON-NLS-1$

	/**
	 * FFmpeg deinterlacing filter that deinterlaces the given block by
	 * filtering every second line with a (−1 4 2 4 −1) filter.
	 */
	public static final  String FILTER_FFMPEG_DEINTERLACING = "ffmpegdeint"; //$NON-NLS-1$

	/**
	 * Vertically applied FIR lowpass deinterlacing filter that deinterlaces the
	 * given block by filtering all lines with a (−1 2 6 2 −1) filter.
	 */
	public static final  String FILTER_FIR_LOWPASS_DEINTERLACING = "lowpass5"; //$NON-NLS-1$

	/**
	 * Linear blend deinterlacing filter that deinterlaces the given block by
	 * filtering all lines with a (1 2 1) filter.
	 */
	public static final  String FILTER_LINEAR_BLEND_DEINTERLACING = "linblenddeint"; //$NON-NLS-1$

	/**
	 * Linear interpolating deinterlacing filter that deinterlaces the given
	 * block by linearly interpolating every second line.
	 */
	public static final  String FILTER_LINEAR_INTERPOLATING_DEINTERLACING = "linipoldeint"; //$NON-NLS-1$

	/**
	 * Median deinterlacing filter that deinterlaces the given block by applying
	 * a median filter to every second line.
	 */
	public static final  String FILTER_MEDIAN_DEINTERLACING = "mediandeint"; //$NON-NLS-1$

	/**
	 * Create a new deinterlacing filter using the given method
	 * 
	 * @param filter
	 */
	public PPDeinterlaceFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof PPDeinterlaceFilter) {
			return ((PPDeinterlaceFilter) obj).filter.equals(this.filter);
		}
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.filter.hashCode();
	}

	public String[] toCommandList(VideoInfo inputVideoInfo) {

		String[] args = new String[2];
		args[0] = "-vf-add"; //$NON-NLS-1$
		args[1] = "pp=" + this.filter; //$NON-NLS-1$
		return args;

	}

}
