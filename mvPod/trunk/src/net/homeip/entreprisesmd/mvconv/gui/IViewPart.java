package net.homeip.entreprisesmd.mvconv.gui;

/**
 * Class class that implement this interface are part of the user interface or
 * create a user interface.
 * 
 * @author patapouf
 * 
 */
public interface IViewPart {

	/**
	 * Return the view site.
	 * 
	 * @return the view site.
	 */
	IViewSite getViewSite();

	/**
	 * Initialize this view with the given view site.
	 * 
	 * @param site
	 *            the view site.
	 */
	void init(IViewSite site);

}
