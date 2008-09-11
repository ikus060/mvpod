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
	private static final String DVD_NOT_AVAILABLE = "^Couldn't open DVD device: (.*)$"; //$NON-NLS-1$
	/**
	 * Regular expression to match GrabXVPort exception.
	 */
	private static final String GRAB_XV_PORT_FAILED = "Could not find free Xvideo port"; //$NON-NLS-1$
	/**
	 * Regular expression to seek failed.
	 */
	private static final String SEEK_FAILED = "Seek failed"; //$NON-NLS-1$
	/**
	 * Regular expression to read header.
	 */
	private static final String READING_HEADER_FAILED = "Main header checksum mismatch"; //$NON-NLS-1$
	/**
	 * Regular expression when palette are invalid.
	 */
	private static final String PALETTE_INVALID = "QT palette: skipped entry \\(out of count\\):"; //$NON-NLS-1$
	/**
	 * Maximum number of palette error before throwing an exception.
	 */
	private static final int PALETTE_COUNT_MAX = 25;
	/**
	 * Regular expression to match if MP4Box decide to rename the file.
	 */
	private static final String MP4BOX_ERROR_RENAMING_FILE = "^Error renaming file (.*)$"; //$NON-NLS-1$

	/**
	 * Regular expression when Xvideo port are not available.
	 */
	private static final String XVIDEO_PORT_NOTE_AVAILABLE = "It seems there is no Xvideo support for your video card available."; //$NON-NLS-1$

	/**
	 * Regular expression when scaling dimension are invalid.
	 */
	private static final String INVALID_SCALING_DIMENSION = "swScaler: (.*) -> (.*) is invalid scaling dimension";

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
	 * Notify this class that exception have been found.
	 * <p>
	 * Sub class may implement this method to do some supprementary instruction
	 * when an exception are found.
	 * </p>
	 * 
	 * @param exceptionFound
	 *            the exception found.
	 * @return True to continue the parsing process.
	 */
	public boolean exceptionFound(MPlayerException exceptionFound) {
		this.exception = exceptionFound;
		return false;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.StreamParser#parseLine(java.lang.String)
	 */
	public boolean parseLine(String line) {

		System.out.println(line);

		if (this.exception != null) {
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
			this.paletteCount++;
			if (this.paletteCount >= PALETTE_COUNT_MAX) {
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

		matcher = Pattern
				.compile(XVIDEO_PORT_NOTE_AVAILABLE, Pattern.MULTILINE)
				.matcher(line);
		if (matcher.find()) {
			return exceptionFound(new XvPortNotAvailableException());
		}

		matcher = Pattern.compile(INVALID_SCALING_DIMENSION, Pattern.MULTILINE)
				.matcher(line);
		if (matcher.find()) {
			return exceptionFound(new InvalidScalingException(matcher.group(1), matcher.group(2)));
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
		if (this.exception != null) {
			throw this.exception;
		}
	}

}
