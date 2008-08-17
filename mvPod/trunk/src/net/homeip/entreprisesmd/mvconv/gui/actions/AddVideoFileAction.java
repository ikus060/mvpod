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
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoFile;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

/**
 * This action are use to add a new video file to the video list.
 * 
 * @author patapouf
 * 
 */
public class AddVideoFileAction extends Action {

	/**
	 * List of supported video file extention (*.avi, *.wmv, ...).
	 */
	private static final String[] EXTENTIONS = {
			"*.mov;*.duk;*.h264;*.264;*.qt;*.cam;*.rar;*.avi;*.mp4;*.qt;"
					+ "*.wmv;*.ogv;*.mpg;*.p64;*.nsv;*.mkv;*.ogg;*.mpq;*.vob;*.vp5;"
					+ "*.dv;*.asf;*.ts;*.vp6;*.asxl;*.flv", "*.*" };

	/**
	 * List of localization identifier of each extentions.
	 */
	private static final String[] EXTENTION_NAMES = { Localization.FILE_VIDEO,
			Localization.FILE_EXTENTION_ALL };

	/**
	 * Operation to add files.
	 */
	private class AddFileOperation implements Operation {
		/**
		 * True to cancel the operation.
		 */
		boolean cancel = false;
		/**
		 * File names
		 */
		private String fileNames[];
		/**
		 * File path
		 */
		private String filePath;

		/**
		 * Create a new operation.
		 * 
		 * @param filePath
		 *            the file path (directory).
		 * @param fileNames
		 *            a list of files
		 */
		public AddFileOperation(String filePath, String[] fileNames) {
			this.filePath = filePath;
			this.fileNames = fileNames;
		}

		public void cancel() {
			cancel = true;
		}

		public void start() {
			for (int index = 0; index < fileNames.length && !cancel; index++) {
				final Video video = createInputOutputVideo(filePath + File.separator
						+ fileNames[index]);
				if (video != null) {
					Display.getDefault().asyncExec(new Runnable(){
						public void run() {
							videoList.addInputVideo(video);
						}
					});
				}
			}
		}

	};

	/**
	 * The output file provider.
	 */
	private IVideoOutputFileProvider fileProvider;

	/**
	 * Last file name selected or null.
	 */
	private String lastFilePath;

	/**
	 * Shell provider.
	 */
	private IShellProvider shellProvider;

	/**
	 * The video list.
	 */
	private VideoList videoList;

	/**
	 * MPlayer provider.
	 */
	private MPlayerProvider mplayerProvider;
	
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
	public AddVideoFileAction(IShellProvider shellProvider,
			VideoList videoList, IVideoOutputFileProvider fileProvider, MPlayerProvider mplayerProvider) {

		super(Localization.getString(Localization.ACTION_ADD_VIDEO_FILE));

		setImageDescriptor(IconLoader.loadIcon(IconLoader.ICON_VIDEOFILE_22));

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

		lastFilePath = Main.instance().getPreferenceStore().getString(
				Main.PREF_LAST_DIRECTORY);
	}

	/**
	 * Return an input/output video from the filename.
	 * 
	 * @param filename
	 *            the inputfile name
	 * @return an input/output video from the filename
	 */
	private Video createInputOutputVideo(String filename) {
		InputVideoFile inputVideoFile;
		try {
			inputVideoFile = new InputVideoFile(new File(filename));
		} catch (FileNotFoundException e) {
			return null;
		}

		File output = fileProvider.getFile(inputVideoFile);

		return new Video(inputVideoFile, output);
	}

	/**
	 * This implementation of this methode add a new video to the video list.
	 */
	public void run() {

		//Check if Mplayer exist
		if (mplayerProvider.getWrapper() == null) {
			ErrorMessage.showLocalizedError(shellProvider.getShell(),
					Localization.MPLAYER_NOT_FOUND);
			return;
		}
		
		
		// Setup dialog
		FileDialog dlg = new FileDialog(shellProvider.getShell(), SWT.OPEN
				| SWT.MULTI);

		dlg.setFilterPath(lastFilePath);
		dlg.setFilterExtensions(EXTENTIONS);
		String[] localizedNames = new String[EXTENTION_NAMES.length];
		for (int index = 0; index < EXTENTION_NAMES.length; index++) {
			localizedNames[index] = Localization
					.getString(EXTENTION_NAMES[index]);
		}
		dlg.setFilterNames(localizedNames);
		String title = Localization
				.getString(Localization.ACTION_ADD_VIDEO_FILE_DIALOG_TEXT);
		dlg.setText(title);

		// Open dialog
		String filename = dlg.open();
		if (filename == null) {
			return;
		}

		// Analyze the user selection
		if (dlg.getFileNames() != null) {
			String filePath = dlg.getFilterPath();
			String[] fileNames = dlg.getFileNames();
			lastFilePath = dlg.getFilterPath();
			Main.instance().getPreferenceStore().setValue(
					Main.PREF_LAST_DIRECTORY, lastFilePath);

			
			Operation operation = new AddFileOperation(filePath, fileNames);
			String progressMessage = Localization.getString(Localization.ACTION_ADD_VIDEO_FILE_PROGRESS_MESSAGE);
			ProgressDialog progressDlg = new ProgressDialog(shellProvider.getShell());
			progressDlg.setOperation(operation);
			progressDlg.setProgressMessage(progressMessage);
			progressDlg.open();

		}
	}
}
