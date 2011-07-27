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

SAMPLE.HomeScreenTestSet = Class.extend(SAMPLE.BaseSampleTestSet, {
	
	
	title: "HomeScreenTestSet",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		this.parent();
		IMAT.log_debug("Initializing the Home Screen tests.");
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to make sure that the next test runs in the right state.
	 */
	setUp: function()
	{
		//override the BaseSampleTestSet to do nothing instead.
	},
	
	/**
	 * tearDown the App to normalize the way tests finish. Reset anything that might cause the next
	 * test to fail.
	 */
	tearDown: function()
	{
		this.performActions([["swipeLeft"]]);
	},
	
	testHomeScreenSwipeBetweenIconSets : function()
	{
		this.performActions([
			["swipeRight"],
			["swipeLeft"]
		]);
	},
	
	testAboutAlertAndSaveMessage : function(alert)
	{
		var testString = "this is a test message";
		//Save a reference to the existing alert mechanism
		var defaultAlert = UIATarget.onAlert;
		
		//Override the alert handler to respond to the alert that will show when we
		//interact with the about button.
		UIATarget.onAlert = function onAlert(alert) {
			assertValid(alert.elements().firstWithName("Test Dialog"));
			alert.textFields()[0].setValue(testString);
			alert.buttons()["Save"].tap();
		};
		
		this.performActions([
			["selectAboutButton"],
			["wait", 2] //wait for the alert to be handled.
		]);	
		
		//set the alert handler again, this time to validate that the test we entered
		//earlier is still there when we see the alert again.
		UIATarget.onAlert = function onAlert(alert) {
			var text = alert.textFields()[0].value();
			assertEquals(text, testString);
			alert.buttons()["Cancel"].tap();
		};
		
		this.performActions([
			["selectAboutButton"],
			["wait", 2] //wait for the alert to be handled.
		]);
		
		//revert the alert handler back to its original state.
		UIATarget.onAlert = defaultAlert;
	}
});

//After a test is defined, add an instance of it to the global suiteRunner object.
IMAT.suiteRunner.addTestSet( new SAMPLE.HomeScreenTestSet());