package net.homeip.entreprisesmd.mvconv.core.profile;

import java.util.LinkedList;
import java.util.List;


/**
 * This class maintain a list of all available profile.
 * <p>
 * The list contain every hard coded profile implemented in mvPod and look
 * for custom profile in the application directory and the user home directory.
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
	private List<ProfileListObserver> observerList = new LinkedList<ProfileListObserver>();

	/**
	 * Create a new profile list
	 */
	public ProfileList() {

		// Add hard codec profile
		addProfile(new IPodH264Profile());
		addProfile(new IPodXVidProfile());
		
		//TODO : Add a new profile to support iPod MPEG
		
		// Retreive any custom profile
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
		observerList.add(observer);
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
		ProfileListObserver[] observers = new ProfileListObserver[observerList.size()];
		observers = observerList.toArray(observers);
		for(int index=0;index<observers.length;index++) {
			observers[index].profileListAsChanged(this);
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
	public void remoceProfileListObserver(ProfileListObserver observer) {
		observerList.remove(observer);
	}

}
