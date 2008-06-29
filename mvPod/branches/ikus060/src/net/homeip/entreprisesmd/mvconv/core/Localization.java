package net.homeip.entreprisesmd.mvconv.core;

import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoDemuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoFormat;

/**
 * This class are use to access localized information.
 * 
 * @author patapouf
 * 
 */
public final class Localization {

	/*
	 * Constants
	 */
	public static final String VERSION = "VERSION";
	public static final String VERSION_TEXT = "VERSION_TEXT";
	public static final String VERSION_BUILD = "VERSION_BUILD";

	public static final String APPLICATION_NAME = "APPLICATION_NAME";
	public static final String APPLICATION_DESCRIPTION = "APPLICATION_DESCRIPTION";
	public static final String APPLICATION_COPYRIGHT = "APPLICATION_COPYRIGHT";
	public static final String APPLICATION_WEBSITE = "APPLICATION_WEBSITE";
	public static final String NA = "NA";

	public static final String MPLAYER_NOT_FOUND = "MPLAYER_NOT_FOUND";
	public static final String MPLAYER_DVDDEVICE_NOT_AVAILABLE = "MPLAYER_DVDDEVICE_NOT_AVAILABLE";
	public static final String MPLAYER_GRAP_XV_PORT = "MPLAYER_GRAP_XV_PORT";
	public static final String MPLAYER_UNSUPPORTED_FORMAT = "MPLAYER_UNSUPPORTED_FORMAT";
	public static final String MPLAYER_PALETTE_ERROR = "MPLAYER_PALETTE_ERROR";
	public static final String MPLAYER_COMPONENT_MISSING = "MPLAYER_COMPONENT_MISSING";

	public static final String DURATION_HOURS = "DURATION_HOURS";
	public static final String DURATION_MINUTES = "DURATION_MINUTES";
	public static final String DURATION_SECONDS = "DURATION_SECONDS";

	public static final String FILE_VIDEO = "FILE_VIDEO";
	public static final String FILE_EXTENTION_VOB = "FILE_EXTENTION_VOB";
	public static final String FILE_EXTENTION_ISO = "FILE_EXTENTION_ISO";
	public static final String FILE_EXTENTION_ALL = "FILE_EXTENTION_ALL";

	public static final String SUMMARY_TITLE = "SUMMARY_TITLE";
	public static final String VIDEO_OPTIONS_TITLE = "VIDEO_OPTIONS_TITLE";
	public static final String VIDEOCODEC_OPTIONS_TITLE = "VIDEOCODEC_OPTIONS_TITLE";
	public static final String AUDIO_OPTIONS_TITLE = "AUDIO_OPTIONS_TITLE";
	public static final String CUSTOM_OPTIONS_TITLE = "CUSTOM_OPTIONS_TITLE";
	public static final String WELCOME_TITLE = "WELCOME_TITLE";

	public static final String CONVERTJOB_DESCRIPTION = "CONVERTJOB_DESCRIPTION";
	public static final String CONVERTJOB_PROGRESS_DESCRIPTION = "CONVERTJOB_PROGRESS_DESCRIPTION";
	public static final String CONVERTJOB_LESS_THAN_A_MINUTE = "CONVERTJOB_LESS_THAN_A_MINUTE";
	public static final String CONVERTJOB_FAILED_DESCRIPTION = "CONVERTJOB_FAILED_DESCRIPTION";

	public static final String JOB_QUEUE_WINDOW_TITLE = "JOB_QUEUE_WINDOW_TITLE";
	public static final String JOB_QUEUE_WINDOW_CLOSE_BUTTON = "JOB_QUEUE_WINDOW_CLOSE_BUTTON";
	public static final String JOB_QUEUE_WINDOW_CLEAR_BUTTON = "JOB_QUEUE_WINDOW_CLEAR_BUTTON";
	public static final String JOB_QUEUE_WINDOW_CLEAR_COMPLETED = "JOB_QUEUE_WINDOW_CLEAR_COMPLETED";

