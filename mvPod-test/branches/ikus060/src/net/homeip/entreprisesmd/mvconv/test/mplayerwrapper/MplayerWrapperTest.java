package net.homeip.entreprisesmd.mvconv.test.mplayerwrapper;

import java.io.File;
import java.io.FileNotFoundException;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.*;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.*;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.MP4BoxMuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.*;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.*;

import junit.framework.TestCase;

/**
 * This class test the video encoding functionnality by encoding several input
 * video file in defferent format to a specific video format that can be read on
 * portable device like the iPod.
 * 
 * @author patapouf
 * 
 */
public class MplayerWrapperTest extends TestCase {

	/**
	 * An AVI video file.
	 */
	public static final String VIDEO_FILE1 = "testing1.avi";
	public static final String VIDEO_FILE2 = "testing2.wmv";
	public static final String VIDEO_FILE3 = "testing3.mov";
	public static final String VIDEO_FILE4 = "testing4.wmv";

	public static final String SMALL_VIDEO_FILE1 = "testing1.small.avi";
	public static final String SMALL_VIDEO_FILE2 = "testing2.small.wmv";
	public static final String SMALL_VIDEO_FILE3 = "testing3.small.mov";
	public static final String SMALL_VIDEO_FILE4 = "testing4.small.wmv";

	public static final String OUTPUT_DIRECTORY = "../Video/Output";
	public static final String INPUT_DIRECTORY = "../Video/Input";

	/**
	 * This test generate a video for H300.
	 */
	public void testH300Encode() {
		File videoFile = new File(VIDEO_FILE1);
		if (!videoFile.exists()) {
			TestCase.fail(videoFile.getAbsolutePath() + " doesn't exist !");
		}

		File output = new File("Output.H300." + VIDEO_FILE1);
		if (output.exists()) {
			output.delete();
		}

		LameEncodingOptions audioOptions = new LameEncodingOptions(128,
				LameEncodingOptions.TYPE_CONSTANT_BITRATE);
		audioOptions.setChannelMode(LameEncodingOptions.MODE_JOIN_STEREO);
		audioOptions.setOutputSampleRate(44100);

		XVideoEncodingOptions videoOptions = new XVideoEncodingOptions(500);
		videoOptions.setMaxBFrame(0);
		videoOptions.setOutputFrameRate(10);

		EncodingOptions options = new EncodingOptions(videoOptions,
				audioOptions);
		options.addVideoFilter(new InverseTelecineFilter(10));
		options.addVideoFilter(new HarddupFilter());

		options.setScaleOptions(new VideoScalingOptions(220, 176));

		encode(videoFile, output, options);
	}

	/**
	 * This test generate a high resolution video for IPod using the h264 video
	 * codec.
	 */
	public void testIPodHigh() {

		FAACEncodingOptions audioOptions = new FAACEncodingOptions(128);
		audioOptions.setMPEGVersion(FAACEncodingOptions.MPEG_VERSION_4);
		audioOptions.setObjectType(FAACEncodingOptions.OBJECT_TYPE_LOW);
		audioOptions.setOutputSampleRate(48000);

		X264EncodingOptions videoOptions = new X264EncodingOptions(1500);

		videoOptions.setMaximumLocalBitrate(1500, 244);
		videoOptions.setOutputFrameRate(30.0);
		videoOptions.setBitStreamLevel(X264EncodingOptions.LEVEL_30);
		videoOptions.setReferenceFrame(2);
		videoOptions.setSubpelRefinement(6);

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
		options.addVideoFilter(new PullupFilter());
		options.addVideoFilter(new HarddupFilter());

		options.setScaleOptions(new VideoScalingOptions(640, 480));

		options.setMuxer(new MP4BoxMuxer());

		encode("ipod.high.mp4", options);
	}

