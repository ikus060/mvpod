package net.homeip.entreprisesmd.mvconv.core.profile;

import java.util.LinkedList;
import java.util.List;

/**
 * This class maintain a list of all available profile.
 * <p>
 * The list contain every hard coded profile implemented in mvPod and look for
 * custom profile in the application directory and the user home directory.
 * </p>
 * 
 * @author patapouf
 * 
 */
public class ProfileList {

	/**
	 * List of profile.
	 */
	private List<Profile> profiles = new LinkedList<Profile>();
	/**
	 * Observer list
	 */

	ProfileListObserver[] observerList = new ProfileListObserver[0];

	/**
	 * Create a new profile list
	 */
	public ProfileList() {

		// Add hard coded profile
		addProfile(new IPodH264Profile());
		addProfile(new IPodXVidProfile());

		// Retrieve any custom profile
		Profile[] customProfiles = Profiles.getUserProfiles();
		if (customProfiles != null) {
			for (int index = 0; index < customProfiles.length; index++) {
				addProfile(customProfiles[index]);
			}
		}

	}

	/**
	 * Use to add a new profile to the list of profile.
	 * 
	 * @param profile
	 *            the profile.
	 */
	public void addProfile(Profile profile) {
		profiles.add(profile);
		notifyObservers();
	}

	/**
	 * Add the given observer to the collections of observers that will be
	 * notify when the profile list change.
	 * 
	 * @param observer
	 *            the observer to add.
	 */

	public void addProfileListObserver(ProfileListObserver observer) {

		ProfileListObserver[] newObserverList = new ProfileListObserver[observerList.length + 1];
		System.arraycopy(observerList, 0, newObserverList, 0,
				observerList.length);
		newObserverList[observerList.length] = observer;

		observerList = newObserverList;
	}

	/**
	 * Return the profile at the given index.
	 * 
	 * @return the profile.
	 */
	public Profile getProfile(int index) {
		return profiles.get(index);
	}

	/**
	 * Return the number of profile in the list.
	 * 
	 * @return the number of profile.
	 */
	public int getProfileCount() {
		return profiles.size();
	}

	/**
	 * Return the list of profile contains in the profile list.
	 * 
	 * @return the list of profile.
	 */
	public Profile[] getProfiles() {
		Profile[] returnedList = new Profile[profiles.size()];
		return profiles.toArray(returnedList);
	}

	/**
	 * Use to notify observer that this object has changed.
	 */
	private void notifyObservers() {
		for (int index = 0; index < observerList.length; index++) {
			observerList[index].profileListAsChanged(this);
		}
	}

	/**
	 * Remove the given profile from the collections of profiles.
	 * 
	 * @param profile
	 *            the profile to remove.
	 */
	public void removeProfile(Profile profile) {
		profiles.remove(profile);
		notifyObservers();
	}

	/**
	 * Remove the given observer from the collections of observers.
	 * 
	 * @param observer
	 *            the observer to add.
	 */
	public void removeProfileListObserver(ProfileListObserver observer) {

		int index = 0;
		while (index < observerList.length && observerList[index] != observer) {
			index++;
		}
		if (index >= observerList.length)
			return;

		ProfileListObserver[] newObserverList = new ProfileListObserver[observerList.length - 1];
		System.arraycopy(observerList, 0, newObserverList, 0, index);
		System.arraycopy(observerList, index + 1, newObserverList, index,
				observerList.length - index-1);
		observerList = newObserverList;
	}

}
