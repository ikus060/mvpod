package net.homeip.entreprisesmd.mvconv.gui.inputvideo;

import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.gui.IViewPart;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;

import org.eclipse.swt.widgets.Composite;

/**
 * This Composite interface are use to change option of a input video.
 * 
 * @author patapouf
 * 
 */
public abstract class InputVideoComposite extends Composite implements IViewPart {

	/**
	 * The input video object.
	 */
	private Video video;

	/**
	 * The view site.
	 */
	private IViewSite site;

	/**
	 * Create a new InputVideoComposite.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the style of this composite interface.
	 */
	public InputVideoComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Return the associated input video object.
	 * 
	 * @return the input video object.
	 */
	public Video getVideo() {
		return video;
	}

	/**
	 * Return the view site.
	 * 
	 * @return the view site.
	 */
	public IViewSite getViewSite() {
		return site;
	}

	/**
	 * Set the video.
	 * 
	 * @param video
	 *            the video.
	 */
	public void setVideo(Video video) {
		this.video = video;
	}

	/**
	 * Initialize this view with the given view site. Sub-class that overload
	 * this method must call super.init().
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {
		this.site = site;
	}

}
