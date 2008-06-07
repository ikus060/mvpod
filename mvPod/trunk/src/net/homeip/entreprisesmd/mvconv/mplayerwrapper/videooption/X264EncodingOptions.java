package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class are use as a video option in an EncodingOptions class to encode
 * the input video in x264 video format.
 * <p>
 * This class offer usual option use when encoding a video to x264 format.
 * </p>
 * 
 * @author patapouf
 * 
 */
public class X264EncodingOptions extends VideoEncodingOptions {

	/**
	 * Minimum B-Frame number.
	 */
	public static final int BFRAME_MIN_VALUE = 0;
	/**
	 * Maximum B-Frame number.
	 */
	public static final int BFRAME_MAX_VALUE = 16;
	/**
	 * Minimum bitrate value.
	 */
	public static final int BITRATE_MIN_VALUE = 0;
	/**
	 * Maximum bitrate value.
	 */
	public static final int BITRATE_MAX_VALUE = 9216;
	/**
	 * Level 1.0.
	 */
	public static final String LEVEL_10 = "1";
	/**
	 * Level 1b.
	 */
	public static final String LEVEL_1B = "1b";
	/**
	 * Level 1.1.
	 */
	public static final String LEVEL_11 = "11";
	/**
	 * Level 1.2.
	 */
	public static final String LEVEL_12 = "12";
	/**
	 * Level 1.3.
	 */
	public static final String LEVEL_13 = "13";
	/**
	 * Level 2.0.
	 */
	public static final String LEVEL_20 = "2";
	/**
	 * Level 2.1.
	 */
	public static final String LEVEL_21 = "21";
	/**
	 * Level 2.2.
	 */
	public static final String LEVEL_22 = "22";
	/**
	 * Level 3.0.
	 */
	public static final String LEVEL_30 = "3";
	/**
	 * Level 3.1.
	 */
	public static final String LEVEL_31 = "31";
	/**
	 * Level 3.2.
	 */
	public static final String LEVEL_32 = "32";
	/**
	 * Level 4.0.
	 */
	public static final String LEVEL_40 = "4";
	/**
	 * Level 4.1.
	 */
	public static final String LEVEL_41 = "41";
	/**
	 * Level 4.2.
	 */
	public static final String LEVEL_42 = "42";
	/**
	 * Level 5.0.
	 */
	public static final String LEVEL_50 = "5";
	/**
	 * Level 5.1.
	 */
	public static final String LEVEL_51 = "51";
	/**
	 * Diamond search, radius 1 (fast).
	 */
	public static final int MOTION_ESTIMATION_DIAMON = 1;
	/**
	 * Hexagon search, radius 2 (default).
	 */
	public static final int MOTION_ESTIMATION_HEXAGON = 2;
	/**
	 * Uneven multi-hexagon search (slow).
	 */
	public static final int MOTION_ESTIMATION_UNEVEN = 3;
	/**
	 * Exhaustive search (very slow, and no better than umh).
	 */
	public static final int MOTION_ESTIMATION_EXHAUSTIVE = 4;
	/**
	 * Direct macroblocks are not used.
	 */
	public static final int MOTION_PREDICTION_NONE = 1;
	/**
	 * Motion vectors are extrapolated from neighboring blocks. (default)
	 */
	public static final int MOTION_PREDICTION_SPATIAL = 2;
	/**
	 * Motion vectors are extrapolated from the following P-frame.
	 */
	public static final int MOTION_PREDICTION_TEMPORAL = 3;
	/**
	 * The codec selects between spatial and temporal for each frame.
	 */
	public static final int MOTION_PREDICTION_AUTO = 4;
	/**
	 * Minimum reference frame value.
	 */
	public static final int REFERENCE_FRAME_MIN_VALUE = 1;
	/**
	 * Maximum reference frame value.
	 */
	public static final int REFERENCE_FRAME_MAX_VALUE = 16;
	/**
	 * Default subpel quality value.
	 */
	private static final int SUBQ_DEFAULT = 6;
	/**
	 * Minimum subpel quality value.
	 */
	public static final int SUQ_MIN_VALUE = 1;
	/**
	 * Maximum subpel quality value.
	 */
	public static final int SUQ_MAX_VALUE = 7;
	/**
	 * Define the buffer size to determine the local bitrate value.<br>
	 * vbv_bufsize=?
	 */
	private int averagePeriode;
	/**
	 * Define the bitrate value in Kbps.<br>
	 * bitrate=?
	 */
	private int bitrate;
	/**
	 * Define the bit stream level.<br>
	 * level_idc=?
	 */
	private String bitStreamLevel;
	/**
	 * Define if B-Frame adaptation are enable. (no)b_adapt
	 */
	private boolean enableBFrameAdapt;
	/**
	 * Define if cabac function are enabled. (no)cabac
	 */
	private boolean enableCabac;
	/**
	 * Define if we use all partitions.<br>
	 * partitions=all
	 */
	private boolean enablePartitions;

