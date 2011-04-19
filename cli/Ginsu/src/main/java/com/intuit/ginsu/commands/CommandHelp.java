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

import java.io.PrintWriter;

import java.util.logging.*;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author rpfeffer
 * @dateCreated Mar 26, 2011
 * 
 *              This command renders help text for the Ginsu Application
 * 
 */
@Parameters(commandDescription = "Print out this help text.")
public class CommandHelp extends Command implements ICommand {

	/**
	 * TODO: Replace this Parameter with an actual parameter that we are going to use.
	 */
	public static final String PLACEHOLDER = "-placeholder";
	@Parameter(names = {PLACEHOLDER},
			description = "This is just a placeholder until we implement more of this class",
			hidden = true)
	public boolean placeholder;
	
	
	/**
	 * Create a new Command 
	 * @param printwriter The {@link PrintWriter} to write to.
	 * @param logger The {@link Logger} to log to.
	 */
	public CommandHelp(PrintWriter printwriter, Logger logger) {
		super(printwriter, logger);
	}

	public static final String NAME = "help";
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#run()
	 */
	public int run() {
		
		// TODO Complete this method.
		
		int exitStatus  = 0;
		return exitStatus;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#cleanUp()
	 */
	public void cleanUp() {
		// TODO Auto-generated method stub

	}
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#getName()
	 */
	public String getName()
	{
		return CommandHelp.NAME;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object command)
	{
		return (command != null &&
				command instanceof CommandHelp &&
				((CommandHelp)command).getName() == this.getName());
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#isRunnable()
	 */
	public boolean isRunnable() {
		return true;
	}

}
