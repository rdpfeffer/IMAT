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

	private static String SCRIPT_COMPLETED = "Script completed.";
	private static String SCRIPT_EXCEPTION = "An exception occurred while trying to run the script.";
	private static String SCRIPT_TYPE_EXCEPTION = "Exception raised while running script: TypeError:";
	private static String SCRIPT_STOPED_BY_USER = "Script was stopped by the user.";
	private boolean executionComplete = false;
	private boolean ranToCompletion = false;
	private RandomAccessFile reader = null;
	private long filePointer = 0;
	private Logger logger = Logger.getLogger(iOSTestMonitor.class);

	/* (non-Javadoc)
	 * @see com.intuit.tools.imat.ITestMonitor#isExecutionComplete()
	 */
	public boolean isExecutionComplete() {
		return executionComplete;
	}

	/* (non-Javadoc)
	 * @see com.intuit.tools.imat.ITestMonitor#testsDidRunToCompletion()
	 */
	public boolean testsDidRunToCompletion() {
		return ranToCompletion;
	}

	/* (non-Javadoc)
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
			reader.close();
			reader = null;
		} catch (IOException e) {
			logger.fatal("Could not monitor log file: " + file, e);
			executionComplete = true;
			ranToCompletion = false;
		}
	}

	/**
	 * @param file
	 * @throws IOException
	 */
	private void initReader(File file) throws IOException {
		if (reader == null) {
			try {
				reader = new RandomAccessFile(file, "r");
			} catch (FileNotFoundException e) {
				String reason = "could not find log file: " + file;
				logger.debug(reason, e);
				IOException ex = new IOException(reason);
				ex.initCause(e);
				throw ex;
			}
		}
	}

	/**
	 * @param line
	 * @return
	 */
	private boolean lineContainsTerminator(String line) {
		boolean containsTerminator = false;
		if (line.contains(SCRIPT_EXCEPTION)
				|| line.contains(SCRIPT_TYPE_EXCEPTION)
				|| line.contains(SCRIPT_STOPED_BY_USER)
				|| line.contains(SCRIPT_COMPLETED)) {
			logger.debug("Found a script terminator: " + line);
			containsTerminator = true;
		}
		return containsTerminator;
	}

	/**
	 * @param line
	 * @return
	 */
	private boolean ranToCompletion(String line) {
		boolean ranToCompletion = false;
		if (line.contains(SCRIPT_COMPLETED)) {
			logger.info("Script has fully completed execution successfuly.");
			ranToCompletion = true;
		} else {
			logger.info("Script stopped running prematurely.");
		}
		return ranToCompletion;
	}
}
