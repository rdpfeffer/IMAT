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
 
/**
 * The Base view is responsible for a few things...
 *		1) It maintains state of the current view context.
 *		2) It holds references to the global application and target objects defined by UIAutomation
 *		   so that all of our subviews are not coupled to global objects or static getters.
 * 		3) Provides base functionality for all subviews to validate their own state.
 *		4) Dismisses input widgets
 *		5) Handles Alerts: (TODO: Remove. This should really be handled from the test object)
 *		
 *
 * All views that automate parts of the app should derive from this class.
 */
GINSU.BaseView = Class.create({
	
	/**
	 * Initialize the base view
	 */
	initialize: function()
	{
		this.viewName = "";
		
		// Now these global objects are encapsulated as objects of the base class.
		this.target = null;
		this.app = null;
		this.mainWin = null;
		this.refreshAppContext();
		GINSU.log_trace("Base View initialized: " + this.target + " " + this.app + " " + this.mainWin);
		
	},
	
	/**
	 * Convenience method for instances in which the view needs a refreshed view of the application
	 * context. This will refresh the target, app and mainWin variables to the current context of
	 * the application.
	 */
	refreshAppContext: function()
	{
		this.target = UIATarget.localTarget();
		this.app = this.target.frontMostApp();
		this.mainWin = this.app.mainWindow();
	},
	
	/**
	 * Handles State validation logging and screen capturing leveraging a given validator function.
	 * 
	 * @param stateName string - The state you want to validate
	 * @param isTransientState boolean - true if the state you are validating is transient due to 
	 * 	asynchronous changes in the app (eg. due to network callbacks). If you validation throws an 
	 * 	exception when this true, the exception will be ignored and the automation will be allowed 
	 *	to move on. This can happen if the asynchronous change causes the app to transition out of 
	 *  that state before the validation can finish.
	 * @param validatorFunc function - The callback function defining the valid state. This function 
	 * 	should expect a view as the first parameter of the function and, optionally, an args object
	 *	which, if defined will be passed to validatorFunc as the second parameter.
	 * @param args object, Optional - if defined, they will be passed to the validator function for
	 *	dynamic state validation.
	 *
	 * @throws An exception if the validation fails.
	 */
	validateState: function(stateName, isTransientState, view, validatorFunc, args)
	{
		var validationLogMessage = "VALIDATE " + this.viewName + ": " + stateName + " state.";
		GINSU.log_debug(validationLogMessage);
		this.target.captureScreenWithName("SCREEN: " + validationLogMessage + " " + new Date());
		try
		{
			if(typeof args == "undefined")
			{
				validatorFunc(view);
			}
			else
			{
				validatorFunc(view, args);
			}
		}
		catch (e)
		{
			if(isTransientState)
			{
				GINSU.log_debug("Transient State Validation Failed, most likely due to an " +
					"asynchronous state transition in the app. Exception is being ignored. " + e);
			}
			else
			{
				throw "Validation Error" + e;
			}
		}
		GINSU.log_trace("Validation on " + this.viewName + ": " + stateName + " state passed." );
	},
	
	/**
	 * Waits for activity indicators to finish before letting the automation move on. This function
	 * assumes that the intended activity indicator to wait on is the first one.
	 *
	 * @param elem UIAElement - the element containing the UIAActivityIndicator element to be waited 
	 *	on. If no visible UIAElements are found, the automation will move on.
	 * @param timeout number - the length of time to wait for activity before throwing an exception.
	 * @param waitForInvalid boolean - optional. Set to true if you are waiting for an element to 
	 *  become invalid
	 *
	 * @throws an exception if elem is not a valid element or if the timeout defined is not a 
	 * 	number.
	 */
	waitForActivity: function(elem, timeout)
	{
		var activityIndicator;
		if(elem.checkIsValid() && typeof timeout == "number")
		{
			//find the first visible activity indicator
			GINSU.log_trace("Number of Activity indicators: " + elem.activityIndicators().length);
			for (var i = 0; i < elem.activityIndicators().length; i++)
			{
				GINSU.log_trace("checking activity indicator with name: " + elem.activityIndicators()[i].name());
				if(elem.activityIndicators()[i].checkIsValid() && elem.activityIndicators()[i].isVisible())
				{
					GINSU.log_trace("found activity indicator");
					activityIndicator = elem.activityIndicators()[i];
				}
				else
				{
					GINSU.log_trace("Activity indicator is valid: " + elem.activityIndicators()[i].checkIsValid());
					GINSU.log_trace("Activity indicator is visible: " + elem.activityIndicators()[i].isVisible());
				}
			}
			if (activityIndicator)
			{
				this.target.pushTimeout(timeout);
				activityIndicator.waitForInvisible();
				this.target.popTimeout();
			}
			else
			{
				GINSU.log_debug("No Visible activity indicators were found. Automation will continue");
				this.target.logElementTree();	
			}
		}
		else
		{
			throw "Exception while waiting for activity | elem is valid: " + 
				(elem.checkIsValid() ? "true":"false") + ", type of timeout: " + typeof timeout +
				". Elem should be valid and the timeout should be a number";
		}
	},
	
	/**
	 * Dismisses the keyboard from view.
	 *
	 * @param buttonName string - the name of the button used to dismiss the keyboard from view.
	 * @param timeout number, Optional - the length of time to wait for activity before throwing an 
	 *	exception.
	 *
	 * TODO: Think about moving this somewhere else. Could it be an extension of the UIAWindow 
	 * object?
	 */
	dismissKeyboard: function(buttonName, timeout)
	{
		this.refreshAppContext(); //TODO: Remove this if it has no effect on our tests succeeding.
		try
		{
			if (typeof timeout == "undefined")
			{
				timeout = .5;
			}
			this.target.pushTimeout(timeout);
			this.app.keyboard().buttons().firstWithName(buttonName).tap();
			this.target.popTimeout();
		}
		catch (e)
		{
			GINSU.log_debug("Could not dismiss Keyboard.");
			this.target.logElementTree();
		}
	},
	
	/**
	 * For when a given test fails, this is is part of a larger iteration of escapeAction calls
	 * which enable our tests to keep running even if one fails
	 */
	escapeAction: function()
	{
		//do nothing.
	},
	
});