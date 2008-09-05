package net.homeip.entreprisesmd.mvconv.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.ComponentMissingException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.DVDNotAvailableException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.GrabXvPortException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerNotFoundException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PaletteException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoDemuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.XvPortNotAvailableException;

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
	public static final String VERSION = "VERSION"; //$NON-NLS-1$
	public static final String VERSION_TEXT = "VERSION_TEXT"; //$NON-NLS-1$
	public static final String VERSION_BUILD = "VERSION_BUILD"; //$NON-NLS-1$

	public static final String APPLICATION_NAME = "APPLICATION_NAME"; //$NON-NLS-1$
	public static final String APPLICATION_DESCRIPTION = "APPLICATION_DESCRIPTION"; //$NON-NLS-1$
	public static final String APPLICATION_COPYRIGHT = "APPLICATION_COPYRIGHT"; //$NON-NLS-1$
	public static final String APPLICATION_WEBSITE = "APPLICATION_WEBSITE"; //$NON-NLS-1$
	public static final String NA = "NA"; //$NON-NLS-1$

	public static final String MPLAYER_NOT_FOUND = "MPLAYER_NOT_FOUND"; //$NON-NLS-1$
	public static final String MPLAYER_DVDDEVICE_NOT_AVAILABLE = "MPLAYER_DVDDEVICE_NOT_AVAILABLE"; //$NON-NLS-1$
	public static final String MPLAYER_GRAP_XV_PORT = "MPLAYER_GRAP_XV_PORT"; //$NON-NLS-1$
	public static final String MPLAYER_UNSUPPORTED_FORMAT = "MPLAYER_UNSUPPORTED_FORMAT"; //$NON-NLS-1$
	public static final String MPLAYER_PALETTE_ERROR = "MPLAYER_PALETTE_ERROR"; //$NON-NLS-1$
	public static final String MPLAYER_COMPONENT_MISSING = "MPLAYER_COMPONENT_MISSING"; //$NON-NLS-1$
	public static final String MPLAYER_XVIDEO_NOT_AVAILABLE = "MPLAYER_XVIDEO_NOT_AVAILABLE"; //$NON-NLS-1$

	public static final String DURATION_HOURS = "DURATION_HOURS"; //$NON-NLS-1$
	public static final String DURATION_MINUTES = "DURATION_MINUTES"; //$NON-NLS-1$
	public static final String DURATION_SECONDS = "DURATION_SECONDS"; //$NON-NLS-1$

	public static final String FILE_VIDEO = "FILE_VIDEO"; //$NON-NLS-1$
	public static final String FILE_EXTENTION_VOB = "FILE_EXTENTION_VOB"; //$NON-NLS-1$
	public static final String FILE_EXTENTION_ISO = "FILE_EXTENTION_ISO"; //$NON-NLS-1$
	public static final String FILE_EXTENTION_ALL = "FILE_EXTENTION_ALL"; //$NON-NLS-1$

	public static final String SUMMARY_TITLE = "SUMMARY_TITLE"; //$NON-NLS-1$
	public static final String VIDEO_OPTIONS_TITLE = "VIDEO_OPTIONS_TITLE"; //$NON-NLS-1$
	public static final String VIDEOCODEC_OPTIONS_TITLE = "VIDEOCODEC_OPTIONS_TITLE"; //$NON-NLS-1$
	public static final String AUDIO_OPTIONS_TITLE = "AUDIO_OPTIONS_TITLE"; //$NON-NLS-1$
	public static final String CUSTOM_OPTIONS_TITLE = "CUSTOM_OPTIONS_TITLE"; //$NON-NLS-1$
	public static final String WELCOME_TITLE = "WELCOME_TITLE"; //$NON-NLS-1$

	public static final String CONVERTJOB_DESCRIPTION = "CONVERTJOB_DESCRIPTION"; //$NON-NLS-1$
	public static final String CONVERTJOB_PROGRESS_DESCRIPTION = "CONVERTJOB_PROGRESS_DESCRIPTION"; //$NON-NLS-1$
	public static final String CONVERTJOB_LESS_THAN_A_MINUTE = "CONVERTJOB_LESS_THAN_A_MINUTE"; //$NON-NLS-1$
	public static final String CONVERTJOB_FAILED_DESCRIPTION = "CONVERTJOB_FAILED_DESCRIPTION"; //$NON-NLS-1$

	public static final String JOB_QUEUE_WINDOW_TITLE = "JOB_QUEUE_WINDOW_TITLE"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_CLOSE_BUTTON = "JOB_QUEUE_WINDOW_CLOSE_BUTTON"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_CLEAR_BUTTON = "JOB_QUEUE_WINDOW_CLEAR_BUTTON"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_CLEAR_COMPLETED = "JOB_QUEUE_WINDOW_CLEAR_COMPLETED"; //$NON-NLS-1$

	public static final String JOB_QUEUE_WINDOW_CLEAR = "JOB_QUEUE_WINDOW_CLEAR"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_CANCEL = "JOB_QUEUE_WINDOW_CANCEL"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_REMOVE = "JOB_QUEUE_WINDOW_REMOVE"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_OPEN = "JOB_QUEUE_WINDOW_OPEN"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_MOREINFO = "JOB_QUEUE_WINDOW_MOREINFO"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_START = "JOB_QUEUE_WINDOW_START"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_QUEUED = "JOB_QUEUE_WINDOW_QUEUED"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_COMPLETED = "JOB_QUEUE_WINDOW_COMPLETED"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_FAILED = "JOB_QUEUE_WINDOW_FAILED"; //$NON-NLS-1$
	public static final String JOB_QUEUE_WINDOW_CANCELED = "JOB_QUEUE_WINDOW_CANCELED"; //$NON-NLS-1$
	public static final String JOB_QUEUE_CONFIRM_QUIT = "JOB_QUEUE_CONFIRM_QUIT"; //$NON-NLS-1$

	public static final String INPUTOUTPUT_INPUT = "INPUTOUTPUT_INPUT"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_OUPUT = "INPUTOUTPUT_OUPUT"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_BROWSE = "INPUTOUTPUT_BROWSE"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_LANGAGE = "INPUTOUTPUT_LANGAGE"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_TITLE = "INPUTOUTPUT_TITLE"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_SUBTITLE = "INPUTOUTPUT_SUBTITLE"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_FILENAME = "INPUTOUTPUT_FILENAME"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_VIDEO = "INPUTOUTPUT_VIDEO"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_AUDIO = "INPUTOUTPUT_AUDIO"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_INPUTVIDEOFILE_DESCRIPTION = "INPUTOUTPUT_INPUTVIDEOFILE_DESCRIPTION"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_INPUTVIDEODVD_DESCRIPTION = "INPUTOUTPUT_INPUTVIDEODVD_DESCRIPTION"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_TRACK = "INPUTOUTPUT_TRACK"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_NONE = "INPUTOUTPUT_NONE"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_DEFAULT = "INPUTOUTPUT_DEFAULT"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_RETRIEVE_INFO_FAILED = "INPUTOUTPUT_RETRIEVE_INFO_FAILED"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_FILE_NO_MORE_EXIST = "INPUTOUTPUT_FILE_NO_MORE_EXIST"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_OUTPUT_FILEDIALOG_TITLE = "INPUTOUTPUT_OUTPUT_FILEDIALOG_TITLE"; //$NON-NLS-1$

	public static final String INPUTOUTPUT_MORE_OPTIONS = "INPUTOUTPUT_MORE_OPTIONS"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_MORE_OPTIONS_DIALOG_TITLE = "INPUTOUTPUT_MORE_OPTIONS_DIALOG_TITLE"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_ASPECT_RATIO = "INPUTOUTPUT_ASPECT_RATIO"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_ASPECT_RATIO_CUSTOM_MESSAGE = "INPUTOUTPUT_ASPECT_RATIO_CUSTOM_MESSAGE"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_ASPECT_RATIO_CUSTOM_FORMAT = "INPUTOUTPUT_ASPECT_RATIO_CUSTOM_FORMAT"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_ASPECT_RATIO_KEEP = "INPUTOUTPUT_ASPECT_RATIO_KEEP"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_ASPECT_RATIO_4_3 = "INPUTOUTPUT_ASPECT_RATIO_4_3"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_ASPECT_RATIO_16_9 = "INPUTOUTPUT_ASPECT_RATIO_16_9"; //$NON-NLS-1$
	public static final String INPUTOUTPUT_ASPECT_RATIO_CUSTOM = "INPUTOUTPUT_ASPECT_RATIO_CUSTOM"; //$NON-NLS-1$

	public static final String FORMATTER_UNKNOWN_FORMAT = "FORMATTER_UNKNOWN_FORMAT"; //$NON-NLS-1$
	public static final String FORMATTER_VIDEO_INFO = "FORMATTER_VIDEO_INFO"; //$NON-NLS-1$
	public static final String FORMATTER_AUDIO_OPTIONS = "FORMATTER_AUDIO_OPTIONS"; //$NON-NLS-1$
	public static final String FORMATTER_VIDEO_OPTIONS = "FORMATTER_VIDEO_OPTIONS"; //$NON-NLS-1$
	public static final String FORMATTER_SCALING_OPTIONS = "FORMATTER_SCALING_OPTIONS"; //$NON-NLS-1$

	public static final String SCALING_METHOD_CROP = "SCALING_METHOD_CROP"; //$NON-NLS-1$
	public static final String SCALING_METHOD_FILL = "SCALING_METHOD_FILL"; //$NON-NLS-1$
	public static final String SCALING_METHOD_FIT = "SCALING_METHOD_FIT"; //$NON-NLS-1$
	public static final String SCALING_METHOD_SCALE = "SCALING_METHOD_SCALE"; //$NON-NLS-1$

	public static final String MENU_FILE = "MENU_FILE"; //$NON-NLS-1$
	public static final String MENU_VIDEOS = "MENU_VIDEOS"; //$NON-NLS-1$
	public static final String MENU_WINDOW = "MENU_WINDOW"; //$NON-NLS-1$
	public static final String MENU_HELP = "MENU_HELP"; //$NON-NLS-1$

	public static final String ACTION_ADD_VIDEO_FILE = "ACTION_ADD_VIDEO_FILE"; //$NON-NLS-1$
	public static final String ACTION_ADD_VIDEO_FILE_DIALOG_TEXT = "ACTION_ADD_VIDEO_FILE_DIALOG_TEXT"; //$NON-NLS-1$
	public static final String ACTION_ADD_VIDEO_FILE_PROGRESS_MESSAGE = "ACTION_ADD_VIDEO_FILE_PROGRESS_MESSAGE"; //$NON-NLS-1$
	public static final String ACTION_ADD_DVD = "ACTION_ADD_DVD"; //$NON-NLS-1$
	public static final String ACTION_ADD_DVD_FROM_DEVICE = "ACTION_ADD_DVD_FROM_DEVICE"; //$NON-NLS-1$
	public static final String ACTION_ADD_DVD_FROM_DEVICE_PROGRESS_MESSAGE = "ACTION_ADD_DVD_FROM_DEVICE_PROGRESS_MESSAGE"; //$NON-NLS-1$
	public static final String ACTION_ADD_DVD_FROM_ISO = "ACTION_ADD_DVD_FROM_ISO"; //$NON-NLS-1$
	public static final String ACTION_ADD_DVD_FROM_ISO_DIALOG_TEXT = "ACTION_ADD_DVD_FROM_ISO_DIALOG_TEXT"; //$NON-NLS-1$
	public static final String ACTION_ADD_DVD_FROM_ISO_PROGRESS_MESSAGE = "ACTION_ADD_DVD_FROM_ISO_PROGRESS_MESSAGE"; //$NON-NLS-1$
	public static final String ACTION_ADD_DVD_FROM_DIRECTORY = "ACTION_ADD_DVD_FROM_DIRECTORY"; //$NON-NLS-1$
	public static final String ACTION_ADD_DVD_FROM_DIRECTORY_DIALOG_TEXT = "ACTION_ADD_DVD_FROM_DIRECTORY_DIALOG_TEXT"; //$NON-NLS-1$
	public static final String ACTION_ADD_DVD_FROM_DIRECTORY_PROGRESS_MESSAGE = "ACTION_ADD_DVD_FROM_DIRECTORY_PROGRESS_MESSAGE"; //$NON-NLS-1$

	public static final String ACTION_REMOVEALL = "ACTION_REMOVEALL"; //$NON-NLS-1$
	public static final String ACTION_REMOVE = "ACTION_REMOVE"; //$NON-NLS-1$
	public static final String ACTION_PREVIEW = "ACTION_PREVIEW"; //$NON-NLS-1$
	public static final String ACTION_CONVERT = "ACTION_CONVERT"; //$NON-NLS-1$
	public static final String ACTION_CONVERT_ALL = "ACTION_CONVERT_ALL"; //$NON-NLS-1$
	public static final String ACTION_CONVERT_FAILED = "ACTION_CONVERT_FAILED"; //$NON-NLS-1$
	public static final String ACTION_SHOW_JOB_QUEUE = "ACTION_SHOW_JOB_QUEUE"; //$NON-NLS-1$
	public static final String ACTION_CLOSE = "ACTION_CLOSE"; //$NON-NLS-1$
	public static final String ACTION_EXIT = "ACTION_EXIT"; //$NON-NLS-1$
	public static final String ACTION_SHOW_ABOUT_DIALOG = "ACTION_SHOW_ABOUT_DIALOG"; //$NON-NLS-1$
	public static final String ACTION_PREFERENCES = "ACTION_PREFERENCES"; //$NON-NLS-1$

	public static final String DVD_DIALOG_LABEL = "DVD_DIALOG_LABEL"; //$NON-NLS-1$
	public static final String DVD_DIALOG_TITLE = "DVD_DIALOG_TITLE"; //$NON-NLS-1$

	public static final String ADDDVD_DIRECTORY_NOT_VALID = "ADDDVD_DIRECTORY_NOT_VALID"; //$NON-NLS-1$
	public static final String ADDDVD_ISO_NOT_VALID = "ADDDVD_ISO_NOT_VALID"; //$NON-NLS-1$

	public static final String PREVIEW_FAILED = "PREVIEW_FAILED"; //$NON-NLS-1$
	public static final String PREVIEW_DIALOG_TITLE = "PREVIEW_DIALOG_TITLE"; //$NON-NLS-1$
	public static final String PREVIEW_DIALOG_MESSAGE = "PREVIEW_DIALOG_MESSAGE"; //$NON-NLS-1$

	public static final String PROGRESS_DIALOG_TITLE = "PROGRESS_DIALOG_TITLE"; //$NON-NLS-1$

	public static final String REPLACE_FILE_DIALOG_MESSAGE = "REPLACE_FILE_DIALOG_MESSAGE"; //$NON-NLS-1$
	public static final String REPLACE_FILE_DIALOG_ALWAYS = "REPLACE_FILE_DIALOG_ALWAYS"; //$NON-NLS-1$
	public static final String REPLACE_FILE_DIALOG_REPLACE = "REPLACE_FILE_DIALOG_REPLACE"; //$NON-NLS-1$
	public static final String REPLACE_FILE_DIALOG_CANCEL = "REPLACE_FILE_DIALOG_CANCEL"; //$NON-NLS-1$

	public static final String PREFERENCE_TITLE = "PREFERENCE_TITLE"; //$NON-NLS-1$
	public static final String PREFERENCE_BROWSE = "PREFERENCE_BROWSE"; //$NON-NLS-1$
	public static final String PREFERENCE_MPLAYER_EMPLACEMENT = "PREFERENCE_MPLAYER_EMPLACEMENT"; //$NON-NLS-1$
	public static final String PREFERENCE_MP4BOX_EMPLACEMENT = "PREFERENCE_MP4BOX_EMPLACEMENT"; //$NON-NLS-1$
	public static final String PREFERENCE_RESTART_WARNING = "PREFERENCE_RESTART_WARNING"; //$NON-NLS-1$
	public static final String PREFERENCE_DIRECTORY_GROUP = "PREFERENCE_DIRECTORY_GROUP"; //$NON-NLS-1$
	public static final String PREFERENCE_OPTION_GROUP = "PREFERENCE_OPTION_GROUP"; //$NON-NLS-1$
	public static final String PREFERENCE_MPLAYER_GROUP = "PREFERENCE_MPLAYER_GROUP"; //$NON-NLS-1$
	public static final String PREFERENCE_REPLACE = "PREFERENCE_REPLACE"; //$NON-NLS-1$
	public static final String PREFERENCE_VIDEO_OUTPUT_DEVICE = "PREFERENCE_VIDEO_OUTPUT_DEVICE"; //$NON-NLS-1$

	public static final String PROFILE_TEMP = "PROFILE_TEMP"; //$NON-NLS-1$
	public static final String PROFILE_CUSTOM = "PROFILE_CUSTOM"; //$NON-NLS-1$
	public static final String PROFILE_IPOD_H264 = "PROFILE_IPOD_H264"; //$NON-NLS-1$
	public static final String PROFILE_IPOD_XVID = "PROFILE_IPOD_XVID"; //$NON-NLS-1$

	public static final String OPTIONS_PROFILE = "OPTIONS_PROFILE"; //$NON-NLS-1$
	public static final String OPTIONS_VIDEO = "OPTIONS_VIDEO"; //$NON-NLS-1$
	public static final String OPTIONS_AUDIO = "OPTIONS_AUDIO"; //$NON-NLS-1$
	public static final String OPTIONS_BITRATE = "OPTIONS_BITRATE"; //$NON-NLS-1$
	public static final String OPTIONS_CHANNEL = "OPTIONS_CHANNEL"; //$NON-NLS-1$
	public static final String OPTIONS_BITRATE_FORMAT_VALUE = "OPTIONS_BITRATE_FORMAT_VALUE"; //$NON-NLS-1$
	public static final String OPTIONS_BITRATE_UNIT = "OPTIONS_BITRATE_UNIT"; //$NON-NLS-1$
	public static final String OPTIONS_DIMENSION = "OPTIONS_DIMENSION"; //$NON-NLS-1$
	public static final String OPTIONS_TYPE = "OPTIONS_TYPE"; //$NON-NLS-1$
	public static final String OPTIONS_AUDIO_CODEC = "OPTIONS_AUDIO_CODEC"; //$NON-NLS-1$
	public static final String OPTIONS_VIDEO_CODEC = "OPTIONS_VIDEO_CODEC"; //$NON-NLS-1$
	public static final String OPTIONS_VARIABLE_METHOD = "OPTIONS_VARIABLE_METHOD"; //$NON-NLS-1$
	public static final String OPTIONS_VARIABLE_QUALITY = "OPTIONS_VARIABLE_QUALITY"; //$NON-NLS-1$
	public static final String OPTIONS_SAMPLE_RATE = "OPTIONS_SAMPLE_RATE"; //$NON-NLS-1$
	public static final String OPTIONS_TWO_PASS = "OPTIONS_TWO_PASS"; //$NON-NLS-1$
	public static final String OPTIONS_FRAME_RATE = "OPTIONS_FRAME_RATE"; //$NON-NLS-1$
	public static final String OPTIONS_NTSC_FILM = "OPTIONS_NTSC_FILM"; //$NON-NLS-1$
	public static final String OPTIONS_NTSC = "OPTIONS_NTSC"; //$NON-NLS-1$
	public static final String OPTIONS_PAL = "OPTIONS_PAL"; //$NON-NLS-1$
	public static final String OPTIONS_FPS = "OPTIONS_FPS"; //$NON-NLS-1$
	public static final String OPTIONS_FRAME_RATE_CUSTOM = "OPTIONS_FRAME_RATE_CUSTOM"; //$NON-NLS-1$
	public static final String OPTIONS_FRAME_RATE_CUSTOM_MESSAGE = "OPTIONS_FRAME_RATE_CUSTOM_MESSAGE"; //$NON-NLS-1$
	public static final String OPTIONS_FRAME_RATE_NO_CHANGE = "OPTIONS_FRAME_RATE_NO_CHANGE"; //$NON-NLS-1$
	public static final String OPTIONS_VIDEO_SCALING_METHODE = "OPTIONS_VIDEO_SCALING_METHODE"; //$NON-NLS-1$

	public static final String OPTIONS_CHANNEL_MODE_AUTO = "OPTIONS_CHANNEL_MODE_AUTO"; //$NON-NLS-1$
	public static final String OPTIONS_CHANNEL_MODE_STEREO = "OPTIONS_CHANNEL_MODE_STEREO"; //$NON-NLS-1$
	public static final String OPTIONS_CHANNEL_MODE_JOIN_STEREO = "OPTIONS_CHANNEL_MODE_JOIN_STEREO"; //$NON-NLS-1$
	public static final String OPTIONS_CHANNEL_MODE_DUAL = "OPTIONS_CHANNEL_MODE_DUAL"; //$NON-NLS-1$
	public static final String OPTIONS_CHANNEL_MODE_MONO = "OPTIONS_CHANNEL_MODE_MONO"; //$NON-NLS-1$
	public static final String OPTIONS_CHANNEL_MODE_FULL51 = "OPTIONS_CHANNEL_MODE_FULL51"; //$NON-NLS-1$
	public static final String OPTIONS_CHANNEL_MODE_SURROUND = "OPTIONS_CHANNEL_MODE_SURROUND"; //$NON-NLS-1$

	public static final String OPTIONS_LAME_FAST = "OPTIONS_LAME_FAST"; //$NON-NLS-1$
	public static final String OPTIONS_LAME_TYPE_AVERAGE = "OPTIONS_LAME_TYPE_AVERAGE"; //$NON-NLS-1$
	public static final String OPTIONS_LAME_TYPE_VARIABLE = "OPTIONS_LAME_TYPE_VARIABLE"; //$NON-NLS-1$
	public static final String OPTIONS_LAME_TYPE_CONSTANT = "OPTIONS_LAME_TYPE_CONSTANT"; //$NON-NLS-1$
	public static final String OPTIONS_LAME_VARIABLE_METHOD_CBR = "OPTIONS_LAME_VARIABLE_METHOD_CBR"; //$NON-NLS-1$
	public static final String OPTIONS_LAME_VARIABLE_METHOD_MT = "OPTIONS_LAME_VARIABLE_METHOD_MT"; //$NON-NLS-1$
	public static final String OPTIONS_LAME_VARIABLE_METHOD_RH = "OPTIONS_LAME_VARIABLE_METHOD_RH"; //$NON-NLS-1$
	public static final String OPTIONS_LAME_VARIABLE_METHOD_ABR = "OPTIONS_LAME_VARIABLE_METHOD_ABR"; //$NON-NLS-1$
	public static final String OPTIONS_LAME_VARIABLE_METHOD_MTRH = "OPTIONS_LAME_VARIABLE_METHOD_MTRH"; //$NON-NLS-1$

	public static final String OPTIONS_FAAC_OBJECT_TYPE = "OPTIONS_FAAC_OBJECT_TYPE"; //$NON-NLS-1$
	public static final String OPTIONS_FAAC_OBJECT_TYPE_MAIN = "OPTIONS_FAAC_OBJECT_TYPE_MAIN"; //$NON-NLS-1$
	public static final String OPTIONS_FAAC_OBJECT_TYPE_LOW = "OPTIONS_FAAC_OBJECT_TYPE_LOW"; //$NON-NLS-1$
	public static final String OPTIONS_FAAC_OBJECT_TYPE_SSR = "OPTIONS_FAAC_OBJECT_TYPE_SSR"; //$NON-NLS-1$
	public static final String OPTIONS_FAAC_OBJECT_TYPE_LTP = "OPTIONS_FAAC_OBJECT_TYPE_LTP"; //$NON-NLS-1$
	public static final String OPTIONS_FAAC_MPEG_VERSION = "OPTIONS_FAAC_MPEG_VERSION"; //$NON-NLS-1$
	public static final String OPTIONS_FAAC_MPEG_VERSION_2 = "OPTIONS_FAAC_MPEG_VERSION_2"; //$NON-NLS-1$
	public static final String OPTIONS_FAAC_MPEG_VERSION_4 = "OPTIONS_FAAC_MPEG_VERSION_4"; //$NON-NLS-1$

	public static final String OPTIONS_X264_CABAC = "OPTIONS_X264_CABAC"; //$NON-NLS-1$
	public static final String OPTIONS_X264_TRELLIS = "OPTIONS_X264_TRELLIS"; //$NON-NLS-1$
	public static final String OPTIONS_X264_PARTITIONS = "OPTIONS_X264_PARTITIONS"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL = "OPTIONS_X264_LEVEL"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL10 = "OPTIONS_X264_LEVEL10"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL11 = "OPTIONS_X264_LEVEL11"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL12 = "OPTIONS_X264_LEVEL12"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL13 = "OPTIONS_X264_LEVEL13"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL1B = "OPTIONS_X264_LEVEL1B"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL20 = "OPTIONS_X264_LEVEL20"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL21 = "OPTIONS_X264_LEVEL21"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL22 = "OPTIONS_X264_LEVEL22"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL30 = "OPTIONS_X264_LEVEL30"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL31 = "OPTIONS_X264_LEVEL31"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL32 = "OPTIONS_X264_LEVEL32"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL40 = "OPTIONS_X264_LEVEL40"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL41 = "OPTIONS_X264_LEVEL41"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL42 = "OPTIONS_X264_LEVEL42"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL50 = "OPTIONS_X264_LEVEL50"; //$NON-NLS-1$
	public static final String OPTIONS_X264_LEVEL51 = "OPTIONS_X264_LEVEL51"; //$NON-NLS-1$
	public static final String OPTIONS_X264_MOTION_ESTIMATION = "OPTIONS_X264_MOTION_ESTIMATION"; //$NON-NLS-1$
	public static final String OPTIONS_X264_MOTION_ESTIMATION_DIAMON = "OPTIONS_X264_MOTION_ESTIMATION_DIAMON"; //$NON-NLS-1$
	public static final String OPTIONS_X264_MOTION_ESTIMATION_HEXAGON = "OPTIONS_X264_MOTION_ESTIMATION_HEXAGON"; //$NON-NLS-1$
	public static final String OPTIONS_X264_MOTION_ESTIMATION_UNEVEN = "OPTIONS_X264_MOTION_ESTIMATION_UNEVEN"; //$NON-NLS-1$
	public static final String OPTIONS_X264_MOTION_ESTIMATION_EXHAUSTIVE = "OPTIONS_X264_MOTION_ESTIMATION_EXHAUSTIVE"; //$NON-NLS-1$
	public static final String OPTIONS_X264_REFERENCE_FRAME = "OPTIONS_X264_REFERENCE_FRAME"; //$NON-NLS-1$
	public static final String OPTIONS_X264_SUBPEL_REFINEMENT_QUALITY = "OPTIONS_X264_SUBPEL_REFINEMENT_QUALITY"; //$NON-NLS-1$
	public static final String OPTIONS_X264_BFRAME = "OPTIONS_X264_BFRAME"; //$NON-NLS-1$
	public static final String OPTIONS_X264_THREAD = "OPTIONS_X264_THREAD"; //$NON-NLS-1$

	public static final String OPTIONS_XVID_TRELLIS = "OPTIONS_XVID_TRELLIS"; //$NON-NLS-1$
	public static final String OPTIONS_XVID_BFRAME = "OPTIONS_XVID_BFRAME"; //$NON-NLS-1$
	public static final String OPTIONS_XVID_CARTOON = "OPTIONS_XVID_CARTOON"; //$NON-NLS-1$
	public static final String OPTIONS_XVID_QUARTER_PIXEL = "OPTIONS_XVID_QUARTER_PIXEL"; //$NON-NLS-1$

	private static final String LOCALE_BUNDLE_NAME = "locale"; //$NON-NLS-1$
	private static final String FORMAT_BUNDLE_NAME = "format"; //$NON-NLS-1$
	private static final String LANGUAGE_BUNDLE_NAME = "language"; //$NON-NLS-1$
	private static final String VERSION_BUNDLE_NAME = "version"; //$NON-NLS-1$

	private static ResourceBundle localBundle;
	private static ResourceBundle formatBundle;
	private static ResourceBundle languageBundle;
	private static ResourceBundle versionBundle;

	/**
	 * Private constructor.
	 */
	private Localization() {
		/*
		 * Private constructore use to prevent creation of the utility class.
		 */
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
		}

		return Localization.getString(Localization.FORMATTER_UNKNOWN_FORMAT);

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
		}
		return Localization.getString(Localization.FORMATTER_UNKNOWN_FORMAT);

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
		}
		return Localization.getString(Localization.FORMATTER_UNKNOWN_FORMAT);

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
	 * Return a localized mplayer exception message.
	 * 
	 * @param exception
	 *            the Mplayer exception.
	 * @return the localized message
	 */
	public static String getLocalizedMplayerException(
			MPlayerException exception, String defaultID) {
		String key = defaultID;
		if (exception instanceof GrabXvPortException) {

			key = Localization.MPLAYER_GRAP_XV_PORT;
		} else if (exception instanceof DVDNotAvailableException) {
			return getString(Localization.MPLAYER_DVDDEVICE_NOT_AVAILABLE,
					((DVDNotAvailableException) exception).getDevice());
		} else if (exception instanceof PaletteException) {

			key = Localization.MPLAYER_PALETTE_ERROR;
		} else if (exception instanceof ComponentMissingException) {
			return getString(Localization.MPLAYER_COMPONENT_MISSING,
					((ComponentMissingException) exception).getComponentName());
		} else if (exception instanceof MPlayerNotFoundException) {
			return getString(Localization.MPLAYER_NOT_FOUND,
					((MPlayerNotFoundException) exception).getComponentName());
		} else if (exception instanceof XvPortNotAvailableException) {

			key = Localization.MPLAYER_XVIDEO_NOT_AVAILABLE;
		}
		return getString(key);
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
			localeString = ""; //$NON-NLS-1$
		}

		return String.format(localeString, args);
	}

	/**
	 * Initiate the bundle.
	 */
	private static void init() {
		localBundle = ResourceBundle.getBundle(LOCALE_BUNDLE_NAME);
		formatBundle = ResourceBundle.getBundle(FORMAT_BUNDLE_NAME);
		languageBundle = ResourceBundle.getBundle(LANGUAGE_BUNDLE_NAME);
		versionBundle = ResourceBundle.getBundle(VERSION_BUNDLE_NAME);
	}

}
