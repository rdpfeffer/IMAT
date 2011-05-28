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

	private final File file;
	private final IFileListener listener;
	private long lastModified;

	public FileNotifier(IFileListener listener, File file) {
		this.listener = listener;
		this.file = file;
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
		}
	}
}
