package com.intuit.tools.imat.validators;

import java.io.File;
import java.io.IOException;

import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.beust.jcommander.ParameterException;
import com.intuit.tools.imat.validators.CanonicalFileValidator.FileValidationHelper;

public class CanonicalFileValidatorTests {
	@Test
	public void testCanonicalFileExists() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("TempFile", ".txt");
		} catch (IOException e) {
			assert false : "Could not create temp file";
		}
		CanonicalFileValidator validator = new CanonicalFileValidator();
		validator.validate("Anything Goes Here!", tempFile.getPath());
		tempFile.delete();
	}

	@Test(expectedExceptions = ParameterException.class, expectedExceptionsMessageRegExp = "^Could not resolve given path.*")
	public void testCanonicalFileDoesNotExist() {
		String nonExistantFile = "andTheFileWasNeverFoundAgain.txt";
		FileValidationHelper mockHelper = EasyMock
				.createMock(FileValidationHelper.class);
		try {
			mockHelper.validate("andTheFileWasNeverFoundAgain.txt");
		} catch (IOException e) {
			assert false: "Easy mock misconfiguration";
		}
		EasyMock.expectLastCall().andThrow(new IOException());
		EasyMock.replay(mockHelper);
		CanonicalFileValidator validator = new CanonicalFileValidator();
		validator.helper = mockHelper;
		validator.validate("Anything Goes Here!",
				nonExistantFile);
		EasyMock.verify(mockHelper);
	}
}
