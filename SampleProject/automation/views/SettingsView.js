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

SAMPLE.SettingsView = Class.extend(SAMPLE.BasicView, {
	
	/**
	 * Initialize the view. Grab references to all things on the screen that are of importance and
	 * set them as properties of the class.
	 */
	initialize: function()
	{
		this.parent();
		this.viewName = "SettingsView";
		this.backButton = this.getElement("backButton");
		this.singleLineTextField = this.getElement("singleLineTextField");
		this.multipleLineTextField = this.getElement("multipleLineTextField");
		this.toggleSwitch = this.getElement("toggleSwitch");
		this.slider = this.getElement("slider");
				
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
			assertTrue(that.viewName == "SettingsView");
		});
	},
	
	validateAllFieldsEditedAction : function()
	{
		this.validateState("Edited Preferences", false, this, function(that) {
			IMAT.log_debug(that.singleLineTextField.value() + " == single line comment");
			assertTrue(that.singleLineTextField.value() == "single line comment");
			
			IMAT.log_debug(that.multipleLineTextField.value() + " == multiline comments are much longer than single line comments and hopefully this takes up more than one line");
			assertTrue(that.multipleLineTextField.value() == "multiline comments are much longer than single line comments and hopefully this takes up more than one line");
			
			IMAT.log_debug(that.toggleSwitch.value() + " == true");
			assertTrue(that.toggleSwitch.value() == true);
			
			IMAT.log_debug(that.slider.value().toString() + " == 100%");
			assertTrue(that.slider.value().toString() == "100%");
		});
		return this;
	},	
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	enterSingleLineCommentAction : function(text)
	{
		IMAT.log_debug("Enter single line comment.");
		this.getElement("singleLineTextField").setValue(text);
		return this;
	},
	
	enterMultiLineCommentAction : function(text)
	{
		IMAT.log_debug("Enter multiple line comment.");
		this.getElement("multipleLineTextField").setValue(text);
		return this;
	},
	
	toggleSwitchAction : function(state)
	{
		IMAT.log_debug("Toggle the switch to state: " + state);
		if (state == "on") {
			this.getElement("toggleSwitch").setValue(true);
		}
		else if (state == "off") {
			this.getElement("toggleSwitch").setValue(false);
		}
		return this;
	},
	
	adjustSliderToValueAction : function(value)
	{
		IMAT.log_debug("Adjust slider to full value.");
		this.getElement("slider").dragToValue(value);
		return this;
	},

});