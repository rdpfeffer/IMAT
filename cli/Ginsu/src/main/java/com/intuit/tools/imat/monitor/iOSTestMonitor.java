/*******************************************************************************
 * Copyright (c) 2009 Intuit, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * Contributors:
 *     Intuit, Inc - initial API and implementation
 *******************************************************************************/
package com.intuit.tools.imat.monitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;

import com.intuit.tools.imat.ITestMonitor;

/**
 * @author rpfeffer
 * @dateCreated May 7, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
public class iOSTestMonitor implements ITestMonitor {

	private boolean executionComplete = false;
	private boolean ranToCompletion = false;
	private boolean logFoundAtLeastOnce = false;
	private RandomAccessFile reader = null;
	private long filePointer = 0;
	private Logger logger = Logger.getLogger(iOSTestMonitor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.ITestMonitor#isExecutionComplete()
	 */
	public boolean isExecutionComplete() {
		return executionComplete;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.ITestMonitor#testsDidRunToCompletion()
	 */
	public boolean testsDidRunToCompletion() {
		return ranToCompletion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.IFileListener#fileChanged(java.io.File)
	 */
	public void fileChanged(File file) {
		try {
			initReader(file);
			String line;
			logger.debug("Seeking to: " + String.valueOf(filePointer));
			reader.seek(filePointer);
			while ((line = reader.readLine()) != null) {
				logger.trace("Line is: " + line);
				if (lineContainsTerminator(line)) {
					executionComplete = true;
					ranToCompletion = ranToCompletion(line);
					break;
				}
			}
			filePointer = Math.max(0, reader.getFilePointer() - 27);
			logger.debug("Setting file pointer to: "
					+ String.valueOf(filePointer));
			cleanUpReader();
		} catch (IOException e) {
			logger.fatal("Could not monitor log file: " + file, e);
			executionComplete = true;
			ranToCompletion = false;
		} catch (FileInFluxException e) {
			logger.debug("File was still in flux while trying to parse. Waiting"
					+ "for next notification.");
		}
	}

	private void cleanUpReader() {
		try {
			reader.close();
			reader = null;
		} catch (IOException e) {
			logger.error("Error while discontinuing monitor", e);
		}
	}

	/**
	 * @param file
	 * @throws FileInFluxException In cases where the scripts happen to be writing very
	 * large amounts of text out to the log file at a given time. 
	 */
	private void initReader(File file) throws FileInFluxException {
		if (reader == null) {
			try {
				reader = new RandomAccessFile(file, "r");
				logFoundAtLeastOnce = true;
			} catch (FileNotFoundException e) {
				logger.debug("Could not find log file even though we were"
						+ " notified of a change. Log has been seen by "
						+ "monitor: " + logFoundAtLeastOnce);
				//Note we throw a different exception to mask the fact that we
				//get a FileNotFoundException when the framework is writing 
				//large amounts of text to the logs. This is OK because our
				//monitor is only being notified when the file being monitored
				//has changed, which implies that it also exists, even if
				//the call to read it says it does not. By throwing this 
				//exception instead we will defer reading it until the next time
				//we are notified.
				throw new FileInFluxException();
			}
		}
	}

	/**
	 * @param line
	 * @return
	 */
	private boolean lineContainsTerminator(String line) {
		boolean containsTerminator = false;
		for (iOSEndOfLogMsg msg : iOSEndOfLogMsg.values()) {
			if (line.contains(msg.getMessage())) {
				logger.debug("Found a script terminator: " + line);
				containsTerminator = true;
				break;
			}
		}
		return containsTerminator;
	}

	/**
	 * @param line
	 * @return
	 */
	private boolean ranToCompletion(String line) {
		boolean ranToCompletion = false;
		if (line.contains(iOSEndOfLogMsg.SCRIPT_COMPLETED.getMessage())) {
			logger.info("***** Script has fully completed execution successfuly."
					+ "*****");
			ranToCompletion = true;
		} else {
			logger.info("***** Script stopped running prematurely.*****");
		}
		return ranToCompletion;
	}
}
