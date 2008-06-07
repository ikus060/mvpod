package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contain all available information about a video. Some information
 * can be unavailable or totaly incorrect.
 * 
 * @author patapouf
 * 
 */

// TODO : Create a new model to revieve the video info and audio info (must
// support audio file, video file with or without audio, multiple audio stream,
// etc.)

public class VideoInfo {

	/**
	 * The WMV framerate return by mplayer.
	 */
	private static final int WMV_FRAMERATE = 1000;

	/**
	 * Regular expression to check if a file is valid.
	 */
	private static final String VALID_FILE = "Starting playback...";

	/**
	 * Convert factor.
	 */
	private static final int KPBS_TO_BPS_FACTOR = 1000;

	/**
	 * String return by mplayer.
	 */
	private String mplayerOutput;
	/**
	 * Video codec
	 */
	private String videocodec = null;
	/**
	 * Video format.
	 */
	private VideoFormat videoformat = null;
	/**
	 * Video muxer.
	 */
	private String videoMuxer = null;
	/**
	 * Video bitrate.
	 */
	private double videoBitrate = -1;
	/**
	 * Total length in second.
	 */
	private double length = -1;
	/**
	 * Number of title (for DVD only).
	 */
	private int numberOfTitle = -1;
	/**
	 * Audio stream detail (if available).
	 */
	private List<AudioStream> audioStreams = null;
	/**
	 * Subtitle detail (if available).
	 */
	private List<SubtitleStream> subtitleLanguages = null;
	/**
	 * Video dimension.
	 */
	private VideoDimension dimension = null;
	/**
	 * Video frame rate in fps.
	 */
	private double framerate = -1;

	/**
	 * Create a new Video information from mplayer ouput.
	 * 
	 * @param mplayerOutput
	 *            the mplayer output string.
	 * @throws UnsupportedFileException
	 *             when the given mplayer output doesn't contain the information
	 *             to validate that it's a supported file.
	 */
	public VideoInfo(String mplayerOutput) throws UnsupportedFileException {

		Matcher matcher = Pattern.compile(VALID_FILE, Pattern.MULTILINE)
				.matcher(mplayerOutput);
		if (!matcher.find()) {
			throw new UnsupportedFileException();
		}

		this.mplayerOutput = mplayerOutput;
	}

