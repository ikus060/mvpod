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
	 * Audio bitrate.
	 */
	private int audioBitrate;
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
			VideoScalingOptions[] supportedVideoScalings, int defaultAudioBitrate,
			int defaultVideoBitrate, VideoScalingOptions defaultScaling) {

		if (maxAudioBitrate < 0) {
			throw new IllegalArgumentException(
					"Invalid maximum audio bitrate value " + maxAudioBitrate);
		}
		if (maxVideoBitrate < 0) {
			throw new IllegalArgumentException(
					"Invalid maximum video bitrate value " + maxVideoBitrate);
		}
		if (supportedVideoScalings == null) {
			throw new NullPointerException();
		}
		if (supportedVideoScalings.length <= 0) {
			throw new IllegalArgumentException("Empty supported dimention list");
		}
		if (audioBitrate < 0 || audioBitrate > maxAudioBitrate) {
			throw new IllegalArgumentException(
					"Invalid default audio bitrate value " + audioBitrate);
		}
		if (videoBitrate < 0 || videoBitrate > maxVideoBitrate) {
			throw new IllegalArgumentException(
					"Invalid default video bitrate value " + videoBitrate);
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
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getAudioBitrate()
	 */
	public int getAudioBitrate() {
		return audioBitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getMaximumAudioBitrate()
	 */
	public int getMaximumAudioBitrate() {
		return maxAudioBitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getMaximumVideoBitrate()
	 */
	public int getMaximumVideoBitrate() {
		return maxVideoBitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getSupportedVideoDimensions()
	 */
	public VideoScalingOptions[] getSupportedVideoScalings() {
		return supportedVideoScalings.clone();
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getVideoBitrate()
	 */
	public int getVideoBitrate() {
		return videoBitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#getVideoScaling()
	 */
	public VideoScalingOptions getVideoScaling() {
		return videoScaling;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#setAudioBitrate(int)
	 */
	public void setAudioBitrate(int bitrate) {
		if (bitrate <= 0 || bitrate > maxAudioBitrate) {
			throw new IllegalArgumentException("Invalid audio bitrate value "
					+ bitrate);
		}
		this.audioBitrate = bitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile#setVideoBitrate(int)
	 */
	public void setVideoBitrate(int bitrate) {
		if (bitrate <= 0 || bitrate > maxVideoBitrate) {
			throw new IllegalArgumentException("Invalid video bitrate value "
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
		while (index < supportedVideoScalings.length
				&& !supportedVideoScalings[index].equals(dimension)) {
			index++;
		}
		if (index >= supportedVideoScalings.length) {
			throw new IllegalArgumentException(
					"Invalid video scaling options, given options are not supported");
		}

		this.videoScaling = dimension;

	}

}
