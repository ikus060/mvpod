package net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class define available options to encode audio stream in FAAC format.
 * 
 * @author patapouf
 * 
 */
public class FAACEncodingOptions extends AudioEncodingOptions {
	/**
	 * Sample rate.
	 */
	private static final int SAMPLE_RATE_DEFAULT = 48000;
	/**
	 * Minimum bitrate value.
	 */
	public static final int BITRATE_MIN_VALUE = 0;
	/**
	 * Maximum bitrate value.
	 */
	public static final int BITRATE_MAX_VALUE = 1024;
	
	/**
	 * MPEG version 2.
	 */
	public static final int MPEG_VERSION_2 = 2;
	/**
	 * MPEG version 4.
	 */
	public static final int MPEG_VERSION_4 = 4;

	/**
	 * Main object type complexity.
	 */
	public static final int OBJECT_TYPE_MAIN = 1;
	/**
	 * Low object type complexity.
	 */
	public static final int OBJECT_TYPE_LOW = 2;
	/**
	 * SRS object type complexity.
	 */
	public static final int OBJECT_TYPE_SSR = 3;
	/**
	 * LTP object type complexity.
	 */
	public static final int OBJECT_TYPE_LTP = 4;

	/**
	 * Bitrate value in Kbps.
	 */
	private int bitrate;
	/**
	 * Mpeg version to use.
	 */
	private int mpegVersion;
	/**
	 * Object type complexity.
	 */
	private int objectType;

	/**
	 * Create a new FAACOption with given average bitrate.
	 * 
	 * @param bitrate
	 *            the average bitrate in Kbps.
	 */
	public FAACEncodingOptions(int bitrate) {
		
		super(AudioFormat.FORMAT_MPEG4_AAC);
		
		if (bitrate < BITRATE_MIN_VALUE || bitrate>BITRATE_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid bitrate value " //$NON-NLS-1$
					+ bitrate);
		}
		this.bitrate = bitrate;
		this.mpegVersion = MPEG_VERSION_4;
		this.objectType = OBJECT_TYPE_MAIN;
		this.setOutputSampleRate(SAMPLE_RATE_DEFAULT);
	}

	/**
	 * Return the average bitrate in Kbps.
	 * 
	 * @return the average bitrate in Kbps.
	 */
	public int getBitrate() {
		return this.bitrate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.AudioEncodingOptions#getChannels()
	 */
	public int getChannels() {
		return AudioEncodingOptions.CHANNELS_STEREO;
	}

	/**
	 * Return the MPEG Version.
	 * 
	 * @return the MPEG Version
	 */
	public int getMPEGVersion() {
		return this.mpegVersion;
	}

	/**
	 * Return the object type complexity.
	 * 
	 * @return the object type complexity
	 */
	public int getObjectType() {
		return this.objectType;
	}

	/**
	 * Set the average bitrate value in Kbps.
	 * 
	 * @param bitrate
	 *            the new average bitrate in Kbps
	 */
	public void setBitrate(int bitrate) {
		if (bitrate < BITRATE_MIN_VALUE || bitrate>BITRATE_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid bitrate value " //$NON-NLS-1$
					+ bitrate);
		}
		this.bitrate = bitrate;
	}

	/**
	 * Set the mpeg version of the resulting audio. Default value are MPEG4
	 * 
	 * @param version
	 *            the mpeg version (Valid value are 2 or 4)
	 */
	public void setMPEGVersion(int version) {
		if (version != MPEG_VERSION_2 && version != MPEG_VERSION_4) {
			throw new IllegalArgumentException("Invalid mpeg version " //$NON-NLS-1$
					+ version);
		}
		this.mpegVersion = version;
	}

	/**
	 * Set object type complexity. Default value are 1 : MAIN.
	 * 
	 * @param type
	 *            the object type complexcity.
	 */
	public void setObjectType(int type) {
		if (this.objectType != OBJECT_TYPE_MAIN && this.objectType != OBJECT_TYPE_LOW
				&& this.objectType != OBJECT_TYPE_SSR
				&& this.objectType != OBJECT_TYPE_LTP) {
			throw new IllegalArgumentException("Invalid object type " + type); //$NON-NLS-1$
		}
		this.objectType = type;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.AudioOption#toCommandList()
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {

		String value = ""; //$NON-NLS-1$

		value += "br=" + this.bitrate; //$NON-NLS-1$
		value += ":mpeg=" + this.mpegVersion; //$NON-NLS-1$
		value += ":object=" + this.objectType; //$NON-NLS-1$

		String[] defaultArgs = super.toCommandList(inputVideoInfo);

		String[] args = new String[6 + defaultArgs.length];
		args[0] = "-oac"; //$NON-NLS-1$
		args[1] = "faac"; //$NON-NLS-1$
		args[2] = "-faacopts"; //$NON-NLS-1$
		args[3] = value;
		args[4] = "-channels"; //$NON-NLS-1$
		args[5] = Integer.toString(2);

		System.arraycopy(defaultArgs, 0, args,
				args.length - defaultArgs.length, defaultArgs.length);

		return args;

	}

}
