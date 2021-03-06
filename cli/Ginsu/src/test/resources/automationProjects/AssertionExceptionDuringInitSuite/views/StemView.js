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
 * StemView.js
 * 
 * This view is responsible for discovering its current environment and 
 * specializing itself based upon what is around it. Just like a "stem cell," 
 * it will use the environmental factors, in this case the application state, to
 * beget a new view to match what it detects on the screen. Also note that this
 * is a rare exception where we will have a view live under the IMAT namespace
 * instead of the SAMPLE namespace.
 */
IMAT.StemView = Class.extend(IMAT.BaseView, {
	
	/**
	 * Initialize the stem view Factory
	 */
	initialize: function()
	{
		this.parent();
		//do nothing.
	},
	
	/**
	 * Get a specialized view depending on what we see on the screen.
	 *
	 * @return {IMAT.BaseView} the Specialized view based upon what was seen 
	 * on screen. 
	 */
	getSpecializedView: function()
	{	
		var specializedView = null;
		try
		{
			IMAT.log_info("Stem View beginning specialization.");
			this.refreshAppContext();
			var navBar = this.getElementFromView("navBar", "BasicView");
			assertValid(navBar, "Navigation Bar");
			var navBarName = navBar.name();
			if (navBarName === "Sample App") {
				IMAT.log_info("Specializing to HomeScreenView");
				specializedView = new SAMPLE.HomeScreenView();
			} else {
				IMAT.log_info("Specializing to BasicView when navBarName was: " + navBarName);
				specializedView = new SAMPLE.BasicView();
			}
		}
		catch(e)
		{
			IMAT.log_warning("Failed trying to recover the StemView after an error: " + e);
			IMAT.log_state();
		}
		return specializedView;
	}
});