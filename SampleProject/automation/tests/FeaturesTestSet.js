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
 * This is a test set writen to test scenarios from the Features section of the
 * sample application. It demonstrates the following...
 *
 *			1) How to effectively use setUp() and tearDown() functions to 
 *			simplify your tests and reduce redundant lines of code.
 *
 *			2) The proper way to set up a "run-of-the-mill" test set.
 *
 *			3) How to capture manual test cases along side your automated
 *			tests and the APIs that support manual execution.
 */
SAMPLE.FeaturesTestSet = Class.extend(SAMPLE.BaseSampleTestSet, {
	
	
	title: "FeaturesTestSet",
	
	initialize: function()
	{
		this.parent();
	},
	
	/**
	 * Navigate to the features section of the applicaiton
	 */
	setUp: function()
	{
		this.performActions([["selectFeaturesButton"]]);
	},
	
	/**
	 * Return to the home screen
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
	},
	
	testManuallySomethingThatCurrentlyPasses : function()
	{
		/*
			This test is an example of a test that has not been automated yet. It has been executed
			and been found to pass. Normally in this scenaio, the tester will write the test in 
			comments like...
			
				"Setup:
					-select the features icon from the home page
				Steps:
					-Validate that the details talk about the ease of setting up IMAT
					-Scroll right 
					-Validate that the details talk about the patterns you can follow
					-Scroll right
					-Validate that the details talk about the time you will save when you use imat.
				tear down:
					-go back to the home screen."
				
			The person who executed the test can then mark the test as passing like follows. Note 
			that the date string is optional. In this case, it is used to give the date that the test
			was last executed.
		 */
		manualPass("7/20/2011");
	},
	
	testManuallySomethingThatCurrentlyFails : function()
	{
		/*
			This test is an example of a test that has not been automated yet. It has been executed
			and been found to fail. Normally in this scenaio, the tester will write the test in 
			comments like...
			
				"Setup:
					-select the features icon from the home page
				Steps:
					-tap on the dotted area above the image three times
					-validate that it cycles back to the original image.
				tear down:
					-go back to the home screen."
				
			The person who executed the test can then mark the test as failing like follows. Note 
			that the date string is optional. In this case, it is used to give the date that the test
			was last executed.
		 */
		manualFail("7/20/2011");
	}
});

//After a test is defined, add an instance of it to the global suiteRunner object.
IMAT.suiteRunner.addTestSet( new SAMPLE.FeaturesTestSet());