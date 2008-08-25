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
			public VideoOptionsInterface createInterface(Composite parent,
					int style) {
				return new X264OptionsComposite(parent, style);
			}

			public VideoEncodingOptions getEncodingOptions() {
				X264EncodingOptions encodingOptions = new X264EncodingOptions(
						BITRATE_DEFAULT);
				encodingOptions.enableThreads(true);
				return encodingOptions;
			}

			public VideoFormat getVideoFormat() {
				return VideoFormat.FORMAT_H264_AVC;
			}
		};
	}

	/**
	 * Button to enable/disable B-Frame usage.
	 */
	private Button bframeButton;
	/**
	 * Bitstream label provider.
	 */
	private LabelProvider bitStreamLevelLabelProvider = new LabelProvider() {
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
	private ComboViewer bitStreamLevelViewer;
	/**
	 * Cabac enable/disable button.
	 */
	private Button cabacButton;

	/**
	 * Modify listener.
	 */
	private ModifyListener modifyListener = new ModifyListener() {
		public void modifyText(ModifyEvent e) {
			if (e.widget == referenceFrameSpinner) {
				referenceFrameAsChanged();
			} else if (e.widget == subqSpinner) {
				subqSelectionAsChanged();
			}
		}
	};
	/**
	 * Motion estimation label provider.
	 */
	private LabelProvider motionEstimationLabelProvider = new LabelProvider() {
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
	private ComboViewer motionEstimationViewer;

	/**
	 * Parition enable/disable button.
	 */
	private Button partitionsButton;
	/**
	 * Listener to profile context.
	 */
	private IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			profileAsChanged();
		}
	};
	/**
	 * Reference frame spinner
	 */
	private Spinner referenceFrameSpinner;

	/**
	 * Selection listener.
	 */
	private ISelectionChangedListener selectionChangeListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == bitStreamLevelViewer) {
				bitStreamLevelSelectionAsChanged();
			} else if (event.getSource() == motionEstimationViewer) {
				motionEstimationSelectionAsChanged();
			}
		}
	};

	/**
	 * Selection listener.
	 */
	private SelectionListener selectionListener = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			if (e.widget == cabacButton) {
				cabacSelectionAsChanged();
			} else if (e.widget == trellisButton) {
				trellisSelectionAsChanged();
			} else if (e.widget == partitionsButton) {
				partitionsSelectionAsChanged();
			} else if (e.widget == twoPassButton) {
				twoPassSelectionAsChanged();
			} else if (e.widget == bframeButton) {
				bframeSelectionAsChanged();
			} else if (e.widget == threadButton) {
				threadSelectionAsChanged();
			}
		}
	};
	/**
	 * Subpel refinement quality viewer.
	 */
	private Spinner subqSpinner;
	/**
	 * Thread enabled/disable viewer
	 */
	private Button threadButton;
	/**
	 * Trellis enable/disable button.
	 */
	private Button trellisButton;
	/**
	 * Two pass quality button.
	 */
	private Button twoPassButton;

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
	private void bframeSelectionAsChanged() {
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
		boolean bframeSelected = bframeButton.getSelection();
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
	private void cabacSelectionAsChanged() {
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
		boolean cabacSelection = cabacButton.getSelection();
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
	private void bitStreamLevelSelectionAsChanged() {
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
	private String getBitStreamLevelSelection() {
		Object selection = ((IStructuredSelection) bitStreamLevelViewer
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
	private Integer getMotionEstimationSelection() {
		Object selection = ((IStructuredSelection) motionEstimationViewer
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

		bitStreamLevelViewer = new ComboViewer(this, SWT.READ_ONLY
				| SWT.DROP_DOWN);
		bitStreamLevelViewer.setLabelProvider(bitStreamLevelLabelProvider);
		bitStreamLevelViewer
				.addSelectionChangedListener(selectionChangeListener);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_10);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_11);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_12);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_13);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_1B);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_20);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_21);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_22);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_30);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_31);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_32);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_40);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_41);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_42);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_50);
		bitStreamLevelViewer.add(X264EncodingOptions.LEVEL_51);

		// Reference frame
		label = new Label(this, SWT.NONE);
		label.setText(referenceFrameText);
		// label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		referenceFrameSpinner = new Spinner(this, SWT.BORDER);
		referenceFrameSpinner
				.setMinimum(X264EncodingOptions.REFERENCE_FRAME_MIN_VALUE);
		referenceFrameSpinner
				.setMaximum(X264EncodingOptions.REFERENCE_FRAME_MAX_VALUE);
		referenceFrameSpinner.addModifyListener(modifyListener);

		// Motion estimation
		label = new Label(this, SWT.NONE);
		label.setText(motionEstimationText);
		// label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		motionEstimationViewer = new ComboViewer(this, SWT.READ_ONLY
				| SWT.DROP_DOWN);
		motionEstimationViewer.setLabelProvider(motionEstimationLabelProvider);
		motionEstimationViewer
				.addSelectionChangedListener(selectionChangeListener);
		motionEstimationViewer
				.add(X264EncodingOptions.MOTION_ESTIMATION_DIAMON);
		motionEstimationViewer
				.add(X264EncodingOptions.MOTION_ESTIMATION_HEXAGON);
		motionEstimationViewer
				.add(X264EncodingOptions.MOTION_ESTIMATION_UNEVEN);
		motionEstimationViewer
				.add(X264EncodingOptions.MOTION_ESTIMATION_EXHAUSTIVE);

		// Subpel refinement quality
		label = new Label(this, SWT.NONE);
		label.setText(subQText);
		// label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		subqSpinner = new Spinner(this, SWT.BORDER);
		subqSpinner.setMinimum(X264EncodingOptions.SUQ_MIN_VALUE);
		subqSpinner.setMaximum(X264EncodingOptions.SUQ_MAX_VALUE);
		subqSpinner.addModifyListener(modifyListener);

		Composite checkBoxGroup = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(2, true);
		layout.marginHeight=0;
		layout.marginWidth=0;
		checkBoxGroup.setLayout(layout);
		checkBoxGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true, true,4,1));
		
		// Two-pass
		twoPassButton = new Button(checkBoxGroup, SWT.CHECK);
		twoPassButton.setText(twoPassText);
		twoPassButton.addSelectionListener(selectionListener);

		// Cabac
		cabacButton = new Button(checkBoxGroup, SWT.CHECK);
		cabacButton.setText(cabacText);
		cabacButton.addSelectionListener(selectionListener);

		// Trellis
		String trellisText = Localization
				.getString(Localization.OPTIONS_X264_TRELLIS);
		trellisButton = new Button(checkBoxGroup, SWT.CHECK);
		trellisButton.setText(trellisText);
		trellisButton.addSelectionListener(selectionListener);

		// Partitions
		partitionsButton = new Button(checkBoxGroup, SWT.CHECK);
		partitionsButton.setText(partitionsText);
		partitionsButton.addSelectionListener(selectionListener);

		// B-Frame
		bframeButton = new Button(checkBoxGroup, SWT.CHECK);
		bframeButton.setText(bframeText);
		bframeButton.addSelectionListener(selectionListener);

		// Thread
		threadButton = new Button(checkBoxGroup, SWT.CHECK);
		threadButton.setText(threadText);
		threadButton.addSelectionListener(selectionListener);

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
	 * Notify this class that user change the selected motion estimation.
	 */
	private void motionEstimationSelectionAsChanged() {
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
	private void partitionsSelectionAsChanged() {
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
		boolean partitionsSelection = partitionsButton.getSelection();
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
	private void profileAsChanged() {

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
		if (cabacButton.getSelection() != x264Options.isCabacEnabled()) {
			cabacButton.setSelection(x264Options.isCabacEnabled());
		}

		// Change trellis
		if (trellisButton.getSelection() != x264Options.isTrellisEnabled()) {
			trellisButton.setSelection(x264Options.isTrellisEnabled());
		}

		// Change partitions
		if (partitionsButton.getSelection() != x264Options
				.isPartitionsEnabled()) {
			partitionsButton.setSelection(x264Options.isPartitionsEnabled());
		}

		// Change two-pass
		if (twoPassButton.getSelection() != (x264Options.getPass() == 2)) {
			twoPassButton.setSelection(x264Options.getPass() == 2);
		}

		// Change B-Frame
		if (bframeButton.getSelection() != (x264Options.getMaxBFrame() > 0)) {
			bframeButton.setSelection(x264Options.getMaxBFrame() > 0);
		}

		// Change level
		String levelSelected = getBitStreamLevelSelection();
		if (levelSelected == null
				|| !levelSelected.equals(x264Options.getBitStreamLevel())) {
			bitStreamLevelViewer.setSelection(new StructuredSelection(
					x264Options.getBitStreamLevel()));
		}

		// Change motion estimation
		Integer motionEstimationSelected = getMotionEstimationSelection();
		if (motionEstimationSelected == null
				|| !motionEstimationSelected.equals(x264Options
						.getMotionEstimation())) {
			motionEstimationViewer.setSelection(new StructuredSelection(
					x264Options.getMotionEstimation()));
		}

		// Change Reference frame
		Integer referenceFrameSelected = referenceFrameSpinner.getSelection();
		if (referenceFrameSelected == null
				|| !referenceFrameSelected.equals(x264Options
						.getReferenceFrame())) {
			referenceFrameSpinner.setSelection(x264Options.getReferenceFrame());
		}

		// Change SubQ
		Integer subqSelected = subqSpinner.getSelection();
		if (subqSelected == null
				|| !subqSelected.equals(x264Options.getSubpelRefinement())) {
			subqSpinner.setSelection(x264Options.getSubpelRefinement());
		}

		this.layout();
	}

	/**
	 * Notify this class that user change the reference frame.
	 */
	private void referenceFrameAsChanged() {
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
		int referenceFrameSelection = referenceFrameSpinner.getSelection();
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
	private void subqSelectionAsChanged() {
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
		Integer subqSelection = subqSpinner.getSelection();
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
	private void trellisSelectionAsChanged() {
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
		boolean trellisSelection = trellisButton.getSelection();
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
	private void twoPassSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();

		// Check if value as changed
		boolean twoPassSelection = twoPassButton.getSelection();
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
	private void threadSelectionAsChanged() {
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
		boolean threadSelection = threadButton.getSelection();
		if (threadSelection != x264Options.isThreadsEnabled()) {

			// Change profile value
			x264Options.enableThreads(threadSelection);
			options.setVideoOptions(x264Options);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}
}
