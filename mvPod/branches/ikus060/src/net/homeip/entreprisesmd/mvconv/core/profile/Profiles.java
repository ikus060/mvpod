package net.homeip.entreprisesmd.mvconv.core.profile;

import java.io.File;
import java.io.FileNotFoundException;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.LameEncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.HarddupFilter;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.InverseTelecineFilter;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.XVideoEncodingOptions;

/**
 * This class offer generic manipulation that can be use on profile.
 * 
 * @author patapouf
 * 
 */
public class Profiles {
	/**
	 * Relative path to the application directory of default profile.
	 */
	//TODO : CustomProfile : Use this constant value
	//private static final String APP_PROFILE_DIRECTORY = "profiles";
	/**
	 * Key value for user home directory.
	 */
	private static final String USER_HOME = "user.home"; //$NON-NLS-1$
	/**
	 * Relative path to the user directory of user profile.
	 */
	// TODO : Cross Platform : This value must be change under windows
	private static final String USER_HOME_PROFILE_DIRECTORY = ".mvPod/profiles"; //$NON-NLS-1$

	/**
	 * Create a new profile base on the encoding options.
	 * @param options the options.
	 * @return the profile.
	 */
	public static Profile createOnFlyProfile(final EncodingOptions options) {
		return new Profile() {

			public EncodingOptions getEncodingOptions() {
				return options;
			}

			public String getName() {
				return Localization.getString(Localization.PROFILE_TEMP);
			}
		};
	}
	
	/**
	 * Return a custom profile.
	 * @return
	 */
	public static Profile createCustomProfile(){
		return new Profile() {

			public EncodingOptions getEncodingOptions() {
				LameEncodingOptions audioOptions = new LameEncodingOptions(128,
						LameEncodingOptions.TYPE_CONSTANT_BITRATE);
				audioOptions.setChannelMode(LameEncodingOptions.MODE_JOIN_STEREO);
				audioOptions.setOutputSampleRate(44100);

				XVideoEncodingOptions videoOptions = new XVideoEncodingOptions(500);
				videoOptions.setMaxBFrame(0);
				videoOptions.setOutputFrameRate(10);

				EncodingOptions options = new EncodingOptions(videoOptions,
						audioOptions);
				options.addVideoFilter(new InverseTelecineFilter(10));
				options.addVideoFilter(new HarddupFilter());

				options.setScaleOptions(new VideoScalingOptions(220, 176));
				return options;
			}

			public String getName() {
				return Localization.getString(Localization.PROFILE_CUSTOM);
			}
		};
	}
	
	/**
	 * Return a list of user profile loaded from a file.
	 * <p>
	 * The method look in the user home directory for any compatible profile and
	 * check in the application directory.
	 * </p>
	 * 
	 * @return
	 */
	public static Profile[] getUserProfiles() {

		String home = System.getProperty(USER_HOME);
		File profileDirectory = new File(home, USER_HOME_PROFILE_DIRECTORY);
		Profile[] userProfiles = new Profile[0];
		if (profileDirectory.exists()) {
			try {
				userProfiles = getUserProfilesFromDirectory(profileDirectory);
			} catch (FileNotFoundException e) {
				// Not supposed to happen
				e.printStackTrace();
			}
		}

		return userProfiles;

		// TODO : CustomProfile : Implement the listing of profile from application directory

		// String home = System.getProperty(USER_HOME);
		// File profileDirectory = new File(home, USER_HOME_PROFILE_DIRECTORY);
		// Profile[] userProfiles = null;
		// if(profileDirectory.exists()){
		// try {
		// userProfiles = getCustomProfilesFromDirectory(profileDirectory);
		// } catch (FileNotFoundException e) {
		// // Not supposed to happen
		// e.printStackTrace();
		// }
		// }

	}

	/**
	 * Return a list of user profile loaded from file present in the given
	 * directory.
	 * 
	 * @param directory
	 *            the directory that contain the profile's files.
	 * @return the user profile list.
	 * @throws FileNotFoundException
	 *             if the directory doesn't exist.
	 */
	public static Profile[] getUserProfilesFromDirectory(File directory)
			throws FileNotFoundException {

		if (!directory.exists() || !directory.isDirectory()) {
			throw new FileNotFoundException();
		}

		File[] files = directory.listFiles(new ProfileFilenameFilter());
		Profile[] profiles = new Profile[files.length];
		for (int index = 0; index < files.length; index++) {
			profiles[index] = new UserProfile(files[index]);
		}

		return profiles;
	}

	/**
	 * Private constructor.
	 */
	private Profiles() {
		// Private constructor to prevent creation of utility class
	}
}
