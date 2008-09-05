package net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class define available options to encode audio stream in Lame format
 * (also know as mp3).
 * 
 * @author patapouf
 * 
 */
public class LameEncodingOptions extends AudioEncodingOptions {

	/**
	 * Maximum bitrate value.
	 */
	public static final int BITRATE_MAX_VALUE = 1024;
	/**
	 * Maximum bitrate value.
	 */
	public static final int BITRATE_MIN_VALUE = 0;
	/**
	 * Minimum quality value.
	 */
	public static final int QUALITY_MIN_VALUE = 1;
	/**
	 * Maximum quality value.
	 */
	public static final int QUALITY_MAX_VALUE = 9;

	/**
	 * Average bitrate type.
	 */
	public static final int TYPE_AVERAGE_BITRATE = 0;
	/**
	 * Constant bitrate type.
	 */
	public static final int TYPE_CONSTANT_BITRATE = 1;
	/**
	 * Variable bitrate type.
	 */
	public static final int TYPE_VARIABLE_BITRATE = 2;

	/**
	 * CBR variable bitrate method.
	 */
	public static final int VARIABLE_METHOD_CBR = 0;
	/**
	 * MT variable bitrate method.
	 */
	public static final int VARIABLE_METHOD_MT = 1;
	/**
	 * RH variable bitrate method (default value).
	 */
	public static final int VARIABLE_METHOD_RH = 2;
	/**
	 * ABR variable bitrate method.
	 */
	public static final int VARIABLE_METHOD_ABR = 3;
	/**
	 * MTRH variable bitrate method.
	 */
	public static final int VARIABLE_METHOD_MTRH = 4;

	/**
	 * Automatic channel mode.
	 */
	public static final int MODE_AUTO = -1;
	/**
	 * Stereo channel mode.
	 */
	public static final int MODE_STEREO = 0;
	/**
	 * Join Stereo channel mode.
	 */
	public static final int MODE_JOIN_STEREO = 1;
	/**
	 * Dual channel mode.
	 */
	public static final int MODE_DUAL_CHANNEL = 2;
	/**
	 * Mono channel mode.
	 */
	public static final int MODE_MONO = 3;

	/**
	 * Type of encoding (average, constant, variable).
	 */
	private int type;
	/**
	 * Average bitrate.
	 */
	private int bitrate;
	/**
	 * Quality value.
	 */
	private int quality;
	/**
	 * Variable method to use.
	 */
	private int variableMethod = VARIABLE_METHOD_RH;
	/**
	 * Channel mode.
	 */
	private int mode; // mode
	/**
	 * Using the fast encoding mode.
	 */
	private boolean fast;

