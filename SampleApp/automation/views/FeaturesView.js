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

AUTO.FeaturesView = Class.extend(IMAT.BaseView, {
	
	/**
	 * Initialize the view. Grab references to all things on the screen that are of importance and
	 * set them as properties of the class.
	 */
	initialize: function()
	{
		this.parent();
		this.viewName = "FeaturesView";
		this.backButton = this.getElement("backButton");
		this.detailsAndCloseButton = this.getElement("detailsAndCloseButton");
		
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
			assertTrue(that.viewName == "FeaturesView");
		});
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	returnToHomeScreenAction : function() {
		IMAT.log_debug("Returning to app home screen.");
		this.target.delay(1);
		this.getElement("backButton").tap();
		return new AUTO.StarterView();
	},
	
	viewAndCloseDetailsAction : function() {
		IMAT.log_debug("Click on details button, wait 2 seconds, click on close button.");
		this.getElement("detailsAndCloseButton").tap();
		this.target.delay(2);
		this.getElement("detailsAndCloseButton").tap();
		return this;
	},
	
	zoomAction : function() {
		IMAT.log_debug("Zoom on image by double tapping.");
		var c = this.getElement("contentArea");
		this.target.doubleTap(c);
		this.target.delay(2);
		return this;
	},
	
	swipeRightAction : function() {
		IMAT.log_debug("Swiping right to view other images.");
		var c = this.getElement("contentArea");
		var p1 = { x: 80, y: 50 };
		var p2 = { x: 20, y: 50 };
		this.target.flickFromTo(p1,p2);
		return this;
	},
	
});