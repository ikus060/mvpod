package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * Throw by any mplayer class when the input video refer to a DVD device that
 * are not available.
 * 
 * @author patapouf
 * 
 */
public class DVDNotAvailableException extends MPlayerException {

	/**
	 * Serial version unique identifier.
	 */
	private static final long serialVersionUID = 1894483966881964878L;

	/**
	 * Unavailable device.
	 */
	private String unavailableDevice;

	/**
	 * Create a new DVDNotAvailableException.
	 * 
	 * @param device
	 *            the unavailable device
	 */
	public DVDNotAvailableException(String device) {
		super("Couldn't open DVD device");
		this.unavailableDevice = device;
	}

	/**
	 * Return the unavailable device.
	 * @return the device.
	 */
	public String getDevice() {
		return unavailableDevice;
	}

}