	/**
	 * Define if we use multiple thread.<br>
	 * threads=auto
	 */
	private boolean enableThreads;
	/**
	 * Define if we use trellis.<br>
	 * trellis=1
	 */
	private boolean enableTrellis;
	/**
	 * Define the number of reference frames.<br>
	 * frameref=?
	 */
	private int frameref;
	/**
	 * Maximum B-frame. bframes=<0−16>
	 */
	private int maxBFrame;
	/**
	 * Define the maximum local bitrate. vbv_maxrate=?
	 */
	private int maxLocalBitrate;
	/**
	 * Define the motion estimation algorithm.<br>
	 * me=?
	 */
	private int motionEstimation;
	/**
	 * Define the motion prediction to use.<br>
	 * direct_pred=?
	 */
	private int motionPrediction;
	/**
	 * Define the subpel rafinemnet quality.<br>
	 * subq=?
	 */
	private int subpelQuality;

	/**
	 * Create a new X264 encoding options with default value.
	 * 
	 * @param bitrate
	 *            the average bitrate in Kbps
	 */
	public X264EncodingOptions(int bitrate) {

		super(VideoFormat.FORMAT_H264_AVC);

		if (bitrate < BITRATE_MIN_VALUE || bitrate > BITRATE_MAX_VALUE) {
			throw new IllegalArgumentException("Ivalid bitrate value "
					+ bitrate);
		}
		this.averagePeriode = -1;
		this.bitrate = bitrate;
		this.bitStreamLevel = LEVEL_51;
		this.enableBFrameAdapt = true;
		this.enableCabac = true;
		this.enablePartitions = true;
		this.enableThreads = false;
		this.enableTrellis = false;
		this.frameref = 1;
		this.maxBFrame = 0;
		this.maxLocalBitrate = -1;
		this.motionEstimation = MOTION_ESTIMATION_HEXAGON;
		this.motionPrediction = MOTION_PREDICTION_SPATIAL;
		this.subpelQuality = SUBQ_DEFAULT;
	}

	/**
	 * Use to automatically decides when to use B-frames and how many, up to the
	 * maximum B-Frame <code>setMaxBFrame</code>. If this option is disabled,
	 * then the maximum number of B-frames is used. (default : enable);
	 * 
	 * @param enabled
	 */
	public void enableBFrameAdapt(boolean enabled) {
		this.enableBFrameAdapt = enabled;
	}

	/**
	 * Set usage of CABAC (Context-Adaptive Binary Arithmetic Coding). Slightly
	 * slows down encoding and decoding, but should save 10−15% bitrate. Unless
	 * you are looking for decoding speed, you should not disable it. By
	 * default, CABAC are enabled.
	 * 
	 * @param enabled
	 *            True to enabled CABAC
	 */
	public void enableCabac(boolean enabled) {
		this.enableCabac = enabled;
	}

	/**
	 * Enable some optional macroblock type. The idea is to find the type and
	 * size that best describe a certain area of the picture. For example, a
	 * global pan is better represented by 16x16 blocks, while small moving
	 * objects are better represented by smaller blocks.
	 * 
	 * @param enable
	 *            True to enable Partitions
	 */
	public void enablePartition(boolean enable) {
		this.enablePartitions = enable;
	}

	/**
	 * Enable multi threading usage when encoding.
	 * 
	 * @param enable
	 *            True to enable multi threading
	 */
	public void enableThreads(boolean enable) {
		this.enableThreads = enable;
	}

