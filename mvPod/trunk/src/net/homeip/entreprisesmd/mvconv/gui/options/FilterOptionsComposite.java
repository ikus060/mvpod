package net.homeip.entreprisesmd.mvconv.gui.options;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profile;
import net.homeip.entreprisesmd.mvconv.gui.IProfileContextListener;
import net.homeip.entreprisesmd.mvconv.gui.IViewPart;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class FilterOptionsComposite extends Composite implements
		IViewPart {

	/**
	 * Minimum video bitrate value.
	 */
	private static final int MINIMUM_VIDEO_BITRATE = 100;

	/**
	 * Minimum audio bitrate value.
	 */
	private static final int MINIMUM_AUDIO_BITRATE = 32;
	/**
	 * Increment video bitrate value.
	 */
	private static final int INCREMENT_VIDEO_BITRATE = 2;
	/**
	 * Increment audio bitrate value.
	 */
	private static final int INCREMENT_AUDIO_BITRATE = 32;

	/**
	 * Font with bold style.
	 */
	private Font boldFont;

	/**
	 * Audio bitrate editor.
	 */
	private ScaleEditor audioBitrateEditor;

	/**
	 * Video bitrate ditor.
	 */
	private ScaleEditor videoBitrateEditor;

	/**
	 * Video dimention viewer.
	 */
	private ComboViewer videoDimentionViewer;

	/**
	 * Listener to the profile context.
	 */
	IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			profileAsChanged();
		}
	};

	/**
	 * Selection listener to bitrate editor.
	 */
	private MouseListener mouseListener = new MouseAdapter() {
		public void mouseUp(MouseEvent e) {
			bitrateSelectionAsChanged();
		}
	};

	/**
	 * Selection listener to video dimension viewer.
	 */
	private ISelectionChangedListener selectionListenerToVideoDimension = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			videoDimentsionSelectionAsChanged();
		}
	};
	/**
	 * Label provider for video dimension viewer.
	 */
	private LabelProvider labelProvider = new LabelProvider() {
		public String getText(Object element) {
			if (element instanceof VideoScalingOptions) {
				return ((VideoScalingOptions) element).getWidth() + " x " //$NON-NLS-1$
						+ ((VideoScalingOptions) element).getHeight();
			}
			return element.toString();
		}
	};

	/**
	 * View site.
	 */
	private IViewSite site;

	private VideoScalingOptions[] lastScalingOptions;

	/**
	 * Create a new hard codec profile option composite interface to edit
	 * options of the profile.
	 * 
	 * @param parent
	 *            the parent of this composite interface.
	 * @param style
	 *            the style
	 */
	public FilterOptionsComposite(Composite parent, int style) {
		super(parent, style);
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
	 *            the view site.
	 */
	public void init(IViewSite site) {

		this.site = site;

		// Setup font
		FontData[] boldedFontData = getFont().getFontData();
		for (int fontIndex = 0; fontIndex < boldedFontData.length; fontIndex++) {
			boldedFontData[fontIndex].setStyle(SWT.BOLD);
		}
		this.boldFont = new Font(Display.getCurrent(), boldedFontData);

		// Set layout
		this.setLayout(new GridLayout(1, false));

		// Prepare localized string
		String bitrateText = Localization
				.getString(Localization.OPTIONS_BITRATE);
		String dimensionText = Localization
				.getString(Localization.OPTIONS_DIMENSION);
		String bitrateValueFormat = Localization
				.getString(Localization.OPTIONS_BITRATE_FORMAT_VALUE);

		// Check the first column size.
		GC gc = new GC(this);
		Point bitrateTextSize = gc.textExtent(bitrateText);
		Point dimensionTextSize = gc.textExtent(dimensionText);
		gc.dispose();
		int firstColumnWidth = Math.max(bitrateTextSize.x, dimensionTextSize.x);

		// Video options
		Group videoGroup = new Group(this, SWT.NONE);
		videoGroup.setFont(this.boldFont);
		videoGroup.setText(Localization.getString(Localization.OPTIONS_VIDEO));
		videoGroup.setLayout(new GridLayout(2, false));
		videoGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label label = new Label(videoGroup, SWT.NONE);
		label.setText(bitrateText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.videoBitrateEditor = new ScaleEditor(videoGroup, SWT.NONE);
		this.videoBitrateEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		this.videoBitrateEditor.setFormatValue(bitrateValueFormat);
		this.videoBitrateEditor.setIncrement(INCREMENT_VIDEO_BITRATE);
		this.videoBitrateEditor.addMouseListener(this.mouseListener);

		label = new Label(videoGroup, SWT.NONE);
		label.setText(dimensionText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.videoDimentionViewer = new ComboViewer(videoGroup, SWT.READ_ONLY
				| SWT.DROP_DOWN);
		this.videoDimentionViewer
				.addSelectionChangedListener(this.selectionListenerToVideoDimension);
		this.videoDimentionViewer.setLabelProvider(this.labelProvider);

		// Audio options
		Group audioGroup = new Group(this, SWT.NONE);
		audioGroup.setFont(this.boldFont);
		audioGroup.setText(Localization.getString(Localization.OPTIONS_AUDIO));
		audioGroup.setLayout(new GridLayout(2, false));
		audioGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		label = new Label(audioGroup, SWT.NONE);
		label.setText(bitrateText);
		label.setLayoutData(new GridData(firstColumnWidth, SWT.DEFAULT));

		this.audioBitrateEditor = new ScaleEditor(audioGroup, SWT.NONE);
		this.audioBitrateEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		this.audioBitrateEditor.setFormatValue(bitrateValueFormat);
		this.audioBitrateEditor.setIncrement(INCREMENT_AUDIO_BITRATE);
		this.audioBitrateEditor.addMouseListener(this.mouseListener);
		

		profileAsChanged();

		// Attach listener
		getViewSite().getProfileContext().addProfileContextListener(
				this.profileContextListener);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getViewSite().getProfileContext().removeProfileContextListener(
						FilterOptionsComposite.this.profileContextListener);
			}
		});

	}

	/**
	 * Notify this view that user change the slected bitrate value.
	 */
	void bitrateSelectionAsChanged() {

		Profile selectedProfile = this.site.getProfileContext().getSelectedProfile();
		if (!(selectedProfile instanceof HardCodedProfile)) {
			// Must hide everything !
			return;
		}
		HardCodedProfile profile = (HardCodedProfile) selectedProfile;

		// Check if value as changed
		int audioBitrate = this.audioBitrateEditor.getSelection();
		int videoBitrate = this.videoBitrateEditor.getSelection();
		if (audioBitrate != profile.getAudioBitrate()
				|| videoBitrate != profile.getVideoBitrate()) {

			// Change profile value
			profile.setAudioBitrate(audioBitrate);
			profile.setVideoBitrate(videoBitrate);

			// To force update
			this.site.getProfileContext().setSelectedProfile(profile);

		}

	}

	/**
	 * Update this view to reflect the profile modification.
	 */
	void profileAsChanged() {

		Profile selectedProfile = this.site.getProfileContext().getSelectedProfile();
		if (!(selectedProfile instanceof HardCodedProfile)) {
			// Must hide everything !
			return;
		}

		HardCodedProfile profile = (HardCodedProfile) selectedProfile;
		EncodingOptions encodingOptions = profile.getEncodingOptions();

		if (this.videoBitrateEditor.getMaximum() != profile.getMaximumVideoBitrate()) {
			this.videoBitrateEditor.setRange(MINIMUM_VIDEO_BITRATE, profile
					.getMaximumVideoBitrate());
		}
		if (this.videoBitrateEditor.getSelection() != profile.getVideoBitrate()) {
			this.videoBitrateEditor.setSelection(profile.getVideoBitrate());
		}

		if (this.audioBitrateEditor.getMaximum() != profile.getMaximumAudioBitrate()) {
			this.audioBitrateEditor.setRange(MINIMUM_AUDIO_BITRATE, profile
					.getMaximumAudioBitrate());
		}
		if (this.audioBitrateEditor.getSelection() != profile.getAudioBitrate()) {
			this.audioBitrateEditor.setSelection(encodingOptions.getAudioOptions()
					.getBitrate());
		}

		VideoScalingOptions[] videoScalings = profile
				.getSupportedVideoScalings();
		if (!videoScalings.equals(this.lastScalingOptions)) {
			updateViewer(this.videoDimentionViewer, videoScalings, encodingOptions
					.getScaleOptions());
			this.lastScalingOptions = videoScalings;
		} else {
			StructuredSelection structSelection = new StructuredSelection(
					profile.getVideoScaling());
			this.videoDimentionViewer.setSelection(structSelection);
		}

	}

	/**
	 * This method are use to update a viewer (Update the list and the
	 * selection).
	 * 
	 * @param viewer
	 *            the viewer component.
	 * @param elements
	 *            new list of element for viewer.
	 * @param selection
	 *            the selection element.
	 */
	void updateViewer(ComboViewer viewer, Object[] objects,
			Object selection) {

		// Update the list
		viewer.getCombo().removeAll();

		for (int index = 0; index < objects.length; index++) {
			Object element = objects[index];
			viewer.add(element);
			if (element.equals(selection)) {
				viewer.setSelection(new StructuredSelection(element));
			}
		}

		Object curSelection = ((IStructuredSelection) viewer.getSelection())
				.getFirstElement();
		if (curSelection == null && objects.length > 0) {
			StructuredSelection structSelection = new StructuredSelection(
					objects[0]);
			viewer.setSelection(structSelection);
		}
	}

	/**
	 * Notify this class that user change the video dimention.
	 */
	void videoDimentsionSelectionAsChanged() {

		Profile selectedProfile = this.site.getProfileContext().getSelectedProfile();
		if (!(selectedProfile instanceof HardCodedProfile)) {
			// Must hide everything !
			return;
		}
		HardCodedProfile profile = (HardCodedProfile) selectedProfile;

		// Try to get the selected dimention
		Object selection = ((IStructuredSelection) this.videoDimentionViewer
				.getSelection()).getFirstElement();
		if (!(selection instanceof VideoScalingOptions)) {
			return;
		}
		VideoScalingOptions selectedDimention = (VideoScalingOptions) selection;

		// Check if it's different
		if (!profile.getVideoScaling().equals(selectedDimention)) {

			// Change profile value
			profile.setVideoScaling(selectedDimention);

			// To force update
			this.site.getProfileContext().setSelectedProfile(profile);
		}

	}

}
