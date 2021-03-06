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
	},

	//////////////////////////////    View State Validators    /////////////////////////////////////
	
	/**
	 * Validate the view in its default initial state
	 */
	validateInitialViewState : function()
	{
		this.parent();
		this.validateState("List View INITIAL", false, this, function(that){
			assertValid(that.getTable(), "Table");
		});
	},
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	selectListingAction : function() {
		this.getElementFromView("firstTableItem", "ListView").tap();
		return new SAMPLE.WebView();
	},
	
	scrollToTopAction : function() {
		this.getElementFromView("firstTableItem", "ListView").scrollToVisible();
		return this;
	},
	
	scrollToBottomAction : function() {
		this.getElementFromView("lastTableItem", "ListView").scrollToVisible();
		return this;
	},
	
	scrollDownAction : function() {
		this.getTable().scrollDown();
		return this;
	},
	
	scrollUpAction : function() {
		this.getTable().scrollUp();
		return this;
	},
	
	//////////////////////////////////    Helper Functions    //////////////////////////////////////
	
	getTable : function()
	{
		return this.getElementFromView("table", "ListView");
	}
	
});