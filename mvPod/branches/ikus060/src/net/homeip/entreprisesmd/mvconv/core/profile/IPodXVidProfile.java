package net.homeip.entreprisesmd.mvconv.core.profile;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.FAACEncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.HarddupFilter;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.XVideoEncodingOptions;

/**
 * This profile are intended to produce video file that are compatible with iPod
 * G5 and any other similare product.
 * 
 * @author patapouf
 * 
 */
public class IPodXVidProfile extends AbstractHardCodedProfile {

	/**
	 * Default audio bitrate in Kbps. (128 Kbps)
	 */
	private static final int AUDIO_BITRATE_DEFAULT = 128;
	/**
	 * Maximum audio bitrate in Kbps. (160 Kbps)
	 */
	private static final int AUDIO_BITRATE_MAX = 160;
	/**
	 * Default audio sample rate. (48000 Hz)
	 */
	private static final int AUDIO_SAMPLE_RATE = 48000;
	/**
	 * Default bitrate in Kbps. (500 Kbps)
	 */
	private static final int VIDEO_BITRATE_DEFAULT = 500;
	/**
	 * Maximum local bitrate value in Kbps. (1500 Kbps)
	 */
	private static final int VIDEO_BITRATE_MAX = 2500;

	/**
	 * 320 x 240 dimension.
	 */
	private static final VideoScalingOptions VIDEO_DIMENSION_320X240 = new VideoScalingOptions(
			320, 240);

	/**
	 * 480 x 320 dimension.
	 */
	private static final VideoScalingOptions VIDEO_DIMENSION_480X320 = new VideoScalingOptions(
			480, 320);

	/**
	 * 640 x 480 dimension.
	 */
	private static final VideoScalingOptions VIDEO_DIMENSION_640X480 = new VideoScalingOptions(
			640, 480);
	/**
	 * List of supported dimensions.
	 */
	private static final VideoScalingOptions[] VIDEO_DIMENSIONS = new VideoScalingOptions[] {
			VIDEO_DIMENSION_320X240, VIDEO_DIMENSION_480X320,
			VIDEO_DIMENSION_640X480 };
	/**
	 * Maximum video frame rate.
	 */
	private static final double VIDEO_FRAMERATE_MAX = 30.0;

	/**
	 * Create a new ipod profile.
	 */
	public IPodXVidProfile() {
		super(AUDIO_BITRATE_MAX, VIDEO_BITRATE_MAX, VIDEO_DIMENSIONS,
				AUDIO_BITRATE_DEFAULT, VIDEO_BITRATE_DEFAULT,
				VIDEO_DIMENSION_640X480);
	}

	/**
	 * 
	 * Return the default encoding options for this profile.
	 * <p>
	 * This implementation create a new encoding options with default value to
	 * create a video file that will playback correctly on iPod G5 and any other
	 * compatible product.
	 * </p>
	 * 
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.Profile#getEncodingOptions()
	 */
	public EncodingOptions getEncodingOptions() {

		FAACEncodingOptions audioOptions = new FAACEncodingOptions(
				getAudioBitrate());
		audioOptions.setMPEGVersion(FAACEncodingOptions.MPEG_VERSION_4);
		audioOptions.setObjectType(FAACEncodingOptions.OBJECT_TYPE_LOW);
		audioOptions.setOutputSampleRate(AUDIO_SAMPLE_RATE);

		XVideoEncodingOptions videoOptions = new XVideoEncodingOptions(
				getVideoBitrate());

		videoOptions.setMaxOutputFrameRate(VIDEO_FRAMERATE_MAX);
		videoOptions.enableTrellis(true);
		videoOptions.setPass(twoPassEnabled() ? 2 : 1);
		videoOptions.setMaxBFrame(0);

		EncodingOptions options = new EncodingOptions(videoOptions,
				audioOptions);

		options.setScaleOptions(getVideoScaling());
		// options.addVideoFilter(new PullupFilter());
		options.addVideoFilter(new HarddupFilter());

		options.setMuxer(new MP4Muxer());

		return options;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.Profile#getName()
	 */
	public String getName() {
		return Localization.getString(Localization.PROFILE_IPOD_XVID);
	}

}
