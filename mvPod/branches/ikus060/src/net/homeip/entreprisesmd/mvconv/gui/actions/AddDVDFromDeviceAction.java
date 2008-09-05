package net.homeip.entreprisesmd.mvconv.gui.actions;

import java.io.File;
import java.io.FileNotFoundException;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.gui.ErrorMessage;
import net.homeip.entreprisesmd.mvconv.gui.IVideoOutputFileProvider;
import net.homeip.entreprisesmd.mvconv.gui.MPlayerProvider;
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.DVDNotAvailableException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideoDVD;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Display;

/**
 * This action can be use to add a DVD from a DVD device.
 * 
 * @author patapouf
 * 
 */
public class AddDVDFromDeviceAction extends Action {

	/**
	 * Operation to add files.
	 */
	private class AddDVDOperation implements Operation {
		/**
		 * DVD device.
		 */
		String device;

		/**
		 * Create a new operation.
		 * 
		 * @param filePath
		 *            the file path (directory).
		 * @param fileNames
		 *            a list of files
		 */
		public AddDVDOperation(String device) {
			this.device = device;
		}

		public void cancel() {
			// Cannot cancel this opperation
		}

		public void start() {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					Video video = createInputOutputVideo(AddDVDOperation.this.device);

					if (video != null) {
						AddDVDFromDeviceAction.this.videoList
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
	public AddDVDFromDeviceAction(IShellProvider shellProvider,
			VideoList videoList, IVideoOutputFileProvider fileProvider,
			MPlayerProvider mplayerProvider) {

		super(Localization.getString(Localization.ACTION_ADD_DVD_FROM_DEVICE));

		setImageDescriptor(IconLoader.loadIcon(IconLoader.ICON_DVD_DEVICE_22));

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
	}

	/**
	 * Return an input/output video from the filename.
	 * 
	 * @param device
	 *            the dvd device
	 * @return an input/output video from the filename
	 */
	Video createInputOutputVideo(String device) {
		InputVideoDVD inputVideoDvd = null;
		try {

			inputVideoDvd = InputVideoDVD.fromDVDDevice(device);

		} catch (FileNotFoundException e) {
			ErrorMessage.showLocalizedError(this.shellProvider.getShell(),
					Localization.MPLAYER_DVDDEVICE_NOT_AVAILABLE, device);
			return null;
		} catch (DVDNotAvailableException e) {
			ErrorMessage.showMPlayerException(this.shellProvider.getShell(), e,
					null);
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

		String[] devices = InputVideoDVD.getAvailablesDVDDevice();

		DVDDeviceDialog dlg = new DVDDeviceDialog(this.shellProvider.getShell());
		dlg.setDeviceList(devices);
		dlg.open();

		String device = dlg.getDevice();

		if (device != null) {

			Operation operation = new AddDVDOperation(device);
			String progressMessage = Localization
					.getString(Localization.ACTION_ADD_DVD_FROM_DEVICE_PROGRESS_MESSAGE);
			ProgressDialog progressDlg = new ProgressDialog(this.shellProvider
					.getShell());
			progressDlg.setOperation(operation);
			progressDlg.setProgressMessage(progressMessage);
			progressDlg.open();

		}
	}
}
