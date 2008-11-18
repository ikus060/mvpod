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
			 throw new IllegalArgumentException("Invalid dimension " + width + " x " + height); //$NON-NLS-1$ //$NON-NLS-2$
		this.width = width;
		this.height = height;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof VideoDimension) {
			return ((VideoDimension)obj).width == this.width && ((VideoDimension)obj).height == this.height;
		}
		return false;
	}
	
	/**
	 * Return the video height.
	 * 
	 * @return the height.
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Return the video width.
	 * 
	 * @return the width.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.width + this.height;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.width + "x" + this.height; //$NON-NLS-1$
	}
}
