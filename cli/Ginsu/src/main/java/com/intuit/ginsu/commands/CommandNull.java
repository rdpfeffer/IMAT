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
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class CommandNull implements ICommand {

	public static final String NAME = "null";
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#run()
	 */
	public void run() {
		//Do nothing
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#cleanUp()
	 */
	public void cleanUp() {
		//Do nothing
	}

	public String getName()
	{
		return CommandNull.NAME;
	}
	
	@Override
	public boolean equals(Object command)
	{
		return (command != null &&
				command.getClass() == this.getClass() &&
				((CommandNull)command).getName() == this.getName());
	}

	public boolean isRunnable() {
		return false;
	}

}
