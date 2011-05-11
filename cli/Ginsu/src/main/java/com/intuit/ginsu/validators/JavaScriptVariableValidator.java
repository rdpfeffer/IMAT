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
package com.intuit.ginsu.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import com.google.common.base.CharMatcher;
import com.intuit.ginsu.cli.App;

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

	private static final CharMatcher NON_JAVASCRIPT_VAR = CharMatcher
			.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z'))
			.or(CharMatcher.inRange('0', '9')).or(CharMatcher.is('$'))
			.or(CharMatcher.is('_')).negate();

	private static final CharMatcher NUMBERS = CharMatcher.inRange('0', '9');
	private static final String TRY_HELP = "Try \"" + App.APP_NAME_LOWERCASE
			+ " [command] -help\" to get more information.";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.beust.jcommander.IParameterValidator#validate(java.lang.String,
	 * java.lang.String)
	 */
	public void validate(String name, String value) throws ParameterException {
		if (NUMBERS.matches(value.charAt(0))) {
			throw new ParameterException(name + " may not start with a number."
					+ " Given: " + value + " " + TRY_HELP);
		}
		for (int i = 0; i < value.length(); i++) {
			if (NON_JAVASCRIPT_VAR.matches(value.charAt(i))) {
				throw new ParameterException(name
								+ " may contain only letters,"
								+ " numbers, and any of the following characters: '$', "
								+ "'_'. Was given the value: " + value + " "
								+ TRY_HELP);
			}
		}
		if (value.equals(App.APP_NAME_UPPERCASE)) {
			throw new ParameterException(name + " may not be equal to \""
					+ App.APP_NAME_UPPERCASE + "\". " + TRY_HELP);
		}
	}
}
