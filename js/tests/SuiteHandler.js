/*******************************************************************************
* Copyright (c) 2009 Intuit, Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.opensource.org/licenses/eclipse-1.0.php
* 
* Contributors:
*     Intuit, Inc - initial API and implementation
*******************************************************************************/
GINSU.SuiteHandler = Class.create({
	
	initialize: function()
	{
		
	},
	
	/**
	 * Initializes the suite before any testSets are run
	 */
	initSuite: function()
	{
		GINSU.log_trace("Initializing Suite");
	},
	
	/**
	 * Cleans up the suite after all testSets are run
	 */
	cleanUpSuite: function()
	{
		GINSU.log_trace("Cleaning up Suite");
	}
});
