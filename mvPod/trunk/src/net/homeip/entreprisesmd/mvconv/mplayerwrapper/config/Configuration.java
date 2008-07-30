package net.homeip.entreprisesmd.mvconv.mplayerwrapper.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.ErrorParser;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.StreamReader;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoOutputDevice;

/**
 * This class represent the structure of a mplayer configuration file.
 * 
 * @author patapouf
 * 
 */
public class Configuration {

	/**
	 * Size of buffer used to read the file.
	 */
	private static final int BUFFER_SIZE = 128;
	/**
	 * Regex for video output parameters.
	 */
	private static final String PATTERN_VIDEO_OUTPUT = "^vo\\s*=\\s*(.*)$";
	/**
	 * Video output device list.
	 */
	private static VideoOutputDevice[] videoOutputDevices = null;

	/**
	 * Return a list of available video output device. This list are generate by
	 * execution of <code>mplayer -vo help</code> command.
	 * 
	 * @return list of available video output device.
	 * 
	 * @throws MPlayerException
	 *             if any error occur.
	 */
	public static VideoOutputDevice[] getAvailableVideoOutputDevices(
			MPlayerWrapper wrapper) throws MPlayerException {

		if (videoOutputDevices != null)
			return videoOutputDevices;

		String[] options = new String[] { "-vo", "help" };

		// Execute process
		Process newProcess;
		try {
			newProcess = wrapper.mplayer(options);
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
				errorParser, true);
		StreamReader errorStream = new StreamReader(proc.getErrorStream(),
				errorParser, false);
		errorStream.readInThread();
		inputStream.readInThread();

		// Wait for process
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			throw new MPlayerException("mplayer process get interrupted", e);
		}

		// Check relevant error
		errorParser.throwException();
		errorParser = null;

		String output = inputStream.toString();
		inputStream = null;
		errorStream = null;

		ArrayList<VideoOutputDevice> list = new ArrayList<VideoOutputDevice>();

		// Parse the output
		Matcher matcher = Pattern.compile("\t([a-zA-Z0-9]*)\t(.*)").matcher(
				output);
		while (matcher.find()) {
			String name = matcher.group(1);
			String description = matcher.group(2);

			list.add(new VideoOutputDevice(name, description));
		}

		VideoOutputDevice[] vo = new VideoOutputDevice[list.size()];
		videoOutputDevices = list.toArray(vo);
		return videoOutputDevices;
	}

	/**
	 * This method are use to read the content of the file and return it in a
	 * string.
	 * 
	 * @param configFile
	 *            the config file to read
	 * @return the content of the file
	 */
	private static String readFile(File configFile) {

		StringBuffer buf = new StringBuffer();
		FileReader reader = null;
		try {
			reader = new FileReader(configFile);
		} catch (FileNotFoundException e) {
			return "";
		}

		try {
			char[] buffer = new char[BUFFER_SIZE];
			int read = reader.read(buffer);
			while (read >= 0) {
				buf.append(buffer, 0, read);
				read = reader.read(buffer);
			}
			reader.close();
		} catch (IOException e) {
			return "";
		}

		return buf.toString();
	}

	/**
	 * Configuration file linked too.
	 */
	private File configFile;

	/**
	 * Content of the config file.
	 */
	private String content;

	/**
	 * Mplayer to used.
	 */
	private MPlayerWrapper wrapper;

	/**
	 * Constructor of the class. This constructor need to be linked to a
	 * configuration file. If the file doesn't exist it's will be created.
	 * 
	 * @param configFile
	 *            The configuration linked too.
	 */
	public Configuration(File configFile, MPlayerWrapper wrapper) {

		this.content = readFile(configFile);
		this.configFile = configFile;
		this.wrapper = wrapper;

	}

	/**
	 * Return the current videoOutput device selected in the configuration file.
	 * 
	 * @return the video output device selected.
	 */
	public VideoOutputDevice getVideoOutputDevice() {

		Matcher matcher = Pattern.compile(PATTERN_VIDEO_OUTPUT,Pattern.MULTILINE)
				.matcher(content);

		String name = null;
		while (matcher.find()) {
			name = matcher.group(1);
		}
		if (name != null) {
			return getVideoOutputDeviceFromName(name);
		}

		return null;

	}

	/**
	 * Return the video output device associate with the given name.
	 * 
	 * @param name
	 *            the name of the video output device
	 * @return the video output device associate with the given name.
	 */
	private VideoOutputDevice getVideoOutputDeviceFromName(String name) {

		VideoOutputDevice[] vo = null;
		try {
			vo = getAvailableVideoOutputDevices(wrapper);
		} catch (MPlayerException e) {
			return new VideoOutputDevice(name, "");
		}

		int index = 0;
		while (index < vo.length && !vo[index].getName().equals(name)) {
			index++;
		}
		if (index < vo.length) {
			return vo[index];
		}
		return new VideoOutputDevice(name, "");

	}

	/**
	 * Use to save the configuration file.
	 */
	public void save() throws IOException {

		FileWriter writer;

		writer = new FileWriter(configFile);

		writer.write(content);

		writer.flush();
		writer.close();

	}

	/**
	 * Change the video output device.
	 * 
	 * @param device
	 *            the new video output device to use.
	 */
	public void setVideoOutputDevice(VideoOutputDevice device) {

		Matcher matcher = Pattern.compile(PATTERN_VIDEO_OUTPUT, Pattern.MULTILINE)
				.matcher(content);

		if (matcher.find()) {
			content = matcher.replaceAll("vo=" + device.getName());
		} else {
			content += "\r\nvo=" + device.getName();
		}

	}

}
