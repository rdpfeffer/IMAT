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
		this.validateInitialViewState();
	},

	//////////////////////////////    View State Validators    /////////////////////////////////////
	
	/**
	 * Validate the view in its default initial state
	 */
	validateInitialViewState : function()
	{
		this.validateState("INITIAL", false, this, function(that){
			assertValid(that.getElement("singleLineTextField"), "Single Line Text Field");
			assertValid(that.getElement("multipleLineTextField"), "Multi Line Text Field");
			assertValid(that.getElement("toggleSwitch"), "Toggle Switch");
			assertValid(that.getElement("slider"), "Slider");
		});
	},
	
	validateAllFieldsAction : function(singleLineVal, multiLineVal, toggleVal, sliderVal)
	{
		this.validateState("Edited Preferences", false, this, function(that) {
			assertEquals(that.getElement("singleLineTextField").value(), singleLineVal);
			assertEquals(that.getElement("multipleLineTextField").value(), multiLineVal);
			assertEquals(that.getElement("toggleSwitch").value(), toggleVal);
			assertEquals(that.getElement("slider").value().toString(), sliderVal);
		});
		return this;
	},	
	
	//////////////////////////////////    View Actions    //////////////////////////////////////////
	
	enterSingleLineCommentAction : function(text)
	{
		var textField = this.getElement("singleLineTextField");
		textField.scrollToVisible();
		textField.setValue(text);
		return this;
	},
	
	enterMultiLineCommentAction : function(text)
	{
		var textField = this.getElement("multipleLineTextField");
		textField.scrollToVisible();
		textField.setValue(text);
		return this;
	},
	
	toggleSwitchAction : function(state)
	{
		var toggle = this.getElement("toggleSwitch");
		toggle.scrollToVisible();
		if (state == "on") {
			toggle.setValue(true);
		}
		else if (state == "off") {
			toggle.setValue(false);
		}
		return this;
	},
	
	adjustSliderToValueAction : function(value)
	{
		var slider = this.getElement("slider");
		slider.scrollToVisible();
		slider.dragToValue(value);
		return this;
	}

});