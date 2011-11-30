/*******************************************************************************
* Copyright (c) 2011 Intuit, Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.opensource.org/licenses/eclipse-1.0.php
* 
* Contributors:
*     Intuit, Inc - initial API and implementation
*******************************************************************************/

/**
 * 	Constructs a TestRunner object
 *	@class The {@link IMAT.TestRunner} object is the workhorse of the test
 *	execution lifecycle of a given Test Set. It's major responsibilities 
 *	include:
 *  <ul>
 *		<li>Inspecting a Test Set to determine which properties are
 * 		runnable testcases.</li>
 *		<li>Filtering out tests which do not match the set of internal 
 * 		filters it currently holds</li>
 *  	<li>Running tests which match the filters it currently holds</li>
 *  </ul>
 * @memberOf IMAT 
 */
 /*
  * @TODO implement an optional retry mechanism to gain more validation when
  *	tests fail.
  */
IMAT.TestRunner = Class.extend(/** @lends IMAT.TestRunner# */{
	
	/**
	 * The constructor called when invoking "new" on this class object.
	 * @ignore 
	 */
	initialize: function()
	{
		this.filtersArray = [];
	},
	
	/**
	 * Returns the signature for the test case
	 * 
	 * @param {IMAT.BaseTestSet} testSet
	 * 					A testSet in our test harness
	 * @param {string} testCaseName 
	 * 					The name of the test case to be run.
	 * 
	 * @return {string} The signature of the test case. This will be of the form
	 * <code>SomeTestSet.testCaseName()</code> where SomeTestSet is the test set
	 *  class and testCaseName is the method representing the test case.
	 */
	getTestCaseSignature: function(testSet, testCaseName)
	{
		return testSet.title + "." + testCaseName + "()";
	},
	
	/**
	 * Determines if a given property is a function we should run as part of the 
	 * test
	 * 
	 * @param {mixed} prop
	 * 					A property of the test object which may or may not be a 
	 * 					test case
	 * @param {IMAT.BaseTestSet} testSet
	 * 					A testSet in our test harness
	 * 
	 * @return {boolean} true if prop is a function and starts with word "test" 
	 * like "testSomething: function(){...},"
	 */
	isPropValidTestCase: function(prop, testSet)
	{
		var funcPattern = /^test[0-9,a-z,A-Z,_]+/;
		return typeof testSet[prop] == "function" && funcPattern.test(prop.toString());
	},
	
	/**
	 * Determines if a test case matches at least one of the filters given with 
	 * the test set.
	 * 
	 * @param {string} testCase
	 * 					The name of the test case to be run.
	 * @param {IMAT.BaseTestSet} testSet
	 * 					A testSet in our test harness
	 * 
	 * @return {boolean} true if the testCase matches at least one of the filters.
	 */
	testCaseMatchesFilter: function(testCase, testSet)
	{
		var filterPattern;
		var isMatch = false;
		var testSignature = this.getTestCaseSignature(testSet, testCase);
		if(this.filtersArray.length > 0)
		{
			for(var i = 0; i < this.filtersArray.length; i++)
			{
				filterPattern = new RegExp(this.filtersArray[i]);
				if (filterPattern.test(testSignature))
				{
					isMatch = true;
					break;
				}
			}
		}
		else
		{
			isMatch = true;
		}
		return isMatch;
	},
	
	/**
	 * Convenience function for determining if the prop is a test in the testSet 
	 * that should be run. This function validates that prop is both a valid 
	 * test function and that it matches at least one filter.
	 * 
	 * @param {mixed} prop
	 * 					A property of the test object which may or may not be a 
	 * 					test case
	 * @param {IMAT.BaseTestSet} testSet
	 * 					A testSet in our test harness
	 *
	 * @returns {boolean} true if the test should be run.
	 */
	isPropRunnableTest: function(prop, testSet)
	{
		return (this.isPropValidTestCase(prop, testSet) && 
			this.testCaseMatchesFilter(prop, testSet));
	},
	
	/**
	 * Determine if a Test Set contains runnable test cases.
	 *
	 * @param {IMAT.BaseTestSet} testSet.
	 * 					A testSet in our test harness
	 *
	 * @returns {boolean} true if the testSet has runnable test cases.
	 */
	containsRunnableTestCases: function(testSet)
	{		
		var isRunnable = false;
		for(prop in testSet)
		{
			if (this.isPropRunnableTest(prop, testSet)) 
			{
				isRunnable = true;
				break;
			}
		}
		return isRunnable;
	},
	
	/**
	 * Returns an array of the runnable tests in the testSet. 
	 *
	 * @param {IMAT.BaseTestSet} testSet.
	 * 					A Test Set which we may execute against.
	 */
	collectRunnableTests: function(testSet, runnableTests)
	{
		if (!runnableTests) {
			runnableTests = [];
		}
		for(prop in testSet) {
			if (this.isPropRunnableTest(prop, testSet))  {
				runnableTests.push(this.getTestCaseSignature(testSet, prop));
			}
		}
	},
	
	/**
	 * Logs out a list of the runnable tests in the testSet. This is a function 
	 * written for test development convenience to see if your filters are 
	 * including the appropriate tests.
	 *
	 * @param {IMAT.BaseTestSet} testSet.
	 * 					A Test Set which we may execute against.
	 */
	previewRunnableTests: function(testSet)
	{
		IMAT.log_debug("\tTestSet: " + testSet.title);
		for(prop in testSet)
		{
			if (this.isPropRunnableTest(prop, testSet)) 
			{
				IMAT.log_debug("\t\t" + testSet.title + "." + prop + "()");
			}
		}
	},
	
	/**
	 * Runs a given test case in the test set. Never call this function 
	 * directly. Its merely a helper for {@link IMAT.TestRunner.runTestCases}
	 * 
	 * @param {IMAT.BaseTestSet} testSet.
	 * 					A Test Set which we will execute.
	 * @param {string} testCase
	 * 					The name of the test case we are about to run.
	 */
	runTestCase: function(testSet, testCaseName) {
		var testSignature = this.getTestCaseSignature(testSet, testCaseName);
		
		//this try block allows us our "fail fast" approach
		try {
			IMAT.log_start(testSignature);
			if (!IMAT.settings.SKIP_SET_UP_TEST_CASE) {
				testSet.setUp();
			}
			testSet[testCaseName]();
			if (!IMAT.settings.SKIP_TEAR_DOWN_TEST_CASE) {
				testSet.tearDown();
			}
			//if we get here, the test has passed.
			IMAT.log_pass(testSignature);
			this.markTestAsRanWithResult(testSignature, IMAT.TestReporter.RESULT_PASS);
		} catch (e)  {
			this.logException(e, testSignature);
			//Invoke the cleanup process so that we can make an attempt at  
			//starting the next test fresh
			if (!IMAT.settings.SKIP_DO_CLEANUP) {
				testSet.doCleanup();
			}
		}
	},
	
	/**
	 * Runs a set of test cases. This is the main function of the TestRunner 
	 * class. It drives each test set through its testing lifecycle.
	 *
	 * @param {IMAT.BaseTestSet} testSet.
	 * 					A Test Set which we will execute.
	 * 
	 * @param {function} test
	 *  				The test we are about to run.
	 */
	runTestCases: function(testSet, test)
	{		
		if(this.containsRunnableTestCases(testSet))
		{
			// NOTE: The Code which translates the Plist files over to Junit
			// XML reports keys off these tokens to do the translation. 
			// In terms of guaranteeing that the report can be generated it is
			// important to ensure that these tokens are always printed out at
			// the beginning and end of each testSet.
			var setUpToken = testSet.title + ".setUpTestSet();";
			var tearDownToken = testSet.title + ".tearDownTestSet();";
			try {
				IMAT.log_start(setUpToken);
				if (!IMAT.settings.SKIP_SET_UP_TEST_SET ) {
					testSet.setUpTestSet();
				} 
				IMAT.log_pass(setUpToken);
			} catch (setUpException) {
				this.logException(setUpException, setUpToken);
			}
			for(prop in testSet)
			{
				// NOTE: We do not check for hasOwnProperty() because that  
				// function does not check the prototype of the object, just the
				// object itself
				if (this.isPropRunnableTest(prop, testSet)) 
				{
					this.runTestCase(testSet, prop);
				}
			}
			try {
				IMAT.log_start(tearDownToken);
				if (!IMAT.settings.SKIP_TEAR_DOWN_TEST_SET) {
					testSet.tearDownTestSet();
				}
				IMAT.log_pass(tearDownToken);
			} catch (tearDownException) {
				this.logException(tearDownException, tearDownToken);
			}
		}
	},
	
	/**
	 * Maintain an internal reference to the array of filters so that the set of
	 * runnable tests can be generated. Users should never call this method 
	 * directly.
	 *
	 * @param {array} the Array of strings to use as filters.
	 * 
	 * @see IMAT.SuiteRunner#previewAllRunnableTests for the full set of 
	 * documentation.
	 */
	addFilters : function(filtersArray)
	{
		this.filtersArray = this.filtersArray.concat(filtersArray);
	},
	
	/**
	 * Handle the exception from a failure in the execution of the test life-
	 * cycle. Note, this is used internally by The testing framework and should
	 * not be used directly in an automation project.
	 * 
	 * This function will extract the message from the exception object 
	 * if it derives from the JavaScript Error object and do everything it can
	 * to ensure that the exception is converted to a string before loging out 
	 * to the results log. Furthermore, this function will handle the logging of
	 * the current state of the application shown in view and mark the test as 
	 * either failed or needing attention depending on the type of exception 
	 * thrown. If the exception comes from one of the IMAT assertion APIs
	 * (e.g. assertTrue()) then the test will be marked as failing. However, if
	 * there is any other type of exception, the exception will be logged as an
	 * issue so that the testing engineers known that there is a problem in the 
	 * scripts.
	 *
	 * @param {mixed} exception 
	 * 						The exception to be handled.
	 * @param {srting} signature
	 * 						the signature of the function where the exception  
	 * 						came from.
	 * 
	 * @see IMAT.settings.LOG_STATE_ON_ERROR if you would like to turn off 
	 * logging the state of the application when an error happens. 
	 * @see IMAT.AssertionException for more information on the exception 
	 *  object which will report as test failures.
	 */
	logException: function(exception, signature)
	{
		//first get the error string and log it out.
		var errorString = "";
		if (typeof exception == "string") {
			errorString = exception;
		} else if (exception.message) {
			errorString = exception.message;
		} else {
			errorString = exception.toString();
		}
		IMAT.log_error(errorString);
		
		//then print out the current view tree
		if (IMAT.settings.LOG_STATE_ON_ERROR) {
			IMAT.log_state();
		}
		//classify the test as having failed or needing attention
		if (exception instanceof IMAT.AssertionException) {
			IMAT.log_fail(signature);
			this.markTestAsRanWithResult(signature, IMAT.TestReporter.RESULT_FAILURE);
		} else {
			IMAT.log_issue(signature);
			this.markTestAsRanWithResult(signature, IMAT.TestReporter.RESULT_ERROR);
		}
	},
	
	setTestReporter: function(testReporter) 
	{
		this.reporter = testReporter;
	},
	
	markTestAsRanWithResult: function(testSignature, testResult) 
	{
		if (this.reporter) {
			this.reporter.markTestAsRanWithResult(testSignature, testResult);
		}
	}
});
