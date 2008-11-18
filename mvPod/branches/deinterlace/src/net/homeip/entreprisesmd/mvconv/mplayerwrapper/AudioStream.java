package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * This class represent an audio stream. It's can be use to define the audio
 * stream to encode or only to retrieve a list of available audio stream.
 * 
 * For a list of every language code :
 * http://www.loc.gov/standards/iso639-2/php/code_list.php
 * 
 * @author patapouf
 * 
 */
public class AudioStream {

	/**
	 * Audio identifier.
	 */
	private String audioID;
	/**
	 * Audio bitrate in Kbps(if available).
	 */
	private int bitrate;
	/**
	 * Audio smaple rate in Hz (if available).
	 */
	private int sampleRate;
	/**
	 * Audio channel (number of channel) (if available).
	 */
	private int channel;
	/**
	 * Audio format (if available).
	 */
	private AudioFormat format;
	/**
	 * Audio language (if available).
	 */
	private String langage;

	/**
	 * Create a new Audio Stram with only audio identifier.
	 * 
	 * @param audioID
	 *            the audio identifier.
	 */
	public AudioStream(String audioID) {
		this.audioID = audioID;
		this.bitrate = -1;
		this.sampleRate = -1;
		this.channel = -1;
		this.format = null;
		this.langage = null;
	}

	/**
	 * Create a new Audio stream.
	 * 
	 * @param audioID
	 *            the audio identifier
	 * @param bitrate
	 *            the audio bitrate in kbps
	 * @param sampleRate
	 *            the samplerate in Hz
	 * @param channel
	 *            the number of audio channel
	 * @param format
	 *            the audio format
	 * @param langage
	 *            the language as define in the ISO 639-1 or ISO 639-2
	 */
	public AudioStream(String audioID, int bitrate, int sampleRate,
			int channel, AudioFormat format, String langage) {
		this.audioID = audioID;
		this.bitrate = bitrate;
		this.sampleRate = sampleRate;
		this.channel = channel;
		this.format = format;
		this.langage = langage;
	}

	/**
	 * Return the Audio format.
	 * 
	 * @return the audio format
	 */
	public AudioFormat getAudioFormat() {
		return this.format;
	}

	/**
	 * Return the audio track ID.
	 * 
	 * @return the audio track ID
	 */
	public String getAudioID() {
		return this.audioID;
	}

	/**
	 * Return the bitrate in Kbps.
	 * 
	 * @return the bitrate in Kbps
	 */
	public int getBitrate() {
		return this.bitrate;
	}

	/**
	 * Return the channel Mode.
	 * 
	 * @return the channel mode
	 */
	public int getChannelMode() {
		return this.channel;
	}

	/**
	 * Return the language identifier as define in the ISO 639-1 or ISO 639-2.
	 * 
	 * @return the language identifier as define in the ISO 639-1 or ISO 639-2
	 */
	public String getLanguage() {
		return this.langage;
	}

	/**
	 * Return the sample rate in Hz.
	 * 
	 * @return the sample rate in Hz
	 */
	public int getSampleRate() {
		return this.sampleRate;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "AudioID " + this.audioID; //$NON-NLS-1$
	}

}
