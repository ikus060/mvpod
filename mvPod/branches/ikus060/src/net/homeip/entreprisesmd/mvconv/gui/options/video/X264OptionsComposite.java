package net.homeip.entreprisesmd.mvconv.gui.options.video;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profiles;
import net.homeip.entreprisesmd.mvconv.gui.IProfileContextListener;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.VideoEncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.X264EncodingOptions;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.Spinner;

/**
 * This class offer to change the options for X264 codec.
 * 
 * @author patapouf
 * 
 */
public class X264OptionsComposite extends VideoOptionsInterface {
	/**
	 * Number of b-frame to use when enable.
	 */
	private static final int BFRAME_NUMBER = 4;
	/**
	 * Default bitrate value
	 */
	private static final int BITRATE_DEFAULT = 500;

	/**
	 * Return the mapper for this interface.
	 * 
	 * @return the mapper.
	 */
	public static VideoOptionsMapper getMapper() {
		return new VideoOptionsMapper() {
			X264EncodingOptions encodingOptions;

			public VideoOptionsInterface createInterface(Composite parent,
					int style) {
				return new X264OptionsComposite(parent, style);
			}

			public VideoEncodingOptions getEncodingOptions() {
				if (encodingOptions == null) {
					encodingOptions = new X264EncodingOptions(BITRATE_DEFAULT);
					encodingOptions.enableThreads(true);
				}
				return encodingOptions;
			}

			public VideoFormat getVideoFormat() {
				return VideoFormat.FORMAT_H264_AVC;
			}

			public boolean match(VideoEncodingOptions options) {
				if(options instanceof X264EncodingOptions)
					return true;
				return false;
			}

			public void setDefaultEncodingOptions(VideoEncodingOptions options) {
				if(options instanceof X264EncodingOptions) {
					encodingOptions = (X264EncodingOptions)options;
				}
			}
		};
	}

