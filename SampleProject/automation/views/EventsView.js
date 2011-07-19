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

SAMPLE.EventsView = Class.extend(SAMPLE.ListView, {
	
	/**
	 * Initialize the view. Grab references to all things on the screen that are of importance and
	 * set them as properties of the class.
	 */
	initialize: function()
	{
		this.parent();
		this.viewName = "EventsView";
		this.backButton = this.getElement("backButton");
		this.navBar = this.getElement("navBar");
		this.navBarTitle = this.getElement("navBarTitle");
		
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
		this.validateState("INITIAL", false, this, function(that){
			assertTrue(that.viewName == "EventsView");
		});
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	// Actions inherited from ListView.js
	// No additional actions
	
});