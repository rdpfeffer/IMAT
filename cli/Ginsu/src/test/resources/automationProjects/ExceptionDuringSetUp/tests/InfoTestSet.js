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
 * This is a test set writen to test scenarios from the info section of the
 * sample application. It demonstrates the following...
 *
 *			1) How to effectively use setUp() and tearDown() functions to 
 *			simplify your tests and reduce redundant lines of code.
 *
 *			2) The proper way to set up a "run-of-the-mill" test set.
 *
 *			3) When this test set is run using the troublesomeSuite.js suite
 *			file, this test set will show the flow of execution as the 
 *			IMAT.BaseFunctionalTestSet#doCleanup() function recovers from a 
 *			test failure (see testFailureExample() below)
 */
SAMPLE.InfoTestSet = Class.extend(SAMPLE.BaseSampleTestSet, {
	
	
	title: "InfoTestSet",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		this.parent();
		IMAT.log_debug("Initializing the Info Tests.");
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to make sure that the next test runs in the right state.
	 */
	setUp: function()
	{
		//This is our intentional failure
		jones.foo();
		this.performActions([["selectInfoButton"]]);
	},
	
	/**
	 * tearDown the App to normalize the way tests finish. Reset anything that might cause the next
	 * test to fail.
	 */
	tearDown: function()
	{
		this.performActions([["returnToHomeScreen"]]);
	},
	
	testSelectListingInInfo : function()
	{
		this.performActions([
			["selectListing"],
			["waitForActivity"],
			["validateCorrectPageLoaded", "Facebook"],
			["returnToInfoScreen"]
		]);
	},
	
	/**
	 * When this test case is run, it will throw an exception and force the framework to recover.
	 * This is an intentional failure to show that the framework can recover from failtures quickly
	 * if set up correctly.
	 */
	testFailureExample: function() {
		this.performActions([
			["selectListing"],
			["waitForActivity"]
		]);
	},	
	
	testScrollingWhileViewingInfoList : function()
	{
		this.performActions([
			["scrollDown"],
			["scrollUp"]
		]);
	}
});

//After a test is defined, add an instance of it to the global suiteRunner object.
IMAT.suiteRunner.addTestSet( new SAMPLE.InfoTestSet());