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
 * This is the Base parent object for all test sets in the IMAT sample 
 * automation project. All test sets in this directory inherit from it using the  
 * the inheritance.js framework packaged within IMAT core. It serves a few 
 * demonstrative purposes...
 *
 *			1) It shows that test sets, like any other objects in your 
 *			automation project, can be extended. By making use of powerful   
 *			language features like inheritance, your project can abstract away
 *			low level details and improve test maintenance concerns. With IMAT
 *			it's much easier to stay DRY.
 *
 *			2) It shows what functions you must implement if you would like to
 *			make use of the cleanup functionality provided by 
 *			IMAT.BaseFunctionalTestSet
 */
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
	 * Return the name of the base view which we consider to be the "Base State" when recovering 
	 * from a test failure.
	 *
	 * NOTE: This function will be called by IMAT.BaseFunctionalTestSet#doCleanup() after it has 
	 * made an attempt to identify the current view. It will then try to escape from that view by
	 * calling escapeAction() on the view, each time comparing the resulting view's viewName 
	 * property to the value returned by this function. This will continue until the condition is 
	 * met or an exception is thrown. This is the first of two functions you must implement in your 
	 * test sets to make the cleanup process work.
	 *
	 * @return string - Matching the viewName property of the view which is the base view we should
	 * go back to before each test.
	 */
	getBaseStateViewName: function() {
		return "HomeScreenView";
	},
	
	/**
	 * Return the base view which we consider to be the "Base State" when recovering from a test
	 * failure.
	 *
	 * NOTE: This function will be called by IMAT.BaseFunctionalTestSet#doCleanup() after it has 
	 * made an attempt to identify the current view, and escape from that view to a known state.
	 * It will then set that known state by calling this function. This is the second of two 
	 * functions you must implement in your test sets to make the cleanup process work.
	 *
	 * @return SAMPLE.HomeScreenView which is considered the base view for all functional tests
	 */
	getBaseView: function() {
		return new SAMPLE.HomeScreenView();
	}
});