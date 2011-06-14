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
 * 	Constructs a BaseFunctionalTestSet object
 *	@class The {@link IMAT.BaseFunctionalTestSet} object is common test set 
 * 	object to be extended by all tests doing testing at the UI level. 
 * 	<p>Since this
 *  framework is primarily intended to be a UI testing framework, we assume that
 *  most test sets will extend this class. However, if those assumptions prove
 *  incorrect and you find yourself needing something entirely different, you 
 *  are free to omit this as a parent class to your Test Sets and implement 
 *  something that makes sense for  you.</p>
 *  <p>On the other hand, if you are doing UI testing, it is strongly recommended
 *  that you take advantage of the implementations this class provides.</p> 
 *  <ul>
 *		<li>Alert management at the test level so that you can test for expected
 * 		alerts and fail out when certain types of unexpected alerts occur.</li>
 *		<li>Clean-up hooks to reset your application to a know good state after
 * 		an exception or a failure has occured.</li>
 *  	<li>The Perform actions function. This is the ""miracle method" that 
 * 		allows our tests to be readable as well as easy to triage.</li>
 *  </ul>
 * @memberOf IMAT 
 * @augments IMAT.BaseTestSet 
 */
IMAT.BaseFunctionalTestSet = Class.extend(IMAT.BaseTestSet, /** @lends IMAT.BaseFunctionalTestSet# */{
	
	/**
	 * The constructor called when invoking "new" on this class object.
	 * @ignore 
	 */
	initialize: function() {
		IMAT.log_debug("Initializing the Base Functional Tests");
		this.viewContext = undefined;
	},
	
	
	
	/**
	 * The alert manager used for testing scenarios when alerts are expected and 
	 * the user is suposed to take some sort of action.
	 *
	 * @example
	 * //...within some Test Set...
	 * testIncorrectPasswordOnLogin: function() {
	 * 	this.alertManager.handleAlertWithTitleAndButtonClick("Incorrect Password", "Reset");
	 *	this.performActions([
	 * 		["login", "testUser", "badPassword"],
	 * 		["wait", 10], //wait for the alert to pop up and be handled.
	 * 		["validateResetPassword"]
	 * 	]); 
	 * 	this.alertManager.resetAlertHandler();
	 * },
	 */
	alertManager : /** @lends IMAT.BaseFunctionalTestSet.alertManager */{
		
		defaultAlertHandler : UIATarget.onAlert,
	
		/**
		 * Sets the alert handler to handle only alerts with the given title. 
		 * The alert handler will return true if the title matches the alert 
		 * triggered, however calling this function explicitly will not return 
		 * anything.
		 * 
		 * @param {String} title
		 *  					The title of the UIAAlert to handler
		 * @throws {String} when the value of alert.name() does not equal title. 
		 */
		handleDefaultAlertWithTitle : function(title) {
			UIATarget.onAlert = function onAlert(alert) {
				
				IMAT.log_debug("Handling alert named: '" + title + "'");
				
				// if the name field exists then check that
				var alertTitle = null;
				if(alert.name()) {
					alertTitle = alert.name();
				// if not then look for a text field with the title we are looking for
				} else {
					alertTitle = alert.elements().firstWithName(title).name();
				}
				
				assertEquals(alertTitle, title);
				return true;
			};
		},
	
		/**
		 * 
		 */
		handleAlertWithTitleAndButtonClick : function(title, buttonName) {
			// need an internal reference to pass to the callback method ('this' changes context)
			var ref = this;
			UIATarget.onAlert = function onAlert(alert) {
				ref.handleDefaultAlertWithTitle(title);
				
				IMAT.log_debug("Tapping Alert Button: '" + buttonName + "'");
				// tap tap taparoo that button with the given name
				alert.buttons()[buttonName].tap();
				return true;
			};
		},
		
		/**
		 * Set the handler back to the default one after we are done.
		 */
		resetAlertHandler : function() {
			UIATarget.onAlert = this.defaultAlertHandler;
		},
		
		/**
		 * Set the handler back to the default one after we are done. Pass the 
		 * contextView
		 */
		resetAlertHandler : function(viewContext) {
			UIATarget.onAlert = this.defaultAlertHandler;
			//wait for the next view coming up after the alert is handled.
			viewContext.target.delay(10);
		}
	},
	
	/**
	 * @ignore
	 */
	setUp: function() {
		
	},
	
	/**
	 * @ignore
	 */
	setUpTestSet: function() {
		
	},
	
	/**
	 * @ignore
	 */
	tearDownTestSet: function() {
		
	},
	
	/**
	 * @ignore
	 */
	tearDown: function() {
		
	},
	
	/**
	 * Do any cleanup needed to make sure that subsequent tests run in a known 
	 * good state. This function iteratively calls escapeAction on the current 
	 * view context until the returned context is not set.
	 *
	 * If the escapeAction ever throws an exception, we attempt to discover what 
	 * view we are on and try the escape process again. This will happen up to 5 
	 * times before we give up.
	 *
	 *  @param {number} attemptCount 
	 * 					The number of attempt counts we have left.
	 */
	doCleanup: function(attemptCount) { 
		var stemView;
		//initialize the attempt
		if (typeof attemptCount == "undefined")
		{
			attemptCount = 0;
		}
		//attempt the whole escape process up to 5 times
		if (attemptCount < 5)  {
			try {
				do {
					IMAT.log_debug("Escaping from view: " + this.viewContext.viewName);
					if (this.viewContext.viewName != this.getBaseStateViewName()) {
						this.viewContext = this.viewContext.escapeAction();
					}
				}
				while(this.viewContext && this.viewContext.viewName != this.getBaseStateViewName());
				
				//we should now be at the login view. If we are not, trying to instantiate one here
				// will throw an exception and cause us to try again.
				this.viewContext = this.getBaseView();
			} catch (e) {
				IMAT.log_debug("Escape failed when attempt count was " + attemptCount);
				stemView = new IMAT.StemView();
				this.viewContext = stemView.getSpecializedView();
				//make a recursive call to doCleanup and increment the attemptCount
				this.doCleanup(++attemptCount);
			}
		} else {
			IMAT.log_error("Automation failed cleanup more than 5 times in a row.");
		}
	},
	
	/**
	 * Get the name of the view which we consider to be the "Base State" when 
	 * recovering from a test failure.This implementation does nothing and 
	 * returns undefined. You must override this in order to get test recovery 
	 * working correctly. 
	 *
	 * @return String The name of our base view for all functional tests.
	 */
	getBaseStateViewName: function() {
		return	undefined;
	},
	
	/**
	 * Return the base view which we consider to be the "Base State" when 
	 * recovering from a test failure. This implementation does nothing and 
	 * returns undefined. You must override this in order to get test recovery 
	 * working correctly. 
	 *
	 * @return {IMAT.BaseView} The View we are trying to get to.
	 */
	getBaseView: function() {
		return undefined;
	},
	
	/**
	 * Performs a set of actions defined by the two dimensional actions array 
	 * passed in.
	 * <p>When perform actions is called, it automatically updates the view
	 * context to the resulting view of each action. Furthermore it logs action
	 * information to aid in triage when tests fail.</p> 
	 * <p>Notice the example below. Each action array starts with the name of 
	 * the action, and is followed by the parameters to that action. This is 
	 * also a very easy format to read what the test does and understand it
	 * without much prior knowledge of JavaScript or the framework within.</p>
	 *  
	 * @example
	 * //...within some test set....
	 * testSearchEnterTermAndClear: function() {
	 * 	this.performActions([
	 * 		["search", "Carrots"],
	 *		["validateItemPresent", "Carrots"],
	 *		["clearSerachTerm"]
	 * 	]);
	 * },
	 *
	 *  @param {Array} actions
	 * 					Where each element in the actions array is another 
	 * 					{@link Array}. The first element in the sub-array should 
	 *  				be a string representing an action method of the current
	 *  				viewContext (<code>this.viewContext</code>) of the test. 
	 * 					The rest of the elements in the array are interpreted as
	 * 					parameters to that method.
	 */
	performActions: function(actions) {
		var typeCheckMessage = "Perform actions must take a parameter of a 2 dimensional array.";
		assertTrue((actions instanceof Array), typeCheckMessage);
		var i;
		for (i = 0; i < actions.length; i++) {
			assertTrue((actions[i] instanceof Array), typeCheckMessage);
			assertTrue(actions[i].length > 0, "All actions require at least one string representing the action.");
			var action = actions[i][0] + "Action";
			var logMessage = "ACTION: " + actions[i][0] + "("; 
			var j, 
			var args = [];
			for (j = 1; j < actions[i].length; j++)
			{
				args.push(actions[i][j]);
				logMessage = logMessage + " " + actions[i][j];
			}
			logMessage = logMessage + ")";
			IMAT.log_info(logMessage);
			var actionFunction = this.viewContext[action];
			assertTrue((typeof actionFunction == "function"),
				action + " is not an action of " + this.viewContext.viewName +
				" instead it evaluates to: " + actionFunction);
			this.viewContext = actionFunction.apply(this.viewContext, args);
		}
	}
});