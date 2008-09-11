package net.homeip.entreprisesmd.mvconv.core.profile;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions;

/**
 * Class that implement this interface provide an encoding options with default
 * value to create a video file that will playback correctly on a specific
 * device. compatible product.
 * 
 * @author patapouf
 * 
 */
public interface HardCodedProfile extends Profile {

	/**
	 * Return the current video bitrate.
	 * 
	 * @param return the video bitrate.
	 */
	int getAudioBitrate();

	/**
	 * Return the maximum audio bitrate in Kbps supported by the device.
	 * 
	 * @return the maximum audio bitrate in Kbps.
	 */
	int getMaximumAudioBitrate();

	/**
	 * Return the maximum video bitrate in Kbps supported by the device.
	 * 
	 * @return the maximum video bitrate in Kbps.
	 */
	int getMaximumVideoBitrate();

	/**
	 * Return a list of supported video dimension.
	 * 
	 * @return list of video dimension.
	 */
	VideoScalingOptions[] getSupportedVideoScalings();

	/**
	 * Return the current video bitrate.
	 * 
	 * @return the video bitrate.
	 */
	int getVideoBitrate();

	/**
	 * Return the current selected video scaling options of this profile.
	 * 
	 * @returmn the video scaling options.
	 */
	VideoScalingOptions getVideoScaling();

	/**
	 * Sets the audio bitrate of this profile.
	 * 
	 * @param bitrate
	 *            the audio bitrate.
	 */
	void setAudioBitrate(int bitrate);

	/**
	 * Return true if the two pass mode are enabled.
	 */
	public boolean twoPassEnabled();

	/**
	 * Use to enable the two pass mode.
	 * 
	 * @param enable
	 *            True to enable the two pass mode.
	 */
	public void enableTwoPass(boolean enable);

	/**
	 * Sets the audio bitrate of this profile.
	 * 
	 * @param bitrate
	 *            the audio bitrate.
	 */
	void setVideoBitrate(int bitrate);

	/**
	 * Sets the audio bitrate of this profile.
	 * 
	 * @param bitrate
	 *            the audio bitrate.
	 */
	void setVideoScaling(VideoScalingOptions dimension);

}
