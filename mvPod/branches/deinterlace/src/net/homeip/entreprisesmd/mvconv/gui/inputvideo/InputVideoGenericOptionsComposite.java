package net.homeip.entreprisesmd.mvconv.gui.inputvideo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.core.video.VideoObserver;
import net.homeip.entreprisesmd.mvconv.gui.ComboCustomViewer;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog are use to modify advance options of on inputVideo. This
 * interface are shown when user click on 'More Options..' button.
 * 
 * @author patapouf
 * 
 */
public class InputVideoGenericOptionsComposite extends InputVideoComposite {

	private static final String ASPECT_RATIO_PATTERN = "([0-9]+?\\.?[0-9]*):([0-9]+?\\.?[0-9]*)"; //$NON-NLS-1$

	/**
	 * Custom item.
	 */
	private static final String CUSTOM_ITEM = "Custom"; //$NON-NLS-1$

	/**
	 * Frame rate viewer
	 */
	ComboCustomViewer aspectRatioEditor;

	/**
	 * Aspect Ratio label provider.
	 */
	LabelProvider aspectRatioLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			String key = null;
			if (element.equals(InputVideo.ASPECT_RATIO_KEEP)) {
				key = Localization.INPUTOUTPUT_ASPECT_RATIO_KEEP;
			} else if (element.equals(InputVideo.ASPECT_RATIO_4_3)) {
				key = Localization.INPUTOUTPUT_ASPECT_RATIO_4_3;
			} else if (element.equals(InputVideo.ASPECT_RATIO_16_9)) {
				key = Localization.INPUTOUTPUT_ASPECT_RATIO_16_9;
			} else if (element.equals(CUSTOM_ITEM)) {
				key = Localization.INPUTOUTPUT_ASPECT_RATIO_CUSTOM;
			}
			if (key != null) {
				return Localization.getString(key);
			}
			return element.toString();
		}
	};

	ComboViewer deinterlaceMethodViewer;
	ComboViewer deinterlaceFilterViewer;

	Button interlacedVideoButton;
	Button deinterlaceVideoButton;

	/**
	 * This observer are use to be notify of any change of the video so the
	 * composite can be ubdate to reflect this modification.
	 */
	VideoObserver videoObserver = new VideoObserver() {
		public void videoHasChanged(Video video) {
			InputVideoGenericOptionsComposite.this.videoHasChanged();
		}
	};

	/**
	 * Selection listener.
	 */
	ISelectionChangedListener selectionChangeListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == InputVideoGenericOptionsComposite.this.aspectRatioEditor) {
				frameRateSelectionAsChanged();
			} else if (event.getSource() == InputVideoGenericOptionsComposite.this.deinterlaceMethodViewer) {
				deinterlaceMethodSelectionAsChanged();
			}
		}
	};

	Listener listener = new Listener() {

		public void handleEvent(Event event) {
			if (event.widget == InputVideoGenericOptionsComposite.this.interlacedVideoButton) {
				interlacedVideoSelectAsChanged();
			} else if (event.widget == InputVideoGenericOptionsComposite.this.deinterlaceVideoButton) {
				deinterlaceVideoSelectAsChanged();
			}
		}

	};

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            parent of this interface
	 * @param style
	 *            the style of this composite interface
	 */
	public InputVideoGenericOptionsComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		// Available for sub-class
	}

	/**
	 * Notify this class that user select a new deinterlace method.
	 */
	void deinterlaceMethodSelectionAsChanged() {

	}

	/**
	 * Notify this class that user change the deinterlace option (enable or
	 * disable).
	 */
	void deinterlaceVideoSelectAsChanged() {
		
		/*InputVideo inputVideo = getVideo().getInputVideo();

		// Get Selection
		boolean selection = this.interlacedVideoButton.getSelection();

		if (selection != inputVideo.isInterlaced()) {
			// Change inputvideo value
			inputVideo.setInterlaced(selection);
			getVideo().setInputVideo(inputVideo);
		}*/
		
	}

	/**
	 * Notify this class that user select an new aspect ratio.
	 */
	void frameRateSelectionAsChanged() {

		InputVideo inputVideo = getVideo().getInputVideo();

		// Get Selection
		Object selection = ((IStructuredSelection) this.aspectRatioEditor
				.getSelection()).getFirstElement();
		Double aspectRatio = null;
		if (CUSTOM_ITEM.equals(selection)) {
			aspectRatio = promptForAspectRatio();
		} else {
			aspectRatio = getAspectRatioSelection();
		}

		if (aspectRatio != null && aspectRatio != inputVideo.getAspectRatio()) {

			// Change inputvideo value
			inputVideo.setAspectRatio(aspectRatio);
			getVideo().setInputVideo(inputVideo);

		}
	}

	/**
	 * Return the aspect ratio selection.
	 * 
	 * @return the aspect ratio selection.
	 */
	private Double getAspectRatioSelection() {
		Object selection = ((IStructuredSelection) this.aspectRatioEditor
				.getSelection()).getFirstElement();
		if (!(selection instanceof Double)) {
			return null;
		}
		return (Double) selection;
	}

	void interlacedVideoSelectAsChanged() {

		InputVideo inputVideo = getVideo().getInputVideo();

		// Get Selection
		boolean selection = this.interlacedVideoButton.getSelection();

		if (selection != inputVideo.isInterlaced()) {
			// Change inputvideo value
			inputVideo.setInterlaced(selection);
			getVideo().setInputVideo(inputVideo);
		}
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.gui.inputvideo.InputVideoComposite#init(net.homeip.entreprisesmd.mvconv.gui.IViewSite)
	 */
	public void init(IViewSite site) {

		// Layout
		this.setLayout(new GridLayout(2, false));

		// Load localized text
		String aspectRatio = Localization
				.getString(Localization.INPUTOUTPUT_ASPECT_RATIO);
		String interlaced = Localization
				.getString(Localization.INPUTOUTPUT_INTERLACED);
		String interlaceOptions = Localization
				.getString(Localization.INPUTOUTPUT_INTERLACE_OPTIONS);
		String deinterlace = Localization
				.getString(Localization.INPUTOUTPUT_DEINTERLACE);
		String deinterlaceMethod = Localization
				.getString(Localization.INPUTOUTPUT_DEINTERLACE_METHOD);
		String deinterlaceFilter = Localization
				.getString(Localization.INPUTOUTPUT_DEINTERLACE_FILTER);
		/*
		 * Components
		 */

		// Aspect Ratio
		Label label = new Label(this, SWT.NONE);
		label.setText(aspectRatio);

		this.aspectRatioEditor = new ComboCustomViewer(this);
		this.aspectRatioEditor
				.addSelectionChangedListener(this.selectionChangeListener);
		this.aspectRatioEditor.setLabelProvider(this.aspectRatioLabelProvider);
		this.aspectRatioEditor.add(CUSTOM_ITEM);
		this.aspectRatioEditor.add(InputVideo.ASPECT_RATIO_KEEP);
		this.aspectRatioEditor.add(InputVideo.ASPECT_RATIO_4_3);
		this.aspectRatioEditor.add(InputVideo.ASPECT_RATIO_16_9);

		// Interlace Options
		Group interlaceGroup = new Group(this, SWT.NONE);
		interlaceGroup.setText(interlaceOptions);
		interlaceGroup.setLayout(new GridLayout(2, false));
		interlaceGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 2, 1));

		this.interlacedVideoButton = new Button(interlaceGroup, SWT.CHECK);
		this.interlacedVideoButton.setText(interlaced);
		this.interlacedVideoButton.addListener(SWT.Selection, this.listener);
		this.interlacedVideoButton.setLayoutData(new GridData(SWT.FILL,
				SWT.FILL, true, false, 2, 1));

		this.deinterlaceVideoButton = new Button(interlaceGroup, SWT.CHECK);
		this.deinterlaceVideoButton.setText(deinterlace);
		this.deinterlaceVideoButton.addListener(SWT.Selection, this.listener);
		this.deinterlaceVideoButton.setLayoutData(new GridData(SWT.FILL,
				SWT.FILL, true, false, 2, 1));

		label = new Label(interlaceGroup, SWT.NONE);
		label.setText(deinterlaceMethod);

		this.deinterlaceMethodViewer = new ComboViewer(interlaceGroup,
				SWT.READ_ONLY);
		this.deinterlaceMethodViewer
				.addSelectionChangedListener(this.selectionChangeListener);

		label = new Label(interlaceGroup, SWT.NONE);
		label.setText(deinterlaceFilter);

		this.deinterlaceFilterViewer = new ComboViewer(interlaceGroup,
				SWT.READ_ONLY);
		this.deinterlaceFilterViewer
				.addSelectionChangedListener(this.selectionChangeListener);

		videoHasChanged();

		getVideo().addVideoObserver(this.videoObserver);

		// Disposal
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getVideo().removeVideoObserver(
						InputVideoGenericOptionsComposite.this.videoObserver);
			}
		});

	}

	/**
	 * This method are use to prompt user for aspect ratio value when 'custom'
	 * item are selected in combo box.
	 * 
	 * @return the aspect ration or null if user cancel the operation.
	 */
	private Double promptForAspectRatio() {

		Double aspectRatio = null;
		InputVideo inputVideo = getVideo().getInputVideo();

		// Ask new value to user
		Shell parentShell = getShell();
		String dialogTitle = Localization
				.getString(Localization.APPLICATION_NAME);
		String dialogMessage = Localization
				.getString(Localization.INPUTOUTPUT_ASPECT_RATIO_CUSTOM_MESSAGE);
		String initialValue = Double.toString(inputVideo.getAspectRatio());
		IInputValidator validator = new IInputValidator() {
			public String isValid(String newText) {
				if (Pattern.compile(ASPECT_RATIO_PATTERN).matcher(newText)
						.find()) {
					return null;
				}
				try {
					Double.parseDouble(newText);
					return null;
				} catch (NumberFormatException e) {
					// Mplayer generate alot of garbage, so we expecte some
					// parsing to fail.
				}

				return Localization
						.getString(Localization.INPUTOUTPUT_ASPECT_RATIO_CUSTOM_FORMAT);
			}
		};

		InputDialog dlg = new InputDialog(parentShell, dialogTitle,
				dialogMessage, initialValue, validator);

		int id = dlg.open();
		if (id == IDialogConstants.OK_ID) {
			String value = dlg.getValue();
			Matcher matcher = Pattern.compile(ASPECT_RATIO_PATTERN).matcher(
					value);
			if (matcher.find()) {
				double value1 = (int) (Double.parseDouble(matcher.group(1)) * 10000);
				double value2 = (int) (Double.parseDouble(matcher.group(2)));
				aspectRatio = (double) ((int) (value1 / value2)) / 10000;
			} else {
				aspectRatio = Double.parseDouble(value);
			}

		}

		return aspectRatio;
	}

	/**
	 * Notify this class that video has changed.
	 */
	protected void videoHasChanged() {

		if (getVideo() == null) {
			return;
		}

		InputVideo inputVideo = getVideo().getInputVideo();
		Double aspectRatio = getAspectRatioSelection();

		// Update aspect ratio
		if (aspectRatio == null
				|| !aspectRatio.equals(inputVideo.getAspectRatio())) {
			this.aspectRatioEditor.setSelection(new StructuredSelection(
					inputVideo.getAspectRatio()));
		}

		// Update interlaced mode
		boolean interlaced = this.interlacedVideoButton.getSelection();
		if (interlaced != inputVideo.isInterlaced()) {
			this.interlacedVideoButton.setSelection(inputVideo.isInterlaced());
			interlaced = inputVideo.isInterlaced();
		}
		
		// Update deinterlace mode
		boolean deinterlace = this.deinterlaceVideoButton.getSelection();
		/*if (deinterlace != inputVideo.getDeinterlaceMethod()) {
			this.deinterlaceVideoButton.setSelection(inputVideo.isInterlaced());
			deinterlace = inputVideo.isInterlaced();
		}*/
		
		
		// Update disable state
		this.deinterlaceVideoButton.setEnabled(interlaced);
		this.deinterlaceMethodViewer.getCombo().setEnabled(interlaced & deinterlace);
		this.deinterlaceFilterViewer.getCombo().setEnabled(interlaced & deinterlace);
		
	}
}