	/**
	 * This test generate a video in medium resolution using the h264 codec.
	 */
	public void testIPodMedium() {

		FAACEncodingOptions audioOptions = new FAACEncodingOptions(128);
		audioOptions.setMPEGVersion(FAACEncodingOptions.MPEG_VERSION_4);
		audioOptions.setObjectType(FAACEncodingOptions.OBJECT_TYPE_LOW);
		audioOptions.setOutputSampleRate(48000);

		X264EncodingOptions videoOptions = new X264EncodingOptions(750);

		videoOptions.setMaximumLocalBitrate(1500, 244);
		videoOptions.setOutputFrameRate(29.58);
		videoOptions.setBitStreamLevel(X264EncodingOptions.LEVEL_13);
		videoOptions.setReferenceFrame(2);
		videoOptions.setSubpelRefinement(6);

		videoOptions.enableCabac(false);
		videoOptions.enablePartition(true);
		videoOptions.enableThreads(true);
		videoOptions.enableTrellis(true);
		videoOptions
				.setMotionEstimation(X264EncodingOptions.MOTION_ESTIMATION_UNEVEN);
		videoOptions
				.setMotionPrediction(X264EncodingOptions.MOTION_PREDICTION_AUTO);

		EncodingOptions options = new EncodingOptions(videoOptions,
				audioOptions);
		options.addVideoFilter(new PullupFilter());
		options.addVideoFilter(new HarddupFilter());

		options.setScaleOptions(new VideoScalingOptions(640, 480));

		options.setMuxer(new MP4BoxMuxer());

		encode("ipod.medium.mp4", options);
	}

	/**
	 * This test generate a video for PMP.
	 */
	public void testPMPEncode() {
		File videoFile = new File(VIDEO_FILE1);
		if (!videoFile.exists()) {
			TestCase.fail(videoFile.getAbsolutePath() + " doesn't exist !");
		}

		File output = new File("Output.PMP." + VIDEO_FILE1);
		if (output.exists()) {
			output.delete();
		}

		LameEncodingOptions audioOptions = new LameEncodingOptions(192,
				LameEncodingOptions.TYPE_CONSTANT_BITRATE);
		audioOptions.setChannelMode(LameEncodingOptions.MODE_JOIN_STEREO);
		audioOptions.setOutputSampleRate(44100);

		XVideoEncodingOptions videoOptions = new XVideoEncodingOptions(1500);
		videoOptions.setMaxBFrame(0);
		videoOptions.setOutputFrameRate(30);

		EncodingOptions options = new EncodingOptions(videoOptions,
				audioOptions);
		options.addVideoFilter(new HarddupFilter());

		options.setScaleOptions(new VideoScalingOptions(640, 480));

		encode(videoFile, output, options);
	}

	/**
	 * This test generate a video for U10.
	 */
	public void testU10Encode() {
		File videoFile = new File(VIDEO_FILE1);
		if (!videoFile.exists()) {
			TestCase.fail(videoFile.getAbsolutePath() + " doesn't exist !");
		}

		File output = new File("Output.PMP." + VIDEO_FILE1);
		if (output.exists()) {
			output.delete();
		}

		LameEncodingOptions audioOptions = new LameEncodingOptions(128,
				LameEncodingOptions.TYPE_CONSTANT_BITRATE);
		audioOptions.setChannelMode(LameEncodingOptions.MODE_JOIN_STEREO);
		audioOptions.setOutputSampleRate(44100);

		XVideoEncodingOptions videoOptions = new XVideoEncodingOptions(384);
		videoOptions.setMaxBFrame(0);
		videoOptions.setOutputFrameRate(15);

		EncodingOptions options = new EncodingOptions(videoOptions,
				audioOptions);
		options.addVideoFilter(new HarddupFilter());

		options.setScaleOptions(new VideoScalingOptions(320, 240));

		encode(videoFile, output, options);
	}

