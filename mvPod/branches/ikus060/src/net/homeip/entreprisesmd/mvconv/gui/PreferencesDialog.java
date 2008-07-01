package net.homeip.entreprisesmd.mvconv.gui;

import java.io.IOException;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoOutputDevice;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.config.Configuration;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.MP4BoxMuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.X264EncodingOptions;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
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
	 * MP4Box button.
	 */
	private Button mp4boxButton;
	/**
	 * MP4Box directory text component.
	 */
	private Text mp4boxDirectoryText;
	/**
	 * MPlayer button.
	 */
	private Button mplayerButton;
	/**
	 * Mplayer directory text component.
	 */
	private Text mplayerDirectoryText;

	/**
	 * Property listener.
	 */
	private IPropertyChangeListener propertiesListener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			PreferencesDialog.this.propertyChange();
		}
	};
	/**
	 * Replace button.
	 */
	private Button replaceButton;
	/**
	 * Selection change listener
	 */
	private ISelectionChangedListener selectionChangeListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == videoOutputDeviceComboViewer) {
				videoOutputDeviceSelectionAsChanged();
			}
		}
	};
	/**
	 * Selection listener.
	 */
	private SelectionListener selectionListener = new SelectionAdapter() {

		public void widgetSelected(SelectionEvent event) {
			if (event.widget == replaceButton) {
				replaceSelectionAsChanged();
			} else if (event.widget == mp4boxButton) {
				handleMP4BoxBrowse();
			} else if (event.widget == mplayerButton) {
				handleMPlayerBrowse();
			}
		}

	};
	/**
	 * The view site.
	 */
	private IViewSite site;
	/**
	 * User configuration.
	 */
	private Configuration userConfiguration;

	/**
	 * Combo viewer
	 */
	private ComboViewer videoOutputDeviceComboViewer;

	private LabelProvider videoOutputDeviceLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			if (element instanceof VideoOutputDevice) {
				return ((VideoOutputDevice) element).getName();
			}
			return element.toString();
		}
	};

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
	protected Control createDialogArea(Composite parent) {

		String browseText = Localization
				.getString(Localization.PREFERENCE_BROWSE);
		String directoryGroupText = Localization
				.getString(Localization.PREFERENCE_DIRECTORY_GROUP);
		String optionGroupText = Localization
				.getString(Localization.PREFERENCE_OPTION_GROUP);
		String mplayerGroupText = Localization
				.getString(Localization.PREFERENCE_MPLAYER_GROUP);

		/*
		 * General options
		 */
		Group optionGroup = new Group(parent, SWT.NONE);
		optionGroup.setLayout(new GridLayout(1, false));
		optionGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		optionGroup.setText(optionGroupText);

		// Alway replace existing file button
		String replaceText = Localization
				.getString(Localization.PREFERENCE_REPLACE);
		replaceButton = new Button(optionGroup, SWT.CHECK);
		replaceButton.setText(replaceText);
		replaceButton.addSelectionListener(selectionListener);

		/*
		 * Mplayer options
		 */
		Group mplayerGroup = new Group(parent, SWT.NONE);
		mplayerGroup.setLayout(new GridLayout(2, false));
		mplayerGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		mplayerGroup.setText(mplayerGroupText);

		// Video output selection
		Label label;
		String videoOuput = Localization
				.getString(Localization.PREFERENCE_VIDEO_OUTPUT_DEVICE);
		label = new Label(mplayerGroup, SWT.NONE);
		label.setText(videoOuput);

		videoOutputDeviceComboViewer = new ComboViewer(mplayerGroup,
				SWT.READ_ONLY | SWT.DROP_DOWN);
		videoOutputDeviceComboViewer
				.setLabelProvider(videoOutputDeviceLabelProvider);
		videoOutputDeviceComboViewer
				.addSelectionChangedListener(selectionChangeListener);

		VideoOutputDevice[] videoOutputDevices = {};
		try {
			videoOutputDevices = Configuration
					.getAvailableVideoOutputDevices(site.getMplayer());
		} catch (MPlayerException e) {
			// TODO : handle this error !
			e.printStackTrace();
		}

		for (int i = 0; i < videoOutputDevices.length; i++) {
			videoOutputDeviceComboViewer.add(videoOutputDevices[i]);
		}

		VideoOutputDevice videoOutputDeviceSelected = userConfiguration
				.getVideoOutputDevice();
		if (videoOutputDeviceSelected != null) {
			videoOutputDeviceComboViewer.setSelection(new StructuredSelection(
					videoOutputDeviceSelected));
		}

		/*
		 * Directory options
		 */
		Group directoryGroup = new Group(parent, SWT.NONE);
		directoryGroup.setLayout(new GridLayout(3, false));
		directoryGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		directoryGroup.setText(directoryGroupText);

		// Mplayer emplacement
		String mplayerText = Localization
				.getString(Localization.PREFERENCE_MPLAYER_EMPLACEMENT);

		label = new Label(directoryGroup, SWT.NONE);
		label.setText(mplayerText);

		mplayerDirectoryText = new Text(directoryGroup, SWT.BORDER
				| SWT.READ_ONLY);
		mplayerDirectoryText.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, false));

		mplayerButton = new Button(directoryGroup, SWT.PUSH);
		mplayerButton.setText(browseText);
		mplayerButton.addSelectionListener(selectionListener);

		// MP4Box emplacement
		String mp4boxText = Localization
				.getString(Localization.PREFERENCE_MP4BOX_EMPLACEMENT);
		label = new Label(directoryGroup, SWT.NONE);
		label.setText(mp4boxText);

		mp4boxDirectoryText = new Text(directoryGroup, SWT.BORDER
				| SWT.READ_ONLY);
		mp4boxDirectoryText.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, false));

		mp4boxButton = new Button(directoryGroup, SWT.PUSH);
		mp4boxButton.setText(browseText);
		mp4boxButton.addSelectionListener(selectionListener);

		propertyChange();

		Main.instance().getPreferenceStore().addPropertyChangeListener(
				propertiesListener);

		// Add disposal instruction
		directoryGroup.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				Main.instance().getPreferenceStore()
						.removePropertyChangeListener(propertiesListener);
			}
		});

		return directoryGroup;

	}

	/**
	 * Return the view site.
	 * 
	 * @return the view site.
	 */
	public IViewSite getViewSite() {
		return site;
	}

	/**
	 * Notify his user that user want to browse a new directory emplacement for
	 * MP4Box tool.
	 */
	private void handleMP4BoxBrowse() {
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
	 * Notify his user that user want to browse a new directory emplacement for
	 * Mplayer tool.
	 */
	private void handleMPlayerBrowse() {

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

	/**
	 * Initialize this view with the given view site. Sub-class that overload
	 * this method must call super.init().
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {
		this.site = site;

		userConfiguration = site.getMplayer().getUserConfiguration();
	}

	/**
	 * Notify this class that property has change.
	 */
	private void propertyChange() {

		IPreferenceStore store = Main.instance().getPreferenceStore();

		replaceButton.setSelection(store.getBoolean(Main.PREF_REPLACE_FILE));

		String mplayerDirectory = store.getString(Main.PREF_MPLAYER_DIRECTORY);
		mplayerDirectoryText.setText(mplayerDirectory);

		String mp4boxDirectory = store.getString(Main.PREF_MP4BOX_DIRECTORY);
		mp4boxDirectoryText.setText(mp4boxDirectory);

	}

	/**
	 * Notify this class that user change the selection of replace existing file
	 * options.
	 */
	private void replaceSelectionAsChanged() {

		boolean selection = replaceButton.getSelection();

		IPreferenceStore store = Main.instance().getPreferenceStore();
		store.setValue(Main.PREF_REPLACE_FILE, selection);

	}

	/**
	 * Notify this class that user change the selected video output device.
	 */
	private void videoOutputDeviceSelectionAsChanged() {

		IStructuredSelection selection = (IStructuredSelection) videoOutputDeviceComboViewer
				.getSelection();
		Object objectSelected = selection.getFirstElement();

		if (objectSelected instanceof VideoOutputDevice) {
			userConfiguration
					.setVideoOutputDevice((VideoOutputDevice) objectSelected);
			try {
				userConfiguration.save();
			} catch (IOException e) {
				// TODO handle this error !
				e.printStackTrace();
			}
		}

	}
}
