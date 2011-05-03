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

import java.util.Hashtable;
import java.util.Map;

import com.beust.jcommander.IDefaultProvider;
import com.beust.jcommander.JCommander;
import com.intuit.ginsu.NullCommand;
import com.intuit.ginsu.ICommand;
import com.intuit.ginsu.IInputHandlingService;
import com.intuit.ginsu.commands.SupportedCommandCollection;

/**
 * @author rpfeffer
 * @dateCreated Mar 26, 2011
 * 
 *              This file provides services for parsing command line input
 *              from the terminal. It wraps JCommander and maintains a
 *              runnable command state. Anyone that would like to implement
 *              a command that is runnable by this class should see the 
 *              documentation at http://jcommander.org/#Complex as well 
 *              as implement the ICommand interface.
 * 
 */
public class CommandLineParsingService implements IInputHandlingService {

	// According to the best practices listed at:
	// http://code.google.com/docreader/#p=google-guice&s=google-guice&t=MinimizeMutability
	// we should try keep all injected objects immutable
	private final JCommander jCommander;
	private final MainArgs mainArgs;
	private final Map<String, ICommand> supportedCommands;
	private StringBuilder stringBuilder;
	private ICommand command;
	

	CommandLineParsingService( JCommander jCommander, MainArgs mainArgs,
			SupportedCommandCollection supportedCommands) {
		this.mainArgs = mainArgs;
		this.jCommander = jCommander;
		this.supportedCommands = supportedCommands;
		
		//Setup The JCommander Object with the Supported Commands 
		this.loadSupportedCommands();
		
		//until we successfully call parse, this will be the object we get back
		this.command = new NullCommand();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.ginsu.cli.IInputParsingService#parseInput(java.lang.String[])
	 */
	public void handleInput(String[] input) {
		
		stringBuilder = new StringBuilder();
		ICommand parsedCommand = getParsedCommand(input);
		if(parsedCommand.shouldRenderCommandUsage())
		{
			UsagePrinter usagePrinter;
			if(parsedCommand.getName() == UsagePrinter.NAME)
			{
				usagePrinter = getUsagePrinter();				
			}
			else
			{
				stringBuilder.append("Explanation..." +
						System.getProperty("line.separator"));
				jCommander.usage(parsedCommand.getName(), stringBuilder);
				usagePrinter = (UsagePrinter) supportedCommands.get(UsagePrinter.NAME);
				usagePrinter.setUsage(stringBuilder.toString());
			}
			parsedCommand = usagePrinter;
		}
		command = parsedCommand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.cli.IInputParsingService#getCommand()
	 */
	public ICommand getCommand() {
		return command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.cli.IInputParsingService#getMainCommand()
	 */
	public Hashtable<String, String> getConfigurationOverride() {
		return mainArgs.getConfigurationOverride();
	}

	/**
	 * Set the default provider on the CommandLineParsingService.
	 * 
	 * @param defaultProvider
	 *            The object which provides default values for objects passed
	 *            via the command line.
	 */
	public void setDefaultProvider(IDefaultProvider defaultProvider) {
		jCommander.setDefaultProvider(defaultProvider);
	}
	
	/**
	 * Get the command parsed from the arguments passed in.
	 * @return the {@link ICommand} that was parsed from the consuming class. 
	 * @throws Exception
	 *             When more or less than one of the supported commands is
	 *             parsed from the input given by the consuming class
	 */
	private ICommand getParsedCommand(String[] input)
	{
		ICommand parsedCommand;
		try
		{
			jCommander.parse(input);
			parsedCommand = supportedCommands.get(jCommander.getParsedCommand());
		}
		catch (Throwable e)
		{
			//When jCommander parses the command, it is possible for it
			//to throw an unchecked exception at runtime.
			stringBuilder.append(e.getMessage() + 
					System.getProperty("line.separator"));
			parsedCommand = getUsagePrinter();
		}
		return parsedCommand;
	}
	
	private UsagePrinter getUsagePrinter()
	{
		UsagePrinter usagePrinter = (UsagePrinter) supportedCommands.get(UsagePrinter.NAME);
		//This gets called twice if an error was thrown
		//We do a check here to make sure it happens correctly.
		if(!usagePrinter.hasBeenWrittenTo())
		{
			stringBuilder.append("Usage is Defined as follows..." +
					System.getProperty("line.separator"));
			stringBuilder.append(System.getProperty("line.separator"));
			jCommander.usage(stringBuilder);
			usagePrinter.setUsage(stringBuilder.toString());
		}
		return usagePrinter;
	}
	
	/**
	 * load the commands that we support into the jCommander object. 
	 */
	private void loadSupportedCommands()
	{
		// add all of the commands in our collection of supported commands
		for (Map.Entry<String, ICommand> entry : supportedCommands.entrySet()) {
			jCommander.addCommand(entry.getKey(), entry.getValue());
		}
	}
	
	

}
