package net.homeip.entreprisesmd.mvconv.gui;

/**
 * Listener to an ActionContext object.
 * 
 * @author patapouf
 * 
 */
public interface IActionContextListener {

	/**
	 * Fired when the ActionContext changed.
	 * 
	 * @param context
	 *            the context object.
	 */
	void contextChanged(ActionContext context);
}
