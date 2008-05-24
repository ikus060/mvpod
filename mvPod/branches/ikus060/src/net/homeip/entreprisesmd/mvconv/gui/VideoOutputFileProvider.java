package net.homeip.entreprisesmd.mvconv.gui;

import java.io.File;
import java.util.Arrays;

import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoDVD;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoFile;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoDemuxer;

/**
 * This class can be use to obtain an output file that consider the input video
 * and the user preference. The file extention are generate depending of the
 * selected muxer in the encoding options.
 * <p>
 * When the use select a different profile, all output file name for each video
 * present in the video list are rename to correspond to the new selection.
 * </p>
 * 
 * @author patapouf
 * 
 */
public class VideoOutputFileProvider implements IVideoOutputFileProvider {
	/**
	 * Default extention return when there is not profile selected.
	 */
	private static final String DEFAULT_EXTENTION = ".avi";

	/**
	 * Profile context to determine the file extention.
	 */
	private ProfileContext profileContext;
	/**
	 * Video list use to rename output file.
	 */
	private VideoList videoList;
	/**
	 * Primary extention.
	 */
	private String lastPrimaryExtention = DEFAULT_EXTENTION;
	/**
	 * Last demuxer.
	 */
	private VideoDemuxer lastDemuxer;

	/**
	 * Create a new file provider.
	 * 
	 * @param context
	 *            the profile context used to determine the file extention.
	 * @param list
	 *            the video list to rename in case that profile context change.
	 */
	public VideoOutputFileProvider(ProfileContext context, VideoList list) {

		this.profileContext = context;
		this.videoList = list;

		// Add observer
		profileContext.addProfileContextListener(new IProfileContextListener() {
			public void profileContextAsChanged(ProfileContext context) {
				VideoOutputFileProvider.this.profileContextAsChanged();
			}
		});

	}

	/**
	 * This method are use to rename all video presnet in the video list.
	 * 
	 * @param extentions
	 *            list of extentions.
	 */
	private void changeFileExtention(String[] extentions) {

		if (extentions.length <= 0) {
			return;
		}
		String primaryExtention = extentions[0];
		Arrays.sort(extentions);

		Video[] videos = videoList.getVideos();
		for (int index = 0; index < videos.length; index++) {

			Video video = videos[index];
			File outputFile = video.getOutputFile();

			String extention = parseFileExtention(outputFile.getName());
			if (extention != null) {
				int pos = Arrays.binarySearch(extentions, extention);
				if (pos < 0) {
					String filepath = outputFile.getAbsolutePath();
					String newFilepath = filepath.substring(0, filepath
							.length()
							- extention.length())
							+ primaryExtention;
					video.setOutputFile(new File(newFilepath));
				}
			} else {
				String newFilepath = outputFile.getAbsolutePath()
						+ primaryExtention;
				video.setOutputFile(new File(newFilepath));
			}
		}
	}

	/**
	 * Return the extention by considering the muxer.
	 * 
	 * @return the extention by considering the muxer
	 */
	private String getPrimaryExtention() {
		return lastPrimaryExtention;
	}

	/**
	 * Return an output file with the inputvideo.
	 * 
	 * @param inputVideo
	 *            the inputvideo
	 * @return the output file
	 */
	public File getFile(InputVideo inputVideo) {

		if (inputVideo instanceof InputVideoFile) {

			String path = ((InputVideoFile) inputVideo).getFile()
					.getAbsolutePath();
			int pos = path.lastIndexOf(".");
			path = path.substring(0, pos);
			path += ".Output" + getPrimaryExtention();

			return new File(path);

		} else if (inputVideo instanceof InputVideoDVD) {

			String path = System.getProperty("user.home");
			path += File.separator + "DVD" + getPrimaryExtention();

			return new File(path);
		}

		return null;
	}

	/**
	 * Return the file extention of the given filename.
	 * 
	 * @param filename
	 *            the file name.
	 * @return the file extention or null if there file doesn't have one.
	 */
	private String parseFileExtention(String filename) {

		int pos = filename.lastIndexOf(".");
		if (pos > 0) {
			return filename.substring(pos);
		}
		return null;
	}

	/**
	 * Notify this class that profile selection has change.
	 * 
	 * @param context
	 *            the profile context
	 */
	private void profileContextAsChanged() {

		if (profileContext.getSelectedProfile() == null) {
			return;
		}
		VideoDemuxer demuxer = profileContext.getSelectedProfile()
				.getEncodingOptions().getMuxer().getVideoDemuxer();

		if (demuxer.equals(lastDemuxer)) {
			return; // Same demuxer => nothing to do
		}

		String[] extentions = demuxer.getFileExtentions();
		if (extentions.length > 0) {
			lastPrimaryExtention = extentions[0];
		} else {
			lastPrimaryExtention = DEFAULT_EXTENTION;
		}

		// Change file extentions
		changeFileExtention(extentions);

		// Keep trace of last demuxer
		lastDemuxer = demuxer;

	}
}
