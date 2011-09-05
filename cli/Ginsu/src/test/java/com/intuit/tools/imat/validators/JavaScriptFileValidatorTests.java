package com.intuit.tools.imat.validators;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.beust.jcommander.ParameterException;

public class JavaScriptFileValidatorTests {

	private JavaScriptFileValidator validator;

	@BeforeClass
	public void createValidator() {
		this.validator = new JavaScriptFileValidator();
	}

	@Test
	public void testHappyCase() {
		File nonJSFile = null;
		try {
			nonJSFile = File.createTempFile("somefile", ".js");
			nonJSFile.deleteOnExit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			assert false : "could not create temp file";
		}
		testHelper(nonJSFile.getAbsolutePath());
	}

	@Test(expectedExceptions = ParameterException.class,
			expectedExceptionsMessageRegExp = "^.*was not a \\.js file.*$")
	public void testNonJSFile() {
		File nonJSFile = null;
		try {
			nonJSFile = File.createTempFile("NonJS", ".txt");
			nonJSFile.deleteOnExit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			assert false : "could not create temp file";
		}
		testHelper(nonJSFile.getAbsolutePath());
	}

	@Test(expectedExceptions = ParameterException.class,
			expectedExceptionsMessageRegExp = "^.*does not exist.*$")
	public void testNonExistantJSFile() {
		testHelper("nonExistant.js");
	}

	@Test(expectedExceptions = ParameterException.class,
			expectedExceptionsMessageRegExp = "^.*was not a file.*$")
	public void testDirectory() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("dir", ".txt");
			tempFile.deleteOnExit();
		} catch (IOException e) {
			assert false : "could not create temp file";
		}
		String dirPath = tempFile.getParentFile().getAbsolutePath()
				+ File.separator + "someDir.js";
		File dir = new File(dirPath);
		dir.mkdirs();
		testHelper(dir.getAbsolutePath());
	}

	private void testHelper(String fileName) throws ParameterException {
		this.validator.validate("anythingReally", fileName);
	}
}
