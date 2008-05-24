package net.homeip.entreprisesmd.mvconv.gui;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.job.JobQueue;
import net.homeip.entreprisesmd.mvconv.core.profile.ProfileList;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.gui.actions.AddVideoFileAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.CloseWindowAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.ConvertAllVideoAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.ConvertVideoAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.DVDMenuAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.ExitAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.PreferencesAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.PreviewAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.RemoveAllVideoAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.RemoveVideoAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.ShowAboutDialogAction;
import net.homeip.entreprisesmd.mvconv.gui.actions.ShowJobQueueWindowAction;
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;
import net.homeip.entreprisesmd.mvconv.gui.inputvideo.InputVideoListComposite;
import net.homeip.entreprisesmd.mvconv.gui.options.EncodingOptionsComposite;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * This class present the main windows that are use to select the video file to
 * convert and the encoding options.
 * 
 * @author patapouf
 * 
 */
public class MainWindow extends ApplicationWindow {

	/**
	 * Minimum window width in pixel.
	 */
	private static final int MIN_WIDTH = 450;
	/**
	 * Minimum window height in pixel.
	 */
	private static final int MIN_HEIGHT = 575;
	/**
	 * Initial window width.
	 */
	private static final int INIT_WIDTH = 450;
	/**
	 * Initial window height.
	 */
	private static final int INIT_HEIGHT = 620;

	/**
	 * Component to show input video selection.
	 */
	private InputVideoListComposite inputVideoComp;

	/**
	 * Component to select encoding options.
	 */
	private EncodingOptionsComposite encodingOptionsComp;

	/**
	 * The shell provider.
	 */
	private IShellProvider shellProvider = new IShellProvider() {
		public Shell getShell() {
			return MainWindow.this.getShell();
		}
	};

	/**
	 * The action context use by every view part.
	 */
	private IViewSite site = new IViewSite() {

		public ActionContext getActionContext() {
			return actionContext;
		}

		public JobQueue getJobQueue() {
			return Main.instance().getJobQueue();
		}

		public MPlayerWrapper getMplayer() {
			return Main.instance().getMPlayer();
		}

		public ProfileContext getProfileContext() {
			return profileContext;
		}

		public ProfileList getProfileList() {
			return Main.instance().getProfileList();
		}

		public VideoList getVideoList() {
			return videoList;
		}

	};

	/**
	 * Action context for this window.
	 */
	private ActionContext actionContext = new ActionContext();

	/**
	 * Mplayer provider.
	 */
	private MPlayerProvider mplayerProvider = new MPlayerProvider() {

		public MPlayerWrapper getWrapper() {
			return Main.instance().getMPlayer();
		}

	};

	/**
	 * Profile context for this window.
	 */
	private ProfileContext profileContext = new ProfileContext();

	/**
	 * The video list.
	 */
	private VideoList videoList = new VideoList();

	/**
	 * File name provider.
	 */
	private IVideoOutputFileProvider fileProvider = new VideoOutputFileProvider(
			profileContext, videoList);

	/**
	 * AddVideoFileAction use in menu.
	 */
	private Action addVideoFileAction = new AddVideoFileAction(shellProvider,
			videoList, fileProvider, mplayerProvider);
	/**
	 * DVDMenuAction use in menu.
	 */
	private Action addDVDAction = new DVDMenuAction(shellProvider, videoList,
			fileProvider, mplayerProvider);
	/**
	 * PreviewAction use in menu.
	 */
	private Action previewAction = new PreviewAction(shellProvider,
			actionContext, profileContext, mplayerProvider);
	/**
	 * ConvertVideoAction use in menu.
	 */
	private Action convertVideoAction = new ConvertVideoAction(shellProvider,
			actionContext, profileContext, videoList, mplayerProvider, Main
					.instance().getJobQueue());
	/**
	 * convertAllVideoAction use in menu.
	 */
	private Action convertAllVideoAction = new ConvertAllVideoAction(
			shellProvider, profileContext, videoList, mplayerProvider, Main
					.instance().getJobQueue());
	/**
	 * RemoveVideoAction use in menu.
	 */
	private Action removeVideo = new RemoveVideoAction(videoList, actionContext);
	/**
	 * RemoveAllVideo use in menu.
	 */
	private Action removeAllVideoAction = new RemoveAllVideoAction(videoList);

	/**
	 * Create a new <code>MainWindow</code>.
	 * 
	 * @param parentShell
	 *            the parrent shell or null.
	 */
	public MainWindow(Shell parentShell) {
		super(parentShell);
		addToolBar(SWT.FLAT);
		addMenuBar();
	}

