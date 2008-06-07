package net.homeip.entreprisesmd.mvconv.gui.options.video;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.VideoEncodingOptions;

import org.eclipse.swt.widgets.Composite;

/**
 * This interface are use to map the VideoFormat, the encoding options and a video options interface.
 * 
 * @author patapouf
 * 
 */
public interface VideoOptionsMapper {

	/**
	 * Return the associated audio format.
	 * 
	 * @return the audio format.
	 */
	VideoFormat getVideoFormat();

	/**
	 * Return a default encoding options.
	 * 
	 * @return the encoding options.
	 */
	VideoEncodingOptions getEncodingOptions();

	/**
	 * Create the new video options interface.
	 * 
	 * @param parent
	 *            the parent of this interface.
	 * @param style
	 *            the style.
	 */
	VideoOptionsInterface createInterface(Composite parent, int style);

}
