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

SAMPLE.EventsTestSet = Class.extend(IMAT.BaseFunctionalTestSet, {
	
	title: "EventsTestSet",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		IMAT.log_debug("Initializing the Events Tests.");
		IMAT.log_debug("Note: This is where you could declare your test fixtures.");
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to make sure that the next test runs in the right state.
	 */
	setUp: function()
	{
		this.performActions([["selectEventsButton"]]);
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
		//initialize the view context before any tests in this test set run.
		this.viewContext = new SAMPLE.HomeScreenView();
	},
	
	/**
	 * @return SAMPLE.HomeScreenView which is considered the base view for all functional tests
	 */
	getBaseView: function() {
		return new SAMPLE.HomeScreenView();
	},
	
	/**
	 * Test all of the macros so that we have a clear definition of what they will allow.
	 */
	
	testSelectListingInEvents : function()
	{
		this.performActions([
			["selectListing"],
			["waitForActivity"],
			["returnToEventsScreen"]
		]);
	},
	
	testScrollingWhileViewingEventsList : function()
	{
		this.performActions([
			["scrollToBottom"],
			["scrollToTop"],
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
IMAT.suiteRunner.addTestSet( new SAMPLE.EventsTestSet());