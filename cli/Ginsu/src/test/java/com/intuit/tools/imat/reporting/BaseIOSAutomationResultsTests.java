package com.intuit.tools.imat.reporting;

import java.io.File;
import java.io.IOException;
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
	protected JUnitReportWriter writer = null;
	protected LinkedBlockingQueue<Dict> logQueue = null;
	protected LinkedBlockingQueue<JunitTestSuite> reportQueue = null;
	protected File tempFile = null;
	protected File tempDir = null;
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

		Logger writerLogger = EasyMock.createNiceMock(Logger.class);
		writer = new JUnitReportWriter(writerLogger, reportQueue);

		try {
			tempFile = File.createTempFile("BaseIOSAutomationResultsTests",
					".txt");
			tempDir = new File(tempFile.getParentFile().getAbsolutePath() 
					+ File.separator + "reports");
			tempDir.mkdir();
			writer.setTargetDir(tempDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert writer.getTargetDir() != null : "Temp dir was null";
	}

	@AfterMethod
	public void afterMethod() {
		reader = null;
		translator = null;
		logQueue.clear();
		reportQueue.clear();
		tempFile.delete();
		deleteAllFilesInGeneratedProject(tempDir.getAbsolutePath());
	}

	@Test
	public void testSyntaxError() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(1));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("ParseError.plist", props);
	}

	@Test
	public void testAgainst300KbLogFile() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(1690));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(6));
		verifyAgainstResultFile("300KBOnTrace.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresAfterCleanUpSuite() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(74));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("AssertionExceptionAfterCleanUpSuite.plist",
				props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(74));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ExceptionAfterCleanUpSuite.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresBeforeInitSuite() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(1));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("AssertionExceptionBeforeInitSuite.plist",
				props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(1));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("ExceptionBeforeInitSuite.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringCleanUpSuite() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(66));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("AssertionExceptionDuringCleanUpSuite.plist",
				props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(66));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ExceptionDuringCleanUpSuite.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringInitSuite() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(10));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("AssertionExceptionDuringInitSuite.plist",
				props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(11));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("ExceptionDuringInitSuite.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringSetUpTestSet() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(73));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("AssertionExceptionDuringSetUpTestSet.plist",
				props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(58));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ExceptionDuringSetUpTestSet.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringSetUp() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(41));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("AssertionExceptionDuringSetUp.plist", props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(41));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ExceptionDuringSetUp.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringTearDown() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(80));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("AssertionExceptionDuringTearDown.plist", props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(80));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ExceptionDuringTearDown.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringTearDownTestSet() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(73));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile(
				"AssertionExceptionDuringTearDownTestSet.plist", props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(73));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ExceptionDuringTearDownTestSet.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailuresDuringTest() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(87));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("AssertionExceptionDuringTest.plist", props);

		logQueue.clear();
		reportQueue.clear();

		props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(87));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ExceptionDuringTest.plist", props);
	}

	@Test
	public void testAgainstLogFileWithFailures() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(1));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(0));
		verifyAgainstResultFile("ParseError.plist", props);
	}

	@Test
	public void testAgainstLogFileWithCleanRun() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(73));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("CleanRun.plist", props);
	}

	@Test
	public void testAgainstLogFileWithManualRun() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(27));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ManualRun.plist", props);
	}
	
	@Test
	public void testAgainstLogFileWithExceptionDuringTestRecovery() {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(LOG_QUEUE_SIZE_KEY, new Integer(55));
		props.put(REPORT_QUEUE_SIZE_KEY, new Integer(1));
		verifyAgainstResultFile("ExceptionDuringTestRecovery.plist", props);
	}

	protected void verifyAgainstResultFile(String fileName,
			HashMap<String, Object> props) {
		// Do nothing, this must be implemented by the concrete class
	}
}
