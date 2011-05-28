/**
 * 
 * This file defines how objects in our UI are referenced. Whenever we make a call to getElement(), 
 * this object map is referenced and returns the corresponding locator for the element in that view.
 * This makes the way we reference UIAElements more consistent and maintainable.
 */

IMAT.viewMapPrefix = "UIATarget.localTarget().frontMostApp().mainWindow()";
IMAT.viewMap = {	
	StarterView : {
		
		button: [
			".buttons()[0]",
			".buttons().firstWithName(\"Some Button\")"
		],
		textField: function() {
			return UIATarget.localTarget().frontMostApp().mainWindow().textFields()[0];
		},
		someText: ".scrollViews()[0];"
	}
};