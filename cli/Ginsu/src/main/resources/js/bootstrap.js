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
 *  @namespace The IMAT namespace holds all of the formal classes of the Intuit
 *  Mobile Automation Toolkit(IMAT). If an object is part of the IMAT namespace,
 *  then it should provide some sort of common functionality applicable to all 
 *  types of consumers of this framework.
 */
var IMAT = {};

/**
 *  @namespace The IMAT.settings namespace should hold all things related to 
 *  settings used while running tests. For example,Your automation can reference 
 *  this namespace to get or set the logging levels.
 */
IMAT.settings = {};


// Declare the base inheritance framework
#import "./inheritance-2.7.js"

//Declare the constants
#import "constants.js"

// Declare our test macros
#import "./testMacros.js"

//declare our utilities
#import "./utils/exceptions.js"
#import "./utils/generators.js"
#import "./utils/String-extensions.js"
#import "./BaseTestSet.js"


if(IMAT_TARGET_PLATFORM)
{
	switch(IMAT_TARGET_PLATFORM)
	{
		case "ios":	
#import "./platforms/ios/platform.js";
			break;
	}
}
else
{
	throw "IMAT_TARGET_PLATFORM not Set! Please set to one of the following: " + IMAT.platforms; 
}

// Declare the Runner Classes
#import "./TestRunner.js"
#import "./SuiteRunner.js"

// Declare a global suite runner object that we can use to run our tests.
IMAT.suiteRunner = new IMAT.SuiteRunner();
IMAT.suiteRunner.setTestRunner(new IMAT.TestRunner());