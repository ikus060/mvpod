package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class are usefull to apply frameRate conversion on telecine video.
 * 
 * @author patapouf
 * 
 */
public class InverseTelecineFilter implements VideoFilter {

	/**
	 * Define the input frame rate in fps.
	 */
	private double outputFrameRate;

	/**
	 * Create a new InverseTelecineFilter that will alter the output frame rate.
	 * For each <code>inputFrameRate</code> input frames the filter will
	 * output <code>outputFrameRate</code> frames. <br>
	 * The ratio of <code>inputFrameRate</code>/<code>outputFrameRate</code>
	 * should exactly match the −fps/−ofps options value. <br>
	 * 
	 * This could be used to filter movies that are broadcast on TV at a frame
	 * rate different from their original framerate.
	 * 
	 * @param outputFrameRate the ouput frame rate in fps
	 */
	public InverseTelecineFilter(double outputFrameRate) {
		if (outputFrameRate <= 0) {
			throw new IllegalArgumentException(
					"Invalid ouput frame rate value " + outputFrameRate);
		}
		this.outputFrameRate = outputFrameRate;
	}

	/**
	 * Return the output frame rate.
	 * 
	 * @return the output frame rate.
	 */
	public double getOutputFrameRate() {
		return outputFrameRate;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#getPriority()
	 */
	public int getPriority() {
		return 0;
	}
	
	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#toCommandList()
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {

		String value = "";
		value += "filmdint=";
		value += String.format("io=%s:%s",
				inputVideoInfo.getFrameRate(),
				outputFrameRate);

		String[] args = new String[2];
		args[0] = "-vf-add";
		args[1] = value;
		return args;
	}

}
