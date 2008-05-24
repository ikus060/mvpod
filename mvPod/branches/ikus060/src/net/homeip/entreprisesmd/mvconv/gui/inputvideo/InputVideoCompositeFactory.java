package net.homeip.entreprisesmd.mvconv.gui.inputvideo;

import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoDVD;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoFile;

import org.eclipse.swt.widgets.Composite;

/**
 * This class are use by the InputVideoListComposite to create the component for
 * each video and to create a description of this video.
 * 
 * @author patapouf
 * 
 */
public final class InputVideoCompositeFactory {

	/**
	 * Private constructor.
	 */
	private InputVideoCompositeFactory() {

	}

	/**
	 * Create a new InputVideoComposite interface appropriate for the given
	 * input/output video.
	 * 
	 * @param video
	 *            the input/output video
	 * @param site
	 *            the view site to initialize this component.
	 * @param parent
	 *            the parent of the composite interface
	 * @param style
	 *            the style of this composite interface
	 * @return a new composite interface
	 */
	public static InputVideoComposite createInputVideoComposite(Video video,
			IViewSite site, Composite parent, int style) {

		InputVideoComposite comp = null;
		if (video.getInputVideo() instanceof InputVideoFile) {
			comp = new InputVideoFileComposite(parent, style);
		} else if (video.getInputVideo() instanceof InputVideoDVD) {
			comp = new InputVideoDVDComposite(parent, style);
		}

		if (comp != null) {
			comp.init(site);
			comp.setVideo(video);
		}

		return comp;
	}
}
