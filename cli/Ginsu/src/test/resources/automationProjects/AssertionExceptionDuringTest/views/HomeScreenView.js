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

SAMPLE.HomeScreenView = Class.extend(IMAT.BaseView, {
	
	/**
	 * Initialize the view. Grab references to all things on the screen that are of importance and
	 * set them as properties of the class.
	 */
	initialize: function()
	{
		this.parent();
		this.viewName = "HomeScreenView";
		//Validate the initial view state
		this.validateInitialViewState();
	},
	
	escapeAction : function()
	{
		return this.swipeLeftAction();
	},
	
	waitForActivityAction : function()
	{
		var w = UIATarget.localTarget().frontMostApp().mainWindow();
		IMAT.waitForActivity(w, 10);
	},

	//////////////////////////////    View State Validators    /////////////////////////////////////
	
	/**
	 * Validate the view in its default initial state
	 */
	validateInitialViewState : function()
	{
		this.validateState("INITIAL", false, this, function(that){
			assertValid(that.getElementFromView("navBarWithTitle", "BasicView", "Sample App"), 
				"Title of Nav Bar");
			assertValid(that.getElement("eventsButton"), "Events Button");
			assertValid(that.getElement("infoButton"), "Info Button");
			assertValid(that.getElement("intuitButton"), "Intuit Button");
			assertValid(that.getElement("featuresButton"), "Features Button");
		});
	},
	
	validateScreenShowsLeftmostIconsAction: function()
	{
		this.validateState("Screen shows Left-most icons", false, this, function(that){
			//the variable rect is where the settings button should be 
			//after swiping to the left
			assertValid(that.getElement("eventsButton").withPredicate("rect.origin.x == 0"),
				"settings button off the screen");
		});
		return this;
	},
	
	validateScreenShowsRightmostIconsAction: function()
	{
		this.validateState("Screen shows Right-most icons", false, this, function(that){
			//the variable rect is where the settings button should be 
			//after swiping to the right
			assertValid(that.getElement("settingsButton").withPredicate("rect.origin.x == 0"),
				"settings button on the screen");
		});
		return this;
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	selectEventsButtonAction : function() {
		this.getElement("eventsButton").tap();
		return new SAMPLE.EventsView();
	},
	
	selectInfoButtonAction : function() {
		this.getElement("infoButton").tap();
		return new SAMPLE.InfoView();
	},
	
	selectIntuitButtonAction : function() {
		this.getElement("intuitButton").tap();
		return new SAMPLE.WebView();
	},
	
	selectFeaturesButtonAction : function() {
		this.getElement("featuresButton").tap();
		return new SAMPLE.FeaturesView();	
	},
	
	selectSettingsButtonAction : function() {
		this.getElement("settingsButton").tap();
		return new SAMPLE.SettingsView();
	},
	
	swipeRightAction : function() {
		var p1 = { x: 200, y: 300 };
		var p2 = { x: 0, y: 300 };
		this.waitForNavigationToComplete();
		this.target.dragFromToForDuration(p1, p2, 0.5);
		this.validateScreenShowsRightmostIconsAction();
		return this;
	},
	
	swipeLeftAction : function() {
		var p1 = { x: 0, y: 300 };
		var p2 = { x: 200, y: 300 };
		this.waitForNavigationToComplete();
		this.target.dragFromToForDuration(p1, p2, 0.5);
		this.validateScreenShowsLeftmostIconsAction();
		return this;		
	},
	
	selectAboutButtonAction : function() {
		this.getElement("aboutButton").tap();
		return this;
	},
	
	waitForNavigationToComplete : function() {
		this.target.pushTimeout(0.05);
		if (this.isElementFromViewPresent("backButton", "BasicView")) {
			this.getElementFromView("backButton", "BasicView").waitForInvalid();
		}
		this.target.popTimeout();
	}
});