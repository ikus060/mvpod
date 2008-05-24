package net.homeip.entreprisesmd.mvconv.gui;

import java.io.File;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;

/**
 * This interface provide a way to obtain an output file.
 * 
 * @author patapouf
 * 
 */
public interface IVideoOutputFileProvider {

	/**
	 * Return an output file with the inputvideo.
	 * 
	 * @param inputVideo
	 *            the inputvideo
	 * @return the output file
	 */
	File getFile(InputVideo inputVideo);

}
