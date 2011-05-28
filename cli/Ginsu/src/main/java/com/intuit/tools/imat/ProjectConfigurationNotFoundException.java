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
 * @dateCreated Apr 25, 2011
 * 
 *              This exeption represents an exception related to project
 *              configuration. Some commands and features of IMAT, require
 *              reference to an existing IMAT automation project and will not
 *              function correctly if that project does not exist. This
 *              exception provides a means to surface that condition to the rest
 *              of the application.
 * 
 */
public class ProjectConfigurationNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 *            The message to relay for this exception.
	 */
	public ProjectConfigurationNotFoundException(String message) {
		super("The configuration properties for this project were not found."
				+ message);
	}
}