	public static final String JOB_QUEUE_WINDOW_CLEAR = "JOB_QUEUE_WINDOW_CLEAR";
	public static final String JOB_QUEUE_WINDOW_CANCEL = "JOB_QUEUE_WINDOW_CANCEL";
	public static final String JOB_QUEUE_WINDOW_REMOVE = "JOB_QUEUE_WINDOW_REMOVE";
	public static final String JOB_QUEUE_WINDOW_OPEN = "JOB_QUEUE_WINDOW_OPEN";
	public static final String JOB_QUEUE_WINDOW_MOREINFO = "JOB_QUEUE_WINDOW_MOREINFO";
	public static final String JOB_QUEUE_WINDOW_START = "JOB_QUEUE_WINDOW_START";
	public static final String JOB_QUEUE_WINDOW_QUEUED = "JOB_QUEUE_WINDOW_QUEUED";
	public static final String JOB_QUEUE_WINDOW_COMPLETED = "JOB_QUEUE_WINDOW_COMPLETED";
	public static final String JOB_QUEUE_WINDOW_FAILED = "JOB_QUEUE_WINDOW_FAILED";
	public static final String JOB_QUEUE_WINDOW_CANCELED = "JOB_QUEUE_WINDOW_CANCELED";
	public static final String JOB_QUEUE_CONFIRM_QUIT = "JOB_QUEUE_CONFIRM_QUIT";

	public static final String INPUTOUTPUT_INPUT = "INPUTOUTPUT_INPUT";
	public static final String INPUTOUTPUT_OUPUT = "INPUTOUTPUT_OUPUT";
	public static final String INPUTOUTPUT_BROWSE = "INPUTOUTPUT_BROWSE";
	public static final String INPUTOUTPUT_LANGAGE = "INPUTOUTPUT_LANGAGE";
	public static final String INPUTOUTPUT_TITLE = "INPUTOUTPUT_TITLE";
	public static final String INPUTOUTPUT_SUBTITLE = "INPUTOUTPUT_SUBTITLE";
	public static final String INPUTOUTPUT_FILENAME = "INPUTOUTPUT_FILENAME";
	public static final String INPUTOUTPUT_VIDEO = "INPUTOUTPUT_VIDEO";
	public static final String INPUTOUTPUT_AUDIO = "INPUTOUTPUT_AUDIO";
	public static final String INPUTOUTPUT_INPUTVIDEOFILE_DESCRIPTION = "INPUTOUTPUT_INPUTVIDEOFILE_DESCRIPTION";
	public static final String INPUTOUTPUT_INPUTVIDEODVD_DESCRIPTION = "INPUTOUTPUT_INPUTVIDEODVD_DESCRIPTION";
	public static final String INPUTOUTPUT_TRACK = "INPUTOUTPUT_TRACK";
	public static final String INPUTOUTPUT_NONE = "INPUTOUTPUT_NONE";
	public static final String INPUTOUTPUT_DEFAULT = "INPUTOUTPUT_DEFAULT";
	public static final String INPUTOUTPUT_RETRIEVE_INFO_FAILED = "INPUTOUTPUT_RETRIEVE_INFO_FAILED";
	public static final String INPUTOUTPUT_FILE_NO_MORE_EXIST = "INPUTOUTPUT_FILE_NO_MORE_EXIST";
	public static final String INPUTOUTPUT_OUTPUT_FILEDIALOG_TITLE = "INPUTOUTPUT_OUTPUT_FILEDIALOG_TITLE";

	public static final String INPUTOUTPUT_MORE_OPTIONS = "INPUTOUTPUT_MORE_OPTIONS";
	public static final String INPUTOUTPUT_MORE_OPTIONS_DIALOG_TITLE = "INPUTOUTPUT_MORE_OPTIONS_DIALOG_TITLE";
	public static final String INPUTOUTPUT_ASPECT_RATIO = "INPUTOUTPUT_ASPECT_RATIO";
	public static final String INPUTOUTPUT_ASPECT_RATIO_CUSTOM_MESSAGE = "INPUTOUTPUT_ASPECT_RATIO_CUSTOM_MESSAGE";
	public static final String INPUTOUTPUT_ASPECT_RATIO_CUSTOM_FORMAT = "INPUTOUTPUT_ASPECT_RATIO_CUSTOM_FORMAT";
	public static final String INPUTOUTPUT_ASPECT_RATIO_KEEP = "INPUTOUTPUT_ASPECT_RATIO_KEEP";
	public static final String INPUTOUTPUT_ASPECT_RATIO_4_3 = "INPUTOUTPUT_ASPECT_RATIO_4_3";
	public static final String INPUTOUTPUT_ASPECT_RATIO_16_9 = "INPUTOUTPUT_ASPECT_RATIO_16_9"; 
	public static final String INPUTOUTPUT_ASPECT_RATIO_CUSTOM = "INPUTOUTPUT_ASPECT_RATIO_CUSTOM";
	
