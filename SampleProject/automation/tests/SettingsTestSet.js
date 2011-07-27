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

SAMPLE.SettingsTestSet = Class.extend(SAMPLE.BaseSampleTestSet, {
	
	
	title: "SettingsTestSet",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		this.parent();
		IMAT.log_debug("Initializing the Settings Tests.");
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to make sure that the next test runs in the right state.
	 */
	setUp: function()
	{
		this.performActions([
			["swipeRight"],
			["selectSettingsButton"]
		]);
	},
	
	/**
	 * tearDown the App to normalize the way tests finish. Reset anything that might cause the next
	 * test to fail.
	 */
	tearDown: function()
	{
		this.performActions([
			["returnToHomeScreen"],
			["swipeLeft"]
		]);
	},
	
	testModifyFieldsInSettings : function()
	{
		var singleLineText = "single line comment";
		var multiLineText = "multiline comments are much longer than single line comments so this will most likely take up more than one line";
		this.performActions([
			["enterSingleLineComment", singleLineText],
			["enterMultiLineComment", multiLineText],
			["toggleSwitch", "on"],
			["adjustSliderToValue", 1],
			["validateAllFields", singleLineText, multiLineText, 1, "100%"], 
			["toggleSwitch", "off"],
			["adjustSliderToValue", 0]
		]);
	}
});

//After a test is defined, add an instance of it to the global suiteRunner object.
IMAT.suiteRunner.addTestSet( new SAMPLE.SettingsTestSet());