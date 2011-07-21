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

SAMPLE.StarterView = Class.extend(IMAT.BaseView, {
	
	/**
	 * Initialize the view. Grab references to all things on the screen that are of importance and
	 * set them as properties of the class.
	 */
	initialize: function()
	{
		this.parent();
		this.viewName = "StarterView";
		this.backButton = this.getElement("backButton");
		
		this.eventsButton = this.getElement("eventsButton");
 		this.infoButton = this.getElement("infoButton");
 		this.intuitButton = this.getElement("intuitButton");
 		this.featuresButton = this.getElement("featuresButton");
 		this.settingsButton = this.getElement("settingsButton");

		IMAT.log_debug("initializing SAMPLE." + this.viewName);

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
		IMAT.waitForActivity(w,10);
	},

	//////////////////////////////    View State Validators    /////////////////////////////////////
	
	/**
	 * Validate the view in its default initial state
	 */
	validateInitialViewState : function()
	{
		this.validateState("INITIAL", false, this, function(that){
			assertTrue(that.viewName == "StarterView");
		});
	},
	
	validateAlertText : function(text)
	{
		this.validateState("Alert Saved", false, this, function(that) {
			assertTrue(text == "this is a test message");
		});
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	selectEventsButtonAction : function() {
		IMAT.log_debug("Executing the testAction function which is supposed to tap the Events button");
		this.target.delay(1);
		this.getElement("eventsButton").tap();
		this.target.delay(1);
		return new SAMPLE.EventsView();
	},
	
	selectInfoButtonAction : function() {
		IMAT.log_debug("Selecting Info button from home screen.");
		this.target.delay(1);
		this.getElement("infoButton").tap();
		this.target.delay(1);
		return new SAMPLE.InfoView();
	},
	
	selectIntuitButtonAction : function() {
		IMAT.log_debug("Selecting Intuit button from home screen.");
		this.target.delay(1);
		this.getElement("intuitButton").tap();
		this.target.delay(1);
		return new SAMPLE.WebView();
	},
	
	selectFeaturesButtonAction : function() {
		IMAT.log_debug("Selecting Features button from home screen.");
		this.target.delay(1);
		this.getElement("featuresButton").tap();
		this.target.delay(1);
		return new SAMPLE.FeaturesView();	
	},
	
	selectSettingsButtonAction : function() {
		IMAT.log_debug("Selecting Features button from home screen.");
		this.target.delay(1);
		this.getElement("settingsButton").tap();
		this.target.delay(1);
		return new SAMPLE.SettingsView();
	},
	
	swipeRightAction : function() {
		IMAT.log_debug("Swipe screen to the right.");
		var p1 = { x: 200, y: 300 };
		var p2 = { x: 0, y: 300 };
		this.target.dragFromToForDuration(p1, p2, 0.5);
		return this;
	},
	
	swipeLeftAction : function() {
		IMAT.log_debug("Swipe screen to the left.");
		var p1 = { x: 0, y: 300 };
		var p2 = { x: 200, y: 300 };
		this.target.dragFromToForDuration(p1, p2, 0.5);
		return this;		
	},
	
	waitAction : function() {
		this.target.delay(2);
		return this;
	},
	
	selectAboutButtonAction : function() {
		this.expectAlertAction();
		this.getElement("aboutButton").tap();
		return this;
	},
	
	expectAlertAction : function() {
		var ref = this;
		this.defaultAlert = UIATarget.onAlert;
		UIATarget.onAlert = function onAlert(alert) {
			alert.textFields()[0].setValue("this is a test message");
			alert.buttons()["Save"].tap();
		}
	},
	
	verifyAlertSavedAction : function() {
		this.expectAlertAgainAction();
		this.getElement("aboutButton").tap();
		return this;
	},
	
	expectAlertAgainAction : function() {
		var ref = this;
		this.defaultAlert = UIATarget.onAlert;
		UIATarget.onAlert = function onAlert(alert) {
			var text = alert.textFields()[0].value();
			validateAlertText(text);
			alert.buttons()["Cancel"].tap();
		}
	},
	
});