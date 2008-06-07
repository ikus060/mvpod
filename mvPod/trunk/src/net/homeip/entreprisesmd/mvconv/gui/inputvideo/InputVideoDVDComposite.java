package net.homeip.entreprisesmd.mvconv.gui.inputvideo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.VideoInfoFormater;
import net.homeip.entreprisesmd.mvconv.gui.ErrorMessage;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioStream;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoDVD;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.SubtitleStream;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoInfo;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This class present a user interface to modify every option of the input
 * video.
 * 
 * @author patapouf
 * 
 */
public class InputVideoDVDComposite extends AbstractInputVideoComposite {

	/**
	 * Unique identifier for title viewer.
	 */
	private static final int TITLE_ID = 0;
	/**
	 * Unique identifier for audio viewer.
	 */
	private static final int AUDIO_ID = 1;
	/**
	 * Unique identifier for subtitle viewer.
	 */
	private static final int SUBTITLE_ID = 2;
	/**
	 * Factor to create darker color.
	 */
	private static final double DARKER_COLOR_FACTOR = 0.95;
	/**
	 * Daker color for background of description text.
	 */
	private Color darkerBackground;
	/**
	 * Text component use to display the input file name.
	 */
	private Text inputFileName;
	/**
	 * Title Viewer.
	 */
	private ComboViewer titleCombo;
	/**
	 * Audio Viewer.
	 */
	private ComboViewer languageCombo;
	/**
	 * Subtitle viewer.
	 */
	private ComboViewer subtitleCombo;
	/**
	 * Text component use to display some detail about the video.
	 */
	private Text videoDescription;
	/**
	 * Text component use to display some detail about the audio.
	 */
	private Text audioDescription;
	/**
	 * Local variable use to determine if we need to update the viewer.
	 */
	private InputVideoDVD lastInputVideo;

	/**
	 * Create a new InputVideoFileComposite.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the style of this composite interface.
	 */
	public InputVideoDVDComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * This methode should be overload by sub class to create the input
	 * component for the selection of this input video. The implementation of
	 * this class return an empty Composite.
	 * 
	 * @param parent
	 *            the parent of this Area.
	 * @return the control created.
	 */
	protected Control createInputArea(Composite parent) {

		// Setup darker background
		RGB rgb = getBackground().getRGB();
		rgb.red = (int) (rgb.red * DARKER_COLOR_FACTOR);
		rgb.green = (int) (rgb.green * DARKER_COLOR_FACTOR);
		rgb.blue = (int) (rgb.blue * DARKER_COLOR_FACTOR);
		darkerBackground = new Color(Display.getCurrent(), rgb);

		// Create the composite component
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(6, false));

