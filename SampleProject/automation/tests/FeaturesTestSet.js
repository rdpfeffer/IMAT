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

SAMPLE.FeaturesTestSet = Class.extend(SAMPLE.BaseSampleTestSet, {
	
	
	title: "FeaturesTestSet",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		this.parent();
		IMAT.log_trace("Initializing the Features Tests.");
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to make sure that the next test runs in the right state.
	 */
	setUp: function()
	{
		this.performActions([["selectFeaturesButton"]]);
	},
	
	/**
	 * tearDown the App to normalize the way tests finish. Reset anything that might cause the next
	 * test to fail.
	 */
	tearDown: function()
	{
		this.performActions([["returnToHomeScreen"]]);
	},
	
	testZoomInAndOutInFeatures : function()
	{
		this.performActions([
			["zoom"],
			["zoom"]
		]);
	},	
	
	testViewDetailsInFeatures : function()
	{
		this.performActions([
			["viewAndCloseDetails"],
			["swipeRight"],
			["viewAndCloseDetails"],
			["swipeRight"],
			["viewAndCloseDetails"]
		]);
	},
	
	testScrollingInFeatures : function()
	{
		this.performActions([
			["swipeRight"],
			["validateImageSwitched", "lego.jpg", "pattern.jpg"],
			["swipeRight"],
			["validateImageSwitched", "pattern.jpg", "time.jpg"],
			["swipeLeft"],
			["validateImageSwitched", "time.jpg", "pattern.jpg"],
			["swipeLeft"],
			["validateImageSwitched", "pattern.jpg", "lego.jpg"]
		]);
	}
});

//After a test is defined, add an instance of it to the global suiteRunner object.
IMAT.suiteRunner.addTestSet( new SAMPLE.FeaturesTestSet());