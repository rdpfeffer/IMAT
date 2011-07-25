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
		
		this.eventsButton = this.getElement("eventsButton");
 		this.infoButton = this.getElement("infoButton");
 		this.intuitButton = this.getElement("intuitButton");
 		this.featuresButton = this.getElement("featuresButton");
 		this.settingsButton = this.getElement("settingsButton");
		//Validate the initial view state
		this.validateInitialViewState();
	},
	
	escapeAction : function()
	{
		this.swipeLeftAction();
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
			assertValid(that.eventsButton, "Events Button");
			assertValid(that.infoButton, "Info Button");
			assertValid(that.intuitButton, "Intuit Button");
			assertValid(that.featuresButton, "Features Button");
		});
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	selectEventsButtonAction : function() {
		this.getElement("eventsButton").tap();
		this.target.delay(1);
		return new SAMPLE.EventsView();
	},
	
	selectInfoButtonAction : function() {
		this.getElement("infoButton").tap();
		this.target.delay(1);
		return new SAMPLE.InfoView();
	},
	
	selectIntuitButtonAction : function() {
		this.getElement("intuitButton").tap();
		this.target.delay(1);
		return new SAMPLE.WebView();
	},
	
	selectFeaturesButtonAction : function() {
		this.getElement("featuresButton").tap();
		this.target.delay(1);
		return new SAMPLE.FeaturesView();	
	},
	
	selectSettingsButtonAction : function() {
		this.getElement("settingsButton").tap();
		this.target.delay(1);
		return new SAMPLE.SettingsView();
	},
	
	swipeRightAction : function() {
		var p1 = { x: 200, y: 300 };
		var p2 = { x: 0, y: 300 };
		this.target.dragFromToForDuration(p1, p2, 0.5);
		this.target.delay(.5);
		return this;
	},
	
	swipeLeftAction : function() {
		var p1 = { x: 0, y: 300 };
		var p2 = { x: 200, y: 300 };
		this.target.dragFromToForDuration(p1, p2, 0.5);
		this.target.delay(.5);
		return this;		
	},
	
	selectAboutButtonAction : function() {
		this.getElement("aboutButton").tap();
		return this;
	}
});