package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.io.File;

/**
 * This class offer function to search for specific application path.
 * 
 * @author patapouf
 * 
 */
public final class ApplicationFinder {

	/**
	 * Private constructor.
	 */
	private ApplicationFinder() {
		/*
		 * Private constructore use to prevent creation of utility class.
		 */
	}

	/**
	 * Search for the absolute path to the given application.
	 * 
	 * @param app
	 *            the application file name.
	 * @return the directory that contain the application or null if the
	 *         application are not found.
	 */
	public static File getApplicationPath(String app) {
		return getApplicationPath(app, new File[0]);
	}

	/**
	 * Search for the absolute path to the given application.
	 * 
	 * @param app
	 *            the application file name.
	 * @param pathsList
	 *            list of path to look.
	 * @return the directory that contain the application or null if the
	 *         application are not found.
	 */
	public static File getApplicationPath(String app, File[] pathsList) {

		File[] paths = pathsList;
		if (System.getProperty("os.name").indexOf("Windows") >= 0) { //$NON-NLS-1$ //$NON-NLS-2$
			// TODO : Cross Platform : Must find mplayer path
			// File directory = new File(path);
			// paths.add(directory);
			
		} else {

			File[] newPaths = new File[paths.length + 2];
			System.arraycopy(paths, 0, newPaths, 0, paths.length);
			newPaths[paths.length] = new File("/usr/bin"); //$NON-NLS-1$
			newPaths[paths.length + 1] = new File("/usr/local/bin"); //$NON-NLS-1$
			paths = newPaths;
		}

		File currentDirectory = null;
		int index = 0;
		boolean found = false;
		while (index < paths.length && !found) {
			currentDirectory = paths[index];
			String[] files = currentDirectory.list();

			for (int i = 0; i < files.length; i++) {
				if (files[i].equals(app)) {
					found = true;
				}
			}

			index++;
		}

		if (!found) {
			return null;
		}

		return currentDirectory;
	}

}
