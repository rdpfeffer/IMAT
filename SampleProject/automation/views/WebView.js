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

SAMPLE.WebView = Class.extend(SAMPLE.BasicView, {
	
	/**
	 * Initialize the view. Grab references to all things on the screen that are of importance and
	 * set them as properties of the class.
	 */
	initialize: function()
	{
		this.parent();
		this.viewName = "WebView";
		this.backButton = this.getElement("backButton");
		
		IMAT.log_debug("initializing SAMPLE." + this.viewName);
		
		//Validate the initial view state
		this.validateInitialViewState();
	},

	//////////////////////////////    View State Validators    /////////////////////////////////////
	
	/**
	 * Validate the view in its default initial state
	 */
	validateInitialViewState : function()
	{
		this.validateState("INITIAL", false, this, function(that){
			assertTrue(that.viewName == "WebView");
		});
	},

	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	returnToInfoScreenAction : function() {
		IMAT.log_debug("Return to Info screen from web view.");
		this.getElement("backButton").tap();
		return new SAMPLE.InfoView();
	},
	
	returnToEventsScreenAction : function() {
		IMAT.log_debug("Return to Events screen from web view.");
		this.getElement("backButton").tap();
		return new SAMPLE.EventsView();
	},
	
	scrollDownAction : function() {
		IMAT.log_state();
		UIATarget.localTarget().frontMostApp().mainWindow().scrollViews()[0].scrollDown();
		return this;
	},
	
	scrollUpAction : function() {
		UIATarget.localTarget().frontMostApp().mainWindow().scrollViews()[0].scrollUp();
		return this;
	},
	
	waitForNetworkActivityAction : function()
	{
		var backButton = this.getElement("backButton");
		this.waitForActivity(backButton,5);
		return this;
	},

});