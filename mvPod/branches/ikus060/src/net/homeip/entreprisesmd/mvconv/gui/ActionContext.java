package net.homeip.entreprisesmd.mvconv.gui;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;

/**
 * Use by Action object to know the current context to execute the action. with
 * the containing information Action object can determine the enabled state and
 * execute the action on the write object.
 * 
 * @author patapouf
 * 
 */
public class ActionContext {
	/**
	 * Define the subject of selection.
	 */
	private Object subject;

	/**
	 * Define the selection in the subject.
	 */
	private ISelection selection;

	/**
	 * list of listener of the action context.
	 */
	private List<IActionContextListener> listenerList = new LinkedList<IActionContextListener>();

	/**
	 * Use to register a new listener to this context.
	 * 
	 * @param listener
	 *            the listener.
	 */
	public void addActionContextListener(IActionContextListener listener) {
		listenerList.add(listener);
	}

	/**
	 * Use to retrieve the current selection of the subject.
	 * 
	 * @return the selection of null.
	 */
	public ISelection getSelection() {
		return selection;
	}

	/**
	 * Use to retrive the current subject.
	 * 
	 * @return the subject.
	 */
	public Object getSubject() {
		return subject;
	}

	/**
	 * Use to remove a listener from this Context.
	 * 
	 * @param listener
	 *            the listener to remove.
	 */
	public void removeActionContextListener(IActionContextListener listener) {
		listenerList.remove(listener);
	}

	/**
	 * Use to change the action context.
	 * 
	 * @param subject
	 *            Define the new subject (can be null if there is no subject).
	 * @param selection
	 *            Define the new selection (can be null for most subject type).
	 */
	public void setSubject(Object subject, ISelection selection) {
		this.subject = subject;
		this.selection = selection;

		IActionContextListener[] listeners = new IActionContextListener[listenerList.size()];
		listeners = listenerList.toArray(listeners);
		for(int index=0;index<listeners.length;index++) {
			listeners[index].contextChanged(this);
		}
	}

}
