package com.intuit.tools.imat.validators;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.beust.jcommander.ParameterException;

public class TemplateFileValidatorTests {
	
	private TemplateFileValidator validator;
	
	@BeforeClass
	public void createValidator() {
		this.validator = new TemplateFileValidator();
	}
	
	@Test
	public void testHappyCase() {
		File traceTemplateFile = null;
		File traceFile = null;
		try {
			traceTemplateFile = File.createTempFile("somefile", ".tracetemplate");
			traceFile = File.createTempFile("somefile", ".trace");
			traceTemplateFile.deleteOnExit();
			traceFile.deleteOnExit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			assert false : "could not create temp file";
		}
		testHelper(traceTemplateFile.getAbsolutePath());
		testHelper(traceFile.getAbsolutePath());
	}

	@Test(expectedExceptions = ParameterException.class,
			expectedExceptionsMessageRegExp = "^.*not the correct file type.*$")
	public void testNonTemplateFile() {
		File nonJSFile = null;
		try {
			nonJSFile = File.createTempFile("NonTemplate", ".txt");
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
		testHelper("nonExistant.tracetemplate");
	}

	private void testHelper(String fileName) throws ParameterException {
		this.validator.validate("anythingReally", fileName);
	}
}
