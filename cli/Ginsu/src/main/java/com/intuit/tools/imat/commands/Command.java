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
package com.intuit.tools.imat.commands;

import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.beust.jcommander.Parameter;
import com.intuit.tools.imat.ICommand;
import com.intuit.tools.imat.IncompleteCommandException;

/**
 * @author rpfeffer
 * @dateCreated Apr 3, 2011
 * 
 *              This is the base class for all commands. It provides a partial
 *              implementation of the ICommand interface.
 * 
 */
public abstract class Command implements ICommand {

	private static final int UNSET_EXIT_STATUS = -1;

	private static final int PRINT_HELP_EXIT_STATUS = 0;

	private static final String UNSET_ERROR_MESSAGE = "";
	protected final PrintWriter printWriter;
	protected final Logger logger;
	protected int exitStatus;
	protected String errorMessage;

	/**
	 * The flag used in the command line client for specifying when the the help
	 * text for this command should be printed.
	 */
	@Parameter(names = { "-help" }, description = "Print the usage for this command.")
	public boolean help = false;

	/**
	 * @param printwriter
	 * @param logger
	 */
	Command(PrintWriter printwriter, Logger logger) {
		this.printWriter = printwriter;
		this.logger = logger;
		this.exitStatus = UNSET_EXIT_STATUS;
		this.errorMessage = UNSET_ERROR_MESSAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#getExitStatus()
	 */
	public int getExitStatus() throws IncompleteCommandException {
		this.assertCommandComplete();
		return this.exitStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#getErrorMessage()
	 */
	public String getErrorMessage() throws IncompleteCommandException {
		this.assertCommandComplete();
		return this.errorMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#isCommandComplete()
	 */
	public boolean isCommandComplete() {
		return (this.exitStatus != UNSET_EXIT_STATUS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#shouldPrintCommandUsage()
	 */
	public boolean shouldRenderCommandUsage() {
		if (this.help) {
			this.exitStatus = PRINT_HELP_EXIT_STATUS;
		}
		return this.help;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.ICommand#expectsProject()
	 */
	public boolean expectsProject() {
		return false;
	}

	/**
	 * @throws IncompleteCommandException
	 *             when the Command is not complete and it should be.
	 */
	private void assertCommandComplete() throws IncompleteCommandException {
		if (!this.isCommandComplete()) {
			throw new IncompleteCommandException(this, Thread.currentThread()
					.getStackTrace()[3].getMethodName());
		}
	}
}