		/*
		 * Filename components
		 */
		String filenameText = Localization
				.getString(Localization.INPUTOUTPUT_FILENAME);
		Label label;
		label = new Label(comp, SWT.NONE);
		label.setText(filenameText);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		inputFileName = new Text(comp, SWT.READ_ONLY | SWT.BORDER);
		inputFileName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 5, 1));

		/*
		 * Title selection
		 */
		label = new Label(comp, SWT.NONE);
		label.setText(Localization.getString(Localization.INPUTOUTPUT_TITLE));
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		titleCombo = createViewer(comp, TITLE_ID);
		titleCombo.getCombo().setLayoutData(
				new GridData(SWT.LEFT, SWT.FILL, false, false));

		/*
		 * Language selection
		 */
		label = new Label(comp, SWT.NONE);
		label.setText(Localization.getString(Localization.INPUTOUTPUT_LANGAGE));
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		languageCombo = createViewer(comp, AUDIO_ID);
		languageCombo.getCombo().setLayoutData(
				new GridData(SWT.LEFT, SWT.FILL, false, false));

		/*
		 * Subtitle selection
		 */
		String subtitleText = Localization
				.getString(Localization.INPUTOUTPUT_SUBTITLE);
		label = new Label(comp, SWT.NONE);
		label.setText(subtitleText);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		subtitleCombo = createViewer(comp, SUBTITLE_ID);
		subtitleCombo.getCombo().setLayoutData(
				new GridData(SWT.LEFT, SWT.FILL, false, false));

		/*
		 * Description label
		 */

		label = new Label(comp, SWT.NONE);
		label.setText(Localization.getString(Localization.INPUTOUTPUT_VIDEO));
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		videoDescription = new Text(comp, SWT.BORDER | SWT.WRAP | SWT.READ_ONLY);
		videoDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 5, 1));
		videoDescription.setBackground(darkerBackground);

		label = new Label(comp, SWT.NONE);
		label.setText(Localization.getString(Localization.INPUTOUTPUT_AUDIO));
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		audioDescription = new Text(comp, SWT.BORDER | SWT.WRAP | SWT.READ_ONLY);
		audioDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 5, 1));
		audioDescription.setBackground(darkerBackground);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				darkerBackground.dispose();
			}
		});

		return comp;
	}

	/**
	 * Search for the audio stream that match the given audio id.
	 * 
	 * @param streams
	 *            the audio stream list.
	 * @param audioID
	 *            the audio id.
	 * @return the audio stream or null if not found.
	 */
	private AudioStream findAudioSelection(List<AudioStream> streams,
			String audioID) {

		Iterator<AudioStream> iter = streams.iterator();
		while (iter.hasNext()) {
			AudioStream stream = iter.next();
			if (stream.getAudioID().equals(audioID)) {
				return stream;
			}
		}
		return null;
	}

	/**
	 * Search for the subtitle stream that match the given subtitle id.
	 * 
	 * @param streams
	 *            the subtitle stream list.
	 * @param subtitleID
	 *            the subtitle id.
	 * @return the subtitle stream or null if not found.
	 */
	private SubtitleStream findSubtitleSelection(List<SubtitleStream> streams,
			String subtitleID) {

		Iterator<SubtitleStream> iter = streams.iterator();
		while (iter.hasNext()) {
			SubtitleStream stream = iter.next();
			if (stream.getSubtitleID().equals(subtitleID)) {
				return stream;
			}
		}
		return null;
	}

	/**
	 * Return the input video (type cast).
	 * 
	 * @return the input video.
	 */
	protected InputVideoDVD getInputVideo() {
		return (InputVideoDVD) getVideo().getInputVideo();
	}

	/**
	 * Used to update description component.
	 * 
	 * @param info
	 *            the video information.
	 */
	protected void updateDescription(VideoInfo info) {
		String description;
		// Video description
		description = VideoInfoFormater.formatVideoInfo(info);
		videoDescription.setText(description);

		// Audio description
		List<AudioStream> audioStreams = info.getAudioStreams();
		AudioStream audioSelection = findAudioSelection(audioStreams,
				getInputVideo().getAudioTrack());
		if (audioSelection != null) {
			description = VideoInfoFormater.formatAudioStream(audioSelection);
		} else {
			description = "";
		}
		audioDescription.setText(description);
	}

	/**
	 * Used to update audio component.
	 * 
	 * @param info
	 *            the video information.
	 */
	protected void updateLanguage(VideoInfo info) {

		List<AudioStream> audioStreams = info.getAudioStreams();
		AudioStream audioSelection = findAudioSelection(audioStreams,
				getInputVideo().getAudioTrack());

		if (audioSelection != null) {
			updateViewer(languageCombo, audioStreams, audioSelection, false,
					false);
		} else {
			updateViewer(languageCombo, audioStreams, disableElement, false,
					true);
		}
	}

	/**
	 * Used to update subtitle component.
	 * 
	 * @param info
	 *            the video info
	 */
	protected void updateSubtitle(VideoInfo info) {
		List<SubtitleStream> subtitleStreams = info.getSubtitleLanguages();
		SubtitleStream subSelection = findSubtitleSelection(subtitleStreams,
				getInputVideo().getSubtitleTrack());
		Object selection = subSelection;
		if (selection == null) {
			selection = disableElement;
		}
		updateViewer(subtitleCombo, subtitleStreams, selection, false, true);
	}

	/**
	 * This implementation update all input video fields.
	 */
	protected void videoHasChanged() {

		super.videoHasChanged();

		// Check if the input video has changed
		if (lastInputVideo != getInputVideo()) {

			lastInputVideo = getInputVideo();

			// Update the file name
			inputFileName.setText(getInputVideo().getDevice());

			// Check if viewsite are available
			if (getViewSite() == null) {
				return;
			}

			VideoInfo info;
			try {
				MPlayerWrapper mplayer = getViewSite().getMplayer();
				if (mplayer == null) {
					ErrorMessage.showLocalizedError(getShell(),
							Localization.MPLAYER_NOT_FOUND);
					return;
				}

				info = mplayer.getVideoInfo(getInputVideo());

			} catch (MPlayerException e) {
				e.printStackTrace();
				ErrorMessage.showLocalizedError(getShell(),
						Localization.INPUTOUTPUT_RETRIEVE_INFO_FAILED);
				return;
			}

			// CheckAudio selection
			List<AudioStream> audioStreams = info.getAudioStreams();
			AudioStream audioSelection = findAudioSelection(audioStreams,
					getInputVideo().getAudioTrack());

			if (audioSelection == null && audioStreams.size() > 0) {
				audioSelection = audioStreams.get(0);

				InputVideoDVD inputVideo = new InputVideoDVD(getInputVideo()
						.getDevice(), getInputVideo().getTitle(),
						audioSelection.getAudioID(), getInputVideo()
								.getSubtitleTrack());

				if (audioStreams.size() == 1) {
					lastInputVideo = inputVideo;
					getVideo().setInputVideo(inputVideo);
				} else {
					getVideo().setInputVideo(inputVideo);
					return;
				}
			}

			// Update audio language
			updateLanguage(info);

			// Update subtitle
			updateSubtitle(info);

			// Update title
			List<String> titles = new LinkedList<String>();
			int count = info.getNumberOfTitles();
			for (int i = 1; i <= count; i++) {
				titles.add(Integer.toString(i));
			}
			updateViewer(titleCombo, titles, getInputVideo().getTitle());

			// Update description
			updateDescription(info);

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
		if (id == TITLE_ID) {

			IStructuredSelection structSelection = (IStructuredSelection) titleCombo
					.getSelection();
			String title = null;
			Object elementSelection = structSelection.getFirstElement();
			if (elementSelection instanceof String) {
				title = ((String) elementSelection);
			}

			InputVideoDVD inputVideo = new InputVideoDVD(getInputVideo()
					.getDevice(), title, getInputVideo().getAudioTrack(),
					getInputVideo().getSubtitleTrack());
			getVideo().setInputVideo(inputVideo);

		} else if (id == AUDIO_ID) {

			IStructuredSelection structSelection = (IStructuredSelection) languageCombo
					.getSelection();
			String audioID = null;
			Object elementSelection = structSelection.getFirstElement();
			if (elementSelection instanceof AudioStream) {
				audioID = ((AudioStream) elementSelection).getAudioID();
			}

			InputVideoDVD inputVideo = new InputVideoDVD(getInputVideo()
					.getDevice(), getInputVideo().getTitle(), audioID,
					getInputVideo().getSubtitleTrack());
			getVideo().setInputVideo(inputVideo);

		} else if (id == SUBTITLE_ID) {

			IStructuredSelection structSelection = (IStructuredSelection) subtitleCombo
					.getSelection();
			String subtitleID = null;
			Object elementSelection = structSelection.getFirstElement();
			if (elementSelection instanceof SubtitleStream) {
				subtitleID = ((SubtitleStream) elementSelection)
						.getSubtitleID();
			}
			InputVideoDVD inputVideo = new InputVideoDVD(getInputVideo()
					.getDevice(), getInputVideo().getTitle(), getInputVideo()
					.getAudioTrack(), subtitleID);
			getVideo().setInputVideo(inputVideo);

		}
	}
}
