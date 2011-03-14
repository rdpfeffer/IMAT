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
public class CommandNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3910927828509086193L;
	
	public String getMessage()
	{
		return "There was no command found in the given arguments";
	}

}
