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
import java.util.logging.Logger;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
@Parameters(commandDescription = "Use this command to run your tests.")
public class CommandRunTests extends Command implements ICommand{

	/**
	 * TODO: Replace this Parameter with an actual parameter that we are going to use.
	 */
	public static final String PLACEHOLDER = "-placeholder";
	@Parameter(names = {PLACEHOLDER},
			description = "This is just a placeholder until we implement more of this class")
	public boolean placeholder;
	
	public CommandRunTests(PrintWriter printwriter, Logger logger) {
		super(printwriter, logger);
	}

	public static final String NAME = "run-tests";
	
	public int run() {
		int exitStatus  = 0;
		// TODO Auto-generated method stub
		
		return exitStatus;
	}

	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	public String getName()
	{
		return CommandRunTests.NAME;
	}
	
	@Override
	public boolean equals(Object command)
	{
		return (command != null &&
				command instanceof CommandRunTests &&
				((CommandRunTests)command).getName() == this.getName());
	}

	public boolean isRunnable() {
		return true;
	}
}
