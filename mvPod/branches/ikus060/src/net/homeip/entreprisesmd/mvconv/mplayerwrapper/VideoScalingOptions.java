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
	 * This method try to scale the source video to fit the final dimension and
	 * the crop the exeding part of the video. The result is a video that match
	 * exactly the given dimension.
	 */
	public static final int METHODE_CROP = 1 << 1;
	/**
	 * This method try to scale the source video to match the given dimension.
	 * It's possible that the resulting video are smaller or equal to the given
	 * dimension.
	 */
	public static final int METHODE_SCALE = 1 << 2;
	/**
	 * This method try to scale the source video and fill the empty part with
	 * black band to produce an output video that match exactly the given
	 * dimension.
	 */
	public static final int METHODE_FILL = 1 << 3;

	/**
	 * Rounding the dimension to the closest multiple of 16.
	 */
	public static final int MULTIPLE_16 = 1 << 4;

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
		this(width, height, METHODE_SCALE);
	}

	/**
	 * Create a new VideoScalingOptions with scaling method.
	 * 
	 * @param dimension
	 *            the video dimension.
	 */
	public VideoScalingOptions(VideoDimension dimension) {
		this(dimension, METHODE_SCALE);
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

		int mask = METHODE_CROP | METHODE_FILL | METHODE_SCALE | MULTIPLE_16;
		style = style & mask;
		this.method = METHODE_SCALE;
		if ((style & METHODE_CROP) != 0) {
			this.method = METHODE_CROP;
		} else if ((style & METHODE_FILL) != 0) {
			this.method = METHODE_FILL;
		} else if ((style & METHODE_SCALE) != 0) {
			this.method = METHODE_SCALE;
		}

		if ((style & MULTIPLE_16) != 0) {
			this.mutiple = 16;
		}

		if (dimension==null){
			throw new NullPointerException();
		}

		this.dimension = dimension;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof VideoScalingOptions) {
			return ((VideoScalingOptions) obj).dimension.equals(dimension)
					&& ((VideoScalingOptions) obj).method == method;
		}
		return false;
	}

	/**
	 * Return the video height.
	 * 
	 * @return the height.
	 */
	public int getHeight() {
		return dimension.getHeight();
	}

	/**
	 * Return the scaling method.
	 * 
	 * @return the method.
	 */
	public int getScalingMethod() {
		return method;
	}

	/**
	 * Return the video dimension (width and height value).
	 * 
	 * @return the dimension.
	 */
	public VideoDimension getVideoDimension() {
		return dimension;
	}

	/**
	 * Return the video width.
	 * 
	 * @return the width.
	 */
	public int getWidth() {
		return dimension.getWidth();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return dimension.hashCode();
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

		} else {

			return (value + multiple) - (value + multiple) % multiple;

		}

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

		int width = dimension.getWidth();
		int height = dimension.getHeight();

		String value = "";
		if (method == METHODE_CROP) {

			int scaledWidth = width;
			int scaledHeight = inputHeight * width / inputWidth;
			scaledHeight = scaledHeight - (scaledHeight % 2);
			if (scaledHeight < height) {
				scaledWidth = (inputWidth * height) / inputHeight;
				scaledWidth = scaledWidth - (scaledWidth % 2);
				scaledHeight = height;
			}
			value = "scale=" + scaledWidth + ":" + scaledHeight + ",crop="
					+ round(width, mutiple) + ":" + round(height, mutiple);

		} else if (method == METHODE_FILL) {

			int scaledWidth = width;
			int scaledHeight = inputHeight * width / inputWidth;
			scaledHeight = scaledHeight - (scaledHeight % 2);
			if (scaledHeight > height) {
				scaledWidth = (inputWidth * height) / inputHeight;
				scaledWidth = scaledWidth - (scaledWidth % 2);
				scaledHeight = height;
			}
			value = "scale=" + scaledWidth + ":" + scaledHeight + ",expand="
					+ round(width, mutiple) + ":" + round(height, mutiple);

		} else {

			int scaledWidth = width;
			int scaledHeight = inputHeight * width / inputWidth;
			scaledHeight = scaledHeight - (scaledHeight % 2);
			if (scaledHeight > height) {
				scaledWidth = (inputWidth * height) / inputHeight;
				scaledWidth = scaledWidth - (scaledWidth % 2);
				scaledHeight = height;
			}

			if(mutiple==16){
				value = "scale=" + round(scaledWidth, mutiple) + ":-10";				
			} else {
				value = "scale=" + round(scaledWidth, mutiple) + ":"
				+ round(scaledHeight, mutiple);
			}

		}

		String[] args = new String[2];
		args[0] = "-vf-add";
		args[1] = value;
		return args;

	}
}
