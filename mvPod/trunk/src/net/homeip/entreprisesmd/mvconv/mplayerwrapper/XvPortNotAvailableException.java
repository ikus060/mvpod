package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * Throws by any mplayer execution when mplayer cannot find an XVideo adapter to
 * display the video. This problem occur when the video drivers used for the
 * video card doesn't provide a XVideo adapters.<br>
 * 
 * To solve the problem, use an alternate video output like x11. To select x11
 * as video output execute mplayer -vo x11 [...].
 * 
 * @author patapouf
 * 
 */
public class XvPortNotAvailableException extends MPlayerException {

	/**
	 * Serial version unique identifier.
	 */
	private static final long serialVersionUID = -1735245512910877730L;

	/**
	 * Create a new exception.
	 */
	public XvPortNotAvailableException() {
		super(
				"There no Xvideo support for your video card available. Try using an alternate video output.");
	}

}