	public static final String FORMATTER_UNKNOWN_FORMAT = "FORMATTER_UNKNOWN_FORMAT";
	public static final String FORMATTER_VIDEO_INFO = "FORMATTER_VIDEO_INFO";
	public static final String FORMATTER_AUDIO_OPTIONS = "FORMATTER_AUDIO_OPTIONS";
	public static final String FORMATTER_VIDEO_OPTIONS = "FORMATTER_VIDEO_OPTIONS";
	public static final String FORMATTER_SCALING_OPTIONS = "FORMATTER_SCALING_OPTIONS";

	public static final String SCALING_METHOD_CROP = "SCALING_METHOD_CROP";
	public static final String SCALING_METHOD_FILL = "SCALING_METHOD_FILL";
	public static final String SCALING_METHOD_FIT = "SCALING_METHOD_FIT";
	public static final String SCALING_METHOD_SCALE = "SCALING_METHOD_SCALE";

	public static final String MENU_FILE = "MENU_FILE";
	public static final String MENU_VIDEOS = "MENU_VIDEOS";
	public static final String MENU_WINDOW = "MENU_WINDOW";
	public static final String MENU_HELP = "MENU_HELP";

	public static final String ACTION_ADD_VIDEO_FILE = "ACTION_ADD_VIDEO_FILE";
	public static final String ACTION_ADD_VIDEO_FILE_DIALOG_TEXT = "ACTION_ADD_VIDEO_FILE_DIALOG_TEXT";
	public static final String ACTION_ADD_VIDEO_FILE_PROGRESS_MESSAGE = "ACTION_ADD_VIDEO_FILE_PROGRESS_MESSAGE";
	public static final String ACTION_ADD_DVD = "ACTION_ADD_DVD";
	public static final String ACTION_ADD_DVD_FROM_DEVICE = "ACTION_ADD_DVD_FROM_DEVICE";
	public static final String ACTION_ADD_DVD_FROM_DEVICE_PROGRESS_MESSAGE = "ACTION_ADD_DVD_FROM_DEVICE_PROGRESS_MESSAGE";
	public static final String ACTION_ADD_DVD_FROM_ISO = "ACTION_ADD_DVD_FROM_ISO";
	public static final String ACTION_ADD_DVD_FROM_ISO_DIALOG_TEXT = "ACTION_ADD_DVD_FROM_ISO_DIALOG_TEXT";
	public static final String ACTION_ADD_DVD_FROM_ISO_PROGRESS_MESSAGE = "ACTION_ADD_DVD_FROM_ISO_PROGRESS_MESSAGE";
	public static final String ACTION_ADD_DVD_FROM_DIRECTORY = "ACTION_ADD_DVD_FROM_DIRECTORY";
	public static final String ACTION_ADD_DVD_FROM_DIRECTORY_DIALOG_TEXT = "ACTION_ADD_DVD_FROM_DIRECTORY_DIALOG_TEXT";
	public static final String ACTION_ADD_DVD_FROM_DIRECTORY_PROGRESS_MESSAGE = "ACTION_ADD_DVD_FROM_DIRECTORY_PROGRESS_MESSAGE";

	public static final String ACTION_REMOVEALL = "ACTION_REMOVEALL";
	public static final String ACTION_REMOVE = "ACTION_REMOVE";
	public static final String ACTION_PREVIEW = "ACTION_PREVIEW";
	public static final String ACTION_CONVERT = "ACTION_CONVERT";
	public static final String ACTION_CONVERT_ALL = "ACTION_CONVERT_ALL";
	public static final String ACTION_CONVERT_FAILED = "ACTION_CONVERT_FAILED";
	public static final String ACTION_SHOW_JOB_QUEUE = "ACTION_SHOW_JOB_QUEUE";
	public static final String ACTION_CLOSE = "ACTION_CLOSE";
	public static final String ACTION_EXIT = "ACTION_EXIT";
	public static final String ACTION_SHOW_ABOUT_DIALOG = "ACTION_SHOW_ABOUT_DIALOG";
	public static final String ACTION_PREFERENCES = "ACTION_PREFERENCES";

