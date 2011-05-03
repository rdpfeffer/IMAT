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
import com.intuit.ginsu.ICommand;
import com.intuit.ginsu.IncompleteCommandException;

/**
 * @author rpfeffer
 * @dateCreated Mar 26, 2011
 * 
 *              This command renders help text for the Ginsu Application
 * 
 */
@Parameters(commandDescription = "Print out this help text. To get help for a specific command. Type: ginsu [command] -help")
public class UsagePrinter implements ICommand {

	public static final String NAME = "help";
	private final PrintWriter printWriter;
	private String usage;
	private boolean hasBeenWrittenTo = false;

	/**
	 * Create a new Command
	 * 
	 * @param printwriter
	 *            The {@link PrintWriter} to write to.
	 * @param logger
	 *            The {@link Logger} to log to.
	 */
	UsagePrinter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#run()
	 */
	public int run() {
		printWriter.println(this.usage);
		int exitStatus = 0;
		return exitStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#getName()
	 */
	public String getName() {
		return UsagePrinter.NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#isRunnable()
	 */
	public boolean isRunnable() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.ICommand#getExitStatus()
	 */
	public int getExitStatus() throws IncompleteCommandException {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.ICommand#getErrorMessage()
	 */
	public String getErrorMessage() throws IncompleteCommandException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.ICommand#isCommandComplete()
	 */
	public boolean isCommandComplete() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.ICommand#shouldRenderCommandUsage()
	 */
	public boolean shouldRenderCommandUsage() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.ICommand#expectsProject()
	 */
	public boolean expectsProject() {
		return false;
	}

	/**
	 * Set the usage information on the usage Printer. After a call to this
	 * function, {@link com.intuit.ginsu.cli.UsagePrinter#hasBeenWrittenTo()}
	 * will return true
	 * 
	 * @param usage
	 *            the usage to set
	 */
	public void setUsage(String usage) {
		this.usage = usage;
		hasBeenWrittenTo = true;
	}

	/**
	 * @return true if the usage text has been set, false otherwise.
	 */
	public boolean hasBeenWrittenTo() {
		return hasBeenWrittenTo;
	}

}
