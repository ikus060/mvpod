package net.homeip.entreprisesmd.mvconv.gui.inputvideo;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.core.video.VideoObserver;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioStream;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.SubtitleStream;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This class contain the main part to present the input video selection to the
 * user. This class can be use to facilitate the the creation of a new
 * InputVideoComposite.
 * 
 * @author patapouf
 * 
 */
public class AbstractInputVideoComposite extends InputVideoComposite {

	/**
	 * The filename text area (real-only field).
	 */
	private Text filename;

	/**
	 * Font with bold style.
	 */
	private Font boldFont;

	/**
	 * Item to disable audio or subtitle.
	 */
	protected String disableElement = Localization
			.getString(Localization.INPUTOUTPUT_NONE);
	/**
	 * Default item selection for viewer.
	 */
	protected String defaultElement = Localization
			.getString(Localization.INPUTOUTPUT_DEFAULT);

	/**
	 * This observer are use to be notify of any change of the video so the
	 * composite can be ubdate to reflect this modification.
	 */
	private VideoObserver videoObserver = new VideoObserver() {
		public void videoHasChanged(Video video) {
			AbstractInputVideoComposite.this.videoHasChanged();
		}
	};

	/**
	 * Label provider for language combo viewer and subtitle combo viewer.
	 */
	private LabelProvider labelProvider;

	/**
	 * The selection listener from language combo viewer and subtitle combo
	 * viewer.
	 */
	private SelectionListener selectionListener;

	/**
	 * Create a new AbstractInputVideoComposite.
	 * 
	 * @param parent
	 *            the parent of this composite interface.
	 * @param style
	 *            the style of this composite interface.
	 */
	public AbstractInputVideoComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the label provider for language combo viewer and subtitle combo
	 * viewer.
	 * 
	 * @return the label provider.
	 */
	private LabelProvider createLabelProvider() {
		return new LabelProvider() {
			public String getText(Object element) {
				if (element instanceof AudioStream) {

					String languageId = ((AudioStream) element).getLanguage();
					String track = ((AudioStream) element).getAudioID();

					if (languageId != null && !languageId.equals("")) {
						String languageName = Localization
								.getLocalizedLanguage(languageId);
						return languageName;
					}

					track = Localization.getString(
							Localization.INPUTOUTPUT_TRACK, track);
					return track;

				} else if (element instanceof SubtitleStream) {

					String languageId = ((SubtitleStream) element)
							.getLanguage();
					if (languageId != null && !languageId.equals("")) {
						String languageName = Localization
								.getLocalizedLanguage(languageId);
						return languageName;
					}

					String track = ((SubtitleStream) element).getSubtitleID();
					track = Localization.getString(
							Localization.INPUTOUTPUT_TRACK, track);
					return track;
				} else if (element == null) {
					return "None";
				}
				return element.toString();
			}
		};
	}

	/**
	 * This method should be overload by sub class to create the input component
	 * for the selection of this input video. The implementation of this class
	 * return null.
	 * 
	 * @param parent
	 *            the parent of this Area.
	 * @return the control created.
	 */
	protected Control createInputArea(Composite parent) {
		return null;
	}

