package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * This class represent a video output device use that can be use to display the
 * video.
 * 
 * This video output device can be use in mplayer command list to redefine the
 * video output to use like <code> mplayer -vo <videoOutputDevice></code> or in
 * mplayer configuration file.
 * 
 * @author patapouf
 * 
 */
public class VideoOutputDevice {
	/**
	 * The name of the video output device (argument to use). This value are not
	 * human readable.
	 */
	private String name;

	/**
	 * Define the description provide by mplayer. This value are not localised.
	 */
	private String description;

	/**
	 * Constructor of a VideoOutputDevice
	 * 
	 * @param name
	 *            The name of the device
	 * @param description
	 *            the description provide by mplayer.
	 */
	public VideoOutputDevice(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * Return the video output device description. This value are not localised.
	 * 
	 * @return the device description provide by mplayer.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Return the name of the video output device. This value are not human
	 * readable.
	 * 
	 * @return the name of the device.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name;
	}

}