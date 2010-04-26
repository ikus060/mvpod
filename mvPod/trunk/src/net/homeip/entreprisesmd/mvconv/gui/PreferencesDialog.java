package net.homeip.entreprisesmd.mvconv.gui;

import java.io.IOException;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoOutputDevice;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.config.Configuration;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.MP4BoxMuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.MP4CreatorMuxer;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * This dialog are use by user to change some preference value.
 * 
 * @author patapouf
 * 
 */
public class PreferencesDialog extends Dialog {
	/**
	 * Selection listener.
	 */
	Listener listener = new Listener() {
		public void handleEvent(Event event) {
			switch (event.type) {
			case SWT.Selection:
				if (event.widget == PreferencesDialog.this.replaceFileButton) {
					replaceSelectionAsChanged();
				} else if (event.widget == PreferencesDialog.this.mp4boxButton) {
					handleMP4BoxBrowse();
				} else if (event.widget == PreferencesDialog.this.mp4creatorButton) {
					handleMp4creatorBrowse();
				} else if (event.widget == PreferencesDialog.this.mplayerButton) {
					handleMPlayerBrowse();
				}
				break;
			}
		}
	};

	/**
	 * MP4Box button.
	 */
	Button mp4boxButton;

	/**
	 * MP4Box directory text component.
	 */
	Text mp4boxDirectoryText;
	/**
	 * mp4converter button.
	 */
	Button mp4creatorButton;
	/**
	 * mp4creator directory text component.
	 */
	Text mp4creatorDirectoryText;
	/**
	 * MPlayer button.
	 */
	Button mplayerButton;

