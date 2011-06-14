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
 * Constructs a {@link IMAT.BaseTestSet}. 
 * 
 * @class The {@link IMAT.BaseTestSet} is the most basic of all tests and 
 * does nothing more than provide stubbed out methods that the 
 * {@link IMAT.TestRunner} expects and initialize some basic instance 
 * variables related to its title etc. 
 */
IMAT.BaseTestSet = Class.extend(/** @lends IMAT.BaseTestSet.prototype */{
	
	/**
	 * The Title this test goes by. Used to identify this test in the logs. 
	 * 
	 * @type String 
	 */
	title: "Base Test Class",
	
	/**
	 * A field that tells the TestRunner object to capture the state of the AUT
	 * when a test fails. When truthy, the {@link IMAT.TestRunner} class will 
	 * call <code>IMAT.log_state()</code> after a test fails. When falsy, the 
	 * {@link IMAT.TestRunner} will not capture any state.
	 * 
	 * @type boolean
	 */
	logOnFailure: true,
	
	//The constructor called when invoking "new" on this class object.
	//See the class declaration above for more information.
	initialize: function()
	{
		//do nothing
	},
	
	/**
	 * The Base setUp function for the Test Harness. setUp() is run before every 
	 * test case in a test set.
	 * 
	 * @returns {undefined} 
	 * 					The framework expects no values to be returned 
	 * 					from this function.
	 */
	setUp: function() 
	{
		//do nothing
	},
	
	/**
	 * The Base tearDown function for the Test Harness. tearDown() is run after 
	 * every test case in a test set.
	 * 
	 * @returns {undefined} 
	 * 					The framework expects no values to be returned 
	 * 					from this function.
	 */
	tearDown: function()
	{
		//do nothing.
	},
	
	/**
	 * This is run before every test set. Note that it is a higher level setUp 
	 * function than the plain setUp function because it sets up a whole set of 
	 * test cases at once.
	 * 
	 * @returns {undefined} 
	 * 					The framework expects no values to be returned 
	 * 					from this function.
	 */
	setUpTestSet: function()
	{
		//do nothing.
	},
	
	/**
	 * This is run after every test set. Note that it is a higher level tearDown 
	 * function than the plain tearDown function because it tears down a whole 
	 * set of test cases at once.
	 *
	 * @returns {undefined} 
	 * 					The framework expects no values to be returned 
	 * 					from this function.
	 */
	tearDownTestSet: function()
	{
		//do nothing.
	},
	
	/**
	 * Do remaining cleanup work to clean up after test failures.
	 * 
	 * @returns {undefined} 
	 * 					The framework expects no values to be returned 
	 * 					from this function.
	 */
	doCleanup: function()
	{
		//do nothing.
	}
});