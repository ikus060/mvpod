package net.homeip.entreprisesmd.mvconv.gui.actions;

import java.io.File;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.job.ConvertJob;
import net.homeip.entreprisesmd.mvconv.core.job.Job;
import net.homeip.entreprisesmd.mvconv.core.job.JobQueue;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.gui.ActionContext;
import net.homeip.entreprisesmd.mvconv.gui.ErrorMessage;
import net.homeip.entreprisesmd.mvconv.gui.IActionContextListener;
import net.homeip.entreprisesmd.mvconv.gui.MPlayerProvider;
import net.homeip.entreprisesmd.mvconv.gui.Main;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;

/**
 * This action are use to play the selected Video in mplayer with the current
 * encoding options.
 * 
 * @author patapouf
 * 
 */
public class ConvertVideoAction extends Action {

	/**
	 * Action context to execute the action.
	 */
	private ActionContext actionContext;
	/**
	 * Mplayer wrapper.
	 */
	private MPlayerProvider mplayerProvider;
	/**
	 * The video list.
	 */
	private VideoList videoList;

	/**
	 * profile context.
	 */
	private ProfileContext profileContext;

	/**
	 * Job queue.
	 */
	private JobQueue jobQueue;
	/**
	 * Shell provider to obtain a shell.
	 */
	private IShellProvider shellProvider;

	/**
	 * Listener to the action context.
	 */
	private IActionContextListener listener = new IActionContextListener() {
		public void contextChanged(ActionContext context) {
			ConvertVideoAction.this.contextChanged(context);
		}
	};

	/**
	 * Create a new preview action.
	 * 
	 * @param shellProvider
	 *            a shell provider to obtain a shell.
	 * @param actionContext
	 *            the action context to execute the action.
	 */
	public ConvertVideoAction(IShellProvider shellProvider,
			ActionContext actionContext, ProfileContext profileContext,
			VideoList videoList, MPlayerProvider mplayerProvider, JobQueue jobQueue) {

		super(Localization.getString(Localization.ACTION_CONVERT));

		if (shellProvider == null || actionContext == null
				|| profileContext == null || videoList == null
				|| mplayerProvider == null || jobQueue == null) {
			throw new NullPointerException();
		}

		actionContext.addActionContextListener(this.listener);

		contextChanged(actionContext);

		this.shellProvider = shellProvider;
		this.actionContext = actionContext;
		this.profileContext = profileContext;
		this.videoList = videoList;
		this.mplayerProvider = mplayerProvider;
		this.jobQueue = jobQueue;
	}

	/**
	 * Use to determine if this action are enabled or not depending of the
	 * selection.
	 * 
	 * @param context
	 *            the Action context.
	 */
	void contextChanged(ActionContext context) {
		boolean enabled = false;
		if (context.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) context
					.getSelection();
			Object element = selection.getFirstElement();
			if (element instanceof Video) {
				enabled = true;
			}
		}
		this.setEnabled(enabled);
	}

	/**
	 * Create a new convert job for the given video
	 * 
	 * @return
	 */
	private Job createJobForVideo(MPlayerWrapper mplayer, Video video, EncodingOptions options) {

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
	 * This implementation of the method show a preview of the selected Video.
	 */
	public void run() {

		// Check if mplayer exist
		MPlayerWrapper mplayer = this.mplayerProvider.getWrapper();
		if (mplayer == null) {
			ErrorMessage.showLocalizedError(this.shellProvider.getShell(),
					Localization.MPLAYER_NOT_FOUND);
			return;
		}
		
		// Get selected video
		Video video = null;
		if (this.actionContext.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) this.actionContext
					.getSelection();
			Object element = selection.getFirstElement();
			if (element instanceof Video) {
				video = (Video) element;
			}
		}

		if (video == null) {
			return;
		}

		// Check if we overwrite output file
		File outputFile = video.getOutputFile();
		if (outputFile.exists()) {

			String toogle = Main.instance().getPreferenceStore()
					.getString(Main.PREF_REPLACE_FILE);

			if (!toogle.equals(MessageDialogWithToggle.ALWAYS)) {

				ReplaceFileMessageDialog dlg = new ReplaceFileMessageDialog(
						this.shellProvider.getShell(), outputFile.getName(), false);
				dlg.setPrefKey(Main.PREF_REPLACE_FILE);
				dlg.setPrefStore(Main.instance().getPreferenceStore());

				int idButton = dlg.open();
				if (idButton != ReplaceFileMessageDialog.REPLACE_ID) {
					return;
				}
			}
		}

		// Start to create the convert job
		EncodingOptions options;
		options = this.profileContext.getSelectedProfile().getEncodingOptions();

		// Create the job
		Job job = createJobForVideo(mplayer, video, options);
		if (job == null) {
			return;
		}

		// Remove the vide from the list
		this.videoList.removeVideo(video);
		// Append the job to the jobqueue
		this.jobQueue.append(job);

	}
}
