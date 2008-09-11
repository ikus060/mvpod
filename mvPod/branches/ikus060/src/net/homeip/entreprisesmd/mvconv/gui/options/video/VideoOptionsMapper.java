package net.homeip.entreprisesmd.mvconv.gui.options.video;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.VideoEncodingOptions;

import org.eclipse.swt.widgets.Composite;

/**
 * This interface are use to map the VideoFormat, the encoding options and a
 * video options interface.
 * 
 * @author patapouf
 * 
 */
public interface VideoOptionsMapper {

	/**
	 * Create the new video options interface.
	 * 
	 * @param parent
	 *            the parent of this interface.
	 * @param style
	 *            the style.
	 */
	VideoOptionsInterface createInterface(Composite parent, int style);

	/**
	 * Return a default encoding options.
	 * 
	 * @return the encoding options.
	 */
	VideoEncodingOptions getEncodingOptions();

	/**
	 * Return the associated audio format.
	 * 
	 * @return the audio format.
	 */
	VideoFormat getVideoFormat();

	/**
	 * Return True if the given video encoding options match with this Mapper.
	 * 
	 * @param options
	 *            the encoding options to verify.
	 * @return True if the given video encoding options match with this Mapper.
	 */
	boolean match(VideoEncodingOptions options);

	/**
	 * Redefine the default encoding options to remember the last configuration
	 * of this Mapper.
	 * 
	 * @param options
	 *            the new video encoding options.
	 */
	void setDefaultEncodingOptions(VideoEncodingOptions options);

}
