/*
 * MencoderStreamParser.java
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
 * $Id: MencoderStreamParser.java 166 2007-04-15 21:39:03Z jlee $
 */
package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class are use to read any input stream.
 * <p>
 * This reader can be use to read the content of an input stream and retrive it
 * content or parse it with a StreamParser object. It's also possible to
 * execution the reading process in a new thread.
 * </p>
 * 
 * @author patapouf
 * 
 */
public class StreamReader {

	/**
	 * Sleep delay between each try.
	 */
	private static final int SLEEP_DELAY = 100;
	/**
	 * Maximum sleep count.
	 */
	private static final int SLEEP_COUNT = 5;

	/**
	 * The stream reader.
	 */
	BufferedReader reader;
	/**
	 * String buffer to stock lines.
	 */
	StringBuffer buffer;
	/**
	 * Thread that read (if threaded).
	 */
	Thread thread;
	/**
	 * Stream parser or null.
	 */
	StreamParser parser;
	/**
	 * True to stop the reading process.
	 */
	boolean stop = false;

	/**
	 * Create a new stream reader without a parser.
	 * 
	 * @param input
	 *            the input stream to read.
	 * @param buffered
	 *            True to buffer all data. Must be True if you call toString().
	 */
	public StreamReader(InputStream input, boolean buffer) {
		this(input, null, buffer);
	}

	/**
	 * Create a new stream reader.
	 * 
	 * @param input
	 *            the input stream to read.
	 * @param parser
	 *            the stream parser.
	 * @param buffered
	 *            True to buffer all data. Must be True if you call toString().
	 */
	public StreamReader(InputStream input, StreamParser parser, boolean buffered) {
		this.reader = new BufferedReader(new InputStreamReader(input));
		this.parser = parser;
		if (buffered) {
			this.buffer = new StringBuffer();
		}
	}

	/**
	 * Start the reading process in current thread.
	 * 
	 * @return the data read from stream.
	 * 
	 */
	public String read() {

		try {

			String inputLine;
			while (!this.stop && (inputLine = this.reader.readLine()) != null) {
				if (this.buffer != null) {
					this.buffer.append(inputLine + "\r\n"); //$NON-NLS-1$
				}
				if (this.parser != null) {
					this.stop = this.stop || !this.parser.parseLine(inputLine);
				}
			}
			this.reader.close();

		} catch (IOException e) {
			// Nothing to do ..
			e.printStackTrace();
		}
		if (this.buffer != null) {
			return this.buffer.toString();
		}
		return "NOT_ACTIVATED"; //$NON-NLS-1$
	}

	/**
	 * Start the reading process in a new Thread.
	 * <p>
	 * To retrieve the data read from the stream, use toString() method.
	 * </p>
	 */
	public void readInThread() {

		this.thread = new Thread(new Runnable() {
			public void run() {

				try {

					String inputLine = ""; //$NON-NLS-1$
					String lastLine = ""; //$NON-NLS-1$
					while (!StreamReader.this.stop && (inputLine = StreamReader.this.reader.readLine()) != null) {
						if (StreamReader.this.buffer != null && !lastLine.equals(inputLine)) {
							StreamReader.this.buffer.append(inputLine + "\r\n"); //$NON-NLS-1$
						}
						lastLine = inputLine;
						if (StreamReader.this.parser != null) {
							StreamReader.this.stop = StreamReader.this.stop || !StreamReader.this.parser.parseLine(inputLine);
						}
					}

					StreamReader.this.reader.close();

				} catch (IOException e) {
					// Nothing to do ..
					if (!StreamReader.this.stop) {
						e.printStackTrace();
					}
				}
			}
		});

		// Start the parsing
		this.thread.start();
	}

	/**
	 * Use to stop reading the stream. This method wait until the reading
	 * finish.
	 */
	public void stop() {

		this.stop = true;

		// Wait until the thread end
		if (this.thread != null) {
			int count = 0;
			while (this.thread != null && this.thread.isAlive() && count < SLEEP_COUNT) {
				try {
					Thread.sleep(SLEEP_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				count++;
			}
			this.thread = null;
		}

	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		if (this.thread != null) {
			while (this.thread.isAlive()) {
				try {
					Thread.sleep(SLEEP_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.thread = null;
		}
		if (this.buffer != null) {
			return this.buffer.toString();
		}
		return "NOT_ACTIVATED"; //$NON-NLS-1$

	}

}
