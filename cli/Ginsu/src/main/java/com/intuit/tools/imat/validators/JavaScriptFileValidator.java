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
package com.intuit.tools.imat.validators;

import java.io.File;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * @author rpfeffer
 * @dateCreated Jun 5, 2011
 *
 * This class validates that a given parameter is a JavaScript file, rather than
 * a directory or otherwise. To be considered as a file containing javascript it
 * must have a file suffix of ".js" in order to pass this validation.
 *
 */
public class JavaScriptFileValidator implements IParameterValidator {

	private static final String JS_FILE_SUFFIX = ".js";
	
	/* (non-Javadoc)
	 * @see com.beust.jcommander.IParameterValidator#validate(java.lang.String, java.lang.String)
	 */
	public void validate(String name, String value) throws ParameterException {
		File fileValue = new File(value);
		if (!fileValue.exists()) {
			throw new ParameterException("The given file for " + name + " does " +
					"not exist: " + value);
		}
		if (!fileValue.isFile()) {
			throw new ParameterException("The value given for " + name + " was" +
					" not a file: " + value);
		}
		if (!value.endsWith(JS_FILE_SUFFIX)) {
			throw new ParameterException("The file given for " + name + " was" +
				" not a js file: " + value);
		}
	}

}
