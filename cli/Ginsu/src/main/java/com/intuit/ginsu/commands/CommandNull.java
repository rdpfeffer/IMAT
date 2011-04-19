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
 *              The null command is not actually a command. Instead, it is a
 *              place holder implementation following the null object pattern
 *              for the ICommand interface. By that, we mean that it implements
 *              all of the expected methods in the ICommand interface but none
 *              of those methods actually do anything. This reduces the
 *              conditional logic and allows the application to complete
 *              normally even when there is a parsing error.
 * 
 */
public class CommandNull implements ICommand {

	public static final String NAME = "null";
	private static final int NULL_COMMAND_ERROR_CODE = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#run()
	 */
	public int run() {
		return this.getExitStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#cleanUp()
	 */
	public void cleanUp() {
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#getName()
	 */
	public String getName() {
		return CommandNull.NAME;
	}

	@Override
	public boolean equals(Object command) {
		return (command != null && command instanceof CommandNull && ((CommandNull) command)
				.getName() == this.getName());
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#isRunnable()
	 */
	public boolean isRunnable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#getExitStatus()
	 */
	public int getExitStatus() {
		// Always return an error code, as the null command only gets created in
		// error states
		return NULL_COMMAND_ERROR_CODE;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#getErrorMessage()
	 */
	public String getErrorMessage() {
		// Note we rely on the application to surface the correct error message.
		// Note: this should only happen during input parsing
		return "";
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#isCommandComplete()
	 */
	public boolean isCommandComplete() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.commands.ICommand#shouldRenderCommandUsage()
	 */
	public boolean shouldRenderCommandUsage() {
		// there is now help for this command so we will always return false here
		return false;
	}

}
