package com.intuit.tools.imat.reporting;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.tools.imat.cli.BaseFunctionalTest;

public class IOSAutomationResultsReaderTests extends BaseFunctionalTest {

	private IOSAutomationResultsReader reader = null;
	private LinkedBlockingQueue<Dict> queue = null;
	private static final String subdir = "automationResults" + File.separator;

	@BeforeMethod
	public void beforeMethod() {
		queue = new LinkedBlockingQueue<Dict>();
		Logger logger = EasyMock.createNiceMock(Logger.class);
		this.reader = new IOSAutomationResultsReader(logger, queue);
	}

	@AfterMethod
	public void afterMethod() {
		reader = null;
		queue.clear();
	}

	@Test
	public void testSyntaxError() {
		String filename = "ParseError.plist";
		reader.setPlistFile(getTestResourceAsFile(subdir + filename));
		reader.run();
		assert queue.size() == 3 : "Buffer was expected to have three results for"
				+ " the file named " + filename;
	}
	
	@Test
	public void testNestedDictionaries() {
		String filename = "Nested.plist";
		reader.setPlistFile(getTestResourceAsFile(subdir + filename));
		reader.run();
		assert queue.size() == 5 : "Buffer was expected to have 5 results for"
				+ " the file named " + filename + " instead it had: " +
				queue.size();
		
		Dict currentDict = null;
		currentDict = queue.remove();
		assert currentDict.countSubDicts() == 0 : "the first element in the " +
				"queue should not have any nested dictionaries";
		
		currentDict = queue.remove();
		assert currentDict.countSubDicts() == 1 : "the second element in the " +
				"queue should have 1 nested dictionary";

		currentDict = queue.remove();
		assert currentDict.countSubDicts() == 2 : "the third element in the " +
				"queue should have 2 nested dictionaries";
		
		currentDict = queue.remove();
		assert currentDict.countSubDicts() == 0 : "the last element in the " +
				"queue should not have any nested dictionaries";
	}
}
