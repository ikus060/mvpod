package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * This class represent the dimension of a video (width x height in pixel).
 * 
 * @author patapouf
 * 
 */
public class VideoDimension {

	/**
	 * Video height.
	 */
	private int height;
	/**
	 * Video width.
	 */
	private int width;

	/**
	 * Create a new VideoDimension.
	 * 
	 * @param width
	 *            the width of the video.
	 * @param height
	 *            the height of the video.
	 */
	public VideoDimension(int width, int height) {
		if(width < 0 || height < 0)
			 throw new IllegalArgumentException("Invalid dimension " + width + " x " + height);
		this.width = width;
		this.height = height;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof VideoDimension) {
			return ((VideoDimension)obj).width == width && ((VideoDimension)obj).height == height;
		}
		return false;
	}
	
	/**
	 * Return the video height.
	 * 
	 * @return the height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Return the video width.
	 * 
	 * @return the width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return width + height;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return width + "x" + height;
	}
}
