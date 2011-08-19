package com.intuit.tools.imat.reporting;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.tools.imat.cli.BaseFunctionalTest;

public abstract class BaseIOSAutomationResultsTests extends BaseFunctionalTest {

	protected IOSAutomationResultsReader reader = null;
	protected IOSLogEntryToJUnitTranslator translator = null;
	protected LinkedBlockingQueue<Dict> logQueue = null;
	protected LinkedBlockingQueue<JunitTestSuite> reportQueue = null;
	protected static final String LOG_QUEUE_SIZE_KEY = "log_queue_size";
	protected static final String REPORT_QUEUE_SIZE_KEY = "report_queue_size";
	
	protected static final String SUBDIR = "automationResults" + File.separator;

	@BeforeMethod
	public void beforeMethod() {
		logQueue = new LinkedBlockingQueue<Dict>();
		reportQueue = new LinkedBlockingQueue<JunitTestSuite>();

		Logger readerLogger = EasyMock.createNiceMock(Logger.class);
		reader = new IOSAutomationResultsReader(readerLogger, logQueue);

		Logger translatorLogger = EasyMock.createNiceMock(Logger.class);
		translator = new IOSLogEntryToJUnitTranslator(translatorLogger,
				logQueue, reportQueue);
	}

	@AfterMethod
	public void afterMethod() {
		reader = null;
		translator = null;
		logQueue.clear();
		reportQueue.clear();
	}

	@Test
	public void testSyntaxError() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(3));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("ParseError.plist", props);
	}

	@Test
	public void testNestedDictionaries() {
		String filename = "Nested.plist";
		reader.setPlistFile(getTestResourceAsFile(SUBDIR + filename));
		reader.run();
		assert logQueue.size() == 5 : "Buffer was expected to have 5 results for"
				+ " the file named " + filename + " instead it had: "
				+ logQueue.size();

		Dict currentDict = null;
		currentDict = logQueue.remove();
		assert currentDict.countSubDicts() == 0 : "the first element in the "
				+ "queue should not have any nested dictionaries";

		currentDict = logQueue.remove();
		assert currentDict.countSubDicts() == 1 : "the second element in the "
				+ "queue should have 1 nested dictionary";

		currentDict = logQueue.remove();
		assert currentDict.countSubDicts() == 2 : "the third element in the "
				+ "queue should have 2 nested dictionaries";

		currentDict = logQueue.remove();
		assert currentDict.countSubDicts() == 0 : "the last element in the "
				+ "queue should not have any nested dictionaries";
	}

	@Test
	public void testAgainst300KbLogFile() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(1314));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(6));
		verifyAgainstResultFile("300KBOnTrace.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresAfterCleanUpSuite() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(76));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("AssertionExceptionAfterCleanUpSuite.plist",
				props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(76));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("ExceptionAfterCleanUpSuite.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresBeforeInitSuite() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(4));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("AssertionExceptionBeforeInitSuite.plist",
				props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(4));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("ExceptionBeforeInitSuite.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringCleanUpSuite() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(75));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("AssertionExceptionDuringCleanUpSuite.plist",
				props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(75));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("ExceptionDuringCleanUpSuite.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringInitSuite() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(5));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("AssertionExceptionDuringInitSuite.plist",
				props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(5));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("ExceptionDuringInitSuite.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringSetUpTestSet() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(171));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("AssertionExceptionDuringSetUpTestSet.plist",
				props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(171));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("ExceptionDuringSetUpTestSet.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringSetUp() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(84));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("AssertionExceptionDuringSetUp.plist", props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(84));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("ExceptionDuringSetUp.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringTearDown() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(93));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("AssertionExceptionDuringTearDown.plist", props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(84));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("ExceptionDuringTearDown.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringTearDownTestSet() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(131));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile(
				"AssertionExceptionDuringTearDownTestSet.plist", props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(131));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("ExceptionDuringTearDownTestSet.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringTest() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(355));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("AssertionExceptionDuringTest.plist", props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(355));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ExceptionDuringTest.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailures() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(3));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("ParseError.plist", props);
	}

	@Test
	public void testAgainstLogFileWithCleanRun() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(75));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(4));
		verifyAgainstResultFile("CleanRun.plist", props);
	}

	@Test
	public void testAgainstLogFileWithManualRun() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(31));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ManualRun.plist", props);
	}

	protected void verifyAgainstResultFile(String fileName,
			HashMap<String, Object> props) {
		// Do nothing, this must be implemented by the concrete class
	}
}
