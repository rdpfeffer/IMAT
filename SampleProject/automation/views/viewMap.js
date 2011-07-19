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
	
	BasicView : {
		backButton : ".navigationBar().leftButton()",
		navBar : ".navigationBar()",
	},
	
	ListView : {
		navBar : ".navigationBar()",
		backButton : ".navigationBar().leftButton()",
		table : ".tableViews()[0]",
		firstTableItem : ".tableViews()[0].cells()[0]",
		lastTableItem : ".tableViews()[0].cells()[-1]",
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
		rightButton : "navigationBar().rightButton()",
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
		singleLineTextField : ".tableViews()[0].cells()[0].textFields()[0]",
		multipleLineTextField : ".tableViews()[0].cells()[1].textFields()[0]",
		toggleSwitch : ".tableViews()[0].cells()[4].switches()[0]",
		slider : ".tableViews()[0].cells()[5].sliders()[0]",
	},
	
	StemView : {
		navBar : ".navigationBar()",
	}
};