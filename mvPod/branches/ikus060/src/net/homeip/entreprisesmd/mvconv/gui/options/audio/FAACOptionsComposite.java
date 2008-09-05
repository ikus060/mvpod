package net.homeip.entreprisesmd.mvconv.gui.options.audio;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profiles;
import net.homeip.entreprisesmd.mvconv.gui.IProfileContextListener;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;
import net.homeip.entreprisesmd.mvconv.gui.options.ScaleEditor;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.AudioEncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.FAACEncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.LameEncodingOptions;

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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This class offer to change the options for Lame codec.
 * 
 * @author patapouf
 * 
 */
public class FAACOptionsComposite extends AudioOptionsInterface {
	/**
	 * Default bitrate value
	 */
	private static final int BITRATE_DEFAULT = 128;
	/**
	 * Increment audio bitrate value.
	 */
	private static final int INCREMENT_AUDIO_BITRATE = 2;
	/**
	 * Page increment audio bitrate value.
	 */
	private static final int PAGE_INCREMENT_AUDIO_BITRATE = 32;

	/**
	 * Return the mapper for this interface.
	 * 
	 * @return the mapper.
	 */
	public static AudioOptionsMapper getMapper() {
		return new AudioOptionsMapper() {
			public AudioOptionsInterface createInterface(Composite parent,
					int style) {
				return new FAACOptionsComposite(parent, style);
			}

			public AudioFormat getAudioFormat() {
				return AudioFormat.FORMAT_MPEG4_AAC;
			}

			public AudioEncodingOptions getEncodingOptions() {
				return new FAACEncodingOptions(BITRATE_DEFAULT);
			}
		};
	}

