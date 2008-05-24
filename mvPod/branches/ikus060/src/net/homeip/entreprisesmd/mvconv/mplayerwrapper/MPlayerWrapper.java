package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.io.File;
import java.io.IOException;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.DefaultEncodingJob;

/**
 * This class offer every function available to press mplayer or mencoder
 * opperation.
 * 
 * @author patapouf
 * 
 */
public class MPlayerWrapper {

	/**
	 * mplayer filename.
	 */
	public static final String MPLAYER_BIN = System.getProperty("os.name")
			.indexOf("Windows") >= 0 ? "mplayer.exe" : "mplayer";
	/**
	 * mencoder filename.
	 */
	public static final String MENCODER_BIN = System.getProperty("os.name")
			.indexOf("Windows") >= 0 ? "mencoder.exe" : "mencoder";

	/**
	 * Fullpath to mplayer.
	 */
	private String mplayerPath;
	/**
	 * Fullpath to mendoder.
	 */
	private String mencoderPath;

	/**
	 * Create a new Wrapper classe. Search mplayer in default directory.
	 * 
	 * @throws MPlayerNotFoundException
	 *             if mplayer are not found.
	 */
	public MPlayerWrapper() throws MPlayerNotFoundException {
		this(new File[0]);
	}

	/**
	 * Create a new Wrapper class and search for mplayer in specified directory.
	 * 
	 * @param paths
	 *            list of directory to search for mplayer.
	 * @throws MPlayerNotFoundException
	 *             if mplayer are not found.
	 */
	public MPlayerWrapper(File[] paths) throws MPlayerNotFoundException {
		File mplayerFile = ApplicationFinder.getApplicationPath(MPLAYER_BIN,
				paths);
		if (mplayerFile == null) {
			throw new MPlayerNotFoundException();
		}

		File mencoderFile = ApplicationFinder.getApplicationPath(MENCODER_BIN,
				paths);
		if (mencoderFile == null) {
			throw new MPlayerNotFoundException();
		}

		try {
			mplayerPath = mplayerFile.getCanonicalPath();
			mencoderPath = mencoderFile.getCanonicalPath();
		} catch (IOException e) {
			mplayerPath = mplayerFile.getAbsolutePath();
			mencoderPath = mencoderFile.getAbsolutePath();
		}

	}

	/**
	 * Return a new Encoding Job that can be execute as part of a thread.
	 * 
	 * @param inputVideo
	 *            the input video file to encode.
	 * @param outputFile
	 *            the resulting video encoded.
	 * @param options
	 *            the encoding options.
	 * @return the Encoding Job to use to process the encoding.
	 * @throws MPlayerException
	 *             when any error occur withing the creation of the job.
	 */
	public EncodingJob encode(InputVideo inputVideo, File outputFile,
			EncodingOptions options) throws MPlayerException {
		return options.getMuxer().getEncodingJob(this, inputVideo, outputFile,
				options);
	}

	/**
	 * Return detailed information about the input video.
	 * 
	 * @param inputVideo
	 *            the input video.
	 * @return information on the given video.
	 * @throws MPlayerException
	 *             when any error occur within the process
	 */
	public VideoInfo getVideoInfo(InputVideo inputVideo)
			throws MPlayerException {

		String[] options = new String[] { "-v", "-msglevel", "identify=9",
				"-vo", "null", "-ao", "null", "-frames", "0" };

		String[] inputVideoArgs = inputVideo.toCommandList();

		String[] command = new String[inputVideoArgs.length + options.length];
		for (int i = 0; i < inputVideoArgs.length; i++) {
			command[i] = inputVideoArgs[i];
		}
		for (int i = 0; i < options.length; i++) {
			command[i + inputVideoArgs.length] = options[i];
		}

		// Execute process
		Process newProcess;
		try {
			newProcess = mplayer(command);
		} catch (IOException e) {
			throw new MPlayerException("Can't run mplayer process", e);
		}

		final Process proc = newProcess;

		// Mplayer Reader
		ErrorParser errorParser = new ErrorParser() {
			public boolean exceptionFound(MPlayerException exception) {
				super.exceptionFound(exception);
				proc.destroy();
				return false;
			}
		};
		StreamReader inputStream = new StreamReader(proc.getInputStream(),
				errorParser);
		StreamReader errorStream = new StreamReader(proc.getErrorStream(),
				errorParser);
		errorStream.readInThread();
		inputStream.readInThread();

		// Wait for process
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			throw new MPlayerException("Error running process", e);
		}

