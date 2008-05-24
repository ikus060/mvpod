/*
 * ConverterOptions.java
 * Copyright (C) 2005-2007 James Lee
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 * 
 * $Id: ConverterOptions.java 166 2007-04-15 21:39:03Z jlee $
 */
package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.util.ArrayList;
import java.util.List;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiofilter.AudioFilter;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.DefaultMuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videofilter.VideoFilter;

/**
 * This class represent a set of generic option available for video playing or
 * encoding.
 * 
 * @author patapouf
 * 
 */
public abstract class GenericOptions {

	/**
	 * Audio filter.
	 */
	private List<AudioFilter> audioFilters = new ArrayList<AudioFilter>();
	/**
	 * Muxer to use.
	 */
	private Muxer muxer = new DefaultMuxer();
	/**
	 * Scaling options.
	 */
	private VideoScalingOptions scaleOptions;
	/**
	 * Video filter.
	 */
	private List<VideoFilter> videoFilters = new ArrayList<VideoFilter>();

	/**
	 * Create a new generic options.
	 */
	public GenericOptions() {

	}

	/**
	 * Add an audio filter to the encoding options.
	 * 
	 * @param filter
	 *            the audio option to add
	 */
	public void addAudioFilter(AudioFilter filter) {
		audioFilters.add(filter);
	}

	/**
	 * Add an audio filter to the encoding options.
	 * 
	 * @param filter
	 *            the audio option to add
	 */
	public void addVideoFilter(VideoFilter filter) {
		videoFilters.add(filter);
	}

	/**
	 * Return current audio filters of this encoding options.
	 * 
	 * @return arrays of audio filter
	 */
	public AudioFilter[] getAudioFilter() {
		AudioFilter[] filters = new AudioFilter[0];
		return audioFilters.toArray(filters);
	}

	/**
	 * Return the muxer to use.
	 * 
	 * @return the muser
	 */
	public Muxer getMuxer() {
		return muxer;
	}

	/**
	 * Return the video scaling options.
	 * 
	 * @return the scaling options or null if the video are not resized.
	 */
	public VideoScalingOptions getScaleOptions() {
		return scaleOptions;
	}

	/**
	 * Return current video filters of this encoding options.
	 * 
	 * @return arrays of video filter
	 */
	public VideoFilter[] getVideoFilter() {
		VideoFilter[] filters = new VideoFilter[0];

		return videoFilters.toArray(filters);
	}

	/**
	 * Remove the given audio filter from the encoding option.
	 * 
	 * @param filter
	 *            the audio filter to remove
	 */
	public void removeAudioFilter(AudioFilter filter) {
		this.audioFilters.remove(filter);
	}

	/**
	 * Remove the given video filter from the encoding option.
	 * 
	 * @param filter
	 *            the video filter to remove
	 */
	public void removeVideoFilter(AudioFilter filter) {
		this.videoFilters.remove(filter);
	}

	/**
	 * Set the muxer.
	 * 
	 * @param muxer
	 *            the new muxer
	 */
	public void setMuxer(Muxer muxer) {
		if (muxer == null) {
			throw new NullPointerException();
		}
		this.muxer = muxer;
	}

	/**
	 * Use to define a scaling options to define the dimension of the resulting
	 * video.
	 * 
	 * @param options
	 *            the scaling options or null to don't resize the video.
	 */
	public void setScaleOptions(VideoScalingOptions options) {
		this.scaleOptions = options;
	}

}
