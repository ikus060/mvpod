package net.homeip.entreprisesmd.mvconv.core.profile;

/**
 * Class that implement this interface can be notify want the profile list
 * changed.
 * 
 * @author patapouf
 * 
 */
public interface ProfileListObserver {

	/**
	 * Notify the class that profile list as been changed.
	 * 
	 * @param list
	 *            the profile that changed.
	 */
	void profileListAsChanged(ProfileList list);

}
