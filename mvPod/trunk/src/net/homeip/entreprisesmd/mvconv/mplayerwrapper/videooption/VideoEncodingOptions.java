package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class represent the audio options associate to a specific audio format.
 * This options are use when encoding video stream.
 * 
 * @author patapouf
 * 
 */
public abstract class VideoEncodingOptions {
	/**
	 * NTSC-M frame rate (value: 29.97).
	 */
	public static final double NTSC_FRAME_RATE = 29.97;
	/**
	 * NTSC-film frame rate (value: 23.976).
	 */
	public static final double NTSC_FILM_FRAME_RATE = 23.976;
	/**
	 * PAL-N frame rate (value: 25).
	 */
	public static final double PAL_FRAME_RATE = 25;
	/**
	 * Minimum frame rate value.
	 */
	public static final double FRAME_RATE_MIN_VALUE = 0.1;
	/**
	 * Maximum frame rate value.
	 */
	public static final double FRAME_RATE_MAX_VALUE = 60.0;
	
	/**
	 * Frame rate value for the video output.
	 */
	private double frameRate = -1;
	/**
	 * Maximum frame rate value for the video output.
	 */
	private double maxFrameRate = -1;
	/**
	 * Mumber of pass (1 or 2).
	 */
	private int pass = 1;
	/**
	 * Video format product by this encoding options.
	 */
	private VideoFormat videoFormat;

	/**
	 * Create a new video encoding options.
	 * 
	 * @param format
	 *            the video format create by this encoding options. This value
	 *            can be change with the method <code>setVideFormat</code>.
	 */
	public VideoEncodingOptions(VideoFormat format) {
		if (format == null) {
			throw new NullPointerException();
		}
		this.videoFormat = format;
	}

	/**
	 * Return the averabe bitrate to be used in Kbps.
	 * 
	 * @return the average bitrate in Kbps
	 */
	public abstract int getBitrate();

	/**
	 * Return the maximum frames per second (fps) value for the output file or
	 * -1 if this options are disable.
	 * 
	 * @return the maximum frames per second (fps) value for the output file or
	 *         -1 if this options are disable.
	 */
	public double getMaxOutputFrameRate() {
		return maxFrameRate;
	}

	/**
	 * Return the frames per second (fps) value for the output file or -1 if
	 * this options are disable.
	 * 
	 * @return the frames per second (fps) value for the output file or -1 if
	 *         this options are disable
	 */
	public double getOutputFrameRate() {
		return frameRate;
	}

	/**
	 * Return the number of pass.
	 * 
	 * @return the number of pass
	 */
	public int getPass() {
		return pass;
	}

	/**
	 * Return the video format product by this encoding options.
	 * 
	 * @return the video format.
	 */
	public VideoFormat getVideoFormat() {
		return videoFormat;
	}

	/**
	 * Set the average bitrate to be used in kbits/second.
	 * 
	 * @param bitrate
	 *            the new average bitrate value in Kbps
	 */
	public abstract void setBitrate(int bitrate);

	/**
	 * Set a max frames per second (fps) value for the output file, which can be
	 * different from that of the source material.
	 * 
	 * @param frameRate
	 *            the framerate
	 */
	public void setMaxOutputFrameRate(double frameRate) {
		if (frameRate <= 0) {
			throw new IllegalArgumentException("Invalid frame rate "
					+ frameRate);
		}
		this.maxFrameRate = frameRate;
	}

	/**
	 * Set a frames per second (fps) value for the output file, which can be
	 * different from that of the source material.
	 * 
	 * @param frameRate
	 *            the framerate
	 */
	public void setOutputFrameRate(double frameRate) {
		if (frameRate <= 0 && frameRate!=-1.0) {
			throw new IllegalArgumentException("Invalid frame rate "
					+ frameRate);
		}
		this.frameRate = frameRate;
	}

	/**
	 * Set the number of pass.
	 * 
	 * @param pass
	 *            the number of pass
	 */
	public void setPass(int pass) {
		if (pass < 1 && pass > 2) {
			throw new IllegalArgumentException("Invalid pass value " + pass);
		}
		this.pass = pass;
	}

	/**
	 * Use by sub class to define the video format produce by this encoding
	 * options.
	 * 
	 * @param format
	 *            the new video format.
	 */
	protected void setVideoFormat(VideoFormat format) {
		if (format == null) {
			throw new NullPointerException();
		}
		this.videoFormat = format;
	}

	/**
	 * Return an argument list that will be add to mencoder arguments when
	 * executing the encoding. Sub-class may overload this function to add it's
	 * own arguments.
	 * 
	 * @param inputVideoInfo
	 *            detail information about input video
	 * @param pass
	 *            the pass count
	 * @return the argument list
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo, int pass) {

		if (frameRate != -1) {

			String[] args = new String[2];
			args[0] = "-ofps";
			args[1] = Double.toString(frameRate);
			return args;

		} else if (maxFrameRate != -1) {

			if (inputVideoInfo.getFrameRate() == -1
					|| inputVideoInfo.getFrameRate() > maxFrameRate) {
				String[] args = new String[2];
				args[0] = "-ofps";
				args[1] = Double.toString(maxFrameRate);
				return args;
			}
		}

		return new String[0];
	}

}
