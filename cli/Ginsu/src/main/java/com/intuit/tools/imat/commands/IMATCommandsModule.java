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
package com.intuit.tools.imat.commands;

import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.intuit.tools.imat.IApplicationResourceService;
import com.intuit.tools.imat.ICommand;
import com.intuit.tools.imat.IFileMonitoringService;
import com.intuit.tools.imat.IScriptLauncher;
import com.intuit.tools.imat.ITestMonitor;
import com.intuit.tools.imat.annotations.AntScript;
import com.intuit.tools.imat.annotations.AppleScript;
import com.intuit.tools.imat.annotations.Instruments;
import com.intuit.tools.imat.annotations.UsageRenderer;
import com.intuit.tools.imat.annotations.iOS;
import com.intuit.tools.imat.reporting.IReportingService;

/**
 * @author rpfeffer
 * @dateCreated Apr 28, 2011
 * 
 *              This class defines the configuration for all classes in the
 *              com.intuit.tools.imat.commands package
 * 
 */
public class IMATCommandsModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {

	}
	
	/**
	 * Provide all of the supported commands that we would like to support.
	 * 
	 * TODO RP: Someday it would be nice to have a CommandLoader object that could do
	 * some sort of dynamic class loading to do this instead of hard-coding it in.
	 * 
	 * @return a {@link SupportedCommandCollection} of Commands that we support.
	 */
	@Provides @Singleton SupportedCommandCollection provideSupportedCommands(
			@UsageRenderer ICommand usageRenderer,
			CommandNewProject generateproject,
			CommandInitEnv initEnv,
			CommandRunTests runTests)
	{
		// Keep reference to our commands so we can return the one we want later
		SupportedCommandCollection supportedCommands = new SupportedCommandCollection();
		supportedCommands.put(usageRenderer.getName(), usageRenderer);
		supportedCommands.put(generateproject.getName(), generateproject);
		supportedCommands.put(initEnv.getName(), initEnv);
		supportedCommands.put(runTests.getName(), runTests);
		return supportedCommands;
	}
	
	@Provides CommandInitEnv provideCommandInitEnv(PrintWriter printwriter, 
			@AntScript IScriptLauncher scriptLauncher, 
			IApplicationResourceService appResourceService)
	{
		Logger logger = Logger.getLogger(CommandInitEnv.class);
		return new CommandInitEnv(printwriter, logger, scriptLauncher, appResourceService);
	}
	
	@Provides CommandNewProject provideCommandGenerateProject(PrintWriter printwriter, 
			@AntScript IScriptLauncher scriptLauncher, 
			IApplicationResourceService appResourceService)
	{
		Logger logger = Logger.getLogger(CommandNewProject.class);
		return new CommandNewProject(printwriter, logger, scriptLauncher, appResourceService);
	}

	@Provides CommandRunTests provideCommandRunTests(PrintWriter printwriter, 
			@AppleScript IScriptLauncher scriptLauncher,
			@Instruments IScriptLauncher instrumentsLauncher,
			IApplicationResourceService applicationResourceService,
			IFileMonitoringService fileMonitoringService,
			@iOS ITestMonitor testMonitor,
			IReportingService reportingService)
	{
		Logger logger = Logger.getLogger(CommandRunTests.class);
		return new CommandRunTests(printwriter, logger, scriptLauncher, 
				instrumentsLauncher, applicationResourceService, reportingService);
	}
	
	@Provides SynchronousCommandDispatchService provideDispatchService()
	{
		return new SynchronousCommandDispatchService(
				Logger.getLogger(SynchronousCommandDispatchService.class));
	}
}
