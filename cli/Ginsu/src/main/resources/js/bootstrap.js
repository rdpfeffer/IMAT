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

/**
 * This is to encapsulate all JS classes within your own namespace to prevent overwriting existing 
 * framework classes. This object creates that namespace for us. All JS Prototypes created
 * as "classes" as well as any functions will be properties in the namespace of this global object.
 */
var GINSU = {};
GINSU.settings = {};


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


if(GINSU_TARGET_PLATFORM)
{
	switch(GINSU_TARGET_PLATFORM)
	{
		case "ios":	
#import "./platforms/ios/platform.js";
			break;
	}
}
else
{
	throw "GINSU_TARGET_PLATFORM not Set! Please set to one of the following: " + GINSU.platforms; 
}

// Declare the Runner Classes
#import "./TestRunner.js"
#import "./SuiteRunner.js"

// Declare a global suite runner object that we can use to run our tests.
GINSU.suiteRunner = new GINSU.SuiteRunner();
GINSU.suiteRunner.setTestRunner(new GINSU.TestRunner());