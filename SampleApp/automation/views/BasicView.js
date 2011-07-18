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

SAMPLE.BasicView = Class.extend(IMAT.BaseView, {
	
	/**
	 * Initialize the view. Grab references to all things on the screen that are of importance and
	 * set them as properties of the class.
	 */
	initialize: function()
	{
		this.parent();
		this.viewName = "BasicView";
		this.backButton = this.getElement("backButton");
		this.navBar = this.getElement("navBar");

		IMAT.log_debug("initializing SAMPLE." + this.viewName);

		//Validate the initial view state
		//this.validateInitialViewState();
	},
	
	escapeAction : function()
	{
		IMAT.log_debug("Attempt to back out until we hit the app home screen.");
		this.getElement("backButton").tap();
		return this;
	},
	
	waitForActivityAction : function()
	{
		var w = UIATarget.localTarget().frontMostApp().mainWindow();
		this.waitForActivity(w,10);
	},

	//////////////////////////////    View State Validators    /////////////////////////////////////
	
	/**
	 * Validate the view in its default initial state
	 */
	validateInitialViewState : function()
	{
		this.validateState("INITIAL", false, this, function(that){
			assertTrue(that.viewName == "BasicView");
		});
	},
	
	validateCorrectPageLoadedAction : function(page)
	{
		this.validateState("Facebook Mobile", false, this, function(that) {
			IMAT.log_debug(that.navBar.name() + " == " + page);
			assertTrue(that.navBar.name() == page);
		});
		return new SAMPLE.WebView();
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	returnToHomeScreenAction : function()
	{
		IMAT.log_debug("Returning to app home screen.");
		this.target.delay(1);
		this.getElement("backButton").tap();
		return new SAMPLE.StarterView();
	},
	
	waitAction : function() {
		this.target.delay(3);
		return this;
	},

});

//////////	Alert Handler Code	/////////////
UIATarget.onAlert = function onAlert(alert) {
	
	// be default, select cancel
	
}