	/**
	 * Create a new LameOption.
	 * 
	 * @param bitrate
	 *            the average bitrate in Kbps.
	 * @param type
	 *            the type of the resulting audio stream (average bitrate,
	 *            constant bitrate or variable bitrate).
	 */
	public LameEncodingOptions(int bitrate, int type) {
		
		super(AudioFormat.FORMAT_MP3);
		
		
		if (bitrate < BITRATE_MIN_VALUE || bitrate > BITRATE_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid bitrate value " //$NON-NLS-1$
					+ bitrate);
		}
		if (type != TYPE_AVERAGE_BITRATE && type != TYPE_CONSTANT_BITRATE
				&& type != TYPE_VARIABLE_BITRATE) {
			throw new IllegalArgumentException("Invalid type " + type); //$NON-NLS-1$
		}
		this.type = type;
		this.bitrate = bitrate;
		this.quality = 0;
		this.variableMethod = VARIABLE_METHOD_RH;
		this.mode = MODE_AUTO;
		this.fast = false;
	}

	/**
	 * Create a new LameOption with variable bitrate.
	 * 
	 * @param bitrate
	 *            the bitrate in Kbps.
	 * @param method
	 *            the variable bitrate encoding method.
	 * @param quality
	 *            the algorithmic quality (value 0 to 9).
	 * @param fast
	 *            true to activate the faster mode (resulting in lower quality
	 *            and higher bitrate).
	 */
	public LameEncodingOptions(int bitrate, int method, int quality,
			boolean fast) {
		
		super(AudioFormat.FORMAT_MP3);
		
		if (bitrate < BITRATE_MIN_VALUE || bitrate > BITRATE_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid bitrate value " //$NON-NLS-1$
					+ bitrate);
		}
		if (method != VARIABLE_METHOD_CBR && method != VARIABLE_METHOD_MT
				&& method != VARIABLE_METHOD_RH
				&& method != VARIABLE_METHOD_ABR
				&& method != VARIABLE_METHOD_MTRH) {
			throw new IllegalArgumentException("Invalid variable methode " //$NON-NLS-1$
					+ method);
		}
		if (quality < QUALITY_MIN_VALUE || quality > QUALITY_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid quality " + quality); //$NON-NLS-1$
		}
		this.type = TYPE_VARIABLE_BITRATE;
		this.bitrate = bitrate;
		this.quality = quality;
		this.variableMethod = VARIABLE_METHOD_RH;
		this.mode = MODE_AUTO;
		this.fast = fast;
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

		switch(this.mode){
		case MODE_STEREO: return CHANNELS_STEREO;
		case MODE_JOIN_STEREO: return CHANNELS_STEREO;
		case MODE_DUAL_CHANNEL: return CHANNELS_STEREO;
		case MODE_MONO: return CHANNELS_MONO;
		case MODE_AUTO: return CHANNELS_STEREO;
		default: return CHANNELS_STEREO;
		}
		
	}
	
	/**
	 * Return the audio channel mode : stereo, joint-stereo, dualchannel, mono.
	 * Default value are set to automatic detection.
	 * 
	 * @return the audio channel mode.
	 */
	public int getChannelMode() {
		return this.mode;
	}

	/**
	 * Return the encoding type of the resulting audio stream. (average bitrate,
	 * constant bitrate or variable bitrate).
	 * 
	 * @return the encoding type.
	 */
	public int getEncodingType() {
		return this.type;
	}

	/**
	 * Return true if the faster encoding are enable. This parameter are only
	 * effective on variable bitrate encoding type.
	 * 
	 * @return true if the faster encoding are enabled.
	 */
	public boolean getFasterEncoding() {
		return this.fast;
	}

	/**
	 * Return the variable bitrate encoding method.
	 * 
	 * @return the variable bitrate encoding method.
	 */
	public int getVariableMethod() {
		return this.variableMethod;
	}

	/**
	 * Return the quality of the audio stream when encoding in variable bitrate.
	 * This parameter are only efficient when encoding in Variable bitrate type.
	 * 
	 * @return the quality level (0 : highest, 9 : Lowest)
	 */
	public int getVariableQuality() {
		return this.quality;
	}

	/**
	 * Set the average bitrate value in Kbps.
	 * 
	 * @param bitrate
	 *            the average bitrate in Kbps.
	 */
	public void setBitrate(int bitrate) {
		if (bitrate < BITRATE_MIN_VALUE || bitrate > BITRATE_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid bitrate value " //$NON-NLS-1$
					+ bitrate);
		}
		this.bitrate = bitrate;
	}

	/**
	 * Set the audio channel mode : stereo, joint-stereo, dualchannel, mono.
	 * Default value are set to automatic detection.
	 * 
	 * @param mode
	 *            the audio channel mode.
	 */
	public void setChannelMode(int mode) {
		if (mode != MODE_AUTO && mode != MODE_STEREO
				&& mode != MODE_JOIN_STEREO && mode != MODE_DUAL_CHANNEL
				&& mode != MODE_MONO) {
			throw new IllegalArgumentException("Invalid channel mode " + mode); //$NON-NLS-1$
		}
		this.mode = mode;
	}

	/**
	 * Set the encoding type of the resulting audio stream.
	 * 
	 * @param type
	 *            the encoding type (average, constant or variable).
	 */
	public void setEncodingType(int type) {
		if (type != TYPE_AVERAGE_BITRATE && type != TYPE_CONSTANT_BITRATE
				&& type != TYPE_VARIABLE_BITRATE) {
			throw new IllegalArgumentException("Invalid type " + type); //$NON-NLS-1$
		}
		this.type = type;
	}

	/**
	 * Set the faster encoding on variable bitrate. This results in slightly
	 * lower quality and higher bitrates. This parameter are only effective with
	 * variable bitrate encoding type.
	 * 
	 * @param faster
	 *            true to enable faster encoding.
	 */
	public void setFasterEncoding(boolean faster) {
		this.fast = faster;
	}

	/**
	 * Set the variable bitrate encoding methode : cbr, mt, rh, abr or mtrh.
	 * Default are rh.
	 * 
	 * @param methode
	 *            the variable bitrate encoding method.
	 */
	public void setVariableMethode(int methode) {
		if (methode != VARIABLE_METHOD_CBR && methode != VARIABLE_METHOD_MT
				&& methode != VARIABLE_METHOD_RH
				&& methode != VARIABLE_METHOD_ABR
				&& methode != VARIABLE_METHOD_MTRH) {
			throw new IllegalArgumentException("Invalid variable methode " //$NON-NLS-1$
					+ methode);
		}
		this.variableMethod = methode;
	}

	/**
	 * Set the quality of the audio stream when encoding in variable bitrate.
	 * This parameter are only efficient when encoding in Variable bitrate type.
	 * 
	 * @param quality
	 *            the quality level (0 : highest, 9 : Lowest)
	 */
	public void setVariableQuality(int quality) {
		if (quality < QUALITY_MIN_VALUE || quality > QUALITY_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid quality " + quality); //$NON-NLS-1$
		}
		this.quality = quality;
	}

	/**
	 * Return an argument list that will be add to mencoder arguments when
	 * executing the encoding. Sub-class may overload this function to add it's
	 * own arguments.
	 * 
	 * @param inputVideoInfo
	 *            detail information about input video
	 * @return the argument list
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.AudioOption#toCommandList()
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {

		String value = ""; //$NON-NLS-1$

		switch (this.type) {
		case TYPE_AVERAGE_BITRATE:
			value += "abr"; //$NON-NLS-1$
			value += ":br=" + this.bitrate; //$NON-NLS-1$
			break;

		case TYPE_CONSTANT_BITRATE:
			value += "cbr"; //$NON-NLS-1$
			value += ":br=" + this.bitrate; //$NON-NLS-1$
			break;
		case TYPE_VARIABLE_BITRATE:
			value += "vbr=" + this.variableMethod; //$NON-NLS-1$
			value += ":q=" + this.quality; //$NON-NLS-1$
			if (this.fast) {
				value += ":fast"; //$NON-NLS-1$
			}
			break;
		default:
			// Not supposed to happen
		}

		if (this.mode != MODE_AUTO) {
			value += ":mode=" + this.mode; //$NON-NLS-1$
		}

		String[] defaultArgs = super.toCommandList(inputVideoInfo);

		String[] args = new String[4 + defaultArgs.length];
		args[0] = "-oac"; //$NON-NLS-1$
		args[1] = "mp3lame"; //$NON-NLS-1$
		args[2] = "-lameopts"; //$NON-NLS-1$
		args[3] = value;

		System.arraycopy(defaultArgs, 0, args,
				args.length - defaultArgs.length, defaultArgs.length);

		return args;
	}



}
