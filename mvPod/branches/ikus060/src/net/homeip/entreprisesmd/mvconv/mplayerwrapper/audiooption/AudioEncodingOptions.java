package net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class represent the audio options associate to a specific audio format.
 * This options are use when encoding ausio stream.
 * 
 * @author patapouf
 * 
 */
public abstract class AudioEncodingOptions {
	/**
	 * Default sample rate.
	 */
	private static final int SAMPLE_RATE_DEFAULT = 44100;
	/**
	 * Minimum sample rate.
	 */
	public static final int SAMPLE_RATE_MIN_VALUE = 1000;
	/**
	 * Maximum sample rate.
	 */
	public static final int SAMPLE_RATE_MAX_VALUE = 192000;
	/**
	 * 1 channels.
	 */
	public static final int CHANNELS_MONO = 1;
	/**
	 * 2 channels.
	 */
	public static final int CHANNELS_STEREO = 2;
	/**
	 * 4 channels.
	 */
	public static final int CHANNELS_SURROUND = 4;
	/**
	 * 6 channels.
	 */
	public static final int CHANNELS_FULL51 = 6;
	/**
	 * Sample rate value in Hz; -1 to don't change the sample rate.
	 */
	private int sampleRate = -1;
	/**
	 * Audio format product by this encoding options.
	 */
	private AudioFormat audioFormat;

	/**
	 * Create a new video encoding options.
	 * 
	 * @param format
	 *            the audio format create by this encoding options. This value
	 *            can be change with the method <code>setAudioFormat</code>.
	 */
	public AudioEncodingOptions(AudioFormat format) {
		if (format == null) {
			throw new NullPointerException();
		}
		this.audioFormat = format;
		this.sampleRate = SAMPLE_RATE_DEFAULT;
	}

	/**
	 * Return the audio format product by this encoding options.
	 * 
	 * @return the audio format.
	 */
	public AudioFormat getAudioFormat() {
		return this.audioFormat;
	}

	/**
	 * Return the averabe bitrate to be used in Kbps.
	 * 
	 * @return the average bitrate in Kbps
	 */
	public abstract int getBitrate();

	/**
	 * Return the number of channels. Sub class may return the appropriate
	 * number according to the selected channel mode.
	 * 
	 * @return the number of channels.
	 */
	public abstract int getChannels();

	/**
	 * Return the output sample rate to be used for audio encoding or -1 if this
	 * options are disable.
	 * 
	 * @return the output sample rate to be used for audio encoding or -1 if
	 *         this options are disable.
	 */
	public int getOutputSampleRate() {
		return this.sampleRate;
	}

	/**
	 * Use by sub class to define the audio format produce by this encoding
	 * options.
	 * 
	 * @param format
	 *            the new audio format.
	 */
	protected void setAudioFormat(AudioFormat format) {
		if (format == null) {
			throw new NullPointerException();
		}
		this.audioFormat = format;
	}

	/**
	 * Set the average bitrate to be used in kbits/second.
	 * 
	 * @param bitrate
	 *            the new average bitrate value in Kbps
	 */
	public abstract void setBitrate(int bitrate);

	/**
	 * Set the output sample rate to be used for audio encoding (of course sound
	 * cards have limits on this). The default is fast resampling that may cause
	 * distortion.
	 * 
	 * @param sampleRate
	 *            the ouput sample rate in Hz
	 */
	public void setOutputSampleRate(int sampleRate) {
		if (sampleRate < SAMPLE_RATE_MIN_VALUE
				|| sampleRate > SAMPLE_RATE_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid sample rate " //$NON-NLS-1$
					+ sampleRate);
		}
		this.sampleRate = sampleRate;
	}

	/**
	 * Return an argument list that will be add to mencoder arguments when
	 * executing the encoding. Sub-class may overload this function to add it's
	 * own arguments.
	 * 
	 * @param inputVideoInfo
	 *            detail information about input video
	 * @return the argument list
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {

		if (this.sampleRate != -1) {
			String[] args = new String[2];
			args[0] = "-srate"; //$NON-NLS-1$
			args[1] = Integer.toString(this.sampleRate);
			return args;
		}
		return new String[0];
	}

}
