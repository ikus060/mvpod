package net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

public class TfieldsDeinterlaceFilter extends DeinterlaceFilter {
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
	 * Define the field dominance value;
	 */
	int fieldsDominance;
	
	public TfieldsDeinterlaceFilter(int fields){
		this.fieldsDominance = fields;
	}
	
	public String[] toCommandList(VideoInfo inputVideoInfo) {
		//0: Leave fields unchanged (will jump/flicker).
		//1: Interpolate missing lines. (The algorithm used might not be so good.)
		//2: Translate fields by 1/4 pixel with linear interpolation (no jump).
		//4: Translate fields by 1/4 pixel with 4tap filter (higher quality) (default).
		
		String[] args = new String[4];
		args[0] = "-vf-add"; //$NON-NLS-1$
		args[1] = "tfields=4"; //$NON-NLS-1$
		args[2] = "-field-dominance"; //$NON-NLS-1$
		args[3] = Integer.toString(this.fieldsDominance);
		
		return null;
	}

}
