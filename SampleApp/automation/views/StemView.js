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
 * instead of the AUTO namespace.
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
			this.refreshAppContext();
			
			//TODO: Fill in the rest of this function. You should try to create
			//a generic way to dicover what view of your app you are currently 
			//on based upon what you can see on the screen. This may be a piece
			//of text on the navigation bar or even 
			return new AUTO.StarterView();
			
		}
		catch(e)
		{
			IMAT.log_warning("There was an issue trying to recover the view after an error. " + e);
			IMAT.log_state();
		}
		return specializedView;
	}
});