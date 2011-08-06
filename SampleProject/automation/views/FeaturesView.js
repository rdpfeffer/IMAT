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
		this.validateInitialViewState();
	},

	//////////////////////////////    View State Validators    /////////////////////////////////////
	
	/**
	 * Validate the view in its default initial state
	 */
	validateInitialViewState : function()
	{
		this.validateState("INITIAL", false, this, function(that) {
			assertValid(that.getElement("detailsAndCloseButton"), "Details Button");
		});
	},
	
	validateImageSwitchedAction : function(previousImageName, currentImageName)
	{
		this.validateState("image switched", false, this, function(that) {
			var oldImage = UIATarget.localTarget().frontMostApp().mainWindow().images().firstWithName(previousImageName);
			var currentImage = UIATarget.localTarget().frontMostApp().mainWindow().images().firstWithName(currentImageName);
			assertFalse(that.isElementWithinViewRange(oldImage));
			assertTrue(that.isElementWithinViewRange(currentImage));
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
		this.getElement("detailsAndCloseButton").tap();
		this.validateDetailsShowing();
		this.getElement("detailsAndCloseButton").tap();
		return this;
	},
	
	zoomAction : function() {
		var c = this.getElement("contentArea");
		this.target.doubleTap(c);
		this.target.delay(1);
		return this;
	},
	
	swipeRightAction : function() {
		var p1 = { x: 200, y: 300 };
		var p2 = { x: 0, y: 300 };
		this.target.dragFromToForDuration(p1, p2, 0.5);
		this.target.delay(1);
		return this;
	},
	
	swipeLeftAction : function() {
		var p1 = { x: 0, y: 300 };
		var p2 = { x: 200, y: 300 };
		this.target.dragFromToForDuration(p1, p2, 0.5);
		this.target.delay(1);
		return this;		
	},
	
	isElementWithinViewRange : function(element)
	{
		if (element && element instanceof UIAElement)
		{
			//get reference to the x,y coords of the Application.
			var appRect = UIATarget.localTarget().frontMostApp().rect();
			var appUpperLeftX = appRect.origin.x;
			var appUpperRightX = appRect.origin.x + appRect.size.width;
			var appUpperLeftY = appRect.origin.y;
			var appLowerLeftY = appRect.origin.y + appRect.size.height;
			
			//get reference to the x,y coords of the points defining the element
			var elemRect = element.rect();
			var elemUpperLeftX, elemLowerLeftX = elemRect.origin.x;
			var elemUpperRightX, elemLowerRightX = elemRect.origin.x + elemRect.size.width;
			var elemUpperLeftY, elemUpperRightY = elemRect.origin.y;
			var elemLowerLeftY, elemLowerRightY = elemRect.origin.y + elemRect.size.height;
			
			//This helper function will help us determine if a given point of the element falls 
			//within the range of the application.
			var testPoint = function(x, y) {
				var numberXVal = new Number(x);
				var numberYVal = new Number(y);
				return (numberXVal.isBetween(appUpperLeftX, appUpperRightX) && 
					numberYVal.isBetween(appUpperLeftY, appLowerLeftY))
			};
			
			//If any of the 4 points of the element fall within the range of the view of the 
			//application, then we can say that this element is within the view range
			return (testPoint(elemUpperLeftX, elemUpperLeftY) ||
					testPoint(elemUpperRightX, elemUpperRightY) ||
					testPoint(elemLowerLeftX, elemLowerLeftY) ||
					testPoint(elemLowerRightX, elemLowerRightY));
			
		}
		else
		{
			throw "given parameter \"element\" was not of type UIAElement: " + element;
		}
	}

});