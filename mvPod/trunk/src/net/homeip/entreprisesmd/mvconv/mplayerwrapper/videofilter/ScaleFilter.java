package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This class represent a scaling filter that will by apply when encoding the
 * video. Scale video to the defined width and height
 * 
 * @deprecated this class must not be use, it's better to use the VideoScaling options.
 * 
 * @author patapouf
 * 
 */
public class ScaleFilter implements VideoFilter {

	/**
	 * Define the ending video resolution in pixel.
	 */
	private int width, height;

	/**
	 * Use to create a new filter to scale the video size to the designated
	 * width and height.
	 * 
	 * @param width
	 *            the final width of the video
	 * @param height
	 *            the final height of the video
	 */
	public ScaleFilter(int width, int height) {
		if (width <= 0) {
			throw new IllegalArgumentException("Invalid width value " + width);
		}
		if (height <= 0) {
			throw new IllegalArgumentException("Invalid height value " + height);
		}
		this.width = width;
		this.height = height;
	}

	/**
	 * Return the height of the resulting video.
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#getPriority()
	 */
	public int getPriority() {
		return 0;
	}
	
	/**
	 * Return the width of the resulting video.
	 * 
	 * @return the width value
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#toCommandList()
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {

		String value = String.format("scale=%d:%d", width, height);

		String[] args = new String[2];
		args[0] = "-vf-add";
		args[1] = value;
		return args;
	}

}
