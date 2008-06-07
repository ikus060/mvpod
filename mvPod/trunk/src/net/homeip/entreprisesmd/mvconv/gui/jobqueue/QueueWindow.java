package net.homeip.entreprisesmd.mvconv.gui.jobqueue;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.job.Job;
import net.homeip.entreprisesmd.mvconv.core.job.JobInProgressException;
import net.homeip.entreprisesmd.mvconv.core.job.JobObserver;
import net.homeip.entreprisesmd.mvconv.core.job.JobQueue;
import net.homeip.entreprisesmd.mvconv.core.job.JobQueueObserver;
import net.homeip.entreprisesmd.mvconv.gui.ErrorMessage;
import net.homeip.entreprisesmd.mvconv.gui.Main;
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This windows show up to the user a list of all encoding job in progress or
 * done.
 * 
 * @author patapouf
 * 
 */
public class QueueWindow extends Dialog {
	/**
	 * Minimum window width.
	 */
	private static final int MIN_WIDTH = 370;
	/**
	 * Minimum window height.
	 */
	private static final int MIN_HEIGHT = 275;
	/**
	 * Initial window width.
	 */
	private static final int INIT_WIDTH = 370;
	/**
	 * Initial window height.
	 */
	private static final int INIT_HEIGHT = 440;
	/**
	 * Clear button identifier.
	 */
	private static final int CLEAR_ID = 89;
	/**
	 * Key value to store job data.
	 */
	private static final String JOB_DATA = "JobData";

	/**
	 * Count number of runnable.
	 */
	private int runnableCount = 0;

