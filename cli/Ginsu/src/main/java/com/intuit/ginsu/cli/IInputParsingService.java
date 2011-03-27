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

import com.intuit.ginsu.commands.ICommand;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public interface IInputParsingService {

	/**
	 * Parse and validate the input from the user
	 * @param input An Array of strings representing the user's input
	 */
	public void parseInput(String[] input);
	
	/**
	 * Get he command we parsed from the user's input
	 * @return an {@link ICommand} to be run.
	 */
	public ICommand getCommand();

	/**
	 * Get the main command context under which the parsed command will run.
	 * @return an {@link ICommand} representing the Command Context
	 */
	public ICommand getMainCommandContext();
	
}
