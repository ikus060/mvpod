package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * Throws by any mplayer execution when mplayer determine that it's impossible
 * to scale to a given dimension.
 * 
 * To solve the problem, change the dimension.
 * 
 * @author Patrik Dufresne
 * 
 */
public class InvalidScalingException extends MPlayerException {

	/**
	 * Serial version unique identifier.
	 */
	private static final long serialVersionUID = -1735245512910877730L;

	/**
	 * Description of the source dimension.
	 */
	String fromDimension;
	/**
	 * Description of the final dimension.
	 */
	String toDimension;

	/**
	 * Create a new exception.
	 */
	public InvalidScalingException(String fromDimension, String toDimension) {
		super(fromDimension +  "-> " + toDimension
				+ " is invalid scaling dimension");
		this.fromDimension = fromDimension;
		this.toDimension = toDimension;
	}

	/**
	 * Return description of source dimension.
	 * 
	 * @return source dimension.
	 */
	public String getFromDimension() {
		return fromDimension;
	}

	/**
	 * Return description of final dimension.
	 * 
	 * @return final dimension.
	 */
	public String getToDimension() {
		return toDimension;
	}

}
