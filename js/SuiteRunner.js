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

GINSU.SuiteRunner = Class.create({
	
	/**
	 * Empty Initializer
	 */
	initialize: function()
	{
		this.testSetArray = new Array();
		this.testRunner;
	},
	
	/**
	 * Runs a given test case
	 * 
	 * @param test object - A test running in our test harness.
	 * @param testCaseName string - The name of the test case in our test we are going to run.
	 * 
	 * @return boolean true if the test passes
	 */
	runTests: function(suiteHandler)
	{
		var testSet;
		GINSU.log_debug("Running Suite...");
		if(suiteHandler)
		{
			
			GINSU.log_start("suiteHandler.initSuite();");
			suiteHandler.initSuite();
			GINSU.log_pass("suiteHandler.initSuite();");
			while (testSet = this.testSetArray.pop())
			{
				this.testRunner.runTestCases(testSet);
			}
			GINSU.log_start("suiteHandler.cleanUpSuite();");
			suiteHandler.cleanUpSuite();
			GINSU.log_pass("suiteHandler.cleanUpSuite();");
		}
		else
		{
			throw "suiteHandler was not defined when calling SuiteRunner.runTests(...)"	
		}
	},
	
	previewAllRunnableTests: function()
	{
		GINSU.log_debug("Previewing all runnable tests...");
		for(var i = 0; i < this.testSetArray.length; i++)
		{
			this.testRunner.previewRunnableTests(this.testSetArray[i]);
		}
	},
	
	/**
	 * Adds a set of test cases to the current list of test cases to run.
	 *
	 * @param testSet object The object containing a set of test objects to run.
	 */
	addTestSet: function(testSet)
	{		
		this.testSetArray.push(testSet);
	},
	
	/**
	 * Adds the filters in the filterArray into the maintained list of filters.
	 *
	 * @param filterArray array - An array of strings which define what tests to include.
	 * 	If left blank, this will include all tests. Note that the string may represent a regular
	 * 	expression.
	 */
	addFilters: function(filterArray)
	{		
		this.testRunner.addFilters(filterArray);
	},
	
	setTestRunner: function(runner)
	{
		this.testRunner = runner;
	}
	
});