	/**
	 * Audio bitrate editor.
	 */
	private ScaleEditor bitrateEditor;
	/**
	 * Bitrate label.
	 */
	private Label bitrateLabel;
	/**
	 * Listener to profile context.
	 */
	IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			profileAsChanged();
		}
	};

	/**
	 * MPEG version viewer.
	 */
	ComboViewer mpegVersionViewer;
	/**
	 * MPEG label provider
	 */
	private LabelProvider mpegVersionLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			String key = null;
			if (element.equals(FAACEncodingOptions.MPEG_VERSION_2)) {
				key = Localization.OPTIONS_FAAC_MPEG_VERSION_2;
			} else if (element.equals(FAACEncodingOptions.MPEG_VERSION_4)) {
				key = Localization.OPTIONS_FAAC_MPEG_VERSION_4;
			}
			if (key != null) {
				return Localization.getString(key);
			}
			return element.toString();
		}
	};
	/**
	 * Type label provider.
	 */
	private LabelProvider objectTypeLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			String key = null;
			if (element.equals(FAACEncodingOptions.OBJECT_TYPE_LOW)) {
				key = Localization.OPTIONS_FAAC_OBJECT_TYPE_LOW;
			} else if (element.equals(FAACEncodingOptions.OBJECT_TYPE_LTP)) {
				key = Localization.OPTIONS_FAAC_OBJECT_TYPE_LTP;
			} else if (element.equals(FAACEncodingOptions.OBJECT_TYPE_MAIN)) {
				key = Localization.OPTIONS_FAAC_OBJECT_TYPE_MAIN;
			} else if (element.equals(FAACEncodingOptions.OBJECT_TYPE_SSR)) {
				key = Localization.OPTIONS_FAAC_OBJECT_TYPE_SSR;
			}
			if (key != null) {
				return Localization.getString(key);
			}
			return element.toString();
		}
	};
	/**
	 * Type viewer.
	 */
	ComboViewer objectTypeViewer;
	/**
	 * Sample rate combo viewer.
	 */
	ComboViewer sampleRateViewer;
	/**
	 * Sample rate label provider.
	 */
	private LabelProvider sampleRateViewerLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			if (element instanceof Integer) {
				return element.toString() + " Hz"; //$NON-NLS-1$
			}
			return element.toString();
		}
	};
	/**
	 * Selection listener to video dimension viewer.
	 */
	private ISelectionChangedListener selectionChangeListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {

			if (event.getSource() == FAACOptionsComposite.this.objectTypeViewer) {
				objectTypeSelectionAsChanged();
			} else if (event.getSource() == FAACOptionsComposite.this.mpegVersionViewer) {
				mpegVersionSelectionAsChanged();
			} else if (event.getSource() == FAACOptionsComposite.this.sampleRateViewer) {
				sampleRateAsChanged();
			}

		}
	};
	/**
	 * Selection listener to bitrate editor.
	 */
	private SelectionListener selectionListener = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			bitrateSelectionAsChanged();
		}
	};

	/**
	 * Create a new lame composite interface.
	 * 
	 * @param parent
	 *            the parent of this interface.
	 * @param style
	 *            the style.
	 */
	public FAACOptionsComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Notify this view that user change the slected bitrate value.
	 */
	void bitrateSelectionAsChanged() {

		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Check if value as changed
		int audioBitrate = this.bitrateEditor.getSelection();
		EncodingOptions options = profile.getEncodingOptions();
		AudioEncodingOptions audioOptions = options.getAudioOptions();

		if (audioBitrate != audioOptions.getBitrate()) {

			// Change profile value
			audioOptions.setBitrate(audioBitrate);
			options.setAudioOptions(audioOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}

	}

	/**
	 * Return the selected type from the type viewer
	 * 
	 * @return the select type.
	 */
	int getMpegVersionSelection() {
		Object selection = ((IStructuredSelection) this.mpegVersionViewer
				.getSelection()).getFirstElement();
		if (!(selection instanceof Integer)) {
			return -1;
		}
		return (Integer) selection;
	}

	/**
	 * Return the selected type from the type viewer
	 * 
	 * @return the select type.
	 */
	int getObjectTypeSelection() {
		Object selection = ((IStructuredSelection) this.objectTypeViewer
				.getSelection()).getFirstElement();
		if (!(selection instanceof Integer)) {
			return -1;
		}
		return (Integer) selection;
	}
	/**
	 * Return the selected sample rate.
	 * 
	 * @return the selected sample rate.
	 */
	Integer getSampleRateSelection() {
		Object selection = ((IStructuredSelection) this.sampleRateViewer
				.getSelection()).getFirstElement();
		if (!(selection instanceof Integer)) {
			return null;
		}
		return (Integer) selection;
	}
	/**
	 * Initialize this view with the given view site.
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {

		super.init(site);

		this.setLayout(new GridLayout(4, false));

		// Prepare localized string
		String bitrateText = Localization
				.getString(Localization.OPTIONS_BITRATE);
		String bitrateValueFormat = Localization
				.getString(Localization.OPTIONS_BITRATE_FORMAT_VALUE);
		String typeText = Localization
				.getString(Localization.OPTIONS_FAAC_OBJECT_TYPE);
		String mpegVersionText = Localization
				.getString(Localization.OPTIONS_FAAC_MPEG_VERSION);
		String sampleRateText = Localization
		.getString(Localization.OPTIONS_SAMPLE_RATE);
		
		// Check the first column size.
		GC gc = new GC(this);
		Point bitrateTextSize = gc.textExtent(bitrateText);
		Point typeTextSize = gc.textExtent(typeText);
		Point sampleTextSize = gc.textExtent(sampleRateText);
		gc.dispose();
		int firstColumnWidth = Math.max(bitrateTextSize.x, typeTextSize.x);
		firstColumnWidth = Math.max(firstColumnWidth, sampleTextSize.x);

		// Bitrate
		this.bitrateLabel = new Label(this, SWT.NONE);
		this.bitrateLabel.setText(bitrateText);
		this.bitrateLabel.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.bitrateEditor = new ScaleEditor(this, SWT.NONE);
		this.bitrateEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));
		this.bitrateEditor.setFormatValue(bitrateValueFormat);
		this.bitrateEditor.setIncrement(INCREMENT_AUDIO_BITRATE);
		this.bitrateEditor.setPageIncrement(PAGE_INCREMENT_AUDIO_BITRATE);
		this.bitrateEditor.setRange(FAACEncodingOptions.BITRATE_MIN_VALUE,
				FAACEncodingOptions.BITRATE_MAX_VALUE);
		this.bitrateEditor.addSelectionListener(this.selectionListener);

		// Type
		Label label = new Label(this, SWT.NONE);
		label.setText(typeText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.objectTypeViewer = new ComboViewer(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		this.objectTypeViewer.getCombo().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, false, false));
		this.objectTypeViewer.setLabelProvider(this.objectTypeLabelProvider);
		this.objectTypeViewer.addSelectionChangedListener(this.selectionChangeListener);
		this.objectTypeViewer.add(FAACEncodingOptions.OBJECT_TYPE_MAIN);
		this.objectTypeViewer.add(FAACEncodingOptions.OBJECT_TYPE_LOW);
		this.objectTypeViewer.add(FAACEncodingOptions.OBJECT_TYPE_SSR);
		this.objectTypeViewer.add(FAACEncodingOptions.OBJECT_TYPE_LTP);
	
		// MPEG version
		label = new Label(this, SWT.NONE);
		label.setText(mpegVersionText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.mpegVersionViewer = new ComboViewer(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		this.mpegVersionViewer.getCombo().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, false, false));
		this.mpegVersionViewer.setLabelProvider(this.mpegVersionLabelProvider);
		this.mpegVersionViewer.addSelectionChangedListener(this.selectionChangeListener);
		this.mpegVersionViewer.add(FAACEncodingOptions.MPEG_VERSION_2);
		this.mpegVersionViewer.add(FAACEncodingOptions.MPEG_VERSION_4);

		// Sample rate
		label = new Label(this, SWT.NONE);
		label.setText(sampleRateText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.sampleRateViewer = new ComboViewer(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		this.sampleRateViewer.getCombo().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));
		this.sampleRateViewer.setLabelProvider(this.sampleRateViewerLabelProvider);
		this.sampleRateViewer.add(48000);
		this.sampleRateViewer.add(44100);
		this.sampleRateViewer.addSelectionChangedListener(this.selectionChangeListener);
		
		// Force update
		profileAsChanged();

		// Add listener
		getViewSite().getProfileContext().addProfileContextListener(
				this.profileContextListener);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getViewSite().getProfileContext().removeProfileContextListener(
						FAACOptionsComposite.this.profileContextListener);
			}
		});

	}
	/**
	 * Notify this class that user change the sample rate.
	 */
	void sampleRateAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}
		EncodingOptions options = profile.getEncodingOptions();
		AudioEncodingOptions audioOptions = options.getAudioOptions();
		if (!(audioOptions instanceof LameEncodingOptions)) {
			return;
		}

		// Check if value as changed
		Integer sampleRateSelected = getSampleRateSelection();
		if (sampleRateSelected != null
				&& sampleRateSelected != audioOptions.getOutputSampleRate()) {

			// Change profile value
			audioOptions.setOutputSampleRate(sampleRateSelected);
			options.setAudioOptions(audioOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}
	}
	/**
	 * Update this view to reflect the profile modification.
	 */
	void profileAsChanged() {

		// Get the Lame encoding options
		Profile selectedProfile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (selectedProfile instanceof HardCodedProfile) {
			return;
		}
		AudioEncodingOptions audioOptions = selectedProfile
				.getEncodingOptions().getAudioOptions();
		if (!(audioOptions instanceof FAACEncodingOptions)) {
			return;
		}
		FAACEncodingOptions faacOptions = (FAACEncodingOptions) audioOptions;

		// Change bitrate editor
		if (faacOptions.getBitrate() != this.bitrateEditor.getSelection()) {
			this.bitrateEditor.setSelection(faacOptions.getBitrate());
		}

		// Type viewer
		int typeSelected = getObjectTypeSelection();
		if (typeSelected != faacOptions.getObjectType()) {
			this.objectTypeViewer.setSelection(new StructuredSelection(faacOptions
					.getObjectType()));
		}

		// MPEG viewer
		int mpegVersionSelected = getMpegVersionSelection();
		if (mpegVersionSelected != faacOptions.getMPEGVersion()) {
			this.mpegVersionViewer.setSelection(new StructuredSelection(faacOptions
					.getMPEGVersion()));
		}

		// Sample rate viewer
		Integer sampleRateSelected = getSampleRateSelection();
		if (sampleRateSelected == null
				|| sampleRateSelected != faacOptions.getOutputSampleRate()) {
			this.sampleRateViewer.setSelection(new StructuredSelection(faacOptions
					.getOutputSampleRate()));
		}
		
	}

	/**
	 * Notify this class that user change the mpeg version.
	 */
	void mpegVersionSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}
		EncodingOptions options = profile.getEncodingOptions();
		AudioEncodingOptions audioOptions = options.getAudioOptions();
		if (!(audioOptions instanceof FAACEncodingOptions)) {
			return;
		}
		FAACEncodingOptions faacOptions = (FAACEncodingOptions) audioOptions;

		// Check if value as changed
		int mpegVersionSelected = getMpegVersionSelection();
		if (mpegVersionSelected != faacOptions.getMPEGVersion()) {

			// Change profile value
			faacOptions.setMPEGVersion(mpegVersionSelected);
			options.setAudioOptions(faacOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}
	}

	/**
	 * Notify this class that user change the type.
	 */
	void objectTypeSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}
		EncodingOptions options = profile.getEncodingOptions();
		AudioEncodingOptions audioOptions = options.getAudioOptions();
		if (!(audioOptions instanceof FAACEncodingOptions)) {
			return;
		}
		FAACEncodingOptions lameOptions = (FAACEncodingOptions) audioOptions;

		// Check if value as changed
		int typeSelected = getObjectTypeSelection();
		if (typeSelected != lameOptions.getObjectType()) {

			// Change profile value
			lameOptions.setObjectType(typeSelected);
			options.setAudioOptions(lameOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}
	}
}
