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

SAMPLE.FeaturesTestSet = Class.extend(IMAT.BaseFunctionalTestSet, {
	
	//All tests
	title: "FeaturesTestSet",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		IMAT.log_debug("Initializing the Features Tests.");
		IMAT.log_debug("Note: This is where you could declare your test fixtures.");
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to make sure that the next test runs in the right state.
	 */
	setUp: function()
	{
		//Do nothing
		IMAT.log_debug("setUp was called");
		//This is where you would invoke actions to setup the right viewContext before each test in
		//this test set
	},
	
	/**
	 * tearDown the App to normalize the way tests finish. Reset anything that might cause the next
	 * test to fail.
	 */
	tearDown: function()
	{
		//Do nothing
		IMAT.log_debug("tearDown was called");
		//This is where you would invoke actions to reset the viewContext after each test in this
		//test Set
	},
	
	setUpTestSet: function()
	{
		IMAT.log_debug("setUpTestSet was called");
		//This is where you would invoke actions to setup the right viewContext before any of the 
		//tests in this test set are run.
		
		//A good thing to do here would be to initialize this.viewContext
		this.viewContext = new SAMPLE.StarterView();
	},
	
	tearDownTestSet: function()
	{
		IMAT.log_debug("tearDownTestSet was called");		
		//This is where you would invoke actions to setup the right viewContext after all of the 
		//tests in this test set are run.
	},
	
	doCleanup: function()
	{
		//Do nothing
		IMAT.log_debug("doCleanup was called");	
	},
	
	/**
	 * Return the base view which we consider to be the "Base State" when recovering from a test
	 * failure. This implementation does nothing and returns undefined. You must override this
	 * in order to get test recovery working correctly. 
	 *
	 * @return IMAT.LoginView which is considered the base view for all functional tests
	 */
	getBaseView: function() {
		return new SAMPLE.StarterView();
	},
	
	/**
	 * Test all of the macros so that we have a clear definition of what they will allow.
	 */
	
	testZoomInAndOutInFeatures : function()
	{
		this.performActions([
			["selectFeaturesButton"],
			["zoom"],
			["zoom"],
			["returnToHomeScreen"],
		]);
	},	
	
	testViewDetailsInFeatures : function()
	{
		this.performActions([
			["selectFeaturesButton"],
			["viewAndCloseDetails"],
			["returnToHomeScreen"],
		]);
	},
	
	testScrollingInFeatures : function()
	{
		this.performActions([
			["selectFeaturesButton"],
			["swipeRight"],
			["validateImageSwitched", "lego.jpg", "pattern.jpg"],
			["swipeRight"],
			["validateImageSwitched", "pattern.jpg", "time.jpg"],
			["swipeLeft"],
			["validateImageSwitched", "time.jpg", "pattern.jpg"],
			["swipeLeft"],
			["validateImageSwitched", "pattern.jpg", "lego.jpg"],
			["returnToHomeScreen"],
		]);
	},
	
	testManuallyFeaturesTestThatAlwaysPasses : function()
	{
		/*
			always passes
		*/
		manualPass("7/20/2011");
	},
	
	testManuallyFeaturesTestThatAlwaysFails : function()
	{
		/*
			always fails
		*/
		manualFail("7/20/2011");
	},
	
});

//After a test is defined, add an instance of it to the global suiteRunner object.
IMAT.suiteRunner.addTestSet( new SAMPLE.FeaturesTestSet());