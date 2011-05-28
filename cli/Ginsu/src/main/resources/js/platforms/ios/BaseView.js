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
 * @class The Base view is responsible for a few things...
 * <ol> 
 *		<li>It validates the state of the current view context while providing
 * 		a common logging patterns and screen capture.</li>
 *		<li>It validates the state of the current view context.</li> 
 *		<li>It holds references to the global application and target objects 
 * 		defined by Apple's UIAutomation framework.</li>
 * 		<li>Provides base functionality for all views to get access to elements
 * 		on the screen.</li>
 *		<li>Does common things that all other views miight need to do like 
 * 		Dismiss the keyboard or wait for some sort of network activity to 
 * 		complete.</li>
 * </ol>	
 *
 * All views that automate parts of the app should derive from this class.
 */
IMAT.BaseView = Class.create(/** @lends IMAT.BaseView# */{
	
	/**
	 * The constructor called when invoking "new" on this class object.
	 * @ignore 
	 */
	initialize: function()
	{
		this.viewName = "";
		
		// Now these global objects are encapsulated as objects of the base class.
		this.target = null;
		this.app = null;
		this.mainWin = null;
		this.refreshAppContext();
		IMAT.log_trace("Base View initialized: " + this.target + " " + this.app + " " + this.mainWin);
		
	},
	
	/**
	 * Convenience method for instances in which the view needs a refreshed view 
	 * of the application context. This will refresh the target, app and mainWin 
	 * variables to the current context of the application.
	 */
	refreshAppContext: function()
	{
		this.target = UIATarget.localTarget();
		this.app = this.target.frontMostApp();
		this.mainWin = this.app.mainWindow();
	},
	
	/**
	 * Handles State validation logging and screen capturing leveraging a given 
	 * validator function.
	 * 
	 * @param {string} stateName 
	 * 					The state you want to validate
	 * @param {boolean} isTransientState
	 * 					true if the state you are validating is transient due to 
	 * 					asynchronous changes in the app (eg. due to network 
	 * 					callbacks). If the validation throws an exception when 
	 * 					this true, the exception will be ignored and the 
	 * 					automation will be allowed to move on. This can happen 
	 * 					if the asynchronous change causes the app to transition 
	 * 					out of that state before the validation can finish.
	 * @param {IMAT.BaseView} view
	 *  				The target view to validate. This will become the first
	 *					parameter passed to the inner validation function passed
	 *  				to this method (validatorFunc).
	 * @param {function} validatorFunc 
	 * 					The callback function defining the valid state. This 
	 * 					function should expect a view as the first parameter of 
	 * 					the function and, optionally, an args object which, if 
	 * 					defined will be passed to validatorFunc as the second 
	 * 					parameter.
	 * @param {object} args 
	 * 					Optional. If defined, they will be passed to the 
	 * 					validator function for dynamic state validation.
	 *
	 * @throws An exception of type {@type String} if the validation fails.
	 */
	validateState: function(stateName, isTransientState, view, validatorFunc, args)
	{
		var validationLogMessage = "VALIDATE " + this.viewName + ": " + stateName + " state.";
		IMAT.log_debug(validationLogMessage);
		this.target.captureScreenWithName("SCREEN: " + validationLogMessage + " " + new Date());
		try {
			if(typeof args == "undefined") {
				validatorFunc(view);
			} else {
				validatorFunc(view, args);
			}
		} catch (e) {
			if(isTransientState) {
				IMAT.log_debug("Transient State Validation Failed, most likely due to an " +
					"asynchronous state transition in the app. Exception is being ignored. " + e);
			} else {
				throw "Validation Error: " + e;
			}
		}
		IMAT.log_trace("Validation on " + this.viewName + ": " + stateName + " state passed." );
	},
	
	/**
	 * Waits for activity indicators to finish before letting the automation 
	 * move on. This function assumes that the intended activity indicator to 
	 * wait on is the first one found below the parameter <code>elem</code>.
	 *
	 * @param {UIAElement} elem
	 * 						The element containing the UIAActivityIndicator 
	 * 						element to be waited on. If no visible UIAElements 
	 * 						are found, the automation will move on.
	 * @param {number} timeout
	 * 						The length of time to wait for activity before 
	 * 						throwing an exception.
	 *
	 * @throws an exception if elem is not a valid element or if the timeout 
	 * defined is not a number.
	 */
	waitForActivity: function(elem, timeout)
	{
		var activityIndicator;
		if(elem.checkIsValid() && typeof timeout == "number") {
			//find the first visible activity indicator
			IMAT.log_trace("Number of Activity indicators: " + elem.activityIndicators().length);
			for (var i = 0; i < elem.activityIndicators().length; i++) {
				IMAT.log_trace("checking activity indicator with name: " + elem.activityIndicators()[i].name());
				if(elem.activityIndicators()[i].checkIsValid() && elem.activityIndicators()[i].isVisible()) {
					IMAT.log_trace("found activity indicator");
					activityIndicator = elem.activityIndicators()[i];
				} else {
					IMAT.log_trace("Activity indicator is valid: " + elem.activityIndicators()[i].checkIsValid());
					IMAT.log_trace("Activity indicator is visible: " + elem.activityIndicators()[i].isVisible());
				}
			}
			if (activityIndicator) {
				this.target.pushTimeout(timeout);
				activityIndicator.waitForInvisible();
				this.target.popTimeout();
			} else {
				IMAT.log_debug("No Visible activity indicators were found. Automation will continue");
				IMAT.log_state();	
			}
		} else {
			throw "Exception while waiting for activity | elem is valid: " + 
				(elem.checkIsValid() ? "true":"false") + ", type of timeout: " + typeof timeout +
				". Elem should be valid and the timeout should be a number";
		}
	},
	
	/**
	 * Dismisses the keyboard from view.
	 *
	 * @param {string} buttonName 
	 * 						The name of the button on the keyboard used to 
	 * 						dismiss the keyboard from view.
	 * @param {number} throttle 
	 * 						Optional. The timeout value we should throttle while
	 *					 	attempting to tap the button that is to dismiss the 
	 *						keyboard. 
	 */
	 /*
	 * TODO: Think about moving this somewhere else. Could it be an extension of the UIAWindow 
	 * object?
	 */
	dismissKeyboard: function(buttonName, throttle)
	{
		this.refreshAppContext(); //TODO: Remove this if it has no effect on our tests succeeding.
		try
		{
			if (typeof throttle == "undefined")
			{
				throttle = .5;
			}
			this.target.pushTimeout(throttle);
			this.app.keyboard().buttons().firstWithName(buttonName).tap();
			this.target.popTimeout();
		}
		catch (e)
		{
			IMAT.log_debug("Could not dismiss Keyboard.");
			IMAT.log_state();
		}
	},
    
    /**
     * Get an element defined within the {@link IMAT.viewMap}. <b>Note</b>, the
     * view map is defined within the project when it is generated via the IMAT 
     * command line utility and contains a set of key-value pairs mapping the 
     * keys to locator values. These values can be one of three types:
     * <ul>
     * <li>A {@link String} representing the location of the element, like<br/> 
     * <code>refreshButton : ".navigationBar().buttons()[\"Refresh\"]",</code><br/>
     * In this case, the string is evaluated and the function simply returns the result.</li>
     * <li>An {@link Array} of {@link String}s representing possible locations 
     * of the element, like <br/><code>refreshButton : 
     * [".navigationBar().buttons()[\"Refresh\"]", ".navigationBar().buttons()[0]"],</code><br/>
     * In this case, each element in the array is evaluated until the first 
     * element is found and does not contain an undefined result or UIAElementNil.</li>
     * <li>A {@link function} which returns the desired element.</li>
     * </ul>
     * More concretely, see the following example of a viewMap for an imaginary
     * project. Note the different types of locators. For the locators that are
     * of type {@type string}, the IMAT.viewMapPrefix is appended before evaluating
     * them.
     * 
     * @example
     * //viewMap.js for an imaginary project..
     * IMAT.viewMapPrefix = "UIATarget.localTarget().frontMostApp().mainWindow()";
     * IMAT.viewMap = {	
     * 	ItemListView : {
     * 		searchBar :  ".toolbars()[0].searchBars()[0]",
     * 		clearSearchButton :  [
     * 			".toolbars()[0].searchBars()[0].buttons().firstWithName(\"Clear text\")",
     * 			".toolbars()[0].searchBars()[0].buttons()[0]"],
     * 		listButton :  ".toolbars()[0].buttons()[1]",
     * 		itemWithName: function(name) {
     * 			return UIATarget.localTarget().frontMostApp().mainWindow().tableViews[0].cells()[name];
     * 		},
     * 	},
     * 	...
     * };
     * 
     * @param {string} elementID
     * 						The name of the property from the view map for the 
     * 						current view. When the value assigned to this 
     * 						property is evaluated, it should return the desired
     *						element currently on screen.
     * 
     * @requires IMAT.viewMap, A map of locators defining elements on 
     * each view. IMAT.viewMapPrefix, the prefix to all string based locators. 
     */
    getElement: function (elementID){
		var element = UIAElementNil;
        if (IMAT.viewMap && IMAT.viewMapPrefix)
        {
            IMAT.log_trace("View name is: " + this.viewName );
            IMAT.log_trace("Finding element: " + elementID);
            
            this.target.pushTimeout(5);
            var locator = IMAT.viewMap[this.viewName][elementID];
            IMAT.log_trace("the view map returned the following locator: " + locator);
            if (typeof locator == "function") {
            	IMAT.log_trace("Locator for " + elementID + " was a function.");
            	locatorArgs = [];
            	for (var argInd = 1; argInd < arguments.length; argInd++) {
            		locatorArgs.push(arguments[argInd]);
            	}
            	element = locator.apply(this, locatorArgs);
            } else if (locator instanceof Array) {
            	IMAT.log_trace("Locator for " + elementID + " was an array.");
            	var tempElement = undefined;
            	var i = 0
            	for (i = 0; i < locator.length; i++) {
            		tempElement = eval(IMAT.viewMapPrefix + locator[i])
            		if (tempElement && !(tempElement instanceof UIAElementNil)){
            			element = tempElement;
            			break
            		}
            	}
            } else if (typeof locator == "string") {
            	IMAT.log_trace("Locator for " + elementID + " was a string.");
            	element = eval(IMAT.viewMapPrefix + locator);
            }
            this.target.popTimeout();
            IMAT.log_debug("Found " + element + " when searching for " + elementID);
        } else {
            IMAT.log_warning("Both IMAT.viewMap and IMAT.viewMapPrefix must be defined to " +
            	"use IMAT.BaseView.getElement(). Cannot get element.");
        }
        if (element instanceof UIAElementNil) {
        	IMAT.log_warning("Could not locate element: " + elementID + " in view: " + this.viewName);
        }
        return element;
	},
	
	/**
	 * 	Do nothing for the desired number of seconds.
	 *	@param {Number} seconds
	 *						The number of seconds to wait. If a string is pased,
	 *						will attempt to convert to an integer using parseInt.
	 *  @returns this view.
	 */
	waitAction: function(seconds) {
		if (typeof seconds == "string")
		{
			seconds = parseInt(seconds);
			if (isNaN(seconds))
			{
				seconds = 0;
			}
		}
		this.target.delay(seconds);
		return this;
	},
	
	/**
	 * For when a given test fails, this is is part of a larger iteration of 
	 * escapeAction calls which enable our tests to keep running even if one fails
	 * @ignore 
	 */
	escapeAction: function()
	{
		//do nothing.
	},
	
});