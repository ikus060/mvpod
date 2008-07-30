/*
 * MPlayerNotFoundException.java
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
 * $Id: MPlayerNotFoundException.java 117 2006-12-13 22:59:03Z jlee $
 */
package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * Thrown by MPlayerWrapper if mplayer application file are not found.
 * 
 * @author patapouf
 * 
 */
public class MPlayerNotFoundException extends MPlayerException {

	/**
	 * Unique identifier
	 */
	private static final long serialVersionUID = 4938881482875864297L;

	private String componentName;
	
	/**
	 * Create a new mplayer not found exception.
	 */
	public MPlayerNotFoundException(String componentName) {
		super(componentName + " not found");
		this.componentName = componentName;
	}
	
	/**
	 * Return the missing component name.
	 * @return the missing component name.
	 */
	public String getComponentName(){
		return componentName;
	}

}
