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

SAMPLE.SuiteHandler = Class.create({
	
	initialize: function()
	{
		//do nothing. All objects created using Class.create must have an initialize function.
	},
	
	/**
	 * Initializes the suite before any testSets are run
	 */
	initSuite: function()
	{
		jones.foo();
	},
	
	/**
	 * Cleans up the suite after all testSets are run
	 */
	cleanUpSuite: function()
	{
		IMAT.log_trace("Cleaning up Suite");
	}
});
