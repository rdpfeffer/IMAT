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
 * This is a test set writen to test scenarios from the Events section of the
 * sample application. It demonstrates the following...
 *
 *			1) How to effectively use setUp() and tearDown() functions to 
 *			simplify your tests and reduce redundant lines of code.
 *
 *			2) The proper way to set up a "run-of-the-mill" test set.
 */
SAMPLE.EventsTestSet = Class.extend(SAMPLE.BaseSampleTestSet, {
	
	title: "EventsTestSet",
	
	initialize: function()
	{
		this.parent();
	},
	
	/**
	 * Navigate to the events section of the applicaiton
	 */
	setUp: function()
	{
		this.performActions([["selectEventsButton"]]);
	},
	
	/**
	 * Return to the home screen
	 */
	tearDown: function()
	{
		this.performActions([["returnToHomeScreen"]]);
	},
	
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
	}
});

//After a test is defined, add an instance of it to the global suiteRunner object.
IMAT.suiteRunner.addTestSet( new SAMPLE.EventsTestSet());