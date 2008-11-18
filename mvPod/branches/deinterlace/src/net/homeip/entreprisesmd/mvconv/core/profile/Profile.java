package net.homeip.entreprisesmd.mvconv.core.profile;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;

/**
 * This interface represent an encoding profile use in user interface to limit
 * or define default value for all encoding options. This way user don't have to
 * select manually every options.
 * 
 * @author patapouf
 * 
 */
public interface Profile {

	/**
	 * Return the default encoding options for this profile.
	 * <p>
	 * Class that implement this method must provide the encoding options with
	 * default value.
	 * </p>
	 * 
	 * @return the encoding options.
	 */
	EncodingOptions getEncodingOptions();

	/**
	 * Return the name of this profile.
	 * <p>
	 * This value are not intended to be unique value.
	 * </p>
	 * 
	 * @return the name of this profile.
	 */
	String getName();

}
