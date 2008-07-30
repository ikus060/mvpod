package net.homeip.entreprisesmd.mvconv.core.profile;

import java.io.File;

import org.eclipse.jface.preference.IPreferenceStore;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.gui.Main;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.FAACEncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.MP4BoxMuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.HarddupFilter;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.X264EncodingOptions;

/**
 * This profile are intended to produce video file that are compatible with iPod
 * G5 and any other similare product.
 * 
 * @author patapouf
 * 
 */
public class IPodH264Profile extends AbstractHardCodedProfile {

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
	 * Reference frame value. (2 frames)
	 */
	private static final int REFERENCE_FRAME = 1;
	/**
	 * Subpel rafinement value. (6 frames)
	 */
	private static final int SUBPEL_RAFINEMENT = 6;
	/**
	 * Buffer size in kbits for maximum local bitrate. (244 kbits)
	 */
	private static final int VIDEO_BITRATE_BUFFER_SIZE = 244;
	/**
	 * Default bitrate in Kbps. (500 Kbps)
	 */
	private static final int VIDEO_BITRATE_DEFAULT = 500;
	/**
	 * Maximum local bitrate value in Kbps. (1500 Kbps)
	 */
	private static final int VIDEO_BITRATE_MAX = 1500;

	/**
	 * 320 x 240 dimension.
	 */
	private static final VideoScalingOptions VIDEO_DIMENSION_320X240 = new VideoScalingOptions(
			320, 240, VideoScalingOptions.MULTIPLE_16 | VideoScalingOptions.METHOD_FIT);
	/**
	 * 640 x 480 dimension.
	 */
	private static final VideoScalingOptions VIDEO_DIMENSION_640X480 = new VideoScalingOptions(
			640, 480, VideoScalingOptions.MULTIPLE_16 | VideoScalingOptions.METHOD_FIT);
	/**
	 * List of supported dimensions.
	 */
	private static final VideoScalingOptions[] VIDEO_DIMENSIONS = new VideoScalingOptions[] {
			VIDEO_DIMENSION_320X240, VIDEO_DIMENSION_640X480 };
	/**
	 * Maximum video frame rate.
	 */
	private static final double VIDEO_FRAMERATE_MAX = 30.0;

	/**
	 * Create a new ipod profile.
	 */
	public IPodH264Profile() {
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

		X264EncodingOptions videoOptions = new X264EncodingOptions(
				getVideoBitrate());

		videoOptions.setMaximumLocalBitrate(768, VIDEO_BITRATE_BUFFER_SIZE);
		videoOptions.setMaxOutputFrameRate(VIDEO_FRAMERATE_MAX);
		videoOptions.setBitStreamLevel(X264EncodingOptions.LEVEL_13);
		videoOptions.setReferenceFrame(REFERENCE_FRAME);
		videoOptions.setSubpelRefinement(SUBPEL_RAFINEMENT);
		videoOptions.setMaxBFrame(0);

		videoOptions.enableCabac(false);
		videoOptions.enablePartition(true);
		videoOptions.enableThreads(true);
		videoOptions.enableTrellis(true);
		videoOptions
				.setMotionEstimation(X264EncodingOptions.MOTION_ESTIMATION_UNEVEN);
		videoOptions
				.setMotionPrediction(X264EncodingOptions.MOTION_PREDICTION_AUTO);
		videoOptions.setPass(2);

		EncodingOptions options = new EncodingOptions(videoOptions,
				audioOptions);

		options.setScaleOptions(getVideoScaling());
		//options.addVideoFilter(new PullupFilter());
		options.addVideoFilter(new HarddupFilter());

		IPreferenceStore store = Main.instance().getPreferenceStore();
		File path = new File(store.getString(Main.PREF_MP4BOX_DIRECTORY));
		options.setMuxer(new MP4BoxMuxer(new File[]{path}));

		return options;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.Profile#getName()
	 */
	public String getName() {
		return Localization.getString(Localization.PROFILE_IPOD_H264);
	}

}
