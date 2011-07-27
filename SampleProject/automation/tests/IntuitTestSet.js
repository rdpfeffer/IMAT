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

SAMPLE.IntuitTestSet = Class.extend(SAMPLE.BaseSampleTestSet, {
	
	
	title: "IntuitTestSet",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		this.parent();
		IMAT.log_debug("Initializing the Intuit Tests.");
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
	}
});

//After a test is defined, add an instance of it to the global suiteRunner object.
IMAT.suiteRunner.addTestSet( new SAMPLE.IntuitTestSet());