	/**
	 * Button to enable/disable B-Frame usage.
	 */
	Button bframeButton;
	/**
	 * Bitstream label provider.
	 */
	LabelProvider bitStreamLevelLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			String key = null;
			if (element.equals(X264EncodingOptions.LEVEL_10)) {
				key = Localization.OPTIONS_X264_LEVEL10;
			} else if (element.equals(X264EncodingOptions.LEVEL_11)) {
				key = Localization.OPTIONS_X264_LEVEL11;
			} else if (element.equals(X264EncodingOptions.LEVEL_12)) {
				key = Localization.OPTIONS_X264_LEVEL12;
			} else if (element.equals(X264EncodingOptions.LEVEL_13)) {
				key = Localization.OPTIONS_X264_LEVEL13;
			} else if (element.equals(X264EncodingOptions.LEVEL_1B)) {
				key = Localization.OPTIONS_X264_LEVEL1B;
			} else if (element.equals(X264EncodingOptions.LEVEL_20)) {
				key = Localization.OPTIONS_X264_LEVEL20;
			} else if (element.equals(X264EncodingOptions.LEVEL_21)) {
				key = Localization.OPTIONS_X264_LEVEL21;
			} else if (element.equals(X264EncodingOptions.LEVEL_22)) {
				key = Localization.OPTIONS_X264_LEVEL22;
			} else if (element.equals(X264EncodingOptions.LEVEL_30)) {
				key = Localization.OPTIONS_X264_LEVEL30;
			} else if (element.equals(X264EncodingOptions.LEVEL_31)) {
				key = Localization.OPTIONS_X264_LEVEL31;
			} else if (element.equals(X264EncodingOptions.LEVEL_32)) {
				key = Localization.OPTIONS_X264_LEVEL32;
			} else if (element.equals(X264EncodingOptions.LEVEL_40)) {
				key = Localization.OPTIONS_X264_LEVEL40;
			} else if (element.equals(X264EncodingOptions.LEVEL_41)) {
				key = Localization.OPTIONS_X264_LEVEL41;
			} else if (element.equals(X264EncodingOptions.LEVEL_42)) {
				key = Localization.OPTIONS_X264_LEVEL42;
			} else if (element.equals(X264EncodingOptions.LEVEL_50)) {
				key = Localization.OPTIONS_X264_LEVEL50;
			} else if (element.equals(X264EncodingOptions.LEVEL_51)) {
				key = Localization.OPTIONS_X264_LEVEL51;
			}
			if (key != null) {
				return Localization.getString(key);
			}
			return element.toString();
		}
	};
	/**
	 * Bit stream level viewer.
	 */
	ComboViewer bitStreamLevelViewer;
	/**
	 * Cabac enable/disable button.
	 */
	Button cabacButton;

	/**
	 * Modify listener.
	 */
	ModifyListener modifyListener = new ModifyListener() {
		public void modifyText(ModifyEvent e) {
			if (e.widget == X264OptionsComposite.this.referenceFrameSpinner) {
				referenceFrameAsChanged();
			} else if (e.widget == X264OptionsComposite.this.subqSpinner) {
				subqSelectionAsChanged();
			}
		}
	};
	/**
	 * Motion estimation label provider.
	 */
	LabelProvider motionEstimationLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			String key = null;
			if (element.equals(X264EncodingOptions.MOTION_ESTIMATION_DIAMON)) {
				key = Localization.OPTIONS_X264_MOTION_ESTIMATION_DIAMON;
			} else if (element
					.equals(X264EncodingOptions.MOTION_ESTIMATION_EXHAUSTIVE)) {
				key = Localization.OPTIONS_X264_MOTION_ESTIMATION_EXHAUSTIVE;
			} else if (element
					.equals(X264EncodingOptions.MOTION_ESTIMATION_HEXAGON)) {
				key = Localization.OPTIONS_X264_MOTION_ESTIMATION_HEXAGON;
			} else if (element
					.equals(X264EncodingOptions.MOTION_ESTIMATION_UNEVEN)) {
				key = Localization.OPTIONS_X264_MOTION_ESTIMATION_UNEVEN;
			}
			if (key != null) {
				return Localization.getString(key);
			}
			return element.toString();
		}
	};
	/**
	 * Motion estimation viewer.
	 */
	ComboViewer motionEstimationViewer;

	/**
	 * Parition enable/disable button.
	 */
	Button partitionsButton;
	/**
	 * Listener to profile context.
	 */
	IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			profileAsChanged();
		}
	};
	/**
	 * Reference frame spinner
	 */
	Spinner referenceFrameSpinner;

	/**
	 * Selection listener.
	 */
	ISelectionChangedListener selectionChangeListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == X264OptionsComposite.this.bitStreamLevelViewer) {
				bitStreamLevelSelectionAsChanged();
			} else if (event.getSource() == X264OptionsComposite.this.motionEstimationViewer) {
				motionEstimationSelectionAsChanged();
			}
		}
	};

	/**
	 * Selection listener.
	 */
	SelectionListener selectionListener = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			if (e.widget == X264OptionsComposite.this.cabacButton) {
				cabacSelectionAsChanged();
			} else if (e.widget == X264OptionsComposite.this.trellisButton) {
				trellisSelectionAsChanged();
			} else if (e.widget == X264OptionsComposite.this.partitionsButton) {
				partitionsSelectionAsChanged();
			} else if (e.widget == X264OptionsComposite.this.twoPassButton) {
				twoPassSelectionAsChanged();
			} else if (e.widget == X264OptionsComposite.this.bframeButton) {
				bframeSelectionAsChanged();
			} else if (e.widget == X264OptionsComposite.this.threadButton) {
				threadSelectionAsChanged();
			}
		}
	};
	/**
	 * Subpel refinement quality viewer.
	 */
	Spinner subqSpinner;
	/**
	 * Thread enabled/disable viewer
	 */
	Button threadButton;
	/**
	 * Trellis enable/disable button.
	 */
	Button trellisButton;
	/**
	 * Two pass quality button.
	 */
	Button twoPassButton;

	/**
	 * Create a new lame composite interface.
	 * 
	 * @param parent
	 *            the parent of this interface.
	 * @param style
	 *            the style.
	 */
	public X264OptionsComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Notify this view that user change the selection of b-frame.
	 */
	void bframeSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof X264EncodingOptions)) {
			return;
		}
		X264EncodingOptions x264Options = (X264EncodingOptions) videoOptions;

		// Check if value as changed
		boolean bframeSelected = this.bframeButton.getSelection();
		if (bframeSelected != (x264Options.getMaxBFrame() > 0)) {

			// Change profile value
			x264Options.setMaxBFrame(bframeSelected ? BFRAME_NUMBER : 0);
			options.setVideoOptions(x264Options);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Notify this class that user change the selected CABAC mode.
	 */
	void cabacSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof X264EncodingOptions)) {
			return;
		}
		X264EncodingOptions x264Options = (X264EncodingOptions) videoOptions;

		// Check if value as changed
		boolean cabacSelection = this.cabacButton.getSelection();
		if (cabacSelection != x264Options.isCabacEnabled()) {

			// Change profile value
			x264Options.enableCabac(cabacSelection);
			options.setVideoOptions(x264Options);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Notify this class that user change the selected trellis mode.
	 */
	void bitStreamLevelSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof X264EncodingOptions)) {
			return;
		}
		X264EncodingOptions x264Options = (X264EncodingOptions) videoOptions;

		// Check if value as changed
		String levelSelected = getBitStreamLevelSelection();
		if (levelSelected != x264Options.getBitStreamLevel()) {

			// Change profile value
			x264Options.setBitStreamLevel(levelSelected);
			options.setVideoOptions(x264Options);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Return the selected bit steram level.
	 * 
	 * @return the selection.
	 */
	String getBitStreamLevelSelection() {
		Object selection = ((IStructuredSelection) this.bitStreamLevelViewer
				.getSelection()).getFirstElement();
		if (!(selection instanceof String)) {
			return null;
		}
		return (String) selection;
	}

	/**
	 * Return the selected motion estimation.
	 * 
	 * @return the motion estimation.
	 */
	Integer getMotionEstimationSelection() {
		Object selection = ((IStructuredSelection) this.motionEstimationViewer
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
		String cabacText = Localization
				.getString(Localization.OPTIONS_X264_CABAC);
		String partitionsText = Localization
				.getString(Localization.OPTIONS_X264_PARTITIONS);
		String bitStreamLevelText = Localization
				.getString(Localization.OPTIONS_X264_LEVEL);
		String motionEstimationText = Localization
				.getString(Localization.OPTIONS_X264_MOTION_ESTIMATION);
		String referenceFrameText = Localization
				.getString(Localization.OPTIONS_X264_REFERENCE_FRAME);
		String subQText = Localization
				.getString(Localization.OPTIONS_X264_SUBPEL_REFINEMENT_QUALITY);
		String twoPassText = Localization
				.getString(Localization.OPTIONS_TWO_PASS);
		String bframeText = Localization
				.getString(Localization.OPTIONS_X264_BFRAME);
		String threadText = Localization
				.getString(Localization.OPTIONS_X264_THREAD);

		// Check the first column size.
		GC gc = new GC(this);
		Point bitStreamLevelTextSize = gc.textExtent(bitStreamLevelText);
		Point motionEstimationTextSize = gc.textExtent(motionEstimationText);
		Point referenceFrameTextSize = gc.textExtent(referenceFrameText);
		gc.dispose();
		int firstColumnWidth = Math.max(bitStreamLevelTextSize.x, 0);
		firstColumnWidth = Math.max(motionEstimationTextSize.x,
				firstColumnWidth);
		firstColumnWidth = Math.max(referenceFrameTextSize.x, firstColumnWidth);
		firstColumnWidth = SWT.DEFAULT;

		// Bit stream level
		Label label = new Label(this, SWT.NONE);
		label.setText(bitStreamLevelText);
		// label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.bitStreamLevelViewer = new ComboViewer(this, SWT.READ_ONLY
				| SWT.DROP_DOWN);
		this.bitStreamLevelViewer.setLabelProvider(this.bitStreamLevelLabelProvider);
		this.bitStreamLevelViewer
				.addSelectionChangedListener(this.selectionChangeListener);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_10);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_11);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_12);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_13);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_1B);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_20);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_21);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_22);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_30);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_31);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_32);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_40);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_41);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_42);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_50);
		this.bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_51);

		// Reference frame
		label = new Label(this, SWT.NONE);
		label.setText(referenceFrameText);
		// label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.referenceFrameSpinner = new Spinner(this, SWT.BORDER);
		this.referenceFrameSpinner
				.setMinimum(X264EncodingOptions.REFERENCE_FRAME_MIN_VALUE);
		this.referenceFrameSpinner
				.setMaximum(X264EncodingOptions.REFERENCE_FRAME_MAX_VALUE);
		this.referenceFrameSpinner.addModifyListener(this.modifyListener);

		// Motion estimation
		label = new Label(this, SWT.NONE);
		label.setText(motionEstimationText);
		// label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.motionEstimationViewer = new ComboViewer(this, SWT.READ_ONLY
				| SWT.DROP_DOWN);
		this.motionEstimationViewer.setLabelProvider(this.motionEstimationLabelProvider);
		this.motionEstimationViewer
				.addSelectionChangedListener(this.selectionChangeListener);
		this.motionEstimationViewer
				.add(X264EncodingOptions.MOTION_ESTIMATION_DIAMON);
		this.motionEstimationViewer
				.add(X264EncodingOptions.MOTION_ESTIMATION_HEXAGON);
		this.motionEstimationViewer
				.add(X264EncodingOptions.MOTION_ESTIMATION_UNEVEN);
		this.motionEstimationViewer
				.add(X264EncodingOptions.MOTION_ESTIMATION_EXHAUSTIVE);

		// Subpel refinement quality
		label = new Label(this, SWT.NONE);
		label.setText(subQText);
		// label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.subqSpinner = new Spinner(this, SWT.BORDER);
		this.subqSpinner.setMinimum(X264EncodingOptions.SUQ_MIN_VALUE);
		this.subqSpinner.setMaximum(X264EncodingOptions.SUQ_MAX_VALUE);
		this.subqSpinner.addModifyListener(this.modifyListener);

		Composite checkBoxGroup = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(2, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		checkBoxGroup.setLayout(layout);
		checkBoxGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 4, 1));

		// Two-pass
		this.twoPassButton = new Button(checkBoxGroup, SWT.CHECK);
		this.twoPassButton.setText(twoPassText);
		this.twoPassButton.addSelectionListener(this.selectionListener);

		// Cabac
		this.cabacButton = new Button(checkBoxGroup, SWT.CHECK);
		this.cabacButton.setText(cabacText);
		this.cabacButton.addSelectionListener(this.selectionListener);

		// Trellis
		String trellisText = Localization
				.getString(Localization.OPTIONS_X264_TRELLIS);
		this.trellisButton = new Button(checkBoxGroup, SWT.CHECK);
		this.trellisButton.setText(trellisText);
		this.trellisButton.addSelectionListener(this.selectionListener);

		// Partitions
		this.partitionsButton = new Button(checkBoxGroup, SWT.CHECK);
		this.partitionsButton.setText(partitionsText);
		this.partitionsButton.addSelectionListener(this.selectionListener);

		// B-Frame
		this.bframeButton = new Button(checkBoxGroup, SWT.CHECK);
		this.bframeButton.setText(bframeText);
		this.bframeButton.addSelectionListener(this.selectionListener);

		// Thread
		this.threadButton = new Button(checkBoxGroup, SWT.CHECK);
		this.threadButton.setText(threadText);
		this.threadButton.addSelectionListener(this.selectionListener);

		// Force update
		profileAsChanged();

		// Add listener
		getViewSite().getProfileContext().addProfileContextListener(
				this.profileContextListener);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getViewSite().getProfileContext().removeProfileContextListener(
						X264OptionsComposite.this.profileContextListener);
			}
		});

	}

	/**
	 * Notify this class that user change the selected motion estimation.
	 */
	void motionEstimationSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof X264EncodingOptions)) {
			return;
		}
		X264EncodingOptions x264Options = (X264EncodingOptions) videoOptions;

		// Check if value as changed
		Integer motionEstimationSelection = getMotionEstimationSelection();
		if (motionEstimationSelection != x264Options.getMotionEstimation()) {

			// Change profile value
			x264Options.setMotionEstimation(motionEstimationSelection);
			options.setVideoOptions(x264Options);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Notify this class that user change the selected trellis mode.
	 */
	void partitionsSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof X264EncodingOptions)) {
			return;
		}
		X264EncodingOptions x264Options = (X264EncodingOptions) videoOptions;

		// Check if value as changed
		boolean partitionsSelection = this.partitionsButton.getSelection();
		if (partitionsSelection != x264Options.isPartitionsEnabled()) {

			// Change profile value
			x264Options.enablePartition(partitionsSelection);
			options.setVideoOptions(x264Options);
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
		VideoEncodingOptions videoOptions = selectedProfile
				.getEncodingOptions().getVideoOptions();
		if (!(videoOptions instanceof X264EncodingOptions)) {
			return;
		}
		X264EncodingOptions x264Options = (X264EncodingOptions) videoOptions;

		// Change cabac
		if (this.cabacButton.getSelection() != x264Options.isCabacEnabled()) {
			this.cabacButton.setSelection(x264Options.isCabacEnabled());
		}

		// Change trellis
		if (this.trellisButton.getSelection() != x264Options.isTrellisEnabled()) {
			this.trellisButton.setSelection(x264Options.isTrellisEnabled());
		}

		// Change partitions
		if (this.partitionsButton.getSelection() != x264Options
				.isPartitionsEnabled()) {
			this.partitionsButton.setSelection(x264Options.isPartitionsEnabled());
		}

		// Change two-pass
		if (this.twoPassButton.getSelection() != (x264Options.getPass() == 2)) {
			this.twoPassButton.setSelection(x264Options.getPass() == 2);
		}

		// Change B-Frame
		if (this.bframeButton.getSelection() != (x264Options.getMaxBFrame() > 0)) {
			this.bframeButton.setSelection(x264Options.getMaxBFrame() > 0);
		}

		// Change level
		String levelSelected = getBitStreamLevelSelection();
		if (levelSelected == null
				|| !levelSelected.equals(x264Options.getBitStreamLevel())) {
			this.bitStreamLevelViewer.setSelection(new StructuredSelection(
					x264Options.getBitStreamLevel()));
		}

		// Change motion estimation
		Integer motionEstimationSelected = getMotionEstimationSelection();
		if (motionEstimationSelected == null
				|| !motionEstimationSelected.equals(x264Options
						.getMotionEstimation())) {
			this.motionEstimationViewer.setSelection(new StructuredSelection(
					x264Options.getMotionEstimation()));
		}

		// Change Reference frame
		Integer referenceFrameSelected = this.referenceFrameSpinner.getSelection();
		if (referenceFrameSelected == null
				|| !referenceFrameSelected.equals(x264Options
						.getReferenceFrame())) {
			this.referenceFrameSpinner.setSelection(x264Options.getReferenceFrame());
		}

		// Change SubQ
		Integer subqSelected = this.subqSpinner.getSelection();
		if (subqSelected == null
				|| !subqSelected.equals(x264Options.getSubpelRefinement())) {
			this.subqSpinner.setSelection(x264Options.getSubpelRefinement());
		}

		this.layout();
	}

	/**
	 * Notify this class that user change the reference frame.
	 */
	void referenceFrameAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof X264EncodingOptions)) {
			return;
		}
		X264EncodingOptions x264Options = (X264EncodingOptions) videoOptions;

		// Check if value as changed
		int referenceFrameSelection = this.referenceFrameSpinner.getSelection();
		if (referenceFrameSelection != x264Options.getReferenceFrame()) {

			// Change profile value
			x264Options.setReferenceFrame(referenceFrameSelection);
			options.setVideoOptions(x264Options);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Notify this class that user change the subq.
	 */
	void subqSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof X264EncodingOptions)) {
			return;
		}
		X264EncodingOptions x264Options = (X264EncodingOptions) videoOptions;

		// Check if value as changed
		Integer subqSelection = this.subqSpinner.getSelection();
		if (subqSelection != x264Options.getSubpelRefinement()) {

			// Change profile value
			x264Options.setSubpelRefinement(subqSelection);
			options.setVideoOptions(x264Options);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Notify this class that user change the selected trellis mode.
	 */
	void trellisSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof X264EncodingOptions)) {
			return;
		}
		X264EncodingOptions x264Options = (X264EncodingOptions) videoOptions;

		// Check if value as changed
		boolean trellisSelection = this.trellisButton.getSelection();
		if (trellisSelection != x264Options.isTrellisEnabled()) {

			// Change profile value
			x264Options.enableTrellis(trellisSelection);
			options.setVideoOptions(x264Options);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Notify this class that user change the selected trellis mode.
	 */
	void twoPassSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();

		// Check if value as changed
		boolean twoPassSelection = this.twoPassButton.getSelection();
		if (twoPassSelection != (videoOptions.getPass() == 2)) {

			// Change profile value
			videoOptions.setPass(twoPassSelection ? 2 : 1);
			options.setVideoOptions(videoOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Notify this class that user change the selected trellis mode.
	 */
	void threadSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof X264EncodingOptions)) {
			return;
		}
		X264EncodingOptions x264Options = (X264EncodingOptions) videoOptions;

		// Check if value as changed
		boolean threadSelection = this.threadButton.getSelection();
		if (threadSelection != x264Options.isThreadsEnabled()) {

			// Change profile value
			x264Options.enableThreads(threadSelection);
			options.setVideoOptions(x264Options);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}
}
