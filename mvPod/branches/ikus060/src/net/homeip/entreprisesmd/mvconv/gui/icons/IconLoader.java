package net.homeip.entreprisesmd.mvconv.gui.icons;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * This class can be use to load icons into image.
 * 
 * @author patapouf
 * 
 */
public class IconLoader {

	/**
	 * Application icon.
	 */
	public static final String ICON_APP_16 = "iriverter2-16.png";
	/**
	 * Application icon.
	 */
	public static final String ICON_APP_22 = "iriverter2-22.png";
	/**
	 * Application icon.
	 */
	public static final String ICON_APP_32 = "iriverter2-32.png";
	/**
	 * Application icon.
	 */
	public static final String ICON_APP_64 = "iriverter2-64.png";
	/**
	 * Convert video icon.
	 */
	public static final String ICON_CONVERT_22 = "convert-22.png";
	/**
	 * Add dvd icon.
	 */
	public static final String ICON_DVD_16 = "dvd-16.png";
	/**
	 * Add dvd icon.
	 */
	public static final String ICON_DVD_22 = "dvd-22.png";
	/**
	 * Add dvd from device icon.
	 */
	public static final String ICON_DVD_DEVICE_16 = "dvd-device-16.png";
	/**
	 * Add dvd from device icon.
	 */
	public static final String ICON_DVD_DEVICE_22 = "dvd-device-22.png";
	/**
	 * Preview video icon.
	 */
	public static final String ICON_PREVIEW_16 = "preview-16.png";
	/**
	 * Preview video icon.
	 */
	public static final String ICON_PREVIEW_22 = "preview-22.png";
	/**
	 * Add video file icon.
	 */
	public static final String ICON_VIDEOFILE_16 = "videofile-16.png";
	/**
	 * Add video file icon.
	 */
	public static final String ICON_VIDEOFILE_22 = "videofile-22.png";

	/**
	 * Unique instance of icon loader.
	 */
	public static IconLoader instance;

	/**
	 * Private constructor.
	 */
	private IconLoader() {

	}

	/**
	 * Use to retrieve the unique instance of this class.
	 * 
	 * @return the icon loader.
	 */
	public static IconLoader getImageLoader() {
		if (instance == null)
			instance = new IconLoader();
		return instance;
	}

	/**
	 * Use to load an icon.
	 * 
	 * @param filename
	 *            Define the filename of the icon.
	 * @return ImageDescriptor of this image.
	 */
	public static ImageDescriptor loadIcon(String filename) {
		return ImageDescriptor.createFromFile(getImageLoader().getClass(),
				filename);
	}

}
