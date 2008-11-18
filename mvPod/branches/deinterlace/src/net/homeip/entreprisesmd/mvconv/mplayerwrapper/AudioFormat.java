package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contain all functionally to handle audio format.
 * <p>
 * This class can be use to map mplayer audio format value to one of the
 * constant value of this class.
 * </p>
 * 
 * @author patapouf
 * 
 */
public class AudioFormat {

	public static final AudioFormat FORMAT_AC3 = new AudioFormat("FORMAT_AC3", //$NON-NLS-1$
			new String[] { ".ac3" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_ACELP = new AudioFormat(
			"FORMAT_ACELP", new String[] { ".wma" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_ADPCM = new AudioFormat(
			"FORMAT_ADPCM", new String[] { ".wav" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_ALAC = new AudioFormat(
			"FORMAT_ALAC", new String[] { ".mp4a" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_ALAW = new AudioFormat(
			"FORMAT_ALAW", new String[] { ".wav" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_ALF = new AudioFormat("FORMAT_ALF", //$NON-NLS-1$
			new String[] { ".alf" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_AMR = new AudioFormat("FORMAT_AMR", //$NON-NLS-1$
			new String[] { ".amr", ".awb" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_ATRAC = new AudioFormat(
			"FORMAT_ATRAC", new String[] { ".wav" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_CREATIVE = new AudioFormat(
			"FORMAT_CREATIVE", new String[] { ".wav" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_DTS = new AudioFormat("FORMAT_DTS", //$NON-NLS-1$
			new String[] { ".dts", ".wav" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_DVDPCM = new AudioFormat(
			"FORMAT_DVDPCM", new String[0]); //$NON-NLS-1$
	public static final AudioFormat FORMAT_FLAC = new AudioFormat(
			"FORMAT_FLAC", new String[] { ".flac" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_G726 = new AudioFormat(
			"FORMAT_G726", new String[] { ".g726" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_GSM = new AudioFormat("FORMAT_GSM", //$NON-NLS-1$
			new String[] { ".gsm" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_IMA = new AudioFormat("FORMAT_IMA", //$NON-NLS-1$
			new String[] { ".wav" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_IMA_PCM = new AudioFormat(
			"FORMAT_IMA_PCM", new String[] { ".pcm" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_IMC = new AudioFormat("FORMAT_IMC", //$NON-NLS-1$
			new String[] { ".wav" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_MAC3 = new AudioFormat(
			"FORMAT_MAC3", new String[] { ".aiff" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_MAC6 = new AudioFormat(
			"FORMAT_MAC6", new String[] { ".aiff" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_MAD = new AudioFormat("FORMAT_MAD", //$NON-NLS-1$
			new String[] { ".mp3" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_MP3 = new AudioFormat("FORMAT_MP3", //$NON-NLS-1$
			new String[] { ".mp3" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_MPC = new AudioFormat("FORMAT_MPC", //$NON-NLS-1$
			new String[] { ".mpc" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_MPEG4_AAC = new AudioFormat(
			"FORMAT_MPEG4_AAC", new String[] { ".aac", ".aacp" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static final AudioFormat FORMAT_MSN = new AudioFormat("FORMAT_MSN", //$NON-NLS-1$
			new String[] { ".wav" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_PCM = new AudioFormat("FORMAT_PCM", //$NON-NLS-1$
			new String[] { ".pcm", ".wav" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_QCLP = new AudioFormat(
			"FORMAT_QCLP", new String[0]); //$NON-NLS-1$
	public static final AudioFormat FORMAT_QDM2 = new AudioFormat(
			"FORMAT_QDM2", new String[0]); //$NON-NLS-1$
	public static final AudioFormat FORMAT_QDMC = new AudioFormat(
			"FORMAT_QDMC", new String[0]); //$NON-NLS-1$
	public static final AudioFormat FORMAT_SHRN = new AudioFormat(
			"FORMAT_SHRN", new String[] { ".shn" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_SPX = new AudioFormat("FORMAT_SPX", //$NON-NLS-1$
			new String[] { ".spx" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_TRSP = new AudioFormat(
			"FORMAT_TRSP", new String[] { ".wav" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_TTA = new AudioFormat("FORMAT_TTA", //$NON-NLS-1$
			new String[] { ".tta" }); //$NON-NLS-1$
	public static final AudioFormat FORMAT_ULAW = new AudioFormat(
			"FORMAT_ULAW", new String[] { ".ulaw" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_VOWR = new AudioFormat(
			"FORMAT_VOWR", new String[0]); //$NON-NLS-1$
	public static final AudioFormat FORMAT_VRBS = new AudioFormat(
			"FORMAT_VRBS", new String[] { ".ogg", ".ogm" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static final AudioFormat FORMAT_WAV_PCM = new AudioFormat(
			"FORMAT_WAV_PCM", new String[] { ".wav" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_WMA1 = new AudioFormat(
			"FORMAT_WMA1", new String[] { ".wma" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_WMA9 = new AudioFormat(
			"FORMAT_WMA9", new String[] { ".wma" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final AudioFormat FORMAT_WVPK = new AudioFormat(
			"FORMAT_WVPK", new String[] { ".wv" }); //$NON-NLS-1$ //$NON-NLS-2$

	private static Map<String, AudioFormat> formats = new HashMap<String, AudioFormat>();
	static {
		formats.put("0", FORMAT_PCM); //$NON-NLS-1$
		formats.put("1", FORMAT_PCM); //$NON-NLS-1$
		formats.put("2", FORMAT_PCM); //$NON-NLS-1$
		formats.put("3", FORMAT_WVPK); //$NON-NLS-1$
		formats.put("6", FORMAT_ALAW); //$NON-NLS-1$
		formats.put("7", FORMAT_ULAW); //$NON-NLS-1$
		formats.put("17", FORMAT_IMA); //$NON-NLS-1$
		formats.put("34", FORMAT_TRSP); //$NON-NLS-1$
		formats.put("49", FORMAT_MSN); //$NON-NLS-1$
		formats.put("50", FORMAT_MSN); //$NON-NLS-1$
		formats.put("69", FORMAT_G726); //$NON-NLS-1$
		formats.put("80", FORMAT_MAD); //$NON-NLS-1$
		formats.put("85", FORMAT_MP3); //$NON-NLS-1$
		formats.put("117", FORMAT_VOWR); //$NON-NLS-1$
		formats.put("255", FORMAT_MPEG4_AAC); //$NON-NLS-1$
		formats.put("304", FORMAT_ACELP); //$NON-NLS-1$
		formats.put("352", FORMAT_WMA1); //$NON-NLS-1$
		formats.put("353", FORMAT_WMA9); //$NON-NLS-1$
		formats.put("354", FORMAT_WMA9); //$NON-NLS-1$
		formats.put("512", FORMAT_CREATIVE); //$NON-NLS-1$
		formats.put("624", FORMAT_ATRAC); //$NON-NLS-1$
		formats.put("1025", FORMAT_IMC); //$NON-NLS-1$
		formats.put("8132", FORMAT_ALF);  //$NON-NLS-1$
		formats.put("8192", FORMAT_AC3);  //$NON-NLS-1$
		formats.put("8193", FORMAT_DTS);  //$NON-NLS-1$
		formats.put("28781", FORMAT_MPEG4_AAC);  //$NON-NLS-1$
		formats.put("65537", FORMAT_DVDPCM);  //$NON-NLS-1$
		formats.put("33583981", FORMAT_ADPCM);  //$NON-NLS-1$
		formats.put("285242221", FORMAT_IMA_PCM);  //$NON-NLS-1$
		formats.put("agsm", FORMAT_GSM);  //$NON-NLS-1$
		formats.put("alac", FORMAT_ALAC);  //$NON-NLS-1$
		formats.put("alaw", FORMAT_ALAW);  //$NON-NLS-1$
		formats.put("fl32", FORMAT_PCM);  //$NON-NLS-1$
		formats.put("fLaC", FORMAT_FLAC);  //$NON-NLS-1$
		formats.put("ima4", FORMAT_IMA);  //$NON-NLS-1$
		formats.put("MAC3", FORMAT_MAC3);  //$NON-NLS-1$
		formats.put("MAC6", FORMAT_MAC6);  //$NON-NLS-1$
		formats.put("MP4A", FORMAT_MPEG4_AAC);  //$NON-NLS-1$
		formats.put("mp4a", FORMAT_MPEG4_AAC);  //$NON-NLS-1$
		formats.put("MPC ", FORMAT_MPC);  //$NON-NLS-1$
		formats.put("NONE", FORMAT_PCM);  //$NON-NLS-1$
		formats.put("Qclp", FORMAT_QCLP);  //$NON-NLS-1$
		formats.put("QDM2", FORMAT_QDM2);  //$NON-NLS-1$
		formats.put("QDMC", FORMAT_QDMC);  //$NON-NLS-1$
		formats.put("RADV", FORMAT_WAV_PCM);  //$NON-NLS-1$
		formats.put("raw ", FORMAT_PCM);  //$NON-NLS-1$
		formats.put("samr", FORMAT_AMR);  //$NON-NLS-1$
		formats.put("shrn", FORMAT_SHRN);  //$NON-NLS-1$
		formats.put("sowt", FORMAT_PCM);  //$NON-NLS-1$
		formats.put("spx ", FORMAT_SPX);  //$NON-NLS-1$
		formats.put("TTA1", FORMAT_TTA);  //$NON-NLS-1$
		formats.put("twos", FORMAT_PCM);  //$NON-NLS-1$
		formats.put("ulaw", FORMAT_ULAW);  //$NON-NLS-1$
		formats.put("vrbs", FORMAT_VRBS);  //$NON-NLS-1$
		formats.put("WVPK", FORMAT_WVPK);  //$NON-NLS-1$
	}

	/**
	 * Return the associated audio format, as define in VideoInfo, according to
	 * the given video identifer return by mplayer.
	 * 
	 * @param formatID
	 *            the formatID return by mplayer.
	 * @return the format identifier.
	 */
	public static AudioFormat fromMplayerAudioFormatID(String formatID) {

		return formats.get(formatID);

	}

	/**
	 * Format identifier.
	 */
	private String formatId;
	/**
	 * File extensions list.
	 */
	private String[] fileExtentions;

	/**
	 * Create a new audio format object.
	 * 
	 * @param id
	 *            the format identifier.
	 */
	private AudioFormat(String id, String[] extentions) {
		this.formatId = id;
		this.fileExtentions = extentions;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof AudioFormat) {
			return ((AudioFormat) obj).formatId.equals(this.formatId);
		}
		return false;
	}

	/**this.
	 * Return the format id.
	 * 
	 * @return format identifier.
	 */
	public String getFormatID() {
		return this.formatId;
	}

	/**
	 * Return a list of associated files extention. (e.g.: .mp3, .flac, .ogg)
	 * 
	 * @return list of file extentions.
	 */
	public String[] getFileExtentions() {
		return this.fileExtentions.clone();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.formatId.hashCode();
	}

}
