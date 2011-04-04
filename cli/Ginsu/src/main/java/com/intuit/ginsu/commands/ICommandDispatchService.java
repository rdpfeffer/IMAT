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
 * @dateCreated Mar 26, 2011
 *
 * This interface defines how Commands are dispatched.
 *
 */
public interface ICommandDispatchService {

	/**
	 * Dispatch the given command
	 * @param command - The {@link ICommand} to execute.
	 */
	public void dispatch(ICommand command);
}
