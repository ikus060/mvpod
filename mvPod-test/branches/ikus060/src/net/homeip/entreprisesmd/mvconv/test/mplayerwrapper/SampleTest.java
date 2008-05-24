package net.homeip.entreprisesmd.mvconv.test.mplayerwrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import junit.framework.TestCase;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioStream;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoFile;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerNotFoundException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

public class SampleTest extends TestCase {

	private static final String AUDIO_SAMPLE_FILE_LISTING = "/data/audioListing";
	private static final String VIDEO_SAMPLE_FILE_LISTING = "/data/videoListing";

	/**
	 * File that work.
	 */
	private List<String> workingFiles = new LinkedList<String>();

	/**
	 * Files failed to retrieve information.
	 */
	private List<String> failedFiles = new LinkedList<String>();

	/**
	 * List of audio format and an assosiated filename.
	 */
	private Map<String, String> mplayerVideoFormat = new HashMap<String, String>();

	/**
	 * List of audio format and an assosiated filename + other file extentions.
	 */
	private Map<String, String> mplayerAudioFormat = new HashMap<String, String>();

	/**
	 * List of muxer and it associated extentions.
	 */
	private Map<String, String> mplayerVideoMuxer = new HashMap<String, String>();

	/**
	 * List of file extentions.
	 */
	private Set<String> fileExtentions = new HashSet<String>();

	/**
	 * This test try to retrieve all information on every video presnet in the
	 * listing. This listing include some file that will failed.
	 */
	public void testGetVideoInfoForAllAudioFile() {

		loopOnFileList(new File(AUDIO_SAMPLE_FILE_LISTING));

	}

	/**
	 * This test try to retrieve all information on every video presnet in the
	 * listing. This listing include some file that will failed.
	 */
	public void testGetVideoInfoForAllVideoFile() {

		loopOnFileList(new File(VIDEO_SAMPLE_FILE_LISTING));

	}

	/**
	 * This method loop on the file list.
	 * 
	 * @param list
	 *            the file list.
	 */
	private void loopOnFileList(File list) {

		// Init mplayer
		MPlayerWrapper wrapper = null;
		try {
			wrapper = new MPlayerWrapper();
		} catch (MPlayerNotFoundException e) {
			e.printStackTrace();
			TestCase.fail("MPlayerNotFoundException");
		}

		// Init list reading
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(list));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			TestCase.fail("FileNotFoundException " + list.getAbsolutePath());
		}

		// Read list
		String filename;
		try {

			while ((filename = reader.readLine()) != null) {
				retrieveVideoInfo(wrapper, filename);
				filename = null;

				System.gc();
				System.out.println("free memory: "
						+ Runtime.getRuntime().freeMemory() / 1024
						+ "KB, total memory:"
						+ Runtime.getRuntime().totalMemory() / 1024
						+ "KB, max memory:" + Runtime.getRuntime().maxMemory()
						/ 1024 + "KB");
			}

		} catch (IOException e) {
			e.printStackTrace();
			TestCase.fail("IOException");
		}

		// Failed files
		System.out.println("Failed files");
		System.out.println("============");
		Iterator<String> fileIter = failedFiles.iterator();
		while (fileIter.hasNext()) {
			System.out.println(fileIter.next());
		}

		// Video format
		System.out.println("Video format");
		System.out.println("============");
		Iterator<Entry<String, String>> formatIter = mplayerVideoFormat
				.entrySet().iterator();
		while (formatIter.hasNext()) {
			Entry<String, String> entry = formatIter.next();
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}

		// Audio format
		System.out.println("Audio format");
		System.out.println("============");
		formatIter = mplayerAudioFormat.entrySet().iterator();
		while (formatIter.hasNext()) {
			Entry<String, String> entry = formatIter.next();
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}

		// Video muxer
		System.out.println("Video muxer");
		System.out.println("============");
		Iterator<Entry<String, String>> muxerIter = mplayerVideoMuxer
				.entrySet().iterator();
		while (muxerIter.hasNext()) {
			Entry<String, String> entry = muxerIter.next();
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}

	}

	private void retrieveVideoInfo(MPlayerWrapper mplayer, String filename) {

		InputVideo inputVideo;
		try {
			inputVideo = new InputVideoFile(new File(filename));
		} catch (FileNotFoundException e) {
			failedFiles.add(filename);
			return;
		}

		// Get Video info
		VideoInfo info = null;
		try {
			info = mplayer.getVideoInfo(inputVideo);
		} catch (MPlayerException e) {
			failedFiles.add(filename);
			return;
		}

		// Store extensions
		String extention = "";
		int pos = filename.lastIndexOf(".");
		if (pos > 0) {
			extention = filename.substring(pos);
			fileExtentions.add(extention);
		}

		// Store video format
		String videoFormat = info.getMplayerVideoFormat();
		String extentions = mplayerVideoFormat.get(videoFormat);
		if (extentions == null) {
			extentions = filename + ", " + extention;
		} else {
			extentions += ", " + extention;
		}
		mplayerVideoFormat.put(videoFormat, extentions);

		// Store audio format
		List<AudioStream> audioStreams = info.getAudioStreams();
		if (videoFormat.equals("") && audioStreams != null
				&& audioStreams.size() > 0
				&& audioStreams.get(0).getAudioFormat() != null) {
			String audioFormat = audioStreams.get(0).getAudioFormat()
					.getFormatID();

			extentions = mplayerAudioFormat.get(audioFormat);
			if (extentions == null) {
				extentions = filename + ", " + extention;
			} else {
				extentions += ", " + extention;
			}
			mplayerAudioFormat.put(audioFormat, extentions);
		}

		// Store demuxer
		String videoMuxer = info.getMplayerVideoMuxer();
		extentions = mplayerVideoMuxer.get(videoMuxer);
		if (extentions == null) {
			extentions = filename + ", " + extention;
		} else {
			extentions += ", " + extention;
		}
		mplayerVideoMuxer.put(videoMuxer, extentions);

		info = null;
		inputVideo = null;

		workingFiles.add(filename);

	}

}
