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

import java.util.Hashtable;


/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              This Interface defines an interface for how user input should be
 *              interpreted.
 * 
 */
public interface IInputHandlingService {

	/**
	 * Handle and validate the input from the user
	 * @param input An Array of strings representing the user's input
	 */
	public void handleInput(String[] input);
	
	/**
	 * Get the command we parsed from the user's input
	 * @return an {@link ICommand} to be run.
	 */
	public ICommand getCommand();

	/**
	 * Get the configuration override which will replace the existing runtime
	 * configuration for the duration of the command.
	 * 
	 * @return a {@link Hashtable} representing holding the properties which
	 *         will override the existing configuration
	 */
	public Hashtable<String, String> getConfigurationOverride();
	
}
