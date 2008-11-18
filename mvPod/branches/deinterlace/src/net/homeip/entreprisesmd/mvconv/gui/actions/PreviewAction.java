package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.gui.ActionContext;
import net.homeip.entreprisesmd.mvconv.gui.ErrorMessage;
import net.homeip.entreprisesmd.mvconv.gui.IActionContextListener;
import net.homeip.entreprisesmd.mvconv.gui.MPlayerProvider;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiofilter.AudioFilter;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;

/**
 * This action are use to play the selected Video in mplayer with the current
 * encoding options.
 * 
 * @author patapouf
 * 
 */
public class PreviewAction extends Action {

	/**
	 * Action context to execute the action.
	 */
	private ActionContext actionContext;
	/**
	 * Mplayer provider.
	 */
	private MPlayerProvider mplayerProvider;
	/**
	 * Profile context to use.
	 */
	private ProfileContext profileContext;
	/**
	 * Shell provider to obtain a shell.
	 */
	private IShellProvider shellProvider;

	/**
	 * Listener to the action context.
	 */
	private IActionContextListener listener = new IActionContextListener() {
		public void contextChanged(ActionContext context) {
			PreviewAction.this.contextChanged(context);
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
	public PreviewAction(IShellProvider shellProvider,
			ActionContext actionContext, ProfileContext profileContext,
			MPlayerProvider mplayerProvider) {

		super(Localization.getString(Localization.ACTION_PREVIEW));

		setImageDescriptor(IconLoader.loadIcon(IconLoader.ICON_PREVIEW_22));

		if (shellProvider == null) {
			throw new NullPointerException();
		}
		if (actionContext == null) {
			throw new NullPointerException();
		}

		actionContext.addActionContextListener(listener);

		contextChanged(actionContext);

		this.shellProvider = shellProvider;
		this.actionContext = actionContext;
		this.profileContext = profileContext;
		this.mplayerProvider = mplayerProvider;
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
	 * This implementation of the method show a preview of the selected Video.
	 */
	public void run() {

		// Check if mplayer exist
		MPlayerWrapper mplayer = mplayerProvider.getWrapper();
		if (mplayer == null) {
			ErrorMessage.showLocalizedError(shellProvider.getShell(),
					Localization.MPLAYER_NOT_FOUND);
			return;
		}

		// Get selected video
		Video video = null;
		if (actionContext.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) actionContext
					.getSelection();
			Object element = selection.getFirstElement();
			if (element instanceof Video) {
				video = (Video) element;
			}
		}
		if (video == null) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profileContext.getSelectedProfile()
				.getEncodingOptions();

		PlayingOptions playOptions = new PlayingOptions();
		EncodingOptions encodeOptions = options;
		if (encodeOptions != null) {

			AudioFilter[] audioFilters = encodeOptions.getAudioFilter();
			for (int index = 0; index < audioFilters.length; index++) {
				playOptions.addAudioFilter(audioFilters[index]);
			}

			VideoFilter[] videoFilters = encodeOptions.getVideoFilter();
			for (int index = 0; index < videoFilters.length; index++) {
				playOptions.addVideoFilter(videoFilters[index]);
			}

			playOptions.setScaleOptions(encodeOptions.getScaleOptions());

		}

		// Convert encoding options to playing options
		PlayingJob job = null;
		try {
			job = mplayer.play(video.getInputVideo(), playOptions);
		} catch (MPlayerException e) {
			e.printStackTrace();
			ErrorMessage.showMPlayerException(shellProvider.getShell(), e,
					Localization.PREVIEW_FAILED);
		}

		// Playing selected video
		if (job != null) {
			PreviewDialog dlg = new PreviewDialog(shellProvider.getShell());
			dlg.setPlayingJob(job);
			dlg.setBlockOnOpen(true);
			dlg.open();
		}

	}
}
