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

AUTO.EventsView = Class.extend(IMAT.BaseView, {
	
	/**
	 * Initialize the view. Grab references to all things on the screen that are of importance and
	 * set them as properties of the class.
	 */
	initialize: function()
	{
		this.parent();
		this.viewName = "EventsView";
		this.backButton = this.getElement("backButton");
		
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
			assertTrue(that.viewName == "EventsView");
		});
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	returnToHomeScreenAction : function() {
		IMAT.log_debug("Returning to app home screen.");
		this.target.delay(1);
		this.getElement("backButton").tap();
		return new AUTO.StarterView();
	},
	
	selectListingAction : function() {
		IMAT.log_debug("Selecting an item from the Info screen table.");
		this.getElement("firstTableItem").tap();
		this.target.delay(5);
		return new AUTO.WebView();
	},
	
	scrollToBottomAction : function() {
		IMAT.log_debug("Scroll until last cell in table is visible.");
		var lastIndex = this.getElement("table").cells().length - 1;
		this.target.delay(2);
		UIATarget.localTarget().frontMostApp().mainWindow().tableViews()[0].cells()[lastIndex].scrollToVisible();
		return this;
	},

});