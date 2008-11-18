package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represent an input video file present on the local file system.
 * 
 * @author patapouf
 * 
 */
public class InputVideoFile extends InputVideo {

	/**
	 * Input video file.
	 */
	private File inputVideoFile;

	/**
	 * Audio track id.
	 */
	private String audioTrack;

	/**
	 * Subtitle track id.
	 */
	private String subtitleTrack;

	/**
	 * Create a new input video file.
	 * 
	 * @param file
	 *            the video file.
	 * @throws FileNotFoundException
	 *             if the file do not exists.
	 */
	public InputVideoFile(File file) throws FileNotFoundException {
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		this.inputVideoFile = file;
		this.audioTrack = null;
		this.subtitleTrack = null;
	}

	/**
	 * Create a new input video file.
	 * 
	 * @param file
	 *            the video file.
	 * @param audioTrack
	 *            The audio track to encode or null to let mplayer select the
	 *            default track according to the configuration file.
	 * @param subtitleTrack
	 *            the subtitle trackto show or null to disable subtitle.
	 * @throws FileNotFoundException
	 *             if the file do not exists.
	 */
	public InputVideoFile(File file, String audioTrack, String subtitleTrack)
			throws FileNotFoundException {
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		this.inputVideoFile = file;
		this.audioTrack = audioTrack;
		this.subtitleTrack = subtitleTrack;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo#equals(net.homeip.entreprisesmd.mvconv.mplayerwrapper.InputVideo)
	 */
	public boolean equals(InputVideo obj) {
		if (obj instanceof InputVideoFile) {

			InputVideoFile inputVideo = (InputVideoFile) obj;

			if (!this.inputVideoFile.equals(inputVideo.inputVideoFile)) {
				return false;
			}
			if (this.audioTrack != null && !this.audioTrack.equals(inputVideo.audioTrack)) {
				return false;
			}
			if (inputVideo.audioTrack != null
					&& !inputVideo.audioTrack.equals(this.audioTrack)) {
				return false;
			}
			if (this.subtitleTrack != null
					&& !this.subtitleTrack.equals(inputVideo.subtitleTrack)) {
				return false;
			}
			if (inputVideo.subtitleTrack != null
					&& !inputVideo.subtitleTrack.equals(this.subtitleTrack)) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Retrun the selected audio track identifier or null.
	 * 
	 * @return the audio track identifier or null.
	 */
	public String getAudioTrack() {
		return this.audioTrack;
	}

	/**
	 * Return the input file.
	 * 
	 * @return the input file.
	 */
	public File getFile() {
		return this.inputVideoFile;
	}

	/**
	 * Return the selected subtitle track identifier or null.
	 * 
	 * @return the selected track identifier or null.
	 */
	public String getSubtitleTrack() {
		return this.subtitleTrack;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.inputVideoFile.hashCode();
	}

	/**
	 * Return the argument list to define the input video to mplayer.
	 * 
	 * @return the argument list.
	 */
	public String[] toCommandList() {

		String path;
		try {
			path = this.inputVideoFile.getCanonicalPath();
		} catch (IOException e) {
			path = this.inputVideoFile.getAbsolutePath();
		}

		List<String> argsList = new LinkedList<String>();
		argsList.add(path);

		if (this.audioTrack != null) {
			argsList.add("-aid"); //$NON-NLS-1$
			argsList.add(this.audioTrack);
		}
		if (this.subtitleTrack != null) {
			argsList.add("-sid"); //$NON-NLS-1$
			argsList.add(this.subtitleTrack);
		}

		String[] defaultArgs = super.toCommandList();
		for(int i=0; i<defaultArgs.length;i++){
			argsList.add(defaultArgs[i]);
		}
		
		String[] args = new String[argsList.size()];
		args = argsList.toArray(args);
		argsList = null;
		return args;
	}

}
