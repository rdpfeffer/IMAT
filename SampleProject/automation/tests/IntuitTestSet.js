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

SAMPLE.IntuitTestSet = Class.extend(IMAT.BaseFunctionalTestSet, {
	
	
	title: "IntuitTestSet",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		IMAT.log_debug("Initializing the Intuit Tests.");
		IMAT.log_debug("Note: This is where you could declare your test fixtures.");
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to make sure that the next test runs in the right state.
	 */
	setUp: function()
	{
		this.performActions([["selectIntuitButton"]]);
	},
	
	/**
	 * tearDown the App to normalize the way tests finish. Reset anything that might cause the next
	 * test to fail.
	 */
	tearDown: function()
	{
		this.performActions([["returnToHomeScreen"]]);
	},
	
	setUpTestSet: function()
	{
		this.viewContext = new SAMPLE.HomeScreenView();
	},
	
	tearDownTestSet: function()
	{
		IMAT.log_debug("tearDownTestSet was called");		
		//This is where you would invoke actions to setup the right viewContext after all of the 
		//tests in this test set are run.
	},
	
	/**
	 * Return the base view which we consider to be the "Base State" when recovering from a test
	 * failure. This implementation does nothing and returns undefined. You must override this
	 * in order to get test recovery working correctly. 
	 *
	 * @return SAMPLE.HomeScreenView which is considered the base view for all functional tests
	 */
	getBaseView: function() {
		return new SAMPLE.HomeScreenView();
	},
	
	/**
	 * Test all of the macros so that we have a clear definition of what they will allow.
	 */
	
	testLoadHomePageInIntuit : function()
	{
		this.performActions([
			["waitForActivity"],
			["validateCorrectPageLoaded", "Intuit Small Business - Website Builder, Quickbooks, Payroll & Payment Solutions"]
		]);
	},	
	
	testScrollingWhileInIntuit : function()
	{
		this.performActions([
			["waitForActivity"],
			["scrollDown"],
			["scrollUp"]
		]);
	},
	
	testManuallySomethingThatCurrentlyPasses : function()
	{
		//always passes
		manualPass("7/20/2011");
	},
	
	testManuallySomethingThatCurrentlyFails : function()
	{
		//always fails
		manualFail("7/20/2011");
	}
});

//After a test is defined, add an instance of it to the global suiteRunner object.
IMAT.suiteRunner.addTestSet( new SAMPLE.IntuitTestSet());