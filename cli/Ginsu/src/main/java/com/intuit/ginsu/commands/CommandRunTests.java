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
package com.intuit.ginsu.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.intuit.ginsu.IApplicationResourceService;
import com.intuit.ginsu.ICommand;
import com.intuit.ginsu.IFileListener;
import com.intuit.ginsu.IFileMonitoringService;
import com.intuit.ginsu.IProjectResourceService;
import com.intuit.ginsu.IScriptLauncher;
import com.intuit.ginsu.ITestMonitor;
import com.intuit.ginsu.MisconfigurationException;
import com.intuit.ginsu.cli.converters.FileConverter;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 *
 * //TODO RP: We should explain the use of this class once it has been implemented.
 *
 */
@Parameters(commandDescription = "Use this command to run your tests.")
public class CommandRunTests extends ScriptedCommand implements ICommand, IFileListener{

	private static final long MONITORING_INTERVAL = 3000;
	private static final String AUTOMATION_RESULTS_PATH = "logs" + File.separator +
		"runs" + File.separator + "Run 1" + File.separator + "Automation Results.plist";
		
	
	/**
	 * The suite we will run in our tests
	 */
	public static final String SUITE_FILE = "-suite";
	@Parameter(names = {SUITE_FILE, "-s"},
			description = "The filename of the suite file to run. Note, this path " +
					"should be relative to the current project directory.")
	public String suite;
	
	/**
	 * The template file we will use in our tests.
	 */
	public static final String TEMPLATE_FILE = "-template";
	@Parameter(names = {TEMPLATE_FILE, "-t"},
			description = "The filename of the template to run.",
			converter = FileConverter.class)
	public File template;
	
	public static final String NAME = "run-tests";
	private static final int MAX_ATTEMPTS_TO_FIND_LOG = 5;
	private static final long WAIT_PERIOD_FOR_LOG_IN_MILLISECONDS = 5000;
	
	private final IApplicationResourceService applicationResourceService;
	private final IProjectResourceService projResourceService;
	private final IFileMonitoringService fileMonitoringService;
	private final ITestMonitor testMonitor;
	
	/**
	 * @param printwriter
	 * @param logger
	 * @param scriptLauncher
	 * @param projResourceService
	 */
	CommandRunTests(PrintWriter printwriter, 
			Logger logger, 
			IScriptLauncher scriptLauncher, 
			IApplicationResourceService applicationResourceService,
			IProjectResourceService projResourceService,
			IFileMonitoringService fileMonitoringService,
			ITestMonitor testMonitor
			) {
		super(printwriter, logger, scriptLauncher);
		this.applicationResourceService = applicationResourceService;
		this.projResourceService = projResourceService;
		this.fileMonitoringService = fileMonitoringService;
		this.testMonitor = testMonitor;
	}

	
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.ICommand#run()
	 */
	public int run() throws MisconfigurationException {
		exitStatus  = startTests();
		if (exitStatus == 0 )
		{
			monitorTests();
			exitStatus = stopTests();
		}
		if(testMonitor.testsDidRunToCompletion())
		{
			//TODO: Create the XML Formatted Test Result Files.
		}
		return exitStatus;
	}
	
	/**
	 * @throws MisconfigurationException
	 */
	private int startTests() throws MisconfigurationException
	{
		String suiteFilePath ="";
		int exitCode = 0;
		try {
			LinkedHashMap<String, String> arguments = new LinkedHashMap<String, String>();
			suiteFilePath = projResourceService.getProjectResourceFile(suite).getCanonicalPath();
			arguments.put(SUITE_FILE, suiteFilePath);
			arguments.put(TEMPLATE_FILE, template.getPath());
			IScriptLauncher scriptLauncher = this.getScriptLauncher();
			scriptLauncher.setProperties(arguments);
			scriptLauncher.setScript("StartXCodeInstrument.scpt");
			exitCode = scriptLauncher.runScript();
		} catch (FileNotFoundException e) {
			MisconfigurationException ex = 
				new MisconfigurationException("Could not find suite file: " + suiteFilePath);
			ex.initCause(e);
			throw ex;
		} catch (IOException e) {
			MisconfigurationException ex = 
				new MisconfigurationException("Error while resolving path.");
			ex.initCause(e);
			throw ex;
		}
		return exitCode;
	}
	
	/**
	 * @throws MisconfigurationException 
	 * 
	 */
	private void monitorTests() throws MisconfigurationException
	{
		File testResultLog = null;
		int tries = 0;
		while (testResultLog == null & tries < MAX_ATTEMPTS_TO_FIND_LOG)
		{
			try {
				testResultLog = applicationResourceService.getAppResourceFile(
						AUTOMATION_RESULTS_PATH, true);
			} catch (FileNotFoundException e) {
				logger.debug("Could not find log file on attempt: " + String.valueOf(tries));
				try {
					Thread.sleep(WAIT_PERIOD_FOR_LOG_IN_MILLISECONDS);
				} catch (InterruptedException e1) {
					logger.error("Interupted while waiting for test log file.", e);
				}
			}
			tries++;
		}
		if (testResultLog == null) {
			MisconfigurationException ex = new MisconfigurationException(
					"Could not find Automation Results file: "
					+ AUTOMATION_RESULTS_PATH);
			throw ex;
		}
		logger.debug("Monitoring Log file...");
		fileMonitoringService.monitorFile(testResultLog, MONITORING_INTERVAL, this);
	}
	
	/**
	 * 
	 */
	private synchronized int stopTests()
	{
		while(!testMonitor.isExecutionComplete())
		{
			try {
				//wait until the test monitor says that all test execution is complete.
				logger.debug("Waiting for test execution to complete.");
				wait(); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.debug("test execution is complete. Lauching Apple Script to stop Instruments.");
		IScriptLauncher scriptLauncher = this.getScriptLauncher();
		scriptLauncher.setScript("StopXCodeInstrument.scpt");
		return scriptLauncher.runScript();
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.ICommand#getName()
	 */
	public String getName()
	{
		return CommandRunTests.NAME;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.ICommand#isRunnable()
	 */
	public boolean isRunnable() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.Command#expectsProject()
	 */
	@Override
	public boolean expectsProject()
	{
		return true;
	}



	public synchronized void fileChanged(File file) {
		this.testMonitor.fileChanged(file);
		logger.trace("notifying that the file has changed.");
		notifyAll();
	}
}
