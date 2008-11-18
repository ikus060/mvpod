package net.homeip.entreprisesmd.mvconv.core;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioStream;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoDVD;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoFile;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.AudioEncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.VideoEncodingOptions;

/**
 * This class are use to format a time in second to a user friendly way.
 * 
 * @author patapouf
 * 
 */
public final class VideoInfoFormater {

	/**
	 * Number of second in an hour.
	 */
	private static final int SECONDS_IN_HOUR = 3600;
	/**
	 * Number of second in a minute.
	 */
	private static final int SECONDS_IN_MINUTE = 60;

	/**
	 * Private constructor.
	 */
	private VideoInfoFormater() {
		/*
		 * Private constructor use to present creation of the utility class.
		 */
	}

	/**
	 * Return a string describing the audio stream.
	 * 
	 * @param stream
	 *            the audio stream.
	 * @return the description.
	 */
	public static String formatAudioStream(AudioStream stream) {
		String na = Localization.getString(Localization.NA);

		String audioFormatText = na;
		String audioChannel = na;
		String audioSampleRate = na;
		String audioBitrate = na;

		AudioFormat audioFormatValue = stream.getAudioFormat();
		if (audioFormatText != null) {
			audioFormatText = Localization.getLocalizedFormat(audioFormatValue);
		}

		int channel = stream.getChannelMode();
		if (channel > 0) {
			audioChannel = Integer.toString(channel) + " ch";  //$NON-NLS-1$
		}

		int sampleRate = stream.getSampleRate();
		if (sampleRate > 0) {
			audioSampleRate = sampleRate + " Hz";  //$NON-NLS-1$
		}

		double bitrate = stream.getBitrate();
		if (bitrate > 0) {
			audioBitrate = Double.toString(bitrate) + " kbps";
		}

		// Audio Codec: %s, Channels: %s, Sample rate: %s, Bitrate: %s
		String description = Localization.getString(
				Localization.FORMATTER_AUDIO_OPTIONS, audioFormatText,
				audioChannel, audioSampleRate, audioBitrate);

		return description;
	}

	/**
	 * Return a string describing the audio encoding options.
	 * 
	 * @param options
	 *            the audio encoding options.
	 * @return the description.
	 */
	public static String formatAudioEncodingOptions(AudioEncodingOptions options) {

		String na = Localization.getString(Localization.NA);

		// Audio codec
		String codec = na;
		AudioFormat format = options.getAudioFormat();
		codec = Localization.getLocalizedFormat(format);

		// Bitrate
		int bitrateValue = options.getBitrate();
		String bitrate = na;
		if (bitrateValue > 0) {
			bitrate = bitrateValue + " kbps";
		}

		// Channels
		String channels = na;
		int channelsValue = options.getChannels();
		if (channelsValue > 0) {
			channels = channelsValue + " ch";
		}

		// Sample Rate
		String sampleRate = na;
		int sampleRateValue = options.getOutputSampleRate();
		if (sampleRateValue > 0) {
			sampleRate = sampleRateValue + " Hz";
		}

		// Audio Codec: %s, Channels: %s, Sample rate: %s, Bitrate: %s
		String description = Localization.getString(
				Localization.FORMATTER_AUDIO_OPTIONS, codec, channels,
				sampleRate, bitrate);

		return description;
	}

