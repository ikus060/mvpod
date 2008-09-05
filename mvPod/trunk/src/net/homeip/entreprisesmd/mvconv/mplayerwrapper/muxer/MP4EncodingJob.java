package net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingProgressObserver;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.ErrorParser;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoFile;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.StreamReader;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This encoding job are use to generate MP4 video file from default output of
 * mencoder. This class use the MP4Box utility to generate the MP4Video file.
 * 
 * @author patapouf
 * 
 */
public class MP4EncodingJob implements EncodingJob {

	/**
	 * True if job are canceled.
	 */
	private boolean canceled;
	/**
	 * The adapted encoding job.
	 */
	private EncodingJob encodingJob;
	/**
	 * Video information after the encoding.
	 */
	private VideoInfo info;
	/**
	 * Fuulpath to MP4Box utility.
	 */
	private String mp4box;
	/**
	 * The mplayer wrapper to use.
	 */
	private MPlayerWrapper mplayer;
	/**
	 * The output file.
	 */
	private File outputFile;

	/**
	 * Create a new MP4 encoding job.
	 * 
	 * @param mp4box
	 *            the full path to MP4Box utility.
	 * @param mplayer
	 *            the mplayer wrapper.
	 * @param encodingJob
	 *            the adapted encoding job from witch take the output video.
	 * @param outputFile
	 *            the output file.
	 * @param options
	 *            the encoding options.
	 * @throws FileNotFoundException
	 *             if MP4Box utility are not found.
	 */
	public MP4EncodingJob(String mp4box, MPlayerWrapper mplayer,
			EncodingJob encodingJob, File outputFile)
			throws FileNotFoundException {

		if (mp4box == null) {
			throw new NullPointerException();
		}
		if (encodingJob == null) {
			throw new NullPointerException();
		}
		if (!(new File(mp4box)).exists()) {
			throw new FileNotFoundException(mp4box + " not found"); //$NON-NLS-1$
		}

		this.encodingJob = encodingJob;
		this.mp4box = mp4box;
		this.outputFile = outputFile;
		this.mplayer = mplayer;

	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob#addProgressObserver(net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingProgressObserver)
	 */
	public void addProgressObserver(EncodingProgressObserver observer) {
		this.encodingJob.addProgressObserver(observer);
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob#cancel()
	 */
	public void cancel() {
		this.canceled = true;
		this.encodingJob.cancel();
	}

	/**
	 * Use to extract audio track from video file.
	 * 
	 * @param inputFile
	 *            the input video send to MP4Box.
	 * @return the audio file generated by MP4Box.
	 * @throws MPlayerException
	 *             if any error occur.
	 */
	private File extractAudio(File inputFile) throws MPlayerException {

		if (this.info.getAudioStreams().size() <= 0) {
			throw new MPlayerException("Invalid input file " + inputFile); //$NON-NLS-1$
		}

		/*
		 * Extraction
		 */
		StreamReader inputStream;
		StreamReader errorStream;

		int exitCode = 1;
		try {

			String[] args = new String[3];
			args[0] = "-aviraw"; //$NON-NLS-1$
			args[1] = "audio"; //$NON-NLS-1$
			args[2] = inputFile.getCanonicalPath();
			Process proc = mp4box(args);

			ErrorParser errorParser = new ErrorParser();
			inputStream = new StreamReader(proc.getInputStream(), errorParser, true);
			errorStream = new StreamReader(proc.getErrorStream(), errorParser, true);
			inputStream.read();
			errorStream.read();
			errorParser.throwException();

			exitCode = proc.waitFor();

		} catch (IOException ioe) {
			throw new MPlayerException("Can't run MP4Box process", ioe); //$NON-NLS-1$
		} catch (InterruptedException ie) {
			throw new MPlayerException("MP4Box process are interrupted", ie); //$NON-NLS-1$
		}

		if (exitCode > 0) {
			throw new MPlayerException(errorStream.toString()
					+ inputStream.toString());
		}

		/*
		 * Retreive file
		 */
		File file = getAudioFile(inputFile);
		if (!file.exists()) {
			throw new MPlayerException("Audio file " + file.getAbsolutePath() //$NON-NLS-1$
					+ " not found"); //$NON-NLS-1$
		}

		/*
		 * Rename files as appropriate
		 */
		AudioFormat audioFormat = this.info.getAudioStreams().get(0)
				.getAudioFormat();
		String[] fileExtentions = audioFormat.getFileExtentions();
		if (fileExtentions.length > 0) {
			File renamedFile = new File(file.getAbsolutePath()
					+ fileExtentions[0]);
			file.renameTo(renamedFile);
			file = renamedFile;
		}

		return file;
	}

	/**
	 * Use to extract video from video file.
	 * 
	 * @param inputFile
	 *            the input video send to MP4Box.
	 * @return the audio file generated by MP4Box.
	 * @throws MPlayerException
	 *             if any error occur.
	 */
	private File extractVideo(File inputFile) throws MPlayerException {

		StreamReader inputStream;
		StreamReader errorStream;

		int exitCode = 1;
		try {

			String[] args = new String[3];
			args[0] = "-aviraw"; //$NON-NLS-1$
			args[1] = "video"; //$NON-NLS-1$
			args[2] = inputFile.getCanonicalPath();

			Process proc = mp4box(args);

			ErrorParser errorParser = new ErrorParser();
			inputStream = new StreamReader(proc.getInputStream(), errorParser, true);
			errorStream = new StreamReader(proc.getErrorStream(), errorParser, true);
			inputStream.read();
			errorStream.read();
			errorParser.throwException();

			exitCode = proc.waitFor();

		} catch (IOException ioe) {
			throw new MPlayerException("Can't run MP4Box process", ioe); //$NON-NLS-1$
		} catch (InterruptedException ie) {
			throw new MPlayerException("MP4Box process are interrupted", ie); //$NON-NLS-1$
		}

		if (exitCode > 0) {
			throw new MPlayerException(errorStream.toString()
					+ inputStream.toString());
		}

		/*
		 * Retreive file
		 */
		File file = getVideoFile(inputFile);
		if (!file.exists()) {
			throw new MPlayerException("Video file " + file.getAbsolutePath() //$NON-NLS-1$
					+ " not found"); //$NON-NLS-1$
		}

		return file;

	}

	/**
	 * Return the audio file generated by MP4Box.
	 * 
	 * @param inputFile
	 *            the input file send to MP4Box.
	 * 
	 * @return the resulting audio file.
	 */
	private File getAudioFile(File inputFile) {

		String path = inputFile.getAbsolutePath();
		if (path.lastIndexOf(".") > 0) { //$NON-NLS-1$
			path = path.substring(0, path.lastIndexOf(".")); //$NON-NLS-1$
		}

		path += "_audio.raw"; //$NON-NLS-1$

		return new File(path);
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob#getFrameRate()
	 */
	public double getFrameRate() {
		return this.encodingJob.getFrameRate();
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob#getLength()
	 */
	public int getLength() {
		return this.encodingJob.getLength();
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob#getPercentCompleted()
	 */
	public int getPercentCompleted() {
		return this.encodingJob.getPercentCompleted();
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob#getTimeRemaining()
	 */
	public int getTimeRemaining() {
		return this.encodingJob.getTimeRemaining();
	}

	/**
	 * Return the video file generated by MP4Box.
	 * 
	 * @param inputFile
	 *            the input video file send to MP4Box.
	 * @return the resulting video file.
	 */
	private File getVideoFile(File inputFile) {

		String path = inputFile.getAbsolutePath();
		if (path.lastIndexOf(".") > 0) { //$NON-NLS-1$
			path = path.substring(0, path.lastIndexOf(".")); //$NON-NLS-1$
		}

		VideoFormat format = this.info.getVideoFormat();

		if (format.equals(VideoFormat.FORMAT_H264)
				|| format.equals(VideoFormat.FORMAT_H264_AVC)) {
			path += "_video.h264"; //$NON-NLS-1$
		} else if (format.equals(VideoFormat.FORMAT_MPEG_XVID)) {
			path += "_video.cmp"; //$NON-NLS-1$
		} else {
			path += "_video.raw"; //$NON-NLS-1$
		}

		return new File(path);
	}

	/**
	 * Use to create a new MP4 file from the video file and the audio file.
	 * 
	 * @param videoFile
	 *            the input video file.
	 * @param audioFile
	 *            the input audio file.
	 * @throws MPlayerException
	 *             if any error occur in the process.
	 */
	private void importToMP4(File videoFile, File audioFile)
			throws MPlayerException {

		StreamReader inputStream;
		StreamReader errorStream;

		/*
		 * Import audio & video in one command
		 */
		int exitCode = 1;
		try {
			String[] args = new String[5];
			args[0] = this.outputFile.getCanonicalPath();
			args[1] = "-add"; //$NON-NLS-1$
			args[2] = audioFile.getCanonicalPath();
			args[3] = "-add"; //$NON-NLS-1$
			args[4] = videoFile.getCanonicalPath() + ":fps=" //$NON-NLS-1$
			+ this.info.getFrameRate();

			Process proc = mp4box(args);

			ErrorParser errorParser = new ErrorParser();
			inputStream = new StreamReader(proc.getInputStream(), errorParser, true);
			errorStream = new StreamReader(proc.getErrorStream(), errorParser, true);
			inputStream.read();
			errorStream.read();
			errorParser.throwException();

			exitCode = proc.waitFor();

		} catch (IOException ioe) {
			throw new MPlayerException("Can't run MP4Box process", ioe); //$NON-NLS-1$
		} catch (InterruptedException ie) {
			throw new MPlayerException("MP4Box process are interrupted", ie); //$NON-NLS-1$
		}

		if (exitCode > 0) {
			throw new MPlayerException(errorStream.toString()
					+ inputStream.toString());
		}

	}

	/**
	 * Execute a command in a subprocess.
	 * 
	 * @param arguments
	 *            arguments list.
	 * @return A new Process object for managing the subprocess.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	private Process mp4box(final String[] arguments) throws IOException {
		String[] cmds = new String[arguments.length + 1];
		cmds[0] = this.mp4box;
		System.arraycopy(arguments, 0, cmds, 1, arguments.length);

		String commandStr = this.mp4box + " "; //$NON-NLS-1$
		for (int i = 0; i < arguments.length; i++) {
			commandStr += arguments[i] + " "; //$NON-NLS-1$
		}

		System.out.println("mp4muxer>> " + commandStr); //$NON-NLS-1$

		return Runtime.getRuntime().exec(cmds);
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob#removeProgressObserver(net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingProgressObserver)
	 */
	public void removeProgressObserver(EncodingProgressObserver observer) {
		this.encodingJob.removeProgressObserver(observer);
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob#start()
	 */
	public void start() throws MPlayerException {

		// Do the encoding job with mencoder
		this.encodingJob.start();
		if (this.canceled)
			return;

		// Check if mencoder produce the video
		if (!this.outputFile.exists()) {
			throw new MPlayerException("Output file " //$NON-NLS-1$
					+ this.outputFile.getAbsolutePath() + " not found"); //$NON-NLS-1$
		}

		// Rename the video
		File tempAviFile = new File(this.outputFile.getParentFile(), this.outputFile
				.getName()
				+ "_" + System.currentTimeMillis() + "temp.avi"); //$NON-NLS-1$ //$NON-NLS-2$
		if (!this.outputFile.renameTo(tempAviFile)) {
			throw new MPlayerException("Can't rename " + this.outputFile.getName() //$NON-NLS-1$
					+ " to " + tempAviFile.getName()); //$NON-NLS-1$
		}

		// Retreive information on input video
		try {
			this.info = this.mplayer.getVideoInfo(new InputVideoFile(tempAviFile));
		} catch (FileNotFoundException e) {
			throw new MPlayerException("Temp file " //$NON-NLS-1$
					+ tempAviFile.getAbsolutePath() + " not found"); //$NON-NLS-1$
		}

		// Extract video and audio
		File videoFile;
		File audioFile;
		try {
			videoFile = extractVideo(tempAviFile);
			audioFile = extractAudio(tempAviFile);
		} catch (MPlayerException e) {
			tempAviFile.delete();
			throw e;
		}

		// Import audio and Video to MP4 file
		try {
			importToMP4(videoFile, audioFile);
		} catch (MPlayerException e) {
			audioFile.delete();
			videoFile.delete();
			tempAviFile.delete();
			throw e;
		}

		audioFile.delete();
		videoFile.delete();
		tempAviFile.delete();

	}

}
