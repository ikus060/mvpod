package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.gui.Main;
import net.homeip.entreprisesmd.mvconv.gui.jobqueue.QueueWindow;

import org.eclipse.jface.action.Action;

/**
 * CThis actino are use to show the job queue window.
 * 
 * @author patapouf
 * 
 */
public class ShowJobQueueWindowAction extends Action {

	/**
	 * Create a new ShowJobQueueWindowAction.
	 */
	public ShowJobQueueWindowAction() {

		super(Localization.getString(Localization.ACTION_SHOW_JOB_QUEUE));

	}

	/**
	 * <p>
	 * This implementation show the job queue window.
	 * </p>
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {

		QueueWindow win = Main.instance().getQueueWindow();
		if (win != null) {
			Main.instance().getWindowManager().add(win);
			win.open();
		}

	}

}
