package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * Throws by any mplayer execution when mplayer failed to grab an xv port (under
 * linux) to diplay the video. When this error occur, it's possible to solve the
 * problem by closing all audio/video application running.
 * 
 * @author patapouf
 * 
 */
public class GrabXvPortException extends MPlayerException {

	/**
	 * Serial version unique identifier.
	 */
	private static final long serialVersionUID = -1735245512910877730L;

	/**
	 * Create a new exception.
	 */
	public GrabXvPortException() {
		super(
				"Could not find free Xvideo port - maybe another process is already using it."); //$NON-NLS-1$
	}

}