	/**
	 * @see org.eclipse.jface.window.ApplicationWindow#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setMinimumSize(MIN_WIDTH, MIN_HEIGHT);
		shell.setSize(INIT_WIDTH, INIT_HEIGHT);

		shell.setText(Localization.getString(Localization.APPLICATION_NAME));

		Image image16 = IconLoader.loadIcon(IconLoader.ICON_APP_16)
				.createImage();
		Image image22 = IconLoader.loadIcon(IconLoader.ICON_APP_22)
				.createImage();
		Image image32 = IconLoader.loadIcon(IconLoader.ICON_APP_32)
				.createImage();
		Image image64 = IconLoader.loadIcon(IconLoader.ICON_APP_64)
				.createImage();
		shell.setImages(new Image[] { image16, image22, image32, image64 });
	}

	/**
	 * Creates and returns this window's contents. This implementation create
	 * the video list composite and the TabFolder to select the encoding
	 * options.
	 * 
	 * @param parent
	 *            the composite parent.
	 * 
	 * @return the composite content of this window.
	 */
	protected Control createContents(Composite parent) {

		Composite comp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		/*
		 * Input video list composite
		 */
		inputVideoComp = new InputVideoListComposite(comp, SWT.NONE);
		inputVideoComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		inputVideoComp.init(site);

		// Spacer
		new Label(comp, SWT.NONE);

		/*
		 * Encoding options composite
		 */
		encodingOptionsComp = new EncodingOptionsComposite(comp, SWT.NONE);
		encodingOptionsComp.init(site);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		encodingOptionsComp.setLayoutData(data);

		return comp;

	}

	/**
	 * Returns a new menu manager for the window. This implementation create a
	 * custom Menu for this application.
	 * 
	 * @return a new menu manager for the window.
	 */
	protected MenuManager createMenuManager() {
		MenuManager mm = new MenuManager();

		Separator sparator = new Separator();

		/*
		 * File menu
		 */
		MenuManager fileMenu = new MenuManager(Localization
				.getString(Localization.MENU_FILE));
		mm.add(fileMenu);

		fileMenu.add(new CloseWindowAction(shellProvider));
		fileMenu.add(sparator);
		fileMenu.add(new ExitAction());

		/*
		 * Videos menu
		 */
		MenuManager videoMenu = new MenuManager(Localization
				.getString(Localization.MENU_VIDEOS));
		mm.add(videoMenu);

		videoMenu.add(addVideoFileAction);
		videoMenu.add(addDVDAction);
		videoMenu.add(sparator);
		videoMenu.add(removeVideo);
		videoMenu.add(removeAllVideoAction);
		videoMenu.add(sparator);
		videoMenu.add(previewAction);
		videoMenu.add(convertVideoAction);
		videoMenu.add(convertAllVideoAction);

		// TODO : Usability : Add a list of current video.

		// TODO : Usability : Add a menu for vidéo output options ?? In this
		// menu we can add the list of profile.

		/*
		 * Window menu
		 */
		MenuManager windowMenu = new MenuManager(Localization
				.getString(Localization.MENU_WINDOW));
		mm.add(windowMenu);

		windowMenu.add(new ShowJobQueueWindowAction());
		windowMenu.add(new PreferencesAction(shellProvider));

		/*
		 * Help menu
		 */
		MenuManager helpMenu = new MenuManager(Localization
				.getString(Localization.MENU_HELP));
		mm.add(helpMenu);

		// TODO : Provide a documentation. DocBook seem a good idea
		// http://en.wikipedia.org/wiki/DocBook

		helpMenu.add(new ShowAboutDialogAction(shellProvider));

		return mm;
	}

	/**
	 * Returns a new tool bar manager for the window.
	 * <p>
	 * This implementation create a custom toolbar for this application.
	 * </p>
	 * 
	 * @param style
	 *            swt style bits used to create the Toolbar.
	 * @return a new tool bar manager for the window.
	 */
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager tm = new ToolBarManager(style);

		tm.add(addVideoFileAction);
		tm.add(addDVDAction);
		tm.add(new Separator());
		tm.add(previewAction);
		tm.add(convertAllVideoAction);

		return tm;
	}

	/**
	 * 
	 * 
	 * @see org.eclipse.jface.window.Window#handleShellCloseEvent()
	 */
	protected void handleShellCloseEvent() {

		//TODO : Find a more fancy way to do this confirmation.
		boolean quit = Main.instance().confirmQuit(getShell());

		if (quit) {
			super.handleShellCloseEvent();
		}

	}

}
