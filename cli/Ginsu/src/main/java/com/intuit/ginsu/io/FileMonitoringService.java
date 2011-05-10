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
package com.intuit.ginsu.io;

import java.io.File;
import java.util.Timer;

import com.intuit.ginsu.IFileListener;
import com.intuit.ginsu.IFileMonitoringService;

/**
 * @author rpfeffer
 * @dateCreated May 7, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
public class FileMonitoringService implements IFileMonitoringService {

	private final Timer timer;

	/**
	 * @param timer
	 */
	FileMonitoringService(Timer timer) {
		this.timer = timer;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.IFileMonitoringService#monitorFile(java.io.File, long, com.intuit.ginsu.IFileListener)
	 */
	public void monitorFile(File file, long interval, IFileListener listener) {
		// TODO Auto-generated method stub
		assert interval > 0 : "The interval over which files are monitored must"
				+ " be positive. Given: " + String.valueOf(interval);
		timer.schedule(new FileNotifier(listener, file), 0, interval);
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.IFileMonitoringService#stopMonitoring()
	 */
	public void stopMonitoring() {
		timer.cancel();
	}

}
