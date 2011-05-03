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
import org.apache.log4j.Logger;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.intuit.ginsu.ICommand;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 *
 * //TODO RP: We should explain the use of this class once it has been implemented.
 *
 */
@Parameters(commandDescription = "Use this command to run your tests.")
public class CommandRunTests extends Command implements ICommand{

	/**
	 * TODO RP: When we implement this classReplace this Parameter with an actual parameter that we are going to use.
	 */
	public static final String PLACEHOLDER = "-placeholder";
	@Parameter(names = {PLACEHOLDER},
			description = "This is just a placeholder until we implement more of this class")
	public boolean placeholder;
	
	CommandRunTests(PrintWriter printwriter, Logger logger) {
		super(printwriter, logger);
	}

	public static final String NAME = "run-tests";
	
	public int run() {
		int exitStatus  = 0;
		// TODO RP: We still need to finish this feature.
		
		return exitStatus;
	}

	public String getName()
	{
		return CommandRunTests.NAME;
	}

	public boolean isRunnable() {
		return true;
	}
	
	@Override
	public boolean expectsProject()
	{
		return true;
	}
}
