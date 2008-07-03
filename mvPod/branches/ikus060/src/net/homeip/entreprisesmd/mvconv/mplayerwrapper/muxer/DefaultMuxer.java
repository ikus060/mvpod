package net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingCommand;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.GenericOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoDemuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiofilter.AudioFilter;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.AudioEncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.VideoEncodingOptions;

/**
 * This muxer generate default AVI file and handle multiple pass.
 * 
 * @author patapouf
 * 
 */
public class DefaultMuxer implements Muxer {

	/**
	 * The property key to retrieve the os value.
	 */
	private static final String OS_NAME = "os.name";
	/**
	 * Linux OS name.
	 */
	private static final String OS_NAME_LINUX = "Linux";
	/**
	 * No output file
	 */
	private static String NO_OUPUTFILE_LINUX = "/dev/null";
	/**
	 * No output file
	 */
	private static String NO_OUPUTFILE_WIN = "nul";
	/**
	 * Define the value for no output file.
	 */
	private static String NO_OUPUTFILE = "/dev/null";
	static{
		String os = System.getProperty(OS_NAME);
		if (os.equals(OS_NAME_LINUX)) {
			NO_OUPUTFILE = NO_OUPUTFILE_LINUX;
		}else{
			NO_OUPUTFILE = NO_OUPUTFILE_WIN;
		}
		//TODO Add value for MAC OS X
	}
	

	/**
	 * Use to add arguments list according to the given encoding options.
	 * 
	 * @param command
	 *            the command to fill.
	 * @param info
	 *            the input video information.
	 * @param options
	 *            the encoding options.
	 * @param pass
	 *            the pass count.
	 */
	public static void fillCommandWithEncodingOptions(EncodingCommand command,
			VideoInfo info, EncodingOptions options, int pass) {

		// Handle Video options
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		String[] videoArgs = videoOptions.toCommandList(info, pass);
		command.add(videoArgs);

		// Handle Audio options
		AudioEncodingOptions audioOptions = options.getAudioOptions();
		String[] audioArgs = audioOptions.toCommandList(info);
		command.add(audioArgs);

		fillCommandWithGenericOptions(command, info, options);

	}

	/**
	 * Use to add arguments list according to the given generic options.
	 * 
	 * @param command
	 *            the command to fill.
	 * @param info
	 *            the input video information.
	 * @param options
	 *            the generic options.
	 */
	public static void fillCommandWithGenericOptions(EncodingCommand command,
			VideoInfo info, GenericOptions options) {

		// TODO : We must investigate a problem related to image scaling.
		// Scaling work with video file, but failed with DVD [Back to the future
		// III].

		/*
		 * Handle Video Filter.
		 * 
		 * It's important to execute this filter in a determined order. We class
		 * it in "before", "user" and "after". The scaling option must be place
		 * between before and user.
		 */
		VideoFilter[] videoFilters = options.getVideoFilter();
		List<VideoFilter> before = new ArrayList<VideoFilter>();
		List<VideoFilter> after = new ArrayList<VideoFilter>();
		List<VideoFilter> user = new ArrayList<VideoFilter>();
		for (int filterIndex = 0; filterIndex < videoFilters.length; filterIndex++) {
			if (videoFilters[filterIndex].getPriority() > 0) {
				after.add(videoFilters[filterIndex]);
			} else if (videoFilters[filterIndex].getPriority() < 0) {
				before.add(videoFilters[filterIndex]);
			} else {
				user.add(videoFilters[filterIndex]);
			}
		}

		Comparator<VideoFilter> videoFilterComparator = new Comparator<VideoFilter>() {
			public int compare(VideoFilter o1, VideoFilter o2) {
				return o1.getPriority() - o2.getPriority();
			}
		};

		Collections.sort(before, videoFilterComparator);
		Collections.sort(after, videoFilterComparator);

		for (int filterIndex = 0; filterIndex < before.size(); filterIndex++) {
			String[] filterArgs = before.get(filterIndex).toCommandList(info);
			command.add(filterArgs);
		}

		VideoScalingOptions scalingOptions = options.getScaleOptions();
		String[] scaleArgs = scalingOptions.toCommandList(info);
		command.add(scaleArgs);

		for (int filterIndex = 0; filterIndex < user.size(); filterIndex++) {
			String[] filterArgs = user.get(filterIndex).toCommandList(info);
			command.add(filterArgs);
		}

		for (int filterIndex = 0; filterIndex < after.size(); filterIndex++) {
			String[] filterArgs = after.get(filterIndex).toCommandList(info);
			command.add(filterArgs);
		}

		/*
		 * Handle Audio filter
		 */
		AudioFilter[] audioFilters = options.getAudioFilter();
		for (int filterIndex = 0; filterIndex < audioFilters.length; filterIndex++) {
			String[] filterArgs = audioFilters[filterIndex].toCommandList(info);
			command.add(filterArgs);
		}

	}

