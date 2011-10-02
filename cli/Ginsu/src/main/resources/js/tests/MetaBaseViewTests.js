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

//Core test framework
/**
 * This class Tests the BaseView class to ensure that its functions work as 
 * expected
 * @ignore 
 */
IMAT.MetaBaseViewTests = Class.extend(IMAT.BaseTestSet, {
	
	title: "MetaBaseViewTests",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function() {
		this.baseView = new IMAT.MockView();
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to clear out the old set of test data and set the right target server environment
	 */
	setUp: function() {
		//Do nothing
	},
	
	/**
	 * tearDown the App to normalize the way tests finish. Clear the cache if possible.
	 */
	tearDown: function() {
		//Do nothing
	},
	
	setUpTestSet: function() {
		//Do nothing.
	},
	
	tearDownTestSet: function()	{
		//Do nothing
	},
	
	doCleanup: function() {
		//Do nothing
	},
	
	testThatValidateStateReturnsNormallyForPassingAssertions: function() {
		try {
			this.baseView.validateHappyCase();
		} catch (e) {
			assertTrue(false, "Validating the happy case failed. with exception" +
				e.toString());
		}
	},
	
	testThatValidateStateThrowsAnAssertionExceptionForFailedAssertions: function() {
		try {
			this.baseView.validateWithForcedAssertionException();
			assertTrue(false, "we expected a forced IMAT.AsserrtionException");
		} catch (e) {
			assertTrue(e instanceof IMAT.AssertionException, 
				"Validate State did not throw an assertion exception for "+
				"failed validation. Instead we got: " + e.toString());
		}
	},
	
	testThatValidateStateThrowsAnExceptionForGeneralExceptions: function() {
		try {
			this.baseView.validateWithForcedException();
			assertTrue(false, "we expected a forced exception");
		} catch (e) {
			assertFalse(e instanceof IMAT.AssertionException, 
				"Validate State threw an assertion exception for a general "+
				"exception");
		}
	},
	
	testThatValidateStateDoesNotThowExceptionsForTransientStates: function() {
		try {
			this.baseView.validateTransientStateWithForcedAssertionException();
		} catch (e) {
			assertTrue(false, "transient states should only show warnings "+
				"and never pass on assertion exceptions");
		}
		
		try {
			this.baseView.validateTransientStateWithForcedException();
		} catch (e2) {
			assertTrue(false, "transient states should only show warnings "+
				"and never pass on exceptions");
		}
	},
	
	testThatValidateStateDoesNotRequireDynamicArgsInValidatorFunction: function() {
		this.baseView.validateAnonymousFuncRequiresNoArgs();
	},
	
	testThatValidateStateAcceptsOptionalArgsObjInValidatorFunction: function() {
		this.baseView.validateAnonymousFuncTakesOptionalArgsObj();
	}
});

IMAT.suiteRunner.addTestSet( new IMAT.MetaBaseViewTests());