package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * This class represent the video sclaing options . This class define the wanted
 * width and height of the video and the cropping mode to use to fit the video
 * to this dimension.
 * <p>
 * This cropping method are important for some device that can't handle the
 * scaling of the video. it's possible to fit the source video into the given
 * dimension by scaling it. Some device support this method but other can't
 * handle the empty space let the scaling. This empty space need to be fill by
 * black band.
 * </p>
 * 
 * @author patapouf
 * 
 */
public class VideoScalingOptions {
	/**
	 * This method scale the video to best fit the given dimension and crop the
	 * excess part.
	 */
	public static final int METHOD_CROP = 1 << 1;
	/**
	 * This method scale the video and don't bother about the aspect ratio.
	 */
	public static final int METHOD_SCALE = 1 << 2;
	/**
	 * This method scale the video to best fit the given dimension and fill the
	 * remaining space with black band.
	 */
	public static final int METHOD_FILL = 1 << 3;

	/**
	 * Thie method scale the video to best fit the given dimension.
	 */
	public static final int METHOD_FIT = 1 << 4;

	/**
	 * Rounding the dimension to the closest multiple of 16.
	 */
	public static final int MULTIPLE_16 = 1 << 5;

	/**
	 * Video dimension.
	 */
	private VideoDimension dimension;
	/**
	 * The fit method to use.
	 */
	private int method;

	/**
	 * Round dimension to the closest multiple of this value.
	 */
	private int mutiple = 1;

	/**
	 * Create a new VideoScalingOptions with scaling method.
	 * 
	 * @param width
	 *            the width of the video.
	 * @param height
	 *            the height of the video.
	 */
	public VideoScalingOptions(int width, int height) {
		this(width, height, METHOD_FIT);
	}

	/**
	 * Create a new VideoScalingOptions with scaling method.
	 * 
	 * @param dimension
	 *            the video dimension.
	 */
	public VideoScalingOptions(VideoDimension dimension) {
		this(dimension, METHOD_FIT);
	}

	/**
	 * Create a new VideoScalingOptions.
	 * 
	 * @param width
	 *            the width of the video.
	 * @param height
	 *            the height of the video.
	 * @param method
	 *            the fit method.
	 */
	public VideoScalingOptions(int width, int height, int method) {
		this(new VideoDimension(width, height), method);
	}

