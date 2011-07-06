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
var i = 1;

AUTO.StarterView = Class.extend(IMAT.BaseView, {
	
	/**
	 * Initialize the view. Grab references to all things on the screen that are of importance and
	 * set them as properties of the class.
	 */
	initialize: function()
	{
		this.parent();
		this.viewName = "StarterView";
		
		this.eventsButton = this.getElement("eventsButton");
 		this.infoButton = this.getElement("infoButton");
 		this.intuitButton = this.getElement("intuitButton");
 		this.featuresButton = this.getElement("featuresButton");
 		this.settingsButton = this.getElement("settingsButton");
		
		IMAT.log_debug("initializing AUTO." + this.viewName);
		
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
			assertTrue(that.viewName == "StarterView");
		});
	},	
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	selectEventsButtonAction : function() {
		IMAT.log_debug("Executing the testAction function which is supposed to tap the Events button");
		this.target.delay(1);
		this.getElement("eventsButton").tap();
		this.target.delay(3);
		return new AUTO.EventsView();
	},
	
	selectInfoButtonAction : function() {
		IMAT.log_debug("Selecting Info button from home screen.");
		this.target.delay(1);
		this.getElement("infoButton").tap();
		this.target.delay(3);
		return new AUTO.InfoView();
	},
	
	selectIntuitButtonAction : function() {
		IMAT.log_debug("Selecting Intuit button from home screen.");
		this.target.delay(1);
		this.getElement("intuitButton").tap();
		this.target.delay(3);
		return new AUTO.WebView();
	},
	
	selectFeaturesButtonAction : function() {
		IMAT.log_debug("Selecting Features button from home screen.");
		this.target.delay(1);
		this.getElement("featuresButton").tap();
		this.target.delay(3);
		return new AUTO.FeaturesView();	
	},
	
	selectSettingsButtonAction : function() {
		IMAT.log_debug("SelectingFeatures button from home screen.");
		this.target.delay(1);
		this.getElement("settingsButton").tap();
		this.target.delay(3);
		return new AUTO.SettingsView();
	},
	
	checkSavesTextAction : function(text) {
		IMAT.log_debug("Checking to see if it saves text when you enter into About alert.");
		this.target.delay(1);
		this.getElement("aboutButton").tap();
		IMAT.log_state();
		// enter text into text field
		// click save
		// assert that the text in field is same as text entered before
		
	},
		
});

///////// 	Alert Handler Code	 //////////
UIATarget.onAlert = function onAlert(alert) {
	alert.buttons()["Save"].tap();
	return true;
}