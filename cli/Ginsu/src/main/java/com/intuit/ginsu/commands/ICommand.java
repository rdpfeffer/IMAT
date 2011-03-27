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

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 *
 * This defines the main interface for Commands in Ginsu. All commands
 * should implement this interface.
 *
 */
public interface ICommand {

	/**
	 * Execute the command.
	 */
	public void run();
	
	/**
	 * Clean up after the command has finished.
	 */
	public void cleanUp();
	
	/**
	 * @return true if the ICommand is runnable, false otherwise
	 */
	public boolean isRunnable();
}
