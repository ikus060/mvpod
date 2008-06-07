/*
 * MencoderCommand.java
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
 * $Id: MencoderCommand.java 161 2007-04-14 19:52:46Z jlee $
 */
package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a list of arguments that will be pass to mplayer.
 */
public class EncodingCommand {

	/**
	 * Arguments list.
	 */
	private List<String> arguments = new ArrayList<String>();

	/**
	 * Create a new arguments EncodingCommand with specific arguments list.
	 * 
	 * @param args
	 *            the arguments list
	 */
	public EncodingCommand(String[] args) {
		for (int i = 0; i < args.length; i++) {
			this.arguments.add(args[i]);
		}
	}

	/**
	 * Create a new arguments EncodingCommand with an input video.
	 * 
	 * @param inputVideo
	 *            the input video
	 */
	public EncodingCommand(InputVideo inputVideo) {
		this(inputVideo, null, null);
	}

	/**
	 * Create a new arguments EncodingCommand with specific arguments list.
	 * 
	 * @param inputVideo
	 *            the input video
	 * @param outputVideo
	 *            the ouput video
	 */
	public EncodingCommand(InputVideo inputVideo, File outputVideo) {
		this(inputVideo, outputVideo, null);
	}

	/**
	 * Create a new arguments EncodingCommand with specific arguments list. This
	 * constructor is intend to create a command with an input and an output
	 * 
	 * @param inputVideo
	 *            the input video
	 * @param outputVideo
	 *            the ouput video
	 * @param args
	 *            the arguments list
	 */
	public EncodingCommand(InputVideo inputVideo, File outputVideo,
			String[] args) {

		// Add input video information
		if (inputVideo != null) {
			String[] inputVideoArgs = inputVideo.toCommandList();
			for (int i = 0; i < inputVideoArgs.length; i++) {
				this.arguments.add(inputVideoArgs[i]);
			}
		}

		// Add ouput video information
		if (outputVideo != null) {
			this.arguments.add("-o");
			try {
				this.arguments.add(outputVideo.getCanonicalPath());
			} catch (IOException e) {
				this.arguments.add(outputVideo.getAbsolutePath());
			}
		}

		// Add encoding options
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				this.arguments.add(args[i]);
			}
		}

	}

	/**
	 * Use to add more arguement to the command.
	 * 
	 * @param args
	 *            the argument list
	 */
	public void add(String[] args) {

		for (int argIndex = 0; argIndex < args.length; argIndex++) {
			arguments.add(args[argIndex]);
		}

	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String string = "";

		for (int i = 0; i < arguments.size(); i++) {
			string += (String) arguments.get(i) + " ";
		}

		return string;
	}

	/**
	 * Return an array describing every argument composing the command.
	 * 
	 * @return an array with argument
	 */
	public String[] toStringArray() {
		return (String[]) arguments.toArray(new String[] {});
	}

}