	/**
	 * Use to format a duration time.
	 * 
	 * @param duration
	 *            the duration in second.
	 * @return the string.
	 */
	public static String formatDuration(double duration) {

		int hours = (int) duration / SECONDS_IN_HOUR;
		int minutes = (int) (duration % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE;
		int seconds = (int) (duration % SECONDS_IN_MINUTE);

		String format = "";
		if (hours > 0) {
			format += Localization
					.getString(Localization.DURATION_HOURS, hours);
		}
		if (minutes > 0) {
			if (hours > 0) {
				format += " ";
			}
			format += Localization.getString(Localization.DURATION_MINUTES,
					minutes);
		}
		if ((seconds > 0) || (hours <= 0 && minutes <= 0)) {
			if (hours > 0 || minutes > 0) {
				format += " ";
			}
			format += Localization.getString(Localization.DURATION_SECONDS,
					seconds);
		}

		return format;
	}

	/**
	 * Return a user friendly description of the inputVideo.
	 * 
	 * @param inputVideo
	 *            the input video for witch we want a short description.
	 * @return the video description.
	 */
	public static String formatInputVideo(InputVideo inputVideo) {

		if (inputVideo instanceof InputVideoFile) {
			return ((InputVideoFile) inputVideo).getFile().getName();
		} else if (inputVideo instanceof InputVideoDVD) {
			return ((InputVideoDVD) inputVideo).getDevice();
		}
		return "";

	}

	/**
	 * Return a string describing the video encoding options.
	 * 
	 * @param options
	 *            the video encoding options.
	 * @return the description.
	 */
	public static String formatVideoEncodingOptions(VideoEncodingOptions options) {
		String na = Localization.getString(Localization.NA);

		// Video codec
		String codec = na;
		if (options.getVideoFormat() != null) {
			codec = Localization.getLocalizedFormat(options.getVideoFormat());
		}

		// Framerate
		String frameRate = na;
		double frameRateValue = options.getOutputFrameRate();
		double maxFrameRateValue = options.getMaxOutputFrameRate();
		if (frameRateValue > 0) {
			frameRate = frameRateValue + " fps";
		} else if (maxFrameRateValue > 0) {
			frameRate = maxFrameRateValue + " fps (max)";
		}

		// Bitrate
		int bitrateValue = options.getBitrate();
		String bitrate = na;
		if (bitrateValue > 0) {
			bitrate = bitrateValue + " kbps";
		}

		// Video Codec: %s, Framerate: %s, Bitrate: %s
		String description = Localization
				.getString(Localization.FORMATTER_VIDEO_OPTIONS, codec,
						frameRate, bitrate);

		return description;
	}

	/**
	 * Return a description of the video information.
	 * 
	 * @param info
	 *            the video information.
	 * @param audioSelected
	 *            the selected audio stream selected.
	 * @return the description.
	 */
	public static String formatVideoInfo(VideoInfo info) {
		String na = Localization.getString(Localization.NA);

		String duration = VideoInfoFormater.formatDuration(info.getLength());

		// Video Dimension
		String dimension = na;
		if (info.getVideoDimention() != null) {
			dimension = info.getVideoDimention().getWidth() + " x "
					+ info.getVideoDimention().getHeight();
		}

		// Video codec
		String videoCodec = na;
		if (info.getVideoFormat() != null) {
			videoCodec = Localization.getLocalizedFormat(info.getVideoFormat());
		}

		// Frame rate
		double fps = info.getFrameRate();
		String videoFps = na;
		if (fps > 0) {
			videoFps = fps + " fps";
		}

		// Bitrate
		double bitrate = info.getVideoBitrate();
		String videoBitrate = na;
		if (bitrate > 0) {
			videoBitrate = bitrate + " kbps";
		}

		// Duration, dimension, video codec, framerate, bitrate
		String description = Localization.getString(
				Localization.FORMATTER_VIDEO_INFO, duration, dimension,
				videoCodec, videoFps, videoBitrate);

		return description;
	}

	/**
	 * Return a string describing the video scaling options.
	 * 
	 * @param options
	 *            the video scaling options.
	 * @return the description.
	 */
	public static String formatVideoScalingOptions(VideoScalingOptions options) {
		String na = Localization.getString(Localization.NA);

		// Dimension
		String dimension = na;
		if (options.getVideoDimension() != null) {
			dimension = options.getVideoDimension().getWidth() + " x "
					+ options.getVideoDimension().getHeight();
		}

		// Method
		String method = "";
		if (options.getScalingMethod() == VideoScalingOptions.METHOD_CROP) {
			method = Localization.getString(Localization.SCALING_METHOD_CROP);
		} else if (options.getScalingMethod() == VideoScalingOptions.METHOD_FILL) {
			method = Localization.getString(Localization.SCALING_METHOD_FILL);
		} else if (options.getScalingMethod() == VideoScalingOptions.METHOD_SCALE) {
			method = Localization.getString(Localization.SCALING_METHOD_SCALE);
		} else if (options.getScalingMethod() == VideoScalingOptions.METHOD_FIT) {
			method = Localization.getString(Localization.SCALING_METHOD_FIT);
		}

		// Dimentions: %s, %s
		String description = Localization.getString(
				Localization.FORMATTER_SCALING_OPTIONS, dimension, method);

		return description;
	}
}
