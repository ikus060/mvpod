package net.homeip.entreprisesmd.mvconv.gui;

import java.io.File;
import java.io.IOException;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.job.Job;
import net.homeip.entreprisesmd.mvconv.core.job.JobQueue;
import net.homeip.entreprisesmd.mvconv.core.job.JobQueueObserver;
import net.homeip.entreprisesmd.mvconv.core.profile.ProfileList;
import net.homeip.entreprisesmd.mvconv.gui.jobqueue.QueueWindow;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerNotFoundException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.window.WindowManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class start the application.
 * 
 * @author patapouf
 * 
 */
public final class Main {

	/**
	 * Version of the application. This value are replace by the ant script.
	 */
	public static final String APPLICATION_VERSION = "@Version@";
	
	/**
	 * Key preference to clear job when done.
	 */
	public static final String PREF_AUTO_CLEAR_JOBS = "pref.autoClearJobs";
	/**
	 * Key preference to replace file when converting.
	 */
	public static final String PREF_REPLACE_FILE = "pref.replaceFile";
	/**
	 * Key preference for last directory browse.
	 */
	public static final String PREF_LAST_DIRECTORY = "pref.lastDirectory";
	/**
	 * Key preference for mplayer directory.
	 */
	public static final String PREF_MPLAYER_DIRECTORY = "pref.mplayerDirectory";
	/**
	 * Key preference for mp4box directory.
	 */
	public static final String PREF_MP4BOX_DIRECTORY = "pref.mp4BoxDirectory";

	/**
	 * Preference file name.
	 */
	private static final String PREFERENCE_FILENAME = "iriverter.pref";
	/**
	 * Key value for user home directory.
	 */
	private static final String USER_HOME = "user.home";
	/**
	 * Key value for OS name property.
	 */
	private static final String OS_NAME = "os.name";
	/**
	 * Linux OS value.
	 */
	private static final String OS_NAME_LINUX = "Linux";
	/**
	 * Windows OS value.
	 */
	private static final String OS_NAME_WINDOWS = "Windows";
	
	/**
	 * Unique instance of this class.
	 */
	private static Main instance;

	/**
	 * Mplayer wrapper.
	 */
	private MPlayerWrapper mplayer;
	/**
	 * Job queue.
	 */
	private JobQueue jobQueue;
	/**
	 * Queue window.
	 */
	private QueueWindow queueWindow;

	/**
	 * Profile list.
	 */
	private ProfileList profileList;

	/**
	 * Preference store.
	 */
	private PreferenceStore preferenceStore;

	/**
	 * Windows manager
	 */
	private WindowManager winManager;

	/**
	 * Observer to job queue.
	 */
	private JobQueueObserver observer = new JobQueueObserver() {

		public void autoClearModeHasChanged(JobQueue queue) {
			getPreferenceStore().setValue(PREF_AUTO_CLEAR_JOBS,
					queue.getAutoClearMode());
		}

		public void jobQueueHasChanged(JobQueue queue) {

			if (queue.getJobCount() > 0) {
				
				QueueWindow win = getQueueWindow();
				if (win != null) {
					getWindowManager().add(win);
					win.open();
				}
				
			}

		}

		public void jobStatusHasChanged(JobQueue queue, Job job) {
			// Do nothing
		}

	};

	/**
	 * Private constructor.
	 */
	private Main() {

	}

	/**
	 * Entry point.
	 * 
	 * @param args
	 *            argument list.
	 */
	public static void main(String[] args) {

		instance = new Main();
		instance.run();
	}

