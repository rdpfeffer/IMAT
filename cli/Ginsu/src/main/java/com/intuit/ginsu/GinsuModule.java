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
package com.intuit.ginsu;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import com.beust.jcommander.JCommander;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.intuit.ginsu.annotations.AppName;
import com.intuit.ginsu.cli.CommandLineParsingService;
import com.intuit.ginsu.cli.IInputParsingService;
import com.intuit.ginsu.commands.CommandDispatchServiceImpl;
import com.intuit.ginsu.commands.CommandGenerateProject;
import com.intuit.ginsu.commands.CommandHelp;
import com.intuit.ginsu.commands.CommandInitEnv;
import com.intuit.ginsu.commands.CommandMain;
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
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class GinsuModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(AppName.class).toInstance("Ginsu");
		bind(IInputParsingService.class).to(CommandLineParsingService.class);
		bind(IConfigurationService.class).to(PropertyFileConfigurationService.class);
		bind(ICommandDispatchService.class).to(CommandDispatchServiceImpl.class);
		bind(OutputStream.class).toInstance(System.out);
	}
	
	@Provides PrintWriter providePrintWriter(OutputStream outputStream)
	{
		return new PrintWriter(outputStream, true);
	}
	
	@Provides JCommander provideJCommander(@AppName String appName, CommandMain commandMain, SupportedCommandCollection supportedCommands)
	{
		JCommander jCommander = new JCommander(commandMain); 
		jCommander.setProgramName(appName);
		// add all of the commands in our collection of supported commands
		for (Map.Entry<String, ICommand> entry : supportedCommands
				.entrySet()) {
			jCommander.addCommand(entry.getKey(), entry.getValue());
		}
		return jCommander;
	}
	
	@Provides CommandMain provideCommandMain(PrintWriter printWriter)
	{
		return new CommandMain(printWriter);
	}
	
	
	@Provides SupportedCommandCollection provideSupportedCommands()
	{
		// Keep reference to our commands so we can return the one we want later
		SupportedCommandCollection supportedCommands = new SupportedCommandCollection();
		supportedCommands.put(CommandHelp.NAME, new CommandHelp());
		supportedCommands.put(CommandInitEnv.NAME, new CommandInitEnv());
		supportedCommands.put(CommandGenerateProject.NAME, new CommandGenerateProject());
		supportedCommands.put(CommandRunTests.NAME, new CommandRunTests()); 
		return supportedCommands;
	}

}
