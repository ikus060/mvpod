/*

 * InputVideo.java
 * Copyright (C) 2005-2007 James Lee
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 * 
 * $Id: InputVideo.java 161 2007-04-14 19:52:46Z jlee $
 */
package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * This interface are use to represent an InputVideo.
 * 
 * @author patapouf
 * 
 */
public abstract class InputVideo {

	/**
	 * Aspect ratio 16:9.
	 */
	public static final double ASPECT_RATIO_16_9 = 1.7777;
	/**
	 * Aspect ratio 4:3.
	 */
	public static final double ASPECT_RATIO_4_3 = 1.3333;
	/**
	 * Aspect ratio, Keep original value.
	 */
	public static final double ASPECT_RATIO_KEEP = 0;
	
	/**
	 * Aspect ratio.
	 */
	private double overwriteAspectRatio = 0;
	
	/**
	 * Return True if the <code>obj</code> are equals to this object.
	 * 
	 * @param obj
	 *            the object to compare with.
	 * @return True if the <code>obj</code> are equals to this object.
	 */
	public abstract boolean equals(InputVideo obj);

	/**
	 * Return the aspect ratio of the video file.
	 * 
	 * @return The aspect ratio that overwrite the default value or 0 if we keep
	 *         the default value.
	 */
	public double getAspectRatio() {
		return overwriteAspectRatio;
	}

	/**
	 * Use to overwrite the aspect ratio of the video file.
	 * <p>
	 * Some video codec like MPEG1/2 contain an attribute defining the video
	 * aspect ratio and this value are use by mplayer to pre-scale the video to
	 * fit this value. Some video file encoded by individual people doesn't
	 * define the right value for aspect ratio so mvPod must offer an easy way
	 * to redefine this value.
	 * </p>
	 * Standard values:
	 * <ul>
	 * <li>1.25 = 4:3</li>
	 * <li>1.75 = 16:9</li>
	 * </ul>
	 * 
	 * @param value
	 *            The aspect ratio value or 0 to keep default aspect ratio of
	 *            the video.
	 */
	public void setAspectRatio(double value) {
		if(value<=0)
			overwriteAspectRatio = 0;
		overwriteAspectRatio = value;
	}

	/**
	 * Return the argument list to define the input video to mplayer.
	 * 
	 * @return the argument list.
	 */
	public String[] toCommandList(){
		
		if (overwriteAspectRatio > 0 ) {

			String[] args = new String[2];
			args[0] = "-aspect";
			args[1] = Double.toString(overwriteAspectRatio) + ":1";
			return args;

		}

		return new String[0];
		
	}

}
