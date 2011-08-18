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
package com.intuit.tools.imat.cli;

/**
 * @author rpfeffer
 * @dateCreated Aug 13, 2011
 * 
 *              This is an enumeration of all of the Exit Status codes for the
 *              IMAT command line tool. Each exit status meaning and value is
 *              listed below.
 * 
 */
public enum ExitStatus {

	/**
	 * The UNSET exit status (value: -1) allows the implementer of the
	 * ICommandDispatchService to check to see if a given command has been run
	 * yet.
	 */
	UNSET(-1),

	/**
	 * The Success exit status (value: 0) should be returned when a command has
	 * exited normally
	 */
	SUCCESS(0),

	/**
	 * The INTERNAL_ERROR exit status (value: 1) should be used in cases where
	 * internal errors occur that are not at the fault of the imat user.
	 * Normally there will be an exception which accompanies this error code.
	 * This exception typically will be printed out to aide in debugging the
	 * issue.
	 */
	INTERNAL_ERROR(1),

	/**
	 * The TESTING_ERROR exit status (value: 2) should be used for cases where
	 * there is an error in the tests that causes the text execution to complete
	 * prematurely. This will also alert any invoking agent that the reports
	 * will not be complete or even exist to begin with.
	 */
	TESTING_ERROR(2),

	/**
	 * The VALIDATION_ERROR exit status (value: 3) should be used in cases where
	 * the user has entered invalid command line options and needs to correct 
	 * the input.
	 */
	VALIDATION_ERROR(3),

	/**
	 * The SETUP_ERROR exit status (value: 4) should be used in cases where the
	 * user has failed to setup their environment correctly. An example of this
	 * would be in cases where the user incorrectly sets the output path of the
	 * Automation Results.plist file in Instruments.
	 */
	SETUP_ERROR(4);

	private final int status;

	ExitStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the status code
	 */
	public int getStatus() {
		return status;
	}
}
