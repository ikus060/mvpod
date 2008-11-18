package net.homeip.entreprisesmd.mvconv.gui.inputvideo;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog are use to display generic option composite interface.
 * 
 * @author patapouf
 * 
 */
public class InputVideoGenericOptionsDialog extends
		org.eclipse.jface.dialogs.Dialog {

	InputVideoGenericOptionsComposite composite;

	/**
	 * The view site.
	 */
	private IViewSite site;

	/**
	 * The input video object.
	 */
	private Video video;

	protected InputVideoGenericOptionsDialog(Shell parentShell) {
		super(parentShell);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);

		shell.setText(Localization
				.getString(Localization.INPUTOUTPUT_MORE_OPTIONS_DIALOG_TITLE));

	}

	/**
	 * Adds buttons to this dialog's button bar.
	 * <p>
	 * The <code>Dialog</code> implementation of this framework method adds
	 * standard ok and cancel buttons using the <code>createButton</code>
	 * framework method. These standard buttons will be accessible from
	 * <code>getCancelButton</code>, and <code>getOKButton</code>.
	 * Subclasses may override.
	 * </p>
	 * 
	 * @param parent
	 *            the button bar composite
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.CLOSE_LABEL, false);
	}
	
	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {

		composite = new InputVideoGenericOptionsComposite(parent, SWT.NONE);
		if (video != null) {
			composite.setVideo(video);
		}
		if (site != null) {
			composite.init(site);
		}

		return composite;

	}

	/**
	 * Return the associated input video object.
	 * 
	 * @return the input video object.
	 */
	public Video getVideo() {

		if (composite != null) {
			return composite.getVideo();
		}
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
	 * Initialize this view with the given view site. Sub-class that overload
	 * this method must call super.init().
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {
		this.site = site;
		if (composite != null) {
			composite.init(site);
		}
	}

	/**
	 * Set the video.
	 * 
	 * @param video
	 *            the video.
	 */
	public void setVideo(Video video) {

		if (composite != null) {
			composite.setVideo(video);
		}

		this.video = video;
	}

}
