package net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingCommand;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingProgressObserver;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.ErrorParser;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.StreamParser;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.StreamReader;

/**
 * This encoding job are use as default to execute the mplayer command for one
 * pass mode or tow pass mode.
 * 
 * @author patapouf
 * 
 */
public class DefaultEncodingJob implements EncodingJob {

	/**
	 * Factor of 60 to convert min to second.
	 */
	private static final int MIN_TO_SEC_FACTOR = 60;

	/**
	 * Define the delay between each notification to listener.
	 */
	private static final int UPDATE_DELAY = 1000;

	/**
	 * The mplayer wrapper to use.
	 */
	private MPlayerWrapper mplayer;
	/**
	 * The mencoder process.
	 */
	private Process proc;
	/**
	 * The command to execute.
	 */
	private EncodingCommand command1;
	/**
	 * The second command to execute in two pass mode.
	 */
	private EncodingCommand command2;
	/**
	 * Pass log file.
	 */
	private File passFile;
	/**
	 * True if the first command are executed
	 */
	private boolean command1Completed;
	/**
	 * The observer list.
	 */
	private List<EncodingProgressObserver> observers = new ArrayList<EncodingProgressObserver>();

	/**
	 * The last notification time.
	 */
	long lastUpdateTime = 0;
	/**
	 * The total length of the video.
	 */
	int length;
	/**
	 * The percent completed.
	 */
	int percent;
	/**
	 * Time remaining in second. (in sec)
	 */
	int timeRemaining;
	/**
	 * Maximum time remaining. (in sec)
	 */
	int maximumTimeRemaining = 60;
	/**
	 * The frame rate treat.
	 */
	double frameRate;
	/**
	 * The standard input stream reader.
	 */
	private StreamReader inputStream;
	/**
	 * The error input stream reader.
	 */
	private StreamReader errorStream;
	/**
	 * True if process are abort.
	 */
	private boolean canceled;

	/**
	 * The stream parser to handle progress notification.
	 */
	private StreamParser parser = new StreamParser() {

		/**
		 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.StreamParser#parseLine(java.lang.String)
		 */
		public boolean parseLine(String line) {

			Matcher matcher;
			if (line.indexOf("Video stream:") > -1) { //$NON-NLS-1$
				matcher = Pattern.compile("[0-9.]* secs").matcher(line); //$NON-NLS-1$
				matcher.find();
				DefaultEncodingJob.this.length = (int) Double.parseDouble(matcher.group().substring(0,
						matcher.group().indexOf(' ')));
			}

			if (System.currentTimeMillis()
					- DefaultEncodingJob.this.lastUpdateTime > UPDATE_DELAY) {
				// Percent
				matcher = Pattern.compile("([0-9]*)%").matcher(line); //$NON-NLS-1$
				if (matcher.find()) {
					DefaultEncodingJob.this.percent = Integer.parseInt(matcher.group(1));
				}

				// Time remaining
				matcher = Pattern.compile("([0-9]*)min").matcher(line); //$NON-NLS-1$
				if (matcher.find()) {
					DefaultEncodingJob.this.timeRemaining = Integer.parseInt(matcher.group(1))
							* MIN_TO_SEC_FACTOR;
					DefaultEncodingJob.this.maximumTimeRemaining = Math.max(
							DefaultEncodingJob.this.maximumTimeRemaining,
							DefaultEncodingJob.this.timeRemaining);
				}

				// Frame per seconde
				matcher = Pattern.compile("([0-9.]*)fps").matcher(line); //$NON-NLS-1$
				if (matcher.find()) {
					try {
						DefaultEncodingJob.this.frameRate = Double.parseDouble(matcher.group(1));
					} catch (NumberFormatException e) {
						/*
						 * mplayer return alot of garbage so in case of error
						 * don't fail.
						 */
					}

				}

				// Notify observer
				notifyObservers();

				DefaultEncodingJob.this.lastUpdateTime = System.currentTimeMillis();
			}

			return true;
		}

	};

	/**
	 * Create a new simple encoding job.
	 * 
	 * @param mplayer
	 *            the mplayer instance that might be use to execute the job.
	 * @param command
	 *            the command to execute.
	 */
	public DefaultEncodingJob(MPlayerWrapper mplayer, EncodingCommand command) {

		if (mplayer == null || command == null) {
			throw new NullPointerException();
		}

		this.mplayer = mplayer;
		this.command1 = command;
		this.command2 = null;
	}