	/**
	 * Create a new VideoScalingOptions.
	 * 
	 * @param width
	 *            the width of the video.
	 * @param height
	 *            the height of the video.
	 * @param style
	 *            the fit method.
	 */
	public VideoScalingOptions(VideoDimension dimension, int style) {

		int mask = METHOD_CROP | METHOD_FILL | METHOD_SCALE | METHOD_FIT
				| MULTIPLE_16;
		int scaleOptions = style & mask; 
		this.method = METHOD_FIT;
		if ((scaleOptions & METHOD_CROP) != 0) {
			this.method = METHOD_CROP;
		} else if ((scaleOptions & METHOD_FILL) != 0) {
			this.method = METHOD_FILL;
		} else if ((scaleOptions & METHOD_FIT) != 0) {
			this.method = METHOD_FIT;
		} else if ((scaleOptions & METHOD_SCALE) != 0) {
			this.method = METHOD_SCALE;
		}

		if ((scaleOptions & MULTIPLE_16) != 0) {
			this.mutiple = 16;
		}

		if (dimension == null) {
			throw new NullPointerException();
		}

		this.dimension = dimension;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof VideoScalingOptions) {
			return ((VideoScalingOptions) obj).dimension.equals(this.dimension)
					&& ((VideoScalingOptions) obj).method == this.method;
		}
		return false;
	}

	/**
	 * Return the video height.
	 * 
	 * @return the height.
	 */
	public int getHeight() {
		return this.dimension.getHeight();
	}

	/**
	 * Return the scaling method.
	 * 
	 * @return the method.
	 */
	public int getScalingMethod() {
		return this.method;
	}

	/**
	 * Return the video dimension (width and height value).
	 * 
	 * @return the dimension.
	 */
	public VideoDimension getVideoDimension() {
		return this.dimension;
	}

	/**
	 * Return the video width.
	 * 
	 * @return the width.
	 */
	public int getWidth() {
		return this.dimension.getWidth();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.dimension.hashCode();
	}

	/**
	 * Use to round to closest multiple of 16.
	 * 
	 * @param value
	 *            the input value
	 * @return the rounded value.
	 */
	private int round(int value, int multiple) {
		if (value % multiple <= (value + multiple) % multiple) {
			return value - value % multiple;
		}
		return (value + multiple) - (value + multiple) % multiple;
	}

	/**
	 * Return an argument list that will be add to mencoder arguments when
	 * executing the encoding.
	 * 
	 * @param inputVideoInfo
	 *            detail information about input video
	 * @return the argument list
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {

		// Check if we have the input video dimension
		if (inputVideoInfo.getVideoDimention() == null) {
			return new String[0];
		}

		int inputWidth = inputVideoInfo.getVideoDimention().getWidth();
		int inputHeight = inputVideoInfo.getVideoDimention().getHeight();

		int width = this.dimension.getWidth();
		int height = this.dimension.getHeight();

		String value = ""; //$NON-NLS-1$
		if (this.method == METHOD_CROP) {

			int scaledWidth = width;
			int scaledHeight = inputHeight * width / inputWidth;
			scaledHeight = scaledHeight - (scaledHeight % 2);
			if (scaledHeight < height) {
				scaledWidth = (inputWidth * height) / inputHeight;
				scaledWidth = scaledWidth - (scaledWidth % 2);
				scaledHeight = height;
			}
			value = "scale=" + scaledWidth + ":" + scaledHeight + ",crop=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ round(width, this.mutiple) + ":" + round(height, this.mutiple); //$NON-NLS-1$

		} else if (this.method == METHOD_FILL) {

			int scaledWidth = width;
			int scaledHeight = inputHeight * width / inputWidth;
			scaledHeight = scaledHeight - (scaledHeight % 2);
			if (scaledHeight > height) {
				scaledWidth = (inputWidth * height) / inputHeight;
				scaledWidth = scaledWidth - (scaledWidth % 2);
				scaledHeight = height;
			}
			value = "scale=" + scaledWidth + ":" + scaledHeight + ",expand=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ round(width, this.mutiple) + ":" + round(height, this.mutiple); //$NON-NLS-1$

		} else if (this.method == METHOD_SCALE) {

			if (this.mutiple == 16) {
				value = "scale=" + round(width, this.mutiple) + ":" //$NON-NLS-1$ //$NON-NLS-2$
						+ round(height, this.mutiple) + ",crop=" //$NON-NLS-1$
						+ round(width, this.mutiple) + ":" + round(height, this.mutiple); //$NON-NLS-1$
			} else {
				value = "scale=" + width + ":" + height + ",crop=" + width //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ ":" + height; //$NON-NLS-1$
			}

		} else /* if(method == METHOD_FIT) */{

			int scaledWidth = width;
			int scaledHeight = inputHeight * width / inputWidth;
			scaledHeight = scaledHeight - (scaledHeight % 2);
			if (scaledHeight > height) {
				scaledWidth = (inputWidth * height) / inputHeight;
				scaledWidth = scaledWidth - (scaledWidth % 2);
				scaledHeight = height;
			}

			if (this.mutiple == 16) {
				value = "scale=" + round(scaledWidth, this.mutiple) + ":-10"; //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				value = "scale=" + scaledWidth + ":-2"; //$NON-NLS-1$ //$NON-NLS-2$
			}

		}

		String[] args = new String[2];
		args[0] = "-vf-add"; //$NON-NLS-1$
		args[1] = value;
		return args;

	}
}
