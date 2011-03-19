/*******************************************************************************
 * Copyright (c) 2011 Intuit, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * Contributors:
 *     Intuit, Inc - initial API and implementation
 *******************************************************************************/
package com.intuit.ginsu.cli;

/**
 * @author rpfeffer
 * @dateCreated Mar 11, 2011
 * 
 *              This class exists to provide exception information about invalid
 *              ginsu commands.
 * 
 */
public class InvalidCommandException extends Exception {

	/**
	 * The UID for this exception
	 */
	private static final long serialVersionUID = -6088637440575571239L;
	private String command;

	/**
	 * The default constructor for this class
	 * 
	 * @param command
	 *            A {@link String} representing the command.
	 */
	public InvalidCommandException(String command) {
		this.command = command;
	}

	/**
	 * Print the stack trace with a customized message.
	 */
	public void printStackTrace() {
		System.out.println(this.getMessage());
		super.printStackTrace();
	}

	/**
	 * get the customized message for this exception.
	 */
	public String getMessage() {
		return "The command \"" + this.command + "\" does not exist!";
	}
}
