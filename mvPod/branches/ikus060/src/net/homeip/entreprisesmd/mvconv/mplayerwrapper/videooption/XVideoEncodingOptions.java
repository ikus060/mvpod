package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * Create a new XVideo encoding options.
 * 
 * @author patapouf
 * 
 */
public class XVideoEncodingOptions extends VideoEncodingOptions {

	/**
	 * Minimum bitrate value.
	 */
	public static final int BITRATE_MIN_VALUE = 0;
	/**
	 * Maximum bitrate value.
	 */
	public static final int BITRATE_MAX_VALUE = 16000;
	/**
	 * Maximum B-Frame.
	 */
	private static final int MAX_BFRAME = 4;

	/**
	 * Define the bitrate value in Kbps.<br>
	 * bitrate=?
	 */
	private int bitrate;
	/**
	 * Define the maximum number of B-Frame.<br>
	 * max_bframes=?
	 */
	private int maxBFrame; // max_bframes
	/**
	 * True if cartoon options are enabled.<br>
	 * (no)cartoon
	 */
	private boolean enableCartoon;
	/**
	 * True if quarter pixel options are enabled.<br>
	 * (no)qpel
	 */
	private boolean enableQuarterPixel;
	/**
	 * True if trellis options are enabled<br>
	 * (no)trellis
	 */
	private boolean enableTrellis;

	/**
	 * Create a simple XVideo encoding option.
	 * 
	 * @param bitrate
	 *            the average bitrate in Kbps
	 */
	public XVideoEncodingOptions(int bitrate) {

		super(VideoFormat.FORMAT_MPEG_XVID);

		if (bitrate < BITRATE_MIN_VALUE || bitrate > BITRATE_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid bitrate value " //$NON-NLS-1$
					+ bitrate);
		}
		this.bitrate = bitrate;
		this.maxBFrame = 2;
		this.enableCartoon=false;
		this.enableQuarterPixel=false;
		this.enableTrellis = true;
	}

	/**
	 * Activate this if your encoded sequence is an anime/cartoon. It modifies
	 * some Xvid internal thresholds so Xvid takes better decisions on frame
	 * types and motion vectors for flat looking cartoons..
	 * 
	 * @param enable
	 *            True to enable cartoon options.
	 */
	public void enableCartoon(boolean enable) {
		this.enableCartoon = enable;
	}

	/**
	 * MPEG-4 uses a half pixel precision for its motion search by default. The
	 * standard proposes a mode where encoders are allowed to use quarter pixel
	 * precision. This option usually results in a sharper image. Unfortunately
	 * it has a great impact on bitrate and sometimes the higher bitrate use
	 * will prevent it from giving a better image quality at a fixed bitrate. It
	 * is better to test with and without this option and see whether it is
	 * worth activating.
	 * 
	 * @param enable
	 *            True to enable quarter pixel options.
	 */
	public void enableQuarterPixel(boolean enable) {
		this.enableQuarterPixel = enable;
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
	 * Return the average bitrate value.
	 * 
	 * @return the bitrate in Kbps
	 */
	public int getBitrate() {
		return this.bitrate;
	}

	/**
	 * Return the maximum number of B-frames to put between I/P-frames (default:
	 * 2).
	 * 
	 * @return the maximum number of B-frames
	 */
	public int getMaxBFrame() {
		return this.maxBFrame;
	}
	/**
	 * Return True if cartoon option are enable.
	 * 
	 * @return True if cartoon option are enable.
	 */
	public boolean isCartoonEnabled() {
		return this.enableCartoon;
	}
	/**
	 * Return True if quarter pixel options are enabled.
	 * 
	 * @return True if quarter pixel options are enabled.
	 */
	public boolean isQuarterPixelEnabled() {
		return this.enableQuarterPixel;
	}
	/**
	 * Return True if rate-distortion optimal quantization are enabled.
	 * 
	 * @return True if rate-distortion optimal quantization are enabled
	 */
	public boolean isTrellisEnabled() {
		return this.enableTrellis;
	}

	/**
	 * Set the average bitrate value.
	 * 
	 * @param bitrate
	 *            the new average bitrate value in Kbps
	 */
	public void setBitrate(int bitrate) {
		if (bitrate < BITRATE_MIN_VALUE || bitrate > BITRATE_MAX_VALUE) {
			throw new IllegalArgumentException("Invalid bitrate value " //$NON-NLS-1$
					+ bitrate);
		}
		this.bitrate = bitrate;
	}

	/**
	 * Set the maximum number of B-frames to put between I/P-frames.
	 * 
	 * @param maxBFrame
	 *            the maximum number of B-Frame
	 */
	public void setMaxBFrame(int maxBFrame) {
		if (maxBFrame < 0 || maxBFrame > MAX_BFRAME) {
			throw new IllegalArgumentException("Invalid max B-Frame value " //$NON-NLS-1$
					+ maxBFrame);
		}
		this.maxBFrame = maxBFrame;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.VideoEncodingOptions#toCommandList()
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo, int pass) {

		String value = ""; //$NON-NLS-1$

		if (super.getPass() > 1) {
			value += ":pass=" + pass; //$NON-NLS-1$
		}

		value += "bitrate=" + this.bitrate; //$NON-NLS-1$

		value += ":max_bframes=" + this.maxBFrame; //$NON-NLS-1$

		if(this.enableCartoon){
			value += ":cartoon"; //$NON-NLS-1$
		}
		
		if(this.enableQuarterPixel){
			value += ":qpel"; //$NON-NLS-1$
		}
		
		if (!this.enableTrellis) {
			value += ":notrellis"; //$NON-NLS-1$
		}

		String[] defaultArgs = super.toCommandList(inputVideoInfo, pass);

		String[] args = new String[4 + defaultArgs.length];
		args[0] = "-ovc"; //$NON-NLS-1$
		args[1] = "xvid"; //$NON-NLS-1$
		args[2] = "-xvidencopts"; //$NON-NLS-1$
		args[3] = value;

		System.arraycopy(defaultArgs, 0, args,
				args.length - defaultArgs.length, defaultArgs.length);

		return args;
	}

}
