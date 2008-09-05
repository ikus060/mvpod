package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.IShellProvider;

/**
 * This action are use to close the main window.
 * 
 * @author patapouf
 * 
 */
public class CloseWindowAction extends Action {

	/**
	 * Shell to close.
	 */
	private IShellProvider shellProvider;

	/**
	 * Create a new close window action.
	 * 
	 * @param window
	 *            the window to close.
	 */
	public CloseWindowAction(IShellProvider shellProvider) {
		
		super(Localization.getString(Localization.ACTION_CLOSE));
		
		this.shellProvider = shellProvider;
	}

	/**
	 * This implementation cclose the the shell.
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		this.shellProvider.getShell().close();
	}
}
