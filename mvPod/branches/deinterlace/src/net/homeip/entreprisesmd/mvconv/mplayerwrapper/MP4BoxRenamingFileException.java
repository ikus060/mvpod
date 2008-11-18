package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * This exception are throw when the MP4Box find an error and decide to rename
 * the output file.
 * 
 * @author patapouf
 * 
 */
public class MP4BoxRenamingFileException extends MPlayerException {

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -6002711250744816892L;
	/**
	 * New file name.
	 */
	private String newFileName;

	/**
	 * Create a new exception.
	 * 
	 * @param newFileName
	 *            the file name use by MP4Box
	 */
	public MP4BoxRenamingFileException(String newFileName) {
		super("Error detected. MP4Box rename the output file '" + newFileName //$NON-NLS-1$
				+ "'"); //$NON-NLS-1$
		this.newFileName = newFileName;
	}

	/**
	 * Return the new output file name.
	 * 
	 * @return the output file name.
	 */
	public String getFileName() {
		return this.newFileName;
	}

}