	/**
	 * The queue list component.
	 */
	private QueueList queueList;
	/**
	 * The check box component.
	 */
	private Button clearCheckBox;
	/**
	 * The observed job queue.
	 */
	private JobQueue jobQueue;
	/**
	 * The observer of job queue.
	 */
	private JobQueueObserver jobQueueObserver = new JobQueueObserver() {

		public void jobQueueHasChanged(JobQueue queue) {
			runnableCount++;
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					updateJobList();
					runnableCount--;
				}
			});
		}

		public void jobStatusHasChanged(JobQueue queue, final Job job) {
			updateJobStatus(job);
		}

		public void autoClearModeHasChanged(JobQueue queue) {
			if (clearCheckBox.getSelection() != queue.getAutoClearMode()) {
				clearCheckBox.setSelection(queue.getAutoClearMode());
			}
		}
	};
	/**
	 * Observer to all job.
	 */
	private JobObserver jobObserver = new JobObserver() {

		public void jobProgressHasChanged(final Job job) {

			runnableCount++;
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					updateJobProgress(job);
					runnableCount--;
				}
			});
		}

	};

	/**
	 * Listener to all queue item.
	 */
	private QueueItemListener queueItemListener = new QueueItemListener() {

		public void actionSelected(int action, SelectionEvent event) {
			handleQueueItemActionSelected(action, event);
		}

	};

	/**
	 * Create a new Queue list windows.
	 * 
	 * @param jobQueue
	 *            the Job queue to observer.
	 */
	public QueueWindow(JobQueue jobQueue) {

		super((Shell) null);
		setShellStyle(SWT.SHELL_TRIM);

		if (jobQueue == null) {
			throw new NullPointerException();
		}

		this.jobQueue = jobQueue;

	}

	/**
	 * Handle button press.
	 * 
	 * @param buttonId
	 *            the unique identifier of the pressed button.
	 */
	protected void buttonPressed(int buttonId) {
		if (buttonId == CLEAR_ID) {
			jobQueue.clearCompletedJobs();
		} else if (buttonId == IDialogConstants.CLOSE_ID) {
			this.close();
		}
	}

	/**
	 * This methode remove any reference to the JobQueue if the windows closing.
	 * 
	 * @return true if the window is (or was already) closed, and false if it is
	 *         still open
	 */
	public boolean close() {

		// TODO : Find a more fancy way to do this confirmation.
		boolean quit = Main.instance().confirmQuit(getShell());
		if (!quit) {
			return false;
		}

		boolean closed = super.close();
		if (closed) {
			jobQueue.removeJobQueueObserver(jobQueueObserver);
		}
		return closed;
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);

		shell.setMinimumSize(MIN_WIDTH, MIN_HEIGHT);
		shell.setSize(INIT_WIDTH, INIT_HEIGHT);

		shell.setText(Localization
				.getString(Localization.JOB_QUEUE_WINDOW_TITLE));

		Image image16 = IconLoader.loadIcon(IconLoader.ICON_APP_16)
				.createImage();
		Image image22 = IconLoader.loadIcon(IconLoader.ICON_APP_22)
				.createImage();
		Image image32 = IconLoader.loadIcon(IconLoader.ICON_APP_32)
				.createImage();
		Image image64 = IconLoader.loadIcon(IconLoader.ICON_APP_64)
				.createImage();
		shell.setImages(new Image[] { image16, image22, image32, image64 });
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1; // this is incremented by createButton
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		composite.setFont(parent.getFont());

		Control ctrl = createClearCheck(composite);
		ctrl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		// Add the buttons to the button bar.
		createButton(composite, CLEAR_ID, Localization
				.getString(Localization.JOB_QUEUE_WINDOW_CLEAR_BUTTON), false);
		createButton(composite, IDialogConstants.CLOSE_ID,
				IDialogConstants.CLOSE_LABEL, true);

		return composite;
	}

	/**
	 * Create the clear check box.
	 * 
	 * @param parent
	 *            the composite parent of this component.
	 * @return the clear check box.
	 */
	protected Control createClearCheck(Composite parent) {

		((GridLayout) parent.getLayout()).numColumns++;

		clearCheckBox = new Button(parent, SWT.CHECK);
		clearCheckBox.setText(Localization
				.getString(Localization.JOB_QUEUE_WINDOW_CLEAR_COMPLETED));
		clearCheckBox.setSelection(jobQueue.getAutoClearMode());

		clearCheckBox.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				jobQueue.setAutoClearMode(clearCheckBox.getSelection());
			}

		});

		return clearCheckBox;

	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {

		queueList = new QueueList(parent, SWT.BORDER);
		queueList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		updateJobList();
		this.jobQueue.addJobQueueObserver(jobQueueObserver);

		// Add Dispose instruction
		parent.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {

				// Remove
				jobQueue.removeJobQueueObserver(jobQueueObserver);

				// Remove observer from all job
				for (int index = 0; index < queueList.getItemCount(); index++) {
					Job job = (Job) queueList.getItem(index).getData(JOB_DATA);
					if (job != null) {
						job.removeJobObserver(jobObserver);
					}
				}

			}
		});

		return queueList;
	}

	/**
	 * Return the associated QueueItem for a Job.
	 * 
	 * @param job
	 *            the job.
	 * @return the queue item.
	 */
	private QueueItem getQueueItemForJob(Job job) {
		int index = 0;
		while (index < queueList.getItemCount()
				&& queueList.getItem(index).getData(JOB_DATA) != job) {
			index++;
		}

		if (index < queueList.getItemCount()) {
			return queueList.getItem(index);
		}
		return null;
	}

	/**
	 * Use to handle the user click on an action (in a queueitem).
	 * 
	 * @param action
	 *            the action clicked
	 * @param event
	 *            the selection event.
	 */
	private void handleQueueItemActionSelected(int action, SelectionEvent event) {

		QueueItem item = (QueueItem) event.widget;
		Job job = (Job) item.getData(JOB_DATA);
		int status = jobQueue.getJobStatus(job);

		if (status == JobQueue.STATUS_QUEUED) {

			try {
				jobQueue.remove(job);
			} catch (JobInProgressException e) {
				// Not supposed to happen
				e.printStackTrace();
			}

		} else if (status == JobQueue.STATUS_IN_PROGRESS) {

			job.cancel();

		} else if (status == JobQueue.STATUS_COMPLETED) {

			if (action == 1) {
				try {
					jobQueue.remove(job);
				} catch (JobInProgressException e) {
					// Not supposed to happen
					e.printStackTrace();
				}
			} else {
				job.open();
			}

		} else if (status == JobQueue.STATUS_FAILED) {

			if (action == 1) {
				try {
					jobQueue.remove(job);
				} catch (JobInProgressException e) {
					// Not supposed to happen
					e.printStackTrace();
				}
			} else {
				ErrorMessage.showError(getShell(), job.getFailedDescription());
			}

		} else if (status == JobQueue.STATUS_CANCELED) {
			if (action == 1) {
				try {
					jobQueue.remove(job);
				} catch (JobInProgressException e) {
					// Not supposed to happen
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Update the list of job.
	 */
	private void updateJobList() {

		Job[] jobs = jobQueue.getJobs();
		for (int index = 0; index < jobs.length; index++) {

			QueueItem item;
			Job job;
			if (index < queueList.getItemCount()) {
				item = queueList.getItem(index);
			} else {
				item = new QueueItem(queueList, SWT.NONE);
				item.addQueueItemListener(queueItemListener);
			}

			job = (Job) item.getData(JOB_DATA);
			if (jobs[index] != job) {
				job = jobs[index];
				item.setData(JOB_DATA, job);

				item.setDescription(job.getDescription());
				updateJobStatus(job);
			}
		}

		if (queueList.getItemCount() > jobs.length) {
			int count = queueList.getItemCount();
			for (int index = jobs.length; index < count; index++) {
				queueList.getItem(jobs.length).dispose();
			}
		}

	}

	/**
	 * Update the progress information of the given Job.
	 * 
	 * @param job
	 *            the job.
	 */
	private void updateJobProgress(Job job) {

		QueueItem queueItem = getQueueItemForJob(job);
		if (queueItem == null) {
			return;
		}

		queueItem.setPercentCompleted(job.getPercentCompleted());
		queueItem.setProgressDescription(job.getProgressDescription());
	}

	/**
	 * Update the status of the given Job.
	 * 
	 * @param job
	 *            the job.
	 */
	private void updateJobStatus(final Job job) {

		int status = jobQueue.getJobStatus(job);
		if (status == JobQueue.STATUS_QUEUED) {
			runnableCount++;
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					QueueItem queueItem = getQueueItemForJob(job);
					queueItem.setAction1Description(Localization
							.getString(Localization.JOB_QUEUE_WINDOW_REMOVE));
					queueItem.setAction2Description("");
					queueItem.setProgressDescription(Localization
							.getString(Localization.JOB_QUEUE_WINDOW_QUEUED));
					queueItem.setPercentCompleted(-1);
					runnableCount--;
				}
			});

			job.removeJobObserver(jobObserver);

		} else if (status == JobQueue.STATUS_IN_PROGRESS) {
			runnableCount++;
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					QueueItem queueItem = getQueueItemForJob(job);
					queueItem.setAction1Description(Localization
							.getString(Localization.JOB_QUEUE_WINDOW_CANCEL));
					queueItem.setAction2Description("");
					queueItem.setProgressDescription("");
					updateJobProgress(job);
					runnableCount--;
				}
			});

			job.addJobObserver(jobObserver);

		} else if (status == JobQueue.STATUS_COMPLETED) {

			runnableCount++;
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					QueueItem queueItem = getQueueItemForJob(job);
					queueItem.setAction1Description(Localization
							.getString(Localization.JOB_QUEUE_WINDOW_CLEAR));
					queueItem.setAction2Description(Localization
							.getString(Localization.JOB_QUEUE_WINDOW_OPEN));
					queueItem
							.setProgressDescription(Localization
									.getString(Localization.JOB_QUEUE_WINDOW_COMPLETED));
					queueItem.setPercentCompleted(-1);
					runnableCount--;
				}
			});

			job.removeJobObserver(jobObserver);

		} else if (status == JobQueue.STATUS_FAILED) {

			runnableCount++;
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					QueueItem queueItem = getQueueItemForJob(job);
					queueItem.setAction1Description(Localization
							.getString(Localization.JOB_QUEUE_WINDOW_CLEAR));
					queueItem.setAction2Description(Localization
							.getString(Localization.JOB_QUEUE_WINDOW_MOREINFO));
					queueItem.setProgressDescription(Localization
							.getString(Localization.JOB_QUEUE_WINDOW_FAILED));
					queueItem.setPercentCompleted(-1);
					runnableCount--;
				}
			});

			job.removeJobObserver(jobObserver);

		} else if (status == JobQueue.STATUS_CANCELED) {

			runnableCount++;
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					QueueItem queueItem = getQueueItemForJob(job);
					queueItem.setAction1Description(Localization
							.getString(Localization.JOB_QUEUE_WINDOW_CLEAR));
					queueItem.setAction2Description("");
					queueItem.setProgressDescription(Localization
							.getString(Localization.JOB_QUEUE_WINDOW_CANCELED));
					queueItem.setPercentCompleted(-1);
					runnableCount--;
				}
			});

			job.removeJobObserver(jobObserver);

		}

	}

}
