package net.homeip.entreprisesmd.mvconv.core.profile;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions;

/**
 * This class make easier the implementation of the HardCodedProfile interface.
 * 
 * @author patapouf
 * 
 */
public abstract class AbstractHardCodedProfile implements HardCodedProfile {
	/**
	 * Audio bitrate.
	 */
	private int audioBitrate;
	/**
	 * Maximum audio bitrate.
	 */
	private int maxAudioBitrate;
	/**
	 * Maximum video bitrate.
	 */
	private int maxVideoBitrate;
	/**
	 * List of supported video dimensions.
	 */
	private VideoScalingOptions[] supportedVideoScalings;
	/**
	 * Current pass count;
	 */
	private boolean twoPass = false;
	/**
	 * Video bitrate.
	 */
	private int videoBitrate;

	/**
	 * Video scaling options.
	 */
	private VideoScalingOptions videoScaling;

	/**
	 * Create a new <code>AbstractHardCodedProfile</code> class.
	 * 
	 * @param maxAudioBitrate
	 *            the maximum audio bitrate in Kbps.
	 * @param maxVideoBitrate
	 *            the maximum video bitrate in Kbps.
	 * @param supportedVideoScalings
	 *            list of supported video dimensions.
	 * @param defaultAudioBitrate
	 *            the initial audio bitrate value.
	 * @param defaultVideoBitrate
	 *            the initial video bitrate value.
	 * @param defaultScaling
	 *            the initial dimension.
	 */
	public AbstractHardCodedProfile(int maxAudioBitrate, int maxVideoBitrate,
			VideoScalingOptions[] supportedVideoScalings,
			int defaultAudioBitrate, int defaultVideoBitrate,
			VideoScalingOptions defaultScaling) {

		if (maxAudioBitrate < 0) {
			throw new IllegalArgumentException(
					"Invalid maximum audio bitrate value " + maxAudioBitrate); //$NON-NLS-1$
		}
		if (maxVideoBitrate < 0) {
			throw new IllegalArgumentException(
					"Invalid maximum video bitrate value " + maxVideoBitrate); //$NON-NLS-1$
		}
		if (supportedVideoScalings == null) {
			throw new NullPointerException();
		}
		if (supportedVideoScalings.length <= 0) {
			throw new IllegalArgumentException("Empty supported dimention list"); //$NON-NLS-1$
		}
		if (this.audioBitrate < 0 || this.audioBitrate > maxAudioBitrate) {
			throw new IllegalArgumentException(
					"Invalid default audio bitrate value " + this.audioBitrate); //$NON-NLS-1$
		}
		if (this.videoBitrate < 0 || this.videoBitrate > maxVideoBitrate) {
			throw new IllegalArgumentException(
					"Invalid default video bitrate value " + this.videoBitrate); //$NON-NLS-1$
		}
		if (defaultScaling == null) {
			throw new NullPointerException();
		}

		this.maxAudioBitrate = maxAudioBitrate;
		this.maxVideoBitrate = maxVideoBitrate;
		this.supportedVideoScalings = supportedVideoScalings;
		this.audioBitrate = defaultAudioBitrate;
		this.videoBitrate = defaultVideoBitrate;
		this.videoScaling = defaultScaling;
	}

	/**
	 * This implementation change the two pass mode.
	 */
	public void enableTwoPass(boolean enable) {
		this.twoPass = enable;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getAudioBitrate()
	 */
	public int getAudioBitrate() {
		return this.audioBitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getMaximumAudioBitrate()
	 */
	public int getMaximumAudioBitrate() {
		return this.maxAudioBitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getMaximumVideoBitrate()
	 */
	public int getMaximumVideoBitrate() {
		return this.maxVideoBitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getSupportedVideoDimensions()
	 */
	public VideoScalingOptions[] getSupportedVideoScalings() {
		return this.supportedVideoScalings.clone();
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getVideoBitrate()
	 */
	public int getVideoBitrate() {
		return this.videoBitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getVideoScaling()
	 */
	public VideoScalingOptions getVideoScaling() {
		return this.videoScaling;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#setAudioBitrate(int)
	 */
	public void setAudioBitrate(int bitrate) {
		if (bitrate <= 0 || bitrate > this.maxAudioBitrate) {
			throw new IllegalArgumentException("Invalid audio bitrate value " //$NON-NLS-1$
					+ bitrate);
		}
		this.audioBitrate = bitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#setVideoBitrate(int)
	 */
	public void setVideoBitrate(int bitrate) {
		if (bitrate <= 0 || bitrate > this.maxVideoBitrate) {
			throw new IllegalArgumentException("Invalid video bitrate value " //$NON-NLS-1$
					+ bitrate);
		}
		this.videoBitrate = bitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#setVideoScaling(net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions)
	 */
	public void setVideoScaling(VideoScalingOptions dimension) {
		if (dimension == null) {
			throw new NullPointerException();
		}

		int index = 0;
		while (index < this.supportedVideoScalings.length
				&& !this.supportedVideoScalings[index].equals(dimension)) {
			index++;
		}
		if (index >= this.supportedVideoScalings.length) {
			throw new IllegalArgumentException(
					"Invalid video scaling options, given options are not supported"); //$NON-NLS-1$
		}

		this.videoScaling = dimension;

	}

	/**
	 * This implementation return true if two pass mode are enabled.
	 */
	public boolean twoPassEnabled() {
		return twoPass;
	}

}
