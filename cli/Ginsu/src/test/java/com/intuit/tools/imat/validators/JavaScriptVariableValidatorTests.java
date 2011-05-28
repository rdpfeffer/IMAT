package com.intuit.tools.imat.validators;

import org.testng.annotations.Test;

import com.beust.jcommander.ParameterException;
import com.intuit.tools.imat.cli.App;
import com.intuit.tools.imat.validators.JavaScriptVariableValidator;

public class JavaScriptVariableValidatorTests {
	@Test
	public void testValidInput() {

		JavaScriptVariableValidator validator = new JavaScriptVariableValidator();
		String[] validEntries = new String[] { "_", "$", "a", "z", "A", "Z",
				"z0", "a9", "abcF0Baz_9" };

		for (int i = 0; i < validEntries.length; i++) {
			validator.validate("myVar", validEntries[i]);
		}
	}

	@Test(expectedExceptions = ParameterException.class, 
			expectedExceptionsMessageRegExp = ".*may contain only letters,.*")
	public void testInValidChars() {
		JavaScriptVariableValidator validator = new JavaScriptVariableValidator();
		String[] validEntries = new String[] { "-", "@", "(", "=", "+", ">"};
		for (int i = 0; i < validEntries.length; i++) {
			validator.validate("myVar", validEntries[i]);
		}
	}
	
	@Test(expectedExceptions = ParameterException.class, 
			expectedExceptionsMessageRegExp = ".*may not start with a number.*")
	public void testStartsWithNumber() {

		JavaScriptVariableValidator validator = new JavaScriptVariableValidator();
		String[] validEntries = new String[] { "0", "0a", "0Z", "0_", "0$"};

		for (int i = 0; i < validEntries.length; i++) {
			validator.validate("myVar", validEntries[i]);
		}
	}
	
	@Test(expectedExceptions = ParameterException.class, 
			expectedExceptionsMessageRegExp = ".*may not be equal to.*")
	public void testIsAppName() {
		JavaScriptVariableValidator validator = new JavaScriptVariableValidator();
		validator.validate("myVar", App.APP_NAME_UPPERCASE);
		
	}
}
