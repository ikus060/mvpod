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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog are use to modify advance options of one inputVideo.
 * 
 * @author patapouf
 * 
 */
public class InputVideoGenericOptionsComposite extends InputVideoComposite {

	private static final String ASPECT_RATIO_PATTERN = "([0-9]+?\\.?[0-9]*):([0-9]+?\\.?[0-9]*)";

	/**
	 * Custom item.
	 */
	private static final String CUSTOM_ITEM = "Custom";

	/**
	 * Frame rate viewer
	 */
	private ComboCustomViewer aspectRatioEditor;

	/**
	 * Aspect Ratio label provider.
	 */
	private LabelProvider aspectRatioLabelProvider = new LabelProvider() {
		public String getText(Object element) {
			String key = null;
			if (element.equals(InputVideo.ASPECT_RATIO_KEEP)) {
				key = Localization.INPUTOUTPUT_ASPECT_RATIO_KEEP;
			} else if (element.equals(InputVideo.ASPECT_RATIO_4_3)) {
				key = Localization.INPUTOUTPUT_ASPECT_RATIO_4_3;
			} else if (element.equals(InputVideo.ASPECT_RATIO_16_9)) {
				key = Localization.INPUTOUTPUT_ASPECT_RATIO_16_9;
			} else if(element.equals(CUSTOM_ITEM)){
				key = Localization.INPUTOUTPUT_ASPECT_RATIO_CUSTOM;
			}
			if (key != null) {
				return Localization.getString(key);
			}
			return element.toString();
		}
	};
	/**
	 * This observer are use to be notify of any change of the video so the
	 * composite can be ubdate to reflect this modification.
	 */
	private VideoObserver videoObserver = new VideoObserver() {
		public void videoHasChanged(Video video) {
			InputVideoGenericOptionsComposite.this.videoHasChanged();
		}
	};

	/**
	 * Selection listener.
	 */
	private ISelectionChangedListener selectionChangeListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == aspectRatioEditor) {
				frameRateSelectionAsChanged();
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

	}

	/**
	 * Notify this class that user select an new aspect ratio.
	 */
	private void frameRateSelectionAsChanged() {

		InputVideo inputVideo = getVideo().getInputVideo();

		// Get Selection
		Object selection = ((IStructuredSelection) aspectRatioEditor
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
		Object selection = ((IStructuredSelection) aspectRatioEditor
				.getSelection()).getFirstElement();
		if (!(selection instanceof Double)) {
			return null;
		}
		return (Double) selection;
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

		// Components
		Label label = new Label(this, SWT.NONE);
		label.setText(aspectRatio);

		aspectRatioEditor = new ComboCustomViewer(this);
		aspectRatioEditor.addSelectionChangedListener(selectionChangeListener);
		aspectRatioEditor.setLabelProvider(aspectRatioLabelProvider);
		aspectRatioEditor.add(CUSTOM_ITEM);
		aspectRatioEditor.add(InputVideo.ASPECT_RATIO_KEEP);
		aspectRatioEditor.add(InputVideo.ASPECT_RATIO_4_3);
		aspectRatioEditor.add(InputVideo.ASPECT_RATIO_16_9);

		videoHasChanged();

		getVideo().addVideoObserver(videoObserver);

		// Disposal
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getVideo().removeVideoObserver(videoObserver);
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
				} else {
					try {
						Double.parseDouble(newText);
						return null;
					} catch (NumberFormatException e) {

					}
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
				double value1 = (int)(Double.parseDouble(matcher.group(1)) * 10000);
				double value2 = (int)(Double.parseDouble(matcher.group(2)));				
				aspectRatio = (double)((int)(value1 / value2))/10000;
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

		if (aspectRatio == null
				|| !aspectRatio.equals(inputVideo.getAspectRatio())) {
			aspectRatioEditor.setSelection(new StructuredSelection(inputVideo
					.getAspectRatio()));
		}

	}
}
