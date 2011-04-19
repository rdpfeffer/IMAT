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

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;

import com.beust.jcommander.IDefaultProvider;
import com.beust.jcommander.JCommander;
import com.google.inject.Inject;
import com.intuit.ginsu.commands.CommandNull;
import com.intuit.ginsu.commands.ICommand;
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
public class CommandLineParsingService implements IInputParsingService {

	// According to the best practices listed at:
	// http://code.google.com/docreader/#p=google-guice&s=google-guice&t=MinimizeMutability
	// we should try keep all injected objects immutable
	private final JCommander jCommander;
	private final MainArgs mainArgs;
	private final PrintWriter printWriter;
	private final Map<String, ICommand> supportedCommands;
	private ICommand command;
	

	@Inject
	public CommandLineParsingService(PrintWriter printWriter, 
			JCommander jCommander, MainArgs mainArgs,
			SupportedCommandCollection supportedCommands) {
		this.printWriter = printWriter;
		this.mainArgs = mainArgs;
		this.jCommander = jCommander;
		this.supportedCommands = supportedCommands;
		
		//Setup The JCommanderObject with the Supported Commands 
		this.loadSupportedCommands();
		
		//until we successfully call parse, this will be the object we get back
		this.command = new CommandNull();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.ginsu.cli.IInputParsingService#parseInput(java.lang.String[])
	 */
	public void parseInput(String[] input) {

		try
		{
			this.jCommander.parse(input);
			ICommand parsedCommand = this.getParsedCommand();
			if(parsedCommand.shouldRenderCommandUsage())
			{
				StringBuilder stringBuilder = new StringBuilder();
				this.jCommander.usage(parsedCommand.getName(), stringBuilder);
				this.printWriter.println(stringBuilder.toString());
			}
			else
			{
				//Since we are not printing usage, we will set the current 
				//command to the one that was parsed and override the CommandNull 
				//object
				this.command = parsedCommand;
			}
		}
		catch (Throwable e)
		{
			//print a message out to the user
			this.printWriter.println(e.getMessage());
			StringBuilder stringBuilder = new StringBuilder();
			this.jCommander.usage(stringBuilder);
			this.printWriter.println(stringBuilder.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.cli.IInputParsingService#getCommand()
	 */
	public ICommand getCommand() {
		return this.command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.cli.IInputParsingService#getMainCommand()
	 */
	public Hashtable<String, String> getConfigurationOverride() {
		// TODO Auto-generated method stub
		return this.mainArgs.getConfigurationOverride();
	}

	/**
	 * Set the default provider on the CommandLineParsingService.
	 * 
	 * @param defaultProvider
	 *            The object which provides default values for objects passed
	 *            via the command line.
	 */
	public void setDefaultProvider(IDefaultProvider defaultProvider) {
		this.jCommander.setDefaultProvider(defaultProvider);
	}
	
	/**
	 * Get the command parsed from the arguments passed in.
	 * @return the {@link ICommand} that was parsed from the consuming class. 
	 * @throws Exception
	 *             When more or less than one of the supported commands is
	 *             parsed from the input given by the consuming class
	 */
	private ICommand getParsedCommand() throws Exception
	{
		ICommand parsedCommand = this.supportedCommands.get(this.jCommander.getParsedCommand());
		if(parsedCommand == null)
		{
			throw new Exception("You must supply at least one supported command.");
		}
		return parsedCommand;
	}
	
	/**
	 * load the commands that we support into the jCommander object. 
	 */
	private void loadSupportedCommands()
	{
		// add all of the commands in our collection of supported commands
		for (Map.Entry<String, ICommand> entry : supportedCommands
				.entrySet()) {
			jCommander.addCommand(entry.getKey(), entry.getValue());
		}
	}

}