	public static final String DVD_DIALOG_LABEL = "DVD_DIALOG_LABEL";
	public static final String DVD_DIALOG_TITLE = "DVD_DIALOG_TITLE";

	public static final String ADDDVD_DIRECTORY_NOT_VALID = "ADDDVD_DIRECTORY_NOT_VALID";
	public static final String ADDDVD_ISO_NOT_VALID = "ADDDVD_ISO_NOT_VALID";

	public static final String PREVIEW_FAILED = "PREVIEW_FAILED";
	public static final String PREVIEW_DIALOG_TITLE = "PREVIEW_DIALOG_TITLE";
	public static final String PREVIEW_DIALOG_MESSAGE = "PREVIEW_DIALOG_MESSAGE";

	public static final String PROGRESS_DIALOG_TITLE = "PROGRESS_DIALOG_TITLE";

	public static final String REPLACE_FILE_DIALOG_MESSAGE = "REPLACE_FILE_DIALOG_MESSAGE";
	public static final String REPLACE_FILE_DIALOG_ALWAYS = "REPLACE_FILE_DIALOG_ALWAYS";
	public static final String REPLACE_FILE_DIALOG_REPLACE = "REPLACE_FILE_DIALOG_REPLACE";
	public static final String REPLACE_FILE_DIALOG_CANCEL = "REPLACE_FILE_DIALOG_CANCEL";

	public static final String PREFERENCE_TITLE = "PREFERENCE_TITLE";
	public static final String PREFERENCE_BROWSE = "PREFERENCE_BROWSE";
	public static final String PREFERENCE_MPLAYER_EMPLACEMENT = "PREFERENCE_MPLAYER_EMPLACEMENT";
	public static final String PREFERENCE_MP4BOX_EMPLACEMENT = "PREFERENCE_MP4BOX_EMPLACEMENT";
	public static final String PREFERENCE_RESTART_WARNING = "PREFERENCE_RESTART_WARNING";
	public static final String PREFERENCE_DIRECTORY_GROUP = "PREFERENCE_DIRECTORY_GROUP";
	public static final String PREFERENCE_OPTION_GROUP = "PREFERENCE_OPTION_GROUP";
	public static final String PREFERENCE_REPLACE = "PREFERENCE_REPLACE";

	public static final String PROFILE_TEMP = "PROFILE_TEMP";
	public static final String PROFILE_CUSTOM = "PROFILE_CUSTOM";
	public static final String PROFILE_IPOD_H264 = "PROFILE_IPOD_H264";
	public static final String PROFILE_IPOD_XVID = "PROFILE_IPOD_XVID";

	public static final String OPTIONS_PROFILE = "OPTIONS_PROFILE";
	public static final String OPTIONS_VIDEO = "OPTIONS_VIDEO";
	public static final String OPTIONS_AUDIO = "OPTIONS_AUDIO";
	public static final String OPTIONS_BITRATE = "OPTIONS_BITRATE";
	public static final String OPTIONS_CHANNEL = "OPTIONS_CHANNEL";
	public static final String OPTIONS_BITRATE_FORMAT_VALUE = "OPTIONS_BITRATE_FORMAT_VALUE";
	public static final String OPTIONS_BITRATE_UNIT = "OPTIONS_BITRATE_UNIT";
	public static final String OPTIONS_DIMENSION = "OPTIONS_DIMENSION";
	public static final String OPTIONS_TYPE = "OPTIONS_TYPE";
	public static final String OPTIONS_AUDIO_CODEC = "OPTIONS_AUDIO_CODEC";
	public static final String OPTIONS_VIDEO_CODEC = "OPTIONS_VIDEO_CODEC";
	public static final String OPTIONS_VARIABLE_METHOD = "OPTIONS_VARIABLE_METHOD";
	public static final String OPTIONS_VARIABLE_QUALITY = "OPTIONS_VARIABLE_QUALITY";
	public static final String OPTIONS_SAMPLE_RATE = "OPTIONS_SAMPLE_RATE";
	public static final String OPTIONS_TWO_PASS = "OPTIONS_TWO_PASS";
	public static final String OPTIONS_FRAME_RATE = "OPTIONS_FRAME_RATE";
	public static final String OPTIONS_NTSC_FILM = "OPTIONS_NTSC_FILM";
	public static final String OPTIONS_NTSC = "OPTIONS_NTSC";
	public static final String OPTIONS_PAL = "OPTIONS_PAL";
	public static final String OPTIONS_FPS = "OPTIONS_FPS";
	public static final String OPTIONS_FRAME_RATE_CUSTOM_MESSAGE = "OPTIONS_FRAME_RATE_CUSTOM_MESSAGE";
	public static final String OPTIONS_FRAME_RATE_NO_CHANGE = "OPTIONS_FRAME_RATE_NO_CHANGE";
	public static final String OPTIONS_VIDEO_SCALING_METHODE = "OPTIONS_VIDEO_SCALING_METHODE";

