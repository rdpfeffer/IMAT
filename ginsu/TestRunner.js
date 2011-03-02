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

GINSU.TestRunner = Class.extend({
	
	/**
	 * 	Constructs a TestRunner object
	 *
	 * 	@param numRetries number - The number of times the testrunner should try to run a test over
	 * 		again before logging a failure.
	 * 	@TODO implement retry mechanism.
	 */
	initialize: function(numRetries)
	{
		this.retries = numRetries;
		this.filtersArray = new Array();
	},
	
	/**
	 * Returns the signature for the test case
	 * 
	 * @param testCaseName string - the name of the test case to be run.
	 * @param testSet object - A testSet in our test harness
	 * 
	 * @return string The signature.
	 */
	getTestCaseSignature: function(testSet, testCaseName)
	{
		return testSet.title + "." + testCaseName + "()";
	},
	
	/**
	 * Determines if a given property is a function we should run as part of the test
	 * 
	 * @param prop string - A property of the test object which may or may not be a test case
	 * @param testSet object - A testSet in our test harness
	 * 
	 * @return boolean true if prop is a function and starts with word "test" like 
	 * "testSomething: function(){...},"
	 */
	isPropValidTestCase: function(prop, testSet)
	{
		var funcPattern = /^test[0-9,a-z,A-Z,_]+/;
		return typeof testSet[prop] == "function" && funcPattern.test(prop.toString());
	},
	
	/**
	 * Determines if a test case matches at least one of the filters given with the test set.
	 * 
	 * @param testCase string - the name of the test case to be run.
	 * @param testSet object - A testSet in our test harness
	 * 
	 * @return boolean true if the testCase matches at least one of the filters.
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
	 * Convenience function for determining if the prop is a test in the testSet that should be run.
	 * This function validates that prop is both a valid test function and that it matches at least 
	 * one filter.
	 * 
	 * @param prop string the name of a propery in testSet
	 * @param testSet string the testSet object we are currently executing against
	 *
	 * @returns boolean true if the test should be run.
	 */
	isPropRunnableTest: function(prop, testSet)
	{
		return (this.isPropValidTestCase(prop, testSet) && this.testCaseMatchesFilter(prop, testSet));
	},
	
	/**
	 * Runs a set of test cases. This is the main function of the TestRunner class. It drives the
	 * whole harness
	 *
	 * @param testSet object The object containing a set of test objects to run.
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
	 * Print out a list of the runnable tests in the testSet. This is a function written for
	 * test development convenience to see if your filters are including the appropriate tests.
	 *
	 * @param testSet the set we are to potentially execute against.
	 */
	previewRunnableTests: function(testSet)
	{
		GINSU.log_debug("	TestSet: " + testSet.title);
		for(prop in testSet)
		{
			if (this.isPropRunnableTest(prop, testSet)) 
			{
				GINSU.log_debug("		" + testSet.title + "." + prop + "()");
			}
		}
	},
	
	/**
	 * Runs a given test case
	 * 
	 * @param testSet object - A testSet running in our test harness.
	 * @param testCaseName string - The name of the test case in our test we are going to run.
	 * 
	 * @return boolean true if the test passes
	 */
	runTestCase: function(testSet, testCaseName)
	{
		var testSignature = this.getTestCaseSignature(testSet, testCaseName);
		
		//this try block allows us our "fail fast" approach
		try 
		{
			GINSU.log_start(testSignature);
			testSet.setUp();
			testSet[testCaseName]();
			testSet.tearDown();
			//if we get here, the test has passed.
			GINSU.log_pass(testSignature);
		}
		catch (e) 
		{
			//first log an error
			GINSU.log_error(e);
			//then print out the current view tree
			if (testSet.logOnFailure) 
			{
				GINSU.log_state();
			}
			//finally log that the test has failed.
			GINSU.log_fail(testSignature);
			
			//Invoke the cleanup process so that we can make an attempt at starting the next test 
			//fresh
			testSet.doCleanup();
		}
	},
	
	/**
	 * Runs a set of test cases. This is the main function of the TestRunner class. It drives the
	 * whole harness
	 *
	 * @param testSet object The object containing a set of test objects to run.
	 */
	runTestCases: function(testSet, test)
	{		
		if(this.containsRunnableTestCases(testSet))
		{
			GINSU.log_start(testSet.title + ".setUpTestSet();");
			GINSU.log_trace("Setting up the test set");
			testSet.setUpTestSet();
			GINSU.log_pass(testSet.title + ".setUpTestSet();");
			for(prop in testSet)
			{
				//NOTE: We do not check for hasOwnProperty() because that function does not check the 
				//prototype of the object, just the object itself
				if (this.isPropRunnableTest(prop, testSet)) 
				{
					this.runTestCase(testSet, prop);
				}
			}
			GINSU.log_start(testSet.title + ".tearDownTestSet();");
			GINSU.log_trace("Tearing down the test set.");
			testSet.tearDownTestSet();
			GINSU.log_pass(testSet.title + ".tearDownTestSet();");
		}
	},
	
	/**
	 * Add filters to the current set maintained by the TestRunner object. All filters added will
	 * be concatenated with the existing set.
	 *
	 * @param filtersArray array of strings representing filters. NOTE that regular expressions are
	 * supported and encouraged! 
	 *
	 * @see this.previewRunnableTests() to test if your filter is working correctly.
	 * 
	 * @discussion Filters represented as Regular expressions do not need to include the wrapping "/"
	 * characters. Also note that modifiers like g, i etc are not supported. Since the regular 
	 * expressions are represented as strings, all "\" should be escaped like "\\"
	 *
	 * @example /^Regular(\W)expressions(\W)are(\W)cool(\W)\.$/ should be represented as...
	 *	"^Regular(\\W)expressions(\\W)are(\\W)cool(\\W)\\.$"
	 */
	addFilters : function(filtersArray)
	{
		this.filtersArray = this.filtersArray.concat(filtersArray);
	}
	
});
