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

/**
 * 
 * This file defines how objects in our UI are referenced. Whenever we make a call to getElement(), 
 * this object map is referenced and returns the corresponding locator for the element in that view.
 * This makes the way we reference UIAElements more consistent and maintainable.
 */

IMAT.viewMapPrefix = "UIATarget.localTarget().frontMostApp().mainWindow()";
IMAT.viewMap = {	
	StarterView : {
		eventsButton: ".scrollViews()[0].buttons().firstWithName(\"Events\")",
		infoButton: ".scrollViews()[0].buttons().firstWithName(\"Info\")",
		intuitButton: ".scrollViews()[0].buttons().firstWithName(\"Intuit\")",
		featuresButton: ".scrollViews()[0].buttons().firstWithName(\"Features\")",
		settingsButton: ".scrollViews()[0].buttons().firstWithName(\"Settings\")",
		aboutButton: ".navigationBar().rightButton()",
	},
	
	EventsView : {
		navBar : ".navigationBar()",
		backButton : ".navigationBar().leftButton()",
		table : ".tableViews()[0]",
		firstTableItem : ".tableViews()[0].cells()[0]",
		lastTableItem : ".tableViews()[0].cells()[-1]",
	},
	
	InfoView : {
		navBar : ".navigationBar()",
		backButton : ".navigationBar().leftButton()",
		firstTableItem : ".tableViews()[0].cells()[0]",
	},
	
	FeaturesView : {
		navBar : ".navigationBar()",
		backButton : ".navigationBar().leftButton()",
		detailsAndCloseButton : ".navigationBar().rightButton()",
		image : ".images()[0]",
		contentArea : ".contentArea()",
	},
	
	WebView : {
		navBar : ".navigationBar()",
		backButton : ".navigationBar().leftButton()",
	},
	
	SettingsView : {
		navBar : ".navigationBar()",
		backButton : ".navigationBar().leftButton()",
	},
};