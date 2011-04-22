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

import org.apache.log4j.Logger;

import com.beust.jcommander.Parameters;
import com.intuit.ginsu.commands.ICommand;
import com.intuit.ginsu.commands.IncompleteCommandException;

/**
 * @author rpfeffer
 * @dateCreated Mar 26, 2011
 * 
 *              This command renders help text for the Ginsu Application
 * 
 */
@Parameters(commandDescription = "Print out this help text. To get help for a specific command. Type: ginsu [command] -help")
public class UsagePrinter implements ICommand {
	
	private final PrintWriter printWriter;
	private final String usage;
	
	
	/**
	 * Create a new Command 
	 * @param printwriter The {@link PrintWriter} to write to.
	 * @param logger The {@link Logger} to log to.
	 */
	public UsagePrinter(PrintWriter printWriter, String usage) {
		this.printWriter = printWriter;
		this.usage = usage;
	}

	public static final String NAME = "help";
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#run()
	 */
	public int run() {	
		printWriter.println(this.usage);
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
		return UsagePrinter.NAME;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object command)
	{
		return (command != null &&
				command instanceof UsagePrinter &&
				((UsagePrinter)command).getName() == this.getName());
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#isRunnable()
	 */
	public boolean isRunnable() {
		return true;
	}

	public int getExitStatus() throws IncompleteCommandException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getErrorMessage() throws IncompleteCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCommandComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean shouldRenderCommandUsage() {
		// TODO Auto-generated method stub
		return true;
	}

}
