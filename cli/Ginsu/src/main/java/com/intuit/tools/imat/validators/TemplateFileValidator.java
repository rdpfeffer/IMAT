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
 * @dateCreated Jun 9, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class TemplateFileValidator implements IParameterValidator {

	private static final String TEMPLATE_FILE_SUFFIX = ".tracetemplate";
	private static final String TRACE_FILE_SUFFIX = ".trace";
	
	/* (non-Javadoc)
	 * @see com.beust.jcommander.IParameterValidator#validate(java.lang.String, java.lang.String)
	 */
	public void validate(String name, String value) throws ParameterException {
		File fileValue = new File(value);
		if (!fileValue.exists()) {
			throw new ParameterException("The given file for " + name
					+ " does not exist: " + value + ". If you did not supply a"
					+ " value for " + name + ", it may be that the default" 
					+ " documentation  value is not a valid option. Please read"
					+ " the command's documentation for more detail.");
		}
		if (!value.endsWith(TEMPLATE_FILE_SUFFIX)
				&& !value.endsWith(TRACE_FILE_SUFFIX)) {
			throw new ParameterException("The file given for " + name + " was"
					+ " not the correct file type. It must be one of "
					+ TEMPLATE_FILE_SUFFIX + " or " + TRACE_FILE_SUFFIX + "."
					+ " Instead, we were given the file named: " + value);
		}
	}

}
