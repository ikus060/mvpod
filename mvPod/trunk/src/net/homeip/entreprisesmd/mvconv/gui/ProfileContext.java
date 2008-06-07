package net.homeip.entreprisesmd.mvconv.gui;

import java.util.LinkedList;
import java.util.List;

import net.homeip.entreprisesmd.mvconv.core.profile.Profile;

/**
 * Use by Action object to know the current profile selected to execute the
 * action according to the encoding options selected.
 * 
 * @author patapouf
 * 
 */
public class ProfileContext {

	/**
	 * Selected profile.
	 */
	private Profile selection;

	/**
	 * list of listener of the action context.
	 */
	private List<IProfileContextListener> listenerList = new LinkedList<IProfileContextListener>();

	/**
	 * Use to register a new listener to this context.
	 * 
	 * @param listener
	 *            the listener.
	 */
	public void addProfileContextListener(IProfileContextListener listener) {
		listenerList.add(listener);
	}

	/**
	 * Use to retrieve the current selected profile.
	 * 
	 * @return the selection or null.
	 */
	public Profile getSelectedProfile() {
		return selection;
	}

	/**
	 * Use to remove a listener from this Context.
	 * 
	 * @param listener
	 *            the listener to remove.
	 */
	public void removeProfileContextListener(IProfileContextListener listener) {
		listenerList.remove(listener);
	}

	/**
	 * Use to change the selected profile.
	 * 
	 * @param profile
	 *            the new selected profile
	 */
	public void setSelectedProfile(Profile profile) {

		this.selection = profile;

		IProfileContextListener[] listeners = new IProfileContextListener[listenerList.size()];
		listeners = listenerList.toArray(listeners);
		for(int index=0;index<listeners.length;index++) {
			listeners[index].profileContextAsChanged(this);
		}
	}

}
