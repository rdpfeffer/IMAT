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
import java.util.Timer;

import com.intuit.tools.imat.IFileListener;
import com.intuit.tools.imat.IFileMonitoringService;

/**
 * @author rpfeffer
 * @dateCreated May 7, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
public class FileMonitoringService implements IFileMonitoringService {

	/*
	 * The period the Timer waits between checking the file's modified timestamp 
	 */
	private static long TIMER_INTERVAL_IN_MILISECONDS = 3000;
	
	/*
	 * The timer that keeps track of when to check the file
	 */
	private final Timer timer;

	/**
	 * @param timer
	 */
	FileMonitoringService(Timer timer) {
		this.timer = timer;
	}

	/* (non-Javadoc)
	 * @see com.intuit.tools.imat.IFileMonitoringService#monitorFile(java.io.File, long, com.intuit.tools.imat.IFileListener)
	 */
	public void monitorFile(File file, long interval, IFileListener listener) {
		// TODO Auto-generated method stub
		assert interval > 0 : "The interval over which files are monitored must"
				+ " be positive. Given: " + String.valueOf(interval);
		FileNotifier notifier = new FileNotifier(listener, file);
		notifier.setMinimumInterval(interval);
		timer.schedule(notifier, 0, TIMER_INTERVAL_IN_MILISECONDS);
	}

	/* (non-Javadoc)
	 * @see com.intuit.tools.imat.IFileMonitoringService#stopMonitoring()
	 */
	public void stopMonitoring() {
		timer.cancel();
	}

}
