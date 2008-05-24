package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.gui.Main;

import org.eclipse.jface.action.Action;

/**
 * This action are use to exit the application.
 * 
 * @author patapouf
 * 
 */
public class ExitAction extends Action {

	/**
	 * Create a new exit action.
	 */
	public ExitAction() {

		super(Localization.getString(Localization.ACTION_EXIT));
		
	}

	/**
	 * This implementation exit the application.
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {

		Main.instance().getWindowManager().close();
	
	}

}
