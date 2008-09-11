package net.homeip.entreprisesmd.mvconv.gui.options.audio;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.AudioEncodingOptions;

import org.eclipse.swt.widgets.Composite;

/**
 * This interface are use to map the AudioFormat, the encoding options and an
 * audio options interface.
 * 
 * @author patapouf
 * 
 */
public interface AudioOptionsMapper {

	/**
	 * Create the new audio options interface.
	 * 
	 * @param parent
	 *            the parent of this interface.
	 * @param style
	 *            the style.
	 */
	AudioOptionsInterface createInterface(Composite parent, int style);

	/**
	 * Return the associated audio format.
	 * 
	 * @return the audio format.
	 */
	AudioFormat getAudioFormat();

	/**
	 * Return a default encoding options.
	 * 
	 * @return the encoding options.
	 */
	AudioEncodingOptions getEncodingOptions();
	
	/**
	 * Return True if the given audio encoding options match with this Mapper.
	 * 
	 * @param options
	 *            the encoding options to verify.
	 * @return True if the given audio encoding options match with this Mapper.
	 */
	boolean match(AudioEncodingOptions options);

	/**
	 * Redefine the default encoding options to remember the last configuration
	 * of this Mapper.
	 * 
	 * @param options
	 *            the new audio encoding options.
	 */
	void setDefaultEncodingOptions(AudioEncodingOptions options);

}
