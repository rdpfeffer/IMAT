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
IMAT.MockView = Class.extend(IMAT.BaseView, {
	
	title: "MockView",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function() {
		this.parent();
		this.viewName = "MockView";
	},
	
	validateHappyCase: function() {
		this.validateState("happy case", false, null, function(){
			assertTrue(true);
		});
	},
	
	validateWithForcedAssertionException: function() {
		this.validateState("throwing an AssertionException", false, null, function(){
			throw new IMAT.AssertionException("Forced Assertion Exception");
		});
	},
	
	validateWithForcedException: function() {
		var foo = {};
		this.validateState("throwing an Exception", false, null, function(){
			foo.jones(); //this function does not exist
		});
	},
	
	validateTransientStateWithForcedAssertionException: function() {
		this.validateState("throwing an AssertionException", true, null, function(){
			throw new IMAT.AssertionException("Forced Assertion Exception");
		});
	},
	
	validateTransientStateWithForcedException: function() {
		var foo = {};
		this.validateState("throwing an Exception w/ transient state", true, null, function(){
			foo.jones(); //this function does not exist
		});
	},
	
	validateAnonymousFuncRequiresNoArgs: function() {
		this.validateState("anonymous func reqires no args", true, null, function(){
			//do nothing
			assertTrue(arguments.length === 1);
		});
	},
	
	validateAnonymousFuncTakesOptionalArgsObj: function() {
		this.validateState("anonymous func takes optional args", true, null, function(view, args){
			assertEquals(args.ima, "baller");
		}, {ima:"baller"});
	}
});