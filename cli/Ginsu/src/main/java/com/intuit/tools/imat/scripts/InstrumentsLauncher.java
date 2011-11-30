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

import org.apache.log4j.Logger;

import com.intuit.tools.imat.IApplicationResourceService;
import com.intuit.tools.imat.cli.ExitStatus;

/**
 * @author rpfeffer
 * @dateCreated Nov 26, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
public class InstrumentsLauncher extends ExternallyScriptedProcessLauncher {

	public static final String TEMPLATE_FILE = "-template";
	public static final String APPLICATION_UNDER_TEST = "-app";
	public static final String SUITE_FILE = "-suite";
	public static final String RESULTS_PATH = "-resultsPath";
	public static final String DEVICE_ID = "-deviceID";
	private static final String COMMAND = "instruments";
	private static final String DEFAULT_DEVICE_TEMPLATE = "/Developer/Platforms/iPhoneOS.platform/Developer/Library/Instruments/PlugIns/AutomationInstrument.bundle/Contents/Resources/Automation.tracetemplate";

	/**
	 * @param resourceService
	 * @param logger
	 */
	InstrumentsLauncher(IApplicationResourceService resourceService,
			Logger logger) {
		super(resourceService, logger);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.IScriptLauncher#runScript()
	 */
	public ExitStatus runScript() {
		String templateFile = "-t "  + (properties.get(TEMPLATE_FILE) == null ? DEFAULT_DEVICE_TEMPLATE : properties.get(TEMPLATE_FILE));
		String deviceOption = properties.get(DEVICE_ID) == null ? "" : "-w " + properties.get(DEVICE_ID);
		String commandString = 	COMMAND + " " + 
								deviceOption + " " +
								templateFile + " " +
								properties.get(APPLICATION_UNDER_TEST) + " " +
								"-e UIASCRIPT " + properties.get(SUITE_FILE) + " " +
								"-e UIARESULTSPATH " + properties.get(RESULTS_PATH);
		logger.debug("Launching Instruments with Command: " + commandString);
		this.executeCommand(commandString);
		return ExitStatus.SUCCESS;
	}

}
