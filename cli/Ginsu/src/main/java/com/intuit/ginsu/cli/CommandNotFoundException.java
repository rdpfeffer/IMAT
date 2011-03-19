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
 * @dateCreated Mar 13, 2011
 * 
 *              This exception is thrown when a ginsu command is not found.
 * 
 */
public class CommandNotFoundException extends Exception {

	/**
	 * The UID of the exception
	 */
	private static final long serialVersionUID = 3910927828509086193L;

	public String getMessage() {
		return "There was no command found in the given arguments";
	}

}
