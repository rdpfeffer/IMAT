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
	HomeScreenView : {
		eventsButton: 			".scrollViews()[0].buttons().firstWithName(\"Events\")",
		infoButton: 			".scrollViews()[0].buttons().firstWithName(\"Info\")",
		intuitButton: 			".scrollViews()[0].buttons().firstWithName(\"Intuit\")",
		featuresButton: 		".scrollViews()[0].buttons().firstWithName(\"Features\")",
		settingsButton: 		".scrollViews()[0].buttons().firstWithName(\"Settings\")",
		aboutButton: 			".navigationBar().rightButton()",
	},
	
	BasicView : {
		navBar : 				".navigationBar()",
		backButton : 			".navigationBar().leftButton()"
	},
	
	ListView : {	
		table : 				".tableViews()[0]",
		firstTableItem : 		".tableViews()[0].cells()[0]",
		lastTableItem : 		function() {
			var tableViewCells = UIATarget.localTarget().frontMostApp().mainWindow().tableViews()[0].cells();
			return tableViewCells[tableViewCells.length - 1];
		},
	},
	
	FeaturesView : {
		detailsAndCloseButton : ".navigationBar().rightButton()",
		image : 				".images()[0]",
		contentArea : 			".contentArea()",
		imageWithName :			function(imageName) {
			return UIATarget.localTarget().frontMostApp().mainWindow().images().firstWithName(imageName);
		},
	},
	
	SettingsView : {
		singleLineTextField : 	".tableViews()[0].cells()[0].textFields()[0]",
		multipleLineTextField : ".tableViews()[0].cells()[1].textFields()[0]",
		toggleSwitch :			".tableViews()[0].cells()[4].switches()[0]",
		slider : 				".tableViews()[0].cells()[5].sliders()[0]",
	},
	
	WebView : {
		scrollView :			".scrollViews()[0]"
	},
	
	StemView : {
		navBar : 				".navigationBar()",
		backButton : 			".navigationBar().leftButton()",
	},
};