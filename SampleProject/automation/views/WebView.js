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
		//Validate the initial view state
		this.validateInitialViewState();
	},

	//////////////////////////////    View State Validators    /////////////////////////////////////
	
	/**
	 * Validate the view in its default initial state
	 */
	validateInitialViewState : function()
	{
		this.parent();
		this.validateState("INITIAL", false, this, function(that){
			assertValid(that.getElement("scrollView"), "Scroll View");
		});
	},

	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	returnToInfoScreenAction : function() {
		this.getBackButton().tap();
		this.target.delay(.5);
		return new SAMPLE.InfoView();
	},
	
	returnToEventsScreenAction : function() {
		this.getBackButton().tap();
		this.target.delay(.5);
		return new SAMPLE.EventsView();
	},
	
	scrollDownAction : function() {
		this.getElement("scrollView").scrollDown();
		return this;
	},
	
	scrollUpAction : function() {
		this.getElement("scrollView").scrollUp();
		return this;
	},
	
	waitForActivityAction : function()
	{
		this.waitForActivity(this.getNavBar(), 15);
		return this;
	}

});