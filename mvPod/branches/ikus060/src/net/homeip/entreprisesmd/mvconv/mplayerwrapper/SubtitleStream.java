package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * This class contain sub title information.
 * 
 * @author patapouf
 * 
 */
public class SubtitleStream {

	/**
	 * The subtitle identifer.
	 */
	private String subtitleID;
	/**
	 * The subtitle language as define in the ISO 639-1 or ISO 639-2 (if
	 * available).
	 */
	private String langage;

	/**
	 * Create a new Subtitle information with the given information.
	 * 
	 * @param subtitleID
	 *            the subtitle unique identifier.
	 * @param language
	 *            the subtitle language as define in the ISO 639-1 or ISO 639-2.
	 */
	public SubtitleStream(String subtitleID, String language) {
		if (subtitleID == null) {
			throw new NullPointerException();
		}
		this.subtitleID = subtitleID;
		this.langage = language;
	}

	/**
	 * Return the language of this subtitle (if available).
	 * 
	 * @return the language as define in the ISO 639-1 or ISO 639-2.
	 */
	public String getLanguage() {
		return this.langage;
	}

	/**
	 * Return the subtitle identifier.
	 * 
	 * @return the subtitle identifier.
	 */
	public String getSubtitleID() {
		return this.subtitleID;
	}

}
