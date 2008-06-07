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
	 * @param command
	 *            the command to execute
	 */
	public DefaultPlayingJob(MPlayerWrapper mplayer, EncodingCommand command) {
		this.mplayer = mplayer;
		this.command = command;
	}

	/**
	 * Cancel the job.
	 */
	public void cancel() {
		// Terminate process
		if (proc != null) {

			canceled = true;

			if (inputStream != null) {
				inputStream.stop();
				inputStream = null;
			}
			if (errorStream != null) {
				errorStream.stop();
				errorStream = null;
			}

			proc.destroy();
		}
	}

	/**
	 * Execute the given command in a progress and handle any error that can
	 * occur whiting this process.
	 * 
	 * @param command
	 *            the command to execute
	 * @throws MPlayerException
	 *             when any error occur
	 */
	private void executeCommand(EncodingCommand command)
			throws MPlayerException {

		try {
			proc = mplayer.mplayer(command.toStringArray());
		} catch (IOException ioe) {
			throw new MPlayerException("Can't run mencoder process", ioe);
		}

		ErrorParser errorParser = new ErrorParser() {
			public boolean exceptionFound(MPlayerException exception) {
				super.exceptionFound(exception);
				cancel();
				return false;
			}
		};
		inputStream = new StreamReader(proc.getInputStream(), false);
		errorStream = new StreamReader(proc.getErrorStream(), errorParser, true);
		inputStream.readInThread();
		errorStream.readInThread();

		int exitCode = 1;
		try {
			exitCode = proc.waitFor();
		} catch (InterruptedException ie) {
			inputStream = null;
			errorStream = null;
			throw new MPlayerException("mencoder process are interrupted", ie);
		}

		if(!canceled){
			String error = errorStream.toString();
			inputStream = null;
			errorStream = null;		
			
			
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
		executeCommand(command);
	}

}