	/**
	 * Enabled rate-distortion optimal quantization.
	 * 
	 * @param enable
	 *            True to enable rate-distortion optimal quantization
	 */
	public void enableTrellis(boolean enable) {
		this.enableTrellis = enable;
	}

	/**
	 * Return the averabe bitrate to be used in Kbps.
	 * 
	 * @return the average bitrate in Kbps
	 */
	public int getBitrate() {
		return bitrate;
	}

	/**
	 * Return the bit stream level has define in Annexe A of H.264 Standards.
	 * Defautl value are level 5.1
	 * 
	 * @return the bit stream level
	 */
	public String getBitStreamLevel() {
		return bitStreamLevel;
	}

	/**
	 * Return the maximum number of B-frames to put between I/P-frames (default:
	 * 2).
	 * 
	 * @return the maximum number of B-frames
	 */
	public int getMaxBFrame() {
		return maxBFrame;
	}

	/**
	 * Return the average buffer size for the maximum local bitrate in kbits.
	 * 
	 * @return the average buffer size for the maximum local bitrate in kbits.
	 */
	public int getMaximumBufferSize() {
		return averagePeriode;
	}

	/**
	 * Return the maximum local bitrate value in Kbps.
	 * 
	 * @return the maximum local bitrate value in Kbps.
	 */
	public int getMaximumLocalBitrate() {
		return maxLocalBitrate;
	}

	/**
	 * Returhm the fullpixel motion estimation algorithm.
	 * 
	 * @return the fullpixel motion estimation algorithm
	 */
	public int getMotionEstimation() {
		return motionEstimation;
	}

	/**
	 * Return the type of motion prediction used for direct macroblocks in
	 * B-frames. Default value are spatial type.
	 * 
	 * @return the type of motion prediction used
	 */
	public int getMotionPrediction() {
		return motionPrediction;
	}

	/**
	 * Return the number of previous frames used as predictors in B- and
	 * P-frames (default: 1).
	 * 
	 * @return the number of previous frames used as predictors
	 */
	public int getReferenceFrame() {
		return frameref;
	}

	/**
	 * Return the subpel refinement quality value.
	 * 
	 * @return the subpel refinement quality value
	 */
	public int getSubpelRefinement() {
		return subpelQuality;
	}

	/**
	 * Return True if B-Frame adaptation are enable.
	 * 
	 * @return True if B-Frame adaptation are enable.
	 */
	public boolean isBFrameAdaptEnabled() {
		return enableBFrameAdapt;
	}

	/**
	 * Return usage of CABAC (Context-Adaptive Binary Arithmetic Coding). By
	 * default, CABAC are enabled.
	 * 
	 * @return True if CABAC are enabled.
	 */
	public boolean isCabacEnabled() {
		return enableCabac;
	}

	/**
	 * Return True if optional partitions are enabled.
	 * 
	 * @return True if optional partitions are enabled
	 */
	public boolean isPartitionsEnabled() {
		return enablePartitions;
	}

	/**
	 * Return True if multi-threading are enabled.
	 * 
	 * @return True if multi-threading are enabled.
	 */
	public boolean isThreadsEnabled() {
		return enableThreads;
	}

	/**
	 * Return True if rate-distortion optimal quantization are enabled.
	 * 
	 * @return True if rate-distortion optimal quantization are enabled
	 */
	public boolean isTrellisEnabled() {
		return enableTrellis;
	}

	/**
	 * Set the average bitrate to be used in kbits/second. Since local bitrate
	 * may vary, this average may be inaccurate for very short videos. Constant
	 * bitrate can be achieved by combining this parameters to maximum local
	 * bitrate, at significant reduction in quality.
	 * 
	 * @param bitrate
	 *            the new average bitrate value in Kbps
	 */
	public void setBitrate(int bitrate) {
		if (bitrate < BITRATE_MIN_VALUE || bitrate > BITRATE_MAX_VALUE) {
			throw new IllegalArgumentException("Ivalid bitrate value "
					+ bitrate);
		}
		this.bitrate = bitrate;
	}