	public static final String OPTIONS_CHANNEL_MODE_AUTO = "OPTIONS_CHANNEL_MODE_AUTO";
	public static final String OPTIONS_CHANNEL_MODE_STEREO = "OPTIONS_CHANNEL_MODE_STEREO";
	public static final String OPTIONS_CHANNEL_MODE_JOIN_STEREO = "OPTIONS_CHANNEL_MODE_JOIN_STEREO";
	public static final String OPTIONS_CHANNEL_MODE_DUAL = "OPTIONS_CHANNEL_MODE_DUAL";
	public static final String OPTIONS_CHANNEL_MODE_MONO = "OPTIONS_CHANNEL_MODE_MONO";
	public static final String OPTIONS_CHANNEL_MODE_FULL51 = "OPTIONS_CHANNEL_MODE_FULL51";
	public static final String OPTIONS_CHANNEL_MODE_SURROUND = "OPTIONS_CHANNEL_MODE_SURROUND";

	public static final String OPTIONS_LAME_FAST = "OPTIONS_LAME_FAST";
	public static final String OPTIONS_LAME_TYPE_AVERAGE = "OPTIONS_LAME_TYPE_AVERAGE";
	public static final String OPTIONS_LAME_TYPE_VARIABLE = "OPTIONS_LAME_TYPE_VARIABLE";
	public static final String OPTIONS_LAME_TYPE_CONSTANT = "OPTIONS_LAME_TYPE_CONSTANT";
	public static final String OPTIONS_LAME_VARIABLE_METHOD_CBR = "OPTIONS_LAME_VARIABLE_METHOD_CBR";
	public static final String OPTIONS_LAME_VARIABLE_METHOD_MT = "OPTIONS_LAME_VARIABLE_METHOD_MT";
	public static final String OPTIONS_LAME_VARIABLE_METHOD_RH = "OPTIONS_LAME_VARIABLE_METHOD_RH";
	public static final String OPTIONS_LAME_VARIABLE_METHOD_ABR = "OPTIONS_LAME_VARIABLE_METHOD_ABR";
	public static final String OPTIONS_LAME_VARIABLE_METHOD_MTRH = "OPTIONS_LAME_VARIABLE_METHOD_MTRH";

	public static final String OPTIONS_FAAC_OBJECT_TYPE = "OPTIONS_FAAC_OBJECT_TYPE";
	public static final String OPTIONS_FAAC_OBJECT_TYPE_MAIN = "OPTIONS_FAAC_OBJECT_TYPE_MAIN";
	public static final String OPTIONS_FAAC_OBJECT_TYPE_LOW = "OPTIONS_FAAC_OBJECT_TYPE_LOW";
	public static final String OPTIONS_FAAC_OBJECT_TYPE_SSR = "OPTIONS_FAAC_OBJECT_TYPE_SSR";
	public static final String OPTIONS_FAAC_OBJECT_TYPE_LTP = "OPTIONS_FAAC_OBJECT_TYPE_LTP";
	public static final String OPTIONS_FAAC_MPEG_VERSION = "OPTIONS_FAAC_MPEG_VERSION";
	public static final String OPTIONS_FAAC_MPEG_VERSION_2 = "OPTIONS_FAAC_MPEG_VERSION_2";
	public static final String OPTIONS_FAAC_MPEG_VERSION_4 = "OPTIONS_FAAC_MPEG_VERSION_4";

