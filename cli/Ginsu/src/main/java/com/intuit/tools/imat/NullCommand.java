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
package com.intuit.tools.imat;

import com.intuit.tools.imat.cli.ExitStatus;

/**
 * @author rpfeffer
 * @dateCreated Mar 26, 2011
 * 
 *              The null command is not actually a command. Instead, it is a
 *              place holder implementation following the null object pattern
 *              for the ICommand interface. By that, we mean that it implements
 *              all of the expected methods in the ICommand interface but none
 *              of those methods actually do anything. This reduces the
 *              conditional logic and allows the application to continue
 *              normally even when there is an input handling error.
 * 
 */
public class NullCommand implements ICommand {

	public static final String NAME = "null";
	private static final ExitStatus STATUS = ExitStatus.VALIDATION_ERROR;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#run()
	 */
	public ExitStatus run() {
		return STATUS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#getName()
	 */
	public String getName() {
		return NullCommand.NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#isRunnable()
	 */
	public boolean isRunnable() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#getExitStatus()
	 */
	public int getExitStatus() {
		// Always return an error code, as the null command only gets created in
		// error states
		return STATUS.getStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#getErrorMessage()
	 */
	public String getErrorMessage() {
		// Note we rely on the application to surface the correct error message.
		// Note: this should only happen during input handling
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#isCommandComplete()
	 */
	public boolean isCommandComplete() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.ICommand#shouldRenderCommandUsage()
	 */
	public boolean shouldRenderCommandUsage() {
		// there is now help for this command so we will always return false
		// here
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.ICommand#expectsProject()
	 */
	public boolean expectsProject() {
		return false;
	}

}
