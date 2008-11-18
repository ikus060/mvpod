package net.homeip.entreprisesmd.mvconv.gui;

import net.homeip.entreprisesmd.mvconv.core.job.JobQueue;
import net.homeip.entreprisesmd.mvconv.core.profile.ProfileList;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;

/**
 * This class are use to provide an easy way to access every content of the
 * view.
 * 
 * @author patapouf
 * 
 */
public interface IViewSite {

	/**
	 * Return the action context for this window.
	 * 
	 * @return
	 */
	ActionContext getActionContext();

	/**
	 * Return the global job queue of the application.
	 * 
	 * @return the job queue.
	 */
	JobQueue getJobQueue();

	/**
	 * Return the global mplayer wrapper of the application.
	 * 
	 * @return the mplayer wrapper.
	 */
	MPlayerWrapper getMplayer();

	/**
	 * Return the profile context for this window.
	 * 
	 * @return
	 */
	ProfileContext getProfileContext();

	/**
	 * Return the global profile list of the application.
	 * 
	 * @return the profile list.
	 */
	ProfileList getProfileList();

	/**
	 * Return the local video list of the window.
	 * 
	 * @return the video list.
	 */
	VideoList getVideoList();

}
