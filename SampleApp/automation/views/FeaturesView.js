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

SAMPLE.FeaturesView = Class.extend(SAMPLE.BasicView, {
	
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
		this.validateState("INITIAL", false, this, function(that) {
			assertTrue(that.viewName == "FeaturesView");
		});
	},
	
	validateImageSwitchedAction : function(previousImage, presentImage)
	{
		this.validateState("image switched", false, this, function(that) {
			var oldImage = UIATarget.localTarget().frontMostApp().mainWindow().images().firstWithName(previousImage);
			var currentImage = UIATarget.localTarget().frontMostApp().mainWindow().images().firstWithName(presentImage);
			IMAT.log_debug(oldImage.name() + " == " + previousImage);
			assertTrue(oldImage.name() == previousImage);
			IMAT.log_debug(currentImage.name() + " == " + presentImage);
			assertTrue(currentImage.name() == presentImage);
		});
		return this;
	},
	
	validateDetailsShowing : function()
	{
		this.validateState("Details showing", false, this, function(that) {
			var rightButton = that.getElement("detailsAndCloseButton");
			IMAT.log_debug(rightButton.name() + " == Close");
			assertTrue(rightButton.name() == "Close");
		});
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	viewAndCloseDetailsAction : function() {
		IMAT.log_debug("Click on details button, wait 2 seconds, click on close button.");
		this.getElement("detailsAndCloseButton").tap();
		this.validateDetailsShowing();
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

});