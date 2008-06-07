package net.homeip.entreprisesmd.mvconv.core.job;

import java.io.File;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.VideoInfoFormater;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingProgressObserver;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;

import org.eclipse.swt.program.Program;

/**
 * This class adapte the EncodingJob to the Job interface to be manage by a
 * JobQueue.
 * 
 * @author patapouf
 * 
 */
public class ConvertJob extends AbstractJob {
	/**
	 * True if the job has been canceled.
	 */
	private boolean canceled;
	/**
	 * The encoding job adapted.
	 */
	private EncodingJob encodingJob;
	/**
	 * Failure description.
	 */
	private String failedDescription = "";
	/**
	 * Job description.
	 */
	private String description;

	/**
	 * observer of the encoding job.
	 */
	private EncodingProgressObserver observer = new EncodingProgressObserver() {
		public void progressHasChanged(EncodingJob job) {
			ConvertJob.this.notifyObservers();
		}
	};

	/**
	 * Output file.
	 */
	private File outputFile;

	/**
	 * Create a new Convert job.
	 * 
	 * @param encodingJob
	 *            the adapted encoding job
	 * @param description
	 *            a description of the job
	 * @param outputFile
	 *            the resulting ouput file
	 */
	public ConvertJob(EncodingJob encodingJob, InputVideo inputVideo,
			File outputFile) {

		if (encodingJob == null || inputVideo == null || outputFile == null) {
			throw new NullPointerException();
		}

		this.encodingJob = encodingJob;
		this.outputFile = outputFile;

		// Create a description
		String source = VideoInfoFormater.formatInputVideo(inputVideo);
		String destination = outputFile.getName();
		this.description = Localization.getString(
				Localization.CONVERTJOB_DESCRIPTION, source, destination);

	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#cancel()
	 */
	public void cancel() {
		canceled = true;
		encodingJob.cancel();
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#getFailedDescription()
	 */
	public String getFailedDescription() {
		return failedDescription;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.AbstractJob#getPercentCompleted()
	 */
	public int getPercentCompleted() {
		return encodingJob.getPercentCompleted();
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#getProgressDescription()
	 */
	public String getProgressDescription() {

		int timeRemain = encodingJob.getTimeRemaining();

		String remain;
		if (timeRemain > 0) {
			remain = VideoInfoFormater.formatDuration(timeRemain);
		} else {
			remain = Localization
					.getString(Localization.CONVERTJOB_LESS_THAN_A_MINUTE);
		}

		String desc = Localization.getString(
				Localization.CONVERTJOB_PROGRESS_DESCRIPTION, encodingJob
						.getFrameRate(), remain);

		return desc;
	}
	
	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.job.Job#isCanceled()
	 */
	public boolean isCanceled(){
		return canceled;
	}
	
	/**
	 * This implementation open the video file in default video player of the
	 * operating system.
	 * 
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#open()
	 */
	public void open() {

		Program.launch(outputFile.getAbsolutePath());

	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#run()
	 */
	public void run() throws JobFailureException {

		boolean failed = false;
		encodingJob.addProgressObserver(observer);

		try {

			encodingJob.start();

		} catch (MPlayerException e) {
			e.printStackTrace();
			failedDescription = Localization.getString(
					Localization.CONVERTJOB_FAILED_DESCRIPTION, e.getMessage());
			failed = true;
		}

		encodingJob.addProgressObserver(observer);

		if (failed) {
			throw new JobFailureException(failedDescription);
		}

	}

}
