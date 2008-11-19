package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

public class YadifDeinterlaceFilter extends DeinterlaceFilter {

	/**
	 * If the decoder does not export the appropriate information, it falls back
	 * to 0 (top field first).
	 */
	public static final int FIELDS_DOMINANCE_AUTO = -1;
	/**
	 * bottom field first
	 */
	public static final int FIELDS_DOMINANCE_BOTTOM_FIRST = 1;

	/**
	 * top field first
	 */
	public static final int FIELDS_DOMINANCE_TOP_FIRST = 0;
	/**
	 * Output 1 frame for each field.
	 */
	public static final int MODE_DOUBLE_FRAMERATE = 2;
	/**
	 * Output 1 frame for each frame.
	 */
	public static final int MODE_ORIGINAL_FRAMERATE = 1;

	/**
	 * True to skips spatial interlacing check.
	 */
	boolean disableSpatialCheck;

	/**
	 * Define the field dominance value;
	 */
	int fieldsDominance;

	/**
	 * Define the mode to use.
	 */
	int mode;

	/**
	 * Create a new deinterlacing filter with Yadif.
	 * 
	 * @param mode
	 *            Define hte mode to use
	 * @param spatialCheck
	 *            True to enable spatialCheck (default : true)
	 */
	public YadifDeinterlaceFilter(int mode, boolean spatialCheck, int fields) {
		this.mode = mode;
		this.disableSpatialCheck = !spatialCheck;
		this.fieldsDominance = fields;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof YadifDeinterlaceFilter) {
			YadifDeinterlaceFilter yadif = (YadifDeinterlaceFilter) obj;

			return yadif.mode == this.mode
					&& yadif.disableSpatialCheck == this.disableSpatialCheck
					&& yadif.fieldsDominance == this.fieldsDominance;
		}
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.mode;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter#toCommandList(net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo)
	 */
	public String[] toCommandList(VideoInfo inputVideoInfo) {
		// Mode
		// 0: Output 1 frame for each frame.
		// 1: Output 1 frame for each field.
		// 2: Like 0 but skips spatial interlacing check.
		// 3: Like 1 but skips spatial interlacing check.

		int yadifMode = this.mode + (this.disableSpatialCheck ? 2 : 0);

		String[] args = new String[4];
		args[0] = "-vf-add"; //$NON-NLS-1$
		args[1] = "yadif=" + yadifMode; //$NON-NLS-1$
		args[2] = "-field-dominance"; //$NON-NLS-1$
		args[3] = Integer.toString(this.fieldsDominance);
		return null;
	}

}