	/**
	 * Mplayer directory text component.
	 */
	Text mplayerDirectoryText;
	LabelProvider muxerLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			if (element.equals(MP4BoxMuxer.class)) {
				return MP4BoxMuxer.MP4BOX_BIN;
			} else if (element.equals(MP4CreatorMuxer.class)) {
				return MP4CreatorMuxer.MP4CREATOR_BIN;
			}
			return element.toString();
		}
	};
	/**
	 * Combo to select the default muxer.
	 */
	ComboViewer muxerViewer;

	/**
	 * Property listener.
	 */
	IPropertyChangeListener propertiesListener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			PreferencesDialog.this.propertyChange();
		}
	};

	/**
	 * Replace button.
	 */
	Button replaceFileButton;
	/**
	 * Selection change listener
	 */
	ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == PreferencesDialog.this.videoDevicesViewer) {
				videoOutputDeviceSelectionAsChanged(event);
			} else if (event.getSource() == PreferencesDialog.this.muxerViewer) {
				muxerSelectionAsChanged(event);
			}
		}
	};
	/**
	 * The view site.
	 */
	IViewSite site;

	/**
	 * User configuration.
	 */
	Configuration userConfig;

	LabelProvider videoDevicesLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			if (element instanceof VideoOutputDevice) {
				return ((VideoOutputDevice) element).getName();
			}
			return element.toString();
		}
	};

	/**
	 * Combo viewer
	 */
	ComboViewer videoDevicesViewer;

	/**
	 * Create a new preference window.
	 * 
	 * @param parentShell
	 *            the parent of this window.
	 */
	public PreferencesDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {

		super.configureShell(shell);

		String title = Localization.getString(Localization.PREFERENCE_TITLE);
		shell.setText(title);
		shell.setMinimumSize(450, 150);

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
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.CLOSE_LABEL, true);
	}

	/**
	 * This implementation create the content of the preference window.
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		// Get localized text
		String browseText = Localization
				.getString(Localization.PREFERENCE_BROWSE);
		String directoryGroupText = Localization
				.getString(Localization.PREFERENCE_DIRECTORY_GROUP);
		String optionGroupText = Localization
				.getString(Localization.PREFERENCE_GENERAL_OPTION_GROUP);
		String mplayerGroupText = Localization
				.getString(Localization.PREFERENCE_MPLAYER_GROUP);
		String mp4boxText = Localization
				.getString(Localization.PREFERENCE_MP4BOX_EMPLACEMENT);

		String mp4creatorText = Localization
				.getString(Localization.PREFERENCE_MP4CONVERTER_LOCATION);
		String defaultMp4MuxerText = Localization
				.getString(Localization.OPTIONS_MP4_MUXER);

		/*
		 * General options
		 */
		Group generalGroup = new Group(comp, SWT.NONE);
		generalGroup.setLayout(new GridLayout(1, false));
		generalGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		generalGroup.setText(optionGroupText);

		// "Alway replace existing file" check button
		String replaceText = Localization
				.getString(Localization.PREFERENCE_REPLACE);
		replaceFileButton = new Button(generalGroup, SWT.CHECK);
		replaceFileButton.setText(replaceText);
		replaceFileButton.addListener(SWT.Selection, listener);

		/*
		 * MPlayer options
		 */
		Group mplayerGroup = new Group(comp, SWT.NONE);
		mplayerGroup.setLayout(new GridLayout(3, false));
		mplayerGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		mplayerGroup.setText(mplayerGroupText);

		// Video output selection
		Label label;
		String videoOuput = Localization
				.getString(Localization.PREFERENCE_VIDEO_OUTPUT_DEVICE);
		label = new Label(mplayerGroup, SWT.NONE);
		label.setText(videoOuput);

		videoDevicesViewer = new ComboViewer(mplayerGroup, SWT.READ_ONLY
				| SWT.DROP_DOWN);
		videoDevicesViewer.setLabelProvider(videoDevicesLabelProvider);
		videoDevicesViewer.setContentProvider(new ArrayContentProvider());
		videoDevicesViewer.setInput(getVideoOutputDevices());
		VideoOutputDevice currentVideoDevice = userConfig
				.getVideoOutputDevice();
		if (currentVideoDevice != null) {
			videoDevicesViewer.setSelection(new StructuredSelection(
					currentVideoDevice));
		}
		videoDevicesViewer.addSelectionChangedListener(selectionListener);

		new Label(mplayerGroup, SWT.NONE);

		// Mplayer emplacement
		String mplayerText = Localization
				.getString(Localization.PREFERENCE_MPLAYER_EMPLACEMENT);

		label = new Label(mplayerGroup, SWT.NONE);
		label.setText(mplayerText);

		this.mplayerDirectoryText = new Text(mplayerGroup, SWT.BORDER
				| SWT.READ_ONLY);
		this.mplayerDirectoryText.setLayoutData(new GridData(SWT.FILL,
				SWT.FILL, true, false));

		this.mplayerButton = new Button(mplayerGroup, SWT.PUSH);
		this.mplayerButton.setText(browseText);
		this.mplayerButton.addListener(SWT.Selection, this.listener);

		/*
		 * Directory options
		 */
		Group muxerGroup = new Group(comp, SWT.NONE);
		muxerGroup.setLayout(new GridLayout(3, false));
		muxerGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		muxerGroup.setText(directoryGroupText);

		// Choose
		label = new Label(muxerGroup, SWT.NONE);
		label.setText(defaultMp4MuxerText);

		this.muxerViewer = new ComboViewer(muxerGroup, SWT.READ_ONLY
				| SWT.DROP_DOWN);
		this.muxerViewer.setLabelProvider(this.muxerLabelProvider);
		this.muxerViewer.setContentProvider(new ArrayContentProvider());
		this.muxerViewer.setInput(new Class[] { MP4BoxMuxer.class,
				MP4CreatorMuxer.class });
		this.muxerViewer.addSelectionChangedListener(selectionListener);
		new Label(muxerGroup, SWT.NONE);

		// MP4Box options
		label = new Label(muxerGroup, SWT.NONE);
		label.setText(mp4boxText);
		this.mp4boxDirectoryText = new Text(muxerGroup, SWT.BORDER
				| SWT.READ_ONLY);
		this.mp4boxDirectoryText.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, false));
		this.mp4boxButton = new Button(muxerGroup, SWT.PUSH);
		this.mp4boxButton.setText(browseText);
		this.mp4boxButton.addListener(SWT.Selection, listener);

		// mp4converter options
		label = new Label(muxerGroup, SWT.NONE);
		label.setText(mp4creatorText);
		this.mp4creatorDirectoryText = new Text(muxerGroup, SWT.BORDER
				| SWT.READ_ONLY);
		this.mp4creatorDirectoryText.setLayoutData(new GridData(SWT.FILL,
				SWT.FILL, true, false));
		this.mp4creatorButton = new Button(muxerGroup, SWT.PUSH);
		this.mp4creatorButton.setText(browseText);
		this.mp4creatorButton.addListener(SWT.Selection, this.listener);

		// Force update of fields
		propertyChange();

		Main.instance().getPreferenceStore().addPropertyChangeListener(
				this.propertiesListener);

		// Add disposal instruction
		muxerGroup.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				Main.instance().getPreferenceStore()
						.removePropertyChangeListener(
								PreferencesDialog.this.propertiesListener);
			}
		});

		return comp;

	}

	VideoOutputDevice[] getVideoOutputDevices() {

		VideoOutputDevice[] videoOutputDevices = {};
		try {
			videoOutputDevices = Configuration
					.getAvailableVideoOutputDevices(site.getMplayer());
		} catch (MPlayerException e) {
			// TODO : handle this error !
			e.printStackTrace();
			return null;
		}
		return videoOutputDevices;

	}

	/**
	 * Return the view site.
	 * 
	 * @return the view site.
	 */
	public IViewSite getViewSite() {
		return this.site;
	}

	/**
	 * Notify his user that user want to browse a new directory emplacement for
	 * MP4Box tool.
	 */
	void handleMP4BoxBrowse() {
		IPreferenceStore store = Main.instance().getPreferenceStore();

		String mplayerDirectory = store.getString(Main.PREF_MP4BOX_DIRECTORY);

		FileDialog dlg = new FileDialog(getShell(), SWT.OPEN);
		dlg.setFilterPath(mplayerDirectory);
		dlg.setFileName(MP4BoxMuxer.MP4BOX_BIN);

		dlg.setFilterExtensions(new String[] { MP4BoxMuxer.MP4BOX_BIN });
		dlg.setFilterNames(new String[] { MP4BoxMuxer.MP4BOX_BIN });

		String filePath = dlg.open();

		if (filePath != null) {
			filePath = dlg.getFilterPath();
			store.setValue(Main.PREF_MP4BOX_DIRECTORY, filePath);
		}
	}

	/**
	 * Notify this class that user want to browse a new directory emplacement
	 * for mp4creator tool.
	 */
	void handleMp4creatorBrowse() {
		IPreferenceStore store = Main.instance().getPreferenceStore();

		String mplayerDirectory = store
				.getString(Main.PREF_MP4CREATOR_DIRECTORY);

		FileDialog dlg = new FileDialog(getShell(), SWT.OPEN);
		dlg.setFilterPath(mplayerDirectory);
		dlg.setFileName(MP4CreatorMuxer.MP4CREATOR_BIN);

		dlg
				.setFilterExtensions(new String[] { MP4CreatorMuxer.MP4CREATOR_BIN });
		dlg.setFilterNames(new String[] { MP4CreatorMuxer.MP4CREATOR_BIN });

		String filePath = dlg.open();

		if (filePath != null) {
			filePath = dlg.getFilterPath();
			store.setValue(Main.PREF_MP4CREATOR_DIRECTORY, filePath);
		}
	}

	/**
	 * Notify his user that user want to browse a new directory emplacement for
	 * Mplayer tool.
	 */
	void handleMPlayerBrowse() {

		IPreferenceStore store = Main.instance().getPreferenceStore();

		String mplayerDirectory = store.getString(Main.PREF_MPLAYER_DIRECTORY);

		FileDialog dlg = new FileDialog(getShell(), SWT.OPEN);
		dlg.setFilterPath(mplayerDirectory);
		dlg.setFileName(MPlayerWrapper.MPLAYER_BIN);

		dlg.setFilterExtensions(new String[] { MPlayerWrapper.MPLAYER_BIN });
		dlg.setFilterNames(new String[] { MPlayerWrapper.MPLAYER_BIN });

		String filePath = dlg.open();

		if (filePath != null) {
			filePath = dlg.getFilterPath();
			store.setValue(Main.PREF_MPLAYER_DIRECTORY, filePath);

			// Show warning message
			String message = Localization
					.getString(Localization.PREFERENCE_RESTART_WARNING);

			ErrorMessage.show(getShell(), message, SWT.ICON_WARNING);

		}

	}

	void handleMuxerCombo() {

	}

	/**
	 * Initialize this view with the given view site. Sub-class that overload
	 * this method must call super.init().
	 * 
	 * @param viewSite
	 *            the view site.
	 */
	public void init(IViewSite viewSite) {
		this.site = viewSite;

		this.userConfig = viewSite.getMplayer().getUserConfiguration();
	}

	void muxerSelectionAsChanged(SelectionChangedEvent event) {

		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		Object element = selection.getFirstElement();
		if (element == null || !(element instanceof Class<?>))
			return;

		Class<?> clazz = (Class<?>) element;

		String classname = clazz.getCanonicalName();

		IPreferenceStore store = Main.instance().getPreferenceStore();
		store.setValue(Main.PREF_MP4MUXER_CLASS, classname);

	}

	/**
	 * Notify this class that property has change.
	 */
	void propertyChange() {

		IPreferenceStore store = Main.instance().getPreferenceStore();

		this.replaceFileButton.setSelection(store
				.getBoolean(Main.PREF_REPLACE_FILE));

		// Mplayer directory
		String mplayerDirectory = store.getString(Main.PREF_MPLAYER_DIRECTORY);
		this.mplayerDirectoryText.setText(mplayerDirectory);

		// Mp4box directory
		String mp4boxDirectory = store.getString(Main.PREF_MP4BOX_DIRECTORY);
		this.mp4boxDirectoryText.setText(mp4boxDirectory);

		// Mp4creator directory
		String mp4converterDirectory = store
				.getString(Main.PREF_MP4CREATOR_DIRECTORY);
		this.mp4creatorDirectoryText.setText(mp4converterDirectory);

		// Default Mp4 muxer
		String classname = store.getString(Main.PREF_MP4MUXER_CLASS);
		Class<?> clazz = null;
		try {
			clazz = Class.forName(classname);
			if (clazz != null) {
				muxerViewer.setSelection(new StructuredSelection(clazz));
			}
		} catch (ClassNotFoundException e) {
			// Fail silently.
		}

	}

	/**
	 * Notify this class that user change the selection of replace existing file
	 * options.
	 */
	void replaceSelectionAsChanged() {

		boolean selection = this.replaceFileButton.getSelection();

		IPreferenceStore store = Main.instance().getPreferenceStore();
		store.setValue(Main.PREF_REPLACE_FILE, selection);

	}

	/**
	 * Notify this class that user change the selected video output device.
	 */
	void videoOutputDeviceSelectionAsChanged(SelectionChangedEvent event) {

		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		Object objectSelected = selection.getFirstElement();

		if (objectSelected instanceof VideoOutputDevice) {
			this.userConfig
					.setVideoOutputDevice((VideoOutputDevice) objectSelected);
			try {
				this.userConfig.save();
			} catch (IOException e) {
				// TODO handle this error !
				e.printStackTrace();
			}
		}

	}
}