	/**
	 * Set the bitstream’s level as defined by annex A of the H.264 standard.
	 * This is used for telling the decoder what capabilities it needs to
	 * support. Use this parameter only if you know what it means, and you have
	 * a need to set it.<br>
	 * Default value are level 5.1.<br>
	 * 
	 * @param level
	 *            the bitstream level
	 */
	public void setBitStreamLevel(String level) {
		if (!level.equals(LEVEL_10) && !level.equals(LEVEL_1B)
				&& !level.equals(LEVEL_11) && !level.equals(LEVEL_12)
				&& !level.equals(LEVEL_13) && !level.equals(LEVEL_20)
				&& !level.equals(LEVEL_21) && !level.equals(LEVEL_22)
				&& !level.equals(LEVEL_30) && !level.equals(LEVEL_31)
				&& !level.equals(LEVEL_32) && !level.equals(LEVEL_40)
				&& !level.equals(LEVEL_41) && !level.equals(LEVEL_42)
				&& !level.equals(LEVEL_50) && !level.equals(LEVEL_51)) {
			throw new IllegalArgumentException(
					"Invalid bit stream level value " + level);
		}
		this.bitStreamLevel = level;
	}

	/**
	 * Set the maximum number of B-frames to put between I/P-frames.
	 * 
	 * @param maxBFrame
	 *            the maximum number of B-Frame
	 */
	public void setMaxBFrame(int maxBFrame) {
		if (maxBFrame < BFRAME_MIN_VALUE || maxBFrame > BFRAME_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid max B-Frame value "
					+ maxBFrame);
		}
		this.maxBFrame = maxBFrame;
	}

	/**
	 * Set the maximum local bitrate in kbits/second. By default this option are
	 * disable. To disable this function set the value to -1.
	 * 
	 * @param bitrate
	 *            the maximum local bitrate value in Kbps
	 * @param bufferSize
	 *            the average buffer size in kbits
	 */
	public void setMaximumLocalBitrate(int bitrate, int bufferSize) {
		if (bitrate <= 0 && bitrate != -1) {
			throw new IllegalArgumentException(
					"Invalid maximum local bitrate value " + bitrate);
		}
		if (bufferSize <= 0) {
			throw new IllegalArgumentException("Invalid buffer size value "
					+ bufferSize);
		}
		this.maxLocalBitrate = bitrate;
		this.averagePeriode = bufferSize;
	}

	/**
	 * Set the fullpixel motion estimation algorithm. Default algorithm are
	 * Hexagon search.
	 * 
	 * @param algorithm
	 *            the new algorithm to use
	 */
	public void setMotionEstimation(int algorithm) {
		if (algorithm != MOTION_ESTIMATION_DIAMON
				&& algorithm != MOTION_ESTIMATION_HEXAGON
				&& algorithm != MOTION_ESTIMATION_UNEVEN
				&& algorithm != MOTION_ESTIMATION_EXHAUSTIVE) {
			throw new IllegalArgumentException(
					"Invalid motion estination algorithm value " + algorithm);
		}
		this.motionEstimation = algorithm;
	}

	/**
	 * Set the type of motion prediction used for direct macroblocks in
	 * B-frames. Default value are spatial type.
	 * 
	 * @param motionPrediction
	 *            the new motion prediction type to use
	 */
	public void setMotionPrediction(int motionPrediction) {
		if (motionPrediction != MOTION_PREDICTION_AUTO
				&& motionPrediction != MOTION_PREDICTION_NONE
				&& motionPrediction != MOTION_PREDICTION_SPATIAL
				&& motionPrediction != MOTION_PREDICTION_TEMPORAL) {
			throw new IllegalArgumentException(
					"Invalid motion prediction value " + motionPrediction);
		}
		this.motionPrediction = motionPrediction;
	}

	/**
	 * Set the number of previous frames used as predictors in B-frame and
	 * P-frame. This is effective in anime, but in live-action material the
	 * improvements usually drop off very rapidly above 6 or so reference
	 * frames. This has no effect on decoding speed, but does increase the
	 * memory needed for decoding. Some decoders can only handle a maximum of 15
	 * reference frames.<br>
	 * By default this value are set to 1.
	 * 
	 * @param number
	 *            the new number of reference frame
	 */
	public void setReferenceFrame(int number) {
		if (number < REFERENCE_FRAME_MIN_VALUE
				|| number > REFERENCE_FRAME_MAX_VALUE) {
			throw new IllegalArgumentException(
					"Invalid reference frame number value " + number);
		}
		this.frameref = number;
	}

	/**
	 * Set the subpel refinement quality. This parameter controls quality versus
	 * speed tradeoffs involved in the motion estimation decision process. i.e.:
	 * subq=5 can compress up to 10% better than subq=1.<br>
	 * <ul>
	 * <li>1 Runs fullpixel precision motion estimation on all candidate
	 * macroblock types. Then selects the best type. Then refines the motion of
	 * that type to fast quarterpixel precision (fastest).</li>
	 * <li>2 Runs halfpixel precision motion estimation on all candidate
	 * macroblock types. Then selects the best type. Then refines the motion of
	 * that type to fast quarterpixel precision.</li>
	 * <li>3 As 2, but uses a slower quarterpixel refinement.</li>
	 * <li>4 Runs fast quarterpixel precision motion estimation on all
	 * candidate macroblock types. Then selects the best type. Then finishes the
	 * quarterpixel refinement for that type.</li>
	 * <li>5 Runs best quality quarterpixel precision motion estimation on all
	 * candidate macroblock types, before selecting the best type (default).
	 * <li>6 Enables rate-distortion optimization of macroblock types in I- and
	 * P-frames.</li>
	 * <li>7 Enables rate-distortion optimization of motion vectors and intra
	 * modes. (best)</li>
	 * </ul>
	 * In the above, "all candidates" does not exactly mean all enabled types:
	 * 4x4, 4x8, 8x4 are tried only if 8x8 is better than 16x16.
	 * 
	 * @param value
	 *            the new subpel refinement value
	 */
	public void setSubpelRefinement(int value) {
		if (value < SUQ_MIN_VALUE || value > SUQ_MAX_VALUE) {
			throw new IllegalArgumentException(
					"Invalid subpel refinement value " + value);
		}
		this.subpelQuality = value;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.VideoEncodingOptions#toCommandList(net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo,
	 *      int)
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo, int pass) {

		String value = "";

		value += "bitrate=" + bitrate;

		if (!enableCabac) {
			value += ":nocabac";
		}

		switch (motionPrediction) {
		case MOTION_PREDICTION_NONE:
			value += ":direct_pred=none";
			break;
		case MOTION_PREDICTION_SPATIAL:
			value += ":direct_pred=spatial";
			break;
		case MOTION_PREDICTION_TEMPORAL:
			value += ":direct_pred=temporal";
			break;
		case MOTION_PREDICTION_AUTO:
			value += ":direct_pred=auto";
			break;
		default:
			// Not supposed to happen
		}

		switch (motionEstimation) {
		case MOTION_ESTIMATION_DIAMON:
			value += ":me=dia";
			break;
		case MOTION_ESTIMATION_HEXAGON:
			value += ":me=hex";
			break;
		case MOTION_ESTIMATION_UNEVEN:
			value += ":me=umh";
			break;
		case MOTION_ESTIMATION_EXHAUSTIVE:
			value += ":me=esa";
			break;
		default:
			// Not supposed to happen
		}

		boolean enableTurbo = super.getPass() > 1 && pass == 1;
		if (!enableTurbo) {
			value += ":frameref=" + frameref;
		}

		value += ":level_idc=" + bitStreamLevel;

		if (enablePartitions) {
			value += ":partitions=all";
		}

		if (super.getPass() > 1) {
			value += ":pass=" + pass;
		}

		if (!enableTurbo) {
			value += ":subq=" + subpelQuality;
		}

		if (enableThreads) {
			value += ":threads=auto";
		}
		if (enableTrellis) {
			value += ":trellis=1";
		}

		if (enableTurbo) {
			value += ":turbo=1";
		}

		if (maxLocalBitrate != -1) {
			value += ":vbv_maxrate=" + maxLocalBitrate;
			value += ":vbv_bufsize=" + averagePeriode;
		}

		value += ":bframes=" + maxBFrame;
		if (maxBFrame > 0 && !enableBFrameAdapt) {
			value += ":nob_adapt";
		}

		String[] defaultArgs = super.toCommandList(inputVideoInfo, pass);

		String[] args = new String[4 + defaultArgs.length];
		args[0] = "-ovc";
		args[1] = "x264";
		args[2] = "-x264encopts";
		args[3] = value;

		System.arraycopy(defaultArgs, 0, args,
				args.length - defaultArgs.length, defaultArgs.length);

		return args;
	}

}
