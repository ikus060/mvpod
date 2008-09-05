package net.homeip.entreprisesmd.mvconv.core.profile;

import java.io.File;
import java.io.FilenameFilter;

/**
 * This class are use to filter a listing directory.
 * 
 * @author patapouf
 * 
 */
public class ProfileFilenameFilter implements FilenameFilter {

	/**
	 * Profile filename extention.
	 */
	private static final String PROFILE_EXTENTION = ".profile"; //$NON-NLS-1$

	/**
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	public boolean accept(File dir, String name) {

		if (name.endsWith(PROFILE_EXTENTION)) {
			return true;
		}
		return false;
	}
}
