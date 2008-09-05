package net.homeip.entreprisesmd.mvconv.core.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * 
 * @author patapouf
 * 
 */
public class JobQueue {

	/**
	 * In progress status.
	 */
	public static final int STATUS_IN_PROGRESS = 1;
	/**
	 * Job done status.
	 */
	public static final int STATUS_COMPLETED = 2;
	/**
	 * Error occur in the process.
	 */
	public static final int STATUS_FAILED = 3;
	/**
	 * In queue.
	 */
	public static final int STATUS_QUEUED = 4;
	/**
	 * In queue.
	 */
	public static final int STATUS_CANCELED = 5;
	
	
	/**
	 * The jobs queue.
	 */
	private List<Job> jobs = new ArrayList<Job>();
	/**
	 * List of status for each job.
	 */
	private Map<Job, Integer> jobStatus = new HashMap<Job, Integer>();
	/**
	 * Use to run the executer.
	 */
	private Thread thread;
	/**
	 * The current "in progress" job.
	 */
	private Job inProgressJob;

	/**
	 * To sync the multi thread operation.
	 */
	private final Semaphore available = new Semaphore(1, true);
	
	/**
	 * True to enable autoClear mode.
	 */
	private boolean autoClear = false;
	
	/**
	 * True when this class are dispose.
	 */
	private boolean closing = false;
	
	/**
	 * List of observers.
	 */
	private List<JobQueueObserver> observerList = new LinkedList<JobQueueObserver>();
	
	
	/**
	 * This Runnable are use to execute jobs in queue.
	 */
	private Runnable executer = new Runnable() {
		public void run() {

			inProgressJob = getNextJobToExecute();

			while (inProgressJob != null && !closing) {

				setJobStatus(inProgressJob, JobQueue.STATUS_IN_PROGRESS);

				// Run the job
				try {

					inProgressJob.run();

					if(inProgressJob.isCanceled()){
						setJobStatus(inProgressJob, JobQueue.STATUS_CANCELED);
					} else {
						setJobStatus(inProgressJob, JobQueue.STATUS_COMPLETED);	
					}
					
					

					if (autoClear) {
						remove(inProgressJob);
					}

				} catch (Exception e) {
					setJobStatus(inProgressJob, JobQueue.STATUS_FAILED);
				}

				inProgressJob = getNextJobToExecute();

			}

		}
	};

