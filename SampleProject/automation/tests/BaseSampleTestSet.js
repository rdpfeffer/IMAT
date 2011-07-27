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

SAMPLE.BaseSampleTestSet = Class.extend(IMAT.BaseFunctionalTestSet, {
	
	title: "BaseSampleTestSet",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		this.parent();
	},
	
	setUpTestSet: function()
	{
		//initialize the view context before any tests in this test set run.
		this.viewContext = new SAMPLE.HomeScreenView();
	},
	
	/**
	 * Return the base view which we consider to be the "Base State" when recovering from a test
	 * failure.
	 *
	 * @return SAMPLE.HomeScreenView which is considered the base view for all functional tests
	 */
	getBaseView: function() {
		return new SAMPLE.HomeScreenView();
	},
	
	getBaseViewName: function() {
		return "HomeScreenView";
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
IMAT.suiteRunner.addTestSet( new SAMPLE.BaseSampleTestSet());