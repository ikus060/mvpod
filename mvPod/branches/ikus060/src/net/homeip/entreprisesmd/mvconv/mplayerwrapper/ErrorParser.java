package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class can be use to parse the content read by a Stream read. This
 * implementation parse the data to find any possible exception that can happen
 * in the process.
 * 
 * @author patapouf
 * 
 */
public class ErrorParser implements StreamParser {
	/**
	 * Regular expression to match if there is a DVDNotAvailable exception.
	 */
	private static final String DVD_NOT_AVAILABLE = "^Couldn't open DVD device: (.*)$";
	/**
	 * Regular expression to match GrabXVPort exception.
	 */
	private static final String GRAB_XV_PORT_FAILED = "Could not find free Xvideo port";
	/**
	 * Regular expression to seek failed.
	 */
	private static final String SEEK_FAILED = "Seek failed";
	/**
	 * Regular expression to read header.
	 */
	private static final String READING_HEADER_FAILED = "Main header checksum mismatch";
	/**
	 * Regular expression when palette are invalid.
	 */
	private static final String PALETTE_INVALID = "QT palette: skipped entry \\(out of count\\):";
	/**
	 * Maximum number of palette error before throwing an exception.
	 */
	private static final int PALETTE_COUNT_MAX = 25;

	/**
	 * Regular expression to match if MP4Box decide to rename the file.
	 */
	private static final String MP4BOX_ERROR_RENAMING_FILE = "^Error renaming file (.*)$";

	/**
	 * Regular expression to read header.
	 */
	// TODO : Move this error in VideoInfo with a method like
	// isSupportedVideo(), isSupportAudio()
	// private static final String UNSUPPORTED_VIDEO_FORMAT = "Cannot find codec
	// matching selected -vo and video format";
	/**
	 * Exception found.
	 */
	private MPlayerException exception = null;

	/**
	 * Pallet error counter.
	 */
	private int paletteCount;

	/**
	 * Create a new error parser.
	 * 
	 */
	public ErrorParser() {

	}

	/**
	 * Notify this class that exception have been found.
	 * <p>
	 * Sub class may implement this method to do some supprementary instruction
	 * when an exception are found.
	 * </p>
	 * 
	 * @param exception
	 *            the exception found.
	 * @return True to continue the parsing process.
	 */
	public boolean exceptionFound(MPlayerException exception) {
		this.exception = exception;
		return false;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.StreamParser#parseLine(java.lang.String)
	 */
	public boolean parseLine(String line) {

		System.out.println(line);
		
		if (exception != null) {
			return true;
		}

		Matcher matcher;
		matcher = Pattern.compile(DVD_NOT_AVAILABLE, Pattern.MULTILINE)
				.matcher(line);
		if (matcher.find()) {
			return exceptionFound(new DVDNotAvailableException(matcher.group(1)));
		}

		matcher = Pattern.compile(GRAB_XV_PORT_FAILED, Pattern.MULTILINE)
				.matcher(line);
		if (matcher.find()) {
			return exceptionFound(new GrabXvPortException());
		}

		matcher = Pattern.compile(SEEK_FAILED, Pattern.MULTILINE).matcher(line);
		if (matcher.find()) {
			return exceptionFound(new SeekException());
		}

		matcher = Pattern.compile(READING_HEADER_FAILED, Pattern.MULTILINE)
				.matcher(line);
		if (matcher.find()) {
			return exceptionFound(new SeekException());
		}

		matcher = Pattern.compile(PALETTE_INVALID, Pattern.MULTILINE).matcher(
				line);
		if (matcher.find()) {
			paletteCount++;
			if (paletteCount >= PALETTE_COUNT_MAX) {
				return exceptionFound(new SeekException());
			}
		}

		matcher = Pattern
				.compile(MP4BOX_ERROR_RENAMING_FILE, Pattern.MULTILINE)
				.matcher(line);
		if (matcher.find()) {
			return exceptionFound(new MP4BoxRenamingFileException(matcher
					.group(1)));
		}

		
		
		return true;
	}

	/**
	 * Throw the exception found in the parsing process if there is one.
	 * 
	 * @throws MPlayerException
	 *             if the parse detec any error.
	 */
	public void throwException() throws MPlayerException {
		if (exception != null) {
			throw exception;
		}
	}

}
