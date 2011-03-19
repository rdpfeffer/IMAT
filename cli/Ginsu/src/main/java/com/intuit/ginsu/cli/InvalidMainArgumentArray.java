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
 *              This exception is thrown when Ginsu gets an invalid set of
 *              arguments passed in. This occurs when 0 arguments are passed in
 *              or when the input we get is like *really* weird.
 * 
 */
public class InvalidMainArgumentArray extends Exception {

	/**
	 * The UID for this Exception
	 */
	private static final long serialVersionUID = -6511774857737999001L;

	/**
	 * Get the customized message for this exception
	 */
	public String getMessage() {
		return "Ginsu Expects at least one argument.";
	}

}
