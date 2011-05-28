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

/**
 * @author rpfeffer
 * @dateCreated Apr 3, 2011
 * 
 *              This exception class defines how the application should report
 *              exceptions related to incomplete commands
 * 
 */
public class IncompleteCommandException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with custom messaging for Incomplete Commands
	 * 
	 * @param command
	 *            The Command which was running when it was called.
	 * @param methodNameExcludingParenthesis
	 *            the name of the method that required command to be complete
	 *            before it was called.
	 */
	public IncompleteCommandException(ICommand command,
			String methodNameExcludingParenthesis) {
		super("Internal Error: Tried to call " + methodNameExcludingParenthesis
				+ "() on command of type " + command.getClass().toString()
				+ " before the command had completed execution.");
	}
}