	/**
	 * This test generate a video file for Zen.
	 */
	public void testZenEncode() {
		File videoFile = new File(VIDEO_FILE1);
		if (!videoFile.exists()) {
			TestCase.fail(videoFile.getAbsolutePath() + " doesn't exist !");
		}

		File output = new File("Output.Zen." + VIDEO_FILE1);
		if (output.exists()) {
			output.delete();
		}

		LameEncodingOptions audioOptions = new LameEncodingOptions(192,
				LameEncodingOptions.TYPE_CONSTANT_BITRATE);
		audioOptions.setChannelMode(LameEncodingOptions.MODE_JOIN_STEREO);
		audioOptions.setOutputSampleRate(44100);

		XVideoEncodingOptions videoOptions = new XVideoEncodingOptions(1500);
		videoOptions.setMaxBFrame(0);
		videoOptions.setOutputFrameRate(25);

		EncodingOptions options = new EncodingOptions(videoOptions,
				audioOptions);
		options.addVideoFilter(new HarddupFilter());

		options.setScaleOptions(new VideoScalingOptions(640, 480));

		encode(videoFile, output, options);
	}

	/**
	 * This test try to generate video file for X5.
	 */
	public void testX5Encode() {
		File videoFile = new File(VIDEO_FILE1);
		if (!videoFile.exists()) {
			TestCase.fail(videoFile.getAbsolutePath() + " doesn't exist !");
		}

		File output = new File("Output.X5." + VIDEO_FILE1);
		if (output.exists()) {
			output.delete();
		}

		LameEncodingOptions audioOptions = new LameEncodingOptions(192,
				LameEncodingOptions.TYPE_CONSTANT_BITRATE);
		audioOptions.setChannelMode(LameEncodingOptions.MODE_JOIN_STEREO);
		audioOptions.setOutputSampleRate(44100);

		XVideoEncodingOptions videoOptions = new XVideoEncodingOptions(256);
		videoOptions.setMaxBFrame(0);
		videoOptions.setOutputFrameRate(14);

		EncodingOptions options = new EncodingOptions(videoOptions,
				audioOptions);
		options.addVideoFilter(new HarddupFilter());

		options.setScaleOptions(new VideoScalingOptions(160, 128));

		encode(videoFile, output, options);
	}

	/**
	 * Encode video file with the given encoding options.
	 * 
	 * @param ouputName
	 *            the output file name
	 * @param options
	 *            the encoding option
	 */
	private void encode(String ouputName, EncodingOptions options) {

		String[] files = new String[] { /* SMALL_VIDEO_FILE1, */
				SMALL_VIDEO_FILE2,
				/* SMALL_VIDEO_FILE3, */SMALL_VIDEO_FILE4 };

		for (int i = 0; i < files.length; i++) {
			File videoFile = new File(INPUT_DIRECTORY, files[i]);
			if (!videoFile.exists()) {
				TestCase.fail(videoFile.getAbsolutePath() + " doesn't exist !");
			}

			File output = new File(OUTPUT_DIRECTORY, files[i] + "_" + ouputName);
			if (output.exists()) {
				output.delete();
			}

			encode(videoFile, output, options);
		}

	}

	/**
	 * Encode the given input video file.
	 * 
	 * @param fileInput
	 *            the input video file
	 * @param fileOutput
	 *            the ouput video file
	 * @param options
	 *            the encoding options
	 */
	private void encode(File fileInput, File fileOutput, EncodingOptions options) {
		MPlayerWrapper wrapper = null;
		try {

			InputVideoFile input = new InputVideoFile(fileInput);

			wrapper = new MPlayerWrapper();
			EncodingJob job = wrapper.encode(input, fileOutput, options);

			job.addProgressObserver(new EncodingProgressObserver() {

				public void progressHasChanged(EncodingJob job) {
					System.out.println("Completed: "
							+ job.getPercentCompleted() + "%   Time remaining:"
							+ job.getTimeRemaining() / 60 + " min");
				}

			});

			job.start();

		} catch (MPlayerException e) {
			e.printStackTrace();
			TestCase.fail(e.getMessage());
			return;
		} catch (MPlayerNotFoundException e) {
			e.printStackTrace();
			TestCase.fail(e.getMessage());
			return;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			TestCase.fail(e.getMessage());
		}
	}

}