	/**
	 * Use to add arguments list according to the given playing options.
	 * 
	 * @param command
	 *            the command to fill.
	 * @param info
	 *            the input video information.
	 * @param options
	 *            the playing options.
	 */
	public static void fillCommandWithPlayingOptions(EncodingCommand command,
			VideoInfo info, PlayingOptions options) {

		fillCommandWithGenericOptions(command, info, options);

	}

	/**
	 * Use to add arguments to command to support passlog file.
	 * 
	 * @param command
	 *            the command to fill.
	 * @param passLogFile
	 *            the passlog file.
	 */
	public static void fillPassLogFile(EncodingCommand command, File passLogFile) {

		String[] args = new String[2];
		args[0] = "-passlogfile";
		try {
			args[1] = passLogFile.getCanonicalPath();
		} catch (IOException e) {
			args[1] = passLogFile.getAbsolutePath();
		}
		command.add(args);

	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer#getEncodingJob(net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo,
	 *      java.io.File,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions)
	 */
	public EncodingJob getEncodingJob(MPlayerWrapper wrapper,
			InputVideo inputVideo, File outputFile, EncodingOptions options)
			throws MPlayerException {

		VideoInfo info = wrapper.getVideoInfo(inputVideo);

		if (options.getVideoOptions().getPass() == 1) {

			EncodingCommand command1 = new EncodingCommand(inputVideo,
					outputFile);
			fillCommandWithEncodingOptions(command1, info, options, 1);
			return new DefaultEncodingJob(wrapper, command1);

		} else if (options.getVideoOptions().getPass() == 2) {

			File passFile = new File(outputFile.getParentFile(), outputFile
					.getName()
					+ ".passfile.log");

			EncodingCommand command1 = new EncodingCommand(inputVideo,
					new File(NO_OUPUTFILE));
			fillPassLogFile(command1, passFile);
			fillCommandWithEncodingOptions(command1, info, options, 1);

			EncodingCommand command2 = new EncodingCommand(inputVideo,
					outputFile);
			fillPassLogFile(command2, passFile);
			fillCommandWithEncodingOptions(command2, info, options, 2);

			return new DefaultEncodingJob(wrapper, command1, command2, passFile);

		} else {
			throw new MPlayerException("Invalid number of pass "
					+ options.getVideoOptions().getPass());
		}

	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer#getPlayingJob(net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo,
	 *      net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingOptions)
	 */
	public PlayingJob getPlayingJob(MPlayerWrapper wrapper,
			InputVideo inputVideo, PlayingOptions options)
			throws MPlayerException {

		VideoInfo info = wrapper.getVideoInfo(inputVideo);

		EncodingCommand command = new EncodingCommand(inputVideo);
		fillCommandWithPlayingOptions(command, info, options);
		return new DefaultPlayingJob(wrapper, command);
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer#getVideoDemuxer()
	 */
	public VideoDemuxer getVideoDemuxer() {
		return VideoDemuxer.MUXER_AVI;
	}

}
