package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * Throws by any mplayer execution when mplayer failed to seek.
 * 
 * @author patapouf
 * 
 */
public class SeekException extends MPlayerException {

	/**
	 * Serial version unique identifier.
	 */
	private static final long serialVersionUID = -1735245512910877730L;

	/**
	 * Create a new exception.
	 */
	public SeekException() {
		super(
				"Could not find free Xvideo port - maybe another process is already using it."); //$NON-NLS-1$
	}

}
