package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.gui.PreferencesDialog;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.IShellProvider;

/**
 * This action show the preferences window.
 * 
 * @author patapouf
 * 
 */
public class PreferencesAction extends Action {

	/**
	 * Shell provider.
	 */
	private IShellProvider shellProvider;
	/**
	 * View site.
	 */
	private IViewSite site;

	/**
	 * Create a new preference window.
	 */
	public PreferencesAction(IShellProvider shellProvider, IViewSite site) {

		super(Localization.getString(Localization.ACTION_PREFERENCES),
				Action.AS_PUSH_BUTTON);

		this.shellProvider = shellProvider;
		this.site = site;
	}

	/**
	 * This implementation show the preferences window.
	 */
	public void run() {
		PreferencesDialog dlg = new PreferencesDialog(shellProvider.getShell());
		dlg.init(site);
		dlg.open();
	}
}
