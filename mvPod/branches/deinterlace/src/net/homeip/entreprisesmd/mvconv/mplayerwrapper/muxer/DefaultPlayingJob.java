package net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer;

import java.io.IOException;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingCommand;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.ErrorParser;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PlayingJob;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.StreamReader;

/**
 * This encoding job are use as default to execute the mplayer command for one
 * pass mode or tow pass mode.
 * 
 * @author patapouf
 * 
 */
public class DefaultPlayingJob implements PlayingJob {

	/**
	 * The mplayer wrapper to use.
	 */
	private MPlayerWrapper mplayer;
	/**
	 * The mencoder process.
	 */
	private Process proc;
	/**
	 * The standard input stream reader.
	 */
	private StreamReader inputStream;
	/**
	 * The error input stream reader.
	 */
	private StreamReader errorStream;

	/**
	 * The command to execute.
	 */
	private EncodingCommand command;
	/**
	 * True if process are abort.
	 */
	private boolean canceled;

	/**
	 * Create a new playing job.
	 * 
	 * @param mplayer
	 *            the mplayer instance that might be use to execute the job
	 * @param encodingCommand
	 *            the command to execute
	 */
	public DefaultPlayingJob(MPlayerWrapper mplayer,
			EncodingCommand encodingCommand) {
		this.mplayer = mplayer;
		this.command = encodingCommand;
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
				this.inputStream = null;
			}
			if (this.errorStream != null) {
				this.errorStream.stop();
				this.errorStream = null;
			}

			this.proc.destroy();
		}
	}

	/**
	 * Execute the given command in a progress and handle any error that can
	 * occur whiting this process.
	 * 
	 * @param encodingCommand
	 *            the command to execute
	 * @throws MPlayerException
	 *             when any error occur
	 */
	private void executeCommand(EncodingCommand encodingCommand)
			throws MPlayerException {

		try {
			this.proc = this.mplayer.mplayer(encodingCommand.toStringArray());
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
		this.inputStream = new StreamReader(this.proc.getInputStream(), errorParser,
				false);
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

		errorParser.throwException();

		if (!this.canceled) {
			String error = this.errorStream.toString();
			this.inputStream = null;
			this.errorStream = null;

			// Check if there any revealant error
			errorParser.throwException();

			// Check the return value
			if (exitCode > 0) {
				throw new MPlayerException(error);
			}
		}

	}

	/**
	 * Start the execution of the encoding process. This methode exit when the
	 * encoding process are finish.
	 * 
	 * @throws MPlayerException
	 *             if any error occur
	 */
	public void start() throws MPlayerException {
		executeCommand(this.command);
	}

}
