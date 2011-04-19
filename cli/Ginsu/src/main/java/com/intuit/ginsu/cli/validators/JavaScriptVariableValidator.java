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
package com.intuit.ginsu.cli.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * @author rpfeffer
 * @dateCreated Apr 17, 2011
 * 
 *              This Class validates that parameters passed in by the user which
 *              are meant to eventually be passed as javascript variables
 *              actually match the specification for what a javascript variable
 *              can be. For more information, @see
 *              http://en.wikipedia.org/wiki/JavaScript_syntax#Variables
 * 
 */
public class JavaScriptVariableValidator implements IParameterValidator {

	/* (non-Javadoc)
	 * @see com.beust.jcommander.IParameterValidator#validate(java.lang.String, java.lang.String)
	 */
	public void validate(String name, String value) throws ParameterException {
		
		//TODO Finish implementing this method.
		
		//cannot Start with a number
		
		
		//cannot be "GINSU"
		
		//cannot contain "@", even though the Actual JS Spec allows it.
		
		

	}

}
