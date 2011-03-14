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

/**
 * @author rpfeffer
 * @dateCreated Mar 13, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class InternalCommandParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1849263877730901463L;

	/**
	 * 
	 */
	public String getMessage()
	{
		return "An internal command parsing exception has occured that "
		+ "should not happen. Please report this bug to the maintainers " 
		+ "of ginsu along with a copy of the command that was used to "
		+ "generate the exception";
	}
}
