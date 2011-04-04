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
package com.intuit.ginsu.cli;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import com.beust.jcommander.IDefaultProvider;
import com.beust.jcommander.JCommander;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.intuit.ginsu.annotations.AppName;
import com.intuit.ginsu.commands.CommandDispatchServiceImpl;
import com.intuit.ginsu.commands.CommandGenerateProject;
import com.intuit.ginsu.commands.CommandHelp;
import com.intuit.ginsu.commands.CommandInitEnv;
import com.intuit.ginsu.commands.CommandRunTests;
import com.intuit.ginsu.commands.ICommand;
import com.intuit.ginsu.commands.ICommandDispatchService;
import com.intuit.ginsu.commands.SupportedCommandCollection;
import com.intuit.ginsu.config.IConfigurationService;
import com.intuit.ginsu.config.PropertyFileConfigurationService;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              This is the main configuration file for the Ginsu Command Line
 *              utility. This file determines what implementation types should
 *              be used for the interfaces that our application needs to use. 
 *              When we need an implementation of that interface in a class, it
 *              is injected using Google Guice. For more information on Google
 *              Guice and using configuration modules to enable dependency 
 *              injection see: http://code.google.com/p/google-guice/
 * 
 */
public class GinsuCLIModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(AppName.class).toInstance("Ginsu");
		bind(IInputParsingService.class).to(CommandLineParsingService.class);
		bind(IConfigurationService.class).to(PropertyFileConfigurationService.class).asEagerSingleton();
		bind(ICommandDispatchService.class).to(CommandDispatchServiceImpl.class);
		bind(IDefaultProvider.class).to(PropertyFileConfigurationService.class);
		bind(OutputStream.class).toInstance(System.out);
		bind(MainArgs.class);
	}
	
	/**
	 * Provide a PrintWriter that has the desired output stream.
	 * @param outputStream an {@link OutputStream} to print to.
	 * @return a {@link PrintWriter} with the {@link OutputStream} passed in.
	 */
	@Provides PrintWriter providePrintWriter(OutputStream outputStream)
	{
		return new PrintWriter(outputStream, true);
	}
	
	/**
	 * 
	 * @param appName
	 * @param mainArgs
	 * @param supportedCommands
	 * @return
	 */
	@Provides JCommander provideJCommander(@AppName String appName, 
			MainArgs mainArgs, 
			SupportedCommandCollection supportedCommands,
			IDefaultProvider defaultProvider)
	{
		JCommander jCommander = new JCommander(mainArgs); 
		jCommander.setProgramName(appName);
		jCommander.setDefaultProvider(defaultProvider);
		// add all of the commands in our collection of supported commands
		for (Map.Entry<String, ICommand> entry : supportedCommands
				.entrySet()) {
			jCommander.addCommand(entry.getKey(), entry.getValue());
		}
		return jCommander;
	}
	
	/**
	 * Provide all of the supported commands that we would like to support.
	 * 
	 * TODO: Someday it would be nice to have a CommandLoader object that could do
	 * some sort of dynamic class loading to do this instead of hard-coding it in.
	 * 
	 * @return a {@link SupportedCommandCollection} of Commands that we support.
	 */
	@Provides SupportedCommandCollection provideSupportedCommands(PrintWriter printWriter, Logger logger)
	{
		// Keep reference to our commands so we can return the one we want later
		SupportedCommandCollection supportedCommands = new SupportedCommandCollection();
		supportedCommands.put(CommandHelp.NAME, new CommandHelp(printWriter, logger));
		supportedCommands.put(CommandInitEnv.NAME, new CommandInitEnv(printWriter, logger));
		supportedCommands.put(CommandGenerateProject.NAME, new CommandGenerateProject(printWriter, logger));
		supportedCommands.put(CommandRunTests.NAME, new CommandRunTests(printWriter, logger)); 
		return supportedCommands;
	}

}
