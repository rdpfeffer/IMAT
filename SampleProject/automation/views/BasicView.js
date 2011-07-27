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
	},

	//////////////////////////////    View State Validators    /////////////////////////////////////
	
	/**
	 * Validate the view in its default initial state
	 */
	validateInitialViewState : function()
	{
		this.validateState("Basic View INITIAL", false, this, function(that){
			assertValid(that.getNavBar(), "Navigation Bar");
			assertValid(that.getBackButton(), "Back Button");
		});
	},
	
	validateCorrectPageLoadedAction : function(page)
	{
		this.validateState("Correct Page Loaded", false, this, function(that) {
			var pageNmae = that.getNavBar().name();
			assertTrue(pageNmae.indexOf(page) >= 0, page + " was not a substrng of " + pageNmae);
		});
		return this;
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	returnToHomeScreenAction : function()
	{
		this.getBackButton().tap();
		this.target.delay(1);
		return new SAMPLE.HomeScreenView();
	},
	
	escapeAction : function()
	{
		this.returnToHomeScreenAction();
		//we return this in the case of the escape action so that we will continue hitting the back 
		//button until we get to the home screen.
		return this;
	},
	
	waitForActivityAction : function()
	{
		var w = UIATarget.localTarget().frontMostApp().mainWindow();
		this.waitForActivity(w,15);
		return this;
	},
	
	//////////////////////////////////    Helper Functions    //////////////////////////////////////
	getNavBar : function()
	{
		return this.getElementFromView("navBar", "BasicView");
	},
	
	getBackButton : function()
	{
		return this.getElementFromView("backButton", "BasicView");
	}

});