	public static final String OPTIONS_X264_CABAC = "OPTIONS_X264_CABAC";
	public static final String OPTIONS_X264_TRELLIS = "OPTIONS_X264_TRELLIS";
	public static final String OPTIONS_X264_PARTITIONS = "OPTIONS_X264_PARTITIONS";
	public static final String OPTIONS_X264_LEVEL = "OPTIONS_X264_LEVEL";
	public static final String OPTIONS_X264_LEVEL10 = "OPTIONS_X264_LEVEL10";
	public static final String OPTIONS_X264_LEVEL11 = "OPTIONS_X264_LEVEL11";
	public static final String OPTIONS_X264_LEVEL12 = "OPTIONS_X264_LEVEL12";
	public static final String OPTIONS_X264_LEVEL13 = "OPTIONS_X264_LEVEL13";
	public static final String OPTIONS_X264_LEVEL1B = "OPTIONS_X264_LEVEL1B";
	public static final String OPTIONS_X264_LEVEL20 = "OPTIONS_X264_LEVEL20";
	public static final String OPTIONS_X264_LEVEL21 = "OPTIONS_X264_LEVEL21";
	public static final String OPTIONS_X264_LEVEL22 = "OPTIONS_X264_LEVEL22";
	public static final String OPTIONS_X264_LEVEL30 = "OPTIONS_X264_LEVEL30";
	public static final String OPTIONS_X264_LEVEL31 = "OPTIONS_X264_LEVEL31";
	public static final String OPTIONS_X264_LEVEL32 = "OPTIONS_X264_LEVEL32";
	public static final String OPTIONS_X264_LEVEL40 = "OPTIONS_X264_LEVEL40";
	public static final String OPTIONS_X264_LEVEL41 = "OPTIONS_X264_LEVEL41";
	public static final String OPTIONS_X264_LEVEL42 = "OPTIONS_X264_LEVEL42";
	public static final String OPTIONS_X264_LEVEL50 = "OPTIONS_X264_LEVEL50";
	public static final String OPTIONS_X264_LEVEL51 = "OPTIONS_X264_LEVEL51";
	public static final String OPTIONS_X264_MOTION_ESTIMATION = "OPTIONS_X264_MOTION_ESTIMATION";
	public static final String OPTIONS_X264_MOTION_ESTIMATION_DIAMON = "OPTIONS_X264_MOTION_ESTIMATION_DIAMON";
	public static final String OPTIONS_X264_MOTION_ESTIMATION_HEXAGON = "OPTIONS_X264_MOTION_ESTIMATION_HEXAGON";
	public static final String OPTIONS_X264_MOTION_ESTIMATION_UNEVEN = "OPTIONS_X264_MOTION_ESTIMATION_UNEVEN";
	public static final String OPTIONS_X264_MOTION_ESTIMATION_EXHAUSTIVE = "OPTIONS_X264_MOTION_ESTIMATION_EXHAUSTIVE";
	public static final String OPTIONS_X264_REFERENCE_FRAME = "OPTIONS_X264_REFERENCE_FRAME";
	public static final String OPTIONS_X264_SUBPEL_REFINEMENT_QUALITY = "OPTIONS_X264_SUBPEL_REFINEMENT_QUALITY";
	public static final String OPTIONS_X264_BFRAME = "OPTIONS_X264_BFRAME";

	public static final String OPTIONS_XVID_TRELLIS = "OPTIONS_XVID_TRELLIS";
	public static final String OPTIONS_XVID_BFRAME = "OPTIONS_XVID_BFRAME";
	public static final String OPTIONS_XVID_CARTOON = "OPTIONS_XVID_CARTOON";
	public static final String OPTIONS_XVID_QUARTER_PIXEL = "OPTIONS_XVID_QUARTER_PIXEL";

	private static final String LOCALE_BUNDLE_NAME = "locale";
	private static final String FORMAT_BUNDLE_NAME = "format";
	private static final String LANGUAGE_BUNDLE_NAME = "language";
	private static final String VERSION_BUNDLE_NAME = "version";

	private static ResourceBundle localBundle;
	private static ResourceBundle formatBundle;
	private static ResourceBundle languageBundle;
	private static ResourceBundle versionBundle;

