package net.homeip.entreprisesmd.mvconv.gui.jobqueue;

import org.eclipse.swt.events.SelectionEvent;

/**
 * This interface provide method to be notify when the user select an action in
 * the QueueItem interface.
 * 
 * @author patapouf
 * 
 */
public interface QueueItemListener {

	/**
	 * Sent user select action.
	 * 
	 * @param action
	 *            the action number.
	 * @param event
	 *            an event containing information about the selection.
	 */
	void actionSelected(int action, SelectionEvent event);

}
