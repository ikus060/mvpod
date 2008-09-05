package net.homeip.entreprisesmd.mvconv.gui.actions;

import java.io.File;
import java.io.FileNotFoundException;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.gui.ErrorMessage;
import net.homeip.entreprisesmd.mvconv.gui.IVideoOutputFileProvider;
import net.homeip.entreprisesmd.mvconv.gui.MPlayerProvider;
import net.homeip.entreprisesmd.mvconv.gui.Main;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoDVD;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

/**
 * This action are use to add a new dvd source from a DVD Directory (usually
 * named VIDEO_TS).
 * 
 * @author patapouf
 * 
 */
public class AddDVDFromDirectoryAction extends Action {

	/**
	 * List of supported image file extention (*.vob, *.bup, *.ifo, ...).
	 */
	private static final String[] EXTENTIONS = { "*.vob;*.VOB", "*.*" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * List of localisation identifier of each extentions.
	 */
	private static final String[] EXTENTION_NAMES = {
			Localization.FILE_EXTENTION_VOB, Localization.FILE_EXTENTION_ALL };

	/**
	 * Operation to add files.
	 */
	private class AddDVDOperation implements Operation {
		/**
		 * DVD device.
		 */
		String filename;

		/**
		 * Create a new operation.
		 * 
		 * @param filePath
		 *            the file path (directory).
		 * @param fileNames
		 *            a list of files
		 */
		public AddDVDOperation(String filename) {
			this.filename = filename;
		}

		public void cancel() {
			// Cannot cancel the opperation
		}

		public void start() {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					File file = new File(AddDVDOperation.this.filename);
					Video video = createInputOutputVideo(file.getParent());
					if (video != null) {
						AddDVDFromDirectoryAction.this.videoList
								.addInputVideo(video);
					}
				}
			});
		}
	}

	/**
	 * The video list.
	 */
	VideoList videoList;

	/**
	 * Shell provider.
	 */
	IShellProvider shellProvider;

	/**
	 * Last file name selected or null.
	 */
	String lastFilePath;

	/**
	 * The output file provider.
	 */
	IVideoOutputFileProvider fileProvider;

	/**
	 * MPlayer provider.
	 */
	MPlayerProvider mplayerProvider;

	/**
	 * Create a new <code>AddVideoFileAction</code>.
	 * 
	 * @param shellProvider
	 *            a shell provider to obtain a shell.
	 * @param videoList
	 *            a list of video where to add the input video.
	 * @param fileProvider
	 *            a file name provider that will be use to generate the output
	 *            file name of the video.
	 */
	public AddDVDFromDirectoryAction(IShellProvider shellProvider,
			VideoList videoList, IVideoOutputFileProvider fileProvider,
			MPlayerProvider mplayerProvider) {

		super(Localization
				.getString(Localization.ACTION_ADD_DVD_FROM_DIRECTORY));

		if (shellProvider == null) {
			throw new NullPointerException();
		}
		if (videoList == null) {
			throw new NullPointerException();
		}
		if (fileProvider == null) {
			throw new NullPointerException();
		}
		this.shellProvider = shellProvider;
		this.videoList = videoList;
		this.fileProvider = fileProvider;
		this.mplayerProvider = mplayerProvider;

		this.lastFilePath = Main.instance().getPreferenceStore().getString(
				Main.PREF_LAST_DIRECTORY);
	}

	/**
	 * Return an input/output video from the filename.
	 * 
	 * @param directory
	 *            the dvd directory.
	 * @return an input/output video from the filename.
	 */
	Video createInputOutputVideo(String directory) {
		InputVideoDVD inputVideoDvd;
		try {
			inputVideoDvd = InputVideoDVD.fromDVDDirectory(new File(directory));
		} catch (FileNotFoundException e) {
			ErrorMessage.showLocalizedError(this.shellProvider.getShell(),
					Localization.ADDDVD_DIRECTORY_NOT_VALID);
			return null;
		}

		File output = this.fileProvider.getFile(inputVideoDvd);

		return new Video(inputVideoDvd, output);
	}

	/**
	 * This implementation of this methode add a new video to the video list.
	 */
	public void run() {

		// Check if Mplayer exist
		if (this.mplayerProvider.getWrapper() == null) {
			ErrorMessage.showLocalizedError(this.shellProvider.getShell(),
					Localization.MPLAYER_NOT_FOUND);
			return;
		}

		// Setup dialog
		FileDialog dlg = new FileDialog(this.shellProvider.getShell(), SWT.OPEN);
		dlg.setFilterPath(this.lastFilePath);
		dlg.setFilterExtensions(EXTENTIONS);
		String[] localizedNames = new String[EXTENTION_NAMES.length];
		for (int index = 0; index < EXTENTION_NAMES.length; index++) {
			localizedNames[index] = Localization
					.getString(EXTENTION_NAMES[index]);
		}
		dlg.setFilterNames(localizedNames);
		String title = Localization
				.getString(Localization.ACTION_ADD_DVD_FROM_DIRECTORY_DIALOG_TEXT);
		dlg.setText(title);

		// Open dialog
		String filename = dlg.open();

		// Add video
		if (filename != null) {

			filename = dlg.getFilterPath() + File.separator + dlg.getFileName();

			this.lastFilePath = dlg.getFilterPath();
			Main.instance().getPreferenceStore().setValue(
					Main.PREF_LAST_DIRECTORY, this.lastFilePath);

			Operation operation = new AddDVDOperation(filename);
			String progressMessage = Localization
					.getString(Localization.ACTION_ADD_DVD_FROM_DIRECTORY_PROGRESS_MESSAGE);
			ProgressDialog progressDlg = new ProgressDialog(this.shellProvider
					.getShell());
			progressDlg.setOperation(operation);
			progressDlg.setProgressMessage(progressMessage);
			progressDlg.open();

		}
	}
}
