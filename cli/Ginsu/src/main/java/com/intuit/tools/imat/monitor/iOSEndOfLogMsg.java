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
package com.intuit.tools.imat.monitor;

/**
 * @author rpfeffer
 * @dateCreated Aug 13, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public enum iOSEndOfLogMsg {

	IMPORT_EXCEPTION	("An exception occurred while trying to run the script."),
//	RANGE_ERROR			("Exception raised while running script: RangeError:"),
//	REFERENCE_ERROR		("Exception raised while running script: ReferenceError:"),
//	SYNTAX_ERROR		("Exception raised while running script: SyntaxError:"),
//	TYPE_ERROR			("Exception raised while running script: TypeError:"),
	PARSE_ERROR			("Script threw an uncaught JavaScript error: Parse error"),
	ERROR_PREFIX		("Exception raised while running script:"),
	STOPPED_BY_USER		("Script was stopped by the user."),
	SCRIPT_COMPLETED	("Script completed.");
	
	private final String message;
	iOSEndOfLogMsg(String message) {
		this.message = message;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
