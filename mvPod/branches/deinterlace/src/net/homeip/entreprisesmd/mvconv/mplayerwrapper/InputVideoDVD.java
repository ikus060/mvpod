package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represent an InputVideo from a DVD (files of device).
 * 
 * @author patapouf
 * 
 */
public class InputVideoDVD extends InputVideo {

	/**
	 * Key value to retreive OS.
	 */
	private static final String OS_NAME = "os.name"; //$NON-NLS-1$

	/**
	 * Linux OS value.
	 */
	private static final String OS_NAME_LINUX = "Linux"; //$NON-NLS-1$

	/**
	 * Windows OS value.
	 */
	private static final String OS_NAME_WINDOWS = "Windows"; //$NON-NLS-1$

	/**
	 * This private static class are use to filter the listing of directory and
	 * find out if there is DVD video file in a directory.
	 * 
	 * @author patapouf
	 * 
	 */
	static class DVDDirectoryFilenameFilter implements FilenameFilter {

		/**
		 * List of needed files.
		 */
		private static String[] fileTypes = new String[] { "VIDEO_TS.BUP", //$NON-NLS-1$
				"VIDEO_TS.IFO" }; //$NON-NLS-1$

		/**
		 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
		 */
		public boolean accept(File dir, String name) {
			boolean match = false;

			int i = 0;
			while (i < fileTypes.length && !match) {
				if (name.endsWith(fileTypes[i])) {
					match = true;
				}
				i++;
			}
			return match;
		}
	}

	/**
	 * Create a new input video from the default dvd device.
	 * 
	 * @param device
	 *            the device
	 * @return an instance of InputVideoDVD that point to the given dvd device
	 * 
	 * @throws FileNotFoundException
	 *             whe the device are not found
	 * @throws DVDNotAvailableException
	 *             when the DVD device are not ready or not available
	 */
	public static InputVideoDVD fromDVDDevice(String device)
			throws FileNotFoundException, DVDNotAvailableException {

		String OS = System.getProperty(OS_NAME);
		if (OS.startsWith(OS_NAME_WINDOWS)) {

			//TODO : Find a way to determine if it's a DVD device
			File devicefile = new File(device);
			if (!devicefile.exists()) {
				throw new FileNotFoundException();
			}

		} else if (OS.equals(OS_NAME_LINUX)) {

			File devicefile = new File(device);
			if (!devicefile.exists()) {
				throw new FileNotFoundException();
			}
			try {
				FileInputStream input = new FileInputStream(devicefile);
				input.read();
				input.close();
			} catch (Exception e) {
				throw new DVDNotAvailableException(device);
			}

		} else {
			// TODO : Cross Platform : Support MAC
			throw new UnsupportedOperationException();
		}

		return new InputVideoDVD(device);

	}

	/**
	 * Create a new input video from a directory containing .VOB files. The
	 * directory mush point to VIDEO_TS directory.
	 * 
	 * @param directory
	 *            the directory
	 * @return the new input video dvd
	 * @throws FileNotFoundException
	 *             when the given directory are not a valid DVD directory
	 *             containing VIDEO_TS.BUP or VIDEO_TS.IFO
	 */
	public static InputVideoDVD fromDVDDirectory(File directory)
			throws FileNotFoundException {

		if (!directory.exists()) {
			throw new FileNotFoundException(directory.getAbsolutePath()
					+ " not found"); //$NON-NLS-1$
		}
		if (!directory.isDirectory()) {
			throw new FileNotFoundException("Directory " //$NON-NLS-1$
					+ directory.getAbsolutePath() + " not found"); //$NON-NLS-1$
		}

		int count = directory.list(new DVDDirectoryFilenameFilter()).length;
		if (count <= 0) {
			throw new FileNotFoundException("DVD files in " //$NON-NLS-1$
					+ directory.getAbsolutePath() + " not found"); //$NON-NLS-1$
		}

		try {
			return new InputVideoDVD(directory.getCanonicalPath());
		} catch (IOException e) {
			return new InputVideoDVD(directory.getAbsolutePath());
		}

	}

	/**
	 * Create a new input video dvd from a DVDVideo image file. (i.e.: a .iso or
	 * .bin)
	 * 
	 * @param imageFile
	 *            the image file
	 * @return the new input video dvd
	 * @throws FileNotFoundException
	 *             when the file are not found
	 */
	public static InputVideoDVD fromImageFile(File imageFile)
			throws FileNotFoundException {

		if (!imageFile.exists()) {
			throw new FileNotFoundException(imageFile.getAbsolutePath()
					+ " not found"); //$NON-NLS-1$
		}

		try {
			return new InputVideoDVD(imageFile.getCanonicalPath());
		} catch (IOException e) {
			return new InputVideoDVD(imageFile.getAbsolutePath());
		}
	}