		// Check revelant error
		errorParser.throwException();
		errorParser = null;

		String output = inputStream.toString();
		inputStream = null;
		errorStream = null;
		return new VideoInfo(output);

	}

	/**
	 * Return mplayer filepath.
	 * 
	 * @return mplayer filepath.
	 */
	protected String getMplayer() {
		return mplayerPath + File.separator + MPLAYER_BIN;
	}

	/**
	 * Return mencoder filepath.
	 * 
	 * @return mencoder filepath.
	 */
	protected String getMencoder() {
		return mencoderPath + File.separator + MENCODER_BIN;
	}

	/**
	 * Execute a command in a seperate process.
	 * 
	 * @param arguments
	 *            argument list.
	 * @return A new Process object for managing the subprocess.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public Process mencode(String[] arguments) throws IOException {
		String[] cmds = new String[arguments.length + 1];
		cmds[0] = getMencoder();
		System.arraycopy(arguments, 0, cmds, 1, arguments.length);

		String commandStr = getMencoder() + " ";
		for (int i = 0; i < arguments.length; i++) {
			commandStr += arguments[i] + " ";
		}

		System.out.println("wrapper>> " + commandStr);

		return Runtime.getRuntime().exec(cmds);
	}

	/**
	 * Execute a command in a subprocess.
	 * 
	 * @param arguments
	 *            argument list.
	 * @return A new Process object for managing the subprocess.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public Process mplayer(String[] arguments) throws IOException {
		String[] cmds = new String[arguments.length + 1];
		cmds[0] = getMplayer();
		System.arraycopy(arguments, 0, cmds, 1, arguments.length);

		String commandStr = getMplayer() + " ";
		for (int i = 0; i < arguments.length; i++) {
			commandStr += arguments[i] + " ";
		}

		System.out.println("wrapper>> " + commandStr);

		return Runtime.getRuntime().exec(cmds);
	}

	/**
	 * Return a new Playing Job that can be execute as part of a thread.
	 * 
	 * @param inputVideo
	 *            the input video file to play.
	 * @param options
	 *            the playing options.
	 * @return the Playing Job to use to process the playing.
	 * @throws MPlayerException
	 *             when any error occur within the creation of the job.
	 */
	public PlayingJob play(InputVideo inputVideo, PlayingOptions options)
			throws MPlayerException {

		return options.getMuxer().getPlayingJob(this, inputVideo, options);
	}

	/**
	 * This methode are use to split a video in several part.
	 * 
	 * @param inputVideo
	 *            the input video
	 * @param outpuFile
	 *            the based output file name
	 * @param start
	 *            the starting position
	 * @param end
	 *            the ending position
	 * @return the encoding job to process the split
	 * @deprecated This method must be modify and are not yet functional.
	 * @throws MPlayerException
	 *             if any error occur withing the job creation.
	 */
	public EncodingJob split(InputVideo inputVideo, File outpuFile,
			double start, double end) throws MPlayerException {

		if (start < 0 && end < 0) {
			throw new IllegalArgumentException("Invalid start/end lenght");
		}

		VideoInfo info = this.getVideoInfo(inputVideo);

		double totalLenght = info.getLength();

		if (start >= totalLenght) {
			throw new IllegalArgumentException("Invalid start lenght");
		}
		if (end >= totalLenght) {
			throw new IllegalArgumentException("Invalid start lenght");
		}

		String[] arguments;
		if (start < 0 && end > 0) {
			arguments = new String[] { "-ovc", "copy", "-oac", "copy",
					"-endpos", Double.toString(end) };
		} else if (start >= 0 && end < 0) {
			arguments = new String[] { "-ovc", "copy", "-oac", "copy", "-ss",
					Double.toString(start) };
		} else if (start >= 0 && end > 0) {
			arguments = new String[] { "-ovc", "copy", "-oac", "copy", "-ss",
					Double.toString(start), "-endpos", Double.toString(end) };
		} else {
			throw new IllegalArgumentException("Invalid start/end lenght");
		}

		EncodingCommand command = new EncodingCommand(inputVideo, outpuFile,
				arguments);
		return new DefaultEncodingJob(this, command);

	}
}
