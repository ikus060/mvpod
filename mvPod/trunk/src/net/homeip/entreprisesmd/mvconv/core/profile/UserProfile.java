package net.homeip.entreprisesmd.mvconv.core.profile;

import java.io.File;
import java.io.FileNotFoundException;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;

/**
 * This class represent a user profile customized for it's needs. This profile
 * are loaded and save to file.
 * 
 * @author patapouf
 * 
 */
public class UserProfile implements Profile {

	private File file;
	
	/**
	 * Create a new custom profile and attach it to the given file.
	 * 
	 * @param file
	 *            the profile file.
	 * @throws FileNotFoundException 
	 */
	public UserProfile(File file) throws FileNotFoundException {
		if(file == null)
			throw new NullPointerException();
		if(!file.exists())
			throw new FileNotFoundException();
		this.file = file;
	}

	/**
	 * Return the name of this profile.
	 * <p>
	 * This implementation load the encoding option from a file.
	 * </p>
	 * 
	 * @return the name of this profile.
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.Profile#getEncodingOptions()
	 */
	public EncodingOptions getEncodingOptions() {
		//TODO : CustomProfile : load the encoding option from file
		return null;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.profile.Profile#getName()
	 */
	public String getName() {
		return file.getName();
	}

}
