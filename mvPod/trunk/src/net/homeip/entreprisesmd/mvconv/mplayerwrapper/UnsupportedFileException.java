package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * Throw by VideoInfo when the identification of the video show that this file
 * are not supported by mplayer.
 * 
 * @author patapouf
 * 
 */
public class UnsupportedFileException extends MPlayerException {

	/**
	 * serial version unique identifier
	 */
	private static final long serialVersionUID = 2046301407693163437L;

	/**
	 * Create a new unsupported exception.
	 */
	public UnsupportedFileException() {
		super("Unsupported file");
	}

}
