package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

/**
 * This classe represent a croping filter that will by apply when encoding the
 * video. The croping filter are use to redefine the limit the video. The main
 * usage to this filter are to remove black bands from widescreen movies.
 * 
 * @deprecated this class must not be use, it's better to use the VideoScaling options.
 * 
 * @author patapouf
 * 
 */
public class CropFilter implements VideoFilter {

	/**
	 * Define the dimension of the cropped region.
	 */
	private int width, height;

	/**
	 * Define the x, y coordination of the cropped region.
	 */
	private int x, y;

	/**
	 * Create a new cropping filter with diffrent width and height than the
	 * original video. The croped region are centred with the original video.
	 * 
	 * @param width
	 *            the width of the croped area
	 * @param height
	 *            the height of the croped area
	 */
	public CropFilter(int width, int height) {
		if (width <= 0) {
			throw new IllegalArgumentException("Invalid width value " + width);
		}
		if (height <= 0) {
			throw new IllegalArgumentException("Invalid height value " + height);
		}
		this.width = width;
		this.height = height;
		this.x = -1;
		this.y = -1;
	}

	/**
	 * Create a new cropping filter with a diffrent width and height than the
	 * original video. The croped region are centred to x and y coordinate.
	 * 
	 * @param width
	 *            the width of the croped area
	 * @param height
	 *            the height of the croped area
	 * @param x
	 *            the position of the croped area on X axis
	 * @param y
	 *            the position of the ctoped area on Y axis
	 */
	public CropFilter(int width, int height, int x, int y) {
		if (width <= 0) {
			throw new IllegalArgumentException("Invalid width value " + width);
		}
		if (height <= 0) {
			throw new IllegalArgumentException("Invalid height value " + height);
		}
		if (x <= 0) {
			throw new IllegalArgumentException("Invalid x value " + x);
		}
		if (y <= 0) {
			throw new IllegalArgumentException("Invalid y vlaue " + y);
		}

		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	/**
	 * Return the height of the crop area.
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
	 * Return the width of the crop area.
	 * 
	 * @return the width value
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Return the position of the crop area on X axis.
	 * 
	 * @return the position of the crop area on X axis.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Return the position of the crop area on Y axis.
	 * 
	 * @return the position of the crop area on Y axis.
	 */
	public int getY() {
		return y;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#toCommandList()
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {

		String value = "";
		if (x > 0 && y > 0) {
			value = String.format("=%d:%d:%d:%d", width, height, x, y);
		} else {
			value = String.format("=%d:%d", width, height);
		}

		String[] args = new String[2];
		args[0] = "-vf-add";
		args[1] = value;
		return args;
	}

}
