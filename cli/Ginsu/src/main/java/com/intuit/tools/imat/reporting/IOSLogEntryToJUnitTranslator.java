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
package com.intuit.tools.imat.reporting;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

/**
 * @author rpfeffer
 * @dateCreated Aug 15, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
public class IOSLogEntryToJUnitTranslator implements Runnable {

	private static final String SUITE_ENDED = "tearDownTestSet";

	private final Logger logger;
	private final BlockingQueue<Dict> testLogQueue;
	private final BlockingQueue<JunitTestSuite> reportQueue;
	private JunitTestSuite currentSuite;
	private long currentSuiteExecutionTime;
	private DateFormat dateFormat;
	private int testSuiteCount;

	IOSLogEntryToJUnitTranslator(Logger logger,
			BlockingQueue<Dict> testLogQueue,
			BlockingQueue<JunitTestSuite> reportQueue) {

		this.logger = logger;
		this.testLogQueue = testLogQueue;
		this.reportQueue = reportQueue;
		this.dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	public void run() {
		logger.debug("translating to Junit Report Objects");
		Dict dict;
		IOSPhaseTranslator translator = new PreInitSuitePhaseTranslator();
		try {
			while (testLogQueueIsNotEmpty((dict = testLogQueue.take()))) {
				logger.trace("processing dictionary entry: " + dict.toString());
				translator.process(dict);
			}
		} catch (InterruptedException e) {
			logger.error(e);
		}
		
	}

	private boolean testLogQueueIsNotEmpty(Dict dict) {
		boolean isNotEmpty = true;
		if (dict.getCode() == UIALoggerCode.STOPPED) {
			isNotEmpty = false;
		}
		return isNotEmpty;
	}

	private JunitTestCase createTestCaseFromStart(Dict dict) {
		String name = dict.getString().split("\\.")[1];
		JunitTestCase testCase = new JunitTestCase();
		testCase.setClassname(currentSuite.getName());
		testCase.setTestcasename(name);
		testCase.setStartTime(dict.getDate());
		return testCase;
	}

	private void completeTestCase(JunitTestCase testCase, Dict dict,
			String logBuffer) {
		JunitTestCaseInnerMessage innerMessage = new JunitTestCaseInnerMessage();
		// complete the setup of the test case based upon the status of the
		// dictionary log entry.
		switch (dict.getCode()) {
		case FAIL:
			innerMessage.setMessage(logBuffer);
			testCase.setInnerMessage(innerMessage);
			currentSuite.incrementFailureCount();
			testCase.setStatus(JunitTestCase.STATUS.FAIL);
			break;
		case ISSUE:
			innerMessage.setMessage(logBuffer);
			testCase.setInnerMessage(innerMessage);
			//Isses show up as errors in the Junit Reports
			currentSuite.incrementErrorCount();
			testCase.setStatus(JunitTestCase.STATUS.ERROR);
			break;
		case PASS:
			testCase.setStatus(JunitTestCase.STATUS.PASS);
			break;
		default:
			assert false : "Tried to complete a testCase on an entry that "
					+ "was not a completing entry";
		}

		// Compute the duration of the test case
		long testCaseDuration = 0;
		try {
			Date start = dateFormat.parse(testCase.getStartTime());
			Date end = dateFormat.parse(testCase.getEndTime());
			testCaseDuration = (end.getTime() - start.getTime()) / 1000;
		} catch (ParseException e) {
			logger.error("Parsing exeption while calculating test"
					+ "case duration.", e);
		}
		currentSuiteExecutionTime += testCaseDuration;
		testCase.setTime(String.valueOf(testCaseDuration));

		// finally add the test case to the list of cases in this suite
		currentSuite.getTestCaseList().add(testCase);
	}

	private void completeSuite(Dict dict) {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String hostName = addr.getHostName();
			currentSuite.setHostname(hostName);

		} catch (UnknownHostException e) {
			logger.warn("Could not determine host name for this "
					+ "suite", e);
			currentSuite.setHostname("Unknown Host");
		}

		currentSuite.setTime(String.valueOf(currentSuiteExecutionTime));
		currentSuite.setId(Integer.toString(testSuiteCount));
		currentSuite.setPackagename("");
		int inumTests = currentSuite.getTestCaseList().size();
		currentSuite.setTests(Integer.toString(inumTests));
		currentSuite.setTimestamp(dict.getDate());
		try {
			reportQueue.put(currentSuite);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testSuiteCount++;
	}
	
	/**
	 * This phase is primarily for future proofing in case we need to do more
	 * calculation or processing before the automation starts interacting with
	 * the UI.
	 * 
	 * @author rpfeffer
	 * @dateCreated Aug 17, 2011 
	 */
	private class PreInitSuitePhaseTranslator implements IOSPhaseTranslator {
		private static final String INIT_SUITE = "suiteHandler.initSuite()";

		public IOSPhaseTranslator process(Dict dict) {
			IOSPhaseTranslator nextPhaseTranslator;
			if (dict.getString().contains(INIT_SUITE)) {
				// Move on to the next phase
				nextPhaseTranslator = new InitSuitePhaseTranslator();
			} else {
				nextPhaseTranslator = this;
			}
			return nextPhaseTranslator;
		}
	}

	private class InitSuitePhaseTranslator implements IOSPhaseTranslator {
		public IOSPhaseTranslator process(Dict dict) {
			IOSPhaseTranslator nextPhase = this;

			// once complete jump to the next phase
			if (dict.isCompletionLogEntry()) {
				nextPhase = new SetUpTestSetPhaseTranslator();
			}
			// if we see an exception jump to the end
			if (dict.isExceptionLogEntry()) {
				nextPhase = new PostCleanUpSuitePhaseTranslator();
			}
			return nextPhase;
		}
	}

	private class SetUpTestSetPhaseTranslator implements IOSPhaseTranslator {
		private JunitTestCase testCase;
		private String logBuffer = "";

		public IOSPhaseTranslator process(Dict dict) {
			IOSPhaseTranslator nextPhase = this;

			// Found SUITE_STARTED pattern
			if (dict.isStartLogEntry()) {
				// Intialize suite execution time.
				currentSuiteExecutionTime = 0;
				String currentSuiteName = dict.getString().split("\\.")[0];
				currentSuite = new JunitTestSuite();
				currentSuite.setName(currentSuiteName);
				testCase = createTestCaseFromStart(dict);
			} else if (dict.isCompletionLogEntry()) {
				completeTestCase(testCase, dict, logBuffer);
				nextPhase = new TestCasePhaseTranslator();
			} else {
				logBuffer = logBuffer + dict.getString() + "\n";
			}
			return nextPhase;
		}
	}

	private class TestCasePhaseTranslator implements IOSPhaseTranslator {
		private JunitTestCase testCase;
		private String logBuffer = "";

		public IOSPhaseTranslator process(Dict dict) {
			IOSPhaseTranslator nextPhase = this;
			if (dict.isStartLogEntry()) {
				testCase = createTestCaseFromStart(dict);
			} else if (dict.isCompletionLogEntry()) {
				completeTestCase(testCase, dict, logBuffer);
				nextPhase = new CleanUpTestCasePhaseTranslator(testCase);
			} else {
				logBuffer = logBuffer + dict.getString() + "\n";
			}
			return nextPhase;
		}
	}

	private class CleanUpTestCasePhaseTranslator implements IOSPhaseTranslator {
		private final JunitTestCase previousTestCase;
		private boolean cleanupSeparatorAdded = false;
		private static final String separator = ""
				+ "---------------------- Cleanup Logs ----------------------\n";

		CleanUpTestCasePhaseTranslator(JunitTestCase previousTestCase) {
			this.previousTestCase = previousTestCase;
		}

		public IOSPhaseTranslator process(Dict dict) {
			IOSPhaseTranslator nextPhase = this;
			if (dict.isStartLogEntry()) {
				//determine if the next phase starting is a test or a test set
				//tear down
				if (dict.getString().contains(SUITE_ENDED)) {
					nextPhase = new TearDownTestSetPhaseTranslator();
				} else {
					nextPhase = new TestCasePhaseTranslator();
				}
				nextPhase = nextPhase.process(dict);
			} else {
				JunitTestCaseInnerMessage inner = previousTestCase.getInnerMessage();
				if (!cleanupSeparatorAdded) {
					inner.appendToMessage(separator);
					cleanupSeparatorAdded = true;
				}
				inner.appendToMessage(dict.getString() + "\n");
			}
			return nextPhase;
		}
	}

	private class TearDownTestSetPhaseTranslator implements IOSPhaseTranslator {
		private JunitTestCase testCase;
		private String logBuffer = "";
		
		public IOSPhaseTranslator process(Dict dict) {
			IOSPhaseTranslator nextPhase = this;
			
			if (dict.isStartLogEntry()) {
				testCase = createTestCaseFromStart(dict);
			} else if (dict.isCompletionLogEntry()) {
				completeTestCase(testCase, dict, logBuffer);
				completeSuite(dict);
				nextPhase = new PostTestSetPhaseTranslator();
			} else {
				logBuffer = logBuffer + dict.getString() + "\n";
			}
			
			return nextPhase;
		}
	}
	
	private class PostTestSetPhaseTranslator implements IOSPhaseTranslator {
		private static final String CLEANUP_SUITE = "suiteHandler.cleanUpSuite()";
		
		public IOSPhaseTranslator process(Dict dict) {
			IOSPhaseTranslator nextPhase = this;
			if (dict.getString().contains(CLEANUP_SUITE)) {
				nextPhase = new CleanUpSuitePhaseTranslator();
			} else {
				nextPhase = new SetUpTestSetPhaseTranslator();
			}
			nextPhase = nextPhase.process(dict);
			return nextPhase;
		}
	}

	private class CleanUpSuitePhaseTranslator implements IOSPhaseTranslator {
		public IOSPhaseTranslator process(Dict dict) {
			IOSPhaseTranslator nextPhase = this;
			if (dict.isCompletionLogEntry() || dict.isExceptionLogEntry()) {
				nextPhase = new PostCleanUpSuitePhaseTranslator();
			}
			return nextPhase;
		}
	}

	private class PostCleanUpSuitePhaseTranslator implements IOSPhaseTranslator {
		public IOSPhaseTranslator process(Dict dict) {
			return this;
		}
	}

}
