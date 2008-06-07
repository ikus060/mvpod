package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.gui.ActionContext;
import net.homeip.entreprisesmd.mvconv.gui.IActionContextListener;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * This Action are use to remove the selected video from the video list.
 * 
 * @author patapouf
 * 
 */
public class RemoveVideoAction extends Action {

	/**
	 * The video list.
	 */
	private VideoList videoList;

	/**
	 * The action context.
	 */
	private ActionContext actionContext;

	/**
	 * Listener to the action context.
	 */
	private IActionContextListener listener = new IActionContextListener() {
		public void contextChanged(ActionContext context) {
			RemoveVideoAction.this.contextChanged(context);
		}
	};

	/**
	 * Create a new remove all video action.
	 * 
	 * @param list
	 *            the video list.
	 * @param context
	 *            the action context.
	 */
	public RemoveVideoAction(VideoList list, ActionContext context) {
		super(Localization.getString(Localization.ACTION_REMOVE));
		this.videoList = list;
		this.actionContext = context;

		context.addActionContextListener(listener);

		contextChanged(context);
	}

	/**
	 * Use to determine if this action are enabled or not depending of the
	 * selection.
	 * 
	 * @param context
	 *            the Action context.
	 */
	private void contextChanged(ActionContext context) {
		boolean enabled = false;
		if (context.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) context
					.getSelection();
			Object element = selection.getFirstElement();
			if (element instanceof Video) {
				enabled = true;
			}
		}
		this.setEnabled(enabled);
	}

	/**
	 * This implementation remove the selected video from the video list.
	 */
	public void run() {

		Video video = null;
		if (actionContext.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) actionContext
					.getSelection();
			Object element = selection.getFirstElement();
			if (element instanceof Video) {
				video = (Video) element;
			}
		}

		if (video == null) {
			return;
		}

		videoList.removeVideo(video);

	}
}
