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
package com.intuit.tools.imat.scripts;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.intuit.tools.imat.IApplicationResourceService;

/**
 * @author rpfeffer
 * @dateCreated Apr 22, 2011
 * 
 *              This class launches AppleScripts as an external process. It
 *              enables the IMAT application to do things that are only
 *              accessible through the applescripting framework. This includes
 *              things like launching Instruments, or any other application that
 *              can be scripted using the AppleScript language.
 * 
 */
public class AppleScriptLauncher extends ExternallyScriptedProcessLauncher {

	private static final String COMMAND = "osascript";

	AppleScriptLauncher(IApplicationResourceService resourceService,
			Logger logger) {
		super(resourceService, logger);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.IScriptLauncher#runScript()
	 */
	public int runScript() {
		int exitStatus = 1;
		try {
			exitStatus = this.executeCommand(COMMAND + " "
					+ getScriptAsFile().getAbsolutePath()
					+ getOrderedParamterString());
		} catch (FileNotFoundException e) {
			logger.error("Could not find the appleScript.", e);
		}
		return exitStatus;
	}
}
