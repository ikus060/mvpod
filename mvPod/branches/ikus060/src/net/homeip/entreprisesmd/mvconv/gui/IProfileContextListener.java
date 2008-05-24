package net.homeip.entreprisesmd.mvconv.gui;

/**
 * Listener to an ProfileContext object.
 * 
 * @author patapouf
 * 
 */
public interface IProfileContextListener {

	/**
	 * Fired when the profile as changed.
	 * 
	 * @param context
	 *            the context object.
	 */
	void profileContextAsChanged(ProfileContext context);
}
