package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * This interface are use by StreamReader to parse the content of the stream.
 * 
 * @author patapouf
 * 
 */
public interface StreamParser {

	/**
	 * This function are call for every line.
	 * 
	 * @param line
	 *            the line to parse.
	 * @return True to continue the parsing process.
	 */
	boolean parseLine(String line);

}
