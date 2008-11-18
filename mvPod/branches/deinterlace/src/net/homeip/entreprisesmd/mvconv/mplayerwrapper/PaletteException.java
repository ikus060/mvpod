package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * Throws by any mplayer execution when video palette are invalid.
 * 
 * @author patapouf
 * 
 */
public class PaletteException extends MPlayerException {

	/**
	 * Serial version unique identifier.
	 */
	private static final long serialVersionUID = -1455315613335171980L;

	/**
	 * Create a new exception.
	 */
	public PaletteException() {
		super("Palette skipped entry (out of count)"); //$NON-NLS-1$
	}

}