	/**
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() {

		closing = true;
		if (inProgressJob != null) {
			inProgressJob.cancel();
		}
		inProgressJob = null;

	}

	/**
	 * Remove every job with Completed status.
	 */
	public void clearCompletedJobs() {

		try {
			available.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		Job[] jobArray = new Job[0];
		jobArray = jobs.toArray(jobArray);

		for (int index = 0; index < jobArray.length; index++) {
			Job job = jobArray[index];
			if (_getJobStatus(job) == JobQueue.STATUS_COMPLETED) {
				jobs.remove(job);
				jobStatus.remove(job);
			}
		}

		available.release();

		notifyHasChanged();

	}

	/**
	 * Add the given observer to the collections of observers that will be
	 * notify when the Job queue change.
	 * 
	 * @param observer
	 *            the observer to add
	 */
	public void addJobQueueObserver(JobQueueObserver observer) {
		observerList.add(observer);
	}

	/**
	 * Append a job the the queue.
	 * 
	 * @param job
	 *            the job to queue.
	 */
	public void append(Job job) {

		// Add the job
		try {
			available.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		jobs.add(0, job);
		jobStatus.put(job, JobQueue.STATUS_QUEUED);
		available.release();

		// Notify observer
		notifyHasChanged();

		// Start the process
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(executer);
			thread.start();
		}

	}

	/**
	 * Return True if the auto clear mode are enabled.
	 * 
	 * @return True if the auto clear mode are enabled
	 */
	public boolean getAutoClearMode() {
		return autoClear;
	}

	/**
	 * Return jobs at given index.
	 * 
	 * @param index
	 *            the index.
	 * @return the jobs.
	 */
	public Job getJobAtIndex(int index) {
		return jobs.get(index);
	}

	/**
	 * Return the number of jobs in queue.
	 * 
	 * @return the number of job in queue.
	 */
	public int getJobCount() {
		return jobs.size();
	}

	/**
	 * Return a list of jobs in queue.
	 * 
	 * @return the list of jobs.
	 */
	public Job[] getJobs() {
		Job[] jobArray = new Job[0];
		return jobs.toArray(jobArray);
	}

	/**
	 * Return the status of the Jobs.
	 * 
	 * @param job
	 *            the job for witch we want the status.
	 * @return the job status.
	 */
	public int getJobStatus(Job job) {

		try {
			available.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			return -1;
		}

		int status = _getJobStatus(job);

		available.release();

		return status;

	}

	/**
	 * Return the status of the Jobs (not thread safe).
	 * 
	 * @param job
	 *            the job for witch we want the status.
	 * @return the job status.
	 */
	private int _getJobStatus(Job job) {

		Integer status = jobStatus.get(job);
		if (status != null) {
			return status;
		}
		return -1;
	}

	/**
	 * Return the next job to execute.
	 * 
	 * @return the job
	 */
	protected Job getNextJobToExecute() {

		try {
			available.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			return null;
		}

		// Find the first job to execute
		Job[] jobArray = new Job[0];
		jobArray = jobs.toArray(jobArray);

		int index = jobArray.length - 1;
		while (index >= 0
				&& _getJobStatus(jobArray[index]) != JobQueue.STATUS_QUEUED) {
			index--;
		}

		available.release();

		if (index >= 0) {
			return jobArray[index];
		}

		return null;
	}

	/**
	 * Notify every observer that the job queue has changed.
	 */
	protected void notifyHasChanged() {
		JobQueueObserver[] observers = new JobQueueObserver[observerList.size()];
		observers = observerList.toArray(observers);
		for(int index=0;index<observers.length;index++) {
			observers[index].jobQueueHasChanged(this);
		}
	}

	/**
	 * Notify every observer that status of a job has changed.
	 * 
	 * @param job
	 *            the job that changed.
	 */
	protected void notifyStatusHasChanged(Job job) {
		JobQueueObserver[] observers = new JobQueueObserver[observerList.size()];
		observers = observerList.toArray(observers);
		for(int index=0;index<observers.length;index++) {
			observers[index].jobStatusHasChanged(this, job);
		}
	}

	/**
	 * Notify every observer that the autoClear mode has changed.
	 */
	protected void notifyAutoClearModeHasChanged() {
		JobQueueObserver[] observers = new JobQueueObserver[observerList.size()];
		observers = observerList.toArray(observers);
		for(int index=0;index<observers.length;index++) {
			observers[index].autoClearModeHasChanged(this);
		}
	}

	/**
	 * Remove the given job from the list.
	 * 
	 * @param job
	 *            the job to remove from the queue.
	 * @throws JobInProgressException
	 *             if the given job are in progress.
	 */
	public void remove(Job job) throws JobInProgressException {

		// Remove jobs
		try {
			available.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		if (_getJobStatus(job) == JobQueue.STATUS_IN_PROGRESS) {
			throw new JobInProgressException();
		}
		jobs.remove(job);
		jobStatus.remove(job);
		available.release();

		// Notify observer
		notifyHasChanged();

	}

	/**
	 * Remove the given observer from the collections of observers.
	 * 
	 * @param observer
	 *            the observer to add
	 */
	public void removeJobQueueObserver(JobQueueObserver observer) {
		observerList.remove(observer);
	}

	/**
	 * Change the state of the auto remove options to automatically remove job
	 * that are done from the JobQueue list.
	 * 
	 * This methode doesn't remove job that are currently completed.
	 * 
	 * @see clearCompletedJobs
	 * 
	 * @param clear
	 *            True to enable auto clear mode
	 */
	public void setAutoClearMode(boolean clear) {
		this.autoClear = clear;
		notifyAutoClearModeHasChanged();
	}

	/**
	 * Change the status of a job.
	 * 
	 * @param job
	 *            the job.
	 * @param status
	 *            the new status.
	 */
	protected void setJobStatus(Job job, int status) {
		jobStatus.put(job, status);
		notifyStatusHasChanged(job);
	}
	
	/**
	 * Stop all process.
	 */
	public void stop(){
		
		closing = true;
		if (inProgressJob != null) {
			inProgressJob.cancel();
		}
		inProgressJob = null;
		
	}

}