	/**
	 * Return True if the windows can be closed.
	 * @param shell
	 * @return
	 */
	public boolean confirmQuit(Shell shell){
		JobQueue jobQueue = getJobQueue();
		Job[] jobs = jobQueue.getJobs();
		int count = 0;
		for(int index=0;index<jobs.length;index++){
			int status = jobQueue.getJobStatus(jobs[index]);
			if(status==JobQueue.STATUS_QUEUED || status==JobQueue.STATUS_IN_PROGRESS){
				count++;
			}
		}
		
		WindowManager winManager = Main.instance().getWindowManager();
		if (winManager.getWindowCount() == 1 && count>0) {
			// Show warning message
			String message = Localization
					.getString(Localization.JOB_QUEUE_CONFIRM_QUIT);

			int id = ErrorMessage.show(shell, message, SWT.ICON_QUESTION
					| SWT.YES | SWT.NO);
			if (id != SWT.YES) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Return the global JobQueue.
	 * 
	 * @return the job queue.
	 */
	public JobQueue getJobQueue() {

		if (jobQueue == null) {
			jobQueue = new JobQueue();
			jobQueue.addJobQueueObserver(observer);
			boolean clear = getPreferenceStore().getBoolean(
					PREF_AUTO_CLEAR_JOBS);
			jobQueue.setAutoClearMode(clear);
		}

		return jobQueue;

	}

	/**
	 * Return the mplayer wrapper.
	 * 
	 * @return the mplayer wrapper.
	 */
	public MPlayerWrapper getMPlayer() {
		return mplayer;
	}

	/**
	 * Return preference store.
	 * 
	 * @return preference store.
	 */
	public IPreferenceStore getPreferenceStore() {
		return preferenceStore;
	}

	/**
	 * Return the profile list.
	 * 
	 * @return the profile list.
	 */
	public ProfileList getProfileList() {

		if (profileList == null) {
			profileList = new ProfileList();
		}

		return profileList;
	}

	/**
	 * Return the unique window queue of the application.
	 * 
	 * @return the unique window queue.
	 */
	public QueueWindow getQueueWindow() {
		if (queueWindow == null) {
			queueWindow = new QueueWindow(jobQueue);
			queueWindow.setBlockOnOpen(false);
		}
		return queueWindow;
	}

	/**
	 * Return the application window manager.
	 * 
	 * @return the application window manager.
	 */
	public WindowManager getWindowManager() {
		if (winManager == null) {
			winManager = new WindowManager();
		}
		return winManager;
	}

	/**
	 * Return the unique instance of this class.
	 * 
	 * @return instance of this class.
	 */
	public static Main instance() {
		return instance;
	}

	/**
	 * Load mplayer wrapper.
	 */
	private void loadMPlayer() {
		
		IPreferenceStore store = getPreferenceStore();
		File path = new File(store.getString(PREF_MPLAYER_DIRECTORY));
		
		try {
			
			mplayer = new MPlayerWrapper(new File[]{path});
			
		} catch (MPlayerNotFoundException e) {
			ErrorMessage.showLocalizedError(null, Localization.MPLAYER_NOT_FOUND);
			return;
		}
	}

	/**
	 * Load application preference.
	 */
	private void loadPreference() {
		preferenceStore = new PreferenceStore(PREFERENCE_FILENAME);

		File preferenceFile = new File(PREFERENCE_FILENAME);
		if (preferenceFile.exists()) {
			try {
				preferenceStore.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Set default value
		String os = System.getProperty(OS_NAME);
		preferenceStore.setDefault(PREF_AUTO_CLEAR_JOBS, false);
		preferenceStore.setDefault(PREF_REPLACE_FILE, false);
		preferenceStore.setDefault(PREF_LAST_DIRECTORY, System.getProperty(USER_HOME));
		if(os.equals(OS_NAME_LINUX)){
			preferenceStore.setDefault(PREF_MPLAYER_DIRECTORY, "/usr/bin");
			preferenceStore.setDefault(PREF_MP4BOX_DIRECTORY, "/usr/bin");			
		} else if (os.equals(OS_NAME_WINDOWS)){
			preferenceStore.setDefault(PREF_MPLAYER_DIRECTORY, ".");
			preferenceStore.setDefault(PREF_MP4BOX_DIRECTORY, ".");
		} else {
			preferenceStore.setDefault(PREF_MPLAYER_DIRECTORY, ".");
			preferenceStore.setDefault(PREF_MP4BOX_DIRECTORY, ".");
		}

	}

	/**
	 * Open a mainWindow.
	 */
	private void openMainWindow() {

		MainWindow win = new MainWindow(null);
		getWindowManager().add(win);
		win.open();

	}

	/**
	 * Run the application.
	 */
	public void run() {

		// Set the application name
		Display.setAppName(Localization
				.getString(Localization.APPLICATION_NAME));

		// Load application preference
		loadPreference();

		// Load mplayer
		loadMPlayer();

		// Show the Main window
		openMainWindow();

		// Loop until all windows are closed
		Display display = Display.getCurrent();
		while (winManager.getWindowCount() > 0) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

		// Save preference
		savePreference();
		
		getJobQueue().stop();

	}

	/**
	 * Save application preferences.
	 */
	private void savePreference() {
		try {
			preferenceStore.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