	/**
	 * Return a list of all available dvd device. There migth be other.
	 * 
	 * @return the list of avaiable dvd device
	 */
	public static String[] getAvailablesDVDDevice() {

		String OS = System.getProperty(OS_NAME);
		if (OS.startsWith(OS_NAME_WINDOWS)) {
			File[] files = File.listRoots();
			String[] devices = new String[files.length];
			for(int index=0;index<files.length;index++){
				devices[index] =files[index].getAbsolutePath();
			}
			return devices;

		} else if (OS.equals(OS_NAME_LINUX)) {
			// TODO : Cross Platform : Complete this list
			String[] devices = new String[] { "/dev/dvd" }; //$NON-NLS-1$
			return devices;
		} else {
			// TODO : Cross Platform : Support MAC
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * The DVD device.
	 */
	private String device;

	/**
	 * Audio track id.
	 */
	private String audioTrack;

	/**
	 * Subtitle track id.
	 */
	private String subtitleTrack;

	/**
	 * The selected title.
	 */
	private String title;

	/**
	 * Create a new input video from a given dvd device.
	 * 
	 * @param dvdDevice
	 *            the dvd device to use. It's can be a directory, a file name or
	 *            a truly dvd device (i.e.: "/dev/dvd" for linux and "D:" for
	 *            windows ).
	 */
	public InputVideoDVD(String dvdDevice) {
		this(dvdDevice, null, null, null);
	}

	/**
	 * Create a new input video from a given dvd device.
	 * 
	 * @param dvdDevice
	 *            the dvd device to use. It's can be a directory, a file name or
	 *            a truly dvd device (i.e.: "/dev/dvd" for linux and "D:" for
	 *            windows ).
	 * @param title
	 *            the select title on the disc.
	 * @param audioTrack
	 *            The audio track to encode or null to let mplayer select the
	 *            default track according to the configuration file.
	 * @param subtitleTrack
	 *            the subtitle trackto show or null to disable subtitle.
	 */
	public InputVideoDVD(String dvdDevice, String title, String audioTrack,
			String subtitleTrack) {
		if (dvdDevice == null) {
			throw new NullPointerException();
		}

		this.device = dvdDevice;
		this.audioTrack = audioTrack;
		this.subtitleTrack = subtitleTrack;
		this.title = title;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo#equals(net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo)
	 */
	public boolean equals(InputVideo obj) {
		if (obj instanceof InputVideoDVD) {

			InputVideoDVD inputVideo = (InputVideoDVD) obj;

			if (!this.device.equals(inputVideo.device)) {
				return false;
			}
			if (this.audioTrack != null && !this.audioTrack.equals(inputVideo.audioTrack)) {
				return false;
			}
			if (inputVideo.audioTrack != null
					&& !inputVideo.audioTrack.equals(this.audioTrack)) {
				return false;
			}
			if (this.subtitleTrack != null
					&& !this.subtitleTrack.equals(inputVideo.subtitleTrack)) {
				return false;
			}
			if (inputVideo.subtitleTrack != null
					&& !inputVideo.subtitleTrack.equals(this.subtitleTrack)) {
				return false;
			}
			if (this.title != null && !this.title.equals(inputVideo.title)) {
				return false;
			}
			if (inputVideo.title != null && !inputVideo.title.equals(this.title)) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Retrun the selected audio track identifier or null.
	 * 
	 * @return the audio track identifier or null.
	 */
	public String getAudioTrack() {
		return this.audioTrack;
	}

	/**
	 * Return the DVD device.
	 * 
	 * @return the DVD device
	 */
	public String getDevice() {
		return this.device;
	}

	/**
	 * Return the selected subtitle track identifier or null.
	 * 
	 * @return the selected track identifier or null
	 */
	public String getSubtitleTrack() {
		return this.subtitleTrack;
	}

	/**
	 * Return the selected title.
	 * 
	 * @return the selected title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.device.hashCode();
	}

	/**
	 * Return the argument list to define the input video to mplayer.
	 * 
	 * @return the argument list
	 */
	public String[] toCommandList() {

		String argTitle;
		if (this.title != null) {
			argTitle = String.format("dvd://%s", this.title); //$NON-NLS-1$
		} else {
			argTitle = "dvd://"; //$NON-NLS-1$
		}

		List<String> argsList = new LinkedList<String>();
		argsList.add(argTitle);

		argsList.add("-dvd-device"); //$NON-NLS-1$
		argsList.add(this.device);

		if (this.audioTrack != null) {
			argsList.add("-aid"); //$NON-NLS-1$
			argsList.add(this.audioTrack);
		}
		if (this.subtitleTrack != null) {
			argsList.add("-sid"); //$NON-NLS-1$
			argsList.add(this.subtitleTrack);
		}

		String[] defaultArgs = super.toCommandList();
		for(int i=0; i<defaultArgs.length;i++){
			argsList.add(defaultArgs[i]);
		}
		
		
		String[] args = new String[argsList.size()];
		args = argsList.toArray(args);
		argsList = null;
		return args;
	}

}
