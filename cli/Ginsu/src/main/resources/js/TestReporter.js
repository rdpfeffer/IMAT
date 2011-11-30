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
 * Creates and Maintains Reporting Statistics 
 * @class The {@link IMAT.TestReporter} object synthesises statistics and 
 * maintains data to keep track of test counts, pass/failure rates, and will
 * report on the number of manual vs. automated tests there are in a code base.
 */
IMAT.TestReporter = Class.extend(/** @lends IMAT.TestReporter# */{
	
	/**
	 * The constructor called when invoking "new" on this class object.
	 * @ignore 
	 */
	initialize: function()
	{
		this.automatedTests = [];
		this.manualTests = [];
		this.incompleteTests = [];
		this.numFailed = 0
		this.numErrored = 0;
		this.numPassed = 0;
	},
	
	/**
	 * 
	 */
	reportProjection: function() 
	{
		var numAutomatedTests = this.automatedTests.length;
		var numManualTests = this.manualTests.length; 
		var percentAutomated = undefined;
		if (numAutomatedTests + numManualTests > 0) {
			percentAutomated = numAutomatedTests / (numAutomatedTests + numManualTests) * 100;
			IMAT.log_info("Number of Automated Tests:\t" + numAutomatedTests);
			IMAT.log_info("Number of Manual Tests:\t\t" + numManualTests);
			IMAT.log_info("Percentage automated:\t\t" + percentAutomated + "%");
			IMAT.log_info("****Expected Tests****");
			this.listTests(this.automatedTests);
			this.listTests(this.manualTests);
			IMAT.log_info("**********************");
		} else {
			IMAT.log_info("There were no tests to report against.");
		}
	},
	
	reportRetrospective: function()
	{
		this.collectResultsOfTests();
		var numAutomatedTests = this.automatedTests.length;
		var numManualTests = this.manualTests.length; 
		var totalPossibleTests = numAutomatedTests + numManualTests;
		if ( totalPossibleTests > 0) {
			var passPercentage = this.numPassed / totalPossibleTests * 100;
			IMAT.log_info("Number of Tests passed:\t\t" + this.numPassed);
			IMAT.log_info("Number of Tests failed:\t\t" + this.numFailed);
			IMAT.log_info("Number of Tests with errors:\t" + this.numErrored);
			IMAT.log_info("Pass Percentage:\t\t\t" + passPercentage + "%");
			if (this.incompleteTests.length > 0) {
				var completionPercentage = this.incompleteTests.length / totalPossibleTests * 100;
				IMAT.log_info("Number of Tests that did not run: " + this.incompleteTests.length);
				IMAT.log_info("Completion Percentage: " + completionPercentage);
				IMAT.log_info("****Tests that did not get run.****");
				this.listTests(this.incompleteTests);
				IMAT.log_info("**********************");
			} else {
				IMAT.log_info("Completion Percentage:\t\t100%");
			}
		} else {
			IMAT.log_info("There were no tests to report against.");
		}
	},
	
	
	addTest: function(testSignature)
	{
		var test = {};
		test.hasRun = false;
		test.result = undefined;
		if (this.isManualTest(testSignature)) {
			this.manualTests[testSignature] = test;
			this.manualTests.length++;
		} else {
			IMAT.log_trace("Adding Automated test: " + testSignature);
			this.automatedTests[testSignature] = test;
			this.automatedTests.length++;
		}
	},
	
	markTestAsRanWithResult: function(testSignature, testResult) 
	{
		if (this.isManualTest(testSignature) && this.manualTests[testSignature]) {
			this.manualTests[testSignature].hasRun = true;
			this.manualTests[testSignature].result = testResult;
		} else if (this.automatedTests[testSignature]) {
			this.automatedTests[testSignature].hasRun = true;
			this.automatedTests[testSignature].result = testResult;
		}
	},
	
	/**
	 * Determine if a test is a manual test based on it's signature. A test is a
	 * manual test if the function name starts with "testManually"
	 *  
	 * 	@param {String}
	 *					The signature of the test. 
	 *
	 *  @returns {boolean} true if the test is manual, false otherwise
	 *  @see  IMAT.TestRunner#getTestCaseSignature
	 */
	isManualTest: function(testSignature)
	{
		var manualTestPattern = /^[0-9,a-z,A-Z,_]+\.testManually[0-9,a-z,A-Z,_]+/;
		var result = false;
		if (testSignature && typeof testSignature === "string") {
			result = manualTestPattern.test(testSignature);
		}
		IMAT.log_trace("Is " + testSignature + " a manual test signature?: " + 
			(result ? "YES":"NO"));
		return result;
	},
	
	/*
	 * Log the test signatures in one of the test arrays of this object.
	 * @ignore 
	 *  
	 * 	@param {Array}
	 *					The array of test signatures 
	 * 
	 */
	listTests: function(testArray) 
	{
		for (testSignature in testArray) 
		{
			IMAT.log_info(testSignature);
		}
	},
	
	collectResultsOfTests: function()
	{
		var numberFailed = 0;
		var numberPassed = 0;
		var numberErrored = 0;
		var incompleteTests = [];
		this.iterateOverTestsWithCallback(function(test, signature){
			if (test.hasRun) {
				if (test.result === IMAT.TestReporter.RESULT_PASS) {
					numberPassed++;
				} else if (test.result === IMAT.TestReporter.RESULT_FAILURE) {
					numberFailed++;
				} else if (test.result === IMAT.TestReporter.RESULT_ERROR) {
					numberErrored++;
				}
			} else {
				incompleteTests[signature] = test;
				incompleteTests.length++;
			}		
		});
		this.numFailed = numberFailed
		this.numErrored = numberErrored;
		this.numPassed = numberPassed;
		this.incompleteTests = incompleteTests;
	},
	
	/**
	 * 
	 */
	iterateOverTestsWithCallback: function(callback)
	{
		for (autoTest in this.automatedTests) {
			callback(this.automatedTests[autoTest], autoTest);
		}
		for (manualTest in this.manualTests) {
			callback(this.manualTests[manualTest], manualTest);
		}
	}
});

IMAT.TestReporter.RESULT_ERROR = "ERROR";
IMAT.TestReporter.RESULT_FAILURE = "FAILURE";
IMAT.TestReporter.RESULT_PASS = "PASS";