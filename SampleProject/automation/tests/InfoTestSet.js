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