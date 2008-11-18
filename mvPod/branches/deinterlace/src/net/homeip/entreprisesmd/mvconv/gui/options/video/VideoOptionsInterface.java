package net.homeip.entreprisesmd.mvconv.gui.options.video;

import net.homeip.entreprisesmd.mvconv.gui.IViewSite;

import org.eclipse.swt.widgets.Composite;

/**
 * This class are use to display video options to user. This class are intended
 * to be sub-class.
 * 
 * @author patapouf
 * 
 */
public abstract class VideoOptionsInterface extends Composite {

	/**
	 * Create a new interface.
	 * 
	 * @param parent
	 *            the parent of this interface
	 * @param style
	 *            the style.
	 */
	public VideoOptionsInterface(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * View site.
	 */
	private IViewSite viewSite;

	/**
	 * Return the view site.
	 * 
	 * @return the view site.
	 */
	public IViewSite getViewSite() {
		return this.viewSite;
	}

	/**
	 * Initialize this view with the given view site.
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {

		this.viewSite = site;

	}

}