	/**
	 * Private constructor.
	 */
	private Localization() {

	}

	/**
	 * Gets the format string for the given key.
	 * 
	 * @param formatID
	 *            the key (must be a FORMAT_ constants)
	 * @return the localized string
	 */
	public static String getLocalizedFormat(String formatID) {
		if (formatBundle == null) {
			init();
		}

		try {
			return formatBundle.getString(formatID);
		} catch (MissingResourceException e) {
			return Localization
					.getString(Localization.FORMATTER_UNKNOWN_FORMAT);
		}

	}

	/**
	 * Gets the format string for the given audio format.
	 * 
	 * @param format
	 *            the audio format.
	 * @return the localized string.
	 */
	public static String getLocalizedFormat(AudioFormat format) {
		if (format != null && format.getFormatID() != null) {
			return getLocalizedFormat(format.getFormatID());
		} else {
			return Localization
					.getString(Localization.FORMATTER_UNKNOWN_FORMAT);
		}
	}

	/**
	 * Gets the format string for the given video format.
	 * 
	 * @param format
	 *            the video format.
	 * @return the localized string.
	 */
	public static String getLocalizedFormat(VideoFormat format) {
		if (format != null && format.getFormatID() != null) {
			return getLocalizedFormat(format.getFormatID());
		} else {
			return Localization
					.getString(Localization.FORMATTER_UNKNOWN_FORMAT);
		}
	}

	/**
	 * Gets the format string for the given demuxer.
	 * 
	 * @param demuxer
	 *            the demuxer.
	 * @return the localized string.
	 */
	public static String getLocalizedFormat(VideoDemuxer demuxer) {
		if (demuxer != null && demuxer.getDemuxerID() != null) {
			return getLocalizedFormat(demuxer.getDemuxerID());
		} else {
			return Localization
					.getString(Localization.FORMATTER_UNKNOWN_FORMAT);
		}
	}

	/**
	 * Return the localized language name.
	 * 
	 * @param languageId
	 *            the language id from ISO629.
	 * @return the localized language name.
	 */
	public static String getLocalizedLanguage(String languageId) {
		if (languageBundle == null) {
			init();
		}
		try {
			return languageBundle.getString(languageId);
		} catch (MissingResourceException e) {
			return languageId;
		}
	}

	/**
	 * Return the localized text version.
	 * 
	 * @return the text version.
	 */
	public static String getLocalizedVersion(String key) {
		if (versionBundle == null) {
			init();
		}
		try {
			return versionBundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Gets a string for the given key.
	 * 
	 * @param key
	 *            the key
	 * @return the localized string
	 */
	public static String getString(String key) {
		if (localBundle == null) {
			init();
		}
		try {
			return localBundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Gets a string for the given key.
	 * 
	 * @param key
	 *            the key
	 * @param args
	 *            Arguments referenced by the format specifiers in the format
	 *            string. If there are more arguments than format specifiers,
	 *            the extra arguments are ignored. The number of arguments is
	 *            variable and may be zero. The maximum number of arguments is
	 *            limited by the maximum dimension of a Java array as defined by
	 *            the <a href="http://java.sun.com/docs/books/vmspec/">Java
	 *            Virtual Machine Specification</a>. The behaviour on a
	 *            <tt>null</tt> argument depends on the <a
	 *            href="../util/Formatter.html#syntax">conversion</a>.
	 * 
	 * @return the lozalized string
	 */
	public static String getString(String key, Object... args) {
		if (localBundle == null) {
			init();

		}
		String localeString = null;
		try {
			localeString = localBundle.getString(key);
		} catch (MissingResourceException e) {
			localeString = "";
		}

		return String.format(localeString, args);
	}

	/**
	 * Initiate the bundle.
	 */
	private static void init() {
		localBundle = PropertyResourceBundle.getBundle(LOCALE_BUNDLE_NAME);
		formatBundle = PropertyResourceBundle.getBundle(FORMAT_BUNDLE_NAME);
		languageBundle = PropertyResourceBundle.getBundle(LANGUAGE_BUNDLE_NAME);
		versionBundle = PropertyResourceBundle.getBundle(VERSION_BUNDLE_NAME);
	}

}