	/**
	 * Return the list of audio stream.
	 * 
	 * @return the list of audio stream.
	 */
	public List<AudioStream> getAudioStreams() {

		if (audioStreams != null) {
			return audioStreams;
		}
		audioStreams = new LinkedList<AudioStream>();

		// Find all audio identifer
		Set<String> audioIds = new HashSet<String>();
		Matcher matcher = Pattern.compile("^ID_AUDIO_ID=([0-9]+)$",
				Pattern.MULTILINE).matcher(mplayerOutput);
		while (matcher.find()) {
			audioIds.add(matcher.group(1));

		}

		// Find selected audio ID
		matcher = Pattern.compile("'-aid' '([0-9]+)'", Pattern.MULTILINE)
				.matcher(mplayerOutput);
		String defaultAudioID = "";
		if (matcher.find()) {
			defaultAudioID = matcher.group(1);
		} else if (audioIds.size() == 1) {
			defaultAudioID = audioIds.iterator().next();
		}

		// Loop to look for more detail
		Iterator<String> iter = audioIds.iterator();
		while (iter.hasNext()) {

			String audioID = iter.next();
			int bitrate = -1;
			int sampleRate = -1;
			int channel = -1;
			AudioFormat format = null;

			// Find out audio stream language if available
			String language = "";
			matcher = Pattern.compile("^ID_AID_" + audioID + "_LANG=(.*)$",
					Pattern.MULTILINE).matcher(mplayerOutput);
			if (matcher.find()) {
				language = matcher.group(1);
			}

			// Search more detail
			matcher = Pattern.compile(
					"^audio stream: .* format: (.*) language: (.*) aid: "
							+ audioID + ".$", Pattern.MULTILINE).matcher(
					mplayerOutput);
			if (matcher.find()) {
				AudioFormat.fromMplayerAudioFormatID(matcher.group(1));
				language = matcher.group(2);
			}

			if (audioID.equals(defaultAudioID)) {
				// Search for default audio stream
				matcher = Pattern.compile("^ID_AUDIO_BITRATE=(.*)$",
						Pattern.MULTILINE).matcher(mplayerOutput);
				while (matcher.find()) {
					bitrate = Integer.parseInt(matcher.group(1))
							/ KPBS_TO_BPS_FACTOR;
				}

				matcher = Pattern.compile("^ID_AUDIO_RATE=(.*)$",
						Pattern.MULTILINE).matcher(mplayerOutput);
				while (matcher.find()) {
					sampleRate = Integer.parseInt(matcher.group(1)); // in Hz
				}
				matcher = Pattern.compile("^ID_AUDIO_NCH=(.*)$",
						Pattern.MULTILINE).matcher(mplayerOutput);
				while (matcher.find()) {
					channel = Integer.parseInt(matcher.group(1));
				}
				matcher = Pattern.compile("^ID_AUDIO_FORMAT=(.*)$",
						Pattern.MULTILINE).matcher(mplayerOutput);
				while (matcher.find()) {
					format = AudioFormat.fromMplayerAudioFormatID(matcher.group(1));
				}
			}

			// Add this stream information to the list
			audioStreams.add(new AudioStream(audioID, bitrate, sampleRate,
					channel, format, language));

			bitrate = -1;
			sampleRate = -1;
			channel = -1;
			format = null;
			language = "";
		}

		return audioStreams;
	}

