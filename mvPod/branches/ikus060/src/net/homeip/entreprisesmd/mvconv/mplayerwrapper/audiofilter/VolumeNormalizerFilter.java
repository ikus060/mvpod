package net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This filter maximizes the volume without distorting the sound.
 * 
 * @author patapouf
 * 
 */
public class VolumeNormalizerFilter implements AudioFilter {

	/**
	 * Default target value.
	 */
	private static final double DEFAULT_TARGET = 0.25;
	
	/**
	 * This methode use a single sample to smooth the variations via the
	 * standard weighted mean over past samples.
	 */
	public static final int METHODE_SINGLE = 1;

	/**
	 * this methode several samples to smooth the variations via the standard
	 * weighted mean over past samples.
	 */
	public static final int METHODE_MULTIPLE = 2;

	/**
	 * Method to use.
	 */
	private int method;
	/**
	 * The target value.
	 */
	private double target;

	/**
	 * Create a new VolumFilter with default method and target value. (Default
	 * method METHODE_SINGLE and default target 0.25).
	 */
	public VolumeNormalizerFilter() {
		this(METHODE_SINGLE, DEFAULT_TARGET);
	}

	/**
	 * Create a new VolumeFilter with the defined method and target.
	 * 
	 * @param method
	 *            the method
	 * @param target
	 *            the target (value between 0 and 1)
	 */
	public VolumeNormalizerFilter(int method, double target) {
		if (method != METHODE_SINGLE && method != METHODE_MULTIPLE) {
			throw new IllegalArgumentException("Invalid methode");
		}
		if (target <= 0 || target > 1) {
			throw new IllegalArgumentException("Invalid target");
		}

		this.method = method;
		this.target = target;
	}

	/**
	 * Return the used method to maximize the volume.
	 * 
	 * @return the used method
	 */
	public int getMethod() {
		return this.method;
	}

	/**
	 * Return the target amplitude as a fraction of the maximum for the sample
	 * type. Default value is 0.25.
	 * 
	 * @return the target amplitude
	 */
	public double getTarget() {
		return this.target;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiofilter.AudioFilter#toCommandList()
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {
		String[] args = new String[2];
		args[0] = "-af-add";
		args[1] = "volnorm=" + getMethod() + ":" + getTarget();
		return args;
	}

}
