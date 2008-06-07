package net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This filter implements software volume control. Use this filter with caution
 * since it can reduce the signal to noise ratio of the sound. In most cases it
 * is best to set the level for the PCM sound to max, leave this filter out and
 * control the output level to your speakers with the master volume control of
 * the mixer. In case your sound card has a digital PCM mixer instead of an
 * analog one, and you hear distortion, use the MASTER mixer instead. If there
 * is an external amplifier connected to the computer (this is almost always the
 * case), the noise level can be minimized by adjusting the master level and the
 * volume knob on the amplifier until the hissing noise in the background is
 * gone. This filter has a second feature: It measures the overall maximum sound
 * level and prints out that level when MPlayer exits. This volume estimate can
 * be used for setting the sound level in MEncoder such that the maximum dynamic
 * range is utilized.
 * 
 * NOTE: This filter is not reentrant and can therefore only be enabled once for
 * every audio stream.
 * 
 * WARNING: This feature creates distortion and should be considered a last
 * resort.
 * 
 * EXAMPLE: mplayer −af volume=10.1:0 media.avi Would amplify the sound by
 * 10.1dB and hard-clip if the sound level is too high.
 * 
 * 
 * @author patapouf
 * 
 */
public class VolumeFilter implements AudioFilter {

	/**
	 * The desired grain in dB.
	 */
	private int grain;
	/**
	 * Softclipping enable or disable.
	 */
	private boolean softCliping;

	/**
	 * Create a new volume filter with default value.<br>
	 * Grain : 0, softclipping turn off.
	 */
	public VolumeFilter() {
		this(0, false);
	}

	/**
	 * Create a new volume filter with given grain value and softclipping.
	 * 
	 * @param grain
	 *            the grain value in dB
	 * @param softClipping
	 *            True to enable the soft clipping
	 */
	public VolumeFilter(int grain, boolean softClipping) {
		this.grain = grain;
		this.softCliping = softClipping;
	}

	/**
	 * Return the desire grain in dB.
	 * 
	 * @return the grain in dB
	 */
	public int getGrain() {
		return this.grain;
	}

	/**
	 * Return True if soft clipping are enabled.
	 * 
	 * @return true if soft clipping are enabled
	 */
	public boolean getSoftClipping() {
		return this.softCliping;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiofilter.AudioFilter#toArgument()
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {
		String[] args = new String[2];
		args[0] = "-af-add";
		args[1] = "volume=" + getGrain() + ":"
				+ (getSoftClipping() ? "1" : "0");
		return args;
	}

}
