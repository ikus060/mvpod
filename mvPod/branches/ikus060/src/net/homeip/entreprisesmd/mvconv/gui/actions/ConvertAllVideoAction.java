package net.homeip.entreprisesmd.mvconv.gui.actions;

import java.io.File;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.job.ConvertJob;
import net.homeip.entreprisesmd.mvconv.core.job.Job;
import net.homeip.entreprisesmd.mvconv.core.job.JobQueue;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.core.video.VideoListObserver;
import net.homeip.entreprisesmd.mvconv.gui.ErrorMessage;
import net.homeip.entreprisesmd.mvconv.gui.MPlayerProvider;
import net.homeip.entreprisesmd.mvconv.gui.Main;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.IShellProvider;

/**
 * This Action are use to convert all video in the video list.
 * 
 * @author patapouf
 * 
 */
public class ConvertAllVideoAction extends Action {

	/**
	 * The video list.
	 */
	VideoList videoList;

	/**
	 * profile context.
	 */
	ProfileContext profileContext;

	/**
	 * Job queue.
	 */
	JobQueue jobQueue;

	/**
	 * Mplayer wrapper.
	 */
	MPlayerProvider mplayerProvider;

	/**
	 * Shell provider.
	 */
	IShellProvider shellProvider;

	/**
	 * Observer on the video list.
	 */
	VideoListObserver observer = new VideoListObserver() {
		public void listHasChanged(VideoList list) {
			ConvertAllVideoAction.this.listHasChanged();
		}
	};

	/**
	 * Create a new convert all video action.
	 * 
	 * @param shellProvider
	 *            the shell provider.
	 * @param profileContext
	 *            the profile context.
	 * @param list
	 *            the video list.
	 * @param mplayer
	 *            the mplayer wrapper
	 * @param jobQueue
	 *            the job queue.
	 */
	public ConvertAllVideoAction(IShellProvider shellProvider,
			ProfileContext profileContext, VideoList videoList,
			MPlayerProvider mplayerProvider, JobQueue jobQueue) {
		super(Localization.getString(Localization.ACTION_CONVERT_ALL));

		setImageDescriptor(IconLoader.loadIcon(IconLoader.ICON_CONVERT_22));

		if (shellProvider == null || profileContext == null
				|| videoList == null || mplayerProvider == null || jobQueue == null) {
			throw new NullPointerException();
		}

		this.shellProvider = shellProvider;
		this.profileContext = profileContext;
		this.videoList = videoList;
		this.mplayerProvider = mplayerProvider;
		this.jobQueue = jobQueue;

		videoList.addInputVideoListObserver(this.observer);

		listHasChanged();
	}

	/**
	 * Create a new convert job for the given video
	 * 
	 * @return
	 */
	Job createJobForVideo(MPlayerWrapper mplayer, Video video, EncodingOptions options) {

		// Create an encoding job
		InputVideo inputVideo = video.getInputVideo();
		File outputFile = video.getOutputFile();

		EncodingJob encodingJob;
		try {
			encodingJob = mplayer.encode(inputVideo, outputFile, options);
		} catch (MPlayerException e) {
			e.printStackTrace();
			ErrorMessage.showMPlayerException(this.shellProvider.getShell(), e,
					Localization.ACTION_CONVERT_FAILED);
			return null;
		}

		ConvertJob job = new ConvertJob(encodingJob, inputVideo, outputFile);

		return job;
	}

	/**
	 * Update the status of this action depending if the list are empty.
	 */
	void listHasChanged() {
		boolean enabled = false;
		if (this.videoList.getCount() > 0) {
			enabled = true;
		}
		this.setEnabled(enabled);
	}

	/**
	 * This implementation send every video present in the video list to the
	 * encoding job queue with the current encoding options.
	 */
	public void run() {

		// Check if mplayer exist
		MPlayerWrapper mplayer = this.mplayerProvider.getWrapper();
		if (mplayer == null) {
			ErrorMessage.showLocalizedError(this.shellProvider.getShell(),
					Localization.MPLAYER_NOT_FOUND);
			return;
		}
		
		// Retreive the video list
		Video[] videos = this.videoList.getVideos();

		// Check if we overwrite output file
		int index = 0;
		IPreferenceStore store = Main.instance().getPreferenceStore();
		boolean prompt = !store.getString(Main.PREF_REPLACE_FILE).equals(
				MessageDialogWithToggle.ALWAYS);
		while (index < videos.length && prompt) {

			if (videos[index].getOutputFile().exists()) {

				File outputFile = videos[index].getOutputFile();

				ReplaceFileMessageDialog dlg = new ReplaceFileMessageDialog(
						this.shellProvider.getShell(), outputFile.getName(), false);
				dlg.setPrefKey(Main.PREF_REPLACE_FILE);
				dlg.setPrefStore(Main.instance().getPreferenceStore());

				int idButton = dlg.open();
				if (idButton != ReplaceFileMessageDialog.REPLACE_ID) {
					return;
				}

				prompt = !store.getString(Main.PREF_REPLACE_FILE).equals(
						MessageDialogWithToggle.ALWAYS);

			}

			index++;
		}

		// Start to create the convert job
		EncodingOptions options = this.profileContext.getSelectedProfile()
				.getEncodingOptions();

		for (index = 0; index < videos.length; index++) {

			// Create the job
			Job job = createJobForVideo(mplayer, videos[index], options);
			if (job == null) {
				return;
			}

			// Remove the vide from the list
			this.videoList.removeVideo(videos[index]);
			// Append the job to the jobqueue
			this.jobQueue.append(job);

		}

	}

}
