package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.gui.AboutDialog;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.IShellProvider;

/**
 * This action are use to diaplay the about dialog.
 * 
 * @author patapouf
 * 
 */
public class ShowAboutDialogAction extends Action {

	/**
	 * Shell provider.
	 */
	private IShellProvider shellProvider;
	
	/**
	 * Create a new ShowJobQueueWindowAction.
	 */
	public ShowAboutDialogAction(IShellProvider shellProvider) {

		super(Localization.getString(Localization.ACTION_SHOW_ABOUT_DIALOG));

		if(shellProvider==null) {
			throw new NullPointerException();
		}
		
		this.shellProvider = shellProvider;
	}

	/**
	 * <p>
	 * This implementation show the about dialog.
	 * </p>
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		
		AboutDialog dlg = new AboutDialog(shellProvider.getShell());
		dlg.open();
		
	}

}
