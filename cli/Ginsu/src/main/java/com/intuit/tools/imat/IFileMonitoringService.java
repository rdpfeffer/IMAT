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
package com.intuit.tools.imat;

import java.io.File;

/**
 * @author rpfeffer
 * @dateCreated May 7, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
public interface IFileMonitoringService {
	/**
	 * Monitors a file with the given listener, asking for notifications at minimum once per 
	 * interval
	 * 
	 * @param file The file to monitor
	 * @param interval The minimum amount of time in milliseconds to wait between being notified.
	 * @param listener The object to be notified of file changes.
	 */
	public void monitorFile(File file, long interval, IFileListener listener);

	/**
	 * Stop monitoring the file.
	 */
	public void stopMonitoring();

}
