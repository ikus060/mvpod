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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This class offer to change the options for Lame codec.
 * 
 * @author patapouf
 * 
 */
public class LameOptionsComposite extends AudioOptionsInterface {
	/**
	 * Default bitrate value
	 */
	private static final int BITRATE_DEFAULT = 128;
	/**
	 * Increment audio bitrate value.
	 */
	private static final int INCREMENT_AUDIO_BITRATE = 1;
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
				return new LameOptionsComposite(parent, style);
			}

			public AudioFormat getAudioFormat() {
				return AudioFormat.FORMAT_MP3;
			}

			public AudioEncodingOptions getEncodingOptions() {
				return new LameEncodingOptions(BITRATE_DEFAULT,
						LameEncodingOptions.TYPE_AVERAGE_BITRATE);
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
	 * Channel label provider.
	 */
	private LabelProvider channelLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			String key = null;
			if (element.equals(LameEncodingOptions.MODE_AUTO)) {
				key = Localization.OPTIONS_CHANNEL_MODE_AUTO;
			} else if (element.equals(LameEncodingOptions.MODE_DUAL_CHANNEL)) {
				key = Localization.OPTIONS_CHANNEL_MODE_DUAL;
			} else if (element.equals(LameEncodingOptions.MODE_JOIN_STEREO)) {
				key = Localization.OPTIONS_CHANNEL_MODE_JOIN_STEREO;
			} else if (element.equals(LameEncodingOptions.MODE_MONO)) {
				key = Localization.OPTIONS_CHANNEL_MODE_MONO;
			} else if (element.equals(LameEncodingOptions.MODE_STEREO)) {
				key = Localization.OPTIONS_CHANNEL_MODE_STEREO;
			}
			if (key != null) {
				return Localization.getString(key);
			}
			return element.toString();
		}
	};
	/**
	 * Channel viewer.
	 */
	private ComboViewer channelViewer;
	/**
	 * Fast encoding button
	 */
	private Button fastEncodingButton;
	/**
	 * Listener to profile context.
	 */
	private IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			profileAsChanged();
		}
	};
	/**
	 * Quality editor.
	 */
	private ScaleEditor qualityEditor;
	/**
	 * Variable quality label.
	 */
	private Label qualityLabel;
	/**
	 * Type label provider.
	 */
	private LabelProvider typeLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			String key = null;
			if (element.equals(LameEncodingOptions.TYPE_AVERAGE_BITRATE)) {
				key = Localization.OPTIONS_LAME_TYPE_AVERAGE;
			} else if (element
					.equals(LameEncodingOptions.TYPE_CONSTANT_BITRATE)) {
				key = Localization.OPTIONS_LAME_TYPE_CONSTANT;
			} else if (element
					.equals(LameEncodingOptions.TYPE_VARIABLE_BITRATE)) {
				key = Localization.OPTIONS_LAME_TYPE_VARIABLE;
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
	private ComboViewer typeViewer;
	/**
	 * Variable method label.
	 */
	private Label variableMethodLabel;
	/**
	 * Variable method label provider.
	 */
	private LabelProvider variableMethodLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			String key = null;
			if (element.equals(LameEncodingOptions.VARIABLE_METHOD_ABR)) {
				key = Localization.OPTIONS_LAME_VARIABLE_METHOD_ABR;
			} else if (element.equals(LameEncodingOptions.VARIABLE_METHOD_CBR)) {
				key = Localization.OPTIONS_LAME_VARIABLE_METHOD_CBR;
			} else if (element.equals(LameEncodingOptions.VARIABLE_METHOD_MT)) {
				key = Localization.OPTIONS_LAME_VARIABLE_METHOD_MT;
			} else if (element.equals(LameEncodingOptions.VARIABLE_METHOD_MTRH)) {
				key = Localization.OPTIONS_LAME_VARIABLE_METHOD_MTRH;
			} else if (element.equals(LameEncodingOptions.VARIABLE_METHOD_RH)) {
				key = Localization.OPTIONS_LAME_VARIABLE_METHOD_RH;
			}
			if (key != null) {
				return Localization.getString(key);
			}
			return element.toString();
		}
	};
	/**
	 * Variable method viewer.
	 */
	private ComboViewer variableMethodViewer;
	/**
	 * Sample rate combo viewer.
	 */
	private ComboViewer sampleRateViewer;
	/**
	 * Sample rate label provider.
	 */
	private LabelProvider sampleRateViewerLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			if (element instanceof Integer) {
				return element.toString() + " Hz";
			}
			return element.toString();
		}
	};
	/**
	 * Selection listener to video dimension viewer.
	 */
	private ISelectionChangedListener selectionChangeListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {

			if (event.getSource() == channelViewer) {
				channelSelectionAsChanged();
			} else if (event.getSource() == typeViewer) {
				typeSelectionAsChanged();
			} else if (event.getSource() == variableMethodViewer) {
				variableMethodAsChanged();
			} else if (event.getSource() == sampleRateViewer) {
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
	public LameOptionsComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Notify this view that user change the slected bitrate value.
	 */
	private void bitrateSelectionAsChanged() {

		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Check if value as changed
		int audioBitrate = bitrateEditor.getSelection();
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
	 * Use to change the grid layout.
	 * 
	 * @param object
	 *            the grid layout.
	 * @param height
	 *            the heigth value.
	 * @return the new GridData.
	 */
	private Object changeHeightLayout(Object object, int height) {
		if (!(object instanceof GridData)) {
			return object;
		}
		GridData data = (GridData) object;
		data.heightHint = height;
		return data;
	}

	/**
	 * Notify this class that user change the channel mode.
	 */
	private void channelSelectionAsChanged() {

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
		LameEncodingOptions lameOptions = (LameEncodingOptions) audioOptions;

		// Check if value as changed
		int channelSelected = getChannelSelection();
		if (channelSelected != lameOptions.getChannelMode()) {

			// Change profile value
			lameOptions.setChannelMode(channelSelected);
			options.setAudioOptions(lameOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}

	}

	/**
	 * Return the selected channel from the channel viewer.
	 * 
	 * @return the selected channel.
	 */
	private Integer getChannelSelection() {
		Object selection = ((IStructuredSelection) channelViewer.getSelection())
				.getFirstElement();
		if (!(selection instanceof Integer)) {
			return null;
		}
		return (Integer) selection;
	}

	/**
	 * Return the selected sample rate.
	 * 
	 * @return the selected sample rate.
	 */
	private Integer getSampleRateSelection() {
		Object selection = ((IStructuredSelection) sampleRateViewer
				.getSelection()).getFirstElement();
		if (!(selection instanceof Integer)) {
			return null;
		}
		return (Integer) selection;
	}

	/**
	 * Return the selected type from the type viewer
	 * 
	 * @return the select type.
	 */
	private Integer getTypeSelection() {
		Object selection = ((IStructuredSelection) typeViewer.getSelection())
				.getFirstElement();
		if (!(selection instanceof Integer)) {
			return null;
		}
		return (Integer) selection;
	}

	/**
	 * Return the select variable method from the variable method viewer.
	 * 
	 * @return the selected variable method.
	 */
	private Integer getVariableMethodSelection() {
		Object selection = ((IStructuredSelection) variableMethodViewer
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
		String channelText = Localization
				.getString(Localization.OPTIONS_CHANNEL);
		String typeText = Localization.getString(Localization.OPTIONS_TYPE);
		String variableMethodText = Localization
				.getString(Localization.OPTIONS_VARIABLE_METHOD);
		String qualityText = Localization
				.getString(Localization.OPTIONS_VARIABLE_QUALITY);
		String sampleRateText = Localization
				.getString(Localization.OPTIONS_SAMPLE_RATE);

		// Check the first column size.
		GC gc = new GC(this);
		Point bitrateTextSize = gc.textExtent(bitrateText);
		Point channelTextSize = gc.textExtent(channelText);
		Point typeTextSize = gc.textExtent(typeText);
		Point sampleRateTextSize = gc.textExtent(sampleRateText);
		gc.dispose();
		int firstColumnWidth = Math.max(bitrateTextSize.x, channelTextSize.x);
		firstColumnWidth = Math.max(firstColumnWidth, typeTextSize.x);
		firstColumnWidth = Math.max(firstColumnWidth, sampleRateTextSize.x);

		Composite comp = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(4, false);
		layout.marginHeight=0;
		layout.marginWidth=0;
		layout.verticalSpacing=0;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,false,4,1));
		
		// Bitrate
		bitrateLabel = new Label(comp, SWT.NONE);
		bitrateLabel.setText(bitrateText);
		bitrateLabel.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		bitrateEditor = new ScaleEditor(comp, SWT.NONE);
		bitrateEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));
		bitrateEditor.setFormatValue(bitrateValueFormat);
		bitrateEditor.setIncrement(INCREMENT_AUDIO_BITRATE);
		bitrateEditor.setPageIncrement(PAGE_INCREMENT_AUDIO_BITRATE);
		bitrateEditor.setRange(LameEncodingOptions.BITRATE_MIN_VALUE,
				LameEncodingOptions.BITRATE_MAX_VALUE);
		bitrateEditor.addSelectionListener(selectionListener);

		// Quality viewer
		qualityLabel = new Label(comp, SWT.NONE);
		qualityLabel.setText(qualityText);
		qualityLabel.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		qualityEditor = new ScaleEditor(comp, SWT.NONE);
		qualityEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));
		qualityEditor.setRange(LameEncodingOptions.QUALITY_MIN_VALUE,
				LameEncodingOptions.QUALITY_MAX_VALUE);
		qualityEditor.addSelectionListener(selectionListener);

		// Type
		Label label = new Label(this, SWT.NONE);
		label.setText(typeText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		typeViewer = new ComboViewer(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		typeViewer.getCombo().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));
		typeViewer.setLabelProvider(typeLabelProvider);
		typeViewer.addSelectionChangedListener(selectionChangeListener);
		typeViewer.add(LameEncodingOptions.TYPE_AVERAGE_BITRATE);
		typeViewer.add(LameEncodingOptions.TYPE_CONSTANT_BITRATE);
		typeViewer.add(LameEncodingOptions.TYPE_VARIABLE_BITRATE);

		// Variable method bitrate
		variableMethodLabel = new Label(this, SWT.NONE);
		variableMethodLabel.setText(variableMethodText);

		variableMethodViewer = new ComboViewer(this, SWT.READ_ONLY
				| SWT.DROP_DOWN);
		variableMethodViewer.getCombo().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));
		variableMethodViewer.setLabelProvider(variableMethodLabelProvider);
		variableMethodViewer
				.addSelectionChangedListener(selectionChangeListener);
		variableMethodViewer.add(LameEncodingOptions.VARIABLE_METHOD_ABR);
		variableMethodViewer.add(LameEncodingOptions.VARIABLE_METHOD_CBR);
		variableMethodViewer.add(LameEncodingOptions.VARIABLE_METHOD_MT);
		variableMethodViewer.add(LameEncodingOptions.VARIABLE_METHOD_MTRH);
		variableMethodViewer.add(LameEncodingOptions.VARIABLE_METHOD_RH);

		// Sample rate
		label = new Label(this, SWT.NONE);
		label.setText(sampleRateText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		sampleRateViewer = new ComboViewer(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		sampleRateViewer.getCombo().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));
		sampleRateViewer.setLabelProvider(sampleRateViewerLabelProvider);
		sampleRateViewer.add(48000);
		sampleRateViewer.add(44100);
		sampleRateViewer.addSelectionChangedListener(selectionChangeListener);

		// Channel mode
		label = new Label(this, SWT.NONE);
		label.setText(channelText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		channelViewer = new ComboViewer(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		channelViewer.getCombo().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));
		channelViewer.setLabelProvider(channelLabelProvider);
		channelViewer.addSelectionChangedListener(selectionChangeListener);
		channelViewer.add(LameEncodingOptions.MODE_AUTO);
		channelViewer.add(LameEncodingOptions.MODE_STEREO);
		channelViewer.add(LameEncodingOptions.MODE_JOIN_STEREO);
		channelViewer.add(LameEncodingOptions.MODE_DUAL_CHANNEL);
		channelViewer.add(LameEncodingOptions.MODE_MONO);

		// Fast encoding
		String fastEncodingText = Localization
				.getString(Localization.OPTIONS_LAME_FAST);
		fastEncodingButton = new Button(this, SWT.CHECK);
		fastEncodingButton.setText(fastEncodingText);
		fastEncodingButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, false, 2, 1));
		// Force update
		profileAsChanged();

		// Add listener
		getViewSite().getProfileContext().addProfileContextListener(
				profileContextListener);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getViewSite().getProfileContext().removeProfileContextListener(
						profileContextListener);
			}
		});

	}

	/**
	 * Update this view to reflect the profile modification.
	 */
	private void profileAsChanged() {

		// Get the Lame encoding options
		Profile selectedProfile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (selectedProfile instanceof HardCodedProfile) {
			return;
		}
		AudioEncodingOptions audioOptions = selectedProfile
				.getEncodingOptions().getAudioOptions();
		if (!(audioOptions instanceof LameEncodingOptions)) {
			return;
		}
		LameEncodingOptions lameOptions = (LameEncodingOptions) audioOptions;

		// Change bitrate editor
		if (lameOptions.getBitrate() != bitrateEditor.getSelection()) {
			bitrateEditor.setSelection(lameOptions.getBitrate());
		}

		// Type viewer
		Integer typeSelected = getTypeSelection();
		if (typeSelected == null
				|| typeSelected != lameOptions.getEncodingType()) {
			typeViewer.setSelection(new StructuredSelection(lameOptions
					.getEncodingType()));
		}

		// Channel viewer
		Integer channelSelected = getChannelSelection();
		if (channelSelected == null
				|| channelSelected != lameOptions.getChannelMode()) {
			channelViewer.setSelection(new StructuredSelection(lameOptions
					.getChannelMode()));
		}

		// Variable method viewer
		Integer variableMethodSelected = getVariableMethodSelection();
		if (variableMethodSelected == null
				|| variableMethodSelected != lameOptions.getVariableMethod()) {
			variableMethodViewer.setSelection(new StructuredSelection(
					lameOptions.getVariableMethod()));
		}

		// Sample rate viewer
		Integer sampleRateSelected = getSampleRateSelection();
		if (sampleRateSelected == null
				|| sampleRateSelected != lameOptions.getOutputSampleRate()) {
			sampleRateViewer.setSelection(new StructuredSelection(lameOptions
					.getOutputSampleRate()));
		}

		// Enable or disable
		int newType = lameOptions.getEncodingType();
		boolean bitrateType = newType == LameEncodingOptions.TYPE_AVERAGE_BITRATE
				|| newType == LameEncodingOptions.TYPE_CONSTANT_BITRATE;
		boolean variableType = newType == LameEncodingOptions.TYPE_VARIABLE_BITRATE;

		bitrateLabel.setVisible(bitrateType);
		bitrateEditor.setVisible(bitrateType);
		if (bitrateType) {
			bitrateLabel.setLayoutData(changeHeightLayout(bitrateLabel
					.getLayoutData(), SWT.DEFAULT));
			bitrateEditor.setLayoutData(changeHeightLayout(bitrateEditor
					.getLayoutData(), SWT.DEFAULT));
		} else {
			bitrateLabel.setLayoutData(changeHeightLayout(bitrateLabel
					.getLayoutData(), 0));
			bitrateEditor.setLayoutData(changeHeightLayout(bitrateEditor
					.getLayoutData(), 0));
		}

		qualityLabel.setVisible(variableType);
		qualityEditor.setVisible(variableType);
		if (variableType) {
			qualityLabel.setLayoutData(changeHeightLayout(qualityLabel
					.getLayoutData(), SWT.DEFAULT));
			qualityEditor.setLayoutData(changeHeightLayout(qualityEditor
					.getLayoutData(), SWT.DEFAULT));
		} else {
			qualityLabel.setLayoutData(changeHeightLayout(qualityLabel
					.getLayoutData(), 0));
			qualityEditor.setLayoutData(changeHeightLayout(qualityEditor
					.getLayoutData(), 0));
		}

		fastEncodingButton.setVisible(variableType);
		variableMethodLabel.setVisible(variableType);
		variableMethodViewer.getCombo().setVisible(variableType);

		bitrateLabel.getParent().layout();
	}

	/**
	 * Notify this class that user change the sample rate.
	 */
	private void sampleRateAsChanged() {
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
	 * Notify this class that user change the type.
	 */
	private void typeSelectionAsChanged() {
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
		LameEncodingOptions lameOptions = (LameEncodingOptions) audioOptions;

		// Check if value as changed
		Integer typeSelected = getTypeSelection();
		if (typeSelected != null
				&& typeSelected != lameOptions.getEncodingType()) {

			// Change profile value
			lameOptions.setEncodingType(typeSelected);
			options.setAudioOptions(lameOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}
	}

	/**
	 * Notify this class that user change the variable method
	 */
	private void variableMethodAsChanged() {
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
		LameEncodingOptions lameOptions = (LameEncodingOptions) audioOptions;

		// Check if value as changed
		Integer variableMethodSelected = getVariableMethodSelection();
		if (variableMethodSelected != null
				&& variableMethodSelected != lameOptions.getVariableMethod()) {

			// Change profile value
			lameOptions.setVariableMethode(variableMethodSelected);
			options.setAudioOptions(lameOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}
	}
}