	/**
	 * Return the frame rate (fps) of the input video.
	 * 
	 * @return frame rate.
	 */
	public double getFrameRate() {

		if (framerate != -1) {
			return framerate;
		}

		framerate = -1;
		Matcher matcher = Pattern.compile("^ID_VIDEO_FPS=([0-9.]*)$",
				Pattern.MULTILINE).matcher(mplayerOutput);
		if (matcher.find()) {
			try {
				framerate = Double.parseDouble(matcher.group(1));
				if (framerate == WMV_FRAMERATE) {
					framerate = -1;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		return framerate;
	}

	/**
	 * Return the duration in second.
	 * 
	 * @return the duration in second.
	 */
	public double getLength() {

		if (length != -1) {
			return length;
		}

		length = 0;
		Matcher matcher = Pattern.compile("^ID_LENGTH=([0-9.]*)$",
				Pattern.MULTILINE).matcher(mplayerOutput);
		if (matcher.find()) {
			try {
				length = Double.parseDouble(matcher.group(1));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return length;
	}

	/**
	 * Return the number of angle for a title.
	 * 
	 * @param title
	 *            the title identifier.
	 * @return number of chapter.
	 */
	public int getNumberOfAngles(int title) {

		int numberOfChapters = -1;
		Matcher matcher = Pattern.compile(
				"^ID_DVD_TITLE_" + title + "_ANGLES=([1-9]+)$").matcher(
				mplayerOutput);
		if (matcher.find()) {
			numberOfChapters = Integer.parseInt(matcher.group(1));
		}

		return numberOfChapters;
	}

	/**
	 * Return the number of title of the input video.
	 * 
	 * @return number of title of the input video.
	 */
	public int getNumberOfTitles() {

		if (numberOfTitle != -1) {
			return numberOfTitle;
		}

		numberOfTitle = 0;
		Matcher matcher = Pattern.compile("^ID_DVD_TITLES=([0-9]*)$",
				Pattern.MULTILINE).matcher(mplayerOutput);
		if (matcher.find()) {
			numberOfTitle = Integer.parseInt(matcher.group(1));
		}
		return numberOfTitle;
	}

	/**
	 * Return the number of chapter of the input video.
	 * 
	 * @param title
	 *            the title id for witch we want the number of chapters.
	 * @return number of chapter.
	 */
	public int getNumberOfChapters(int title) {

		int numberOfChapters = -1;
		Matcher matcher = Pattern.compile(
				"^ID_DVD_TITLE_" + title + "_CHAPTERS=([1-9]+)$",
				Pattern.MULTILINE).matcher(mplayerOutput);
		if (matcher.find()) {
			numberOfChapters = Integer.parseInt(matcher.group(1));
		}

		return numberOfChapters;
	}

	/**
	 * Return a list of available subtitle.
	 * 
	 * @return the list of available subtitle.
	 */
	public List<SubtitleStream> getSubtitleLanguages() {

		if (subtitleLanguages != null) {
			return subtitleLanguages;
		}

		List<SubtitleStream> subtitleLanguages = new ArrayList<SubtitleStream>();

		// Parse embedded subtitles (tested with matroska, ogg vorbis)

		Matcher matcher = Pattern.compile("^ID_SUBTITLE_ID=([0-9]+)$",
				Pattern.MULTILINE).matcher(mplayerOutput);
		while (matcher.find()) {
			String sid = matcher.group(1);
			String description = "";

			Matcher descMatcher = Pattern.compile(
					"^ID_SID_" + sid + "_LANG=(.*)$", Pattern.MULTILINE)
					.matcher(mplayerOutput);
			if (descMatcher.find()) {
				description = descMatcher.group(1);
			}

			// Add name if found (I've seen Matroska use these) Here's an
			// example:
			// ID_SUBTITLE_ID=0
			// ID_SID_0_NAME=Normal Subtitles
			// ID_SID_0_LANG=eng
			// [mkv] Track ID 4: subtitles (S_TEXT/SSA) "Normal Subtitles",
			// -sid 0, -slang eng
			// ID_SUBTITLE_ID=1
			// ID_SID_1_NAME=Subtitles with Karaoke
			// ID_SID_1_LANG=eng
			// [mkv] Track ID 5: subtitles (S_TEXT/SSA) "Subtitles with
			// Karaoke", -sid 1, -slang eng

			descMatcher = Pattern.compile("^ID_SID_" + sid + "_NAME=(.*)$",
					Pattern.MULTILINE).matcher(mplayerOutput);
			if (descMatcher.find()) {
				description = description + ": " + descMatcher.group(1);
			}

			// hack: Add matroska subtitle format info.
			String t = "^\\[mkv\\] Track ID .*: subtitles \\((.*)\\).* -sid "
					+ sid + ",.*$";
			descMatcher = Pattern.compile(t, Pattern.MULTILINE).matcher(
					mplayerOutput);
			if (descMatcher.find()) {
				description = description + " (" + descMatcher.group(1) + ")";
			}

			subtitleLanguages.add(new SubtitleStream(sid, description));
		}

		return subtitleLanguages;
	}

	/**
	 * Return the video bitrate in Kbps.
	 * 
	 * @return the video bitrate in Kbps.
	 */
	public double getVideoBitrate() {
		if (videoBitrate != -1) {
			return videoBitrate;
		}

		Matcher matcher = Pattern.compile("^ID_VIDEO_BITRATE=([0-9.]*)$",
				Pattern.MULTILINE).matcher(mplayerOutput);
		videoBitrate = -1;
		if (matcher.find()) {
			videoBitrate = Integer.parseInt(matcher.group(1))
					/ KPBS_TO_BPS_FACTOR;
		}
		return videoBitrate;
	}

	/**
	 * Return height if the input video.
	 * 
	 * @return height of the video.
	 */
	public VideoDimension getVideoDimention() {
		if (dimension != null) {
			return dimension;
		}

		parseHeightAndWidth();
		return dimension;
	}

	/**
	 * Return the video format.
	 * 
	 * @return the video format.
	 */
	public VideoFormat getVideoFormat() {

		if (videoformat != null) {
			return videoformat;
		}

		Matcher matcher = Pattern.compile("ID_VIDEO_FORMAT=(.*)",
				Pattern.MULTILINE).matcher(mplayerOutput);
		if (matcher.find()) {
			videoformat = VideoFormat.fromMplayerVideoFormatID(matcher.group(1));
		}
		return videoformat;
	}

	public String getMPlayerAudioCodec() {

		Matcher matcher = Pattern.compile("ID_AUDIO_CODEC=(.*)",
				Pattern.MULTILINE).matcher(mplayerOutput);
		String audiocodec = "";
		if (matcher.find()) {
			audiocodec = matcher.group(1);
		}
		return audiocodec;
	}

	public String getMPlayerAudioFormat() {

		Matcher matcher = Pattern.compile("ID_AUDIO_FORMAT=(.*)",
				Pattern.MULTILINE).matcher(mplayerOutput);
		String audioformat = "";
		if (matcher.find()) {
			audioformat = matcher.group(1);
		}
		return audioformat;
	}

	public String getMPlayerVideoCodec() {
		if (videocodec != null) {
			return videocodec;
		}

		Matcher matcher = Pattern.compile("ID_VIDEO_CODEC=(.*)",
				Pattern.MULTILINE).matcher(mplayerOutput);
		videocodec = "";
		if (matcher.find()) {
			videocodec = matcher.group(1);
		}
		return videocodec;
	}

	/**
	 * Return the video format (this value are not user friendly).
	 * 
	 * @return the video format.
	 */
	public String getMplayerVideoFormat() {

		Matcher matcher = Pattern.compile("ID_VIDEO_FORMAT=(.*)",
				Pattern.MULTILINE).matcher(mplayerOutput);
		String mplayerFormat = "";
		if (matcher.find()) {
			mplayerFormat = matcher.group(1);
		}
		return mplayerFormat;
	}
	
	/**
	 * Return the video format (this value are not user friendly).
	 * 
	 * @return the video format.
	 */
	public String getMplayerVideoMuxer() {

		if (videoMuxer != null) {
			return videoMuxer;
		}

		Matcher matcher = Pattern.compile("ID_DEMUXER=(.*)",
				Pattern.MULTILINE).matcher(mplayerOutput);
		videoMuxer = "";
		if (matcher.find()) {
			videoMuxer = matcher.group(1);
		}
		return videoMuxer;
	}

	/**
	 * Find Width and height information.
	 */
	private void parseHeightAndWidth() {

		int width = -1;
		int height = -1;
		Matcher matcher;

		try {
			matcher = Pattern.compile("ID_VIDEO_WIDTH=(\\d+)").matcher(
					mplayerOutput);
			if (matcher.find()) {
				width = Integer.parseInt(matcher.group(1));
			}

			matcher = Pattern.compile("ID_VIDEO_HEIGHT=(\\d+)").matcher(
					mplayerOutput);
			if (matcher.find()) {
				height = Integer.parseInt(matcher.group(1));
			}
		} catch (NumberFormatException e) {
			// Nothing to do
			e.printStackTrace();
		}

		if (width != -1 && height != -1) {
			dimension = new VideoDimension(width, height);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("AudioStream : " + getAudioStreams() + "\r\n");
		buf.append("Subtitle Languages : " + getSubtitleLanguages() + "\r\n");
		buf.append("Number Of Title : " + getNumberOfTitles() + "\r\n");
		buf.append("Video Format : " + getVideoFormat() + "\r\n");
		buf.append("Video Framerate : " + getFrameRate() + "\r\n");
		buf.append("Video Bitrate : " + getVideoBitrate() + "\r\n");
		buf.append("Dimention : " + getVideoDimention() + "\r\n");
		buf.append("Length : " + getLength() + "\r\n");

		return buf.toString();
	}

}
