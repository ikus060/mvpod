package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.gui.IVideoOutputFileProvider;
import net.homeip.entreprisesmd.mvconv.gui.MPlayerProvider;
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Control;

import org.eclipse.swt.widgets.Menu;

/**
 * This Action are use to add a new input video as a DVD (from a device, from an
 * iso file, from a video directory).
 * 
 * @author patapouf
 * 
 */
public class DVDMenuAction extends Action {
	/**
	 * AddDVDFromDevice action.
	 */
	Action addDVDFromDevice;
	/**
	 * AddDVDFromDirectory action.
	 */
	Action addDVDFromDirectory;
	/**
	 * AddDVDFromIso action.
	 */
	Action addDVDFromIso;
	
	/**
	 * Menu creator.
	 */
	IMenuCreator menuCreator = new IMenuCreator() {

		Menu controlMenu;
		Menu subMenu;

		public void dispose() {
			if (this.controlMenu != null) {
				this.controlMenu.dispose();
			}
			this.controlMenu = null;

			if (this.subMenu != null) {
				this.subMenu.dispose();
			}
			this.subMenu = null;
		}

		public Menu getMenu(Control parent) {
			if (this.controlMenu != null) {
				return this.controlMenu;
			}

			Menu menu = new Menu(parent);
			addMenuItem(menu, DVDMenuAction.this.addDVDFromDevice);
			addMenuItem(menu, DVDMenuAction.this.addDVDFromDirectory);
			addMenuItem(menu, DVDMenuAction.this.addDVDFromIso);

			this.controlMenu = menu;
			return menu;
		}

		public Menu getMenu(Menu parent) {
			if (this.subMenu != null) {
				return this.subMenu;
			}

			Menu menu = new Menu(parent);
			addMenuItem(menu, DVDMenuAction.this.addDVDFromDevice);
			addMenuItem(menu, DVDMenuAction.this.addDVDFromDirectory);
			addMenuItem(menu, DVDMenuAction.this.addDVDFromIso);

			this.subMenu = menu;
			return menu;
		}

		private void addMenuItem(Menu parent, IAction action) {
			(new ActionContributionItem(action)).fill(parent, -1);
		}

	};

	/**
	 * Create a new <code>AddVideoFileAction</code>.
	 * 
	 * @param shellProvider
	 *            a shell provider to obtain a shell.
	 * @param videoList
	 *            a list of video where to add the input video.
	 * @param fileProvider
	 *            a file name provider that will be use to generate the output
	 *            file name of the video.
	 */
	public DVDMenuAction(IShellProvider shellProvider,
			VideoList videoList, IVideoOutputFileProvider fileProvider, MPlayerProvider mplayerProvider) {
		
		super(Localization.getString(Localization.ACTION_ADD_DVD),
				IAction.AS_DROP_DOWN_MENU);
		
		setImageDescriptor(IconLoader.loadIcon(IconLoader.ICON_DVD_22));

		this.addDVDFromDevice = new AddDVDFromDeviceAction(shellProvider, videoList, fileProvider, mplayerProvider);
		this.addDVDFromDirectory = new AddDVDFromDirectoryAction(shellProvider, videoList, fileProvider, mplayerProvider);
		this.addDVDFromIso = new AddDVDFromIsoAction(shellProvider, videoList, fileProvider, mplayerProvider);

		this.setMenuCreator(this.menuCreator);
	}

	/**
	 * This implementation of this methode add a new video to the video list.
	 */
	public void run() {
		this.addDVDFromDevice.run();
	}

}
