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

SAMPLE.ListView = Class.extend(SAMPLE.BasicView, {
	
	/**
	 * Initialize the view. Grab references to all things on the screen that are of importance and
	 * set them as properties of the class.
	 */
	initialize: function()
	{
		this.parent();
		this.viewName = "ListView";

		IMAT.log_debug("initializing SAMPLE." + this.viewName);

		//Validate the initial view state
		//this.validateInitialViewState();
	},

	//////////////////////////////    View State Validators    /////////////////////////////////////
	
	/**
	 * Validate the view in its default initial state
	 */
	validateInitialViewState : function()
	{
		this.validateState("INITIAL", false, this, function(that){
			assertTrue(that.viewName == "ListView");
		});
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	selectListingAction : function() {
		IMAT.log_debug("Selecting an item from the table.");
		this.getElement("firstTableItem").tap();
		this.target.delay(5);
		return new SAMPLE.WebView();
	},
	
	scrollToTopAction : function() {
		IMAT.log_debug("Scroll until first cell in table is visible");
		this.target.delay(2);
		UIATarget.localTarget().frontMostApp().mainWindow().tableViews()[0].cells()[0].scrollToVisible();
		return this;
	},
	
	scrollToBottomAction : function() {
		IMAT.log_debug("Scroll until last cell in table is visible.");
		var lastIndex = this.getElement("table").cells().length - 1;
		this.target.delay(2);
		UIATarget.localTarget().frontMostApp().mainWindow().tableViews()[0].cells()[lastIndex].scrollToVisible();
		return this;
	},
	
});