	/**
	 * This method are use to create a 'More Options..' button that will show a
	 * dialog with more options for the input video.
	 * 
	 * @param parent
	 *            the parent of the button
	 * @return the button
	 */
	protected Control createMoreOptionsButton(Composite parent) {

		String moreOptionsText = Localization
				.getString(Localization.INPUTOUTPUT_MORE_OPTIONS);
		Button moreOptionsButton = new Button(parent, SWT.PUSH);
		moreOptionsButton.setText(moreOptionsText);

		moreOptionsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				InputVideoGenericOptionsDialog dlg = new InputVideoGenericOptionsDialog(
						getShell());
				dlg.setVideo(getVideo());
				dlg.init(getViewSite());
				dlg.open();
			}
		});

		return moreOptionsButton;

	}

	/**
	 * This method my be overload by sub class to a different output selection.
	 * The implementation of this class create a simple file selection.
	 * 
	 * @param parent
	 *            the parent of this Area.
	 * @return the control created.
	 */
	protected Control createOutputArea(Composite parent) {

		parent.setLayout(new GridLayout(3, false));

		Label label = new Label(parent, SWT.NONE);
		label
				.setText(Localization
						.getString(Localization.INPUTOUTPUT_FILENAME));
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		filename = new Text(parent, SWT.SINGLE | SWT.BORDER);
		filename.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		filename.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				outputFilenameChanged();
			}
		});

		Button browseButton = new Button(parent, SWT.PUSH);
		browseButton.setText(Localization
				.getString(Localization.INPUTOUTPUT_BROWSE));
		browseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false));
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});

		return filename;
	}

	/**
	 * Create a new Selection changed listener.
	 * 
	 * @return the selection changed listener.
	 */
	private SelectionListener createSelectionListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int id = -1;
				if (e.widget.getData() instanceof Integer) {
					id = (Integer) e.widget.getData();
				}
				viewerSelectionHasChanged(id);
			}
		};
	}

	/**
	 * Create a viewer component.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param id
	 *            the unique identifier of this viewer.
	 * @return the viewer.
	 */
	protected ComboViewer createViewer(Composite parent, int id) {

		ComboViewer subCombo = new ComboViewer(parent, SWT.DROP_DOWN
				| SWT.READ_ONLY);

		if (labelProvider == null) {
			labelProvider = createLabelProvider();
		}
		subCombo.setLabelProvider(labelProvider);

		if (selectionListener == null) {
			selectionListener = createSelectionListener();
		}

		subCombo.getCombo().addSelectionListener(selectionListener);

		subCombo.getCombo().setData(id);

		return subCombo;
	}

	/**
	 * Handle browsing selection to change output file.
	 */
	private void handleBrowse() {

		File outputFile = this.getVideo().getOutputFile();

		String dlgTitle = Localization
				.getString(Localization.INPUTOUTPUT_OUTPUT_FILEDIALOG_TITLE);

		FileDialog dlg = new FileDialog(this.getShell(), SWT.SAVE);
		String path = outputFile.getParent().toString();
		String filename = outputFile.getName();
		dlg.setFilterPath(path);
		dlg.setFileName(filename);
		dlg.setText(dlgTitle);
		String selection = dlg.open();

		if (selection != null) {
			File file = new File(dlg.getFilterPath() + File.separator
					+ dlg.getFileName());
			this.getVideo().setOutputFile(file);
		}

	}

	/**
	 * Initialize this view with the given view site. This implementation
	 * initialize the composite component.
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {

		super.init(site);

		// Create font
		FontData[] boldedFontData = getFont().getFontData();
		for (int fontIndex = 0; fontIndex < boldedFontData.length; fontIndex++) {
			boldedFontData[fontIndex].setStyle(SWT.BOLD);
		}
		boldFont = new Font(Display.getCurrent(), boldedFontData);

		this.setLayout(new GridLayout(1, false));

		Group inputComposite = new Group(this, SWT.SHADOW_NONE);
		inputComposite.setText(Localization
				.getString(Localization.INPUTOUTPUT_INPUT));
		inputComposite.setFont(boldFont);
		inputComposite.setLayout(new FillLayout());
		inputComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));

		Group outputComposite = new Group(this, SWT.SHADOW_NONE);
		outputComposite.setText(Localization
				.getString(Localization.INPUTOUTPUT_OUPUT));
		outputComposite.setFont(boldFont);
		outputComposite.setLayout(new FillLayout());
		outputComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false));

		createInputArea(inputComposite);

		createOutputArea(outputComposite);

		// Disposal
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getVideo().removeVideoObserver(videoObserver);
				videoObserver = null;
				boldFont.dispose();
				boldFont = null;
			}
		});
	}

	/**
	 * Notify this class that user change the output file name in TextField.
	 */
	private void outputFilenameChanged() {

		// Check if value as changed
		String outputFilename = filename.getText();
		File file = new File(outputFilename);
		if (!file.equals(getVideo().getOutputFile())) {
			// Change the output file value
			getVideo().setOutputFile(file);
		}

	}

	/**
	 * This method are use to update the language combo box. It's update the
	 * list and the selection.
	 * 
	 * @param viewer
	 *            the viewer component.
	 * @param elements
	 *            new list of element for viewer.
	 * @param selection
	 *            the selection element.
	 */
	protected void updateViewer(ComboViewer viewer,
			List<? extends Object> elements, Object selection) {
		updateViewer(viewer, elements, selection, false, false);
	}

	/**
	 * This method are use to update the language combo box. It's update the
	 * list and the selection.
	 * 
	 * @param viewer
	 *            the viewer component.
	 * @param elements
	 *            new list of element for viewer.
	 * @param selection
	 *            the selection element.
	 * @param defaultValue
	 *            True to show a default element in the viewer list.
	 * @param disableValue
	 *            True to show a disabled element in the viewer list.
	 */
	protected void updateViewer(ComboViewer viewer,
			List<? extends Object> elements, Object selection,
			boolean defaultValue, boolean disableValue) {

		// Update the list
		viewer.getCombo().removeAll();

		if (defaultValue) {
			viewer.add(defaultElement);
			if (defaultElement.equals(selection)) {
				viewer.setSelection(new StructuredSelection(defaultElement));
			}
		}
		if (disableValue) {
			viewer.add(disableElement);
			if (disableElement.equals(selection)) {
				viewer.setSelection(new StructuredSelection(disableElement));
			}
		}

		Iterator<? extends Object> audioIter = elements.iterator();
		while (audioIter.hasNext()) {
			Object element = audioIter.next();
			viewer.add(element);
			if (element.equals(selection)) {
				viewer.setSelection(new StructuredSelection(element));
			}

		}

		Object curSelection = ((IStructuredSelection) viewer.getSelection())
				.getFirstElement();
		if (curSelection == null && elements.size() > 0) {
			StructuredSelection structSelection = new StructuredSelection(
					elements.get(0));
			viewer.setSelection(structSelection);
		}
	}

	/**
	 * Sub-class may overload this method to update field. This implementation
	 * do nothing.
	 */
	protected void videoHasChanged() {

		if (getVideo() != null) {
			String newFilename = getVideo().getOutputFile().getAbsolutePath();
			if (!filename.getText().equals(newFilename)) {
				filename.setText(newFilename);
			}
		}

	}

	/**
	 * Notify sub class that audio trac selection selection has changed. Default
	 * implementation do nothing.
	 * <p>
	 * Sub-class may overload this method to update the domain model to reflect
	 * the selection.
	 * </p>
	 * 
	 * @param id
	 *            the unique identifier of the viewer.
	 */
	protected void viewerSelectionHasChanged(int id) {

	}

	/**
	 * Set the video. This implementation add and remove video observer.
	 * 
	 * @param video
	 *            the new video.
	 */
	public void setVideo(Video video) {
		if (getVideo() != null) {
			getVideo().removeVideoObserver(videoObserver);
		}

		super.setVideo(video);

		video.addVideoObserver(videoObserver);

		videoHasChanged();
	}

}
