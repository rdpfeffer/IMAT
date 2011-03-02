/*******************************************************************************
* Copyright (c) 2009 Intuit, Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.opensource.org/licenses/eclipse-1.0.php
* 
* Contributors:
*     Intuit, Inc - initial API and implementation
*******************************************************************************/
GINSU.BaseTest = Class.extend({
	
	title: "Base Test Class",
	logOnFailure: true,
	
	initialize: function()
	{
		//do nothing
	},
	
	/**
	 * The Base setUp function for the Test Harness. setUp() is run before every test case in a test
	 * set
	 */
	setUp: function() 
	{
		//do nothing
	},
	
	/**
	 * The Base tearDown function for the Test Harness. tearDown() is run after every test case in
	 * a test set.
	 */
	tearDown: function()
	{
		//do nothing.
	},
	
	/**
	 * This is run before every test set. Note that it is a higher level setUp function than the 
	 * plain setUp function because it sets up a whole set of test cases at once.
	 */
	setUpTestSet: function()
	{
		//do nothing.
	},
	
	/**
	 * This is run after every test set. Note that it is a higher level tearDown function than the 
	 * plain tearDown function because it tears down a whole set of test cases at once.
	 */
	tearDownTestSet: function()
	{
		//do nothing.
	},
	
	/**
	 * Do remaining cleanup work to clean up after test failures
	 */
	doCleanup: function()
	{
		//do nothing.
	}
});