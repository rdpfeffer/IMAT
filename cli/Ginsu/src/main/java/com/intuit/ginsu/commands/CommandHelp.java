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

/**
 * @author rpfeffer
 * @dateCreated Mar 26, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class CommandHelp extends Command implements ICommand {

	
	public CommandHelp(PrintWriter printwriter, Logger logger) {
		super(printwriter, null);
	}

	public static final String NAME = "help";
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#run()
	 */
	public int run() {
		int exitStatus  = 0;
		// TODO Auto-generated method stub
		
		return exitStatus;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#cleanUp()
	 */
	public void cleanUp() {
		// TODO Auto-generated method stub

	}
	
	public String getName()
	{
		return CommandHelp.NAME;
	}
	
	@Override
	public boolean equals(Object command)
	{
		return (command != null &&
				command instanceof CommandHelp &&
				((CommandHelp)command).getName() == this.getName());
	}

	public boolean isRunnable() {
		return true;
	}

}