	/**
	 * Create a new multiple pass encoding job.
	 * 
	 * @param mplayer
	 *            the mplayer instance that might be use to execute the job.
	 * @param command1
	 *            the first command to execute.
	 * @param command2
	 *            the second command to execute for second pass.
	 */
	public DefaultEncodingJob(MPlayerWrapper mplayer, EncodingCommand command1,
			EncodingCommand command2, File passFile) {
		if (mplayer == null || command1 == null || command2 == null
				|| passFile == null) {
			throw new NullPointerException();
		}

		this.mplayer = mplayer;
		this.command1 = command1;
		this.command2 = command2;
		this.passFile = passFile;
	}

	/**
	 * Add an observer to this Job.
	 * 
	 * @param observer
	 *            the observer to add.
	 */
	public void addProgressObserver(EncodingProgressObserver observer) {
		this.observers.add(observer);
	}

	/**
	 * Cancel the job.
	 */
	public void cancel() {
		// Terminate process
		if (this.proc != null) {

			this.canceled = true;

			if (this.inputStream != null) {
				this.inputStream.stop();
			}
			if (this.errorStream != null) {
				this.errorStream.stop();
			}

			this.proc.destroy();
		}
	}

	/**
	 * Execute the given command in a progress and handle any error that can
	 * occur whiting this process.
	 * 
	 * @param command
	 *            the command to execute.
	 * @throws MPlayerException
	 *             when any error occur.
	 */
	private void executeCommand(EncodingCommand command)
			throws MPlayerException {

		try {
			this.proc = this.mplayer.mencode(command.toStringArray());
		} catch (IOException ioe) {
			throw new MPlayerException("Can't run mencoder process", ioe); //$NON-NLS-1$
		}

		ErrorParser errorParser = new ErrorParser() {
			public boolean exceptionFound(MPlayerException exception) {
				super.exceptionFound(exception);
				cancel();
				return false;
			}
		};
		this.inputStream = new StreamReader(this.proc.getInputStream(), this.parser, false);
		this.errorStream = new StreamReader(this.proc.getErrorStream(), errorParser, true);
		this.inputStream.readInThread();
		this.errorStream.readInThread();

		int exitCode = 1;
		try {
			exitCode = this.proc.waitFor();
		} catch (InterruptedException ie) {
			this.inputStream = null;
			this.errorStream = null;
			throw new MPlayerException("mencoder process are interrupted", ie); //$NON-NLS-1$
		}

		String error = this.errorStream.toString();
		this.inputStream = null;
		this.errorStream = null;

		// Check if there any revelant error
		errorParser.throwException();

		// Check the return value
		if (exitCode > 0 && !this.canceled) {
			throw new MPlayerException(error);
		}

	}

	/**
	 * Return the average frame rate.
	 * 
	 * @return the frame rate.
	 */
	public double getFrameRate() {
		return this.frameRate;
	}

	/**
	 * Return the length.
	 * 
	 * @return the length value.
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * Return the percentage of task that are completed.
	 * 
	 * @return the percent completed.
	 */
	public int getPercentCompleted() {

		// Handle the case when there is tow command to execute
		if (this.command2 != null) {

			if (this.command1Completed) {
				return this.percent / 2 + 50;
			}
			return this.percent / 2;
		}
		return this.percent;
	}

	/**
	 * Return the average number of second remaining to complete this task.
	 * 
	 * @return the average number of second remaining.
	 */
	public int getTimeRemaining() {

		// Handle the case when there is tow command to execute
		if (this.command2 != null) {

			if (this.command1Completed) {
				return this.timeRemaining;
			}
			return this.maximumTimeRemaining * 2 - this.timeRemaining;
		}
		return this.timeRemaining;

	}

	/**
	 * Use to notify observer that progress information are changed.
	 */
	void notifyObservers() {

		for (int i = 0; i < this.observers.size(); i++) {
			this.observers.get(i).progressHasChanged(this);
		}

	}

	/**
	 * Start the execution of the encoding process. This method exit when the
	 * encoding process are finish.
	 * 
	 * @throws MPlayerException
	 *             is any error occur
	 */
	public void start() throws MPlayerException {

		executeCommand(this.command1);
		if (this.canceled)
			return;

		// re-init process value
		this.percent = 0;
		this.timeRemaining = this.maximumTimeRemaining;
		this.command1Completed = true;

		if (this.command2 != null) {
			executeCommand(this.command2);

			// Remove pass file
			this.passFile.delete();
		}

	}

	/**
	 * Remove the observer from this job.
	 * 
	 * @param observer
	 *            the observer to remove.
	 */
	public void removeProgressObserver(EncodingProgressObserver observer) {
		this.observers.remove(observer);
	}

}
