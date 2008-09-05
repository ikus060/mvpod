package net.homeip.entreprisesmd.mvconv.gui.options;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profiles;
import net.homeip.entreprisesmd.mvconv.gui.ComboCustomViewer;
import net.homeip.entreprisesmd.mvconv.gui.IProfileContextListener;
import net.homeip.entreprisesmd.mvconv.gui.IViewPart;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.VideoEncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.X264EncodingOptions;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * This class display audio options to user and give him the chance to change
 * the audio codec.
 * 
 * @author patapouf
 * 
 */
public class GenericVideoOptionsComposite extends Composite implements
		IViewPart {

	/**
	 * Custom item.
	 */
	private static final String CUSTOM_ITEM = "Custom"; //$NON-NLS-1$

	/**
	 * Increment video bitrate value.
	 */
	private static final int INCREMENT_VIDEO_BITRATE = 2;

	/**
	 * Bitrate spinner.
	 */
	SpinnerEditor bitrateSpinner;
	/**
	 * Frame rate viewer
	 */
	ComboCustomViewer frameRateEditor;
	/**
	 * Scaling method editor.
	 */
	ComboViewer scalingMethodCombo;

	/**
	 * Video width text editor
	 */
	Text widthText;
	/**
	 * Video height editor.
	 */
	Text heightText;

	/**
	 * Frame rate label provider.
	 */
	private LabelProvider frameRateLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			String key = null;
			if (element.equals(VideoEncodingOptions.NTSC_FILM_FRAME_RATE)) {
				key = Localization.OPTIONS_NTSC_FILM;
			} else if (element.equals(VideoEncodingOptions.NTSC_FRAME_RATE)) {
				key = Localization.OPTIONS_NTSC;
			} else if (element.equals(VideoEncodingOptions.PAL_FRAME_RATE)) {
				key = Localization.OPTIONS_PAL;
			} else if (element.equals(-1.0)) {
				return Localization
						.getString(Localization.OPTIONS_FRAME_RATE_NO_CHANGE);
			} else if (element.equals(CUSTOM_ITEM)) {
				return Localization
						.getString(Localization.OPTIONS_FRAME_RATE_CUSTOM);
			}
			if (key != null) {
				return element.toString() + " " + Localization.getString(key); //$NON-NLS-1$
			}
			return element.toString();
		}
	};
	/**
	 * Scaling methode label provider.
	 */
	private LabelProvider scalingMethodLabelProvider = new LabelProvider() {
		public String getText(Object element) {

			int methode = 0;
			if (element instanceof Integer) {
				methode = ((Integer) element).intValue();
			}

			String key = null;
			if ((methode & VideoScalingOptions.METHOD_CROP) != 0) {
				key = Localization.SCALING_METHOD_CROP;
			} else if ((methode & VideoScalingOptions.METHOD_FILL) != 0) {
				key = Localization.SCALING_METHOD_FILL;
			} else if ((methode & VideoScalingOptions.METHOD_SCALE) != 0) {
				key = Localization.SCALING_METHOD_SCALE;
			} else if ((methode & VideoScalingOptions.METHOD_FIT) != 0) {
				key = Localization.SCALING_METHOD_FIT;
			}
			if (key != null) {
				return Localization.getString(key);
			}
			return element.toString();
		}
	};
	/**
	 * Listener to profile context.
	 */
	IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			profileAsChanged();
		}
	};
	/**
	 * Selection listener.
	 */
	private ISelectionChangedListener selectionChangeListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == GenericVideoOptionsComposite.this.frameRateEditor) {
				frameRateSelectionAsChanged();
			} else if (event.getSource() == GenericVideoOptionsComposite.this.scalingMethodCombo) {
				scalingMethodAsChanged();
			}
		}
	};
	/**
	 * Selection listener.
	 */
	private Listener listener = new Listener() {
		public void handleEvent(Event e) {
			if (e.widget == GenericVideoOptionsComposite.this.bitrateSpinner) {
				bitrateSelectionAsChanged();
			} else if (e.widget == GenericVideoOptionsComposite.this.widthText) {
				dimensionAsChanged();
			} else if (e.widget == GenericVideoOptionsComposite.this.heightText) {
				dimensionAsChanged();
			}
		}
	};
	/**
	 * Numeric Verify listener.
	 */
	private VerifyListener verifyListener = new VerifyListener() {
		public void verifyText(VerifyEvent event) {
			event.doit = true;
			if (event.text.length() > 0) {
				try {
					Integer.parseInt(event.text);
				} catch (NumberFormatException e) {
					event.doit = false;
				}
			}
		}
	};

	/**
	 * View site.
	 */
	private IViewSite site;

	/**
	 * Create a new composite interface to select audio options for audio
	 * encoding.
	 * 
	 * @param parent
	 *            the parent of this composite interface.
	 * @param style
	 *            the style
	 */
	public GenericVideoOptionsComposite(Composite parent, int style) {
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
		int videoBitrate = this.bitrateSpinner.getSelection();
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();

		if (videoBitrate != videoOptions.getBitrate()) {

			// Change profile value
			videoOptions.setBitrate(videoBitrate);
			options.setVideoOptions(videoOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}

	}

	/**
	 * Notify this view that user change the video dimension value.
	 */
	void dimensionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Check if value as changed
		Integer width = getVideoWidth();
		Integer height = getVideoHeight();
		EncodingOptions options = profile.getEncodingOptions();
		VideoScalingOptions scalingOptions = options.getScaleOptions();

		if (width != null
				&& height != null
				&& (width.intValue() != scalingOptions.getWidth() || height
						.intValue() != scalingOptions.getHeight())) {

			// Change profile value
			scalingOptions = new VideoScalingOptions(width, height,
					scalingOptions.getScalingMethod());
			options.setScaleOptions(scalingOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}
	}

	/**
	 * Notify this class that user select an new frame rate.
	 */
	void frameRateSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Check if value as changed
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();

		// Get Selection
		Double frameRate = null;
		Object selection = ((IStructuredSelection) this.frameRateEditor
				.getSelection()).getFirstElement();
		if (CUSTOM_ITEM.equals(selection)) {
			// Ask new value to user
			Shell parentShell = getShell();
			String dialogTitle = Localization
					.getString(Localization.APPLICATION_NAME);
			String dialogMessage = Localization
					.getString(Localization.OPTIONS_FRAME_RATE_CUSTOM_MESSAGE);
			double initialValue = videoOptions.getOutputFrameRate();
			String unit = Localization.getString(Localization.OPTIONS_FPS);

			InputSpinnerDialog dlg = new InputSpinnerDialog(parentShell,
					dialogTitle, dialogMessage, initialValue, null);
			dlg.setDigits(3);
			dlg.setIncrement(1);
			dlg.setMinimum(VideoEncodingOptions.FRAME_RATE_MIN_VALUE);
			dlg.setMaximum(VideoEncodingOptions.FRAME_RATE_MAX_VALUE);
			dlg.setUnitString(unit);

			int id = dlg.open();
			if (id == IDialogConstants.OK_ID) {
				frameRate = dlg.getValue();
			}

		} else {
			frameRate = getFrameRateSelection();
		}

		if (frameRate != null && frameRate != videoOptions.getOutputFrameRate()) {

			// Change profile value
			videoOptions.setOutputFrameRate(frameRate);
			options.setVideoOptions(videoOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}
	}

	/**
	 * Notify this class that user as change the scaling method.
	 */
	void scalingMethodAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Check if value as changed
		Integer method = getScalingMethod();
		EncodingOptions options = profile.getEncodingOptions();
		VideoScalingOptions scalingOptions = options.getScaleOptions();

		if (method != null
				&& method.intValue() != scalingOptions.getScalingMethod()) {

			// Change profile value
			scalingOptions = new VideoScalingOptions(scalingOptions.getWidth(),
					scalingOptions.getHeight(), method);
			options.setScaleOptions(scalingOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);

		}
	}

	/**
	 * Return the frame rate selection.
	 * 
	 * @return the frame rate selection.
	 */
	Double getFrameRateSelection() {
		Object selection = ((IStructuredSelection) this.frameRateEditor
				.getSelection()).getFirstElement();
		if (!(selection instanceof Double)) {
			return null;
		}
		return (Double) selection;
	}

	/**
	 * Return the scaling method selected by user.
	 * 
	 * @return the scaling method.
	 */
	Integer getScalingMethod() {
		Object selection = ((IStructuredSelection) this.scalingMethodCombo
				.getSelection()).getFirstElement();
		if (!(selection instanceof Integer)) {
			return null;
		}
		return (Integer) selection;
	}

	/**
	 * Return the width selection.
	 * 
	 * @return the width selection.
	 */
	Integer getVideoHeight() {
		try {
			return Integer.parseInt(this.heightText.getText());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Return the width selection.
	 * 
	 * @return the width selection.
	 */
	Integer getVideoWidth() {
		try {
			return Integer.parseInt(this.widthText.getText());
		} catch (NumberFormatException e) {
			return null;
		}
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
	 * Initialize this view with the given view site.
	 * 
	 * @param site
	 *            the view site.this.setLayout(new GridLayout(3, false));
	 */
	public void init(IViewSite site) {

		this.site = site;

		this.setLayout(new GridLayout(4, false));

		// Prepare localized string
		String bitrateText = Localization
				.getString(Localization.OPTIONS_BITRATE);
		String bitrateUnit = Localization
				.getString(Localization.OPTIONS_BITRATE_UNIT);
		String frameRateText = Localization
				.getString(Localization.OPTIONS_FRAME_RATE);
		String videoDimension = Localization
				.getString(Localization.OPTIONS_DIMENSION);
		String videoScalingMethode = Localization
				.getString(Localization.OPTIONS_VIDEO_SCALING_METHODE);

		// Check the first column size.
		GC gc = new GC(this);
		Point bitrateTextSize = gc.textExtent(bitrateText);
		gc.dispose();
		int firstColumnWidth = Math.max(bitrateTextSize.x, 0);
		firstColumnWidth = SWT.DEFAULT;

		// Bitrate
		Label label = new Label(this, SWT.NONE);
		label.setText(bitrateText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.bitrateSpinner = new SpinnerEditor(this, SWT.BORDER);
		this.bitrateSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false));
		this.bitrateSpinner.setUnitString(bitrateUnit);
		this.bitrateSpinner.setIncrement(INCREMENT_VIDEO_BITRATE);
		this.bitrateSpinner.setMinimum(X264EncodingOptions.BITRATE_MIN_VALUE);
		this.bitrateSpinner.setMaximum(X264EncodingOptions.BITRATE_MAX_VALUE);
		this.bitrateSpinner.addListener(SWT.Selection, this.listener);

		// Frame rate
		label = new Label(this, SWT.NONE);
		label.setText(frameRateText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.frameRateEditor = new ComboCustomViewer(this);
		this.frameRateEditor.addSelectionChangedListener(this.selectionChangeListener);
		this.frameRateEditor.setLabelProvider(this.frameRateLabelProvider);
		this.frameRateEditor.add(CUSTOM_ITEM);
		this.frameRateEditor.add(-1.0);
		this.frameRateEditor.add(VideoEncodingOptions.NTSC_FRAME_RATE);
		this.frameRateEditor.add(VideoEncodingOptions.NTSC_FILM_FRAME_RATE);
		this.frameRateEditor.add(VideoEncodingOptions.PAL_FRAME_RATE);

		// Video dimension
		label = new Label(this, SWT.NONE);
		label.setText(videoDimension);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		Composite videoDimComp = new Composite(this, SWT.BORDER);
		GridLayout layout = new GridLayout(3, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		videoDimComp.setLayout(layout);
		videoDimComp
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		this.widthText = new Text(videoDimComp, SWT.BORDER);
		this.widthText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.widthText.addVerifyListener(this.verifyListener);
		this.widthText.addListener(SWT.FocusOut, this.listener);

		label = new Label(videoDimComp, SWT.NONE);
		label.setText("x"); //$NON-NLS-1$

		this.heightText = new Text(videoDimComp, SWT.BORDER);
		this.heightText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.heightText.addVerifyListener(this.verifyListener);
		this.heightText.addListener(SWT.FocusOut, this.listener);

		label = new Label(this, SWT.NONE);
		label = new Label(this, SWT.NONE);

		// Video scaling methode
		label = new Label(this, SWT.NONE);
		label.setText(videoScalingMethode);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.scalingMethodCombo = new ComboViewer(this, SWT.READ_ONLY);
		this.scalingMethodCombo.addSelectionChangedListener(this.selectionChangeListener);
		this.scalingMethodCombo.setLabelProvider(this.scalingMethodLabelProvider);
		this.scalingMethodCombo.add(VideoScalingOptions.METHOD_CROP);
		this.scalingMethodCombo.add(VideoScalingOptions.METHOD_FILL);
		this.scalingMethodCombo.add(VideoScalingOptions.METHOD_SCALE);
		this.scalingMethodCombo.add(VideoScalingOptions.METHOD_FIT);

		// Force update
		profileAsChanged();

		// Add listener
		getViewSite().getProfileContext().addProfileContextListener(
				this.profileContextListener);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getViewSite().getProfileContext().removeProfileContextListener(
						GenericVideoOptionsComposite.this.profileContextListener);
			}
		});

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
		VideoEncodingOptions videoOptions = selectedProfile
				.getEncodingOptions().getVideoOptions();
		VideoScalingOptions scaleOptions = selectedProfile.getEncodingOptions()
				.getScaleOptions();

		// Change bitrate editor
		if (videoOptions.getBitrate() != this.bitrateSpinner.getSelection()) {
			this.bitrateSpinner.setSelection(videoOptions.getBitrate());
		}

		// Change frame rate
		Double frameRate = getFrameRateSelection();
		if (frameRate == null || frameRate != videoOptions.getOutputFrameRate()) {
			this.frameRateEditor.setSelection(new StructuredSelection(videoOptions
					.getOutputFrameRate()));
		}

		// Change video dimension
		Integer width = getVideoWidth();
		if (width == null || width.intValue() != scaleOptions.getWidth()) {
			this.widthText.setText(Integer.toString(scaleOptions.getWidth()));
		}

		// Change video dimension
		Integer height = getVideoHeight();
		if (width == null || height.intValue() != scaleOptions.getWidth()) {
			this.heightText.setText(Integer.toString(scaleOptions.getHeight()));
		}

		// Change scaling method
		Integer methode = getScalingMethod();
		if (methode == null
				|| methode.intValue() != scaleOptions.getScalingMethod()) {
			this.scalingMethodCombo.setSelection(new StructuredSelection(
					scaleOptions.getScalingMethod()));
		}

	}

}
