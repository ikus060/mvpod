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
public interface InputVideo {

	/**
	 * Return the argument list to define the input video to mplayer.
	 * 
	 * @return the argument list.
	 */
	String[] toCommandList();

	/**
	 * Return True if the <code>obj</code> are equals to this object.
	 * 
	 * @param obj
	 *            the object to compare with.
	 * @return True if the <code>obj</code> are equals to this object.
	 */
	boolean equals(InputVideo obj);

}
