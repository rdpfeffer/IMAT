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
 * 	Constructs a SuiteRunner object
 *	@class The {@link IMAT.SuiteRunner} object is the highest level object 
 *  managing the test execution lifecycle. It's major responsibilities include:
 *  <ul>
 *		<li>Providing an API for suite files of an automation project to run and
 *  	manage tests</li>
 *		<li>Giving a preview of all tests considered runnable given it's current 
 * 		state</li>
 *  	<li>Maintaining an array of Test Sets.</li>
 *  </ul>
 * <b>IMPORTANT NOTE</b>: This class wraps the {@link IMAT.TestRunner} object, 
 * so you should never create one yourself or call it directly.
 * @memberOf IMAT 
 */
IMAT.SuiteRunner = Class.create(/** @lends IMAT.SuiteRunner# */{
	
	/**
	 * The constructor called when invoking "new" on this class object.
	 * @ignore 
	 */
	initialize: function()
	{
		this.testSetArray = new Array();
		this.testRunner;
	},
	
	/**
	 * Runs a given test case
	 * 
	 * @param {SuiteHander} suiteHandler 
	 * 					An object that implements the following functions:
	 * <ul>
	 * 		<li><code>initSuite()</code></li>
	 *		<li><code>cleanUpSuite()</code></li>
	 * </ul>
	 */
	runTests: function(suiteHandler)
	{
		var testSet;
		IMAT.log_debug("Running Suite...");
		if(suiteHandler)
		{
			var initSuiteToken = "suiteHandler.initSuite();"; 
			var cleanUpSuiteToken = "suiteHandler.cleanUpSuite();";
			IMAT.log_start(initSuiteToken);
			if (!IMAT.settings.SKIP_INIT_SUITE) {
				suiteHandler.initSuite();
			}
			IMAT.log_pass(initSuiteToken);
			while (testSet = this.testSetArray.pop())
			{
				this.testRunner.runTestCases(testSet);
			}
			IMAT.log_start(cleanUpSuiteToken);
			if (!IMAT.settings.SKIP_CLEAN_UP_SUITE) {
				suiteHandler.cleanUpSuite();
			}
			IMAT.log_pass(cleanUpSuiteToken);
		}
		else
		{
			throw "suiteHandler was not defined when calling SuiteRunner.runTests(...)"	
		}
	},
	
	/**
	 * Log out a list of testCases that will be run when 
	 * {@link IMAT.SuiteRunner.runTests} is called. Call this function after 
	 * setting the filters. <b>NOTE:</b> Since this should be used to debug 
	 * which tests are included in a filter, this should be run when 
	 * {@link IMAT.settings.logLevel} is set to {@link IMAT.logLevels.LOG_DEBUG}
	 * or higher. 
	 *
	 *  @example
	 *  //What follows is an example of a suite file located in the suites/ 
	 *  //directory of an automation project generated by IMAT. If run, it would
	 *  //spit out a log with all of the tests matching the filters that were set
	 *
	 *  var IMAT_TARGET_PLATFORM = "ios";
	 *
	 *  #import "../project.js"
	 *
	 *  //run the tests
	 *  IMAT.suiteRunner.addFilters(["List.*\\.test.*Search"]);
	 *  IMAT.suiteRunner.previewAllRunnableTests();
	 */
	previewAllRunnableTests: function()
	{
		IMAT.log_debug("Previewing all runnable tests...");
		for(var i = 0; i < this.testSetArray.length; i++)
		{
			this.testRunner.previewRunnableTests(this.testSetArray[i]);
		}
	},
	
	/**
	 * Adds a set of test cases to the current list of test cases to run. By 
	 * convetion we normally call this after declaring a test set. See the 
	 * example below. 
	 *
	 * @param {IMAT.BaseTestSet} testSet 
	 * 					The object containing a set of test objects to run.
	 * @example
	 * EXAMPLE.MyTestSet = Class.extend(IMAT.BaseFunctionalTest, {
	 * 		...Tests would normally go here...
	 * });
	 *  
	 * IMAT.suiteRunner.addTestSet( new EXAMPLE.MyTestSet()); 
	 */
	addTestSet: function(testSet)
	{		
		this.testSetArray.push(testSet);
	},
	
	/**
	 * Add filters to the current set maintained by the TestRunner object. All 
	 * filters added will be concatenated with the existing set. <b>Note:</b>
	 * this function can be called more than once if you have the need to split 
	 * your test filtering accross mulitple places. 
	 *
	 * @param {array} filterArray 
	 * 						An array of strings representing filters. NOTE  
	 * 						that strings given representing regular expressions 
	 * 						are supported and encouraged! 
	 * 
	 * Filters represented as Regular expressions do not need to include the 
	 * wrapping "/" characters. Also note that modifiers like g, i etc are not 
	 * supported. Since the regular expressions are represented as strings, all 
	 * "\" should be escaped like "\\" 
	 *
	 * @example 
	 * //Selects all test cases with the word "search" in the signature.
	 * IMAT.suiteRunner.addFilters(["search"]);
	 * 
	 * //Selects all test cases with the word "search" or the word "navigate" in
	 * //the signature.
	 * IMAT.suiteRunner.addFilters(["search", "navigate"]); 
	 *
	 * //Selects all test cases whose Test Sets have the word "List" in their
	 * //title and whose method signatures have the word "Search"
	 * //Also note the double backslash we used to escape the period part of the
	 * //total test signature.
	 * IMAT.suiteRunner.addFilters(["List.*\\.test.*Search"]); 
	 * 
	 * //would give...
	 * DEBUG: 	TestSet: ItemListTestSet
	 * DEBUG: 		ItemListTestSet.testSearchForNonExistantItems()
	 */
	addFilters: function(filterArray)
	{		
		this.testRunner.addFilters(filterArray);
	},
	
	/**
	 *  Set the testRunner
	 *
	 *  @param {IMAT.TestRunner} runner 
	 * 					The test runner object we wish to set. 
	 * 
	 *  @ignore 
	 */
	setTestRunner: function(runner)
	{
		this.testRunner = runner;
	}
	
});

