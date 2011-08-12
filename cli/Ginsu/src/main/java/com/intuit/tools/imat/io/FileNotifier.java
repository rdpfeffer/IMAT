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
package com.intuit.tools.imat.io;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

import com.intuit.tools.imat.IFileListener;

/**
 * @author rpfeffer
 * @dateCreated May 7, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
public class FileNotifier extends TimerTask {

	private static long NON_EXISTENT_MODIFIED = -1;
	private static long DEFAULT_CHECK_BACK_INTERVAL = 30000; //30 Seconds
	private final File file;
	private final IFileListener listener;
	private long lastModified;
	
	private long minimumInterval = 0;

	public FileNotifier(IFileListener listener, File file) {
		this.listener = listener;
		this.file = file;
		this.minimumInterval = DEFAULT_CHECK_BACK_INTERVAL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		long newLastModified = (file.exists() ? file.lastModified()
				: NON_EXISTENT_MODIFIED);
		if (newLastModified != lastModified && listener != null) {
			lastModified = newLastModified;
			listener.fileChanged(file);
		} else {
			long currentTime = (new Date()).getTime();
			if (currentTime - lastModified > minimumInterval) {
				file.setLastModified(currentTime);
				lastModified = currentTime;
			}
		}
	}
	
	/**
	 * Set the interval of how often we want to check the file even if we have 
	 * not been notified that it has changed. Note: the default interval is
	 * 30000 milliseconds (30 seconds).
	 * @param interval the guaranteed minimum amount of time between notifying
	 * the listener. 
	 */
	public void setMinimumInterval(long interval) {
		this.minimumInterval = interval;
	}
}
